<#include init />
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
                    <h1 class="position-relative MainTitle">
                        <div class="yellowBorder" style="height:25px"></div>${the_title}
                    </h1>
                    <p class="mt-1 mainDetails"><@liferay.language key="IndustrySubHeader" />
                </div>
            </div>
            <div class="userTypeCard position-relative topBorder mt-5 py-5 px-5">
                    <div class="d-flex justify-content-between align-items-center topTools">
                        <div class="dropdown programsDropdown" style="display: none;" data-toggle="dropdown" aria-expanded="false">
                            <div class=""><span><@liferay.language key='Programs' /></span><img class="mx-3" src="/o/energy-hub-theme/images/header/downArrow.svg"></div>
                            <div class="dropdown-menu">
                            <a href="#"><img src="/o/energy-hub-theme/images/programList/innovation.svg"><@liferay.language key='Innovation' /></a>
                            <a href="#"><img src="/o/energy-hub-theme/images/programList/job.svg"><@liferay.language key='FindAJob' /></a>
                            <a href="#"><img src="/o/energy-hub-theme/images/programList/education.svg"><@liferay.language key='Education' /></a>
                            <a href="#"><img src="/o/energy-hub-theme/images/programList/bussines.svg"><@liferay.language key='ForBusiness' /></a>
                            <a href="#"><img src="/o/energy-hub-theme/images/programList/community.svg"><@liferay.language key='Community' /></a>
                            <a href="#" class="active"><img src="/o/energy-hub-theme/images/programList/industry.svg"><@liferay.language key='Industry' /></a>
                            <a href="#"><img src="/o/energy-hub-theme/images/programList/directory.svg"><@liferay.language key='Directory' /></a>
                            <a href="#"><img src="/o/energy-hub-theme/images/programList/resources.svg"><@liferay.language key='Resources' /></a>
                            </div>
                        </div>
						<!-- <div class="d-flex searchBlue align-items-center">
                            <span><img width="18px" src="/o/energy-hub-theme/images/inputSearch.svg"></span>
                            <input type="text" placeholder="TypeYourSearchHere">
                        </div> -->
                        </div>
                    <div class="mt-4">
                        <div class="mainGrid grid">
                            <div class="programList" style="margin-right: 3rem">
                               ${processor.processColumn("column-1")}
                            </div>
                            <div class=" pb-5">
                            <#if !is_signed_in> 
                                <div class="card info">
                                    <div class="d-flex justify-content-start align-items-center py-2 px-3">
                                        <div>
                                        	<img width="30px" src="/o/energy-hub-theme/images/userType/about.svg">
                                        </div>
                                    	<div class="px-3 text">
                                            <p class="mb-0 bold"><@liferay.language key='CarDealerHint' />  
                                            	<a href="${sign_in_url}" class="links"><@liferay.language key='SignIn' /></a>
                                        	</p>
                                            <p class="mb-0"><@liferay.language key='DontHaveAccount' />? 
                                            	<a href="/userType" class="links"><@liferay.language key='Signup' /></a>
                                        	</p>
                                    	</div>
                                    </div>
                                </div>
                            </#if>
                                <div class="innovationContent">
                                    <div class="row">
                                       ${processor.processColumn("column-2")}
                                    </div> 
									<div class="row mt-5">
                                       ${processor.processColumn("column-3")}
                                    </div>
									<div class="row mt-5">
                                       ${processor.processColumn("column-4")}
                                    </div> 
                                    <div class="row mt-5">
									   ${processor.processColumn("column-5")}
									</div>
									<div class="row mt-5">
									   ${processor.processColumn("column-6")}
									</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
</div>

<script>
	$(document).ready(function () {
		$("a.menuBar").removeClass("active");
		$(".programParent").addClass("active");
	});
</script>
