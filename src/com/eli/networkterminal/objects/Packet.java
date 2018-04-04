package com.eli.networkterminal.objects;

import java.util.UUID;

import com.google.gson.Gson;

public class Packet {
	
	private String header;
	private UUID senderID; // Most of the time this will not matter, but some scenarios will require this to be defined on the client.
	private UUID receiverID;
	private String[][] data;
	
	public Packet(String header, UUID senderID, UUID receiverID, String[][] data) {
		this.header = header;
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.data = data;
	}

	public Packet(String header, UUID senderID, UUID receiverID) {
		this.header = header;
		this.senderID = senderID;
		this.receiverID = receiverID;
	}

	public Packet(String header, UUID senderID) {
		this.header = header;
		this.senderID = senderID;
	}

	public Packet(String header) {
		this.header = header;
	}
	
	public Packet() {}
	
	public String getJSON() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
	
	public static Packet fromJSON(String json) {
		Gson gson = new Gson();
		Packet pkt = null;
		try {
			pkt = gson.fromJson(json, Packet.class);
		} catch (Exception e) {}
		return pkt;
	}
	
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public UUID getSenderID() {
		return senderID;
	}

	public void setSenderID(UUID senderID) {
		this.senderID = senderID;
	}

	public UUID getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(UUID receiverID) {
		this.receiverID = receiverID;
	}

	public String[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
	}
	
	
}
