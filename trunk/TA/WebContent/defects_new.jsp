<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <title>Testing Administration | New Defect</title>
   <link rel="Shortcut Icon" href="resources/images/icon_reports.png" />
	
	<link rel="stylesheet" href="resources/css/reset.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="resources/css/style.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="resources/css/invalid.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="resources/css/facebox.js" type="text/css" />		
	
	<script type="text/javascript" src="resources/scripts/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="resources/scripts/jquery-dateFormat.min.js"></script>

	<script type="text/javascript" src="resources/facybox/jquery.fancybox.js?v=2.1.5"></script>
	<link rel="stylesheet" type="text/css" href="resources/facybox/jquery.fancybox.css?v=2.1.5" media="screen" />
	  <!-- gritter plugin -->
	<link rel="stylesheet" type="text/css" href="resources/gritter/jquery.gritter.css" />
	<script type="text/javascript" src="resources/gritter/jquery.gritter.js"></script>
	
	 <!-- formee css style -->
	 <link rel="stylesheet" type="text/css" href="resources/formee/formee-structure.css" />
	 <link rel="stylesheet" type="text/css" href="resources/formee/formee-style.css" />
	 <script type="text/javascript" src="resources/formee/formee.js"></script>
	
	<script type="text/javascript" src="resources/scripts/commons.js"></script>
	<script type="text/javascript" src="easyui_1.3.4/jquery.easyui.min.js"></script>

  </head>
  <body>
  
   <div class="formee-msg-info">
    	<h3>New Defect to ALM</h3> 
    </div>
   <form id="frm" class="formee"  method="post">
	    <fieldset>
	        <legend>New Defect</legend>
	        <div class="grid-12-12">
	                <label>Defect ID </label>
	                <input name="build.id" type="text" class="formee-medium" readOnly="readonly"> 
	        </div>
	      
	        <div class="grid-12-12">
	                <label>Execution Status <em class="formee-req">*</em></label>
	                <ul class="formee-list">
	                    <li><input name="build.status" type="radio" checked="checked" value="Passed"><label>Passed</label></li>
	                    <li><input name="build.status" type="radio" value="Failed"><label>Failed</label></li>
	                    <li><input name="build.status" type="radio" value="Warning"><label>Warning</label></li>
	                    <li><input name="build.status" type="radio" value="Not Completed"><label>Not Completed</label></li>
	                </ul>
	        </div>
	         <div class="grid-12-12">
	                <label>Execution Type <em class="formee-req">*</em></label>
	                <select name="build.type" class="formee-small">
	                    <option value="st">Smoke Testing</option>
	                    <option value="hc">Health Check</option>
	                    <option value="rt">Regression Testing</option>
	                    <option value="others">Others</option>
	                </select>
	        </div>
	               
	        <div class="grid-12-12">
	               <input class="right"  type="submit"  onclick="save()" title="submit"  value="Save">
	        </div>
	    </fieldset>  
	</form>
 </body>
 </html>