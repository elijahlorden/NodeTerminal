package com.eli.networkterminal.clientpackethandlers;

import java.util.UUID;

import com.eli.networkterminal.main.Constants;
import com.eli.networkterminal.main.Main;
import com.eli.networkterminal.network.ClientNode;
import com.eli.networkterminal.network.ClientPacketHandler;
import com.eli.networkterminal.network.ServerPacketHandler;
import com.eli.networkterminal.objects.Configuration;
import com.eli.networkterminal.objects.Packet;

public class PacketHandlerConnectionInfo implements ClientPacketHandler {

	@Override
	public void handle(Packet pkt, ClientNode node) {
		String[][] data = pkt.getData();
		if (data != null && data.length > 0 && data[0].length >= 2) {
			try {
				UUID localID = UUID.fromString(data[0][0]);
				UUID remoteID = UUID.fromString(data[0][1]);
				Main.client.setServerConnectionID(localID);
				Main.client.setServerID(remoteID);
				System.out.println("Received connection information:\nLocal ID: " + data[0][0] +"\nRemote ID: " + data[0][1]);
				System.out.println("Sending node info to server");
				Packet nodeInfoPkt = new Packet("NodeInfo");
				Configuration config = Configuration.getConfig(Constants.mainConfigName);
				String nodeName = config.get("nodeName");
				nodeInfoPkt.setData(new String[][]{{nodeName}});
			} catch (Exception e) {}
		}
	}

	@Override
	public String getHeader() {
		return "ConnectionInfo";
	}
	
}
