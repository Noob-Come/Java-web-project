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
    <script src="js/jquery.slim.min.js"></script>
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap4.0.min.js"></script>
    <script type="text/javascript" src="js/wangEditor.min.js"></script>
    <script type="text/javascript">
    $(document).ready(function(){
    	$("#addForm").submit(function(){
    		if($("#title").val()==""){
    			alert("文章标题不能为空！");
    			return false;
    		}
    		if($("#keywords").val()==""){
    			alert("关键字不能为空！");
    			return false;
	    	}
		    if($("#summary").val()==""){
			    alert("简介不能为空！");
			    return false;
			}
		    if($("#content").val()==""){
			    alert("内容不能为空！");
			    return false;
		    }    
		    return true;
	    });
    });
    </script>
</head>
<body>
<div style="position: relative;top: 10%">
<c:if test="${requestScope.msg eq '1' }">
    <div id="msg" class="info success">文章添加成功！</div>
</c:if>
<c:if test="${requestScope.msg eq '0' }">
    <div id="msg" class="info danger">文章添加失败！</div>
</c:if>
    
</div>
<div class="container">
    <form action="admin/article?method=add" method="post" id="addForm">
        <div class="form-group">
            <label for="title">文章标题</label>
            <input type="text" class="form-control" id="title" name="title" placeholder="文章标题">
        </div>
        <div class="form-group">
            <label for="catalogId">栏目</label>
            <select class="form-control" id="catalogId" name="catalogId">
            <c:forEach var="c" items="${requestScope.catalogs}">
                <option value="${c.catalogId }">${c.catalogName}</option>
            </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="keywords">关键字</label>
            <input type="text" class="form-control" id="keywords" name="keywords" placeholder="关键字">
        </div>
        <div class="form-group">
            <label for="desci">简介</label>
            <textarea class="form-control" id="desci" rows="3" id="summary" name="summary" placeholder="简介"></textarea>
        </div>
        <div class="form-group"  >
            <label for="div1">内容</label>
            <div id="div1"  >
            </div>
            <textarea id="content" name="content" style="display: none"      ></textarea>
        </div>
        <input type="submit" value="发表" />
    </form>

            <script type="text/javascript">
                var E = window.wangEditor
                var editor = new E('#div1')
                var $text1 = $('#content')
                editor.customConfig.onchange = function (html) {
                    // 监控变化，同步更新到 textarea
                    $text1.val(html)
                }
                editor.create()
                // 初始化 textarea 的值
                $text1.val(editor.txt.html())
            </script>
</div>
</body>
</html>