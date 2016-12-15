<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0" />
    <title>项目管理系统</title>

    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css" />

    <style type="text/css">
        html,body {
            height: 100%;
        }
        .box {
            filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#6699FF', endColorstr='#6699FF'); /*  IE */
            background-image:linear-gradient(bottom, #6699FF 0%, #6699FF 100%);
            background-image:-o-linear-gradient(bottom, #6699FF 0%, #6699FF 100%);
            background-image:-moz-linear-gradient(bottom, #6699FF 0%, #6699FF 100%);
            background-image:-webkit-linear-gradient(bottom, #6699FF 0%, #6699FF 100%);
            background-image:-ms-linear-gradient(bottom, #6699FF 0%, #6699FF 100%);

            margin: 0 auto;
            position: relative;
            width: 100%;
            height: 100%;
        }
        .login-box {
            width: 100%;
            max-width:500px;
            height: 400px;
            position: absolute;
            top: 50%;

            margin-top: -200px;
            /*设置负值，为要定位子盒子的一半高度*/

        }
        @media screen and (min-width:500px){
            .login-box {
                left: 50%;
                /*设置负值，为要定位子盒子的一半宽度*/
                margin-left: -250px;
            }
        }

        .form {
            width: 100%;
            max-width:500px;
            height: 275px;
            margin: 25px auto 0px auto;
            padding-top: 25px;
        }
        .login-content {
            height: 300px;
            width: 100%;
            max-width:500px;
            background-color: rgba(255, 250, 2550, .6);
            float: left;
        }


        .input-group {
            margin: 0px 0px 30px 0px !important;
        }
        .form-control,
        .input-group {
            height: 40px;
        }

        .form-group {
            margin-bottom: 0px !important;
        }
        .login-title {
            padding: 20px 10px;
            background-color: rgba(0, 0, 0, .6);
        }
        .login-title h1 {
            margin-top: 10px !important;
        }
        .login-title small {
            color: #fff;
        }

        .link p {
            line-height: 20px;
            margin-top: 30px;
        }
        .btn-sm {
            padding: 8px 24px !important;
            font-size: 16px !important;
        }
    </style>

</head>

<body>
<div class="box">
    <div class="login-box">
        <div class="login-title text-center">
            <h1><small>登录</small></h1>
        </div>
        <div class="login-content ">
            <div class="form">
                <form id ="login" name="login" method="post" >
                    <div id="info"></div>
                    <tr>
                        <td width="150" height="29" align="center"></td>
                        <td width="*" id="log" style="color: #FF0000"></td>
                    </tr>
                    <div class="form-group">
                        <div class="col-xs-12  ">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                                <input type="text" id="username" name="username" class="form-control" placeholder="用户名">
                                <span  id="msg1"  style="color: #ff0000"></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12  ">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                                <input type="text" id="password" name="password" class="form-control" placeholder="密码">
                                <span  id="msg2"  style="color: #ff0000"></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group form-actions"id="login">
                        <div class="col-xs-4 col-xs-offset-4 ">
                            <button  id = "login"  onclick="login()"  class="btn btn-sm btn-info" ><span class="glyphicon glyphicon-off" ></span> 登录</button>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-6 link">
                            <p class="text-center remove-margin"><small>忘记密码？</small> <a href="javascript:void(0)" ><small>找回</small></a>
                            </p>
                        </div>
                        <div class="col-xs-6 link">
                            <p class="text-center remove-margin"><small>还没注册?</small> <a href="javascript:void(0)" ><small>注册</small></a>
                            </p>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>

<script src="/resources/js/jquery.js" type="text/javascript"></script>
<script src="/resources/js/jquery.validate.js" type="text/javascript"></script>
<script src="/resources/js/jquery.form.js" type="text/javascript"></script>

<script type="text/javascript">

    $(document).ready(function check() {
        //判断用户名是否为空
        $("username").blur("click",function () {
            var username = $("#username").val();//获取用户名文本框的值
            if (username == null || username == "") {//验证是否为空
                $("#msg1").html("Username cannot be empty");
            } else {
                $("#msg1").html("");
            }
        });

        //判断密码是否为空
        $("#password").blur("click",function () {//绑定文本框失去焦点事件
            var password =$("#password").val();//获取密码文本框的值
            if(password == null || password == ""){//验证是否为空
                $("#msg2").html("Password cannot be empty");
            }else{
                $("#msg2").html("");
            }
        });
    });

     function login() {
         $(function () {
             $.post("login.do",
                     {  username:$("#username").val(),
                         password:$("#password").val(),
                         function(data){
                             $("#info").replaceWith('<div id="info">'+ data + '</div>');
                         });

               });
     }
</script>

</html>