package com.blog.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.dao.ArticleDao;
import com.blog.entity.Article;
import com.blog.entity.PageData;

public class IndexServlet extends HttpServlet {

	private ArticleDao articleDao=new ArticleDao();
	private PageData pageData = new PageData();
	/*首页请求的Servlet**/
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//获取总文章数并设置分页对象
		int maxCount = articleDao.getArticleCount();
		pageData.setMaxCount(maxCount);
		pageData.setPageSize(5);
		
		//接受请求参数currIndex
		String currIndex = request.getParameter("currIndex");
		if(currIndex !=null && !"".equals(currIndex)){
			pageData.setCurrIndex(Integer.parseInt(currIndex));//设置当前页码
		}
		
		//根据分页实体获取文章
		List<Article> articleList = articleDao.getArticles(pageData);
		
		//存入请求作用域
		request.setAttribute("articleList", articleList);
		request.setAttribute("pageData", pageData);
		
		//进入首页
		request.getRequestDispatcher("index.jsp").forward(request, response);
		
		
	}

}
