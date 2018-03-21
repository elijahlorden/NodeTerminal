package com.eli.networkterminal.main;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;

public class Constants {

	public static final String version = "0.0.1";
	
	public static final String openTag = "<";
	public static final String closeTag = ">";
	
	public static final String configFolder = System.getProperty("user.home") + File.separator + "Documents" + File.separator +".NodeTerminal";
	
	public static final HashMap<String, Color> colorMap = new HashMap<String, Color>(){{
		put("BLACK",            Color.BLACK);
		put("BLUE",             new Color(51, 153, 255));
		put("CYAN",             Color.CYAN);
		put("DARK_GRAY",        Color.DARK_GRAY);
		put("GRAY",             Color.GRAY);
		put("GREEN",            Color.GREEN);
		put("LIGHT_GRAY",       Color.LIGHT_GRAY);
		put("MAGENTA",          Color.MAGENTA);
		put("ORANGE",           Color.ORANGE);
		put("PINK",             Color.PINK);
		put("RED",              Color.RED);
		put("WHITE",            Color.WHITE);
		put("YELLOW",            Color.YELLOW);
		put("PURPLE", 			new Color(192, 0, 255));
		put("DARK_BLUE",  		new Color(0, 0, 128));
	}};
	
	
	
	
	
	
	
	
	
	
}
