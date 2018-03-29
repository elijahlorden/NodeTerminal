package com.eli.networkterminal.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Properties;

import com.eli.networkterminal.main.Constants;
import com.eli.networkterminal.tools.Util;

public class Configuration {

	public final Properties properties;
	public final String fileName;
	public final String name;
	
	public static final HashMap<String, Configuration> configList = new HashMap<String, Configuration>();
	
	public Configuration(String name) {
		this.properties = new Properties();
		this.name = name;
		this.fileName = Constants.configFolder + File.separator + name + ".properties";
	}
	
	public static synchronized Configuration getConfig(String name) {
		if (configList.containsKey(name)) {
			return configList.get(name);
		} else {
			Configuration nc = new Configuration(name);
			nc.load();
			configList.put(name, nc);
			return nc;
		}
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
	
	public synchronized void set(String key, Object value) {
		String sValue = value.toString();
		properties.setProperty(key, sValue);
		save();
	}
	
	public synchronized String get(String key, String defaultValue) {
		String value = properties.getProperty(key, defaultValue);
		return value;
	}
	
	public synchronized String get(String key) { // If using this, make sure there is a default value specified in Constants.configDefaults.  Otherwise, the default will be set to an emptystring.
		String defaultValue = "";
		if (Constants.configDefaults.containsKey(name + "." + key)) {
			defaultValue = Constants.configDefaults.get(name + "." + key);
		}
		String value = properties.getProperty(key, defaultValue);
		return value;
	}
	
	
	
	
	
}
