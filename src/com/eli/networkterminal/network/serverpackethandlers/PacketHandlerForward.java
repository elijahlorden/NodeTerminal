package com.eli.networkterminal.network.serverpackethandlers;

import com.eli.networkterminal.network.ServerNode;
import com.eli.networkterminal.network.ServerPacketHandler;
import com.eli.networkterminal.objects.Packet;

public class PacketHandlerForward implements ServerPacketHandler {

	@Override
	public void handle(Packet pkt, ServerNode server) {
		
	}

	@Override
	public String getHeader() {
		return "";
	}
	
}
