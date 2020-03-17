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
    <link rel="stylesheet" href="css/bootstrap4.0.min.css" >
    <link rel="shortcut icon" type="image/x-icon" href="img/web-icon.jpg" media="screen" />
    <script src="js/jquery.slim.min.js" ></script>
    <script src="js/popper.min.js" ></script>
    <script src="js/bootstrap4.0.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/ueditor.all.min.js"></script>
    <script type="text/javascript"  src="js/lang/zh-cn/zh-cn.js"></script>
    <!--  <script type="text/javascript">
    $(document).ready(function(){
    	$("#editForm").submit(function(){
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
    </script>-->
</head>
<body>
<div style="position: relative;top: 10%">
    
    
</div>
<div class="container">
    <form action="admin/article?method=edit&articleId=${ article.articleId }" method="post" id="editForm">
        <input type="hidden" value="${ article.articleId }" name="id">
        <div class="form-group">
            <label for="title">文章标题</label>
            <input type="text" class="form-control" id="title" name="title" placeholder="文章标题" value="${ article.title }">
        </div>
        <div class="form-group">
            <label for="catalogId">栏目</label>
            <select class="form-control" id="catalogId" name="catalogId">
                <option value="0" >学习</option>
                <option value="1" selected="selected">生活</option>
            </select>
        </div>
        <div class="form-group">
            <label for="keywords">关键字</label>
            <input type="text" class="form-control" id="keywords" name="keywords" placeholder="关键字" value="${ article.keywords }">
        </div>
        <div class="form-group">
            <label for="summary">简介</label>
            <textarea class="form-control" id="summary" rows="3" name="summary" placeholder="简介" value="${ article.summary }">${ article.summary }</textarea>
        </div>
        <div id="content" style="display: none"><p>${ article.content }</p>
        </div>
        <div class="form-group">
        	<label for="editor">内容</label>
            <script id="editor" type="text/plain"  name="content" style="width:1110px;height:500px;" >
            </script>
         </div>
         <input type="submit" value="修改"/>
         <input type="button" value="返回" onclick="window.location.href='admin/article_list.jsp';">
        </form>
       <script>
            $(function(){
                var ue = UE.getEditor('editor');
                ue.ready(function() {
                    ue.setContent($("#content").html());
                });
            });
       </script>       
</div>
</body>
</html>