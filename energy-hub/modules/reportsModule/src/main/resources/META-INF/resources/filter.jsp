<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
	.alert-danger {
        display: none;
    }
	table tr td {
		border: 0 !important;
	}
	table tr td:first-child {
		text-align: -webkit-center !important;
	}
	@media screen {
		@font-face {
			font-family: 'Lato';
			font-style: normal;
			font-weight: 400;
			src: local('Lato Regular'), local('Lato-Regular'),
				url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff)
				format('woff');
		}
		@font-face {
			font-family: 'Lato';
			font-style: normal;
			font-weight: 700;
			src: local('Lato Bold'), local('Lato-Bold'),
				url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff)
				format('woff');
		}
		@font-face {
			font-family: 'Lato';
			font-style: italic;
			font-weight: 400;
			src: local('Lato Italic'), local('Lato-Italic'),
				url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff)
				format('woff');
		}
		@font-face {
			font-family: 'Lato';
			font-style: italic;
			font-weight: 700;
			src: local('Lato Bold Italic'), local('Lato-BoldItalic'),
				url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff)
				format('woff');
		}
	}
	
	/* CLIENT-SPECIFIC STYLES */
	body, table, td, a {
		-webkit-text-size-adjust: 100%;
		-ms-text-size-adjust: 100%;
	}
	
	table, td {
		mso-table-lspace: 0pt;
		mso-table-rspace: 0pt;
	}
	
	img {
		-ms-interpolation-mode: bicubic;
	}
	
	/* RESET STYLES */
	img {
		border: 0;
		height: auto;
		line-height: 100%;
		outline: none;
		text-decoration: none;
	}
	
	table {
		border-collapse: collapse !important;
	}
	
	body {
		height: 100% !important;
		margin: 0 !important;
		padding: 0 !important;
		width: 100% !important;
	}
	
	/* iOS BLUE LINKS */
	a[x-apple-data-detectors] {
		color: inherit !important;
		text-decoration: none !important;
		font-size: inherit !important;
		font-family: inherit !important;
		font-weight: inherit !important;
		line-height: inherit !important;
	}
	
	/* MOBILE STYLES */
	@media screen and (max-width:600px) {
		h1 {
			font-size: 32px !important;
			line-height: 32px !important;
		}
	}
	
	/* ANDROID CENTER FIX */
	div[style*="margin: 16px 0;"] {
		margin: 0 !important;
	}
	
	.innertable {
		font-family: arial, sans-serif;
		border-collapse: collapse;
		width: 100%;
	}
	
	.innertd, .innerth {
		border: 1px solid #dddddd !important;
		text-align: left;
		padding: 8px;
		max-width: 100px;
	}
	
	.thCount {
		width: 30px;
		text-transform: capitalize;
	}
	
	.hidden {
		display: none !important;
	}
	
	.hrefNoColor {
		color: #212529;
		text-decoration: none;
	}
	
	.hrefNoColor:hover {
		text-decoration: underline;
	}
	
	.border {
		border: 1px solid #707070;
		min-height: 55px;
	}
	
	.download-wrapper:hover {
		cursor: pointer;
	}
	
	.capitalize {
		text-transform: capitalize;
	}
	
	select:focus-visible {
		outline: transparent;
	}
	.thSort {
		height: 100%;
    	text-align-last: end;
    	padding: 0px 0px 5px 0px;
    	max-width: 30px;
	}
</style>
<c:if test="${fn:length(ThreadType) gt 0 || fn:length(SuccessStoryType) gt 0 || fn:length(InnovationChallengeChallengeType) gt 0 ||
	fn:length(InnovationProgramActivityType) gt 0 || fn:length(InnovationProgramStage) gt 0 || fn:length(OnGoingStage) gt 0 ||
	fn:length(OppIndustType) gt 0 || fn:length(NewsType) gt 0 || fn:length(EventType) gt 0 || fn:length(Attendance) gt 0 ||
	fn:length(PublicationType) gt 0 }">
	<div class="col-4 border d-flex align-items-center justify-content-between">
		<label class="mb-0"><liferay-ui:message key="StartDate" /></label> <input id="startDate" type="date" onkeydown="return false"></input>
	</div>
	<div class="col-4 border d-flex align-items-center justify-content-between">
		<label class="mb-0"><liferay-ui:message key="EndDate" /></label> <input id="endDate" type="date" onkeydown="return false"></input>
	</div>
