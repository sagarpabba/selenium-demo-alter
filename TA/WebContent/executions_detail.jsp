<html xmlns:v="urn:schemas-microsoft-com:vml"
xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:w="urn:schemas-microsoft-com:office:word"
xmlns:m="http://schemas.microsoft.com/office/2004/12/omml"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=windows-1252">
<meta name=ProgId content=Word.Document>
<meta name=Generator content="Microsoft Word 15">
<meta name=Originator content="Microsoft Word 15">
<link rel=File-List href="executions_detail_files/filelist.xml">
<title>Testing Administration | Execution Detail</title>
<link rel="Shortcut Icon" href="resources/images/icon_reports.png">
<link rel=Stylesheet type="text/css" media=all href="resources/css/iframe.css">

<style>
<!--
 /* Font Definitions */
 html { overflow-x:hidden; } 
 @font-face
	{font-family:"Cambria Math";
	panose-1:2 4 5 3 5 4 6 3 2 4;
	mso-font-charset:1;
	mso-generic-font-family:roman;
	mso-font-format:other;
	mso-font-pitch:variable;
	mso-font-signature:0 0 0 0 0 0;}
@font-face
	{font-family:Cambria;
	panose-1:2 4 5 3 5 4 6 3 2 4;
	mso-font-charset:0;
	mso-generic-font-family:roman;
	mso-font-pitch:variable;
	mso-font-signature:-536870145 1073743103 0 0 415 0;}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{mso-style-unhide:no;
	mso-style-qformat:yes;
	mso-style-parent:"";
	margin:0in;
	margin-bottom:.0001pt;
	mso-pagination:widow-orphan;
	font-size:12.0pt;
	font-family:"Times New Roman","serif";
	mso-fareast-font-family:"Times New Roman";
	mso-fareast-theme-font:minor-fareast;}
a:link, span.MsoHyperlink
	{mso-style-noshow:yes;
	mso-style-priority:99;
	color:blue;
	text-decoration:underline;
	text-underline:single;}
a:visited, span.MsoHyperlinkFollowed
	{mso-style-noshow:yes;
	mso-style-priority:99;
	color:#954F72;
	text-decoration:underline;
	text-underline:single;}
p.MsoNoSpacing, li.MsoNoSpacing, div.MsoNoSpacing
	{mso-style-priority:1;
	mso-style-unhide:no;
	mso-style-qformat:yes;
	mso-margin-top-alt:auto;
	margin-right:0in;
	mso-margin-bottom-alt:auto;
	margin-left:0in;
	mso-pagination:widow-orphan;
	font-size:12.0pt;
	font-family:"Times New Roman","serif";
	mso-fareast-font-family:"Times New Roman";
	mso-fareast-theme-font:minor-fareast;}
span.GramE
	{mso-style-name:"";
	mso-gram-e:yes;}
.MsoChpDefault
	{mso-style-type:export-only;
	mso-default-props:yes;
	font-size:10.0pt;
	mso-ansi-font-size:10.0pt;
	mso-bidi-font-size:10.0pt;}
@page WordSection1
	{size:8.5in 11.0in;
	margin:1.0in 1.25in 1.0in 1.25in;
	mso-header-margin:.5in;
	mso-footer-margin:.5in;
	mso-paper-source:0;}
div.WordSection1
	{page:WordSection1;}
-->
</style>
<!--[if gte mso 10]>
<style>
 /* Style Definitions */
 table.MsoNormalTable
	{mso-style-name:"Table Normal";
	mso-tstyle-rowband-size:0;
	mso-tstyle-colband-size:0;
	mso-style-noshow:yes;
	mso-style-priority:99;
	mso-style-parent:"";
	mso-padding-alt:0in 5.4pt 0in 5.4pt;
	mso-para-margin:0in;
	mso-para-margin-bottom:.0001pt;
	mso-pagination:widow-orphan;
	font-size:10.0pt;
	font-family:"Times New Roman","serif";}
</style>
<![endif]-->


<script  type="text/javascript" src="resources/scripts/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="resources/facybox/jquery.fancybox.js?v=2.1.5"></script>
<script  type="text/javascript" src="resources/scripts/jquery.mb.browser.min.js"></script>
<script  type="text/javascript" src="resources/scripts/jquery.autoheight.js"></script>

<link rel="stylesheet" type="text/css" href="resources/facybox/jquery.fancybox.css?v=2.1.5" media="screen" />

<script  type="text/javascript" src="resources/scripts/commons.js"></script>
	<!-- scroll up plugin  -->
<link id="scrollUpTheme" rel="stylesheet" href="resources/scrollup/css/themes/image.css">
<link id="scrollUpTheme" rel="stylesheet" href="resources/scrollup/css/labs.css">

<script  type="text/javascript" src="resources/scrollup/jquery.scrollUp.js"></script>

