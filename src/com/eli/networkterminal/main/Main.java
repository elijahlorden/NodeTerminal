package com.eli.networkterminal.main;

import com.eli.networkterminal.command.Commands;
import com.eli.networkterminal.objects.MainTerminalWindow;
import com.eli.networkterminal.tools.Formatting;

public class Main {
	
	
	public static MainTerminalWindow localTerminal;
	
	public static void main(String[] args) {
		
		Commands.registerCommands();
		
		localTerminal = new MainTerminalWindow("NodeTerminal " + Constants.version);
		localTerminal.println(Formatting.tag("color cyan") + "NodeTerminal " + Formatting.tag("/c") + "version " + Formatting.tag("color green") + Constants.version);
		
		
		
		
	}

}