<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

<portlet:actionURL var="createAccountURL" />
<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>
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

                       <a id="goback" class="grayText px-2" href=""><liferay-ui:message key="Back" /></a>
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
							<span class=""><liferay-ui:message key="YouAreRegistering" /> <span class="bold"><liferay-ui:message key="CompanyUser" /></span></span>
							<div class="mb-0 w-100 ">${userDescription}
								<a class="bold learnMore px-2" data-toggle="modal" data-target="#invite">
								<liferay-ui:message key="LearnMore" /></a>
							</div>
                           </div>
                       </div>
                   </div>
                   </c:if>
				<aui:form id="createAccount_form" cssClass="mt-5 registerationForm" accept-charset="UTF8" method="POST">
                          <h1><liferay-ui:message key="PersonalInformation" /></h1>
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
							<aui:input id="DateOfBirth" name="DateOfBirth" label="" type="date" onkeydown="return false"></aui:input>
                          </div>
                          <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer required">
                            <label><liferay-ui:message key="Gender" /></label>
                          		<aui:select cssClass="browser-default custom-select custom-select-lg mb-4 signup_select"
	                         	label="" id="Gender" name="Gender"> 
	                         	<c:forEach var="elt" items="${Gender}">
									 	<aui:option value="${elt.Id}">
									 	 	<c:choose>
												<c:when test="${lang == 'en_US'}">
													${elt.Text}
												</c:when>
												<c:when test="${lang == 'fr_FR'}">
													${elt.TextFr}
												</c:when>
												<c:when test="${lang == 'ar_SA'}">
													${elt.TextAr}
												</c:when>
											</c:choose>
										</aui:option> 
									 </c:forEach>
                    			</aui:select>
                          </div> 
                          <div class="col-12 col-md-6 col-xl-4 mt-4 required inputContainer" style="position: relative">
                            <label><liferay-ui:message key="MobileNumber" /></label>
							<aui:input cssClass="phoneNumberChars phone customMobile" placeholder="00 000 000" max="12" id="MobileNumber" name="MobileNumber" label=""></aui:input>
                          	<span class="mobileFixed">+961</span>
							<span class="spanBorderMobile"></span>
                          </div>
                            <div class="col-12 col-md-6 col-xl-4 mt-4 required inputContainer">
								<label><liferay-ui:message key="Email" /></label>
							<aui:input type="email" cssClass="verifyEmail" id="Email" name="Email" label=""></aui:input>
                          </div>
                          <div class="col-12 col-md-6 col-xl-4 mt-4 required inputContainer">
                            <label><liferay-ui:message key="Password" /></label>
								<aui:input cssClass="password" type="password" label=""  id="Password" name="Password" ></aui:input>
                           </div>
                           <div class="col-12 col-md-6 col-xl-4 mt-4 required inputContainer">
                            <label><liferay-ui:message key="ConfirmPassword" /></label>
						     <aui:input cssClass="ConfirmPassword" type="password" label="" id="ConfirmPassword" name="ConfirmPassword"></aui:input>
                           </div>
                           
                          <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer required">
							  
                            <label><liferay-ui:message key="Profession" /></label>
                             	<aui:select cssClass="browser-default custom-select custom-select-lg mb-4 signup_select"
	                         	label="" id="Profession" name="Profession"> 
	                         	<aui:option disabled="true" selected="true" value=""><liferay-ui:message key="SelectOption" /></aui:option>
		                         	<c:forEach var="elt" items="${Profession}">
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
                          <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer">
                            <label><liferay-ui:message key="LinkedInProfileURL" /></label>
                               <aui:input cssClass="" id="LinkedIn" name="LinkedIn" label=""></aui:input>
                           </div>
