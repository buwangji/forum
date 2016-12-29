<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/dist/sweetalert.css">
</head>
<body>
<div class="header-bar">
    <div class="container">
        <a href="#" class="brand">
            <i class="fa fa-reddit-alien"></i>
        </a>
    </div>
</div>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i> 管理员登录</span>
        </div>

        <form id="loginform" action="" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input type="text" name="adminName">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">密码</label>
                <div class="controls">
                    <input type="password" name="password">
                </div>
            </div>

            <div class="form-actions">
                <button id="logbtn" class="btn btn-primary">登录</button>
            </div>

        </form>
    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/dist/sweetalert.min.js"></script>
<script>
    $(function () {
        function getParameterByName(name, url) {
            if (!url) {
                url = window.location.href;
            }
            name = name.replace(/[\[\]]/g, "\\$&");
            var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
                results = regex.exec(url);
            if (!results) return null;
            if (!results[2]) return '';
            return decodeURIComponent(results[2].replace(/\+/g, " "));
        }
        $("#password").keydown(function () {
            if(event.keyCode == '13'){
                $("#logbtn").click();
            }
        });


        $("#logbtn").click(function () {
            $("#loginform").submit();
        });
        $("#loginform").validate({
            errorElement:"span",
            errorClass:"text-error",
            rules:{
                adminName:{
                    required:true
                },
                password:{
                    required:true
                }
            },
            messages:{
                adminName:{
                    required:"请输入账号"
                },
                password:{
                    required:"请输入密码"
                }
            },
            submitHandler:function (form) {
                $.ajax({
                    url:"/ad/login",
                    type:"post",
                    data:$(form).serialize(),
                    beforeSend:function () {
                        $("#logbtn").text("登录中...").attr("disabled","disabled");
                    },
                    success:function (data) {
                        if(data.state == 'success'){
                            var url = getParameterByName("redirect");
                            if(url){
                                var hash = location.hash;
                                if(hash){
                                    window.location.href = url + hash;
                                }else{
                                    window.location.href = url;
                                }
                            }else{
                                window.location.href = "/ad/home";
                            }
                        }else{
                            //alert(data.message);
                            swal({
                                title: data.message,
                                //text: data.message,
                                imageUrl: "/static/img/error.png" });
                        }
                    },
                    error:function () {
                        swal({
                            title: "服务器错误",
                            //text: "服务器错误",
                            imageUrl: "/static/img/error.png" });
                    },
                    complete:function () {
                        $("#logbtn").text("登录").removeAttr("disabled");
                    }
                });
            }
        });
    });
</script>
</body>
</html>

