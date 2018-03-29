package com.eli.networkterminal.command.commands;

import com.eli.networkterminal.command.Command;
import com.eli.networkterminal.command.CommandResponse;
import com.eli.networkterminal.main.Main;
import com.eli.networkterminal.tools.Formatting;
import com.eli.networkterminal.tools.Tag;
import com.eli.networkterminal.tools.Util;

public class CommandClientConnect implements Command {

	@Override
	public void doCommand(CommandResponse response, Object[] args) {
		if (Main.client.isConnected()) {
			response.end("Error, ClientNode is already connected to "+ Formatting.formatHost(Main.client.hostname, Integer.toString(Main.client.port)));
			return;
		}
		String hostname = (String) args[0];
		int port = (int) args[1];
		response.println("Attempting connection with " + Formatting.formatHost(hostname, Integer.toString(port)));
		int error = Main.client.start(hostname, port);
		switch(error) {
		case 0:
			response.println(Tag.tag("color green") + "Connection established");
			break;
		case 1:
			response.end("Connection failed, unknown host");
			break;
		case 2:
			response.end("Connection failed, unexpected error");
			break;
		}
	}

	@Override
	public String getCommandName() {
		return "connect";
	}
	
	@Override
	public String getCommandGroup() {
		return "client";
	}

	@Override
	public String getShortDesc() {
		return "Attempt to connect to a ServerNode";
	}

	@Override
	public String getLongDesc() {
		return "Attempt to connect to a ServerNode";
	}

	@Override
	public String getArgumentDesc() {
		return "(String) HostName, (Integer) port";
	}

	@Override
	public boolean checkParameters(Object[] args) {
		if (args.length < 2) return false;
		if (!Util.getType(args[0]).equals("String") || !Util.getType(args[1]).equals("Integer")) return false;
		return true;
	}
	
}
