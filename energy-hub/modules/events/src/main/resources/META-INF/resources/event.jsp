<%@ include file="/init.jsp" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="/o/energy-hub-theme/style/eventPage.css">

<portlet:actionURL var="energyProgramURL" /> 
<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>
       
<style>
	.dropdown-menu.show {
	    display: block;
	    overflow: hidden;
	}
	table tr:last-child td:last-child:lang(ar) ,
	table tr:last-child td:first-child:lang(ar),
	table tr:last-child td:lang(ar),
	table tr td:last-child:lang(ar),
	table tr td:first-child:lang(ar),
	table tr:last-child td:last-child:lang(en) ,
	table tr:last-child td:first-child:lang(en),
	table tr:last-child td:lang(en),
	table tr td:last-child:lang(en),
	table tr td:first-child:lang(en) {
		border: 0 !important;
	}
	label.container {
	    padding: 2px;
	}
	label.container:hover {
		text-decoration: underline;
	}
	.eventButton {
	    padding: 4px 20px 4px 15px;
	    background: #23B6D0;
	    border-radius: 20px;
	    color: #fff;
	    border: 0;
	    transition: .3s;
	    box-shadow: 2px 7px 17px #79797929;
	    width: unset;
	    height: unset;
	    margin-left: 10px;
	}
	.eventButton:hover {
	    background: rgb(0, 155, 199, 0.6)!important;
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
	    min-height: 275px;
	}
	.arrow {
	    background: #2FADD1 0% 0% no-repeat padding-box !important;
	    box-shadow: 0px 3px 6px #00000029 !important;
	    border-radius: 50% !important;
	}
