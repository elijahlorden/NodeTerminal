package com.eli.networkterminal.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerNode implements Runnable {

	private Thread nodeThread;
	private ServerSocket serverNodeSocket;
	
	public final int port;
	
	public final ArrayList<ServerConnection> connections;
	
	
	public ServerNode(int port) {
		this.port = port;
		this.connections = new ArrayList<ServerConnection>();
		try {
		System.out.println("Opening ServerNode on port " + port);
		serverNodeSocket = new ServerSocket(port);
		System.out.println("ServerNode running on port " + port);
		startNode();
		} catch (Exception e) {e.printStackTrace();}
		
	}

	@Override
	public void run() {
		while (true) {
			try {
				startConnection(serverNodeSocket.accept());
			} catch(Exception e) {System.out.println("Error accepting ClientNode: " + e.toString());}
		}
	}
	
	public ServerConnection getConnectionByID (int id) {
		for (ServerConnection c : connections) {
			if (c.getID() == id) {
				return c;
			}
		}
		return null;
	}
	
	private void startConnection(Socket s) {
		System.out.println("New client connection starting");
		ServerConnection newConnection = new ServerConnection(s, this);
		try {
			newConnection.open();
			newConnection.start();
		} catch (IOException e) {
			System.out.println("Error starting connection thread: " + e.getMessage());
		}
	}
	
	public synchronized void handleIncomingSignal(int is, String signal) {
		
	}
	
	public synchronized void removeConnection(int id) {
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
		}
	}
	
	
	
	
}