</c:if>


<!-- Threads Filter -->
<c:if test="${fn:length(ThreadType) gt 0 }">
	<div class="col-4 d-flex align-items-center justify-content-between" style="padding: 0">
		<select class="border" style="width: 100% ;padding: 11px;" label="" id="ThreadType" name="ThreadType"> 
			<option disabled="true" selected="true" value=""><liferay-ui:message key="Select" /> <liferay-ui:message key="Type" /></option>
			<c:forEach var="elt" items="${ThreadType}">
				<option cssClass="dropdown-item" value="${elt.id}">
					${elt.value} 
				</option> 
			 </c:forEach>
		</select>
	</div>
</div> <!-- END row -->
</c:if>

<!-- Success Stories Filter-->
<c:if test="${fn:length(SuccessStoryType) gt 0 }">
	<div class="col-4 d-flex align-items-center justify-content-between" style="padding: 0">
		<select class="border" style="width: 100% ;padding: 11px;" label="" id="SuccessStoryType" name="SuccessStoryType"> 
			<option disabled="true" selected="true" value=""><liferay-ui:message key="Select" /> <liferay-ui:message key="Type" /></option>
			<c:forEach var="elt" items="${SuccessStoryType}">
				<option cssClass="dropdown-item" value="${elt.id}">
					${elt.value} 
				</option> 
			 </c:forEach>
		</select>
	</div>
</div> <!-- END row -->
</c:if>

<!-- Innovation Challenges Filter-->
<c:if test="${fn:length(InnovationChallengeChallengeType) gt 0 }">
	<div class="col-4 d-flex align-items-center justify-content-between" style="padding: 0">
		<select class="border" style="width: 100% ;padding: 11px;" label="" id="InnovationChallengeChallengeType" name="InnovationChallengeChallengeType"> 
			<option disabled="true" selected="true" value=""><liferay-ui:message key="Select" /> <liferay-ui:message key="Type" /></option>
			<c:forEach var="elt" items="${InnovationChallengeChallengeType}">
				<option cssClass="dropdown-item" value="${elt.id}">
					${elt.value} 
				</option> 
			 </c:forEach>
		</select>
	</div>
</div> <!-- END row -->
</c:if>

<!-- Innovation Programs Filter-->
<c:if test="${fn:length(InnovationProgramActivityType) gt 0 }">
	<div class="col-4 d-flex align-items-center justify-content-between" style="padding: 0">
		<select class="border" style="width: 100% ;padding: 11px;" label="" id="InnovationProgramActivityType" name="InnovationProgramActivityType"> 
			<option disabled="true" selected="true" value=""><liferay-ui:message key="Select" /> <liferay-ui:message key="Type" /></option>
			<c:forEach var="elt" items="${InnovationProgramActivityType}">
				<option cssClass="dropdown-item" value="${elt.id}">
					${elt.value} 
				</option> 
			 </c:forEach>
		</select>
	</div>
</div> <!-- END row -->
</c:if>
<c:if test="${fn:length(InnovationProgramStage) gt 0 }">
<div class="row mb-2">
	<div class="col-4 d-flex align-items-center justify-content-between" style="padding: 0">
		<select class="border" style="width: 100% ;padding: 11px;" label="" id="InnovationProgramStage" name="InnovationProgramStage"> 
			<option disabled="true" selected="true" value=""><liferay-ui:message key="Select" /> <liferay-ui:message key="Stage" /></option>
			<c:forEach var="elt" items="${InnovationProgramStage}">
				<option cssClass="dropdown-item" value="${elt.id}">
					${elt.value} 
				</option> 
			 </c:forEach>
		</select>
	</div>
</c:if>

