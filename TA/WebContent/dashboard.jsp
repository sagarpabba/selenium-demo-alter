<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
   <head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <title>Testing Administration | Dashboard</title>
   <link rel="Shortcut Icon" href="resources/images/icon_reports.png" />
   <link rel="stylesheet" href="resources/css/reset.css" type="text/css" media="screen" />
   <link rel="stylesheet" href="resources/css/style.css" type="text/css" media="screen" />
   <link rel="stylesheet" href="resources/css/invalid.css" type="text/css" media="screen" />
   <link rel=Stylesheet type="text/css" media=all href="resources/css/messagenotification.css">
  
<script type="text/javascript" src="resources/scripts/jquery-1.10.2.min.js"></script>

<script type="text/javascript" src="resources/scripts/jquery-dateFormat.js"></script>


<script language="javascript" type="text/javascript" src="resources/flot/jquery.flot.js"></script>
<script language="javascript" type="text/javascript" src="resources/flot/jquery.flot.tooltip.min.js"></script>
<script language="javascript" type="text/javascript" src="resources/flot/jquery.flot.categories.js"></script>
<script language="javascript" type="text/javascript" src="resources/flot/jquery.flot.axislabels.js"></script>
<script language="javascript" type="text/javascript" src="resources/flot/jquery.flot.orderBars.js"></script>
<script language="javascript" type="text/javascript" src="resources/flot/jquery.flot.stack.js"></script>
<script language="javascript" type="text/javascript" src="resources/flot/jquery.flot.barnumbers.js"></script>


<script language="javascript" type="text/javascript" src="resources/scripts/commons.js"></script>


<!-- facybox plugin -->

<script type="text/javascript" src="resources/facybox/jquery.fancybox.js?v=2.1.5"></script>
<link rel="stylesheet" type="text/css" href="resources/facybox/jquery.fancybox.css?v=2.1.5" media="screen" />

	<!-- scroll up plugin  -->
	<link id="scrollUpTheme" rel="stylesheet" href="resources/scrollup/css/themes/image.css">
    <link id="scrollUpTheme" rel="stylesheet" href="resources/scrollup/css/labs.css">
	
	<script  type="text/javascript" src="resources/scrollup/jquery.scrollUp.js"></script>  

