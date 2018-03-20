package com.eli.networkterminal.tools;

import com.eli.networkterminal.main.Constants;

public class Formatting {
	
	public String formatBlocks(String in, String blockSymbolColor, String blockTextColor) {
		return null;
	}
	
	public static String removeTags(String s) {
		String r = "";
		int currPos = 0;
		while(s.length() > 0) {
			String token = s.substring(0, 1);
			s = s.substring(1);
			currPos++;
			if (token.equals("\\") && s.substring(0, 1).equals(Constants.openTag)) { //allow escaping of the open tag symbol so it can be used in strings.
				s = s.substring(1);
				try {
					r+=Constants.openTag;
				} catch (Exception e) {}
				continue;
			} else if (token.equals(Constants.openTag)) { //opentag opens the text-attrib tag
				while (!token.endsWith(Constants.closeTag) && s.length() > 0){ //get the rest of the tag.  If the tag is not closed, the rest of the string will not be printed.
					token+= s.substring(0, 1);
					s = s.substring(1);
					currPos++;
				}
			} else {
				r+=token;
			}
		}
		return r;
	}
	
	public static String formatCommandColors(String s) {
		String[] words = s.split(" ");
		Object[] typeWords = Util.parseParamaters(words);
		String argsf = "";
		for (int i=1; i<typeWords.length; i++) {
			argsf+=tag("color", typeColor(Util.getType(typeWords[i]))) + typeWords[i] + " ";
		}
		String namef = "";
		String[] nameWords = words[0].split("\\.");
		for (int i=0; i<nameWords.length-1; i++) {
			namef+=tag("color cyan") + nameWords[i] + tag("color yellow") + ".";
		}
		namef+= tag("color green") + nameWords[nameWords.length-1];
		return namef + " " + argsf;
	}
	
	public static String tag(String s1) {
		return Constants.openTag + s1 + Constants.closeTag;
	}
	
	public static String tag(String s1, String s2) {
		return Constants.openTag + s1 + " " + s2 + Constants.closeTag;
	}
	
	public static String typeColor(String type) {
		switch(type){
		case "Float":
		case "Integer":
			return "BLUE";
		case "String":
			return "WHITE";
		default:
			return "CYAN";
		}
	}
	
	
}
