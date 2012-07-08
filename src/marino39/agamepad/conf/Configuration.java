package marino39.agamepad.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Pattern;

import marino39.agamepad.AndroidGamepadActivity;
import marino39.agamepad.KeyEvent;
import marino39.agamepad.R;
import marino39.agamepad.net.AndroidGamepadClient;
import marino39.agamepad.theme.Theme;
import marino39.agamepad.ui.AGUIMain;
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
import android.os.IBinder;
import android.util.Log;

/**
 * Class that holds all configuration of app. It also
 * takes part in comunication between varius part of
 * Android Gamepad.
 * 
 * @author Marcin Gorzynski
 *
 */
public class Configuration {
	
	private static final String LOG_TAG = "[Configuration]";
	private static final String CONFIG_DIR = "AndroidGamepad/";
	private static final String CLASS_PATH_DEFAULT_CONFIG_DIR = "/marino39/agamepad/conf/default.xml";
	private static Configuration conf = null;
	
	private List<ComponentConfig> componentConfigs = new ArrayList<ComponentConfig>();
	private Theme theme = null;
	private AndroidGamepadActivity mainActivity = null;
	
	/** 
	 * Private default constructor.
	 */
	private Configuration(AndroidGamepadActivity activity) {
		mainActivity = activity;
		theme = new Theme(mainActivity.getResources());
	}
	
	/**
	 * It returns default configuration supplied with application.
	 * @return
	 */
	public static Configuration getDefaultConfiguration(AndroidGamepadActivity activity) {		
		conf = new Configuration(activity);
			
		if (!conf.loadDataFromXML(loadDefaultXML())) {
			Log.e(LOG_TAG, "Configuration::loadXMLFile() returned false.");
			return null;
		}

		return conf;
	}
	
	
	/**
	 * Loads XML file into program.
	 * @return String containing XML tags.
	 */
	private static String loadDefaultXML() {
		InputStream in = Configuration.class.getResourceAsStream(CLASS_PATH_DEFAULT_CONFIG_DIR);
		
		if (in == null) {
			Log.e(LOG_TAG, "Could not get default.xml from classpath.");
			return null;
		}
				
		try {
			int len = in.available();
			byte[] data = new byte[len];
			in.read(data, 0, len);
			return  new String(data);		
		} catch (IOException e) {
			Log.e(LOG_TAG, "IOException during loading default.xml");
			e.printStackTrace();
		}
		
		return null;
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
	 * Get's current instance of Configuration. If createNew is true 
	 * it will create new instance when there isn't any at the moment.
	 * 
	 * @param activity
	 * @param createNew
	 * @return
	 */
	public static Configuration getCurrentInstance(AndroidGamepadActivity activity, boolean createNew) {
		if (conf == null && createNew) {
			return getDefaultConfiguration(activity);
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
		return mainActivity.getResources();
	}

	/**
	 * Loads settings form XML File. 
	 * @param is
	 * @param c
	 */
	private boolean loadDataFromXML(String xml) {
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
	
	/**
	 * Parses XML attribute and assign it to proper variable type. 
	 * 
	 * @param a attribute contents to parse.
	 * @return variable containing parsed data.
	 */
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
	
	/**
	 * Gets current Android Gamepad client.
	 * 
	 * @return
	 */
	public AndroidGamepadClient getAndroidGamepadClient() {
		IBinder binder = mainActivity.getmBinder();
		if (binder.isBinderAlive()) {
			return mainActivity.getmService().getAndroidGamepadClient();
		}
		
		return null;
	}
	
}
