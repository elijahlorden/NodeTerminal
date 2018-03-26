package com.eli.networkterminal.network;

import com.eli.networkterminal.objects.Packet;

public interface PacketHandler {
	
	public void handle(Packet pkr);
	
	public String getHeader();
	
}
