<%@ include file="/init.jsp" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

            
<div class="programsFlex d-flex align-items-center justify-content-between">
	<h1 class="innovationTitle mb-2">${categNameTitle}</h1>
	<c:if test="${is_signed_in && showAddOngoingProjects}">
		<a class="mb-2 viewMyProjects" href="/my-projects?p_r_p_categoryId=${categoryId}&parentCategId=${parentCategId}&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}"><span><liferay-ui:message key="ViewMyProjects" /></span></a>
		<button class="add mb-2" title="Add your Innovation Project" onclick="location.href = '/add-project?p_r_p_categoryId=${categoryId}&parentCategId=${parentCategId}&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}';">
			<span class="plus">
				<img class="mb-1" src="/o/energy-hub-theme/images/add.svg">
			</span>
			<span><liferay-ui:message key="Add" /></span>
		</button>
	</c:if>
	<c:if test="${is_signed_in && showAddInnovationPrograms}">
		<a class="mb-2 viewMyProjects" href="/my-projects?p_r_p_categoryId=${categoryId}&parentCategId=${parentCategId}&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}"><span><liferay-ui:message key="ViewMyProjects" /></span></a>
		<button class="add mb-2" title="Add your Innovation Program" onclick="location.href = '/custom-forms?createInnovationProgram&p_r_p_categoryId=${categoryId}&parentCategId=${parentCategId}&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}';">
			<span class="plus">
				<img class="mb-1" src="/o/energy-hub-theme/images/add.svg">
			</span>
			<span><liferay-ui:message key="Add" /></span>
		</button>
	</c:if>
	<c:if test="${is_signed_in && showAddInnovationChallenges}">
		<a class="mb-2 viewMyProjects" href="/my-projects?p_r_p_categoryId=${categoryId}&parentCategId=${parentCategId}&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}"><span><liferay-ui:message key="ViewMyProjects" /></span></a>
		<button class="add mb-2" title="Add your Innovation Challenge" onclick="location.href = '/custom-forms?createInnovationChallenge&p_r_p_categoryId=${categoryId}&parentCategId=${parentCategId}&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}';">
			<span class="plus">
				<img class="mb-1" src="/o/energy-hub-theme/images/add.svg">
			</span>
			<span><liferay-ui:message key="Add" /></span>
		</button>
	</c:if>
	<c:if test="${is_signed_in && showAddOpportunitiesIndustries}">
		<a class="mb-2 viewMyProjects" href="/my-projects?p_r_p_categoryId=${categoryId}&parentCategId=${parentCategId}&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}"><span><liferay-ui:message key="ViewMyProjects" /></span></a>
		<button class="add mb-2" title="Add your Innovation Challenge" onclick="location.href = '/custom-forms?createOpportunitiesIndustries&p_r_p_categoryId=${categoryId}&parentCategId=${parentCategId}&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}';">
			<span class="plus">
				<img class="mb-1" src="/o/energy-hub-theme/images/add.svg">
			</span>
			<span><liferay-ui:message key="Add" /></span>
		</button>
	</c:if>
</div>
<div class="row mt-5">  
 	<c:forEach var="journalArticle" items="${journalArticleList}">
 		<div class="col-12 col-xl-6 mt-4">
			<a href="/${UrlStart}?categName=${categName}&groupId=${groupId}&articleId=${journalArticle.pk}" class="card innovationCard grid">
				<div class="programImageContainer">
					<div class="programCardImage" style="background: url('${journalArticle.imageURL}');"></div>
				</div>
				<div class=" cardDetails py-4">
					<h2 class="cardTitle">${journalArticle.title}</h2>
					<div class="mt-3 mb-4 programBasicCard">${journalArticle.description}</div>
				</div>
				<c:if test="${journalArticle.category !=''}">
					<div class="category">
						<span class="grayText px-2">${journalArticle.category}</span>
					</div>
				</c:if>
			</a> 
		</div> 
 	</c:forEach>
	<div class="seeAll text-end mt-4">
		<a href="/see-all?parentCategId=${parentCategId}&p_r_p_categoryId=${categoryId}&ddmStructureKey=${ddmStructureKey}">
			<span><liferay-ui:message key="SeeAll" /></span>
			<span class="arrow bg-black"><img src="/o/energy-hub-theme/images/arrow.svg"></span>
		</a>
	</div>
</div> 
