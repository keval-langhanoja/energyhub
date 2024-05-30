<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link rel="stylesheet" href="/o/energy-hub-theme/style/addNewProgram-my projects-ver2.css">
<style>
	.cardTopics {
	    display: flex;
	    flex-flow: row wrap;
	    font-size: 14px;
	    margin: 20px 0 0 0;
		padding: 0;
	    justify-content: center;
	}
	.cardTopics .topicItem {
      display: inline-block;
      border-radius: 3px;
      padding: 0.5px 10px;
      margin: 0 5px 5px 0;
      cursor: default;
      user-select: none;
      transition: background-color 0.3s;
      border: #009BC7;
      border-style: solid;
      border-width: 1px;
      color: #009BC7;
      font-weight: 600;
	}
	.cardTopics .topicItem:hover {
        background: #009BC7;
        color: white;
    }
    .topicItemActive  {
        background: #009BC7;
        color: white !important;
    }
    table th ,
    table tr:first-child th {
	    text-align: center !important;
	}
	.hidden {
		display : none !important;
 	}
 	th, td {
		padding: 20px 10px;
	}
</style>

<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>
<portlet:actionURL var="ManageProjectUrl" />
<div class="row">
   <div class="programsFlex d-flex align-items-center justify-content-between">
       <div class="col-sm-2">
           <button type="button" class=" add mb-2 " onclick="location.href = '${addProjectUrl}';">
               <img src="/o/energy-hub-theme/images/add.svg" class="mr-1"><liferay-ui:message key="Add" /></button>
       </div>
       <div class="d-flex justify-content-end content">
           <div class="col-sm-8">
               <div class="d-flex searchBlue align-items-center">
                   <span><img width="18px" src="/o/energy-hub-theme/images/inputSearch.svg"></span>
                   <input type="text" placeholder="Seacrh" id="queryText">
               </div>
           </div>
           <div class="col-sm-2" style="text-align: center;">
               <button type="button" id='btnSort' class="modal-button">
				<liferay-ui:message key="Sort" /><span class='img_Nav'></span>
               </button>
           </div>
           <div class="col-sm-2" style="text-align: center;">
                 <button type="button" id='btnFilter' class="modal-button"
	                     data-bs-toggle="dropdown" data-bs-auto-close="false" aria-expanded="false">
	                     <liferay-ui:message key="Filter" /><span class='img_Nav'></span></button>
                      <div class="dropdown-menu dropdown-menu-filter" aria-labelledby="btnFilter">
	                      <c:if test="${fn:length(Degreetype) gt 0 }">
		                       <div class="divTitle">
		                           <span class="title"><liferay-ui:message key="ProjectStatus" /></span>
		                           <table class="tbl">
		                           		<tr>
		                                   <td>
		                                       <label class="container"><liferay-ui:message key="Published" />
		                                           <input type="checkbox" name="projectStatus" value="0">
		                                           <span class="checkmark"></span>
		                                       </label>
		                                   </td><td>
		                                       <label class="container"><liferay-ui:message key="InProgress" />
		                                           <input type="checkbox" name="projectStatus" value="2">
		                                           <span class="checkmark"></span>
		                                       </label>
		                                    </td>
		                                 </tr>
		                                 <tr>
		                                    <td>
		                                       <label class="container"><liferay-ui:message key="Rejected" />
		                                           <input type="checkbox" name="projectStatus" value="3">
		                                           <span class="checkmark"></span>
		                                       </label>
		                                   </td>
		                               </tr>
		                           </table>
		                       </div>
		                       <div class="dropdown-divider"></div>
	                       </c:if>
	                       <c:if test="${fn:length(projectCategList) gt 0 }">
		                       <div class="divTitle">
		                           <span class="title"><liferay-ui:message key="ProjectCategory" /></span>
		                           <table class="tbl">
		                              	<tr>
		                              		<c:forEach var="projectCateg" items="${projectCategList}" varStatus="loop">
		                              			<c:if test="${loop.index!=0 && loop.index%2==0}">
		                                   			</tr><tr>
		                                   		</c:if>
			                                   	<td><label class="container"><liferay-ui:message key="${projectCateg.value}" />
			                                       <input type="checkbox" name="projectCategory" value="${projectCateg.id}">
			                                       <span class="checkmark"></span>
			                                   </label></td>
		                             		</c:forEach>
		                              	</tr>
		                           </table>
		                       </div>
	                       	<div class="dropdown-divider"></div>
	                       </c:if>
	                       <c:if test="${fn:length(projectCategList) gt 0 }">
		                       <div class="divTitle">
		                           <span class=" title"><liferay-ui:message key="ProjectStage" /></span>
		                           <table class="tbl">
		                               <tr>
		                                  	<c:forEach var="projectStage" items="${projectStageList}" varStatus="loop">
		                              			<c:if test="${loop.index!=0 && loop.index%2==0}">
		                                   			</tr><tr>
		                                   		</c:if>
		                                    	<td><label class="container"><liferay-ui:message key="${projectStage.value}" />
		                                            <input type="checkbox" name="projectStage" value="${projectStage.id}">
		                                            <span class="checkmark"></span>
		                                        </label></td>
		                                   </c:forEach>
		                               </tr>
		                           </table>
		                       </div>
	                       </c:if>
	                       <c:if test="${fn:length(innovationProgramActivityTypeList) gt 0 }">
	                       		<div class="divTitle">
		                           <span class=" title"><liferay-ui:message key="ProjectStage" /></span>
		                           <table class="tbl">
		                               <tr>
		                                  	<c:forEach var="projectStage" items="${innovationProgramActivityTypeList}" varStatus="loop">
		                              			<c:if test="${loop.index!=0 && loop.index%2==0}">
		                                   			</tr><tr>
		                                   		</c:if>
		                                    	<td><label class="container"><liferay-ui:message key="${projectStage.value}" />
		                                            <input type="checkbox" name="typeOfActivity" value="${projectStage.id}">
		                                            <span class="checkmark"></span>
		                                        </label></td>
		                                   </c:forEach>
		                               </tr>
		                           </table>
		                       </div>
	                       </c:if>
	                       <c:if test="${fn:length(innovationProgramStageList) gt 0 }">
	                       		<div class="divTitle">
		                           <span class=" title"><liferay-ui:message key="ProjectStage" /></span>
		                           <table class="tbl">
		                               <tr>
		                                  	<c:forEach var="projectStage" items="${innovationProgramStageList}" varStatus="loop">
		                              			<c:if test="${loop.index!=0 && loop.index%2==0}">
		                                   			</tr><tr>
		                                   		</c:if>
		                                    	<td><label class="container"><liferay-ui:message key="${projectStage.value}" />
		                                            <input type="checkbox" name="programStage" value="${projectStage.id}">
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
		                                  	<c:forEach var="opptype" items="${Type}" varStatus="loop">
		                              			<c:if test="${loop.index!=0 && loop.index%2==0}">
		                                   			</tr><tr>
		                                   		</c:if>
		                                    	<td><label class="container"><liferay-ui:message key="${opptype.value}" />
		                                            <input type="checkbox" name="opptype" value="${opptype.id}">
		                                            <span class="checkmark"></span>
		                                        </label></td>
		                                   </c:forEach>
		                               </tr>
		                           </table>
		                       </div>
	                       </c:if>
	                       <c:if test="${fn:length(InnovationChallengeChallengeType) gt 0 }">
	                       		<div class="divTitle">
		                           <span class=" title"><liferay-ui:message key="challengeType" /></span>
		                           <table class="tbl">
		                               <tr>
		                                  	<c:forEach var="elt" items="${InnovationChallengeChallengeType}" varStatus="loop">
		                              			<c:if test="${loop.index!=0 && loop.index%2==0}">
		                                   			</tr><tr>
		                                   		</c:if>
		                                    	<td><label class="container"><liferay-ui:message key="${elt.value}" />
		                                            <input type="checkbox" name="challengeType" value="${elt.id}">
		                                            <span class="checkmark"></span>
		                                        </label></td>
		                                   </c:forEach>
		                               </tr>
		                           </table>
		                       </div>
	                       </c:if>
	                       <c:if test="${fn:length(CarMake) gt 0 }">
								<div class="divTitle">
									<span class="title"><liferay-ui:message key="CarMake" /></span>
									<table class="tbl" id="carMake">
										<tr>
											<c:forEach var="elt" items="${CarMake}" begin="0" varStatus="loop">
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
<div class="row mt-4" style="overflow-x: auto;">
	<c:if test="${showTypeFilter}">
		<div class="d-flex justify-content-end px-4">
			<ul class="card-text cardTopics" style="display: list-item;">
				<a href="javascript:;" style="text-decoration: none;" class="topicItem linkTag" id="InnoProg"><liferay-ui:message key="InnovationPrograms" /></a>
				<a href="javascript:;" style="text-decoration: none;" class="topicItem linkTag" id="InnoChall"><liferay-ui:message key="InnovationChallenges" /></a>
				<a href="javascript:;" style="text-decoration: none;" class="" id="clearTypeFilter">X</a>
			</ul>
		</div>
	</c:if>
    <table class="table-responsive viewTable mt-2" style="display: inline-table;">
        <thead>
            <tr>
                <th><liferay-ui:message key="ProjectName"/></th>
		 <c:if test="${showTypeFilter}">
                	<th><liferay-ui:message key="ProjectType"/></th>
                </c:if>
                <th><liferay-ui:message key="SubmitDate"/></th>
                <th><liferay-ui:message key="Status"/></th>
                <th><liferay-ui:message key="TrackStatus"/></th>
                <th><liferay-ui:message key="Actions"/></th>
            </tr>
        </thead>
        <c:forEach var="program" items="${programs}">
        	  <tr>
	            <td>
	            	<img class="projImage" src="${program.coverImage}"
						onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;">
	                <span>
	                    <p class="mt-1 projText" style="display:none">${program.id}</p>
	                    <p class="mt-1 projText">${program.projectTitle}</p>
	                </span>
	
	            </td>
	            <c:if test="${showTypeFilter}">
               	 	<td>
	                	<span class="dateSpan"><liferay-ui:message key="${program.projectType}" /></span>
	            	</td>
                </c:if>
	            <td>
	                <span class="dateSpan">${program.creationDate}</span>
	            </td>
	            <td>
	            <c:choose>
				    <c:when test="${program.status =='0'}">
				        <span class='statusSpan statusPublished'>
		                    <img class="statusImage" src="/o/energy-hub-theme/images/check-white.png">
							<liferay-ui:message key="Published"/> 
	                	</span>
				    </c:when>   
				    <c:when test="${program.status =='2'}">
				        <span class='statusSpan statusPending'>
		                    <img class="statusImage" src="/o/energy-hub-theme/images/check-white.png">
		                    <liferay-ui:message key="InProgress"/>
	                	</span>
				    </c:when>    
				    <c:when test="${program.status =='3'}">
				        <span class='statusSpan statusRejected'>
		                    <img class="statusImage" src="/o/energy-hub-theme/images/check-white.png">
							<liferay-ui:message key="Rejected"/>
	                	</span>
				    </c:when>  
				</c:choose>
	                
	            </td>
	            <td>
	                <button class="btnProgress btnprog" onclick="displayProgress(this,'${program.creationDate}','${program.status}')">
	                    &#9877;
	                </button>
	            </td>
	            <td>
	            	<c:if test="${!program.viewOnly}">
		            	<button class="btnProgress" onclick="ajaxCall('edit', '${program.editProjectUrl}')"><img class="btnImage"
		                        src="/o/energy-hub-theme/images/edit.png"></span></button>
		                <button class="btnProgress" onclick="ajaxCall('delete', '${program.id}')"><img class="btnImage"
		                        src="/o/energy-hub-theme/images/delete.png"></span></button>
	            	</c:if>
	            </td>
        	</tr>
        </c:forEach>
      
    </table>
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

