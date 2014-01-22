<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Testing Administration | Home</title>
	<link rel="Shortcut Icon" href="resources/images/icon_reports.png" />
	
	<link rel="stylesheet" href="resources/css/iframe.css" type="text/css" />
	<!-- <link rel="stylesheet" href="resources/css/reset.css" type="text/css" media="screen" /> -->
	<link rel="stylesheet" href="resources/css/style.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="resources/css/invalid.css" type="text/css" media="screen" />

    <link rel="stylesheet" href="resources/timeto/timeTo.css" type="text/css" />	
 	
	
	<script type="text/javascript" src="resources/scripts/jquery-1.10.2.min.js"></script>
	
	<script type="text/javascript" src="resources/scripts/jquery.mb.browser.min.js"></script>
	
	<script type="text/javascript" src="resources/scripts/jquery.autoheight.js"></script>
	<!-- facybox plugin -->
	<script type="text/javascript" src="resources/facybox/jquery.fancybox.js?v=2.1.5"></script>
    <link rel="stylesheet" type="text/css" href="resources/facybox/jquery.fancybox.css?v=2.1.5" media="screen" />
    
    <script type="text/javascript" src="resources/facybox/fancybox.message.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/facybox/fancybox.message.css"  />
    
  
	<script type="text/javascript" src="resources/scripts/commons.js"></script>
	<script type="text/javascript" src="resources/scripts/simpla.jquery.configuration.js"></script>
	
	
	<style type="text/css">
				/* Start by setting display:none to make this hidden.
			   Then we position it in relation to the viewport window
			   with position:fixed. Width, height, top and left speak
			   speak for themselves. Background we set to 80% white with
			   our animation centered, and no-repeating */
			.modal {
			    display:    none;
			    position:   fixed;
			    z-index:    1000;
			    top:        0;
			    left:       0;
			    height:     100%;
			    width:      100%;
			    background: rgba( 255, 255, 255, .8 ) 
			                url('resources/facybox/fancybox_loading_ajax.gif') 
			                50% 50% 
			                no-repeat;
			}
			
			/* When the body has the loading class, we turn
			   the scrollbar off with overflow:hidden */
			body.loading {
			    overflow: hidden;   
			}
			
			/* Anytime the body has the loading class, our
			   modal element will be visible */
			body.loading .modal {
			    display: block;
			}
				
	
	</style> 
	
	
 <script type="text/javascript">
      $(function(){
    	//  $.fancybox.message.info("this is an info box !");
  		
    	 //delay to insert the user info
    	  setTimeout($.getJSON('http://freegeoip.net/json/', function(location) {
	    		  // example where I update content on the page.
	    		 var  username='<%=session.getAttribute("userid")%>';
	    		  var ip=location.ip;
	    		  var countrycode=location.country_code;
	    		  var countryname=location.country_name;
	    		  var regioncode=location.region_code;
	    		  var regionname=location.region_name;
	    		  var city=location.city;
	    		  var zipcode=location.zipcode;
	    		  var latitude=location.latitude;
	    		  var longitude=location.longitude;
	    		  var metro_code=location.metro_code;
	    		  var areacode=location.areacode;
	    		  dataparams={"location.username":username,"location.ip":ip,"location.countrycode":countrycode,"location.countryname":countryname,"location.regioncode":regioncode,
	    				      "location.regionname":regionname,"location.city":city,"location.zipcode":zipcode,"location.latitude":latitude,"location.longitude":longitude,
	    				      "location.metro_code":metro_code,"location.areacode":areacode};
	    		  console.log("ip is:"+ip+",location country is:"+countryname+",region name is:"+regionname+",city is:"+city+",zipcode is:"+zipcode+",latitude is:"+latitude);
	    		  $.fancybox.showLoading();
	              $.ajax({
	                          type:"POST",
	                          dataType:"json",
	                          async:false,
	                          url:"postVistorInfo",
	                          data:dataparams,
	                          complete:function(){$.fancybox.hideLoading(); },
	                          success:function(msg){
	                        	    if(msg.success){
	                        	    	console.log("insert vistor information to analysis later successfully");
	                        	    }else{
	                        	    	console.log("error to insert vistor information!");
	                        	    }
	                        	  },
	                           error: function (textStatus, errorThrown) {
	                                  alert("Met error code here:"+errorThrown);
	                             }   
	                    }); 
    	          }
	    		
    		  ),90000);
      });
      
 
 </script>  
</head>
<body>
<%
  if(session.getAttribute("userid")==null||session.getAttribute("userid")==""){
	   System.out.println("Session has been invalidated! session id is :"+session.getAttribute("userid"));
	   response.sendRedirect("login.jsp");
	}else{
	   System.out.println("Now User Session ID:"+session.getAttribute("userid")+" is active! ");
	}

