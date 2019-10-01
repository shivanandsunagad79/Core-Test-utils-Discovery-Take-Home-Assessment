package com.globalLogic.discovery;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Shivanand Sunagad
 * @throws   
 * @category
 * 
 */

public class PropertyFileReader {

	private PropertyFileReader() {}

	private static final String OVERRIDES_PROPERTY = "test.config.override";

	private static Properties properties = new Properties();

	static {
		try {
			System.out.println("Starting to load properties: "	+ System.getProperty("user.dir"));

			System.out.println("TestConfig loads properties");
			System.out.println(System.getProperty("user.dir"));
			// properties.load(new FileInputStream("default.properties"));
			properties.load(new FileInputStream(System.getProperty("user.dir") + "/config/config.properties"));


			// if test.config.override= is specified as JVM arg override
			// properties set by default.properties
			Properties systemProperties = System.getProperties();
			String overrideFileName = (String) systemProperties.get(OVERRIDES_PROPERTY);

			if (systemProperties.containsKey(OVERRIDES_PROPERTY)) {
				properties.load(new FileInputStream(System.getProperty("user.dir") + "/" + overrideFileName));
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}

	public static boolean flagSet(String key) {
		System.out.println(properties.get(key));
		return properties.get(key).equals("true");
	}
}