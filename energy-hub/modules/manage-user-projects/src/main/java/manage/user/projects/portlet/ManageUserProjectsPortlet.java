package manage.user.projects.portlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import manage.user.projects.constants.ManageUserProjectsPortletKeys;
import manage.user.projects.helper.UserTypes;
import manage.user.projects.helper.helper;
import manage.user.projects.helper.util;

/**
 * @author vyo
 */
@Component(immediate = true, property = { "com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css", "com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=ManageUserProjects", "javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ManageUserProjectsPortletKeys.MANAGEUSERPROJECTS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" }, 
service = Portlet.class)

public class ManageUserProjectsPortlet extends MVCPortlet {
	private final static Configuration _portletConfiguration = ConfigurationFactoryUtil
			.getConfiguration(PortalClassLoaderUtil.getClassLoader(), "portlet");
	
	private String InnovationProgramCategoryName = "Innovation Programs", InnovationChallengeCategoryName = "Innovation Challenges", 
			OppIndustCategoryName = "Opportunities for Industries", OnGoingCategoryName = "Ongoing Projects", CarsCategoryName = "Cars";
	
	private String InnovationProgramStructureName = "innovation-programs", InnovationChallengeStructureName = "innovation-challenges", 
			OppIndustStructureName = "opportunities-industries", OnGoingStructureName = "basic-programs", carStructureName = "cars";
	
	private String InnovationProgramTitle = "Text03803647", InnovationProgramDescription = "RichText76165816", 
			InnovationProgramImage = "Image92271346", InnovationProgramActivityType = "SelectFromList81632169", 
			InnovationProgramStage = "SelectFromList56281506", InnovationProgramURL = "Text18184187";
	
	private String OnGoingTitle = "Text73021449", OnGoingImage = "Image13718074", OngoingDescription = "RichText70541782",
			OnGoingWebsiteURL = "Text85717445", OnGoingStage = "SelectFromList36742361", OnGoingProjectCategory = "SelectFromList03925321";
	
	private String InnovationChallengeTitle = "Text68843417", InnovationChallengeDescription = "RichText96949120", 
			InnovationChallengeImage = "Image57179735", InnovationChallengeChallengeType = "SelectFromList17093910";
	
	private String OppIndustTitle = "Text11601604", OppIndustDescription = "RichText78666240", 
			OppIndustImage = "Image48657786", OppIndustType = "SelectFromList51477468";
	