<script  type="text/javascript">
    $(function(){
    	
    	//scroll up
    	$.scrollUp({
		        scrollName: 'scrollUp', // Element ID
		        scrollDistance: 300, // Distance from top/bottom before showing element (px)
		        scrollFrom: 'top', // 'top' or 'bottom'
		        scrollSpeed: 300, // Speed back to top (ms)
		       // easingType: 'linear', // Scroll to top easing (see http://easings.net/)
		        animation: 'fade', // Fade, slide, none
		        animationInSpeed: 200, // Animation in speed (ms)
		        animationOutSpeed: 200, // Animation out speed (ms)
		        scrollText: 'TO TOP', // Text for element, can contain HTML
		        scrollTitle: false, // Set a custom <a> title if required. Defaults to scrollText
		        scrollImg: true, // Set true to use image
		        activeOverlay: false, // Set CSS color to display scrollUp active point, e.g '#00FFFF'
		        zIndex: 2147483647 // Z-Index for the overlay
			});
    	//send the data to server and response the data
    	var buildid=gup("buildid");
    	var starttime=gup("starttime");
    	//var tempdate=dateFormat(starttime, 'isoUtcDateTime');
    	var endtime=gup("endtime");
    	var dataparas={"buildid":buildid,"starttime":starttime,"endtime":endtime};
    	//$.fancybox.showLoading();
    	$.ajax({
            type:"POST",
            dataType:"json",
            async:false,
            url:"listBuildResult",
            data:dataparas,
            complete:function(){$.fancybox.hideLoading(); },
            success:function(msg){
				//parse the return data
				var buildstatus=msg.buildstatus;
				var totalnumbers=msg.totalnumbers;
				var totaltime=msg.totaltime;
				var passed=msg.passed;
				var failed=msg.failed;
				var warning=msg.warning;
				
				var steps=msg.steps;
				var values=msg.values;
				var steptr = $("#steprow"); 
				var valuetr= $("#datarow");
				var getstepid=0;
				
				$.each(steps,function(stepindex,stepdata){
					getstepid=getstepid+1;
					var stepid=stepdata.stepid;
					//alert(stepid);
					if(stepdata.stepname!=""){
					    var stepname=stepdata.stepname+"|Run From:"+stepdata.createtime;
					}else{
						var stepname=stepdata.stepname;
					}
					var stepdesc=stepdata.stempdesc;
					var stepstatus=stepdata.status;
					var stepcomment=stepdata.comment;
					
					var clonetr=steptr.clone(); 
					
					clonetr.children("td").each(function(inner_index){  
                      
                      //data index
                           
                           // console.log("get step id is:"+getstepid);
                            switch(inner_index){  
                                  case(0):   
                                     $(this).text(getstepid);                                     
                                     break;  
                                  case(1):  
                                     $(this).text(stepname);  
                                     break;  
                                 case(2):  
                                     $(this).text(stepdesc);  
                                     break;  
                                 case(3): 
                                	 if(stepstatus.indexOf("Passed") !== -1){
                                		 var oldstyle=$(this).attr("style");
                                		 var newstyle=oldstyle+";background:#00B050;";
                                		 $(this).attr("style",newstyle);
                                	 }else if(stepstatus.indexOf("Failed") !== -1){
                                		 var oldstyle=$(this).attr("style");
                                		 var newstyle=oldstyle+";background:#C00000;";
                                		 $(this).attr("style",newstyle);
                                	 }else if(stepstatus.indexOf("Warning") !== -1){
                                		 var oldstyle=$(this).attr("style");
                                		 var newstyle=oldstyle+";background:yellow;";
                                		 $(this).attr("style",newstyle);
                                	 }else{
                                		 
                                	 }
                                     $(this).text(stepstatus);  
                                     break;  
                                 case(4):  
                                     $(this).text(stepcomment);  
                                     break;  
                
                           }//end switch                          
            });//end children.each  
          
            clonetr.appendTo($("#steptable"));                 
           // alert(steptr);
				});
				
				$.each(values,function(valueindex,valuedata){
					var dataname=valuedata.dataname;
					var datavalue=valuedata.datavalue;
					var clonevaluetr=valuetr.clone();  
					clonevaluetr.children("td").each(function(inner_index){  
						
                      //data index
                            switch(inner_index){  
                                  case(0):   
                                     $(this).text(dataname);  
                                     break;  
                                  case(1):  
                                     $(this).text(datavalue);  
                                     break;                     
                           }//end switch                          
                 });//end children.each  
          
                 clonevaluetr.appendTo($("#valuetable"));  
				});
				
				updateSummaryTable(totaltime,totalnumbers,passed,failed,warning);
				steptr.hide();
				valuetr.hide();	
				
				$("#summarytable").show();
				$("#steptable").show();
				$("#valuetable").show();
				
            } //end sucess tag	
		
            });
    	
  /*  	
    	 $.ajax({
 			type:'post',
 			dataType:'json',
 			data:dataparas,
 			url:'listBuildResult',
 			success:function(msg){
 				//parse the return data
 				var buildstatus=msg.buildstatus;
 				var totalnumbers=msg.totalnumbers;
 				var totaltime=msg.totaltime;
 				var passed=msg.passed;
 				var failed=msg.failed;
 				var warning=msg.warning;
 				
 				var steps=msg.steps;
 				var values=msg.values;
 				var steptr = $("#steprow"); 
 				var valuetr= $("#datarow");
 				
 				$.each(steps,function(stepindex,stepdata){
 					var stepid=stepdata.stepid;
 					//alert(stepid);
 					if(stepdata.stepname!=""){
 					    var stepname=stepdata.stepname+"|Run From:"+stepdata.createtime;
 					}else{
 						var stepname=stepdata.stepname;
 					}
 					var stepdesc=stepdata.stempdesc;
 					var stepstatus=stepdata.status;
 					var stepcomment=stepdata.comment;
 					
 					var clonetr=steptr.clone();  
 					clonetr.children("td").each(function(inner_index){  
                          
                          //data index
                                switch(inner_index){  
                                      case(0):   
                                         $(this).text(stepid);  
                                         break;  
                                      case(1):  
                                         $(this).text(stepname);  
                                         break;  
                                     case(2):  
                                         $(this).text(stepdesc);  
                                         break;  
                                     case(3): 
                                    	 if(stepstatus.indexOf("Passed") !== -1){
                                    		 var oldstyle=$(this).attr("style");
                                    		 var newstyle=oldstyle+";background:#00B050;";
                                    		 $(this).attr("style",newstyle);
                                    	 }else if(stepstatus.indexOf("Failed") !== -1){
                                    		 var oldstyle=$(this).attr("style");
                                    		 var newstyle=oldstyle+";background:#C00000;";
                                    		 $(this).attr("style",newstyle);
                                    	 }else if(stepstatus.indexOf("Warning") !== -1){
                                    		 var oldstyle=$(this).attr("style");
                                    		 var newstyle=oldstyle+";background:yellow;";
                                    		 $(this).attr("style",newstyle);
                                    	 }else{
                                    		 
                                    	 }
                                         $(this).text(stepstatus);  
                                         break;  
                                     case(4):  
                                         $(this).text(stepcomment);  
                                         break;  
                    
                               }//end switch                          
                });//end children.each  
              
                clonetr.appendTo($("#steptable"));                 
               // alert(steptr);
 				});
 				
 				$.each(values,function(valueindex,valuedata){
 					var dataname=valuedata.dataname;
 					var datavalue=valuedata.datavalue;
 					var clonevaluetr=valuetr.clone();  
 					clonevaluetr.children("td").each(function(inner_index){  
 						
                          //data index
                                switch(inner_index){  
                                      case(0):   
                                         $(this).text(dataname);  
                                         break;  
                                      case(1):  
                                         $(this).text(datavalue);  
                                         break;                     
                               }//end switch                          
                     });//end children.each  
              
                clonevaluetr.appendTo($("#valuetable"));  
 				});
 				
 				updateSummaryTable(totaltime,totalnumbers,passed,failed,warning);
 				steptr.hide();
 				valuetr.hide();	
 				
 				$("#summarytable").show();
 				$("#steptable").show();
 				$("#valuetable").show();
 				
 				
 			}
    	 }); //ajax end tag
    	 
    */
    	 
    	 function updateSummaryTable(totaltime,totalrun,passed,failed,warning){
    		// alert("insert into "+totaltime);
    		 $("#totaltime").text(totaltime);
    		 $("#totalrun").text(totalrun);
    		 $("#passed").text(passed);
    		 $("#failed").text(failed);
    		 $("#warning").text(warning);
    		 
    	 }
    	 
    	 //add the page comments
    	/*  $("#comments").EasyComment({
    	      path:"/TA/comments/", //Change it to the folder where you put the easycomment files
    	      moderate:false,
    	      maxReply:5,
    	      countPerPage:10
    	    }); */
    	 //scroll up
    	
  
    }); 
