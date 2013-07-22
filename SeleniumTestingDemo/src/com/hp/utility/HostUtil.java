package com.hp.utility;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostUtil {
	
	
	
	
	public static String getShortHostName()
	{
	
		String hostname=null;
		try {
			hostname= InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hostname;
	}
    public static String getHostIP()
    {
    	String hostip=null;
        try {
        	hostip=InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return hostip;
    }
    
    public static String getFQDN()
    {
    	String fqdn=null;
    	try {
			fqdn=InetAddress.getLocalHost().getCanonicalHostName().trim();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return fqdn;
    }
}
