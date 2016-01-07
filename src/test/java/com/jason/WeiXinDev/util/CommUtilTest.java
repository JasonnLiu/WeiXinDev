package com.jason.WeiXinDev.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommUtilTest {
	
	private static Logger logger = LoggerFactory.getLogger(CommUtilTest.class);

	@Test
	public void testGetProperty() {
		String ak = CommUtil.getProperty("ak", "B_Map.properties");
		System.out.println(ak);
		logger.info("log4j");
		
	}

}
