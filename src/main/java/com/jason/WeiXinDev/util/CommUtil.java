package com.jason.WeiXinDev.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CommUtil {
	
	//private static Logger logger 
	
	public static String getProperty(String key,String path){
		String value = null;
		
		Properties info = new Properties();
		InputStream is = CommUtil.class.getClassLoader().getResourceAsStream(path);
		if(null!= is){
			try {
				info.load(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			value = info.getProperty(key);
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("no");
			//logger.error("no InputSream");
		}
		
		return value;
		
	}

}
