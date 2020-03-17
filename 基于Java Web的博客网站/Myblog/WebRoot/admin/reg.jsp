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
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/style.css" />
	<link rel="stylesheet" type="text/css" href="css/body.css"/> 
	
    <style>
    
     .high{ color: red; }
     .msg{ font-size: 13px; }
     .onError{ color: red; }
     .onSuccess{ color: green; }
    
    
        #myCarousel{
            margin-left: 2%;
            width: 900px;
            height: 80%;
            float: left;
            z-index: 999;
            display: inline;
        }
        #login{
            float: left;
            height: 250px;
            width: 330px;
            margin-left: 6%;
            margin-top: 9%;
            display: inline;
            z-index: 999;
        }
        body{
            padding:0;
            margin:0;
        }
    </style>
    <script>
        $(function(){
            $('#myCarousel').carousel({
                interval: 2000
            })
        });
    </script>
</head>
<body>

<div class="container" >
	<section id="content">
    	<h1>博主注册</h1>
    	 <div  id="reg">
		   <div class="form-inline"  >
		       <div class="input-group">
		           <span class="input-group-addon">账号</span>
		           <input type="text" class="form-control" name="adminName" id="adminName">
		       </div><br/><br/>
		       <div class="input-group">
		           <span class="input-group-addon">密码</span>
		           <input type="password" class="form-control" name="adminPass" id="adminPass">
		       </div><br/><br/>
		       <div class="input-group">
		           <span class="input-group-addon">昵称</span>
		           <input type="text" class="form-control" name="nickname" id="nickname">
		       </div><br/><br/>
		       <p style="text-align: right;color: red;position: absolute" id="info"></p>
		       <br/>
		       <button id="loginButton"  class="btn btn-primary" >提交</button>
		   </div>
 		</div>
	</section><!-- content -->
</div>

<br><br><br><br>
<script type="text/javascript">
 	$("#loginButton").click(function(){
 		if($("#adminName").val()=="") {
 			$("#info").text("账号不能为空！");
 		}
 		else if($("#adminPass").val()=="") {
 			$("#info").text("密码不能为空！");
 		}
 		else if($("#adminNickname").val()=="") {
 			$("#info").text("昵称不能为空！");
 		}
 		else {
 			//发送异步请求
 			$.post("Admin",
 					{"method":"reg",
 					"adminName":$("#adminName").val(),
 					"adminPass":$("#adminPass").val(),
 					"nickname":$("#nickname").val()},
 					function(result) {
 						if(result=="0") {
 							$("#info").text("账号已经存在！");
 						}
 						else {
 							//注册成功
 							$("#info").text("注册成功！");
 							window.location.href="<%=basePath%>index";
 						}
 					});
 		}
 	});
 	</script>	
  </body>
</html>
