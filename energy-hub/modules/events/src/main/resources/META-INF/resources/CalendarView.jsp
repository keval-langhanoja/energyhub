<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="/o/energy-hub-theme/style/events-Calendar.css">
<!-- <script -->
<!-- src="/o/energy-hub-theme/plugins/fullcalendar-5.10.1/lib/main.js"></script> -->
<link rel="stylesheet" type="text/css"
	href="/o/energy-hub-theme/style/bootstrap/bootstrap-tagsinput.css" />
<script src="/o/energy-hub-theme/js/bootstrap-tagsinput.min.js"></script>

<portlet:actionURL name="addEvent" var="addEventActionURL"/>


<style>
.form-group {
	margin-bottom: 0;
}

.dashedBorder {
	border: 0.5px dashed #989898 !important;
}

.note-fontname, .note-color, .note-table, .btn-fullscreen, .note-table,
	.note-view, .note-status-output, .note-statusbar, .note-btn .note-icon-picture,
	.btn-codeview {
	display: none;
}

.note-editor.note-frame {
	border: 0.5px solid #989898;
	border-radius: 0px;
	margin-bottom: 0;
}

.note-editor.note-frame .note-editing-area .note-editable {
	min-height: 80px;
}

.sn-checkbox-use-protocol input, .sn-checkbox-open-in-new-window input {
	height: 50% !important;
}

.sn-checkbox-use-protocol input[type="checkbox"],
	.sn-checkbox-open-in-new-window input[type="checkbox"] {
	width: 20%;
	margin: 5px 5%;
}

.dropdown-menu {
	z-index: 1000000;
}

.registerationForm div.bootstrap-tagsinput {
	width: 100%;
	border: 0.5px solid #989898 !important;
	border-radius: 0;
	box-shadow: unset !important;
	min-height: 59px;
}

.darkBlueBackground, .darkBlueBackground:hover {
	background-color: #093A5D;
	border-color: #093A5D;
}

.lightGreenBackground, .lightGreenBackground:hover {
	background-color: #88c08c;
	border-color: #88c08c;
}

.yellowBackground, .yellowBackground:hover {
	background-color: #feca30;
	border-color: #feca30;
}

.lightBlueBackground, .lightBlueBackground:hover {
	background-color: #23b6d052;
	border-color: #23b6d052;
}

.orangeBackground, .orangeBackground:hover {
	background-color: #d3744b;
	border-color: #d3744b;
}

div.fc-daygrid-day-events>div.fc-daygrid-event-harness>a>b {
	color: white;
}

.note-editable img {
	display: none;
}
</style>

