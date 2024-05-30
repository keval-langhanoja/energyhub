<!DOCTYPE html>
<#include init />

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<!-- Google Tag Manager -->
	<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
	new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
	j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
	'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
	})(window,document,'script','dataLayer','GTM-M4695PC');</script>
	<!-- End Google Tag Manager -->

	<title>${the_title} - ${company_name}</title>

	<meta content="initial-scale=1.0, width=device-width" name="viewport" />

	<@liferay_util["include"] page=top_head_include />

 	<link rel="stylesheet" href="${theme_path}/bootstrap-5.0.2-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${theme_path}/style/main.css">
    <link rel="stylesheet" href="${theme_path}/style/animation.css">
    <link rel="stylesheet" href="${theme_path}/style/responsive.css">
    <link rel="stylesheet" type="text/css" href="${theme_path}/style/slick/slick.css" />
    <link rel="stylesheet" type="text/css" href="${theme_path}/style/slick/slick-theme.css" />
    <link rel="stylesheet" href="/o/energy-hub-theme/style/userType.css">
	<link rel="stylesheet" href="/o/energy-hub-theme/style/signup.css">
	<link rel="stylesheet" href="/o/energy-hub-theme/style/aboutUs.css">
	
	
	<link rel="stylesheet" href="/o/energy-hub-theme/style/innovation.css">
	<link rel="stylesheet" href="/o/energy-hub-theme/style/addNewProgram-success.css">
	<link rel="stylesheet" href="/o/energy-hub-theme/style/addInnovationProject.css"> 
	<link rel="stylesheet" href="/o/energy-hub-theme/style/innovationLogged.css">
	<link rel="stylesheet" href="/o/energy-hub-theme/style/innovationItemDetail.css">
	<link rel="stylesheet" href="/o/energy-hub-theme/style/innovationLogged-seeMoreClicked.css">
	<link rel="stylesheet" href="/o/energy-hub-theme/style/addNewProgram-readonly.css">
	<link rel="stylesheet" href="/o/energy-hub-theme/style/addNewProgram-my projects-ver2.css">
	
	<link rel="stylesheet" href="/o/energy-hub-theme/style/industry.css">
	<link rel="stylesheet" href="/o/energy-hub-theme/style/industryModule.css">
	 
 	<link rel="stylesheet" href="/o/energy-hub-theme/style/commercial.css">

	<link rel="stylesheet" href="/o/energy-hub-theme/style/directoryModule.css">
	<link rel="stylesheet" href="/o/energy-hub-theme/style/directoryModule-logged.css">
  
    <script src="${theme_path}/plugins/global/plugins.bundle.js"></script>
	<script src="${theme_path}/js/jquery.min.js"></script>
	<script src="${theme_path}/js/jquery.allowed-chars.min.js"></script>
	<script src="${theme_path}/js/main.js"></script>
	<script type="text/javascript" src="${theme_path}/js/slick.min.js"></script> 
	<script src="${theme_path}/js/gsap.min.js"></script>	
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script src="${theme_path}/bootstrap-5.0.2-dist/js/bootstrap.bundle.min.js"></script>
  	<script src="${theme_path}/js/jquery-ui.min.js"></script>
  	<script src="${theme_path}/js/nicEdit.js"></script>
</head>

<!-- Google tag (gtag.js) -->
<script async src="https://www.googletagmanager.com/gtag/js?id=G-1F7GP409NY"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-1F7GP409NY');
</script>



<body class="${css_class}">
<!-- Google Tag Manager (noscript) -->
<noscript><iframe src="https://www.googletagmanager.com/ns.html?id=GTM-M4695PC"
height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
<!-- End Google Tag Manager (noscript) -->

<@liferay_ui["quick-access"] contentId="#main-content" />

<@liferay_util["include"] page=body_top_include />

<@liferay.control_menu />

<div class="container-fluid" id="wrapper"> 
<#include "${full_templates_path}/header.ftl" />

<#if is_signed_in>
    <#assign roles = user.getRoles() />
    <#list roles as role> 
          <#if role.getName() == "Administrator">
                 <style>
    	  			#wrapper > header {
    	  			 	/*display: none !important;*/
    	  			 	margin-top:50px;
    	  			}
	  			</style> 
                <#break>  
          <#elseif role.getName() == "Content Management">
	          <script>
	          		if(!$('#_com_liferay_product_navigation_product_menu_web_portlet_ProductMenuPortlet_sidenavSliderId')[0].classList.contains('open'))
						$("#_com_liferay_product_navigation_product_menu_web_portlet_ProductMenuPortlet_sidenavToggleId")[0].click()
	          </script>
	          <#break>  
          <#else>
			<style>         
				.control-menu-container {
					display: none !important;	
				}
			</style> 
			 <script>
	          		if($('#_com_liferay_product_navigation_product_menu_web_portlet_ProductMenuPortlet_sidenavSliderId')[0].classList.contains('open'))
						$("#_com_liferay_product_navigation_product_menu_web_portlet_ProductMenuPortlet_sidenavToggleId")[0].click()
	          </script>
          </#if>             
    </#list>  
</#if>
	<section id="content">
		<h2 class="hide-accessible" role="heading" aria-level="1">${the_title}</h2>

		<#if selectable>
			<@liferay_util["include"] page=content_include />
		<#else>
			${portletDisplay.recycle()}

			${portletDisplay.setTitle(the_title)}

			<@liferay_theme["wrap-portlet"] page="portlet.ftl">
				<@liferay_util["include"] page=content_include />
			</@>
		</#if>
	</section>

	<@liferay_util["include"] page=body_bottom_include />
	<#include "${full_templates_path}/footer.ftl" />
</div>


<@liferay_util["include"] page=bottom_include />

</body>
<#if sign_out_url?? && sign_out_url !="">
	<script>
	 	// don't forget to change this function under the below path:
		//../bundles/tomcat-9.0.43/webapps/ROOT/html/common/init-ext.jsp
		$(function () {
			$(document).on('click', 'a[href="${sign_out_url}"]', function (e) {
				e.preventDefault();
				//var win = window.open('http://localhost:8041/Account/Logout', '_blank');
				var win = window.open('https://iam.energyhub-lb.com/Account/Logout', '_blank');
				setTimeout(function () {
					win.close();
					location.href =  "${sign_out_url}";
				}, 500);
			});
		});
	</script>
</#if>
</html>