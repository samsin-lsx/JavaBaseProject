package com.atguigu.jdbc.test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionTest {
	public void testLoalPropertiesConnection() throws Exception {
		InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties prop = new Properties();
		prop.load(is);
		String driverClass = prop.getProperty("driverClass");
		String driverUrl = prop.getProperty("driverUrl");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		Class.forName(driverClass);
		Connection conn = DriverManager.getConnection(driverUrl, username, password);
		System.out.println(conn);
	}

	public void testConnection() throws Exception {
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "root";
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println(conn);
	}
}
