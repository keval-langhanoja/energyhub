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
	            <div class="row mt-5 mb-2">
	            	<jsp:include page="/filter.jsp" />
	            </div>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="left" style="padding: 0">
                            <table bgcolor="#ffffff" width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td align="center" style="padding: 0" class="pt-5">
                                        <table bgcolor="#ffffff" border="0" cellspacing="0" cellpadding="0" 
                                        	class="innertable" id="tableFilter">
                                            <thead>
												<tr class="innertr">
													<th class="innerth capitalize"><liferay-ui:message key="FullName" /></th>
													<th class="innerth capitalize"><liferay-ui:message key="Threads" /></th>
													<th class="innerth capitalize"><liferay-ui:message key="Category" /></th>
													<th class="innerth capitalize"><liferay-ui:message key="Views" />
														<button type="button" id='btnSort' onClick="sortTable('viewcount')" class="modal-button thSort">
															<span class='img_Nav'></span>
														</button>
													</th>
													<th class="innerth capitalize"><liferay-ui:message key="Likes" />
														<button type="button" id='btnSort' onClick="sortTable('likecount')"  class="modal-button thSort">
															<span class='img_Nav'></span>
														</button>
													</th>
													<th class="innerth capitalize"><liferay-ui:message key="Dislikes" />
														<button type="button" id='btnSort' onClick="sortTable('dislikecount')" class="modal-button thSort">
															<span class='img_Nav'></span>
														</button>
													</th>
													<th class="innerth capitalize"><liferay-ui:message key="Comments" />
														<button type="button" id='btnSort' onClick="sortTable('commentscount')" class="modal-button thSort">
															<span class='img_Nav'></span>
														</button>
													</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="report" items="${entries}">
													<tr class="innertr">
														<td class="innerth" style="position: relative;" 
															rowspan=${report.entryTotal}>${report.UserName} </br>
															<c:if test="${report.entryTotal gt 5}">
																<a  href="javascript:;" 
																style="position:absolute;bottom:0px; right: 0;" 
																	class="hrefNoColor" 
																	onclick="showMore('tableFilter')">
																	<liferay-ui:message key="ShowMore"/> 
																	<liferay-ui:message key="threads"/>
															</c:if>
														</a>
													</td> 
													</tr>
													<c:forEach var="thread" items="${report.entries}"  begin="0" varStatus="loop">
														<c:if test="${loop.index lt 4 }">
															<tr class="innertr shown">
														</c:if>
														<c:if test="${loop.index gt 4 }">
															<tr class="innertr hidden">
														</c:if>
															<td class="innerth">
																<a class="mb-2 hrefNoColor" href="${thread.threadUrl}" 
																target="_blank">${thread.threadTitle}</a>
															</td>
															<td class="innerth">${thread.threadType}</td>
															<td class="innerth thCount">${thread.viewcount}</td>
															<td class="innerth thCount">${thread.likecount}</td>
															<td class="innerth thCount">${thread.dislikecount}</td>
															<td class="innerth thCount">${thread.commentscount}</td>
														</tr>
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