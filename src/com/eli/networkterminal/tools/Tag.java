package com.eli.networkterminal.tools;

import com.eli.networkterminal.main.Constants;

public class Tag {
	
	public static String tag(String s1) {
		return Constants.openTag + s1 + Constants.closeTag;
	}
	
	public static String tag(String s1, String s2) {
		return Constants.openTag + s1 + " " + s2 + Constants.closeTag;
	}
	
	public static String multiTag(String s1, String s2) {
		return Constants.openTag + s1 + Constants.closeTag + Constants.openTag + s2 + Constants.closeTag;
	}
	
}
