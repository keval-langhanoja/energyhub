 <#include init />
 	<link rel="stylesheet" href="/o/energy-hub-theme/style/industry.css">
    <link rel="stylesheet" href="/o/energy-hub-theme/style/innovation.css">
	<link rel="stylesheet" href="/o/energy-hub-theme/style/innovationLogged.css">
	<link rel="stylesheet" href="/o/energy-hub-theme/style/innovationLogged-seeMoreClicked.css">
	
<div class="MainContent">
    <!--signup section-->
    <section id="userType" class="userType position-relative">
        <div class="bgImage">
            <svg class="animatedDots green" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                style="left: 32%; top:35%; right: auto;">
                <circle cx="200" cy="50" r="50" />
            </svg>
            <svg class="animatedDots green2" fill="#8BB029" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                style="left: 47%; top:32%">
                <circle cx="200" cy="50" r="50" />
            </svg>
            <svg class="animatedDots orange" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                style="left: 57%; top:15%; right: auto;">
                <circle cx="200" cy="50" r="50" />
            </svg>
        </div>
        <div class="content mx-auto py-5 px-3 position-relative">
            <div class="d-flex justify-content-between titleSec">
                   <div class="d-flex path">
<!--                        <a><@liferay.language key="home" /><span class="px-2"><img width="10px" src="/o/energy-hub-theme/images/doubleArrow.svg"></span></a> -->
<!--                        <a><@liferay.language key="SignupUserType" /><span class="px-2"><img width="10px" -->
<!--                                    src="/o/energy-hub-theme/images/doubleArrow.svg"></span></a> -->
<!--                        <a><@liferay.language key="Signup" /></a> -->
                   </div>
                <div class="MainTitleHeaderDiv">
                    <#assign assetCategoryLocalService = serviceLocator.findService("com.liferay.asset.kernel.service.AssetCategoryLocalService")/>
 					<#assign currentUrl = themeDisplay.getPortalURL() + themeDisplay.getURLCurrent() />
 					
 					<#assign finalCategName = ""/>
 					<#assign parentCategName = ""/>
 					<#assign currentCategName = ""/>
 					
 					<#if currentUrl?contains("p_r_p_categoryId=")>
	                    <#assign categId = currentUrl?split("p_r_p_categoryId=")[1]?split("&")[0]/>
	                    <#if categId?contains(",")>
	                    	<#assign categId = categId?split(",")[0]>
	                    </#if>
	                    <#assign currentCateg = assetCategoryLocalService.getAssetCategory(categId?number) />
	                    <#assign currentCategName = currentCateg.getName() />
	                </#if>
	                
 					<#if currentUrl?contains("parentCategId=")>
	 					<#assign parentCategId = currentUrl?split("parentCategId=")[1]?split("&")[0]/>
					 	<#assign parentCateg = assetCategoryLocalService.getAssetCategory(parentCategId?number) />
	                    <#assign parentCategName = parentCateg.getName() />
	                    <#assign finalCategName = parentCategName />
	                <#else>
	                	<#assign parentCategName = currentCategName/>
                    </#if>
                    
 					
                    
                    <#assign subHeader = "" />
                    
                    <#if currentCategName == "Ongoing Projects">  
                        <#assign subHeader = "OnGoingSeeAllSubHeader" />
					<#elseif currentCategName == "Innovation Programs">
                        <#assign subHeader = "InnovationProgramsSeeAllSubHeader" />
					<#elseif currentCategName == "Innovation Challenges">
                        <#assign subHeader = "InnovationChallengesSeeAllSubHeader" />
					<#elseif currentCategName == "Coworking Spaces">
                        <#assign subHeader = "CoworkingSpacesSeeAllSubHeader" />
					<#elseif currentCategName == "Featured Apps">
                        <#assign subHeader = "FeaturedAppsSeeAllSubHeader" />
					<#elseif currentCategName == "Opportunities for Industries">
                        <#assign subHeader = "OppforIndustriesSeeAllSubHeader" />
					<#elseif parentCategName == "Education">
						<#assign finalCategName = "Education" />
                        <#assign subHeader = "MoodleCoursesSeeAllSubHeader" />
					<#elseif currentCategName == "News">
                        <#assign subHeader = "NewsSeeAllSubHeader" />
					<#elseif currentCategName == "Events">
                        <#assign subHeader = "EventsSeeAllSubHeader" />
					<#elseif currentCategName == "Publications">
						<#assign finalCategName = "Resources" />
                        <#assign subHeader = "PublicationSeeAllSubHeader" />
					<#elseif currentCategName == "Data and Tools">
						<#assign finalCategName = "Resources" />
                        <#assign subHeader = "DataandToolsSeeAllSubHeader" />
					</#if>
                    
                     <h1 class="position-relative MainTitle">
                    	<div class="yellowBorder" style="height:25px"></div>
                    	<#if finalCategName == "Resources">
                    		<@liferay.language key="Resources" />
						<#else>
							${finalCategName}
						</#if>
                    	<p class="mt-1 mainDetails" id="MainTitleSubHeader">
                    		<@liferay.language key="${subHeader}" />
                    	</p>
                	 </h1>
                   
                </div>
            </div>
            <div class="userTypeCard position-relative topBorder mt-5 py-5 px-5">
                   <div class="d-flex  justify-content-end align-items-center topTools">
                        <!--<div class="d-flex justify-content-end dropdown programsDropdown" data-toggle="dropdown"
                            aria-expanded="false">
                            <a class="readmore" id="goback">
                                Add previous page Title<span class="arrow"><img src="/o/energy-hub-theme/images/arrow.svg"></span>
                            </a>
                        </div>-->
                        <div class="d-flex justify-content-end searchBlue align-items-center">
                            <span><img width="18px" src="/o/energy-hub-theme/images/inputSearch.svg"></span>
                            <input type="text" placeholder="Search" id="queryText">
                        </div>
                    </div>
                    <div class="mt-4"> 
		                ${processor.processColumn("column-1", "portlet-column-content portlet-column-content-only")}
                    </div>
                </div>
        </div>
</div>


<script>
    $( document ).ready(function() { 
    	$("#goback").prop("href", document.referrer);  
		$("a.menuBar").removeClass("active");
		$(".programParent").addClass("active"); 
	});
</script>
