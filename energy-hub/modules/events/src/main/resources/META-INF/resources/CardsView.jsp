<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>
<link rel="stylesheet" href="/o/energy-hub-theme/style/events.css">
<link rel='stylesheet' href='/o/energy-hub-theme/plugins/fullcalendar-5.10.1/lib/main.css' />
<script src="/o/energy-hub-theme/plugins/fullcalendar-5.10.1/lib/main.js"></script>

<portlet:actionURL var="energyProgramURL" />
<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>

<style>
    .programList {
    	display: none;
    }
	#p_p_id_com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet_INSTANCE_OeSSppt4Rf9p_ {
	    display: none;
	}
	div.innovationContent > div:nth-child(1),
	div.pb-5 > div.card.info {
		display: none;
	}
	.card-title {
	    display: -webkit-box;
	    -webkit-line-clamp: 2;
	    -webkit-box-orient: vertical;
	    overflow: hidden;
	    text-overflow: ellipsis;
		 max-width: 100px;
	}
	.card-body {
	    min-height: 300px;
	}
	.dropdown-menu.show {
	    display: block;
	    overflow: hidden;
	}
	table tr:last-child td:last-child ,
	table tr:last-child td,
	table tr td:first-child ,
	table tr td:last-child  {
		border: 0;
	}
	label.container:hover {
		text-decoration: underline;
	}
	.card-body-text {
	    min-height: 230px;
	}
	.grid {
		display: block; 
	    /* grid-template-columns: auto 1fr; */
	    /* grid-gap: 2rem; */
	}
</style>

<div class="d-flex justify-content-end align-items-center topTools">
	<div class="programsFlex d-flex align-items-center justify-content-end">
		<div class="d-flex contentt">
			<div class="col-lg-2 col-sm-12" style="text-align: center;">
				<div class="btn-group">
					<button type="button" id='btnFilter' class="modal-button" data-bs-toggle="dropdown"
						 data-bs-auto-close="false" aria-expanded="false">
						<liferay-ui:message key="Filter" /><span class='img_Nav'></span>
					</button>
					<div class="dropdown-menu dropdown-menu-filter" aria-labelledby="btnFilter">
						<div class="divTitle">
							<span class="title"><liferay-ui:message key="Type" /></span>
							<table class="tbl">
								<tr>
									<c:forEach var="elt" items="${EventType}" varStatus="loop">
										<c:if test="${loop.index!=0 && loop.index%2==0}">
								</tr>
								<tr>
									</c:if>
									<td><label class="container"><liferay-ui:message key="${elt.value} " /> 
										<input	type="checkbox" name="type" value="${elt.id}">
										<span class="checkmark"></span>
									</label></td>
									</c:forEach>
								</tr>
							</table>
						</div>
						<div class="dropdown-divider"></div>
						<div class="divTitle">
							<span class="title"><liferay-ui:message key="Attendance" /></span>
							<table class="tbl">
								<tr>
									<c:forEach var="elt" items="${Attendance}" varStatus="loop">
										<c:if test="${loop.index!=0 && loop.index%2==0}">
								</tr>
								<tr>
									</c:if>
									<td><label class="container"><liferay-ui:message key="${elt.value} " /> 
										<input	type="checkbox" name="attendance" value="${elt.id}">
										<span class="checkmark"></span>
									</label></td>
									</c:forEach>
								</tr>
							</table>
						</div>
						<div>
							<div class="d-flex justify-content-end fixBtns mt-5 mb-3">
								<button type="button" class="reset mx-3" id="reset" data-dismiss="modal"><liferay-ui:message key="Reset" /></button>
								<button type="button" id="applyFilter" class="blueBtn submit"><liferay-ui:message key="Apply" /></button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="d-flex searchBlue align-items-center">
            <span><img width="18px" src="/o/energy-hub-theme/images/inputSearch.svg"></span>
            <input type="text" placeholder="Seacrh" id="queryText">
        </div>
	</div>
