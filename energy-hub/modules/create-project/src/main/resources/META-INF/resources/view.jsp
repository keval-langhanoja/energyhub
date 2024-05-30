<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

	<link rel="stylesheet" href="/o/energy-hub-theme/style/addInnovationProject.css">  
	<link rel="stylesheet" href="/o/energy-hub-theme/style/addNewProgram-success.css"> 
	
	<script src="/o/energy-hub-theme/js/bootstrap-tagsinput.min.js"></script>
<portlet:actionURL var="createProjectURL" />
<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>
<style>
	.form-group {
		margin-bottom: 0;
	}
	.dashedBorder {
    	border: 0.5px dashed #989898 !important;
	}
</style>

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
                   <a><liferay-ui:message key="AddInnovationProject" /></a>

               </div>
               <div class="">
                   <h1 class="position-relative MainTitle">
                       <div class="yellowBorder"></div>${the_title}
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

                   <a class="grayText px-2"><liferay-ui:message key="Back" /></a>
               </div>
           </div>
           <div class="userTypeCard position-relative topBorder mt-3 py-5 px-5">
				<aui:form id="createProject_form" cssClass="mt-5 registerationForm" enctype="multipart/form-data"  accept-charset="UTF8" method="POST">
                  <aui:input id="jspType" value ="view.jsp" name="jspType" type="hidden"></aui:input>
                  <aui:input id="edit" value ="${edit}" name="edit" type="hidden"></aui:input>
                  <aui:input id="articleId" value ="${articleId}" name="articleId" type="hidden"></aui:input>
                       <h1><liferay-ui:message key="GeneralInformation" /></h1>
                  	<div class="row">
                       <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer required">
				<label><liferay-ui:message key="ProjectTitle" /></label>
                      		<aui:input id="ProjectTitle" name="ProjectTitle" label=""></aui:input>
                      		<script language="javascript">
                      			if(${edit} == true)
									document.getElementById("<portlet:namespace />ProjectTitle").value = "${ProjectTitle}";
							</script>
                       </div>
                       <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer">
			   <label><liferay-ui:message key="ProjectStage" /></label>
                           <aui:select aria-labelledby="dropdownMenuButton"
								cssClass="browser-default custom-select custom-select-lg mb-3 signup_select"
								label="" id="ProjectStage" name="ProjectStage"> 
								 <c:forEach var="projectStage" items="${projectStageList}">
								 	<aui:option cssClass="dropdown-item" value="${projectStage.id}">
								 	${projectStage.value} 
									</aui:option> 
									<div class="dropdown-divider"></div>
								 </c:forEach>
							</aui:select> 
							<script language="javascript">
                      			if(${edit} == true)
                      				$("#<portlet:namespace />ProjectStage").val("${ProjectStage}").change();
							</script>
                       </div>
                       <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer">
				<label><liferay-ui:message key="ProjectCategory" /></label>
                       		<aui:select aria-labelledby="dropdownMenuButton"
								class="browser-default custom-select custom-select-lg mb-3 signup_select"
								label="" id="ProjectCategory" name="ProjectCategory"> 
								 <c:forEach var="projectCateg" items="${projectCategList}">
								 	<aui:option cssClass="dropdown-item" value="${projectCateg.id}">
								 	${projectCateg.value} 
									</aui:option> 
									<div class="dropdown-divider"></div>
								 </c:forEach>
							</aui:select> 
							<script language="javascript">
                      			if(${edit} == true)
                      				$("#<portlet:namespace />ProjectCategory").val("${ProjectCategory}").change();
							</script>
                       </div>
                   </div>
                   <div class="row">
                       <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer">
			   <label><liferay-ui:message key="WebsiteURL" /></label>
                           <aui:input id="WebsiteURL" name="WebsiteURL" label=""></aui:input>
                           <script language="javascript">
                      			if(${edit} == true)
									document.getElementById("<portlet:namespace />WebsiteURL").value = "${WebsiteURL}";
							</script>
                       </div>
                       <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainerCheckbox">
			   				<label><liferay-ui:message key="AddComments" /></label>
                             <aui:input id="addComments" type="checkbox" name="addComments" value="true" label="" />
                             <script language="javascript">
                      			if(${edit} == true)
									document.getElementById("<portlet:namespace />addComments").value = "${addComments}";
							</script>
                       </div>
                   </div>
                   <div class="separator"></div>
                       <h1><liferay-ui:message key="AboutTheProject" /></h1>
                   <div class="row">
                       <div class="col-12 col-md-6 mt-4 description inputContainer required">
						<label><liferay-ui:message key="PleaseDescribeTheProductSolutionYouAreOffering" /></label>
                            <aui:input cssClass="" id="SolutionOfferingDescription" type="textarea"  name="SolutionOfferingDescription" label=""></aui:input>
                            <script language="javascript">
                      			if(${edit} == true)
									document.getElementById("<portlet:namespace />SolutionOfferingDescription").value = "${SolutionOfferingDescription}";
							</script>
                       </div>
                       <div class="col-12 col-md-6">
                           <div class="col-12 mt-4 inputContainer required"> 
                                <label><liferay-ui:message key="UploadCoverImageFor" /></label>
                                <div class="add-innovation-dropdown uploadButton-div"> 
                                    <aui:input style="opacity:0;position: absolute;" type="file" id="coverImage" name="coverImage" label="" 
                           				accept="image/*" hidden="hidden"></aui:input>
                                    <button type="button" id="custom-button" class="uploadButton">
	                                    <img src="/o/energy-hub-theme/images/upload.svg"> <liferay-ui:message key="UploadFile" />
	                                </button>
                                   	<span id="coverImage-text"><liferay-ui:message key="NoFileChosen" /></span>
                                    <script language="javascript">
		                      			if(${edit} == true){
		                      				$("#coverImage-text").text("${coverImageName}");
		                      				$(".inputContainer").removeClass("required");
		                      			}
									</script>
									<aui:input id="FileEntryId" value ="${FileEntryId}" name="FileEntryId" type="hidden"></aui:input>
                                </div>
                           </div>
                           <div class="col-12 mt-4 inputContainer row border-bottom border-primary">
                               <label><liferay-ui:message key="AddTagsForProgram" /> "<liferay-ui:message key="TypeAndPressEnter" />"</label>
                               <div class="add-innovation-dropdown" value="" id="tagProgram">
	                               <aui:input id="tagProgramValues" name="tagProgramValues" type="hidden"></aui:input>
                               </div>
                               <script type="text/javascript"> 
									$('#tagProgram').tagsinput({
										itemValue: 'id',
										itemText: 'text',
										trimValue: true,
										allowDuplicates: false
									}); 
									
									var tag_programs = {}, selectedTags = {};
									
									<c:forEach var="tag" items="${selectedTags}">
									selectedTags['${tag.tagId}'] = '${tag.name}';
									$('#tagProgram').tagsinput('add', { id: '${tag.tagId}', text: '${tag.name}' });
									</c:forEach>
									
									<c:forEach var="tag" items="${allTags}">
										if( !selectedTags['${tag.tagId}'] ) {
											tag_programs['${tag.tagId}'] = '${tag.name}';
										}
								 	</c:forEach>
								</script>
                           </div>
                       </div>
                   </div>
                   <div class="separator"></div>
                       <h1><liferay-ui:message key="Documents" /></h1>
                   <div class="row">
                       <div class="col-12">
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
                    </div>
                    <div class="separator"></div>
                    <div class="inputContainer row border-bottom border-primary">
                        <div class="d-flex justify-content-between flex-wrap mb-">
                            <h1><liferay-ui:message key="InviteCollaboratorsToThisProject" /></h1>
                        </div>
                        <div class="add-innovation-dropdown" id="tagInvited">
                        	<aui:input id="tagInvitedValues" name="tagInvitedValuesInput" type="hidden"></aui:input>
							<script type="text/javascript"> 
								var availableUsers = {};
								$('#tagInvited').tagsinput({
									itemValue: 'id',
									itemText: 'text',
									trimValue: true,
									allowDuplicates: false
								});
								
								<c:forEach var="tag" items="${selectedUsers}">
								$('#tagInvited').tagsinput('add', { id: '${tag.id}', text: '${tag.name}' });
								</c:forEach>
								
								<c:forEach var="user" items="${allUsers}">
									availableUsers['${user.emailAddress}'] = {
										portraitId: ${user.portraitId},
										text: "${user.firstName} ${user.lastName}<br>${user.emailAddress}",
										firstName: "${user.firstName}",
										lastName: "${user.lastName}",
										name: "${user.firstName} ${user.lastName}".trim() ? "${user.firstName} ${user.lastName}" : "${user.emailAddress}",
										email: "${user.emailAddress}",
										img: "${user.comments}"
									};
								</c:forEach>
							</script>
					 	</div>
                    </div>
                    <div class="d-flex justify-content-end fixBtns my-5">
                    	<button class="blueBorderBtn reset mt-3 mx-3" type="" id="clearCreateAccount"><liferay-ui:message key="Clear"/></button>
				   		<aui:button cssClass="blueBtn submit mt-3" type="button" onclick="ajaxCall('key')" id="save" value="Proceed"></aui:button>
                    </div>
                </aui:form>
            </div>
        </div>
