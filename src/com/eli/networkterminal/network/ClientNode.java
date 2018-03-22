package com.eli.networkterminal.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientNode implements Runnable {
	
	private Socket clientNodeSocket;
	private Thread clientNodeThread;
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
	
	@Override
	public void run() {
		
	}
	
	public void send() {
		
	}
	
	public void handle() {
		
	}
	
	public void start() {
		
	}
	
	public void stop() {
		
	}
	
}
