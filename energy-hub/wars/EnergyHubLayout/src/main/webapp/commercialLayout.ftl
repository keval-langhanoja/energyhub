<div class="MainContent">
	<!--signup section-->
	<section id="signup" class="signup userType position-relative">
		<div class="bgImage"> </div>
		<div class="content position-relative mx-auto pt-5 pb-3 px-3">
			<div class=" d-flex justify-content-between titleSec">
                   <div class="d-flex path">
<!--                        <a><@liferay.language key="home" /><span class="px-2"><img width="10px" src="/o/energy-hub-theme/images/doubleArrow.svg"></span></a> -->
<!--                        <a><@liferay.language key="SignupUserType" /><span class="px-2"><img width="10px" -->
<!--                                    src="/o/energy-hub-theme/images/doubleArrow.svg"></span></a> -->
<!--                        <a><@liferay.language key="Signup" /></a> -->
                   </div>
				<!--  <div class="MainTitleHeaderDiv">
					<h1 class="position-relative MainTitle">
						<div class="yellowBorder"></div><@liferay.language key='Commercial' />
					</h1>
					<p class="mt-1 mainDetails" id="MainTitleSubHeader"></p>
				</div>-->
			</div>
			<div class="d-flex justify-content-center position-relative flex-wrap mt-5 commercial-nav-con">
				<div>
					<ul class="nav nav-tabs tabs px-5" id="myTab" role="tablist">
						<li><a class="nav-link active" id="status-tab" href="/commercial-pv-status"><@liferay.language key='PVStatus' /></a></li>
						<li><a class="nav-link" id="LGBC-tab" href="/commercial-green-buildings" role="tab"><@liferay.language key='GreenBuildings' /></a></li>
						<li><a class="nav-link" id="netMetering-tab" href="/commercial-net-metering"><@liferay.language key='NetMetering' /></a></li>
						<li><a class="nav-link" id="certificate-tab" href="/commercial-recs" title="Renewable Energy Certificates (I-REC)"><@liferay.language key='RECs' /></a></li>
						<li><a class="nav-link" id="successStories-tab"href="/commercial-success-stories"><@liferay.language key='SuccessStories' /></a></li>
					</ul>
				</div>
			</div>
			<div class="tab-content" id="myTabContent">
				<div class="tab-pane fade show active" id="divCertificate" role="tabpanel" aria-labelledby="certificate-tab">
					<div class="row tabsRow mb-5 commercialFlex">
						${processor.processColumn("column-1", "portlet-column-content portlet-column-content-only")}
					</div>
					<section id="CertificateSection" class="position-relative detailsSection">
						<div class="userTypeCard position-relative topBorder mt-3 py-5 px-5">
							${processor.processColumn("column-2", "portlet-column-content portlet-column-content-only")}
						</div>
					</section>
				</div>
			</div>
		</div>
	</section>
</div>

<script>
	var winPathname = window.location.pathname.replaceAll('/en', '').replaceAll('/ar', '');
	//if(winPathname =='/commercial-green-buildings') document.getElementById("MainTitleSubHeader").innerHTML="<@liferay.language key='GreenBuildings' />";
	//if(winPathname =='/commercial-net-metering') document.getElementById("MainTitleSubHeader").innerHTML="<@liferay.language key='NetMetering' />";
	//if(winPathname =='/commercial-recs') document.getElementById("MainTitleSubHeader").innerHTML="<@liferay.language key='RECs' />	";
	//if(winPathname =='/commercial-success-stories') document.getElementById("MainTitleSubHeader").innerHTML="<@liferay.language key='SuccessStories' />";
	$('.commercial-nav-con .nav-link').removeClass('active').each(function () {
	    if( winPathname == $(this).attr('href') ) {
        	$(this).addClass('active');
	    }
    }); 
	    
	$(document).ready(function () {
		$("a.menuBar").removeClass("active");
		$(".programParent").addClass("active");
		
	});
</script>
