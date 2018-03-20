package com.eli.networkterminal.command.commands;

import com.eli.networkterminal.command.Command;
import com.eli.networkterminal.command.CommandResponse;
import com.eli.networkterminal.tools.Formatting;
import com.eli.networkterminal.tools.Util;

public class CommandTest implements Command {

	@Override
	public void doCommand(CommandResponse response, Object[] args) {
		response.println(Formatting.tag("color yellow") + "Test!");
	}

	@Override
	public String getCommandName() {
		return "test";
	}
	
	@Override
	public String getCommandGroup() {
		return "";
	}

	@Override
	public String getShortDesc() {
		return "Test";
	}

	@Override
	public String getLongDesc() {
		return "Clears the terminal log.";
	}

	@Override
	public String getArgumentDesc() {
		return "";
	}

	@Override
	public boolean checkParameters(Object[] args) {
		if (args.length < 2) return false;
		if (!Util.getType(args[0]).equals("Integer")) return false;
		return true;
	}
	
}