<!--addEvent popup-->
<div class="modal fade" id="addEventModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content overflow-scroll">
			<div class="modal-header" id="modal-header">
				<h4 class="modal-title" id="modal-title">
					<liferay-ui:message key="AddNewEvent" />
				</h4>
				<button type="button" class="close" id="closeButton">X</button>
			</div>
			<div class="modal-body" id="modal-body">
				<aui:form action="<%=addEventActionURL%>" cssClass="registerationForm"
					style="max-height: 470px;" enctype="multipart/form-data" method="POST">
					<div class="row">
						<div class="col-12 col-md-6 mt-4 required inputContainer">
							<label><liferay-ui:message key="EventName" /></label>
							<aui:input id="EventName" name="EventName" label=""></aui:input>
						</div>
						<div class="col-12 col-md-6 mt-4 required inputContainer">
							<label><liferay-ui:message key="MeetingLink" /></label>
							<aui:input id="MeetingLink" name="MeetingLink" label=""></aui:input>
						</div>
					</div>
					<div class="row">
						<div class="col-12 col-md-6 mt-4 required inputContainer">
							<label><liferay-ui:message key="Attendance" /></label>
							<aui:select
								cssClass="browser-default custom-select custom-select-lg mb-4 signup_select"
								label="" id="Attendance" name="Attendance">
								<aui:option disabled="true" selected="true" value="">
									<liferay-ui:message key="SelectOption" />
								</aui:option>
								<c:forEach var="elt" items="${Attendance}">
									<aui:option cssClass="dropdown-item" value="${elt.id}">
										${elt.value} 
									</aui:option>
								</c:forEach>
							</aui:select>
						</div>
						<div class="col-12 col-md-6 mt-4 required inputContainer">
							<label><liferay-ui:message key="Type" /></label>
							<aui:select
								cssClass="browser-default custom-select custom-select-lg mb-4 signup_select"
								label="" id="EventType" name="EventType">
								<aui:option disabled="true" selected="true" value="">
									<liferay-ui:message key="SelectOption" />
								</aui:option>
								<c:forEach var="elt" items="${EventType}">
									<aui:option cssClass="dropdown-item" value="${elt.id}">
										${elt.value} 
									</aui:option>
								</c:forEach>
							</aui:select>
						</div>
					</div>
					<div class="row">
						<div class="col-12 col-md-6 mt-4 inputContainer required">
							<label><liferay-ui:message key="StartDate" /></label>
							<aui:input id="StartDate" name="StartDate" label=""
								type="datetime-local" onkeydown="return false"
								onchange="startDateChanged()" />
						</div>
						<div class="col-12 col-md-6 mt-4 inputContainer required">
							<label><liferay-ui:message key="EndDate" /></label>
							<aui:input id="EndDate" name="EndDate" label=""
								type="datetime-local" onkeydown="return false" />
						</div>
					</div>
					<div class="row">
						<div class="col-12 col-md-6 mt-4 inputContainer">
							<label><liferay-ui:message key="OtherLinks" /></label>
							<aui:input id="OtherLinks" name="OtherLinks" label=""></aui:input>
						</div>
						<div class="col-12 col-md-6 mt-4 required inputContainer">
							<label><liferay-ui:message key="UploadCoverImageFor" /></label>
							<div class="add-innovation-dropdown uploadButton-div">
								<aui:input style="opacity:0;position: absolute;" type="file"
									id="EventImage" name="EventImage" label="" accept="image/*" />
								<button type="button" id="custom-button" class="uploadButton">
									<img src="/o/energy-hub-theme/images/upload.svg">
									<liferay-ui:message key="UploadFile" />
								</button>
								<span id="EventImage-text"><liferay-ui:message key="NoFileChosen" /></span>
								<aui:input id="FileEntryId" value="${FileEntryId}" name="FileEntryId" type="hidden" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-12 col-md-12 mt-4 inputContainer">
							<label><liferay-ui:message key="Speakers" /></label>
							<aui:input id="tagInvitedValuesInput" name="Speakers" label=""></aui:input>
						</div>
					</div>
					<div class="row">
						<div class="col-12 col-md-12 mt-4 inputContainer">
							<label><liferay-ui:message key="Description" /></label>
							<aui:input id="EventDescription" name="EventDescription" label=""></aui:input>
						</div>
					</div>
					<div class="d-flex justify-content-end fixBtns my-2"">
						<button class="blueBorderBtn reset mt-3 mx-3" type="" id="clear">
							<liferay-ui:message key="Clear" />
						</button>
					   <div cssClass="blueBtn submit mt-3">
                            <aui:input type="submit" id="save" value="Add" name="Add" label=""></aui:input>
                       </div>
					</div>
			</div>
			<aui:input id="coverImageName" value ="${logoName}" name="coverImageName" type="hidden"></aui:input> 
			</aui:form>
		</div>
	</div>
</div>
</div>

