package com.jason.WeiXinDev.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LBSUtilTest {
	
	private static Logger logger = LoggerFactory.getLogger(LBSUtilTest.class);
	
	@Test
	public void testConvCoord() throws IOException, JSONException {
		String[] str = LBSUtil.convCoord("138.11", "35.44");
		System.out.println(str[0]);
		System.out.println(str[1]);
		
		String url =  "lng_dest="  + "&lat_dest="  + "&lng_my="  + "&lat_my=" ;
		System.out.println(url);
		logger.info("log4j");
	}
	

}