<!-- Ongoing Project Filter-->
<c:if test="${fn:length(OnGoingCateg) gt 0 }">
	<div class="col-4 d-flex align-items-center justify-content-between" style="padding: 0">
		<select class="border" style="width: 100% ;padding: 11px;" label="" id="OnGoingCateg" name="OnGoingCateg"> 
			<option disabled="true" selected="true" value=""><liferay-ui:message key="Select" /> <liferay-ui:message key="ProjectCategory" /></option>
			<c:forEach var="elt" items="${OnGoingCateg}">
				<option cssClass="dropdown-item" value="${elt.id}">
					${elt.value} 
				</option> 
			 </c:forEach>
		</select>
	</div>
</div> <!-- END row -->
</c:if>
<c:if test="${fn:length(OnGoingStage) gt 0 }">
<div class="row mb-2">
	<div class="col-4 d-flex align-items-center justify-content-between" style="padding: 0">
		<select class="border" style="width: 100% ;padding: 11px;" label="" id="OnGoingStage" name="OnGoingStage"> 
			<option disabled="true" selected="true" value=""><liferay-ui:message key="Select" /> <liferay-ui:message key="Stage" /></option>
			<c:forEach var="elt" items="${OnGoingStage}">
				<option cssClass="dropdown-item" value="${elt.id}">
					${elt.value} 
				</option> 
			 </c:forEach>
		</select>
	</div>
</c:if>

<!-- Opportunities For Industries Filter-->
<c:if test="${fn:length(OppIndustType) gt 0 }">
	<div class="col-4 d-flex align-items-center justify-content-between" style="padding: 0">
		<select class="border" style="width: 100% ;padding: 11px;" label="" id="OppIndustType" name="OppIndustType"> 
			<option disabled="true" selected="true" value=""><liferay-ui:message key="Select" /> <liferay-ui:message key="Type" /></option>
			<c:forEach var="elt" items="${OppIndustType}">
				<option cssClass="dropdown-item" value="${elt.id}">
					${elt.value} 
				</option> 
			 </c:forEach>
		</select>
	</div>
</div> <!-- END row -->
</c:if>

<!-- News Filter-->
<c:if test="${fn:length(NewsType) gt 0 }">
	<div class="col-4 d-flex align-items-center justify-content-between" style="padding: 0">
		<select class="border" style="width: 100% ;padding: 11px;" label="" id="NewsType" name="NewsType"> 
			<option disabled="true" selected="true" value=""><liferay-ui:message key="Select" /> <liferay-ui:message key="Type" /></option>
			<c:forEach var="elt" items="${NewsType}">
				<option cssClass="dropdown-item" value="${elt.id}">
					${elt.value} 
				</option> 
			 </c:forEach>
		</select>
	</div>
</div> <!-- END row -->
</c:if>

<!-- Events Filter-->
<c:if test="${fn:length(EventType) gt 0 }">
	<div class="col-4 d-flex align-items-center justify-content-between" style="padding: 0">
		<select class="border" style="width: 100% ;padding: 11px;" label="" id="EventType" name="EventType"> 
			<option disabled="true" selected="true" value=""><liferay-ui:message key="Select" /> <liferay-ui:message key="Type" /></option>
			<c:forEach var="elt" items="${EventType}">
				<option cssClass="dropdown-item" value="${elt.id}">
					${elt.value} 
				</option> 
			 </c:forEach>
		</select>
	</div>
</div> <!-- END row -->
</c:if>
<c:if test="${fn:length(Attendance) gt 0 }">
	<div class="row mb-2">
		<div class="col-4 d-flex align-items-center justify-content-between" style="padding: 0">
			<select class="border" style="width: 100% ;padding: 11px;" label="" id="Attendance" name="Attendance"> 
				<option disabled="true" selected="true" value=""><liferay-ui:message key="Select" /> <liferay-ui:message key="Attendance" /></option>
				<c:forEach var="elt" items="${Attendance}">
					<option cssClass="dropdown-item" value="${elt.id}">
						${elt.value} 
					</option> 
				 </c:forEach>
			</select>
		</div>
</c:if>

<!-- Publication Filter-->
<c:if test="${fn:length(PublicationType) gt 0 }">
	<div class="col-4 d-flex align-items-center justify-content-between" style="padding: 0">
		<select class="border" style="width: 100% ;padding: 11px;" label="" id="PublicationType" name="PublicationType"> 
			<option disabled="true" selected="true" value=""><liferay-ui:message key="Select" /> <liferay-ui:message key="Type" /></option>
			<c:forEach var="elt" items="${PublicationType}">
				<option cssClass="dropdown-item" value="${elt.id}">
					${elt.value} 
				</option> 
			 </c:forEach>
		</select>
	</div>
