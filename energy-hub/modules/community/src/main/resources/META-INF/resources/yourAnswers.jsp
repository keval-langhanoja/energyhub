<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<link rel="stylesheet"
	href="/o/energy-hub-theme/style/communityForum.css">
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
   	.pointer:hover{
	    cursor: pointer;
   	}
</style>

<div class="row justify-content-between topTools">
	<div class="col-4 dropdown programsDropdown mt-3"
		data-toggle="dropdown" aria-expanded="false">
		<div class="">
			<span><liferay-ui:message key="Menu" /></span><img class="mx-3"
				src="/o/energy-hub-theme/images/header/downArrow.svg">
		</div>
		<uL>
			<li><a href="#"> <span><liferay-ui:message key="Menu" /></span>
			</a></li>
			<li><a class="active" href="javascript:;" onclick="location.href ='/policy-forum?view&p_r_p_categoryId=${categoryId}'+
							'&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${threadType}'" class="active"> <img
					src="/o/energy-hub-theme/images/menuBlue.svg"> <span><liferay-ui:message key="Questions" /></span>
			</a></li>
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
	<div class="col-4 d-flex searchBlueSmall align-items-center mt-3">
		<span><img width="18px"
			src="/o/energy-hub-theme/images/inputSearch.svg"></span> <input
			type="text" placeholder="Seacrh">
	</div>
