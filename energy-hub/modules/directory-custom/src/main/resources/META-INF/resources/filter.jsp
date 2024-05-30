<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="btn-group" class="d-flex justify-content-end">
	<button type="button" id='btnFilter' class="modal-button" data-bs-toggle="dropdown" 
		data-bs-auto-close="false" aria-expanded="false"><liferay-ui:message key="Filter" /><span class='img_Nav'></span>
	</button>
	<div class="dropdown-menu dropdown-menu-filter" aria-labelledby="btnFilter">
		<c:if test="${fn:length(Degreetype) gt 0 }">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="DegreeType" /></span>
				<table class="tbl">
					<c:forEach var="degreetype" items="${Degreetype}" varStatus="loop">
						<tr>
							<td>
							<label class="container"><liferay-ui:message key="${degreetype.value}" />
								<input type="checkbox" name="Degreetype" value="${degreetype.id}">
								<span class="checkmark"></span>
							</label>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="dropdown-divider"></div>
		</c:if>
		<c:if test="${fn:length(Attendance) gt 0 }">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="Attendance" /></span>
				<table class="tbl">
					<tr>
						<c:forEach var="attendance" items="${Attendance}" varStatus="loop">
							<c:if test="${loop.index!=0 && loop.index%2==0}">
					</tr>
					<tr>
						</c:if>
						<td>
							<label class="container"><liferay-ui:message key="${attendance.value}" /> 
								<input type="checkbox" name="Attendance" value="${attendance.id}">
								<span class="checkmark"></span>
							</label>
						</td>
						</c:forEach>
					</tr>
				</table>
			</div> 
		</c:if>
		<c:if test="${fn:length(OrganizationType) gt 0 }">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="Type" /></span>
				<table class="tbl">
					<c:forEach var="orgtype" items="${OrganizationType}" varStatus="loop">
						<tr>
							<td>
								<label class="container"><liferay-ui:message key="${orgtype.value}" /> 
									<input type="checkbox" name="OrgType" value="${orgtype.id}">
									<span class="checkmark"></span>
								</label>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:if>
		<c:if test="${fn:length(mainBusinessActivity) gt 0 }">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="MainBusinessActivity" /></span>
				<table class="tbl" id="MainBusinessActivity">
					<c:forEach var="act" items="${mainBusinessActivity}" begin="0" varStatus="loop">
						<c:if test="${loop.index lt 4 }">
							<tr class="shown">
						</c:if>
						<c:if test="${loop.index gt 4 }">
							<tr class="hidden">
						</c:if>
							<td>
							<label class="container"><liferay-ui:message key="${act.value}" /> 
								<input type="checkbox" name="mainBusinessActivity" value="${act.id}">
								<span class="checkmark"></span>
							</label>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="d-flex justify-content-start" id="MainBusinessActivityDiv">
					<a  href="javascript:;" onclick="showMore('MainBusinessActivity')"><liferay-ui:message key="Show4More" />
						<span class="arrow">
							<img src="/o/energy-hub-theme/images/arrow.svg">
						</span>
					</a>
				</div>
			</div>
			<div class="dropdown-divider"></div>
		</c:if>
		<c:if test="${fn:length(kaza) gt 0 }">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="Caza" /></span>
				<table class="tbl" id="Caza">
					<c:if test="${loop.index lt 4 }">
						<tr class="shown">
					</c:if>
					<c:if test="${loop.index gt 4 }">
						<tr class="hidden">
					</c:if>
						<c:forEach var="kazaelt" items="${kaza}" varStatus="loop">
							<c:if test="${loop.index!=0 && loop.index%2==0}">
								</tr>
								<c:if test="${loop.index lt 4 }">
									<tr class="shown">
								</c:if>
								<c:if test="${loop.index gt 4 }">
									<tr class="hidden">
								</c:if>
						</c:if>
						<td>
							<label class="container"><liferay-ui:message key="${kazaelt.value}" /> 
								<input type="checkbox" name="kaza" value="${kazaelt.id}">
								<span class="checkmark"></span>
							</label>
						</td>
						</c:forEach>
					</tr>
				</table>
				<div class="d-flex justify-content-start" id="CazaDiv">
					<a  href="javascript:;" onclick="showMore('Caza')"><liferay-ui:message key="Show4More" />
						<span class="arrow">
							<img src="/o/energy-hub-theme/images/arrow.svg">
						</span>
					</a>
				</div>
			</div>
			<div class="dropdown-divider"></div>
		</c:if>
		<c:if test="${fn:length(numberOfEmployees) gt 0 }">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="NumberOfEmployees" /></span>
				<table class="tbl">
					<tr>
						<c:forEach var="emp" items="${numberOfEmployees}" varStatus="loop">
							<c:if test="${loop.index!=0 && loop.index%2==0}">
					</tr>
					<tr>
						</c:if>
						<td>
							<label class="container"><liferay-ui:message key="${emp.value}" /> 
								<input type="checkbox" name="numberOfEmployees" value="${emp.id}">
								<span class="checkmark"></span>
							</label>
						</td>
						</c:forEach>
					</tr>
				</table>
			</div>
		</c:if>
		<c:if test="${fn:length(InnovationType) gt 0 }">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="Type" /></span>
				<table class="tbl">
					<c:forEach var="innovation" items="${InnovationType}" varStatus="loop">
						<tr>
							<td>
								<label class="container"><liferay-ui:message key="${innovation.value}" /> 
									<input type="checkbox" name="InnovationType" value="${innovation.id}">
									<span class="checkmark"></span>
								</label>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:if>
		<c:if test="${fn:length(Mohafaza) gt 0 }">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="Mohafaza" /></span>
				<table class="tbl">
					<tr>
						<c:forEach var="moh" items="${Mohafaza}" varStatus="loop">
							<c:if test="${loop.index!=0 && loop.index%2==0}">
					</tr>
					<tr>
						</c:if>
						<td>
							<label class="container"><liferay-ui:message key="${moh.value}" /> 
								<input type="checkbox" name="Mohafaza" value="${moh.id}">
								<span class="checkmark"></span>
							</label>
						</td>
						</c:forEach>
					</tr>
				</table>
			</div>
		</c:if>
		<div>
			<div class="d-flex justify-content-end fixBtns mt-5 mb-3">
				<button type="button" class="reset mx-3" id="reset" data-dismiss="modal"><liferay-ui:message key="Reset" /></button>
				<button type="button" id="applyFilter" class="blueBtn  submit"><liferay-ui:message key="Apply" /></button>
			</div>
		</div>
	</div>
</div>
<style>  
 	.hidden {
		display : none !important;
 	}
</style>
<script> 
	function showMore(tableName) {
		var $rows = $('#'+ tableName +' tr');
		var lastActiveIndex = $rows.filter('.shown:last').index() +1;
		$rows.filter(':lt(' + (lastActiveIndex + 4) + ')').removeClass('hidden');
		$rows.filter(':lt(' + (lastActiveIndex + 4) + ')').addClass('shown'); 
		
		if($rows.length == lastActiveIndex)
			$("#"+tableName+"Div").addClass('hidden');
	}
	 
</script>