</div> <!-- END row -->
</c:if>

<!-- Courses Report Filter-->
<c:if test="${fn:length(MoodleCategs) gt 0 }">
<div class="row mb-2">
	<div class="col-3 d-flex align-items-center justify-content-between" style="padding: 0">
		<select class="border" style="width: 100% ;padding: 11px;" label="" id="MoodleCategs" name="MoodleCategs"> 
			<option disabled="true" selected="true" value=""><liferay-ui:message key="Select" /> <liferay-ui:message key="Type" /></option>
			<c:forEach var="elt" items="${MoodleCategs}">
				<option cssClass="dropdown-item" value="${elt.id}">
					${elt.value} 
				</option> 
			 </c:forEach>
		</select>
	</div> 
</c:if>

<!-- Courses Attendees Report Filter-->
<c:if test="${fn:length(country) gt 0 }">
	<div class="col-3 d-flex align-items-center justify-content-between" style="padding: 0">
		<select class="border" style="width: 100% ;padding: 11px;" label="" id="country" name="country"> 
			<option disabled="true" selected="true" value=""><liferay-ui:message key="Select" /> <liferay-ui:message key="Country" /></option>
			<c:forEach var="elt" items="${country}">
				<option cssClass="dropdown-item" value="${elt.id}">
					${elt.value} 
				</option> 
			 </c:forEach>
		</select>
	</div> 
</c:if>
 
<c:if test="${fn:length(profession) gt 0 }">
	<div class="col-3 d-flex align-items-center justify-content-between" style="padding: 0">
		<select class="border" style="width: 100% ;padding: 11px;" label="" id="profession" name="profession"> 
			<option disabled="true" selected="true" value=""><liferay-ui:message key="Select" /> <liferay-ui:message key="Profession" /></option>
			<c:forEach var="elt" items="${profession}">
				<option cssClass="dropdown-item" value="${elt.id}">
					${elt.value} 
				</option> 
			 </c:forEach>
		</select>
	</div> 
</c:if>
 
<c:if test="${fn:length(roles) gt 0 }">
	<div class="col-3 d-flex align-items-center justify-content-between" style="padding: 0">
		<select class="border" style="width: 100% ;padding: 11px;" label="" id="roles" name="roles"> 
			<option disabled="true" selected="true" value=""><liferay-ui:message key="Select" /> <liferay-ui:message key="Role" /></option>
			<c:forEach var="elt" items="${roles}">
				<option cssClass="dropdown-item" value="${elt.id}">
					${elt.value} 
				</option> 
			 </c:forEach>
		</select>
	</div>
</c:if>
<c:if test="${fn:length(gender) gt 0 }">
	<div class="col-3 d-flex align-items-center justify-content-between" style="padding: 0">
		<select class="border" style="width: 100% ;padding: 11px;" label="" id="gender" name="gender"> 
			<option disabled="true" selected="true" value=""><liferay-ui:message key="Select" /> <liferay-ui:message key="Gender" /></option>
			<c:forEach var="elt" items="${gender}">
				<option cssClass="dropdown-item" value="${elt.Id}">
					${elt.Text} 
				</option> 
			 </c:forEach>
		</select>
	</div>
	</div> <!-- END row -->
</c:if>

