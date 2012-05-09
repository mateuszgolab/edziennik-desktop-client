package com.polsl.edziennik.desktopclient.controller.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public final class LangManager {
	
	private static volatile LangManager instance = null;
      
    public static LangManager getInstance() 
    {
        if (instance == null) {
            synchronized (LangManager.class) 
            {
                if (instance == null) 
                {
                    instance = new LangManager();
                }
            }
        }
        return instance;
    }
    
   
    private LangManager()
    {
    	
    }
    
    public static ResourceBundle getResource(String bundle)
    {
    	return ResourceBundle.getBundle(bundle);
    }
    
    public static ResourceBundle getResource(String bundle, Locale locale)
    {
    	return ResourceBundle.getBundle(bundle, locale);
    }
}
