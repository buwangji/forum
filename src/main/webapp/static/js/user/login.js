/**
 * Created by Administrator on 2016/12/16.
 */
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


    $("#logbtn").click(function () {
        $("#loginform").submit();
    });
    $("#loginform").validate({
        errorElement:"span",
        errorClass:"text-error",
        rules:{
            username:{
                required:true
            },
            password:{
                required:true
            }
        },
        messages:{
            username:{
                required:"请输入账号"
            },
            password:{
                required:"请输入密码"
            }
        },
        submitHandler:function (form) {
            $.ajax({
                url:"/login",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#logbtn").text("登录中...").attr("disabled","disabled");
                },
                success:function (data) {
                    if(data.state == 'success'){
                        alert("成功登录");
                        window.location.href = "/home";
                    }else{
                        alert(data.message);
                    }
                },
                error:function () {
                    alert("服务器错误");
                },
                complete:function () {
                    $("#logbtn").text("登录").removeAttr("disabled");
                }
            });
        }
    });
});