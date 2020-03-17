package com.blog.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.blog.entity.Catalog;
import com.blog.util.JDBCUtil;

public class CatalogDao extends BaseDao {
	
	/**
	 * 添加栏目
	 * @param Catalog
	 * @return
	 */
	public boolean addCatalog(Catalog catalog) {
		String sql = "insert into catalog(catalog_id,catalog_name,descr values(?,?,?)";
		try {
			conn=JDBCUtil.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,catalog.getCatalogId());
			pstmt.setString(2,catalog.getCatalogName());
			pstmt.setString(3,catalog.getDescr());
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
	 * 根据栏目ID获取栏目信息
	 * @param articleId
	 * @return
	 */
	public Catalog getCatalog(int catalogId) {
		String sql = "select * from catalog where catalog_id="+catalogId;
		Catalog catalog=null;
		try {
			conn=JDBCUtil.getConnection();
			stmt=conn.createStatement();
			rst=stmt.executeQuery(sql);
			if(rst.next()) {
				catalog =new Catalog();
				catalog.setCatalogId(catalogId);
				catalog.setCatalogName(rst.getString(1));
				catalog.setDescr(rst.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.closeAll(conn, stmt, null, rst);
		}
		return catalog;
	}
	
	/**
	 * 获取所有栏目
	 * @return
	 */
	public List<Catalog> getCatalogList() {
		String sql = "select * from catalog";
		List<Catalog> catalogList=new ArrayList<Catalog>();
		try {
			conn=JDBCUtil.getConnection();
			stmt=conn.createStatement();
			rst=stmt.executeQuery(sql);
			while(rst.next()) {
				Catalog catalog =new Catalog();
				catalog.setCatalogId(rst.getInt(1));
				catalog.setCatalogName(rst.getString(2));
				catalog.setDescr(rst.getString(3));
				catalogList.add(catalog);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.closeAll(conn, stmt, null, rst);
		}
		return catalogList;
	 }
}
