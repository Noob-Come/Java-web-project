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
	 * �������Ա�û�������
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
	
	/**����ע��ҳ��**/
	private void gotoReg(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException{
		//��ת��ע�����
		request.getRequestDispatcher("/admin/reg.jsp").forward(request, response);
	}
	/**�����½ҳ��**/
	private void gotoLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��ת��login.jsp
		request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
		
	}
	/**�û�ע��**/
	private void reg(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		//��ȡҪע���û���Ϣ
		String adminName = request.getParameter("adminName");
		String adminPass = request.getParameter("adminPass");
		String nickname= request.getParameter("nickname");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		//�����û�����ѯ���ݿ��Ƿ����Ը��û���Ϊ���ֵĹ���Ա�û�
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
	

	/**ִ�е�¼**/
	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��ȡ�û������������֤��
		String adminName = request.getParameter("adminName");
		String adminPass = request.getParameter("adminPass");
		String vcode = request.getParameter("vcode");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//�����û�����ѯ����Ա�û�
		Admin loginAdmin = adminDao.queryAdminByName(adminName);
		if(loginAdmin==null) {
			out.print("0");
		}else if(!loginAdmin.getAdminPass().equals(adminPass)) {
			out.print("1");
		}else if(!request.getSession().getAttribute("sRandFont").equals(vcode)) {
			out.print("2");
		}else {
			//��¼�ɹ�
			out.print("3");
			
			//1.����ǰ��¼�Ĳ��͹���Ա����ػ�
			HttpSession session = request.getSession();
			session.setAttribute("loginAdmin", loginAdmin);
			
			//2.���һ����¼��־
			AdminLoginLog log = new AdminLoginLog();
			log.setAdminId(loginAdmin.getAdminId());
			log.setLoginIp(request.getRemoteAddr());
			log.setLoginDate(new Date());
			logDao.addLoginLog(log);
			
		}
	}
}