</script>
<!--[if gte mso 9]><xml>
 <o:shapedefaults v:ext="edit" spidmax="1026"/>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <o:shapelayout v:ext="edit">
  <o:idmap v:ext="edit" data="1"/>
 </o:shapelayout></xml><![endif]-->
</head>

<body lang=EN-US link=blue vlink="#954F72" style='tab-interval:.5in'>
<div class="modal"></div>
<div class=WordSection1>

<table class=MsoNormalTable border=0 cellspacing=4 cellpadding=0 width="90%"
 style='width:90.52%;mso-cellspacing:3.0pt;mso-yfti-tbllook:1184;mso-padding-alt:
 0in 0in 0in 0in'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes;
  height:8.4pt'>
  <td width="99%" style='width:99.36%;border:none;border-top:solid #D7410B 3.0pt;
  padding:3.0pt 3.0pt 3.0pt 3.0pt;height:8.4pt'></td>
 </tr>
</table>

<p class=MsoNoSpacing><span lang=EN-IE style='background:yellow;mso-highlight:
yellow;mso-ansi-language:EN-IE'>Note: This email is an automatically generated
summary report for Proactive Care environment smoke test. The email is designed
to provide early warning of environmental failures requiring corrective action.
Given the testing complexity and nature of automated testing, the summary
information provided in this email may be insufficient to determine the cause
of &quot;Failed&quot; and &quot;No Run&quot; results. In such circumstances,
the detailed test execution information available within the associated ALM
test suite should be reviewed by the following links: </span><a
href="td://support_automation.ts_dev.qc1d.atlanta.hp.com/qcbin/TestLabModule-000000003649890581?EntityType=ITestSet&amp;EntityID=90823">Test
Set ID: 90823</a> PAF_SmokeTesting_AllDevices_pdeauto06.fc.hp.com or <a
href="file:///\\pdeauto06.fc.hp.com\PAF_Automation\Test_Reports">http://pdeauto06.fc.hp.com/TA/index.jsp</a>
<o:p></o:p></p>

