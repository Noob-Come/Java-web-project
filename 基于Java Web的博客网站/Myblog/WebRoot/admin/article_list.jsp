<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh">
<head>
	<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>博客管理系统</title>
    <link rel="shortcut icon" type="image/x-icon" href="img/web-icon.jpg" media="screen" />
    <link rel="stylesheet" href="css/bootstrap4.0.min.css" >
    <script src="js/jquery.slim.min.js" ></script>
    <script src="js/popper.min.js" ></script>
    <script src="js/bootstrap4.0.min.js"></script>
    <script src="js/layer.js"></script>
    <script src="js/jquery-3.2.1.min.js"></script>
</head>
<body>
<%@ include file="nav.jsp" %>
<br/>
<table class="table table-sm table-hover">
    <thead>
    <tr class="table-info">
        <th>序号</th>
        <th width="30%">标题</th>
        <th>发表时间</th>
        <th>点击量</th>
        <th>详情</th>
        <th>评论</th>
        <th>编辑</th>
        <th>删除</th>
    </tr>
    </thead>
    <tbody>
	    <c:forEach var="article" items="${ requestScope.articleList }" varStatus="vs">
	    <tr>
	        <th scope="row">${ vs.count+(pageData.currIndex-1)*pageData.pageSize }</th>
	        <td>${ article.title }</td>
	        <td>${ article.localpubDate }</td>
	        <td>${ article.click }</td>
	        <td><button type="button" class="btn btn-outline-info btn-sm" onclick="fullScreen('《${ article.title }》','admin/article?method=gotoDetail&articleId=${ article.articleId }')">详情</button></td>
	        <td><button type="button" class="btn btn-outline-success btn-sm" onclick="fullScreen('《${ article.title }》|评论管理','comment?method=manageComment&articleId=${ article.articleId }')">评论</button></td>
	        <td><button type="button" class="btn btn-outline-primary btn-sm" onclick="fullScreen('《${ article.title }》|编辑','admin/article?method=gotoEdit&articleId=${ article.articleId }')">编辑</button>&nbsp;&nbsp;</td>
	        <td><button type="button" class="btn btn-outline-danger btn-sm" onclick="ifdelete('${article.articleId}','${article.title}')">删除</button></td>
	    </tr>
	    </c:forEach>
    </tbody>
</table>
<nav aria-label="Page navigation example" style="position: absolute;bottom: 10px;left: 42%">
    <ul class="pagination justify-content-center">
        <li class="page-item  <c:if test="${ pageData.currIndex eq 1 }"> disabled</c:if>">
            <a class="page-link" href="admin/article?method=gotoList&currIndex=1" >&laquo;</a>
        </li>
        <c:forEach var="p" begin="1" end="${pageData.sumPages }">
            <li class="page-item  <c:if test="${p eq pageData.currIndex }">active</c:if>">
            	<a class="page-link" href="admin/article?method=gotoList&currIndex=${ p }">${ p }</a>
            </li>
        </c:forEach>
        <li class="page-item <c:if test="${pageData.currIndex eq pageData.sumPages }"> disabled</c:if> ">
        	<a class="page-link" href="admin/article?method=gotoList&currIndex=${ pageData.sumPages }" >&raquo;</a>
        </li>
    </ul>
</nav>

<script>
    function fullScreen(title,url){
        var index = layer.open({
            type: 2,
            title: title,
            area: ['70%', '70%'],
            content: url,
            maxmin: true
        });
        layer.full(index);
    }
   
   function ifdelete(id,title) {
        layer.confirm('确定删除该文章吗?', {
            btn: ['确定','取消'] //按钮
        	}, function(){
            	window.location.href="<%=basePath%>admin/article?method=articleDel&articleId="+id;//删除文章的servlet请求
            }, function(){
            	
            });
    }
    
</script>
</body>
</html>