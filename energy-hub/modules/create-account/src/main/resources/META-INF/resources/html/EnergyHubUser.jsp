<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

<portlet:actionURL var="createAccountURL" />
<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>

<style>
	.modal-body {
	    position: relative;
	    flex: 1 1 auto;
	    padding: 1rem 2rem;
	}
	.modal-content {
	    border-radius: 1rem;
	    max-height: 550px;
	    overflow: scroll;
	}
</style>
<div class="MainContent">
       <!--signup section-->
       <section id="signup" class="signup userType position-relative">
           <div class="bgImage">
               <svg class="animatedDots orange" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                   style="left: 25%; top: 55%; right: auto">
                   <circle cx="200" cy="50" r="50" />
               </svg>
               <svg class="animatedDots green2" fill="#8BB029" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                   style="left: 3%; top:15%">
                   <circle cx="200" cy="50" r="50" />
               </svg>
           </div>
           <div class="content position-relative mx-auto py-5 px-3">
               <div class=" d-flex justify-content-between titleSec">
                   <div class="d-flex path">
<!--                        <a><liferay-ui:message key="Home" /><span class="px-2"><img width="10px" src="/o/energy-hub-theme/images/doubleArrow.svg"></span></a> -->
<!--                        <a><liferay-ui:message key="SignupUserType" /><span class="px-2"><img width="10px" -->
<!--                                    src="/o/energy-hub-theme/images/doubleArrow.svg"></span></a> -->
<!--                        <a><liferay-ui:message key="Signup" /></a> -->
                   </div>
                   <div class="MainTitleHeaderDiv">
                       <h1 class="position-relative MainTitle">
                          	<c:if test="${!edit}">
                           		<div class="yellowBorder" style="height:25px"></div><liferay-ui:message key="Signup" />
							</c:if>
                        	<c:if test="${edit}">
                        		<div class="yellowBorder" style="height:25px"></div><liferay-ui:message key="EditAccount" />
                        	</c:if>
                       </h1>
                   </div>
               </div>
           </div>
           <div class="content mx-auto py-5 px-3">
               <svg class="animatedDots blue" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                   style="left: 1%; top:10%">
                   <circle cx="200" cy="50" r="50" />
               </svg>
               <svg class="animatedDots green2" fill="#8BB029" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                   style="left: 2%; top:15%; right: auto">
                   <circle cx="200" cy="50" r="50" />
               </svg>
               <div class="d-flex justify-content-between flex-wrap">
                   <div href="" class="back">
                       <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="9.77"
                           height="8.57" viewBox="0 0 9.77 8.57">
                           <defs>
                               <clipPath id="clip-path">
                                   <rect x="9" y="6" width="9.77" height="8.57" fill="none" />
                               </clipPath>
                               <filter id="Path_1325" x="-4.115" y="-6" width="22.885" height="26.57"
                                   filterUnits="userSpaceOnUse">
                                   <feOffset dy="3" input="SourceAlpha" />
                                   <feGaussianBlur stdDeviation="3" result="blur" />
                                   <feFlood flood-opacity="0.161" />
                                   <feComposite operator="in" in2="blur" />
                                   <feComposite in="SourceGraphic" />
                               </filter>
                               <filter id="Path_1325-2" x="-9" y="-6" width="22.885" height="26.57"
                                   filterUnits="userSpaceOnUse">
                                   <feOffset dy="3" input="SourceAlpha" />
                                   <feGaussianBlur stdDeviation="3" result="blur-2" />
                                   <feFlood flood-opacity="0.161" />
                                   <feComposite operator="in" in2="blur-2" />
                                   <feComposite in="SourceGraphic" />
                               </filter>
                           </defs>
                           <g id="Group_846" data-name="Group 846" transform="translate(-151.071 -348.203)">
                               <g id="Scroll_Group_1" data-name="Scroll Group 1" transform="translate(142.071 342.203)"
                                   clip-path="url(#clip-path)" style="isolation: isolate">
                                   <g id="Component_13_1" data-name="Component 13 – 1" transform="translate(9 6)">
                                       <g id="Group_844" data-name="Group 844"
                                           transform="translate(179.228 364.57) rotate(180)">
                                           <g id="Group_707" data-name="Group 707"
                                               transform="translate(169.458 364.57) rotate(-90)">
                                               <g id="Group_642" data-name="Group 642" transform="translate(0)">
                                                   <g id="Group_667" data-name="Group 667">
                                                       <g transform="matrix(0, -1, 1, 0, 0, 9.77)"
                                                           filter="url(#Path_1325)">
                                                           <path id="Path_1325-3" data-name="Path 1325"
                                                               d="M4.285,4.885a.6.6,0,0,1-.424-.176L.176,1.025A.6.6,0,0,1,1.025.175l3.26,3.261L7.546.176a.6.6,0,0,1,.849.849L4.709,4.709a.6.6,0,0,1-.424.176Zm0,0"
                                                               transform="translate(9.77 0) rotate(90)" fill="#333" />
                                                       </g>
                                                   </g>
                                               </g>
                                           </g>
                                           <g id="Group_708" data-name="Group 708"
                                               transform="translate(174.343 364.57) rotate(-90)">
                                               <g id="Group_642-2" data-name="Group 642" transform="translate(0 0)">
                                                   <g id="Group_667-2" data-name="Group 667">
                                                       <g transform="matrix(0, -1, 1, 0, 0, 4.88)"
                                                           filter="url(#Path_1325-2)">
                                                           <path id="Path_1325-4" data-name="Path 1325"
                                                               d="M4.285,4.885a.6.6,0,0,1-.424-.176L.176,1.025A.6.6,0,0,1,1.025.175l3.26,3.261L7.546.176a.6.6,0,0,1,.849.849L4.709,4.709a.6.6,0,0,1-.424.176Zm0,0"
                                                               transform="translate(4.88 0) rotate(90)" fill="#333" />
                                                       </g>
                                                   </g>
                                               </g>
                                           </g>
                                       </g>
                                   </g>
                               </g>
                           </g>
                       </svg>

                       <a id="goback" class="grayText px-2"><liferay-ui:message key="Back" /></a>
                   </div>
                   <div class="goLogin">
                       <p class="mb-0 d-inline"><liferay-ui:message key="YouAlreayHaveAnAccount" />?</p>
                       <a class="" href="" class="loginLink"><liferay-ui:message key="LoginNow" /></a>
                   </div>
               </div>
               <div class="userTypeCard position-relative topBorder mt-3 py-5 px-5">
                <c:if test="${!edit}">
                   <div class="card info">
                       <div class="d-flex">
                           <div class="userTypeImage yellowTopBorder py-2">
                               <img width="30px" src="/o/energy-hub-theme/images/involved/Mentor.svg">
                           </div>
                           <div class="choosenUserDetails py-2 px-4">
							<span class=""><liferay-ui:message key="YouAreRegistering" /> <span class="bold"><liferay-ui:message key="EnergyHubUser" /></span></span>
							<div class="mb-0 w-100 ">${userDescription}
								<a class="bold learnMore px-2" data-toggle="modal" data-target="#invite">
								<liferay-ui:message key="LearnMore" /></a>
							</div>
                           </div>
                       </div>
                   </div>
                   </c:if>
					<aui:form id="createAccount_form" cssClass="mt-5 registerationForm" accept-charset="UTF8" method="POST">
                       <h1><liferay-ui:message key="GeneralInformation" /></h1>
                	   <div class="row">
                           <div class="col-12 col-md-6 col-xl-4 mt-4 required inputContainer">
							<label><liferay-ui:message key="FirstName" /></label>
								<aui:input cssClass="alphaOnly" id="FirstName" name="FirstName" label=""></aui:input>
                           </div>
                           <div class="col-12 col-md-6 col-xl-4 mt-4 required inputContainer">
                            <label><liferay-ui:message key="LastName" /></label>
                               <aui:input cssClass="alphaOnly" id="LastName" name="LastName" label=""></aui:input>
                           </div>
                           <div class="col-12 col-md-6 col-xl-4 mt-4 required inputContainer">
                            <label><liferay-ui:message key="DateOfBirth" /></label>
								<aui:input id="DateOfBirth" type="date" name="DateOfBirth" label="" onkeydown="return false"></aui:input>
                           </div>
                           <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer required">
                            <label><liferay-ui:message key="Gender" /></label>
                           		<aui:select cssClass="browser-default custom-select custom-select-lg mb-4 signup_select"
		                         	label="" id="Gender" name="Gender"> 
		                         	<aui:option disabled="true" selected="true" value="">Select option</aui:option>
		                         	<c:forEach var="gender" items="${Gender}">
									 	<aui:option value="${gender.Id}">
									 	 	<c:choose>
												<c:when test="${lang == 'en_US'}">
													${gender.Text}
												</c:when>
												<c:when test="${lang == 'fr_FR'}">
													${gender.TextFr}
												</c:when>
												<c:when test="${lang == 'ar_SA'}">
													${gender.TextAr}
												</c:when>
											</c:choose>
										</aui:option> 
									 </c:forEach>
                     			</aui:select>
                           </div>
                           <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer required">
							<label><liferay-ui:message key="Country" /></label>
								 <aui:select cssClass="browser-default custom-select custom-select-lg mb-4 signup_select"
									label="" id="Country" name="Country"> 
									<aui:option disabled="true" selected="true" value=""><liferay-ui:message key="SelectOption" /></aui:option>
									 <c:forEach var="nationality" items="${Country}">
									 	<aui:option value="${nationality.Id}">
									 	 	<c:choose>
												<c:when test="${lang == 'en_US'}">
													${nationality.Text}
												</c:when>
												<c:when test="${lang == 'fr_FR'}">
													${nationality.TextFR}
												</c:when>
												<c:when test="${lang == 'ar_SA'}">
													${nationality.TextAR}
												</c:when>
											</c:choose>
										</aui:option> 
									 </c:forEach>
								</aui:select>
                           </div> 
                           <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="Caza" /></label>
                           		<aui:select cssClass="browser-default custom-select custom-select-lg mb-4 signup_select"
		                         	label="" id="Kaza" name="Kaza" disabled="true"> 
		                         	<aui:option disabled="true" selected="true" value=""><liferay-ui:message key="SelectOption" /></aui:option>
		                         	<c:forEach var="elt" items="${Kaza}">
										<aui:option value="${elt.Id}">
											<c:choose>
												<c:when test="${lang == 'en_US'}">
													${elt.Text}
												</c:when>
												<c:when test="${lang == 'fr_FR'}">
													${elt.TextFR}
												</c:when>
												<c:when test="${lang == 'ar_SA'}">
													${elt.TextAR}
												</c:when>
											</c:choose>
										</aui:option> 
									</c:forEach>
                     			</aui:select>
                           </div>
                           <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer" style="position: relative">
							<label><liferay-ui:message key="MobileNumber" /></label>
								<aui:input cssClass="phoneNumberChars phone customMobile" max="12" id="MobileNumber" name="MobileNumber" label="" placeholder="00 000 000">
	                           		<span class="mobileFixed">+961</span>
	                           		<span class="spanBorderMobile"></span>
								</aui:input>
                           </div>
                             <div class="col-12 col-md-6 col-xl-4 mt-4 required inputContainer">
							<label><liferay-ui:message key="Email" /></label>
								<aui:input type="email" cssClass="verifyEmail" id="Email" name="Email" label="" autocomplete="off"></aui:input>
                           </div>
                           <div class="col-12 col-md-6 col-xl-4 mt-4 required inputContainer">
							<label><liferay-ui:message key="Password" /></label>
								<aui:input cssClass="password" type="password" label=""  id="Password" name="Password" autocomplete="off"></aui:input>
                           </div>
                           <div class="col-12 col-md-6 col-xl-4 mt-4 required inputContainer">
							<label><liferay-ui:message key="ConfirmPassword" /></label>
						     <aui:input cssClass="ConfirmPassword" type="password" label="" id="ConfirmPassword" name="ConfirmPassword" autocomplete="off"></aui:input>
                           </div>
                           <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer required">
							<label><liferay-ui:message key="Profession" /></label>
                               <aui:select cssClass="browser-default custom-select custom-select-lg mb-4 signup_select"
		                         	label="" id="Profession" name="Profession"> 
		                         	<aui:option disabled="true" selected="true" value=""><liferay-ui:message key="SelectOption" /></aui:option>
		                         	<c:forEach var="prof" items="${Profession}">
										<aui:option value="${prof.Id}">
											<c:choose>
												<c:when test="${lang == 'en_US'}">
													${prof.Text}
												</c:when>
												<c:when test="${lang == 'fr_FR'}">
													${prof.TextFR}
												</c:when>
												<c:when test="${lang == 'ar_SA'}">
													${prof.TextAR}
												</c:when>
											</c:choose>
										</aui:option> 
									</c:forEach>
                     			</aui:select>
                           </div>
                           <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="Company" />/<liferay-ui:message key="Organization" />/<liferay-ui:message key="AcademicInstitution" /></label>
                           		<aui:select cssClass="browser-default custom-select custom-select-lg mb-4 signup_select"
		                         	label="" id="Company" name="Company"> 
		                         	<aui:option disabled="true" selected="true" value=""><liferay-ui:message key="SelectOption" /></aui:option>
		                         	<c:forEach var="elt" items="${compnayDropDown}">
										<aui:option value="${elt.Company}--${elt.Id}">
											${elt.Company}
										</aui:option> 
									</c:forEach>
									<aui:option value="other"><liferay-ui:message key="Other" /></aui:option> 
                     			</aui:select>
                           </div>
                           <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer" id="otherDiv">
							<label><liferay-ui:message key="Other" /></label>
                               <aui:input cssClass="" id="Other" name="Other" label="" disabled="true"></aui:input>
                           </div>
                           <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="LinkToWebsite" />/<liferay-ui:message key="SocialMedia" /></label>
                               <aui:input cssClass="" id="LinkToWebsite" name="LinkToWebsite" label=""></aui:input>
                           </div>
                           <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="LinkedInProfileURL" /></label>
                               <aui:input cssClass="" id="LinkedIn" name="LinkedIn" label=""></aui:input>
                           </div>
                          <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer">
	                    		<label><liferay-ui:message key="ProfileImage" /></label>
			                    <div class="add-innovation-dropdown uploadButton-div"> 
			                         <aui:input style="opacity:0;position: absolute;" type="file" id="coverImage" name="coverImage" label="" 
			                				accept="image/*" hidden="hidden"></aui:input>
			                         <button type="button" id="custom-button" class="uploadButton">
			                          <img src="/o/energy-hub-theme/images/upload.svg"><liferay-ui:message key="UploadFile" />
			                      	</button>
			                      	<span id="coverImage-text"><liferay-ui:message key="NoFileChosen" /></span> 
			                     </div>
		                	</div>
