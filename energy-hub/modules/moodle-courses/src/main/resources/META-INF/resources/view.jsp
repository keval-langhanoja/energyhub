<%@ include file="/init.jsp" %>
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
	.subCateg{
		display: list-item !important;
	    margin: 0px 25px;
	    font-weight: normal;
    }
</style>
<div class="row mt-5">
	<div class="monitorcard justify-content-between align-items-center py-2 px-3">
		<div class="justify-content-between align-items-start" style="display:flex">
			<p class="mt-5">
				<strong><liferay-ui:message key="CoursesCategories" /></strong>
			</p>
		</div>
		<div class="px-3 goCheck row mb-5">
			  <ul class="row m-0"  style="text-align: start;">
				<c:forEach var="elt" items="${categories}" varStatus="loop">
					<li class="col-12 col-xl-4 col-lg-6 col-md-12 col-sm-6" style="font-weight: bold;">
                        <input type="checkbox" class="categCheckbox" name="category" value="${elt.categoryID}">
                        <label>${elt.categoryName}</label>
						<c:if test="${fn:length(elt.children) gt 0 }">
							<c:forEach var="child" items="${elt.children}" varStatus="loop">
								<ul class="subCateg">
	                                <li>
		                                 <input type="checkbox" class="categCheckbox" name="category" value="${child.categoryID}">
		                                 <label>${child.categoryName}</label>
                               		</li>
                          		</ul>
							</c:forEach>
						</c:if>
					</li>
				</c:forEach>
			</ul>
		</div>
		<button class="blueBtn submit mb-5 post" style="width:auto !important; padding: 0px 15px;" id="submit"><liferay-ui:message key="Browse" /></button>
	</div>
</div>
<script src="/o/energy-hub-theme/js/checkbox-multilevels.js"></script>
<script>
	var _searchParams = new URLSearchParams(location.search);
	var filterData = JSON.parse(_searchParams.get('filter') || '{}');
	
	$('#submit').on('click', function () {
		var category = [], date = [], stage = [];
		$('input[name="category"]:checked').each(function () {
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
		searchParams.set('pageNo', "0");
		searchParams.set('pageSize', "10");
		searchParams.set('filter', JSON.stringify(filterData));
		location.href = location.origin+'/courses?seeAllCourses&'+searchParams.toString();
	}
</script>
