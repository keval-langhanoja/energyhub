<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="/o/energy-hub-theme/style/communityForum.css">
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
	.detailsItemhref {
		color: #808080 !important;
	    font-size: 12px;
	    text-decoration: none;
	}
	.titlehref:hover{
		text-decoration: underline !important;
	    color: #23b6d0 !important;
	}
	textarea {
	    resize: none;
	    border-top: 1px solid rgba(0,0,0,.125) !important;
	    height: 70px;
	    width: 100%;
	}
	.post {
		float: right;
	    width: 100px;
	    margin: 8px !important;
    }
    .deleteButton {
        background-color: transparent;
	    border: 0px;
	    width: 100%;
	    display: flex;
	    padding: 10px;
    }
    .deleteButton:hover { 
    	text-decoration: underline;
    }
    .plus {
        width: 15px;
    	margin: 0px 5px;
    }
    .flexDisplay {
        align-items: center;
	    flex-direction: row;
	    justify-content: flex-end;
	    display: flex;
    }
    .uploadButton {
	    width: auto;
	    margin: 0px 10px !important;
    }
    .uploadButton img:hover {
    	background: none;
    	display: inline;
    }
    ul.ui-autocomplete{
    	display: inline-block;
	    padding: 10px 20px;
	    border: 1px solid rgba(0, 0, 0, 0.125) !important;
	    background-color: white;
    }
    .ui-menu-item{
    	padding: 5px;
    	border-bottom: 1px solid rgba(0, 0, 0, 0.125);
   	}
    .ui-menu-item:hover{
    	background-color: #009bc7;
	    color: white;
	    cursor: pointer;
   	}
   	.cardQuestionsParent {
	    padding-right: 0%;
	    padding-left: 0%;
	}
	.cardAnswerReplyParent:before{
	    background: #8AAF29 !important;
	}
	.filterTitle {
		color: black !important;
	}
	.pointer:hover{
	    cursor: pointer;
   	}
</style>

<div class="row justify-content-between topTools">
	<div class="col-4 dropdown programsDropdown mt-3" data-toggle="dropdown" aria-expanded="false">
		<div class=""><span><liferay-ui:message key="Menu" /></span><img class="mx-3" src="/o/energy-hub-theme/images/header/downArrow.svg">
		</div>
		<div class="dropdown-menu">
			<a href="javascript:;" onclick="location.href ='/policy-forum?view&p_r_p_categoryId=${categoryId}'+
							'&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${threadType}'" class="active">
				<img src="/o/energy-hub-theme/images/menuBlue.svg"><span><liferay-ui:message key="Questions" /></span></a>
			<a href="${sign_in_url}"><img src="/o/energy-hub-theme/images/userBlue.svg">
				<span class="loginTxt"><liferay-ui:message key="LoginToJoinDiscussions" /></span></a>
		</div>
	</div>
	<div class="col-4 d-flex searchBlueSmall align-items-center mt-3">
		<span><img width="18px" src="/o/energy-hub-theme/images/inputSearch.svg"></span>
		<input type="text" placeholder="Search">
	</div>