<u1:p></u1:p>

<p class=MsoNoSpacingCxSpMiddle style='margin-left:.5in;mso-add-space:auto;
text-indent:-.25in'><span style='font-size:7.0pt;mso-fareast-font-family:Symbol;
mso-bidi-font-weight:bold'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
style='font-size:14.0pt;font-family:"Cambria","serif";mso-fareast-font-family:
"Times New Roman";mso-fareast-theme-font:major-fareast;mso-bidi-font-family:
"Times New Roman";mso-bidi-theme-font:major-bidi'>Testing Execution Overview <u1:p></u1:p></span></b><o:p></o:p></p>

<table  id="summarytable" class=MsoNormalTable border=0 cellspacing=0 cellpadding=0 width=1297
 style='margin-left:-1.15pt;border-collapse:collapse;mso-table-layout-alt:
 fixed;mso-yfti-tbllook:1184;mso-padding-alt:0in 5.4pt 0in 5.4pt'
>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;height:20.65pt'>
  <td width=229 valign=bottom style='width:171.45pt;border:solid windowtext 1.0pt;
  border-bottom:solid windowtext 1.5pt;mso-border-alt:solid windowtext .5pt;
  mso-border-bottom-alt:solid windowtext 1.5pt;background:#0070C0;padding:0in 5.4pt 0in 5.4pt;
  height:20.65pt'>
  <p class=MsoNoSpacing><b><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";color:white;
  mso-hide:all'>Run Summary<u1:p></u1:p></span></b><span style='
  mso-hide:all'><o:p></o:p></span></p>
  </td>
  <td width=201 valign=bottom style='width:150.95pt;border-top:solid windowtext 1.0pt;
  border-left:none;border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-bottom-alt:solid windowtext 1.5pt;
  mso-border-right-alt:solid windowtext .5pt;background:#0070C0;padding:0in 5.4pt 0in 5.4pt;
  height:20.65pt'>
  <p class=MsoNoSpacing><b><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";color:white;
  mso-hide:all'>Total Run Time<u1:p></u1:p></span></b><span style='display:
  none;mso-hide:all'><o:p></o:p></span></p>
  </td>
  <td width=188 valign=bottom style='width:141.1pt;border-top:solid windowtext 1.0pt;
  border-left:none;border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-bottom-alt:solid windowtext 1.5pt;
  mso-border-right-alt:solid windowtext .5pt;background:#0070C0;padding:0in 5.4pt 0in 5.4pt;
  height:20.65pt'>
  <p class=MsoNoSpacing><b><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";color:white;
  mso-hide:all'>Total Run Tests<u1:p></u1:p></span></b><span style='display:
  none;mso-hide:all'><o:p></o:p></span></p>
  </td>
  <td width=178 valign=bottom style='width:133.45pt;border-top:solid windowtext 1.0pt;
  border-left:none;border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-bottom-alt:solid windowtext 1.5pt;
  mso-border-right-alt:solid windowtext .5pt;background:#0070C0;padding:0in 5.4pt 0in 5.4pt;
  height:20.65pt'>
  <p class=MsoNoSpacing><b><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";color:white;
  mso-hide:all'>Passed Tests<u1:p></u1:p></span></b><span style='
  mso-hide:all'><o:p></o:p></span></p>
  </td>
  <td width=163 valign=bottom style='width:122.05pt;border-top:solid windowtext 1.0pt;
  border-left:none;border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-bottom-alt:solid windowtext 1.5pt;
  mso-border-right-alt:solid windowtext .5pt;background:#0070C0;padding:0in 5.4pt 0in 5.4pt;
  height:20.65pt'>
  <p class=MsoNoSpacing><b><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";color:white;
  mso-hide:all'>Failed Tests<u1:p></u1:p></span></b><span style='
  mso-hide:all'><o:p></o:p></span></p>
  </td>
  <td width=175 valign=bottom style='width:131.1pt;border-top:solid windowtext 1.0pt;
  border-left:none;border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-bottom-alt:solid windowtext 1.5pt;
  mso-border-right-alt:solid windowtext .5pt;background:#0070C0;padding:0in 5.4pt 0in 5.4pt;
  height:20.65pt'>
  <p class=MsoNoSpacing><b><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";color:white;
  mso-hide:all'>No Run Tests<u1:p></u1:p></span></b><span style='
  mso-hide:all'><o:p></o:p></span></p>
  </td>
  <td width=163 valign=bottom style='width:122.6pt;border-top:solid windowtext 1.0pt;
  border-left:none;border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-bottom-alt:solid windowtext 1.5pt;
  mso-border-right-alt:solid windowtext .5pt;background:#0070C0;padding:0in 5.4pt 0in 5.4pt;
  height:20.65pt'>
  <p class=MsoNoSpacing><b><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";color:white;
  mso-hide:all'>Warning Tests<u1:p></u1:p></span></b><span style='
  mso-hide:all'><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:1;mso-yfti-lastrow:yes;height:26.5pt'>
  <td width=229 valign=bottom style='width:171.45pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext 1.5pt;mso-border-alt:
  solid windowtext .5pt;mso-border-top-alt:solid windowtext 1.5pt;padding:0in 5.4pt 0in 5.4pt;
  height:26.5pt'>
  <p class=MsoNoSpacing><b><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";mso-hide:all'>PAF
  Automation Status<o:p></o:p></span></b></p>
  </td>
  <td width=201 valign=bottom style='width:150.95pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext 1.5pt;mso-border-top-alt:solid windowtext 1.5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0in 5.4pt 0in 5.4pt;height:26.5pt'>
  <p class=MsoNoSpacing><b><span ><span id="totaltime" style='font-size:13.0pt;
  font-family:"Cambria","serif";mso-fareast-font-family:"Times New Roman";
  mso-hide:all'>0 minutes<o:p></o:p></span></b></p>
  </td>
  </span>
  <td width=188 valign=bottom style='width:141.1pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext 1.5pt;mso-border-top-alt:solid windowtext 1.5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0in 5.4pt 0in 5.4pt;height:26.5pt'>
  <p class=MsoNoSpacing><b><span id="totalrun"><span style='font-size:13.0pt;
  font-family:"Cambria","serif";mso-fareast-font-family:"Times New Roman";
  mso-hide:all'><o:p></o:p></span></b></p>
  </td>
  </span>
  <td width=178 valign=bottom style='width:133.45pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext 1.5pt;mso-border-top-alt:solid windowtext 1.5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  background:#00B050;padding:0in 5.4pt 0in 5.4pt;height:26.5pt'>
  <p class=MsoNoSpacing><b><span id="passed"><span style='font-size:13.0pt;
  font-family:"Cambria","serif";mso-fareast-font-family:"Times New Roman";
  mso-hide:all'><o:p></o:p></span></b></p>
  </td>
  </span>
  <td width=163 valign=bottom style='width:122.05pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext 1.5pt;mso-border-top-alt:solid windowtext 1.5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  background:#C00000;padding:0in 5.4pt 0in 5.4pt;height:26.5pt'>
  <p class=MsoNoSpacing><b><span id="failed"><span style='font-size:13.0pt;
  font-family:"Cambria","serif";mso-fareast-font-family:"Times New Roman";
  mso-hide:all'><o:p></o:p></span></b></p>
  </td>
  </span>
  <td width=175 valign=bottom style='width:131.1pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext 1.5pt;mso-border-top-alt:solid windowtext 1.5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  background:yellow;padding:0in 5.4pt 0in 5.4pt;height:26.5pt'>
  <p class=MsoNoSpacing><b><span id="norun"><span style='font-size:13.0pt;
  font-family:"Cambria","serif";mso-fareast-font-family:"Times New Roman";
  mso-hide:all'>0<o:p></o:p></span></b></p>
  </td>
  </span>
  <td width=163 valign=bottom style='width:122.6pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext 1.5pt;mso-border-top-alt:solid windowtext 1.5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  background:yellow;padding:0in 5.4pt 0in 5.4pt;height:26.5pt'>
  <p class=MsoNoSpacing><b><span id="warning"><span style='font-size:13.0pt;
  font-family:"Cambria","serif";mso-fareast-font-family:"Times New Roman";
  mso-hide:all'><o:p></o:p></span></b></p>
  </td>
 </tr>
