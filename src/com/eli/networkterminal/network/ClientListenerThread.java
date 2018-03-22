package com.eli.networkterminal.network;

import java.io.DataInputStream;
import java.net.Socket;

public class ClientListenerThread extends Thread {
	
	private ClientNode clientNode;
	private Socket clientSocket;
	private DataInputStream input;
	
	public ClientListenerThread(ClientNode clientNode, Socket clientSocket) {
		this.clientNode = clientNode;
		this.clientSocket = clientSocket;
		open();
		start();
	}
	
	public void open() {
		try {
			input = new DataInputStream(clientSocket.getInputStream());
		} catch (Exception e) {
			System.out.println("Error getting InputStream: " + e.getMessage());
		}
	}
	
	public void close() {
		try {
			if (input != null) {
				input.close();
			}
		} catch (Exception e) {
			System.out.println("Error closing InputStream: " + e.getMessage());
		}
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				clientNode.handle(input.readUTF());
			} catch (Exception e) {
				System.out.println("Listener Error: " + e.getMessage());
			}
		}
	}
	
}
