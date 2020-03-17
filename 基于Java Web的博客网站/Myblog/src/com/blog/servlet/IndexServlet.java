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
	/*��ҳ�����Servlet**/
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//��ȡ�������������÷�ҳ����
		int maxCount = articleDao.getArticleCount();
		pageData.setMaxCount(maxCount);
		pageData.setPageSize(5);
		
		//�����������currIndex
		String currIndex = request.getParameter("currIndex");
		if(currIndex !=null && !"".equals(currIndex)){
			pageData.setCurrIndex(Integer.parseInt(currIndex));//���õ�ǰҳ��
		}
		
		//���ݷ�ҳʵ���ȡ����
		List<Article> articleList = articleDao.getArticles(pageData);
		
		//��������������
		request.setAttribute("articleList", articleList);
		request.setAttribute("pageData", pageData);
		
		//������ҳ
		request.getRequestDispatcher("index.jsp").forward(request, response);
		
		
	}

}
