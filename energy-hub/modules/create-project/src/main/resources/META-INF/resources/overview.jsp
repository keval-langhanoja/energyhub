<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

	<link rel="stylesheet" href="/o/energy-hub-theme/style/addInnovationProject.css">  
	<link rel="stylesheet" href="/o/energy-hub-theme/style/addNewProgram-success.css">
	<link rel="stylesheet" href="/o/energy-hub-theme/style/addNewProgram-readonly.css">
	<script src="/o/energy-hub-theme/js/bootstrap-tagsinput.min.js"></script>
	<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>
<portlet:actionURL var="createProjectURL" />

<div class="MainContent">
        <!--signup section-->
        <section id="signup" class="signup userType position-relative">
            <div class="bgImage">
                <svg class="animatedDots orange" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                    style="left: 25%; top: 55%; right: auto">
                    <circle cx="200" cy="50" r="50" />
                </svg>
                <svg class="animatedDots green2" fill="#8BB029" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                    style="left: 3%; top:15%">
                    <circle cx="200" cy="50" r="50" />
                </svg>
            </div>
            <div class="content position-relative mx-auto py-5 px-3">
                <div class=" d-flex justify-content-between titleSec">
                    <div class="d-flex path">
                        <a><liferay-ui:message key="Home" /><span class="px-2"><img width="10px" src="/o/energy-hub-theme/images/doubleArrow.svg"></span></a>
                        <a><liferay-ui:message key="InnovationPage" /><span class="px-2"><img width="10px"
                                    src="/o/energy-hub-theme/images/doubleArrow.svg"></span></a>
                        <a><liferay-ui:message key="OverviewPage" /></a>

                    </div>
                    <div class="">
                        <h1 class="position-relative MainTitle">
                            <div class="yellowBorder" style="height:25px"></div><liferay-ui:message key="Overview" />
                        </h1>
                    </div>
                </div>
            </div>
            <div class="content mx-auto py-5 px-3">
                <svg class="animatedDots blue" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                    style="left: 1%; top:10%">
                    <circle cx="200" cy="50" r="50" />
                </svg>
                <svg class="animatedDots green2" fill="#8BB029" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                    style="left: 2%; top:15%; right: auto">
                    <circle cx="200" cy="50" r="50" />
                </svg>
                <div class="d-flex justify-content-between flex-wrap">
                    <div href="" class="back">
                        <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="9.77"
                            height="8.57" viewBox="0 0 9.77 8.57">
                            <defs>
                                <clipPath id="clip-path">
                                    <rect x="9" y="6" width="9.77" height="8.57" fill="none" />
                                </clipPath>
                                <filter id="Path_1325" x="-4.115" y="-6" width="22.885" height="26.57"
                                    filterUnits="userSpaceOnUse">
                                    <feOffset dy="3" input="SourceAlpha" />
                                    <feGaussianBlur stdDeviation="3" result="blur" />
                                    <feFlood flood-opacity="0.161" />
                                    <feComposite operator="in" in2="blur" />
                                    <feComposite in="SourceGraphic" />
                                </filter>
                                <filter id="Path_1325-2" x="-9" y="-6" width="22.885" height="26.57"
                                    filterUnits="userSpaceOnUse">
                                    <feOffset dy="3" input="SourceAlpha" />
                                    <feGaussianBlur stdDeviation="3" result="blur-2" />
                                    <feFlood flood-opacity="0.161" />
                                    <feComposite operator="in" in2="blur-2" />
                                    <feComposite in="SourceGraphic" />
                                </filter>
                            </defs>
                            <g id="Group_846" data-name="Group 846" transform="translate(-151.071 -348.203)">
                                <g id="Scroll_Group_1" data-name="Scroll Group 1" transform="translate(142.071 342.203)"
                                    clip-path="url(#clip-path)" style="isolation: isolate">
                                    <g id="Component_13_1" data-name="Component 13 – 1" transform="translate(9 6)">
                                        <g id="Group_844" data-name="Group 844"
                                            transform="translate(179.228 364.57) rotate(180)">
                                            <g id="Group_707" data-name="Group 707"
                                                transform="translate(169.458 364.57) rotate(-90)">
                                                <g id="Group_642" data-name="Group 642" transform="translate(0)">
                                                    <g id="Group_667" data-name="Group 667">
                                                        <g transform="matrix(0, -1, 1, 0, 0, 9.77)"
                                                            filter="url(#Path_1325)">
                                                            <path id="Path_1325-3" data-name="Path 1325"
                                                                d="M4.285,4.885a.6.6,0,0,1-.424-.176L.176,1.025A.6.6,0,0,1,1.025.175l3.26,3.261L7.546.176a.6.6,0,0,1,.849.849L4.709,4.709a.6.6,0,0,1-.424.176Zm0,0"
                                                                transform="translate(9.77 0) rotate(90)" fill="#333" />
                                                        </g>
                                                    </g>
                                                </g>
                                            </g>
                                            <g id="Group_708" data-name="Group 708"
                                                transform="translate(174.343 364.57) rotate(-90)">
                                                <g id="Group_642-2" data-name="Group 642" transform="translate(0 0)">
                                                    <g id="Group_667-2" data-name="Group 667">
                                                        <g transform="matrix(0, -1, 1, 0, 0, 4.88)"
                                                            filter="url(#Path_1325-2)">
                                                            <path id="Path_1325-4" data-name="Path 1325"
                                                                d="M4.285,4.885a.6.6,0,0,1-.424-.176L.176,1.025A.6.6,0,0,1,1.025.175l3.26,3.261L7.546.176a.6.6,0,0,1,.849.849L4.709,4.709a.6.6,0,0,1-.424.176Zm0,0"
                                                                transform="translate(4.88 0) rotate(90)" fill="#333" />
                                                        </g>
                                                    </g>
                                                </g>
                                            </g>
                                        </g>
                                    </g>
                                </g>
                            </g>
                        </svg>

                        <a id="goback" class="grayText px-2"><liferay-ui:message key="Back" /></a>
                    </div>
                </div>
                <div class="userTypeCard position-relative topBorder mt-3 py-5 px-5">
                    <div class="card info p-3">
                        <div class="d-flex justify-content-start align-items-center">
                            <div><img width="30px" src="/o/energy-hub-theme/images/userType/about.svg"></div>
                            <div class="px-3 text">
                                <p class="mb-0 font-weight-bold"><liferay-ui:message key="ReviewInformation" /></p>
                            </div>
                        </div>
                    </div>
                    <aui:form id="createProject_form" cssClass="mt-5 registerationForm" enctype="multipart/form-data" accept-charset="UTF8" method="POST">
                  		<aui:input id="jspType" value ="overview.jsp" name="jspType" type="hidden"></aui:input>
                  		<aui:input id="edit" value ="${edit}" name="edit" type="hidden"></aui:input>
                  		<aui:input id="articleId" value ="${articleId}" name="articleId" type="hidden"></aui:input>
                        <div class="row">
                            <h1><liferay-ui:message key="GeneralInformation" /></h1>
                            <div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
                                <label><liferay-ui:message key="ProgramTitle" /></label>
                            </div>
                            <div class="col-6 col-md-8 col-xl-8 mt-4 inputContainer">
                            	<aui:input cssClass="data lblProgramTitle" id="ProjectTitle" name="ProjectTitle" label="" disabled="true" value="${ProjectTitle}"></aui:input>
