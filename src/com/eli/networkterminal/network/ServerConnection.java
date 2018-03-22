package com.eli.networkterminal.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnection extends Thread {
	
	private Socket socket;
	private int id;
	private ServerNode server;
	
	private DataInputStream streamIn;
	private DataOutputStream streamOut;
	
	public ServerConnection(Socket socket, ServerNode server) {
		this.socket = socket;
		this.server = server;
		this.id = socket.getPort();
	}
	
	public void send(String signal) {
		try {
			streamOut.writeUTF(signal);
			streamOut.flush();
		} catch (IOException e) {
			System.out.println(id + " threw error while sending: " + e.getMessage());
			server.removeConnection(id);
			this.interrupt();
		}
	}
	
	public int getID() {
		return id;
	}
	
	public void run() {
		System.out.println("ServerNode thread " + id + " started");
		while (true) {
			try {
				server.handleIncomingSignal(id, streamIn.readUTF());
			} catch (IOException e) {
				System.out.println(id + " threw error while reading: " + e.getMessage());
				server.removeConnection(id);
				this.interrupt();
			}
		}
	}
	
	public void open() throws IOException {
		streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		streamOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	}
	
	public void close() throws IOException {
		if (socket != null) socket.close();
		if (streamIn != null) streamIn.close();
		if (streamOut != null) streamOut.close();
	}
	
}
