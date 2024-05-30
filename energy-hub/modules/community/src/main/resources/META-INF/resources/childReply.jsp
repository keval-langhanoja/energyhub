<%@ include file="/init.jsp" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="/o/energy-hub-theme/style/communityForum.css">
<portlet:actionURL var="communityURL"/>
<portlet:resourceURL var="testAjaxResourceUrl"/>
<style>
	.cardQuestions:lang(en),
	.cardQuestions:lang(fr) {
		padding-right: 0%;
		padding-left: 4%;
	}

	.cardQuestions:lang(ar) {
		padding-right: 4%;
		padding-left: 0%;
	}
	.pointer:hover{
	    cursor: pointer;
   	}
</style>
<div class="container cardQuestions hide cardQuestions_${child.replyParentId}">
	<div class="row">
		<div class="col-12">
			<div class="card cardAnswer">
				<div class="card-body">
					<div class="row justify-content-between">
						<div class="col-9">
							<div class="row">
								<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
									<img class="cardUserImg" src="${forum.userPortrait}" onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;"/>
								</div>
								<div class="col-lg-7 col-md-7 col-sm-7 col-xs-7" style="padding: 0px">
									<div class="cardUserName">${child.replyuserName}
										<c:if test="${not empty child.userRole && child.userRole !=''}">
											<span class="cardUserDate">(<liferay-ui:message key="${child.userRole}" />)</span>
										</c:if>
									</div>
									<div class="cardUserDate">${child.createDate}</div>
								</div>
							</div>
						</div>
						<c:if test="${forum.userId == user.getUserId()}">
							<div class="col-1">
								<a href="javascript:;" id='deleteModal' onclick="openModal('${child.replyId}')">
									<img src="/o/energy-hub-theme/images/threeDots.svg">
								</a>
								<div class="dropdown-menu dropdown-menu-filter" id="deleteModal_${child.replyId}" aria-labelledby="deleteModal" style="border-radius: 7px !important;">
									<button class="deleteButton" onclick="ajaxCall('deleteReply', '${child.replyId}', '','')"><liferay-ui:message key="Delete" /></button>
								</div>
							</div>
						</c:if>
					</div>
					<h5 class="card-title">
						<img src="/o/energy-hub-theme/images/arrowRightBlue.svg">  ${forum.title}
					</h5>
					<p class="card-text">${child.reply}</p>
					<c:if test="${child.imageUrl != ''}">
						<a class="readmore" id="download_${child.replyId}" href="javascript:;" onclick="downloadDoc('${child.imageUrl}','download_${child.replyId}')">
							<liferay-ui:message key="DownloadDocument"/>
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
								<li class="detailsItem">
									<c:choose>
										<c:when test="${child.liked}">
											<img class="green-up-arrow pointer" id="green-arrow" src="/o/energy-hub-theme/images/green-up-arrow.png">
											<span id="total-likers" class="green-likers">${child.likecount}</span>
											<span class="comments-items">|</span>
										</c:when>
										<c:when test="${!child.liked}">
											<img class="arrows pointer" src="/o/energy-hub-theme/images/grey-up-arrow.png" onclick="ajaxCall('reply_like', '${child.replyId}')">
											<span id="total-likers" class="">${child.likecount}</span>
											<span class="comments-items">|</span>
										</c:when>
									</c:choose>
								</li>
								<li class="detailsItem">
									<c:choose>
										<c:when test="${child.dislike}">
											<img class="red-down-arrow pointer" id="red-arrow" src="/o/energy-hub-theme/images/red-down-arrow.png">
											<span id="total-likers" class="red-dislikers">${child.dislikecount}</span>
										</c:when>
										<c:when test="${!child.dislike}">
											<img class="red-down-arrow pointer" id="red-arrow" src="/o/energy-hub-theme/images/grey-down-arrow.png" onclick="ajaxCall('reply_dislike', '${child.replyId}')">
											<span id="total-likers" class="">${child.dislikecount}</span>
										</c:when>
									</c:choose>
								</li>
							</ul>
						</div>
						<div class="col-auto">
							<ul class="cardReplies">
								<li>
									<a href="javascript:;" class="pointer" onclick="$('.cardQuestions_${child.replyId}').removeClass('hide');">
										<img src="/o/energy-hub-theme/images/doubleBlueArrow.svg">
										<liferay-ui:message key="ShowAllReplies"/>
									${fn: length(child.children)}</li>
								</a>
								<c:if test="${isSignedIn}">
									<li>
										<a href="javascript:;" class="pointer" onclick="document.getElementById('replyDiv_${child.replyId}').style.display = ''">
											<img src="/o/energy-hub-theme/images/arrowRightBlue.svg">
											<liferay-ui:message key="Reply"/>
										</li>
									</a>
								</c:if>
							</ul>
						</div>
					</div>
				</div>
				<div class="textarea-container" style="display: none" id="replyDiv_${child.replyId}">
					<textarea id="reply_${child.replyId}" name="w3review" rows="4" cols="50" placeholder="AddReply..."></textarea>
					<input style="opacity:0;position: absolute;" type="file" id="replyDoc_${child.replyId}" name="replyDoc" label="" accept="image/*" hidden="hidden"/>
					<div class="flexDisplay">
						<button type="button" id="custom-button" value="UploadDocument" onclick="clickCustomButton('replyDoc_${child.replyId}', 'replyDoc-text_${child.replyId}')" class="uploadButton ">
							<img src="/o/energy-hub-theme/images/upload.svg">
							<span id="replyDoc-text_${child.replyId}"/>
						</button>
						<button class="blueBtn submit mt-3 post" onclick="ajaxCall('postReply', '${forum.id}', '${child.replyId}', $('#reply_${child.replyId}').val())"><liferay-ui:message key="Post" /></button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<c:if test="${fn:length(child.children) > 0}">
	<c:forEach var="childloop" items="${child.children}">
		<c:set var="child" value="${childloop}" scope="request"/>
		<c:set var="forum" value="${forum}" scope="request"/>
		<jsp:include page="/childReply.jsp"/>
	</c:forEach>
</c:if>
