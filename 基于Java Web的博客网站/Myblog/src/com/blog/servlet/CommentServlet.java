package com.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.blog.dao.CommentDao;
import com.blog.entity.Comment;

public class CommentServlet extends HttpServlet {

	private CommentDao commentDao=new CommentDao();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		//System.out.println(method+"  "+articleId);
		if(method.equals("addComment")) {
			addComment(request,response);
		} else if(method.equals("manageComment")){
			manageComment(request,response);
		} else if(method.equals("commentDel")) {
			commentDel(request,response);
		}
	}

	/**删除评论**/
	public void commentDel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取评论id
		int cId = Integer.parseInt(request.getParameter("cId"));
		
		//获取评论的文章ID
		int articleId = commentDao.getCommentById(cId).getArticleId();
		
		System.out.println(articleId);
		
		//删除评论
		commentDao.deleteComment(cId);
		
		//跳转
		request.getRequestDispatcher("comment?method=manageComment&articleId="+articleId).forward(request, response);
	}
	
	/**管理评论**/
	public void manageComment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//获取文章id
		int articleId = Integer.parseInt((String)request.getParameter("articleId"));
		
		//从数据库获取文章所有评论
		List<Comment> commentList = commentDao.getArticleComment(articleId);
		
		//存入请求作用域
		request.setAttribute("commentList", commentList);
		
		//跳转
		request.getRequestDispatcher("admin/comment_list.jsp").forward(request, response);
	}
	/**添加评论**/
	public void addComment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//获取文章id
		//int articleId = Integer.parseInt(request.getParameter("articleId"));
		
		//System.out.println(articleId);
		//从请求作用域里面获取评论信息
		Comment comment = new Comment();
		comment.setcContent((String)request.getParameter("content"));
		comment.setArticleId((Integer.parseInt((String)request.getParameter("articleId"))));
		comment.setcDate(new Date());
		comment.setcEmail((String)request.getParameter("email"));
		comment.setcNickname((String)request.getParameter("name"));
		
		
		//添加到数据库
		boolean flag = commentDao.addComment(comment);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		if(flag) out.print("1");
		else out.print("0");
	}
}
