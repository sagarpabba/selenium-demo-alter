<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Testing Administration | Execution Summary Report</title>
 <link rel="Shortcut Icon" href="resources/images/icon_reports.png" />
   
  <!-- easyui css file -->
<link rel="stylesheet" type="text/css" href="easyui_1.3.4/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="easyui_1.3.4/themes/default/easyui.css" />

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


<script type="text/javascript">
  $(function(){
	  
	    var longDateFormat  = 'MM/dd/yyyy HH:mm:ss';
		var buildid=gup("buildid");
		var buildname=gup("buildname");
		var buildstatus=gup("buildstatus");
		var buildtype=gup("buildtype");
		var starttime=$.format.date(gup("starttime"),longDateFormat);
		var endtime=$.format.date(gup("endtime"),longDateFormat);
		
		var buildcomment=gup("buildcomment");
		console.log("get the build comment is:"+buildcomment);
		if(buildcomment="0"){
			console.log("build comment is 0 ,reset to null");
			buildcomment="";
		}
    
	  	$("input[name='build.id']").val(buildid);
	  //	$("input[name='build.id']").prop("disabled",true);
		$("input[name='build.name']").val(buildname);
		
		//console.log("build type is :"+buildstatus);
	    $("input[name='build.status'][value='"+buildstatus+"']").attr("checked","checked");
	    var selectvalue="";
	    if(buildtype=="Smoke Testing"){
	    	selectvalue="st";
	    }else if(buildtype=="Health Check"){
	    	selectvalue="hc";
	    }else if(buildtype=="Regression Testing"){
	    	selectvalue="rt";
	    }else{
	    	selectvalue="others";
	    }
	  
	    $("select[name='build.type']").val(selectvalue);
	   // console.log("start time is:"+starttime);
	   // console.log("end time is:"+endtime);
	   // console.log("get value is:"+$("input[name='build.starttime']").attr("class"));
	    $("#starttime").datetimebox('setValue', starttime);
	    $("#endtime").datetimebox('setValue', endtime);	
	    $("textarea[name='build.comments']").val(buildcomment);
	        	 
  });
  

</script>
<script type="text/javascript">
//submit the function 
function submitExecution(){
            var buildid=$("input[name='build.id']").val();
            var buildname=$("input[name='build.name']").val();
            var buildstatus=$("input[name='build.status']:checked").val();
            var buildtype=$("select[name='build.type'] :selected").text();
            var starttime=$("#starttime").datetimebox('getValue');
            var endtime=$("#endtime").datetimebox('getValue');
            var comments=$("textarea[name='build.comments']").val();
            
            console.log("build id:"+buildid+",build name:"+buildname+",build status:"+buildstatus+",type is :"+buildtype);
            var dataparams={"build.id":buildid,"build.name":buildname,"build.status":buildstatus,"build.type":buildtype,"build.starttime":starttime,"build.endtime":endtime,"build.comments":comments};
           // ajaxloader("POST","json","newBuildAction",dataparams,function(msg){
            //    alert("message is now ");
             //   console.log("success update"+msg);
               // generateMsg("success","update the execution already!");           
          //  });
          $.fancybox.showLoading();
          $.ajax({
                      type:"POST",
                      dataType:"json",
                      async:false,
                      url:"newBuildAction",
                      data:dataparams,
                      complete:function(){$.fancybox.hideLoading(); },
                      success:function(msg){
                    	   // alert(msg.success);
                    	    parent.growlMessage("Update Execution Detail","Update Execution ID:"+buildid+", Update Result is:"+msg.success,false,5);
                    	    parent.$.fancybox.close();  
                    	    parent.$("#builds").datagrid('reload');
                    	  },
                       error: function (textStatus, errorThrown) {
                              alert("Met error code here:"+errorThrown);
                         }   
                }); 
           //location.reload();
    }


</script>
</head>
<body>
    
    <form id="frm" class="formee"  method="post">
	    <fieldset>
	        <legend>Execution</legend>
	        <div class="grid-12-12">
	                <label>Execution ID </label>
	                <input name="build.id" type="text" class="formee-medium" readOnly="readonly"> 
	        </div>
	        <div class="grid-12-12">
	                <label>Execution Summary Name <em class="formee-req">*</em></label>
	               <input name="build.name" type="text" class="formee-medium" value=""> 
	        </div>
	        <div class="grid-12-12">
	                <label>Execution Status <em class="formee-req">*</em></label>
	                <ul class="formee-list">
	                    <li><input name="build.status" type="radio" checked="checked" value="Passed"><label>Passed</label></li>
	                    <li><input name="build.status" type="radio" value="Failed"><label>Failed</label></li>
	                    <li><input name="build.status" type="radio" value="Warning"><label>Warning</label></li>
	                    <li><input name="build.status" type="radio" value="Not Completed"><label>Not Completed</label></li>
	                    <li><input name="build.status" type="radio" value="invalid"><label>Invalid Execution</label></li>
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
	                <label>Execution Start Time and End Time</label>
	               <input id="starttime" name="build.starttime" type="text" class="easyui-datetimebox formee-small" data-options="required:true,showSeconds:true" style="width:200px" value="">             
	               <input id="endtime" name="build.endtime" type="text" class="easyui-datetimebox formee-small" data-options="required:true,showSeconds:true" style="width:200px" value="">
	        </div>
	         <div class="grid-12-12">
	                <label>Execution Analysis Comments <em class="formee-req">*</em></label>
	               <textarea name="build.comments" rows="" cols=""></textarea>
	        </div>
	       <div class="grid-12-12">
	                <label>Execution New Defects(Seperated by , character) </label>
	               <textarea name="build.newdefects" rows="" cols=""></textarea>
	        </div>
	        <div class="grid-12-12">
	                <label>Execution Existing Defects Before(Seperated by , character) </label>
	               <textarea name="build.olddefects" rows="" cols=""></textarea>
	        </div>
	       
	        <div class="grid-12-12">
	               <input class="right"  type="submit"  onclick="submitExecution()" title="submit"  value="Submit Execution Now">
	        </div>
	    </fieldset>  
	</form>
</body>
</html>