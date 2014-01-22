<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Register to Us</title>
   <!-- easyui css file -->
<link rel="stylesheet" type="text/css" href="easyui_1.3.4/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="easyui_1.3.4/themes/default/easyui.css" />
	
<script type="text/javascript" src="resources/scripts/jquery-1.10.2.min.js"></script>

<script type="text/javascript" src="resources/facybox/jquery.fancybox.js?v=2.1.5"></script>
<link rel="stylesheet" type="text/css" href="resources/facybox/jquery.fancybox.css?v=2.1.5" media="screen" />
  <!-- gritter plugin -->
<link rel="stylesheet" type="text/css" href="resources/gritter/jquery.gritter.css" />
<script type="text/javascript" src="resources/gritter/jquery.gritter.js"></script>

 <script type="text/javascript" src="resources/scripts/commons.js"></script>
 <!-- easyui js file -->
<script type="text/javascript" src="easyui_1.3.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="easyui_1.3.4/validator.js"></script>

<style>
		/*
		
		Tutoralzine Demo 
		Original tutorial: Creating a Facebook-like Registration Form with jQuery
		Tutorial URL: http://tutorialzine.com/2009/08/creating-a-facebook-like-registration-form-with-jquery/
		
		You are free to use the following demo code for any purpose you see fit.
		
		*/
		
		
		/* Page styles */
		
		body,h1,h2,h3,p,td,quote,small,form,input,ul,li,ol,label{
			margin:0px;
			padding:0px;
		}
		
		body{
			margin-top:20px;
			font-family:Arial, Helvetica, sans-serif;
			color:#51555C;
			height:100%;
			
			font-size:11px;
		}
		
		
		/* Form styles */
		
		input,select{
			padding:3px;
			color:#333333;
			
			border:1px solid #96A6C5;
			margin-top:2px;
			width:200px;
			font-size:11px;
		}
		
		select{
			width:auto;
			padding:2px;
		}
		
		.formline{
			padding:3px;
		}
		
		label{
			font-size:11px;
			display:block;
			text-align:right;
		}
		
		table{
			width:300px;
		}
		
		td{
			font-size:11px;
		}
		
		.input-container{
			padding:1px;
		}
		
		#div-regForm,.registered{	
			border:3px solid #eeeeee;
			padding:15px;
			
			background:url(resources/images/registerbg.jpg) repeat-x #cbd4e4;
			color:#203360;
			
			margin:30px auto 40px auto;
			width:400px;
		}
		
		.form-title,
		.form-sub-title{
			font-size:20px;
			
			font-family:"Lucida Grande",Tahoma,Verdana,Arial,sans-serif;
			font-size:20px;
			font-weight:bold;
		}
		
		.form-sub-title{
			font-weight:normal;
			padding:6px 0 15px 0;
		}
		
		.greenButton{
			width:auto;
			margin:10px 0 0 2px;
			padding:3px 4px 3px 4px;
			color:white;
			background-color:#589d39;
			outline:none;
			border:1px solid #006600;
			font-weight:bold;
		}
		
		.greenButton:active{
			background-color:#006600;
			padding:4px 3px 2px 5px;
		}
		
		#loading{
			left:10px;
			position:relative;
			top:3px;
			visibility:hidden;
		}
		
		#error{
			background-color:#ffebe8;
			border:1px solid #dd3c10;
			padding:7px 3px;
			text-align:center;
			margin-top:10px;
			visibility:hidden;
		}

</style>
<script type="text/javascript">
//submit the function 

