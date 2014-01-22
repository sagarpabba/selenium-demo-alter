<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<!-- Title etc -->
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>My Calendar</title>

		<!-- Stylesheets etc -->
		<link rel="stylesheet" type="text/css" href="resources/fullcalendar/jquery-ui-1.9.2.custom.css" />
		<link rel="stylesheet" type="text/css" href="resources/fullcalendar/fullcalendar.css" />
		<link rel="stylesheet" type="text/css" href="resources/fullcalendar/view_calendar.css" />
		<!-- <link rel="stylesheet" type="text/css" href="resources/fullcalendar/jquery.miniColors.css" /> -->
		<!-- easyui css file -->
		<!-- <link rel="stylesheet" type="text/css" href="easyui_1.3.4/themes/icon.css" />
		<link rel="stylesheet" type="text/css" href="easyui_1.3.4/themes/default/easyui.css" /> -->
	</head>
	<body>

		<!-- Content Wrapper -->
		<div id="content_wrapper">	
			
			<!-- Event generation -->
			<div id="event_generation_wrapper">	
			
			     <div class='left' style="display:none">
					<div class='text'>Background Color:</div><br /> 	
					<div class='text'>Border Color:</div><br />
					<div class='text'>Text Color:</div><br />	
				</div>
				<div class='right'>
					<input id="txt_background_color" type='hidden' class='color_picker' value='#b55793' /><br />
					<input id="txt_border_color" type='hidden' class='color_picker' value='#39a87f' /><br />
					<input id="txt_text_color" type='hidden' class='color_picker' value='#1c0303' /><br />
				</div>
				<textarea id='txt_description'>Testing</textarea><br />
				<input id="btn_gen_event" type="button" value="New Task In Blow List" class='btn' />
				<nobr><input id="btn_update_event" type="button" value="Remove Task" class='btn'/></nobr>
				
				<input id="txt_current_event" type="hidden" value="" />
				
				<div id='external_events'>
				<div id='external_event_template' class='external-event ui-draggable'></div>
				
			     <input type='checkbox' id='drop-remove' /> <label for='drop-remove'>Remove Item After Drop</label>
		        
			    </div>
			</div>
			<!-- Calendar div -->
			<div id="calendar">
			</div>
		
		    
		    
		</div>

		<!-- Include scripts at bottom to aid dom loading and prevent hangs -->

		<script type='text/javascript' src='resources/fullcalendar/fullcalendar.js'></script>
		
		<script type='text/javascript' src='resources/fullcalendar/view_calendar.js'></script>
		<!-- <script type='text/javascript' src='resources/fullcalendar/jquery.miniColors.js'></script> -->
	   
		<script type="text/javascript" src="resources/scripts/commons.js"></script>
		
		
	</body>
</html>	