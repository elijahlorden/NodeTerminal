package com.eli.networkterminal.network;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;

import com.eli.networkterminal.tools.Util;

public class NetworkAddress {
	
	public final byte[] address;
	public final byte[] mask;
	
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

	public String addressToString() {
		return (address[0]&0xff) + "." + (address[1]&0xff) + "." + (address[2]&0xff) + "." + (address[3]&0xff);
	}
	
	public String maskToString() {
		return (mask[0]&0xff) + "." + (mask[1]&0xff) + "." + (mask[2]&0xff) + "." + (mask[3]&0xff);
	}
	
	public String octetsToString(byte[] octets) {
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
	
	
	
	
}