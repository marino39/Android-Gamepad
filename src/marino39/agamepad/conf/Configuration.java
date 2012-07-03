package marino39.agamepad.conf;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;

import marino39.agamepad.KeyEvent;
import marino39.agamepad.R;
import marino39.agamepad.theme.Theme;
import marino39.ui.components.UIComponent;
import marino39.ui.main.UIMain;
import marino39.utils.ReflectionUtils;
import net.n3.nanoxml.IXMLParser;
import net.n3.nanoxml.IXMLReader;
import net.n3.nanoxml.StdXMLReader;
import net.n3.nanoxml.XMLElement;
import net.n3.nanoxml.XMLException;
import net.n3.nanoxml.XMLParserFactory;

import android.content.res.Resources;
import android.location.Address;
import android.util.Log;

public class Configuration {
	
	private static final String LOG_TAG = "[Configuration]";
	private static final String CONFIG_DIR = "AndroidGamepad/";
	private static final String CLASS_PATH_DEFAULT_CONFIG_DIR = "/marino39/agamepad/conf/default.xml";
	private static Configuration conf = null;
	
	private List<ComponentConfig> componentConfigs = new ArrayList<ComponentConfig>();
	private Theme theme = null;
	private Resources androidResources = null;
	
	/** 
	 * Private default constructor.
	 */
	private Configuration(Resources r) {
		theme = new Theme(r);
		androidResources = r;
	}
	
	/**
	 * It returns default configuration supplied with application.
	 * @return
	 */
	public static Configuration getDefaultConfiguration(Resources r) {	
		conf = new Configuration(r);
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
			
			if (!conf.loadXMLFile(xml)) {
				Log.e(LOG_TAG, "Configuration::loadXMLFile() returned false.");
				return null;
			}
		} catch (IOException e) {
			Log.e(LOG_TAG, "IOException during loading default.xml");
			e.printStackTrace();
		}

		return conf;
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
	 * Get's current configuration.
	 * @return
	 */
	public static Configuration getCurrentInstance(Resources r) {
		if (conf == null) {
			return getDefaultConfiguration(r);
		}
		return conf;
	}
	
	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public Resources getAndroidResources() {
		return androidResources;
	}

	public void setAndroidResources(Resources androidResources) {
		this.androidResources = androidResources;
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
					Object component = ReflectionUtils
							.createClassInstance("marino39.agamepad.conf." + child.getName() + "Config");				
					Properties p = child.getAttributes();
					Enumeration<Object> e = p.keys();
					
					while(e.hasMoreElements()) {
						String key = (String) e.nextElement();
						String uval = p.getProperty(key);
						Object val = parseXMLValue(uval);
						ReflectionUtils.setFieldValue(component, key, val);
					}
					
					if (component != null) {
						componentConfigs.add((ComponentConfig) component);
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
	
	public void populate(UIMain ui) {
		for (int i = 0; i < componentConfigs.size(); i++) {
			UIComponent c = componentConfigs.get(i).getConfiguredComponent();
			if (c != null) {
				ui.addUIComponent(c);
			}
		}
		ui.invalidate();
	}
	
	private Object parseXMLValue(String a) {
		Pattern floatPattern = Pattern.compile( "([0-9]*)\\.([0-9]*)" );
		Pattern intPattern = Pattern.compile( "([0-9]*)" );
		Pattern keyeventPattern = Pattern.compile( "(KeyEvent)(\\.)(.*)" );
		Pattern resourcePattern = Pattern.compile( "(R)(\\.)(.*)" );
		Pattern stringPattern = Pattern.compile( "(['])(.*)(['])" );

		if (floatPattern.matcher(a).matches()) {
			return Float.parseFloat(a);
		} else if (intPattern.matcher(a).matches()) {
			return Integer.parseInt(a);
		} else if (resourcePattern.matcher(a).matches()) {
			return ReflectionUtils.getFieldValue(R.drawable.class, a.substring(a.lastIndexOf('.') + 1));
		} else if (stringPattern.matcher(a).matches()) {
			return a.substring(1, a.length() - 1);
		} else if (keyeventPattern.matcher(a).matches()) {
			String[] splited = a.split("\\.");
			return splited.length == 2 ? ReflectionUtils.getFieldValue(KeyEvent.class, splited[1]) : a;
		} else {
			return a;
		}
	}
	
}
