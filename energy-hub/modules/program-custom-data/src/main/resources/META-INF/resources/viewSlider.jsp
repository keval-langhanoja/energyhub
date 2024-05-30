<%@ include file="/init.jsp" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style>
	.serviceProviders-slider {
	    max-height: 300px;
	}
	.slick-list {
	    max-height: 300px;
	}
	.slick-track {
	    max-height: 300px;
	}
	.addNewForm {
	    width: 100px!important;
	    height: 40px!important;
	    background: #009bc7 0% 0% no-repeat padding-box !important;
	    border-radius: 20px !important;
	    color: #fff !important;
	    transition: .3s !important;
	    box-shadow: 0px 3px 6px #00000029 !important;
	    border: 1px solid #009BC7 !important;
	    opacity: 1;
	    padding: 0 30px !important;
	}
</style>
<c:if test="${fn:length(usersByRole) >0 ||  fn:length(journalArticleList) > 0}">
	<div class="col-md-12 col-lg-12 col-xl-12">
	    <div class="d-flex justify-content-between flex-wrap mb-4">
	        <h1 style="padding-top:13px">${categName}</h1>
			  <div class="sliderControls">
			  	<c:if test="${is_signed_in && showAddCar}">
			  		<a class="mb-2 viewMyProjects" href="/my-projects?p_r_p_categoryId=${categoryId}&parentCategId=${parentCategId}&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}"><span><liferay-ui:message key="ViewMyCars" /></span></a>
					<button class="addNewForm add mb-2 mx-4" onclick="location.href = '/custom-forms?createCars&p_r_p_categoryId=${categoryId}&parentCategId=${parentCategId}&folderId=${folderId}&ddmStructureKey=${ddmStructureKey}&ddmTemplateKey=${ddmTemplateKey}';">
						<span class="plus">
							<img class="mb-1" src="/o/energy-hub-theme/images/add.svg">
						</span>
						<span><liferay-ui:message key="Add" /></span>
					</button>
				</c:if>
				<button class="prev">
					<svg id="Component_19_1" data-name="Component 19 – 1"
						xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
						width="7" height="12" viewBox="0 0 4.005 6.837">
						<defs>
							<clipPath id="clip-path">
								<rect id="Rectangle_127" data-name="Rectangle 127" width="6.837"
									height="4.005" fill="#43D2F4" />
							</clipPath>
						</defs>
						<g id="Group_643" data-name="Group 643"
							transform="translate(-415 300.837) rotate(-90)">
							<g id="Group_642" data-name="Group 642" transform="translate(294 415)">
								<g id="Group_641" data-name="Group 641" transform="translate(0 0)"
									clip-path="url(#clip-path)">
									<path id="Path_1325" data-name="Path 1325"
										d="M297.416,418.9a.477.477,0,0,1-.338-.14l-2.938-2.938a.479.479,0,0,1,.677-.677l2.6,2.6,2.6-2.6a.478.478,0,0,1,.677.677l-2.938,2.938a.475.475,0,0,1-.338.14Zm0,0"
										transform="translate(-293.999 -415.001)" fill="#43D2F4" />
								</g>
							</g>
						</g>
					</svg>
				</button>
				<button class="next">
					<svg id="Component_19_1" data-name="Component 19 – 1"
						xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
						width="7" height="12" viewBox="0 0 4.005 6.837">
						<defs>
							<clipPath id="clip-path">
								<rect id="Rectangle_127" data-name="Rectangle 127" width="6.837"
									height="4.005" fill="#43D2F4" />
							</clipPath>
						</defs>
						<g id="Group_643" data-name="Group 643"
							transform="translate(-415 300.837) rotate(-90)">
							<g id="Group_642" data-name="Group 642" transform="translate(294 415)">
								<g id="Group_641" data-name="Group 641" transform="translate(0 0)"
									clip-path="url(#clip-path)">
									<path id="Path_1325" data-name="Path 1325"
										d="M297.416,418.9a.477.477,0,0,1-.338-.14l-2.938-2.938a.479.479,0,0,1,.677-.677l2.6,2.6,2.6-2.6a.478.478,0,0,1,.677.677l-2.938,2.938a.475.475,0,0,1-.338.14Zm0,0"
										transform="translate(-293.999 -415.001)" fill="#43D2F4" />
								</g>
							</g>
						</g>
					</svg>
				</button>
			</div>
		</div>
	    <div class="serviceProviders-slider2">
	    	<c:if test="${carDealerUserRole}">
		        <c:forEach var="user" items="${journalArticleList}"> 
					<div class="serviceProviders-slider-items">
						<div class="card blue text-center">
							<img class="involveImage" src="${user.imageURL}"
							 onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;"/> 
							<div class="dets mt-3">
				 				<p class="detstitle">${user.carMake}</p> 
				 				<p class="">${user.carModel}</p> 
					 		</div>
							<a href="${user.userUrl}"
								class="getInvolved-slider-items readmore mt-3"><liferay-ui:message key="Explore" />
								<span class="arrow">
									<img src="/o/energy-hub-theme/images/arrow.svg">
								</span>
							</a>
						</div>
					</div>
		        </c:forEach>
	        </c:if>
	        <c:if test="${otherRoles}">
		        <c:forEach var="user" items="${usersByRole}"> 
			        <c:choose>
					    <c:when test="${isAcademic}">
				        	<div class="serviceProviders-slider-items">
								<div class="card blue text-center"> 
									<img class="involveImage" src="${user.imageUrl}"
									 onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;"/> 
									<div class="dets mt-3">
						 				<p class="detstitle">${user.universityName}</p> 
						 				<p class="">${user.acronym}</p> 
							 		</div>
									<a href="${user.academicReditUrl}"
										class="getInvolved-slider-items readmore mt-3"><liferay-ui:message key="Explore" />
										<span class="arrow">
											<img src="/o/energy-hub-theme/images/arrow.svg">
										</span>
									</a>
								</div>
							</div>
				         </c:when>
						<c:otherwise>
							<div class="serviceProviders-slider-items">
								<div class="card blue text-center"> 
									<img class="involveImage" src="${user.imageUrl}"
									 onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;"/> 
									<div class="dets mt-3">
						 				<p class="detstitle">${user.firstName} ${user.lastName}</p> 
						 				<p class="">${user.company}</p> 
							 		</div>
									<a href="${user.userUrl}"
										class="getInvolved-slider-items readmore mt-3"><liferay-ui:message key="Explore" />
										<span class="arrow">
											<img src="/o/energy-hub-theme/images/arrow.svg">
										</span>
									</a>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
		        </c:forEach>
	        </c:if>
	    </div>
		<c:if test="${showSeeAll}">
			<c:if test="${seeAllUrl != ''}">
				<div class="seeAll text-end mt-4">
		            <a href="/${seeAllUrl}">
		                <span><liferay-ui:message key="SeeAll" /></span>
		                <span class="arrow bg-black"><img src="/o/energy-hub-theme/images/arrow.svg"></span>
		            </a>
		        </div>
		    </c:if>
			<c:if test="${seeAllUrl == ''}">
				<div class="seeAll text-end mt-4">
		            <a href="/see-all?userRoleName=${userRoleName}">
		                <span><liferay-ui:message key="SeeAll" /></span>
		                <span class="arrow bg-black"><img src="/o/energy-hub-theme/images/arrow.svg"></span>
		            </a>
		        </div>
		    </c:if>
	    </c:if>
	</div>
