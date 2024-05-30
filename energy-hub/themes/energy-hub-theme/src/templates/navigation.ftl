<#assign currentUrl = themeDisplay.getPortalURL() + themeDisplay.getURLCurrent() />
<nav class="${nav_css_class}" style="display: inline;" id="navigation" role="navigation">
	<#list nav_items as nav_item>
		<#assign nav_item_attr_has_popup = "" nav_item_css_class = "" nav_item_layout = nav_item.getLayout() />

		<#if nav_item.isSelected()>
			<#assign nav_item_attr_has_popup = "aria-haspopup='true'" nav_item_css_class = "selected"/>
		</#if> 
		<#assign isAdmin = false />
		<#if is_signed_in>
			<#assign roles = user.getRoles() />
			<#list roles as role> 
				<#if role.getName() == "Administrator">
					<#assign isAdmin = true />
					<#break>  
				</#if>             
			</#list>  
		</#if>
		<#if nav_item.getName() == "Reports" && isAdmin>
			<a aria-labelledby="layout_${nav_item.getLayoutId()}" ${nav_item_attr_has_popup}  class="menuBar" 
				onclick="return enhanceScroll(this, '${the_title}', '${language}', '${nav_item.getURL()}', false);" 
				href="#${"${nav_item.getURL()?substring(nav_item.getURL()?last_index_of('/') + 1)}"}" ${nav_item.getTarget()} 
				role="menuitem">
				<span><@liferay_theme["layout-icon"] layout=nav_item_layout /> ${nav_item.getName()}</span>
			</a>
		<#elseif nav_item.getName() != "Reports">
			<#if nav_item.hasChildren()>
				<a class="dropdown">
					<a data-toggle="dropdown" class="menuBar programParent" aria-expanded="false">
					<span>${nav_item.getName()}</span>
					<img src="${theme_path}/images/header/downArrow.svg">
					</a>
					<#if nav_item.getName() != "Reports">
						<div class="dropdown-menu programsDropMenu" aria-labelledby="dropdownMenuButton">
							<#list nav_item.getChildren() as nav_child>
								<#assign nav_child_css_class = "" />

								<#if nav_item.isSelected()>
									<#assign nav_child_css_class = "selected" />
								</#if>

								<#assign assetCategoryLocalService = serviceLocator.findService("com.liferay.asset.kernel.service.AssetCategoryLocalService")/>
								<#assign categ = "" />
				
								<#assign currentPlid = nav_child.getLayout().getPlid()/>
								<#assign navItemCategoryIds = assetCategoryLocalService.getCategoryIds("com.liferay.portal.kernel.model.Layout", currentPlid) />
								<#if navItemCategoryIds?has_content>
									<#list navItemCategoryIds as navItemCategoryId>
										<#assign categ =  categ + navItemCategoryId + "," />
									</#list>
								</#if>
								<#assign categ = categ?remove_ending(",")/>
								<#assign navChildUrl = nav_child.getURL()+"?p_r_p_categoryId=" +categ/>

								<a aria-labelledby="layout_${nav_child.getLayoutId()}" onclick="enhanceScroll(this, '${the_title}', '${language}', '${navChildUrl}', true)"
								role="menuitem">
								<img src="${theme_path}/images/programs/${"${nav_child.getURL()?substring(nav_child.getURL()?last_index_of('/') + 1)}"}.svg">
								${nav_child.getName()?upper_case}
								</a>
							</#list> 
						</div>
					</#if>
				</a>
			<#else>
				<a aria-labelledby="layout_${nav_item.getLayoutId()}" ${nav_item_attr_has_popup}  class="menuBar" 
				onclick="return enhanceScroll(this, '${the_title}', '${language}', '${nav_item.getURL()}', false);" 
				href="#${"${nav_item.getURL()?substring(nav_item.getURL()?last_index_of('/') + 1)}"}" ${nav_item.getTarget()} 
				role="menuitem"><span><@liferay_theme["layout-icon"] layout=nav_item_layout /> ${nav_item.getName()}</span></a>
			</#if>
		</#if>
	</#list>
</nav>