<!-- FOR ALL FILTER BUTTONS -->
<c:choose>
	<c:when test = "${fn:length(OppIndustType) gt 0  || fn:length(ThreadType) gt 0|| fn:length(SuccessStoryType) gt 0
		 || fn:length(PublicationType) gt 0 || fn:length(InnovationChallengeChallengeType) gt 0 || fn:length(NewsType) gt 0
		 || fn:length(country) gt 0 || fn:length(profession) gt 0 || fn:length(roles) gt 0 ||  fn:length(gender) gt 0}">
		<div class="row mb-2" style="justify-content: end;">
			<div class="download-wrapper d-flex align-items-center justify-content-center col-4 border" onClick="filter()">
				<p style="margin: 0 auto"><liferay-ui:message key="Filter" /></p>
			</div>
			<div class="download-wrapper d-flex align-items-center justify-content-center col-4 border" onClick="reset()">
				<p style="margin: 0 auto"><liferay-ui:message key="Reset" /></p>
			</div>
			<div class="download-wrapper d-flex align-items-center justify-content-center col-4 border"
					onClick="exportCSVFile('${reportName}', '${csvString}')">
				<p style="margin: 0 auto"><liferay-ui:message key="Download" /></p>
			</div>
		</div>
	 </c:when>
	 <c:when test = "${fn:length(MoodleCategs) gt 0}">
			<div class="download-wrapper d-flex align-items-center justify-content-center col-3 border" onClick="filter()">
				<p style="margin: 0 auto"><liferay-ui:message key="Filter" /></p>
			</div>
			<div class="download-wrapper d-flex align-items-center justify-content-center col-3 border" onClick="reset()">
				<p style="margin: 0 auto"><liferay-ui:message key="Reset" /></p>
			</div>
			<div class="download-wrapper d-flex align-items-center justify-content-center col-3 border"
					onClick="exportCSVFile('${reportName}', '${csvString}')">
				<p style="margin: 0 auto"><liferay-ui:message key="Download" /></p>
			</div>
		</div>
	 </c:when>
	 	<c:when test = "${fn:length(OnGoingStage) gt 0 || fn:length(InnovationProgramStage) gt 0 || fn:length(Attendance) gt 0 }">
			<div class="download-wrapper d-flex align-items-center justify-content-center col-2 border" onClick="filter()">
				<p style="margin: 0 auto"><liferay-ui:message key="Filter" /></p>
			</div>
			<div class="download-wrapper d-flex align-items-center justify-content-center col-2 border" onClick="reset()">
				<p style="margin: 0 auto"><liferay-ui:message key="Reset" /></p>
			</div>
			<div class="download-wrapper d-flex align-items-center justify-content-center col-4 border"
					onClick="exportCSVFile('${reportName}', '${csvString}')">
				<p style="margin: 0 auto"><liferay-ui:message key="Download" /></p>
			</div>
		</div>
	 </c:when>
	 	<c:when test = "${fn:length(OnGoingStage) gt 0 || fn:length(InnovationProgramStage) gt 0 || fn:length(Attendance) gt 0 || fn:length(MoodleCategs) gt 0 }">
			<div class="download-wrapper d-flex align-items-center justify-content-center col-2 border" onClick="filter()">
				<p style="margin: 0 auto"><liferay-ui:message key="Filter" /></p>
			</div>
			<div class="download-wrapper d-flex align-items-center justify-content-center col-2 border" onClick="reset()">
				<p style="margin: 0 auto"><liferay-ui:message key="Reset" /></p>
			</div>
			<div class="download-wrapper d-flex align-items-center justify-content-center col-4 border"
					onClick="exportCSVFile('${reportName}', '${csvString}')">
				<p style="margin: 0 auto"><liferay-ui:message key="Download" /></p>
			</div>
		</div>
	 </c:when>
	 <c:otherwise>
		 <div class="row mb-2" style="justify-content: end;">
			<div class="download-wrapper d-flex align-items-center justify-content-center col-1 border" onClick="filter()">
				<p style="margin: 0 auto"><liferay-ui:message key="Filter" /></p>
			</div>
			<div class="download-wrapper d-flex align-items-center justify-content-center col-1 border" onClick="reset()">
				<p style="margin: 0 auto"><liferay-ui:message key="Reset" /></p>
			</div>
			<div class="download-wrapper d-flex align-items-center justify-content-center col-2 border"
					onClick="exportCSVFile('${reportName}', '${csvString}')">
				<p style="margin: 0 auto"><liferay-ui:message key="Download" /></p>
			</div>
		</div>
	 </c:otherwise>
</c:choose>

