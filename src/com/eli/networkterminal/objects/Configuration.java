package com.eli.networkterminal.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.eli.networkterminal.main.Constants;
import com.eli.networkterminal.tools.Util;

public class Configuration {

	public final Properties properties;
	public final String fileName;
	
	public Configuration(String fileName) {
		this.properties = new Properties();
		this.fileName = Constants.configFolder + File.separator + fileName + ".properties";
	}
	
	public boolean load() {
		System.out.println("Loading configuration file " + fileName);
		try {
		File conff = new File(Constants.configFolder);
		conff.mkdirs();
		File f = new File(fileName);
		if (!f.exists()) {
			f.createNewFile();
		}
		FileInputStream is = new FileInputStream(f);
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
	
	public void set(String key, Object value) {
		String sValue = value.toString();
		properties.setProperty(key, sValue);
		save();
	}
	
	public Object get(String key, Object defaultValue) {
		String value = properties.getProperty(key, defaultValue.toString());
		return Util.transformString(value);
	}
	
	
	
	
	
}
