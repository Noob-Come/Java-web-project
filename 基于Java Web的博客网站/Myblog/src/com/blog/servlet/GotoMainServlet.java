package com.blog.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.dao.AdminLoginLogDao;
import com.blog.dao.ArticleDao;
import com.blog.dao.CommentDao;
import com.blog.entity.Admin;
import com.blog.entity.AdminLoginLog;

public class GotoMainServlet extends HttpServlet {

	private AdminLoginLogDao logDao=new AdminLoginLogDao();
	private ArticleDao articleDao=new ArticleDao();
	private CommentDao commentDao=new CommentDao();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//获取会话域中的当前管理员用户信息
		Admin loginAdmin = (Admin)request.getSession().getAttribute("loginAdmin");
		
		//查询管理员上次登录信息
		AdminLoginLog lastLoginLog = null;
		List<AdminLoginLog> recentLoginLog = logDao.getRecentLoginLogs(loginAdmin.getAdminId());
		if(recentLoginLog!=null&&recentLoginLog.size()==2) {
			lastLoginLog = recentLoginLog.get(1);
		}
		
		//文章数
		int articleCount = articleDao.getArticleCount(loginAdmin.getAdminId());
		
		//评论数
		int commentCount = commentDao.getCommentCount(loginAdmin.getAdminId());
		
		//登录次数
		int loginCount = logDao.getLoginCount(loginAdmin.getAdminId());
		
		//System.out.println(""+articleCount+commentCount+loginCount);
		
		//查询服务器相关信息
		String serverIp = request.getLocalAddr();
		int serverPort = request.getLocalPort();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String serverTime = sdf.format(new Date());
		
		//保存到请求作用域
		request.setAttribute("lastLoginLog", lastLoginLog);
		request.setAttribute("articleCount", articleCount);
		request.setAttribute("commentCount", commentCount);
		request.setAttribute("loginCount", loginCount);
		request.setAttribute("serverIp", serverIp);
		request.setAttribute("serverPort", serverPort);
		request.setAttribute("serverTime", serverTime);
		request.setAttribute("loginIp", request.getRemoteAddr());
		
		//System.out.println((String)request.getAttribute("serverTime"));
		
		
		//跳转到主页面
		request.getRequestDispatcher("/admin/main.jsp").forward(request, response);
	}

}
