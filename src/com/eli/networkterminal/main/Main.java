package com.eli.networkterminal.main;

import java.io.IOException;
import java.net.Socket;

import com.eli.networkterminal.command.Commands;
import com.eli.networkterminal.network.ServerNode;
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
		
		ServerNode server = new ServerNode(12345);
		
		try {
			Socket test = new Socket("127.0.0.1", 12345);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
