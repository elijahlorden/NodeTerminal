package com.eli.networkterminal.command.commands;

import com.eli.networkterminal.command.Command;
import com.eli.networkterminal.command.CommandResponse;
import com.eli.networkterminal.main.Main;
import com.eli.networkterminal.tools.Formatting;
import com.eli.networkterminal.tools.Tag;

public class CommandClientDisconnect implements Command {

	@Override
	public void doCommand(CommandResponse response, Object[] args) {
		if (Main.client.isConnected()) {
			response.println("Disconnecting from " + Formatting.formatHost(Main.client.hostname, Integer.toString(Main.client.port)));
			Main.client.stop();
		} else {
			response.end("Error, ClientNode is not connected.");
		}
	}

	@Override
	public String getCommandName() {
		return "disconnect";
	}
	
	@Override
	public String getCommandGroup() {
		return "client";
	}

	@Override
	public String getShortDesc() {
		return "Disconnect from ServerNode";
	}

	@Override
	public String getLongDesc() {
		return "Disconnect from currently connected ServerNode";
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