%>
<div id="body-wrapper"> 
    <div id="sidebar">
         <div id="sidebar-wrapper"> <!-- Sidebar with logo and menu -->			
			<h1 id="sidebar-title"><a href="#">Simpla Admin</a></h1>		  
			<!-- Logo (221px wide) -->
			<div id="currentdate"></div>
			<script src="resources/timeto/jquery.timeTo.js"></script>
			<script>
		        $('#currentdate').timeTo();
		    </script>
			<a href="#"><img id="logo" src="resources/images/logo.png" alt="Simpla Admin logo" /></a>
		  
			<!-- Sidebar Profile links -->
			<div id="profile-links">
					Hello, <a href="#" title="Edit your profile"><%=session.getAttribute("userid") %></a>, you have <a href="#messages" rel="modal" title="3 Messages">3 Messages</a><br />
					<br />
					<a href="#" title="View the Site">View the Latest Execution</a> | <a href="logoutAction" title="Sign Out">Sign Out</a>
			</div>        
			
			<ul id="main-nav">  <!-- Accordion Menu -->				
						<li>
							<a rel="dashboard.jsp" href="#" class="nav-top-item no-submenu current"> Dashboard </a>       
						</li>				
						<li> 
						    <a  href="#" class="nav-top-item"> Execution Results</a> 
                            <ul>
								<li><a rel="executions.jsp" href="#">History Executions</a></li>
							</ul>     
						</li>
						<li> 
						    <a  href="#" class="nav-top-item"> Execution Data</a> 
                            <ul>
								<li><a rel="http://localhost:9999/tadata" href="#">Test Data</a></li>
							</ul>     
						</li>
						<li>
							<a href="#" class="nav-top-item">Report Analysis </a>
							<ul>
								<li><a rel="executions_report.jsp" href="#">Execution Analysis</a></li>
								<li><a rel="eu_report.jsp" href="#">Effort Utilization Rate</a></li>
								<li><a rel="code_report.jsp" href="#">Code Changes Analysis</a></li>
							</ul>
						</li>
						<li>
							<a href="#" class="nav-top-item">Source Code Management </a>
							<ul>
								<li><a rel="svn.jsp" href="#">SVN</a></li>
								<li><a rel="svn.jsp" href="#">Star Team</a></li>
								<li><a rel="git.jsp" href="#">Git</a></li>
							</ul>
						</li>				
						<li>
							<a href="#" class="nav-top-item"> Defects </a>
							<ul>
								<li><a rel="defects.jsp" href="#">Defect List</a></li>
								<li><a rel="defects_new.jsp" href="#">Raise New Defect</a></li>	
								<li><a rel="defects_alm.jsp" href="#">Track All ALM Defects</a></li>							
							</ul>
						</li>
						
						<li>
							<a href="#" class="nav-top-item">Task And Event </a>
							<ul>
								<li><a rel="mycalendar.jsp" href="#">My Work Task</a></li>
								<li><a id="effort" rel="myeffort.jsp" href="#">Daily Work Effort</a> 
								
							</ul>
						</li>
						
						<li>
							<a rel="settings.jsp" href="#" class="nav-top-item no-submenu">Settings</a>
						</li>
						<li>
							<a rel="demos.jsp" href="#" class="nav-top-item no-submenu">Demos</a>
						</li>      				
			 </ul> <!-- End #main-nav -->			
     </div>
   </div> <!-- End #sidebar --> 
    	<div id="main-content"> <!-- Main Content Section with everything -->
			
			<noscript> <!-- Show a notification if the user has disabled javascript -->
				<div class="notification error png_bg">
					<div>
						Javascript is disabled or is not supported by your browser. Please <a href="http://browsehappy.com/" title="Upgrade to a better browser">upgrade</a> your browser or <a href="http://www.google.com/support/bin/answer.py?answer=23852" title="Enable Javascript in your browser">enable</a> Javascript to navigate the interface properly.
					</div>
				</div>
			</noscript>
		    <div id="maincontent">
		          <jsp:include page="dashboard.jsp"></jsp:include>
		    </div>		  
		    <div class="clear"></div>
			
		   <!-- End Notifications -->		
			<div id="footer">
				<small> <!-- Remove this notice or replace it with whatever you want -->
						&#169; Copyright 2013 HP | Powered by <a href="http://themeforest.net/item/simpla-admin-flexible-user-friendly-admin-skin/46073">Simpla Admin</a> | <a href="#">Top</a>
				</small>
			</div>
			
    </div>
  
</div>
 <div class="modal"></div>
</body>
</html>
