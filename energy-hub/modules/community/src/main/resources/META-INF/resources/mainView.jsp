<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="/o/energy-hub-theme/style/communityForum.css">
<portlet:actionURL var="communityURL" />
<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>

<style>
	.seeAll a {
	    display: flex;
	    justify-content: end !important;
	    align-items: center !important;
    }
    .seeAll:hover {
	    transform: none;
	}
	.mainViewCommunity {
	    padding-left: 50px;
	    padding-right: 10px;
	}
	.innovationCard .cardDetails p {
		max-height: 200px;
	}
	.cardDetails {
		justify-content: start;
	}
	.threadType {
		text-decoration : none;
	}
	.threadType:hover {
		color: #333333 !important;
        text-decoration : underline !important;
	}
	.innovationCard .cardDetails p {
	    font-size: 14px !important;
	    font-weight: 400;
	    letter-spacing: 0.14px;
	    color: #333333;
	    overflow: hidden;
	    max-height: 150px;
	}
	.innovationCard .cardDetails .cardTitle {
		width: 100%;
	}
	.programCardImage {
	    background-color: #f0f5fc !important;
	}
</style>

<div class="row mt-5 innovationContent mainViewCommunity">  
 	<c:forEach var="type" items="${ThreadTypes}">
 		<a class="threadType" href="/policy-forum?view&p_r_p_categoryId=${categoryId}&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${type.id}">
			<h1 class="innovationTitle mb-2">${type.value} (${type.count})</h1>
		</a>
		<c:forEach var="journalArticle" items="${type.forumList}">
	 		<div class="col-12 col-xl-6 mt-4 innovationContent">
				<a href="javascript:;" onclick="ajaxCall('view', '${journalArticle.id}', '/policy-forum?viewForum&p_r_p_categoryId=${categoryId}&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${type.id}&forumId=${journalArticle.id}')"  class="card innovationCard grid">
					<div class="programImageContainer">
						<div class="programCardImage" style="background: url('${journalArticle.coverIamgeURL}');"></div>
					</div>
					<div class=" cardDetails py-4" style="padding: 20px;">
						<h2 class="cardTitle">${journalArticle.title}</h2></br>
						<p class="mt-3 mb-4">${journalArticle.description}</p>
					</div>
				</a> 
			</div> 
		</c:forEach>
		<div class="seeAll text-end mt-4">
			<a href="/policy-forum?view&p_r_p_categoryId=${categoryId}&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}&threadType=${type.id}">
				<span><liferay-ui:message key="SeeAll" /></span>
				<span class="arrow bg-black"><img src="/o/energy-hub-theme/images/arrow.svg"></span>
			</a>
		</div>
 	</c:forEach>
</div>

<script>
function ajaxCall(key, id, url) {
	if(key == "view" && ! ${isSignedIn}){
		window.location.href = location.origin + url;
	}
	if(${isSignedIn}) {
		var xhr = new XMLHttpRequest();
		var formData = new FormData();
		 
		var data = {
				 <portlet:namespace />id: id,
				 <portlet:namespace />key: key
		};
		
		xhr.onloadend = function (e) {
			if(key == "view"){
				window.location.href = location.origin + url;
			}
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
</script>