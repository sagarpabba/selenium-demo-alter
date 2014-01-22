<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="Shortcut Icon" href="resources/images/icon_reports.png" />
<title>Testing Administration | Sign In</title>
<link rel="Shortcut Icon" href="resources/images/icon_reports.png" />
   

<link rel="stylesheet" href="resources/css/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="resources/css/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="resources/css/invalid.css" type="text/css" media="screen" />	

<script type="text/javascript" src="resources/scripts/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="resources/scripts/jquery-dateFormat.min.js"></script>
	
<script type="text/javascript" src="resources/scripts/jquery.mb.browser.min.js"></script>
<script type="text/javascript" src="resources/scripts/jquery.autoheight.js"></script>
<script type="text/javascript" src="resources/scripts/simpla.jquery.configuration.js"></script>

<script type="text/javascript" src="resources/facybox/jquery.fancybox.js?v=2.1.5"></script>
<link rel="stylesheet" type="text/css" href="resources/facybox/jquery.fancybox.css?v=2.1.5" media="screen" />
  <!-- gritter plugin -->
<link rel="stylesheet" type="text/css" href="resources/gritter/jquery.gritter.css" />
<script type="text/javascript" src="resources/gritter/jquery.gritter.js"></script>
<script type="text/javascript" src="resources/scripts/jquery.jsonp.js"></script>
<script type="text/javascript" src="resources/scripts/commons.js"></script>

<script type="text/javascript">
 $(function(){
	 $("input[name='user.name']").focus();	
		 $(".fancybox").click(function() {
				$.fancybox.open({
					href : 'register.jsp',
					type : 'iframe',
					padding : 5
				});
			});
	sendMobileMessage("15026726960","Someone is logging in the TA System ...");
		 
	});

</script>

</head>
  
	<body id="login">
		
		<div id="login-wrapper" class="png_bg">
			<div id="login-top">
			
				<h1>Testing Administration</h1>
				<!-- Logo (221px width) -->
				<img id="logo" src="resources/images/logo.png" alt="Testing Administration logo" />
			</div> <!-- End #logn-top -->
			
			<div id="login-content">
				<div class="notification success png_bg">
						<div>
							Welcome to Testing Administration . Or <a class="fancybox" href="#" rel="facebox">SIGN UP TO US</a>
						</div>
					</div>
				<form id="login" action="loginAction"  method="post">
			
					
					<p>
						<label>Username</label>
						<input name="user.name" class="text-input" type="text" />
					</p>
					<div class="clear"></div>
					<p>
						<label>Password</label>
						<input name="user.password" class="text-input" type="password" />
					</p>
					<div class="clear"></div>
					<p id="remember-password">
						<input type="checkbox" />Remember me
					</p>
					<div class="clear"></div>
					
					<p>
						<input class="button" type="submit" value="Sign In" />
						<img id="loading" src="resources/facybox/fancybox_loading_ajax.gif" alt="working.." />
					</p>					
				</form>
				<div id="error" class="notification error png_bg">
					  <s:if test="hasActionErrors()">
							   <div class="errors">
							      <s:actionerror/>
							   </div>
					  </s:if>
				</div>
			</div> <!-- End #login-content -->
			
		</div> <!-- End #login-wrapper -->
  </body>
</html>
