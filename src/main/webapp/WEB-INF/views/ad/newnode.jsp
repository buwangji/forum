<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/1/1
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <%--<link href="/static/js/dist/sweetalert.css" rel="stylesheet">--%>
</head>
<body>
<%@ include file="../include/adminNavber.jsp"%>
<div class="container-fluid" style="margin-top:20px">
    <form id="saveForm" action="">
        <legend>添加节点</legend>
        <label>节点名称</label>
        <input type="text" name="nodename">
        <div class="form-actions">
            <button id="saveBtn" class="btn btn-primary">保存</button>
        </div>
    </form>
</div>
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<%--<script src="/static/js/dist/sweetalert.min.js"></script>--%>
<script>
    $(function () {
        $("#saveBtn").click(function () {
            $("#saveForm").submit();
        });
        $("#saveForm").validate({
            errorElement:"span",
            errorClass:"text-error",
            rules:{
                nodename:{
                    required:true
                }
            },
            messages:{
                nodename:{
                    required:"请输入节点名称"
                }
            },
            submitHandler:function (form) {
                $.ajax({
                    url:"/ad/newnode",
                    type:'post',
                    data:$(form).serialize(),
                    success:function (json) {
                        if (json.state == "success"){
                           alert("成功!");
//                           window.location.href = "/ad/node";
                        }else{
                            alert(json.message);
                        }
                    },
                    error:function () {
                        alert("添加失败,服务器异常");
                    }
                })
            }
        })
    });

</script>
</body>
</html>

