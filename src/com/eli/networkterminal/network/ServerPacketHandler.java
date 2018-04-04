package com.eli.networkterminal.network;

import com.eli.networkterminal.objects.Packet;

public interface ServerPacketHandler {
	
	public void handle(Packet pkt, ServerNode node);
	
	public String getHeader();
	
}
