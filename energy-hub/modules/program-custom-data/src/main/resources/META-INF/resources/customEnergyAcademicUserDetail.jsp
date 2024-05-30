<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<portlet:actionURL var="energyProgramURL" /> 
<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>	
<!-- <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet"> -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">

<style>
	.about-image {
	    max-width: 100%;
    	max-height: 50% !important;
    	margin: 0 auto !important;
    	height: auto !important;
	}
	.programTitle p{
	    font-size: 15px;
		letter-spacing: 1.02px;
		color: #333333; 
		text-decoration: underline;
		flex-wrap: wrap;
		font-weight: 500;
	}
	.programDesc p{
		font-size: 15px;
		letter-spacing: 1.02px;
		color: #333333;  
		flex-wrap: wrap;
		font-weight: 500;
		margin: 0 2rem;
	}
	.margins {
		font-size: 15px;
		letter-spacing: 1.02px;
		color: #333333;  
		flex-wrap: wrap;
		font-weight: 500;
		margin: 0 2rem; 
	}
	.modal-dialog {
		min-width: 90%;
	}
	.description textarea {
	    min-height: auto !important;
	}
	.form-group {
	 	margin-bottom: 0;
	}
	.note-fontname, .note-color, .note-table, .btn-fullscreen, .note-table, .note-view,
	 .note-status-output, .note-statusbar,.note-btn .note-icon-picture, .btn-codeview{
	    display: none;
	}
	
	.note-editor.note-frame{
	    border: 0.5px solid #989898;
	    border-radius: 0px;
	    margin-bottom: 0;
	}
	
	.note-editor.note-frame .note-editing-area .note-editable {
	    min-height: 150px;
	}
	.sn-checkbox-use-protocol input, 
	.sn-checkbox-open-in-new-window input {
	    height: 50% !important;
   	}
   	.sn-checkbox-use-protocol input[type="checkbox"], 
   	 .sn-checkbox-open-in-new-window input[type="checkbox"] {
	    width: 20%;
	    margin: 5px 5%;
	}
	.note-editable img{
        display: none;
    }
