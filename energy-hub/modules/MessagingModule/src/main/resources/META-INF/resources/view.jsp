<%@ include file="/init.jsp" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="/o/energy-hub-theme/style/MyInbox.css">
<link rel="stylesheet" type="text/css" href="/o/energy-hub-theme/style/bootstrap/bootstrap-tagsinput.css" />
<script src="/o/energy-hub-theme/js/bootstrap-tagsinput.min.js"></script>
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
	textarea {
	    resize: none;
	    width: 100%;
	}
	.dropdown-toggle direction-down, 
	#_MessagingModule_MessagingModulePortlet_INSTANCE_fMvOk6dTF93t_saveToggle {
		display: none;	
	}
	.bootstrap-tagsinput input {
	    min-height: 59px;
	} 
	div.bootstrap-tagsinput {
	    border: 1px solid #ccc !important;
    	box-shadow: inset 0 1px 1px rgb(0 0 0 / 8%) !important;
	}
	#tagProgram-dropdown {
		z-index: 99999;
	}
	.bootstrap-tagsinput .tag {
	    margin-right: 2px;
	    color: #17a2b8;
	}
</style>
<div class="content mx-auto pb-5 px-3 position-relative">
	<div class="userTypeCard position-relative topBorder mt-5">
	<c:if test="${isAdmin}">
		<div class="d-flex justify-content-end fixBtns">
			<button class="blueBtn mt-3" onClick="location.href = '/messaging?admin'"><liferay-ui:message key="Reports"></liferay-ui:message></button>
		</div>
	</c:if>
		<div class="card" style="min-height: 450px;">
			<div class="row g-0 pr-2">
				<div class="col-12 col-lg-4 col-md-12">
					<div class="chatList py-2 px-2 mt-2">
						<div class="d-flex searchBlue align-items-center">
							<span><img class="search" width="18px"
									src="/o/energy-hub-theme/images/inputSearch.svg"></span>
							<input type="text" placeholder="Find people and conversations" id="nonAdminusers">
							<span class="mx-3" style="color:#707070;cursor: pointer;" onClick="closeChatSearch()">X</span>
							<button class="myInboxAdd" style="float:right" onClick="$('#search').modal('show');">
								<span class="plus"><img class="newChat" src="/o/energy-hub-theme/images/plus.svg"></span>
							</button>
						</div>
						<div>
							<div id="divChatOption" class="d-flex position-relative flex-wrap">
								<div>
									<ul class="px-3 nav nav-tabs tabs" id="myTab" role="tablist">
										<li>
											<a class="nav-link recentFilter active" id="certificate-tab" onClick="filterChats('Recent')" href="javascript:;">
												<liferay-ui:message key="Recent" />
											</a>
										</li>
										<li>
											<a class="nav-link unreadFilter" id="successStories-tab" onClick="filterChats('isUnread')" href="javascript:;">
												<liferay-ui:message key="Unread" />
											</a>
										</li>
										<li>
											<a class="nav-link groupFilter" id="status-tab" onClick="filterChats('isGroup')" href="javascript:;">
												<liferay-ui:message key="Groups" />
											</a>
										</li>
									</ul>
									<hr>
								</div>
							</div>
						</div>
						<div class="peopleChats mb-1" id="chatsDiv">
							<ul class="peopleList" id="peopleListDiv">
								<!-- DRAW CHAT LIST-->
							</ul>
						</div>
					</div>
					<hr class="d-block d-lg-none mt-1 mb-0">
				</div>
				<div class="col-12 col-lg-8 col-md-12" id="selectChat">
					<div class="selectChat"><liferay-ui:message key="SelectChatToDisplayContent" /></div>
				</div>
				<div class="col-12 col-lg-8 col-md-12" id="chatMainDiv" style="display: none">
					<div class="row mb-1 px-4">
						<div class="userName mb-3 mt-3 mx-3 pt-2" id="otherUsersName"></div>
					</div>
					<hr class="chatHeaderSeparator">
					<div class="chat-messages p-3" id="messagesDiv">
						<!-- DRAW CHAT -->
					</div>
					<div class="">
						<hr class="chatFooterSeparator">
					</div>
					<div class="row pb-2 px-3">
					<p class="blockedText" style="display:none !important;"><liferay-ui:message key="ThisChatIsBlocked" />!</p>
						<div class= "row d-flex textMessage align-items-center" id="textMessageDiv">
							<div class="textareattt col-11" id="inputMessage" type="text" contentEditable="true"></div>
							<div class="col-1">
	                            <button class="btnMessage" id="sendMessage" onClick="sendNewMessage()">
		                            <span>
		                            	<img style="margin-right:15px;" width="25px" src="/o/energy-hub-theme/images/sendMessage.png">
		                            </span>
	                            </button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--search popup-->
