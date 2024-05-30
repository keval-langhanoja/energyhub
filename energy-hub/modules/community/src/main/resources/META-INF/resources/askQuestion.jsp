<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

<link rel="stylesheet" href="/o/energy-hub-theme/style/communityForum.css">
<link rel="stylesheet" type="text/css" href="/o/energy-hub-theme/style/bootstrap/bootstrap-tagsinput.css" />

    <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
    
<script src="/o/energy-hub-theme/js/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="/o/energy-hub-theme/js/ckeditor.js"></script> 
<portlet:actionURL var="communityURL" />
<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>

<style>
	.programList a.active:before {
		left: 0 !important;
		width: 3px !important;
	}
	.programList a {
	    font-weight: 700 !important;
	    text-transform: capitalize !important;
	}
	.card {
		border : 1px solid rgba(0,0,0,.125) !important;
	    border-radius: 0.25rem !important;
	}
	.form-group {
		margin-bottom: 0 !important;
	}
	.registerationForm div.bootstrap-tagsinput {
	    border: 0.5px solid #989898 !important;
        min-height: 59px;
	}
	.bootstrap-tagsinput input {
	    min-height: 59px;
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

<div class="row justify-content-between topTools pb-3">
    <div class="col-4 dropdown programsDropdown mt-3" data-toggle="dropdown" aria-expanded="false">
        <div class=""><span><liferay-ui:message key="Menu" /></span><img class="mx-3" src="/o/energy-hub-theme/images/header/downArrow.svg">
        </div>
        <div class="dropdown-menu">
            <a href="javascript:;" onclick="location.href ='/policy-forum?view&p_r_p_categoryId=${categoryId}'+
							'&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${threadType}'" class="active">
                <svg xmlns="http://www.w3.org/2000/svg" width="13" height="9.334" viewBox="0 0 13 9.334">
                    <g id="Component_437_1" data-name="Component 437 – 1" transform="translate(1 1)">
                      <path id="Path_15229" data-name="Path 15229" d="M56,234.5h7.945" transform="translate(-52.944 -234.5)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
                      <path id="Path_15230" data-name="Path 15230" d="M56,239h7.945" transform="translate(-52.944 -235.334)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
                      <path id="Path_15231" data-name="Path 15231" d="M56,243.5h7.945" transform="translate(-52.944 -236.166)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
                      <path id="Path_15232" data-name="Path 15232" d="M52.25,234.5h.006" transform="translate(-52.25 -234.5)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
                      <path id="Path_15233" data-name="Path 15233" d="M52.25,239h.006" transform="translate(-52.25 -235.334)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
                      <path id="Path_15234" data-name="Path 15234" d="M52.25,243.5h.006" transform="translate(-52.25 -236.166)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
                    </g>
                  </svg>
                <span><liferay-ui:message key="Questions" /></span></a>
            <a href="#">
                <svg xmlns="http://www.w3.org/2000/svg" width="17" height="17" viewBox="0 0 17 17">
                    <g id="Component_459_1" data-name="Component 459 – 1" transform="translate(1 1)">
                      <path id="Path_15266" data-name="Path 15266" d="M59,359.5a7.5,7.5,0,1,0-7.5-7.5A7.5,7.5,0,0,0,59,359.5Z" transform="translate(-51.5 -344.5)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
                      <path id="Path_15267" data-name="Path 15267" d="M56.818,349.75a2.25,2.25,0,0,1,4.372.75c0,1.5-2.25,2.25-2.25,2.25" transform="translate(-51.5 -344.5)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
                      <path id="Path_15268" data-name="Path 15268" d="M59,355.75h.007" transform="translate(-51.5 -344.5)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
                    </g>
                  </svg>
                <span><liferay-ui:message key="YourQuestions" /></span>
            </a>
                <a href="#">
                   <svg xmlns="http://www.w3.org/2000/svg" width="15.5" height="15.5" viewBox="0 0 15.5 15.5">
                       <g id="Component_460_1" data-name="Component 460 – 1" transform="translate(1 1)">
                         <path id="Path_15270" data-name="Path 15270" d="M65.75,402.625a6.286,6.286,0,0,1-.675,2.85,6.375,6.375,0,0,1-5.7,3.525,6.284,6.284,0,0,1-2.85-.675L52.25,409.75l1.425-4.275a6.286,6.286,0,0,1-.675-2.85,6.374,6.374,0,0,1,3.525-5.7,6.284,6.284,0,0,1,2.85-.675h.375a6.36,6.36,0,0,1,6,6Z" transform="translate(-52.25 -396.25)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
                       </g>
                     </svg>
                   <span><liferay-ui:message key="YourAnswers" /></span>
               </a> 
              <a href="javascript:;"
				onclick="location.href ='/policy-forum?yourdrafts&p_r_p_categoryId=${categoryId}'+
				'&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${threadType}'">
			  <span><img class="mb-1 plus" src="/o/energy-hub-theme/images/draft.svg"><liferay-ui:message key="YourDrafts" /></span> 
			</a> 
        </div>
    </div>
</div>
<div class="mainGrid grid">
    <div class="programList">
        <uL>
            <li><a href="#"><span><liferay-ui:message key="Menu" /></span></a>
            </li>
            <li><a class="active" href="javascript:;" onclick="location.href ='/policy-forum?view&p_r_p_categoryId=${categoryId}'+
							'&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${threadType}'">
                <svg xmlns="http://www.w3.org/2000/svg" width="13" height="9.334" viewBox="0 0 13 9.334">
                    <g id="Component_437_1" data-name="Component 437 – 1" transform="translate(1 1)">
                      <path id="Path_15229" data-name="Path 15229" d="M56,234.5h7.945" transform="translate(-52.944 -234.5)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
                      <path id="Path_15230" data-name="Path 15230" d="M56,239h7.945" transform="translate(-52.944 -235.334)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
                      <path id="Path_15231" data-name="Path 15231" d="M56,243.5h7.945" transform="translate(-52.944 -236.166)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
                      <path id="Path_15232" data-name="Path 15232" d="M52.25,234.5h.006" transform="translate(-52.25 -234.5)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
                      <path id="Path_15233" data-name="Path 15233" d="M52.25,239h.006" transform="translate(-52.25 -235.334)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
                      <path id="Path_15234" data-name="Path 15234" d="M52.25,243.5h.006" transform="translate(-52.25 -236.166)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
                    </g>
                  </svg>    
                <span><liferay-ui:message key="Questions" /></span></a>
            </li>
           <c:choose>
				<c:when test="${!isSignedIn}">
					<li><a href="#"> <img
							src="/o/energy-hub-theme/images/userBlue.svg"> <span
							class="loginTxt"><liferay-ui:message key="LoginToJoinDiscussions" /></span></a></li>
				</c:when>
				<c:when test="${isSignedIn}">
					<li><a href="javascript:;"
						onclick="location.href ='/policy-forum?yourquestions&p_r_p_categoryId=${categoryId}'+
						'&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${threadType}'">
							<svg xmlns="http://www.w3.org/2000/svg" width="17" height="17"
								viewBox="0 0 17 17">
                                        <g id="Component_459_1"
									data-name="Component 459 – 1" transform="translate(1 1)">
                                            <path id="Path_15266"
									data-name="Path 15266"
									d="M59,359.5a7.5,7.5,0,1,0-7.5-7.5A7.5,7.5,0,0,0,59,359.5Z"
									transform="translate(-51.5 -344.5)" fill="none" stroke="gray"
									stroke-linecap="round" stroke-linejoin="round" stroke-width="2" />
                                            <path id="Path_15267"
									data-name="Path 15267"
									d="M56.818,349.75a2.25,2.25,0,0,1,4.372.75c0,1.5-2.25,2.25-2.25,2.25"
									transform="translate(-51.5 -344.5)" fill="none" stroke="gray"
									stroke-linecap="round" stroke-linejoin="round" stroke-width="2" />
                                            <path id="Path_15268"
									data-name="Path 15268" d="M59,355.75h.007"
									transform="translate(-51.5 -344.5)" fill="none" stroke="gray"
									stroke-linecap="round" stroke-linejoin="round" stroke-width="2" />
                                        </g>
                                    </svg> <span><liferay-ui:message key="YourQuestions" /></span>
					</a></li>
					<li><a href="javascript:;"
						onclick="location.href ='/policy-forum?youranswers&p_r_p_categoryId=${categoryId}'+
						'&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${threadType}'">
							<svg xmlns="http://www.w3.org/2000/svg" width="15.5"
								height="15.5" viewBox="0 0 15.5 15.5">
                                    <g id="Component_460_1"
									data-name="Component 460 – 1" transform="translate(1 1)">
                                        <path id="Path_15270"
									data-name="Path 15270"
									d="M65.75,402.625a6.286,6.286,0,0,1-.675,2.85,6.375,6.375,0,0,1-5.7,3.525,6.284,6.284,0,0,1-2.85-.675L52.25,409.75l1.425-4.275a6.286,6.286,0,0,1-.675-2.85,6.374,6.374,0,0,1,3.525-5.7,6.284,6.284,0,0,1,2.85-.675h.375a6.36,6.36,0,0,1,6,6Z"
									transform="translate(-52.25 -396.25)" fill="none" stroke="gray"
									stroke-linecap="round" stroke-linejoin="round" stroke-width="2" />
                                    </g>
                                </svg> <span><liferay-ui:message key="YourAnswers" /></span>
					</a></li>
					<li><a href="javascript:;"
							onclick="location.href ='/policy-forum?yourdrafts&p_r_p_categoryId=${categoryId}'+
							'&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${threadType}'">
							  <span><img class="mb-1 plus" src="/o/energy-hub-theme/images/draft.svg"><liferay-ui:message key="YourDrafts" /></span> 
						</a></li>
				</c:when>
			</c:choose>
        </uL>
    </div>
    <div class="pb-5">
        <aui:form  id="askQuestion_form" cssClass="mt-5 registerationForm" enctype="multipart/form-data"  accept-charset="UTF8" method="POST">
            <div class="row">
                <h1><liferay-ui:message key="StartAConversation" /></h1>
                <div class="col-12 mt-4 inputContainer">
					<label><liferay-ui:message key="ThreadTitle" /></label>
                	<aui:input id="ThreadTitle" name="ThreadTitle" label=""></aui:input>
                </div>
                <div class="col-12 mt-4 inputContainer">
					<label><liferay-ui:message key="ThreadType" /></label>
                    <aui:select aria-labelledby="dropdownMenuButton" class="browser-default custom-select custom-select-lg mb-3 signup_select"
						label="" id="ThreadType" name="ThreadType"> 
						 <c:forEach var="type" items="${ThreadTypeList}">
						 	<aui:option cssClass="dropdown-item" value="${type.id}">
						 	${type.value} 
							</aui:option> 
							<div class="dropdown-divider"></div>
						 </c:forEach>
					</aui:select> 
                </div> 
                <div class="col-12 mt-4 inputContainer">
					<label><liferay-ui:message key="ThreadSubject" /></label>
                    <aui:select aria-labelledby="dropdownMenuButton" class="browser-default custom-select custom-select-lg mb-3 signup_select"
						label="" id="ProjectCategory" name="ProjectCategory"> 
						 <c:forEach var="category" items="${ProjectCategoryList}">
						 	<aui:option cssClass="dropdown-item" value="${category.id}">
						 	${category.value} 
							</aui:option> 
							<div class="dropdown-divider"></div>
						 </c:forEach>
					</aui:select> 
                </div> 
                <div class="col-12 mt-4 inputContainer">
                    <label><liferay-ui:message key="UploadThreadDocument" /></label>
                    <div class="add-innovation-dropdown uploadButton-div"> 
                         <aui:input style="opacity:0;position: absolute;" type="file" id="coverImage" name="coverImage" label="" 
                				accept="image/*" hidden="hidden"></aui:input>
                         <button type="button" id="custom-button" class="uploadButton">
                          <img src="/o/energy-hub-theme/images/upload.svg"><liferay-ui:message key="UploadFile" />
                      	</button>
                      	<span id="coverImage-text"><liferay-ui:message key="NoFileChosen" /></span> 
                     </div>
                </div>
                <div class="col-12 mt-4 inputContainer">
                    <label><liferay-ui:message key="AddTagsForThread" /> "<liferay-ui:message key="TypeAndPressEnter" />"</label>
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
                <div class="col-12 mt-4 description inputContainer ">
	                <label><liferay-ui:message key="QuestionDetails" /></label>
    				<div id="summernote"></div>            	
<%--                <aui:input id="QuestionDetails" type="textarea"  name="QuestionDetails" label="Thread Details"></aui:input> --%>
                </div>
               
            </div>
            <div class="d-flex justify-content-end fixBtns my-5">
                <button class="blueBorderBtn reset mt-3 mx-3 px-3" style="width: auto !important; font-size: 15px !important;" 
                	type="button" onclick="ajaxCall('draft')" id="save"><liferay-ui:message key="SaveAsDraft" /></button>
                <button class="blueBtn submit mt-3" type="button" onclick="ajaxCall('publish')" 
                	id="save"><liferay-ui:message key="Publish" /></button>
            </div>
            <aui:input id="folderId" value ="${folderId}" name="folderId" type="hidden"></aui:input>
            <aui:input id="ddmStructureKey" value ="${ddmStructureKey}" name="ddmStructureKey" type="hidden"></aui:input>
            <aui:input id="ddmTemplateKey" value ="${ddmTemplateKey}" name="ddmTemplateKey" type="hidden"></aui:input>
        </aui:form>
    </div>
</div>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>

<script>  
	$(document).ready(function () {
	    $('#summernote').summernote();
	    $('.note-icon-picture').parent('.note-btn').hide();
	    $('.note-icon-video').parent('.note-btn').hide();
	});

	var realFileBtn = $("#<portlet:namespace/>coverImage");
	var customBtn = document.getElementById("custom-button");
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
	
	function ajaxCall(key) {
		var xhr = new XMLHttpRequest();
		var formData = new FormData();
		var data = {
			 <portlet:namespace />ThreadTitle: $("#<portlet:namespace/>ThreadTitle").val(),
			 <portlet:namespace />ProjectCategory: $("#<portlet:namespace/>ProjectCategory").val(),
			 <portlet:namespace />ThreadType: $("#<portlet:namespace/>ThreadType").val(),
			 <portlet:namespace />QuestionDetails: $("#summernote").summernote('code').replace(/<img[^>]*>/g,""),
			 <portlet:namespace />tagProgramValues: JSON.stringify($('#tagProgram').tagsinput('items')),
			 <portlet:namespace />ddmTemplateKey: $("#<portlet:namespace/>ddmTemplateKey").val(),
			 <portlet:namespace />folderId: $("#<portlet:namespace/>folderId").val(),
			 <portlet:namespace />ddmStructureKey: $("#<portlet:namespace/>ddmStructureKey").val(),
			 <portlet:namespace />workflowAction: key,
		};
		
		var file = $("#<portlet:namespace/>coverImage")[0];
		if( file && file.files.length ) {
// 			formData.append('<portlet:namespace />coverImage', file.files[0]);
			formData.append('<portlet:namespace />coverImageName', file.files[0].name);
 			formData.append('<portlet:namespace />coverImage', base64String);
 			formData.append('<portlet:namespace />coverImageSize', file.files[0].size);
		}
		
		xhr.onloadend = function (e) {
			window.history.back();
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

	$('body').scroll(function(){
		$('#tagProgram-dropdown').remove();
  	});
	
	$(document).ready(function () {
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
	});
</script>
