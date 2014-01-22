/* Wait for DOM to load etc */
$(document).ready(function(){

	//Initialisations
	
	initialise_calendar();
	initialise_color_pickers();
	initialise_buttons();
	//here actually list the database event titles
	
	initialise_event_generation();
	
	initialise_update_event();
	initialise_ajax_event();
});


/* Initialise buttons */
function initialise_buttons(){

	//$('.btn').button();
}


/* Binds and initialises event generation functionality */
function initialise_event_generation(){

	//Bind event
	$('#btn_gen_event').bind('click', function(){

		//Retrieve template event
		var template_event = $('#external_event_template').clone();
		
		var background_color = $('#txt_background_color').val();
        var border_color = $('#txt_border_color').val();
        var text_color = $('#txt_text_color').val();
        
		var title = $('#txt_title').val();
		var description = $('#txt_description').val();
	
		//Edit id
		$(template_event).attr('id', get_uni_id());
		//Add template data attributes
		$(template_event).attr('data-background', background_color);
        $(template_event).attr('data-border', border_color);
        $(template_event).attr('data-text', text_color);
		$(template_event).attr('data-title', title);
		$(template_event).attr('data-description', description);

       //Style external event
        $(template_event).css('background-color', background_color);
        $(template_event).css('border-color', border_color);
        $(template_event).css('color', text_color);
        
		//Set text of external event
		$(template_event).text(description);

		//Append to external events container
		$('#external_events').append(template_event);
      //  $(template_event).insertAfter($('#external_events'));
		//Initialise external event
		initialise_external_event('#' + $(template_event).attr('id'));

		//Show
		$(template_event).fadeIn(2000);
	});
}
function initialise_ajax_event(){
	
      var datapram={"eventgroupname":1};
      $.ajax({
          type:'post',
            dataType:'json',
            url:'eventsTypeAction',
            data:datapram,
            success:function(msg){
                    var data=msg.eventyperow;
                    $.each(data,function(index,eachdata){
                           var eventid=eachdata.eventypeid;
                           var eventtitle=eachdata.eventtypename;
                          // alert(n);                     
                           var template_event = $('#external_event_template').clone();
                 
                            //Edit id
                            var background_color = $('#txt_background_color').val();
                            var border_color = $('#txt_border_color').val();
                            var text_color = $('#txt_text_color').val();
                            
                            $(template_event).attr('id', eventid);
                            //get_uni_id());
                            //Add template data attributes
                            $(template_event).attr('data-background', background_color);
                            $(template_event).attr('data-border', border_color);
                            $(template_event).attr('data-text', text_color);
                            $(template_event).attr('data-title', eventtitle);
                           
                           //Style external event
                            $(template_event).css('background-color', background_color);
                            $(template_event).css('border-color', border_color);
                            $(template_event).css('color', text_color);
                                     
                            //Set text of external event
                            $(template_event).text(eventid+" "+eventtitle);
                    
                            //Append to external events container
                            $('#external_events').append(template_event);
                    
                            //Initialise external event
                            initialise_external_event('#' + $(template_event).attr('id'));
                    
                            //Show
                            $(template_event).fadeIn(2000);
                    });
            }//end success tag
            
                
      });
    
}

/* Initialise external events */
function initialise_external_event(selector){

	//Initialise booking types
	$(selector).each(function(){

		//Make draggable
		$(this).draggable({
			revert: true,
			revertDuration: 0,
			zIndex: 999,
			cursorAt: {
				left: 10,
				top: 1
			}
		});

		//Create event object
		var event_object = {
			title: $.trim($(this).text())
		};

		//Store event in dom to be accessed later
		$(this).data('eventObject', event_object);
	});
}


/* Initialise color pickers */
function initialise_color_pickers(){
	//Initialise color pickers
	
	//$('.color_picker').miniColors({
	////		'trigger': 'show',
	//		'opacity': 'none'
	//	});
	
}


