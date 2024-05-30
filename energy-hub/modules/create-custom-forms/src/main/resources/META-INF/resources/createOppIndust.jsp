<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %> 
<portlet:actionURL var="createProjectURL"/>
<portlet:resourceURL var="testAjaxResourceUrl"/>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>	
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<style>
	.form-group {
		margin-bottom: 0;
	}
	.dashedBorder {
    	border: 0.5px dashed #989898 !important;
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
<div class="MainContent">
<!--signup section-->
	<section id="signup" class="signup userType position-relative">
		<div class="bgImage">
			<svg class="animatedDots orange" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg" style="left: 25%; top: 55%; right: auto">
				<circle cx="200" cy="50" r="50"/>
			</svg>
			<svg class="animatedDots green2" fill="#8BB029" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg" style="left: 3%; top:15%">
				<circle cx="200" cy="50" r="50"/>
			</svg>
		</div>
		<div class="content position-relative mx-auto py-5 px-3">
			<div class=" d-flex justify-content-between titleSec">
<!-- 				<div class="d-flex path"> -->
<!-- 					<a><liferay-ui:message key="Home" /><span class="px-2"> -->
<!-- 						<img width="10px" src="/o/energy-hub-theme/images/doubleArrow.svg"/> -->
<!-- 					</a> -->
<!-- 					<a><liferay-ui:message key="AddInnovationProject" /></a> -->
<!-- 				</div> -->
<!-- 				<div class=""> -->
<!-- 					<h1 class="position-relative MainTitle"> -->
<%-- 						<div class="yellowBorder"/>${the_title} --%>
<!-- 					</h1> -->
<!-- 					<p class="mt-1 mainDetails">Text goes here, text goes here, text goes here</p> -->
<!-- 				</div> -->
			</div>
		</div>
		<div class="content mx-auto py-5 px-3">
		<svg class="animatedDots blue" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg" style="left: 1%; top:10%">
			<circle cx="200" cy="50" r="50"/>
		</svg>
		<svg class="animatedDots green2" fill="#8BB029" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg" style="left: 2%; top:15%; right: auto">
			<circle cx="200" cy="50" r="50"/>
		</svg>
		<div class="d-flex justify-content-between flex-wrap">
			<div href="" class="back">
				<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="9.77" height="8.57" viewBox="0 0 9.77 8.57">
					<defs>
						<clipPath id="clip-path">
							<rect x="9" y="6" width="9.77" height="8.57" fill="none"/>
						</clipPath>
						<filter id="Path_1325" x="-4.115" y="-6" width="22.885" height="26.57" filterUnits="userSpaceOnUse">
							<feOffset dy="3" input="SourceAlpha"/>
							<feGaussianBlur stdDeviation="3" result="blur"/>
							<feFlood flood-opacity="0.161"/>
							<feComposite operator="in" in2="blur"/>
							<feComposite in="SourceGraphic"/>
						</filter>
						<filter id="Path_1325-2" x="-9" y="-6" width="22.885" height="26.57" filterUnits="userSpaceOnUse">
							<feOffset dy="3" input="SourceAlpha"/>
							<feGaussianBlur stdDeviation="3" result="blur-2"/>
							<feFlood flood-opacity="0.161"/>
							<feComposite operator="in" in2="blur-2"/>
							<feComposite in="SourceGraphic"/>
						</filter>
					</defs>
					<g id="Group_846" data-name="Group 846" transform="translate(-151.071 -348.203)">
						<g id="Scroll_Group_1" data-name="Scroll Group 1" transform="translate(142.071 342.203)" clip-path="url(#clip-path)" style="isolation: isolate">
							<g id="Component_13_1" data-name="Component 13 – 1" transform="translate(9 6)">
								<g id="Group_844" data-name="Group 844" transform="translate(179.228 364.57) rotate(180)">
									<g id="Group_707" data-name="Group 707" transform="translate(169.458 364.57) rotate(-90)">
										<g id="Group_642" data-name="Group 642" transform="translate(0)">
											<g id="Group_667" data-name="Group 667">
												<g transform="matrix(0, -1, 1, 0, 0, 9.77)" filter="url(#Path_1325)">
													<path id="Path_1325-3" data-name="Path 1325" d="M4.285,4.885a.6.6,0,0,1-.424-.176L.176,1.025A.6.6,0,0,1,1.025.175l3.26,3.261L7.546.176a.6.6,0,0,1,.849.849L4.709,4.709a.6.6,0,0,1-.424.176Zm0,0" transform="translate(9.77 0) rotate(90)" fill="#333"/>
												</g>
											</g>
										</g>
									</g>
									<g id="Group_708" data-name="Group 708" transform="translate(174.343 364.57) rotate(-90)">
										<g id="Group_642-2" data-name="Group 642" transform="translate(0 0)">
											<g id="Group_667-2" data-name="Group 667">
												<g transform="matrix(0, -1, 1, 0, 0, 4.88)" filter="url(#Path_1325-2)">
													<path id="Path_1325-4" data-name="Path 1325" d="M4.285,4.885a.6.6,0,0,1-.424-.176L.176,1.025A.6.6,0,0,1,1.025.175l3.26,3.261L7.546.176a.6.6,0,0,1,.849.849L4.709,4.709a.6.6,0,0,1-.424.176Zm0,0" transform="translate(4.88 0) rotate(90)" fill="#333"/>
												</g>
											</g>
										</g>
									</g>
								</g>
							</g>
						</g>
					</g>
				</svg>
				<a class="grayText px-2"><liferay-ui:message key="Back" /></a>
			</div>
		</div>
		<div class="userTypeCard position-relative topBorder mt-3 py-5 px-5">
			<aui:form id="createProject_form" cssClass="mt-5 registerationForm" enctype="multipart/form-data" accept-charset="UTF8" method="POST">
				<aui:input id="edit" value="${edit}" name="edit" type="hidden"/>
				<aui:input id="p_r_p_categoryId" value="${p_r_p_categoryId}" name="p_r_p_categoryId" type="hidden"/>
				<aui:input id="folderId" value="${folderId}" name="folderId" type="hidden"/>
				<aui:input id="ddmStructureKey" value="${ddmStructureKey}" name="ddmStructureKey" type="hidden"/>
				<aui:input id="ddmTemplateKey" value="${ddmTemplateKey}" name="ddmTemplateKey" type="hidden"/>
				<aui:input id="articleId" value="${articleId}" name="articleId" type="hidden"/>
				<h1><liferay-ui:message key="GeneralInformation" /></h1>
				<div class="row">
					<div class="col-12 col-md-4 col-xl-4 mt-4 inputContainer required">
						<label><liferay-ui:message key="Name" /></label>
						<aui:input id="OppIndustTitle" name="OppIndustTitle" label=""/>
					</div>
					<div class="col-12 col-md-4 col-xl-4 mt-4 inputContainer required">
						<label><liferay-ui:message key="URL" /></label>
						<aui:input id="OppIndustURL" name="OppIndustURL" label=""/>
					</div>
					<div class="col-12 col-md-4 col-xl-4 mt-4 inputContainer">
						<label><liferay-ui:message key="Type" /></label>
						<aui:select aria-labelledby="dropdownMenuButton" cssClass="browser-default custom-select custom-select-lg mb-3 signup_select" 
						label="" id="OppIndustType" name="OppIndustType">
							<c:forEach var="projectStage" items="${Type}">
								<aui:option cssClass="dropdown-item" value="${projectStage.id}">
									<liferay-ui:message key="${projectStage.value}"/>
								</aui:option>
								<div class="dropdown-divider"/>
							</c:forEach>
						</aui:select>
					</div>
				</div>
				<div class="row">
					<div class="col-12 col-md-6 col-xl-6 mt-4 inputContainer required">
						<label><liferay-ui:message key="StartDate" /></label>
						<aui:input id="OppIndustStartDate" name="OppIndustStartDate" type="date" onkeydown="return false" label="" />
					</div>
					<div class="col-12 col-md-6 col-xl-6 mt-4 inputContainer required">
						<label><liferay-ui:message key="EndDate" /></label>
						<aui:input id="OppIndustEndDate" name="OppIndustEndDate" type="date" onkeydown="return false" label="" />
					</div>
				</div>
				<div class="separator"></div>
				<h1><liferay-ui:message key="AboutTheProject" /></h1>
				<div class="row">
					<div class="col-12 col-md-12">
						<div class="col-12 mt-4 inputContainer required">
							<label><liferay-ui:message key="UploadCoverImageFor" /></label>
							<div class="add-innovation-dropdown uploadButton-div">
								<aui:input style="opacity:0;position: absolute;" type="file" id="OppIndustImage" 
								name="OppIndustImage" label="" accept="image/*" hidden="hidden"/>
								<button type="button" id="custom-button" class="uploadButton">
									<img src="/o/energy-hub-theme/images/upload.svg"> <liferay-ui:message key="UploadFile" />
								</button>
									<span id="OppIndustImage-text"><liferay-ui:message key="NoFileChosen" /></span>
									<aui:input id="FileEntryId" value="${FileEntryId}" name="FileEntryId" type="hidden"/>
								</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-12 col-md-6 mt-4 description inputContainer required">
						<label><liferay-ui:message key="Description" /></label>
						<div id="OppIndustDescription"></div>  
					</div>
					<div class="col-12 col-md-6 mt-4 description inputContainer required">
						<label><liferay-ui:message key="Eligibility" /></label>
						<div id="OppIndustEligibility"></div>  
					</div>
				</div>
				<div class="d-flex justify-content-end fixBtns my-5">
					<button class="blueBorderBtn reset mt-3 mx-3" type="" id="clear"><liferay-ui:message key="Clear" /></button>
					<aui:button cssClass="blueBtn submit mt-3"  type="submit" onclick="ajaxCall('key')" id="save" value="Proceed"/>
				</div>
			</aui:form>
		</div>
	</div>
	</section>
</div>
					
<script> 

	$(document).ready(function () {
	    $('#OppIndustDescription').summernote();
	    $('#OppIndustEligibility').summernote();
	    $('.note-icon-picture').parent('.note-btn').hide();
	    $('.note-icon-video').parent('.note-btn').hide();

	    document.getElementById("<portlet:namespace/>OppIndustStartDate").setAttribute("max", getFormattedDate18YearsAgo(new Date()));

		function getFormattedDate18YearsAgo(date) {
			var day = ('0' + date.getDate()).slice(-2);
	  	 	var month = ('0' + (date.getMonth() + 1)).slice(-2);
		   	var year = date.getFullYear().toString();
		   	return year + '-' + month + '-' + day;
		}
		
	    if(${edit} == true) {
			document.getElementById("<portlet:namespace />OppIndustTitle").value = "${OppIndustTitle}";
			document.getElementById("<portlet:namespace />OppIndustURL").value = "${OppIndustURL}";
			document.getElementById("<portlet:namespace />OppIndustStartDate").value = "${OppIndustStartDate}";
			document.getElementById("<portlet:namespace />OppIndustEndDate").value = "${OppIndustEndDate}";
			document.getElementsByClassName("note-editable")[0].innerHTML = "${OppIndustDescription}";
			document.getElementsByClassName("note-editable")[1].innerHTML = "${OppIndustEligibility}";
			document.getElementById("<portlet:namespace />FileEntryId").value = "${FileEntryId}";
			document.getElementById("OppIndustImage-text").innerHTML = "${OppIndustImageName}";
			$("#<portlet:namespace />OppIndustType").val("${OppIndustType}").change();
		}
	    
	    function validateEmail(email) {
			const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			return re.test(String(email).toLowerCase());
		}
		
		var img = document.createElement("img");
		img.src = "/o/energy-hub-theme/images/img/user.jpg";
	});
	
	$('#<portlet:namespace />OppIndustStartDate').on('change', function(e){
		var startDate = $(this).val();
    	$('#<portlet:namespace />OppIndustEndDate').attr('min', startDate);
	});
	$('#<portlet:namespace />OppIndustEndDate').on('change', function(e){
		var endDate = $(this).val();
		$('#<portlet:namespace />OppIndustStartDate').attr('max', endDate);
	});

	$('#clear').on('click', function (e) {
		e.preventDefault();
		$("form")[0].reset();
		$(this).parent().find('.error').remove()
		$("span.error").each(function () {
			$(this).remove();
		});
		$("html, body").animate({ scrollTop: 0 }, "slow");
	}); 

	var realFileBtn = $("#<portlet:namespace/>OppIndustImage");
	var customBtn = document.getElementById("custom-button");
	var customTxt = document.getElementById("OppIndustImage-text");
	var base64String;

	customBtn.onclick = function() {
		realFileBtn.click();
	};

	realFileBtn.change(function() {
		if (realFileBtn.val()) {
			if (this.files && this.files[0]) {
				var reader = new FileReader();
				reader.onload = function(e) {
					customTxt.innerHTML = realFileBtn.val().match(/[\/\\]([\w\d\s\.\-\(\)]+)$/)[1];
					base64String = e.target.result;
				};
				reader.readAsDataURL(this.files[0]);
			  }
		} else {
			customTxt.innerHTML = "No file chosen";
		}
	}); 
	
	function ajaxCall(key) {
		var xhr = new XMLHttpRequest();
		var formData = new FormData();
		var data = {
			 <portlet:namespace />edit: ${edit},
			 <portlet:namespace />categId: ${categId},
			 <portlet:namespace />folderId: ${folderId},
			 <portlet:namespace />ddmStructureKey: ${ddmStructureKey},
			 <portlet:namespace />articleId: ${articleId},
			 <portlet:namespace />OppIndustTitle: $("#<portlet:namespace/>OppIndustTitle").val(),
			 <portlet:namespace />OppIndustURL: $("#<portlet:namespace/>OppIndustURL").val(),
			 <portlet:namespace />OppIndustStartDate: $("#<portlet:namespace/>OppIndustStartDate").val(),
			 <portlet:namespace />OppIndustEndDate: $("#<portlet:namespace/>OppIndustEndDate").val(),
			 <portlet:namespace />OppIndustType: $("#<portlet:namespace/>OppIndustType").val(),
			 <portlet:namespace />OppIndustDescription: $("#OppIndustDescription").summernote('code').replace(/<img[^>]*>/g,""),
			 <portlet:namespace />OppIndustEligibility: $("#OppIndustEligibility").summernote('code').replace(/<img[^>]*>/g,"")
		};
		
		var file = $("#<portlet:namespace/>OppIndustImage")[0]; //OppIndustImage
		if( file && file.files.length ) {
// 			formData.append('<portlet:namespace />OppIndustImage', file.files[0]);
			formData.append('<portlet:namespace />OppIndustImageName', file.files[0].name);
			formData.append('<portlet:namespace />OppIndustImage', base64String);
			formData.append('<portlet:namespace />OppIndustImageName', file.files[0].size);
		}
		
		xhr.onloadend = function (e) {
			window.location.href = location.origin +"/custom-forms?successMessage&${myProjectUrl}";
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
