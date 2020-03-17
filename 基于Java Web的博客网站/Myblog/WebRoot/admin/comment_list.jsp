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
<div style="position: relative;top: 10%">
</div>
<div class="container">
    <table class="table">
        <thead class="thead-default">
        <tr>
            <th>流水号</th>
            <th>评论内容</th>
            <th>日期</th>
            <th>昵称</th>
            <th>邮箱</th>
            <th>删除</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="comment" items="${ requestScope.commentList }" varStatus="vs">
	        <tr>
	            <th scope="row">${ comment.cId }</th>
	            <td>${ comment.cContent }</td>
	            <td>${ comment.cDate }</td>
	            <td>${ comment.cNickname }</td>
	            <td>${ comment.cEmail }</td>
	            <td><button type="button" class="btn btn-outline-danger btn-sm" onclick="ifdelete('${ comment.cId }')">删除</button></td>
	        </tr>
        </c:forEach>
        </tbody>
    </table>
    <script>
            function ifdelete(id) {
                layer.confirm('确定删除该评论吗?', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                	window.location.href="<%=basePath%>comment?method=commentDel&cId="+id;//删除评论的servlet请求
                }, function(){
                	
	            });
            }
    </script>
</div>
</body>
</html>