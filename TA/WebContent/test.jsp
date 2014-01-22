<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="resources/scripts/jquery-1.10.2.min.js"></script>



<script language="javascript" type="text/javascript" src="resources/flot/jquery.flot.js"></script>
<script language="javascript" type="text/javascript" src="resources/flot/jquery.flot.tooltip.min.js"></script>
<script language="javascript" type="text/javascript" src="resources/flot/jquery.flot.categories.js"></script>
<script language="javascript" type="text/javascript" src="resources/flot/jquery.flot.axislabels.js"></script>
<script language="javascript" type="text/javascript" src="resources/flot/jquery.flot.orderBars.js"></script>
<script language="javascript" type="text/javascript" src="resources/flot/jquery.flot.stack.js"></script>
<script language="javascript" type="text/javascript" src="resources/flot/jquery.flot.barnumbers.js"></script>

<script language="javascript" type="text/javascript" src="resources/scripts/commons.js"></script>
<style type="text/css">
body { font-family: Verdana, Arial, sans-serif; font-size: 12px; }
#placeholder { width: 450px; height: 200px; position: relative; margin: 0 auto; }
.legend table, .legend > div { height: 82px !important; opacity: 1 !important; right: -55px; top: 10px; width: 116px !important; }
.legend table { border: 1px solid #555; padding: 5px; }
#flot-tooltip { font-size: 12px; font-family: Verdana, Arial, sans-serif; position: absolute; display: none; border: 2px solid; padding: 2px; background-color: #FFF; opacity: 0.8; -moz-border-radius: 5px; -webkit-border-radius: 5px; -khtml-border-radius: 5px; border-radius: 5px; }

</style>	
<script>
	$(function() {
		 //ajax loading data
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
		        //  $.plot($("#placeholder"), data1,baroptions );
		     
    	 });
      </script>
</head>
<body>

 <div id="placeholder" style="width:1200px;height:400px;"></div>
 
</body>
</html>