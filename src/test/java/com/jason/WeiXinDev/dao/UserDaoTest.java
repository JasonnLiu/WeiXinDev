package com.jason.WeiXinDev.dao;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserDaoTest {

//	@Test
//	public void testSave() {
//		UserDao.save("2", "location_X", "location_Y", "loc_x", "loc_y");
//	}
	
	@Test
	public void testQuery() {
		String[] str = UserDao.queryUserLoc("3");
		System.out.println(str == null);
	}

}
