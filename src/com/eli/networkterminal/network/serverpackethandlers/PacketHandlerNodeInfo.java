package com.eli.networkterminal.network.serverpackethandlers;

import com.eli.networkterminal.network.ServerNode;
import com.eli.networkterminal.network.ServerPacketHandler;
import com.eli.networkterminal.objects.Packet;

public class PacketHandlerNodeInfo implements ServerPacketHandler {

	@Override
	public void handle(Packet pkt, ServerNode node) {
		String[][] data = pkt.getData();
		if (data.length > 0 && data[0].length > 0) {
			String nodeName = data[0][0];
		}
	}

	@Override
	public String getHeader() {
		return "NodeInfo";
	}
	
}
