<%@ include file="/init.jsp"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
 
<portlet:resourceURL var="testAjaxResourceUrl"></portlet:resourceURL>
<portlet:actionURL var="createProjectURL" />


<style>
	.OverviewBtn {
		font-size: 18px;
		text-transform: capitalize;
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
					<a><liferay-ui:message key="Home" /><span class="px-2"><img width="10px" src="/o/energy-hub-theme/images/doubleArrow.svg"></span></a>
					<a><liferay-ui:message key="InnovationPage" /><span class="px-2"><img width="10px"
								src="/o/energy-hub-theme/images/doubleArrow.svg"></span></a>
					<a><liferay-ui:message key="OverviewPage" /></a>

				</div>
				<div class="">
					<h1 class="position-relative MainTitle">
						<div class="yellowBorder" style="height:25px"></div><liferay-ui:message key="Overview" />
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
								<g id="Component_13_1" data-name="Component 13 ? 1" transform="translate(9 6)">
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
			</div>
			<div class="userTypeCard position-relative topBorder mt-3 py-5 px-5">
				<div class="card info p-3">
					<div class="d-flex justify-content-start align-items-center">
						<div><img width="30px" src="/o/energy-hub-theme/images/userType/about.svg"></div>
						<div class="px-3 text">
							<p class="mb-0 font-weight-bold"><liferay-ui:message key="ReviewInformation" /></p>
						</div>
					</div>
				</div>
				<aui:form id="createProject_form" cssClass="mt-5 registerationForm" enctype="multipart/form-data" accept-charset="UTF8" method="POST">
					<h1><liferay-ui:message key="CarInformation" /></h1>
					<div class="row hide" id="CarMakeDiv">
						<div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="CarMake" /></label>
						</div>
						<div class="col-6 col-md-8 col-xl-8 inputContainer">
							<aui:input cssClass="data lblProgramTitle" id="CarMake" name="CarMake" label="" disabled="true" value="${CarMake}"></aui:input>
						</div>
						<div class="separatorDashed mt-0"></div>
					</div>
					<div class="row hide" id="CarModelDiv">
						<div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="CarModel" /></label>
						</div>
						<div class="col-6 col-md-8 col-xl-8 inputContainer">
							<aui:input cssClass="data lblProgramTitle" id="CarModel" name="CarModel" label="" disabled="true" value=""></aui:input>
						</div>
						<div class="separatorDashed mt-0"></div>
					</div>
					<div class="row hide" id="ModelYearDiv">
						<div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="ModelYear" /></label>
						</div>
						<div class="col-6 col-md-8 col-xl-8 inputContainer">
							<aui:input cssClass="data lblProgramTitle" id="ModelYear" name="ModelYear" label="" disabled="true" value=""></aui:input>
						</div>
						<div class="separatorDashed mt-0"></div>
					</div>
					<div class="row hide" id="RangeDiv">
						<div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="Range" /></label>
						</div>
						<div class="col-6 col-md-8 col-xl-8 inputContainer">
							<aui:input cssClass="data lblProgramTitle" id="Range" name="Range" label="" disabled="true" value=""></aui:input>
						</div>
						<div class="separatorDashed mt-0"></div>
					</div>
					<div class="row hide" id="PriceRangeFromDiv">
						<div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="PriceRangeFrom" /></label>
						</div>
						<div class="col-6 col-md-8 col-xl-8 inputContainer">
							<aui:input cssClass="data lblProgramTitle" id="PriceRangeFrom" name="PriceRangeFrom" label="" disabled="true" value=""></aui:input>
						</div>
						<div class="separatorDashed mt-0"></div>
					</div>
					<div class="row hide" id="PriceRangeToDiv">
						<div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="PriceRangeTo" /></label>
						</div>
						<div class="col-6 col-md-8 col-xl-8 inputContainer">
							<aui:input cssClass="data lblProgramTitle" id="PriceRangeTo" name="PriceRangeTo" label="" disabled="true" value=""></aui:input>
						</div>
						<div class="separatorDashed mt-0"></div>
					</div>
					<div class="row hide" id="FuelTypeDiv">
						<div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="FuelType" /></label>
						</div>
						<div class="col-6 col-md-8 col-xl-8 inputContainer">
							<aui:input cssClass="data lblProgramTitle" id="FuelType" name="FuelType" label="" disabled="true" value=""></aui:input>
						</div>
						<div class="separatorDashed mt-0"></div>
					</div>
					<div class="row hide" id="ChargingTimeDiv">
						<div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="ChargingTime" /></label>
						</div>
						<div class="col-6 col-md-8 col-xl-8 inputContainer">
							<aui:input cssClass="data lblProgramTitle" id="ChargingTime" name="ChargingTime" label="" disabled="true" value=""></aui:input>
						</div>
						<div class="separatorDashed mt-0"></div>
					</div>
					<div class="row hide" id="FuelEfficiencyDiv">
						<div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="FuelEfficiency" /></label>
						</div>
						<div class="col-6 col-md-8 col-xl-8 inputContainer">
							<aui:input cssClass="data lblProgramTitle" id="FuelEfficiency" name="FuelEfficiency" label="" disabled="true" value=""></aui:input>
						</div>
						<div class="separatorDashed mt-0"></div>
					</div>
					<div class="row hide" id="ElectricMotorbatteryDiv">
						<div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="ElectricMotorbattery" /></label>
						</div>
						<div class="col-6 col-md-8 col-xl-8 inputContainer">
							<aui:input cssClass="data lblProgramTitle" id="ElectricMotorBattery" name="ElectricMotorBattery" label="" disabled="true" value=""></aui:input>
						</div>
						<div class="separatorDashed mt-0"></div>
					</div>
					<div class="row hide" id="VehicleClassSizeDiv">
						<div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="VehicleClassSize" /></label>
						</div>
						<div class="col-6 col-md-8 col-xl-8 inputContainer">
							<aui:input cssClass="data lblProgramTitle" id="VehicleClassSize" name="VehicleClassSize" label="" disabled="true" value=""></aui:input>
						</div>
						<div class="separatorDashed mt-0"></div>
					</div>
					<div class="row hide" id="SedanSubClassDiv">
						<div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="SedanSubClass" /></label>
						</div>
						<div class="col-6 col-md-8 col-xl-8 inputContainer">
							<aui:input cssClass="data lblProgramTitle" id="SedanSubClass" name="SedanSubClass" label="" disabled="true" value=""></aui:input>
						</div>
						<div class="separatorDashed mt-0"></div>
					</div>
					<div class="row hide" id="StationWagonSubClassDiv">
						<div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="StationWagonSubClass" /></label>
						</div>
						<div class="col-6 col-md-8 col-xl-8 inputContainer">
							<aui:input cssClass="data lblProgramTitle" id="StationWagonSubClass" name="StationWagonSubClass" label="" disabled="true" value=""></aui:input>
						</div>
						<div class="separatorDashed mt-0"></div>
					</div>
					<div class="row hide" id="PickupTruckSubClassDiv">
						<div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="PickupTruckSubClass" /></label>
						</div>
						<div class="col-6 col-md-8 col-xl-8 inputContainer">
							<aui:input cssClass="data lblProgramTitle" id="PickupTruckSubClass" name="PickupTruckSubClass" label="" disabled="true" value=""></aui:input>
						</div>
						<div class="separatorDashed mt-0"></div>
					</div>
					<div class="row hide" id="VansSubClassDiv">
						<div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="VansSubClass" /></label>
						</div>
						<div class="col-6 col-md-8 col-xl-8 inputContainer">
							<aui:input cssClass="data lblProgramTitle" id="VansSubClass" name="VansSubClass" label="" disabled="true" value=""></aui:input>
						</div>
						<div class="separatorDashed mt-0"></div>
					</div>
					<div class="row hide" id="TransmissionDiv">
						<div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="Transmission" /></label>
						</div>
						<div class="col-6 col-md-8 col-xl-8 inputContainer">
							<aui:input cssClass="data lblProgramTitle" id="Transmission" name="Transmission" label="" disabled="true" value=""></aui:input>
						</div>
						<div class="separatorDashed mt-0"></div>
					</div>
					<div class="row hide" id="CarWebsiteLinkDiv">
						<div class="col-6 col-md-4 col-xl-4 mt-4 inputContainer">
							<label><liferay-ui:message key="CarWebsiteLink" /></label>
						</div>
						<div class="col-6 col-md-8 col-xl-8 inputContainer">
							<aui:input cssClass="data lblProgramTitle" id="CarWebsiteLink" name="CarWebsiteLink" label="" disabled="true" value=""></aui:input>
						</div>
					</div>
					<div class="row hide" id="CarImageDiv">
                        <h1><liferay-ui:message key="Image" /></h1>
                        <div class="col-12">
                        	<div class="d-flex flex-wrap justify-content-start align-items-center mt-4 py-3 px-2 dashedBorder">
                            <div class="document my-2 mx-2 text-center">
                            	<div class="docDetails">
                                	<img class="my-2" style="max-width:60px;" id="tmpImage" src="" 
                                		onerror="this.src='/o/energy-hub-theme/images/documents.png'; this.onerror=null;">
                                    	<aui:input cssClass="data lblProgramTitle" id="CarImageName" name="CarImageName" label="" disabled="true" value=""></aui:input>
                                     </div>
                                 </div>
                            </div>
                        </div>
                    </div>
					<div class="d-flex justify-content-end fixBtns my-5">
				   		<button type="button" class="blueBorderBtn reset mt-3 mx-3 OverviewBtn" onClick="backAndEdit()"><liferay-ui:message key="BackAndEdit" /></button>
				   		<aui:button cssClass="blueBtn submit mt-3 OverviewBtn" type=" " id="save" onclick="ajaxCall('${my-projects}')" value="Submit"></aui:button> 
					</div>
				</aui:form>
			</div>
    	</div>
    </section>
</div>	
<script src="https://cdn.rawgit.com/localForage/localForage/4ce19202/dist/localforage.min.js"></script>		
<script>
	var obj = {};
	var carFile;
	var edit = false;
	var articleId;
	var FileEntryId;
	
	$(document).ready(function () {
		fillFormValues();
		
	    $('#goback').on('click', function (e) {
	    	e.preventDefault();
	    	window.history.back();
	    }); 
	    $("#<portlet:namespace/>back").on('click', function (e) {
	    	e.preventDefault();
	    	window.history.back()	
	    }); 
	});

	function backAndEdit() {
		obj = JSON.parse(localStorage['passedFormValues']);
		window.history.back();
	}
	
	var base64;
	function fillFormValues() {
		obj = JSON.parse(localStorage['passedFormValues']);
		
		if(obj.CarMakeValue != "" && obj.CarMakeValue !="Select option"){
			$("#CarMakeDiv").removeClass('hide');
			$("#<portlet:namespace/>CarMake").val(obj.CarMakeValue);
		}
		if(obj.CarModel != ""){
			$("#CarModelDiv").removeClass('hide');
			$("#<portlet:namespace/>CarModel").val(obj.CarModel);
		}
		if(obj.ModelYear != "" && obj.CarMakeValue !="Select option"){
			$("#ModelYearDiv").removeClass('hide');
			$("#<portlet:namespace/>ModelYear").val(obj.ModelYear);
		}
		if(obj.CarWebsiteLink != ""){
			$("#CarWebsiteLinkDiv").removeClass('hide');
			$("#<portlet:namespace/>CarWebsiteLink").val(obj.CarWebsiteLink);
		}
		if(obj.Range != ""){
			$("#RangeDiv").removeClass('hide');
			$("#<portlet:namespace/>Range").val(obj.Range + "Km");
		}
		if(obj.PriceRangeFrom != ""){
			$("#PriceRangeFromDiv").removeClass('hide');
			$("#<portlet:namespace/>PriceRangeFrom").val(obj.PriceRangeFrom + "$");
		}
		if(obj.PriceRangeTo != ""){
			$("#PriceRangeToDiv").removeClass('hide');
			$("#<portlet:namespace/>PriceRangeTo").val(obj.PriceRangeTo + "$");
		}
		if(obj.FuelTypeValue != "" && obj.FuelTypeValue !="Select option"){
			$("#FuelTypeDiv").removeClass('hide');
			$("#<portlet:namespace/>FuelType").val(obj.FuelTypeValue);
		}
		if(obj.ChargingTime != ""){
			$("#ChargingTimeDiv").removeClass('hide');
			$("#<portlet:namespace/>ChargingTime").val(obj.ChargingTime + "h/240V");
		}
		if(obj.FuelEfficiency != ""){
			$("#FuelEfficiencyDiv").removeClass('hide');
			$("#<portlet:namespace/>FuelEfficiency").val(obj.FuelEfficiency + "Kwh/100Km");
		}
		if(obj.ElectricMotorBattery != ""){
			$("#ElectricMotorBatteryDiv").removeClass('hide');
			$("#<portlet:namespace/>ElectricMotorBattery").val(obj.ElectricMotorBattery);
		}
		if(obj.TransmissionValue != "" && obj.TransmissionValue !="Select option"){
			$("#TransmissionDiv").removeClass('hide');
			$("#<portlet:namespace/>Transmission").val(obj.TransmissionValue);
		}
		if(obj.VehicleClassSizeValue != "" && obj.VehicleClassSizeValue !="Select option"){
			$("#VehicleClassSizeDiv").removeClass('hide');
			$("#<portlet:namespace/>VehicleClassSize").val(obj.VehicleClassSizeValue);
		}
		if(obj.SedanSubClassValue != "" && obj.SedanSubClassValue !="Select option"){
			$("#SedanSubClassDiv").removeClass('hide');
			$("#<portlet:namespace/>SedanSubClass").val(obj.SedanSubClassValue);
		}
		if(obj.StationWagonSubClassValue != "" && obj.StationWagonSubClassValue !="Select option"){
			$("#StationWagonSubClassDiv").removeClass('hide');
			$("#<portlet:namespace/>StationWagonSubClass").val(obj.StationWagonSubClassValue);
		}
		if(obj.PickUpTruckSubClassValue != "" && obj.PickUpTruckSubClassValue !="Select option"){
			$("#PickUpTruckSubClassDiv").removeClass('hide');
			$("#<portlet:namespace/>PickUpTruckSubClass").val(obj.PickUpTruckSubClassValue);
		}
		if(obj.VanSubClassValue != "" && obj.VanSubClassValue !="Select option"){
			$("#VanSubClassDiv").removeClass('hide');
			$("#<portlet:namespace/>VanSubClass").val(obj.VanSubClassValue);
		}
		if(obj.CarImageName != ""){
			$("#CarImageDiv").removeClass('hide');
			$("#<portlet:namespace/>CarImageName").val(obj.CarImageName);
			$("#tmpImage").attr("src","/o/energy-hub-theme/images/"+obj.CarImageExt+".svg");
			var imgeBase64 = obj.CarImage64;
			base64 = imgeBase64;
			carFile = base64ToFile(imgeBase64, obj.CarImageName);
		}
		if(obj.edit != ""){
			edit = obj.edit;
		}
		if(obj.articleId != ""){
			articleId = obj.articleId;
		}
		if(obj.FileEntryId != ""){
			FileEntryId = obj.FileEntryId;
		}
	}
	
	document.getElementById("tmpImage").onerror = function() {
		$("#tmpImage").attr("src","/o/energy-hub-theme/images/documents.png");
	};
	
	 function base64ToFile(dataurl, filename) {
        var arr = dataurl.split(','),
            mime = arr[0].match(/:(.*?);/)[1],
            bstr = atob(arr[1]), 
            n = bstr.length, 
            u8arr = new Uint8Array(n);
        while(n--){
            u8arr[n] = bstr.charCodeAt(n);
        }
        return new File([u8arr], filename, {type:mime});
    }
	
	function ajaxCall(key) {
		localStorage.removeItem( 'passedFormValues' );
		var xhr = new XMLHttpRequest();
		var formData = new FormData();
		var parentCategId = obj.parentCategId;
		var data = {
			 <portlet:namespace />overview: false,
			 <portlet:namespace />edit: edit,
			 <portlet:namespace />articleId: articleId,
			 <portlet:namespace />FileEntryId: obj.FileEntryId,
			 <portlet:namespace />folderId: obj.folderId,
			 <portlet:namespace />categId: obj.categId,
			 <portlet:namespace />ddmStructureKey: obj.ddmStructureKey,
			 <portlet:namespace />CarMake: obj.CarMake,
			 <portlet:namespace />CarModel: obj.CarModel,
			 <portlet:namespace />ModelYear: obj.ModelYear,
			 <portlet:namespace />CarWebsiteLink: obj.CarWebsiteLink,
			 <portlet:namespace />Range: obj.Range,
			 <portlet:namespace />PriceRangeFrom: obj.PriceRangeFrom,
			 <portlet:namespace />PriceRangeTo: obj.PriceRangeTo,
			 <portlet:namespace />FuelType: obj.FuelType,
			 <portlet:namespace />ChargingTime: obj.ChargingTime,
			 <portlet:namespace />FuelEfficiency: obj.FuelEfficiency,
			 <portlet:namespace />ElectricMotorBattery: obj.ElectricMotorBattery,
			 <portlet:namespace />Transmission: obj.Transmission,
			 <portlet:namespace />VehicleClassSize: obj.VehicleClassSize,
			 <portlet:namespace />SedanSubClass: obj.SedanSubClass,
			 <portlet:namespace />StationWagonSubClass: obj.StationWagonSubClass,
			 <portlet:namespace />PickUpTruckSubClass: obj.PickUpTruckSubClass,
			 <portlet:namespace />VanSubClass: obj.VanSubClass
		};
		if( carFile ) {
// 			formData.append('<portlet:namespace />CarImage', carFile);
			formData.append('<portlet:namespace />CarImage', obj.base64);
			formData.append('<portlet:namespace />CarImageName', obj.CarImageName);
		}
		
		xhr.onloadend = function (e) {
			var res = xhr.responseText.toLowerCase();
			window.location.href = location.origin +"/transportation?p_r_p_categoryId=" + parentCategId;
			
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