package com.eli.networkterminal.command.commands;

import com.eli.networkterminal.command.Command;
import com.eli.networkterminal.command.CommandResponse;
import com.eli.networkterminal.main.Main;
import com.eli.networkterminal.tools.Formatting;
import com.eli.networkterminal.tools.Tag;

public class CommandClientInfo implements Command {

	@Override
	public void doCommand(CommandResponse response, Object[] args) {
		if (Main.client.isConnected()) {
			response.println("Connection state: " + Tag.tag("color green") + "Connected");
			response.println("Connected to: " + Formatting.formatHost(Main.client.hostname, Integer.toString(Main.client.port)));
			if (Main.client.getServerConnectionID() != null && Main.client.getServerConnectionID() != null) {
				response.println("Local ID: " + Formatting.formatUUID(Main.client.getServerConnectionID().toString()));
				response.println("Remote ID: " + Formatting.formatUUID(Main.client.getServerConnectionID().toString()));
			}
		} else {
			response.println("Connection state: " + Tag.tag("color red") + "Disconnected");
		}
		
	}

	@Override
	public String getCommandName() {
		return "info";
	}
	
	@Override
	public String getCommandGroup() {
		return "client";
	}

	@Override
	public String getShortDesc() {
		return "Display connection information";
	}

	@Override
	public String getLongDesc() {
		return "Display connection information";
	}

	@Override
	public String getArgumentDesc() {
		return "";
	}

	@Override
	public boolean checkParameters(Object[] args) {
		return true;
	}
	
}