<!--                            <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainerCheckbox"> -->
<!-- 							<label><liferay-ui:message key="SubscribeToNewsLetters" /></label> -->
<%--                               <aui:input id="SubscribeToNewsletters" type="checkbox" name="SubscribeToNewsletters" value="true" label="" /> --%>
<!--                            </div> -->
                       </div>
                       <div class="d-flex justify-content-end fixBtns my-5">
							<button class="blueBorderBtn reset mt-3 mx-3" type="" id="clearCreateAccount"><liferay-ui:message key="Clear" /></button>
							<aui:button cssClass="blueBtn submit mt-3" type="submit" onclick="ajaxCall('key')" id="save" value="Send"></aui:button> 
                       </div>
                        <aui:input id="Id" value ="${Id}" name="Id" type="hidden"></aui:input>
						<aui:input id="UserApplicationRoleId" value ="${UserApplicationRoleId}" name="UserApplicationRoleId" type="hidden"></aui:input>
						<aui:input id="logoHidden" value ="${logoHidden}" name="logoHidden" type="hidden"></aui:input>
						<aui:input id="edit" value ="${edit}" name="edit" type="hidden"></aui:input> 
						<aui:input id="coverImageName" value ="${logoName}" name="coverImageName" type="hidden"></aui:input> 
                   </aui:form>

               </div>
           </div>    
   </section> 
