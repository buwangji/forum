<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/1/1
  Time: 21:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<script>
</script>
<body>
<%@ include file="../include/adminNavber.jsp"%>
<!--header-bar end-->
<div class="container-fluid" style="margin-top:20px">
    <table class="table">
        <thead>
        <tr>
            <th>账号</th>
            <th>注册时间</th>
            <th>最后登录时间</th>
            <th>最后登录IP</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.items}" var="user">
            <tr>
                <td>${user.username}</td>
                <td>${user.createtime}</td>
                <td>${user.loginLog.loginTime}</td>
                <td>${user.loginLog.ip}</td>
                <td>
                    <a href="javascript:;" class="update" onClick="update(${user.id},${user.state})"
                       rel="${user.state},${user.id}">${user.state == '1'?'禁用':'恢复'}</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagination pagination-mini pagination-centered">
        <ul id="pagination" style="margin-bottom:20px;"></ul>
    </div>
</div>
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script src="/static/js/dist/sweetalert.min.js"></script>
<script>
    $(function(){
        $("#pagination").twbsPagination({
            totalPages:${page.totalPage},
            visiblePages:5,
            first:'首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href: '?p={{number}}'
        });
    });
    function update(userid,userState){
        $.post("/ad/user",{"userid":userid,"userState":userState},function(json){
            if(json.state=='success'){
                alert("修改成功");
                window.history.go(0);
            }else{
                alert(json.message)
            }
        });
    }

</script>
</body>
</html>
