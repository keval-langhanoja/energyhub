<%@ include file="/init.jsp" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
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
}
</style>
<div class="row mt-5">
   <div class="programsFlex d-flex align-items-center justify-content-between">
		<h1 class="innovationTitle mb-2">${categNameTitle}</h1>
			<div class="d-flex contentt">
				<c:if test="${!seeAllUserRole}">
					<c:if test="${!carsCateg}">
						<div class="d-inline-block" style="text-align: center;">
							<button type="button" id='btnSort' class="modal-button">
								<liferay-ui:message key="Sort" /><span class='img_Nav'></span>
							</button>
						</div>
					</c:if>
					<c:if test="${carsCateg}">
						<div class="d-inline-block" style="text-align: center;">
							<button type="button" id='btnSortPrice' style="width: auto" class="modal-button">
								<liferay-ui:message key="SortPrice" /><span class='img_Nav'></span>
							</button>
						</div>
						<div class="d-inline-block" style="text-align: center;">
							<button type="button" id='btnSortEfficiency' style="width: auto" class="modal-button">
								<liferay-ui:message key="SortEfficiency" /><span class='img_Nav'></span>
							</button>
						</div>
					</c:if>
				</c:if>
				<div class="d-inline-block" style="text-align: center;">
					<jsp:include page="/filter.jsp" />
				</div>
			<c:if test="${is_signed_in && categoryId == '57094'}">
				<button class="add mb-2" onclick="location.href = '/add-project?p_r_p_categoryId=${categoryId}&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}';">
					<span class="plus">
						<img class="mb-1" src="/o/energy-hub-theme/images/add.svg">
					</span>
					<span><liferay-ui:message key="Add" /></span>
				</button>
			</c:if>
		</div> 
	</div>
	
	<c:if test="${carDealerUserRole}">
		<c:forEach var="journalArticle" items="${usersByRole}" varStatus="loop">
				<c:if test="${loop.index!=0 && loop.index%3==0}">
					</div><div class="row">
				</c:if>
				<div class="col-lg-4 mb-5" style="display: grid;">
					<div class="card card-small">
						<img class="card-img-top card-img item-image mt-4" src="${journalArticle.imageUrl}"
						onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;">
						<div class="card-body pb-5 mt-2 dets">
							<div class="card-body dets">
								<h5 class="card-title  card-header-title-small">${journalArticle.company}</h5>
								<a class="card-title" href="//${journalArticle.companyWebsite}">${journalArticle.companyWebsite}</a>
								<span class="programBasicCard mt-3 progOverflow">${journalArticle.carMake}</span>
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
	</c:if>
	<c:if test="${carsCateg}">
		<c:forEach var="journalArticle" items="${programs}" varStatus="loop">
				<c:if test="${loop.index!=0 && loop.index%3==0}">
					</div><div class="row">
				</c:if>
				<div class="col-lg-4 mb-5" style="display: grid;">
					<div class="card card-small">
						<img class="card-img-top card-img item-image mt-4" src="${journalArticle.imageURL}"
						onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;">
						<div class="card-body pb-5 mt-2 dets">
							<div class="card-body dets">
								<h5 class="card-title  card-header-title-small">${journalArticle.title}</h5>
								<span class="programBasicCard mt-3 progOverflow">${journalArticle.description}</span>
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
	</c:if>
	<c:if test="${seeAllUserRole}">
		<c:forEach var="journalArticle" items="${usersByRole}" varStatus="loop">
				<c:if test="${loop.index!=0 && loop.index%3==0}">
					</div><div class="row">
				</c:if>
				<div class="col-lg-4 mb-5" style="display: grid;">
					<div class="card card-small">
						<img class="card-img-top card-img item-image mt-4" src="${journalArticle.imageURL}"
						onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;">
						<div class="card-body pb-5 mt-2 dets">
							<div class="card-body dets">
								<h5 class="card-title  card-header-title-small">${journalArticle.firstName} ${journalArticle.lastName}</h5>
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
	</c:if>
	<c:if test="${!seeAllUserRole && !carsCateg && !carDealerUserRole}">
		<c:forEach var="journalArticle" items="${programs}">
			<div class="col-12 col-xl-6 mt-4">
				<a href="/program-detail?categName=${categName}&groupId=${groupId}&articleId=${journalArticle.resourcePrimKey}" class="card innovationCard grid">
					<div class="programImageContainer">
						<div class="programCardImage" style="background: url('${journalArticle.imageURL}');"></div>
					</div>
					<div class=" cardDetails py-4">
						<h2 class="cardTitle">${journalArticle.title}</h2>
						<div class="mt-3 mb-4 programBasicCard">${journalArticle.description}</div>
					</div>
					<c:if test="${journalArticle.category != ''}">
						<div class="category">
							<span class="grayText px-2"><liferay-ui:message key="${journalArticle.category}" /></span>
						</div>
					</c:if>
				</a> 
			</div> 
		</c:forEach>
	</c:if>
	<c:if test="${seeAllUserRole && otherTypes}">
		<c:forEach var="journalArticle" items="${programs}">
			<div class="col-12 col-xl-6 mt-4">
				<a href="/program-detail?categName=${categName}&groupId=${groupId}&articleId=${journalArticle.resourcePrimKey}" class="card innovationCard grid">
					<div class="programImageContainer">
						<div class="programCardImage" style="background: url('${journalArticle.imageURL}');"></div>
					</div>
					<div class=" cardDetails py-4">
						<h2 class="cardTitle">${journalArticle.title}</h2>
						<div class="mt-3 mb-4 programBasicCard">${journalArticle.description}</div>
					</div>
					<c:if test="${journalArticle.category != ''}">
						<div class="category">
							<span class="grayText px-2"><liferay-ui:message key="${journalArticle.category}" /></span>
						</div>
					</c:if>
				</a> 
			</div> 
		</c:forEach>
	</c:if>
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
<script>
	var _searchParams = new URLSearchParams(location.search);
	var sortField = _searchParams.get('sortField') || "createDate";
	var queryText = decodeURI(_searchParams.get('queryText') || "");
	var sortKey = _searchParams.get('sort') || "desc";
	var filterData = JSON.parse(_searchParams.get('filter') || '{}');
	var pageNo = ${pageNo};
	var pageSize = ${pageSize};
	
	$('.page-item').on('click', function () {debugger;
		var page = $(this).find('a');
		if( page.hasClass('activepage') || page.hasClass('inactive') ) {
			return;
		}
		
		pageNo = page.hasClass('plink') ? parseInt(page.text())-1 : pageNo+(page.hasClass('nextpage') ? 1 : -1);
		getData();
	});
	