<script>
	var _searchParams = new URLSearchParams(location.search);
	var sortField = _searchParams.get('sortField') || "createDate";
	var sortKey = _searchParams.get('sort') || "desc";
	
	function showMore(tableName, btn) {
		var $rows = $('#'+ tableName +' tr.' + btn);
		var lastActiveIndex = $rows.filter('.shown:last').index() +1;
		$rows.filter(':lt(' + (lastActiveIndex + 4) + ')').removeClass('hidden');
		$rows.filter(':lt(' + (lastActiveIndex + 4) + ')').addClass('shown'); 
		
		if($rows.length == lastActiveIndex){
			$("#"+tableName+"Div").addClass('hidden');
			$("#"+btn).addClass('hidden');
		}
	}
	
	$('#startDate').on('change', function(e){
		var startDate = $(this).val();
		$('#endDate').attr('min', startDate);
	});
	$('#endDate').on('change', function(e){
		var endDate = $(this).val();
		$('#startDate').attr('max', endDate);
	});
	
	var filterData = JSON.parse(_searchParams.get('filter') || '{}');
	if(!jQuery.isEmptyObject(filterData)){
		$('#startDate').val(filterData.startDate);
		$('#endDate').val(filterData.endDate);
		$('#ThreadType').val(filterData.ThreadType);
		$('#SuccessStoryType').val(filterData.SuccessStoryType);
		$('#InnovationChallengeChallengeType').val(filterData.InnovationChallengeChallengeType);
		$('#OppIndustType').val(filterData.OppIndustType);
		$('#NewsType').val(filterData.NewsType);
		$('#EventType').val(filterData.EventType);
		$('#Attendance').val(filterData.Attendance);
		$('#PublicationType').val(filterData.PublicationType);
		$('#InnovationProgramActivityType').val(filterData.InnovationProgramActivityType);
		$('#InnovationProgramStage').val(filterData.InnovationProgramStage);
		$('#OnGoingCateg').val(filterData.OnGoingCateg);
		$('#OnGoingStage').val(filterData.OnGoingStage);
		$('#MoodleCategs').val(filterData.MoodleCategs);
		$('#country').val(filterData.country);
		$('#profession').val(filterData.profession);
		$('#roles').val(filterData.roles);
		$('#gender').val(filterData.gender);
	}
		
	function filter(){
		var today = new Date();
		var dd = String(today.getDate()).padStart(2, '0');
		var mm = String(today.getMonth() + 1).padStart(2, '0'); 
		var yyyy = today.getFullYear(); 
		today = yyyy + '-' + dd + '-' + mm;
		  
		var startDate = [], endDate = [], ThreadType = [], SuccessStoryType = [], InnovationChallengeChallengeType = [], 
			OppIndustType = [], NewsType = [], EventType = [], Attendance = [], PublicationType = [], 
			InnovationProgramActivityType = [], InnovationProgramStage = [], OnGoingCateg = [], OnGoingStage = [],
			MoodleCategs = [], country = [], profession = [], roles = [], gender = [];
		
		startDate.push($("#startDate").val() == ''? '1970-01-01' : $("#startDate").val());
		endDate.push($("#endDate").val() == ''? today : $("#endDate").val());
		ThreadType.push($("#ThreadType").val());
		SuccessStoryType.push($("#SuccessStoryType").val());
		InnovationChallengeChallengeType.push($("#InnovationChallengeChallengeType").val());
		OppIndustType.push($("#OppIndustType").val());
		NewsType.push($("#NewsType").val());
		EventType.push($("#EventType").val());
		Attendance.push($("#Attendance").val());
		PublicationType.push($("#PublicationType").val());
		InnovationProgramActivityType.push($("#InnovationProgramActivityType").val());
		InnovationProgramStage.push($("#InnovationProgramStage").val());
		OnGoingCateg.push($("#OnGoingCateg").val());
		OnGoingStage.push($("#OnGoingStage").val());
		MoodleCategs.push($("#MoodleCategs").val());
		country.push($("#country").val());
		profession.push($("#profession").val());
		roles.push($("#roles").val());
		gender.push($("#gender").val());
		
		filterData = {};
		if( startDate.length && startDate != undefined )  filterData.startDate = startDate;
		if( endDate.length  && endDate != undefined )  filterData.endDate = endDate;
		if( ThreadType.length  && ThreadType[0] != undefined )  filterData.ThreadType = ThreadType;
		if( SuccessStoryType.length  && SuccessStoryType[0] != undefined )  filterData.SuccessStoryType = SuccessStoryType;
		if( InnovationChallengeChallengeType.length  && InnovationChallengeChallengeType[0] != undefined )  filterData.InnovationChallengeChallengeType = InnovationChallengeChallengeType;
		if( OppIndustType.length  && OppIndustType[0] != undefined )  filterData.OppIndustType = OppIndustType;
		if( NewsType.length  && NewsType[0] != undefined )  filterData.NewsType = NewsType;
		if( Attendance.length  && Attendance[0] != undefined )  filterData.Attendance = Attendance;
		if( EventType.length  && EventType[0] != undefined )  filterData.EventType = EventType;
		if( PublicationType.length  && PublicationType[0] != undefined )  filterData.PublicationType = PublicationType;
		if( InnovationProgramActivityType.length  && InnovationProgramActivityType[0] != undefined )  filterData.InnovationProgramActivityType = InnovationProgramActivityType;
		if( InnovationProgramStage.length  && InnovationProgramStage[0] != undefined )  filterData.InnovationProgramStage = InnovationProgramStage;
		if( OnGoingCateg.length  && OnGoingCateg[0] != undefined )  filterData.OnGoingCateg = OnGoingCateg;
		if( OnGoingStage.length  && OnGoingStage[0] != undefined )  filterData.OnGoingStage = OnGoingStage;
		if( MoodleCategs.length  && MoodleCategs[0] != undefined )  filterData.MoodleCategs = MoodleCategs;
		if( country.length  && country[0] != undefined )  filterData.country = country;
		if( profession.length  && profession[0] != undefined )  filterData.profession = profession;
		if( roles.length  && roles[0] != undefined )  filterData.roles = roles;
		if( gender.length  && gender[0] != undefined )  filterData.gender = gender;
		
		getData();
	}
	
	function getData() {
		var searchParams = new URLSearchParams(location.search);
		searchParams.set('filter', JSON.stringify(filterData));
		searchParams.set('sortField', sortField);
		searchParams.set('sort', sortKey);
		location.href = location.origin+location.pathname+'?'+searchParams.toString();
	}
	
	function exportCSVFile(fileTitle, csv) {
		csv = csv.replaceAll("___newLine___", "\r\n");
	    var exportedFilenmae = fileTitle + '.csv' || 'export.csv';
	    var blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
	    if (navigator.msSaveBlob) {
	        navigator.msSaveBlob(blob, exportedFilenmae);
	    } else {
	        var link = document.createElement("a");
	        if (link.download !== undefined) {
	            var url = URL.createObjectURL(blob);
	            link.setAttribute("href", url);
	            link.setAttribute("download", exportedFilenmae);
	            link.style.visibility = 'hidden';
	            document.body.appendChild(link);
	            link.click();
	            document.body.removeChild(link);
	        }
	    }
	} 
	
	function reset() {
		filterData = JSON.parse('{}');
		getData();
	}
	
	function sortTable(title) {
		sortKey = sortKey=='asc' ? 'desc' : 'asc';
		sortField = title; 
		getData(); 
	}
	
//  WORKING IN REGULAR TABLES (NOT GROUPED BY)
// 	function sortTable() {
//     	var table = $('#tableFilter');
// 	    var rows = table.find('tr:gt(0)').toArray().sort(comparer($(this).index()))
// 	    var firstRow = table.find('tr:not("[class*=shown]"):gt(0)')
// 	    this.asc = !this.asc
// 	    if (!this.asc)
// 	    	rows = rows.reverse()
	    	
//     	table.append(firstRow)
// 	    for (var i = 0; i < rows.length; i++){
// 	    	if(rows[i].classList.contains('shown'))
// 	    		table.append(rows[i])
//     	}
// 	}
	
// 	function comparer(index) {
// 	    return function(a, b) {
// 	        var valA = getCellValue(a, index), valB = getCellValue(b, index)
// 	        return $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB : valA.toString().localeCompare(valB)
// 	    }
// 	}
// 	function getCellValue(row, index){
// 		return $(row).children('td').eq(index).text();
// 	}

</script>