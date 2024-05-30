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
		.card-text {
		display: -webkit-box;
	    -webkit-line-clamp: 3;
	    -webkit-box-orient: vertical;
	    overflow: hidden;
	    text-overflow: ellipsis;
	}
	.card-title {
	    display: -webkit-box;
	    -webkit-line-clamp: 2;
	    -webkit-box-orient: vertical;
	    overflow: hidden;
	    text-overflow: ellipsis;
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
							<c:forEach var="type" items="${NewsType}"
								varStatus="loop">
								<c:if test="${loop.index!=0 && loop.index%2==0}">
						</tr>
						<tr>
							</c:if>
							<td><label class="container">${type.value} <input
									type="checkbox" name="type"
									value="${type.id}"> <span class="checkmark"></span>
							</label></td>
							</c:forEach>
						</tr>
					</table>
				</div>
				<div>
					<div class="d-flex justify-content-end fixBtns mt-5 mb-3">
						<button type="button" class="reset mx-3" id="reset"
							data-dismiss="modal"><liferay-ui:message key="Reset" /></button>
						<button type="button" id="applyFilter" class="blueBorderBtn submit"><liferay-ui:message key="Apply" /></button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="row d-flex justify-content-center mt-5">
	<div>
		<div class="row">
			<c:forEach var="news" items="${newsList}" varStatus="loop">
				<c:if test="${loop.index!=0 && loop.index%3==0}">
					</div><div class="row">
				</c:if>
				<div class="col-xl-4 col-lg-4 col-md-6mb-5">
	                <div class="card card-small">
	                    <img class="card-img-top card-img" src="${news.imageURL}"
						onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;">	
	                    <div class="card-body">
	                        <h5 class="card-title  card-header-title-small mt-3">${news.title}</h5>
	                        <div class="card-text mt-3 card-details-small">${news.description}</div>
	                        <hr>
                            <ul class="cardDetails">
								<li class="detailsItem">
									<a href="">
										<img class="mb-1" src="/o/energy-hub-theme/images/contact/react.svg">
										<span>${news.commentcount}</span>
									</a>
								</li>
								<li class="detailsItem">
									<a href="">
										<img class="mb-1" src="/o/energy-hub-theme/images/contact/comment.svg">
										<span>${news.likecount}</span>
									</a>
								</li>
							</ul>
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
	var type = [];
	$('input[name="type"]:checked').each(function () {
		type.push($(this).val());
	});
	
	filterData = {};
	if( type.length ) {
		filterData.type = type;
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
