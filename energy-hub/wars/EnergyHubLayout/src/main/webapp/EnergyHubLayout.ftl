<div class="EnergyHubLayout MainContent" id="main-content" role="main">
	    <#assign roles = user.getRoles() />
	    	<#list roles as role>
	          <#if role.getName() == "Administrator">
	          		<section id="adminSync" class="join position-relative hide">
		           		${processor.processColumn("column-8", "portlet-column-content portlet-column-content-only")}
        			</section>
	                <#break>
	          </#if>             
	   		</#list>   
		<!--Main Section-->
        <section id="home" class="join position-relative">
           	${processor.processColumn("column-2", "portlet-column-content portlet-column-content-only")}
        </section>
        
        <!--About Section-->
        <section id="about" class="about">
            <div class="content d-flex">
                <div class="aboutImage mt-5">
                    <img class="bg" src="/o/energy-hub-theme/images/about/bg.png">
                    <img class="imgBorder" src="/o/energy-hub-theme/images/about/border.png">
                    <img class="MainImage" src="/o/energy-hub-theme/images/about/aboutImg.png">
                </div>
                <div class="card">
                    <div class="blueBorder"></div>
                 		${processor.processColumn("column-3", "portlet-column-content portlet-column-content-only")}
                </div>
            </div>
        </section>
        
		<!--Programs Section-->
        <section id="program" class="program position-relative">
            <svg class="animatedDots green" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                style="right: 6%; top:35%">
                <circle cx="200" cy="50" r="50" />
            </svg>
            <svg class="animatedDots orange" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                style="right: 25%; top:30%">
                <circle cx="200" cy="50" r="50" />
            </svg>
            <svg class="animatedDots blue " viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                style="right: 8%; top:50%">
                <circle cx="200" cy="50" r="50" />
            </svg>
            <svg class="animatedDots orange sec" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                style="right: 6%; bottom:30%">
                <circle cx="200" cy="50" r="50" />
            </svg>
            <svg class="animatedDots blue sec" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                style="right: 20%; bottom:30%">
                <circle cx="200" cy="50" r="50" />
            </svg>
            <svg class="animatedDots green2" fill="#8BB029" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg"
                style="right: 0; bottom:20%">
                <circle cx="200" cy="50" r="50" />
            </svg>
            
            <div>
            	${processor.processColumn("column-4", "portlet-column-content portlet-column-content-only")}
            </div>
           
        </section>
        <!--innovation section-->
        <section id="getinvolved" class="getInvolved">
            <div class="content">
                <div class="row">
                    <div class="col-md-6 col-lg-7 col-xl-8">
                        <div class="d-flex justify-content-between flex-wrap mb-5">
                            <h1><@liferay.language key='HowToGetInvolved' /></h1>
                            <div class="sliderControls">
                                <button class="prev">
                                    <svg id="Component_19_1" data-name="Component 19 � 1"
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
                                    <svg id="Component_19_1" data-name="Component 19 � 1"
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
                      
							${processor.processColumn("column-1", "portlet-column-content portlet-column-content-only")}
                      
                    </div>
                    <div class="col-md-6 col-lg-5 col-xl-4 position-relative">
                        <div class="mt-5 joinInnovationCard card">
                        <img class="innovationImg" style="bottom: 10%;" src="/o/energy-hub-theme/images/getInvolvedImage.png">
                            <div class="joinInnovation text-center">
                                <h2><@liferay.language key='BePart' /><small><@liferay.language key='OfThe' /></small><@liferay.language key='EnergyTransition' /></h2>
                                <a class="joinBtn mt-5" href="https://www.energyhub-lb.com/usertype"><@liferay.language key='JoinNow' /></a>
                               <!-- <a class="joinBtn mt-5" href="http://localhost:8080/usertype"><@liferay.language key='JoinNow' /></a>-->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

 		<!--community section-->
		<section id="community" class="community">
            <div class="row">
                <div class="position-relative">
                    <h1 class="title text-center capitalText"><@liferay.language key='CommunityForum' /></h1>
                    <p class="details text-center"><@liferay.language key='CommunityDesc' /></p>
                </div>
                <div class="col-12">   
					${processor.processColumn("column-5", "portlet-column-content portlet-column-content-only")}
                </div>
            </div>
        </section>
        
        <!--News and Events section-->
        <section id="news" class="news position-relative">
            <div class="bgImage"></div>
            <div class="layer"></div>
            <div class="position-relative pt-5" style="z-index: 3;">
                <h1 class="title text-center"><@liferay.language key='NewsAndEvents' /></h1>
                <p class="details text-center"><@liferay.language key='NewsAndEventsDescription' /></p>
            </div>
            <div class="content mt-5">
                <div class="cards NewsEventsHome">
                	<!--news section-->
                    <div class="newsCards">
						${processor.processColumn("column-6", "portlet-column-content portlet-column-content-only")}
                    </div>
                     <!--events section-->
                    <div class="events">
                        ${processor.processColumn("column-7", "portlet-column-content portlet-column-content-only")}
                    </div>
                </div>
            </div>
        </section>
</div>
