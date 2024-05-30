<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
	.about-image {
	    max-width: 100%;
/*     	max-height: 50% !important; */
    	margin: 0 auto !important;
    	height: auto !important;
	}
</style>
<div class="userTypeCard position-relative topBorder mt-6 py-5 px-5">
	<div class="row">
		<div class="row">
			<h1><liferay-ui:message key="GeneralInformation" /></h1>
		</div>
		<c:if test="${currentUser.imageUrl!=''}">
			<div class="col-lg-4 col-md-12 mt-3">
				<div class="row">
					<img src="${currentUser.imageUrl}" class="about-image"  
						onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;"/>
				</div>
			</div>
		</c:if>
		<c:if test="${currentUser.imageUrl ==''}">
			<div class="col-12">
		</c:if>
		<c:if test="${currentUser.imageUrl!=''}">
			<div class="col-lg-8 col-md-12">
		</c:if> 
			<c:if test="${not empty currentUser.firstName && currentUser.firstName !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="Name" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.firstName} 
							<c:if test="${not empty currentUser.lastName && currentUser.lastName!=''}">
							${currentUser.lastName}
							</c:if>
						</label>
					</div>
				</div>
			</c:if>
			
 			<c:if test="${not empty currentUser.company && currentUser.company !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="Company" /> <liferay-ui:message key="Name" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.company}</label>
					</div>
				</div>
			</c:if>
 
			<c:if test="${not empty currentUser.email && currentUser.email !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="Email" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.email}</label>
					</div>
				</div>
			</c:if>
			
			<c:if test="${not empty currentUser.userRole && currentUser.userRole !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="Role" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data"><liferay-ui:message key="${currentUser.userRole}" /></label>
					</div>
				</div>
			</c:if>
			
			<c:if test="${not empty currentUser.carMake && currentUser.carMake !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="carMake" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.carMake}</label>
					</div>
				</div>
			</c:if>
			
			<c:if test="${not empty currentUser.otherCarMake && currentUser.otherCarMake !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="otherCarMake" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.otherCarMake}</label>
					</div>
				</div>
			</c:if>
			
			<c:if test="${not empty currentUser.googleMapLink && currentUser.googleMapLink !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="googleMapLink" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">
							<a class="" href="${currentUser.googleMapLink}" target="_blank">
								${currentUser.googleMapLink}
							</a>
						</label>
					</div>
				</div>
			</c:if>
			
			<c:if test="${not empty currentUser.companyPhoneNumber && currentUser.companyPhoneNumber !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="PhoneNumber" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.companyPhoneNumber}</label>
					</div>
				</div>
			</c:if>
 
			<c:if test="${not empty currentUser.country && currentUser.country !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6 mt-4 inputContainer">
						<label><liferay-ui:message key="Address" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.country}<c:if test="${not empty currentUser.kaza && currentUser.kaza!=''}"> ${currentUser.kaza}</c:if></label>
					</div>
				</div>
			</c:if>
	 
			<c:if test="${not empty currentUser.companyWebsite && currentUser.companyWebsite !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="LinkToCompanyWebsite" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">
							<a class="" href="${currentUser.companyWebsite}" target="_blank">
								${currentUser.companyWebsite}
							</a>
						</label>
					</div>
				</div>
			</c:if>
			
			<c:if test="${not empty currentUser.linkToWebsite && currentUser.linkToWebsite !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="Website" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">
							<a class="" href="${currentUser.linkToWebsite}" target="_blank">
								${currentUser.linkToWebsite}
							</a>
						</label>
					</div>
				</div>
			</c:if>
			
			<c:if test="${not empty currentUser.universityToApply && currentUser.universityToApply !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="LinkToUniversityProfile" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.universityToApply}</label>
					</div>
				</div>
			</c:if>
	 
			<c:if test="${not empty currentUser.linkedIn && currentUser.linkedIn !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="LinkedIn" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.linkedIn}</label>
					</div>
				</div>
			</c:if>
	 
			<c:if test="${not empty currentUser.profession && currentUser.profession !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="Profession" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.profession}</label>
					</div>
				</div>
			</c:if>
	 
			<c:if test="${not empty currentUser.yearOfEstablishment && currentUser.yearOfEstablishment !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="YearOfEstablishment" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.yearOfEstablishment}</label>
					</div>
				</div>
			</c:if>
			
			<c:if test="${not empty currentUser.numberOfEmployees && currentUser.numberOfEmployees !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="NumberOfEmployees" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.numberOfEmployees}</label>
					</div>
				</div>
			</c:if>
			
			<c:if test="${not empty currentUser.sector && currentUser.sector !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="Sector" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.sector}</label>
					</div>
				</div>
			</c:if>
	 
			<c:if test="${not empty currentUser.organizationType && currentUser.organizationType !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="OrganizationType" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.organizationType}</label>
					</div>
				</div>
			</c:if>
			
			<c:if test="${not empty currentUser.stage && currentUser.stage !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="Stage" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.stage}</label>
					</div>
				</div>
			</c:if>
	 
			<c:if test="${not empty currentUser.role && currentUser.role !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="Role" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.role}</label>
					</div>
				</div>
			</c:if>
	 
			<c:if test="${not empty currentUser.typeOfSupport && currentUser.typeOfSupport !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="TypeOfSupport" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.typeOfSupport}</label>
					</div>
				</div>
			</c:if>
			
			<c:if test="${not empty currentUser.maturityLevelOfSolution && currentUser.maturityLevelOfSolution !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="MaturityLevel" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.maturityLevelOfSolution}</label>
					</div>
				</div>
			</c:if>
			
			<c:if test="${not empty currentUser.challengeToSolve && currentUser.challengeToSolve !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="ChallengeSolve" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.challengeToSolve}</label>
					</div>
				</div>
			</c:if>
			
			<c:if test="${not empty currentUser.sdgSolution && currentUser.sdgSolution !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="SDGSolutionLabel" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.sdgSolution}</label>
					</div>
				</div>
			</c:if>
			
			<c:if test="${not empty currentUser.mainBusinessActivity && currentUser.mainBusinessActivity !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="MainBusinessActivity" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.mainBusinessActivity}</label>
					</div>
				</div>
			</c:if>
	 
			<c:if test="${not empty currentUser.description && currentUser.description !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="Description" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.description}</label>
					</div>
				</div>
			</c:if>
	 
			<c:if test="${not empty currentUser.solutionOfferingDescription && currentUser.solutionOfferingDescription !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="Description" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">${currentUser.solutionOfferingDescription}</label>
					</div>
				</div>
			</c:if>
			
			<c:if test="${not empty currentUser.areas && currentUser.areas !=''}">
				<div class="separatorDashed"></div>
				<div class="row">
					<div class="col-6  mt-4 inputContainer">
						<label><liferay-ui:message key="AreaOfExpertiseLabel" /></label>
					</div>
					<div class="col-6  mt-4 inputContainer">
						<label id="lblMaturityStage" class="data">
							<c:forEach var="elt" items="${currentUser.areas}">
								<liferay-ui:message key="${elt.area}" /> - <liferay-ui:message key="${elt.level}" /> <br>
							</c:forEach>
						</label>
					</div>
				</div>
			</c:if> 
		</div>
	</div>
</div>
