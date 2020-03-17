package com.blog.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.blog.entity.AdminLoginLog;
import com.blog.util.DateUtil;
import com.blog.util.JDBCUtil;

public class AdminLoginLogDao extends BaseDao {
	
	/**
	 * 添加日志
	 * @param log
	 * @return
	 */
	public boolean addLoginLog(AdminLoginLog log) {
		String sql = "insert into admin_login_log(admin_id,login_ip,login_date) " +
				"values(?,?,?)";
		try {
			conn=JDBCUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,log.getAdminId());
			pstmt.setString(2,log.getLoginIp());
			pstmt.setTimestamp(3, DateUtil.toSqlTimestamp(log.getLoginDate()));
			int r=pstmt.executeUpdate();
			if(r>0) return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			JDBCUtil.closeAll(conn, null, pstmt, null);
		}
		return false;
	}
	
	/**
	 * 获取最近登录信息
	 * @param adminId
	 * @return
	 */
	public List<AdminLoginLog> getRecentLoginLogs(int adminId) {
		List<AdminLoginLog> logs=new ArrayList<AdminLoginLog>();
		String sql="select * from admin_login_log where admin_id=? order by log_id desc limit 2";
		try {
			conn=JDBCUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,adminId);
			rst=pstmt.executeQuery();
			while(rst.next()) {
				AdminLoginLog log = new AdminLoginLog();
				log.setLogId(rst.getInt(1));
				log.setAdminId(rst.getInt(2));
				log.setLoginIp(rst.getString(3));
				log.setLoginDate(rst.getTimestamp(4));
				logs.add(log);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.closeAll(conn, null, pstmt, rst);
		}
		return logs;
	}
	
	/**
	 * 获取登录次数
	 * @param adminId
	 * @return
	 */
	public int getLoginCount(int adminId) {
		String sql = "select count(log_id) from admin_login_log where admin_id="+adminId;
		try {
			conn=JDBCUtil.getConnection();
			stmt=conn.createStatement();
			rst=stmt.executeQuery(sql);
			if(rst.next()) {
				return rst.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			JDBCUtil.closeAll(conn, stmt, null, rst);
		}
		return 0;
	}
}
