package com.eli.networkterminal.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientNode {
	
	private Socket clientNodeSocket;
	private ClientListenerThread listenerThread;
	private DataInputStream input;
	private DataOutputStream output;
	
	public final String hostname;
	public final int port;
	
	public ClientNode(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		System.out.println("Establishing connection with Server at " + hostname + ":" + port);
		try {
			clientNodeSocket = new Socket(hostname, port);
			System.out.println("Connected");
			start();
		} catch (UnknownHostException e ) {
			System.out.println("Unknown Host: " + hostname + ":" + port);
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e.getMessage());
		}
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
	
	public void handle(String signal) {
		
	}
	
	public void start() throws IOException {
		input = new DataInputStream(clientNodeSocket.getInputStream());
		output = new DataOutputStream(clientNodeSocket.getOutputStream());
		listenerThread = new ClientListenerThread(this, clientNodeSocket);
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
	
}