</div>
<div class="">
	<div class="mainGrid">
		<div class="pb-5">
			<div>
				<h1 class="newsletterTitle"><liferay-ui:message key="AllEvents" /></h1>
				<div class="d-flex justify-content-md-between mb-4">
						<button type="button" class="viewLinks active" onclick="openCity(event, 'cardView')">
							<liferay-ui:message key="CardsView" />
						</button>
						<button type="button" class="viewLinks" onclick="openCity(event, 'calendarView')">
							<liferay-ui:message key="CalendarView" />
					 	</button>
					</div>
				<div id='calendarView' class="tabcontent" style="display:none">
					<div id="calendar"></div>
				</div>
				<div id='cardView' class="tabcontent">
					<div class="row">
						<c:forEach var="event" items="${eventsList}" varStatus="loop">
							<c:if test="${loop.index!=0 && loop.index%3==0}">
								</div><div class="row">
							</c:if>
							<div class="cardCol col-xl-4 col-lg-6 col-md-6 mb-5">
						        <div class="card cardEvent card-small">
						            <div class="card-body">
						                <div class="card-body-text"  onclick="window.location.href = '${event.detailURL}';">
						                    <div class="d-flex  justify-content-between align-items-center">
						                        <div class="">
						                            <p class="card-subtitle">${event.startDateDisplay}</p>
						                            <h5 class="card-title card-header-title-medium">${event.eventName}</h5>
						                            <c:if  test="${event.attendeesTotal gt 0 }">
						                            	<c:forEach var="att" items="${event.attendees}" begin="0" end="3" varStatus = "status">
						                            		<img class="cardUserImg whiteBorder ${!status.first ? 'move-left' : ''}" src="${att.PortraitUrl}"
							            						onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;"/>
					                                   	</c:forEach>
							                            <p class="card-text card-details-small"> ${event.attendeesNames}
							                            	<c:if test="${event.attendeesTotal gt 3 }">
								                            	<liferay-ui:message key="and" /> ${event.attendeesTotal}
								                            	<liferay-ui:message key="Others" /> ${event.attendeesTotal}
								                            	<liferay-ui:message key="AreGoing" />
							                             	</c:if>
							                             	<c:if test="${event.attendeesTotal eq 1 }">
								                            	<liferay-ui:message key="IsGoing" />
							                             	</c:if>
							                             	<c:if test="${event.attendeesTotal gt 1 }">
								                            	<liferay-ui:message key="AreGoing" />
							                             	</c:if>
							                            </p>
						                            </c:if>
						                            <c:if  test="${event.attendeesTotal eq 0 }">
						                            	<p class="card-text card-details-small"><liferay-ui:message key="FirstToAttend" /></p>
				                            	 	</c:if>	
						                        </div>
						                        <div class="d-flex align-items-top">
						                            <img class="card-img-top card-img" src="/o/energy-hub-theme/images/Illustration.png">
						                        </div>
						                    </div>
						                </div>
						                <div class="d-flex  justify-content-between align-items-center">
						                    <div class="d-flex  justify-content-start align-items-center">
						                        <img class="cardUserImg" src="/o/energy-hub-theme/images/img/user.jpg">
						                        <div class="mx-2">
						                            <div class="cardUserName d-flex">${event.eventHostName}</div>
						                            <div class="cardUserLive d-flex">
							                            <c:if test="${event.isLive()}">
										          		  <span>. </span><liferay-ui:message key="Live" />
										            	</c:if>
										            	<c:if test="${event.isUpcoming()}">
															<span>. </span><liferay-ui:message key="Upcoming" />
										            	</c:if>
										            	<c:if test="${event.isEnded()}">
															<span>. </span><liferay-ui:message key="Ended" />
										            	</c:if>
						                            </div>
						                        </div>
						                    </div>
						                    <c:if test="${is_signed_in && !ended}">
							                    <div class="d-flex align-items-center">
							                        <div class="btn-group">
								                        <button type="button" id='btnAttend' class="add" data-bs-toggle="dropdown" data-bs-auto-close="false" aria-expanded="false">
								                            <span class="plus">
								                            	<img src="/o/energy-hub-theme/images/plus.svg">
								                            </span>
								                            <span><liferay-ui:message key="Attend" /></span>
								                        </button>
								                        <div class="dropdown-menu dropdown-menu-attend" aria-labelledby="btnAttend">
								                            <div class="divTitle">
								                                <table class="tbl">
								                                    <tr class="bordered">
								                                        <td>
								                                            <label class="container"  onclick="ajaxCall('yes','${event.resourcePrimaryKey}')"><liferay-ui:message key="Yes" /></label>
								                                        </td>
								                                    </tr>
								                                    <tr class="bordered">
								                                        <td>
								                                            <label class="container"  onclick="ajaxCall('maybe','${event.resourcePrimaryKey}')"><liferay-ui:message key="Maybe" /></label>
								                                        </td>
								                                    </tr> 
								                                    <tr>
								                                        <td>
								                                            <label class="container" onclick="ajaxCall('no','${event.resourcePrimaryKey}')"><liferay-ui:message key="No" /></label>
								                                        </td>
								                                    </tr>
			                                					</table>
								                            </div>
								                        </div>
							                    	</div>
							                    </div>
							            	</c:if>
						                </div>
						            </div>
						        </div>
						    </div>
						</c:forEach>
					</div>
				</div>
				
				<div class="row d-flex justify-content-end">
					<div class="d-flex justify-content-end mt-5">
				        <nav aria-label="Page navigation example ">
				            <ul class="pagination">
				                <li class="page-item">
				                    <a class="page-link prevpage leftArrow ${pageNo==0 ? 'inactive' : 'active' }" href="#" aria-label="Previous">
				                        <span aria-hidden="true"></span>
				                    </a>
				                </li>
				                <c:forEach begin="0" end="${totalPages}" varStatus="loop">
					                <li class="page-item"><a class="page-link plink ${loop.index==pageNo?'activepage':''}" href="#">${loop.index+1}</a></li>
				                </c:forEach>
				                
				                <li class="page-item">
				                    <a class="page-link nextpage rightArrow ${pageNo==(totalPages) ? 'inactive' : 'active' }" href="#" aria-label="Next">
				                        <span aria-hidden="true"></span>
				                    </a>
				                </li>
				            </ul>
				        </nav>
				    </div>
			    </div>
			</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="/CalendarView.jsp" />
