package com.woniuxy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




public class Function {
	public static Connection getConnection() {
		String url = "jdbc:mysql://127.0.0.1:3306/atm?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
		String user = "root";
		String password = "123456";
		String DriveClass = "com.mysql.cj.jdbc.Driver";
		
		Connection conn = null;
		try {
			Class.forName(DriveClass);
			conn = DriverManager.getConnection(url,user,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;		
	}
	public static void close(Connection conn,PreparedStatement pst) {
		try {
			if(pst != null) {
				pst.close();
			}
			if(conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn,PreparedStatement pst,ResultSet rs) {
		try {
			if(pst != null) {
				pst.close();
			}
			if(conn != null) {
				conn.close();
			}
			if(rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn,PreparedStatement pst1,PreparedStatement pst2) {
		try {
			if(pst1 != null) {
				pst1.close();
			}
			if(conn != null) {
				conn.close();
			}
			if(pst2 != null) {
				pst2.close();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}	

}
