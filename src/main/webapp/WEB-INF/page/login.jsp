<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="./includes.jsp"%>

<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
	  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

<!-- The fav icon -->
<link rel="shortcut icon" href="${base}/res/img/favicon.ico">
<script type="text/javascript">
	$(document).ready(function() {
		
		var $loginForm = $("#login_form");
		$loginForm.submit(function () {
			$.post("${base}/system/login", {
				"name" :  $("#username").val(),
				"passwd" : $("#password").val(),
				"remeberMe":true,
				"code":$("#captcha").val()
			}, function(data) {
				if (data.statusCode==200) {
					location.href='${base}/system/main';
				} else {
					$.dialog({type: "error", content: data.message, modal: true, autoCloseTime: 3000});
				}
			}, "json");
			return false;
		});
		
		
		var $captchaImage = $("#captchaImage");
		$captchaImage.click(function() {
			var timestamp = (new Date()).valueOf();
			var imageSrc = $captchaImage.attr("src");
			if (imageSrc.indexOf("?") >= 0) {
				imageSrc = imageSrc.substring(0, imageSrc.indexOf("?"));
			}
			imageSrc = imageSrc + "?timestamp=" + timestamp;
			$captchaImage.attr("src", imageSrc);
		});
		$('form').validate();
		//other things to do on document ready, seperated for ajax calls

	});
</script>

</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">

			<div class="row-fluid">
				<div class="span12 center login-header">
					<h2>Welcome to Xerp</h2>
				</div>
				<!--/span-->
			</div>
			<!--/row-->

			<div class="row-fluid">
				<div class="well span5 center login-box">
					<div class="alert alert-info">Please login with your Username
						and Password.</div>
					<form id="login_form" class="form-horizontal" action="index.html"
						method="post">
						<fieldset>
							<div class="input-prepend" title="Username" data-rel="tooltip">
								<span class="add-on"><i class="icon-user"></i></span> <input
									autofocus class="input-large span10" itle="Error Title"
									data-rule-required="true" data-placement="bottom"
									name="username" id="username" type="text" />
							</div>
							<div class="clearfix"></div>

							<div class="input-prepend" title="Password" data-rel="tooltip">
								<span class="add-on"><i class="icon-lock"></i> </span><input
									class="input-large span10" itle="Error Title"
									data-rule-required="true" data-placement="bottom"
									name="password" id="password" type="password"
									 />
							</div>
							<div class="clearfix"></div>
							<div class="input-prepend" title="换一张" data-rel="tooltip">

								<img id="captchaImage" class="captchaImage"
									src="${base}/security/captcha" alt="换一张" />
							</div>
							<div class="clearfix"></div>
							<div class="input-prepend" title="authcode" data-rel="tooltip">
								<span class="add-on"><i class="icon-fire"></i></span> <input
									class="input-large span10" name="code" id="captcha" type="text"
									itle="Error Title" data-rule-required="true"
									data-placement="bottom" />
							</div>

							<div class="clearfix"></div>
							<div class="input-prepend">
								<label class="remember" for="remember"><input
									type="checkbox" id="remember" />Remember me</label>
							</div>
							<div class="clearfix"></div>

							<p class="center span5">
								<button type="submit" class="btn btn-primary">Login</button>
							</p>
						</fieldset>
					</form>
				</div>
				<!--/span-->
			</div>
			<!--/row-->
		</div>
		<!--/fluid-row-->

	</div>
	<!--/.fluid-container-->



</body>
</html>