// 	$('.page-link').on('click', function () {debugger;
// 		var page = $(this).find('a');
// 		if( page.hasClass('activepage') || page.hasClass('inactive') ) {
// 			return;
// 		}
		
// 		pageNo = page.hasClass('plink') ? parseInt(page.text())-1 : pageNo+(page.hasClass('nextpage') ? 1 : -1);
// 		getData();
// 	});
	
	$('#queryText').val(queryText);
	
	$('#queryText').on('keyup', function (e) {
		if( e.keyCode == 13 ) {
			queryText = $(this).val();
			pageNo = 0;
			getData();
		}
	});
	
	$('#btnSort').on('click', function () {
		sortKey = sortKey=='asc' ? 'desc' : 'asc';
		pageNo = 0;
		getData();
	}); 
	$('#btnSortPrice').on('click', function () {
		sortKey = sortKey=='asc' ? 'desc' : 'asc';
		sortField = 'priceRangeFrom';
		pageNo = 0;
		getData();
	});
	$('#btnSortEfficiency').on('click', function () {
		sortKey = sortKey=='asc' ? 'desc' : 'asc';
		sortField = 'fuelEfficiency';
		pageNo = 0;
		getData();
	});
	
	$('#applyFilter').on('click', function () {
		var category = [], date = [], stage = [], type = [], source = [], efficiency = [], 
		datatooltype = [], featuredAppType = [], area = [], level = [], resType = [], typeofactivity = [],
		progstage = [], challengetype = [], oppType = [], carMake = [], priceFrom = [], priceTo = [], fuelFrom = [], 
		fuelTo = [];
		$('input[name="projectCategory"]:checked').each(function () {
			category.push($(this).val());
		});
		$('input[name="projectDate"]:checked').each(function () {
			date.push($(this).val());
		});
		$('input[name="projectStage"]:checked').each(function () {
			stage.push($(this).val());
		});
		$('input[name="Type"]:checked').each(function () {
			type.push($(this).val());
		});
		$('input[name="RenewableEnergySources"]:checked').each(function () {
			source.push($(this).val());
		});
		$('input[name="EnergyEfficiency"]:checked').each(function () {
			efficiency.push($(this).val());
		});
		$('input[name="DataToolType"]:checked').each(function () {
			datatooltype.push($(this).val());
		});
		$('input[name="AreaOfExpertise"]:checked').each(function () {
			area.push($(this).val());
		});
		$('input[name="LevelOfExpertise"]:checked').each(function () {
			level.push($(this).val());
		});
		$('input[name="TypeOfSupport"]:checked').each(function () {
			resType.push($(this).val());
		});
		$('input[name="typeofactivity"]:checked').each(function () {
			typeofactivity.push($(this).val());
		});
		$('input[name="progstage"]:checked').each(function () {
			progstage.push($(this).val());
		});
		$('input[name="challengetype"]:checked').each(function () {
			challengetype.push($(this).val());
		});
		$('input[name="oppType"]:checked').each(function () {
			oppType.push($(this).val());
		});
		$('input[name="featuredAppType"]:checked').each(function () {
			featuredAppType.push($(this).val());
		});
		$('input[name="carMake"]:checked').each(function () {
			carMake.push($(this).val());
		});
		$('input[name="priceFrom"]').each(function () {
			if($(this).val() != "") 
				priceFrom.push($(this).val());
		});
		$('input[name="priceTo"]').each(function () {
			if($(this).val() != "") 
				priceTo.push($(this).val());
		});
		$('input[name="fuelFrom"]').each(function () {
			if($(this).val() != "") 
				fuelFrom.push($(this).val());
		});
		$('input[name="fuelTo"]').each(function () {
			if($(this).val() != "") 
				fuelTo.push($(this).val());
		});
		
		filterData = {};
		if( category.length ) {
			filterData.category = category;
		}
		if( date.length ) {
			filterData.date = date;
		}
		if( stage.length ) {
			filterData.stage = stage;
		}
		if( type.length ) {
			filterData.type = type;
		}
		if( source.length ) {
			filterData.source = source;
		}
		if( efficiency.length ) {
			filterData.efficiency = efficiency;
		}
		if( datatooltype.length ) {
			filterData.datatooltype = datatooltype;
		}
		if( area.length ) {
			filterData.area = area;
		}
		if( level.length ) {
			filterData.level = level;
		}
		if( resType.length ) {
			filterData.resType = resType;
		}
		if( typeofactivity.length ) {
			filterData.typeofactivity = typeofactivity;
		}
		if( progstage.length ) {
			filterData.progstage = progstage;
		}
		if( challengetype.length ) {
			filterData.challengetype = challengetype;
		}
		if( oppType.length ) {
			filterData.oppType = oppType;
		}
		if( featuredAppType.length ) {
			filterData.featuredAppType = featuredAppType;
		}
		if( carMake.length ) {
			filterData.carMake = carMake;
		}
		if( priceFrom.length ) {
			filterData.priceFrom = priceFrom;
		}
		if( priceTo.length ) {
			filterData.priceTo = priceTo;
		}
		if( fuelFrom.length ) {
			filterData.fuelFrom = fuelFrom;
		}
		if( fuelTo.length ) {
			filterData.fuelTo = fuelTo;
		}
		pageNo = 0;
		getData();
	});
	function getData() {
		var searchParams = new URLSearchParams(location.search);
		searchParams.set('queryText', encodeURI(queryText));
		searchParams.set('sortField', sortField);
		searchParams.set('sort', sortKey);
		searchParams.set('filter', JSON.stringify(filterData));
		searchParams.set('pageNo', pageNo);
		searchParams.set('pageSize', 10);
		location.href = location.origin+location.pathname+'?'+searchParams.toString();
	}
	
	$('#reset').on('click', function (e) {
		e.preventDefault();
		sortField = "createDate";
		queryText = "";
		sortKey = "desc";
		filterData = JSON.parse('{}');
		pageNo = "0";
		pageSize = "5";
		$('.checkmark').css('background-color','white'); 
		getData();
	}); 
	
	$('#priceFrom, #priceTo, #fuelFrom, #fuelTo').keydown(function(e) {
	    if ((e.which == 8 || e.which == 46)) {
	        $(this).val('');
	    }
	});
</script>
