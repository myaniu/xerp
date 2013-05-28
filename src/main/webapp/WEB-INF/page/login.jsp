<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理平台</title>
<link href="${base}/res/css/login.css" rel="stylesheet" type="text/css" />
<script src="${base}/res/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">
$(function () {
	var $loginForm = $("#login_form");
	var $username = $("#username");
	var $password = $("#password");
	var $captcha = $("#captcha");
	var $isRememberUsername = $("#isRememberUsername");
	$loginForm.submit(function () {
		if ($username.val() == "") {
			alert("请输入您的用户名!");
			
			return false;
		}
		if ($password.val() == "") {
			alert("请输入您的密码!");
			
			return false;
		}
		if ($captcha.val() == "") {
			alert("请输入您的验证码!");
			
			return false;
		}
		$.post("${base}/system/login", {
			"name" : $username.val(),
			"passwd" : $password.val(),
			"remeberMe":true,
			"code":$captcha.val()
		}, function(data) {
			if (data.statusCode==200) {
				location.href='${base}/system/main';
			} else {
				alert( data.message);
				
			}
		}, "json");
		return false;
	});

	// 刷新验证码
	var $captchaImage = $("#captchaImage");
	$captchaImage.click( function() {
		var timestamp = (new Date()).valueOf();
		var imageSrc = $captchaImage.attr("src");
		if(imageSrc.indexOf("?") >= 0) {
			imageSrc = imageSrc.substring(0, imageSrc.indexOf("?"));
		}
		imageSrc = imageSrc + "?timestamp=" + timestamp;
		$captchaImage.attr("src", imageSrc);
	});
});
</script>
</head>

<body>
	<div id="login">
		<div id="login_header">
			
			<div class="login_headerContent">
				<div class="navList">
					<ul>
						<li><a href="#">设为首页</a></li>
					</ul>
				</div>
				<h2 class="login_title"><img src="${base}/res/images/login_title.png" /></h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form id="login_form" action="#" method="post" >
					<p>
						<label>用户名：</label>
						<input   type="text" id="username" name="name"  size="20" class="login_input" />
					</p>
					<p>
						<label>密码：</label>
						<input type="password" id="password" name="passwd" size="20" class="login_input" />
					</p>
					<p>
						<label>验证码：</label>
						 <input type="text" id="captcha" name="code"  size="5" />
						<span><img id="captchaImage"  src="${base}/security/captcha" alt="换一张"  width="75" height="24"/></span>
					</p>
					<div class="login_bar">
						<input class="sub" type="submit" value=" " />
					</div>
				</form>
			</div>
			<div class="login_banner"><img src="${base}/res/images/login_banner.jpg" /></div>
		
		</div>
		<div id="login_footer">
			Copyright &copy; 2009 www.dwzjs.com Inc. All Rights Reserved.
		</div>
	</div>
</body>
</html>