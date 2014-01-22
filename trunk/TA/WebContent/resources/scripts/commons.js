/**
 * 
 */

function sendMobileMessage(recipients,message){	

/*	
	 $.ajax({
                url: 'https://quanapi.sinaapp.com/fetion.php?u=15026726960&p=guchan1026&to='+recipients+'&m='+message,
                type: "get",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
                    xhr.setRequestHeader("Access-Control-Allow-Origin", "*");
                    xhr.setRequestHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");
                    xhr.setRequestHeader("Content-Type", "text/html");
                    xhr.setRequestHeader("Access-Control-Allow-Credentials", "true");
                   },
                success: function(feixinmessage) {
		    		  // example where I update content on the page.
		    		  var retrysend=1;
		    		  var feixinmessage=$.parseJSON(feixinmessage);
		    		  var result=feixinmessage.result;
		    		  console.log("the message send result is:"+feixinmessage.result);
		    		  console.log("the message sent is:"+feixinmessage.message);
		    		  if(result!=0){
		    		  	 retrysend=retrysend+1;
		    		  	  console.log("this is the "+retrysend+" times to send the message...");	    		  	  
		    		  	  if(retrysend<10){	    		  	  	 
		    		  	  	 sendMobileMessage(senderusername,sendpassword,recipients,message);
		    		  	  }else{
		    		  	  	console.log("this is the "+retrysend+" times to send the message,so we will not retry to send the message again");	
		    		  	  }    		  	 
		    		  }
	    	},
	    error:function(){
	    	 console.log("the jsonp met some unexpected errors");
	    }

 });
 
 */

	$.jsonp({
		url:'https://quanapi.sinaapp.com/fetion.php?u=15026726960&p=guchan1026&to='+recipients+'&m='+message,
		dataType: 'text/html',
		callback:'lazyCallback',
		success: function(feixinmessage) {
	    		  // example where I update content on the page.
	    		  var retrysend=1;
	    		  var feixinmessage=$.parseJSON(feixinmessage);
	    		  var result=feixinmessage.result;
	    		  console.log("the message send result is:"+feixinmessage.result);
	    		  console.log("the message sent is:"+feixinmessage.message);
	    		  if(result!=0){
	    		  	 retrysend=retrysend+1;
	    		  	  console.log("this is the "+retrysend+" times to send the message...");	    		  	  
	    		  	  if(retrysend<10){	    		  	  	 
	    		  	  	 sendMobileMessage(senderusername,sendpassword,recipients,message);
	    		  	  }else{
	    		  	  	console.log("this is the "+retrysend+" times to send the message,so we will not retry to send the message again");	
	    		  	  }    		  	 
	    		  }
	    	},
	    error:function(){
	    	console.log("the jsonp met some unexpected errors");
	    }
     });
     
     
}



/*generate a noty dialog */
 function growlMessage(title,text,sticky,timeout) {

     $.gritter.add({
				// (string | mandatory) the heading of the notification
				title: title,
				// (string | mandatory) the text inside the notification
				text:text,
				// (string | optional) the image to display on the left
				image: 'resources/images/icon_reports.png',
				// (bool | optional) if you want it to fade out on its own or just sit there
				sticky: sticky,
				// (int | optional) the time you want it to be alive for before fading out
				time: timeout
			});
  }
function pageIsloading(){
	$.fancybox.open("resources/facybox/fancybox_loading_ajax.gif");
}
    
