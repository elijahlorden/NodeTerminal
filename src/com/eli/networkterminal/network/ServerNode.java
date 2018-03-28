package com.eli.networkterminal.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import com.eli.networkterminal.main.Constants;
import com.eli.networkterminal.network.serverpackethandlers.*;
import com.eli.networkterminal.objects.Packet;

public class ServerNode implements Runnable {

	private Thread nodeThread;
	private ServerSocket serverNodeSocket;
	
	public final int port;
	
	public final ArrayList<ServerConnection> connections;
	
	public final ArrayList<PacketHandler> handlers;
	
	public final PacketHandler forwardPacketHandler;
	
	
	public ServerNode(int port) {
		this.port = port;
		this.connections = new ArrayList<ServerConnection>();
		this.handlers = new ArrayList<PacketHandler>();
		this.forwardPacketHandler = new PacketHandlerForward();
		registerPacketHandlers();
		try {
		System.out.println("Opening ServerNode on port " + port);
		serverNodeSocket = new ServerSocket(port);
		System.out.println("ServerNode running on port " + port);
		startNode();
		} catch (Exception e) {e.printStackTrace();}
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
	
	public ServerConnection getConnection(String s) {
		
		
		return null;
	}
	
	private void startConnection(Socket s) {
		System.out.println("New client connection starting");
		ServerConnection newConnection = new ServerConnection(s, this);
		try {
			newConnection.open();
			newConnection.start();
			// send info packet
			Packet infoPacket = new Packet("ConnectionInfo");
			infoPacket.setData(new String[][]{{newConnection.getID().toString()}});
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
	
	public void startNode() {
		if (nodeThread == null) {
			nodeThread = new Thread(this);
			nodeThread.start();
		}
	}
	
	public void stopNode() {
		if (nodeThread != null) {
			nodeThread.interrupt();
			nodeThread = null;
		}
	}
	
	
	
	
}