/* Initialises calendar */
function initialise_calendar(){

	//Initialise calendar ,this url:http://www.WhatIBroke.com_\index.htm
	var calendar=$('#calendar').fullCalendar({		
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month,agendaWeek,agendaDay'
		},
		defaultView: 'agendaWeek',
		minTime: 5,
		maxTime: 23,
		firstDay: 1,
		allDaySlot: false,   //show the all day event
		columnFormat: {
			month: 'ddd',
			week: 'ddd dd/MM',
			day: 'dddd M/d'
		},		
	//select the calendar
		selectable: true,
		selectHelper: true,
		select: function(start, end, allDay) {	
		           
		           $.fancybox.open({
					href : "mycalendar_detail.jsp",
					type : 'iframe',
					padding : 6
				   });	            
					var title = prompt('Event Title:');
					if (title) {
					    // insert into database				    
					    //insert into the calendar					    
    						$('#calendar').fullCalendar('renderEvent',
    							{
    									title: title,
    									start: start,
    									end: end,
    									allDay: allDay
    							},
    							true // make the event "stick"
    						);
						//generateMsg("success","Add:"+title+" ,at "+start+",end at "+end+" successfully.");
					}
					
					calendar.fullCalendar('unselect');  //the calendar object need to defined before ,be careful this 
		},
		editable: true,
		eventSources: [
				{
					url: 'eventsAction',
					type:'GET',
					dataType:'json',
					data: {
                         username: '2',
                      },
                    cache: true,
                    error: function() {
                                alert('there was an error while fetching events!');
                      },
                    editable:true,
                    backgroundColor:'#b55793',
                    color: '#39a87f',   // a non-ajax option
                    textColor: '#1c0303' // a non-ajax option
				}
		],
		droppable: true,
		drop: function(date, all_day){
			       external_event_dropped(date, all_day, this);
		},
		eventClick: function(cal_event, js_event, view){
			                calendar_event_clicked(cal_event, js_event, view);
		},
		eventDrop: function(event, delta) {
				             alert(event.title + ' was moved ' + delta + ' days\n' +
					             '(should probably update your database)');
				//save event
			},			
		loading: function(bool) {
				
			}
	});	
	//Initialise external events
	initialise_external_event('.external-event');
}


/* Handle an external event that has been dropped on the calendar */
function external_event_dropped(date, all_day, external_event){

	//Create vars
	var event_object;
	var copied_event_object;
	var duration = 60;
	var cost;

	//Retrive dropped elemetns stored event object
	event_object = $(external_event).data('eventObject');

	//Copy so that multiple events don't reference same object
	copied_event_object = $.extend({}, event_object);
	
	//Assign reported start and end dates
	copied_event_object.start = date;
	copied_event_object.end = new Date(date.getTime() + duration * 60000);
	copied_event_object.allDay = all_day;

	//Assign colors etc
	copied_event_object.backgroundColor = $(external_event).data('background');
	copied_event_object.textColor = $(external_event).data('text');
	copied_event_object.borderColor = $(external_event).data('border');

	//Assign text, price, etc
	copied_event_object.id = get_uni_id();
	copied_event_object.title = $(external_event).data('title');
	copied_event_object.description = $(external_event).data('description');
//	copied_event_object.price = $(external_event).data('price');
	//copied_event_object.available = $(external_event).data('available');

	//Render event on calendar
	$('#calendar').fullCalendar('renderEvent', copied_event_object, true);
		
				// is the "remove after drop" checkbox checked?
				if ($('#drop-remove').is(':checked')) {
					// if so, remove the element from the "Draggable Events" list
					$(external_event).remove();
				}
}


/* Initialise event clicks */
function calendar_event_clicked(cal_event, js_event, view){

	//Set generation values
	set_event_generation_values(cal_event.id, cal_event.backgroundColor, cal_event.borderColor, cal_event.textColor, cal_event.title, cal_event.description, cal_event.price, cal_event.available);
    alert("event title:"+cal_event.title+",Event x:"+cal_event.pageX+",Event y:"+cal_event.pageY+",view is:"+view.name+"event description"+event.description);
	$(this).css('border-color',"red");
	}


/* Set event generation values */
function set_event_generation_values(event_id, bg_color, border_color, text_color, title, description, price, available){

	//Set values
	//$('#txt_background_color').miniColors('value', bg_color);
	//$('#txt_border_color').miniColors('value', border_color);
	//$('#txt_text_color').miniColors('value', text_color);
	//$('#txt_title').val(title);
	//$('#txt_description').val(description);
	//$('#txt_current_event').val(event_id);
	
}


/* Generate unique id */
function get_uni_id(){

	//Generate unique id
	return new Date().getTime() + Math.floor(Math.random()) * 500;
}


/* Initialise update event button */
function initialise_update_event(){
	var test = $('#calendar').fullCalendar( 'clientEvents');
	//Bind event
	$('#btn_update_event').bind('click', function(){

		//Create vars
		var current_event_id = $('#txt_current_event').val();

		//Check if value found
		if(current_event_id){

			//Retrieve current event
			var current_event = $('#calendar').fullCalendar('clientEvents', current_event_id);

			//Check if found
			if(current_event && current_event.length == 1){

				//Retrieve current event from array
				current_event = current_event[0];

				//Set values
				current_event.backgroundColor = $('#txt_background_color').val();
				current_event.textColor = $('#txt_text_color').val();
				current_event.borderColor = $('#txt_border_color').val();
				current_event.title = $('#txt_title').val();
				current_event.description = $('#txt_description').val();
				current_event.price = $('#txt_price').val();
				current_event.available = $('#txt_available').val();

				//Update event
				$('#calendar').fullCalendar('updateEvent', current_event);
				$('#calendar').fullCalendar('refetchEvents');
			}
		}
	});
}