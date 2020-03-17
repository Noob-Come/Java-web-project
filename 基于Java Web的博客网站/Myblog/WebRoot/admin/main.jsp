<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
</head>
<body background="img/cloud.jpg">
<%@ include file="nav.jsp" %>
<div class="card mb-3">
    <div style="height: 180px;overflow: hidden">
        <img class="card-img-top" src="img/cloud.jpg" alt="Card image cap" style="height: 100%;width:100%;">
    </div>

    <div class="card-body" style="background-color:#D0E9C6">
        <h4 class="card-title">${sessionScope.loginAdmin.nickname}</h4>
        <p class="card-text"><small class="text-muted">上次登录时间:${ requestScope.lastLoginLog.loginDate }</small></p>
        <p class="card-text"><small class="text-muted">上次登录IP:${ requestScope.lastLoginLog.loginIp }</small></p>
        <p class="card-text"><small class="text-muted">本次登录IP:${ requestScope.loginIp }</small></p>
    </div>
</div>
<div >
    <table class="table table-hover">
        <p class="text-success" style="text-align: center"> 系统统计</p>
        <thead>
        <tr >
            <th>#</th>
            <th>文章数</th>
            <th>评论数</th>
            <th>登陆次数</th>
        </tr>
        </thead>
        <tbody>
        <tr class="table-success">
            <th scope="row">全部</th>
            <td>${ requestScope.articleCount }</td>
            <td>${ requestScope.commentCount }</td>
            <td>${ requestScope.loginCount }</td>
        </tr>
        </tbody>
    </table>
</div>

<div style="width: 50%;position: relative;left: 25%">
    <table class="table table-sm" >
        <p class="text-success" style="text-align: center"> 系统信息</p>

        <tr>
            <th scope="row">服务器IP</th>
            <td>${ requestScope.serverIp }</td>
        </tr>
        <tr>
            <th scope="row">服务器端口</th>
            <td>${ requestScope.serverPort }</td>
        </tr>
        <tr>
            <th scope="row">服务器当前时间</th>
            <td>${ requestScope.serverTime }</td>
        </tr>

    </table>
</div>
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
</script>
</body>
</html>