</c:if>
<style>
    .arrow {
        background : #2FADD1 0% 0% no-repeat padding-box !important;
    }
	.serviceProviders-slider-items .readmore {
		width: 130px;
	} 
	.serviceProviders-slider-items .card {
		min-height: 280px;
	}
</style>
<script>
$('.serviceProviders-slider2').slick({
    slidesToShow: 3,
    slidesToScroll: 1,
    autoplay: false,
    autoplaySpeed: 4000,
    prevArrow: $('.prev'),
    nextArrow: $('.next'),
    responsive: [{
        breakpoint: 1400,
        settings: {
            slidesToShow: 3,
            slidesToScroll: 3,
            infinite: true,
            dots: true
        }
    }, {
        breakpoint: 1366,
        settings: {
            slidesToShow: 2,
            slidesToScroll: 2,
            infinite: true,
            dots: true
        }
    },
    {
        breakpoint: 980,
        settings: {
            slidesToShow: 1,
            slidesToScroll: 1
        }
    },
    {
        breakpoint: 767,
        settings: {
            slidesToShow: 2,
            slidesToScroll: 2
        }
    },
    {
        breakpoint: 576,
        settings: {
            slidesToShow: 1,
            slidesToScroll: 1
        }
    }
    ]
});

 $('.serviceProviders-slider').slick({
        slidesToShow: 3,
        slidesToScroll: 1,
        autoplay: false,
        autoplaySpeed: 4000,
        prevArrow: $('.prev'),
        nextArrow: $('.next'),
        responsive: [{
            breakpoint: 1400,
            settings: {
                slidesToShow: 3,
                slidesToScroll: 3,
                infinite: true,
                dots: true
            }
        }, {
            breakpoint: 1366,
            settings: {
                slidesToShow: 2,
                slidesToScroll: 2,
                infinite: true,
                dots: true
            }
        },
        {
            breakpoint: 980,
            settings: {
                slidesToShow: 1,
                slidesToScroll: 1
            }
        },
        {
            breakpoint: 767,
            settings: {
                slidesToShow: 2,
                slidesToScroll: 2
            }
        },
        {
            breakpoint: 576,
            settings: {
                slidesToShow: 1,
                slidesToScroll: 1
            }
        }
        ]
    });
</script>
