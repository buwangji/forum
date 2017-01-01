<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/29
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar navbar-inverse  navbar-static-top">
    <div class="navbar-inner">
        <a class="brand" href="/ad/home?_=0">论坛管理系统</a>
        <ul class="nav">
            <li class="${param._=='0'?'active':''}"><a href="/ad/home?_=0">首页</a></li>
            <li class="${param._=='1'?'active':''}"><a href="/ad/topic?_=1">主题管理</a></li>
            <li class="${param._=='2'?'active':''}"><a href="/ad/node?_=2">节点管理</a></li>
            <li class="${param._=='3'?'active':''}"><a href="/ad/user?_=3">用户管理</a></li>
        </ul>
        <ul class="nav pull-right">
            <li><a href="/ad/loginout">安全退出</a></li>
        </ul>
    </div>

</div>
<!--header-bar end-->

