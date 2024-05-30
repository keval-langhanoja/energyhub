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
	.note-editable img{
		display: none;
	}
</style>
<div class="d-flex comments-reply-section mt-4" id="repliesDiv_${child.commentId}" style='display: none !important;'>
    <div class="row d-flex" style="width: 100%;">
    	<div class="d-flex">
    		<div class="col-2">
	            <img class="author-profile profileImageComment" src="${child.userPortrait}">
	        </div>
	        <div class="col-10 w-80 m-l-10 subComment">
	            <p class="w-9rem">
	                <span class="color-name redirectTitle" onclick="window.location.href ='${child.commentUserProfileUrl}';">${child.commentuserName}
	                	<span class="comments-items"><liferay-ui:message key="${child.commentuserRole}" />   - ${child.createDateDisplay}</span>
	                </span>
	            </p>
	            <div>${child.comment}</div>
	            <div class="row d-flex">
					<p>
						<img class="green-up-arrow" id="green-arrow" onclick="ajaxCall('like', '${child.commentId}', '', '', '')"
							src="/o/energy-hub-theme/images/green-up-arrow.png"> 
						<span id="total-likers" class="green-likers">${child.likecount}</span>
						<span class="comments-items">|</span>
						<img class="red-down-arrow" id="red-arrow" onclick="ajaxCall('dislike', '${child.commentId}', '', '')"
							src="/o/energy-hub-theme/images/red-down-arrow.png">
						<span id="total-likers" class="red-dislikers">${child.dislikecount}</span>
						<img class="arrows" src="/o/energy-hub-theme/images/arrowRightBlue.svg">
						<a class="comments-items" onclick="showHideReply('${child.commentId}')"><liferay-ui:message key="Reply"/></a>
						<c:if test="${fn:length(child.children) > 0}">
							<img class="arrows" src="/o/energy-hub-theme/images/doubleBlueArrow.svg">
							<script>
								var ids_${child.commentId} = new Array();
							</script>
							<c:forEach var="com" items="${child.children}">
								<script>
									 obj = new Object();
									 obj.id = ${com.commentId}; 
									 ids_${child.commentId}.push(obj);
							 	</script>
							</c:forEach>
							<a class="comments-items" onclick="showHideReplies(ids_${child.commentId})"><liferay-ui:message key="ShowReplies"/> (${fn:length(child.children)})</a>
						</c:if>	
					</p>
				</div>
				<div class="textarea-container subComment" style="display: none" id="replyDiv_${child.commentId}">
					<div id="reply_${child.commentId}"></div>
					<script>
						$(document).ready(function () {
							$('#reply_${child.commentId}').summernote();
						});
					</script>	
					<div class="flexDisplay p-2">
						<button onclick="ajaxCall('Postcomment', '${articleId}', '${child.commentId}',  'reply_${child.commentId}')" 
						class="blueBtn submit post commentSubmit" value="Post"><liferay-ui:message key="Post" /></button>
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
