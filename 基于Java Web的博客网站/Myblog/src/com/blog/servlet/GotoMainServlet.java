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
		
		//��ȡ�Ự���еĵ�ǰ����Ա�û���Ϣ
		Admin loginAdmin = (Admin)request.getSession().getAttribute("loginAdmin");
		
		//��ѯ����Ա�ϴε�¼��Ϣ
		AdminLoginLog lastLoginLog = null;
		List<AdminLoginLog> recentLoginLog = logDao.getRecentLoginLogs(loginAdmin.getAdminId());
		if(recentLoginLog!=null&&recentLoginLog.size()==2) {
			lastLoginLog = recentLoginLog.get(1);
		}
		
		//������
		int articleCount = articleDao.getArticleCount(loginAdmin.getAdminId());
		
		//������
		int commentCount = commentDao.getCommentCount(loginAdmin.getAdminId());
		
		//��¼����
		int loginCount = logDao.getLoginCount(loginAdmin.getAdminId());
		
		//System.out.println(""+articleCount+commentCount+loginCount);
		
		//��ѯ�����������Ϣ
		String serverIp = request.getLocalAddr();
		int serverPort = request.getLocalPort();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String serverTime = sdf.format(new Date());
		
		//���浽����������
		request.setAttribute("lastLoginLog", lastLoginLog);
		request.setAttribute("articleCount", articleCount);
		request.setAttribute("commentCount", commentCount);
		request.setAttribute("loginCount", loginCount);
		request.setAttribute("serverIp", serverIp);
		request.setAttribute("serverPort", serverPort);
		request.setAttribute("serverTime", serverTime);
		request.setAttribute("loginIp", request.getRemoteAddr());
		
		//System.out.println((String)request.getAttribute("serverTime"));
		
		
		//��ת����ҳ��
		request.getRequestDispatcher("/admin/main.jsp").forward(request, response);
	}

}
