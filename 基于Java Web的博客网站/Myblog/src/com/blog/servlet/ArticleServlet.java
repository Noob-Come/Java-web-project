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
		//获取方法
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

	/**搜索文章**/
	private void search(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		String word = request.getParameter("word");
		
		//模糊搜索
		List<Article> articleList = articleDao.searchArticle(word);
		
		//保存到请求作用域
		request.setAttribute("articleList", articleList);
		
		//跳转
		request.getRequestDispatcher("article_list.jsp").forward(request,response);
	}
	
	/**执行删除文章**/
	private void articleDel(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		
		//获取文章ID
		int articleId = Integer.parseInt(request.getParameter("articleId"));
		
		//获取文章的所有评论
		List<Comment> commentList = commentDao.getArticleComment(articleId);
		
		//先删除文章所有评论
		for(Comment c:commentList) {
			commentDao.deleteComment(c.getcId());
		}
		
		//再从数据库删除文章
		articleDao.deleteArticle(articleId);
		
		//跳转到用户管理界面
		request.getRequestDispatcher("article?method=gotoList").forward(request,response);
		
	}
	
	/**准备进入文章列表**/
	private void gotoList(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		
		///获取会话作用域中的登陆管理员用户
		Admin loginAdmin=(Admin)request.getSession().getAttribute("loginAdmin");
		
		//设置分页对象
		int maxCount = articleDao.getArticleCount(loginAdmin.getAdminId());
		pageData.setMaxCount(maxCount);

		//接受请求参数currIndex
		String currIndex = request.getParameter("currIndex");
		if(currIndex !=null && !"".equals(currIndex)){
			pageData.setCurrIndex(Integer.parseInt(currIndex));//设置当前页码
		}
		//根据分页查询文章列表
		List<Article> articleList=new ArrayList<Article>();
		articleList = articleDao.getArticles(loginAdmin.getAdminId(),pageData);
		
		
		//System.out.println(maxCount);
		//存入请求作用域
		request.setAttribute("articleList", articleList);
		request.setAttribute("pageData", pageData);
		//System.out.println((String)request.getAttribute("articleList"));
		
		//进入页面
		request.getRequestDispatcher("article_list.jsp").forward(request, response);
	}
	
	/**准备进入编辑文章**/
	private void gotoEdit(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		
		///查询文章信息
		int articleId = Integer.parseInt(request.getParameter("articleId"));
		Article article=articleDao.getArticleById(articleId);

		//存入请求作用域
		request.setAttribute("article", article);
		
		//页面跳转
		request.getRequestDispatcher("article_edit.jsp").forward(request, response);
	}
	
	/**执行编辑文章**/
	private void edit(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		//获取要添加的文章信息
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
		//修改文章
		articleDao.alertArticle(article, articleId);
		//跳转到文章管理
		request.getRequestDispatcher("article?method=gotoList").forward(request,response);
		
	}
	
	/**执行添加文章**/
	private void add(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		
		//获取要添加的文章信息
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
		
		//存入数据库并给用户一个响应，文章是否添加成功
		boolean flag=articleDao.addArticle(article);
		request.setAttribute("msg", flag?"1":"0");
		gotoAdd(request,response);	
	
	}

	/**准备进入添加文章**/
	private void gotoAdd(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		
		///查询栏目列表
		List<Catalog> catalogs=catalogDao.getCatalogList();

		//存入请求作用域
		request.setAttribute("catalogs", catalogs);
		
		//页面跳转
		request.getRequestDispatcher("article_add.jsp").forward(request, response);
		
	}
	
	/**准备进入查看文章详情**/
	private void gotoDetail(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		
		//获取文章ID
		int articleId = Integer.parseInt(request.getParameter("articleId"));
		
		//从数据库获取文章信息
		Article article=articleDao.getArticleById(articleId);
		
		//保存到作用域
		request.setAttribute("article", article);
		
		//页面跳转
		request.getRequestDispatcher("article_detail.jsp").forward(request, response);
		
	}

}