<!--                           <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainerCheckbox"> -->
<!--                             <label><liferay-ui:message key="SubscribeToNewsLetters" /></label> -->
<%--                              <aui:input id="SubscribeToNewsletters" type="checkbox" name="SubscribeToNewsletters" value="true" label="" /> --%>
<!--                           </div> -->
                      </div>
                      <div class="separator"></div>
                           <h1><liferay-ui:message key="CompanyInformation" /></h1>
                       <div class="row">
                           <div class="col-12 col-md-6 col-xl-4 mt-4 required inputContainer">
                            <label><liferay-ui:message key="Name" /></label>
						     <aui:input cssClass="" label="" id="Company" name="Company"></aui:input>
                           </div>
                           <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer">
                            <label><liferay-ui:message key="Website" /></label>
						     <aui:input cssClass="" label="" id="CompanyWebsite" name="CompanyWebsite"></aui:input>
                           </div>
                           <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer" style="position: relative">
	                            <label><liferay-ui:message key="MobileNumber" /></label>
								<aui:input cssClass="phoneNumberChars phone customMobile" placeholder="00 000 000" max="12" id="CompanyPhoneNumber" name="CompanyPhoneNumber" label=""></aui:input>
                         		<span class="mobileFixed">+961</span>
								<span class="spanBorderMobile"></span>
                          	</div>
                           <div class="col-12 col-md-6 col-xl-4 mt-4 required inputContainer">
	                    		<label><liferay-ui:message key="Logo" /></label>
			                    <div class="add-innovation-dropdown uploadButton-div"> 
			                         <aui:input style="opacity:0;position: absolute;" type="file" id="coverImage" name="coverImage" label="" 
			                				hidden="hidden" accept="image/jpeg,image/png,image/jpg"></aui:input>
			                         <button type="button" id="custom-button" class="uploadButton"/>
			                          <img src="/o/energy-hub-theme/images/upload.svg"><liferay-ui:message key="UploadFile" />
			                      	</button>
			                      	<span id="coverImage-text"><liferay-ui:message key="NoFileChosen" /></span> 
			                     </div>
		                	</div>
                           <div class="col-12 col-md-6 col-xl-4 mt-4 required inputContainer">
	                    		<label><liferay-ui:message key="RegistrationDocument" /></label>
			                    <div class="add-innovation-dropdown uploadButton-div"> 
			                         <aui:input style="opacity:0;position: absolute;" type="file" id="Files" name="Files" label="" 
			                				hidden="hidden" accept="image/jpeg,image/png,image/jpg,application/pdf"></aui:input>
			                         <button type="button" id="files-custom-button" class="uploadButton"/>
			                          <img src="/o/energy-hub-theme/images/upload.svg"> <liferay-ui:message key="UploadFile" />
			                      	</button>
			                      	<span id="files-text"><liferay-ui:message key="NoFileChosen" /></span> 
			                     </div>
		                	</div>
                           <div class="col-12 col-md-6 col-xl-4 mt-4 inputContainer required">
							<label><liferay-ui:message key="Address" /></label>
							 	<aui:select cssClass="browser-default custom-select custom-select-lg mb-4 signup_select"
								label="" id="Country" name="Country"> 
								<aui:option disabled="true" selected="true" value=""><liferay-ui:message key="SelectOption" /></aui:option>
								 <c:forEach var="elt" items="${Country}">
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
                           <div class="col-12 col-md-6 col-xl-4 mt-4 required inputContainer">
							<label><liferay-ui:message key="NumberOfEmployees" /></label>
						     	<aui:select
		                            cssClass="browser-default custom-select custom-select-lg mb-4 signup_select"
		                            label="" id="NumberOfEmployees" name="NumberOfEmployees"> 
		                            <aui:option disabled="true" selected="true" value=""><liferay-ui:message key="SelectOption" /></aui:option>
			                            <c:forEach var="elt" items="${NumberOfEmployees}">
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
                           <div class="col-12 col-md-6 col-xl-4 mt-4 required inputContainer">
							<label><liferay-ui:message key="YearOfEstablishment" /></label>
                           		<aui:select
		                            cssClass="browser-default custom-select custom-select-lg mb-4 signup_select"
		                            label="" id="YearOfEstablishment" name="YearOfEstablishment"> 
		                            <aui:option disabled="true" selected="true" value=""><liferay-ui:message key="SelectOption" /></aui:option>
			                            <c:forEach var="year" items="${YearOfEstablishment}">
											<aui:option value="${year}">
												${year}
											</aui:option> 
										</c:forEach>
		                        </aui:select>
                           </div>
                               <div class="col-12 col-md-6 mt-4 inputContainer required">
								<label><liferay-ui:message key="MainBusinessActivity" /></label>
                                   <aui:select
		                            cssClass="browser-default custom-select custom-select-lg mb-4 signup_select" multiple="true"
		                            label="" id="MainBusinessActivity" name="MainBusinessActivity"> 
			                            <c:forEach var="elt" items="${MainBusinessActivity}">
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
                               <div class="col-12 col-md-6 mt-4 inputContainer required">
								<label><liferay-ui:message key="EnergyhubExpectations" /></label>
                                  <aui:select
		                            cssClass="browser-default custom-select custom-select-lg mb-4 signup_select"
		                            label="" id="Expectations" name="Expectations"> 
		                            <aui:option disabled="true" selected="true" value=""><liferay-ui:message key="SelectOption" /></aui:option>
			                            <c:forEach var="elt" items="${Expectations}">
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
                           </div>
                       </div>
                      <div class="d-flex justify-content-end fixBtns my-5">
						<button class="blueBorderBtn reset mt-3 mx-3" type="" id="clearCreateAccount"><liferay-ui:message key="Clear" /></button>
				   		<aui:button cssClass="blueBtn submit mt-3" type="submit" onclick="ajaxCall('key')" id="save" value="Send"></aui:button>
                      </div>
                      
                      	<aui:input id="Id" value ="${Id}" name="Id" type="hidden"></aui:input>
						<aui:input id="UserApplicationRoleId" value ="${UserApplicationRoleId}" name="UserApplicationRoleId" type="hidden"></aui:input>
						<aui:input id="logoHidden" value ="${logoHidden}" name="logoHidden" type="hidden"></aui:input>
						<aui:input id="filesHidden" value ="${filesHidden}" name="filesHidden" type="hidden"></aui:input>
						<aui:input id="edit" value ="${edit}" name="edit" type="hidden"></aui:input> 
						<aui:input id="coverImageName" value ="${logoName}" name="coverImageName" type="hidden"></aui:input> 
						<aui:input id="filesImageName" value ="${filesName}" name="filesImageName" type="hidden"></aui:input> 
                   
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
	            <button type="button" class="close" data-dismiss="modal" data-dismiss="modal" id="closeLearnMore">X</button>
	       	</div>
		  	<!-- Modal body -->
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
	$('#CreateResponse').on('click', 'button.close', function (eventObject) {
		$('#CreateResponse').modal('hide');
	});
	
	$('#clearCreateAccount').on('click', function (e) {
		e.preventDefault();
		$("form")[0].reset();
		$(this).parent().find('.error').remove()
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
    
    
    var customTxt = '';
	var docTypes = ['jpg', 'jpeg', 'png', 'pdf'];
	var logoTypes = ['jpg', 'jpeg', 'png'];
	var logoFile = $("#<portlet:namespace/>coverImage");
	var docsFile = $("#<portlet:namespace/>Files");
	var logoFileBtn = document.getElementById("custom-button");
	var docsFileBtn = document.getElementById("files-custom-button");
	var coBase64String;
	var filesBase64String;
	
	logoFile.change(function() {
		clickCustomButton(this, 'coverImage', 'coverImage-text');
	});
	docsFile.change(function() {
		clickCustomButton(this, 'Files', 'files-text');
	});
	
    function clickCustomButton(elt, realFileBtn, text) {
    	customTxt = document.getElementById(text);
    	var ext;
       	if ($("#<portlet:namespace/>" + realFileBtn).val()) {
           	if (elt.files && elt.files[0]) {
       			if(realFileBtn == "coverImage"){
	        		document.getElementById("<portlet:namespace />logoHidden").value = "";
					ext = logoTypes;				
       			}
       			if(realFileBtn == "Files"){
       				document.getElementById("<portlet:namespace />filesHidden").value = "";
					ext = docTypes;    				
       			}
   	            var reader = new FileReader();
   	            var extension = elt.files[0].name.split('.').pop().toLowerCase(),  //file extension from input file
  	             isSuccess = ext.indexOf(extension) > -1; 
  	            if (isSuccess) {
  	            	const size = (elt.files[0].size / 1024 / 1024).toFixed(2);
					if (size > 2) {
						customTxt.innerHTML = "File must be less than 2MB";
						realFileBtn.val(''); 
		            } else {
    	            	reader.onload = function(e) {
	    	            	var name = $("#<portlet:namespace/>" + realFileBtn).val().match(/[\/\\]([\w\d\s\.\-\(\)]+)$/)[1];
	    	            	customTxt.innerHTML = name;
	    	            	if(realFileBtn == "coverImage"){
		    	            	document.getElementById("<portlet:namespace />coverImageName").value = name;
		    	            	coBase64String = e.target.result;
	    	            	}
	            			if(realFileBtn == "Files"){
		    	            	document.getElementById("<portlet:namespace />filesImageName").value = name;
		    	            	filesBase64String = e.target.result;
	            			}
	    	            };
	    	            reader.readAsDataURL(elt.files[0]);
		            }
   	          	} else {
	                    customTxt.innerHTML = "Choose a valid File"; 
	                    realFileBtn.val(''); 
	            }
    	    } else {
    	        customTxt.innerHTML = "";
    	    }
		}
    }
    
 	if(${edit} == true) {
 		$('#<portlet:namespace/>Email').prop("disabled", true);
 		document.getElementById("<portlet:namespace />Id").value = "${userData.Id}";
 		document.getElementById("<portlet:namespace />UserApplicationRoleId").value = "${userData.UserApplicationRoleId}";
 		
		document.getElementById("<portlet:namespace />FirstName").value = "${userData.FirstName}";
		document.getElementById("<portlet:namespace />LastName").value = "${userData.LastName}";
		document.getElementById("<portlet:namespace />DateOfBirth").value = "${userData.DateOfBirth}";
		document.getElementById("<portlet:namespace />Email").value = "${userData.Email}";
		document.getElementById("<portlet:namespace />MobileNumber").value = "${userData.MobileNumber}";
		document.getElementById("<portlet:namespace />CompanyPhoneNumber").value = "${userData.CompanyPhoneNumber}";
// 		document.getElementById("<portlet:namespace />SubscribeToNewsletters").value = "${userData.SubscribeToNewsletters}";
		document.getElementById("<portlet:namespace />LinkedIn").value = "${userData.LinkedIn}";
		document.getElementById("<portlet:namespace />Company").value = "${userData.Other}";
		document.getElementById("<portlet:namespace />CompanyWebsite").value = "${userData.CompanyWebsite}";
		$("#<portlet:namespace />Gender").val("${userData.Gender}").change();
		$("#<portlet:namespace />Profession").val("${userData.Profession}").change();
		$("#<portlet:namespace />Country").val("${userData.Country}").change();
		$("#<portlet:namespace />Kaza").val("${userData.Kaza}").change();
		$("#<portlet:namespace />NumberOfEmployees").val("${userData.NumberOfEmployees}").change(); 
		$("#<portlet:namespace />YearOfEstablishment").val("${userData.YearOfEstablishment}").change(); 
		var act = "${userData.MainBusinessActivity}";
		$.each(act.replaceAll('/', ',').split(","), function(i,e){
			$("#<portlet:namespace />MainBusinessActivity option[value='" + e + "']").prop("selected", true);
		});
		$("#<portlet:namespace />Expectations").val("${userData.Expectations}").change(); 
		document.getElementById("<portlet:namespace />logoHidden").value = "${userData.logoHidden}"; 
		document.getElementById("<portlet:namespace />filesHidden").value = "${userData.filesHidden}";
		document.getElementById("coverImage-text").innerHTML = "${userData.logoName}"; 
		document.getElementById("files-text").innerHTML = "${userData.filesName}"; 
		$('.customCoverImage').removeClass("required"); 
        $('.customCoverImage > label').addClass("requiredCustom"); 
        $('.customFiles').removeClass("required"); 
        $('.customFiles > label').addClass("requiredCustom");
	}
 	
 	function ajaxCall(key) {
 		var xhr = new XMLHttpRequest();
 		var formData = new FormData();
 		var data = {
			 <portlet:namespace />key: "add-edit",
 			 <portlet:namespace />Id: $("#<portlet:namespace/>Id").val(),
 			 <portlet:namespace />UserApplicationRoleId: $("#<portlet:namespace/>UserApplicationRoleId").val(),
 			 <portlet:namespace />edit: $("#<portlet:namespace/>edit").val(),
 			 <portlet:namespace />FirstName: $("#<portlet:namespace/>FirstName").val(),
 			 <portlet:namespace />LastName: $("#<portlet:namespace/>LastName").val(),
 			 <portlet:namespace />DateOfBirth: $("#<portlet:namespace/>DateOfBirth").val(),
 			 <portlet:namespace />Gender: $("#<portlet:namespace/>Gender").val(),
 			 <portlet:namespace />MobileNumber: $("#<portlet:namespace/>MobileNumber").val(),
 			 <portlet:namespace />CompanyPhoneNumber: $("#<portlet:namespace/>CompanyPhoneNumber").val(),
 			 <portlet:namespace />Email: $("#<portlet:namespace/>Email").val(),
 			 <portlet:namespace />Password: $("#<portlet:namespace/>Password").val(),
 			 <portlet:namespace />Profession: $("#<portlet:namespace/>Profession").val(),
 			 <portlet:namespace />LinkedIn: $("#<portlet:namespace/>LinkedIn").val(),
//  			 <portlet:namespace />SubscribeToNewsletters: $("#<portlet:namespace/>SubscribeToNewsletters").val(),
 			 <portlet:namespace />Company: $("#<portlet:namespace/>Company").val(),
 			 <portlet:namespace />CompanyWebsite: $("#<portlet:namespace/>CompanyWebsite").val(),
 			 <portlet:namespace />Country: $("#<portlet:namespace/>Country").val(),
 			 <portlet:namespace />Kaza: $("#<portlet:namespace/>Kaza").val(),
 			 <portlet:namespace />NumberOfEmployees: $("#<portlet:namespace/>NumberOfEmployees").val(),
 			 <portlet:namespace />YearOfEstablishment: $("#<portlet:namespace/>YearOfEstablishment").val(),
 			 <portlet:namespace />MainBusinessActivity: $("#<portlet:namespace/>MainBusinessActivity").val(),
 			 <portlet:namespace />Expectations: $("#<portlet:namespace/>Expectations").val(),
 		};
 		var file = $("#<portlet:namespace/>Files")[0];
 		if( file && file.files.length ) {
//  			formData.append('<portlet:namespace />Files', file.files[0]);
 			formData.append('<portlet:namespace />FilesName', file.files[0].name);
 			formData.append('<portlet:namespace />Files', filesBase64String);
 			formData.append('<portlet:namespace />FilesSize', file.files[0].size);
 		}
 		var coImage = $("#<portlet:namespace/>coverImage")[0];
 		if( coImage && coImage.files.length ) {
//  			formData.append('<portlet:namespace />coverImage', coImage.files[0]);
 			formData.append('<portlet:namespace />coverImageName', coImage.files[0].name);
 			formData.append('<portlet:namespace />coverImage', coBase64String);
 			formData.append('<portlet:namespace />coverImageSize', coImage.files[0].size);
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
