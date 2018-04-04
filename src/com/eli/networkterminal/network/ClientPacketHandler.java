package com.eli.networkterminal.network;

import com.eli.networkterminal.objects.Packet;

public interface ClientPacketHandler {
	
	public void handle(Packet pkt, ClientNode node);
	
	public String getHeader();
	
}
