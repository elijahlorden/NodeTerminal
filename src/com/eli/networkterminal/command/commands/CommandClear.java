package com.eli.networkterminal.command.commands;

import com.eli.networkterminal.command.Command;
import com.eli.networkterminal.command.CommandResponse;

public class CommandClear implements Command {

	@Override
	public void doCommand(CommandResponse response, String[] args) {
		response.clear();
	}

	@Override
	public String getCommandName() {
		return "clear";
	}
	
	@Override
	public String getCommandGroup() {
		return "terminal";
	}

	@Override
	public String getShortDesc() {
		return "Clears the terminal log.";
	}

	@Override
	public String getLongDesc() {
		return "Clears the terminal log.";
	}

	@Override
	public String[] getArgumentList() {
		return null;
	}
	
}
