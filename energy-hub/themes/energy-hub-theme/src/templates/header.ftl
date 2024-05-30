<header class="d-flex justify-content-between">
    <div class=" my-auto">
        <a href="${site_default_url}" class="logo"><img class="m-0" alt="logo" src="${theme_path}/images/header/logo.svg"></a>
    </div>

    <div class="menu h-100 my-auto">
    	<#include "${full_templates_path}/navigation.ftl" />
    </div>

    <div class="d-flex my-auto h-100">
        <div class="search pb-1 my-auto" data-toggle="modal" data-target="#search" id="tmpHide">
        </div>
        <#if is_signed_in>
		    <#assign roles = user.getRoles() />
		    <#list roles as role>
          		<#if role.getName() == "Content Management">
	        		<a  class="login my-auto" style="width: auto !important;  padding: 8px;" id="contentManag" href="javascript:;" rel="nofollow">
	        			 <span><@liferay.language key='Content Management' /></span>
					</a> 
	                <#break>
	          	</#if>  
	      		<#if role.getName() == "Administrator" && request.getRemoteAddr() == "51.178.45.174">
		    		<a class="login my-auto" style="width: auto !important;  padding: 8px;" onClick="ajaxCall('BackupVms')" 
		    			id="SyncUsers" href="javascript:;" rel="nofollow">
		    			 <span><@liferay.language key='Backup' /></span>
					</a> 
					<#break>
					</#if>
				<#if role.getName() == "Administrator" && request.getRemoteAddr() == "51.178.45.207">
		    		<a class="login my-auto" style="width: auto !important;  padding: 8px;" onClick="ajaxCall('RestoreVms')" 
		    			id="SyncUsers" href="javascript:;" rel="nofollow">
		    			 <span><@liferay.language key='Restore' /></span>
					</a> 
					<#break>
				</#if>
			</#list> 
            <div class="dropdown notifications my-auto" data-toggle="dropdown" aria-expanded="false">
                <div class="">
                	<div class="unreadChat" id="unreadChat" style="display:none">
	                	<p id="chatsUnreadTotal"></p>
            		</div>
                	<img class="userProfile profileBorder my-auto pointer" style="border-radius: 0!important;" src="${theme_path}/images/header/inbox.png" onclick="location.href = '/messaging';">
                </div>
           	</div> 
           	 <#list roles as role> 
	       	 	<#if role.getName() == "Administrator">
	        		 <div class="hide">
	        		 <#break>
	          	</#if>  
          	 </#list>  
	           	<div class="search pb-1 my-auto" data-toggle="modal" data-target="#search">
	                <img class="userProfile profileBorder IndustryUser my-auto pointer" 
						src="${user.getPortraitURL(themeDisplay)}" onclick="location.href = '/create-account?edit';">	
	            </div>
            <#list roles as role> 
	            <#if role.getName() == "Administrator">
	    		   	</div>
	    		   	<#break>
	          	</#if> 
          	</#list> 
         	<a data-redirect="${is_login_redirect_required?string}" class="login my-auto" href="${sign_out_url}" id="sign-out" rel="nofollow">
					<span><@liferay.language key="Logout" /></span>
			</a>
		<#else>
			<a data-redirect="${is_login_redirect_required?string}" class="login my-auto" 
					href="${sign_in_url}" id="sign-in" rel="nofollow">
					<span><@liferay.language key='Login' /></span>
			</a>
			<img class="loginImg" style="width: 35px" src="${theme_path}/images/header/login.svg">
		</#if>
      	<#if language=="en_US">
			<div class="lang main h-100 my-auto"><button class="langBtn" onclick="changelang('ar')"><@liferay.language key='LanguageAr'/></button></div>
		<#elseif language=="ar_SA">
			<div class="lang main h-100 my-auto"><button class="langBtn" onclick="changelang('en')"><@liferay.language key='LanguageEn'/></button></div>
		</#if>
        <svg class="mx-2 p-1 menuBars" style="display: none;" height="100%" viewBox="0 -53 384 384" width="30px"
            xmlns="http://www.w3.org/2000/svg" onclick="removeHidden('hamburgerMenu')">
            <path fill="#070707"
                d="m368 154.667969h-352c-8.832031 0-16-7.167969-16-16s7.167969-16 16-16h352c8.832031 0 16 7.167969 16 16s-7.167969 16-16 16zm0 0" />
            <path fill="#070707"
                d="m368 32h-352c-8.832031 0-16-7.167969-16-16s7.167969-16 16-16h352c8.832031 0 16 7.167969 16 16s-7.167969 16-16 16zm0 0" />
            <path fill="#070707"
                d="m368 277.332031h-352c-8.832031 0-16-7.167969-16-16s7.167969-16 16-16h352c8.832031 0 16 7.167969 16 16s-7.167969 16-16 16zm0 0" />
        </svg>
    </div>
</header>

<!--mobile menu-->
<div class="hidden hamburgerMenu">
    <ul class="h-100">
    	<#include "${full_templates_path}/navigation_mobile.ftl" />
        <!-- <li class="lang"><button class="langBtn" style="font-size: 17px;">Ø¹Ø±Ø¨ÙŠ</button></li>-->
	</ul>
</div>  

</div>
<style>
	.deleteButton {
        background-color: transparent;
	    border: 0px;
	    width: 100%;
	    display: flex;
	    padding: 10px;
    }
    .deleteButton:hover { 
    	text-decoration: underline;
    }
    .userInfo .dropdown-menu {
	    width: 458px;
	    height: auto;
	    transform: translate3d(0px, 55px, 0px) !important;
	    border: 0;
	    background: #FFFFFF;
	    box-shadow: 0px 3px 6px #00000029;
	    border-radius: 20px;
	    right: 0;
	    left: auto !important;
	}
	.unreadChat {
	    height: 20px;
	    width: 20px;
	    background-color: #8472FC;
	    border-radius: 50%;
	    position: absolute;
	    z-index: 1;
	    margin-left: 3px;
	    margin-top: -5px;
	    color: white;
	 }
	 .unreadChat p{
	    margin-top: 0px;
	    margin-left: 5px;
	    font-size: 13px;
	}
</style>


<script>
	$( document ).ready(function() {
	    fetchUnreadCount();
	});
	
	function changelang(lang) {
		var hrefUrl = location.href.replaceAll('/en', '').replaceAll('/ar', '');
		var originUrl = location.origin;
		var url= hrefUrl.replaceAll(location.origin, location.origin + '/' + lang);
		location.href = url;
	}
	
	//GET UNREAD MESSAGES COUNTER
	setInterval(fetchUnreadCount, 5000);
	
	function fetchUnreadCount() {
	var total = '${themeDisplay.getUser().getComments()}';
		if(total.length <3 && total != '' && total != "0" && !location.href.includes('messaging')) {
			document.getElementById("unreadChat").style.display="block";
			//document.getElementById('chatsUnreadTotal').innerHTML = total;
		}	
	}
	
	$('#contentManag').on('click', function (e) {
		e.preventDefault();
		$("#_com_liferay_product_navigation_product_menu_web_portlet_ProductMenuPortlet_sidenavToggleId")[0].click()
	}); 
</script>