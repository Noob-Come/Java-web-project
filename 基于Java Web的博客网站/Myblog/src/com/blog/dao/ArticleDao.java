package com.blog.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.blog.entity.Article;
import com.blog.entity.PageData;
import com.blog.util.DateUtil;
import com.blog.util.JDBCUtil;

public class ArticleDao extends BaseDao {
	
	/**
	 * 添加文章
	 * @param article
	 * @return
	 */
	public boolean addArticle(Article article) {
		String sql = "insert into article(catalog_id,admin_id,title,keywords,summary,content,pub_date,click) " +
				"values(?,?,?,?,?,?,?,?)";
		try {
			conn=JDBCUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,article.getCatalogId());
			pstmt.setInt(2,article.getAdminId());
			pstmt.setString(3,article.getTitle());
			pstmt.setString(4,article.getKeywords());
			pstmt.setString(5,article.getSummary());
			pstmt.setString(6,article.getContent());
			pstmt.setTimestamp(7,DateUtil.toSqlTimestamp(article.getPubDate()));
			pstmt.setInt(8, article.getClick());
			
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
	 * 根据文章ID增加点击量
	 * @param articleId
	 * @return
	 */
	public boolean increaseClick(int articleId) {
		String sql = "update article set click=click+1 where article_id="+articleId;
		try {
			conn=JDBCUtil.getConnection();
			stmt=conn.createStatement();
			int r=stmt.executeUpdate(sql);
			if(r>0) return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			JDBCUtil.closeAll(conn, stmt, null, rst);
		}
		return false;
	}
	
	/**
	 * 根据文章ID修改文章
	 * @param article
	 * @return
	 */
	public boolean alertArticle(Article article,int articleId) {
		String sql = "update article set title=?,keywords=?,summary=?,content=?,pub_date=? where article_id="+articleId;
		try {
			conn=JDBCUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,article.getTitle());
			pstmt.setString(2,article.getKeywords());
			pstmt.setString(3,article.getSummary());
			pstmt.setString(4,article.getContent());
			pstmt.setTimestamp(5,DateUtil.toSqlTimestamp(new Date()));
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
	 * 获取用户发布的文章数
	 * @param adminId
	 * @return
	 */
	public int getArticleCount(int adminId) {
		String sql = "select count(article_id) from article where admin_id="+adminId;
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
	 * 获取用户发布的所有文章
	 * @param adminId
	 * @return
	 */
	public List<Article> getAdminArticle(int adminId) {
		String sql = "select * from article where admin_id="+adminId;
		List<Article> articles=new ArrayList<Article>();
		try {
			conn=JDBCUtil.getConnection();
			stmt=conn.createStatement();
			rst=stmt.executeQuery(sql);
			while(rst.next()) {
				Article article =new Article();
				article.setArticleId(rst.getInt(1));
				article.setCatalogId(rst.getInt(2));
				article.setAdminId(rst.getInt(3));
				article.setTitle(rst.getString(4));
				article.setKeywords(rst.getString(5));
				article.setSummary(rst.getString(6));
				article.setContent(rst.getString(7));
				article.setPubDate(rst.getTimestamp(8));
				article.setClick(rst.getInt(9));
				articles.add(article);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.closeAll(conn, stmt, null, rst);
		}
		return articles;
	}
	
	/**
	 * 获取所有文章
	 * @param 
	 * @return
	 */
	public List<Article> getArticles() {
		String sql = "select * from article order by article_id desc";
		List<Article> articles=new ArrayList<Article>();
		try {
			conn=JDBCUtil.getConnection();
			stmt=conn.createStatement();
			rst=stmt.executeQuery(sql);
			while(rst.next()) {
				Article article =new Article();
				article.setArticleId(rst.getInt(1));
				article.setCatalogId(rst.getInt(2));
				article.setAdminId(rst.getInt(3));
				article.setTitle(rst.getString(4));
				article.setKeywords(rst.getString(5));
				article.setSummary(rst.getString(6));
				article.setContent(rst.getString(7));
				article.setPubDate(rst.getTimestamp(8));
				article.setClick(rst.getInt(9));
				articles.add(article);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.closeAll(conn, stmt, null, rst);
		}
		return articles;
	}
	
	/**
	 * 根据文章标题和内容模糊搜索最多10篇文章
	 * @param 
	 * @return
	 */
	public List<Article> searchArticle(String word) {
		
		List<Article> articles=new ArrayList<Article>();
		String sql = "select * from article where title like '%"+word+"%' or content like '%"+word+"%' limit 10";
		try {
			conn=JDBCUtil.getConnection();
			stmt=conn.createStatement();
			rst=stmt.executeQuery(sql);
			while(rst.next()) {
				Article article =new Article();
				article.setArticleId(rst.getInt(1));
				article.setCatalogId(rst.getInt(2));
				article.setAdminId(rst.getInt(3));
				article.setTitle(rst.getString(4));
				article.setKeywords(rst.getString(5));
				article.setSummary(rst.getString(6));
				article.setContent(rst.getString(7));
				article.setPubDate(rst.getTimestamp(8));
				article.setClick(rst.getInt(9));
				articles.add(article);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.closeAll(conn, stmt, null, rst);
		}
		return articles;
	}
	
	/**
	 * 根据分页获取相应文章
	 * @param 
	 * @return
	 */
	public List<Article> getArticles(PageData pageData) {
		String sql = "select * from article order by article_id desc limit ?,?";
		List<Article> articles=new ArrayList<Article>();
		try {
			int startIndex = pageData.getPageSize()*(pageData.getCurrIndex()-1);
			int pageSize = pageData.getPageSize();
			conn=JDBCUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, startIndex);
			pstmt.setInt(2, pageSize);
			rst=pstmt.executeQuery();
			while(rst.next()) {
				Article article =new Article();
				article.setArticleId(rst.getInt(1));
				article.setCatalogId(rst.getInt(2));
				article.setAdminId(rst.getInt(3));
				article.setTitle(rst.getString(4));
				article.setKeywords(rst.getString(5));
				article.setSummary(rst.getString(6));
				article.setContent(rst.getString(7));
				article.setPubDate(rst.getTimestamp(8));
				article.setClick(rst.getInt(9));
				articles.add(article);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.closeAll(conn, stmt, null, rst);
		}
		return articles;
	}
	
	/**
	 * 根据分页获取用户相应文章
	 * @param 
	 * @return
	 */
	public List<Article> getArticles(int adminId,PageData pageData) {
		String sql = "select * from article where admin_id="+adminId+" limit ?,?";
		List<Article> articles=new ArrayList<Article>();
		try {
			int startIndex = pageData.getPageSize()*(pageData.getCurrIndex()-1);
			int pageSize = pageData.getPageSize();
			conn=JDBCUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, startIndex);
			pstmt.setInt(2, pageSize);
			rst=pstmt.executeQuery();
			while(rst.next()) {
				Article article =new Article();
				article.setArticleId(rst.getInt(1));
				article.setCatalogId(rst.getInt(2));
				article.setAdminId(rst.getInt(3));
				article.setTitle(rst.getString(4));
				article.setKeywords(rst.getString(5));
				article.setSummary(rst.getString(6));
				article.setContent(rst.getString(7));
				article.setPubDate(rst.getTimestamp(8));
				article.setClick(rst.getInt(9));
				articles.add(article);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.closeAll(conn, stmt, null, rst);
		}
		return articles;
	}
	
	/**
	 * 获取所有用户最近发布的5篇文章
	 * @param 
	 * @return
	 */
	public List<Article> getRecentArticle() {
		String sql = "select * from article order by article_id desc limit 5";
		List<Article> articles=new ArrayList<Article>();
		try {
			conn=JDBCUtil.getConnection();
			stmt=conn.createStatement();
			rst=stmt.executeQuery(sql);
			while(rst.next()) {
				Article article =new Article();
				article.setArticleId(rst.getInt(1));
				article.setCatalogId(rst.getInt(2));
				article.setAdminId(rst.getInt(3));
				article.setTitle(rst.getString(4));
				article.setKeywords(rst.getString(5));
				article.setSummary(rst.getString(6));
				article.setContent(rst.getString(7));
				article.setPubDate(rst.getTimestamp(8));
				article.setClick(rst.getInt(9));
				articles.add(article);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.closeAll(conn, stmt, null, rst);
		}
		return articles;
	}
	
	/**
	 * 根据文章ID获取文章
	 * @param articleId
	 * @return
	 */
	public Article getArticleById(int articleId) {
		String sql = "select * from article where article_id="+articleId;
		Article article=null;
		try {
			conn=JDBCUtil.getConnection();
			stmt=conn.createStatement();
			rst=stmt.executeQuery(sql);
			if(rst.next()) {
				article =new Article();
				article.setArticleId(rst.getInt(1));
				article.setCatalogId(rst.getInt(2));
				article.setAdminId(rst.getInt(3));
				article.setTitle(rst.getString(4));
				article.setKeywords(rst.getString(5));
				article.setSummary(rst.getString(6));
				article.setContent(rst.getString(7));
				article.setPubDate(rst.getTimestamp(8));
				article.setClick(rst.getInt(9));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.closeAll(conn, stmt, null, rst);
		}
		return article;
	}
	
	/** 
	 * 根据文章ID删除文章
	 * @param articleId
	 * @return
	 */
	public boolean deleteArticle(int articleId) {
		String sql = "delete from article where article_id="+articleId;
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
	 * 获取总文章数
	 * @return
	 */
	public int getArticleCount() {
		String sql = "select count(article_id) from article";
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
	 * 获取上一篇文章
	 * @param articleId
	 * @return
	 */
	public Article getPreArticleById(int articleId) {
		String sql = "select * from article where article_id=(select article_id from article where article_id>" +articleId+
				" order by article_id asc limit 1)";
		Article article=null;
		try {
			conn=JDBCUtil.getConnection();
			stmt=conn.createStatement();
			rst=stmt.executeQuery(sql);
			if(rst.next()) {
				article =new Article();
				article.setArticleId(rst.getInt(1));
				article.setCatalogId(rst.getInt(2));
				article.setAdminId(rst.getInt(3));
				article.setTitle(rst.getString(4));
				article.setKeywords(rst.getString(5));
				article.setSummary(rst.getString(6));
				article.setContent(rst.getString(7));
				article.setPubDate(rst.getTimestamp(8));
				article.setClick(rst.getInt(9));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.closeAll(conn, stmt, null, rst);
		}
		return article;
	}

	/**
	 * 获取下一篇文章
	 * @param articleId
	 * @return
	 */
	public Article getNextArticleById(int articleId) {
		String sql = "select * from article where article_id=(select article_id from article where article_id<" +articleId+
				" order by article_id desc limit 1)";
		Article article=null;
		try {
			conn=JDBCUtil.getConnection();
			stmt=conn.createStatement();
			rst=stmt.executeQuery(sql);
			if(rst.next()) {
				article =new Article();
				article.setArticleId(rst.getInt(1));
				article.setCatalogId(rst.getInt(2));
				article.setAdminId(rst.getInt(3));
				article.setTitle(rst.getString(4));
				article.setKeywords(rst.getString(5));
				article.setSummary(rst.getString(6));
				article.setContent(rst.getString(7));
				article.setPubDate(rst.getTimestamp(8));
				article.setClick(rst.getInt(9));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.closeAll(conn, stmt, null, rst);
		}
		return article;
	}
	
	
}