<script type="text/javascript">
     $(function(){ 
    		//scroll up
        var firstday=$.format.date(getFirstDayOfWeek(),"MM/dd/yyyy");
    	var lastday=$.format.date(getLastDayOfWeek(),"MM/dd/yyyy");
    	console.log("firstday:"+firstday);
    	$("#startdate").text(firstday);
    	$("#enddate").text(lastday);
       	$.scrollUp({
   		        scrollName: 'scrollUp', // Element ID
   		        scrollDistance: 300, // Distance from top/bottom before showing element (px)
   		        scrollFrom: 'top', // 'top' or 'bottom'
   		        scrollSpeed: 300, // Speed back to top (ms)
   		        //easingType: 'linear', // Scroll to top easing (see http://easings.net/)
   		        animation: 'fade', // Fade, slide, none
   		        animationInSpeed: 200, // Animation in speed (ms)
   		        animationOutSpeed: 200, // Animation out speed (ms)
   		        scrollText: 'TO TOP', // Text for element, can contain HTML
   		        scrollTitle: false, // Set a custom <a> title if required. Defaults to scrollText
   		        scrollImg: true, // Set true to use image
   		        activeOverlay: false, // Set CSS color to display scrollUp active point, e.g '#00FFFF'
   		        zIndex: 2147483647 // Z-Index for the overlay
   			});   		
    	/*  $(".fancybox").click(function() {
				$.fancybox.open({
					href : 'executions_tracker.jsp',
					type : 'iframe',
					padding : 5
				});
			}); */
			//put the data analysis result
   	 var baroptions={
			  series: {
                 shadowSize: 1,
                 bars : {
                     show : true,
                     showNumbers: true,
                   
                     }
             },
             tooltip: true,
				tooltipOpts: {
					content: "<h4>%s</h4><ul><li>Number is: %y</li></ul>",
					shifts: {
						x: 1
					    }
					},
             xaxis: {
              /*    min: 0.0,
                 max: 6.0,
                 mode: null,
                 ticks: [1.0, 2.0, 3.0, 4.0, 5.0], */
                 mode: "categories",
                 tickLength: 0, // hide gridlines
                 axisLabelUseCanvas: true,
                 axisLabelFontSizePixels: 12,
                 axisLabelFontFamily: 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
                 axisLabelPadding: 5
             },
             yaxis: {
                 axisLabelUseCanvas: true,
                 axisLabelFontSizePixels: 12,
                 axisLabelFontFamily: 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
                 axisLabelPadding: 5
             },
             grid: {
                 hoverable: true,
                 clickable: false,
                 borderWidth: 1
             },
             legend: {
                 labelBoxBorderColor: "none",
                 position: "right"
             }
           
         };
			       
   $.ajax({
	             type:"get",
	             dataType:"json",
	             async:false,
	             url:"executionBarChartWeeklyAction",
	             complete:function(){ },
	             success:function(msg){
	            	 // string to object
	            	 //json from object to string
		            	 var pass=msg.passed;
		            	 var fail=msg.failed;
		            	 //var fail=eval("("+JSON.stringify(msg.failed)+")");
		            	 var warning=msg.warning;
		            	 console.log("passed is:"+pass);
		            	 console.log("failed is:"+fail);
		            	 console.log("warning is:"+warning);
		            	 var data= [
		            	              {
		            	                  label: "Passed Cases",
		            	                  data: pass,
		            	                  bars: {
		            	                    show: true,
		            	      				barWidth: 0.2,
		            	                      fill: true,
		            	                      lineWidth: 1,
		            	                      order: 1,
		            	                      fillColor:  "#00B050"
		            	                  },
		            	                  color: "#00B050"
		            	              },
		            	              {
		            	                  label: "Failed Cases",
		            	                  data: fail,
		            	                  bars: {
		            	                      show: true,
		            	                     
		            	                      barWidth: 0.2,
		            	                      fill: true,
		            	                      lineWidth: 1,
		            	                      order: 2,
		            	                      fillColor:  "#C00000"
		            	                  },
		            	                  color: "#C00000"
		            	              },
		            	              {
		            	                  label: "Warning Cases",
		            	                  data: warning,
		            	                  bars: {
		            	                      show: true,
		            	                    
		            	      				  barWidth: 0.2,
		            	                      fill: true,
		            	                      lineWidth: 1,
		            	                      order: 3,
		            	                      fillColor:  "yellow"
		            	                  },
		            	                  color: "yellow"
		            	              }
		            	        ];
		            	  $.plot($("#placeholder"),data,baroptions);  
	                   }
	         });  
     });