<div class="modal fade" id="search" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
	aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header registerationForm">
				<h1><liferay-ui:message key="StartANewConversation" /></h1>
			</div>
			<div class="">
				<div class="modal-body">
					<div class="row">
						<div class="d-flex justify-content-between flex-wrap">
							<div class="col-12">
								<div class="" id="tagProgram">
<!-- 								<div class="col-12 ui-widget d-flex searchPopup align-items-center add-innovation-dropdown" id="tagProgram"> -->
<!-- 									<span><img width="18px" src="/o/energy-hub-theme/images/inputSearch.svg"></span> -->
								</div>
								<div class="col-12 ui-widget d-flex searchPopup align-items-center mt-3 p-0">
									<input id="groupName" placeholder="Group Name" disabled>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="d-flex justify-content-end fixBtns">
					<button type="button" class="blueBorderBtn reset mt-3 mx-3" onClick="$('#search').modal('hide');"><liferay-ui:message key="Cancel" /></button>
					<button type="button" class="blueBtn mt-3" onclick="startNewChat()"><liferay-ui:message key="StartChat" /></button>
				</div>
			</div>
		</div>
	</div>
</div>

<!--Report popup-->
<div class="modal fade" id="reportPopup" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
	aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header registerationForm  mt-3 mb-3">
				<h1><liferay-ui:message key="HelpUsUnderstandTheProblem" /></br><liferay-ui:message key="WhatAreTheReasonsForReportingThisUser" /> </h1>
			</div>
			<aui:form id="createAccount_form" cssClass="registerationForm" accept-charset="UTF8" method="POST">
				<div class="">
					<div class="modal-body">
						<div class="row">
							<div class="d-flex justify-content-between flex-wrap">
								<div class="col-12">
									<div class="col-12 mt-4 inputContainer required">
										<label><liferay-ui:message key="Reason" /></label>
										<aui:select cssClass="browser-default custom-select custom-select-lg mb-4 signup_select"
											label="" id="report_reason" name="report_reason"> 
											<aui:option disabled="true" selected="true" value=""><liferay-ui:message key="SelectOption" /></aui:option>
											<aui:option value="spam"><liferay-ui:message key="ItsSuspiciousOrSpam" /></aui:option>
											<aui:option value="abusive"><liferay-ui:message key="ItsAbusiveOrHarmful" /></aui:option>
											<aui:option value="Other"><liferay-ui:message key="OtherReasonsPleaseExplain" /></aui:option>
										</aui:select>
									</div>
									<div class="col-12 mt-4 inputContainer required">
										<label><liferay-ui:message key="Description" /></label>
										<aui:input cssClass="alphaOnly" id="description" type="textarea"  name="description" label=""></aui:input>
										<input id="reportedUserId" type="hidden">
										<input id="reportedUserChatId" type="hidden">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="d-flex justify-content-end fixBtns">
						<aui:button cssClass="blueBtn submit mt-3" type="submit" onclick="reportUser()" id="save" value="SendReport"></aui:button>
					</div>
				</div>
   			</aui:form>
		</div>
	</div>
</div>

<script>
	var openedChat;
	var chatsJA = ${chatsja}; 
	var usersList = ${nonAdminusersJA};
	var stopFetch = false;
	
	$('body').scroll(function(){
		$('#tagProgram-dropdown').remove();
  	});
	
	$( document ).ready(function() {
		drawChatsList(chatsJA);
		
		$('#tagProgram').tagsinput({
			itemValue: 'id',
			itemText: 'text',
			trimValue: true,
			allowDuplicates: false
		}); 
		createSearchUsersList();
	});
	
	$('#search').on('click', 'button.close', function (eventObject) {
		$('#search').modal('hide');
		$('#grouName').val('');
		$('#tagProgram').tagsinput('items');
	});
	
	$('#reportPopup').on('click', 'button.close', function (eventObject) {
		$('#reportPopup').modal('hide');
		$('#description').val('');
		$('#report_reason').val('');
	});
	
		
	function fetchNewMessages() {
		ajaxCall('fetchNewMessages', openedChat);
		ajaxCall('getUsersNonAdminNonBlocked');
	}
	
	function sendNewMessage() {
		if(!$("#sendMessage").is(":disabled")){
			if(document.getElementById('inputMessage').innerHTML.trim() !="") { 			
				ajaxCall('sendNewMessage', openedChat, document.getElementById('inputMessage').innerHTML);
			}
		}
	}
	
	setInterval(fetchNewMessages, 3000);
