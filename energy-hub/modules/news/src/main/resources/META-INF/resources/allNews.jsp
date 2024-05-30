<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<link rel="stylesheet" href="/o/energy-hub-theme/style/newsletter.css">
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
	    -webkit-line-clamp: 4;
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
	div.innovationContent > div:nth-child(1),
	div.pb-5 > div.card.info {
		display: none;
	}
	.item-image {
	    width: 200px;
    	margin: 0 auto;
    	min-height: 200px;
	}
	.redirectTitle {
	    color: #42d2f4;
	    cursor: pointer;
	}
</style>
<div class="row">
	<div class="dropdown d-flex col-lg-3 col-md-6 col-sm-12">
		<h3><liferay-ui:message key="AllNews" /></h3>
	</div>
	<div class="col-lg-3 col-md-6 col-sm-12">
		<c:if test="${showAddNews}">
	    	<button class="add mb-2" data-toggle="modal" data-target="#newsForm" style=" position: absolute; bottom:0; right: 0;">
				<span class="plus">
					<img class="mb-1" src="/o/energy-hub-theme/images/add.svg">
				</span>
				<span><liferay-ui:message key="AddNews" /></span>
			</button>
		</c:if>
   	</div>
	<div class="col-lg-3 col-md-6 col-sm-12">
        <div class="d-flex searchBlue align-items-center">
            <span><img width="18px" src="/o/energy-hub-theme/images/inputSearch.svg"></span>
            <input type="text" placeholder="Seacrh" id="queryText">
        </div>
    </div>
	<div class="col-lg-3 col-md-6 col-sm-12">
		<div class="d-flex justify-content-end">
			<button type="button" id='btnFilter' class="modal-button" data-bs-toggle="dropdown" data-bs-auto-close="false" aria-expanded="false">
				<liferay-ui:message key="Filter" /><span class='img_Nav'></span>
			</button>
			<div class="dropdown-menu dropdown-menu-filter" aria-labelledby="btnFilter">
				<div class="divTitle">
					<span class="title"><liferay-ui:message key="Type" /></span>
					<table class="tbl">
						<tr>
							<c:forEach var="type" items="${NewsType}" varStatus="loop">
								<c:if test="${loop.index!=0 && loop.index%2==0}">
						</tr>
						<tr>
							</c:if>
							<td><label class="container"><liferay-ui:message key="${type.value} " />
								<input	type="checkbox" name="type" value="${type.id}">
							 	<span class="checkmark"></span>
							</label></td>
							</c:forEach>
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
			<c:forEach var="news" items="${newsList}" varStatus="loop">
				<c:if test="${loop.index!=0 && loop.index%3==0}">
					</div><div class="row">
				</c:if>
				<div class="col-xl-4 col-lg-4 col-md-6mb-5">
					<div class="card card-small">
						<img class="card-img-top card-img item-image" src="${news.imageURL}"
						onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;">
						<div class="card-body">
							<h5 class="card-title card-header-title-small redirectTitle" onclick="window.location.href = '${news.detailURL}';">${news.title}</h5>
							<p class="card-text card-details-small"> ${news.publishDate}</p>
							<div id="divDesc" class="card-text mt-3 card-details-small">${news.description}</div>
							<hr>
							<ul class="cardDetails">
								<li class="detailsItem">
									<a href="">
										<img class="mb-1" src="/o/energy-hub-theme/images/contact/comment.svg">
										<span>${news.commentcount}</span>
									</a>
								</li>
								<li class="detailsItem">
									<a href="javascript;" onclick="copyLink('${news.detailURL}')" data-toggle="modal" data-target="#copyLink">
										<img class="mb-1" style="height: 20px; width: 20px" 
											src="/o/energy-hub-theme/images/contact/link.png">
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

<div class="modal fade" tabindex="-1" role="dialog" id="copyLink">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title"><liferay-ui:message key="CopyLink" /></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body py-4" style="margin: 0 auto;">
        <button type="button" class="btn btn-primary"><liferay-ui:message key="Copy" /></button>
      </div>
    </div>
  </div>
</div>


<jsp:include page="/createNewsForm.jsp" /> 
<script>
	function copyLink(detailURL) {
		navigator.clipboard.writeText(location.origin + detailURL);
// 		alert("Copied the text: " + detailURL);
	}
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
	
	$('#btnFilter').on('click', function () {
		if( $(".dropdown-menu-filter").hasClass("show") ) {
			$(".dropdown-menu-filter").removeClass("show")
		}else {
			$(".dropdown-menu-filter").addClass("show")
		}
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
