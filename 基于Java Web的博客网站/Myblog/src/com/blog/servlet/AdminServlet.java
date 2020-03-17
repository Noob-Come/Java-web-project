package com.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.blog.dao.AdminDao;
import com.blog.dao.AdminLoginLogDao;
import com.blog.entity.Admin;
import com.blog.entity.AdminLoginLog;

public class AdminServlet extends HttpServlet {
	
	private AdminDao adminDao=new AdminDao();
	private AdminLoginLogDao logDao=new AdminLoginLogDao(); 

	/**
	 * 处理管理员用户的请求
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String method=request.getParameter("method");
		if(method.equals("gotoLogin")) {
			gotoLogin(request,response);
		} else if(method.equals("login")) {
			login(request,response);
		} else if(method.equals("logout")) {
			request.getSession().invalidate();
			gotoLogin(request,response);
		}else if(method.equals("gotoReg")){
			gotoReg(request,response);
		}else if(method.equals("reg")){
			reg(request,response);	
		}
	}
	
	/**进入注册页面**/
	private void gotoReg(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException{
		//跳转到注册界面
		request.getRequestDispatcher("/admin/reg.jsp").forward(request, response);
	}
	/**进入登陆页面**/
	private void gotoLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//跳转至login.jsp
		request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
		
	}
	/**用户注册**/
	private void reg(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		//获取要注册用户信息
		String adminName = request.getParameter("adminName");
		String adminPass = request.getParameter("adminPass");
		String nickname= request.getParameter("nickname");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		//根据用户名查询数据库是否有以该用户名为名字的管理员用户
		Admin loginAdmin = adminDao.queryAdminByName(adminName);
		if(loginAdmin!=null) {
			out.print("0");
		}else  {
			Admin admin = new Admin();
			admin.setAdminName(adminName);
			admin.setAdminPass(adminPass);
			admin.setNickname(nickname);
			
			System.out.println(admin.getNickname());
			adminDao.addAdmin(admin);
			out.print("1");
		}
	}
	

	/**执行登录**/
	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取用户名、密码和验证码
		String adminName = request.getParameter("adminName");
		String adminPass = request.getParameter("adminPass");
		String vcode = request.getParameter("vcode");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//根据用户名查询管理员用户
		Admin loginAdmin = adminDao.queryAdminByName(adminName);
		if(loginAdmin==null) {
			out.print("0");
		}else if(!loginAdmin.getAdminPass().equals(adminPass)) {
			out.print("1");
		}else if(!request.getSession().getAttribute("sRandFont").equals(vcode)) {
			out.print("2");
		}else {
			//登录成功
			out.print("3");
			
			//1.将当前登录的博客管理员存入回话
			HttpSession session = request.getSession();
			session.setAttribute("loginAdmin", loginAdmin);
			
			//2.添加一条登录日志
			AdminLoginLog log = new AdminLoginLog();
			log.setAdminId(loginAdmin.getAdminId());
			log.setLoginIp(request.getRemoteAddr());
			log.setLoginDate(new Date());
			logDao.addLoginLog(log);
			
		}
	}
}