</table>

<p class=MsoNoSpacingCxSpFirst style='margin-left:.5in;mso-add-space:auto;
text-indent:-.25in'><span style='font-size:7.0pt;mso-fareast-font-family:Symbol;
mso-bidi-font-weight:bold'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
style='font-size:14.0pt;font-family:"Cambria","serif";mso-fareast-font-family:
"Times New Roman";mso-fareast-theme-font:major-fareast;mso-bidi-font-family:
"Times New Roman";mso-bidi-theme-font:major-bidi'>Testing result Details<u1:p></u1:p></span></b><o:p></o:p></p>

<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0 width=1297
 style='width:972.7pt;margin-left:-1.15pt;border-collapse:collapse;display:
 none;mso-yfti-tbllook:1184;mso-padding-alt:0in 5.4pt 0in 5.4pt' id="steptable">
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;height:26.5pt'>
  <td width=1297 colspan=5 valign=bottom style='width:972.7pt;border:solid windowtext 1.0pt;
  mso-border-alt:solid windowtext .5pt;background:#0070C0;padding:0in 5.4pt 0in 5.4pt;
  height:26.5pt'>
  <p class=MsoNoSpacing><b><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";color:white;
  mso-hide:all'>Proactive Care Environment Smoke Test<u1:p></u1:p></span></b><span
  style='mso-hide:all'><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:1;height:19.75pt'>
  <td width=43 valign=bottom style='width:32.05pt;border-top:none;border-left:
  solid windowtext 1.0pt;border-bottom:solid windowtext 1.5pt;border-right:
  solid windowtext 1.0pt;mso-border-left-alt:solid windowtext .5pt;mso-border-bottom-alt:
  solid windowtext 1.5pt;mso-border-right-alt:solid windowtext .5pt;background:
  #0070C0;padding:0in 5.4pt 0in 5.4pt;height:19.75pt'>
  <p class=MsoNoSpacing><b><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";color:white;
  mso-hide:all'>ID<u1:p></u1:p></span></b><span style='mso-hide:
  all'><o:p></o:p></span></p>
  </td>
  <td width=202 valign=bottom style='width:151.5pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.0pt;
  mso-border-bottom-alt:solid windowtext 1.5pt;mso-border-right-alt:solid windowtext .5pt;
  background:#0070C0;padding:0in 5.4pt 0in 5.4pt;height:19.75pt'>
  <p class=MsoNoSpacing><b><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";color:white;
  mso-hide:all'>Steps to Perform<u1:p></u1:p></span></b><span style='display:
  none;mso-hide:all'><o:p></o:p></span></p>
  </td>
  <td width=306 valign=bottom style='width:229.4pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.0pt;
  mso-border-bottom-alt:solid windowtext 1.5pt;mso-border-right-alt:solid windowtext .5pt;
  background:#0070C0;padding:0in 5.4pt 0in 5.4pt;height:19.75pt'>
  <p class=MsoNoSpacing><b><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";color:white;
  mso-hide:all'>Checks to Perform<u1:p></u1:p></span></b><span
  style='mso-hide:all'><o:p></o:p></span></p>
  </td>
  <td width=74 nowrap valign=bottom style='width:55.8pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.0pt;
  mso-border-bottom-alt:solid windowtext 1.5pt;mso-border-right-alt:solid windowtext .5pt;
  background:#0070C0;padding:0in 5.4pt 0in 5.4pt;height:19.75pt'>
  <p class=MsoNoSpacing><b><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";color:white;
  mso-hide:all'>Status<u1:p></u1:p></span></b><span style='
  mso-hide:all'><o:p></o:p></span></p>
  </td>
  <td width=672 nowrap valign=bottom style='width:503.95pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.0pt;
  mso-border-bottom-alt:solid windowtext 1.5pt;mso-border-right-alt:solid windowtext .5pt;
  background:#0070C0;padding:0in 5.4pt 0in 5.4pt;height:19.75pt'>
  <p class=MsoNoSpacing><b><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";color:white;
  mso-hide:all'>Comments<u1:p></u1:p></span></b><span style='
  mso-hide:all'><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:2;mso-yfti-lastrow:yes;height:27.0pt'
  id="steprow">
  <td width=43 valign=bottom style='width:32.05pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext 1.5pt;mso-border-alt:
  solid windowtext .5pt;mso-border-top-alt:solid windowtext 1.5pt;padding:0in 5.4pt 0in 5.4pt;
  height:27.0pt'>
  <p class=MsoNoSpacing><span id=stepid><span style='font-size:13.0pt;
  font-family:"Cambria","serif";mso-fareast-font-family:"Times New Roman";
  mso-hide:all;mso-bidi-font-weight:bold'>1<o:p></o:p></span></p>
  </td>
  </span>
  <td width=202 valign=bottom style='width:151.5pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext 1.5pt;mso-border-top-alt:solid windowtext 1.5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0in 5.4pt 0in 5.4pt;height:27.0pt'>
  <p class=MsoNoSpacing><b><span style='font-size:12.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";mso-hide:all'>test<o:p></o:p></span></b></b></p>
  </td>
  </span>
  <td width=306 valign=bottom style='width:229.4pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext 1.5pt;mso-border-top-alt:solid windowtext 1.5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0in 5.4pt 0in 5.4pt;height:27.0pt'>
  <p class=MsoNoSpacing><span id=stepdesc><span style='font-size:13.0pt;
  font-family:"Cambria","serif";mso-fareast-font-family:"Times New Roman";
  mso-hide:all;mso-bidi-font-weight:bold'>Verify login interface<o:p></o:p></span></p>
  </td>
  </span>
  <td width=74 nowrap valign=bottom style='width:55.8pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext 1.5pt;mso-border-top-alt:solid windowtext 1.5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0in 5.4pt 0in 5.4pt;height:27.0pt' id="stepstatus">
  <p class=MsoNoSpacing><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";mso-hide:all;
  mso-bidi-font-weight:bold'>Passed<o:p></o:p></span></p>
  </td>
  <td width=672 nowrap valign=bottom style='width:503.95pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext 1.5pt;mso-border-top-alt:solid windowtext 1.5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0in 5.4pt 0in 5.4pt;height:27.0pt'>
  <p class=MsoNoSpacing><span id=stepcomment><span style='font-size:11.0pt;
  font-family:"Cambria","serif";mso-fareast-font-family:"Times New Roman";
  mso-hide:all;mso-bidi-font-weight:bold'>This is a comments<o:p></o:p></span></p>
  </td>
 </tr>
