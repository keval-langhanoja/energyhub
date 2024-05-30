<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="/o/energy-hub-theme/style/communityForum.css">
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>

<style>
	.project-detail {
	    min-width: 300px;
	}
	div.row.d-flex {
		margin: 0 !important;
	}
	textarea {
	    resize: none;
	    height: 70px;
	    width: 100%;
	}
	.flexDisplay {
        align-items: center;
	    flex-direction: row;
	    justify-content: flex-end;
	    display: flex;
    }
    .textarea-container {
	    border: 1px solid rgba(0,0,0,.125) !important;
    }
	.note-fontname, .note-color, .note-table, .btn-fullscreen,
	.note-table, .note-view, .note-style, .note-status-output, 
	.note-statusbar,.note-btn .note-icon-picture, .btn-codeview{
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
	.note-editor.note-frame {
		border: none;
	}
	.progMainDiv {
	    width: 100%;
	    background-color: white;
	    padding: 2rem;
	}
	.profileImageComment {
		width: 4rem;
		height: 4rem;
		margin-bottom: 1rem;
		border-radius: 50%;
		margin-left: -1rem;
		padding: 0rem;
	}
	.redirectTitle {
	    cursor: pointer;
	}
	.urlPreview{
	    width: 500px;
	}
	
	.linkPreview-data {
	    border: 1px solid #e5e5e5;
	    display: inline-grid;
	}
	.linkText-container {
	    display: flex;
	    flex-direction: column;
	    min-width: 0;
	    padding: 10px;
	    background-color: #f6f6f6;
	    text-align: left;
	}
	.linkText-container > * + * {
	    margin-top: 10px;
	}
	.linkHeader {
	    font-weight: 600;
	    font-size: 18px;
	}
	.linkDescription {
	    white-space: nowrap;
	    min-width: 0;
	    overflow: hidden;
	    display: -webkit-box;
	    -webkit-line-clamp: 2;
	    -webkit-box-orient: vertical;
	    overflow: hidden;
	    text-overflow: ellipsis;
	}
	.linkImg-container {
	    text-align: center;
	}
	.linkImg {
	    object-fit: contain;
	    width: 100%;
	    max-width: 100px;
	}
	.linkDomain {
	    text-transform: uppercase;
	}
	@keyframes bouncedelay {
	    0%,
	    100%,
	    80% {
	        transform: scale(0);
	    }
	    40% {
	        transform: scale(1);
	    }
	}
	.note-editable img{
		display: none;
	}
</style>
<c:if test="${showComments}">
	<div class="d-flex justify-content-between innovation-item-detail commentSectionAll"  id="${instanceId}">
		 <div class="progMainDiv">
	 		<div class="row d-flex comments-section">
				<div>
					<div class="row mb-3">
						<div class="col-4">
     						<span class=" col-4 comments-border2">${fn:length(comments)} <liferay-ui:message key="Comments" /></span>
		    			</div>
		    			<div class="flexDisplay col-8">
							<button class="sort  my-2" id="lastActivity">
								<span class="plus mb-1"><svg xmlns="http://www.w3.org/2000/svg" width="17" height="13" viewBox="0 0 9.148 9.148">
										<g id="Component_438_1" data-name="Component 438 â 1" transform="translate(0.5 0.5)">
											<path id="Path_15137" data-name="Path 15137" d="M375.074,121.148A4.074,4.074,0,1,0,371,117.074,4.075,4.075,0,0,0,375.074,121.148Z" transform="translate(-371 -113)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"></path>
											<path id="Path_15138" data-name="Path 15138" d="M376,115v2.445l1.63.815" transform="translate(-371.926 -113.371)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"></path>
										</g>
									</svg></span>
								<span><liferay-ui:message key="LastActivity" /></span>
							</button>
							<button class="sort my-2" id="top">
								<span class="plus mb-1"><svg xmlns="http://www.w3.org/2000/svg" width="17" height="11" viewBox="0 0 8.605 8.13">
										<g id="Component_446_1" data-name="Component 446 â 1" transform="translate(3.707 0) rotate(45,2,0)">
											<path id="Path_15164" data-name="Path 15164" d="M1021.5,324.755v-7.13" transform="translate(-1017.905 -317.625)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"></path>
											<path id="Path_15165" data-name="Path 15165" d="M1017.12,321.19l3.6-3.565,3.6,3.565" transform="translate(-1017.12 -317.625)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"></path>
										</g>
									</svg>
								</span>
								<span><liferay-ui:message key="Top" /></span>
							</button>
							<button class="sort my-2 active" id="createDate">
								<span class="plus mb-1"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="13" viewBox="0 0 9.846 9.16">
										<g id="Component_439_1" data-name="Component 439 â 1" transform="translate(0.501 0.505)">
											<path id="Path_15145" data-name="Path 15145" d="M593.148,116.7v.375a4.074,4.074,0,1,1-2.416-3.724" transform="translate(-585 -112.997)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"></path>
											<path id="Path_15146" data-name="Path 15146" d="M593.8,114l-4.074,4.078-1.222-1.222" transform="translate(-585.159 -113.182)" fill="none" stroke="gray" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"></path>
										</g>
									</svg>
								</span>
								<span><liferay-ui:message key="DateCreated" /></span>
							</button>
						</div>			
					</div>
					<c:if test="${isSignedIn}">
						<div class="row d-flex mb-4">
							<div class="d-flex profile-discussion">
								<div class="col-1">
									<img class="author-profile" src="${currentUserProfile}">
								</div>
								<div class="col-11 m-l-10 mainComment">
									<div class="textarea-container">
										<div id="new_comment"></div>
										<div class="flexDisplay p-2">
											<button onclick="ajaxCall('Postcomment', '${articleId}', '', 'new_comment')" 
											class="blueBtn submit post commentSubmit" value="Post"><liferay-ui:message key="Post" /></button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${commentsTotal gt 0}">
						<c:forEach var="comment" items="${comments}">
							<div class="d-flex comments-section mt-4 story-body">
								<div class="row d-flex" style="width: 100%;">
									<div class="d-flex">
										<div class="col-2" style="padding: 0;left: -15px;">
											<img class="author-profile profileImageComment" src="${comment.userPortrait}">
										</div>
										<div class="col-10  m-l-10 w-80 subComment">
											<p class="m-l-10 w-9r">
												<span class="color-name redirectTitle"  onclick="window.location.href = '${comment.commentUserProfileUrl}';">${comment.commentuserName}
													<span class="comments-items"><liferay-ui:message key="${comment.commentuserRole}" />   - ${comment.createDateDisplay}</span>
												</span>
											</p>
											<div class="m-l-10 commentText">${comment.comment}</div>
											<div class="row d-flex">
												<p>
													<img class="green-up-arrow" id="green-arrow" onclick="ajaxCall('like', '${comment.commentId}', '', '', '')"
														src="/o/energy-hub-theme/images/green-up-arrow.png">
													<span id="total-likers" class="green-likers">${comment.likecount}</span>
													<span class="comments-items">|</span>
													<img class="red-down-arrow" id="red-arrow"  onclick="ajaxCall('dislike', '${comment.commentId}', '', '')"
														src="/o/energy-hub-theme/images/red-down-arrow.png">
													<span id="total-likers" class="red-dislikers">${comment.dislikecount}</span>
													<img class="arrows" src="/o/energy-hub-theme/images/arrowRightBlue.svg">
													<a class="comments-items" onclick="showHideReply('${comment.commentId}')">Reply</a>
													<c:if test="${fn:length(comment.children) > 0}">
														<img class="arrows" src="/o/energy-hub-theme/images/doubleBlueArrow.svg"> 
														<script>
															var ids_${comment.commentId} = new Array();
														</script>
														<c:forEach var="com" items="${comment.children}">
															<script>
																 obj = new Object();
																 obj.id = ${com.commentId}; 
																 ids_${comment.commentId}.push(obj);
														 	</script>
														</c:forEach>
														<a class="comments-items" onclick="showHideReplies(ids_${comment.commentId})"><liferay-ui:message key="ShowReplies"/> (${fn:length(comment.children)})</a>
													</c:if>	
												</p>
											</div>
											<div class="textarea-container subComment" style="display: none" id="replyDiv_${comment.commentId}">
												<div id="reply_${comment.commentId}"></div>
												<script>
													$(document).ready(function () {
														$('#reply_${comment.commentId}').summernote();
													});
												</script>
												<div class="flexDisplay p-2">
													<button onclick="ajaxCall('Postcomment', '${articleId}', '${comment.commentId}',  'reply_${comment.commentId}	')" 
													class="blueBtn submit post commentSubmit" value="Post"><liferay-ui:message key="Post" /></button>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						 	<c:if test="${fn:length(comment.children) > 0}">
				            	<c:forEach var="child" items="${comment.children}">
							 		<c:set var="child" value="${child}" scope="request" />
							 		<c:set var="comment" value="${comment}" scope="request" />
			          				<jsp:include page="/childReply.jsp" />
		       				 	</c:forEach>
						  	</c:if>
						</c:forEach>
					</c:if>
					<c:if test="${commentsTotal gt 3}">
						<div class="seeAll text-end mt-4">
							<a> <p class="a_more">+<liferay-ui:message key="View3More" /></p> </a>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	
		<div class="project-detail" style="background-color: transparent;">
			<div class="row"></div>
		</div>
	</div>
</c:if>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>

<script>  
	$(document).ready(function () {
	    $('#new_comment').summernote();
	    $('.note-icon-picture').parent('.note-btn').hide();
	    $('.note-icon-video').parent('.note-btn').hide();
	    
		if(sortField != ""){
			$(".sort").removeClass("active");
			$('#'+ sortField).addClass("active");
		}
	});
	
	function showHideReply(id) {
		if(${isSignedIn} && document.getElementById("replyDiv_"+id).style.display =="none") {
			$('#replyDiv_'+id).attr('style','display:block !important');
		}else
			$('#replyDiv_'+id).attr('style','display:none !important');
	}
	
	function showHideReplies(ids) {
		for (var i = 0; i < ids.length; i++) {
			var id = ids[i].id;
			if(document.getElementById("repliesDiv_"+id).style.display =="none") {
					$('#repliesDiv_'+id).attr('style','display:block !important');
			}else
				$('#repliesDiv_'+id).attr('style','display:none !important');
		}
	}
	
	function ajaxCall(key, articleId, commentParentId, commentText) {
		var xhr = new XMLHttpRequest();
		var formData = new FormData();
		
		var promises = [];
		
		var finalComment ="";
		if(commentText !=''){
			var finalComment = $('#' + commentText).summernote('code').replace(/<img[^>]*>/g,"");
			
			var div = document.createElement('div');
			div.setAttribute('id', 'finalComment');
		  	div.innerHTML = finalComment.trim();
		  	
		  	var allAs = div.getElementsByTagName('a');
		  	var apiKey = '87ad9a8b29e1d1bb1516db1fa1b8c24c';
		  	
	    	allAs.forEach((elta, groupIndex) => {
	    		var match = elta.href;
    		 	var request =  $.ajax({
	                url: "https://api.linkpreview.net?key=" + apiKey + "&q=" + match,
	                success: function (result) {
	                    var preview =   '<a href="'+result.url+'">'+
	                                        '<div class="linkPreview-data">'+
	                                            '<div class="linkImg-container">'+
	                                                '<img class="linkImg" src='+result.image+'>'+
	                                            ' </div>'+
	                                            '<div class="linkText-container">'+
	                                                '<span class="linkHeader">'+result.title+'</span>'+
	                                                '<span class="linkDescription">'+result.description+'</span>'+
	                                            '</div>'+
	                                        '</div>'+
	                                    '</a>';
                        finalComment = finalComment.replace(elta.outerHTML,preview);
	                },
	                error: function (error) {
	                    console.log(error.status)
	                }
	            });
	            promises.push(request);
	        });
		}
		
		$.when.apply(null, promises).done(function() {
			var data = {
					 <portlet:namespace />key: key,
					 <portlet:namespace />articleId: articleId,
					 <portlet:namespace />commentParentId: commentParentId,
					 <portlet:namespace />commentText: finalComment
				};
				xhr.onloadend = function (e) {
					location.reload();
				} 
				xhr.onprogress = function (e) {
					if( e.lengthComputable ) {
						var percentComplete = e.loaded / e.total * 100;
						console.log('upload '+percentComplete+'%');
					}
				};
				
				xhr.open('POST', '${testAjaxResourceUrl}&'+$.param(data));
				xhr.send(formData); 
		});
	}
	
	var _searchParams = new URLSearchParams(location.search);
	var sortField = _searchParams.get('sortField') || "createDate";
	var sortKey = _searchParams.get('sort') || "desc";
	
	$('#createDate').on('click', function (e) {
		e.preventDefault();
		$(".sort").removeClass("active");
		$('#createDate').addClass("active");
		sortKey = sortKey=='asc' ? 'desc' : 'asc';
		sortField = "createDate";
		getData();
	});
	$('#top').on('click', function (e) {
		e.preventDefault();
		$(".sort").removeClass("active");
		$('#top').addClass("active");
		sortKey = sortKey=='asc' ? 'desc' : 'asc';
		sortField = "top";
		getData();
	});
	$('#lastActivity').on('click', function (e) {
		e.preventDefault();
		$(".sort").removeClass("active");
		$('#lastActivity').addClass("active");
		sortKey = sortKey=='asc' ? 'desc' : 'asc';
		sortField = "lastActivity";
		getData();
	});
	
	function getData() {
		var searchParams = new URLSearchParams(location.search);
		searchParams.set('sortField', sortField);
		searchParams.set('sort', sortKey);
		location.href = location.origin+location.pathname+'?'+searchParams.toString();
	}
	
	//Pagination
	var ${instanceId}_x=3 , ${instanceId}_y=3 , ${instanceId}_start=${instanceId}_x-1;
	$('#${instanceId} .story-body:gt('+${instanceId}_start+')').attr('style','display:none !important');

	if($('#${instanceId} .story-body').length > ${instanceId}_x){
	    $("#${instanceId} .a_more").show();
	}else{
	    $("#${instanceId} .a_more").hide();
	}

	$('#${instanceId} .a_more').on('click', function() {
	    ${instanceId}_x= (${instanceId}_x+${instanceId}_y <= $('#${instanceId} .story-body').length) ? ${instanceId}_x+${instanceId}_y : $('#${instanceId} .story-body').length;
	    var elemts = $('#${instanceId} .story-body:lt('+${instanceId}_x+')');
	    for(var i=0; elemts.length>i;i++){
	        elemts[i].parentElement.parentElement.style.display= "";
	    }
	    $('#${instanceId} .story-body:lt('+${instanceId}_x+')').show();
	    if(${instanceId}_x == $('#${instanceId} .story-body').length){
	        $("#${instanceId} .a_more").hide();
	    }else{
	        $("#${instanceId} .a_more").show();
	    }
	});
</script>
