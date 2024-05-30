<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<link rel="stylesheet" href="/o/energy-hub-theme/style/newsletter.css">
<style>
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
	.card-img {
	    width: 200px;
	    margin: 0 auto;
	    min-height: 200px;
	    max-height: 200px;
	}
	.redirectTitle {
	    color: #42d2f4;
	    cursor: pointer;
	}
	.noPointer:hover{
		cursor: default;
	}
</style>
<div class="row d-flex mb-4">
	<div class="col-8">
		<h1 class="newsletterTitle"><liferay-ui:message key="LatestNews" /></h1>
    </div>
    <c:if test="${showAddNews}">
	    <div class="col-4">
		    <button class="add mb-2" data-toggle="modal" data-target="#newsForm" style=" position: absolute; bottom:0; right: 0;">
				<span class="plus">
					<img class="mb-1" src="/o/energy-hub-theme/images/add.svg">
				</span>
				<span><liferay-ui:message key="AddNews" /></span>
			</button>
	    </div>
    </c:if>
</div>
<div class="row">
	<c:forEach var="news" items="${newsList}"> 
		<div class="col-lg-4 mb-5" >
			 <div class="card card-small" >
				<img class="card-img-top card-img" src="${news.imageURL}"
						onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;">	
				<div class="card-body">
					<p class="card-subtitle">${news.publishDate}</p>
					<h5 class="card-title redirectTitle" onclick="window.location.href = '${news.detailURL}';">${news.title}</h5>
					<div id="divDesc" class="card-text mt-3 card-details-small">${news.description}</div>
					<hr>
					<ul class="cardDetails">
						<li class="detailsItem">
							<a href="javascript:;" class="noPointer">
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

<div class="seeAll text-end mt-4">
	<a href="/news?allNews">
		<span><liferay-ui:message key="SeeAll" /></span>
		<span class="arrow bg-black"><img src="/o/energy-hub-theme/images/arrow.svg"></span>
	</a>
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