</table>
<p class=MsoNoSpacingCxSpFirst style='margin-left:.5in;mso-add-space:auto;
text-indent:-.25in'><span style='font-size:7.0pt;mso-fareast-font-family:Symbol;
mso-bidi-font-weight:bold'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><b><span
style='font-size:14.0pt;font-family:"Cambria","serif";mso-fareast-font-family:
"Times New Roman";mso-fareast-theme-font:major-fareast;mso-bidi-font-family:
"Times New Roman";mso-bidi-theme-font:major-bidi'>Data Used During Test
Execution<u1:p></u1:p></span></b><o:p></o:p></p>

<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0 width=1297
 style='width:972.7pt;margin-left:-1.15pt;border-collapse:collapse;display:
 none;mso-yfti-tbllook:1184;mso-padding-alt:0in 5.4pt 0in 5.4pt' id="valuetable">
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;height:22.9pt'>
  <td width=247 valign=bottom style='width:185.25pt;border:solid windowtext 1.0pt;
  border-bottom:solid windowtext 1.5pt;mso-border-alt:solid windowtext .5pt;
  mso-border-bottom-alt:solid windowtext 1.5pt;background:#0070C0;padding:0in 5.4pt 0in 5.4pt;
  height:22.9pt'>
  <p class=MsoNoSpacing><b><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";color:white;
  mso-hide:all'>Data Description<u1:p></u1:p></span></b><span style='display:
  none;mso-hide:all'><o:p></o:p></span></p>
  </td>
  <td width=1050 valign=bottom style='width:787.45pt;border-top:solid windowtext 1.0pt;
  border-left:none;border-bottom:solid windowtext 1.5pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext .5pt;mso-border-bottom-alt:solid windowtext 1.5pt;
  mso-border-right-alt:solid windowtext .5pt;background:#0070C0;padding:0in 5.4pt 0in 5.4pt;
  height:22.9pt'>
  <p class=MsoNoSpacing><b><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";color:white;
  mso-hide:all'>Data Value<u1:p></u1:p></span></b><span style='
  mso-hide:all'><o:p></o:p></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:1;mso-yfti-lastrow:yes;height:22.9pt'
  id="datarow">
  <td width=247 valign=bottom style='width:185.25pt;border:solid windowtext 1.0pt;
  border-top:none;mso-border-top-alt:solid windowtext 1.5pt;mso-border-alt:
  solid windowtext .5pt;mso-border-top-alt:solid windowtext 1.5pt;padding:0in 5.4pt 0in 5.4pt;
  height:22.9pt'>
  <p class=MsoNoSpacing><span id=dataname><span style='font-size:13.0pt;
  font-family:"Cambria","serif";mso-fareast-font-family:"Times New Roman";
  mso-hide:all;mso-bidi-font-weight:bold'>Login user<o:p></o:p></span></p>
  </td>
  </span>
  <td width=1050 valign=bottom style='width:787.45pt;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  mso-border-top-alt:solid windowtext 1.5pt;mso-border-top-alt:solid windowtext 1.5pt;
  mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;
  padding:0in 5.4pt 0in 5.4pt;height:22.9pt'>
  <p class=MsoNoSpacing><span id=datavalue><span style='font-size:13.0pt;
  font-family:"Cambria","serif";mso-fareast-font-family:"Times New Roman";
  mso-hide:all;mso-bidi-font-weight:bold'>abcdefg<o:p></o:p></span></p>
  </td>
 </tr>
