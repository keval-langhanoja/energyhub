<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="/o/energy-hub-theme/style/communityForum.css">
<portlet:actionURL var="communityURL" />
<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>

<style>
	.card-text {
		display: -webkit-box;
	    -webkit-line-clamp: 6;
	    -webkit-box-orient: vertical;
	    overflow: hidden;
	    text-overflow: ellipsis;
	}
	.redirectTitle:hover {
	    cursor: pointer;
	}
	.infosection {
		position: absolute;
		bottom: 0;
	}
	.pointer:hover{
	    cursor: pointer;
   	}
</style>
 <div class="d-flex flex-wrap justify-content-center communityContainer mt-5">  
	<div class="row">   
		<c:forEach var="forum" items="${forums}" begin="0" end="3" varStatus="loop">
			<div class="card">
				<div class="card-body" style="min-height: 400px;">
					<div class="row justify-content-between">
						<div class="col-9">
							<div class="row">
								<div class="col-lg-4 col-md-4 col-sm-5 col-xs-5">
									<img class="cardUserImg" src="${forum.userPortrait}" onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;"/>
								</div>
								<div class="col-lg-7 col-md-7 col-sm-7 col-xs-7"
									style="padding:0px">
									<div class="cardUserName">${forum.userName}</div>
									<div class="cardUserDate">${forum.createDateDisplay}</div>
								</div>
							</div>
						</div>
					</div>
					<h5 class="card-title redirectTitle" onclick="window.location.href = '${forum.forumURL}';">${forum.title}</h5>
					<div class="card-text">${forum.description}</div>
					<div class="row justify-content-between mb-5">
						<div class="col-auto">
							<ul class="cardTopics">
								<c:forEach var="tag" items="${forum.currentTags}" begin="0" end="4" varStatus="loop">
									<li class="topicItem">${tag.name}</li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<div class="row justify-content-between mt-4 infosection mb-4">
						<div class="col-auto">
							<ul class="cardDetails">
								<li class="detailsItem">
									<img class="mb-1" src="/o/energy-hub-theme/images/eye.svg">
									<span>${forum.viewcount}</span>
								</li>
								<li class="detailsItem">
									<img class="mb-1" src="/o/energy-hub-theme/images/comment-square.svg">
									<span>${forum.totalReplies}</span>
								</li>
								<li class="detailsItem">
									<img class="mb-1" src="/o/energy-hub-theme/images/arrowUp.svg">
									<span>${forum.likecount}</span>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>   
	</div> 
	
	<div class="seeAll text-end mt-4">
		<a href="${seeAllURL}">
			<span><liferay-ui:message key="SeeAll" /></span>
			<span class="arrow bg-black"><img src="/o/energy-hub-theme/images/arrow.svg"></span>
		</a>
	</div>        
</div>