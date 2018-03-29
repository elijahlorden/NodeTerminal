package com.eli.networkterminal.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.UUID;

import com.eli.networkterminal.clientpackethandlers.*;
import com.eli.networkterminal.main.Constants;
import com.eli.networkterminal.objects.Packet;

public class ClientNode {
	
	private Socket clientNodeSocket;
	private ClientListenerThread listenerThread;
	private DataInputStream input;
	private DataOutputStream output;
	
	public String hostname;
	public int port;
	
	private UUID serverConnectionID;
	private UUID serverID;
	
	public UUID getServerID() {
		return serverID;
	}

	public void setServerID(UUID serverID) {
		this.serverID = serverID;
	}

	public final ArrayList<PacketHandler> handlers;
	
	public ClientNode() {
		this.handlers = new ArrayList<PacketHandler>();
		registerPacketHandlers();
	}
	
	public boolean send(String signal) {
		try {
			output.writeUTF(signal);
		} catch (IOException e) {
			System.out.println("Error while sending signal: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean send(Packet pkt) {
		String signal = pkt.getJSON();
		try {
			output.writeUTF(signal);
		} catch (IOException e) {
			System.out.println("Error while sending signal: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	public void handle(String signal) {
		Packet pkt = Packet.fromJSON(signal);
		if (pkt != null) {
			System.out.println("Received packet with header " + pkt.getHeader());
			boolean handled = false;
			for (PacketHandler p : handlers) {
				if (p.getHeader().equals(pkt.getHeader())) {
					handled = true;
					p.handle(pkt);
				}
			}
			if (!handled) {
				System.out.println("Could not handle packet with header '" + pkt.getHeader() + "'");
			}
		} else {
			System.out.println("Invalid packet data");
		}
	}
	
	// 0 = success, 1 = unknown host, 2 = unexpected error
	public int start(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		System.out.println("Establishing connection with Server at " + hostname + ":" + port);
		try {
			clientNodeSocket = new Socket(hostname, port);
			input = new DataInputStream(clientNodeSocket.getInputStream());
			output = new DataOutputStream(clientNodeSocket.getOutputStream());
			listenerThread = new ClientListenerThread(this, clientNodeSocket);
			System.out.println("Connected");
		} catch (UnknownHostException e ) {
			System.out.println("Unknown Host: " + hostname + ":" + port);
			return 1;
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e.getMessage());
			return 2;
		}
		return 0;
	}
	
	public void stop() {
		try {
			if (input != null) input.close();
			if (output != null) output.close();
			if (clientNodeSocket != null) clientNodeSocket.close();
		} catch (Exception e) {
			System.out.println("Error closing ClientNode: " + e.getMessage());
		}
		listenerThread.close();
		listenerThread.interrupt();
	}
	
	public boolean isConnected() {
		return clientNodeSocket != null && !clientNodeSocket.isClosed() && clientNodeSocket.isConnected() && listenerThread.isAlive() && !listenerThread.isInterrupted();
	}
	
	public UUID getServerConnectionID() {
		return serverConnectionID;
	}

	public void setServerConnectionID(UUID serverConnectionID) {
		this.serverConnectionID = serverConnectionID;
	}

	public void registerPacketHandlers() {
		handlers.add(new PacketHandlerConnectionInfo());
	}
	
}