// 	setInterval(fetchNewMessages, 300000000);

	function startNewChat(){
		var items = $('#tagProgram').tagsinput('items');
		var names ="";
		for(var i = 0; i< items.length; i++){
			names += items[i].id +',';
		}
		names = names.substring(0, names.length - 1);
		ajaxCall('NewChat', names, $("#groupName").val());
	}
	
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
	
	function openChat(chatId, otherUsersName, currentUserId, msgs){
		openedChat = chatId;
		ajaxCall('MarksAsRead', chatId);
		if(msgs !=""){
			chatsJA = msgs;
		}
		$('#messagesDiv').empty();
		var messages = getChatMessages(chatsJA, 'chatId', chatId);
		document.getElementById("chatMainDiv").style.display="block";
		document.getElementById("selectChat").style.display="none";
		document.getElementById("otherUsersName").innerHTML = otherUsersName;
		
		if(messages[0].isblocked){ 
			$(".blockedText")[0].style.display ="block";
			$("#textMessageDiv").addClass("hide");
			$("#sendMessage").attr("disabled", "disabled");
		}else {
			$(".blockedText")[0].style.display ="none";
			$("#textMessageDiv").removeClass("hide");
			$("#sendMessage").prop('disabled', false);
		}
		
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
	
	function drawChatsList(chatsJA){
		$('#peopleListDiv').empty();
		var html=""; 
		for(var i = 0; i< chatsJA.length; i++){
			html += drawChatListItem(chatsJA[i]);
		}	
	 	$("#peopleListDiv").append(html);
		sortRecentChats();
	}
	
	function drawChatListItem(chat){
		var leaveGroup="", blockReport="", isGroup= "", isUnread= "";
		if(chat.isgroup) isGroup = "isGroup";
		if(chat.unreadcounter>0) isUnread = "isUnread";
		
		var html = '<li class="contact-name '+isUnread +' '+ isGroup +'" onclick="openChat(\'' + chat.chatid +'\',\''+ chat.otherusersname+'\', \''+ chat.currentuserid +'\', \'\')"> '+
			'<button class="btnPerson "> '+
				'<div class="row justify-content-between"> '+
					'<div class="col-9"> '+
						'<div class="row"> '+
							'<div class="col-2 col-lg-2 col-md-2 col-sm-2 col-xs-2"> ';
							if(chat.unreadcounter>0) {
								html += '<div class="unread"> '+
	                            	'<p>'+ chat.unreadcounter +'</p> '+
	                        	'</div> ';
							}
							html += '<img class="personImg" src="/o/energy-hub-theme/images/user_portrait.png"> '+
// 							html += '<img class="personImg" src="'+chat.userIamge+'" onerror="this.src=\'/o/energy-hub-theme/images/user_portrait.png\'; this.onerror=null;"/> '+
							'</div> '+
							'<div class="col-8 col-lg-8 col-md-8 col-sm-8 col-xs-8" style="padding:0px;margin-left: 20px;"> ';
							if(chat.isgroup) 
								html += '<div class="personName" title="'+chat.groupname+'">' +chat.groupname+'</div>';
							else
								html += '<div class="personName" title="'+chat.otherusersname+'">' +chat.otherusersname+'</div>';
								
							if(chat.messages.length >0){
								html += '<div class="lastMsg">' + chat.sentby +': '+ chat.lastreceivedmessage + '</div> ';
							}
						html +=	'</div> '+
						'</div> '+
					'</div> '+
					'<div class="col-3 justify-content-end"> '+
						'<div class="dropdown show d-flex justify-content-end"> '+
							'<a href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true"  '+
								'aria-expanded="false" style="margin-left: 33px;height: 25px;"> '+
								'<img style="transform: rotate(90deg);" class="float-right" src="/o/energy-hub-theme/images/threeDots.svg"> '+
							'</a> '+
							'<div class="dropdown-menu chatOptions" aria-labelledby="dropdownMenuLink"> '+
								'<a class="dropdown-item" href="javascript:;" onclick="ajaxCall(\'MarksAsRead\', \''+ chat.chatid +'\')"> '+
									'<img src="/o/energy-hub-theme/images/eye.svg" height="12px" alt=""><liferay-ui:message key="MarksAsRead"/>  '+
								'</a> ';
								if(chat.isgroup) {
								 	leaveGroup += '<a class="dropdown-item" href="javascript:;" onclick="ajaxCall(\'Leave\', \''+ chat.chatid +'\')"> '+
													'<img src="/o/energy-hub-theme/images/logout.png" height="12px" alt=""><liferay-ui:message key="Leave" /> '+
												'</a> ';
								}
								
								var htmlCont = '<a class="dropdown-item" href="javascript:;" onclick="ajaxCall(\'Delete\', \''+ chat.chatid +'\')"> '+
									'<img src="/o/energy-hub-theme/images/bin.png" height="12px" alt=""><liferay-ui:message key="Delete" /> '+
								'</a> ';
								if(!chat.isgroup) {
									if(chat.isblocked && (chat.blockedby == chat.currentuserid || chat.blockedby == 'both')){
										blockReport +=  '<a class="dropdown-item" href="javascript:;" onclick="ajaxCall(\'Unblock\', \''+ chat.otherusersid +'\')"> '+
										'<img src="/o/energy-hub-theme/images/block.png" height="12px" alt=""><liferay-ui:message key="Unblock" /> '+
									'</a>  ';
									}else if(!chat.isblocked || (chat.isblocked && (chat.blockedby != chat.currentuserid && chat.blockedby != 'both'))){
										blockReport +=  '<a class="dropdown-item" href="javascript:;" onclick="ajaxCall(\'Block\', \''+ chat.otherusersid +'\')"> '+
										'<img src="/o/energy-hub-theme/images/block.png" height="12px" alt=""><liferay-ui:message key="Block" /> '+
									'</a>  ';
									}
								}
								if(!chat.isgroup) {
									blockReport += '<a class="dropdown-item" href="javascript:;" onclick="openReportModal('+chat.otherusersid+','+ chat.chatid +')">  '+
											'<img src="/o/energy-hub-theme/images/red-flag.png" height="12px" alt=""><liferay-ui:message key="Report" />  '+
										'</a>  ';
								}
							var htmlEnd = '</div>  '+
						'</div>  ';
							if(chat.messages.length > 0){
								htmlEnd += '<div class="lastMsg d-flex justify-content-end">'+ chat.lastreceivedmessagesentdate +'</div>  ';
							}
						htmlEnd +='</div>  '+
				'</div>  '+
			'</button>  '+
		'</li>  ';
		
		return html + leaveGroup + htmlCont + blockReport + htmlEnd;
	}
	
	function openReportModal(user, chatId){
		$("#reportedUserId").val(user);
		$("#reportedUserChatId").val(chatId);
		$('#reportPopup').modal('show');
	}
	
	function reportUser() {
		ajaxCall('Report', $("#reportedUserId").val(), $("#<portlet:namespace/>report_reason").val(), 
				$("#<portlet:namespace/>description").val(),  $("#reportedUserChatId").val());
	}
	
	function ajaxCall(key, id, message, description, reportedUserChatId) {
 		var xhr = new XMLHttpRequest();
 		var formData = new FormData();
 		if (key == "NewChat"){
 			var data = {
				 <portlet:namespace />key: key,
				 <portlet:namespace />newUsers: id,
				 <portlet:namespace />groupName: message
	 		};
 		}
 		if (key == "sendNewMessage"){
 			var data = {
				 <portlet:namespace />key: key,
				 <portlet:namespace />chatId: id,
				 <portlet:namespace />message: message
	 		};
 		}
		if (key == "fetchNewMessages" || key == "Leave" || key == "Delete" || key == "MarksAsRead"){
			var data = {
				 <portlet:namespace />key: key,
				 <portlet:namespace />chatId: id
	 		};
 		}
		if (key == "Report"){
			var data = {
				 <portlet:namespace />key: key,
				 <portlet:namespace />reportedUserId: id,
				 <portlet:namespace />reportedUserChatId: reportedUserChatId,
				 <portlet:namespace />reason: message,
				 <portlet:namespace />description: description
	 		};
 		}
		if (key == "Block" || key =="Unblock"){
			var data = {
				 <portlet:namespace />key: key,
				 <portlet:namespace />blockedUserId: id
	 		};
 		}
		if (key == "getUsersNonAdminNonBlocked"){
			var data = {
				 <portlet:namespace />key: key
	 		};
 		}
 		
 		xhr.onloadend = function (e) {
 			var res = xhr.responseText.toLowerCase();
 			if(key == "NewChat") {
 				$('#tagProgram').tagsinput('removeAll');
 				$('#search').modal('hide');
 			}
 			if(key == "sendNewMessage") {
 				var msg = JSON.parse(res).message;
 				var time = JSON.parse(res).time;
 				var sendername = JSON.parse(res).sendername;
 				$("#inputMessage")[0].innerHTML ="";
 				 var newMsg = drawMyMessage(msg, time,true, sendername);
 				 $("#messagesDiv").append(newMsg);
 				 $('#messagesDiv').scrollTop($('#messagesDiv')[0].scrollHeight);
 			}
 			if(key == "fetchNewMessages"){
 				chatsJA = JSON.parse(res).chatsja;
 				if(!stopFetch) {
	 				drawChatsList(chatsJA);
 				}
 	 			if(openedChat != undefined && openedChat!=""){
 	 				var currentUserId = JSON.parse(res).currentuserid;
 	 				var otherUsersName = JSON.parse(res).otherusersname;
 		 			openChat(openedChat, otherUsersName, currentUserId, chatsJA);
 	 			}
 	 			usersList = JSON.parse(res).users;
 			}
 			if(key == "getUsersNonAdminNonBlocked"){
 				usersList = JSON.parse(res).users;
 			}
 		}
 		xhr.onprogress = function (e) {
 			if( e.lengthComputable ) {
 				var percentComplete = e.loaded / e.total * 100;
 				console.log('upload '+percentComplete+'%');
 			}
 		};
 		
 		xhr.open('POST', '${testAjaxResourceUrl}&'+$.param(data));
 		xhr.send(formData); 
 	}
	 
	function split( val ) {
      return val.split( /,\s*/ );
    }
	//Search Users
	$("#search").on('hidden.bs.modal', function () {
		$("#groupName").val('');
	});
	
	//READ MORE
    let contents = document.querySelectorAll(".extendText");
    contents.forEach(content => {
        if (content.scrollHeight > content.clientHeight) {
            content.parentElement.getElementsByClassName("btnReadMore")[0].classList.toggle("hideBTN");
        }
    });
    
    function readMore(btn) {
        let post = btn.previousElementSibling;
        post.classList.toggle("tenLines");
        btn.classList.toggle("hideBTN");
    }
	 
    //Search Chats    
    $("#nonAdminusers").bind("keyup keydown", function (event) {
        var evtType = event.type;
        var eWhich = event.which;

        switch (evtType) {
            case 'keyup':
            	if(eWhich == 13 || this.value.length > 1) {
            		getChatSearchVals();
            	}
       	       	break;
            case 'keydown':
            	if(eWhich != 8 && eWhich != 46){
            		getChatSearchVals();
                }else {
                	closeChatSearch();
                    return false;
                }
                break;
            default:
                break;
        }
    });
    
    function getChatSearchVals() {
    	stopFetch = true;
	 	$('.contact-name').hide();
       	var txt = $('#nonAdminusers').val();
       	$('.contact-name:contains("'+txt+'")').show();
    }
    
    function closeChatSearch() {
    	stopFetch = false;
    	$('#nonAdminusers').val('');
    	fetchNewMessages();
    }
    
    function filterChats(filter) {
    	stopFetch = true;
    	var listItems = $("#peopleListDiv li");
    	listItems.each(function(idx, li) {
    	    var chatItem = $(li);
    	    if(filter!="Recent"){
	    	    if(chatItem.hasClass(filter)) 
	    	    	chatItem.show();
	    		else chatItem.hide();
 	   	    }else {
 	   	    	stopFetch = false;
 	   	    	fetchNewMessages();
 	   	    }
    	});
    	
        $(".nav-link").removeClass("active");
	    if(filter == "isGroup") {
	    	$(".nav-link.groupFilter").addClass("active");
	    }else if(filter == "isUnread") {
	    	$(".nav-link.unreadFilter").addClass("active");
	    }else if(filter == "Recent"){
	    	$(".nav-link.recentFilter").addClass("active");
	    }
    }
    
    function sortRecentChats(ul, sortDescending) {
		ul = "peopleListDiv";
  		if(typeof ul == "string")
	    ul = document.getElementById(ul);
	
	  	var lis = ul.getElementsByTagName("LI");
	  	var vals = [];
	  	for(var i = 0, l = lis.length; i < l; i++)
	    	vals.push(lis[i].innerHTML);
	
	  	vals.sort();
	
	  	// Change the list on the page
 	 	for(var i = 0, l = lis.length; i < l; i++)
    		lis[i].innerHTML = vals[i];
	}
    
    function createSearchUsersList() {
    	var tag_programs = {};
    	for(var i = 0; i< usersList.length; i++){
    		if(usersList[i].fullName !="") {
	    		tag_programs[usersList[i].emailAddress] = usersList[i].fullName;
    		}
    	}
    	$('#tagProgram').off('beforeItemRemove').on('beforeItemRemove', function(event) {
	    	tag_programs[event.item.id] = event.item.text;
	    	$('#tagProgram-dropdown').remove();
	    	if($('#tagProgram').tagsinput('items').length -1 > 1)
           		$("#groupName").prop('disabled', false);
           	else
           		$("#groupName").prop('disabled', true);
	    }).off('itemAdded').on('itemAdded', function(event) {
	    	$('#tagProgram-dropdown').remove();
	    	if($('#tagProgram').tagsinput('items').length > 1)
           		$("#groupName").prop('disabled', false);
           	else
           		$("#groupName").prop('disabled', true);
	    })
	    // find input and on un-focus remove the dropdown menu
	    .prev('.bootstrap-tagsinput').find('input').on('blur', function () {
	    	setTimeout(function () {
	    		$('#tagProgram-dropdown').remove();
	    	}, 100)
	    })
	    // set event on keydown to detect the enter key and on any key show the dropdown menu
	    .off('keydown').on('keydown', function (e) {
	    	// if the enter key clicked then add the item to the tags
	    	if( e.keyCode == 13 ) {
	            e.preventDefault();
	            var selected = $('#tagProgram-dropdown').find('a.active');
	            if( selected.length ) {
	            	var id = selected.data('id')+'';
	            	// add the clicked item to the tags
	        		$('#tagProgram').tagsinput('add', {id: id, text: selected.text()});
	        		// remove key from removed tags
	        		delete tag_programs[id];
	            }  
	            e.target.value = '';
	        }  else if(e.keyCode == 8){debugger;
	        	getSearched(tag_programs, e, true);
	        } 
	    	
	        // if any key entered then show the dropdown menu
	        // if not removed item in the tags then don't do any thing
	        else if( Object.keys(tag_programs).length ) {
        		getSearched(tag_programs, e, false);
	        	
	        	if( e.keyCode == 38 ) {
	        		var activeItem = $('#tagProgram-dropdown').find('a.active');
	        		if( activeItem.length ) {
	        			var prev = activeItem.closest('li').prev('li').find('a');
	        			if( prev.length ) {
		        			prev.addClass('active');
	        			}
	       				activeItem.removeClass('active');
	        		}
	        	} else if( e.keyCode == 40 ) {
	        		var activeItem = $('#tagProgram-dropdown').find('a.active').removeClass('active');
	        		if( !activeItem.length ) {
	        			activeItem = $('#tagProgram-dropdown').find('a:first').addClass('active');
	        		} else {
	        			var next = activeItem.closest('li').next('li').find('a');
	        			if( next.length ) {
	        				activeItem = next.addClass('active');
	        			}
	        		}
	        	}
	        }
	    });
    }
    
    function getSearched(tag_programs, e, isBackSpace) {
    	var inputText = $(".bootstrap-tagsinput input").val();
    	if(isBackSpace)
    		inputText = inputText.substr(0, inputText.length - 1);
    	else
    		inputText = inputText + e.key;
    	
	    var items = [];
		$.each(tag_programs, function (id, text) {
			if(text.toLowerCase().startsWith(inputText.toLowerCase())) {
				items.push('<li><a tabindex="-1" href="javascript:;" data-id="'+id+'" style="text-decoration:none;">'+text+'</a></li>');
			}
		});  
		
		if($('#tagProgram-dropdown').length)
			$('#tagProgram-dropdown').remove();
		
		if(items.length > 0){
			$('body').append('<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu" id="tagProgram-dropdown">'
			+ items.join('') +'</ul>');
			$('#tagProgram-dropdown').css({
				position: 'absolute',
				display: 'inline-block',
				top: $(e.target).offset().top+75,
				left: $(e.target).offset().left+175,
			}).html();
		}
		// add event on the menu item, when click then add it to the tags
		$('#tagProgram-dropdown').find('li a').off('click').on('click', function () {
			var id = ''+$(this).data('id');
			// add the clicked item to the tags
			$('#tagProgram').tagsinput('add', {id: id, text: $(this).text()});
			// remove key from removed tags
			delete tag_programs[id];
			// reset input
			e.target.value = '';
		});
    }
</script>