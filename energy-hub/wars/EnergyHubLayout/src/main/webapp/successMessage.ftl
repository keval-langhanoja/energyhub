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
					<p class="mt-1 mainDetails">Text goes here, text goes here, text goes here</p>
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
			<div class="userTypeCard position-relative topBorder mt-3 py-5 px-5">
				<div class="row">
					<h1 class="d-flex justify-content-center"><img src="/o/energy-hub-theme/images/check-mark.png"
							class="tick"><@liferay.language key='Great' /></h1>
				</div>
				<div class="row">
					<div class="col-12 col-md-12 col-xl-12 mt-12 d-flex justify-content-center">
						<label class="notification"><@liferay.language key='ProjectSubmitted' /></label>
					</div>
				</div>
				<div class="row d-flex justify-content-center">
					<div class="card info p-3">
						<div class="d-flex justify-content-start align-items-center">
							<div><img width="30px" src="/o/energy-hub-theme/images/warning.png"></div>
							<div class="px-3 text">
								<p class="mb-0 font-weight-bold"><@liferay.language key='SuccessMessage' /></p>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="d-flex justify-content-center mt-5">
						<div class="col-md-6 col-sm-12">
							<a href="/innovation?p_r_p_categoryId=49005" class="readmore go right">
								<@liferay.language key='InnovationPage' /><span class="arrow"><img src="/o/energy-hub-theme/images/arrow.svg"></span>
							</a>
						</div>
						<div class="col-md-6 col-sm-12">
							<a href="/my-projects" class="readmore go">
								<@liferay.language key='MyProjectsPage' /><span class="arrow"><img src="/o/energy-hub-theme/images/arrow.svg"></span>
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
