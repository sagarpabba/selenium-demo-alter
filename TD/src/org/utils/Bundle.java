package org.utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Bundle{
	   private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("settings"); //resource.properties file
	   public static String getString(String key) {
	     try {
	        return RESOURCE_BUNDLE.getString(key);
	     } catch (MissingResourceException e) {
	       return '!' + key + '!'; 
	     }
	   }
	}
