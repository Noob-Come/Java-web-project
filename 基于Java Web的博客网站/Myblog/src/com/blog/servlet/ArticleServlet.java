package com.blog.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.dao.ArticleDao;
import com.blog.dao.CatalogDao;
import com.blog.dao.CommentDao;
import com.blog.entity.Admin;
import com.blog.entity.Article;
import com.blog.entity.Catalog;
import com.blog.entity.Comment;
import com.blog.entity.PageData;

public class ArticleServlet extends HttpServlet {

	private CatalogDao catalogDao = new CatalogDao();
	private ArticleDao articleDao = new ArticleDao();
	private CommentDao commentDao = new CommentDao();
	private PageData pageData = new PageData();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//��ȡ����
		String method = request.getParameter("method");
		if(method.equals("gotoAdd")) {
			gotoAdd(request,response);
		} else if(method.equals("add")) {
			add(request,response);
		} else if(method.equals("gotoEdit")) {
			gotoEdit(request,response);
		} else if(method.equals("edit")) {
			edit(request,response);
		} else if(method.equals("gotoDetail")) {
			gotoDetail(request,response);
		} else if(method.equals("gotoList")) {
			gotoList(request,response);
		} else if(method.equals("articleDel")) {
			articleDel(request,response);
		} else if(method.equals("search")) {
			search(request,response);
		}
	}

	/**��������**/
	private void search(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		String word = request.getParameter("word");
		
		//ģ������
		List<Article> articleList = articleDao.searchArticle(word);
		
		//���浽����������
		request.setAttribute("articleList", articleList);
		
		//��ת
		request.getRequestDispatcher("article_list.jsp").forward(request,response);
	}
	
	/**ִ��ɾ������**/
	private void articleDel(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		
		//��ȡ����ID
		int articleId = Integer.parseInt(request.getParameter("articleId"));
		
		//��ȡ���µ���������
		List<Comment> commentList = commentDao.getArticleComment(articleId);
		
		//��ɾ��������������
		for(Comment c:commentList) {
			commentDao.deleteComment(c.getcId());
		}
		
		//�ٴ����ݿ�ɾ������
		articleDao.deleteArticle(articleId);
		
		//��ת���û��������
		request.getRequestDispatcher("article?method=gotoList").forward(request,response);
		
	}
	
	/**׼�����������б�**/
	private void gotoList(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		
		///��ȡ�Ự�������еĵ�½����Ա�û�
		Admin loginAdmin=(Admin)request.getSession().getAttribute("loginAdmin");
		
		//���÷�ҳ����
		int maxCount = articleDao.getArticleCount(loginAdmin.getAdminId());
		pageData.setMaxCount(maxCount);

		//�����������currIndex
		String currIndex = request.getParameter("currIndex");
		if(currIndex !=null && !"".equals(currIndex)){
			pageData.setCurrIndex(Integer.parseInt(currIndex));//���õ�ǰҳ��
		}
		//���ݷ�ҳ��ѯ�����б�
		List<Article> articleList=new ArrayList<Article>();
		articleList = articleDao.getArticles(loginAdmin.getAdminId(),pageData);
		
		
		//System.out.println(maxCount);
		//��������������
		request.setAttribute("articleList", articleList);
		request.setAttribute("pageData", pageData);
		//System.out.println((String)request.getAttribute("articleList"));
		
		//����ҳ��
		request.getRequestDispatcher("article_list.jsp").forward(request, response);
	}
	
	/**׼������༭����**/
	private void gotoEdit(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		
		///��ѯ������Ϣ
		int articleId = Integer.parseInt(request.getParameter("articleId"));
		Article article=articleDao.getArticleById(articleId);

		//��������������
		request.setAttribute("article", article);
		
		//ҳ����ת
		request.getRequestDispatcher("article_edit.jsp").forward(request, response);
	}
	
	/**ִ�б༭����**/
	private void edit(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		//��ȡҪ��ӵ�������Ϣ
		int articleId=Integer.parseInt(request.getParameter("articleId"));
		Admin loginAdmin=(Admin)request.getSession().getAttribute("loginAdmin");
		int adminId=loginAdmin.getAdminId();
		int catalogId=Integer.parseInt(request.getParameter("catalogId"));
		
		String title=request.getParameter("title");
		String keywords=request.getParameter("keywords");
		String summary=request.getParameter("summary");
		String content=request.getParameter("content");
		
		Article article=new Article();
		article.setCatalogId(catalogId);
		article.setAdminId(adminId);
		article.setTitle(title);
		article.setKeywords(keywords);
		article.setSummary(summary);
		article.setContent(content);
		article.setPubDate(new Date());
		//�޸�����
		articleDao.alertArticle(article, articleId);
		//��ת�����¹���
		request.getRequestDispatcher("article?method=gotoList").forward(request,response);
		
	}
	
	/**ִ���������**/
	private void add(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		
		//��ȡҪ��ӵ�������Ϣ
		int catalogId=Integer.parseInt(request.getParameter("catalogId"));
		Admin loginAdmin=(Admin)request.getSession().getAttribute("loginAdmin");
		if(loginAdmin==null) {
			
		}
		int adminId=loginAdmin.getAdminId();
		
		String title=request.getParameter("title");
		String keywords=request.getParameter("keywords");
		String summary=request.getParameter("summary");
		String content=request.getParameter("content");
		
		Article article=new Article();
		article.setCatalogId(catalogId);
		article.setAdminId(adminId);
		article.setTitle(title);
		article.setKeywords(keywords);
		article.setSummary(summary);
		article.setContent(content);
		article.setPubDate(new Date());
		article.setClick(0);
		
		//System.out.println(article.getTitle()+"\n"+article.getSummary());
		
		//�������ݿⲢ���û�һ����Ӧ�������Ƿ���ӳɹ�
		boolean flag=articleDao.addArticle(article);
		request.setAttribute("msg", flag?"1":"0");
		gotoAdd(request,response);	
	
	}

	/**׼�������������**/
	private void gotoAdd(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		
		///��ѯ��Ŀ�б�
		List<Catalog> catalogs=catalogDao.getCatalogList();

		//��������������
		request.setAttribute("catalogs", catalogs);
		
		//ҳ����ת
		request.getRequestDispatcher("article_add.jsp").forward(request, response);
		
	}
	
	/**׼������鿴��������**/
	private void gotoDetail(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		
		//��ȡ����ID
		int articleId = Integer.parseInt(request.getParameter("articleId"));
		
		//�����ݿ��ȡ������Ϣ
		Article article=articleDao.getArticleById(articleId);
		
		//���浽������
		request.setAttribute("article", article);
		
		//ҳ����ת
		request.getRequestDispatcher("article_detail.jsp").forward(request, response);
		
	}

}