</div>
<div class="showSmallSort row justify-content-between mt-3">
	<div class="col-4 dropdown dropdownSort programsDropdown">
		<c:if test="${viewForum}">
			<div href="#" class="btn" data-toggle="dropdown"><liferay-ui:message key="Sort" /><img class="mx-3"
					src="/o/energy-hub-theme/images/sort-gray.png">
			</div>
			<ul class="dropdown-menu">
				<li> <a href="javascript:;"> <span class="plus mb-1"><svg
								xmlns="http://www.w3.org/2000/svg" width="17" height="13"
								viewBox="0 0 9.148 9.148">
								<g id="Component_438_1" data-name="Component 438 – 1"
									transform="translate(0.5 0.5)">
									<path id="Path_15137" data-name="Path 15137"
										d="M375.074,121.148A4.074,4.074,0,1,0,371,117.074,4.075,4.075,0,0,0,375.074,121.148Z"
										transform="translate(-371 -113)" fill="none" stroke="gray"
										stroke-linecap="round" stroke-linejoin="round"
										stroke-width="1" />
									<path id="Path_15138" data-name="Path 15138"
										d="M376,115v2.445l1.63.815"
										transform="translate(-371.926 -113.371)" fill="none"
										stroke="gray" stroke-linecap="round" stroke-linejoin="round"
										stroke-width="1" />
								</g>
							</svg></span>
						<liferay-ui:message key="LastActivity" /></a></li>
				<li> <a href="javascript:;">
						<span class="plus mb-1"><svg xmlns="http://www.w3.org/2000/svg" width="17"
								height="11" viewBox="0 0 8.605 8.13">
								<g id="Component_446_1" data-name="Component 446 – 1"
									transform="translate(3.707 0) rotate(45,2,0)">
									<path id="Path_15164" data-name="Path 15164"
										d="M1021.5,324.755v-7.13"
										transform="translate(-1017.905 -317.625)" fill="none"
										stroke="gray" stroke-linecap="round" stroke-linejoin="round"
										stroke-width="1" />
									<path id="Path_15165" data-name="Path 15165"
										d="M1017.12,321.19l3.6-3.565,3.6,3.565"
										transform="translate(-1017.12 -317.625)" fill="none"
										stroke="gray" stroke-linecap="round" stroke-linejoin="round"
										stroke-width="1" />
								</g>
							</svg>
						</span>
						<span><liferay-ui:message key="Top" /></span></a></li>
				<li><a href="javascript:;" class="active">
						<span class="plus mb-1"><svg xmlns="http://www.w3.org/2000/svg" width="20"
								height="13" viewBox="0 0 9.846 9.16">
								<g id="Component_439_1" data-name="Component 439 – 1"
									transform="translate(0.501 0.505)">
									<path id="Path_15145" data-name="Path 15145"
										d="M593.148,116.7v.375a4.074,4.074,0,1,1-2.416-3.724"
										transform="translate(-585 -112.997)" fill="none" stroke="gray"
										stroke-linecap="round" stroke-linejoin="round"
										stroke-width="1" />
									<path id="Path_15146" data-name="Path 15146"
										d="M593.8,114l-4.074,4.078-1.222-1.222"
										transform="translate(-585.159 -113.182)" fill="none"
										stroke="gray" stroke-linecap="round" stroke-linejoin="round"
										stroke-width="1" />
								</g>
							</svg>
						</span>
						<span><liferay-ui:message key="DateCreated" /></span></a></li>
			</ul>
		</c:if> 
	</div>
	<div class="col-4">
		<c:if test="${isSignedIn}">
		    <button class="add mb-2" onclick="location.href ='/policy-forum?askQuestion&p_r_p_categoryId=${categoryId}'+
		    '&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${threadType}'">
				<span class="plus"><img class="mb-1 plus" src="/o/energy-hub-theme/images/plusWhite.svg"></span>
				<span><liferay-ui:message key="AskAQuestion" /></span>
			</button>
		</c:if> 
	</div>