<!--                                 <label id="lblProgramTitle" class="data">Solar energy program</label> -->
                            </div>
                        </div>
                        <div class="separatorDashed"></div>
                        <div class="row">
                            <div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
                                <label><liferay-ui:message key="MaturityStage" /></label>
                            </div>
                            <div class="col-6 col-md-8 col-xl-8 mt-4 inputContainer">
                            <aui:input cssClass="data lblMaturityStage" id="ProjectStage" name="ProjectStage" label="" disabled="true" value="${ProjectStage}"></aui:input>
<!--                                 <label id="lblMaturityStage" class="data">Peak stage</label> -->
                            </div>
                        </div>
                        <div class="separatorDashed"></div>
                        <div class="row">
                            <div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
                                <label><liferay-ui:message key="ProjectCategory" /></label>
                            </div>
                            <div class="col-6 col-md-8 col-xl-8 mt-4 inputContainer">
                            <aui:input cssClass="data lblProjectCategory" id="ProjectCategory" name="ProjectCategory" label="" disabled="true" value="${ProjectCategory}"></aui:input>
<!--                                 <label id="lblProjectCategory" class="data">Solar energy</label> -->
                            </div>
                        </div>
                        <div class="separatorDashed"></div>
                        <div class="row">
                            <div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
                                <label><liferay-ui:message key="WebsiteURL" /></label>
                            </div>
                            <div class="col-6 col-md-8 col-xl-8 mt-4 inputContainer">
                            <aui:input cssClass="data lblWebsiteURL" id="WebsiteURL" name="WebsiteURL" label="" disabled="true" value="${WebsiteURL}"></aui:input>