</style>    
<div class="content mx-auto py-5 px-3 eventContent">
    <svg class="animatedDots blue" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
        style="left: 1%; top:10%">
        <circle cx="200" cy="50" r="50" />
    </svg>
    <svg class="animatedDots green2" fill="#8BB029" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
        style="left: 2%; top:15%; right: auto">
        <circle cx="200" cy="50" r="50" />
    </svg>
    <div class="row">
    	<c:forEach var="event" items="${eventsList}"> 
	        <div class="col-md-12 col-lg-7 col-xl-8">
	            <div style="background-color: white; padding:2rem;">
	                <div class="titleItemDetail d-flex">${event.eventName}</div>
	                <div class="row m-t-10">
	                    <div class="col-4 inline-flex w-100">
	                        <p class="date-text grey-font">${event.startDateDisplay}</p>
	                    </div>
	                </div>
	                <div class="row d-flex">
	                    <div class="row eventImage">
	                        <div class="pb-5 d-flex justify-content-between align-items-end">
	                            <div class="col-md-4">
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
	                            <div class="d-flex  justify-content-end align-items-top">
	                            	<button type="button" class="eventButton" onclick="copyLink('${event.detailURL}')" 
	                            		data-toggle="modal" data-target="#copyLink">
	                                    <span class="plus"><img src="/o/energy-hub-theme/images/shareWhite.svg"></span>
	                                    <span><liferay-ui:message key="Share" /></span>
	                                </button>
	                                <c:if test="${is_signed_in && !ended}">
					                    <div class="d-flex align-items-center">
					                        <div class="btn-group">
						                        <button type="button" id='btnAttend' class="eventButton" data-bs-toggle="dropdown" data-bs-auto-close="false" aria-expanded="false">
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
	                    <div class="py-5 d-flex justify-content-between align-items-end">
	                        <div class="titleItemDetail capitalText d-flex"><liferay-ui:message key="EventDetails" /></div>
                         	<c:if test="${isAttending}">
		                        <div class="d-flex  justify-content-end align-items-top">
		                            <button type="button" id='btnAttend' class="eventButton" 
		                            onclick="makeIcsFile('${event.eventName}', '${event.startDate}', '${event.endDate}', '${event.meetingLink}', '${event.detailURL}')">
		                                <span class="plus"><img src="/o/energy-hub-theme/images/direct-download.png"></span>
		                                <span><liferay-ui:message key="DownloadasICS" /></span>
		                            </button>
		                        </div>
		                     </c:if>
	                    </div>
	                    <div>
	                        <table class="tbl">
	                            <tr>
	                                 <td style="width: 20%;">
	                                    <label class="smallTitle"><liferay-ui:message key="MeetingLink" />:
	                                    </label>
	                                </td>
	                                <td>
		                                <c:choose>
											<c:when test="${fn:containsIgnoreCase(event.meetingLink, 'teams')}">
												 <label class="smallTitle d-flex justify-content-start">
			                                        <span class="plus">
			                                        <img src="/o/energy-hub-theme/images/microsoft-teams.svg"></span>
			                                        <a href="${event.meetingLink}"><liferay-ui:message key="TeamsLink" /></a>
			                                    </label>
											</c:when>
											<c:when test="${fn:containsIgnoreCase(event.meetingLink, 'zoom')}">
												 <label class="smallTitle d-flex justify-content-start">
			                                        <span class="plus">
			                                        <img src="/o/energy-hub-theme/images/zoom.svg"></span>
			                                        <a href="${event.meetingLink}"><liferay-ui:message key="ZoomLink" /></a>
			                                    </label>
											</c:when>
											<c:otherwise>
												<label class="smallTitle d-flex justify-content-start tableLabelsData">
			                                        <span class="plus">
			                                        <img class="mb-1" style="height: 20px; width: 20px" 
														src="/o/energy-hub-theme/images/contact/link.png">
			                                        <a href="${event.meetingLink}"><liferay-ui:message key="MeetingLink" /></a>
			                                    </label>
											</c:otherwise>
										</c:choose>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td style="width: 20%;">
	                                    <label class="smallTitle"><liferay-ui:message key="Time" />:
	                                    </label>
	                                </td>
	                                <td>
	                                    <label class="boldText  d-flex justify-content-start tableLabelsData"><liferay-ui:message key="from" /> ${event.startTime} <liferay-ui:message key="to" /> ${event.endTime}
	                                    </label>
	                                </td>
	                            </tr>
	                            <c:if test="${fn:length(event.speakers) gt 0 }">
		                            <tr>
		                                <td style="width: 20%;">
		                                    <label class="smallTitle"><liferay-ui:message key="Speakers" />:
		                                    </label>
		                                </td>
		                                <td>
		                                    <label class="boldText d-flex justify-content-start tableLabelsData">
		                                    	<c:forEach var="speaker" items="${event.speakers}" varStatus="loop"> 
		                                    		<a class="boldText" href="${speaker.profileURL}"> ${speaker.FullName}&nbsp;<c:if test="${!loop.last}">, </c:if></a>
		                                    	</c:forEach>
		                                    </label>
		                                </td>
		                            </tr>
	                            </c:if>
	                        </table>
	                        <div class="py-4 titleItemDetail capitalText d-flex capitalText"><liferay-ui:message key="EventMainTopic" /></div>
	                        <div class="eventTopicText" id="desc">${event.description}</div>
	                        <div class="pb-4 pt-5 titleItemDetail capitalText d-flex capitalText"><liferay-ui:message key="MeetEventHost" /></div>
	                        <div class="card-container">
	                            <div class="card" style="box-shadow:none !important">
	                                <div class="item d-flex justify-content-center">
	                                    <div class="profile">
	                                        <div class="profile-image">
	                                            <img src="/o/energy-hub-theme/images/img/user.jpg">
	                                        </div>
	                                        <div class="profile-text" onclick="window.location.href = '${event.HostLink}';">
	                                            <h1>${event.HostName}</h1>
	                                            <p>${event.HostRole}</p>
	                                            <button class="blueBorderBtn capitalText">
	                                                <liferay-ui:message key="Explore" />
	                                                <span class="hrVertical"></span>
	                                                <span class="arrow">
	                                                	<img src="/o/energy-hub-theme/images/arrow.svg">
	                                                </span>
	                                            </button>
	                                        </div>
	                                    </div>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </c:forEach>
        <!--SERVICE PROVIDERS-->
        <div class="col-md-12 col-lg-5 col-xl-4 position-relative">
            <div id="serviceProviders" class="serviceProviders">
                <div class="d-flex justify-content-between flex-wrap mb-4">
                    <div class="titleItemDetail capitalText d-flex"><liferay-ui:message key="SimilarEvents" /></div>
                    <div class="sliderControls">
                        <button class="prev">
                            <svg id="Component_19_1" data-name="Component 19 – 1"
                                xmlns="http://www.w3.org/2000/svg"
                                xmlns:xlink="http://www.w3.org/1999/xlink" width="7" height="12"
                                viewBox="0 0 4.005 6.837">
                                <defs>
                                    <clipPath id="clip-path">
                                        <rect id="Rectangle_127" data-name="Rectangle 127" width="6.837"
                                            height="4.005" fill="#43D2F4" />
                                    </clipPath>
                                </defs>
                                <g id="Group_643" data-name="Group 643"
                                    transform="translate(-415 300.837) rotate(-90)">
                                    <g id="Group_642" data-name="Group 642" transform="translate(294 415)">
                                        <g id="Group_641" data-name="Group 641" transform="translate(0 0)"
                                            clip-path="url(#clip-path)">
                                            <path id="Path_1325" data-name="Path 1325"
                                                d="M297.416,418.9a.477.477,0,0,1-.338-.14l-2.938-2.938a.479.479,0,0,1,.677-.677l2.6,2.6,2.6-2.6a.478.478,0,0,1,.677.677l-2.938,2.938a.475.475,0,0,1-.338.14Zm0,0"
                                                transform="translate(-293.999 -415.001)" fill="#43D2F4" />
                                        </g>
                                    </g>
                                </g>
                            </svg>
                        </button>
                        <button class="next">
                            <svg id="Component_19_1" data-name="Component 19 – 1"
                                xmlns="http://www.w3.org/2000/svg"
                                xmlns:xlink="http://www.w3.org/1999/xlink" width="7" height="12"
                                viewBox="0 0 4.005 6.837">
                                <defs>
                                    <clipPath id="clip-path">
                                        <rect id="Rectangle_127" data-name="Rectangle 127" width="6.837"
                                            height="4.005" fill="#43D2F4" />
                                    </clipPath>
                                </defs>
                                <g id="Group_643" data-name="Group 643"
                                    transform="translate(-415 300.837) rotate(-90)">
                                    <g id="Group_642" data-name="Group 642" transform="translate(294 415)">
                                        <g id="Group_641" data-name="Group 641" transform="translate(0 0)"
                                            clip-path="url(#clip-path)">
                                            <path id="Path_1325" data-name="Path 1325"
                                                d="M297.416,418.9a.477.477,0,0,1-.338-.14l-2.938-2.938a.479.479,0,0,1,.677-.677l2.6,2.6,2.6-2.6a.478.478,0,0,1,.677.677l-2.938,2.938a.475.475,0,0,1-.338.14Zm0,0"
                                                transform="translate(-293.999 -415.001)" fill="#43D2F4" />
                                        </g>
                                    </g>
                                </g>
                            </svg>
                        </button>
                    </div>
                </div>
                <div class="serviceProviders-slider">
                	<c:forEach var="topic" items="${similarTopics}"> 
	                    <div class="serviceProviders-slider-items">
							<div class="card cardEvent card-small">
					            <div class="card-body">
					                <div class="card-body-text"  onclick="window.location.href = '${topic.detailURL}';">
					                    <div class="d-flex  justify-content-between align-items-center" style="min-height: 165px;">
					                        <div class="">
					                            <p class="card-subtitle">${topic.startDateDisplay}</p>
					                            <h5 class="card-title card-header-title-medium">${topic.eventName}</h5>
					                            <c:if  test="${topic.AttendeesTotal gt 0 }">
					                            	<c:forEach var="att" items="${topic.Attendees}" begin="0" end="3" varStatus = "status"> 
					                            		<img class="cardUserImg whiteBorder ${!status.first ? 'move-left' : ''}" src="${att.PortraitUrl}"
						            						onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;"/>
				                                   	</c:forEach>
						                            <p class="card-text card-details-small"> ${topic.AttendeesNames} 
						                            	<c:if test="${topic.AttendeesTotal gt 3 }">
							                            	<liferay-ui:message key="and" /> ${topic.AttendeesTotal} 
							                            	<liferay-ui:message key="Others" /> ${topic.AttendeesTotal} 
							                            	<liferay-ui:message key="AreGoing" />
						                             	</c:if>
						                             	<c:if test="${topic.AttendeesTotal eq 1 }">
							                            	<liferay-ui:message key="IsGoing" />
						                             	</c:if>
						                             	<c:if test="${topic.AttendeesTotal gt 1 }">
							                            	<liferay-ui:message key="AreGoing" />
						                             	</c:if>
						                            </p>
						                        </c:if>
					                            <c:if  test="${topic.AttendeesTotal eq 0 }">
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
					                            <div class="cardUserName d-flex">${topic.HostName}</div>
					                            <div class="cardUserLive d-flex">
						                            <c:if test="${topic.isLive}">
									          		  <span>. </span><liferay-ui:message key="Live" />
									            	</c:if>
									            	<c:if test="${topic.isUpcoming}">
														<span>. </span><liferay-ui:message key="Upcoming" />
									            	</c:if>
									            	<c:if test="${topic.ended}">
														<span>. </span><liferay-ui:message key="Ended" />
									            	</c:if>
					                            </div>
					                        </div>
					                    </div>
					                    <c:if test="${is_signed_in && !ended}">
						                    <div class="d-flex align-items-center">
						                        <div class="btn-group">
							                        <button type="button" id='btnAttend' class="add addEvent" data-bs-toggle="dropdown" data-bs-auto-close="false" aria-expanded="false">
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
							                                            <label class="container"  onclick="ajaxCall('yes','${topic.resourcePrimKey}')"><liferay-ui:message key="Yes" /></label>
							                                        </td>
							                                    </tr>
							                                    <tr class="bordered">
							                                        <td>
							                                            <label class="container"  onclick="ajaxCall('maybe','${topic.resourcePrimKey}')"><liferay-ui:message key="Maybe" /></label>
							                                        </td>
							                                    </tr> 
							                                    <tr>
							                                        <td>
							                                            <label class="container" onclick="ajaxCall('no','${topic.resourcePrimKey}')"><liferay-ui:message key="No" /></label>
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
        </div>
    </div>
