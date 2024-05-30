<#include init />
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
<!--                        <a><@liferay.language key="home" /><span class="px-2"><img width="10px" src="/o/energy-hub-theme/images/doubleArrow.svg"></span></a> -->
<!--                        <a><@liferay.language key="SignupUserType" /><span class="px-2"><img width="10px" -->
<!--                                    src="/o/energy-hub-theme/images/doubleArrow.svg"></span></a> -->
<!--                        <a><@liferay.language key="Signup" /></a> -->
                   </div>
                    <div class="MainTitleHeaderDiv">
                        <h1 class="position-relative MainTitle">
                            <div class="yellowBorder" style="height:25px"></div>${the_title}
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
                    <div href="" class="">
                        <a class="innovationPage"><@liferay.language key='InnovationPage' /></a>
                    </div>
                </div>
                <div class="userTypeCard position-relative topBorder mt-3 py-5 px-5">
                     ${processor.processColumn("column-1", "portlet-column-content portlet-column-content-only")}
                     ${processor.processColumn("column-2", "portlet-column-content portlet-column-content-only")}
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