</style>
<div class="programsFlex d-flex align-items-center justify-content-between">
	<div class="col-12" style="background-color: white; padding:2rem;" class="row">
		<div style="background-color: white; padding:2rem;" class="col-6">
			<h1 class="innovationTitle mb-2"><strong>${currentUser.universityName}</strong><c:if test="${currentUser.acronym!=''}"><b> - ${currentUser.acronym}</b></c:if></h1>
		</div>
		<div style="background-color: white; padding:2rem; display: flex; flex-direction: row-reverse;" class="col-6">
			<c:if test="${is_signed_in && showAddAcademic}">
				<button class="add mb-2" data-toggle="modal" data-target="#invite">
					<span class="plus">
						<img class="mb-1" src="/o/energy-hub-theme/images/add.svg">
					</span>
					<span><liferay-ui:message key="AddEnergyProgram" /></span>
				</button>
			</c:if>
		</div>
		<div class="row d-flex">
			<c:if test="${currentUser.imageUrl!=''}">
				<img style="max-width:600px; margin: 0 auto;"src="${currentUser.imageUrl}" />
			</c:if> 
			<c:if test="${currentUser.linkToWebsite!=''}">
				<div class="col-12 inputContainer">
					<label>Website : </label> 
					<a href="//${currentUser.linkToWebsite}" target="_blank" id="totalComments" class="linkStyle data">
						${currentUser.linkToWebsite}
					</a> 
				</div>
			</c:if>
			<c:if test="${not empty currentUser.companyPhoneNumber && currentUser.companyPhoneNumber !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="PhoneNumber" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.companyPhoneNumber}</label>
					</div>
				</div>
			</c:if>
			<div class="captionPart">
				<c:if test="${currentUser.description!=''}">
					<h5 class="card-title  card-header-title-small"><liferay-ui:message key="Description" /></h5>
					${currentUser.description}
				</c:if>
				<c:if test="${currentUser.programs!=''}">
					<h5 class="card-title  card-header-title-small"><liferay-ui:message key="RelatedEnergyPrograms" /> (${fn:length(currentUser.programs)})</h5>
					<c:forEach var="program" items="${currentUser.programs}">
						<c:if test="${program.energyPrograms !=''}">
							<div class ="programTitle mt-4">
								<p>
									${program.energyPrograms}
								</p>
							</div>
						</c:if>
						<div class="row margins">
							<c:if test="${program.location !=''}">
								<div class="col-12 inputContainer">
									<label><liferay-ui:message key="Location" /> : </label> 
									<label id="lblMaturityStage" class="data">${program.location}</label>
								</div>
							</c:if>
							<c:if test="${program.attendance !=''}">
								<div class="col-12 inputContainer">
									<label><liferay-ui:message key="Attendance" /> : </label> 
									<label id="lblMaturityStage" class="data">
										<c:if test="${program.attendance == 'Option65161407'}"><liferay-ui:message key="InPerson" /></c:if>
										<c:if test="${program.attendance == 'Option72620452'}"><liferay-ui:message key="Online" /></c:if>
									</label>
								</div>
							</c:if>
							<c:if test="${program.degreeType !=''}">
								<div class="col-12 inputContainer">
									<label><liferay-ui:message key="DegreeType" /> : </label> 
									<label id="lblMaturityStage" class="data">
										<c:if test="${program.degreeType == 'Option65161407'}"><liferay-ui:message key="BachelorDegreeEngineering" /></c:if>
										<c:if test="${program.degreeType == 'Option58027447'}"><liferay-ui:message key="BachelorDegree" /></c:if>
										<c:if test="${program.degreeType == 'Option45702192'}"><liferay-ui:message key="MastersEngineering" /></c:if>
										<c:if test="${program.degreeType == 'Option91688666'}"><liferay-ui:message key="MastersSciences" /></c:if>
										<c:if test="${program.degreeType == 'Option14319202'}"><liferay-ui:message key="Phd" /></c:if>
										<c:if test="${program.degreeType == 'Option36531767'}"><liferay-ui:message key="DiplomaCertificate" /></c:if>
										<c:if test="${program.degreeType == 'Option94497664'}"><liferay-ui:message key="Course" /></c:if>
									</label>
								</div>
							</c:if>
							<c:if test="${program.links !=''}">
								<div class="col-12 inputContainer">
									<label>Links : </label> 
									<a href="//${program.links}" target="_blank" id="totalComments" class="linkStyle data">
										${program.links}
									</a> 
								</div>
							</c:if>
						</div>
						<c:if test="${program.description !=''}">
							<h5 class="card-title  card-header-title-small" style="margin: 0 2rem; "><liferay-ui:message key="ProgramDescriptionAndMission" /></h5>
							<div class ="programDesc">
								${program.description}
							</div> 
						</c:if>						
					</c:forEach>
				</c:if>
			</div>
		</div>
	</div>
</div>

