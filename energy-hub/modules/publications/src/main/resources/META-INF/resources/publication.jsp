<%@ include file="/init.jsp" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style>
	.card-header-title-small {
		color: black;	
	}
	.item-image {
	    width: 200px;
    	margin: 0 auto;
    	min-height: 200px;
	}
	.serviceProviders-slider-items .readmore {
	 	font-size: 15px;
	    font-weight: 400;
	    letter-spacing: 1.22px;
	    color: #009BC7 !important;
	    text-decoration: none !important;
	    border: none;
	}
	.joinNow:hover span, .searchBtn:hover span, .subscribeBtn:hover span, .readmore:hover span {
	    transform: translateX(5px);
	}
	.card {
   		border-radius: 0.25rem !important;
	}
	.arrow {
	    display: inline-flex;
	    justify-content: center;
	    align-items: center;
	    width: 17px;
	    height: 17px;
	    background: #2FADD1 0% 0% no-repeat padding-box;
	    box-shadow: 0px 3px 6px #00000029;
	    border-radius: 50%;
    }
    .dets p{
	    max-height: 105px;
	    overflow: hidden;
    }
    .dets {
        flex-direction: column;
    	display: flex;
    }
    .programList {
    	display: none;
    }
    .userTypeCard {
   		background: transparent;
  		box-shadow: none;
		padding-top: 0px;
		top: 0 !important;
    }
    .searchBlue {
    	display: none !important;
    }
    .fillBorder:before {
    	display: none !important;
    }
    .arrow {
        background : #2FADD1 0% 0% no-repeat padding-box !important;
    }
	.serviceProviders-slider-items .readmore {
		width: 130px;
	} 
	.serviceProviders-slider-items .card {
	 	min-height: 100%;
	    width: 250px !important;
	    max-height: 500px;
	    height: 500px;
	}
	.project-detail {
		background-color: transparent;
		width: 300px;
	}
	.dets p:first-of-type,
	.dets p:last-of-type {
    	display: none;
	}
	#p_p_id_com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet_INSTANCE_OeSSppt4Rf9p_ {
	    display: none;
	}
	.serviceProviders-slider {
	    min-height: 500px !important;
	    display: flex;
	 }
	 .sliderControls button {
    	width: 30px;
    	height: 30px;
    }
    .sliderControls {
	    padding: 0 !important;
	}
    .topicTitle {
   	    font-weight: 500;
    	line-height: 1.2;
    }
    .titleItemDetail {
	    max-inline-size: fit-content;
	}
	.similarTopicDets {
		display: -webkit-box;
	    -webkit-line-clamp: 4;
	    -webkit-box-orient: vertical;
	    overflow: hidden;
	    text-overflow: ellipsis;
	}
