<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<portlet:actionURL var="syncUrl" />
<portlet:resourceURL var="testAjaxResourceUrlsync"></portlet:resourceURL>
 			
<script>
	function ajaxCall(key) {
		if(key == "BackupVms" || key == "RestoreVms"){
			var xhr = new XMLHttpRequest();
			var formData = new FormData();
			var data = {
				<portlet:namespace />key: key,
			};
			
			xhr.onloadend = function (e) {
				window.history.back();
			}
			
			xhr.onprogress = function (e) {
				if( e.lengthComputable ) {
					var percentComplete = e.loaded / e.total * 100;
					console.log('upload '+percentComplete+'%');
				}
			};
			
			xhr.open('POST', '${testAjaxResourceUrlsync}&'+$.param(data));
			xhr.send(formData); 
		}
	}
</script>