</table>

<p class=MsoNoSpacingCxSpFirst style='margin-left:.5in;mso-add-space:auto;
text-indent:-.25in'><span style='font-size:7.0pt;mso-fareast-font-family:Symbol;
mso-bidi-font-weight:bold'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
class=GramE><b><span style='font-size:14.0pt;font-family:"Cambria","serif";
mso-fareast-font-family:"Times New Roman";mso-fareast-theme-font:major-fareast;
mso-bidi-font-family:"Times New Roman";mso-bidi-theme-font:major-bidi'>If</span></b></span><b><span
style='font-size:14.0pt;font-family:"Cambria","serif";mso-fareast-font-family:
"Times New Roman";mso-fareast-theme-font:major-fareast;mso-bidi-font-family:
"Times New Roman";mso-bidi-theme-font:major-bidi'> the above testing met any
error, you can see the screenshots blow <u1:p></u1:p></span></b></p>

<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0 width=1297
 style='width:972.7pt;margin-left:-1.15pt;border-collapse:collapse;mso-yfti-tbllook:
 1184;mso-padding-alt:0in 5.4pt 0in 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes;
  height:25.6pt'>
  <td width=247 nowrap style='width:185.25pt;border:solid windowtext 1.0pt;
  mso-border-alt:solid windowtext .5pt;background:#0070C0;padding:0in 5.4pt 0in 5.4pt;
  height:25.6pt'>
  <p class=MsoNoSpacing><b><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";color:white'>Error Screenshot File
  Name<u1:p></u1:p></span></b></p>
  </td>
  <td width=1338 style='width:1003.5pt;border:solid windowtext 1.0pt;
  border-left:none;mso-border-top-alt:solid windowtext .5pt;mso-border-bottom-alt:
  solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;background:
  #0070C0;padding:0in 5.4pt 0in 5.4pt;height:25.6pt'>
  <p class=MsoNoSpacing><b><span style='font-size:13.0pt;font-family:"Cambria","serif";
  mso-fareast-font-family:"Times New Roman";color:white'>Screenshot<u1:p></u1:p></span></b></p>
  </td>
 </tr>