</div>

<link rel="stylesheet" type="text/css" href="/o/energy-hub-theme/style/bootstrap/bootstrap-tagsinput.css" />
<script src="/o/energy-hub-theme/js/bootstrap-tagsinput.min.js"></script>

<script> 
 
$('#clear').on('click', function (e) {
	e.preventDefault();
	$("form")[0].reset();
	$(this).parent().find('.error').remove()
	$("span.error").each(function () {
        $(this).remove();
    });
  	$("html, body").animate({ scrollTop: 0 }, "slow");
}); 


var realFileBtn = $("#<portlet:namespace/>coverImage");
var customBtn = document.getElementById("custom-button");
var docsInput = $("#<portlet:namespace/>docs");
var customTxt = document.getElementById("coverImage-text");
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
		 <portlet:namespace />FileEntryId: $("#<portlet:namespace/>FileEntryId").val(),
		 <portlet:namespace />removedDocs: removedDocs.noinput.join(','),
		 <portlet:namespace />docEntryIds: $("#<portlet:namespace/>docEntryIds").val()
	};
	
// 	var docNames = [], docB64 = [], doccs = [];
// 	$.each(allDocInputs, function (xx, docInput) {
// 		var input_id = docInput.attr('id');
// 		var file = $("#<portlet:namespace/>docs"+(xx ? '-'+xx : ''))[0];
// 		if( file && file.files.length ) {
// 			$.each(file.files, function (zz, ff) {
// 				if( !removedDocs[input_id] || removedDocs[input_id].indexOf(ff.name)==-1 ) {
// 					var obj = {};
// 					obj.b64 = getBase64(ff);
// 					obj.name = ff.name;
// // 					formData.append('<portlet:namespace />docs', ff);
// 					docNames.push(ff.name);
// 					doccs.push(obj);
// 				}
// 			});
// 		}
// 	});
	formData.append('<portlet:namespace />docNames', docNames.join(','));
	formData.append('<portlet:namespace />docs', doccs);
	
	var file = $("#<portlet:namespace/>coverImage")[0];
	if( file && file.files.length ) {
// 		formData.append('<portlet:namespace />coverImage', file.files[0]);
		formData.append('<portlet:namespace />coverImageName', file.files[0].name);
		formData.append('<portlet:namespace />coverImage', base64String);
	}
	
	xhr.onloadend = function (e) {
		window.location.href = location.origin +"/add-project?Overview";
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

$('body').scroll(function(){
	$('#tagProgram-dropdown').remove();
	 $('#tagInvited-dropdown')
});
$(document).ready(function () {
	
	function validateEmail(email) {
	    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    return re.test(String(email).toLowerCase());
	}
	
    var img = document.createElement("img");
    img.src = "/o/energy-hub-theme/images/img/user.jpg";
    
    $('#tagProgram').off('beforeItemRemove').on('beforeItemRemove', function(event) {
    	tag_programs[event.item.id] = event.item.text;
    	$('#tagProgram-dropdown').remove();
    }).off('itemAdded').on('itemAdded', function(event) {
    	$('#tagProgram-dropdown').remove();
    })
    // find input and on un-focus remove the dropdown menu
    .prev('.bootstrap-tagsinput').find('input').on('blur', function () {
    	setTimeout(function () {
    		$('#tagProgram-dropdown').remove();
    	}, 100)
    })
    // set event on keydown to detect the enter key and on any key show the dropdown menu
    .off('keydown').on('keydown', function (e) {
    	// if the enter key clicked then add the item to the tags
        if( e.keyCode == 13 ) {
            e.preventDefault();
            var selected = $('#tagProgram-dropdown').find('a.active');
            if( selected.length ) {
            	var id = selected.data('id')+'';
            	// add the clicked item to the tags
        		$('#tagProgram').tagsinput('add', {id: id, text: selected.text()});
        		// remove key from removed tags
        		delete tag_programs[id];
            } else if( e.target.value.trim() ) {
            	$('#tagProgram').tagsinput('add', {id: (-1*Date.now())+'', text: e.target.value.trim()});
            }
            e.target.value = '';
        }
    	
        // if any key entered then show the dropdown menu
        // if not removed item in the tags then don't do any thing
        else if( Object.keys(tag_programs).length ) {
        	if( !$('#tagProgram-dropdown').length ) {
	       		$('body').append('<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu" id="tagProgram-dropdown"></ul>');
	        	$('#tagProgram-dropdown').css({	
	        		position: 'absolute',
	        		display: 'inline-block',
	        		top: $(e.target).offset().top+75,
	        		left: $(e.target).offset().left+175,
	        	}).html((function () {
	        		var items = [];
	        		$.each(tag_programs, function (id, text) {
	        			items.push('<li><a tabindex="-1" href="javascript:;" data-id="'+id+'" style="text-decoration:none;">'+text+'</a></li>');
	        		});
	        		return items.join('');
	        	})());
	        	
	        	// add event on the menu item, when click then add it to the tags
	        	$('#tagProgram-dropdown').find('li a').off('click').on('click', function () {
	        		var id = ''+$(this).data('id');
	        		// add the clicked item to the tags
	        		$('#tagProgram').tagsinput('add', {id: id, text: $(this).text()});
	        		// remove key from removed tags
	        		delete tag_programs[id];
	        		// reset input
	        		e.target.value = '';
	        	});
        	}
        	
        	if( e.keyCode == 38 ) {
        		var activeItem = $('#tagProgram-dropdown').find('a.active');
        		if( activeItem.length ) {
        			var prev = activeItem.closest('li').prev('li').find('a');
        			if( prev.length ) {
	        			prev.addClass('active');
        			}
       				activeItem.removeClass('active');
        		}
        	} else if( e.keyCode == 40 ) {
        		var activeItem = $('#tagProgram-dropdown').find('a.active').removeClass('active');
        		if( !activeItem.length ) {
        			activeItem = $('#tagProgram-dropdown').find('a:first').addClass('active');
        		} else {
        			var next = activeItem.closest('li').next('li').find('a');
        			if( next.length ) {
        				activeItem = next.addClass('active');
        			}
        		}
        	}
        }
    });
    
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

</script>