</style>           
<div class="d-flex justify-content-between innovation-item-detail"> 
	<c:forEach var="publication" items="${publications}"> 
		<div style="background-color: white; padding:2rem;width:100%">
		    <div class="titleItemDetail d-flex"><strong>${publication.title}</strong></div>
		    <div class="row m-t-10">
		        <div class="col-4 inline-flex" style="width: 100%;">
		            <img class="author-profile" src="${publication.userImageURL}" 
		            onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;"/>
		            <p class="description-center grey-font">${publication.author} <liferay-ui:message key="On" /> ${publication.createDate} 
			            <c:if test="${publication.createDate !=''}">
			          	 (<liferay-ui:message key="UpdatedOn" /> ${publication.modifiedDate})
			            </c:if>
		            </p>
		        </div>
		    </div>
		    <div class="row d-flex">
		        <img src="${publication.imageURL}" onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;"/>
		        <div class="captionPart">${publication.description}</div>
		    </div>
		     <c:if test="${fn:length(publication.attachments) > 0}">
			    <div class="row d-flex attachement">
			    	<div class="col-12 inputContainer">
						<label><strong><liferay-ui:message key="Website" /> : </strong></label> 
						<a href="//${publication.url}" id=" "  target="_blank" class="linkStyle">
							${publication.url}
						</a>
					</div>
			        <p>
			        	<strong><liferay-ui:message key="Attachement" /> </strong>
			        	<a class="linkStyle readmore" href="#"  target="_blank">
			        		<strong><liferay-ui:message key="DownloadAll" /></strong>
			        		<img src="/o/energy-hub-theme/images/doubleBlueArrow.svg">
		                </a>
	                </p>
			        <div class="row d-flex sm">
			        <c:forEach var="doc" items="${publication.attachments}">
	                    <div class="col-4 flex-center container-overlay">
			                 <img class="my-2" style="max-width:60px;" src="/o/energy-hub-theme/images/${doc.docExt}.svg" onerror="this.src='/o/energy-hub-theme/images/documents.png'; this.onerror=null;">
	                         <div class="overlay">
	                         	<a class="downloadUrl" href ="${doc.docUrl}" target="_blank">
			                    	<img class="icon-download" src="/o/energy-hub-theme/images/blue-download-arrow.png">
			                    </a>
			                </div>
			                <div class="documentTitle text-center">${doc.docTitle}</div>
			            </div>
	               	</c:forEach>
			        </div>
			    </div>
			</c:if>
		</div>
	    <div class="project-detail">
			<div class="row d-flex">
				<div class="col-8">
					<p class="topicTitle"><liferay-ui:message key="SimilarTopics" /></p>
				</div>
				<div class="col-4 sliderControls">
					<button class="prev">
						<svg id="Component_19_1" data-name="Component 19 – 1"
							xmlns="http://www.w3.org/2000/svg"
							xmlns:xlink="http://www.w3.org/1999/xlink" width="7" height="12"
							viewBox="0 0 4.005 6.837">
							<defs>
								<clipPath id="clip-path">
									<rect id="Rectangle_127" data-name="Rectangle 127"
								width="6.837" height="4.005" fill="#43D2F4" />
								</clipPath>
							</defs>
							<g id="Group_643" data-name="Group 643"
								transform="translate(-415 300.837) rotate(-90)">
								<g id="Group_642" data-name="Group 642"
								transform="translate(294 415)">
									<g id="Group_641" data-name="Group 641"
								transform="translate(0 0)" clip-path="url(#clip-path)">
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
							xmlns="http://www.w3.org/2000/svg"
							xmlns:xlink="http://www.w3.org/1999/xlink" width="7" height="12"
							viewBox="0 0 4.005 6.837">
							<defs>
								<clipPath id="clip-path">
									<rect id="Rectangle_127" data-name="Rectangle 127"
								width="6.837" height="4.005" fill="#43D2F4" />
								</clipPath>
							</defs>
							<g id="Group_643" data-name="Group 643"
								transform="translate(-415 300.837) rotate(-90)">
								<g id="Group_642" data-name="Group 642"
								transform="translate(294 415)">
									<g id="Group_641" data-name="Group 641"
								transform="translate(0 0)" clip-path="url(#clip-path)">
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
		    <div class="serviceProviders-slider">
		        <c:forEach var="topic" items="${similarTopics}"> 
					<div class="serviceProviders-slider-items">
						<div class="card card-small">
							<img class="card-img-top card-img item-image" src="${topic.imageURL}"
							onerror="this.src='/o/energy-hub-theme/images/no-image.png'; this.onerror=null;">
							<div class="card-body pb-5 mt-2 dets">
								<h5 class="card-title  card-header-title-small">${topic.title}</h5>
								<div class="card-text mt-3 card-details-small similarTopicDets">${topic.description}</div>
							</div>
							<div class="d-flex justify-content-start">
								<a href="${topic.detailURL}" class="readmore"><liferay-ui:message key="ReadMore" />
									<span class="arrow">
										<img src="/o/energy-hub-theme/images/arrow.svg">
									</span>
								</a>
							</div>
						</div>
					</div>
		        </c:forEach>
		    </div>
		</div>
	</c:forEach> 
</div>

<script>
// function downloadDoc(doc, id) { debugger;
// 	$('#' +id).attr('href',doc);
// 	$('#' +id).attr('download','name');
// 	$('#' +id).click();
// }

$('a.readmore').click(function(e) { 
	var urls = document.getElementsByClassName("downloadUrl");
	for(var i=0; i<urls.length; i++){
	    var url = document.getElementsByClassName("downloadUrl")[i];
	    url.click();
	}
});
    
$('.serviceProviders-slider').slick({
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: false,
    autoplaySpeed: 4000,
    prevArrow: $('.prev'),
    nextArrow: $('.next'),
    responsive: [{
        breakpoint: 1400,
        settings: {
            slidesToShow: 1,
            slidesToScroll: 1,
            infinite: true,
            dots: true
        }
    }, {
        breakpoint: 1366,
        settings: {
            slidesToShow: 1,
            slidesToScroll: 1,
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
            slidesToShow: 1,
            slidesToScroll: 1
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
