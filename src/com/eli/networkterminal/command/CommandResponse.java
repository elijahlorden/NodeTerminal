package com.eli.networkterminal.command;

import java.awt.Color;

import com.eli.networkterminal.objects.MainTerminalWindow;
import com.eli.networkterminal.tools.Formatting;

public class CommandResponse {

	public MainTerminalWindow localWindow;
	private boolean ended = false;
	
	public CommandResponse(MainTerminalWindow localWindow) {
		this.localWindow = localWindow;
	}
	
	public CommandResponse() {}
	
	public void print(String s, boolean stackTrace) {
		if (localWindow != null) {
			localWindow.print(s, stackTrace);
		}
	}
	
	public void print(String s) {
		print(s, false);
	}
	
	public void println(String s, boolean stackTrace) {
		print(s + "\n", stackTrace);
	}
	
	public void println(String s) {
		print(s + "\n", false);
	}
	
	public void scrollTop() {
		if (localWindow != null) {
			localWindow.scrollTop();
		}
	}
	
	public void scrollBottom() {
		if (localWindow != null) {
			localWindow.scrollBottom();
		}
	}
	
	public void clear() {
		if (localWindow != null) {
			localWindow.clear();
		}
	}
	
	public void end() { // will be used in network commands
		if (!ended) {
			ended = true;
		}
	}
	
	public void end(String error) {
		if (!ended) {
			println(Formatting.tag("i") + Formatting.tag("color red") + error);
		}
	}
	
	
	
}
