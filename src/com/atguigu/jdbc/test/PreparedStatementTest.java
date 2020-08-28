package com.atguigu.jdbc.test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import org.junit.Test;

public class PreparedStatementTest {
	@Test
	public void testInsert() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
			Properties prop = new Properties();
			prop.load(is);
			String driverClass = prop.getProperty("driverClass");
			String driverUrl = prop.getProperty("driverUrl");
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			Class.forName(driverClass);
			conn = DriverManager.getConnection(driverUrl, username, password);
			String sql = "INSERT INTO CUSTOMERS(NAME,EMAIL,BIRTH) VALUES(?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "xiaoli");
			pstmt.setString(2, "xiaoli@qq.com");
			pstmt.setDate(3, new java.sql.Date(new Date().getTime()));
			boolean flag = pstmt.execute();
			System.out.println(flag);
			if (flag) {
				System.out.println("添加成功！");
			} else {
				System.out.println("添加失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
