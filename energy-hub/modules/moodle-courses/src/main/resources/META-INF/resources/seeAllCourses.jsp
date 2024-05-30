<%@ include file="/init.jsp" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
	.searchBlue {
		display: none !important;
	}
</style>
<div class="row mt-5">
   <div class="programsFlex d-flex align-items-center justify-content-between">
		<h1 class="innovationTitle mb-2">${categName}</h1>
		<div class="d-flex contentt">
			<div class="d-inline-block" style="text-align: center;">
				<div class="btn-group">
					<button type="button" id='btnFilter' class="modal-button"
						data-bs-toggle="dropdown" data-bs-auto-close="false"
						aria-expanded="false">
						<liferay-ui:message key="Filter" /><span class='img_Nav'></span></button>
					<div class="dropdown-menu dropdown-menu-filter" aria-labelledby="btnFilter">
						<div class="divTitle">
							<c:forEach var="categ" items="${categories}">
								<c:choose>
									<c:when test="${fn:length(categ.children) gt 0 }">
										<span class=" title">${categ.categoryName}</span>
										<table class="tbl">
										   <tr>
												<c:forEach var="child" items="${categ.children}" varStatus="loop">
													<c:if test="${loop.index!=0 && loop.index%2==0}">
														</tr><tr>
													</c:if>
													<td><label class="container">${child.categoryName}
														<input type="checkbox" name="category" value="${child.categoryID}">
														<span class="checkmark"></span>
													</label></td>
										   		</c:forEach>
									   		</tr>
								   		</table>
								   		<div class="dropdown-divider"></div>
							   		</c:when>
							   		<c:when test="${fn:length(categ.children) eq 0 }"> 
							   			<table class="tbl">
											<tr>
												<td>
													<label class="container title">${categ.categoryName}
														<input type="checkbox" name="category" value="${categ.categoryID}">
														<span class="checkmark"></span>
													</label>
												</td>
											</tr>
										</table>
								   		<div class="dropdown-divider"></div>
							   		</c:when>
						   	  	</c:choose>
						   	</c:forEach>
						</div>
						<div>
							<div
								class="d-flex justify-content-end fixBtns mt-5 mb-3">
								<button type="button" class="reset mx-3" id="reset" data-dismiss="modal"><liferay-ui:message key="Reset" /></button>
								<button type="button" id="applyFilter" class="blueBtn submit"><liferay-ui:message key="Apply" /></button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div> 
	</div>
	<c:forEach var="course" items="${courses}">
 		<div class="col-12 col-xl-6 mt-4">
			<a href="${course.url}" class="card innovationCard grid">
				<div class="programImageContainer">
<%-- 					<div class="programCardImage" style="background: url('${course.imageURL}');"></div> --%>
				</div>
				<div class=" cardDetails py-4" style="overflow:hidden">
					<h2 class="cardTitle">${course.courseName}</h2>
					<p class="mt-3 mb-4">${course.courseSummary}</p>
				</div>
				<div class="category">
					<span class="grayText px-2">${course.categoryName}</span>
				</div>
			</a> 
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
<script>
$('#btnFilter').on('click', function () {
	if( $(".dropdown-menu-filter").hasClass("show") ) {
		$(".dropdown-menu-filter").removeClass("show")
	}else {
		$(".dropdown-menu-filter").addClass("show")
	}
});

var _searchParams = new URLSearchParams(location.search);
var filterData = JSON.parse(_searchParams.get('filter') || '{}');
var pageNo = ${pageNo+1};
var pageSize = ${pageSize};

$('.page-item').on('click', function () {
	var page = $(this).find('a');
	if( page.hasClass('activepage') || page.hasClass('inactive') ) {
		return;
	}
	
	pageNo = page.hasClass('plink') ? parseInt(page.text())-1 : pageNo+(page.hasClass('nextpage') ? 1 : -1);
	getData();
});

$('#applyFilter').on('click', function () {debugger;
	var category = [], date = [], stage = [];
	$('input[name="category"]:checked').each(function () {debugger;
		category.push($(this).val());
	});
	
	filterData = {};
	if( category.length ) {
		filterData.category = category;
	}
	
	pageNo = 0;
	getData();
});
function getData() {
	var searchParams = new URLSearchParams(location.search);
	searchParams.set('filter', JSON.stringify(filterData));
	searchParams.set('pageNo', pageNo);
	searchParams.set('pageSize', pageSize);
	location.href = location.origin+location.pathname+'?'+searchParams.toString();
}

$('#reset').on('click', function (e) {
	e.preventDefault();
	filterData = JSON.parse('{}');
	pageNo = "0";
	pageSize = "10";
	$('.checkmark').css('background-color','white'); 
	getData();
}); 
</script>
