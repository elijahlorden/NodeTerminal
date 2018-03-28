package com.eli.networkterminal.command.commands;

import com.eli.networkterminal.command.Command;
import com.eli.networkterminal.command.CommandResponse;
import com.eli.networkterminal.main.Main;
import com.eli.networkterminal.tools.Formatting;
import com.eli.networkterminal.tools.Util;

public class CommandClientConnect implements Command {

	@Override
	public void doCommand(CommandResponse response, Object[] args) {
		String hostname = (String) args[0];
		int port = (int) args[1];
		response.println("Attempting connection with " + hostname + ":" + port);
		int error = Main.client.start(hostname, port);
		switch(error) {
		case 0:
			response.println(Formatting.tag("color green") + "Connection established");
			break;
		case 1:
			response.println(Formatting.tag("color red") + "Connection failed, unknown host");
			break;
		case 2:
			response.println(Formatting.tag("color red") + "Connection failed, unexpected error");
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
		return "Test";
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
