package com.eli.networkterminal.network;

import com.eli.networkterminal.objects.Packet;

public interface PacketHandler {
	
	public void handle(Packet pkt);
	
	public String getHeader();
	
}
