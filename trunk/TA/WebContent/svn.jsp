<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <title>Testing Administration | SVN Code Track</title>
   <link rel="Shortcut Icon" href="resources/images/icon_reports.png" />
   
      <!-- easyui css file -->
	<link rel="stylesheet" type="text/css" href="easyui_1.3.4/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="easyui_1.3.4/themes/default/easyui.css" />
	
	<!-- <link rel="stylesheet" href="resources/css/reset.css" type="text/css" media="screen" /> -->
	<link rel="stylesheet" href="resources/css/style.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="resources/css/invalid.css" type="text/css" media="screen" />	
	
	<script type="text/javascript" src="resources/scripts/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="resources/scripts/jquery.mb.browser.min.js"></script>
	
	
	<script type="text/javascript" src="resources/scripts/commons.js"></script>
	 <!-- easyui js file -->
    <script type="text/javascript" src="easyui_1.3.4/jquery.easyui.min.js"></script>
	
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
		
	     $("#logs").datagrid({       
	         columns:[[
	               {field:'change_date',title:'Change Date',width:60},
	               {field:'change_revision',title:'Revision',width:20},
	              
	               {field:'change_author',title:'Author',width:26},
	               {field:'change_comment',title:'Change Comments',align:'center',width:100},
	               {field:'change_detail',title:'Detail Changes',align:'center',width:300,fixed:true,resizable:true}          
	        ]],
	        title:'',
	         
	        autoRowWidth:false,
	        autoRowHeight:true,
	        height: 'auto',    
	        nowrap: false,    
	        striped: false,    
	        border: true,    
	        collapsible:true,//    
	       // fit: true,
	        fitColumns:true,
	        url:'listReversionLog',
	        loadMsg:'Trying to load source code log data...',
	        onLoadError : function() {  
	            alert('Sorry ,the data loaded met failed...');  
	        },  
	        sortName:'change_date',
	        sortOrder:'desc',
	        multiSort:true,
	        //idField:'build_id',
	        singleSelect:true,
	        pageSize: 80,//every page number 
	        pageList: [80,160,240],//page every
	       // fitColumns:true,
	      //  frozenColumns:[[    
	        //      {field:'check',checkbox:false}
	        // ]],
	        pagination:true,    
	        rownumbers:true,
	        freezeRow:false,
	        toolbar:"#toolbarlog"
	        
	               
	       // toolbar: '#table_toolbar'
	    });
	     //pagenation
	    $('#logs').datagrid('getPager').pagination({    
	         
	         beforePageText: 'page ',//
	         afterPageText: 'of total {pages} pages',  
	         
	         autoRowHeight:true,
	         striped:true,
	         pagePosition:'top',
	        // idField:"build_id",
	         displayMsg: 'Current listed Logs From {from}-to {to} ,total {total} records' ,
	        onBeforeRefresh:function(pageNumber, pageSize){     
	            $(this).pagination('loading');     
	                //alert('pageNumber:'+pageNumber+',pageSize:'+pageSize);     
	           // $(this).pagination('loadings...');    
	        } 
	     });
	      
	    var tabs = $('#alllogs').tabs().tabs('tabs');
		 for(var i=0; i<tabs.length; i++){
		                tabs[i].panel('options').tab.unbind().bind('mouseenter',{index:i},function(e){
		                    $('#alllogs').tabs('select', e.data.index);
		                });
		   } 
	});
	 
	
	</script>
  </head>
  <body>
  <div id="alllogs">
	<div title="All SVN Activities">
		    <table id="logs" summary="ALL SVN log List" ></table> 
		    <div id="toolbarlog">
		         <label>Changes Made From:</label> 
				 <input class="easyui-datebox" style="width:200px" />
				 <label>To:</label>
				 <input class="easyui-datebox" style="width:200px" />
				 <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchLogs()">Search</a>
		   </div>            
	    </div>
   </div>
 </body>
 </html>