<script>
	function ajaxCall(key, eventId) {
		var xhr = new XMLHttpRequest();
		var formData = new FormData();
		var data = {
				 <portlet:namespace />status: key,
				 <portlet:namespace />eventId: eventId
			};
		
		xhr.onloadend = function (e) {
			window.location.href = '/event-detail?event&articleId=' + eventId;
		} 
		xhr.onprogress = function (e) {
			if( e.lengthComputable ) {
				var percentComplete = e.loaded / e.total * 100;
				console.log('upload '+percentComplete+'%');
			}
		};
		
		xhr.open('POST', '${testAjaxResourceUrl}&'+$.param(data));
		xhr.send(formData); 
	}
	
	function copyLink(detailURL) {
		navigator.clipboard.writeText(detailURL);
	}
	var _searchParams = new URLSearchParams(location.search);
	var pageNo = ${pageNo};
	var pageSize = ${pageSize};
	var filterData = JSON.parse(_searchParams.get('filter') || '{}');
	
	$('.page-item').on('click', function () {
		var page = $(this).find('a');
		if( page.hasClass('activepage') || page.hasClass('inactive') ) {
			return;
		}
		pageNo = page.hasClass('plink') ? parseInt(page.text())-1 : pageNo+(page.hasClass('nextpage') ? 1 : -1);
		getData();
	});
	
	$('#btnFilter').on('click', function () {
		if( $(".dropdown-menu-filter").hasClass("show") ) {
			$(".dropdown-menu-filter").removeClass("show")
		}else {
			$(".dropdown-menu-filter").addClass("show")
		}
	});
	
	$('#applyFilter').on('click', function () {
		var type = [], attendance = [], status =[];
		$('input[name="type"]:checked').each(function () {
			type.push($(this).val());
		});
		$('input[name="attendance"]:checked').each(function () {
			attendance.push($(this).val());
		});
		$('input[name="status"]:checked').each(function () {
			status.push($(this).val());
		});
		
		filterData = {};
		if( type.length ) {
			filterData.type = type;
		}
		if( attendance.length ) {
			filterData.attendance = attendance;
		}
		if( status.length ) {
			filterData.status = status;
		}
		pageNo = 0;
		getData();
	});
	
	function getData() {
		var searchParams = new URLSearchParams(location.search); 
		searchParams.set('pageNo', pageNo);
		searchParams.set('pageSize', pageSize);
		searchParams.set('filter', JSON.stringify(filterData));
		location.href = location.origin+location.pathname+'?'+searchParams.toString();
	}
	
	$('#reset').on('click', function (e) {
		e.preventDefault();
		filterData = JSON.parse('{}');
		pageNo = "0";
		pageSize = "9";
		$('.checkmark').css('background-color','white'); 
		getData();
	}); 
	
	function openCity(evt, tabName) {
		$('.fc-dayGridMonth-button').click();
		var i, tabcontent, tablinks;
		tabcontent = document.getElementsByClassName("tabcontent");
		for (i = 0; i < tabcontent.length; i++) {
		  tabcontent[i].style.display = "none";
		}
		tablinks = document.getElementsByClassName("viewLinks");
		for (i = 0; i < tablinks.length; i++) {
		  tablinks[i].className = tablinks[i].className.replace(" active", "");
		}
		document.getElementById(tabName).style.display = "block";
		evt.currentTarget.className += " active";
		$('.fc-dayGridMonth-button').click();
		$('.fc-dayGridMonth-button').click();
	}
</script>
