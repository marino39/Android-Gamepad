package marino39.agamepad.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import net.n3.nanoxml.IXMLParser;
import net.n3.nanoxml.IXMLReader;
import net.n3.nanoxml.StdXMLReader;
import net.n3.nanoxml.XMLElement;
import net.n3.nanoxml.XMLException;
import net.n3.nanoxml.XMLParserFactory;

import android.util.Log;

public class Configuration {
	
	private static final String LOG_TAG = "[Configuration]";
	private static final String CONFIG_DIR = "AndroidGamepad/";
	private static final String CLASS_PATH_DEFAULT_CONFIG_DIR = "/marino39/agamepad/conf/default.xml";
	
	/** 
	 * Private default constructor.
	 */
	private Configuration() {
		
	}
	
	/**
	 * It returns default configuration supplied with application.
	 * @return
	 */
	public static Configuration getDefaultConfiguration() {	
		Configuration defaultConf = new Configuration();
		InputStream in = Configuration.class.getResourceAsStream(CLASS_PATH_DEFAULT_CONFIG_DIR);
		if (in == null) {
			Log.e(LOG_TAG, "Could not get default.xml from classpath.");
			return null;
		}
				
		try {
			int len = in.available();
			byte[] data = new byte[len];
			in.read(data, 0, len);
			String xml = new String(data);
			
			if (!defaultConf.loadXMLFile(xml)) {
				Log.e(LOG_TAG, "Configuration::loadXMLFile() returned false.");
				return null;
			}
		} catch (IOException e) {
			Log.e(LOG_TAG, "IOException during loading default.xml");
			e.printStackTrace();
		}

		return defaultConf;
	}
	
	/**
	 * It returns configuration saved by user.
	 * @return
	 */
	public static Configuration getSavedConfiguration(String configurationName) {
		return null;
	}
	
	/**
	 * Get's last set configuration.
	 * @return
	 */
	public static Configuration getLastConfiguration() {
		return null;
	}
	
	/**
	 * Loads settings form XML File. 
	 * @param is
	 * @param c
	 */
	private boolean loadXMLFile(String xml) {
		try {
			IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
			IXMLReader reader = StdXMLReader.stringReader(xml);
			parser.setReader(reader);
			
			XMLElement xmlConfiguration = (XMLElement) parser.parse();
			if (xmlConfiguration.getName().equalsIgnoreCase("Configuration")) {
				Vector<XMLElement> children = xmlConfiguration.getChildren();
				for (int i = 0; i < children.size(); i++) {
					XMLElement child = children.get(i);
					
					if (child.getName().equalsIgnoreCase("Button")) {
						
					}
				}
			} else {
				Log.e(LOG_TAG, "Wrong main XML component it should be " 
						+ "Configuration but It's "
						+ xmlConfiguration.getName());
				return false;
			}
			return true;
		} catch (ClassNotFoundException e) {
			Log.e(LOG_TAG, "ClassNotFoundException throwed during parsing configuration.");
			e.printStackTrace();
		} catch (InstantiationException e) {
			Log.e(LOG_TAG, "InstantiationException throwed during parsing configuration.");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Log.e(LOG_TAG, "InstantiationException throwed during parsing configuration.");
			e.printStackTrace();
		} catch (XMLException e) {
			Log.e(LOG_TAG, "XMLException throwed during parsing configuration.");
			e.printStackTrace();
		}
		
		return false;
	}
	
}
