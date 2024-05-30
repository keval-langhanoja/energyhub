<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
	
<div class="MainContent">
	<!--signup section-->
	<section id="signup" class="signup userType position-relative">
		<div class="bgImage">
			<svg class="animatedDots orange" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
				style="left: 25%; top: 55%; right: auto">
				<circle cx="200" cy="50" r="50" />
			</svg>
			<svg class="animatedDots green2" fill="#8BB029" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
				style="left: 3%; top:15%">
				<circle cx="200" cy="50" r="50" />
			</svg>
		</div>
		<div class="content position-relative mx-auto py-5 px-3">
			<div class=" d-flex justify-content-between titleSec">
				<div class="d-flex path">
					<a><liferay-ui:message key="Home" /><span class="px-2"><img width="10px" src="/o/energy-hub-theme/images/doubleArrow.svg"></span></a>
					<a><liferay-ui:message key="InnovationPage" /><span class="px-2"><img width="10px"
								src="/o/energy-hub-theme/images/doubleArrow.svg"></span></a>
					<a><liferay-ui:message key="ProjectName" /></a>

				</div>
				<div class="">
					<h1 class="position-relative MainTitle">
						<div class="yellowBorder" style="height:25px"></div>${the_title}
					</h1>
				</div>
			</div>
		</div>
		<div class="content mx-auto py-5 px-3">
			<svg class="animatedDots blue" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
				style="left: 1%; top:10%">
				<circle cx="200" cy="50" r="50" />
			</svg>
			<svg class="animatedDots green2" fill="#8BB029" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
				style="left: 2%; top:15%; right: auto">
				<circle cx="200" cy="50" r="50" />
			</svg>
			<div class="userTypeCard position-relative topBorder mt-3 py-5 px-5">
				<div class="row">
					<h1 class="d-flex justify-content-center"><img src="/o/energy-hub-theme/images/check-mark.png"
							class="tick"><liferay-ui:message key="Great" /></h1>
				</div>
				<div class="row">
					<div class="col-12 col-md-12 col-xl-12 mt-12 d-flex justify-content-center">
						<label class="notification"><liferay-ui:message key="ProjectSubmitted" /></label>
					</div>
				</div>
				<div class="row d-flex justify-content-center">
					<div class="card info p-3">
						<div class="d-flex justify-content-start align-items-center">
							<div><img width="30px" src="/o/energy-hub-theme/images/warning.png"></div>
							<div class="px-3 text">
								<p class="mb-0 font-weight-bold"><liferay-ui:message key="SuccessMessage" /></p>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="d-flex justify-content-center mt-5">
						<div class="col-md-6 col-sm-12">
							<a href="/innovation?p_r_p_categoryId=${categId}" class="readmore go right">
								<liferay-ui:message key="InnovationPage" /><span class="arrow"><img src="/o/energy-hub-theme/images/arrow.svg"></span>
							</a>
						</div>
						<div class="col-md-6 col-sm-12">
							<a href="${myProjectUrl}" class="readmore go">
								<liferay-ui:message key="MyProjectsPage" /><span class="arrow"><img src="/o/energy-hub-theme/images/arrow.svg"></span>
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
