<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="btn-group">
	<button type="button" id='btnFilter' class="modal-button" data-bs-toggle="dropdown" data-bs-auto-close="false" aria-expanded="false">
		<liferay-ui:message key="Filter" /><span class='img_Nav'></span>
	</button>
	<div class="dropdown-menu dropdown-menu-filter seeAllFilter" aria-labelledby="btnFilter">
		<c:if test="${fn:length(projectCategList) gt 0 }">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="ProjectCategory" /></span>
				<table class="tbl">
					<tr>
						<c:forEach var="projectCateg" items="${projectCategList}" varStatus="loop">
							<c:if test="${loop.index!=0 && loop.index%2==0}">
								</tr><tr>
							</c:if>
							<td><label class="container">${projectCateg.value}
							   <input type="checkbox" name="projectCategory" value="${projectCateg.id}">
							   <span class="checkmark"></span>
						   </label></td>
						</c:forEach>
					</tr>
				</table>
			</div>
			<div class="dropdown-divider"></div>
		</c:if>
		<c:if test="${showDateFilter}">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="Time" /></span>
				<table class="tbl">
					<tr>
						<td>
							<label class="container"><liferay-ui:message key="FromWeekAgo" />
								<input type="checkbox" name="projectDate" value="week">
								<span class="checkmark"></span>
							</label>
						</td>
						<td>
							<label class="container"><liferay-ui:message key="MoreThanWeek" />
								<input type="checkbox" name="projectDate" value="more">
								<span class="checkmark"></span>
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="container"><liferay-ui:message key="LastMonth" />
								<input type="checkbox" name="projectDate" value="month">
								<span class="checkmark"></span>
							</label>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<label class="container"><liferay-ui:message key="RecentlyAdded" />
								<input type="checkbox" name="projectDate" value="recent">
								<span class="checkmark"></span>
							</label>
						</td>
					</tr>
				</table>
			</div>
			<div class="dropdown-divider"></div>
		</c:if>
		<c:if test="${fn:length(projectStageList) gt 0 }">
			<div class="divTitle">
				<span class=" title"><liferay-ui:message key="ProjectStage" /></span>
				<table class="tbl">
				   <tr>
						<c:forEach var="projectStage" items="${projectStageList}" varStatus="loop">
							<c:if test="${loop.index!=0 && loop.index%2==0}">
								</tr><tr>
							</c:if>
							<td><label class="container">${projectStage.value}
								<input type="checkbox" name="projectStage" value="${projectStage.id}">
								<span class="checkmark"></span>
							</label></td>
					   </c:forEach>
				   </tr>
			   </table>
			</div>
		</c:if>
		<c:if test="${fn:length(Type) gt 0 }">
			<div class="divTitle">
				<span class=" title"><liferay-ui:message key="Type" /></span>
				<table class="tbl">
				   <tr>
						<c:forEach var="elt" items="${Type}" varStatus="loop">
							<c:if test="${loop.index!=0 && loop.index%2==0}">
								</tr><tr>
							</c:if>
							<td><label class="container"><liferay-ui:message key="${elt.value} " /> 
								<input type="checkbox" name="oppType" value="${elt.id}">
								<span class="checkmark"></span>
							</label></td>
					   </c:forEach>
				   </tr>
			   </table>
			</div>
		</c:if>
		<c:if test="${fn:length(DataToolType) gt 0 }">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="Type" /></span>
				<table class="tbl">
					<tr>
						<c:forEach var="elt" items="${DataToolType}" varStatus="loop">
							<c:if test="${loop.index!=0 && loop.index%2==0}">
								</tr><tr>
							</c:if>
							<td><label class="container"><liferay-ui:message key="${elt.value} " /> 
							   <input type="checkbox" name="DataToolType" value="${elt.id}">
							   <span class="checkmark"></span>
						   </label></td>
						</c:forEach>
					</tr>
				</table>
			</div>
			<div class="dropdown-divider"></div>
		</c:if>
		<c:if test="${fn:length(RenewableEnergySources) gt 0 }">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="RenewableEnergySources" /></span>
				<table class="tbl">
					<tr>
						<c:forEach var="elt" items="${RenewableEnergySources}" varStatus="loop">
							<c:if test="${loop.index!=0 && loop.index%2==0}">
								</tr><tr>
							</c:if>
							<td><label class="container"><liferay-ui:message key="${elt.value} " /> 
							   <input type="checkbox" name="RenewableEnergySources" value="${elt.id}">
							   <span class="checkmark"></span>
						   </label></td>
						</c:forEach>
					</tr>
				</table>
			</div>
			<div class="dropdown-divider"></div>
		</c:if>
		<c:if test="${fn:length(EnergyEfficiency) gt 0 }">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="EnergyEfficiency" /></span>
				<table class="tbl">
					<tr>
						<c:forEach var="elt" items="${EnergyEfficiency}" varStatus="loop">
							<c:if test="${loop.index!=0 && loop.index%2==0}">
								</tr><tr>
							</c:if>
							<td><label class="container"><liferay-ui:message key="${elt.value} " /> 
							   <input type="checkbox" name="EnergyEfficiency" value="${elt.id}">
							   <span class="checkmark"></span>
						   </label></td>
						</c:forEach>
					</tr>
				</table>
			</div>
			<div class="dropdown-divider"></div>
		</c:if>
		<c:if test="${fn:length(AreaOfExpertise) gt 0 }">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="AreaOfExpertise" /></span>
				<table class="tbl" id="AreaOfExpertise">
					<c:forEach var="act" items="${AreaOfExpertise}" begin="0" varStatus="loop">
						<c:if test="${loop.index lt 4 }">
							<tr class="shown">
						</c:if>
						<c:if test="${loop.index gt 4 }">
							<tr class="hidden">
						</c:if>
							<td>
								<label class="container">${act.value}
									<input type="checkbox" name="AreaOfExpertise" value="${act.id}">
									<span class="checkmark"></span>
								</label>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="d-flex justify-content-start" id="AreaOfExpertiseDiv">
					<a  href="javascript:;" onclick="showMore('AreaOfExpertise')"><liferay-ui:message key="Show4More" />
						<span class="arrow">
							<img src="/o/energy-hub-theme/images/arrow.svg">
						</span>
					</a>
				</div>
			</div>
