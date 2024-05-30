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
				<div class="MainTitleHeaderDiv">
					<h1 class="position-relative MainTitle">
						<div class="yellowBorder"></div><@liferay.language key='Commercial' />
					</h1>
					<p class="mt-1 mainDetails" id="MainTitleSubHeader"><@liferay.language key='PVStatuesReport' /></p>
				</div>
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
					 <div class="row tabsRow mb-5">
                            <div class="col-12 col-lg-6 px-5 mt-4 welcome commercialFlex">
                                ${processor.processColumn("column-1", "portlet-column-content portlet-column-content-only")}
                            </div>
                            <div class="col-12 col-lg-6 mt-4 d-flex justify-content-center">
                                ${processor.processColumn("column-2", "portlet-column-content portlet-column-content-only")}
                            </div>
                        </div>
                        <section id="CertificateSection" class="position-relative detailsSection">
                            <div class="container mx-auto px-3">
                                <div class="userTypeCard position-relative topBorder mt-5 py-5 px-5">
                                    <div class="mb-5" style="padding-top: 60px; padding-bottom: 60px;">
                                       ${processor.processColumn("column-3", "portlet-column-content portlet-column-content-only")}

                                        <div class="mb-5 mx-3 ">
                                            <div class="row d-flex justify-content-between">
                                                <div class="col-sm-4">
                                                    <p
                                                        style="font-size: 12px; font-weight: bold; color: rgb(136, 130, 130); font-family: sans-serif;margin: 0;padding: 0;">
                                                        <@liferay.language key='PVStatuesReport' /></p>
                                                    <div
                                                        style="width: 70px; height: 3px; background-color: #009BC7; margin-left: 4px; margin-right: 4px;">
                                                    </div>
                                                </div>
                                                <!--<div class="col col-sm-2">
                                                    <div class="dropdown">
                                                        <button type="button" id='btnFilter' class="modal-button"
                                                            data-toggle="dropdown" aria-haspopup="true"
                                                            aria-expanded="false"><@liferay.language key='Filter' />
                                                            <span class='img_Nav'></span>
                                                        </button>
                                                        <div class="dropdown-menu dropdown-menu-status"
                                                            aria-labelledby="dropdownMenuButton">
                                                            <span class="dropdown-item"><@liferay.language key='ThisYear' /></span>
                                                            <div class="dropdown-divider"></div>
                                                            <span class="dropdown-item active"><@liferay.language key='LastYear' /></span>
                                                            <div class="dropdown-divider"></div>
                                                            <span class="dropdown-item"><@liferay.language key='3YearsAgo' /></span>
                                                            <div class="dropdown-divider"></div>
                                                            <span class="dropdown-item"><@liferay.language key='5YearsAgo' /></span>
                                                            <div class="dropdown-divider"></div>
                                                            <span class="dropdown-item">+<@liferay.language key='5YearsAgo' /></span>
                                                        </div>
                                                    </div>
                                                </div>-->
                                            </div>
                                        </div>
                                        <div class="col-md-12 mb-5">
                                           ${processor.processColumn("column-4", "portlet-column-content portlet-column-content-only")}
                                        </div>
                                        <div class="col-md-12 mt-5">
                                            ${processor.processColumn("column-5", "portlet-column-content portlet-column-content-only")}
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="container mx-auto py-5 px-3 ">
                                <div class="userTypeCard position-relative py-5 px-5">
                                    <div class="row mb-5 mt-3">
                                        ${processor.processColumn("column-6", "portlet-column-content portlet-column-content-only")}

                                    </div>
                                </div>

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