</div>
<div class="mt-4"  id="${instanceId}">
	<div class="mainGrid grid">
		<div class="programList">
			<div class="d-flex searchBlue align-items-center">
				<span><img width="18px" src="/o/energy-hub-theme/images/inputSearch.svg"></span>
				<input type="text" id="queryText" placeholder="Seacrh">
			</div>
			<uL>
				<li>
					<a href="#">
						<span><liferay-ui:message key="Menu" /></span>
					</a>
				</li>
				<li>
					<a class="active" href="javascript:;" onclick="location.href ='/policy-forum?view&p_r_p_categoryId=${categoryId}'+
							'&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${threadType}'">
						<img src="/o/energy-hub-theme/images/menuBlue.svg">
						<span><liferay-ui:message key="Questions" /></span>
					</a>
				</li>
		      	<c:choose>
				    <c:when test="${!isSignedIn}">
				    	<li><a href="${sign_in_url}">
							<img src="/o/energy-hub-theme/images/userBlue.svg">
							<span class="loginTxt"><liferay-ui:message key="LoginToJoinDiscussions" /></span></a>
						</li>
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
										stroke-linecap="round" stroke-linejoin="round"
										stroke-width="2" />
                                            <path id="Path_15267"
										data-name="Path 15267"
										d="M56.818,349.75a2.25,2.25,0,0,1,4.372.75c0,1.5-2.25,2.25-2.25,2.25"
										transform="translate(-51.5 -344.5)" fill="none" stroke="gray"
										stroke-linecap="round" stroke-linejoin="round"
										stroke-width="2" />
                                            <path id="Path_15268"
										data-name="Path 15268" d="M59,355.75h.007"
										transform="translate(-51.5 -344.5)" fill="none" stroke="gray"
										stroke-linecap="round" stroke-linejoin="round"
										stroke-width="2" />
                                        </g>
                                    </svg> <span><liferay-ui:message key="YourQuestions" /></span>
						</a></li>
						<li><a href="javascript:;"
							onclick="location.href ='/policy-forum?youranswers&p_r_p_categoryId=${categoryId}&folderId=${folderId}'+
							'&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${threadType}'">
								<svg xmlns="http://www.w3.org/2000/svg" width="15.5"
									height="15.5" viewBox="0 0 15.5 15.5">
                                    <g id="Component_460_1"
										data-name="Component 460 – 1" transform="translate(1 1)">
                                        <path id="Path_15270"
										data-name="Path 15270"
										d="M65.75,402.625a6.286,6.286,0,0,1-.675,2.85,6.375,6.375,0,0,1-5.7,3.525,6.284,6.284,0,0,1-2.85-.675L52.25,409.75l1.425-4.275a6.286,6.286,0,0,1-.675-2.85,6.374,6.374,0,0,1,3.525-5.7,6.284,6.284,0,0,1,2.85-.675h.375a6.36,6.36,0,0,1,6,6Z"
										transform="translate(-52.25 -396.25)" fill="none"
										stroke="gray" stroke-linecap="round" stroke-linejoin="round"
										stroke-width="2" />
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
			<div class="row justify-content-between sortList">
				<div class="col-9">
					<c:if test="${viewForum}">
						<button class="sort  my-2" id="lastActivity">
							<span class="plus mb-1"><svg xmlns="http://www.w3.org/2000/svg" width="17" height="13"
								 viewBox="0 0 9.148 9.148">
									<g id="Component_438_1" data-name="Component 438 – 1"
										transform="translate(0.5 0.5)">
										<path id="Path_15137" data-name="Path 15137"
											d="M375.074,121.148A4.074,4.074,0,1,0,371,117.074,4.075,4.075,0,0,0,375.074,121.148Z"
											transform="translate(-371 -113)" fill="none" stroke="gray"
											stroke-linecap="round" stroke-linejoin="round"
											stroke-width="1" />
										<path id="Path_15138" data-name="Path 15138"
											d="M376,115v2.445l1.63.815"
											transform="translate(-371.926 -113.371)" fill="none"
											stroke="gray" stroke-linecap="round" stroke-linejoin="round"
											stroke-width="1" />
									</g>
								</svg></span>
							<span><liferay-ui:message key="LastActivity" /></span>
						</button>
						<button class="sort my-2" id="top">
							<span class="plus mb-1"><svg xmlns="http://www.w3.org/2000/svg"
									width="17" height="11" viewBox="0 0 8.605 8.13">
									<g id="Component_446_1" data-name="Component 446 – 1"
										transform="translate(3.707 0) rotate(45,2,0)">
										<path id="Path_15164" data-name="Path 15164"
											d="M1021.5,324.755v-7.13"
											transform="translate(-1017.905 -317.625)" fill="none"
											stroke="gray" stroke-linecap="round" stroke-linejoin="round"
											stroke-width="1" />
										<path id="Path_15165" data-name="Path 15165"
											d="M1017.12,321.19l3.6-3.565,3.6,3.565"
											transform="translate(-1017.12 -317.625)" fill="none"
											stroke="gray" stroke-linecap="round" stroke-linejoin="round"
											stroke-width="1" />
									</g>
								</svg>
							</span>
							<span><liferay-ui:message key="Top" /></span>
						</button>
						<button class="sort my-2 active" id="createDate">
							<span class="plus mb-1"><svg xmlns="http://www.w3.org/2000/svg"
									width="20" height="13" viewBox="0 0 9.846 9.16">
									<g id="Component_439_1" data-name="Component 439 – 1"
										transform="translate(0.501 0.505)">
										<path id="Path_15145" data-name="Path 15145"
											d="M593.148,116.7v.375a4.074,4.074,0,1,1-2.416-3.724"
											transform="translate(-585 -112.997)" fill="none"
											stroke="gray" stroke-linecap="round" stroke-linejoin="round"
											stroke-width="1" />
										<path id="Path_15146" data-name="Path 15146"
											d="M593.8,114l-4.074,4.078-1.222-1.222"
											transform="translate(-585.159 -113.182)" fill="none"
											stroke="gray" stroke-linecap="round" stroke-linejoin="round"
											stroke-width="1" />
									</g>
								</svg>
							</span>
							<span><liferay-ui:message key="DateCreated" /></span>
						</button>
					</c:if>
				</div>
				<div class="col-3">
					<c:if test="${isSignedIn}">
					    <button class="add mb-2" style="float:right" onclick="location.href ='/policy-forum?askQuestion&p_r_p_categoryId=${categoryId}'+
					    '&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${threadType}'">
							<span class="plus"><img class="mb-1 plus" src="/o/energy-hub-theme/images/plusWhite.svg"></span>
							<span><liferay-ui:message key="AskAQuestion" /></span>
						</button>
					</c:if> 
				</div>
			</div>
			<c:set var = "readmore_index"  value = "1"/>
			<c:forEach var="forum" items="${forums}">
				<div class="card w-100 story-body div_${forum.id}">
					<c:if  test="${readmore_index gt 5}">
						<script type="text/javascript"> 
							$(".div_"+${forum.id})[0].style.display='none';
						</script>
					</c:if>
					<div class="card-body">
						<div class="row justify-content-between">
							<div class="col-9">
								<div class="row">
									<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
										<img class="cardUserImg" src="${forum.userPortrait}" onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;"/>
									</div>
									<div class="col-lg-7 col-md-7 col-sm-7 col-xs-7"
										style="padding:0px">
										<div class="cardUserName">${forum.userName} 
											<c:if test="${not empty forum.userRole && forum.userRole !=''}">
												<span class="cardUserDate">(<liferay-ui:message key="${forum.userRole}" />)</span>
											</c:if>
										</div>
										<aui:input id="userId" value ="${forum.userId}" name="userId" type="hidden"></aui:input>
										<div class="cardUserDate">${forum.createDate}</div>
									</div>
								</div>
							</div>
							<c:if test="${forum.userId == user.getUserId()}">
								<div class="col-1">
									<a href="javascript:;" id='deleteModal'
										onclick="openModal('${forum.id}')"> <img
										src="/o/energy-hub-theme/images/threeDots.svg">
									</a>
									<div class="dropdown-menu dropdown-menu-filter"
										id="deleteModal_${forum.id}"
										aria-labelledby="deleteModal_${forum.id}"
										style="border-radius: 7px !important;">
										<button class="deleteButton"
											onclick="ajaxCall('deleteForum', '${forum.id}','/policy-forum?p_r_p_categoryId=${categoryId}'+
											'&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${threadType}')">
											<liferay-ui:message key="Delete" />	
									</button>
										<c:if test="${isDraft}">
											<button class="deleteButton"
												onclick="ajaxCall('publishDraft', '${forum.id}','/policy-forum?p_r_p_categoryId=${categoryId}&folderId=${folderId}'+
												'&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${threadType}')">
												<liferay-ui:message key="Publish" />		
										</button>
										</c:if>
									</div>
								</div>
							</c:if>
						</div>
						<a class="titlehref">
							<c:choose>
					   			<c:when test="${!viewForum}">
									<h5 class="card-title">${forum.title}</h5>
								</c:when>
								<c:when test="${viewForum}">
									<h5 class="card-title" onclick="ajaxCall('view', '${forum.id}', '/policy-forum?viewForum&p_r_p_categoryId=${categoryId}'+
									'&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&forumId=${forum.id}&threadType=${threadType}')">${forum.title}</h5>
								</c:when>
							</c:choose>
						</a>
						<p class="card-text">${forum.description}</p>
						<c:if test="${forum.imageUrl != ''}">
							<a class="readmore" id="download_${forum.id}" href="javascript:;" onclick="downloadDoc('${forum.imageUrl}','download_${forum.id}')"><liferay-ui:message key="DownloadDocument" />
								<span class="arrow">
									<img src="/o/energy-hub-theme/images/doubleBlueArrow.svg">
								</span>
							</a>
						</c:if>
						<div class="row justify-content-between">
							<div class="col-auto">
								<ul class="cardTopics">
									<c:forEach var="tag" items="${forum.currentTags}">
										<li class="topicItem">${tag.name}</li>
									</c:forEach>
								</ul>
							</div>
							<div class="col-auto">
								<ul class="cardDetails">
									<li class="detailsItem">
								    	<c:choose>
								   			<c:when test="${!forum.view}">
												<img class="mb-1" src="/o/energy-hub-theme/images/eye.svg">
											</c:when>
											<c:when test="${forum.view}">
												<img class="mb-1" src="/o/energy-hub-theme/images/eye-blue.svg">
											</c:when> 
										</c:choose>
										<span>${forum.viewcount}</span>
									</li> 
									<li class="detailsItem">
										<img class="mb-1" src="/o/energy-hub-theme/images/comment-square.svg">
										<span>${forum.totalReplies}</span>
									</li>
									<li class="detailsItem">
										<c:choose>
										    <c:when test="${forum.liked}">
										    	<img class="green-up-arrow pointer" id="green-arrow"  src="/o/energy-hub-theme/images/green-up-arrow.png">
                                                    <span id="total-likers" class="green-likers">${forum.likecount}</span>
                                                     <span class="comments-items">|</span> 
										    </c:when>  
										    <c:when test="${!forum.liked}">
										    		<img class="arrows pointer" src="/o/energy-hub-theme/images/grey-up-arrow.png" onclick="ajaxCall('like', '${forum.id}')">
                                                    <span id="total-likers" class="">${forum.likecount}</span>
                                                    <span class="comments-items">|</span> 
										    </c:when>  
										</c:choose>
									</li>
									<li class="detailsItem">
								 		<c:choose>
										    <c:when test="${forum.dislike}">
										    	<img class="red-down-arrow pointer" id="red-arrow" src="/o/energy-hub-theme/images/red-down-arrow.png" >
                                                <span id="total-likers" class="red-dislikers">${forum.dislikecount}</span>
										    </c:when>   
										    <c:when test="${!forum.dislike}">
										       	<img class="red-down-arrow pointer" id="red-arrow" src="/o/energy-hub-theme/images/grey-down-arrow.png" onclick="ajaxCall('dislike', '${forum.id}')">
                                                <span id="total-likers" class="">${forum.dislikecount}</span>
										    </c:when>  
										</c:choose>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<!-- add reply input field -->
					<c:if test="${isSignedIn && !viewForum}">
						<div class="textarea-container">
							<textarea name="replyText" id="replyText" rows="4" cols="50" placeholder= "Add reply ..."></textarea>
						 	<input style="opacity:0;position: absolute;" type="file" id="replyDoc" name="replyDoc" label="" 
                				accept="image/*" hidden="hidden"/>
							<div class="flexDisplay">
								<button type="button" value="UploadDocument" id="custom-button" onclick="clickCustomButton('replyDoc', 'replyDoc-text')" class="uploadButton ">
		                          <img src="/o/energy-hub-theme/images/upload.svg">
                          		  <span id="replyDoc-text"></span>
		                      	</button>
							  	<button class="blueBtn submit mt-3 post" onclick="ajaxCall('postReply', '${forum.id}')"><liferay-ui:message key="Post" /></button>
							</div>
						</div>
					</c:if>
				</div>
				<c:forEach var="reply" items="${replies}">
					<div class="container cardQuestionsParent">
		                <div class="row">
		                    <div class="col-12">
		                        <div class="card cardAnswer cardAnswerReplyParent">
		                            <div class="card-body">
		                                <div class="row justify-content-between">
		                                    <div class="col-9">
		                                        <div class="row">
		                                            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
		                                                <img class="cardUserImg" src="${forum.userPortrait}" onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;"/>
		                                            </div>
		                                            <div class="col-lg-7 col-md-7 col-sm-7 col-xs-7" style="padding:0px">
		                                                <div class="cardUserName">${reply.replyuserName}
		                                               	<c:if test="${not empty reply.userRole && reply.userRole !=''}">
															<span class="cardUserDate">(<liferay-ui:message key="${reply.userRole}" />)</span>
														</c:if>
		                                                </div>
		                                                <div class="cardUserDate">${reply.createDate}</div>
		                                            </div>
		                                        </div>
		                                    </div>
											<div class="col-1">
												<a href="javascript:;" id='deleteModal' onclick="openModal('${reply.replyId}')"> 
													<img src="/o/energy-hub-theme/images/threeDots.svg">
												</a>
												<div class="dropdown-menu dropdown-menu-filter" id="deleteModal_${reply.replyId}" aria-labelledby="deleteModal" style="border-radius: 7px !important;">
													<button class="deleteButton"
														onclick="ajaxCall('deleteReply', '${reply.replyId}', '','/policy-forum?p_r_p_categoryId=${categoryId}'+
														'&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&forumId=${forum.id}&threadType=${threadType}')">
														<liferay-ui:message key="Delete" />
													</button>
												</div>
											</div>
										</div>
		                                <h5 class="card-title"><img src="/o/energy-hub-theme/images/arrowRightBlue.svg">  ${forum.title}</h5>
		                                <p class="card-text">${reply.reply}</p>
		                                <c:if test="${reply.imageUrl != ''}">
											<a class="readmore" id="download_${reply.replyId}" href="javascript:;" onclick="downloadDoc('${reply.imageUrl}','download_${reply.replyId}')"><liferay-ui:message key="DownloadDocument" />
												<span class="arrow">
													<img src="/o/energy-hub-theme/images/doubleBlueArrow.svg">
												</span>
											</a>
										</c:if>
		                                <div class="row justify-content-between">
		                                    <div class="col-auto">
		                                        <ul class="cardTopics">
		                                            <c:forEach var="tag" items="${forum.currentTags}">
														<li class="topicItem">${tag.name}</li>
													</c:forEach>
		                                        </ul>
		                                    </div>
		                                </div>
		                                <div class="row justify-content-between">
		                                    <div class="col-auto">
												<ul class="cardReplies">
													<li class="detailsItem"><c:choose>
															<c:when test="${reply.liked}">
																<img class="green-up-arrow pointer" id="green-arrow"
																	src="/o/energy-hub-theme/images/green-up-arrow.png">
																<span id="total-likers" class="green-likers">${reply.likecount}</span>
																<span class="comments-items">|</span>
															</c:when>
															<c:when test="${!reply.liked}">
																<img class="arrows pointer"
																	src="/o/energy-hub-theme/images/grey-up-arrow.png"
																	onclick="ajaxCall('reply_like', '${reply.replyId}')">
																<span id="total-likers" class="">${reply.likecount}</span>
																<span class="comments-items">|</span>
															</c:when>
														</c:choose></li>
													<li class="detailsItem"><c:choose>
															<c:when test="${reply.dislike}">
																<img class="red-down-arrow pointer" id="red-arrow"
																	src="/o/energy-hub-theme/images/red-down-arrow.png">
																<span id="total-likers" class="red-dislikers">${reply.dislikecount}</span>
															</c:when>
															<c:when test="${!reply.dislike}">
																<img class="red-down-arrow pointer" id="red-arrow"
																	src="/o/energy-hub-theme/images/grey-down-arrow.png"
																	onclick="ajaxCall('reply_dislike', '${reply.replyId}')">
																<span id="total-likers" class="">${reply.dislikecount}</span>
															</c:when>
														</c:choose></li>
												</ul>
											</div>
		                                    <div class="col-auto">
		                                         <ul class="cardReplies">
		                                             <li><a href="javascript:;" class="pointer" onclick="$('.cardQuestions_${reply.replyId}').removeClass('hide');">
		                                                     <img src="/o/energy-hub-theme/images/doubleBlueArrow.svg">
		                                                     <liferay-ui:message key="ShowAllReplies" /> ${fn:length(reply.children)}</li></a>
                                                   	<c:if test="${isSignedIn}">	
			                                             <li><a href="javascript:;" class="pointer" onclick="document.getElementById('replyDiv_${reply.replyId}').style.display = ''">
			                                                     <img src="/o/energy-hub-theme/images/arrowRightBlue.svg"><liferay-ui:message key="Reply" />
			                                             </li></a>
			                                        </c:if>
		                                         </ul>
	                                     	</div>
		                                </div>
		                            </div>
		                            <c:if test="${isSignedIn && !viewForum}">
										<div class="textarea-container" style="display: none" id="replyDiv_${reply.replyId}">
											<textarea id="reply_${reply.replyId}" name="w3review" rows="4" cols="50" placeholder= "AddReply..."></textarea>
											<input style="opacity:0;position: absolute;" type="file" id="replyDoc_${reply.replyId}" name="replyDoc" label="" 
												accept="image/*" hidden="hidden"/>
											<div class="flexDisplay">
												<button type="button" id="custom-button" onclick="clickCustomButton('replyDoc_${reply.replyId}','replyDoc-text_${reply.replyId}')" class="uploadButton ">
												  <img src="/o/energy-hub-theme/images/upload.svg">
												  <span id="replyDoc-text_${reply.replyId}"></span>
												</button>
												<button class="blueBtn submit mt-3 post" onclick="ajaxCall('postReply', '${forum.id}',
													'${reply.replyId}', $('#reply_${reply.replyId}').val())"><liferay-ui:message key="Post" /></button>
											</div>
										</div>
									</c:if>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            <c:if test="${fn:length(reply.children) > 0}">
		            	<c:forEach var="child" items="${reply.children}">
					 		<c:set var="child" value="${child}" scope="request" />
					 		<c:set var="forum" value="${forum}" scope="request" />
	          				<jsp:include page="/childReply.jsp" />
       				 	</c:forEach>
				  	</c:if>
	            </c:forEach>
	            <c:set var = "readmore_index"  value = "${readmore_index+1}"/>
			</c:forEach>
			<c:if test="${forumsTotal > 5}">
				<div class="seeAll text-end mt-4">
					<a>
						<p class="a_more">+<liferay-ui:message key="View3More" /></p>
					</a>
				</div>
       		</c:if>
		</div>
		<div class="card rightSidebar">
			<div class="card-body">
				<h5 class="card-title filterTitle">
					<img src="/o/energy-hub-theme/images/star.svg">
					<liferay-ui:message key="ThreadCategories" />
				</h5>
				<hr>
				<ul class="card-text cardTopics" style="display: list-item;">
					<c:forEach var="categ" items="${ProjectCategoryList}" end="9"> 
						<a href="javascript:;" style="text-decoration: none;" class="topicItem categFilter" id="${categ.id}">${categ.value}</a>
				 	</c:forEach>
				</ul>
				<h5 class="card-title filterTitle">
					<img src="/o/energy-hub-theme/images/attach.svg">
					<liferay-ui:message key="ProjectTags" />
				</h5>
				<hr>
				<div class="ui-widget searchBlue"> 
				  <input id="tags" placeholder="Search">
				</div>
				<ul class="card-text cardTopics" style="display: list-item;">
					<c:forEach var="tag" items="${allTags}" end="9"> 
						<a href="javascript:;" style="text-decoration: none;" class="topicItem linkTag" id="${tag.tagId}">${tag.name} ( ${tag.assetCount} )</a>
				 	</c:forEach>
				</ul>
				<button class="blueBtn submit mt-3 post" id="reset"><liferay-ui:message key="Clear" /></button>
			</div>
		</div>
	</div>