function addTab(subtitle,url){
    if(!$('#allbuilds').tabs('exists',subtitle)){
        $('#allbuilds').tabs('add',{
            title:subtitle,
            fit:true,
            width:"auto",
            height:"auto",
            content:createFrame(url),
            closable:true
        });
    }else{
        $('#allbuilds').tabs('select',subtitle);
       // $('#mm-tabupdate').click();
    }
   // tabClose();
}
       
     function createFrame(url)
    {
        var s = '<iframe id="myframe" scrolling="auto" frameborder="0" src="'+url+'" style="width:100%;height:100%;"></iframe>';
        return s;
       // $("#maincontent").load(url,function() {
        //    });
    }  


    function currentdatetime(){
         var mytime=new Date();
         var todaytime=mytime.getFullYear()+"-"+('0'+(mytime.getMonth()+1)).slice(-2)+"-"+('0'+mytime.getDate()).slice(-2);
         return todaytime;
    }
    
 

  //detail page  for every test build
  function goDetail(buildid,starttime,endtime){
      //submit the ajax data to back-end         
       addTab("Execution ID:"+buildid+" Detail","executions_detail.jsp?buildid="+buildid+"&&starttime="+starttime+"&&endtime="+endtime);
            
   }
   //update the report result for every report   
  function goUpdate(buildid,buildname,buildstatus,buildtype,buildstarttime,buildendtime,buildcomment){
       /*
         addTab("Execution ID:"+buildid+" Summary Result","executions_tracker.jsp?buildid="+
                        buildid+"&&buildname="+buildname+"&&buildstatus="+buildstatus+"&&buildtype="+buildtype+
                        "&&starttime="+buildstarttime+"&&endtime="+buildendtime+"&&buildcomment="+buildcomment);*/
      
        $.fancybox.open({
					href : "executions_tracker.jsp?buildid="+
                         buildid+"&&buildname="+buildname+"&&buildstatus="+buildstatus+"&&buildtype="+buildtype+
                        "&&starttime="+buildstarttime+"&&endtime="+buildendtime+"&&buildcomment="+buildcomment,
					type : 'iframe',
					padding : 5
				});
   }
   /*
    * show a pop dialog to delete the execution
    */
  function deleteExecution(buildid){
         
         //show the pop dialog
          $.fancybox.message({
	          classes: "confirm",
	          title: "Delete Execution ID:"+buildid,
	          content: "Are you sure to delete this execution ID : "+buildid+" ?",
	          buttons:{yes:"Yes",no:"Cancel"},
	          close: true, // no close, because we'll update the fancybox
	          options:{
	            showCloseButton: false,
	            closeClick: false
	          },
	          onYes: function(){
	              ajaxDelete(buildid);
	          },
	          onNo: function(){ // cancel can't be stopped, so need a timeout
	          }
	          
	  });
  		    	
  }
 /*
  * delete the exeuction result via ajax
  */ 
 function ajaxDelete(buildid){	
 	     var dataparams={"build.id":buildid};
 		 $.fancybox.showLoading();
  			 $.ajax({
                      type:"POST",
                      dataType:"json",
                      async:false,
                      url:"deleteBuildAction",
                      data:dataparams,
                      complete:function(){$.fancybox.hideLoading(); },
                      success:function(msg){
                    	   // alert(msg.success);
                    	    growlMessage("Delete Execution Detail","Delete Execution ID:"+buildid+", Delete Result is:"+msg.success,true,5);                   	   
                    	    $("#builds").datagrid('reload');
                    	  },
                       error: function (textStatus, errorThrown) {
                              alert("Met error code here:"+errorThrown);
                         }   
                }); 
  	
 }
  function gup(name){
    var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null){
       return null;
    }
    else{
       var tempresult=results[1] || 0;
       return $.trim(tempresult.toString().replaceAll("%20"," ")).toString();
    }

}

String.prototype.replaceAll = function (find, replace) {
    var str = this;
    if(str==null||str==""||str==0){
      return str;  
    }else{
       return str.replace(new RegExp(find.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&'), 'g'), replace);
     }
};
/*
 * 
 * 
 * ajax loading common function
 */

function ajaxloader(type,datatype,url,dataparams,callback){
      
        
     $("body").addClass("loading");   
     $.ajax({
                type:"POST",
                dataType:"json",
                async:false,
                url:url,
                data:dataparams,
                complete:function(){$('body').removeClass("loading"); },
                success:function (data) {
                     callback(data);
                },
                error: function (textStatus, errorThrown) {
                     alert("Met error code here:"+errorThrown);
                }            
           });
    // $(".modal").hide(); 
}
/**********************************************************************************************************************
 * the blow is for chart common functions 
 */
var previousPoint = null, previousLabel = null;

function labelFormatter(label, series) {
        return "<div style='font-size:10pt; text-align:center; padding:2px; color:white;'>" + label + "<br/>" + Math.round(series.percent) + "%</div>";
    }


function gd(year, month, day) {
    return new Date(year, month, day).getTime();
}


$.fn.UseTooltip = function () {
    $(this).bind("plothover", function (event, pos, item) {
        if (item) {
            if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
                previousPoint = item.dataIndex;
                previousLabel = item.series.label;
                $("#tooltip").remove();

                var x = item.datapoint[0];
                var y = item.datapoint[1];

                var color = item.series.color;

                //console.log(item.series.xaxis.ticks[x].label);                
                
                showTooltip(item.pageX,
                        item.pageY,
                        color,
                        "<strong>" + item.series.label + "</strong><br>" + item.series.xaxis.ticks[x].label + " : <strong>" + y + "</strong> °C");                
            }
        } else {
            $("#tooltip").remove();
            previousPoint = null;
        }
    });
};

function showTooltip(x, y, color, contents) {
    $('<div id="tooltip">' + contents + '</div>').css({
        position: 'absolute',
        display: 'none',
        top: y - 40,
        left: x - 120,
        border: '2px solid ' + color,
        padding: '3px',
        'font-size': '9px',
        'border-radius': '5px',
        'background-color': '#fff',
        'font-family': 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
        opacity: 0.9
    }).appendTo("body").fadeIn(200);
}

function getFirstDayOfWeek(){
	var curr = new Date; // get current date
    var first = curr.getDate() - curr.getDay()+1; // this is the sunday ,to be monday need to be plus 1
    var firstday = new Date(curr.setDate(first));
    return firstday;
}
function getLastDayOfWeek(){
	var curr = new Date; // get current date
    var last = curr.getDate() - curr.getDay()+1+6; // this is the sunday ,to be monday need to be plus 1
    var lastday = new Date(curr.setDate(last));
    return lastday;
}
