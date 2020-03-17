package com.blog.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.dao.ArticleDao;
import com.blog.dao.CommentDao;
import com.blog.entity.Article;
import com.blog.entity.Comment;

public class GotoDetailServlet extends HttpServlet {

	private ArticleDao articleDao=new ArticleDao();
	private CommentDao commentDao=new CommentDao();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//获取文章id
		int articleId = Integer.parseInt(request.getParameter("articleId"));
		
		//使文章的点击量加1
		articleDao.increaseClick(articleId);
		
		//根据文章id从数据库获取文章信息
		Article article = articleDao.getArticleById(articleId);
		Article preArticle = articleDao.getPreArticleById(articleId);
		Article nextArticle = articleDao.getNextArticleById(articleId);
		
		//根据文章ID获取评论信息
		List<Comment> commentList = commentDao.getArticleComment(articleId);
		
		//保存到请求作用域
		request.setAttribute("article", article);
		request.setAttribute("preArticle", preArticle);
		request.setAttribute("nextArticle", nextArticle);
		request.setAttribute("articleId", articleId);
		request.setAttribute("commentList",commentList);
		
		//跳转
		request.getRequestDispatcher("detail.jsp").forward(request, response);
	}

}