<!--invite popup-->
<div class="modal fade" id="invite" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
    aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
			<div class="modal-header" id="modal-header">
	        	<h4 class="modal-title" id="modal-title"><liferay-ui:message key="AddEngineeringProgram" /></h4>
	            <button type="button" class="close" data-dismiss="modal">X</button>
	       	</div>
			<!-- Modal body -->
	       	<div class="modal-body" id="modal-body">
	        	<aui:form id="createAccount_form" cssClass="px-5 registerationForm" 
					style="max-height: 530px;" accept-charset="UTF8" method="POST">
				   <h1><liferay-ui:message key="EngineeringPrograms" /></h1>
				   <div class="row">
					   <div class="col-12 col-md-6 col-xl-4 mt-4 required inputContainer">
						   <label><liferay-ui:message key="EngineeringPrograms" /></label>
							<aui:input id="EngineeringPrograms" name="EngineeringPrograms" label=""></aui:input>
					   </div>
						<div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="Location" /></label>
							<aui:input id="Location" name="Location" label=""></aui:input>
					   </div>
						<div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="Links" /></label>
							<aui:input id="Links" name="Links" label=""></aui:input>
					   </div>
					   <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer required">
							<label><liferay-ui:message key="Attendance" /></label>
							<aui:select cssClass="browser-default custom-select custom-select-lg mb-4 signup_select"
								label="" id="Attendance" name="Attendance"> 
								<aui:option disabled="true" selected="true" value=""><liferay-ui:message key="SelectOption" /></aui:option>
								<c:forEach var="elt" items="${Attendance}">
									<aui:option cssClass="dropdown-item" value="${elt.id}">
										${elt.value} 
									</aui:option> 
								 </c:forEach>
							</aui:select>
					   </div>
					   <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer required">
							<label><liferay-ui:message key="DegreeType" /></label>
							<aui:select cssClass="browser-default custom-select custom-select-lg mb-4 signup_select"
								label="" id="DegreeType" name="DegreeType"> 
								<aui:option disabled="true" selected="true" value=""><liferay-ui:message key="SelectOption" /></aui:option>
								<c:forEach var="elt" items="${DegreeType}">
									<aui:option cssClass="dropdown-item" value="${elt.id}">
										${elt.value} 
									</aui:option> 
								 </c:forEach>
							</aui:select>
					   </div>
						<div class="col-12 col-md-12 mt-4 description inputContainer required">
							<label><liferay-ui:message key="EngineeringProgramDescription" /></label>
							<div id="summernote"></div>  
						</div> 
					</div>
					<aui:input id="folderId" name="folderId" type="hidden" value ="${folderId}"></aui:input>
					<aui:input id="ddmTemplateKey" name="ddmTemplateKey" type="hidden" value ="${ddmTemplateKey}"></aui:input>
					<div class="d-flex justify-content-end fixBtns">
						<button class="blueBorderBtn reset mt-3 mx-3" type="" id="clearCreateAccount" value="Clear"><liferay-ui:message key="Clear" /></button>
						<aui:button cssClass="blueBtn submit mt-3" type="submit" onclick="ajaxCall('addProgram')" id="save" value="Send"></aui:button>
					</div>
				   </div>
				</aui:form>
	       	</div>
        </div>
    </div>
</div>
<script>
	$('#clearCreateAccount').on('click', function (e) {
		e.preventDefault();
		$("form")[0].reset();
		$(this).parent().find('.error').remove()
		$("span.error").each(function () {
			$(this).remove();
		});
		$('#summernote').summernote('reset');
		$("html, body").animate({ scrollTop: 0 }, "slow");
	}); 

	$(document).ready(function () {
	    $('#summernote').summernote();
	    $('.note-icon-picture').parent('.note-btn').hide();
	    $('.note-icon-video').parent('.note-btn').hide();
	});
	function ajaxCall(key) {debugger;
		var xhr = new XMLHttpRequest();
		var formData = new FormData();
		var data = {
			 <portlet:namespace />jspType: $("#<portlet:namespace/>jspType").val(),
			 <portlet:namespace />EngineeringPrograms: $("#<portlet:namespace/>EngineeringPrograms").val(),
			 <portlet:namespace />Location: $("#<portlet:namespace/>Location").val(),
			 <portlet:namespace />Links: $("#<portlet:namespace/>Links").val(),
			 <portlet:namespace />Attendance: $("#<portlet:namespace/>Attendance").val(),
			 <portlet:namespace />DegreeType: $("#<portlet:namespace/>DegreeType").val(),
			 <portlet:namespace />folderId: $("#<portlet:namespace/>folderId").val(),
			 <portlet:namespace />description:  $("#summernote").summernote('code').replace(/<img[^>]*>/g,"")
		};
		
		xhr.onloadend = function (e) {
			window.location.href = location.origin +"${currentUser.academicReditUrl}";
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