</div>
     
<!--invite popup-->
<div class="modal fade" id="invite" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
	    	<div class="modal-header" id="modal-header">
	        	<h4 class="modal-title" id="modal-title"><liferay-ui:message key="UserInformation" /></h4>
	            <button type="button" class="close" data-dismiss="modal" id="closeLearnMore">X</button>
	       	</div>
	       	<div class="modal-body" id="modal-body">
	        	<p>${userLearnMoreDescription}</p>
	       	</div>
        </div>
    </div>
</div>

<div class="modal fade" id="CreateResponse" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
	    	<div class="modal-header" id="modal-header">
	        	<h4 class="modal-title" id="CreateResponse-title"></h4>
	            <button type="button" class="close" data-dismiss="modal" id="closeWarning">X</button>
	       	</div>
	       	<div class="modal-body" id="modal-body">
	        	<p id="CreateResponseText"></p>
	       	</div>
        </div>
    </div>
</div>
 
<script src="/o/energy-hub-theme/js/jquery.allowed-chars.min.js"></script> 
<script>
	setTimeout(function(){
	  $('#Modal').modal('hide')
	}, 4000);
	
	$('#CreateResponse').on('click', 'button.close', function (eventObject) {
		$('#CreateResponse').modal('hide');
	});
	$('#clearCreateAccount').on('click', function (e) {
		e.preventDefault();
		$("form")[0].reset();
		$(this).parent().find('.error').remove()
		document.getElementById("<portlet:namespace />coverImageName").value ='';
		$("span.error").each(function () {
	        $(this).remove();
	    });
		  $("html, body").animate({ scrollTop: 0 }, "slow");
	}); 
	
    gsap.to(".green circle", {
        duration: 6,
        opacity: 0.5,
        scale: 0.8,
        ease: "sine.inOut",
        stagger: { yoyo: true, repeat: -1, each: 0.4 }
    });
    gsap.to(".blue circle", {
        duration: 4,
        opacity: 0.5,
        scale: 0.8,
        ease: "sine.inOut",
        stagger: { yoyo: true, repeat: -1, each: 0.4 }
    });
    gsap.to(".orange circle", {
        duration: 2,
        opacity: 0.5,
        scale: 0.8,
        ease: "sine.inOut",
        stagger: { yoyo: true, repeat: -1, each: 0.4 }
    });
    gsap.to(".green2 circle", {
        duration: 4,
        opacity: 0.5,
        scale: 0.8,
        ease: "sine.inOut",
        stagger: { yoyo: true, repeat: -1, each: 0.4 }
    });
    $( document ).ready(function() {
    	$("input").removeClass("form-control");
    	$("select").removeClass("form-control");
    	$("div").removeClass("form-group");
    	$("#goback").prop("href", document.referrer);
    	 
    	document.getElementById("<portlet:namespace/>DateOfBirth").setAttribute("max", getFormattedDate18YearsAgo(new Date()));

		function getFormattedDate18YearsAgo(date) {
			var day = ('0' + date.getDate()).slice(-2);
	  	 	var month = ('0' + (date.getMonth() + 1)).slice(-2);
		   	var year = date.getFullYear().toString() - 18;
		   	return year + '-' + month + '-' + day;
		}
    	 
    }); 
    
    $('#<portlet:namespace/>Country').on('change', function() {
  	  	if(this.value == "202")
  	  		$('#<portlet:namespace/>Kaza').prop("disabled", false);
  		if(this.value != "202") {
  			$('#<portlet:namespace/>Kaza').val('').change();
  	  		$('#<portlet:namespace/>Kaza').prop("disabled", true);
  		}	
 	});
    $('#<portlet:namespace/>Company').on('change', function() {
  	  	if(this.value == "other") {
  	  		$('#<portlet:namespace/>Other').prop("disabled", false);
	  	  	$('#otherDiv').addClass("required");
  	  	}
  		if(this.value != "other") {
  			$('#<portlet:namespace/>Other').val('').change();
  	  		$('#<portlet:namespace/>Other').prop("disabled", true);
  	  		$('#otherDiv').removeClass("required");
  		}	
 	});
    
	var realFileBtn = $("#<portlet:namespace/>coverImage");
	var customBtn = document.getElementById("custom-button");
	var customTxt = document.getElementById("coverImage-text");
	var base64String;
	
	customBtn.onclick = function() {
		realFileBtn.click();
	};
	
	realFileBtn.change(function() {
	    if (realFileBtn.val()) {
	        if (this.files && this.files[0]) {
        		document.getElementById("<portlet:namespace />logoHidden").value = "";
	            var reader = new FileReader();
	            reader.onload = function(e) {
	            	var name = realFileBtn.val().match(/[\/\\]([\w\d\s\.\-\(\)]+)$/)[1];
	            	customTxt.innerHTML = name;
            		document.getElementById("<portlet:namespace />coverImageName").value = name;
            		base64String = e.target.result;
	            };
	            reader.readAsDataURL(this.files[0]);
	          }
	    } else {
	        customTxt.innerHTML = "No file chosen";
	    }
	});
	
 	if(${edit} == true) {
 		$('#<portlet:namespace/>Email').prop("disabled", true);
 		document.getElementById("<portlet:namespace />Id").value = "${userData.Id}";
 		document.getElementById("<portlet:namespace />UserApplicationRoleId").value = "${userData.UserApplicationRoleId}";
 		
		document.getElementById("<portlet:namespace />FirstName").value = "${userData.FirstName}";
		document.getElementById("<portlet:namespace />LastName").value = "${userData.LastName}";
		document.getElementById("<portlet:namespace />DateOfBirth").value = "${userData.DateOfBirth}";
		document.getElementById("<portlet:namespace />Email").value = "${userData.Email}";
		document.getElementById("<portlet:namespace />MobileNumber").value = "${userData.MobileNumber}";
		document.getElementById("<portlet:namespace />LinkToWebsite").value = "${userData.LinkToWebsite}";
// 		document.getElementById("<portlet:namespace />SubscribeToNewsletters").value = "${userData.SubscribeToNewsletters}";
		document.getElementById("<portlet:namespace />LinkedIn").value = "${userData.LinkedIn}";
		document.getElementById("<portlet:namespace />logoHidden").value = "${userData.logoHidden}"; 
		document.getElementById("coverImage-text").innerHTML  = "${userData.logoName}"; 
		$('.customCoverImage').removeClass("required"); 
        $('.customCoverImage > label').addClass("requiredCustom"); 
		
		$("#<portlet:namespace />Gender").val("${userData.Gender}").change();
		$("#<portlet:namespace />Profession").val("${userData.Profession}").change();
		$("#<portlet:namespace />Country").val("${userData.Country}").change();
		$("#<portlet:namespace />Kaza").val("${userData.Kaza}").change();
		if("${userData.Other}" !=""){
			$("#<portlet:namespace />Company").val("other").change();
		}else $("#<portlet:namespace />Company").val("${userData.Company}").change();
		document.getElementById("<portlet:namespace />Other").value = "${userData.Other}";
 	}

 	function ajaxCall(key) {
 		var xhr = new XMLHttpRequest();
 		var formData = new FormData();
 		var data = {
			 <portlet:namespace />key: "add-edit",
 			 <portlet:namespace />FirstName: $("#<portlet:namespace/>FirstName").val(),
 			 <portlet:namespace />LastName: $("#<portlet:namespace/>LastName").val(),
 			 <portlet:namespace />DateOfBirth: $("#<portlet:namespace/>DateOfBirth").val(),
 			 <portlet:namespace />Gender: $("#<portlet:namespace/>Gender").val(),
 			 <portlet:namespace />Country: $("#<portlet:namespace/>Country").val(),
 			 <portlet:namespace />Kaza: $("#<portlet:namespace/>Kaza").val(),
 			 <portlet:namespace />MobileNumber: $("#<portlet:namespace/>MobileNumber").val(),
 			 <portlet:namespace />Email: $("#<portlet:namespace/>Email").val(),
 			 <portlet:namespace />Password: $("#<portlet:namespace/>Password").val(),
 			 <portlet:namespace />Profession: $("#<portlet:namespace/>Profession").val(),
 			 <portlet:namespace />Company: $("#<portlet:namespace/>Company").val(),
 			 <portlet:namespace />Other: $("#<portlet:namespace/>Other").val(),
 			 <portlet:namespace />LinkToWebsite: $("#<portlet:namespace/>LinkToWebsite").val(),
 			 <portlet:namespace />LinkedIn: $("#<portlet:namespace/>LinkedIn").val(),
//  			 <portlet:namespace />SubscribeToNewsletters: $("#<portlet:namespace/>SubscribeToNewsletters").val(),
 			 <portlet:namespace />Id: $("#<portlet:namespace/>Id").val(),
 			 <portlet:namespace />UserApplicationRoleId: $("#<portlet:namespace/>UserApplicationRoleId").val(),
 			 <portlet:namespace />edit: $("#<portlet:namespace/>edit").val()
 		};
 		
 		var file = $("#<portlet:namespace/>coverImage")[0];
 		if( file && file.files.length ) {
//  			formData.append('<portlet:namespace />coverImage', file.files[0]);
 			formData.append('<portlet:namespace />coverImageName', file.files[0].name);
 			formData.append('<portlet:namespace />coverImage', base64String);
 			formData.append('<portlet:namespace />coverImageSize', file.files[0].size);
 			formData.append('<portlet:namespace />logoHidden',  $("#<portlet:namespace/>logoHidden").val());
 		}
 		
 		xhr.onloadend = function (e) {
 			var res = xhr.responseText.toLowerCase();
 			if(res.includes("id")) {
 				if(${edit} == true) {
 					location.reload();
 				} else{
				 	if(location.pathname.split('/')[0] == "ar") location.href= '/ar/home';
 		            else location.href= '/en/home';
				}
 			}else if(res.includes("emailexist")) {
 				document.getElementById("CreateResponse-title").innerHTML  = "Warning !";
	 			document.getElementById("CreateResponseText").innerHTML  = "Email Exists";
	 			$('#CreateResponse').modal('show');
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
</script>
