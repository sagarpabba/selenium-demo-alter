<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Testing Administration | Testing Execution Track</title>
	<link rel="Shortcut Icon" href="resources/images/icon_reports.png" />
 
     <!-- easyui css file -->
    <link rel="stylesheet" type="text/css" href="easyui_1.3.4/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="easyui_1.3.4/themes/default/easyui.css" />
	
	<link rel="stylesheet" href="resources/css/reset.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="resources/css/style.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="resources/css/invalid.css" type="text/css" media="screen" />
	<!-- <link rel="stylesheet" href="resources/css/facebox.js" type="text/css" />	 -->	
	
	<script type="text/javascript" src="resources/scripts/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="resources/scripts/jquery-dateFormat.min.js"></script>
	<script type="text/javascript" src="resources/scripts/jquery.mb.browser.min.js"></script>
	
	<script type="text/javascript" src="resources/facybox/jquery.fancybox.js?v=2.1.5"></script>
    <link rel="stylesheet" type="text/css" href="resources/facybox/jquery.fancybox.css?v=2.1.5" media="screen" />
    
	<script type="text/javascript" src="resources/facybox/fancybox.message.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/facybox/fancybox.message.css" media="screen" />
     <!-- gritter plugin -->
	<link rel="stylesheet" type="text/css" href="resources/gritter/jquery.gritter.css" />
	<script type="text/javascript" src="resources/gritter/jquery.gritter.js"></script>
    
    <script type="text/javascript" src="resources/scripts/commons.js"></script>
	 <!-- easyui js file -->
	<script type="text/javascript" src="easyui_1.3.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui_1.3.4/validator.js"></script>
	<!-- scroll up plugin  -->
	<link id="scrollUpTheme" rel="stylesheet" href="resources/scrollup/css/themes/image.css">
    <link id="scrollUpTheme" rel="stylesheet" href="resources/scrollup/css/labs.css">
	
	<script  type="text/javascript" src="resources/scrollup/jquery.scrollUp.js"></script>
	
	<script type="text/javascript">
	 $(function(){	
		 
		//scroll up
	    	$.scrollUp({
			        scrollName: 'scrollUp', // Element ID
			        scrollDistance: 300, // Distance from top/bottom before showing element (px)
			        scrollFrom: 'top', // 'top' or 'bottom'
			        scrollSpeed: 300, // Speed back to top (ms)
			      //  easingType: 'linear', // Scroll to top easing (see http://easings.net/)
			        animation: 'fade', // Fade, slide, none
			        animationInSpeed: 200, // Animation in speed (ms)
			        animationOutSpeed: 200, // Animation out speed (ms)
			        scrollText: 'TO TOP', // Text for element, can contain HTML
			        scrollTitle: false, // Set a custom <a> title if required. Defaults to scrollText
			        scrollImg: true, // Set true to use image
			        activeOverlay: false, // Set CSS color to display scrollUp active point, e.g '#00FFFF'
			        zIndex: 2147483647 // Z-Index for the overlay
				});
		// growlMessage("Update Execution","Update Result is:",true,5);
		 $("#builds").datagrid({       
	         columns:[[
	               {field:'build_id',title:'Execution ID',width:50},
	               {field:'build_starttime',title:'Execution StartTime',width:100},
	               {field:'build_status',title:'Execution Status',styler: function(value,row,index){
		                     if (value=="No Run"){
		                        return 'background-color:#C0C0C0;';
		                      }
		                     else if (value=="Passed"){		                
		                        return 'background-color:#00FF00;';
		                      }
		                     else if (value=="Failed"){		               
		                        return 'background-color:#FF0000;';
		                      }
		                     else if (value=="Warning"){		                
		                        return 'background-color:#FFFF00;';
		                      }else{		                    	  
		                    	  return 'background-color:#FF0000;';
		                      }
	                    }
	               }, 
	               {field:'build_type',title:'Execution Type',width:100},
	               {field:'build_name',title:'Execution Summary Name',align:'left',width:160},
	               {field:'build_comment',title:'Execution Analysis Result',align:'center',width:100,styler: function(value,row,index){
	            	          return 'background-color:#C0C0C0;';
	                   }
	               },
	               {field:'likenumber',title:'Like',width:30},
	               {field:'dislikenumber',title:'Dislike',width:30},
	               
	               {field:'build_endtime',title:'Execution EndTime',width:80},
	               {field:'opt',title:'Operations',width:220,formatter:function(value,row,index){
	                                                        var buildid = row.build_id;
	                                                        var buildname=row.build_name;
	                                                        var buildstatus=row.build_status;
	                                                        var buildtype=row.build_type;
	                                                        var starttime=row.build_starttime;
	                                                        var endtime=row.build_endtime;
	                                                        var buildcomment=row.build_comment;
	                                                        var opt ='';
	                                                        var parameters=buildid+",'"+starttime+"','"+endtime+"'";
	                                                        var updateparameter=buildid+",'"+buildname+"','"+buildstatus+"','"+buildtype+"','"+starttime+"','"+endtime +"','"+buildcomment+"'"; 
	                                                      //  var deleteparameter=buildid;
	                                                        //  console.log("udpate paramter is:"+updateparameter);
	                                                        var detail = '<a href="#" onclick="goDetail('+parameters+');">View Detail Report</a> |  ';
	                                                        var edit =  '<a class="facybox" onclick="goUpdate('+updateparameter+');" target="_parent">Update Execution</a> | ';
	                                                        var deleteit='<a class="facybox" onclick="deleteExecution('+buildid+');" target="_parent">Delete Execution</a> ';
	                                                        
	                                                        return opt+detail+edit+deleteit;                                                       
	                                                }
	         },
	        ]],
	        title:'',
	         
	        autoRowWidth:true,
	        autoRowHeight:true,  
	        striped: false,    
	        border: true,    
	        collapsible:true,//
	        nowrap:false,
	      //  fit: true,//
	        fitColumns:true,
	        url:'latestBuildAction',
	        loadMsg:'Trying to load all the Execution data from Server...',
	        onLoadError : function() {  
	            alert('Sorry ,the data loaded met failed...');  
	        },  
	        sortName:'build_id',
	        sortOrder:'desc',
	        multiSort:true,
	        idField:'build_id',
	        singleSelect:true,
	        pageSize: 20,//every page number 
	        pageList: [20,40,60],//page every
	       // fitColumns:true,
	      //  frozenColumns:[[    
	        //      {field:'check',checkbox:false}
	        // ]],
	        pagination:true,    
	        rownumbers:true,
	      
	       // queryParams: search2Json(),
	        toolbar:"#toolbar"
	        
	    });
	     //pagenation
	    $('#builds').datagrid('getPager').pagination({    
	         
	         beforePageText: 'page ',//
	         afterPageText: 'of total {pages} pages',  
	         
	         autoRowHeight:true,
	         striped:true,
	         pagePosition:'top',
	         idField:"build_id",
	         displayMsg: 'Current listed Data From {from}-to {to} ,total {total} records' ,
	          onBeforeRefresh:function(pageNumber, pageSize){     
	            $(this).pagination('loading');     
	                //alert('pageNumber:'+pageNumber+',pageSize:'+pageSize);     
	           // $(this).pagination('loadings...');    
	        } 
	     });
	     
	     //tab hover operation
	   var tabs = $('#allbuilds').tabs().tabs('tabs');
	   for(var i=0; i<tabs.length; i++){
	                tabs[i].panel('options').tab.unbind().bind('mouseenter',{index:i},function(e){
	                    $('#allbuilds').tabs('select', e.data.index);
	                });
	   } 
      
	   $("#starttime").datebox({
		   formatter:function(date){  
				   var y = date.getFullYear();  
				   var m = date.getMonth()+1;  
				   var d = date.getDate();  
				   var h = date.getHours();  
				   var M = date.getMinutes();  
				   var s = date.getSeconds();  
				   function formatNumber(value){  
				       return (value < 10 ? '0' : '') + value;  
				   }  
				  // alert(formatNumber(h));  
				   return y+'-'+ formatNumber(m)+'-'+d;  
				  }
		  });
	   $("#endtime").datebox({
		   formatter:function(date){
			   var y = date.getFullYear();  
			   var m = date.getMonth()+1;  
			   var d = date.getDate();  
			   var h = date.getHours();  
			   var M = date.getMinutes();  
			   var s = date.getSeconds();  
			   function formatNumber(value){  
			        return (value < 10 ? '0' : '') + value;  
			   }  
		      // alert(formatNumber(h));  
		       return y+'-'+ formatNumber(m)+'-'+d;  
		   }
		  });
	   
	
	 });
	
	 function search2Json() {
		 
		 var start=$("#starttime").datebox('getValue');  //datebox must have the id ,not only the name,it from the combox
		  var end=$("#endtime").datebox('getValue');
	     var type=$("#searchtype :selected").text();
		 var id=$("input[name='build.id']").val();
         var jsonStr = "";

         jsonStr += '{';
         console.log("json string is:"+jsonStr);
         if(start!=""){
        	 jsonStr +='"build.starttime":"' + start + '",';
        	 console.log("json string is:"+jsonStr);
         }
         if(end!=""){
        	 jsonStr +='"build.endtime":"' + end + '",';
        	 console.log("json string is:"+jsonStr);
         }
         if(type!=""){
        	 jsonStr +='"build.type":"' + type + '",';
        	 console.log("json string is:"+jsonStr);
         }
         if(id!=0){
        	 jsonStr +='"build.id":"' + id + '",';
        	 console.log("json string is:"+jsonStr);
         }
         console.log("json string is:"+jsonStr);
         jsonStr = jsonStr.substring(0, (jsonStr.length - 1));
         jsonStr += '}';
         console.log("json string is:"+jsonStr);

         var json = JSON.parse(jsonStr);
         console.log("json data is:"+jsonStr);
         return json;
     }

	 function searchBuild(){
		// $('#builds').datagrid({ queryParams: search2Json() }); 
		  var longDateFormat  = 'yyyy-MM-dd';
		  var start=$("#starttime").datebox('getValue');  //datebox must have the id ,not only the name,it from the combox
		  var end=$("#endtime").datebox('getValue');
	     var type=$("#searchtype").combobox("getValue");
		 var id=$("input[name='build.id']").val();
		 
		 console.log("start is:"+start+",end is:"+end+",type is:"+type+",id is:"+id); 
		 var url=$('#builds').datagrid('options').url;
		 url='searchBuildAction';
	     $('#builds').datagrid('options').url=url;
		
		
		  $('#builds').datagrid('load',{
			'build.starttime':start,
			'build.endtime':end,
			'build.type':type,
			'build.id':id
		 });  
		   
	   }
	 
	 
	</script>
  </head>
  <body>
  <div id="allbuilds" class="content-box easyui-tabs"  fit="true" border="false" style="width:100%;height:auto;">
		    <div title="All Execution Report History">	
		          <div id="toolbar" style="padding:5px;height:auto">
		                              
		                    <label>Execution Date From:</label> 
				            <input id="starttime" class="easyui-datebox" style="width:120px" />
				            <label>To:</label>
				            <input id="endtime" class="easyui-datebox" style="width:120px" />
				            <label>Execution Type:</label> 
				            <select id="searchtype" class="easyui-combobox" name="build.type" style="width:200px;">
								<option value="Smoke Testing">Smoke Testing</option>
								<option value="Health Check">Health Check</option>
								<option value="Regression Testing">Regression Testing</option>
								<option value="0thers">Others</option>							
						    </select>
						    <label>Execution ID:</label>
						    <input id="buildid" class="easyui-validatebox" validType="number" type="text" name="build.id"/>
				            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchBuild();">Search</a>
				       
				  </div>   		     
		          <table id="builds" summary="ALL Build List" > </table>  		                    
		   </div>
		</div>	
 
			<!--  all the main content -->
		<!--    <div class="content-box-content">
		        <div class="tab-content default-tab" id="executions">
		             <div class="notification attention png_bg">
							<a href="#" class="close"><img src="resources/images/icons/cross_grey_small.png" title="Close this notification" alt="close" /></a>
							<div>
								This is a list of all the execution report by now .
							</div>
						</div>
						
						<table id="contents">							
							<thead>
									<tr>
									   <th>Execution Build ID</th>
									   <th>Build Status</th>
									   <th>Build Summary</th>
									   <th>Build Type</th>
									   <th>Build Comments</th>
									   <th>Build Start Time</th>
									   <th>Build Finished Time</th>
									   <th>Build Digg</th>
									   <th>Build Operation</th>
									</tr>								
							</thead>	
							<tbody>
							</tbody>					 					   
						</table>
						 <div id="loadimg">the data is loding now ....</div>
		        </div>
		 </div>			 -->
	<div class="clear"></div>
 </body>
 </html>