</div>
<script> 
$("#tags").autocomplete({
	minLength: 3,
	source: function (request, response) {
     	response($.map(${jaTags}, function (value, key) {
           return {
               label: value.name,
               value: value.tagId
           }
       }));
   
	},
   	select: function(e, ui) {
   		e.preventDefault();
   		$("#tags").val(ui.item.label);
    	filterData = "tag_"+ui.item.value; 
	    getData(); 
	    return false;
   	},    
    focus: function(event, ui) { 
        return false;
    }
});
var customTxt = '';
var base64String;

function clickCustomButton(realFileBtn, text) {
	customTxt = document.getElementById(text);
	$("#" + realFileBtn).click();
	$("#" + realFileBtn).change(function() {
    	if ($("#" + realFileBtn).val()) {
        	if (this.files && this.files[0]) {
	            var reader = new FileReader();
	            reader.onload = function(e) {
	            	customTxt.innerHTML = $("#" + realFileBtn).val().match(/[\/\\]([\w\d\s\.\-\(\)]+)$/)[1];
	            	base64String = e.target.result;
	            };
	            reader.readAsDataURL(this.files[0]);
	          }
	    } else {
	        customTxt.innerHTML = "";
	    }
	});
}
 
function ajaxCall(key, id, replyParentId, replyChildText) {
		if(key == "view" && ! ${isSignedIn}){
			var url = replyParentId;
			window.location.href = location.origin + url;
		}
		if(${isSignedIn}) {
			var xhr = new XMLHttpRequest();
			var formData = new FormData();
			var replyText = "";
			if(key == "postReply")
				replyText = $("#replyText").val();
			if(key == "postReply" && replyChildText != "" && replyChildText != undefined)
				replyText = replyChildText;
			
			var data = {
					 <portlet:namespace />id: id,
					 <portlet:namespace />key: key,
					 <portlet:namespace />replyParentId: replyParentId,
					 <portlet:namespace />replyText: replyText,
			};

			var file = '';
			if(key == "postReply") {
				if( $("#replyDoc")[0] && $("#replyDoc")[0].files.length ) {
					file  = $("#replyDoc")[0];
				}else if($("#replyDoc_" +replyParentId)[0] && $("#replyDoc_" +replyParentId)[0].files.length) {
					file = $("#replyDoc_" +replyParentId)[0];
				}
				
				if( file != '' && file && file.files.length ) {	
// 					formData.append('<portlet:namespace />replyDoc', file.files[0]);
					formData.append('<portlet:namespace />replyDocName', file.files[0].name);
					formData.append('<portlet:namespace />replyDoc', base64String);
				}
			}
			
			xhr.onloadend = function (e) {
				if(key == "view"){
					var url = replyParentId;
					window.location.href = location.origin + url;
				}
				else if(key == "deleteForum"){debugger;
					window.location.href = location.origin + replyParentId;
				}
				else location.reload();
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
	}
	
	function openModal(id) {
		$(".dropdown-menu-filter").removeClass("show")
		if( !$("#deleteModal_" + id+".dropdown-menu-filter").hasClass("show") ) {
			$("#deleteModal_" + id+".dropdown-menu-filter").addClass("show")
		}else $("#deleteModal_" + id+".dropdown-menu-filter").removeClass("show")
	}
	
	var _searchParams = new URLSearchParams(location.search);
	var sortField = _searchParams.get('sortField') || "createDate";
	var sortKey = _searchParams.get('sort') || "desc";
	var queryText = decodeURI(_searchParams.get('queryText') || "");
	var filterData = decodeURI(_searchParams.get('filter') || "");
	
	$( document ).ready(function() {
		if(sortField != ""){
			$(".sort").removeClass("active");
			$('#'+ sortField).addClass("active");
		}
	});

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
	
	$('#queryText').val(queryText);
	$('#queryText').on('keyup', function (e) {
		if( e.keyCode == 13 ) {
			queryText = $(this).val();
			pageNo = 0;
			getData();
		}
	});
	
	$('.linkTag').click(function () {
	    filterData = "tag_"+$(this)[0].id; 
	    getData();
	});
	
	$('.categFilter').click(function () {
	    filterData = "categ_"+$(this)[0].id; 
	    getData();
	});
	
	
	function getData() {
		var searchParams = new URLSearchParams(location.search);
		searchParams.set('queryText', encodeURI(queryText));
		searchParams.set('sortField', sortField);
		searchParams.set('sort', sortKey);
		searchParams.set('filter', encodeURI(filterData));
		location.href = location.origin+location.pathname+'?'+searchParams.toString();
	}
	
	$('#reset').on('click', function (e) {
		e.preventDefault();
		sortField = "createDate";
		queryText = "";
		sortKey = "desc";
		filterData = ''; 
		getData();
	}); 
	
	function downloadDoc(doc, id) { 
		$('#' +id).attr('href',doc);
		$('#' +id).attr('download','name');
		$('#' +id).click();
	}
	
	//Pagination
	var ${instanceId}_x=3 , ${instanceId}_y=3 , ${instanceId}_start=${instanceId}_x-1;
	$('#${instanceId} .story-body:gt('+${instanceId}_start+')').hide();

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
