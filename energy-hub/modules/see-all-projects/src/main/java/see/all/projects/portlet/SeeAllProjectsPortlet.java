package see.all.projects.portlet;

import see.all.projects.constants.SeeAllProjectsPortletKeys;
import see.all.projects.constants.UserTypes;
import see.all.projects.helper.helper;
import see.all.projects.helper.util;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

/**
 * @author vyo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=SeeAllProjects",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SeeAllProjectsPortletKeys.SEEALLPROJECTS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class SeeAllProjectsPortlet extends MVCPortlet {
	private final static Configuration _portletConfiguration = ConfigurationFactoryUtil
			.getConfiguration(PortalClassLoaderUtil.getClassLoader(), "portlet");
	
	private String DataAndToolsType = "SelectFromList31776460",  DataAndToolsRenewable = "SelectFromList65071319", 
			DataAndToolsEfficiency = "SelectFromList62689899", DataAndToolsTitle = "Text04258425", 
			DataAndToolsImage = "Image01601444", DataAndToolsDescription = "RichText92248029";
	
	private String InnovationProgramTitle = "Text03803647", InnovationProgramDescription = "RichText76165816", 
			InnovationProgramImage = "Image92271346", InnovationProgramActivityType = "SelectFromList81632169", 
			InnovationProgramStage = "SelectFromList56281506";
	
	private String OnGoingTitle = "Text73021449", OnGoingImage = "Image13718074", OngoingDescription = "RichText70541782",
			OnGoingStage = "SelectFromList36742361", OnGoingProjectCategory = "SelectFromList03925321";
	
	private String InnovationChallengeTitle = "Text68843417", InnovationChallengeDescription = "RichText96949120", 
			InnovationChallengeImage = "Image57179735", InnovationChallengeChallengeType = "SelectFromList17093910";
	
	private String OppIndustTitle = "Text11601604", OppIndustDescription = "RichText78666240", 
			OppIndustImage = "Image48657786", OppIndustType = "SelectFromList51477468";
	
	private String FeaturedAppTitle = "Text06189651", FeaturedAppDescription = "RichText80799588", 
			FeaturedAppImage = "Image53810155", FeaturedAppType = "SelectFromList10893948";
	
	private String FuelEfficiency = "Numeric67597327", PriceRangeFrom = "Numeric64760835", PriceRangeTo = "Numeric99924442", 
			CarMake = "SelectFromList20767174", CarModel = "Text93196265", CarImage= "Image16722469";
	
	private List<String> oppFilter = Arrays.asList(OppIndustType);
	private List<String> dataToolsFilter = Arrays.asList(DataAndToolsType, DataAndToolsRenewable, DataAndToolsEfficiency);
	private List<String> basicProgFilter = Arrays.asList(OnGoingProjectCategory, OnGoingStage);
	private List<String> InnovProgFilter = Arrays.asList(InnovationProgramActivityType, InnovationProgramStage);
	private List<String> InnovChallengeFilter = Arrays.asList(InnovationChallengeChallengeType);
	private List<String> FeaturedAppFilter = Arrays.asList(FeaturedAppType);
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		try {
			HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
			String categId = httpReq.getParameter("p_r_p_categoryId");
			Long ddmStructureKey = Long.valueOf(helper.ifEmpty(httpReq.getParameter("ddmStructureKey"), "0"));
			String userRoleName = httpReq.getParameter("userRoleName");

			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
			long userId = themeDisplay.getUserId();
			String userEmail = themeDisplay.getUser().getEmailAddress();
			
			String sort = helper.ifEmpty(httpReq.getParameter("sort"), "desc");
			String sortField = helper.ifEmpty(httpReq.getParameter("sortField"), "createDate");
			String queryText = helper.ifEmpty(httpReq.getParameter("queryText"), "");
			JSONObject filter = new JSONObject(helper.ifEmpty(httpReq.getParameter("filter"), "{}"));
			int pageNo = Integer.valueOf(helper.ifEmpty(httpReq.getParameter("pageNo"), "0"));
			int pageSize = Integer.valueOf(helper.ifEmpty(httpReq.getParameter("pageSize"), "10"));
			
			
			if(!helper.isEmpty(userRoleName)) {
				//fill Filter Data for Filter Dropdown
				getIAMFilter(renderRequest, themeDisplay, userRoleName);
				
				List<User> usersByRole =  new ArrayList<User>();
				long userRole = Long.valueOf(UserTypes.getUserTypeValue(userRoleName));
				usersByRole =  UserLocalServiceUtil.getRoleUsers(userRole);
				JSONArray userJA = new JSONArray();
				
				//FILTER
				if(filter.has("area") && filter.getJSONArray("area").length()>0 ) {
					usersByRole = getFilteredUsers(usersByRole, filter.getJSONArray("area"), "AreaOfExpertise");
				}
				if(filter.has("carMake") && filter.getJSONArray("carMake").length()>0 ) {
					usersByRole = getFilteredUsers(usersByRole, filter.getJSONArray("carMake"), "carMake");
				}
				
				//SEARCH
				usersByRole = getSearchedUsers(usersByRole, queryText);
				
				for(User user : usersByRole) {  
					JSONObject userJO = getUserInfo(user, themeDisplay,userRoleName); 
					userJO.put("detailURL", "/detail?userDetail&categName="+ userRoleName +"&groupId="+
							themeDisplay.getLayout().getGroupId() +"&articleId="+ user.getUserId());
					userJO.put("userRoleName", userRoleName);
					userJA.put(userJO);
				}
				
				List<Object> articlesList = helper.toList(userJA);
				int totalPages=  articlesList.isEmpty() ? 0 : (int) Math.floor(articlesList.size()/pageSize);
				renderRequest.setAttribute("totalPages", totalPages);
				renderRequest.setAttribute("pageNo", pageNo > totalPages ? 0 : pageNo);
				renderRequest.setAttribute("pageSize", pageSize);
				
				//PAGINATION
				articlesList= getPageLimitObject(articlesList, pageNo, pageSize);
				
				if(userRoleName.equalsIgnoreCase(UserTypes.CarDealerUser.name())) {
					renderRequest.setAttribute("seeAllUserRole", false);
					renderRequest.setAttribute("carDealerUserRole", true);
					renderRequest.setAttribute("carsCateg", false);
				} else {
					renderRequest.setAttribute("carDealerUserRole", false);
					renderRequest.setAttribute("carsCateg", false);
					renderRequest.setAttribute("seeAllUserRole", true);
				}
				renderRequest.setAttribute("usersByRole", helper.toList(userJA));
			}else {
				renderRequest.setAttribute("seeAllUserRole", false);
				DDMStructure struct = DDMStructureManagerUtil.getStructure(themeDisplay.getLayout().getGroupId(),
						Long.valueOf(PortalUtil.getClassNameId(JournalArticle.class)), String.valueOf(ddmStructureKey));
				
				//fill Filter Data for Filter Dropdown
				getFilterData(themeDisplay, renderRequest, struct);
				
				List<JournalArticle> fetchedArticles = new ArrayList<JournalArticle>();
				AssetEntryQuery assetEntryQuery = new AssetEntryQuery();
				if(categId.contains(",")) {
					String[] ids = categId.split(",");
					for (int i = 0; i < ids.length ; i++) {
						AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getAssetCategory(Long.valueOf(ids[i]));
						if(assetCategory.getName().equalsIgnoreCase("Data and Tools")) {
							categId = ids[i];
						} 
					}
				}
				assetEntryQuery.setAnyCategoryIds(new long[] { Long.valueOf(categId) });
				
				AssetCategory assetCateg = AssetCategoryLocalServiceUtil.fetchAssetCategory(Long.valueOf(categId));
				if(!assetCateg.getName().equalsIgnoreCase("cars")) {
					assetEntryQuery.setOrderByType1(sort);
					assetEntryQuery.setOrderByCol1(sortField); 
				}
				
				List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
				for (AssetEntry ae : assetEntryList) {
					JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
					fetchedArticles.add(JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey()));
				}
				List<Long> articleIds = getArticlesPermission(userEmail);
				List<JournalArticle> filtered = new ArrayList<>();
				
				if(assetCateg != null && assetCateg.getName().equalsIgnoreCase("Ongoing Projects")) {
					//Get articles with User View Permission
					for (JournalArticle article : fetchedArticles) {
						User user = UserLocalServiceUtil.getUser(article.getUserId());
						Role admibnRole =  RoleLocalServiceUtil.getRole(Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.Administrator.name())));
						if (article.getUserId() == userId || articleIds.contains(article.getId()) || user.getRoles().contains(admibnRole)) {
							filtered.add(article);
						}
					}
				}else filtered = fetchedArticles;
				
				//FILTER
				if(filter.has("stage") && filter.getJSONArray("stage").length()>0 ) {
					filtered = getFilteredArticles(themeDisplay, filtered, filter.getJSONArray("stage"), OnGoingStage, "", null);
				}
				if(filter.has("category") && filter.getJSONArray("category").length()>0 ) {
					filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("category"), OnGoingProjectCategory, "", null);
				}
				if(filter.has("date") && filter.getJSONArray("date").length()>0 ) {
					filtered = getFilteredArticlesByCreatedDate(filtered, filter.getJSONArray("date"));
				}
				if(filter.has("source") && filter.getJSONArray("source").length()>0 ) {
					filtered = getFilteredArticles(themeDisplay, filtered, filter.getJSONArray("source"),
							DataAndToolsRenewable, "", null);
				}
				if(filter.has("efficiency") && filter.getJSONArray("efficiency").length()>0 ) {
					filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("efficiency"),
							DataAndToolsEfficiency, "", null);
				}
				if(filter.has("datatooltype") && filter.getJSONArray("datatooltype").length()>0 ) {
					filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("datatooltype"),
							DataAndToolsType, "", null);
				}
				if(filter.has("typeofactivity") && filter.getJSONArray("typeofactivity").length()>0 ) {
					filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("typeofactivity"),
							InnovationProgramActivityType, "", null);
				}
				if(filter.has("progstage") && filter.getJSONArray("progstage").length()>0 ) {
					filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("progstage"), 
							InnovationProgramStage, "", null);
				}
				if(filter.has("challengetype") && filter.getJSONArray("challengetype").length()>0 ) {
					filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("challengetype"), 
							InnovationChallengeChallengeType, "", null);
				}
				if(filter.has("oppType") && filter.getJSONArray("oppType").length()>0 ) {
					filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("oppType"),
							OppIndustType, "", null);
				}
				if(filter.has("featuredAppType") && filter.getJSONArray("featuredAppType").length()>0 ) {
					filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("featuredAppType"),
							FeaturedAppType, "", null);
				}
				if((filter.has("priceFrom") && filter.getJSONArray("priceFrom").length()>0) || 
						(filter.has("priceTo") && filter.getJSONArray("priceTo").length()>0)) {
					
					JSONArray priceTo = (filter.has("priceTo") && filter.getJSONArray("priceTo").length()>0) ?
							filter.getJSONArray("priceTo") : new JSONArray();
					JSONArray priceFrom = (filter.has("priceFrom") && filter.getJSONArray("priceFrom").length()>0) ?
							filter.getJSONArray("priceFrom") : new JSONArray();
					 
					filtered = getFilteredArticles(themeDisplay,filtered, priceFrom, 
							PriceRangeFrom, PriceRangeTo, priceTo); 
				}
				 
				if((filter.has("fuelFrom") && filter.getJSONArray("fuelFrom").length()>0) ||  
					(filter.has("fuelTo") && filter.getJSONArray("fuelTo").length()>0)) {
					
					JSONArray fuelTo = (filter.has("fuelTo") && filter.getJSONArray("fuelTo").length()>0) ?
							filter.getJSONArray("fuelTo") : new JSONArray();
					JSONArray fuelFrom = (filter.has("fuelFrom") && filter.getJSONArray("fuelFrom").length()>0) ?
							filter.getJSONArray("fuelFrom") : new JSONArray();
							
					filtered = getFilteredArticles(themeDisplay,filtered, fuelFrom, 
							FuelEfficiency, FuelEfficiency, fuelTo);
				}
				 
				//SEARCH
				if(!helper.isEmpty(queryText)) {
					List<String> searchField = new ArrayList<String>();
					if(struct.getName("en_US").equalsIgnoreCase("basic-programs")) { //Ongoing Projects
						searchField.add(OnGoingTitle);
						searchField.add(OngoingDescription);
					} else if(struct.getName("en_US").equalsIgnoreCase("innovation-programs")) {
						searchField.add(InnovationProgramTitle);
						searchField.add(InnovationProgramDescription);
					} else if(struct.getName("en_US").equalsIgnoreCase("innovation-challenges")) {
						searchField.add(InnovationChallengeTitle);
						searchField.add(InnovationChallengeDescription);
					} else if(struct.getName("en_US").equalsIgnoreCase("opportunities-industries")) {
						searchField.add(OppIndustTitle);
						searchField.add(OppIndustDescription);
					} else if(struct.getName("en_US").equalsIgnoreCase("data-and-tools")) {
						searchField.add(DataAndToolsTitle);
						searchField.add(DataAndToolsDescription);
					} else if(struct.getName("en_US").equalsIgnoreCase("featured-apps")) {
						searchField.add(FeaturedAppTitle);
						searchField.add(FeaturedAppDescription);
					} else if(struct.getName("en_US").equalsIgnoreCase("cars")) {
						searchField.add(CarMake);
						searchField.add(CarModel);
					}
					
					filtered = getSearchedArticles(themeDisplay, renderRequest, filtered, queryText, searchField);
				}
				//PAGINATION
				List<JournalArticle> pagedArticles = getPageLimit(filtered, pageNo, pageSize);
				
				List<Object> programs = null;
				JSONArray jaRes = new JSONArray();
				String folderId ="", ddmTemplateKey ="";
				for (JournalArticle journalArticle : pagedArticles) {
					ddmStructureKey = Long.valueOf(journalArticle.getDDMStructureKey());
					ddmTemplateKey = journalArticle.getDDMTemplateKey();
					folderId = String.valueOf(journalArticle.getFolderId());
					String content = journalArticle.getContentByLocale(themeDisplay.getLanguageId());
					Document document = SAXReaderUtil.read(content);
					
					JSONObject res = new JSONObject();
					if(struct.getName("en_US").equalsIgnoreCase("data-and-tools")) {
						res = getDataAndToolsData(journalArticle, document, renderRequest);
						renderRequest.setAttribute("otherTypes", true);
					} else if(struct.getName("en_US").equalsIgnoreCase("opportunities-industries")) {
						res = getOppIndustryData(journalArticle, document, renderRequest);
						renderRequest.setAttribute("otherTypes", true);
					} else if(struct.getName("en_US").equalsIgnoreCase("basic-programs")) {
						res = getBasicProgramData(journalArticle, document, renderRequest);
						renderRequest.setAttribute("otherTypes", true);
					} else if(struct.getName("en_US").equalsIgnoreCase("innovation-programs")) {
						res = getInnovationProgramData(journalArticle, document, renderRequest);
						renderRequest.setAttribute("otherTypes", true);
					} else if(struct.getName("en_US").equalsIgnoreCase("innovation-challenges")) {
						res = getInnovationChallengeData(journalArticle, document, renderRequest);
						renderRequest.setAttribute("otherTypes", true);
					} else if(struct.getName("en_US").equalsIgnoreCase("featured-apps")) {
						res = getFeaturedAppsData(journalArticle, document, renderRequest);
						renderRequest.setAttribute("otherTypes", true);
					}else if(struct.getName("en_US").equalsIgnoreCase("cars")) {
						res = getCarsData(journalArticle, document, renderRequest);
					}
					
					jaRes.put(res); 
				}	
				
				if(assetCateg.getName().equalsIgnoreCase("cars") && !sortField.equalsIgnoreCase("createDate")) {
					jaRes = sortCars(jaRes, sortField, sort);
				}
				programs = helper.toList(jaRes);
				
				int totalPages=  filtered.isEmpty() ? 0 : (int) Math.floor(filtered.size()/pageSize);
				renderRequest.setAttribute("programs", programs);
				renderRequest.setAttribute("totalPages", totalPages);
				renderRequest.setAttribute("pageNo", pageNo > totalPages ? 0 : pageNo);
				renderRequest.setAttribute("pageSize", pageSize);
				
				
				int minPrice = getMin(jaRes,"priceRangeTo");
				int maxPrice = getMax(jaRes, "priceRangeFrom");
				
				int fuelFrom = getMin(jaRes,"fuelEfficiency");
				int fuelTo = getMax(jaRes, "fuelEfficiency");
				
				renderRequest.setAttribute("priceFrom", maxPrice);
				renderRequest.setAttribute("priceTo", minPrice);
				renderRequest.setAttribute("fuelFrom", fuelFrom);
				renderRequest.setAttribute("fuelTo", fuelTo);
				
				boolean showAdd = false;
				
				if(assetCateg != null && assetCateg.getName().equalsIgnoreCase("Ongoing Projects")) {
					renderRequest.setAttribute("folderId", folderId);
					renderRequest.setAttribute("ddmTemplateKey", ddmTemplateKey);
					showAdd = true;
					renderRequest.setAttribute("showDateFilter", true);
				}else {
					renderRequest.setAttribute("showDateFilter", false);
				}
				
				if(assetCateg != null && assetCateg.getName().equalsIgnoreCase("cars")) {
					renderRequest.setAttribute("showCarsFilter", true);
				}else {
					renderRequest.setAttribute("showCarsFilter", false);
				}
				
				renderRequest.setAttribute("ddmStructureKey", ddmStructureKey); 
				renderRequest.setAttribute("categName", assetCateg.getName());
				renderRequest.setAttribute("categNameTitle", assetCateg.getTitle(themeDisplay.getLocale()));
				renderRequest.setAttribute("showAdd", showAdd);  
				renderRequest.setAttribute("is_signed_in", themeDisplay.isSignedIn());
				renderRequest.setAttribute("groupId", themeDisplay.getLayout().getGroupId());
				if(assetCateg.getName().equalsIgnoreCase("cars")) {
					renderRequest.setAttribute("seeAllUserRole", false);
					renderRequest.setAttribute("carsCateg", true);
					renderRequest.setAttribute("carDealerUserRole", false);
				} else {
					renderRequest.setAttribute("carDealerUserRole", false);
					renderRequest.setAttribute("carsCateg", false);
					renderRequest.setAttribute("seeAllUserRole", true);
				}
			}
			super.render(renderRequest, renderResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	
	private int getMin(JSONArray jaRes, String value) {
	    int min = 0;
	    for (int i=0 ; i<jaRes.length() ; i++) {
	    	if(jaRes.getJSONObject(i).has(value)) {
	    		int priceFrom = helper.isEmpty(jaRes.getJSONObject(i).getString(value)) ? 
	    				0 : jaRes.getJSONObject(i).getInt(value); 
	    		if (min == 0 || priceFrom < min)
	    			min = priceFrom;
	    	}
	    }
	    return min;
	}
	
	 private int getMax(JSONArray jaRes, String value) {
		int max = 0;
	    for (int i=0 ; i<jaRes.length() ; i++) {
	    	if(jaRes.getJSONObject(i).has(value)) {
		    	int priceRangeTo = helper.isEmpty(jaRes.getJSONObject(i).getString(value)) ? 
		    			0 : jaRes.getJSONObject(i).getInt(value);
		        if (max == 0 || priceRangeTo < max)
		        	max = priceRangeTo;
	    	}
	    }
	    return max;
	}
	private JSONObject getCarsData(JournalArticle journalArticle, Document document, 
		RenderRequest renderRequest) throws PortalException, JSONException, DocumentException {
		String title = document.selectSingleNode("/root/dynamic-element[@name='"+ CarMake +"']").hasContent() ? 
			document.selectSingleNode("/root/dynamic-element[@name='"+ CarMake +"']/dynamic-content").getText() : "";
		String description = document.selectSingleNode("/root/dynamic-element[@name='"+ CarModel +"']").hasContent() ? 
			document.selectSingleNode("/root/dynamic-element[@name='"+ CarModel +"']/dynamic-content").getText() : "";
		String priceRangeFrom = document.selectSingleNode("/root/dynamic-element[@name='"+ PriceRangeFrom +"']").hasContent() ? 
			document.selectSingleNode("/root/dynamic-element[@name='"+ PriceRangeFrom +"']/dynamic-content").getText() : "";
		String priceRangeTo = document.selectSingleNode("/root/dynamic-element[@name='"+ PriceRangeTo +"']").hasContent() ? 
			document.selectSingleNode("/root/dynamic-element[@name='"+ PriceRangeTo +"']/dynamic-content").getText() : "";
		String fuelEfficiency = document.selectSingleNode("/root/dynamic-element[@name='"+ FuelEfficiency +"']").hasContent() ? 
			document.selectSingleNode("/root/dynamic-element[@name='"+ FuelEfficiency +"']/dynamic-content").getText() : "";
		JSONObject coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='"+ CarImage +"']").hasContent() ? 
			document.selectSingleNode("/root/dynamic-element[@name='"+ CarImage +"']/dynamic-content").getText() : "{}");
        
        String imageUrl = "";
        if(coverImageJO.has("fileEntryId")) {
        	DLFileEntry image = DLFileEntryLocalServiceUtil.getFileEntry(coverImageJO.getLong("fileEntryId"));
	           
	        if (image != null) {
	            imageUrl =
                PortalUtil.getPortalURL(renderRequest) + "/documents/" + image.getGroupId() + "/" +
                    image.getFolderId() + "/" + image.getTitle() + "/" + image.getUuid() + "?t=" +
                    System.currentTimeMillis();
	        } 
        }
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        
        DDMStructure struct = DDMStructureManagerUtil.getStructure(themeDisplay.getLayout().getGroupId(),
				Long.valueOf(PortalUtil.getClassNameId(JournalArticle.class)), String.valueOf(journalArticle.getDDMStructureKey()));
        String carMaketitle  = getFieldValueName(themeDisplay, renderRequest, struct,
    			CarMake, title);
		
		JSONObject res = new JSONObject();
		res.put("id", journalArticle.getId());
		res.put("resourcePrimKey", journalArticle.getResourcePrimKey());
		res.put("title", carMaketitle);
		res.put("priceRangeFrom", priceRangeFrom);
		res.put("priceRangeTo", priceRangeTo);
		res.put("fuelEfficiency", fuelEfficiency);
		res.put("description", description);
		res.put("imageURL", imageUrl);
		res.put("detailURL", "program-detail?categName=Cars&groupId="+ themeDisplay.getLayout().getGroupId()
				+ "&articleId=" +  journalArticle.getResourcePrimKey());
		return res;
	}

	private JSONObject getFeaturedAppsData(JournalArticle journalArticle, Document document,
			RenderRequest renderRequest) throws PortalException, JSONException {
		String title = document.selectSingleNode("/root/dynamic-element[@name='"+ FeaturedAppTitle +"']").hasContent() ? 
			document.selectSingleNode("/root/dynamic-element[@name='"+ FeaturedAppTitle +"']/dynamic-content").getText() : "";
		String description = document.selectSingleNode("/root/dynamic-element[@name='"+ FeaturedAppDescription +"']").hasContent() ? 
			document.selectSingleNode("/root/dynamic-element[@name='"+ FeaturedAppDescription +"']/dynamic-content").getText() : "";
		JSONObject coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='"+ FeaturedAppImage +"']").hasContent() ? 
			document.selectSingleNode("/root/dynamic-element[@name='"+ FeaturedAppImage +"']/dynamic-content").getText() : "{}");
        
        String imageUrl = "";
        if(coverImageJO.has("fileEntryId")) {
        	DLFileEntry image = DLFileEntryLocalServiceUtil.getFileEntry(coverImageJO.getLong("fileEntryId"));
	           
	        if (image != null) {
	            imageUrl =
	                PortalUtil.getPortalURL(renderRequest) + "/documents/" + image.getGroupId() + "/" +
	                    image.getFolderId() + "/" + image.getTitle() + "/" + image.getUuid() + "?t=" +
	                    System.currentTimeMillis();
	        } 
        }
         
		JSONObject res = new JSONObject();
		res.put("id", journalArticle.getId());
		res.put("resourcePrimKey", journalArticle.getResourcePrimKey());
		res.put("title", title);
		res.put("description", description);
		res.put("imageURL", imageUrl);
		res.put("category", "");
		res.put("created", journalArticle.getCreateDate());
		return res;
	}
	
	private JSONObject getInnovationChallengeData(JournalArticle journalArticle, Document document,
			RenderRequest renderRequest) throws PortalException, JSONException {
		String title = document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationChallengeTitle +"']").hasContent() ? 
			document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationChallengeTitle +"']/dynamic-content").getText() : "";
		String description = document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationChallengeDescription +"']").hasContent() ? 
			document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationChallengeDescription +"']/dynamic-content").getText() : "";
		JSONObject coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationChallengeImage +"']").hasContent() ? 
				document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationChallengeImage +"']/dynamic-content").getText() : "{}");
		
		String imageUrl = "";
		if(coverImageJO.has("fileEntryId")) {
			DLFileEntry image = DLFileEntryLocalServiceUtil.getFileEntry(coverImageJO.getLong("fileEntryId"));
			
			if (image != null) {
				imageUrl =
						PortalUtil.getPortalURL(renderRequest) + "/documents/" + image.getGroupId() + "/" +
								image.getFolderId() + "/" + image.getTitle() + "/" + image.getUuid() + "?t=" +
								System.currentTimeMillis();
			} 
		}
		
		JSONObject res = new JSONObject();
		res.put("id", journalArticle.getId());
		res.put("resourcePrimKey", journalArticle.getResourcePrimKey());
		res.put("title", title);
		res.put("description", description);
		res.put("imageURL", imageUrl);
		res.put("category", "");
		res.put("created", journalArticle.getCreateDate());
		return res;
	}

	private JSONObject getInnovationProgramData(JournalArticle journalArticle, Document document,
			RenderRequest renderRequest) throws PortalException, JSONException {
		String title = document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationProgramTitle +"']").hasContent() ? 
				document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationProgramTitle +"']/dynamic-content").getText() : "";
		String description = document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationProgramDescription +"']").hasContent() ? 
				document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationProgramDescription +"']/dynamic-content").getText() : "";
		JSONObject coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationProgramImage +"']").hasContent() ? 
				document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationProgramImage +"']/dynamic-content").getText() : "{}");
        
        String imageUrl = "";
        if(coverImageJO.has("fileEntryId")) {
        	DLFileEntry image = DLFileEntryLocalServiceUtil.getFileEntry(coverImageJO.getLong("fileEntryId"));
	           
	        if (image != null) {
	            imageUrl =
	                PortalUtil.getPortalURL(renderRequest) + "/documents/" + image.getGroupId() + "/" +
	                    image.getFolderId() + "/" + image.getTitle() + "/" + image.getUuid() + "?t=" +
	                    System.currentTimeMillis();
	        } 
        }
         
		JSONObject res = new JSONObject();
		res.put("id", journalArticle.getId());
		res.put("resourcePrimKey", journalArticle.getResourcePrimKey());
		res.put("title", title);
		res.put("description", description);
		res.put("imageURL", imageUrl);
		res.put("category", "");
		res.put("created", journalArticle.getCreateDate());
		return res;
	}

	private List<User> getFilteredUsers (List<User> usersByRole, JSONArray filter, String filterName) throws DocumentException {
		List<User> returnedArticles = new ArrayList<User>(); 
		String token = util.getToken(_portletConfiguration);
		
		String dropdowns = util.getDropdowns(token, _portletConfiguration.get("get-comp-attr"));
		if(!helper.isEmpty(dropdowns)) {
			JSONArray dropdownJA = new JSONArray(dropdowns); 
			String fieldValue = ""; 
			for (User user : usersByRole) {
				String userString = util.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), user.getEmailAddress()); 
 				JSONObject userJson = new JSONObject(userString);
				
				for(int i =0; i< dropdownJA.length() ; i++) {
					JSONObject jo = dropdownJA.getJSONObject(i);
					if(filterName.equalsIgnoreCase(jo.getString("name"))) {
						if(userJson.has(filterName) && 
								!String.valueOf(userJson.get(filterName)).equalsIgnoreCase("null")) { 
							fieldValue = String.valueOf(userJson.get(filterName)); 
						} 
					} 
				}
				for(int i=0; i< filter.length();i++) {
					String val = filter.getString(i);
					String[] fieldVals = fieldValue.split("/");
					for(String v : fieldVals) {
						if( v.equalsIgnoreCase(val) ) {
							returnedArticles.add(user);
							break;
						}
					}
				}
			}
		}
		return returnedArticles;
	}

	private JSONObject getDataAndToolsData(JournalArticle journalArticle, Document document,
			RenderRequest renderRequest) throws PortalException, JSONException {
		String title = document.selectSingleNode("/root/dynamic-element[@name='"+DataAndToolsTitle+"']").hasContent() ? 
			document.selectSingleNode("/root/dynamic-element[@name='"+DataAndToolsTitle+"']/dynamic-content").getText() : "";
		String description = document.selectSingleNode("/root/dynamic-element[@name='"+DataAndToolsDescription+"']").hasContent() ? 
			document.selectSingleNode("/root/dynamic-element[@name='"+DataAndToolsDescription+"']/dynamic-content").getText() : "";
		JSONObject coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='"+DataAndToolsImage+"']").hasContent() ? 
			document.selectSingleNode("/root/dynamic-element[@name='"+DataAndToolsImage+"']/dynamic-content").getText() : "{}");
		
        String imageUrl = "";
        if(coverImageJO.has("fileEntryId")) {
        	DLFileEntry image = DLFileEntryLocalServiceUtil.getFileEntry(coverImageJO.getLong("fileEntryId"));
	           
	        if (image != null) {
	            imageUrl =
	                PortalUtil.getPortalURL(renderRequest) + "/documents/" + image.getGroupId() + "/" +
	                    image.getFolderId() + "/" + image.getTitle() + "/" + image.getUuid() + "?t=" +
	                    System.currentTimeMillis();
	        } 
        }
         
		JSONObject res = new JSONObject();
		res.put("id", journalArticle.getId());
		res.put("resourcePrimKey", journalArticle.getResourcePrimKey());
		res.put("title", title);
		res.put("description", description);
		res.put("imageURL", imageUrl);
		res.put("category", "");
		res.put("created", journalArticle.getCreateDate());
		return res;
	}

	private JSONObject getOppIndustryData(JournalArticle journalArticle, Document document,
			RenderRequest renderRequest) throws PortalException, JSONException {
			String title = document.selectSingleNode("/root/dynamic-element[@name='"+OppIndustTitle+"']").hasContent() ? 
				document.selectSingleNode("/root/dynamic-element[@name='"+OppIndustTitle+"']/dynamic-content").getText() : "";
			String description = document.selectSingleNode("/root/dynamic-element[@name='"+OppIndustDescription+"']").hasContent() ? 
				document.selectSingleNode("/root/dynamic-element[@name='"+OppIndustDescription+"']/dynamic-content").getText() : "";
			JSONObject coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='"+OppIndustImage+"']").hasContent() ? 
				document.selectSingleNode("/root/dynamic-element[@name='"+OppIndustImage+"']/dynamic-content").getText() : "{}");
        
        String imageUrl = "";
        if(coverImageJO.has("fileEntryId")) {
        	DLFileEntry image = DLFileEntryLocalServiceUtil.getFileEntry(coverImageJO.getLong("fileEntryId"));
	           
	        if (image != null) {
	            imageUrl =
	                PortalUtil.getPortalURL(renderRequest) + "/documents/" + image.getGroupId() + "/" +
	                    image.getFolderId() + "/" + image.getTitle() + "/" + image.getUuid() + "?t=" +
	                    System.currentTimeMillis();
	        } 
        }
         
		JSONObject res = new JSONObject();
		res.put("id", journalArticle.getId());
		res.put("resourcePrimKey", journalArticle.getResourcePrimKey());
		res.put("title", title);
		res.put("description", description);
		res.put("imageURL", imageUrl);
		res.put("category", "");
		res.put("created", journalArticle.getCreateDate());
		return res;
	}

	private JSONObject getBasicProgramData(JournalArticle journalArticle, Document document, RenderRequest renderRequest) throws PortalException, JSONException {
		String title = document.selectSingleNode("/root/dynamic-element[@name='"+OnGoingTitle+"']").hasContent() ? 
				document.selectSingleNode("/root/dynamic-element[@name='"+OnGoingTitle+"']/dynamic-content").getText() : "";
			String description = document.selectSingleNode("/root/dynamic-element[@name='"+OngoingDescription+"']").hasContent() ? 
				document.selectSingleNode("/root/dynamic-element[@name='"+OngoingDescription+"']/dynamic-content").getText() : "";
			JSONObject coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='"+OnGoingImage+"']").hasContent() ? 
				document.selectSingleNode("/root/dynamic-element[@name='"+OnGoingImage+"']/dynamic-content").getText() : "{}");
        String category = document.selectSingleNode("/root/dynamic-element[@name='"+OnGoingProjectCategory+"']").hasContent()?
        		document.selectSingleNode("/root/dynamic-element[@name='"+OnGoingProjectCategory+"']/dynamic-content").getText() : "";
        
        String imageUrl = "";
        if(coverImageJO.has("fileEntryId")) {
        	DLFileEntry image = DLFileEntryLocalServiceUtil.getFileEntry(coverImageJO.getLong("fileEntryId"));
	           
	        if (image != null) {
	            imageUrl =
	                PortalUtil.getPortalURL(renderRequest) + "/documents/" + image.getGroupId() + "/" +
	                    image.getFolderId() + "/" + image.getTitle() + "/" + image.getUuid() + "?t=" +
	                    System.currentTimeMillis();
	        } 
        }
         
		JSONObject res = new JSONObject();
		res.put("id", journalArticle.getId());
		res.put("resourcePrimKey", journalArticle.getResourcePrimKey());
		res.put("title", title);
		res.put("description", description);
		
		if(category.equalsIgnoreCase("Option56129443")) res.put("category", "SolarEnergy");
		if(category.equalsIgnoreCase("Option37332878")) res.put("category", "WindEnergy");
		if(category.equalsIgnoreCase("Option58497406")) res.put("category", "Bioenergy");
		if(category.equalsIgnoreCase("Option48769494")) res.put("category", "HydroEnergy");
		if(category.equalsIgnoreCase("Option91511371")) res.put("category", "SoftwareApplicationIT");
		if(category.equalsIgnoreCase("Option31259007")) res.put("category", "Other");
		
		res.put("imageURL", imageUrl);
		res.put("created", journalArticle.getCreateDate());
		return res;
	}
	
	private List<JournalArticle> getSearchedArticles(ThemeDisplay themeDisplay, RenderRequest renderRequest,
			List<JournalArticle> filtered, String queryText, List<String> fieldName) throws DocumentException, PortalException {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>(); 
		try {
			queryText = URLDecoder.decode(queryText.toLowerCase(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			queryText = queryText.toLowerCase();
		} 
		
		for (JournalArticle article : filtered) {
			String content = article.getContentByLocale(themeDisplay.getLanguageId());
			Document document = SAXReaderUtil.read(content);
			String title= "", desc = "";
			for(String fName : fieldName) {
				if(content.contains(fName)) {
					if(fName.contains("RichText")) {
						desc = document.selectSingleNode("/root/dynamic-element[@name='" + fName + "']").hasContent() ? 
								document.selectSingleNode("/root/dynamic-element[@name='" + fName + "']/dynamic-content").getText() : "";
					} else {
						title = document.selectSingleNode("/root/dynamic-element[@name='" + fName + "']").hasContent() ? 
								document.selectSingleNode("/root/dynamic-element[@name='" + fName + "']/dynamic-content").getText() : "";
					}
					
					if(fName.equalsIgnoreCase(CarMake)) {
						DDMStructure struct = DDMStructureManagerUtil.getStructure(themeDisplay.getLayout().getGroupId(),
							Long.valueOf(PortalUtil.getClassNameId(JournalArticle.class)), String.valueOf(article.getDDMStructureKey()));
						
						String optionValueName = getFieldValueName(themeDisplay, renderRequest, struct, CarMake, title);
						if(optionValueName.equalsIgnoreCase(queryText)) {
							returnedArticles.add(article);
							break;
						}
					}else {
						if(title.toLowerCase().contains(queryText) || desc.toLowerCase().contains(queryText)) {
							returnedArticles.add(article);
							break;
						}
					}
				}
			}
		}
		return returnedArticles;
	}

	private List<JournalArticle> getFilteredArticlesByCreatedDate(List<JournalArticle> filtered, JSONArray filter) {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>(); 
		for(JournalArticle article : filtered) {
			LocalDate cdate = article.getCreateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			for(int j=0; j< filter.length();j++) {
				String date = filter.getString(j);
				boolean addArticle = false;
				if(date.equalsIgnoreCase("week")) {
					LocalDate start = LocalDate.now().with(DayOfWeek.MONDAY);
				    if( cdate.isBefore(start) && cdate.isAfter(start.minusDays(7)) ) {
				    	addArticle = true;
					}
				} else if(date.equalsIgnoreCase("more")) {
				    if( cdate.isBefore(LocalDate.now().minusDays(7))) {
				    	addArticle = true;
					}
				} if(date.equalsIgnoreCase("recent")) {
				    if( cdate.isAfter(LocalDate.now().minusDays(7)) ) {
				    	addArticle = true;
					}
				} else if(date.equalsIgnoreCase("month")) {
					if( cdate.isBefore(LocalDate.now().minusDays(30)) ) {
				    	addArticle = true;
					}
				}
				
				if( addArticle ) {
					returnedArticles.add(article);
					break;
				}
			}
		}
		
		return returnedArticles;
	}

	private List<JournalArticle> getFilteredArticles(ThemeDisplay themeDisplay, List<JournalArticle> fetchedArticles,
			JSONArray filter, String fieldName, String fieldName2, JSONArray filter2) throws DocumentException {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>(); 
		
		for (JournalArticle article : fetchedArticles) {
			String content = article.getContentByLocale(themeDisplay.getLanguageId());
			Document document = SAXReaderUtil.read(content);
			
			String fieldValue = document.selectSingleNode("/root/dynamic-element[@name='"+ fieldName +"']").hasContent() ? 
					document.selectSingleNode("/root/dynamic-element[@name='"+ fieldName +"']/dynamic-content").getText() : "";
			
					String fieldValue2 ="";
			if(!helper.isEmpty(fieldName2)) {
				fieldValue2 = document.selectSingleNode("/root/dynamic-element[@name='"+ fieldName2 +"']").hasContent() ? 
						document.selectSingleNode("/root/dynamic-element[@name='"+ fieldName2 +"']/dynamic-content").getText() : "";
			}
			
			if(fieldName.equalsIgnoreCase(PriceRangeFrom) ||  fieldName2.equalsIgnoreCase(PriceRangeTo) ||
					fieldName.equalsIgnoreCase(FuelEfficiency)) {
				
				String fromValue = fieldValue, toValue = fieldValue2;
				
				int from = 0, to = 0;
				if(filter.length() > 0) {
					from = helper.isEmpty(filter.getString(0)) ? 0 : filter.getInt(0);
				}

				if(filter2.length() > 0) {
					to = helper.isEmpty(filter2.getString(0)) ? 0 : filter2.getInt(0);
				}
				
				if(from != 0 && to != 0  
						&& !helper.isEmpty(fromValue) && !helper.isEmpty(toValue)) {
					int f1 = Integer.valueOf(fromValue);
					int f2 = Integer.valueOf(toValue);
					if(f1 >= from && f2 <= to) {
						returnedArticles.add(article);
					}
				}
				else if(from != 0 && !helper.isEmpty(fromValue)) {
					int f1 = Integer.valueOf(fromValue);
					if(f1 >= from) {
						returnedArticles.add(article);
					}
				}else if(to != 0 && !helper.isEmpty(toValue)) {
					int f2 = Integer.valueOf(toValue);
					if(f2 <= to) {
						returnedArticles.add(article);
					}
				}
			}else {
				for(int i=0; i< filter.length();i++) {
					String val = filter.getString(i);
					if( fieldValue.equalsIgnoreCase(val) ) {
						returnedArticles.add(article);
						break;
					}
				}
			}
		}
		return returnedArticles;
	}

	private List<Long> getArticlesPermission(String userEmail) throws SQLException {
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement();
		List<Long> articleIds = new ArrayList<>();

		try {
			PreparedStatement preparedStatement = con.prepareStatement("select articleId from ProjectPermission where email=?");
			preparedStatement.setString(1, userEmail);

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				articleIds.add(rs.getLong(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			con.close();
		}
		return articleIds;
	}
	
	public void getFilterData(ThemeDisplay themeDisplay, RenderRequest renderRequest, DDMStructure struct) throws DocumentException, PortalException {
		
		String structName = struct.getName("en_US");
		JSONObject jo = new JSONObject(struct.getDefinition());
		JSONArray ja = new JSONArray(jo.get("fields").toString());
		
		List<String> filterFields = new ArrayList<String>();
		if(structName.equalsIgnoreCase("data-and-tools")) {
			filterFields = dataToolsFilter;
		}else if(structName.equalsIgnoreCase("opportunities-industries")) {
			filterFields = oppFilter;
		}else if(structName.equalsIgnoreCase("basic-programs")) {
			filterFields = basicProgFilter;
		}else if(structName.equalsIgnoreCase("innovation-programs")) {
			filterFields = InnovProgFilter;
		}else if(structName.equalsIgnoreCase("innovation-challenges")) {
			filterFields = InnovChallengeFilter;
		}else if(structName.equalsIgnoreCase("featured-apps")) {
			filterFields = FeaturedAppFilter;
		} 

		for (int i = 0; i < ja.length(); i++) {
			JSONObject joa = ja.getJSONObject(i);
			if (joa.get("type").equals("select")) {
				if (filterFields.contains(joa.getString("name"))) {
					JSONArray joOptions = new JSONArray(joa.get("options").toString());
					JSONArray jaRes = new JSONArray();
					for (int j = 0; j < joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j);
						JSONObject res = new JSONObject(); 
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					}  
					if(structName.equalsIgnoreCase("data-and-tools")) {
						if (joa.getString("name").equalsIgnoreCase(DataAndToolsType))
							renderRequest.setAttribute("DataToolType", helper.toList(jaRes));
						if (joa.getString("name").equalsIgnoreCase(DataAndToolsRenewable))
							renderRequest.setAttribute("RenewableEnergySources", helper.toList(jaRes));
						if (joa.getString("name").equalsIgnoreCase(DataAndToolsEfficiency))
							renderRequest.setAttribute("EnergyEfficiency", helper.toList(jaRes));
					}else if(structName.equalsIgnoreCase("opportunities-industries")) {
						if (joa.getString("name").equalsIgnoreCase(OppIndustType))
							renderRequest.setAttribute("Type", helper.toList(jaRes));
					}else if(structName.equalsIgnoreCase("basic-programs")) {
						if (joa.getString("name").equalsIgnoreCase(OnGoingProjectCategory))
							renderRequest.setAttribute("projectCategList", helper.toList(jaRes));
						if (joa.getString("name").equalsIgnoreCase(OnGoingStage))
							renderRequest.setAttribute("projectStageList", helper.toList(jaRes));
					}else if(structName.equalsIgnoreCase("innovation-programs")) {
						if (joa.getString("name").equalsIgnoreCase(InnovationProgramActivityType))
							renderRequest.setAttribute("typeOfActivity", helper.toList(jaRes));
						if (joa.getString("name").equalsIgnoreCase(InnovationProgramStage))
							renderRequest.setAttribute("progStage", helper.toList(jaRes));
					}else if(structName.equalsIgnoreCase("innovation-challenges")) {
						if (joa.getString("name").equalsIgnoreCase(InnovationChallengeChallengeType))
							renderRequest.setAttribute("challengeTypeList", helper.toList(jaRes));
					}else if(structName.equalsIgnoreCase("featured-apps")) {
						if (joa.getString("name").equalsIgnoreCase(FeaturedAppType))
							renderRequest.setAttribute("FeaturedAppList", helper.toList(jaRes));
					}
				} 	
			}
		}
	
	}
	
	public List<Object> getPageLimitObject(List<Object> articlesList, int pageNum, int pageSize) {
		if (CollectionUtils.isEmpty(articlesList))
			return articlesList;
		
		int start = pageNum*pageSize, end = (pageNum+1)*pageSize;
		if( start>articlesList.size() ) {
			start = 0;
			end = pageSize;
		}
		if( end>articlesList.size() ) {
			end = articlesList.size();
		}
		return articlesList.subList(start, end);
	}
	
	public List<JournalArticle> getPageLimit(List<JournalArticle> articles, int pageNum, int pageSize) {
		if (CollectionUtils.isEmpty(articles))
			return articles;
		
		int start = pageNum*pageSize, end = (pageNum+1)*pageSize;
		if( start>articles.size() ) {
			start = 0;
			end = pageSize;
		}
		if( end>articles.size() ) {
			end = articles.size();
		}
		return articles.subList(start, end);
	}
	
	private JSONObject getUserInfo(User user, ThemeDisplay themeDisplay, String categName) throws PortalException, UnsupportedEncodingException {
		JSONObject userJo = new JSONObject();
		String token = util.getToken(_portletConfiguration);
		String userString = util.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), user.getEmailAddress()); 
		JSONObject userJson = new JSONObject(userString);
		List<String> jsonDataNamesString = new ArrayList<String>();
		List<String> jsonDataNamesInt = new ArrayList<String>();
		List<String> jsonDataNamesDropdown = new ArrayList<String>();
		List<String> jsonDataNamesMultiDropdown = new ArrayList<String>();
		
		if(categName.equalsIgnoreCase(UserTypes.ResearcherUser.name())) {
			jsonDataNamesString =  Arrays.asList("firstName","lastName", "company", "logo", "universityToApply", 
					"linkToWebsite", "linkedIn");
			jsonDataNamesInt =  Arrays.asList("id");
		}
		
		if(categName.equalsIgnoreCase(UserTypes.CarDealerUser.name())) {
			jsonDataNamesString =  Arrays.asList("company", "logo", "companyWebsite");
			jsonDataNamesMultiDropdown =  Arrays.asList("carMake");
			jsonDataNamesInt =  Arrays.asList("id");
		}

		for(String data : jsonDataNamesString) {
			if(data.equalsIgnoreCase("logo")) {
				String userImageURL ="";
				if(userJson.has("logo") && !String.valueOf(userJson.get("logo")).equalsIgnoreCase("null")) {
					String userImageBase64 = userJson.getJSONObject("logo").getString("data");
					String extension = userJson.getJSONObject("logo").getString("extension");
					byte[] decodedString = Base64.getDecoder().decode(userImageBase64.getBytes("UTF-8"));
					
					userImageURL = "data:image/"+ extension +";base64," + Base64.getEncoder().encodeToString(decodedString); 
				}
				userJo.put("imageUrl", userImageURL); 
			}else if(userJson.has(data) && !String.valueOf(userJson.get(data)).equalsIgnoreCase("null")) {
				userJo.put(data, userJson.getString(data));
			}
		}
		
		for(String data : jsonDataNamesInt) {
			 if(userJson.has(data) && !String.valueOf(userJson.get(data)).equalsIgnoreCase("null")) {
				 userJo.put(data, userJson.getInt(data));
			}
		}
		
		String dropdowns = util.getDropdowns(token, _portletConfiguration.get("get-all-attr"));
		JSONArray dropdownJA = new JSONArray(dropdowns); 
		
		for(String data : jsonDataNamesDropdown) {
			for(int i =0; i< dropdownJA.length() ; i++) {
				JSONObject jo = dropdownJA.getJSONObject(i);
				if(data.equalsIgnoreCase(jo.getString("name"))) {
					JSONArray listValueJA = new JSONArray(jo.getString("listValue"));
					for(int j =0; j< listValueJA.length(); j++) {
						JSONObject listValJO = listValueJA.getJSONObject(j);
						if(userJson.has(data) && !String.valueOf(userJson.get(data)).equalsIgnoreCase("null") &&
								listValJO.getInt("Id") == userJson.getInt(data) ) { 
							if(themeDisplay.getLanguageId().equalsIgnoreCase("en_US"))
								userJo.put(data,  listValJO.has("Text")? listValJO.getString("Text") : "");
							if(themeDisplay.getLanguageId().equalsIgnoreCase("ar_SA"))
								userJo.put(data,  listValJO.has("TextAr")? listValJO.getString("TextAr") : "");
							if(themeDisplay.getLanguageId().equalsIgnoreCase("fr_FR"))
								userJo.put(data, listValJO.has("TextFr")? listValJO.getString("TextFr") : "");
							break;
						}
					} 
				} 
			}
		}
		
		for(String data : jsonDataNamesMultiDropdown) {
			for(int i =0; i< dropdownJA.length() ; i++) {
				JSONObject jo = dropdownJA.getJSONObject(i);
				if(data.equalsIgnoreCase(jo.getString("name"))) {
					JSONArray listValueJA = new JSONArray(jo.getString("listValue"));
					if(userJson.has(data) && !String.valueOf(userJson.get(data)).equalsIgnoreCase("null")) {
						String[] vals = userJson.getString(data).split("/");
						String mainbusAct = "";
						for(String val : vals) {
							if(!helper.isEmpty(val)) {
								for(int j =0; j< listValueJA.length(); j++) {
									JSONObject listValJO = listValueJA.getJSONObject(j);
									if(listValJO.getInt("Id") == Integer.valueOf(val)) {
										if(themeDisplay.getLanguageId().equalsIgnoreCase("en_US"))
											mainbusAct += listValJO.getString("Text") + ", ";
										if(themeDisplay.getLanguageId().equalsIgnoreCase("ar_SA"))
											mainbusAct += listValJO.getString("TextAr") + ", ";
										if(themeDisplay.getLanguageId().equalsIgnoreCase("fr_FR"))
											mainbusAct += listValJO.getString("TextFr") + ", ";
									}							
								}
							}
						}
						mainbusAct = helper.isEmpty(mainbusAct) ? "" : mainbusAct.substring(0, mainbusAct.length() - 1); 
						userJo.put(data, mainbusAct);
					} 
				} 
			}
		}
		return userJo; 
		
	}
	
	private List<User> getSearchedUsers(List<User> usersByRole, String queryText) {
		List<User> returnedArticles = new ArrayList<User>(); 
		try {
			queryText = URLDecoder.decode(queryText.toLowerCase(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			queryText = queryText.toLowerCase();
		}
		for (User user : usersByRole) {
			String userFullName = user.getFullName(); 
			if(userFullName.toLowerCase().contains(queryText)) {
				returnedArticles.add(user);
			}
		}
		return returnedArticles;
	}
	
	private void getIAMFilter(RenderRequest renderRequest, ThemeDisplay themeDisplay, String userRoleName) {
		
		List<String> jsonDataNames = new ArrayList<String>();
		if(userRoleName.equalsIgnoreCase(UserTypes.CarDealerUser.name())) {
			jsonDataNames = Arrays.asList("carMake"); 
		}else if(userRoleName.equalsIgnoreCase(UserTypes.ResearcherUser.name())) {
			jsonDataNames = Arrays.asList("AreaOfExpertise"); 
		}
		
		String token = util.getToken(_portletConfiguration);
		String dropdowns = util.getDropdowns(token, _portletConfiguration.get("get-all-attr"));
		JSONArray dropdownJA = new JSONArray(dropdowns);
		
		for(String data : jsonDataNames) {
			for(int i =0; i< dropdownJA.length() ; i++) {
				JSONObject jo = dropdownJA.getJSONObject(i);
				if(data.equalsIgnoreCase(jo.getString("name"))) {
					JSONArray listValueJA = new JSONArray(jo.getString("listValue"));
					JSONArray jaRes = new JSONArray();
					for(int j =0; j< listValueJA.length(); j++) {
						JSONObject listValJO = listValueJA.getJSONObject(j);
						JSONObject res = new JSONObject(); 
						res.put("id", listValJO.getInt("Id"));
						
						String value = "";
						if(themeDisplay.getLanguageId().equalsIgnoreCase("en_US"))
							value = listValJO.getString("Text");
						if(themeDisplay.getLanguageId().equalsIgnoreCase("ar_SA"))
							value = listValJO.getString("TextAr");
						if(themeDisplay.getLanguageId().equalsIgnoreCase("fr_FR"))
							value = listValJO.getString("TextFr");
						
						res.put("value", value);
						jaRes.put(res);
					}
					renderRequest.setAttribute(data, helper.toList(jaRes));
				}
			}
		}
	}
	
	public JSONArray sortCars(JSONArray jaRes, String sortField, String sort) {
		JSONArray sortedJsonArray = jaRes;
		List<JSONObject> jsonValues = new ArrayList<JSONObject>();
	    for (int i = 0; i < jaRes.length(); i++) {
	        jsonValues.add(jaRes.getJSONObject(i));
	    }
		 Collections.sort(jsonValues, new Comparator<JSONObject>() {
	         private final String KEY_NAME = sortField;
	         @Override
	         public int compare(JSONObject a, JSONObject b) {
	            int int1 = 0;
	            int int2 = 0;
	            try {
	               int1 = Integer.valueOf(helper.ifEmpty(a.getString(KEY_NAME), "0"));
	               int2 = Integer.valueOf(helper.ifEmpty(b.getString(KEY_NAME), "0"));
	               if(sort.equalsIgnoreCase("asc"))
	            	   return int1 < int2 ? 1 : (int1 > int2 ? -1 : 0);
	               if(sort.equalsIgnoreCase("desc"))
	            	   return int1 > int2 ? 1 : (int1 > int2 ? -1 : 0);
	            } catch(JSONException e) {
	               e.printStackTrace();
	            }
	            return 0;
	         } 
	      });
		sortedJsonArray = new JSONArray(jsonValues);
		return sortedJsonArray;
	}
	
	public String getFieldValueName(ThemeDisplay themeDisplay, RenderRequest renderRequest,
			DDMStructure ddmStructure, String fieldName, String value) throws DocumentException, PortalException {
		String title = "";
		JSONObject jo = new JSONObject(ddmStructure.getDefinition());
		JSONArray ja = new JSONArray(jo.get("fields").toString());
		 
		for (int i = 0; i < ja.length(); i++) {
			JSONObject joa = ja.getJSONObject(i);
			if (joa.get("type").equals("select")) {
				if (fieldName.equalsIgnoreCase(joa.getString("name"))) {
					JSONArray joOptions = new JSONArray(joa.get("options").toString());
					for (int j = 0; j < joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j);
						if(value.equalsIgnoreCase(option.getString("value"))) {
							title = String.valueOf(new JSONObject(option.get("label").toString()).get("en_US"));
						}
					}  
				} 	
			}
		}
		return title;
	}
}