	private String CarModel ="Text93196265", CarMake ="SelectFromList20767174", CarImage ="Image16722469";
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		try {
			HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
			long[] categId = new long[] { Long.valueOf(httpReq.getParameter("p_r_p_categoryId")) };
			long folderId = Long.valueOf(httpReq.getParameter("folderId"));
			long ddmStructureKey = Long.valueOf(httpReq.getParameter("ddmStructureKey"));
			String ddmTemplateKey = httpReq.getParameter("ddmTemplateKey");

			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
			long userId = themeDisplay.getUserId();
			String userEmail = themeDisplay.getUser().getEmailAddress();
			
			String sort = helper.ifEmpty(httpReq.getParameter("sort"), "desc");
			String sortField = helper.ifEmpty(httpReq.getParameter("sortField"), "createDate");
			String articleTypeFilter = helper.ifEmpty(httpReq.getParameter("articleTypeFilter"), "");
			String queryText = helper.ifEmpty(httpReq.getParameter("queryText"), "");
			JSONObject filter = new JSONObject(helper.ifEmpty(httpReq.getParameter("filter"), "{}"));
			int pageNo = Integer.valueOf(helper.ifEmpty(httpReq.getParameter("pageNo"), "0"));
			int pageSize = Integer.valueOf(helper.ifEmpty(httpReq.getParameter("pageSize"), "5"));
			AssetCategory category = AssetCategoryLocalServiceUtil.getAssetCategory(categId[0]);
			
			String token = util.getToken(_portletConfiguration);
			User user = themeDisplay.getUser();
			String userString = util.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), user.getEmailAddress());
			int userRole = 0;
			if(!helper.isEmpty(userString)) {
				userRole = new JSONObject(userString).getInt("userApplicationRoleId");
			}
			
			List<AssetCategory> categs = new ArrayList<AssetCategory>();
			List<com.liferay.dynamic.data.mapping.model.DDMStructure> structs = new ArrayList<com.liferay.dynamic.data.mapping.model.DDMStructure>();
			//Get Article Categories by User Type Role
			
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.InvestorUserIAM.name()))){
				if(helper.isEmpty(articleTypeFilter)){
					categs = AssetCategoryLocalServiceUtil.getCategories().stream()
							.filter(categ -> categ.getName().equalsIgnoreCase(InnovationProgramCategoryName) 
									|| categ.getName().equalsIgnoreCase(InnovationChallengeCategoryName)).collect(Collectors.toList());
					
					structs = DDMStructureLocalServiceUtil.getStructures().stream()
							.filter(st -> st.getName("en_US").equalsIgnoreCase(InnovationChallengeStructureName)
									|| st.getName("en_US").equalsIgnoreCase(InnovationProgramStructureName)).collect(Collectors.toList());
				}else if(articleTypeFilter.equalsIgnoreCase("InnoChall")){
					categs = AssetCategoryLocalServiceUtil.getCategories().stream()
							.filter(categ -> categ.getName().equalsIgnoreCase(InnovationChallengeCategoryName)).collect(Collectors.toList());
					
					structs = DDMStructureLocalServiceUtil.getStructures().stream()
							.filter(st -> st.getName("en_US").equalsIgnoreCase(InnovationChallengeStructureName)).collect(Collectors.toList());
				}else if(articleTypeFilter.equalsIgnoreCase("InnoProg")){
					categs = AssetCategoryLocalServiceUtil.getCategories().stream()
							.filter(categ -> categ.getName().equalsIgnoreCase(InnovationProgramCategoryName)).collect(Collectors.toList());
					
					structs = DDMStructureLocalServiceUtil.getStructures().stream()
							.filter(st -> st.getName("en_US").equalsIgnoreCase(InnovationProgramStructureName)).collect(Collectors.toList());
				}
				renderRequest.setAttribute("showTypeFilter", true);
				
			}else if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.PolicyMakerUserIAM.name()))
					&& category.getName().equalsIgnoreCase(InnovationChallengeCategoryName)) {
				categs = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase(InnovationChallengeCategoryName)).collect(Collectors.toList());
				
				structs = DDMStructureLocalServiceUtil.getStructures().stream()
						.filter(st -> st.getName("en_US").equalsIgnoreCase(InnovationChallengeStructureName)).collect(Collectors.toList());
				
			}else if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.PolicyMakerUserIAM.name()))
					&& category.getName().equalsIgnoreCase(OppIndustCategoryName)) {
				categs = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase(OppIndustCategoryName)).collect(Collectors.toList());
				
				structs = DDMStructureLocalServiceUtil.getStructures().stream()
						.filter(st -> st.getName("en_US").equalsIgnoreCase(OppIndustStructureName)).collect(Collectors.toList());
				
			}else if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.EntrepreneurUserIAM.name()))) {
				categs = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase(OnGoingCategoryName)).collect(Collectors.toList());
				
				structs = DDMStructureLocalServiceUtil.getStructures().stream()
						.filter(st -> st.getName("en_US").equalsIgnoreCase(OnGoingStructureName)).collect(Collectors.toList());
			}else if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.CarDealerUserIAM.name())) ||
					userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.AdministratorIAM.name()))) {
				categs = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase(CarsCategoryName)).collect(Collectors.toList());
				
				structs = DDMStructureLocalServiceUtil.getStructures().stream()
						.filter(st -> st.getName("en_US").equalsIgnoreCase(carStructureName)).collect(Collectors.toList());
			}
			
			long[] categIds = new long[categs.size()];
			List<String> categNames = new ArrayList<String>();
			for (int i = 0; i < categs.size(); i++) {
				categIds[i] = categs.get(i).getCategoryId();
				categNames.add(categs.get(i).getName());
			}
			
			//fill Filter Data for Filter Dropdown
			getFilterData(themeDisplay, renderRequest, structs);
			
			List<AssetEntry> finalassetEntryList = new ArrayList<AssetEntry>();
			//Get Articles by Category Id
			AssetEntryQuery assetEntryQuery = new AssetEntryQuery();
			assetEntryQuery.setAnyCategoryIds(categIds);
			
			//SORT
			assetEntryQuery.setOrderByCol1(sortField);
			assetEntryQuery.setOrderByType1(sort);
			
			List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
			
			System.out.println("assetEntryList  " + assetEntryList.size());
			//Get Draft
			 assetEntryQuery.setVisible(false);
			 List<AssetEntry> draftList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
			 System.out.println("draftList  " + draftList.size());
			
			//Get Expired
            List<JournalArticle> expiredJ = new ArrayList<JournalArticle>();
            @SuppressWarnings("deprecation")
			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticle.class);
            Property statusProperty = PropertyFactoryUtil.forName("status");
            dynamicQuery.add(statusProperty.eq(3));
            
            List<JournalArticle> results = JournalArticleLocalServiceUtil.dynamicQuery(dynamicQuery);
            for(JournalArticle res : results) {
                List<AssetCategory> jCatgs = AssetCategoryServiceUtil.getCategories(JournalArticle.class.getName(), res.getResourcePrimKey());
                for(long categ: categIds) {
                    AssetCategory catg = AssetCategoryServiceUtil.getCategory(categ);
                    if(jCatgs.contains(catg)) {
                        expiredJ.add(res);
                    }
                }
            }
             
           List<AssetEntry> expiredList = new ArrayList<AssetEntry>();
           List<JournalArticle> majors = getMajorVesrion(expiredJ);
           for(JournalArticle maj : majors) {
               if(maj.getStatus() == 3) {
                   AssetEntry entry = AssetEntryLocalServiceUtil.getEntry(JournalArticle.class.getName(), maj.getResourcePrimKey());
                   expiredList.add(entry);
               }
           }
           
           System.out.println("expiredList  " + expiredList.size());
		
           for(AssetEntry ass : assetEntryList)
               if(!finalassetEntryList.contains(ass))
                   finalassetEntryList.add(ass);
           
               System.out.println("finalassetEntryList after assetEntryList  " + finalassetEntryList.size());
            for(AssetEntry expired : expiredList)
                if(!finalassetEntryList.contains(expired))
                    finalassetEntryList.add(expired);
            
            System.out.println("finalassetEntryList after exp  " + finalassetEntryList.size());
            
            for(AssetEntry draft : draftList)
                if(!finalassetEntryList.contains(draft))
                    finalassetEntryList.add(draft);
            
            System.out.println("finalassetEntryList after draft " + finalassetEntryList.size());
			List<JournalArticle> filtered = new ArrayList<JournalArticle>();
			for (AssetEntry ae : finalassetEntryList) {
			    JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.fetchJournalArticleResource(ae.getClassPK());
			    if(journalArticleResource != null) {
			    	JournalArticle journalArticle = JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey());
			    	if(journalArticle.getUserId() == userId) {
			    		if(category.getName().equalsIgnoreCase(OnGoingCategoryName)) {
			    			//Get articles with User View Permission
			    			List<Long> articleIds = getArticlesPermission(userEmail);
			    			if (articleIds.contains(journalArticle.getId())) 
			    				filtered.add(journalArticle);
			    		}else {
			    			filtered.add(journalArticle);
			    		}
			    	}
			    }
			}

			if(categNames.contains(InnovationProgramCategoryName)) {
				//FILTER
				if(filter.has("programStage") && filter.getJSONArray("programStage").length()>0 ) {
					filtered = getFilteredArticles(themeDisplay, filtered, filter.getJSONArray("programStage"), InnovationProgramStage);
				}
				if(filter.has("typeOfActivity") && filter.getJSONArray("typeOfActivity").length()>0 ) {
					filtered = getFilteredArticles(themeDisplay, filtered, filter.getJSONArray("typeOfActivity"), InnovationProgramActivityType);
				}
			}
			if(categNames.contains(InnovationChallengeCategoryName)) {
				//FILTER
				if(filter.has("challengeType") && filter.getJSONArray("challengeType").length()>0 ) {
					filtered = getFilteredArticles(themeDisplay, filtered, filter.getJSONArray("challengeType"), InnovationChallengeChallengeType);
				}
			}
			if(categNames.contains(OnGoingCategoryName)) {
				//FILTER
				if(filter.has("stage") && filter.getJSONArray("stage").length()>0 ) {
					filtered = getFilteredArticles(themeDisplay, filtered, filter.getJSONArray("stage"), OnGoingStage);
				}
				if(filter.has("category") && filter.getJSONArray("category").length()>0 ) {
					filtered = getFilteredArticles(themeDisplay, filtered, filter.getJSONArray("category"), OnGoingProjectCategory);
				}
				if(filter.has("status") && filter.getJSONArray("status").length()>0 ) {
					filtered = getFilteredArticlesByStatus(filtered, filter.getJSONArray("status"));
				}
			}
			if(categNames.contains(OppIndustCategoryName)) {
				//FILTER
				if(filter.has("challengeType") && filter.getJSONArray("challengeType").length()>0 ) {
					filtered = getFilteredArticles(themeDisplay, filtered, filter.getJSONArray("challengeType"), InnovationChallengeChallengeType);
				}
			} 
			if(categNames.contains(CarsCategoryName)) {
				//FILTER
				if(filter.has("carMake") && filter.getJSONArray("carMake").length()>0 ) {
					filtered = getFilteredArticles(themeDisplay, filtered, filter.getJSONArray("carMake"), CarMake);
				}
			} 
			
			//SEARCH
			if(!helper.isEmpty(queryText)) {
				List<String> searchField = new ArrayList<String>();
				if(categNames.contains(OnGoingCategoryName)) {
					searchField.add(OnGoingTitle);
					searchField.add(OngoingDescription);
					searchField.add(OnGoingWebsiteURL);
				} if(categNames.contains(InnovationProgramCategoryName)) {
					searchField.add(InnovationProgramTitle);
					searchField.add(InnovationProgramDescription);
					searchField.add(InnovationProgramURL);
				} if(categNames.contains(InnovationChallengeCategoryName)) {
					searchField.add(InnovationChallengeTitle);
					searchField.add(InnovationChallengeDescription);
				} if(categNames.contains(OppIndustCategoryName)) {
					searchField.add(OppIndustTitle);
					searchField.add(OppIndustDescription);
				} if(categNames.contains(CarsCategoryName)) {
					searchField.add(CarMake);
					searchField.add(CarModel);
				}
				
				filtered = getSearchedArticles(themeDisplay, renderRequest, filtered, queryText, searchField);
			}
			
			//PAGINATION
			List<JournalArticle> pagedArticles = getPageLimit(filtered, pageNo, pageSize);
			
			String addProjectUrl = "" ;
			
			if(category.getName().equalsIgnoreCase(InnovationProgramCategoryName)) {
				addProjectUrl = "/custom-forms?createInnovationProgram&p_r_p_categoryId=" + categId[0] + "&folderId=" + folderId
						+ "&ddmStructureKey=" + ddmStructureKey + "&ddmTemplateKey=" + ddmTemplateKey;
			} else if(category.getName().equalsIgnoreCase(OnGoingCategoryName)) {
				 addProjectUrl = "/add-project?p_r_p_categoryId=" + categId[0] + "&folderId=" + folderId
							+ "&ddmStructureKey=" + ddmStructureKey + "&ddmTemplateKey=" + ddmTemplateKey;
			} else if(category.getName().equalsIgnoreCase(InnovationChallengeCategoryName)) {
				 addProjectUrl = "/custom-forms?createInnovationChallenge&p_r_p_categoryId=" + categId[0] + "&folderId=" + folderId
							+ "&ddmStructureKey=" + ddmStructureKey + "&ddmTemplateKey=" + ddmTemplateKey;
			} else if(category.getName().equalsIgnoreCase(OppIndustCategoryName)) {
				addProjectUrl = "/custom-forms?createOpportunitiesIndustries&p_r_p_categoryId=" + categId[0] + "&folderId=" + folderId
					+ "&ddmStructureKey=" + ddmStructureKey + "&ddmTemplateKey=" + ddmTemplateKey;
			} else if(category.getName().equalsIgnoreCase(CarsCategoryName)) {
				String parentCategId = httpReq.getParameter("parentCategId");
				addProjectUrl = "/custom-forms?createCars&p_r_p_categoryId=" + categId[0] + "&folderId=" + folderId
						+ "&ddmStructureKey=" + ddmStructureKey + "&ddmTemplateKey=" + ddmTemplateKey + "&parentCategId=" + parentCategId ;
			}
			
			List<Object> programs = null;
			JSONArray jaRes = new JSONArray();
			for (JournalArticle article : pagedArticles) {
				String content = article.getContentByLocale(themeDisplay.getLanguageId());;
				Document document = SAXReaderUtil.read(content);

				String projectTitle = "";
				JSONObject coverImageJO = new JSONObject();
				
				category = AssetCategoryLocalServiceUtil.getCategories(JournalArticle.class.getName(), article.getResourcePrimKey()).get(0);
				ddmStructureKey = Long.valueOf(article.getDDMStructureKey());
				ddmTemplateKey = article.getDDMTemplateKey();
				folderId = article.getFolderId();
				
				String editProjectUrl ="";
				if(category.getName().equalsIgnoreCase(InnovationProgramCategoryName)) {
					projectTitle = document.selectSingleNode("/root/dynamic-element[@name='" + InnovationProgramTitle + "']/dynamic-content").getText();
					coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationProgramImage +"']") != null && 
		        			document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationProgramImage +"']").hasContent()?
		                			document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationProgramImage +"']/dynamic-content").getText() : "{}");
					editProjectUrl = "/custom-forms?createInnovationProgram&p_r_p_categoryId=" + category.getCategoryId() + "&folderId=" + folderId
							+ "&ddmStructureKey=" + ddmStructureKey + "&ddmTemplateKey=" + ddmTemplateKey;
				} else if(category.getName().equalsIgnoreCase(OnGoingCategoryName)) {
					 projectTitle = document.selectSingleNode("/root/dynamic-element[@name='"+ OnGoingTitle +"']/dynamic-content").getText();
					 coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='"+ OnGoingImage +"']") != null && 
			        			document.selectSingleNode("/root/dynamic-element[@name='"+ OnGoingImage +"']").hasContent()?
			                			document.selectSingleNode("/root/dynamic-element[@name='"+ OnGoingImage +"']/dynamic-content").getText() : "{}");
					 
					 editProjectUrl = "/add-project?p_r_p_categoryId=" + category.getCategoryId() + "&folderId=" + folderId
								+ "&ddmStructureKey=" + ddmStructureKey + "&ddmTemplateKey=" + ddmTemplateKey;
				} else if(category.getName().equalsIgnoreCase(InnovationChallengeCategoryName)) {
					 projectTitle = document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationChallengeTitle +"']/dynamic-content").getText();
					 coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationChallengeImage +"']") != null && 
			        			document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationChallengeImage +"']").hasContent()?
			                			document.selectSingleNode("/root/dynamic-element[@name='"+ InnovationChallengeImage +"']/dynamic-content").getText() : "{}");
					 
					 editProjectUrl = "/custom-forms?createInnovationChallenge&p_r_p_categoryId=" + category.getCategoryId() + "&folderId=" + folderId
								+ "&ddmStructureKey=" + ddmStructureKey + "&ddmTemplateKey=" + ddmTemplateKey;
				} else if(category.getName().equalsIgnoreCase(OppIndustCategoryName)) {
					projectTitle = document.selectSingleNode("/root/dynamic-element[@name='"+ OppIndustTitle +"']/dynamic-content").getText();
					coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='"+ OppIndustImage +"']") != null && 
		        			document.selectSingleNode("/root/dynamic-element[@name='"+ OppIndustImage +"']").hasContent()?
		                			document.selectSingleNode("/root/dynamic-element[@name='"+ OppIndustImage +"']/dynamic-content").getText() : "{}");
					
					editProjectUrl = "/custom-forms?createOpportunitiesIndustries&p_r_p_categoryId=" + category.getCategoryId()  + "&folderId=" + folderId
							+ "&ddmStructureKey=" + ddmStructureKey + "&ddmTemplateKey=" + ddmTemplateKey;
				} else if(category.getName().equalsIgnoreCase(CarsCategoryName)) {
					projectTitle = document.selectSingleNode("/root/dynamic-element[@name='"+ CarMake +"']/dynamic-content").getText();
					projectTitle  = getDataValue(themeDisplay, renderRequest, article.getDDMStructure(),
		        			CarMake, projectTitle);
					
					coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='"+ CarImage +"']") != null && 
		        			document.selectSingleNode("/root/dynamic-element[@name='"+ CarImage +"']").hasContent()?
		                			document.selectSingleNode("/root/dynamic-element[@name='"+ CarImage +"']/dynamic-content").getText() : "{}");
					
					String parentCategId = httpReq.getParameter("parentCategId");
					editProjectUrl = "/custom-forms?createCars&p_r_p_categoryId=" + category.getCategoryId()  + "&folderId=" + folderId
							+ "&ddmStructureKey=" + ddmStructureKey + "&ddmTemplateKey=" + ddmTemplateKey + "&parentCategId=" + parentCategId ;
				}
				editProjectUrl += "&articleId=" + article.getResourcePrimKey();
				
				String imageUrl = "";
				if (coverImageJO.has("fileEntryId")) {
					DLFileEntry image = DLFileEntryLocalServiceUtil
							.getFileEntry(coverImageJO.getLong("fileEntryId"));

					if (image != null) {
						imageUrl = PortalUtil.getPortalURL(renderRequest) + "/documents/" + image.getGroupId() + "/"
								+ image.getFolderId() + "/" + image.getTitle() + "/" + image.getUuid() + "?t="
								+ System.currentTimeMillis();
					}
				}

				SimpleDateFormat simpleformat = new SimpleDateFormat("dd/MMMM/yyyy");
				String date = simpleformat.format(article.getCreateDate());

				JSONObject res = new JSONObject();
				res.put("id", article.getId());
				res.put("creationDate", date.replace("/", " "));
				res.put("projectTitle", projectTitle);
				res.put("projectType", category.getName());
				res.put("coverImage", imageUrl);
				res.put("status", article.getStatus());
				res.put("viewOnly", article.getUserId() != userId);
				res.put("editProjectUrl", editProjectUrl);

				jaRes.put(res);
			}

			programs = helper.toList(jaRes);

			int totalPages=  filtered.isEmpty() ? 0 : (int) Math.floor(filtered.size()/pageSize);
			renderRequest.setAttribute("addProjectUrl", addProjectUrl);
			renderRequest.setAttribute("totalPages", totalPages);
			renderRequest.setAttribute("programs", programs);
			renderRequest.setAttribute("pageNo", pageNo > totalPages ? 0 : pageNo);
			renderRequest.setAttribute("pageSize", pageSize);
			super.render(renderRequest, renderResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<JournalArticle> getSearchedArticles(ThemeDisplay themeDisplay, RenderRequest renderRequest, List<JournalArticle> filtered, String queryText, List<String> fieldName) throws DocumentException, PortalException {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>(); 
		try {
			queryText = URLDecoder.decode(queryText.toLowerCase(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			queryText = queryText.toLowerCase();
		}
		
		for (JournalArticle article : filtered) {
			String content = article.getContentByLocale(themeDisplay.getLanguageId());;
			Document document = SAXReaderUtil.read(content);
			for(String fName : fieldName) {
				if(content.contains(fName)) {
					String title = document.selectSingleNode("/root/dynamic-element[@name='" + fName + "']").hasContent() ? 
							document.selectSingleNode("/root/dynamic-element[@name='" + fName + "']/dynamic-content").getText() : "";
					
					if(fName.equalsIgnoreCase(CarMake)) {
						String optionValueName = getDataValue(themeDisplay, renderRequest, article.getDDMStructure(),
		        			CarMake, title);
						if(optionValueName.equalsIgnoreCase(queryText)) {
							returnedArticles.add(article);
							break;
						}
					}else  {
						if(title.toLowerCase().contains(queryText)) {
							returnedArticles.add(article);
							break;
						}
					}
				}
			}
		}
		return returnedArticles;
	}

	private List<JournalArticle> getFilteredArticlesByStatus(List<JournalArticle> filtered, JSONArray filter) {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>(); 
		for (JournalArticle article : filtered) {
			for(int i=0; i< filter.length();i++) {
				int val = filter.getInt(i);
				if( article.getStatus() == val) {
					returnedArticles.add(article);
					break;
				}
			}
		}
		return returnedArticles;
	}

	private List<JournalArticle> getFilteredArticles(ThemeDisplay themeDisplay, List<JournalArticle> fetchedArticles, JSONArray filter, String fieldName) throws DocumentException {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>(); 
		
		for (JournalArticle article : fetchedArticles) {
			String content = article.getContentByLocale(themeDisplay.getLanguageId());;
			Document document = SAXReaderUtil.read(content);
			String fieldValue = document.selectSingleNode("/root/dynamic-element[@name='"+ fieldName +"']").hasContent() ? 
					document.selectSingleNode("/root/dynamic-element[@name='"+ fieldName +"']/dynamic-content").getText() : "";
			
			for(int i=0; i< filter.length();i++) {
				String val = filter.getString(i);
				if( fieldValue.equalsIgnoreCase(val) ) {
					returnedArticles.add(article);
					break;
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
			PreparedStatement preparedStatement = con
					.prepareStatement("select articleId from ProjectPermission where email=?");
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

	@Override
	@SuppressWarnings("deprecation")
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException {
		resourceResponse.setContentType("text/html");
		PrintWriter out = resourceResponse.getWriter();
		String key = resourceRequest.getParameter("key");
		String articleId = resourceRequest.getParameter("id");
		String retVal = "";
		try {
			if (key.equalsIgnoreCase("delete")) {
				JournalArticle article = JournalArticleServiceUtil.getArticle(Long.valueOf(articleId));
				JournalArticleLocalServiceUtil.deleteArticle(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println(String.valueOf(retVal));
		out.flush();

		super.serveResource(resourceRequest, resourceResponse);
	}
	
	public void getFilterData(ThemeDisplay themeDisplay, RenderRequest renderRequest,
			List<com.liferay.dynamic.data.mapping.model.DDMStructure> structs) throws DocumentException, PortalException {
		
		for(com.liferay.dynamic.data.mapping.model.DDMStructure struct : structs) {
			JSONObject jo = new JSONObject(struct.getDefinition());
			JSONArray ja = new JSONArray(jo.get("fields").toString());
			
			for (int i = 0; i < ja.length(); i++) {
				JSONObject joa = ja.getJSONObject(i);
				if (joa.get("type").equals("select")) {
					if (joa.get("name").equals(OnGoingProjectCategory)) {
						JSONArray joOptions = new JSONArray(joa.get("options").toString());
						JSONArray jaRes = new JSONArray();
						for (int j = 0; j < joOptions.length(); j++) {
							JSONObject option = joOptions.getJSONObject(j);
							JSONObject res = new JSONObject();
							res.put("id", option.getString("value"));
							res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
							jaRes.put(res);
						}
						renderRequest.setAttribute("projectCategList", helper.toList(jaRes));
						
					} else if (joa.get("name").equals(OnGoingStage)) {
						JSONArray joOptions = new JSONArray(joa.get("options").toString());
						JSONArray jaRes = new JSONArray();
						for (int j = 0; j < joOptions.length(); j++) {
							JSONObject option = joOptions.getJSONObject(j);
							JSONObject res = new JSONObject();
							res.put("id", option.getString("value"));
							res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
							jaRes.put(res);
						}
						renderRequest.setAttribute("projectStageList", helper.toList(jaRes));
					}else if (joa.get("name").equals(InnovationProgramActivityType)) {
						JSONArray joOptions = new JSONArray(joa.get("options").toString());
						JSONArray jaRes = new JSONArray();
						for (int j = 0; j < joOptions.length(); j++) {
							JSONObject option = joOptions.getJSONObject(j);
							JSONObject res = new JSONObject();
							res.put("id", option.getString("value"));
							res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
							jaRes.put(res);
						}
						renderRequest.setAttribute("innovationProgramActivityTypeList", helper.toList(jaRes));
					}else if (joa.get("name").equals(InnovationProgramStage)) {
						JSONArray joOptions = new JSONArray(joa.get("options").toString());
						JSONArray jaRes = new JSONArray();
						for (int j = 0; j < joOptions.length(); j++) {
							JSONObject option = joOptions.getJSONObject(j);
							JSONObject res = new JSONObject();
							res.put("id", option.getString("value"));
							res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
							jaRes.put(res);
						}
						renderRequest.setAttribute("innovationProgramStageList", helper.toList(jaRes));
					} else if (joa.get("name").equals(InnovationChallengeChallengeType)) {
						JSONArray joOptions = new JSONArray(joa.get("options").toString());
						JSONArray jaRes = new JSONArray();
						for (int j = 0; j < joOptions.length(); j++) {
							JSONObject option = joOptions.getJSONObject(j);
							JSONObject res = new JSONObject();
							res.put("id", option.getString("value"));
							res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
							jaRes.put(res);
						}
						renderRequest.setAttribute("InnovationChallengeChallengeType", helper.toList(jaRes));
					} else if (joa.get("name").equals(OppIndustType)) {
						JSONArray joOptions = new JSONArray(joa.get("options").toString());
						JSONArray jaRes = new JSONArray();
						for (int j = 0; j < joOptions.length(); j++) {
							JSONObject option = joOptions.getJSONObject(j);
							JSONObject res = new JSONObject();
							res.put("id", option.getString("value"));
							res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
							jaRes.put(res);
						}
						renderRequest.setAttribute("Type", helper.toList(jaRes));
					} else if (joa.get("name").equals(CarMake)) {
						JSONArray joOptions = new JSONArray(joa.get("options").toString());
						JSONArray jaRes = new JSONArray();
						for (int j = 0; j < joOptions.length(); j++) {
							JSONObject option = joOptions.getJSONObject(j);
							JSONObject res = new JSONObject();
							res.put("id", option.getString("value"));
							res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
							jaRes.put(res);
						}
						renderRequest.setAttribute("CarMake", helper.toList(jaRes));
					}
				}
			}
		}
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
	
	public String getDataValue(ThemeDisplay themeDisplay, RenderRequest renderRequest, DDMStructure struct, String fieldName, String value) throws DocumentException, PortalException {
		String title = "";
		JSONObject jo = new JSONObject(struct.getDefinition());
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
	
	private List<JournalArticle> getMajorVesrion(List<JournalArticle> articles) throws PortalException {
        List<JournalArticle> res = new ArrayList<JournalArticle>();
        List<String> ids = new ArrayList<String>();
        for (JournalArticle art : articles) {
            if (!ids.contains(art.getArticleId())) {
                JournalArticle journalArticleLatest = JournalArticleLocalServiceUtil
                        .getLatestArticle(art.getResourcePrimKey());
                ids.add(art.getArticleId());
                res.add(journalArticleLatest);
            }
        }
        return res;
    }
}