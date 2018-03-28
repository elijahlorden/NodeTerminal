package com.eli.networkterminal.main;

import java.util.UUID;

import com.eli.networkterminal.command.Commands;
import com.eli.networkterminal.network.ClientNode;
import com.eli.networkterminal.network.ServerNode;
import com.eli.networkterminal.objects.Configuration;
import com.eli.networkterminal.objects.MainTerminalWindow;
import com.eli.networkterminal.objects.Packet;
import com.eli.networkterminal.tools.Formatting;

public class Main {
	
	
	public static MainTerminalWindow localTerminal;
	public static ServerNode server;
	public static ClientNode client;
	
	public static void main(String[] args) {
		
		Configuration mainConfig = Configuration.getConfig("config");
		mainConfig.load();
		
		client = new ClientNode();
		
		Commands.registerCommands();
		
		localTerminal = new MainTerminalWindow("NodeTerminal " + Constants.version);
		localTerminal.println(Formatting.tag("color cyan") + "NodeTerminal " + Formatting.tag("/c") + "version " + Formatting.tag("color green") + Constants.version);
		
		server = new ServerNode(12345);
		
		
	}

}
