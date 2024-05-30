<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="/o/energy-hub-theme/style/directoryModule.css">
<link rel="stylesheet" href="/o/energy-hub-theme/style/directoryModule-logged.css">

<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>

<style>
	.card-header-title-small {
		color: black;	
	}
	.item-image {
	    width: 350px;
    	margin: 0 auto;
    	min-height: 350px;
    	max-height: 350px;
	}
	.readmore {
	 	font-size: 18px;
	    font-weight: 400;
	    letter-spacing: 1.22px;
	    color: #009BC7 !important;
	    text-decoration: none !important;
	}
	.joinNow:hover span, .searchBtn:hover span, .subscribeBtn:hover span, .readmore:hover span {
	    transform: translateX(5px);
	}
	.card {
        border: 1px solid rgba(0,0,0,.125) !important;
   		border-radius: 0.25rem !important;
	}
	.arrow {
	    display: inline-flex;
	    justify-content: center;
	    align-items: center;
	    width: 17px;
	    height: 17px;
	    background: #2FADD1 0% 0% no-repeat padding-box;
	    box-shadow: 0px 3px 6px #00000029;
	    border-radius: 50%;
    }
    .dets p{
	    max-height: 105px;
	    overflow: hidden;
    }
    .dets {
        flex-direction: column;
    	display: flex;
    }
    .programList {
    	display: none;
    }
    .dets p:last-child,
	.dets p::first-child {
    	display: none;
	}
	#p_p_id_com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet_INSTANCE_OeSSppt4Rf9p_ {
	    display: none;
	}
	.card-dets {
	    -webkit-line-clamp: 10;
	    -webkit-box-orient: vertical;
	    display: -webkit-box;
	    overflow: hidden;
    }
    .item-image {
	    width: 100% !important;
	    margin: 0 auto;
	    min-height: 350px;
	    max-height: 350px;
	}