</table>

<p class=MsoNoSpacing><span style='font-size:10.0pt;font-family:"Arial","sans-serif";
color:#888888;background:white'><u1:p>&nbsp;</u1:p></span></p>

<p class=MsoNoSpacing><span style='font-size:10.0pt;font-family:"Arial","sans-serif";
color:#888888;background:white'>You received this message because you are
subscribed to the Proactive Care group:</span> <span style='color:#1F497D'><a
href="mailto:SAHC_PAF@groups.hp.com">SAHC_PAF@groups.hp.com</a></span><span
style='font-size:10.0pt;font-family:"Arial","sans-serif";color:#888888'><br>
<span style='background:white'><span style='orphans: auto;text-align:start;
widows: auto;-webkit-text-stroke-width: 0px;float:none;word-spacing:0px'>To
subscribe or unsubscribe from this group, please visit this page:</span></span></span><span
style='font-size:10.0pt;font-family:"Arial","sans-serif";color:#1F497D'> <a
href="http://pdebld1.fc.hp.com/Subscribe_SupportAutomationHealthCheckPAF.html">http://pdebld1.fc.hp.com/Subscribe_SupportAutomationHealthCheckPAF.html</a></span><span
style='font-size:10.0pt;font-family:"Arial","sans-serif";color:#888888'><br>
<span style='background:white'><span style='orphans: auto;text-align:start;
widows: auto;-webkit-text-stroke-width: 0px;float:none;word-spacing:0px'>For</span>
more details, visit<span class=apple-converted-space><span style='font-family:
"Arial","sans-serif"'> here:</span></span></span> <a
href="td://support_automation.ts_dev.qc1d.atlanta.hp.com/qcbin/TestLabModule-000000003649890581?EntityType=ITestSet&amp;EntityID=90823">Test
Set ID: 90823</a> PAF_SmokeTesting_AllDevices_pdeauto06.fc.hp.com<u1:p></u1:p></span><o:p></o:p></p>

</div>

<!--EndFragment --><!--EndFragment --><!--EndFragment -->
    <hr>
	<div class="comments" id="comments" style="width:1024px;height:800px;">	</div>
</body>

</html>