</script>	
  </head>
  <body>
   <div class="modal"></div>
	<h2>Welcome <%=session.getAttribute("userid") %></h2>
    <p id="page-intro">What's up today?</p>
    <a class="message" href="#" rel="executions.jsp">
    <div class="app-box">
	    <div class="reminder red-shadow">
	        <p class="app-title">Complete Executions</p>
	        <div id="executionnum" class="notifications red">5</div>
	    </div>
    </div>
    </a>
   
    <a class="message" href="#" rel="svn.jsp">
    <div class="app-box">
	    <div class="reminder green-shadow">
	        <p class="app-title">Code Revisions</p>
	        <div id="revisionnum" class="notifications green">100</div>
	    </div>
   </div>
   </a>
   <a class="message" href="#" rel="comments.jsp">
	<div class="app-box">
	    <div class="reminder blue-shadow">
	        <p class="app-title">Recent Comments</p>
	        <div id="commentnum" class="notifications blue">5</div>
	    </div>
	</div>
	</a>
	<a class="message" href="#" rel="defects.jsp">
	<div class="app-box">
	    <div class="reminder yellow-shadow">
	        <p class="app-title">Defects</p>
	        <div id="defectnum" class="notifications yellow">500</div>
	    </div>
	</div>
	</a>
	<a class="message" href="#" rel="vistors.jsp">
	<div class="app-box">
	    <div class="reminder purple-shadow">
	        <p class="app-title">OnLine Vistors</p>
	        <div id="onlinevistornum" class="notifications purple">600</div>
	    </div>
	</div>
	</a>
	<div class="clear"></div> <!-- End .clear -->
	<li><a class="fancybox shortcut-button" href="#" rel="facebox"><span>
					<img src="resources/images/pencil.png" alt="icon" /><br />
					 Log New Execution
				</span></a>
	</li>		
		<div class="content-box">
				
				<div class="content-box-header">
					
					<h3>Latest Execution Trend </h3>
					
				</div> <!-- End .content-box-header -->
				
				<div class="content-box-content">
					
					<div class="tab-content default-tab">
					
						<h4>Weekly Execution Status distribution(From <b id="startdate"></b> to <b id="enddate"></b> )</h4>
						<div id="placeholder" style="width:1260px;height:500px;"></div>
						
					</div> <!-- End #tab3 -->        
					
				</div> <!-- End .content-box-content -->				
		</div> <!-- End .content-box -->
		
		
			<div class="content-box column-left">
				
				<div class="content-box-header">
					
					<h3>Code Latest Update</h3>
					
				</div> <!-- End .content-box-header -->
				
				<div class="content-box-content">
					
					<div class="tab-content default-tab">
					
						<h4>Maecenas dignissim</h4>
						<p>
						Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in porta lectus. Maecenas dignissim enim quis ipsum mattis aliquet. Maecenas id velit et elit gravida bibendum. Duis nec rutrum lorem. Donec egestas metus a risus euismod ultricies. Maecenas lacinia orci at neque commodo commodo.
						</p>
						
					</div> <!-- End #tab3 -->        
					
				</div> <!-- End .content-box-content -->
				
			</div> <!-- End .content-box -->
			
			<div class="content-box column-right">
				
				<div class="content-box-header"> <!-- Add the class "closed" to the Content box header to have it closed by default -->
					
					<h3>Defect Activities</h3>
					
				</div> <!-- End .content-box-header -->
				
				<div class="content-box-content">
					
					<div class="tab-content default-tab">
					
						<h4>This box is closed by default</h4>
						<p>
						Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in porta lectus. Maecenas dignissim enim quis ipsum mattis aliquet. Maecenas id velit et elit gravida bibendum. Duis nec rutrum lorem. Donec egestas metus a risus euismod ultricies. Maecenas lacinia orci at neque commodo commodo.
						</p>
						
					</div> <!-- End #tab3 -->        
					
				</div> <!-- End .content-box-content -->
				
			</div> <!-- End .content-box -->
			
			
			<!-- modal content -->
		<div id="basic-modal-content" style="display:none">
			<form action="#" method="post">							
				 <fieldset>
				    	<legend>Execution Info</legend>
				        <dl>
				        	<dt><label for="comments">Execution ID:</label></dt>
				            <dd><textarea name="comments" id="comments" rows="5" cols="60"></textarea></dd>
				        </dl>
				        <dl>
				        	<dt><label for="upload">Upload a File:</label></dt>
				            <dd><input type="file" name="upload" id="upload" /></dd>
				        </dl>
				        <dl>
				        	<dt><label for="test">Sample Button:</label></dt>
				            <dd><button type="button" name="test" id="test">I Do Nothing</button></dd>
				        </dl>
		    </fieldset>
			</form>
		</div>
		
		<div class="clear"></div>
 </body>
 </html>