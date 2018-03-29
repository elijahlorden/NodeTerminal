package com.eli.networkterminal.clientpackethandlers;

import java.util.UUID;

import com.eli.networkterminal.main.Main;
import com.eli.networkterminal.network.PacketHandler;
import com.eli.networkterminal.objects.Packet;

public class PacketHandlerConnectionInfo implements PacketHandler {

	@Override
	public void handle(Packet pkt) {
		String[][] data = pkt.getData();
		if (data != null && data.length > 0 && data[0].length >= 2) {
			try {
				UUID localID = UUID.fromString(data[0][0]);
				UUID remoteID = UUID.fromString(data[0][1]);
				Main.client.setServerConnectionID(localID);
				Main.client.setServerID(remoteID);
				System.out.println("Received connection information:\nLocal ID: " + data[0][0] +"\nRemote ID: " + data[0][1]);
			} catch (Exception e) {}
		}
	}

	@Override
	public String getHeader() {
		return "ConnectionInfo";
	}
	
}
