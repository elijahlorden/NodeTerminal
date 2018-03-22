package com.eli.networkterminal.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtils {

	public static InetAddress getLocalAddress() {
		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean isCDIRIPV4Address(String address) {
		int dotCount = 0;
		boolean slash = false;
		while (address.length() > 0) {
			String token = address.substring(0, 1);
			address = address.substring(1);
			if (token.equals(".")) {
				dotCount+= 1;
			} else if(token.equals("/")) {
				slash = true;
			}
		}
		return (dotCount == 3 && slash);
	}
	
	
	

	
}
