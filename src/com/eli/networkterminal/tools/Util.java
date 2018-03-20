package com.eli.networkterminal.tools;

public class Util {

	
	//Get the simplified className for an object.
	@SuppressWarnings("rawtypes")
	public static String getType(Object o) {
		if (o == null) return "null";
		Class c = o.getClass();
		return c.getSimpleName();
	}
	
	//Parse string parameters into correct types.  Numbers will be parsed into either floats or integers.
	public static Object[] parseParamaters(String[] params) {
		Object[] returnArray = new Object[params.length];
		for (int i=0;i<params.length;i++) {
			Object n = tryParseNumber(params[i]);
			if (n != null) {
				returnArray[i] = n;
			} else {
				returnArray[i] = params[i];
			}
		}
		return returnArray;
	}
	
	//Try to parse a string into an integer. If that does not work, try a float.  If the string can be parsed into neither, return null.
	public static Object tryParseNumber(String s) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {}
		try {
			return Float.parseFloat(s);
		} catch (Exception e) {}
		return null;
	}
	
	public static void printArray(Object[] array) {
		for (Object o : array) {
			System.out.println(o);
		}
	}
	
	
	
}
