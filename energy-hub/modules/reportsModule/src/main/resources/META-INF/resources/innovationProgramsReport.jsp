<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>
<!DOCTYPE html>
<html>

<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <style type="text/css">
   		table tr td {
		    border: 0 !important;
		}
		table tr td:first-child {
			text-align: -webkit-center !important;
		}
        @media screen {
            @font-face {
                font-family: 'Lato';
                font-style: normal;
                font-weight: 400;
                src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');
            }

            @font-face {
                font-family: 'Lato';
                font-style: normal;
                font-weight: 700;
                src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');
            }

            @font-face {
                font-family: 'Lato';
                font-style: italic;
                font-weight: 400;
                src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');
            }

            @font-face {
                font-family: 'Lato';
                font-style: italic;
                font-weight: 700;
                src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');
            }
        }

        /* CLIENT-SPECIFIC STYLES */
        body,
        table,
        td,
        a {
            -webkit-text-size-adjust: 100%;
            -ms-text-size-adjust: 100%;
        }

        table,
        td {
            mso-table-lspace: 0pt;
            mso-table-rspace: 0pt;
        }

        img {
            -ms-interpolation-mode: bicubic;
        }

        /* RESET STYLES */
        img {
            border: 0;
            height: auto;
            line-height: 100%;
            outline: none;
            text-decoration: none;
        }

        table {
            border-collapse: collapse !important;
        }

        body {
            height: 100% !important;
            margin: 0 !important;
            padding: 0 !important;
            width: 100% !important;
        }

        /* iOS BLUE LINKS */
        a[x-apple-data-detectors] {
            color: inherit !important;
            text-decoration: none !important;
            font-size: inherit !important;
            font-family: inherit !important;
            font-weight: inherit !important;
            line-height: inherit !important;
        }

        /* MOBILE STYLES */
        @media screen and (max-width:600px) {
            h1 {
                font-size: 32px !important;
                line-height: 32px !important;
            }
        }

        /* ANDROID CENTER FIX */
        div[style*="margin: 16px 0;"] {
            margin: 0 !important;
        }
        
        
        .innertable {
		  font-family: arial, sans-serif;
		  border-collapse: collapse;
		  width: 100%;
		}
		
		.innertd, .innerth {
		  border: 1px solid #dddddd !important;
		  text-align: left;
		  padding: 8px;
	      max-width: 100px;
		} 
		.thCount {
			width: 30px;
		}
		.hidden {
			display : none !important;
	 	}
	 	.hrefNoColor {
	 		color: #212529;
    		text-decoration: none;
	 	}
	 	.hrefNoColor:hover {
    		text-decoration: underline;
	 	}
	 	.border {
	 		border: 1px solid #707070;
	 	}
	 	.download-wrapper:hover{
	 		cursor: pointer;
	 	}
	 	.innertr{
	 		display: none;
	 	}
    </style>
</head>