<script>
	$( document ).ready(function() {
		var _searchParams = new URLSearchParams(location.search);
		
		if(_searchParams.get('articleTypeFilter') == 'InnoChall')
			$('#InnoChall').addClass('topicItemActive');
		else
			$('#InnoChall').removeClass('topicItemActive');
		
		if(_searchParams.get('articleTypeFilter') == 'InnoProg')
			$('#InnoProg').addClass('topicItemActive');
		else
			$('#InnoProg').removeClass('topicItemActive');
	});

	$('#btnFilter').on('click', function () {
		if( $(".dropdown-menu-filter").hasClass("show") ) {
			$(".dropdown-menu-filter").removeClass("show")
		}else {
			$(".dropdown-menu-filter").addClass("show")
		}
	});
	
	var _searchParams = new URLSearchParams(location.search);
	var sortField = _searchParams.get('sortField') || "createDate";
	var articleTypeFilter = _searchParams.get('articleTypeFilter');
	var queryText = decodeURI(_searchParams.get('queryText') || "");
	var sortKey = _searchParams.get('sort') || "desc";
	var filterData = JSON.parse(_searchParams.get('filter') || '{}');
	var pageNo = ${pageNo};
	var pageSize = ${pageSize};
	
	$('#queryText').val(queryText);
	
	$('.page-item').on('click', function () {
		var page = $(this).find('a');
		if( page.hasClass('activepage') || page.hasClass('inactive') ) {
			return;
		}
		
		pageNo = page.hasClass('plink') ? parseInt(page.text())-1 : pageNo+(page.hasClass('nextpage') ? 1 : -1);
		getData();
	});
	
	$('#queryText').on('keyup', function (e) {
		if( e.keyCode == 13 ) {
			queryText = $(this).val();
			pageNo = 0;
			getData();
		}
	});
	$('#btnSort').on('click', function () {
		sortKey = sortKey=='asc' ? 'desc' : 'asc';
		getData();
	});
	
	$('#InnoChall').click(function(){
		$('#InnoChall').addClass('topicItemActive');
		articleTypeFilter = 'InnoChall';
		getData();
	});
	
	$('#InnoProg').click(function(){ 
		$('#InnoProg').addClass('topicItemActive');
		articleTypeFilter = 'InnoProg';
		getData();
	});
	
	$('#clearTypeFilter').click(function(){ 
		$('#InnoChall').removeClass('topicItemActive');
		$('#InnoProg').removeClass('topicItemActive');
		articleTypeFilter = '';
		getData();
	});
	
	$('#applyFilter').on('click', function () {
		var category = [], status = [], stage = [], typeOfActivity  = [],
			programStage = [], challengeType = [], opptype = [], carMake = [];
		$('input[name="projectCategory"]:checked').each(function () {
			category.push($(this).val());
		});
		$('input[name="projectStatus"]:checked').each(function () {
			status.push($(this).val());
		});
		$('input[name="projectStage"]:checked').each(function () {
			stage.push($(this).val());
		});
		$('input[name="typeOfActivity"]:checked').each(function () {
			typeOfActivity.push($(this).val());
		});
		$('input[name="programStage"]:checked').each(function () {
			programStage.push($(this).val());
		});
		$('input[name="challengeType"]:checked').each(function () {
			challengeType.push($(this).val());
		});
		$('input[name="opptype"]:checked').each(function () {
			opptype.push($(this).val());
		});
		$('input[name="carMake"]:checked').each(function () {
			carMake.push($(this).val());
		});
		
		filterData = {};
		if( category.length ) {
			filterData.category = category;
		}
		if( status.length ) {
			filterData.status = status;
		}
		if( stage.length ) {
			filterData.stage = stage;
		}
		if( typeOfActivity.length ) {
			filterData.typeOfActivity = typeOfActivity;
		}
		if( programStage.length ) {
			filterData.programStage = programStage;
		}
		if( challengeType.length ) {
			filterData.challengeType = challengeType;
		}
		if( opptype.length ) {
			filterData.opptype = opptype;
		}
		if( carMake.length ) {
			filterData.carMake = carMake;
		}
		pageNo = 0;
		getData();
	}); 
	
	function getData() {
		var searchParams = new URLSearchParams(location.search);
		searchParams.set('queryText', encodeURI(queryText));
		searchParams.set('sortField', sortField);
		searchParams.set('sort', sortKey);
		searchParams.set('articleTypeFilter', articleTypeFilter);
		searchParams.set('filter', JSON.stringify(filterData));
		searchParams.set('pageNo', pageNo);
		searchParams.set('pageSize', pageSize);
		location.href = location.origin+location.pathname+'?'+searchParams.toString();
	}
	
	$('#reset').on('click', function (e) {
		e.preventDefault();
		sortField = "createDate";
		sortKey = "desc";
		articleTypeFilter = "";
		queryText = "";
		filterData = JSON.parse('{}');
		pageNo = "0";
		pageSize = "5";
		$('.checkmark').css('background-color','white'); 
		getData();
	}); 
	
	function showMore(tableName) {
		var $rows = $('#'+ tableName +' tr');
		var lastActiveIndex = $rows.filter('.shown:last').index() +1;
		$rows.filter(':lt(' + (lastActiveIndex + 4) + ')').removeClass('hidden');
		$rows.filter(':lt(' + (lastActiveIndex + 4) + ')').addClass('shown'); 
		
		if($rows.length == lastActiveIndex)
			$("#"+tableName+"Div").addClass('hidden');
	}
	
	function ajaxCall(key,id){
		if(key == 'edit') {
			window.location.href = location.origin + id;
		}else if (key == "delete"){
			AUI().use('aui-io-request', function(A){
			    A.io.request('${testAjaxResourceUrl}', {
			           method: 'post',
			           data: {
		            	   <portlet:namespace />key: key,
		            	   <portlet:namespace />id: id,
			           },
			           on: {
			               	success: function() {
			               		location.reload();
			               	}
			          }
			    });
			});
		}
	} 
	
	function displayProgress(elt,createdDate, status) {
		if ($('.trProgress')[0]) {
	        console.log('clicked');
	        var $tr = $('.trProgress')[0];
	        $tr.remove();
	    } else {
	        var newRow = $('<tr class="trProgress">');
	        var cols = "";
			if(status ==2 ){ //Pending
				 cols += '<td class="px-0" colspan="6">' +
		            '<div class="stepper-wrapper">' +
		            '<div class="stepper-item completed">' +
		            '<div class=""></div>' +
		            '<div class="step-counter"></div>' +
		            '<div class="step-name">' +
		            '<p class="titleSpan">Project submitted</p>' +
		            '<p class="dateSpan">'+ createdDate +'</p>' +
		            '</div>' +
		            '</div>' +
		            '<div class="stepper-item active">' +
		            '<div class="selector"></div>' +
		            '<div class="step-counter selected"></div>' +
		            '<div class="step-name">' +
		            '<p class="titleSpan">Under review</p>' +
		            '<p class="dateSpan">'+ createdDate +'</p>' +
		            '</div>' +
		            '</div>' +
		            '<div class="stepper-item">' +
		            '<div class=""></div>' +
		            '<div class="step-counter"></div>' +
		            '<div class="step-name">' +
		            '<p class="notCompleted">Published successfully</p>' +
		            '<p class="dateSpan"></p>' +
		            '</div>' +
		            '</div>' +
		            '</div>' +
		            '</td>';
			}else if(status == 0) { //Published
				 cols += '<td class="px-0" colspan="6">' +
		            '<div class="stepper-wrapper">' +
		            '<div class="stepper-item completed">' +
		            '<div class=""></div>' +
		            '<div class="step-counter"></div>' +
		            '<div class="step-name">' +
		            '<p class="titleSpan">Project submitted</p>' +
		            '<p class="dateSpan">'+ createdDate +'</p>' +
		            '</div>' +
		            '</div>' +
		            '<div class="stepper-item completed">' +
		            '<div class=""></div>' +
		            '<div class="step-counter"></div>' +
		            '<div class="step-name">' +
		            '<p class="titleSpan">Under review</p>' +
		            '<p class="dateSpan">'+ createdDate +'</p>' +
		            '</div>' +
		            '</div>' +
		            '<div class="stepper-item active">' +
		            '<div class="selector"></div>' +
		            '<div class="step-counter selected"></div>' +
		            '<div class="step-name">' +
		            '<p class="titleSpan">Published successfully</p>' +
		            '<p class="dateSpan">'+ createdDate +'</p>' +
		            '</div>' +
		            '</div>' +
		            '</div>' +
		            '</td>';
			}
	
	        newRow.append(cols);
	        newRow.insertAfter($(elt).parents().closest('tr'));
	    }
	}
</script>
