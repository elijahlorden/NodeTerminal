package com.eli.networkterminal.network;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;

import com.eli.networkterminal.tools.Util;

public class NetworkAddress {
	
	public byte[] address;
	public byte[] mask;
	
	public NetworkAddress(byte[] address, byte[] mask) {
		this.address = address;
		this.mask = mask;
	}
	
	public static NetworkAddress getLocalAddress() {
		try {
			InetAddress address = NetUtils.getLocalAddress();
			NetworkInterface netInt = NetworkInterface.getByInetAddress(address);
			String v4Address = "0.0.0.0/0";
			for (InterfaceAddress addr : netInt.getInterfaceAddresses()) {
				String strAddr = (addr.getAddress() + "/" + addr.getNetworkPrefixLength()).substring(1);
			    if (NetUtils.isCDIRIPV4Address(strAddr)) v4Address = strAddr;
			}
			String[] sepAddr = v4Address.split("/");
			Object[] octets = Util.parseParamaters(sepAddr[0].split("\\."));
			byte[] addressBytes = {
				(byte) ((int)octets[0] & 0xff),
				(byte) ((int)octets[1] & 0xff),
				(byte) ((int)octets[2] & 0xff),
				(byte) ((int)octets[3] & 0xff)
			};
			int prefix = (int) Util.tryParseNumber(sepAddr[1]);
			int mask = 0xffffffff << (32 - prefix);
			byte[] maskBytes = {
				(byte)(mask >>> 24 & 0xff),
				(byte)(mask >>> 16 & 0xff),
				(byte)(mask >>> 8 & 0xff),
				(byte)(mask & 0xff),
			};
			return new NetworkAddress(addressBytes, maskBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public NetworkAddress clone() {
		return new NetworkAddress(address, mask);
	}

	public String addressToString() {
		return (address[0]&0xff) + "." + (address[1]&0xff) + "." + (address[2]&0xff) + "." + (address[3]&0xff);
	}
	
	public String maskToString() {
		return (mask[0]&0xff) + "." + (mask[1]&0xff) + "." + (mask[2]&0xff) + "." + (mask[3]&0xff);
	}
	
	public static String octetsToString(byte[] octets) {
		return (octets[0]&0xff) + "." + (octets[1]&0xff) + "." + (octets[2]&0xff) + "." + (octets[3]&0xff);
	}
	
	public byte[] getHostPortion() {
		return new byte[] {
			(byte) ((~mask[0]) & address[0]),
			(byte) ((~mask[1]) & address[1]),
			(byte) ((~mask[2]) & address[2]),
			(byte) ((~mask[3]) & address[3])
		};
	}
	
	public byte[] getNetworkPortion() {
		return new byte[] {
			(byte) ((mask[0]) & address[0]),
			(byte) ((mask[1]) & address[1]),
			(byte) ((mask[2]) & address[2]),
			(byte) ((mask[3]) & address[3])
		};
	}
	
	public static byte[] invertOctets(byte[] in) {
		return new byte[] {
			(byte) ~in[0],	
			(byte) ~in[1],	
			(byte) ~in[2],	
			(byte) ~in[3],	
		};
	}
	
	public static byte[] andOctets(byte[] b1, byte[] b2) {
		return new byte[] {
				(byte) (b1[0] & b2[0]),	
				(byte) (b1[1] & b2[1]),	
				(byte) (b1[2] & b2[2]),	
				(byte) (b1[3] & b2[3]),	
			};
	}
	
	public static byte[] orOctets(byte[] b1, byte[] b2) {
		return new byte[] {
				(byte) (b1[0] | b2[0]),	
				(byte) (b1[1] | b2[1]),	
				(byte) (b1[2] | b2[2]),	
				(byte) (b1[3] | b2[3]),	
			};
	}
	
	public static byte[] intToOctets(int n) {
		return ByteBuffer.allocate(4).putInt(n).array();
	}
	
	public static int octetsToInt(byte[] octets) {
		return ByteBuffer.wrap(octets).getInt();
	}
	
	public void incrementAddress() {
		int addressInteger = octetsToInt(address);
		addressInteger++;
		address = intToOctets(addressInteger);
	}

	public byte[] getAddress() {
		return address;
	}

	public void setAddress(byte[] address) {
		this.address = address;
	}

	public byte[] getMask() {
		return mask;
	}

	public void setMask(byte[] mask) {
		this.mask = mask;
	}
	
	
	
	
	
	
	
	
	
}