<script>
	function validateEmail(email) {
	    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    return re.test(String(email).toLowerCase());
	}

	$('body').scroll(function(){
		$('#tagInvited-dropdown').remove();
  	});

	$(document).ready(function () {
	    $('#EventDescription').summernote();
	    $('.note-icon-picture').parent('.note-btn').hide();
	    $('.note-icon-video').parent('.note-btn').hide();

	    var img = document.createElement("img");
	    img.src = "/o/energy-hub-theme/images/img/user.jpg";


	    $('#tagInvited').off('beforeItemRemove').on('beforeItemRemove', function(event) {
	    	$('#tagInvited-dropdown').remove();
	    }).off('itemAdded').on('itemAdded', function(event) {
	    	$('#tagInvited-dropdown').remove();
	    })
	    // find input and on un-focus remove the dropdown menu
	    .prev('.bootstrap-tagsinput').find('input').on('blur', function () {
	    	setTimeout(function () {
	    		//$('#tagInvited-dropdown').remove();
	    	}, 100)
	    })
	    // set event on keydown to detect the enter key and on any key show the dropdown menu
	    .off('keydown').on('keydown', function (e) {
	    	// if the enter key clicked then add the item to the tags
	        if( e.keyCode == 13 ) {
	            e.preventDefault();
	            var selected = $('#tagInvited-dropdown').find('a.active');
	            if( selected.length ) {
	            	// add the clicked item to the tags
	        		$('#tagInvited').tagsinput('add', {id: selected.data('id'), text: availableUsers[selected.data('id')].name});
	        		selected.closest('li').hide();
	            } else {
	            	var email = e.target.value.trim();
	            	if( email ) {
	            		if( validateEmail(email) ) {
	            			$('#tagInvited').tagsinput('add', {id: email , text: email});
	            		} else {
	            			alert('Invalid email address');
	            			return;
	            		}
	            	}
	            }

	            e.target.value = '';
	        }

	        // if any key entered then show the dropdown menu
	        // if not removed item in the tags then don't do any thing
	        else {
	        	if( !$('#tagInvited-dropdown').length ) {
		       		$('body').append('<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu" id="tagInvited-dropdown"></ul>');
		        	$('#tagInvited-dropdown').css({
		        		position: 'absolute',
		        		display: 'inline-block',
		        		top: $(e.target).offset().top+75,
		        		left: $(e.target).offset().left+175,
		        	}).html((function () {
		        		var items = [];
		        		$.each(availableUsers, function (userid, dd) {
		        			items.push('<li><a tabindex="-1" href="javascript:;" data-id="'+userid+'" style="text-decoration:none;white-space:nowrap;">'+dd.text+'</a></li>');
		        		});
		        		return items.join('');
		        	})());

		        	// add event on the menu item, when click then add it to the tags
		        	$('#tagInvited-dropdown').off('click', 'li a').on('click', 'li a', function () {
		        		// add the clicked item to the tags
		        		$('#tagInvited').tagsinput('add', {id: $(this).data('id'), text: availableUsers[$(this).data('id')].name});
		        		// hide the item
		        		$(this).closest('li').hide();
		        		// reset input
		        		e.target.value = '';
		        	});
	        	}

	        	if( e.keyCode == 38 ) {
	        		var activeItem = $('#tagInvited-dropdown').find('a.active').removeClass('active').closest('li').prev('li');
	        		while(activeItem.length && !activeItem.is(':visible') ) {
	    				activeItem = activeItem.prev('li');
	    			}
	        		activeItem.find('a').addClass('active');
	        	} else if( e.keyCode == 40 ) {
	        		var activeItem = $('#tagInvited-dropdown').find('a.active').removeClass('active');
	       			activeItem = activeItem.length ? activeItem.closest('li').next('li') : $('#tagInvited-dropdown').find('li:first');

	        		while(activeItem.length && !activeItem.is(':visible') ) {
	    				activeItem = activeItem.next('li');
	    			}
	    			activeItem.find('a').addClass('active');
	    			//$('#tagInvited-dropdown').scrollTop(next.offset().top - 150);
	        	}

	       		var email = e.target.value.trim().toLowerCase(),
	       			existTags = [];

	       		$.each($('#tagInvited').tagsinput('items'), function (xxx, item) {
	       			existTags.push(item.id);
	       		});
	       		$.each(availableUsers, function (userid, dd) {
	       			var fn = (!email || dd.text.toLowerCase().indexOf(email)>=0) && existTags.indexOf(userid)==-1 ? 'show' : 'hide';
	       			$('#tagInvited-dropdown').find('a[data-id="'+userid+'"]').closest('li')[fn]();
	       		});
	        }
	    });
	});

	var realFileBtn = $("#<portlet:namespace/>EventImage");
	var customBtn = document.getElementById("custom-button");
	var customTxt = document.getElementById("EventImage-text");
	var logoTypes = ['jpg', 'jpeg', 'png'];
	var base64String;

	customBtn.onclick = function() {
		realFileBtn.click();
	};

	realFileBtn.change(function() {
		if (realFileBtn.val()) {
			if (this.files && this.files[0]) {
				var reader = new FileReader();
			 	var extension = this.files[0].name.split('.').pop().toLowerCase();
             	var isSuccess = logoTypes.indexOf(extension) > -1;
             	 if (isSuccess) {
					const size = (this.files[0].size / 1024 / 1024).toFixed(2);
					if (size > 2) {
						customTxt.innerHTML = "File must be less than 2MB";
						realFileBtn.val('');
		            } else {
	            	 	reader.onload = function(e) {
		   	            	var name = realFileBtn.val().match(/[\/\\]([\w\d\s\.\-\(\)]+)$/)[1];
		   	            	customTxt.innerHTML = name;
		   	            	document.getElementById("<portlet:namespace />coverImageName").value = name;
		   	            	base64String = e.target.result;
						};
		            	reader.readAsDataURL(this.files[0]);
		            }
			  	}else {
                  customTxt.innerHTML = "Choose a valid File";
                  realFileBtn.val('');
			  	}
			} else {
				customTxt.innerHTML = "No file chosen";
			}
		}
	});

	var calendar;
	document.addEventListener('DOMContentLoaded', function () {
	  	var calendarEl = document.getElementById('calendar');
		var eventsJson = ${eventsJson};
	  	calendar = new FullCalendar.Calendar(calendarEl, {
		    initialView: 'dayGridMonth',
		    headerToolbar: {
		      left: 'prev,next today',
		      center: 'title',
		      right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
		    },
		    buttonText: {
		      today: 'Today',
		      month: 'Month',
		      week: 'Week',
		      day: 'Day',
		      list: 'List'
		    },
		    height: 'auto',
		    navLinks: true,
		    selectable: true,
		    selectMirror: true,
		    nowIndicator: true,
		    select: function (arg) {
		    	if(${is_signed_in} && ${addEvent}) {
		      		$("#addEventModal").modal("show");
		    	}
		    },
		    eventClick: function (event, jsEvent, view) {
		    	window.location.href = event.event.extendedProps.detailURL;
		    },
		    events: eventsJson,
		    eventMaxStack: 2,
		    eventContent: function (arg) {
		    	if(arg.event._def.extendedProps.eventTypeName == 'Webinar') arg.event._def.ui.classNames = 'darkBlueBackground'
		    	else if(arg.event._def.extendedProps.eventTypeName == 'Conference') arg.event._def.ui.classNames = 'lightGreenBackground'
	    		else if(arg.event._def.extendedProps.eventTypeName == 'CallforApplications') arg.event._def.ui.classNames = 'yellowBackground'
    			else if(arg.event._def.extendedProps.eventTypeName == 'Workshop') arg.event._def.ui.classNames = 'lightBlueBackground'
  				else if(arg.event._def.extendedProps.eventTypeName == 'Other') arg.event._def.ui.classNames = 'orangeBackground'
				let htmlTxt = document.createElement('b')
				htmlTxt.innerHTML = arg.event.title
				let arrayOfDomNodes = [htmlTxt]
				return { domNodes: arrayOfDomNodes }
		    }
	  });
	  calendar.render();
	});
	$('#resetButton').on('click', function (e) {
	  e.preventDefault();
	  $("#addEventModal").modal('hide');
	  clearModal();
	});
	$('#closeButton').on('click', function (e) {
	  e.preventDefault();
	  $("#addEventModal").modal('hide');
	  clearModal();
	});

	$('#addEventButton').on('click', function (e) {
	  e.preventDefault();
	  addNewEvent();
	});

	function clearModal() {
		e.preventDefault();
		$("form")[0].reset();
		$(this).parent().find('.error').remove()
		$("span.error").each(function () {
			$(this).remove();
		});
		$("html, body").animate({ scrollTop: 0 }, "slow");
	}

	function getFormattedDate(date) {
	  var day = ('0' + date.getDate()).slice(-2);
	  var month = ('0' + (date.getMonth() + 1)).slice(-2);
	  var year = date.getFullYear().toString();
	  var hour = date.getHours().toString();
	  var minutes = ('0' + date.getMinutes()).slice(-2);
	  return year + '-' + month + '-' + day + 'T' + hour + ':' + minutes;
	}

	function startDateChanged() {
	  document.getElementById("<portlet:namespace/>EndDate").setAttribute("min", $('#<portlet:namespace/>StartDate').val());
	}
</script>