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
						<div class="yellowBorder"></div><@liferay.language key='Directory' />
					</h1>
					<p class="mt-1 mainDetails">Text goes here, text goes here, text goes here</p>
				</div>-->
			</div>
			<div class="d-flex justify-content-center position-relative flex-wrap mt-5 commercial-nav-con">
				<div>
					<ul class="nav nav-tabs tabs px-5" id="myTab" role="tablist">
						<li><a class="nav-link active" id="academic-tab" href="/academic"><@liferay.language key='Academic' /></a></li>
						<li><a class="nav-link" id="governmental-tab" href="/organization" role="tab"><@liferay.language key='Organizations' /></a></li>
						<li><a class="nav-link" id="companies-tab" href="/companies"><@liferay.language key='EnergyCompanies' /></a></li>
						<li><a class="nav-link" id="intl-organizations-tab" href="/directory-innovation"><@liferay.language key='Innovation' /></a></li>
					</ul>
				</div>
			</div>
			<div class="tab-content" id="myTabContent">
				<div class="tab-pane fade show active" id="divCertificate" role="tabpanel" aria-labelledby="certificate-tab">
					<div class="row tabsRow mb-5">
						${processor.processColumn("column-1", "portlet-column-content portlet-column-content-only")}
					</div>
					<section id="CertificateSection" class="position-relative detailsSection">
						<div class= "detailsDiv position-relative mt-3 pb-5 px-5">
							${processor.processColumn("column-2", "portlet-column-content portlet-column-content-only")}
						</div>
					</section>
				</div>
			</div>
		</div>
	</section>
</div>

<script src="/o/energy-hub-theme/js/commercial.js"></script>

<script>
	var winPathname = window.location.pathname.replaceAll('/en', '').replaceAll('/ar', '');
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