<body style="margin: 0 !important; padding: 0 !important;">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="center" style="padding: 20px 10px 0px 10px;">
                <table bgcolor="#ffffff" border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="center" valign="top" style="padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;">
                            <h1 style="font-size: 48px; font-weight: 400; margin: 2;">${reportName}</h1>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td align="center" style="padding: 0px 10px 0px 10px;">
            <div class="row mt-5">
            	<div class="col-4 border d-flex align-items-center justify-content-between">
	            	<label>Start Date</label>
		            <input id="startDate" type="date" onkeydown="return false"></input>
            	</div>
            	<div class="col-4 border d-flex align-items-center justify-content-between">
	            	<label>End Date</label>
		            <input id="endDate"type="date" onkeydown="return false"></input>
            	</div>
            	<div class="download-wrapper d-flex align-items-center justify-content-center col-1 border" 
            		onClick="filter()">
		  			<p style="margin:0 auto">Filter</p>
				</div>
				<div class="download-wrapper d-flex align-items-center justify-content-center col-1 border" 
            		onClick="reset()">
		  			<p style="margin:0 auto">Reset</p>
				</div>
	           	<div class="download-wrapper d-flex align-items-center justify-content-center col-2 border" 
	           		onClick="exportCSVFile('${reportName}', '${csvString}')">
		  			<p  style="margin:0 auto">Download</p>
				</div>
            </div>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="left" style="padding: 0">
                            <table bgcolor="#ffffff" width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td align="center" style="padding: 0" class="pt-5">
                                        <table bgcolor="#ffffff" border="0" cellspacing="0" cellpadding="0" 
                                        	class="innertable" id="reportThreads">
                                            <thead>
												<tr class="innertr1">
													<th class="innerth"><liferay-ui:message key="ProgramName" /></th>
													<th class="innerth"><liferay-ui:message key="Type" /></th>
													<th class="innerth"><liferay-ui:message key="Stage" /></th>
													<th class="innerth"><liferay-ui:message key="StartDate" /></th>
													<th class="innerth"><liferay-ui:message key="EndDate" /></th>
													<th class="innerth"><liferay-ui:message key="User" /></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="elt" items="${entries}">
													<c:forEach var="report" items="${elt.entries}">
														<c:if test="${report.innovationProgramTitle !=''}">
															<tr class="innertr1">
																<td class="innerth">
																	<a class="mb-2 hrefNoColor" href="${report.url}" target="_blank">${report.innovationProgramTitle}</a>
																</td>
																<td class="innerth">${report.innovationProgramType}</td>
																<td class="innerth">${report.innovationProgramStage}</td>
																<td class="innerth">${report.innovationProgramStartDate}</td>
																<td class="innerth">${report.innovationProgramEndDate}</td>
																<td class="innerth">${report.innovationProgramCreator}</td>
															</tr>
														</c:if>	
												 	</c:forEach>
												 </c:forEach>
											</tbody>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</body>
</html>

<script>
	var _searchParams = new URLSearchParams(location.search);
	function showMore(tableName) {
		var $rows = $('#'+ tableName +' tr');
		var lastActiveIndex = $rows.filter('.shown:last').index() +1;
		$rows.filter(':lt(' + (lastActiveIndex + 4) + ')').removeClass('hidden');
		$rows.filter(':lt(' + (lastActiveIndex + 4) + ')').addClass('shown'); 
		
		if($rows.length == lastActiveIndex)
			$("#"+tableName+"Div").addClass('hidden');
	}
	
	$('#startDate').on('change', function(e){
		var startDate = $(this).val();
		$('#endDate').attr('min', startDate);
	});
	$('#endDate').on('change', function(e){
		var endDate = $(this).val();
		$('#startDate').attr('max', endDate);
	});
	
	var filterData = JSON.parse(_searchParams.get('filter') || '{}');
	if(!jQuery.isEmptyObject(filterData)){
		$('#startDate').val(filterData.startDate);
		$('#endDate').val(filterData.endDate);
	}
		
	function filter(){
		var start = $("#startDate").val();
		var end = $("#endDate").val();
		var  startDate = [], endDate = [];
		if(start !='' && end !='') {
			startDate.push(start);
			endDate.push(end);
			
			filterData = {};
			if( startDate.length ) {
				filterData.startDate = startDate;
			}
			
			if( endDate.length ) {
				filterData.endDate = endDate;
			}
			getData();
		}	else alert ('Please Fill Both Date Fields!')		
	}
	
	function getData() {
		var searchParams = new URLSearchParams(location.search);
		searchParams.set('filter', JSON.stringify(filterData));
		location.href = location.origin+location.pathname+'?'+searchParams.toString();
	}
	
	function exportCSVFile(fileTitle, csv) {
		csv = csv.replaceAll("___newLine___", "\r\n");
	    var exportedFilenmae = fileTitle + '.csv' || 'export.csv';
	    var blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
	    if (navigator.msSaveBlob) {
	        navigator.msSaveBlob(blob, exportedFilenmae);
	    } else {
	        var link = document.createElement("a");
	        if (link.download !== undefined) {
	            var url = URL.createObjectURL(blob);
	            link.setAttribute("href", url);
	            link.setAttribute("download", exportedFilenmae);
	            link.style.visibility = 'hidden';
	            document.body.appendChild(link);
	            link.click();
	            document.body.removeChild(link);
	        }
	    }
	} 
	
	function reset() {
		filterData = JSON.parse('{}');
		getData();
	}

</script>
