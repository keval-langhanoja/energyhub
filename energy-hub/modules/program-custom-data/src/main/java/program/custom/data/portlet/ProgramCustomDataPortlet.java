package program.custom.data.portlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
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
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import program.custom.data.constants.ProgramCustomDataPortletKeys;
import program.custom.data.constants.UserTypes;
import program.custom.data.helper.helper;
import program.custom.data.helper.util;

/**
 * @author vyo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=ProgramCustomData",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.name=" + ProgramCustomDataPortletKeys.PROGRAMCUSTOMDATA,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user,administrator"
	},
	service = Portlet.class
)
public class ProgramCustomDataPortlet extends MVCPortlet {
	
	private long innovationProgramFolderId;
	private long innovationChalengeFolderId;
	private long onGoingFolderId;
	private long OppIndustFolderId;
	private long CarsFolderId;
		
	private String InnovationProgramTitle = "Text03803647", InnovationProgramDescription = "RichText76165816", 
			InnovationProgramImage = "Image92271346";
	
	private String OnGoingTitle = "Text73021449", OnGoingDescription = "RichText70541782",
			 OnGoingImage = "Image13718074";
	
	private String InnovationChallengeTitle = "Text68843417", InnovationChallengeDescription = "RichText96949120", 
			InnovationChallengeImage = "Image57179735";

	private String OppIndustTitle = "Text11601604", OppIndustDescription = "RichText78666240", 
			OppIndustImage = "Image48657786";
	
	private String CarModel ="Text93196265", CarMake ="SelectFromList20767174", CarImage ="Image16722469";
	
	private String degreeType= "SelectFromList75723891", attendance = "SelectFromList52092032";
	
	private final static Configuration _portletConfiguration = ConfigurationFactoryUtil
			.getConfiguration(PortalClassLoaderUtil.getClassLoader(), "portlet");
	
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY); 
			long groupId = themeDisplay.getLayout().getGroupId();

			AssetCategory assetCategory = null;
			HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));

			renderRequest.setAttribute("is_signed_in", themeDisplay.isSignedIn());
			renderRequest.setAttribute("groupId", groupId);
			renderRequest.setAttribute("folderId",  httpReq.getParameter("folderId"));
			renderRequest.setAttribute("ddmTemplateKey",  httpReq.getParameter("ddmTemplateKey"));
			renderRequest.setAttribute("parentCategId",  httpReq.getParameter("p_r_p_categoryId"));
			
			String ipadd =  PortalUtil.getHttpServletRequest(renderRequest).getRemoteAddr();
			if(ipadd.equalsIgnoreCase("51.178.45.207")) { //Offline
				innovationProgramFolderId = 40788;
				innovationChalengeFolderId = 40790;
				onGoingFolderId = 40796;
				OppIndustFolderId = 40786;
				CarsFolderId = 40850;
			}else if(ipadd.equalsIgnoreCase("51.178.45.174")) { //Online
				innovationProgramFolderId = 42641;
				innovationChalengeFolderId = 42643;
				onGoingFolderId = 42649;
				OppIndustFolderId = 42639;
				CarsFolderId = 42703;
			}else { //Local
				innovationProgramFolderId = 52803;
				innovationChalengeFolderId = 52805;
				onGoingFolderId = 52811;
				OppIndustFolderId = 49029;
				CarsFolderId = 98056;
			}
			
			if(httpReq.getParameterMap().containsKey("userDetail")) {
				long userId = Long.valueOf(httpReq.getParameter("articleId"));
				String categName = httpReq.getParameter("categName");
				User currentUser = UserLocalServiceUtil.getUser(userId);
				String token = util.getToken(_portletConfiguration);
				String userString = util.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), currentUser.getEmailAddress()); 
				JSONObject currentUserJO = new JSONObject();
				if(!new JSONObject(userString).has("usernotFound")) {
					renderRequest.setAttribute("currentUser", helper.toMap(currentUserJO));
				}
				
				if(categName.equalsIgnoreCase(UserTypes.ResearcherUser.name())
						|| categName.equalsIgnoreCase(UserTypes.IndustryUser.name()) 
						|| categName.equalsIgnoreCase(UserTypes.InvestorUser.name()) 
						|| categName.equalsIgnoreCase(UserTypes.EntrepreneurUser.name()) 
						|| categName.equalsIgnoreCase(UserTypes.EnergyHubUser.name()) 
						|| categName.equalsIgnoreCase(UserTypes.EnergyCompanyUser.name())
						|| categName.equalsIgnoreCase(UserTypes.CarDealerUser.name()) 
						|| categName.equalsIgnoreCase(UserTypes.PolicyMakerUser.name())) {
					include("/userDetail.jsp", renderRequest, renderResponse);
					super.render(renderRequest, renderResponse);
				} 
				if(categName.equalsIgnoreCase(UserTypes.AcademicUser.name())) {
					
					User loggedInUser = themeDisplay.getUser();
					//currentUserJO ==> academmy User JSON
					if(currentUserJO.has("email") && currentUserJO.getString("email").equalsIgnoreCase(loggedInUser.getEmailAddress())) {
						renderRequest.setAttribute("showAddAcademic",true);
					} else if(themeDisplay.getUser().getRoles().contains(RoleLocalServiceUtil.fetchRole(37615))) {
						renderRequest.setAttribute("showAddAcademic", true);  
					} else renderRequest.setAttribute("showAddAcademic",false);
					//Get dropDowns Data for add a new Energy Program Form
					getDropdownsDataAcademic(themeDisplay, renderRequest);
					
					include("/customEnergyAcademicUserDetail.jsp", renderRequest, renderResponse);
					super.render(renderRequest, renderResponse);
				}
			} else {
				String p_r_p_categoryId = httpReq.getParameter("p_r_p_categoryId");
				AssetCategory categName = AssetCategoryLocalServiceUtil.getAssetCategory(Long.valueOf(p_r_p_categoryId));
				if(categName.getName().equalsIgnoreCase("innovation")) {
					String folderId = "", ddmStructureKey = "", ddmTemplateKey = "";
					String token = util.getToken(_portletConfiguration);
					User user = themeDisplay.getUser();
					String userString = util.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), user.getEmailAddress());
					
					//Get OnGoing Projects 
					if(themeDisplay.getPortletDisplay().getColumnId().equalsIgnoreCase("column-7")) {
						folderId = String.valueOf(onGoingFolderId);
						
						com.liferay.dynamic.data.mapping.model.DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
								.filter(st -> st.getName("en_US").equalsIgnoreCase("basic-programs")).findFirst().orElse(null);	
						ddmStructureKey = struct.getStructureKey();
						
						assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
								.filter(categ -> categ.getName().equalsIgnoreCase("Ongoing Projects")).findFirst().orElse(null);

						getCustomData(renderRequest, assetCategory, themeDisplay, groupId);
						renderRequest.setAttribute("categName", assetCategory.getName());
						renderRequest.setAttribute("categNameTitle", assetCategory.getTitle(themeDisplay.getLocale()));
						
						if(new JSONObject(userString).has("roleId")) {
							int userRole = new JSONObject(userString).getInt("userApplicationRoleId");
							if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.EntrepreneurUserIAM.name()))
									|| userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.AdministratorIAM.name()))){
								renderRequest.setAttribute("showAddOngoingProjects", true);  
							}
						}
						if(themeDisplay.getUser().getRoles().contains(RoleLocalServiceUtil.fetchRole(37615))) {
							renderRequest.setAttribute("showAddOngoingProjects", true);  
						}
					}
					
					//Get Innovation Programs
					if(themeDisplay.getPortletDisplay().getColumnId().equalsIgnoreCase("column-2")) {
						folderId = String.valueOf(innovationProgramFolderId);
						ddmTemplateKey = null;
						com.liferay.dynamic.data.mapping.model.DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
								.filter(st -> st.getName("en_US").equalsIgnoreCase("innovation-programs")).findFirst().orElse(null);	
						ddmStructureKey = struct.getStructureKey();
						
						assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
								.filter(categ -> categ.getName().equalsIgnoreCase("Innovation Programs")).findFirst().orElse(null);
						
						getCustomData(renderRequest, assetCategory, themeDisplay, groupId);
						renderRequest.setAttribute("categName", assetCategory.getName());
						renderRequest.setAttribute("categNameTitle", assetCategory.getTitle(themeDisplay.getLocale()));
	
						if(new JSONObject(userString).has("roleId")) {
							int userRole = new JSONObject(userString).getInt("userApplicationRoleId");
							if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.InvestorUserIAM.name()))
									|| userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.AdministratorIAM.name()))){
								renderRequest.setAttribute("showAddInnovationPrograms", true);  
							}
						}
						if(themeDisplay.getUser().getRoles().contains(RoleLocalServiceUtil.fetchRole(37615))) {
							renderRequest.setAttribute("showAddInnovationPrograms", true);  
						}
					}
					
					//Get Innovation Challenges
					if(themeDisplay.getPortletDisplay().getColumnId().equalsIgnoreCase("column-3")) {
						folderId = String.valueOf(innovationChalengeFolderId);
						ddmTemplateKey = null;
						com.liferay.dynamic.data.mapping.model.DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
								.filter(st -> st.getName("en_US").equalsIgnoreCase("innovation-challenges")).findFirst().orElse(null);	
						ddmStructureKey = struct.getStructureKey();
						
						assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
								.filter(categ -> categ.getName().equalsIgnoreCase("Innovation Challenges")).findFirst().orElse(null);
						
						getCustomData(renderRequest, assetCategory, themeDisplay, groupId);
						renderRequest.setAttribute("categName", assetCategory.getName());
						renderRequest.setAttribute("categNameTitle", assetCategory.getTitle(themeDisplay.getLocale()));
						
						if( !helper.isEmpty(userString) && new JSONObject(userString).has("roleId")) {
							int userRole = new JSONObject(userString).getInt("userApplicationRoleId");
							if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.InvestorUserIAM.name())) 
									|| userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.AdministratorIAM.name()))){
								
								renderRequest.setAttribute("showAddInnovationChallenges", true);  
							}
						}
						if(themeDisplay.getUser().getRoles().contains(RoleLocalServiceUtil.fetchRole(37615))) {
							renderRequest.setAttribute("showAddInnovationChallenges", true);  
						}
					}
					
					renderRequest.setAttribute("folderId", folderId);
					renderRequest.setAttribute("ddmStructureKey", ddmStructureKey); 
					renderRequest.setAttribute("ddmTemplateKey", ddmTemplateKey); 
					include("/view.jsp", renderRequest, renderResponse);
					super.render(renderRequest, renderResponse);
					
				} else if(categName.getName().equalsIgnoreCase("Industry") &&
						themeDisplay.getPortletDisplay().getColumnId().equalsIgnoreCase("column-2")){
					
					String folderId = "", ddmStructureKey = "", ddmTemplateKey = "";
					String token = util.getToken(_portletConfiguration);
					User user = themeDisplay.getUser();
					String userString = util.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), user.getEmailAddress());
					
					//Opportunities for Industries
					folderId = String.valueOf(OppIndustFolderId);
					ddmTemplateKey = null;
					com.liferay.dynamic.data.mapping.model.DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
							.filter(st -> st.getName("en_US").equalsIgnoreCase("opportunities-industries")).findFirst().orElse(null);	
					ddmStructureKey = struct.getStructureKey();
					
					assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
							.filter(categ -> categ.getName().equalsIgnoreCase("Opportunities for Industries")).findFirst().orElse(null);
					
					getCustomData(renderRequest, assetCategory, themeDisplay, groupId);
					renderRequest.setAttribute("categName", assetCategory.getName());
					renderRequest.setAttribute("categNameTitle", assetCategory.getTitle(themeDisplay.getLocale()));
					
					if(!helper.isEmpty(userString) && new JSONObject(userString).has("roleId")) {
						int userRole = new JSONObject(userString).getInt("userApplicationRoleId");
						if( userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.PolicyMakerIAM.name()))){
							renderRequest.setAttribute("showAddOpportunitiesIndustries", true);  
						}
					}
					if(themeDisplay.getUser().getRoles().contains(RoleLocalServiceUtil.fetchRole(37615))) {
						renderRequest.setAttribute("showAddOpportunitiesIndustries", true);  
					}
					
					renderRequest.setAttribute("folderId", folderId);
					renderRequest.setAttribute("ddmStructureKey", ddmStructureKey); 
					renderRequest.setAttribute("ddmTemplateKey", ddmTemplateKey); 
					include("/view.jsp", renderRequest, renderResponse);
					super.render(renderRequest, renderResponse);
				} else if(categName.getName().equalsIgnoreCase("Transportation")){
					String token = util.getToken(_portletConfiguration);
					User user = themeDisplay.getUser();
					String userString = util.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), user.getEmailAddress());
					
					//CARS
					com.liferay.dynamic.data.mapping.model.DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
							.filter(st -> st.getName("en_US").equalsIgnoreCase("cars")).findFirst().orElse(null);	
					String ddmStructureKey = struct.getStructureKey();
					
					assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
							.filter(categ -> categ.getName().equalsIgnoreCase("cars")).findFirst().orElse(null);
					
					getCustomData(renderRequest, assetCategory, themeDisplay, groupId);
					renderRequest.setAttribute("categName", assetCategory.getName());
					renderRequest.setAttribute("categNameTitle", assetCategory.getTitle(themeDisplay.getLocale()));
					
					if(!helper.isEmpty(userString) && new JSONObject(userString).has("roleId")) {
						int userRole = new JSONObject(userString).getInt("userApplicationRoleId");
						if( userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.AdministratorIAM.name()))){
							renderRequest.setAttribute("showAddCar", true);  
						}
					}
					if(themeDisplay.getUser().getRoles().contains(RoleLocalServiceUtil.fetchRole(37615))) {
						renderRequest.setAttribute("showAddCar", true);  
					}
					
					renderRequest.setAttribute("folderId", CarsFolderId);
					renderRequest.setAttribute("ddmStructureKey", ddmStructureKey); 
					renderRequest.setAttribute("carDealerUserRole", true);
					renderRequest.setAttribute("otherRoles", false); 
					renderRequest.setAttribute("showSeeAll", true);  
					renderRequest.setAttribute("seeAllUrl", "see-all?parentCategId=" + 
							p_r_p_categoryId +"&p_r_p_categoryId="+ assetCategory.getCategoryId() + "&ddmStructureKey=" + ddmStructureKey);  
					renderRequest.setAttribute("userRoleName", "Cars");  
					include("/viewSlider.jsp", renderRequest, renderResponse);
					super.render(renderRequest, renderResponse);
				}else {
					String userRoleName = "";
					AssetCategory categ = AssetCategoryLocalServiceUtil.getAssetCategory(Long.valueOf(p_r_p_categoryId));
					boolean isAcademic = false;
					if(categ.getName().equalsIgnoreCase("directory")) {
						userRoleName = UserTypes.ResearcherUser.name();
						renderRequest.setAttribute("userRoleName", userRoleName);  
						renderRequest.setAttribute("showSeeAll", true);  
					}
					if(categ.getName().equalsIgnoreCase("Industry")) {
						userRoleName = UserTypes.IndustryUser.name();
					} 
					if(categ.getName().equalsIgnoreCase("for-business")) {
						userRoleName = UserTypes.EnergyCompanyUser.name();
					} 
					if(categ.getName().equalsIgnoreCase("education")) {
						userRoleName = UserTypes.AcademicUser.name();
						isAcademic = true;
						renderRequest.setAttribute("showSeeAll", true);  
						renderRequest.setAttribute("seeAllUrl", "academic"); 
					} 
					if(!helper.isEmpty(userRoleName)) {
						List<User> usersByRole =  new ArrayList<User>();
						String rName = userRoleName;
						long userRole = RoleLocalServiceUtil.getRoles(themeDisplay.getCompanyId()).stream()
								.filter(st -> st.getName().equalsIgnoreCase(rName))
								.findFirst().orElse(null).getRoleId();	
						
						usersByRole =  UserLocalServiceUtil.getRoleUsers(userRole);
							
						JSONArray userJA = new JSONArray();
						for(User user : usersByRole) {  
							String token = util.getToken(_portletConfiguration);
							String userString = util.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), user.getEmailAddress()); 
							if(!new JSONObject(userString).has("usernotFound")) {
								JSONObject userJO = getUserInfo(user, themeDisplay,userRoleName, user.getUserId(), userString, token); 
								userJO.put("userUrl", "/detail?userDetail&categName="+ userRoleName +"&groupId="+
										themeDisplay.getLayout().getGroupId() +"&articleId="+ user.getUserId());
								userJO.put("userRoleName", userRoleName);
								userJA.put(userJO);
							}
						}
						
						renderRequest.setAttribute("usersByRole", helper.toList(userJA));
						
						if(categ.getName().equalsIgnoreCase("directory")) { 
							renderRequest.setAttribute("Org", usersByRole);
						}
					}
					renderRequest.setAttribute("isAcademic", isAcademic);  
					renderRequest.setAttribute("otherRoles", true); 
					renderRequest.setAttribute("carDealerUserRole", false); 
					include("/viewSlider.jsp", renderRequest, renderResponse);
					super.render(renderRequest, renderResponse);
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void serveResource(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
		try {
			addEngineeringPrograms(request);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	private void addEngineeringPrograms(ResourceRequest request) throws PortalException, DocumentException {
		JSONObject jsonData = new JSONObject();
		List<String> jsonDataNames = Arrays.asList("EngineeringPrograms", "Location", "Links", "Attendance",
				"DegreeType", "description", "folderId", "ddmTemplateKey");
		
		for (String dataName : jsonDataNames) {
			String value = ParamUtil.getString(request, dataName);
			jsonData.put(dataName, value);
		}
		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
				.filter(categ -> categ.getName().equalsIgnoreCase("Academic Universities")).findFirst().orElse(null);
		    
	    AssetEntryQuery assetEntryQuery = new AssetEntryQuery();
		assetEntryQuery.setAnyCategoryIds(new long[] {  assetCategory.getCategoryId() }); 
		List<JournalArticle> articles = new ArrayList<JournalArticle>();
		List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
		for (AssetEntry ae : assetEntryList) {
		    JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
		    JournalArticle journalArticle = JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey());
		    articles.add(journalArticle);
		}
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY); 
		User user = themeDisplay.getUser();
		//get related Programs for this user
		articles = getSearchedArticles(themeDisplay, articles, user.getEmailAddress(), Arrays.asList("Text03087898"));
		JournalArticle academicUserArticle = null;
		if(articles.size() > 0) {
			academicUserArticle = articles.get(0);
		}
		long userId = themeDisplay.getUserId();
		long groupId = themeDisplay.getLayout().getGroupId(); 
	    ServiceContext serviceContext = new ServiceContext();
	
	    serviceContext.setAddGroupPermissions(true);
	    serviceContext.setAddGuestPermissions(true);
	    serviceContext.setScopeGroupId(groupId);
	    serviceContext.setAssetCategoryIds(new long[] {assetCategory.getCategoryId()}); 
    	serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
	    Map<Locale, String> titleMap = new HashMap<Locale, String>();
	    Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
	    titleMap.put(Locale.US, user.getEmailAddress());
	    
	    try {  
	    	String fieldsGroupElt = " <dynamic-element index-type=\"\" instance-id=\"wiFI6rGL\" name=\"FieldsGroup66038080\" type=\"fieldset\">\r\n";
		    if(jsonData.has("EngineeringPrograms")) { 
		    	fieldsGroupElt += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\"RichText80306402\" type=\"text\">\r\n" + 
			    		"		<dynamic-content language-id=\"en_US\"><![CDATA[" + jsonData.getString("EngineeringPrograms")  + "]]></dynamic-content>\r\n" + 
			    		"	</dynamic-element>\r\n";
		    }

		    if(jsonData.has("Location")) {
		    	fieldsGroupElt +=	"	<dynamic-element index-type=\"text\" instance-id=\"HXfAY6hy\" name=\"Text84722742\" type=\"rich_text\">\r\n" + 
			    		"		<dynamic-content language-id=\"en_US\"><![CDATA["+ jsonData.getString("Location") +"]]></dynamic-content>\r\n" + 
			    		"	</dynamic-element>\r\n" ;
		    }
		    
		    if(jsonData.has("Links")) {
		    	fieldsGroupElt +="	<dynamic-element index-type=\"keyword\" instance-id=\"tBkABnKP\" name=\"Text45772616\" type=\"select\">\r\n" +
				    		"		<dynamic-content language-id=\"en_US\"><![CDATA["+ jsonData.getString("Links") +"]]></dynamic-content>\r\n" + 
				    		"	</dynamic-element>\r\n" ;
		    }
		    
		    if(jsonData.has("Attendance")) {
		    	fieldsGroupElt +="	<dynamic-element index-type=\"keyword\" instance-id=\"tBkABnKP\" name=\"SelectFromList52092032\" type=\"select\">\r\n" +
		    			"		<dynamic-content language-id=\"en_US\"><![CDATA["+ jsonData.getString("Attendance") +"]]></dynamic-content>\r\n" + 
		    			"	</dynamic-element>\r\n" ;
		    }
		    if(jsonData.has("DegreeType")) {
		    	fieldsGroupElt +="	<dynamic-element index-type=\"keyword\" instance-id=\"tBkABnKP\" name=\"SelectFromList75723891\" type=\"select\">\r\n" +
		    			"		<dynamic-content language-id=\"en_US\"><![CDATA["+ jsonData.getString("DegreeType") +"]]></dynamic-content>\r\n" + 
		    			"	</dynamic-element>\r\n" ;
		    }
		    if(jsonData.has("description")) {
		    	fieldsGroupElt +="	<dynamic-element index-type=\"keyword\" instance-id=\"tBkABnKP\" name=\"RichText48466122\" type=\"select\">\r\n" +
		    			"		<dynamic-content language-id=\"en_US\"><![CDATA["+ jsonData.getString("description") +"]]></dynamic-content>\r\n" + 
		    			"	</dynamic-element>\r\n" ;
		    }
		    fieldsGroupElt += "	</dynamic-element>";
	    
		    com.liferay.dynamic.data.mapping.model.DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
					.filter(st -> st.getName("en_US").equalsIgnoreCase("academic")).findFirst().orElse(null);	
		    
		    if(academicUserArticle != null) {
		    	String content = academicUserArticle.getContent();
		    	String xmlContent = content.replace("</root>", fieldsGroupElt + "</root>");
			    academicUserArticle = JournalArticleLocalServiceUtil.updateArticle(userId, groupId, academicUserArticle.getFolderId(), String
		    			.valueOf(academicUserArticle.getArticleId()), academicUserArticle.getVersion(), xmlContent, serviceContext);
		    }else {
		    	String xmlContent = "<?xml version=\"1.0\"?>\r\n" + 
			    		"\r\n" + 
			    		"<root available-locales=\"en_US\" default-locale=\"en_US\" version=\"1.0\">\r\n" ; 
			    
			    xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\"Text03087898\" type=\"text\">\r\n" + 
			    		"		<dynamic-content language-id=\"en_US\"><![CDATA[" + user.getEmailAddress()  + "]]></dynamic-content>\r\n" + 
			    		"	</dynamic-element>\r\n";
			    
			    xmlContent += fieldsGroupElt;
			    
			    xmlContent += "</root>";
				    
		    	academicUserArticle = JournalArticleLocalServiceUtil.addArticle(userId, groupId, jsonData.getLong("folderId"), titleMap, descriptionMap,
						xmlContent, struct.getStructureKey(), jsonData.getString("ddmTemplateKey"), serviceContext);
		    }
		} catch (Exception ex) {
			ex.printStackTrace();
	    }
	}

	private List<JournalArticle> getSearchedArticles(ThemeDisplay themeDisplay, List<JournalArticle> filtered, String queryText, List<String> fieldName) throws DocumentException {
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
					
					if(title.toLowerCase().contains(queryText)) {
						returnedArticles.add(article);
					}
				}
			}
		}
		return returnedArticles;
	}
	
	private JSONObject getUserInfo(User user, ThemeDisplay themeDisplay, String categName, long userId, String userString, String token) throws PortalException, UnsupportedEncodingException, DocumentException {
		JSONObject userJo = new JSONObject();
		
		JSONObject userJson = new JSONObject(userString);
		List<String> jsonDataNamesString = new ArrayList<String>();
		List<String> jsonDataNamesInt = new ArrayList<String>();
		List<String> jsonDataNamesDropdown = new ArrayList<String>();
		List<String> jsonDataNamesMultiDropdown = new ArrayList<String>();
		
		if(categName.equalsIgnoreCase(UserTypes.InvestorUser.name())) {
			jsonDataNamesString =  Arrays.asList("companyWebsite", "description", "firstName", "lastName", "email", "company",
					"logo", "linkToWebsite", "linkedIn", "companyPhoneNumber");
			jsonDataNamesInt =  Arrays.asList("id", "yearOfEstablishment");
			jsonDataNamesDropdown =  Arrays.asList("numberOfEmployees", "country", "kaza", "profession");
			jsonDataNamesMultiDropdown = Arrays.asList("stage");
			userJo.put("userRole", "InvestorUserRole");
		}
		if(categName.equalsIgnoreCase(UserTypes.CarDealerUser.name())) {
			jsonDataNamesString =  Arrays.asList("companyWebsite","company", "logo", "linkToWebsite", "companyPhoneNumber", "otherCarMake", "googleMapLink");
			jsonDataNamesInt =  Arrays.asList("id", "yearOfEstablishment");
			jsonDataNamesDropdown =  Arrays.asList("country", "kaza");
			jsonDataNamesMultiDropdown = Arrays.asList("carMake");
			userJo.put("userRole", "CarDealerUserRole");
		}
		if(categName.equalsIgnoreCase(UserTypes.IndustryUser.name())) {
			jsonDataNamesString =  Arrays.asList("companyWebsite","company", "logo", "linkToWebsite", "companyPhoneNumber");
			jsonDataNamesInt =  Arrays.asList("id", "yearOfEstablishment");
			jsonDataNamesDropdown =  Arrays.asList("sector", "numberOfEmployees", "country", "kaza");
			userJo.put("userRole", "IndustryUserRole");
		}
		if(categName.equalsIgnoreCase(UserTypes.EnergyCompanyUser.name())) {
			jsonDataNamesString =  Arrays.asList("companyWebsite","company", "logo", "email", "linkToWebsite", "linkedIn");
			jsonDataNamesInt =  Arrays.asList("id", "yearOfEstablishment");
			jsonDataNamesDropdown =  Arrays.asList("profession", "numberOfEmployees", "country", "kaza");
			jsonDataNamesMultiDropdown = Arrays.asList("mainBusinessActivity");
			userJo.put("userRole", "EnergyCompanyUserRole");
		}
		if(categName.equalsIgnoreCase(UserTypes.EnergyHubUser.name())) {
			jsonDataNamesString =  Arrays.asList("firstName","lastName", "logo", "email", "company", "linkToWebsite", "linkedIn");
			jsonDataNamesInt =  Arrays.asList("id", "yearOfEstablishment");
			jsonDataNamesDropdown =  Arrays.asList("profession", "country", "kaza");
			userJo.put("userRole", "EnergyHubUserRole");
		}
		if(categName.equalsIgnoreCase(UserTypes.PolicyMakerUser.name())) {
			jsonDataNamesString =  Arrays.asList("companyWebsite", "description", "email", "company", 
					"logo", "linkToWebsite", "linkedIn", "companyPhoneNumber");
			jsonDataNamesInt =  Arrays.asList("id");
			jsonDataNamesDropdown =  Arrays.asList("country", "kaza", "profession");
			jsonDataNamesMultiDropdown = Arrays.asList("organizationType");
			userJo.put("userRole", "PolicyMakerRole");
		}
		if(categName.equalsIgnoreCase(UserTypes.EntrepreneurUser.name())) {
			jsonDataNamesString =  Arrays.asList("firstName","lastName", "logo", "email", 
					"solutionOfferingDescription", "company", "linkToWebsite", "linkedIn");
			jsonDataNamesInt =  Arrays.asList("id");
			jsonDataNamesDropdown =  Arrays.asList("role", "typeOfSupport", "country", "kaza", "profession");
			userJo.put("userRole", "EntrepreneurUserRole");
		}
		if(categName.equalsIgnoreCase(UserTypes.ResearcherUser.name())) {
			jsonDataNamesString =  Arrays.asList("universityToApply", "firstName", "lastName", "email", "company", "logo", 
					"linkToWebsite", "linkedIn");
			jsonDataNamesInt =  Arrays.asList("id");
			jsonDataNamesDropdown =  Arrays.asList("country", "kaza", "profession", "maturityLevelOfSolution", "challengeToSolve");
			jsonDataNamesMultiDropdown = Arrays.asList("sdgSolution");
			userJo.put("userRole", "ResearcherUserRole");
		}
		if(categName.equalsIgnoreCase(UserTypes.AcademicUser.name())) {
			jsonDataNamesString =  Arrays.asList("universityName","description", "acronym", "logo", 
					"linkToWebsite", "linkedIn", "email", "companyPhoneNumber");
			jsonDataNamesInt =  Arrays.asList("id");
			userJo.put("academicReditUrl", "/detail?userDetail&categName=AcademicUser&groupId=&articleId="+ userId +"&folderId=&ddmTemplateKey=");
			userJo.put("userRole", "AcademicUserRole");
			//Get Programs
			 AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase("Academic Universities")).findFirst().orElse(null);
			    
			    AssetEntryQuery assetEntryQuery = new AssetEntryQuery();
				assetEntryQuery.setAnyCategoryIds(new long[] {  assetCategory.getCategoryId() }); 
				List<JournalArticle> articles = new ArrayList<JournalArticle>();
				List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
				for (AssetEntry ae : assetEntryList) {
				    JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
				    JournalArticle journalArticle = JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey());
				    articles.add(journalArticle);
				}
				
				//get related Programs for this user
				articles = getSearchedArticles(themeDisplay, articles, user.getEmailAddress(), Arrays.asList("Text03087898"));
			if(articles.size() > 0) {
				JournalArticle academicUser = articles.get(0);
				//get fields group data for the Energy Programs
				JSONArray energyProgramsJA = helper.getFieldGroupFromContentXml(academicUser, themeDisplay.getLocale(), "FieldsGroup66038080");
				userJo.put("programs", energyProgramsJA);
			}
		} 
		
		String email="";
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
				if(data.equalsIgnoreCase("email")) email = userJson.getString(data);
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
											mainbusAct += "- " + listValJO.getString("Text") +"<br>";
										if(themeDisplay.getLanguageId().equalsIgnoreCase("ar_SA"))
											mainbusAct += "- " + listValJO.getString("TextAr")+"<br>";
										if(themeDisplay.getLanguageId().equalsIgnoreCase("fr_FR"))
											mainbusAct += "- " + listValJO.getString("TextFr")+"<br>";
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
		

		if(categName.equalsIgnoreCase(UserTypes.ResearcherUser.name())) {
			com.liferay.dynamic.data.mapping.model.DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
					.filter(st -> st.getName("en_US").equalsIgnoreCase("area-expertise")).findFirst().orElse(null);	
			String ddmStructureKey = struct.getStructureKey(); 
			List<JournalArticle> articles = JournalArticleLocalServiceUtil
					.getStructureArticles(themeDisplay.getLayout().getGroupId(), ddmStructureKey);
			articles = getSearchedArticles(themeDisplay, articles, email , Arrays.asList("Text63214587"));
			if(articles.size() > 0) {
				JSONArray areas = helper.getFieldGroupFromContentXmlArea(articles.get(0), themeDisplay.getLocale(), "FieldsGroup65973700");
				userJo.put("areas", helper.toList(areas));
			}
		}
		return userJo; 
	}

	private void getCustomData(RenderRequest renderRequest, AssetCategory assetCategory, 
			ThemeDisplay themeDisplay, long groupId) throws PortalException, DocumentException {
		JSONArray jaRes = new JSONArray();
		String ddmStructureKey = "", ddmTemplateKey = "";
		long folderId = 0;
		
		long categoryId = assetCategory.getCategoryId();
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();
		assetEntryQuery.setAnyCategoryIds(new long[] { categoryId });
		assetEntryQuery.setOrderByCol1("createDate");
		assetEntryQuery.setEnd(4);
		List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
		for (AssetEntry ae : assetEntryList) {
		    JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
		    JournalArticle journalArticle = JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey());

		    folderId = journalArticle.getFolderId();
		    ddmStructureKey = journalArticle.getDDMStructureKey();
		    ddmTemplateKey = journalArticle.getDDMTemplateKey();
		    String content = journalArticle.getContentByLocale(themeDisplay.getLanguageId());;
		    Document document = SAXReaderUtil.read(content);
		    String title = "", description = "", category = "";
		    JSONObject coverImageJO = new JSONObject();
		    
		    String liferayTitle ="", liferayDesc ="", liferayImage ="";
	        if(assetCategory.getName().equalsIgnoreCase("Innovation Programs")) {
	        	liferayTitle = InnovationProgramTitle;
	        	liferayDesc = InnovationProgramDescription;
	        	liferayImage = InnovationProgramImage;
	        }else if (assetCategory.getName().equalsIgnoreCase("Ongoing Projects")) {
	        	liferayTitle = OnGoingTitle;
	        	liferayDesc = OnGoingDescription;
	        	liferayImage = OnGoingImage;
	        }else if(assetCategory.getName().equalsIgnoreCase("Innovation Challenges")) {
	        	liferayTitle = InnovationChallengeTitle;
	        	liferayDesc = InnovationChallengeDescription;
	        	liferayImage = InnovationChallengeImage;
	        }else if(assetCategory.getName().equalsIgnoreCase("Opportunities for Industries")) {
	        	liferayTitle = OppIndustTitle;
	        	liferayDesc = OppIndustDescription;
	        	liferayImage = OppIndustImage;
	        }else if(assetCategory.getName().equalsIgnoreCase("Cars")) {
	        	liferayTitle = CarMake;
	        	liferayDesc = CarModel;
	        	liferayImage = CarImage;
	        }
	        
        	title = document.selectSingleNode("/root/dynamic-element[@name='"+  liferayTitle +"']").hasContent()?
        			document.selectSingleNode("/root/dynamic-element[@name='"+ liferayTitle +"']/dynamic-content").getText() : "";
	        description = document.selectSingleNode("/root/dynamic-element[@name='"+ liferayDesc +"']").hasContent()?
        			document.selectSingleNode("/root/dynamic-element[@name='"+ liferayDesc +"']/dynamic-content").getText() : "";
	        coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='"+ liferayImage +"']") != null && 
        			document.selectSingleNode("/root/dynamic-element[@name='"+ liferayImage +"']").hasContent()?
        			document.selectSingleNode("/root/dynamic-element[@name='"+ liferayImage +"']/dynamic-content").getText() : "{}");
	        
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
			if(assetCategory.getName().equalsIgnoreCase("Cars")) {
	        	String carMaketitle  = getFilterData(themeDisplay, renderRequest, journalArticle.getDDMStructure(),
        			CarMake, title);
				res.put("id", journalArticle.getId());
				res.put("pk", journalArticle.getResourcePrimKey());
				res.put("carMake", carMaketitle);
				res.put("carModel", description);
				res.put("category", category);
				res.put("imageURL", imageUrl);
				res.put("userUrl", "program-detail?categName=Cars&groupId="+ themeDisplay.getLayout().getGroupId()
						+"&articleId="+ journalArticle.getResourcePrimKey());
			}else {
				res.put("id", journalArticle.getId());
				res.put("pk", journalArticle.getResourcePrimKey());
				res.put("title", title);
				res.put("description", description);
				res.put("category", category);
				res.put("imageURL", imageUrl);
			}
			
			jaRes.put(res); 
		}	
		 
		renderRequest.setAttribute("folderId", folderId);
		renderRequest.setAttribute("ddmStructureKey", ddmStructureKey); 
		renderRequest.setAttribute("ddmTemplateKey", ddmTemplateKey); 
		renderRequest.setAttribute("categoryId", categoryId);  
		if(assetCategory.getName().equalsIgnoreCase("Ongoing Projects")) {
			renderRequest.setAttribute("UrlStart", "program-detail-comments");
		} else 
			renderRequest.setAttribute("UrlStart", "program-detail");
		
		renderRequest.setAttribute("journalArticleList", helper.toList(jaRes));
	}
	
	public String getFilterData(ThemeDisplay themeDisplay, RenderRequest renderRequest, DDMStructure struct, String fieldName, String value) throws DocumentException, PortalException {
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
	
	public void getDropdownsDataAcademic(ThemeDisplay themeDisplay, RenderRequest renderRequest) throws DocumentException, PortalException {
		com.liferay.dynamic.data.mapping.model.DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
				.filter(st -> st.getName("en_US").equalsIgnoreCase("academic")).findFirst().orElse(null);	
		
		JSONObject jo =  new JSONObject(struct.getDefinition());
		JSONArray ja = new JSONArray(jo.get("fields").toString());
		 
		for(int k = 0; k<ja.length(); k++) {
			JSONObject JOType = ja.getJSONObject(k);
			if(JOType.get("type").equals("fieldset")) {
				JSONArray rows = JOType.getJSONArray("nestedFields");
				for(int i = 0; i<rows.length(); i++) {
					JSONObject joa = rows.getJSONObject(i);
					if(joa.get("type").equals("select")) {
						if(joa.get("name").equals(degreeType)) { //Degree Type
							JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
							JSONArray jaRes = new JSONArray();
							for(int j = 0; j<joOptions.length(); j++) {
								JSONObject option = joOptions.getJSONObject(j); 
								JSONObject res = new JSONObject();
								res.put("id", option.getString("value"));
								res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
								jaRes.put(res);
							}
							renderRequest.setAttribute("DegreeType",  helper.toList(jaRes));
						} else if(joa.get("name").equals(attendance)) { //Attendance
							JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
							JSONArray jaRes = new JSONArray();
							for(int j = 0; j<joOptions.length(); j++) {
								JSONObject option = joOptions.getJSONObject(j); 
								JSONObject res = new JSONObject();
								res.put("id", option.getString("value"));
								res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
								jaRes.put(res);
							}
							renderRequest.setAttribute("Attendance",  helper.toList(jaRes));
						}
					}
				}
			}
		}
		
	}
}