package com.eli.networkterminal.network.serverpackethandlers;

import com.eli.networkterminal.network.PacketHandler;
import com.eli.networkterminal.objects.Packet;

public class PacketHandlerNodeInfo implements PacketHandler {

	@Override
	public void handle(Packet pkt) {
		
	}

	@Override
	public String getHeader() {
		return "NodeInfo";
	}
	
}
