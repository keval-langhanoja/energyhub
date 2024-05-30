<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<portlet:actionURL var="createProjectURL" />
<portlet:resourceURL var="testAjaxResourceUrl" />

<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
	rel="stylesheet">

<style>
.form-group {
	margin-bottom: 0;
}

.dashedBorder {
	border: 0.5px dashed #989898 !important;
}

.registerationForm .spanBorderMobile {
	border-right: none;
	padding-right: 2px;
	height: 59px;
	width: 20%;
	position: absolute;
	display: block;
	color: transparent;
	left: 301px !important;
	top: 25px !important;
	border-left: 0.5px solid #989898;
}

.registerationForm .mobileFixed {
	position: relative !important;
	display: block;
	left: 0;
	font-size: 16px;
	font-weight: bold;
	letter-spacing: 0px;
	color: #707070;
	z-index: 99;
	margin: 0;
	/* top: 50%; */
	transform: translateY(-50%);
}

@media screen and (max-width: 1200px) {
	.registerationForm .spanBorderMobile {
		width: 30% !important;
	}
}

.registerationForm input, .registerationForm select {
	border-radius: 0px;
}
.form-control[disabled], .form-control[readonly], fieldset[disabled] .form-control {
    background-color: #eee !important;
    opacity: 1;
}
</style>
<div class="MainContent">
	<!--signup section-->
	<section id="signup" class="signup userType position-relative">
		<div class="bgImage">
			<svg class="animatedDots orange" viewBox="0 0 500 200"
				xmlns="http://www.w3.org/2000/svg"
				style="left: 25%; top: 55%; right: auto">
				<circle cx="200" cy="50" r="50" />
			</svg>
			<svg class="animatedDots green2" fill="#8BB029" viewBox="0 0 500 200"
				xmlns="http://www.w3.org/2000/svg" style="left: 3%; top: 15%">
				<circle cx="200" cy="50" r="50" />
			</svg>
		</div>
		<div class="content position-relative mx-auto py-5 px-3">
			<div class=" d-flex justify-content-between titleSec"></div>
		</div>
		<div class="content mx-auto py-5 px-3">
			<svg class="animatedDots blue" viewBox="0 0 500 200"
				xmlns="http://www.w3.org/2000/svg" style="left: 1%; top: 10%">
				<circle cx="200" cy="50" r="50" />
			</svg>
			<svg class="animatedDots green2" fill="#8BB029" viewBox="0 0 500 200"
				xmlns="http://www.w3.org/2000/svg"
				style="left: 2%; top: 15%; right: auto">
				<circle cx="200" cy="50" r="50" />
			</svg>
			<div class="d-flex justify-content-between flex-wrap">
				<div href="" class="back">
					<svg xmlns="http://www.w3.org/2000/svg"
						xmlns:xlink="http://www.w3.org/1999/xlink" width="9.77"
						height="8.57" viewBox="0 0 9.77 8.57">
						<defs>
							<clipPath id="clip-path">
								<rect x="9" y="6" width="9.77" height="8.57" fill="none" />
							</clipPath>
							<filter id="Path_1325" x="-4.115" y="-6" width="22.885"
							height="26.57" filterUnits="userSpaceOnUse">
								<feOffset dy="3" input="SourceAlpha" />
								<feGaussianBlur stdDeviation="3" result="blur" />
								<feFlood flood-opacity="0.161" />
								<feComposite operator="in" in2="blur" />
								<feComposite in="SourceGraphic" />
							</filter>
							<filter id="Path_1325-2" x="-9" y="-6" width="22.885"
							height="26.57" filterUnits="userSpaceOnUse">
								<feOffset dy="3" input="SourceAlpha" />
								<feGaussianBlur stdDeviation="3" result="blur-2" />
								<feFlood flood-opacity="0.161" />
								<feComposite operator="in" in2="blur-2" />
								<feComposite in="SourceGraphic" />
							</filter>
						</defs>
						<g id="Group_846" data-name="Group 846"
							transform="translate(-151.071 -348.203)">
							<g id="Scroll_Group_1" data-name="Scroll Group 1"
							transform="translate(142.071 342.203)"
							clip-path="url(#clip-path)" style="isolation: isolate">
								<g id="Component_13_1" data-name="Component 13 – 1"
							transform="translate(9 6)">
									<g id="Group_844" data-name="Group 844"
							transform="translate(179.228 364.57) rotate(180)">
										<g id="Group_707" data-name="Group 707"
							transform="translate(169.458 364.57) rotate(-90)">
											<g id="Group_642" data-name="Group 642"
							transform="translate(0)">
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
											<g id="Group_642-2" data-name="Group 642"
							transform="translate(0 0)">
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
					<a class="grayText px-2"><liferay-ui:message key="Back" /></a>
				</div>
			</div>
			<div class="userTypeCard position-relative topBorder mt-3 py-5 px-5">
				<aui:form id="createProject_form" cssClass="mt-5 registerationForm"
					enctype="multipart/form-data" accept-charset="UTF8" method="POST">
					<aui:input id="p_r_p_categoryId" value="${p_r_p_categoryId}"
						name="p_r_p_categoryId" type="hidden" />
					<aui:input id="folderId" value="${folderId}" name="folderId"
						type="hidden" />
					<aui:input id="ddmStructureKey" value="${ddmStructureKey}"
						name="ddmStructureKey" type="hidden" />
					<aui:input id="ddmTemplateKey" value="${ddmTemplateKey}"
						name="ddmTemplateKey" type="hidden" />
					<h1>
						<liferay-ui:message key="GeneralInformation" />
					</h1>
					<div class="row">
						<div class="col-12 col-md-4 col-xl-4 mt-4 inputContainer required">
							<label><liferay-ui:message key="CarMake" /></label>
							<aui:select aria-labelledby="dropdownMenuButton"
								cssClass="browser-default custom-select custom-select-lg mb-3 signup_select"
								label="" id="CarMake" name="CarMake">
								<aui:option disabled="true" selected="true" value="">
									<liferay-ui:message key="SelectOption" />
								</aui:option>
								<c:forEach var="elt" items="${CarMake}">
									<aui:option cssClass="dropdown-item" value="${elt.id}">
										<liferay-ui:message key="${elt.value}" />
									</aui:option>
									<div class="dropdown-divider" />
								</c:forEach>
							</aui:select>
						</div>
						<div class="col-12 col-md-4 col-xl-4 mt-4 inputContainer required">
							<label><liferay-ui:message key="CarModel" /></label>
							<aui:input id="CarModel" name="CarModel" label=""></aui:input>
						</div>
						<div class="col-12 col-md-4 col-xl-4 mt-4 inputContainer required">
							<label><liferay-ui:message key="ModelYear" /></label>
							<aui:select aria-labelledby="dropdownMenuButton"
								cssClass="browser-default custom-select custom-select-lg mb-3 signup_select"
								label="" id="ModelYear" name="ModelYear">
								<aui:option disabled="true" selected="true" value="">
									<liferay-ui:message key="SelectOption" />
								</aui:option>
								<c:forEach var="elt" items="${ModelYear}">
									<aui:option value="${elt}">${elt}</aui:option>
									<div class="dropdown-divider" />
								</c:forEach>
							</aui:select>
						</div>
					</div>
					<div class="row">
						<div class="col-12 col-md-4 col-xl-4 mt-4   inputContainer"
							style="position: relative">
							<label><liferay-ui:message key="WebsiteLink" /></label>
							<aui:input id="CarWebsiteLink" name="CarWebsiteLink" label=""></aui:input>
						</div>
						<div class="col-12 col-md-4 col-xl-4 mt-4   inputContainer"
							style="position: relative">
							<label><liferay-ui:message key="Range" /></label>
							<aui:input id="Range" cssClass="NumOnly" name="Range" label=""></aui:input>
							<div class="d-flex justify-content-center spanBorderMobile">
								<div class="mobileFixed">Km</div>
							</div>
						</div>
						<div class="col-12 col-md-4">
							<div class="col-12 mt-4 p-0 inputContainer  ">
								<label><liferay-ui:message key="UploadCarImageFor" /></label>
								<div class="add-innovation-dropdown uploadButton-div">
									<aui:input style="opacity:0;position: absolute;" type="file"
										id="CarImage" name="CarImage" label="" accept="image/*"
										hidden="hidden" />
									<button type="button" id="custom-button" class="uploadButton">
										<img src="/o/energy-hub-theme/images/upload.svg">
										<liferay-ui:message key="UploadFile" />
									</button>
									<span id="CarImage-text"><liferay-ui:message
											key="NoFileChosen" /></span>
									<aui:input id="FileEntryId" value="${FileEntryId}"
										name="FileEntryId" type="hidden" />
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div
							class="col-12 col-md-4 col-xl-4 mt-4   required inputContainer"
							style="position: relative">
							<label><liferay-ui:message key="PriceRangeFrom" /></label>
							<aui:input id="PriceRangeFrom" cssClass="NumOnly"
								name="PriceRangeFrom" label=""></aui:input>
							<div class="d-flex justify-content-center spanBorderMobile">
								<div class="mobileFixed">$</div>
							</div>
						</div>
						<div class="col-12 col-md-4 col-xl-4 mt-4 validNumber inputContainer"
							style="position: relative">
							<label><liferay-ui:message key="PriceRangeTo" /></label>
							<aui:input id="PriceRangeTo" cssClass="NumOnly"
								name="PriceRangeTo" label="" disabled="true"></aui:input>
							<div class="d-flex justify-content-center spanBorderMobile">
								<div class="mobileFixed">$</div>
							</div>
						</div>
						<div class="col-12 col-md-4 col-xl-4 mt-4 inputContainer required">
							<label><liferay-ui:message key="FuelType" /></label>
							<aui:select aria-labelledby="dropdownMenuButton"
								cssClass="browser-default custom-select custom-select-lg mb-3 signup_select"
								label="" id="FuelType" name="FuelType">
								<aui:option disabled="true" selected="true" value="">
									<liferay-ui:message key="SelectOption" />
								</aui:option>
								<c:forEach var="elt" items="${FuelType}">
									<aui:option cssClass="dropdown-item" value="${elt.id}">
										<liferay-ui:message key="${elt.value}" />
									</aui:option>
									<div class="dropdown-divider" />
								</c:forEach>
							</aui:select>
						</div>
					</div>
					<div class="row">
						<div class="col-12 col-md-4 col-xl-4 mt-4 hide inputContainer"
							id="ChargingTimeDiv" style="position: relative">
							<label><liferay-ui:message key="ChargingTime" /></label>
							<aui:input id="ChargingTime" cssClass="NumOnly"
								name="ChargingTime" label=""></aui:input>
							<div class="d-flex justify-content-center spanBorderMobile">
								<div class="mobileFixed">h/240V</div>
							</div>
						</div>
						<div class="col-12 col-md-4 col-xl-4 mt-4   inputContainer"
							style="position: relative">
							<label><liferay-ui:message key="FuelEfficiency" /></label>
							<aui:input id="FuelEfficiency" cssClass="NumOnly"
								name="FuelEfficiency" label=""></aui:input>
							<div class="d-flex justify-content-center spanBorderMobile">
								<div class="mobileFixed">
									kwh/</br>100km
								</div>
							</div>
						</div>
						<div class="col-12 col-md-4 col-xl-4 mt-4   inputContainer"
							style="position: relative">
							<label><liferay-ui:message key="ElectricMotorBattery" /></label>
							<aui:input id="ElectricMotorBattery" cssClass="NumOnly"
								name="ElectricMotorBattery" label=""></aui:input>
						</div>
						<div class="col-12 col-md-4 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="Transmission" /></label>
							<aui:select aria-labelledby="dropdownMenuButton"
								cssClass="browser-default custom-select custom-select-lg mb-3 signup_select"
								label="" id="Transmission" name="Transmission">
								<aui:option disabled="true" selected="true" value="">
									<liferay-ui:message key="SelectOption" />
								</aui:option>
								<c:forEach var="elt" items="${Transmission}">
									<aui:option cssClass="dropdown-item" value="${elt.id}">
										<liferay-ui:message key="${elt.value}" />
									</aui:option>
									<div class="dropdown-divider" />
								</c:forEach>
							</aui:select>
						</div>
						<div
							class="col-12 col-md-4 col-xl-4 mt-4 inputContainer  required">
							<label><liferay-ui:message key="VehicleClassSize" /></label>
							<aui:select aria-labelledby="dropdownMenuButton"
								cssClass="browser-default custom-select custom-select-lg mb-3 signup_select"
								label="" id="VehicleClassSize" name="VehicleClassSize">
								<aui:option disabled="true" selected="true" value="">
									<liferay-ui:message key="SelectOption" />
								</aui:option>
								<c:forEach var="elt" items="${VehicleClassSize}">
									<aui:option cssClass="dropdown-item" value="${elt.id}">
										<liferay-ui:message key="${elt.value}" />
									</aui:option>
									<div class="dropdown-divider" />
								</c:forEach>
							</aui:select>
						</div>
						<div class="col-12 col-md-4 col-xl-4 mt-4 inputContainer hide"
							id="SedanSubClassDiv">
							<label><liferay-ui:message key="SedanSubClass" /></label>
							<aui:select aria-labelledby="dropdownMenuButton"
								cssClass="browser-default custom-select custom-select-lg mb-3 signup_select"
								label="" id="SedanSubClass" name="SedanSubClass">
								<aui:option disabled="true" selected="true" value="">
									<liferay-ui:message key="SelectOption" />
								</aui:option>
								<c:forEach var="elt" items="${SedanSubClass}">
									<aui:option cssClass="dropdown-item" value="${elt.id}">
										<liferay-ui:message key="${elt.value}" />
									</aui:option>
									<div class="dropdown-divider" />
								</c:forEach>
							</aui:select>
						</div>
						<div class="col-12 col-md-4 col-xl-4 mt-4 inputContainer hide"
							id="StationWagonSubClassDiv">
							<label><liferay-ui:message key="StationWagonSubClass" /></label>
							<aui:select aria-labelledby="dropdownMenuButton"
								cssClass="browser-default custom-select custom-select-lg mb-3 signup_select"
								label="" id="StationWagonSubClass" name="StationWagonSubClass">
								<aui:option disabled="true" selected="true" value="">
									<liferay-ui:message key="SelectOption" />
								</aui:option>
								<c:forEach var="elt" items="${StationWagonSubClass}">
									<aui:option cssClass="dropdown-item" value="${elt.id}">
										<liferay-ui:message key="${elt.value}" />
									</aui:option>
									<div class="dropdown-divider" />
								</c:forEach>
							</aui:select>
						</div>
						<div class="col-12 col-md-4 col-xl-4 mt-4 inputContainer hide"
							id="PickUpTruckSubClassDiv">
							<label><liferay-ui:message key="PickUpTruckSubClass" /></label>
							<aui:select aria-labelledby="dropdownMenuButton"
								cssClass="browser-default custom-select custom-select-lg mb-3 signup_select"
								label="" id="PickUpTruckSubClass" name="PickUpTruckSubClass">
								<aui:option disabled="true" selected="true" value="">
									<liferay-ui:message key="SelectOption" />
								</aui:option>
								<c:forEach var="elt" items="${PickUpTruckSubClass}">
									<aui:option cssClass="dropdown-item" value="${elt.id}">
										<liferay-ui:message key="${elt.value}" />
									</aui:option>
									<div class="dropdown-divider" />
								</c:forEach>
							</aui:select>
						</div>
						<div class="col-12 col-md-4 col-xl-4 mt-4 inputContainer hide"
							id="VanSubClassDiv">
							<label><liferay-ui:message key="VanSubClass" /></label>
							<aui:select aria-labelledby="dropdownMenuButton"
								cssClass="browser-default custom-select custom-select-lg mb-3 signup_select"
								label="" id="VanSubClass" name="VanSubClass">
								<aui:option disabled="true" selected="true" value="">
									<liferay-ui:message key="SelectOption" />
								</aui:option>
								<c:forEach var="elt" items="${VanSubClass}">
									<aui:option cssClass="dropdown-item" value="${elt.id}">
										<liferay-ui:message key="${elt.value}" />
									</aui:option>
									<div class="dropdown-divider" />
								</c:forEach>
							</aui:select>
						</div>
					</div>
					<div class="d-flex justify-content-end fixBtns my-5">
						<button class="blueBorderBtn reset mt-3 mx-3" type="" id="clear">
							<liferay-ui:message key="Clear" />
						</button>
						<aui:button cssClass="blueBtn submit mt-3" type="submit"
							onclick="saveValuestoStorgae()" id="save" value="Proceed" />
					</div>
				</aui:form>
			</div>
		</div>
	</section>
