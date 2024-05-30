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
                    <#if the_title == "Signup User Type">
                      	<h1 class="position-relative MainTitle">
                        	<div class="yellowBorder" style="height:25px"></div><@liferay.language key="Signup" />
                    	</h1>
                    	<p class="mt-1 mainDetails"><@liferay.language key="SignupUserTypeSubHeader" /></p>
                    	<#else>
                    	 <h1 class="position-relative MainTitle">
	                        <div class="yellowBorder" style="height:25px"></div>${the_title}
	                    </h1>
                    </#if>
                </div>
            </div>
            <div class="userTypeCard position-relative topBorder mt-5 py-5 px-5">
                ${processor.processColumn("column-1", "portlet-column-content portlet-column-content-only")}
            </div>
        </div>
    </section>
</div>
 