<!-- 			<div class="dropdown-divider"></div> -->
		</c:if>
		<c:if test="${fn:length(typeOfActivity) gt 0 }">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="TypeOfActivity" /></span>
				<table class="tbl">
					<tr>
						<c:forEach var="elt" items="${typeOfActivity}" varStatus="loop">
							<c:if test="${loop.index!=0 && loop.index%2==0}">
								</tr><tr>
							</c:if>
							<td><label class="container"><liferay-ui:message key="${elt.value} " /> 
							   <input type="checkbox" name="typeofactivity" value="${elt.id}">
							   <span class="checkmark"></span>
						   </label></td>
						</c:forEach>
					</tr>
				</table>
			</div>
			<div class="dropdown-divider"></div>
		</c:if>
		<c:if test="${fn:length(progStage) gt 0 }">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="Stage" /></span>
				<table class="tbl">
					<tr>
						<c:forEach var="elt" items="${progStage}" varStatus="loop">
							<c:if test="${loop.index!=0 && loop.index%2==0}">
								</tr><tr>
							</c:if>
							<td><label class="container"><liferay-ui:message key="${elt.value} " /> 
							   <input type="checkbox" name="progstage" value="${elt.id}">
							   <span class="checkmark"></span>
						   </label></td>
						</c:forEach>
					</tr>
				</table>
			</div>
			<div class="dropdown-divider"></div>
		</c:if>
		<c:if test="${fn:length(challengeTypeList) gt 0 }">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="challengeType" /></span>
				<table class="tbl">
					<tr>
						<c:forEach var="elt" items="${challengeTypeList}" varStatus="loop">
							<c:if test="${loop.index!=0 && loop.index%2==0}">
								</tr><tr>
							</c:if>
							<td><label class="container"><liferay-ui:message key="${elt.value} " /> 
							   <input type="checkbox" name="challengetype" value="${elt.id}">
							   <span class="checkmark"></span>
						   </label></td>
						</c:forEach>
					</tr>
				</table>
			</div>
			<div class="dropdown-divider"></div>
		</c:if>
		<c:if test="${fn:length(FeaturedAppList) gt 0 }">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="Type" /></span>
				<table class="tbl">
					<tr>
						<c:forEach var="elt" items="${FeaturedAppList}" varStatus="loop">
							<c:if test="${loop.index!=0 && loop.index%2==0}">
								</tr><tr>
							</c:if>
							<td><label class="container"><liferay-ui:message key="${elt.value} " /> 
							   <input type="checkbox" name="featuredAppType" value="${elt.id}">
							   <span class="checkmark"></span>
						   </label></td>
						</c:forEach>
					</tr>
				</table>
			</div>
		</c:if>
		<c:if test="${fn:length(carMake) gt 0 }">
			<div class="divTitle">
				<span class="title"><liferay-ui:message key="CarMake" /></span>
				<table class="tbl" id="carMake">
					<tr>
						<c:forEach var="elt" items="${carMake}" begin="0" varStatus="loop">
							<c:if test="${loop.index lt 4 && loop.index!=0 && loop.index%2 eq  0}">
								</tr><tr class="shown">
							</c:if>
							<c:if test="${loop.index gt 4 && loop.index!=0 && loop.index%2 eq  0}">
								</tr><tr class="hidden">
							</c:if>
							<td>
								<label class="container"><liferay-ui:message key="${elt.value} " /> 
								   <input type="checkbox" name="carMake" value="${elt.id}">
								   <span class="checkmark"></span>
							   </label>
						   </td>
						</c:forEach>
					</tr>
				</table>
				<div class="d-flex justify-content-start" id="carMakeDiv">
					<a  href="javascript:;" onclick="showMore('carMake')"><liferay-ui:message key="Show4More" />
						<span class="arrow">
							<img src="/o/energy-hub-theme/images/arrow.svg">
						</span>
					</a>
				</div>
			</div>
		</c:if>
		<c:if test="${showCarsFilter}">
			<div class="divTitle">
				<table class="tbl">
					<tr class="row registerationForm">
						<td class="col-6 col-sm-6">
					 		<div class="inputContainer">
					 			<label class="container" style="display: table-row;"><liferay-ui:message key="PriceRangeFrom" />
					 			</label> 
	                            <input type="text" style="max-width: 150px; height: 45px" name="priceFrom" value="${priceFrom}">
	                            <div class="d-flex justify-content-center spanBorderMobileFilter">
									<div class="mobileFixedFilter">$</div>
								</div>
	                        </div>
						</td>
						<td class="col-6 col-sm-6">
	                        <div class="inputContainer">
	                        	<label class="container" style="display: table-row;"><liferay-ui:message key="PriceRangeTo" />
					 			</label> 
	                            <input type="text" style="max-width: 150px; height: 45px" name="priceTo" value="${priceTo}">
	                            <div class="d-flex justify-content-center spanBorderMobileFilter">
									<div class="mobileFixedFilter">$</div>
								</div>
	                        </div>
						</td>
					</tr> 
					<tr class="row registerationForm">
						<td class="col-6 col-sm-6">
					 		<div class="inputContainer">
					 			<label class="container" style="display: table-row;"><liferay-ui:message key="FuelEfficiency" /> <liferay-ui:message key="From" />
					 			</label> 
	                            <input type="text" style="max-width: 150px; height: 45px" name="fuelFrom" value="${fuelFrom}">
	                            <div class="d-flex justify-content-center spanBorderMobileFilter">
									<div class="mobileFixedFilter">Km</div>
								</div>
	                        </div>
						</td>
						<td class="col-6 col-sm-6">
	                        <div class="inputContainer">
	                        	<label class="container" style="display: table-row;"><liferay-ui:message key="FuelEfficiency" /> <liferay-ui:message key="To" />
					 			</label> 
	                            <input type="text" style="max-width: 150px; height: 45px" name="fuelTo" value="${fuelTo}">
	                            <div class="d-flex justify-content-center spanBorderMobileFilter">
									<div class="mobileFixedFilter">Km</div>
								</div>
	                        </div>
						</td>
					</tr> 
				</table>
			</div>
			<div class="dropdown-divider"></div>
		</c:if>
		<div>
			<div
				class="d-flex justify-content-end fixBtns mt-5 mb-3">
				<button type="button" class="reset mx-3" id="reset" data-dismiss="modal"><liferay-ui:message key="Reset" /></button>
				<button type="button" id="applyFilter" class="blueBtn submit"><liferay-ui:message key="Apply" /></button>
			</div>
		</div>
	</div>
</div>
<style>  
 	.hidden {
		display : none !important;
 	}
 	
 	.registerationForm .mobileFixedFilter {
	    position: absolute;
	    display: block;
	    left: 60px;
	    top: 10px;
	    z-index: 9;
	    font-size: 16px;
	    font-weight: bold;
	    letter-spacing: 0px;
	    color: #707070;
	}
	
	.registerationForm .spanBorderMobileFilter {
	    border-right: 0.5px solid #989898;
	    padding-right: 2px;
	    height: 45px;
	    width: 50px;
	    position: absolute;
	    left: 60px;
	    top: 22px;
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
