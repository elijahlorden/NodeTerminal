package com.eli.networkterminal.main;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import com.eli.networkterminal.command.Commands;
import com.eli.networkterminal.network.NetUtils;
import com.eli.networkterminal.network.NetworkAddress;
import com.eli.networkterminal.objects.Configuration;
import com.eli.networkterminal.objects.MainTerminalWindow;
import com.eli.networkterminal.tools.Formatting;

public class Main {
	
	
	public static MainTerminalWindow localTerminal;
	
	public static void main(String[] args) {
		
		Configuration mainConfig = new Configuration("config");
		mainConfig.load();
		
		Commands.registerCommands();
		
		localTerminal = new MainTerminalWindow("NodeTerminal " + Constants.version);
		localTerminal.println(Formatting.tag("color cyan") + "NodeTerminal " + Formatting.tag("/c") + "version " + Formatting.tag("color green") + Constants.version);
		
		
		
		NetworkAddress localAddress = NetworkAddress.getLocalAddress();
		System.out.println(localAddress.addressToString());
		System.out.println(localAddress.maskToString());
		System.out.println(localAddress.octetsToString(localAddress.getHostPortion()));
		System.out.println(localAddress.octetsToString(localAddress.getNetworkPortion()));
		System.out.println(localAddress.octetsToInt(localAddress.getNetworkPortion()));
	}

}
