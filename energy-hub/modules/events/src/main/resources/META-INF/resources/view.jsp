<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  

<link rel="stylesheet" href="/o/energy-hub-theme/style/events.css">

<portlet:actionURL var="energyProgramURL" /> 
<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>

<style>
	.card-title {
	    display: -webkit-box;
	    -webkit-line-clamp: 2;
	    -webkit-box-orient: vertical;
	    overflow: hidden;
	    text-overflow: ellipsis;
	    max-width: 100px;
	}
	.card-body {
	    min-height: 275px;
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
	label.container {
	    padding: 2px;
	}
	label.container:hover {
		text-decoration: underline;
	}
</style>
<div class="row d-flex mb-4">
	<div class="col-8">
		<h1 class="newsletterTitle"><liferay-ui:message key="UpcomingThisWeek" /></h1>
    </div>
</div>
<div class="row">
	<c:if test="${fn:length(eventsList) eq 0}">
		<div class="seeAll d-flex  justify-content-between align-items-center mt-4">
			<a href="/events?allEvents">
				<span><liferay-ui:message key="NoCurrentEvents" /></span>
			</a>
		</div>
	</c:if>
	<c:if test="${fn:length(eventsList) gt 0}">
		<c:forEach var="event" items="${eventsList}"> 
			<div class="cardCol col-xl-4 col-lg-6 col-md-6 mb-5">
		        <div class="card cardEvent card-small">
		            <div class="card-body">
		                <div class="card-body-text"  onclick="window.location.href = '${event.detailURL}';">
		                    <div class="d-flex  justify-content-between align-items-center">
		                        <div class="">
		                            <p class="card-subtitle">${event.startDateDisplay}</p>
		                            <h5 class="card-title card-header-title-medium">${event.eventName}</h5>
		                            <c:if  test="${event.AttendeesTotal gt 0 }">
		                            	<c:forEach var="att" items="${event.Attendees}" begin="0" end="3" varStatus = "status"> 
		                            		<img class="cardUserImg whiteBorder ${!status.first ? 'move-left' : ''}" src="${att.PortraitUrl}"
			            						onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;"/>
	                                   	</c:forEach>
			                            <p class="card-text card-details-small"> ${event.AttendeesNames} 
			                            	<c:if test="${event.AttendeesTotal gt 3 }">
				                            	<liferay-ui:message key="and" /> ${event.AttendeesTotal} 
				                            	<liferay-ui:message key="Others" /> ${event.AttendeesTotal} 
				                            	<liferay-ui:message key="AreGoing" />
			                             	</c:if>
			                             	<c:if test="${event.AttendeesTotal eq 1 }">
				                            	<liferay-ui:message key="IsGoing" />
			                             	</c:if>
			                             	<c:if test="${event.AttendeesTotal gt 1 }">
				                            	<liferay-ui:message key="AreGoing" />
			                             	</c:if>
			                            </p>
		                            </c:if>
		                            <c:if  test="${event.AttendeesTotal eq 0 }">
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
		                            <div class="cardUserName d-flex">${event.HostName}</div>
		                            <div class="cardUserLive d-flex">
			                            <c:if test="${event.isLive}">
						          		  <span>. </span><liferay-ui:message key="Live" />
						            	</c:if>
						            	<c:if test="${event.isUpcoming}">
											<span>. </span><liferay-ui:message key="Upcoming" />
						            	</c:if>
						            	<c:if test="${event.ended}">
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
				                                            <label class="container"  onclick="ajaxCall('yes','${event.resourcePrimKey}')"><liferay-ui:message key="Yes" /></label>
				                                        </td>
				                                    </tr>
				                                    <tr class="bordered">
				                                        <td>
				                                            <label class="container"  onclick="ajaxCall('maybe','${event.resourcePrimKey}')"><liferay-ui:message key="Maybe" /></label>
				                                        </td>
				                                    </tr> 
				                                    <tr>
				                                        <td>
				                                            <label class="container" onclick="ajaxCall('no','${event.resourcePrimKey}')"><liferay-ui:message key="No" /></label>
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
	</c:if>
</div>

<c:if test="${fn:length(eventsList) gt 0}">
	<div class="seeAll text-end mt-4">
		<a href="/events?allEvents">
			<span><liferay-ui:message key="SeeAll" /></span>
			<span class="arrow bg-black"><img src="/o/energy-hub-theme/images/arrow.svg"></span>
		</a>
	</div>
</c:if>

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
</script>