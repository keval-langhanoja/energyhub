 <#include init />
 <link rel="stylesheet" href="/o/energy-hub-theme/style/innovationItemDetail.css">
	
 <div class="MainContent">
        <!--signup section-->
        <section id="projectname" class="project-name userType position-relative">
            <!--<div class="bgImage">
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
                	<div class="MainTitleHeaderDiv">
                        <h1 class="position-relative MainTitle">
                        <#if request.getParameter("categName")?? && request.getParameter("categName") != "" >
                    		<#assign  categName = request.getParameter("categName")/>
                            <div class="yellowBorder" style="height:25px"></div>${categName}
                        <#else>
                        	<div class="yellowBorder" style="height:25px"></div>${the_title}
                        </#if>
                        </h1>
                    </div>
                </div>
            </div>-->
            <div class="content mx-auto py-5 px-3 ">
                <svg class="animatedDots blue" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                    style="left: 1%; top:10%">
                    <circle cx="200" cy="50" r="50" />
                </svg>
                <svg class="animatedDots green2" fill="#8BB029" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                    style="left: 2%; top:15%; right: auto">
                    <circle cx="200" cy="50" r="50" />
                </svg>
                <div class="row d-flex justify-content-between ">
                    <div class="row d-flex">
                         ${processor.processColumn("column-1", "portlet-column-content portlet-column-content-only")}
                    </div>
                </div>
            </div>
        </section>
	</div>
	
<script>
	$(document).ready(function () {
		$("a.menuBar").removeClass("active");
		$(".programParent").addClass("active");
	});
</script>
