package com.eli.networkterminal.command;

public interface Command {
	
	void doCommand(CommandResponse response, String[] args);
	
	String getCommandName();
	
	String getCommandGroup();
	
	String getShortDesc();
	
	String getLongDesc();
	
	String[] getArgumentList();
	
}