function registerNow(){
            var username=$("input[name='user.name']").val();
            var password=$("input[name='user.password']").val();
            var email=$("input[name='user.email']").val();
            var phone=$("input[name='user.phone']").val();
            var sex=$("select[name='user.sex'] :selected").text();
            var type=$("select[name='user.type'] :selected").val();
            console.log("name is:"+username+",password:"+password+",email:"+email+",phone:"+phone+",sex :"+sex);
          //  var dataparams={"user.name":username,"user.password":password,"user.email":email,"user.phone":phone,"user.sex":sex,"user.type":type};
           // ajaxloader("POST","json","newBuildAction",dataparams,function(msg){
            //    alert("message is now ");
             //   console.log("success update"+msg);
               // generateMsg("success","update the execution already!");           
          //  });
        // $('#loading').css('visibility','visible');
      
         $('#regForm').form({  
            // url: 'registerAction',  
             onSubmit: function(){  
             	 //alert($(this).form('validate'));
             	 // $('#loading').css('visibility','hiden');
                  return $(this).form('validate');  
             },  
             success: function(result){  
            	    // $('#loading').css('visibility','hiden');
            	     console.log("return value is:"+result);
                	 parent.growlMessage("New User Registration","Sign UP new User:"+username+" Successfully,You Can Sign In Now!",true,5);
             	     parent.$.fancybox.close();  
             },
             error: function (textStatus, errorThrown) {
      		        growlMessage("New User Registration","Your Input is invalid "+textStatus+" ,please retry again!",true,5);
      	     }
         });  
        /*   $.ajax({
                      type:"POST",
                      dataType:"json",
                      async:false,
                      url:"registerAction",
                      data:dataparams,
                      complete:function(){$('#loading').css('visibility','hidden'); },
                      success:function(msg){	   
                    	    parent.growlMessage("New User Registration","Sign UP new User:"+username+" Successfully,You Can Sign In Now!",true,5);
                    	    parent.$.fancybox.close();  
                    	   },
                      error: function (textStatus, errorThrown) {
                    		   growlMessage("New User Registration","Your Input is invalid "+textStatus+" ,please retry again!",true,5);
                    	   }
                });  */
           //location.reload();
    }


</script>
</head>
<body>
<div id="div-regForm">
<div class="form-title">Sign Up</div>
<div class="form-sub-title">It's free and anyone can join</div>

<form id="regForm" action="registerAction" method="post">
<table>
  <tbody>
  <tr>
    <td><label for="fname">User Name:</label></td>
    <td><div class="input-container"><input class="easyui-validatebox" data-options="required:true,validType:'account[8,20]'"  name="user.name" id="fname" type="text" /></div></td>
  </tr>
  <tr>
    <td><label for="email">Your Email:</label></td>
    <td><div class="input-container "><input class="easyui-validatebox" data-options="required:true,validType:'email'"  name="user.email" id="email" type="text" /></div></td>
  </tr>
  <tr>
    <td><label for="pass">New Password:</label></td>
    <td><div class="input-container"><input class="easyui-validatebox" data-options="required:true" name="user.password"  id="pass" type="password" /></div></td>
  </tr>
  <tr>
    <td><label for="pass">New Password Twice:</label></td>
    <td><div class="input-container"><input class="easyui-validatebox" required="required" validType="equals['#pass']"  id="passwd" type="password" /></div></td>
  </tr>
  <tr>
    <td><label for="email">Phone (Optional):</label></td>
    <td><div class="input-container"><input class="easyui-validatebox" data-options="required:true,validType:'mobile'" name="user.phone" id="email" type="text" /></div></td>
  </tr>
  <tr>
    <td><label for="sex-select">I am:</label></td>
    <td>
    <div class="input-container">
    <select name="user.sex" id="sex-select">
    <option value="0">Select Sex:</option>
    <option value="female">Female</option>
    <option value="male" selected="selected">Male</option>
    </select>
    </div>
    
    </td>
  </tr>
  <tr>
    <td><label for="sex-select">Permission:</label></td>
    <td>
    <div class="input-container">
    <select name="user.type">
    <option value="0">Select Type:</option>
    <option value="1">Admin</option>
    <option value="2" selected="selected">Normal</option>
    </select>
    </div>
    
    </td>
  </tr>
  <tr>
  <td>&nbsp;</td>
  <td><input type="submit" onclick="registerNow()" class="greenButton" value="Sign Up" /><img id="loading" src="resources/facybox/fancybox_loading_ajax.gif" alt="working.." />
</td>
  </tr>
  
  
  </tbody>
</table>

</form>

<div id="error">
&nbsp;
</div>

</div>

</body>
</html>