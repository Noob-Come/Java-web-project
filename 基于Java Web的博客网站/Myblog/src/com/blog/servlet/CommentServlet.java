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

	/**ɾ������**/
	public void commentDel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��ȡ����id
		int cId = Integer.parseInt(request.getParameter("cId"));
		
		//��ȡ���۵�����ID
		int articleId = commentDao.getCommentById(cId).getArticleId();
		
		System.out.println(articleId);
		
		//ɾ������
		commentDao.deleteComment(cId);
		
		//��ת
		request.getRequestDispatcher("comment?method=manageComment&articleId="+articleId).forward(request, response);
	}
	
	/**��������**/
	public void manageComment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//��ȡ����id
		int articleId = Integer.parseInt((String)request.getParameter("articleId"));
		
		//�����ݿ��ȡ������������
		List<Comment> commentList = commentDao.getArticleComment(articleId);
		
		//��������������
		request.setAttribute("commentList", commentList);
		
		//��ת
		request.getRequestDispatcher("admin/comment_list.jsp").forward(request, response);
	}
	/**�������**/
	public void addComment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//��ȡ����id
		//int articleId = Integer.parseInt(request.getParameter("articleId"));
		
		//System.out.println(articleId);
		//�����������������ȡ������Ϣ
		Comment comment = new Comment();
		comment.setcContent((String)request.getParameter("content"));
		comment.setArticleId((Integer.parseInt((String)request.getParameter("articleId"))));
		comment.setcDate(new Date());
		comment.setcEmail((String)request.getParameter("email"));
		comment.setcNickname((String)request.getParameter("name"));
		
		
		//��ӵ����ݿ�
		boolean flag = commentDao.addComment(comment);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		if(flag) out.print("1");
		else out.print("0");
	}
}
