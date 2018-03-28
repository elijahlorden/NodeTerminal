package com.eli.networkterminal.clientpackethandlers;

import com.eli.networkterminal.network.PacketHandler;
import com.eli.networkterminal.objects.Packet;

public class PacketHandlerConnectionInfo implements PacketHandler {

	@Override
	public void handle(Packet pkt) {
		
	}

	@Override
	public String getHeader() {
		return "ConnectionInfo";
	}
	
}
