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
				<div class="row" style="padding: 0 30px;">
					<h1><liferay-ui:message key="SuccessStory" /></h1>
					<div class="col-4 mt-4 inputContainer">
						<label><liferay-ui:message key="Name" /></label>
						<aui:input id="SuccessStoryTitle" name="SuccessStoryTitle" label=""></aui:input>
					</div>
					<div class="col-4 mt-4 inputContainer">
						<label><liferay-ui:message key="Size" /></label>
						<aui:input id="SuccessStorySize" name="SuccessStorySize" label=""></aui:input>
					</div>
					<div class="col-4 mt-4 inputContainer">
						<label><liferay-ui:message key="Type" /></label>
						<aui:select aria-labelledby="dropdownMenuButton" class="browser-default custom-select custom-select-lg mb-3 signup_select"
							label="" id="SuccessStoryType" name="SuccessStoryType"> 
							 <c:forEach var="type" items="${SuccessStoryType}">
								<aui:option cssClass="dropdown-item" value="${type.id}">
								<liferay-ui:message key="${type.value}"/>
								</aui:option> 
								<div class="dropdown-divider"></div>
							 </c:forEach>
						</aui:select> 
					</div>
					<div class="col-12 mt-4 required inputContainer">
						<label><liferay-ui:message key="UploadFile" /></label>
						<div class="add-innovation-dropdown uploadButton-div"> 
							 <aui:input style="opacity:0;position: absolute;" type="file" id="SuccessStoryImage" name="SuccessStoryImage" label="" 
									accept="image/*" hidden="hidden"></aui:input>
							 <button type="button" id="custom-button" class="uploadButton">
							  <img src="/o/energy-hub-theme/images/upload.svg"><liferay-ui:message key="UploadFile" />
							</button>
							<span id="SuccessStoryImage-text"><liferay-ui:message key="NoFileChosen" /></span> 
						 </div>
					</div>
					<div class="col-12 mt-4 inputContainer">
						<label><liferay-ui:message key="UploadFile" /></label>
						<div class="d-flex flex-wrap justify-content-start align-items-center mt-4 py-3 px-2 dashedBorder m-document-cons">
							<div class="my-2 mx-2 uploadCard text-center" style="width:189px">
								 <button type="button" class="upload my-2"><img src="/o/energy-hub-theme/images/upload.svg"></button>
								 <label><liferay-ui:message key="Upload" /></label>
								 <aui:input style="opacity:0;position: absolute;" type="file" id="docs" name="docs"
									hidden="hidden" multiple="true" label=""></aui:input>
								 <aui:input style="opacity:0;position: absolute;" type="file" id="docs-1" name="docs"
									hidden="hidden" multiple="true" label=""></aui:input>
							 </div>
							 <aui:input type="hidden" name="docEntryIds" value="${docEntryIds}" id="docEntryIds"></aui:input>
							 <c:forEach var="doc" items="${docArr}">
							  <div class="document my-2 mx-2 text-center">
							<button class="remove" onclick="removeDoc('noinput', '${doc.docFileEntryId}')"><img src="/o/energy-hub-theme/images/remove.svg"></button>
							<div class="docDetails">
								<img class="my-2" style="max-width:60px;" src="/o/energy-hub-theme/images/${doc.docExt}.svg" onerror="this.src='/o/energy-hub-theme/images/documents.png'; this.onerror=null;">
								<p>${doc.docTitle}</p>
							</div>
						</div>
							 </c:forEach>
						 </div>
					</div>
					<div class="col-12 mt-4 description inputContainer ">
						<label><liferay-ui:message key="Description" /></label>
						<div id="SuccessStoryDescription"></div>
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
	    $('#SuccessStoryDescription').summernote();
	    $('.note-icon-picture').parent('.note-btn').hide();
	    $('.note-icon-video').parent('.note-btn').hide();
	    
	    if(${edit} == true) {
			document.getElementById("<portlet:namespace />SuccessStoryTitle").value = "${SuccessStoryTitle}";
			document.getElementById("<portlet:namespace />SuccessStorySize").value = "${SuccessStorySize}";
			document.getElementsByClassName("note-editable")[0].innerHTML = "${SuccessStoryDescription}";
			document.getElementById("<portlet:namespace />FileEntryId").value = "${FileEntryId}";
			document.getElementById("SuccessStoryImage-text").innerHTML = "${SuccessStoryImageName}";
			$("#<portlet:namespace />SuccessStoryType").val("${SuccessStoryType}").change();
		}
	    
	    function validateEmail(email) {
			const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			return re.test(String(email).toLowerCase());
		}
		
		var img = document.createElement("img");
		img.src = "/o/energy-hub-theme/images/img/user.jpg";
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

	var realFileBtn = $("#<portlet:namespace/>SuccessStoryImage");
	var customBtn = document.getElementById("custom-button");
	var customTxt = document.getElementById("SuccessStoryImage-text");
	var docsInput = $("#<portlet:namespace/>docs");
	var base64String;
	var base64StringFiles;
	
	
	var docTypes = ['jpg', 'jpeg', 'png', 'pdf'];
	var logoTypes = ['jpg', 'jpeg', 'png'];

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
	
	var allDocInputs = [docsInput];
	$('.uploadCard .upload').on('click', function () {
		var foundEmptyInput = false;
		$.each(allDocInputs, function (xx, input) {
			if( !input[0].files.length ) {
				foundEmptyInput = true;
				input.click();
				return false;
			}
		});
		
		if( !foundEmptyInput ) {
			var input_id = docsInput.attr('id')+'-'+allDocInputs.length,
				newInput = $("#"+input_id);
			
			if( !newInput.length ) {
				newInput = $(docsInput[0].outerHTML.replace('id=', 'old-id=')).appendTo('div.uploadCard .input-text-wrapper');
				newInput.attr('id', input_id);
			}
			allDocInputs.push(newInput);
			newInput.click();
		}
	});

	var removedDocs = {noinput: []};
	$('.uploadCard').on('change', 'input[type=file]', function() {
		var input_id = $(this).attr('id');
		$.each(this.files, function (i, file) {
			var filename = file.name.split('.'),
				fileExt = filename.pop();
			
			$('.m-document-cons').append(
				'<div class="document my-2 mx-2 text-center">'+
					'<button class="remove" onclick="removeDoc(\''+input_id+'\', \''+filename+'\')"><img src="/o/energy-hub-theme/images/remove.svg"></button>'+
					'<div class="docDetails">'+
						'<img class="my-2" style="max-width:60px;" src="/o/energy-hub-theme/images/'+fileExt+'.svg" onerror="this.src=\'/o/energy-hub-theme/images/documents.png\'; this.onerror=null;">'+
						'<p>'+file.name+'</p>'+
					'</div>'+
				'</div>'
			);
		});
	});

	function removeDoc(input_id, filename) {
		if( !removedDocs[input_id] ) {
			removedDocs[input_id] = [];
		}
		removedDocs[input_id].push(filename);
	}
	
  	var docNames = [], doccs = [];
	function executeAjax(allDocInputs, callback) {
  		var count = allDocInputs.length;

		$.each(allDocInputs, function (xx, docInput) {
			var input_id = docInput.attr('id');
			var file = $("#<portlet:namespace/>docs"+(xx ? '-'+xx : ''))[0];
			if( file && file.files.length ) {
				$.each(file.files, function (zz, ff) {
					if( !removedDocs[input_id] || removedDocs[input_id].indexOf(ff.name)==-1 ) {
						checkFile(ff);
					}
				});
			}
		});
	
	  function checkFile(ff) {
	  	var reader2 = new FileReader();
		reader2.onload = function () {
			var obj = {};
			obj.b64 = reader2.result;
			obj.name = ff.name;
			docNames.push(ff.name);
			doccs.push(obj);
		 	if (!--count) callback(doccs); 
  		};
	     reader2.readAsDataURL(ff);
	  }
	};
		
	function ajaxCall(key) {
		executeAjax(allDocInputs, ajaxCallFinal);
	}
	
	function ajaxCallFinal(){ 
		var xhr = new XMLHttpRequest();
		var formData = new FormData();
		var data = {
			 <portlet:namespace />edit: ${edit},
			 <portlet:namespace />categId: ${categId},
			 <portlet:namespace />folderId: ${folderId},
			 <portlet:namespace />ddmStructureKey: ${ddmStructureKey},
			 <portlet:namespace />articleId: ${articleId},
			 <portlet:namespace />SuccessStoryTitle: $("#<portlet:namespace/>SuccessStoryTitle").val(),
			 <portlet:namespace />SuccessStorySize: $("#<portlet:namespace/>SuccessStorySize").val(),
			 <portlet:namespace />SuccessStoryType: $("#<portlet:namespace/>SuccessStoryType").val(),
			 <portlet:namespace />SuccessStoryDescription: $("#SuccessStoryDescription").summernote('code').replace(/<img[^>]*>/g,"")
		};
		
		var file = $("#<portlet:namespace/>SuccessStoryImage")[0]; //SuccessStoryImage
		if( file && file.files.length ) {
// 			formData.append('<portlet:namespace />SuccessStoryImage', file.files[0]);
			formData.append('<portlet:namespace />SuccessStoryImageName', file.files[0].name);
			formData.append('<portlet:namespace />SuccessStoryImage', base64String);
			formData.append('<portlet:namespace />SuccessStoryImageSize', file.files[0].size);
		}
		
		formData.append('<portlet:namespace />SuccessStoryMediaNames', docNames.join(','));
		formData.append('<portlet:namespace />SuccessStoryMedia', JSON.stringify(doccs));

		xhr.onloadend = function (e) {
			window.location.href = location.origin +"/commercial-success-stories";
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
