package com.eli.networkterminal.command;

import java.util.ArrayList;

import com.eli.networkterminal.command.commands.*;
import com.eli.networkterminal.objects.MainTerminalWindow;
import com.eli.networkterminal.tools.Util;

public class Commands {
	
	static ArrayList<Command> registeredCommands;
	
	public static void processLocalCommand(String input, MainTerminalWindow localTerminal){
		CommandResponse response = new CommandResponse(localTerminal);
		String[] rawParams = input.split(" ");
		if (rawParams.length < 1) {
			response.end("Error, invalid command name");
			return;
		}
		String[] commandName = rawParams[0].split("\\.");
		String[] params = new String[rawParams.length - 1];
		for (int i=0;i<params.length;i++) {
			params[i] = rawParams[i+1];
		}
		String[] parsedCommandName = parseCommandName(commandName); // [0] = groupName, [1] = commandName
		
		for (Command c : registeredCommands) {
			if (c.getCommandGroup().equals(parsedCommandName[0]) && c.getCommandName().equals(parsedCommandName[1])) {
				executeCommand(c, Util.parseParamaters(params), response);
				return;
			}
		}
		response.end("Error, invalid command name.");
	}
	
	public static void executeCommand(Command command, Object[] args, CommandResponse response) { // rework required when network commands are implemented
		if (command.checkParameters(args) == true) {
			command.doCommand(response, args);
			response.end();
		} else {
			response.end("Error, invalid paramaters.");
		}
	}
	
	public static void registerCommand(Command command) {
		registeredCommands.add(command);
	}
	
	public static String[] parseCommandName(String[] commandName) {
		String commandGroup = "";
		for (int i=0;i<commandName.length-1;i++) {
			commandGroup+= commandName[i] + ".";
		}
		if (commandGroup.length() > 0) {
			commandGroup = commandGroup.substring(0, commandGroup.length()-1);
		}
		return new String[] {commandGroup, commandName[commandName.length-1]};
	}
	
	
	
	
	
	
	
	
	
	public static void registerCommands() {
		registeredCommands = new ArrayList<Command>();
		registerCommand(new CommandClear());
		registerCommand(new CommandTest());
		
		
	}
	
	
	
	
	
	
}
