/**
 * Created by Administrator on 2016/12/17.
 */
$(function(){
    $("#resetBtn").click(function () {
        $("#resetForm").submit();
    });

    $("#resetForm").validate({
        errorElement:'span',
        errorClass:'text-error',
        rules:{
            password:{
                required:true,
                rangelength:[6,18]
            },
            repassword:{
                required:true,
                rangelength:[6,18],
                equalTo:"#password"
            }
        },
        messages:{
            password:{
                required:"请输入密码",
                rangelength:"密码长度6-18个字符"
            },
            repassword:{
                required:"请输入确认密码",
                rangelength:"密码长度6-18个字符",
                equalTo:"两次密码不一致"
            }
        },
        submitHandler:function(form){
            $.ajax({
                url:"/foundpassword/newpassword",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function(){
                    $("#resetBtn").text("保存中...").attr("disabled","disabled");
                },
                success:function(data){
                    if(data.state == 'success') {
                        alert("密码重置成功,请登录");
                        window.location.href = "/login";
                    } else {
                        //alert(data.message);
                        swal({
                            title: data.message,
                            //text: data.message,
                            imageUrl: "/static/img/error.png" });
                    }
                },
                error:function(){
                    //alert("服务器错误");
                    swal({
                        title:"服务器错误",
                        //text: data.message,
                        imageUrl: "/static/img/error.png" });
                },
                complete:function(){
                    $("#resetBtn").text("保存").removeAttr("disabled");
                }
            });

        }
    });
});