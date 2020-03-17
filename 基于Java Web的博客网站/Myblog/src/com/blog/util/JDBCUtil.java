package com.blog.util;
import java.sql.*;

public class JDBCUtil {
	private static Connection conn=null;
	
	/**
	 * 获取数据库连接对象
	 * @return Connection
	 */
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("未找到驱动类...");
		}
		String url="jdbc:mysql://localhost:3306/myblog?characterEncoding=utf8";
		String password="root";
		try {
			conn=DriverManager.getConnection(url,"root",password);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("数据库连接失败...");
		}
		
		return conn;
	}
	/**
	 * 释放资源
	 * @param conn
	 * @param stmt
	 * @param pstmt
	 * @param rst
	 */
	public static void closeAll(Connection conn, Statement stmt,
			PreparedStatement pstmt, ResultSet rst) {
		try {
			if(conn!=null) {
				conn.close();
				conn=null;
			}
			if(stmt!=null) {
				stmt.close();
				stmt=null;
			}
			if(pstmt!=null) {
				pstmt.close();
				pstmt=null;
			}
			if(rst!=null) {
				rst.close();
				rst=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
