/**
 * Created by Administrator on 2016/12/17.
 */
$(function () {
    $("#type").change(function () {
        var value = $(this).val();

        if("email" == value){
            $("#typename").text("电子邮件");
        }else if("phone" == value){
            $("#typename").text("手机号码");
        }
    });
    $("#btn").click(function () {
        $("#form").submit();
    });
    $("#form").validate({
        errorElement:"span",
        errorClass:"text-error",
        rules:{
            value:{
                required:true
            }
        },
        messages:{
            value:{
                required:"请输入有效的电子邮件或电话号码"
            }
        },
        submitHandler:function (form) {
            $.ajax({
                url:"/foundPassword",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#btn").text("提交中...").attr("disabled","disabled");
                },
                success:function (data) {
                    if(data.state == 'success'){
                        var type = $("#type").val();
                        if("email" == type) {
                            alert("请查收邮件进行操作");
                        } else {
                            //TODO 电话的提示
                        }
                    } else{
                        //alert(data.message);
                        swal({
                            title:data.message,
                            //text: data.message,
                            imageUrl: "/static/img/error.png" });
                    }
                },
                error:function () {
                    //alert("服务器错误");
                    swal({
                        title:"服务器错误",
                        //text: data.message,
                        imageUrl: "/static/img/error.png" });
                },
                complete:function () {
                    $("#btn").text("提交").removeAttr("disabled");
                }


            });
        }


    });

});