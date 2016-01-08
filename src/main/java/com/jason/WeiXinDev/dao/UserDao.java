package com.jason.WeiXinDev.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UserDao {

	private static Connection conn = null;

	public static void connect() {
		Properties info = new Properties();

		InputStream in = UserDao.class.getClassLoader().getResourceAsStream("ace_jdbc.properties");

		try {
			info.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String driverClassName = info.getProperty("driverClassName");
		String url = info.getProperty("url");
		String userName = info.getProperty("userName");
		String password = info.getProperty("password");
		try {
			Class.forName(driverClassName);
			conn = DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void save(String open_id, String location_X, String location_Y, String loc_x, String loc_y) {

		connect();
		String sql_insert = "insert into user_location( open_id,lng,lat,bd09_lng,bd09_lat)" + "values('" + open_id
				+ "','" + location_Y + "','" + location_X + "','" + loc_y + "','" + loc_x + "')";
		System.out.println(sql_insert);

		try {

			PreparedStatement pstmt = conn.prepareStatement(sql_insert);
			pstmt.execute();
			pstmt.close();
			conn.close();
			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String[] queryUserLoc(String open_id) {

		connect();

		String sql_query = "select * from user_location where open_id = '" + open_id + "'";
		System.out.println(sql_query);
		String[] returnValue = new String[2];
		PreparedStatement pstmt;
		ResultSet rs;
		try {
			pstmt = conn.prepareStatement(sql_query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				returnValue[1] = rs.getString(5);
				returnValue[0] = rs.getString(6);
			} else {
				returnValue = null;
			}

			pstmt.close();
			conn.close();
			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnValue;
	}

}
