<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<!-- Title etc -->
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>My Calendar</title>
		
	</head>
	<body>
        <div class="formee-msg-info">
    	<h3>New Task</h3> 
	    </div>
	    <form id="frm" class="formee"  method="post">
		    <fieldset>
		        <legend>Execution Scheduler Settings</legend>
		        <div class="grid-12-12">
		                <label>Execution ID </label>
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
		               <input class="right"  type="submit"  onclick="save()" title="submit"  value="Save Settings">
		        </div>
		    </fieldset>  
		    </form>
	   
	
		<!-- Include scripts at bottom to aid dom loading and prevent hangs -->
		<script type='text/javascript' src='resources/scripts/jquery-1.10.2.min.js'></script>
		<script type='text/javascript' src='resources/fullcalendar/jquery-ui.custom.min.js'></script> 
		<script type="text/javascript" src="resources/scripts/jquery.mb.browser.min.js"></script>
		<!-- formee css style -->
		 <link rel="stylesheet" type="text/css" href="resources/formee/formee-structure.css" />
		 <link rel="stylesheet" type="text/css" href="resources/formee/formee-style.css" />
		 <script type="text/javascript" src="resources/formee/formee.js"></script>
		
		
	</body>
</html>	