package com.blog.dao;

import java.sql.*;

import com.blog.entity.Admin;
import com.blog.util.JDBCUtil;

public class AdminDao extends BaseDao{
	
	/**
	 * 根据管理员的账号查询管理员
	 * @param adminName
	 * @return
	 */
	public Admin queryAdminByName(String adminName) {
		Admin admin=null;
		String sql="select * from admin where admin_name="+"'"+adminName+"'";
		
		try {
			conn=JDBCUtil.getConnection();
			stmt=conn.createStatement();
			rst=stmt.executeQuery(sql);
			if(rst.next()) {
				admin=new Admin();
				admin.setAdminId(rst.getInt(1));
				admin.setAdminName(rst.getString(2));
				admin.setAdminPass(rst.getString(3));
				admin.setNickname(rst.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			JDBCUtil.closeAll(conn, stmt, null, rst);
		}
		return admin;
	}
	
	/**
	 * 添加用户
	 * @param admin
	 * @return
	 */
	public boolean addAdmin(Admin admin){
		String sql="insert into admin(admin_name,admin_pass,nickname) "+"values(?,?,?)";
		try {
			conn=JDBCUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,admin.getAdminName());
			pstmt.setString(2,admin.getAdminPass());
			pstmt.setString(3,admin.getNickname());
			int r=pstmt.executeUpdate();
			if(r>0) return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally{
			JDBCUtil.closeAll(conn, null, pstmt, null);
		}
		return false;
	}
	
}
