<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="/o/energy-hub-theme/style/newsletter.css">
<portlet:actionURL var="energyProgramURL" /> 
<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>	
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
	    min-height: 80px;
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
<div class="modal fade" id="newsForm" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
			<div class="modal-header" id="modal-header">
	        	<h4 class="modal-title" id="modal-title"><liferay-ui:message key="AddNews" /></h4>
	            <button type="button" class="close" data-dismiss="modal">X</button>
	       	</div>
			<!-- Modal body -->
	       	<div class="modal-body" id="modal-body">
	        	<aui:form id="createAccount_form" cssClass="registerationForm" 
					style="max-height: 470px;" accept-charset="UTF8" method="POST">
				   <div class="row">
					   <div class="col-12 col-md-12 mt-4 required inputContainer">
							<label><liferay-ui:message key="Type" /></label>
							<aui:select cssClass="browser-default custom-select custom-select-lg mb-4 signup_select"
								label="" id="NewsType" name="NewsType"> 
								<aui:option disabled="true" selected="true" value=""><liferay-ui:message key="SelectOption" /></aui:option>
								<c:forEach var="elt" items="${NewsType}">
									<aui:option cssClass="dropdown-item" value="${elt.id}">
										${elt.value} 
									</aui:option> 
								 </c:forEach>
							</aui:select>
					   </div>
				   </div>
				  <div class="row">
					   <div class="col-12 col-md-12 mt-4 required inputContainer">
						   	<label><liferay-ui:message key="Title" /></label>
							<aui:input id="NewsTitle" name="NewsTitle" label=""></aui:input>
					   </div>
				  </div>
				  <div class="row">
						<div class="col-12 mt-4 inputContainer required">
							<label><liferay-ui:message key="UploadCoverImageFor" /></label>
							<div class="add-innovation-dropdown uploadButton-div">
								<aui:input style="opacity:0;position: absolute;" type="file" id="NewsImage" name="NewsImage" 
									label="" accept="image/*" hidden="hidden"/>
								<button type="button" id="custom-button" class="uploadButton">
									<img src="/o/energy-hub-theme/images/upload.svg"> <liferay-ui:message key="UploadFile" />
								</button>
									<span id="NewsImage-text"><liferay-ui:message key="NoFileChosen" /></span>
									<aui:input id="FileEntryId" value="${FileEntryId}" name="FileEntryId" type="hidden"/>
								</div>
						</div>
					</div>
					  <div class="row">
						<div class="col-12 col-md-12 mt-4 description inputContainer">
							<label><liferay-ui:message key="Description" /></label>
							<div id="NewsDescription"></div>  
						</div> 
					</div>
					<div class="d-flex justify-content-end fixBtns my-2"">
						<button class="blueBorderBtn reset mt-3 mx-3" type="" id="clearCreateAccount" value="Clear"><liferay-ui:message key="Clear" /></button>
						<aui:button cssClass="blueBtn submit mt-3" type="submit" onclick="ajaxCall('addProgram')" id="save" value="Send"></aui:button>
					</div>
				   </div>
				   <aui:input id="coverImageName" value ="${logoName}" name="coverImageName" type="hidden"></aui:input>
				</aui:form>
	       	</div>
        </div>
    </div>
</div>

<script>
	$(document).ready(function () {
	    $('#NewsDescription').summernote();
	    $('.note-icon-picture').parent('.note-btn').hide();
	    $('.note-icon-video').parent('.note-btn').hide();
	});

	var realFileBtn = $("#<portlet:namespace/>NewsImage");
	var customBtn = document.getElementById("custom-button");
	var customTxt = document.getElementById("NewsImage-text");
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
	
	function ajaxCall(key) {
		var xhr = new XMLHttpRequest();
		var formData = new FormData();
		var data = {
			 <portlet:namespace />NewsTitle: $("#<portlet:namespace/>NewsTitle").val(),
			 <portlet:namespace />NewsType: $("#<portlet:namespace/>NewsType").val(),
			 <portlet:namespace />NewsDescription: $("#NewsDescription").summernote('code').replace(/<img[^>]*>/g,"")
		};
		var file = $("#<portlet:namespace/>NewsImage")[0];
		if( file && file.files.length ) {
// 			formData.append('<portlet:namespace />NewsImage', file.files[0]);
			formData.append('<portlet:namespace />NewsImage', base64String);
			formData.append('<portlet:namespace />NewsImageName', file.files[0].name);
		}
		
		xhr.onloadend = function (e) {
			window.location.href = location.origin +"/news?allNews";
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