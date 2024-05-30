<!--contact us-->
       <section id="contact" class="contactUS">
            
            <div class="position-relative">
                <svg class="animatedDots blue" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg">
                    <circle cx="200" cy="50" r="50" />
                </svg>
                <svg class="animatedDots orange" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg">
                    <circle cx="200" cy="50" r="50" />
                </svg>
                <svg class="animatedDots green" viewBox="0 0 500 200" xmlns="http://www.w3.org/2000/svg">
                    <circle cx="200" cy="50" r="50" />
                </svg>
                <!--subscribe container
                <div class="letterJoin position-relative">
                    <h1 class="title text-center">JOIN OUR NEWSLETTER</h1>
                    <p class="details text-center">Subscribe to get the latest updates from the Energy Hub</p>
                    <div class="content mt-5">
                        <div class="d-flex subscribe mx-auto">
                            <input type="email" placeholder="Email Address">
                            <a href="" class="subscribeBtn my-auto"><small>SUBSCRIBE</small><span
                                    class="arrow mx-2"><img src="${theme_path}/images/arrow.svg"></span></a>
                        </div>
                    </div>
                </div>

              	send email container
                <div class="sendEmail position-relative">
                    <h1 class="title text-center">KEEP IN TOUCH WITH US</h1>
                    <p class="details text-center">Text goes here, Text goes here,Text goes here,Text goes here,Text
                        goes here,Text goes here,Text goes here,Text goes here,Text goes here,Text goes here,Text goes
                        here,Text goes here,</p>
                    <form class="mt-5">
                        <div class="row">
                            <div class="col-12 col-sm-6 inputContainer">
                                <input type="text" placeholder="Full name">
                            </div>
                            <div class="col-12 col-sm-6 inputContainer">
                                <input type="text" placeholder="Last Name">
                            </div>
                            <div class="col-12 col-sm-6 inputContainer">
                                <input type="text" placeholder="Email Address">
                            </div>
                            <div class="col-12 col-sm-6 inputContainer">
                                <input type="text" placeholder="Email Address">
                            </div>
                            <div class="col-12 inputContainer">
                                <textarea type="text" placeholder="Your Message"></textarea>
                            </div>
                        </div>
                        <button class="add mt-4" style="text-transform: uppercase; width:auto; float: right;" type="submit"><@liferay.language key="Submit" /></button>
                    </form>
                </div>
            </div>-->
            <!--contact footer-->
            <div class="contact">
                <div class="content">
                    <div class="row ">
                        <div class="col-12 col-lg-5 mt-4">
                            <div class="card">
                                <img class="position-absolute" style="top: -6%;" src="${theme_path}/images/europe.svg">
                                <p class="bold mb-4"><@liferay.language key="EnergyHuEuropeanUnion" />
                                </p>
                                <p><@liferay.language key="EnergyHubFooter" />
                                </p>
                            </div>
                        </div>
                        <div class="col-6 col-md-3 col-lg-2 mt-4">
                            <span class="bold"><@liferay.language key="Sitemap" /></span>
                            <ul>
                                <li><a href="/${language?split('_')[0]}/home"><@liferay.language key="Home" /></a></li>
                                <li><a href="/${language?split('_')[0]}/about"><@liferay.language key="About" /></a></li>
                                <li><a href="/${language?split('_')[0]}/usertype"><@liferay.language key="getinvolved" /></a></li>
                                <!--<li><a href="#">contact</a></li>-->
                            </ul>
                        </div>
                        <div class="col-6 col-md-3 col-lg-2 mt-4">
                            <span class="bold" style="text-transform: uppercase;"><@liferay.language key="Program" /></span>
                            <ul>
                            	<#assign AssetCategoryLocalService = serviceLocator.findService("com.liferay.asset.kernel.service.AssetCategoryLocalService")>
                				<#assign categories = AssetCategoryLocalService.getCategories() />
		                         <#list categories as category>
							        <#assign categName =  category.name />
							        <#if categName =="Community">
							            <li><a style="text-transform: uppercase;"  href="/${language?split('_')[0]}/community?p_r_p_categoryId=${category.getCategoryId()}">${category.getTitle(themeDisplay.getLocale())?cap_first}</a></li> 
							        </#if>
						         	<#if categName =="find-a-job">
						           		<li><a style="text-transform: uppercase;"  href="/${language?split('_')[0]}/find-a-job?p_r_p_categoryId=${category.getCategoryId()}">${category.getTitle(themeDisplay.getLocale())?cap_first}</a></li>
							        </#if>
						         	<#if categName =="for-business">
					             		<li><a style="text-transform: uppercase;"  href="/${language?split('_')[0]}/for-business?p_r_p_categoryId=${category.getCategoryId()}">${category.getTitle(themeDisplay.getLocale())?cap_first}</a></li>
							        </#if>
						         	<#if categName =="Innovation">
				            	 		<li><a style="text-transform: uppercase;"  href="/${language?split('_')[0]}/innovation?p_r_p_categoryId=${category.getCategoryId()}">${category.getTitle(themeDisplay.getLocale())?cap_first}</a></li>
							        </#if>
						         	<#if categName =="Industry">
					              		<li><a style="text-transform: uppercase;"  href="/${language?split('_')[0]}/industry?p_r_p_categoryId=${category.getCategoryId()}">${category.getTitle(themeDisplay.getLocale())?cap_first}</a></li>
							        </#if>
						         	<#if categName =="Education">
						            	<li><a style="text-transform: uppercase;"  href="/${language?split('_')[0]}/education?p_r_p_categoryId=${category.getCategoryId()}">${category.getTitle(themeDisplay.getLocale())?cap_first}</a></li>
							        </#if>
						         	<#if categName =="News">
						            	<li><a style="text-transform: uppercase;"  href="/${language?split('_')[0]}/news?p_r_p_categoryId=${category.getCategoryId()}">${category.getTitle(themeDisplay.getLocale())?cap_first}</a></li>
							        </#if>
						         	<#if categName =="Events">
						            	<li><a style="text-transform: uppercase;"  href="/${language?split('_')[0]}/events?p_r_p_categoryId=${category.getCategoryId()}">${category.getTitle(themeDisplay.getLocale())?cap_first}</a></li>
							        </#if>
						         	<#if categName =="Resources">
						            	<li><a style="text-transform: uppercase;"  href="/${language?split('_')[0]}/resources?p_r_p_categoryId=${category.getCategoryId()}">${category.getTitle(themeDisplay.getLocale())?cap_first}</a></li>
							        </#if>
						         	<#if categName =="Directory">
						            	<li><a style="text-transform: uppercase;" href="/${language?split('_')[0]}/directory?p_r_p_categoryId=${category.getCategoryId()}">${category.getTitle(themeDisplay.getLocale())?cap_first}</a></li>
							        </#if>
							    </#list>
                            </ul>
                        </div>
                        <div class="col-12 col-sm-8 col-md-6 col-lg-3 mt-4">
                            <span class="bold" style="text-transform: uppercase;"><@liferay.language key="Contact" /></span>
                            <ul class="contactInfo">
                                <li>
                                	<div class="d-flex">
	                                    <div class="contactImg"><img src="${theme_path}/images/contact/location.svg"></div>
                                    	<span>
	                                        <b><@liferay.language key="FooterContact1" /></b></br>
	                                        <@liferay.language key="FooterContact2" /></br>
											<@liferay.language key="FooterContact3" /></br>
											<@liferay.language key="Lebanon" />Lebanon
										</span>
									</div>
                                </li>
                               <!-- <li>
                                    <div class="d-flex">
	                                    <div class="contactImg"><img src="${theme_path}/images/contact/call.svg"></div>
	                                    <span>
		                                    <span class="bold blueColor p-0">hotline</br></span>
		                                    <span class="p-0">+961 1 567 876</br></span>
		                                    <span class="p-0">+961 1 786 889</br></span>
		                                </span>
                                    </div>
                                </li> -->
                                <li>
                            		<a href="https://www.linkedin.com/company/e-energyhub/">
                                        <div class="contactImg"><img src="${theme_path}/images/contact/linkedIn.svg"></div>
                                        <span>Linkedin.com/E-EnergyHub</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!--footer-->
        <section class="footer d-flex align-items-center justify-content-center">
            <span>all rights reserved @ ENERGY<Small class="blueColor bold"> HUB</Small> Lebanon &nbsp;&nbsp;</span>
            <a href="/privacy-policy"><@liferay.language key="PrivacyPolicy" /></a>
        </section>