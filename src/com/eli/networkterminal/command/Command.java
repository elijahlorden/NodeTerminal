package com.eli.networkterminal.command;

public interface Command {
	
	void doCommand(CommandResponse response, Object[] args);
	
	String getCommandName();
	
	String getCommandGroup();
	
	String getShortDesc();
	
	String getLongDesc();
	
	String getArgumentDesc();
	
	boolean checkParameters(Object[] args);
	
}