</style>
<div class="row">
	<div class="dropdown d-flex">
		<h3 style="min-width: 90% !important;"><liferay-ui:message key="RenewableEnergySourcesPublications" /></h3>
		<div class="btn-group"  class="d-flex justify-content-end">
			<button type="button" id='btnFilter' class="modal-button"
				data-bs-toggle="dropdown" data-bs-auto-close="false"
				aria-expanded="false">
				<liferay-ui:message key="Filter" /><span class='img_Nav'></span>
			</button>
			<div class="dropdown-menu dropdown-menu-filter"
				aria-labelledby="btnFilter">
				<div class="divTitle">
					<span class="title"><liferay-ui:message key="RenewableEnergySources" /></span>
					<table class="tbl">
						<tr>
							<c:forEach var="source" items="${RenewableEnergySources}"
								varStatus="loop">
								<c:if test="${loop.index!=0 && loop.index%2==0}">
						</tr>
						<tr>
							</c:if>
							<td><label class="container"><liferay-ui:message key="${source.value}" /> 
							<input type="checkbox" name="RenewableEnergySources"
									value="${source.id}"> <span class="checkmark"></span>
							</label></td>
							</c:forEach>
						</tr>
					</table>
				</div>
				<div class="dropdown-divider"></div>
				<div class="divTitle">
					<span class=" title"><liferay-ui:message key="EnergyEfficiency" /></span>
					<table class="tbl">
						<tr>
							<c:forEach var="efficiency" items="${EnergyEfficiency}"
								varStatus="loop">
								<c:if test="${loop.index!=0 && loop.index%2==0}">
						</tr>
						<tr>
							</c:if>
							<td><label class="container"><liferay-ui:message key="${efficiency.value}" />  <input
									type="checkbox" name="EnergyEfficiency"
									value="${efficiency.id}"> <span class="checkmark"></span>
							</label></td>
							</c:forEach>
						</tr>
					</table>
				</div>
				<div class="dropdown-divider"></div>
				<div class="divTitle">
					<span class="title"><liferay-ui:message key="Date" /></span>
					<table class="tbl">
						<tr>
							<td><label class="container"><liferay-ui:message key="FromWeekAgo" /> <input
									type="checkbox" name="projectDate" value="week"> <span
									class="checkmark"></span>
							</label></td>
							<td><label class="container"><liferay-ui:message key="MoreThanWeek" /> <input
									type="checkbox" name="projectDate" value="more"> <span
									class="checkmark"></span>
							</label></td>
						</tr>
						<tr>
							<td><label class="container"><liferay-ui:message key="LastMonth" /> <input
									type="checkbox" name="projectDate" value="month"> <span
									class="checkmark"></span>
							</label></td>
						</tr>
						<tr>
							<td colspan="2"><label class="container"><liferay-ui:message key="RecentlyAdded" />
								<input type="checkbox" name="projectDate" value="recent">
									<span class="checkmark"></span>
							</label></td>
						</tr>
					</table>
				</div>
				<div>
					<div class="d-flex justify-content-end fixBtns mt-5 mb-3">
						<button type="button" class="reset mx-3" id="reset" data-dismiss="modal"><liferay-ui:message key="Reset" /></button>
						<button type="button" id="applyFilter" class="blueBtn submit"><liferay-ui:message key="Apply" /></button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="row d-flex justify-content-center mt-5">
	<div>
		<div class="row">
			<c:forEach var="journalArticle" items="${publications}" varStatus="loop">
				<c:if test="${loop.index!=0 && loop.index%3==0}">
					</div><div class="row">
				</c:if>
				<div class="col-lg-4 mb-5" style="display: grid;">
					<div class="card card-small">
						<img class="card-img-top card-img item-image" src="${journalArticle.imageURL}"
						onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;">
						<div class="card-body">
							<h5 class="card-title  card-header-title-small">${journalArticle.title}</h5>
							<div class="card-text mt-3 card-dets card-details-small card-text-recent">${journalArticle.description}</div>
							<div class="d-flex justify-content-start">
								<a href="${journalArticle.detailURL}" class="readmore mt-3"><liferay-ui:message key="ReadMore" />
									<div class="arrow">
										<img src="/o/energy-hub-theme/images/arrow.svg">
									</div>
								</a>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="d-flex justify-content-end mt-5">
	        <nav aria-label="Page navigation example ">
	            <ul class="pagination">
	                <li class="page-item">
	                    <a class="page-link prevpage leftArrow ${pageNo==0 ? 'inactive' : 'active' }" href="#" aria-label="Previous">
	                        <span aria-hidden="true"></span>
	                    </a>
	                </li>
	                <c:forEach begin="0" end="${totalPages}" varStatus="loop">
		                <li class="page-item"><a class="page-link plink ${loop.index==pageNo?'activepage':''}" href="#">${loop.index+1}</a></li>
	                </c:forEach>
	                
	                <li class="page-item">
	                    <a class="page-link nextpage rightArrow ${pageNo==(totalPages) ? 'inactive' : 'active' }" href="#" aria-label="Next">
	                        <span aria-hidden="true"></span>
	                    </a>
	                </li>
	            </ul>
	        </nav>
	    </div>
	</div>
</div>

<script>
var _searchParams = new URLSearchParams(location.search);
var pageNo = ${pageNo};
var pageSize = ${pageSize};
var filterData = JSON.parse(_searchParams.get('filter') || '{}');

$('.page-item').on('click', function () {
	var page = $(this).find('a');
	if( page.hasClass('activepage') || page.hasClass('inactive') ) {
		return;
	}
	
	pageNo = page.hasClass('plink') ? parseInt(page.text())-1 : pageNo+(page.hasClass('nextpage') ? 1 : -1);
	getData();
});

$('#applyFilter').on('click', function () {
	var source = [], date = [], efficiency = [];
	$('input[name="RenewableEnergySources"]:checked').each(function () {
		source.push($(this).val());
	});
	$('input[name="projectDate"]:checked').each(function () {
		date.push($(this).val());
	});
	$('input[name="EnergyEfficiency"]:checked').each(function () {
		efficiency.push($(this).val());
	});
	
	filterData = {};
	if( source.length ) {
		filterData.source = source;
	}
	if( date.length ) {
		filterData.date = date;
	}
	if( efficiency.length ) {
		filterData.efficiency = efficiency;
	}
	pageNo = 0;
	getData();
});

function getData() {
	var searchParams = new URLSearchParams(location.search); 
	searchParams.set('pageNo', pageNo);
	searchParams.set('pageSize', pageSize);
	searchParams.set('filter', JSON.stringify(filterData));
	location.href = location.origin+location.pathname+'?'+searchParams.toString();
}

$('#reset').on('click', function (e) {
	e.preventDefault();
	filterData = JSON.parse('{}');
	pageNo = "0";
	pageSize = "9";
	$('.checkmark').css('background-color','white'); 
	getData();
}); 
</script>