</div>

<div class="modal fade" tabindex="-1" role="dialog" id="copyLink">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title"><liferay-ui:message key="CopyLink" /></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body py-4" style="margin: 0 auto;">
        <button type="button" class="btn btn-primary"><liferay-ui:message key="Copy" /></button>
      </div>
    </div>
  </div>
</div>

<script>
	function copyLink(detailURL) {
		navigator.clipboard.writeText(location.origin + detailURL);
	}

    $('.serviceProviders-slider').slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        autoplay: false,
        autoplaySpeed: 4000,
        prevArrow: $('.prev'),
        nextArrow: $('.next'),
        responsive: [{
            breakpoint: 767,
            settings: {
                slidesToShow:2,
                slidesToScroll: 2
            }
        },
        {
            breakpoint: 576,
            settings: {
                slidesToShow: 1,
                slidesToScroll: 1
            }
        }
        ]
    });
    
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
    
    //ICS File
    var icsFile = null;
    function convertToICSDate(dateTime) {
        const year = dateTime.getFullYear().toString();
        const month = (dateTime.getMonth() + 1) < 10 ? "0" + (dateTime.getMonth() + 1).toString() : (dateTime.getMonth() + 1).toString();
        const day = dateTime.getDate() < 10 ? "0" + dateTime.getDate().toString() : dateTime.getDate().toString();
        const hours = dateTime.getHours() < 10 ? "0" + dateTime.getHours().toString() : dateTime.getHours().toString();
        const minutes = dateTime.getMinutes() < 10 ? "0" +dateTime.getMinutes().toString() : dateTime.getMinutes().toString();

        return year + month + day + "T" + hours + minutes + "00";
    }
	
	function makeIcsFile(eventName, start, end, mlink, pageurl) {
	  var icsBody =
	      "BEGIN:VCALENDAR\n" +
	      "CALSCALE:GREGORIAN\n" +
	      "METHOD:PUBLISH\n" +
	      "PRODID:-//Test Cal//EN\n" +
	      "VERSION:2.0\n" +
	      "BEGIN:VEVENT\n" +
	      "UID:test-1\n" +
	      "DTSTART;VALUE=DATE:" +
	      convertToICSDate(new Date(start)) +
	      "\n" +
	      "DTEND;VALUE=DATE:" +
	      convertToICSDate(new Date(end)) +
	      "\n" +
	      "SUMMARY:" +
	      eventName +
	      "\n" +
	      "DESCRIPTION:" +
	      document.getElementById("desc").innerHTML.replace(/<(.|\n)*?>/g, '').replace("&nbsp;", " ") + 
	      "Related Links:" + mlink + " \n" +
	      "END:VEVENT\n" +
	      "END:VCALENDAR";
	   
		var element = document.createElement('a');
	    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(icsBody));
	    element.setAttribute('download', eventName + ".ics");
	
	    element.style.display = 'none';
	    document.body.appendChild(element);
	
	    element.click();
	
	    document.body.removeChild(element);
	}
    
</script>
