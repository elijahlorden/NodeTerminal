package com.eli.networkterminal.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Configuration {

	public final Properties properties;
	public final String fileName;
	
	public Configuration(String fileName) {
		this.properties = new Properties();
		this.fileName = System.getProperty("user.home") + File.separator +".NodeTerminal" + File.separator + fileName;
	}
	
	public boolean load() {
		try {
		InputStream is = new FileInputStream(fileName);
		properties.load(is);
		is.close();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean save() {
		try {
			OutputStream os = new FileOutputStream(fileName);
			properties.store(os, "");
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
}
