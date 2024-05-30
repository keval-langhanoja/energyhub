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
	    width: 200px;
    	margin: 0 auto;
    	min-height: 200px;
    	max-height: 200px;
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
	    max-height: 115px;
	    overflow: hidden;
    }
    .dets {
        flex-direction: column;
    	display: flex;
    }
    .progOverflow {
        display: -webkit-box;
	    -webkit-line-clamp: 3;
    	-webkit-box-orient: vertical;
    	overflow: hidden;
    	text-overflow: ellipsis;
    }
    .descOverflow {
        display: -webkit-box;
	    -webkit-line-clamp: 5;
    	-webkit-box-orient: vertical;
    	overflow: hidden;
    	text-overflow: ellipsis;
    }
    .dropdown-menu[aria-labelledby="btnFilter"] td {
    	white-space: normal;
    }
    .searchBlue {
    	background-color: white;
    }
}
</style>
<div class="row mb-5">
	<div class="dropdown d-flex justify-content-end">
	 	<div class="col-sm-10">
	        <div class="d-flex searchBlue align-items-center">
	            <span><img width="18px" src="/o/energy-hub-theme/images/inputSearch.svg"></span>
	            <input type="text" placeholder="Search" id="queryText">
	        </div>
        </div>
		<jsp:include page="/filter.jsp" />
	</div>
</div>
<div class="row d-flex justify-content-center">
	<div>
		<div class="row">
			<c:forEach var="journalArticle" items="${journalArticleList}" varStatus="loop">
				<c:if test="${loop.index!=0 && loop.index%3==0}">
					</div><div class="row">
				</c:if>
				<div class="col-lg-4 mb-5" style="display: grid;">
					<div class="card card-small">
						<img class="card-img-top card-img item-image" src="${journalArticle.imageURL}"
						onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;">
						<div class="card-body pb-5 mt-2 dets">
							<div class="card-body dets">
								<h5 class="card-title  card-header-title-small">${journalArticle.title}</h5>
								<p class="card-text mt-3 card-details-small descOverflow">${journalArticle.description}</p>
								<c:if test="${journalArticle.counter gt 0}">
									<h5 class="card-title  card-header-title-small"><liferay-ui:message key="EnergyPrograms" /> (${journalArticle.counter})</h5>
									<span class="card-text mt-3 card-details-small progOverflow">${journalArticle.programsTitles}</span>
								</c:if>
							</div>
							<div class="d-flex justify-content-start">
								<a href="${journalArticle.detailURL}" class="readmore mt-3"><liferay-ui:message key="ReadMore" />
									<span class="arrow">
										<img src="/o/energy-hub-theme/images/arrow.svg">
									</span>
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
var queryText = decodeURI(_searchParams.get('queryText') || "");

$('.page-item').on('click', function () {
	var page = $(this).find('a');
	if( page.hasClass('activepage') || page.hasClass('inactive') ) {
		return;
	}
	
	pageNo = page.hasClass('plink') ? parseInt(page.text())-1 : pageNo+(page.hasClass('nextpage') ? 1 : -1);
	getData();
});

$('#queryText').val(queryText);
$('#queryText').on('keyup', function (e) {
	if( e.keyCode == 13 ) {
		queryText = $(this).val();
		pageNo = 0;
		getData();
	}
});

$('#applyFilter').on('click', function () {
	var degreetype = [], attendance = [], orgtype = [],
		mainBusinessActivity = [], kaza = [], numberOfEmployees = [],
		innovationType = [], mohafaza = [];
	
	$('input[name="Degreetype"]:checked').each(function () {
		degreetype.push($(this).val());
	});
	$('input[name="Attendance"]:checked').each(function () {
		attendance.push($(this).val());
	});
	$('input[name="OrgType"]:checked').each(function () {
		orgtype.push($(this).val());
	});
	$('input[name="mainBusinessActivity"]:checked').each(function () {
		mainBusinessActivity.push($(this).val());
	});
	$('input[name="kaza"]:checked').each(function () {
		kaza.push($(this).val());
	});
	$('input[name="numberOfEmployees"]:checked').each(function () {
		numberOfEmployees.push($(this).val());
	});
	$('input[name="InnovationType"]:checked').each(function () {
		innovationType.push($(this).val());
	});
	$('input[name="Mohafaza"]:checked').each(function () {
		mohafaza.push($(this).val());
	});
	
	filterData = {};
	if( degreetype.length ) {
		filterData.degreetype = degreetype;
	}
	if( attendance.length ) {
		filterData.attendance = attendance;
	}
	if( orgtype.length ) {
		filterData.orgtype = orgtype;
	}
	if( mainBusinessActivity.length ) {
		filterData.mainBusinessActivity = mainBusinessActivity;
	}
	if( kaza.length ) {
		filterData.kaza = kaza;
	}
	if( numberOfEmployees.length ) {
		filterData.numberOfEmployees = numberOfEmployees;
	}
	if( innovationType.length ) {
		filterData.innovationType = innovationType;
	}
	if( mohafaza.length ) {
		filterData.mohafaza = mohafaza;
	}
	pageNo = 0;
	getData();
});

function getData() {
	var searchParams = new URLSearchParams(location.search); 
	searchParams.set('pageNo', pageNo);
	searchParams.set('pageSize', pageSize);
	searchParams.set('filter', JSON.stringify(filterData));
	searchParams.set('queryText', encodeURI(queryText));
	location.href = location.origin+location.pathname+'?'+searchParams.toString();
}

$('#reset').on('click', function (e) {
	e.preventDefault(); 
	pageNo = "0";
	pageSize = "6";
	queryText = "";
	filterData = JSON.parse('{}');
	$('.checkmark').css('background-color','white'); 
	getData();
}); 
</script>
