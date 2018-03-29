package com.eli.networkterminal.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import com.eli.networkterminal.command.CommandResponse;
import com.eli.networkterminal.main.Constants;
import com.eli.networkterminal.network.serverpackethandlers.PacketHandlerForward;
import com.eli.networkterminal.network.serverpackethandlers.PacketHandlerNodeInfo;
import com.eli.networkterminal.objects.Configuration;
import com.eli.networkterminal.objects.MainTerminalWindow;
import com.eli.networkterminal.objects.Packet;
import com.eli.networkterminal.tools.Tag;
import com.eli.networkterminal.tools.Util;

public class ServerNode implements Runnable {

	private Thread nodeThread;
	private ServerSocket serverNodeSocket;
	
	public int port;
	
	public ArrayList<ServerConnection> connections;
	
	public final ArrayList<PacketHandler> handlers;
	
	public final PacketHandler forwardPacketHandler;
	
	public final UUID serverUUID = UUID.randomUUID();
	
	public final CommandResponse terminalWrapper;
	
	public ServerNode() {
		this.handlers = new ArrayList<PacketHandler>();
		this.forwardPacketHandler = new PacketHandlerForward();
		registerPacketHandlers();
		terminalWrapper = new CommandResponse();
		init();
	}
	
	public ServerNode(MainTerminalWindow window) {
		this.handlers = new ArrayList<PacketHandler>();
		this.forwardPacketHandler = new PacketHandlerForward();
		registerPacketHandlers();
		terminalWrapper = new CommandResponse(window);
		init();
	}
	
	private void init() {
		Configuration config = new Configuration(Constants.mainConfigName);
		if (config.get("autoStartServer") == "True") {
			terminalWrapper.println("Auto-starting ServerNode");
			startNode();
		}
	}
	
	public void registerPacketHandlers() {
		handlers.add(new PacketHandlerNodeInfo());
	}
	
	@Override
	public void run() {
		while (nodeThread != null) {
			try {
				startConnection(serverNodeSocket.accept());
			} catch (Exception e) {System.out.println("Error accepting ClientNode: " + e.toString());}
		}
	}
	
	public ServerConnection getConnectionByID (UUID id) {
		for (ServerConnection c : connections) {
			if (c.getID().equals(id)) {
				return c;
			}
		}
		return null;
	}
	
	private void startConnection(Socket s) {
		System.out.println("New client connection starting");
		ServerConnection newConnection = new ServerConnection(s, this);
		connections.add(newConnection);
		try {
			newConnection.open();
			newConnection.start();
			// send info packet
			Packet infoPacket = new Packet("ConnectionInfo", serverUUID.toString(), newConnection.getID().toString());
			infoPacket.setData(new String[][]{{newConnection.getID().toString(), serverUUID.toString()}}); //{{Connection UUID, Server UUID}}
			newConnection.send(infoPacket.getJSON());
		} catch (IOException e) {
			System.out.println("Error starting connection thread: " + e.getMessage());
		}
	}
	
	public synchronized void handleIncomingSignal(ServerConnection connection, String signal) {
		Packet pkt = Packet.fromJSON(signal);
		if (pkt != null && pkt.getHeader() != null) {
			System.out.println("Received packet with header '" + pkt.getHeader() + "'");
			boolean handled = false;
			for (PacketHandler p : handlers) {
				if (p.getHeader().equals(pkt.getHeader())) {
					handled = true;
					p.handle(pkt);
				}
			}
			if (pkt.getReceiverName() != null && (!handled && !pkt.getReceiverName().equals(Constants.serverName))) {
				System.out.println("");
			} else if (handled) {
				
			} else {
				System.out.println("Could not handle packet with header '" + pkt.getHeader() + "'");
			}
		} else {
			System.out.println("Invalid packet data");
		}
	}
	
	public synchronized void removeConnection(UUID id) {
		ServerConnection connection = getConnectionByID(id);
		if (connection != null) {
			System.out.println("Removing ServerNode thread " + id);
			connections.remove(connection);
			try {
				connection.close();
			} catch (IOException e) {
				System.out.println("Error closing ServerNode thread: " + e.getMessage());
			}
		}
	}
	
	public boolean startNode() {
		if (nodeThread == null) {
			Configuration config = new Configuration(Constants.mainConfigName);
			String configPort = config.get("serverPort");
			Object configPortn = Util.transformString(configPort);
			int portn;
			if (Util.getType(configPortn).equals("Integer") && (int) configPortn <= 65535) {
				portn = (int) configPortn;
			} else {
				System.out.println("Invalid serverPort specified in config, using default port of 57635");
				terminalWrapper.println(Tag.multiTag("color red", "i") + "Invalid serverPort specified in config, using default port of 57635");
				portn = 57635;
			}
			this.port = portn;
			this.connections = new ArrayList<ServerConnection>();
			try {
				System.out.println("Opening ServerNode on port " + port);
				serverNodeSocket = new ServerSocket(port);
				System.out.println("ServerNode running on port " + port);
			} catch (Exception e) {e.printStackTrace(); return false;}
			nodeThread = new Thread(this);
			nodeThread.start();
			return true;
		}
		return false;
	}
	
	public void stopNode() {
		for (ServerConnection connection : connections) {
			Packet endPacket = new Packet("CloseConnection", serverUUID.toString(), connection.getID().toString());
			endPacket.setData(new String[][]{{"ServerNode terminated"}});
			connection.send(endPacket.getJSON());
		}
		if (nodeThread != null) {
			nodeThread.interrupt();
			nodeThread = null;
		}
	}
	
	
	
	
}
