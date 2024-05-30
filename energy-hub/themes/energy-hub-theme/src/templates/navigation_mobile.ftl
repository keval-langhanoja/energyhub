<#assign currentUrl = themeDisplay.getPortalURL() + themeDisplay.getURLCurrent() />
<nav class="${nav_css_class}" style="display: inline;" id="navigation" role="navigation">
	<#list nav_items as nav_item>
	<#assign nav_item_attr_has_popup = "" nav_item_css_class = "" nav_item_layout = nav_item.getLayout() />

	<#if nav_item.isSelected()>
		<#assign nav_item_attr_has_popup = "aria-haspopup='true'" nav_item_css_class = "selected"/>
	</#if> 

	<#if nav_item.hasChildren()>
		<li class="showPrograms" onclick="removeHidden('programsList')">
			<a>
				<span>${nav_item.getName()}</span>
				<img class="mx-2" src="${theme_path}/images/header/downArrow.svg">
			</a>
			<ul class="programsList hidden">
				<#list nav_item.getChildren() as nav_child>
					<#assign nav_child_css_class = "" />

					<#if nav_item.isSelected()>
						<#assign nav_child_css_class = "selected" />
					</#if>
					<li>
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
					</li>
				</#list> 
			</ul>
		</li>
	<#else>
		<li>
			<a aria-labelledby="layout_${nav_item.getLayoutId()}" ${nav_item_attr_has_popup}  class="menuBar" onclick="enhanceScroll(this, '${the_title}', '${language}', '${nav_item.getURL()}', false)" href="#${"${nav_item.getURL()?substring(nav_item.getURL()?last_index_of('/') + 1)}"}" ${nav_item.getTarget()} 
				role="menuitem"><span><@liferay_theme["layout-icon"] layout=nav_item_layout /> ${nav_item.getName()}</span></a>
		</li>
	</#if> 
</#list>
</nav>