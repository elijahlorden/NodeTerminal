package com.eli.networkterminal.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.eli.networkterminal.objects.Packet;

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
			System.out.println("Sending local Node info");
			Packet infopkt = new Packet("NodeInfo");
			send(infopkt);
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
			System.out.println("Received packet with header" + pkt.getHeader());
		} else {
			System.out.println("Invalid packet data");
		}
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
