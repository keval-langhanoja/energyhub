<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
 
<style>
	.industryDataCollection h2:before {
	    content: '';
	    position: absolute;
	    width: 6px;
	    height: 28px;
	    background: #8AAF29 0% 0% no-repeat padding-box;
	    opacity: 1;
	    left: 0px;
	}
	
	table tr td:last-child,
	table tr td:first-child,
	table tr:last-child td,
	table tr:last-child td:last-child {
    	border: none !important;
	}
	.monitorcard {
		border: 1px solid rgba(0,0,0,.125) !important;
	    border-radius: 0.25rem !important;
	}
</style>
<c:forEach var="publication" items="${publications}"> 
	<div class="industryDataCollection col-12 col-xl-6">
		<h2>${publication.title}</h2>
	</div> 
	<div class="industryDataCollection col-12 col-xl-12">
		<p>${publication.subTitle}</p>
		<p></p>
	</div> 
	<div class="row mt-5">
	    <div class="monitorcard justify-content-between align-items-center py-2 px-3">
	    	<div class="justify-content-between align-items-start" style="display:flex">
		       	<p class="mt-5"><strong><liferay-ui:message key="RenewableEnergySources" /></strong></p>
	    	</div>
	        <div class="px-3 goCheck row mb-5">
	        	<ul class="row m-0">
					<c:forEach var="elt" items="${RenewableEnergySources}" varStatus="loop">
						<li class="col-12 col-xl-4 col-lg-6 col-md-12 col-sm-12 d-flex justify-content-start">
							<input type="checkbox" class="categCheckbox" name="RenewableEnergySources" value="${elt.id}">
		                    <label style="margin: -5px 5px 0px 5px;">${elt.value}</label>
						</li>
					</c:forEach>
				</ul>
	        </div>
	    	<div class="justify-content-between align-items-start" style="display:flex">
		       	<p class="mt-5"><strong><liferay-ui:message key="EnergyEfficiency" /></strong></p>
	    	</div>
	        <div class="px-3 goCheck row mb-5">
	        	<ul class="row m-0">
					<c:forEach var="elt" items="${EnergyEfficiency}" varStatus="loop">
						<li class="col-12 col-xl-4 col-lg-6 col-md-12 col-sm-12 d-flex justify-content-start">
							<input type="checkbox" class="categCheckbox" name="EnergyEfficiency" value="${elt.id}">
		                    <label style="margin: -5px 5px 0px 5px;">${elt.value}</label>
						</li>
					</c:forEach>
				</ul> 
	        </div>
			<button class="blueBtn submit mb-5 post" style="width:auto !important; padding: 0px 15px;" id="submit"><liferay-ui:message key="BrowsePublications" /></button>
	    </div>
	</div>
</c:forEach>

<script>
var _searchParams = new URLSearchParams(location.search);
var filterData = JSON.parse(_searchParams.get('filter') || '{}');

$('#submit').on('click', function () {
	var source = [], efficiency = [];
	$('input[name="RenewableEnergySources"]:checked').each(function () {
		source.push($(this).val());
	});
	$('input[name="EnergyEfficiency"]:checked').each(function () {
		efficiency.push($(this).val());
	});
	
	filterData = {};
	if( source.length ) {
		filterData.source = source;
	} 
	if( efficiency.length ) {
		filterData.efficiency = efficiency;
	}
	getData();
});

function getData() {
	var searchParams = new URLSearchParams(location.search);
	searchParams.set('filter', JSON.stringify(filterData));
	location.href = location.origin+location.pathname+'?publications&'+searchParams.toString();
}
</script>
