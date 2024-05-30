<%@ include file="/init.jsp" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="/o/energy-hub-theme/style/MyInbox.css">

<portlet:actionURL var="energyProgramURL" /> 
<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>
<style>
	ul.ui-autocomplete{
    	display: inline-block;
	    padding: 10px 20px;
	    border: 1px solid rgba(0, 0, 0, 0.125) !important;
	    background-color: white;
	    z-index: 99999;
    }
    ul.ui-autocomplete{
    	display: inline-block;
	    padding: 10px 20px;
	    border: 1px solid rgba(0, 0, 0, 0.125) !important;
	    background-color: white;
    }
    .ui-menu-item{
    	padding: 5px;
    	border-bottom: 1px solid rgba(0, 0, 0, 0.125);
   	}
    .ui-menu-item:hover{
    	background-color: #009bc7;
	    color: white;
	    cursor: pointer;
   	}
   	.hide{
   		display: none !important;
   	}
   	.ui-helper-hidden-accessible {
	    display: none !important;
	}
	ul.ui-autocomplete {
	    height: 300px;
	    overflow-y: scroll;
	}
	#divChatOption hr, .chatHeaderSeparator{
		max-width: 97%;
	}
</style>
<div class="content mx-auto pb-5 px-3 position-relative">
	<div class="userTypeCard position-relative topBorder mt-5">
		<div class="card" style="min-height: 450px;">
			<div class="row g-0 pr-2">
				<div class="row mb-1 px-4">
					<div class="userName mb-3 mt-3 mx-3 pt-2" id="otherUsersName"></div>
				</div>
				<hr class="chatHeaderSeparator">
				<div class="col-12 col-lg-12 col-md-12" id="chatMainDiv">
					<div class="chat-messages p-3" id="messagesDiv">
						<!-- DRAW CHAT -->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	$( document ).ready(function() {
		openChat();
	});
	
	function getChatMessages(obj, key, val) {
	    var objects = [];
	    for (var i in obj) {
	        if (!obj.hasOwnProperty(i)) continue;
	        if (typeof obj[i] == 'object') {
	            objects = objects.concat(getChatMessages(obj[i], key, val));
	        } else if (i.toLowerCase() == key.toLowerCase() && 
	        		(obj[key.toLowerCase()] == val || obj[key] == val)) {
	            objects.push(obj);
	        }
	    }
	    return objects;
	}
	
	function openChat(){
		var chatsJA = ${chat};
		var chatId = chatsJA.chatid;
		var otherUsersName = chatsJA.otherusersname;
		var currentUserId = chatsJA.currentuserid;
		var messages = getChatMessages(chatsJA, 'chatId', chatId);
		
		document.getElementById("otherUsersName").innerHTML = otherUsersName;
		
		//Draw Chat
		var elt="";
		var msgs = messages[0].messages;
		var isGroup = messages[0].isgroup;
		for(var i = 0; i< msgs.length; i++){
			var msg = msgs[i].message;
			var sentDate = msgs[i].sentdate;
			var senderFirstName = msgs[i].senderfirstname;
			if(msgs[i].senderid == currentUserId)  elt += drawMyMessage(msg, sentDate, isGroup, senderFirstName);
			else elt += drawOthersMessage(msg, sentDate, isGroup, senderFirstName);
		}	
// 		<div class="row">
// 			<div class="newDay">Today</div>
// 		</div>

		 $("#messagesDiv").append(elt);
		 $('#messagesDiv').scrollTop($('#messagesDiv')[0].scrollHeight);
	}
	
	function drawOthersMessage(msg, time, isGroup, senderFirstName){
		var othersMessage =
			'<div class="chat-message-left"> '+
				'<div style="text-align: -webkit-left;"> ';
					othersMessage +='<p class="personMessageTime">'+ senderFirstName +' '+ time +'</p> ';
					othersMessage += '<div class="userMessage"> '+
						'<div class="tenLines extendText">'+ msg +'</div> '+
						'<button onclick="readMore(this)" class="btnReadMore hideBTN"> '+
						'<liferay-ui:message key="ReadMore" /></button> '+
					'</div> '+
				'</div> '+
			'</div>';
		return othersMessage;
	}
	
	function drawMyMessage(msg, time, isGroup, senderFirstName){
		var myMessage =
			'<div class="chat-message-right"> '+
				'<div style="text-align: -webkit-right;"> ';
					myMessage +='<p class="personMessageTime">'+ senderFirstName +' '+ time +'</p> ';
				 	myMessage += '<div class="personMessage">'+
						'<div class="tenLines extendText">'+ msg +'</div> '+
						'<button onclick="readMore(this)" class="btnReadMore hideBTN"> '+
						'<liferay-ui:message key="ReadMore" /></button> '+
					'</div>'+
				'</div>'+
			'</div>';
		return myMessage;
	}
</script>