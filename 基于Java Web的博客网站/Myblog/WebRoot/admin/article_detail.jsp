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
    <link rel="shortcut icon" type="image/x-icon" href="img/web-icon.jpg" media="screen" />
    <link rel="stylesheet" href="css/bootstrap4.0.min.css" >
    <script src="js/jquery.slim.min.js" ></script>
    <script src="js/popper.min.js" ></script>
    <script src="js/bootstrap4.0.min.js"></script>
</head>
<body>
<table class="table table-striped table-sm" >

    <tr class="table-active">
        <th width="15%">ID</th>
        <td >${ article.articleId }</td>
    </tr>
    <tr class="table-secondary">
        <th>标题</th>
        <td>${ article.title }</td>
    </tr>
    <tr class="table-success">
        <th>关键字</th>
        <td>${ article.keywords }</td>
    </tr>
    <tr class="table-danger">
        <th>简介</th>
        <td>${ article.summary }</td>
    </tr>
    <tr class="table-warning">
        <th>发表时间</th>
        <td>${ article.localpubDate }</td>
    </tr>
    <tr class="table-info">
        <th>点击量</th>
        <td>${ article.click }</td>
    </tr>
    <tr class="table-light">
        <th>内容</th>
        <td>${ article.content }</td>
        <!-- <p><img src="img/chunjie.jpg" style="font-size: 1rem; max-width: 100%;">&nbsp;<br></p> -->
    </tr>
</table>
</body>
</html>