package com.eli.networkterminal.objects;

import com.google.gson.Gson;

public class Packet {
	
	private String header;
	private String senderName;
	private String receiverName;
	private String[][] data;
	
	public Packet(String header, String senderName, String receiverName, String[][] data) {
		this.header = header;
		this.senderName = senderName;
		this.receiverName = receiverName;
		this.data = data;
	}

	public Packet(String header, String senderName, String receiverName) {
		this.header = header;
		this.senderName = senderName;
		this.receiverName = receiverName;
	}

	public Packet(String header, String senderName) {
		this.header = header;
		this.senderName = senderName;
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

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
	}
	
	
}
