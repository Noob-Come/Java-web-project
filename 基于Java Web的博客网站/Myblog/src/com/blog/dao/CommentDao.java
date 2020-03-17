package com.blog.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.blog.entity.Comment;
import com.blog.util.DateUtil;
import com.blog.util.JDBCUtil;

public class CommentDao extends BaseDao {

	/**
	 * 查看某篇文章所有评论
	 * @param articleId
	 * @return
	 */
	public List<Comment> getArticleComment(int articleId) {
		String sql = "select * from comment where article_id="+articleId;
		List<Comment> comments=new ArrayList<Comment>();
		try {
			conn=JDBCUtil.getConnection();
			stmt=conn.createStatement();
			rst=stmt.executeQuery(sql);
			while(rst.next()) {
				Comment comment =new Comment();
				comment.setcId(rst.getInt(1));
				comment.setArticleId(rst.getInt(2));
				comment.setcContent(rst.getString(3));
				comment.setcDate(rst.getTimestamp(4));
				comment.setcNickname(rst.getString(5));
				comment.setcEmail(rst.getString(6));
				comments.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.closeAll(conn, stmt, null, rst);
		}
		return comments;
	}
	
	/**
	 * 添加评论
	 * @param Comment
	 * @return
	 */
	public boolean addComment(Comment comment) {
		String sql = "insert into comment(article_id,c_content,c_date,c_nickname,c_email) " +
				"values(?,?,?,?,?)";
		try {
			conn=JDBCUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,comment.getArticleId());
			pstmt.setString(2,comment.getcContent());
			pstmt.setTimestamp(3,DateUtil.toSqlTimestamp(comment.getcDate()));
			pstmt.setString(4,comment.getcNickname());
			pstmt.setString(5,comment.getcEmail());
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
	 * 删除某篇文章的评论
	 * @param Comment
	 * @return
	 */
	public boolean deleteComment(int cId) {
		String sql = "delete from comment where c_id="+cId;
		try {
			conn=JDBCUtil.getConnection();
			stmt=conn.createStatement();
			int r=stmt.executeUpdate(sql);
			if(r>0) return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			JDBCUtil.closeAll(conn, stmt, null, null);
		}
		return false;
	}
	
	/**
	 * 获取所有文章评论数
	 * @param adminId
	 * @return
	 */
	public int getCommentCount(int adminId) {
		String sql = "select count(t1.c_id) from comment t1,article t2 " +
				"where t1.article_id=t2.article_id and t2.admin_id="+adminId;
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

	/**
	 * 根据评论ID获取评论
	 * @param articleId
	 * @return
	 */
	public Comment getCommentById(int cId) {
		String sql = "select * from comment where c_id="+cId;
		Comment comment=null;
		try {
			conn=JDBCUtil.getConnection();
			stmt=conn.createStatement();
			rst=stmt.executeQuery(sql);
			while(rst.next()) {
				comment =new Comment();
				comment.setcId(rst.getInt(1));
				comment.setArticleId(rst.getInt(2));
				comment.setcContent(rst.getString(3));
				comment.setcDate(rst.getTimestamp(4));
				comment.setcNickname(rst.getString(5));
				comment.setcEmail(rst.getString(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.closeAll(conn, stmt, null, rst);
		}
		return comment;
	}
}
