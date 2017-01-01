<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/29
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>主题管理</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/js/dist/sweetalert.css" rel="stylesheet">
</head>
<body>
<%@ include file="../include/adminNavber.jsp"%>
<!--header-bar end-->
<div class="container-fluid" style="margin-top:20px">
    <table class="table">
        <thead>
        <tr>
            <th>名称</th>
            <th>发布人</th>
            <th>发布时间</th>
            <th>回复数量</th>
            <th>最后回复时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.items}" var="topic">
            <tr>
                <td>
                    <a href="/topicDetail?topicid=${topic.id}" target="_blank">${topic.title}</a>
                </td>
                <td>${topic.user.username}</td>
                <td>${topic.createtime}</td>
                <td>${topic.replynum}</td>
                <td>${topic.lastreplytime}</td>
                <td><a href="javascript:;" rel="${topic.id}" class="del">删除</a></td>
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
    $(function () {
        $("#pagination").twbsPagination({
            totalPages:${page.totalPage},
            visiblePages:5,
            first:'首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href: '?p={{number}}'
        });
        $(".del").click(function () {
            var id = $(this).attr("rel");
            swal({
                    title: "确定要删除该主题?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "确定",
                    closeOnConfirm: false },
                function(){
                    $.ajax({
                        url:"/ad/topic",
                        type:"post",
                        data:{"id":id},
                        success:function(data){
                            if(data == 'success') {
                                swal({title:"删除成功!"},function () {
                                    window.history.go(0);
                                });
                            } else {
                                swal(data);
                            }
                        },
                        error:function(){
                            swal("服务器异常,删除失败!");
                        }
                    });

                });
        })

    });


</script>
</body>
</html>