</div>
<script
	src="https://cdn.rawgit.com/localForage/localForage/4ce19202/dist/localforage.min.js"></script>
<script> 
	var obj = {};
	var fromLS = false, fileChanged = false;
	$(document).ready(function () {
		function validateEmail(email) {
			const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			return re.test(String(email).toLowerCase());
		}
		
		var img = document.createElement("img");
		img.src = "/o/energy-hub-theme/images/img/user.jpg";
		
		if(localStorage['passedFormValues'] != undefined){
			obj = JSON.parse(localStorage['passedFormValues']);
			if(obj != null && obj != {}) {
				fromLS = true;
				fillFormValues();
			}
		}
		
		if(${edit} == true) {
			document.getElementById("<portlet:namespace />CarModel").value = "${CarModelValue}";
			document.getElementById("<portlet:namespace />Range").value = "${RangeValue}";
			document.getElementById("<portlet:namespace />PriceRangeFrom").value = "${PriceRangeFromValue}";
			document.getElementById("<portlet:namespace />PriceRangeTo").value = "${PriceRangeToValue}";
			document.getElementById("<portlet:namespace />ChargingTime").value = "${ChargingTimeValue}";
			document.getElementById("<portlet:namespace />PriceRangeTo").value = "${PriceRangeToValue}";
			document.getElementById("<portlet:namespace />ElectricMotorBattery").value = "${ElectricMotorBatteryValue}";
			document.getElementById("<portlet:namespace />CarWebsiteLink").value = "${CarWebsiteLinkValue}";
			
			$("#<portlet:namespace />CarMake").val("${CarMakeValue}").change();
			$("#<portlet:namespace />ModelYear").val("${ModelYearValue}").change();
			$("#<portlet:namespace />FuelType").val("${FuelTypeValue}").change();
			$("#<portlet:namespace />FuelEfficiency").val("${FuelEfficiencyValue}").change();
			$("#<portlet:namespace />VehicleClassSize").val("${VehicleClassSizeValue}").change();
			$("#<portlet:namespace />SedanSubClass").val("${SedanSubClassValue}").change();
			$("#<portlet:namespace />StationWagonSubClass").val("${StationWagonSubClassValue}").change();
			$("#<portlet:namespace />PickUpTruckSubClass").val("${PickUpTruckSubClassValue}").change();
			$("#<portlet:namespace />VanSubClass").val("${VanSubClassValue}").change();
			$("#<portlet:namespace />Transmission").val("${TransmissionValue}").change(); 
			
			document.getElementById("<portlet:namespace />FileEntryId").value = "${FileEntryId}";
			document.getElementById("CarImage-text").innerHTML = "${CarImageName}"; 
		}
	});
	
	$('#<portlet:namespace/>VehicleClassSize').on('change', function() {
		$("#SedanSubClassDiv").addClass('hide');
		$("#StationWagonSubClassDiv").addClass('hide');
		$("#VanSubClassDiv").addClass('hide');
		$("#PickUpTruckSubClassDiv").addClass('hide');
		
		$("#<portlet:namespace/>SedanSubClass").val('').change();
		$("#<portlet:namespace/>StationWagonSubClass").val('').change();
		$("#<portlet:namespace/>VanSubClass").val('').change();
		$("#<portlet:namespace/>PickUpTruckSubClass").val('').change();
		
  	  	if(this.value == "Option42147336")
  	  		$("#SedanSubClassDiv").removeClass('hide');
  	  	if(this.value == "Option44328455")
  	  		$("#StationWagonSubClassDiv").removeClass('hide');
  	  	if(this.value == "Option91147950")
  	  		$("#VanSubClassDiv").removeClass('hide');
  	  	if(this.value == "Option09774728")
  	  		$("#PickUpTruckSubClassDiv").removeClass('hide');
 	});
	
	$('#<portlet:namespace/>PriceRangeFrom').on('change', function() {
		$('#<portlet:namespace/>PriceRangeTo').val('').change();
  	  	if(this.value != "") {
  	  		$('#<portlet:namespace/>PriceRangeTo').prop("disabled", false);
  	  		$("#<portlet:namespace/>PriceRangeTo").removeClass('disabled');
  	  		document.getElementById('<portlet:namespace/>PriceRangeTo').min=this.value;
  	  	}
  		if(this.value == "") {
  	  		$('#<portlet:namespace/>PriceRangeTo').prop("disabled", true);
  	  		$("#<portlet:namespace/>PriceRangeTo").addClass('disabled');
  		}	
 	});
	
	$('#<portlet:namespace/>PriceRangeTo').on('blur change keyup', function () {
	    if ($(this).val()) {
	        $(this).parent().parent().find('.error').remove();
	        if (parseInt($(this).val()) < parseInt($('#<portlet:namespace/>PriceRangeFrom').val())) {
	            if (screen.width > 767)
	                $("<span class='error'><img style='margin: 0 5px 3px' src='/o/energy-hub-theme/images/error.svg'>Please enter a valid number</span>").insertBefore(($(this).parent()));
	            else
	                $("<span class='error pt-1 mobile'><img style='margin: 0 5px 3px' src='/o/energy-hub-theme/images/error.svg'>Please enter a valid number</span>").insertAfter(($(this).parent()));
	        }
	    }
	});
	
	$('#<portlet:namespace/>FuelType').on('change', function() {
		$("#ChargingTimeDiv").addClass('hide');
		$("#<portlet:namespace/>ChargingTime").val('');
  	  	if(this.value == "Option49306406")
  	  		$("#ChargingTimeDiv").removeClass('hide');
 	});

	$('#clear').on('click', function (e) {
		e.preventDefault();
		$("form")[0].reset();
		$(this).parent().find('.error').remove()
		$("span.error").each(function () {
			$(this).remove();
		});
		$("html, body").animate({ scrollTop: 0 }, "slow");
	}); 

	var realFileBtn = $("#<portlet:namespace/>CarImage");
	var customBtn = document.getElementById("custom-button");
	var customTxt = document.getElementById("CarImage-text");
	var logoTypes = ['jpg', 'jpeg', 'png'];
	var base64String;
	
	customBtn.onclick = function() {
		realFileBtn.click();
	};

	realFileBtn.change(function() {
		if (realFileBtn.val()) {
			if (this.files && this.files[0]) {
				var reader = new FileReader();
				reader.onload = function(e) {
					customTxt.innerHTML = realFileBtn.val().match(/[\/\\]([\w\d\s\.\-\(\)]+)$/)[1];
					base64String = e.target.result;
				};
				reader.readAsDataURL(this.files[0]);
				fileChanged = true;
			  }
		} else {
			customTxt.innerHTML = "No file chosen";
		}
	}); 
	
	function saveValuestoStorgae() {
		var carImageName = "", carImageExt = "";
		if (!fileChanged && fromLS){
			base64String = obj.CarImage64;
			carImageName = obj.CarImageName;			
			carImageExt = obj.CarImageExt;			
		}else localStorage.removeItem( 'passedFormValues' );
		
		var file = $("#<portlet:namespace/>CarImage")[0];
		var carImage = "";
		if( file && file.files.length ) {
			carImage = file.files[0];
		}else carImage = null;
		
		var objLS = {
			"CarMake" : $("#<portlet:namespace/>CarMake").val(),
			"CarMakeValue" : $("#<portlet:namespace/>CarMake option:selected" ).text().trim(),
			"CarModel" : $("#<portlet:namespace/>CarModel").val(),
			"ModelYear" : $("#<portlet:namespace/>ModelYear").val(),
			"CarWebsiteLink" : $("#<portlet:namespace/>CarWebsiteLink").val(),
			"Range" : $("#<portlet:namespace/>Range").val(),
			"PriceRangeFrom" : $("#<portlet:namespace/>PriceRangeFrom").val(),
			"PriceRangeTo" : $("#<portlet:namespace/>PriceRangeTo").val(),
			"FuelType" : $("#<portlet:namespace/>FuelType").val(),
			"FuelTypeValue" : $("#<portlet:namespace/>FuelType option:selected" ).text().trim(),
			"ChargingTime" : $("#<portlet:namespace/>ChargingTime").val(),
			"FuelEfficiency" : $("#<portlet:namespace/>FuelEfficiency").val(),
			"ElectricMotorBattery" : $("#<portlet:namespace/>ElectricMotorBattery").val(),
			"Transmission" : $("#<portlet:namespace/>Transmission").val(),
			"TransmissionValue" : $("#<portlet:namespace/>Transmission option:selected" ).text().trim(),
			"VehicleClassSize" : $("#<portlet:namespace/>VehicleClassSize").val(),
			"VehicleClassSizeValue" : $("#<portlet:namespace/>VehicleClassSize option:selected" ).text().trim(),
			"SedanSubClass" : $("#<portlet:namespace/>SedanSubClass").val(),
			"SedanSubClassValue" : $("#<portlet:namespace/>SedanSubClass option:selected" ).text().trim(),
			"StationWagonSubClass" : $("#<portlet:namespace/>StationWagonSubClass").val(),
			"StationWagonSubClassValue" : $("#<portlet:namespace/>StationWagonSubClass option:selected" ).text().trim(),
			"PickUpTruckSubClass" : $("#<portlet:namespace/>PickUpTruckSubClass").val(),
			"PickUpTruckSubClassValue" : $("#<portlet:namespace/>PickUpTruckSubClass option:selected" ).text().trim(),
			"VanSubClass" : $("#<portlet:namespace/>VanSubClass").val(),
			"VanSubClassValue" : $("#<portlet:namespace/>VanSubClass option:selected" ).text().trim(),
			"CarImageName" : carImage != '' && carImage != null ?  carImage.name : "",
			"CarImage64" : base64String,
			"CarImageExt" : carImage != '' && carImage != null ?  carImage.name.split('.')[1] : "",
			"categId" : ${categId},
			"parentCategId" : ${parentCategId},
			"folderId" : ${folderId},
			"ddmStructureKey" : ${ddmStructureKey},
			"articleId" : "${articleId}",
			"FileEntryId" : "${FileEntryId}",
			"edit" : ${edit}
			
		};
		localStorage.setItem( 'passedFormValues', JSON.stringify(objLS) );
		window.location.href = location.origin +"/custom-forms?overview";
	}	
	
	function fillFormValues() {
		var obj = JSON.parse(localStorage['passedFormValues']);
		
		if(obj.CarMake != "" && obj.CarMake !="Select option"){
			$("#<portlet:namespace/>CarMake").val(obj.CarMake).change();
		}
		if(obj.CarModel != ""){
			$("#<portlet:namespace/>CarModel").val(obj.CarModel).change();
		}
		if(obj.ModelYear != "" && obj.CarMake !="Select option"){
			$("#<portlet:namespace/>ModelYear").val(obj.ModelYear).change();
		}
		if(obj.CarWebsiteLink != ""){
			$("#<portlet:namespace/>CarWebsiteLink").val(obj.CarWebsiteLink).change();
		}
		if(obj.Range != ""){
			$("#<portlet:namespace/>Range").val(obj.Range).change();
		}
		if(obj.PriceRangeFrom != ""){
			$("#<portlet:namespace/>PriceRangeFrom").val(obj.PriceRangeFrom).change();
		}
		if(obj.PriceRangeTo != ""){
			$("#<portlet:namespace/>PriceRangeTo").val(obj.PriceRangeTo).change();
		}
		if(obj.FuelType != "" && obj.FuelType !="Select option"){
			$("#<portlet:namespace/>FuelType").val(obj.FuelType).change();
		}
		if(obj.ChargingTime != ""){
			$("#<portlet:namespace/>ChargingTime").val(obj.ChargingTime).change();
		}
		if(obj.FuelEfficiency != ""){
			$("#<portlet:namespace/>FuelEfficiency").val(obj.FuelEfficiency).change();
		}
		if(obj.ElectricMotorBattery != ""){
			$("#<portlet:namespace/>ElectricMotorBattery").val(obj.ElectricMotorBattery).change();
		}
		if(obj.Transmission != "" && obj.Transmission !="Select option"){
			$("#<portlet:namespace/>Transmission").val(obj.Transmission).change();
		}
		if(obj.VehicleClassSize != "" && obj.VehicleClassSize !="Select option"){
			$("#<portlet:namespace/>VehicleClassSize").val(obj.VehicleClassSize).change();
		}
		if(obj.SedanSubClass != "" && obj.SedanSubClass !="Select option"){
			$("#<portlet:namespace/>SedanSubClass").val(obj.SedanSubClass).change();
		}
		if(obj.StationWagonSubClass != "" && obj.StationWagonSubClass !="Select option"){
			$("#<portlet:namespace/>StationWagonSubClass").val(obj.StationWagonSubClass).change();
		}
		if(obj.PickUpTruckSubClass != "" && obj.PickUpTruckSubClass !="Select option"){
			$("#<portlet:namespace/>PickUpTruckSubClass").val(obj.PickUpTruckSubClass).change();
		}
		if(obj.VanSubClass != "" && obj.VanSubClass !="Select option"){
			$("#<portlet:namespace/>VanSubClass").val(obj.VanSubClass).change();
		}
		if(obj.CarImageName != ""){
			document.getElementById("CarImage-text").innerHTML = obj.CarImageName;
		}
	}
</script>