</div>
<div class="showSmallSort row justify-content-between mt-3">
	<div class="col-4 dropdown dropdownSort programsDropdown">
		<div href="#" class="btn" data-toggle="dropdown">
			<liferay-ui:message key="Sort" /><img class="mx-3" src="/o/energy-hub-theme/images/sort-gray.png">
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
								stroke-linecap="round" stroke-linejoin="round" stroke-width="1" />
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
								stroke-linecap="round" stroke-linejoin="round" stroke-width="1" />
								<path id="Path_15146" data-name="Path 15146"
								d="M593.8,114l-4.074,4.078-1.222-1.222"
								transform="translate(-585.159 -113.182)" fill="none"
								stroke="gray" stroke-linecap="round" stroke-linejoin="round"
								stroke-width="1" />
							</g>
						</svg> </span> <span><liferay-ui:message key="DateCreated" /></span></a></li>
		</ul>
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
<div class="mt-4">
	<div class="mainGrid grid">
		<div class="programList">
			<div class="d-flex searchBlue align-items-center">
				<span><img width="18px"
					src="/o/energy-hub-theme/images/inputSearch.svg"></span> <input
					type="text" placeholder="Seacrh" id="queryText">
			</div>
			<uL>
				<li><a href="#"> <span><liferay-ui:message key="Menu" /></span>
				</a></li>
				<li><a class="active" href="javascript:;" onclick="location.href ='/policy-forum?view&p_r_p_categoryId=${categoryId}'+
							'&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${threadType}'" class="active"> <img
						src="/o/energy-hub-theme/images/menuBlue.svg"> <span><liferay-ui:message key="Questions" /></span>
				</a></li>
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
							onclick="location.href ='/policy-forum?youranswers&p_r_p_categoryId=${categoryId}'+
							'&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${threadType}'">
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
		<div class="pb-5" id="${instanceId}">
			<div class="row justify-content-between sortList">
				<div class="col-9">
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
				</div>
				<div class="col-3">
					<c:if test="${isSignedIn}">
					    <button class="add mb-2" style="float:right" onclick="location.href ='/policy-forum?askQuestion&p_r_p_categoryId=${categoryId}'+
					    '&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${threadType}'">
							<span class="plus"><img class="mb-1 plus"
									src="/o/energy-hub-theme/images/plusWhite.svg"></span>
							<span><liferay-ui:message key="AskAQuestion" /></span>
						</button> 
					</c:if> 
				</div>
			</div>
			<!--REPLIES -->
			<c:set var = "readmore_index"  value = "1"/>
			<c:forEach var="reply" items="${replies}">
				<div class="card w-100 story-body div_${reply.replyId}">
					<c:if  test="${readmore_index gt 5}">
						<script type="text/javascript"> 
							$(".div_"+${reply.replyId})[0].style.display='none';
						</script>
					</c:if>
					<div class="card-body">
						<div class="row justify-content-between">
							<div class="col-9">
								<div class="row">
									<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
										<img class="cardUserImg" src="${reply.userPortrait}" onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;"/>
									</div>
									<div class="col-lg-7 col-md-7 col-sm-7 col-xs-7"
										style="padding: 0px">
										<div class="cardUserName">${reply.replyuserName}
											<c:if test="${not empty reply.userRole && reply.userRole !=''}">
												<span class="cardUserDate">(<liferay-ui:message key="${reply.userRole}" />)</span>
											</c:if>
										</div>
										<div class="cardUserDate">${reply.createDate}</div>
									</div>
								</div>
							</div>
							<c:if test="${reply.userId == user.getUserId()}">
								<div class="col-1">
									<a href="javascript:;" id='deleteModal'
										onclick="openModal('${reply.replyId}')"> <img
										src="/o/energy-hub-theme/images/threeDots.svg">
									</a>
									<div class="dropdown-menu dropdown-menu-filter"
										id="deleteModal_${reply.replyId}" aria-labelledby="deleteModal"
										style="border-radius: 7px !important;">
										<button class="deleteButton"
											onclick="ajaxCall('deleteReply', '${reply.replyId}', '','/policy-forum?p_r_p_categoryId=${categoryId}'+
											'&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}'+
											'&forumId=${reply.forumId}&threadType=${threadType}')">
											<liferay-ui:message key="Delete" /></button>
									</div>
								</div>
							</c:if>
						</div>
						<a class="titlehref">
							<h5 class="card-title" onclick="ajaxCall('view', '${reply.forumId}', '', '/policy-forum?viewForum&p_r_p_categoryId=${categoryId}'+
							'&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}'+
							'&forumId=${reply.forumId}&threadType=${threadType}')">${reply.title}</h5>
						</a>
						<p class="card-text">${reply.reply}</p>
						<div class="row justify-content-between">
							<div class="col-auto">
								<ul class="cardTopics">
									<c:forEach var="tag" items="${reply.currentTags}">
										<li class="topicItem">${tag.name}</li>
									</c:forEach>
								</ul>
							</div> 
							<div class="col-auto">
								<ul class="cardReplies">
									<li class="detailsItem">
										<c:choose>
										    <c:when test="${reply.liked}">
										    	<img class="green-up-arrow pointer" id="green-arrow"  src="/o/energy-hub-theme/images/green-up-arrow.png">
                                                    <span id="total-likers" class="green-likers">${reply.likecount}</span>
                                                    <span class="comments-items">|</span> 
										    </c:when>  
										    <c:when test="${!reply.liked}">
										    		<img class="arrows pointer" src="/o/energy-hub-theme/images/grey-up-arrow.png" onclick="ajaxCall('reply_like', '${reply.replyId}')">
                                                    <span id="total-likers" class="">${reply.likecount}</span>
                                                    <span class="comments-items">|</span> 
										    </c:when>  
										</c:choose>
									</li>
									<li class="detailsItem">
								 		<c:choose>
										    <c:when test="${reply.dislike}">
										    	<img class="red-down-arrow pointer" id="red-arrow" src="/o/energy-hub-theme/images/red-down-arrow.png" >
                                                <span id="total-likers" class="red-dislikers">${reply.dislikecount}</span>
										    </c:when>   
										    <c:when test="${!reply.dislike}">
										       	<img class="red-down-arrow pointer" id="red-arrow" src="/o/energy-hub-theme/images/grey-down-arrow.png" onclick="ajaxCall('reply_dislike', '${reply.replyId}')">
                                                <span id="total-likers" class="">${reply.dislikecount}</span>
										    </c:when>  
										</c:choose>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<c:set var = "readmore_index"  value = "${readmore_index+1}"/>
			</c:forEach>
			<c:if test="${repliesTotal > 5}">
				<div class="seeAll text-end mt-4">
					<a>
						<p class="a_more">+<liferay-ui:message key="View3More"/></p>
					</a>
				</div>
       		</c:if>
			<!--REPLIES -->
		</div>
		<div class="card rightSidebar">
			<div class="card-body">
				<h5 class="card-title filterTitle">
					<img src="/o/energy-hub-theme/images/star.svg">
					<liferay-ui:message key="ThreadCategories"/>
				</h5>
				<hr>
				<ul class="card-text cardTopics" style="display: list-item;">
					<c:forEach var="categ" items="${ProjectCategoryList}" end="9"> 
						<a href="javascript:;" style="text-decoration: none;" class="topicItem categFilter" id="${categ.id}">${categ.value}</a>
				 	</c:forEach>
				</ul>
				<h5 class="card-title filterTitle">
					<img src="/o/energy-hub-theme/images/attach.svg">
					<liferay-ui:message key="ProjectTags"/>
				</h5>
				<hr>
				<div class="ui-widget"> 
				  <input id="tags" placeholder="SearchForTags...">
				</div>
				<ul class="card-text cardTopics" style="display: list-item;">
					<c:forEach var="tag" items="${allTags}" end="9"> 
						<a href="javascript:;" style="text-decoration: none;" class="topicItem linkTag" id="${tag.tagId}">${tag.name} ( ${tag.assetCount} )</a>
				 	</c:forEach>
				</ul>
				<button class="blueBtn submit mt-3 post" id="reset"><liferay-ui:message key="Clear"/></button>
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

	function ajaxCall(key, id, replyParentId, replyChildText) {
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
			
			xhr.onloadend = function (e) {
				if(key == "view" || key == "deleteForum"){debugger;
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
	var queryText = decodeURI(_searchParams.get('queryText') || "");
	var filterData = decodeURI(_searchParams.get('filter') || "");
	
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
		searchParams.set('filter', encodeURI(filterData));
		location.href = location.origin+location.pathname+'?'+searchParams.toString();
	}
	$('#reset').on('click', function (e) {
		e.preventDefault();
		queryText = "";
		filterData = ''; 
		getData();
	}); 
	
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
