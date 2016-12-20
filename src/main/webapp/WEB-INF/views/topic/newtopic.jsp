<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>发布新主题</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
</head>
<body>
<%@include file="../include/navbar.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-plus"></i> 发布新主题</span>
        </div>

        <form action="" id="topicForm" style="padding: 20px">
            <label class="control-label">主题标题</label>
            <input name="title" id="title" type="text" style="width: 100%;box-sizing: border-box;height: 30px" placeholder="请输入主题标题，如果标题能够表达完整内容，则正文可以为空">
            <label class="control-label">正文</label>
            <textarea name="content" id="editor"></textarea>

            <select name="nodeid" id="nodeid" style="margin-top:15px;">
                <option value="">请选择节点</option>
                <c:forEach items="${nodeList}" var="node">
                    <option value="${node.id}">${node.nodename}</option>
                </c:forEach>
            </select>

        </form>
        <div class="form-actions" style="text-align: right">
            <button id="sendBtn"class="btn btn-primary">发布主题</button>
        </div>


    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script>
    $(function(){
        var editor = new Simditor({
            textarea: $('#editor')
            //optional options
        });
        $("#sendBtn").click(function () {
            $("#topicForm").submit();
        });
        $("#topicForm").validate({
            errorElement:"span",
            errorClass:"text-error",
            rules:{
                title:{
                    required:true,
                },
                nodeid:{
                    required:true,
                }
            },
            messages:{
                title:{
                    required:"请输入标题",
                },
                nodeid:{
                    required:"请选择节点",
                }
            },
            submitHandler:function () {
                $.ajax({
                    url:"/newTopic",
                    type:"post",
                    data:$("#topicForm").serialize(),
                    beforeSend:function(){
                        $("#sendBtn").text("发布中").attr("disabled","disabled");
                    },
                    success:function(json){
                        if(json.state == "success"){
                            window.location.href="/topicDetail?topicid="+json.data.id;
                        }else {
                            alert("新增主题异常");
                        }
                    },
                    error:function(){
                        alert("服务器异常");
                    },
                    complete:function () {
                        $("#sendBtn").text("发布主题").removeAttr("disabled");
                    }
                })
            }
        });


    });
</script>

</body>
</html>
