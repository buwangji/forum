<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/15
  Time: 19:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>注册用户</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/dist/sweetalert.css">
</head>
<body>
<%@ include file="../include/navbar.jsp" %>
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i> 注册账号</span>
        </div>

        <form action="" class="form-horizontal" id="regform">
            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input type="text" name="username">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">密码</label>
                <div class="controls">
                    <input type="password" name="password" id="password" onKeyUp=pwStrength(this.value)
                           onBlur=pwStrength(this.value)>
                    <table class="hide">
                        <tr align="center" bgcolor="#f5f5f5">
                            <td width="33%" id="strength_L">弱</td>
                            <td width="33%" id="strength_M">中</td>
                            <td width="73px" id="strength_H">强</td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">重复密码</label>
                <div class="controls">
                    <input type="password" name="repassword">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">电子邮件</label>
                <div class="controls">
                    <input type="email" name="email">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">电话号码</label>
                <div class="controls">
                    <input type="text" name="phone">
                </div>
            </div>
            <div class="form-actions">
                <button class="btn btn-primary" id="regBtn">注册</button>
                <a href="/login">登录</a>
            </div>

        </form>

    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/user/reg.js"></script>
<script src="/static/js/dist/sweetalert.min.js"></script>
<script src="/static/js/user/password.js"></script>
</body>
</html>
