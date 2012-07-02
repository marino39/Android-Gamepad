package marino39.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.util.Log;

public class ReflectionUtils {
	
	public static final String LOG_TAG = "[ReflectionUtils]";	
	
	public static Object getFieldValue(Class c, String fieldName) {
		try {
			Field f = c.getDeclaredField(fieldName);
			return f.get(null);						
		} catch (SecurityException e) {
			Log.e(LOG_TAG, "SecurityException throwed while setFieldValue");
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			Log.e(LOG_TAG, "NoSuchFieldException throwed while setFieldValue");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			Log.e(LOG_TAG, "IllegalArgumentException throwed while setFieldValue");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Log.e(LOG_TAG, "IllegalAccessException throwed while setFieldValue");
			e.printStackTrace();
		}
			
		return null;
	}
	
	public static Object getFieldValue(String fieldPath) {
		try {
			int il = fieldPath.lastIndexOf('.');
			String fieldName = fieldPath.substring(il + 1);
			String className = fieldPath.substring(0, il);
			Class c = Class.forName(className);
			Field f = c.getDeclaredField(fieldName);
			return f.get(null);		
		} catch (ClassNotFoundException e) {
			Log.e(LOG_TAG, "ClassNotFoundException throwed while getFieldValue");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Log.e(LOG_TAG, "IllegalAccessException throwed while getFieldValue");
			e.printStackTrace();
		} catch (SecurityException e) {
			Log.e(LOG_TAG, "SecurityException throwed while getFieldValue");
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			Log.e(LOG_TAG, "NoSuchFieldException throwed while getFieldValue");
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static void setFieldValue(Object instance, String fieldName, Object value) {
		try {
			Class c = instance.getClass();
			String setterName = "set" + fieldName;
					
			Method[] methods = c.getMethods();
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().equalsIgnoreCase(setterName)) {
					methods[i].invoke(instance, value);
				}
			}
		} catch (SecurityException e) {
			Log.e(LOG_TAG, "SecurityException throwed while setFieldValue");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			Log.e(LOG_TAG, "IllegalArgumentException throwed while setFieldValue");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Log.e(LOG_TAG, "IllegalAccessException throwed while setFieldValue");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			Log.e(LOG_TAG, "InvocationTargetException throwed while setFieldValue");
			e.printStackTrace();
		}
	}
	
	public static Object createClassInstance(String className) {
		try {
			Class c = Class.forName(className);
			return c.newInstance();
		} catch (ClassNotFoundException e) {
			Log.e(LOG_TAG, "ClassNotFoundException throwed while createClassInstance");
			e.printStackTrace();
		} catch (InstantiationException e) {
			Log.e(LOG_TAG, "InstantiationException throwed while createClassInstance");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Log.e(LOG_TAG, "IllegalAccessException throwed while createClassInstance");
			e.printStackTrace();
		}
		return null;
	}
	
}