<!--                                 <label id="lblWebsiteURL" class="data">https://www.example.com</label> -->
                            </div>
                        </div>
                        <div class="separator"></div>
                        <div class="row">
                            <h1><liferay-ui:message key="AboutTheProject" /></h1>
                            <div class="col-12 col-md-7 mt-4 description inputContainer">
                            	<p id="elementId">${SolutionOfferingDescription}
                            		<aui:input cssClass="data elementId" id="SolutionOfferingDescription" name="SolutionOfferingDescription" type="hidden" label="" disabled="true" value="${SolutionOfferingDescription}"></aui:input>
								</p>
                            </div>
                            <div class="col-12 col-md-5">
                                <div class="col-12 mt-4 inputContainer">
                                	<aui:input style="opacity:0;position: absolute;" type="file" id="coverImage" name="coverImage" label="" 
	                           			accept="image/*" hidden="hidden"></aui:input>
                                    <img src="${coverImageURL}" class="about-image"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12 mt-4 inputContainer">
                                <label><liferay-ui:message key="Tags" /></label>
                                <div class="add-innovation-dropdown" value="" id="tagProgram">  
	                                <aui:input id="tagProgramValues" name="tagProgramValues" type="hidden"></aui:input>
									<script> 
										$('#tagProgram').tagsinput({
											itemValue: 'id',
											itemText: 'text', 
										});  
									</script>
									<c:forEach var="tag" items="${allTags}">
										<script language="javascript">
											$(function(){
												$('#tagProgram').tagsinput('add', { id: '${tag.tagId}', text: '${tag.name}' });
											});
										</script>
									</c:forEach> 
                               </div>
                            </div>
                        </div>
                        <div class="separator"></div>
                        <div class="row">
                            <h1><liferay-ui:message key="Documents" /></h1>
                            <div class="col-12">
                                <div class="d-flex flex-wrap justify-content-start align-items-center mt-4 py-3 px-2 dashedBorder">
                                    <c:forEach var="docName" items="${docNames}">
	                                    <div class="document my-2 mx-2 text-center">
	                                        <div class="docDetails">
	                                            <img class="my-2" style="max-width:60px;" src="/o/energy-hub-theme/images/${docName.ext}.svg" onerror="this.src='/o/energy-hub-theme/images/documents.png'; this.onerror=null;">
	                                            <p>${docName.name}</p>
	                                        </div>
	                                    </div>
                                   	</c:forEach> 
                                </div>
                            </div>
                        </div>
                        <div class="separator"></div>
                        <div class="row no-border">
                            <div class="d-flex justify-content-between flex-wrap mb-5">
                                <h1><liferay-ui:message key="InvitedCollaborators" /></h1>
                            </div>
                            <div class="add-innovation-dropdown" value="" id="tagInvited">
                            	 	<script> 
										$('#tagInvited').tagsinput({
											itemValue: 'id',
											itemText: 'text', 
										});  
									</script>
									<c:forEach var="invitee" items="${allInvitees}">
										<script language="javascript">
											$(function(){
												$('#tagInvited').tagsinput('add', { id: '${invitee.id}', text: '${invitee.name}' });
											});
										</script>
									</c:forEach> 
                            </div>
                        </div>
                        <div class="d-flex justify-content-end fixBtns my-5">
<%-- 	                        <aui:button cssClass="blueBorderBtn reset mt-3 mx-3" type="" id="back" value="BackAndEdit"></aui:button> --%>
					   		<aui:button cssClass="blueBtn submit mt-3" type=" " id="save" onclick="ajaxCall('${my-projects}')" value="Submit"></aui:button> 
                        </div>
                    </aui:form>

                </div>
            </div>
    </section>
</div>	

<script>
function ajaxCall(key){

	var xhr = new XMLHttpRequest();
	var formData = new FormData();
	var data = {
		 <portlet:namespace />jspType: $("#<portlet:namespace/>jspType").val(),
		 <portlet:namespace />ProjectTitle: $("#<portlet:namespace/>ProjectTitle").val(),
		 <portlet:namespace />ProjectStage: $("#<portlet:namespace/>ProjectStage").val(),
		 <portlet:namespace />ProjectCategory: $("#<portlet:namespace/>ProjectCategory").val(),
		 <portlet:namespace />WebsiteURL: $("#<portlet:namespace/>WebsiteURL").val(),
		 <portlet:namespace />SolutionOfferingDescription: $("#<portlet:namespace/>SolutionOfferingDescription").val(),
		 <portlet:namespace />tagProgramValues: JSON.stringify($('#tagProgram').tagsinput('items')),
		 <portlet:namespace />tagInvitedValues: JSON.stringify($('#tagInvited').tagsinput('items')),
		 <portlet:namespace />edit: $("#<portlet:namespace/>edit").val(),
		 <portlet:namespace />articleId: $("#<portlet:namespace/>articleId").val(),
	};
	
	var file = $("#<portlet:namespace/>coverImage")[0];
	if( file && file.files.length )
// 		formData.append('<portlet:namespace />coverImage', file.files[0]);
		formData.append('<portlet:namespace />coverImage', getBase64(file.files[0]));
	
	xhr.onloadend = function (e) {
		window.location.href = location.origin +"/add-project?successMessage";
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


function getBase64(file) {
	var reader = new FileReader();
	var b64;
	reader.readAsDataURL(file);
	reader.onload = function () {
		b64 = reader.result;
	};
 	reader.onerror = function (error) {
		console.log('Error: ', error);
	};
	return b64;
}

$(document).ready(function () {
    $('#goback').on('click', function (e) {
    	e.preventDefault();
    	window.history.back();
    }); 
    $("#<portlet:namespace/>back").on('click', function (e) {
    	e.preventDefault();
    	window.history.back()	
    }); 
});
</script>
