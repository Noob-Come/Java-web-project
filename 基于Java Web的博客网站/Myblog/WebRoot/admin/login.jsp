<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>博客管理系统</title>
<link rel="shortcut icon" type="image/x-icon" href="img/web-icon.jpg" media="screen" />
<link rel="stylesheet" href="css/bootstrap.min.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/body.css"/> 
</head>
<body>
<div class="container">
	<section id="content">
		<form action="">
			<h1>博主登录</h1>
			<div>
				<input type="text" placeholder="账号" required="" id="adminName" />
			</div>
			<div>
				<input type="password" placeholder="密码" required="" id="adminPass" />
			</div>
			<div>
				<input type="text" placeholder="验证码" required="" id="vcode" />
			</div>
			<div>  <img style="margin:left" onclick="changeValiImage(this)" src="randImgs"  title="点击刷新验证码"  class="vcodeImg"></div>
			 <div class="">
				<span class="help-block u-errormessage" id="js-server-helpinfo">&nbsp;</span>			</div> 
			<div>
				<input type="button" value="登录" class="btn btn-primary" id="js-btn-login"/>
				<p style="text-align: right;color: red;position: absolute" id="info"></p>
				<a href="#">忘记密码?</a>
			</div>
		</form><!-- form -->
		 <div class="button">
			<span class="help-block u-errormessage" id="js-server-helpinfo">&nbsp;</span>
			<a href="Admin?method=gotoReg">点击注册</a>	
		</div> <!-- button -->
	</section><!-- content -->
</div>

<br><br><br><br>

 <script type="text/javascript">
 	$("#js-btn-login").click(function(){
 		if($("#adminName").val()=="") {
 			$("#info").text("账号不能为空！");
 		}else if($("#adminPass").val()=="") {
 			$("#info").text("密码不能为空！");
 		}else if($("#vcode").val()=="") {
 			$("#info").text("验证码为空！");
 		}
 		else {
 			//发送异步请求
 			$.post("Admin",
 					{"method":"login",
 					"adminName":$("#adminName").val(),
 					"adminPass":$("#adminPass").val(),
 					"vcode":$("#vcode").val()},
 					function(result) {
 						if(result=="0") {
 							$("#info").text("账号不存在！");
 						}else if(result=="1") {
 							$("#info").text("密码错误！");
 						}else if(result=="2") {
 							$("#info").text("验证码错误！");
 						}else {
 							//登录成功
 							$("#info").text("登录成功！");
 							window.location.href="<%=basePath%>admin/gotoMain";
 						}
 					});
 		}
 	});	
 </script>
 <script>
 	function changeValiImage(img) {
 		img.src = "randImgs?time=" + new Date().getTime();
 	}
</script>
</body>
</html>