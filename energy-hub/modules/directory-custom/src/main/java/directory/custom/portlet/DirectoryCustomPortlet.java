package directory.custom.portlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
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
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import directory.custom.constants.DirectoryCustomPortletKeys;
import directory.custom.constants.UserTypes;
import directory.custom.helper.helper;
import directory.custom.helper.util;

/**
 * @author vyo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=DirectoryCustom",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + DirectoryCustomPortletKeys.DIRECTORYCUSTOM,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class DirectoryCustomPortlet extends MVCPortlet {
	private final static Configuration _portletConfiguration = ConfigurationFactoryUtil
			.getConfiguration(PortalClassLoaderUtil.getClassLoader(), "portlet");
	
	private String DegreeType = "SelectFromList75723891", Attendance = "SelectFromList52092032";
	private String Mohafaza = "SelectFromList21368200";
	private String CoworkingSpaces = "Text18981315";
	private String FundingInstitutions = "Text98846915";
	private String InnovationPrograms = "Text73021449";
	private String acadmeicFolderId;
	
	private List<String> academicFilter = Arrays.asList(DegreeType, Attendance);
	private List<String> coworkingSpacesFilter = Arrays.asList(Mohafaza);
	private List<String> innovationTypesFilter = Arrays.asList("Innovation Programs","Coworking Spaces","Funding Institutions");
	
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY); 
			HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
			
			String ipadd =  PortalUtil.getHttpServletRequest(renderRequest).getRemoteAddr();
			if(!ipadd.equalsIgnoreCase("51.178.45.207")) { //Offline
				acadmeicFolderId = "40826";
			}else if(!ipadd.equalsIgnoreCase("51.178.45.174")) { //Online
				acadmeicFolderId = "42679";
			}else { //Local
				acadmeicFolderId = "69524";
			}
			
			String pageName = httpReq.getRequestURI().replace("/ar/", "").replace("/en/", "").replace("/", ""), categName ="";
			List<String> categList = new ArrayList<String>();
			boolean investorFilter = false;
			
			String queryText = helper.ifEmpty(httpReq.getParameter("queryText"), "");
			JSONObject filter = new JSONObject(helper.ifEmpty(httpReq.getParameter("filter"), "{}"));
			
			int pageNo = Integer.valueOf(helper.ifEmpty(httpReq.getParameter("pageNo"), "0"));
			int pageSize = Integer.valueOf(helper.ifEmpty(httpReq.getParameter("pageSize"), "6"));
			
			JSONArray jaRes = new JSONArray();
			if(pageName.equalsIgnoreCase("Academic")) {
				categName = "Academic Universities";
				categList = Arrays.asList("Academic Universities");
				com.liferay.dynamic.data.mapping.model.DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
						.filter(st -> st.getName("en_US").equalsIgnoreCase("academic")).findFirst().orElse(null);	
				getFilterData(themeDisplay, renderRequest, struct, categName, academicFilter);
				
				List<User> usersByRole =  new ArrayList<User>();
				long userRole = RoleLocalServiceUtil.getRoles(themeDisplay.getCompanyId()).stream()
					.filter(st -> st.getName().equalsIgnoreCase(UserTypes.AcademicUser.name()))
					.findFirst().orElse(null).getRoleId();	
				List<User> usersByRoleLocal =  UserLocalServiceUtil.getRoleUsers(userRole);
				usersByRole = usersByRoleLocal;
				
				//SEARCH
				usersByRole = getSearchedUsers(usersByRole, queryText);
				
				for(User user : usersByRole) {
					JSONObject res = new JSONObject(); 
					String token = util.getToken(_portletConfiguration);
					String userString = util.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), user.getEmailAddress());
					if(!new JSONObject(userString).has("usernotFound")) {
						res = getContentAcademic(user, renderRequest, themeDisplay, categName, filter, userString, token);
						if(!res.isEmpty())
							jaRes.put(res);  
					}
				}
			}else if(pageName.equalsIgnoreCase("Organization") || pageName.equalsIgnoreCase("Companies")) {
				getIAMFilter(renderRequest, themeDisplay, pageName);
				
				List<User> usersByRole =  new ArrayList<User>();
				if(pageName.equalsIgnoreCase("Organization")) {
					long userRole = RoleLocalServiceUtil.getRoles(themeDisplay.getCompanyId()).stream()
							.filter(st -> st.getName().equalsIgnoreCase(UserTypes.PolicyMakerUser.name()))
							.findFirst().orElse(null).getRoleId();	
					List<User> usersByRoleLocal =  UserLocalServiceUtil.getRoleUsers(userRole);
					
					usersByRole = usersByRoleLocal;
					
				}else if (pageName.equalsIgnoreCase("Companies")) {
					long userRole = RoleLocalServiceUtil.getRoles(themeDisplay.getCompanyId()).stream()
							.filter(st -> st.getName().equalsIgnoreCase(UserTypes.EnergyCompanyUser.name()))
							.findFirst().orElse(null).getRoleId();	
					usersByRole =  UserLocalServiceUtil.getRoleUsers(userRole);
				}
				
				//FILTER
				if(filter.has("mainBusinessActivity") && filter.getJSONArray("mainBusinessActivity").length()>0 ) {
					usersByRole = getFilteredUser(usersByRole, filter.getJSONArray("mainBusinessActivity"), "mainBusinessActivity", pageName);
				}
				if(filter.has("orgtype") && filter.getJSONArray("orgtype").length()>0 ) {
					usersByRole = getFilteredUser(usersByRole, filter.getJSONArray("orgtype"), "organizationType", pageName);
				}
				
				//SEARCH
				usersByRole = getSearchedUsers(usersByRole, queryText);
				
				for(User user : usersByRole) {
					JSONObject res = new JSONObject(); 
					res = getContentUser(user, renderRequest, themeDisplay);
					if(pageName.equalsIgnoreCase("Organization")) {
						res.put("detailURL", "/detail?userDetail&categName=PolicyMakerUser&groupId="+ 
				    			 themeDisplay.getLayout().getGroupId() +"&articleId="+ user.getUserId());
					}else if (pageName.equalsIgnoreCase("Companies")) {
						res.put("detailURL", "/detail?userDetail&categName=EnergyCompanyUser&groupId="+ 
				    			 themeDisplay.getLayout().getGroupId() +"&articleId="+ user.getUserId());
					}
					jaRes.put(res);  
				} 
			} else if(pageName.equalsIgnoreCase("directory-innovation")) {
				//Getting Investor Users 
				List<User> usersByRole =  new ArrayList<User>();
				long userRole = RoleLocalServiceUtil.getRoles(themeDisplay.getCompanyId()).stream()
						.filter(st -> st.getName().equalsIgnoreCase(UserTypes.InvestorUser.name()))
						.findFirst().orElse(null).getRoleId();	
				
				usersByRole =  UserLocalServiceUtil.getRoleUsers(userRole);
				
				//SEARCH
				usersByRole = getSearchedUsers(usersByRole, queryText);
				
				for(User user : usersByRole) { 
					JSONObject res = new JSONObject(); 
					res = getContentUser(user, renderRequest, themeDisplay);
				    res.put("detailURL", "/detail?userDetail&categName=EnergyCompanyUser&groupId="+ 
			    			 themeDisplay.getLayout().getGroupId() +"&articleId="+ user.getUserId());
					jaRes.put(res);  
				} 
			
				//FILTER Investor Users
				if((filter.has("innovationType") && filter.getJSONArray("innovationType").length()>0)) {
					JSONArray filterJS = filter.getJSONArray("innovationType");
					for(int i=0; i< filterJS.length();i++) {
						String val = filterJS.getString(i);
						if(String.valueOf("investor").equalsIgnoreCase(val)) {
							investorFilter = true;
							break;
						}else {
							jaRes = new JSONArray();
						}
					}
				}   
				
				categList = Arrays.asList("Innovation Programs","Coworking Spaces","Funding Institutions");
				com.liferay.dynamic.data.mapping.model.DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
						.filter(st -> st.getName("en_US").equalsIgnoreCase("coworking-spaces")).findFirst().orElse(null);	
				
				getFilterData(themeDisplay, renderRequest, struct, "coworkingSpaces", coworkingSpacesFilter);
				getFilterData(themeDisplay, renderRequest, struct, pageName, innovationTypesFilter);
			} 
			
			if(!pageName.equalsIgnoreCase("Companies") && !investorFilter 
					&& !pageName.equalsIgnoreCase("academic") && !pageName.equalsIgnoreCase("Organization")) {
				long[] categIds = new long[categList.size()];
				for (int i = 0; i < categList.size(); i++) {
					String cName = categList.get(i);
					AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
							.filter(categ -> categ.getName().equalsIgnoreCase(cName)).findFirst().orElse(null);
					categIds[i] = assetCategory.getCategoryId();
				}
				 
				AssetEntryQuery assetEntryQuery = new AssetEntryQuery();
				assetEntryQuery.setAnyCategoryIds(categIds); 
				List<JournalArticle> articles = new ArrayList<JournalArticle>();
				List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
				for (AssetEntry ae : assetEntryList) {
				    JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
			    	if(journalArticleResource != null) {
				    	JournalArticle journalArticle = JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey());
				    	articles.add(journalArticle);
				    }
				}

				//FILTER
				if(filter.has("mohafaza") && filter.getJSONArray("mohafaza").length()>0 ) {
					jaRes = new JSONArray();
					articles = getFilteredArticles(articles, filter.getJSONArray("mohafaza"), Mohafaza, themeDisplay);
				} 
				if(filter.has("innovationType") && filter.getJSONArray("innovationType").length()>0 ) {
					articles = getFilteredArticlesInnovationType(articles, filter.getJSONArray("innovationType"));
				} 
				
				//SEARCH
				if(!helper.isEmpty(queryText)) {
					List<String> searchField = new ArrayList<String>();
					if(pageName.equalsIgnoreCase("directory-innovation")) {
						searchField.add(CoworkingSpaces);
						searchField.add(FundingInstitutions);
						searchField.add(InnovationPrograms);
					}
					
					articles = getSearchedArticles(themeDisplay, articles, queryText, searchField);
				}
				
				for(JournalArticle journalArticle : articles) {
					JSONObject res = new JSONObject(); 
					if(pageName.equalsIgnoreCase("directory-innovation")) 
						 res = getContentInnovation(journalArticle, renderRequest, themeDisplay);
					jaRes.put(res); 
				}
			}
			
			List<Object> articlesList = helper.toList(jaRes);
			int totalPages=  articlesList.isEmpty() ? 0 : (int) Math.floor(articlesList.size()/pageSize);
			renderRequest.setAttribute("totalPages", totalPages);
			renderRequest.setAttribute("pageNo", pageNo > totalPages ? 0 : pageNo);
			renderRequest.setAttribute("pageSize", pageSize);
			
			//PAGINATION
			articlesList= getPageLimit(articlesList, pageNo, pageSize);
			
			renderRequest.setAttribute("journalArticleList", articlesList);
			
			super.render(renderRequest, renderResponse);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<JournalArticle> getFilteredArticlesInnovationType(List<JournalArticle> articles, JSONArray filter) throws NumberFormatException, PortalException {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>();
		for(JournalArticle article : articles) {
			long fieldValue = 0;
			 List<AssetCategory> categs = AssetCategoryServiceUtil.getCategories(JournalArticle.class.getName(), 
					 article.getResourcePrimKey());

			    for(AssetCategory categ : categs) {
			    	if(categ.getName().equalsIgnoreCase("Innovation Programs")) {
			    		fieldValue = categ.getCategoryId();
			    		break;
			    	}
			    	if(categ.getName().equalsIgnoreCase("Coworking Spaces")) {
			    		fieldValue = categ.getCategoryId();
			    		break;
			    	}
			    	if(categ.getName().equalsIgnoreCase("Funding Institutions")) {
			    		fieldValue = categ.getCategoryId();
			    		break;
			    	}
			    }
			
			for(int i=0; i< filter.length();i++) {
				String val = filter.getString(i);
				if( String.valueOf(fieldValue).equalsIgnoreCase(val) ) {
					returnedArticles.add(article);
					break;
				}
			}
		}
		return returnedArticles;
	}

	public void getFilterData(ThemeDisplay themeDisplay, RenderRequest renderRequest,
			com.liferay.dynamic.data.mapping.model.DDMStructure struct, String CategName,List<String> filterFields)
					throws DocumentException, PortalException {
		  
		JSONObject jo = new JSONObject(struct.getDefinition());
		JSONArray ja = new JSONArray(jo.get("fields").toString());

		if(CategName.equalsIgnoreCase("Academic Universities")) {
			for (int i = 0; i < ja.length(); i++) {
				JSONObject joa = ja.getJSONObject(i);
				if (joa.get("type").equals("fieldset")) {
					JSONArray nestedFields = new JSONArray(joa.get("nestedFields").toString());
					for (int n = 0; n < nestedFields.length(); n++) {
						JSONObject nestjo = nestedFields.getJSONObject(n);
						if(nestjo.get("type").equals("select")) {
							if (filterFields.contains(nestjo.getString("name"))) {
								JSONArray joOptions = new JSONArray(nestjo.get("options").toString());
								JSONArray jaRes = new JSONArray();
								for (int j = 0; j < joOptions.length(); j++) {
									JSONObject option = joOptions.getJSONObject(j);
									JSONObject res = new JSONObject(); 
									res.put("id", option.getString("value"));
									res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
									jaRes.put(res);
								}  
								if(nestjo.getString("name").equalsIgnoreCase(DegreeType))
									renderRequest.setAttribute("Degreetype", helper.toList(jaRes));
								else if (nestjo.getString("name").equalsIgnoreCase(Attendance))
									renderRequest.setAttribute("Attendance", helper.toList(jaRes));
							} 
						}
					}
				}
			}
		}
		if(CategName.equalsIgnoreCase("coworkingSpaces")) {
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
//						if (joa.getString("name").equalsIgnoreCase("SelectFromList19704638"))
//							renderRequest.setAttribute("OrgType", helper.toList(jaRes));
						if (joa.getString("name").equalsIgnoreCase(Mohafaza))
							renderRequest.setAttribute("Mohafaza", helper.toList(jaRes));
					} 	
				}
			}
		}
		if(CategName.equalsIgnoreCase("directory-innovation") && filterFields.size() > 0) {
			JSONArray jaRes = new JSONArray();
			for(String filterFiled : filterFields) {
				AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase(filterFiled)).findFirst().orElse(null);
				JSONObject res = new JSONObject(); 
				if(assetCategory != null) {
					res.put("id", assetCategory.getCategoryId());
					res.put("value", filterFiled);
					jaRes.put(res);
				}
			}
			JSONObject res = new JSONObject(); 
			res.put("id", "investor");
			res.put("value", "Business Support Organization");
			jaRes.put(res);
			renderRequest.setAttribute("InnovationType", helper.toList(jaRes));
		}
	}
	
	private void getIAMFilter(RenderRequest renderRequest, ThemeDisplay themeDisplay, String pageName) {
		String filterUrl = "";
		List<String> jsonDataNames = new ArrayList<String>();
		if(pageName.equalsIgnoreCase("Organization")) {
			filterUrl = "get-epolicymaker-attr";
			jsonDataNames = Arrays.asList("OrganizationType"); 
		}else if (pageName.equalsIgnoreCase("Companies")) {
			jsonDataNames = Arrays.asList("mainBusinessActivity", "kaza", "numberOfEmployees"); 
			filterUrl = "get-comp-attr";
		}
		
		String token = util.getToken(_portletConfiguration);
		String dropdowns = util.getDropdowns(token, _portletConfiguration.get(filterUrl));
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
							value = listValJO.getString("TextAR");
						if(themeDisplay.getLanguageId().equalsIgnoreCase("fr_FR"))
							value = listValJO.getString("TextFR");
						
						res.put("value", value);
						jaRes.put(res);
					}
					renderRequest.setAttribute(data, helper.toList(jaRes));
				}
			}
		}
	}

	private JSONObject getContentUser(User user, RenderRequest renderRequest, ThemeDisplay themeDisplay) throws JSONException, PortalException {
		JSONObject res = new JSONObject();
		res.put("id", user.getUserId());
		res.put("title", user.getFullName());
		res.put("description", "");
		res.put("imageURL", user.getPortraitURL(themeDisplay));
		return res;
	}

	private JSONObject getContentAcademic(User user, RenderRequest renderRequest, ThemeDisplay themeDisplay, String categName, JSONObject filter, String userString, String token) throws DocumentException, PortalException, JSONException, UnsupportedEncodingException {
		JSONObject res = new JSONObject();
	    List<String> jsonDataNamesString = Arrays.asList("universityName", "description", "acronym", "logo");
		JSONObject userJson = new JSONObject(userString);
	    for(String data : jsonDataNamesString) {
			if(data.equalsIgnoreCase("logo")) {
				String userImageURL ="";
				if(userJson.has("logo") && !String.valueOf(userJson.get("logo")).equalsIgnoreCase("null")) {
					String userImageBase64 = userJson.getJSONObject("logo").getString("data");
					String extension = userJson.getJSONObject("logo").getString("extension");
					byte[] decodedString = Base64.getDecoder().decode(userImageBase64.getBytes("UTF-8"));
					
					userImageURL = "data:image/"+ extension +";base64," + Base64.getEncoder().encodeToString(decodedString); 
				}
				res.put("imageURL", userImageURL); 
			}else if(userJson.has(data) && !String.valueOf(userJson.get(data)).equalsIgnoreCase("null")) {
				if(data.equalsIgnoreCase("universityName"))
					res.put("title", userJson.getString(data));
				else res.put(data, userJson.getString(data));
			}
		}  
	    
	    String folderId = "", ddmTemplateKey = "";
	    AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
				.filter(categ -> categ.getName().equalsIgnoreCase("Academic Universities")).findFirst().orElse(null);
	    
	    AssetEntryQuery assetEntryQuery = new AssetEntryQuery();
		assetEntryQuery.setAnyCategoryIds(new long[] {  assetCategory.getCategoryId() }); 
		List<JournalArticle> articles = new ArrayList<JournalArticle>();
		List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
		for (AssetEntry ae : assetEntryList) {
		    JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
		    JournalArticle journalArticle = JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey());

		    List<JournalArticle> tmp = new ArrayList<JournalArticle>();
		    tmp.add(journalArticle);
		    //FILTER
			if(filter.has("degreetype") && filter.getJSONArray("degreetype").length()>0 ) {
				tmp = getFilteredArticles(tmp, filter.getJSONArray("degreetype"), DegreeType, themeDisplay);
			}
			if(filter.has("attendance") && filter.getJSONArray("attendance").length()>0 ) {
				tmp = getFilteredArticles(tmp, filter.getJSONArray("attendance"), Attendance, themeDisplay);
			}
			
		    if(tmp.contains(journalArticle))
		    	articles.add(journalArticle);
		    
		    folderId = String.valueOf(journalArticle.getFolderId());
		    ddmTemplateKey  = journalArticle.getDDMTemplateKey();
		}
		
		//get related Programs for this user
		articles = getSearchedArticles(themeDisplay, articles, user.getEmailAddress(), Arrays.asList("Text03087898"));
		if(articles.size() > 0) {
			JournalArticle academicUser = articles.get(0);
			
			//get fields group data for the Energy Programs
			JSONArray energyProgramsJA = helper.getFieldGroupFromContentXml(academicUser, themeDisplay.getLocale(), "FieldsGroup66038080");
			String programsTitles = "";
			int counter = 0;
			for(int i=0 ;i < energyProgramsJA.length() ; i++) {
				JSONObject data = energyProgramsJA.getJSONObject(i); 
				String energyPrograms = data.getString("energyPrograms");
				energyPrograms = energyPrograms.replace("<br />", "(br)");
				energyPrograms = energyPrograms.replaceAll("<[^>]*>", "");
				energyPrograms = energyPrograms.replaceAll("&nbsp;", ""); 
				programsTitles += energyPrograms + ",";
				counter++;
			}
			programsTitles = programsTitles.substring(0, programsTitles.length() - 1); 
			programsTitles = programsTitles.replace("(br)", "<br />");
			programsTitles = programsTitles.replace(",", ", <br />"); 
			
			res.put("programsTitles", programsTitles);
			res.put("programs", energyProgramsJA);
			res.put("counter", counter);
		}
		res.put("id", user.getEmailAddress());
		res.put("detailURL", "/detail?userDetail&categName=AcademicUser&groupId="+ 
				themeDisplay.getLayout().getGroupId() +"&articleId="+  user.getUserId()
				+"&folderId="+  (helper.isEmpty(folderId) ? acadmeicFolderId : folderId) +"&ddmTemplateKey="+  ddmTemplateKey);
		
		if(!filter.isEmpty() && articles.size() == 0)
			return new JSONObject();
		
		return res;
	}

	private JSONObject getContentInnovation(JournalArticle journalArticle, RenderRequest renderRequest, ThemeDisplay themeDisplay) 
			throws DocumentException, PortalException, JSONException {
		
			String title = "", description = "", categName = "";
			JSONObject coverImageJO = new JSONObject();
			JSONObject res = new JSONObject();
			String content = journalArticle.getContentByLocale(themeDisplay.getLanguageId());
		    Document document = SAXReaderUtil.read(content);
		    
		    List<AssetCategory> categs = AssetCategoryServiceUtil.getCategories(JournalArticle.class.getName(), 
		    		journalArticle.getResourcePrimKey());

		    for(AssetCategory categ : categs) {
		    	if(categ.getName().equalsIgnoreCase("Innovation Programs")) {
		    		categName ="Innovation Programs";
		    		break;
		    	}
		    	if(categ.getName().equalsIgnoreCase("Coworking Spaces")) {
		    		categName ="Coworking Spaces";
		    		break;
		    	}
		    	if(categ.getName().equalsIgnoreCase("Funding Institutions")) {
		    		categName ="Funding Institutions";
		    		break;
		    	}
		    }
		    if(categName.equalsIgnoreCase("Innovation Programs")) {
		    	title = document.selectSingleNode("/root/dynamic-element[@name='Text03803647']/dynamic-content").getText();
		    	description = document.selectSingleNode("/root/dynamic-element[@name='RichText76165816']/dynamic-content").getText();
		    	coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='Image92271346']/dynamic-content").getText());
		    }
		    else if(categName.equalsIgnoreCase("Coworking Spaces")) {
		    	title = document.selectSingleNode("/root/dynamic-element[@name='Text18981315']/dynamic-content").getText();
		    	description = document.selectSingleNode("/root/dynamic-element[@name='RichText50633365']/dynamic-content").getText();
		    	coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='Image59711542']/dynamic-content").getText());
		    }
	    	else if(categName.equalsIgnoreCase("Funding Institutions")) {
	    		title = document.selectSingleNode("/root/dynamic-element[@name='Text98846915']/dynamic-content").getText();
		    	description = document.selectSingleNode("/root/dynamic-element[@name='RichText51221879']/dynamic-content").getText();
		    	coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='Image08709087']/dynamic-content").getText());
	    	}
	        
	        
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
			res.put("id", journalArticle.getId());
			res.put("title", title);
			res.put("description", description);
			res.put("imageURL", imageUrl);
			res.put("detailURL", "/program-detail?categName="+ categName +"&groupId="+ 
					themeDisplay.getLayout().getGroupId() +"&articleId="+ journalArticle.getResourcePrimKey());
		return res;
	}
	
	private List<JournalArticle> getSearchedArticles(ThemeDisplay themeDisplay, List<JournalArticle> filtered, String queryText, List<String> fieldName) throws DocumentException {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>(); 
		try {
			queryText = URLDecoder.decode(queryText.toLowerCase(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			queryText = queryText.toLowerCase();
		}
		
		for (JournalArticle article : filtered) {
			String content = article.getContentByLocale(themeDisplay.getLanguageId());
			Document document = SAXReaderUtil.read(content);
			for(String fName : fieldName) {
				if(content.contains(fName)) {
					String title = document.selectSingleNode("/root/dynamic-element[@name='" + fName + "']").hasContent() ? 
							document.selectSingleNode("/root/dynamic-element[@name='" + fName + "']/dynamic-content").getText() : "";
					
					if(title.toLowerCase().contains(queryText)) {
						returnedArticles.add(article);
						break;
					}
				}
			}
		}
		return returnedArticles;
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


	private List<JournalArticle> getFilteredArticles(List<JournalArticle> fetchedArticles, JSONArray filter, String fieldName, ThemeDisplay themeDisplay) throws DocumentException, PortalException {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>(); 
		
		for (JournalArticle article : fetchedArticles) {
			String content = article.getContentByLocale(themeDisplay.getLanguageId());
			Document document = SAXReaderUtil.read(content);
			String fieldValue = "";
			if(fieldName.equalsIgnoreCase(DegreeType) || fieldName.equalsIgnoreCase(Attendance)) {
				JSONArray energyProgramsJA = helper.getFieldGroupFromContentXml(article, themeDisplay.getLocale(), "FieldsGroup66038080");
				for(int i=0 ;i < energyProgramsJA.length() ; i++) {
					JSONObject data = energyProgramsJA.getJSONObject(i); 
					if(fieldName.equalsIgnoreCase(DegreeType)) {
						fieldValue = data.getString("degreeType");
						for(int j=0; j< filter.length();j++) {
							String val = filter.getString(j);
							if( fieldValue.equalsIgnoreCase(val) ) {
								returnedArticles.add(article);
								break;
							}
						}
					} else if(fieldName.equalsIgnoreCase(Attendance)) {
						fieldValue = data.getString("attendance");
						for(int j=0; j< filter.length();j++) {
							String val = filter.getString(j);
							if( fieldValue.equalsIgnoreCase(val) ) {
								returnedArticles.add(article);
								break;
							}
						}
					}
				}
			} else if(fieldName.equalsIgnoreCase(Mohafaza)) {
				List<AssetCategory> categs = AssetCategoryServiceUtil.getCategories(JournalArticle.class.getName(), 
			    		article.getResourcePrimKey());

			    for(AssetCategory categ : categs) {
			    	if(categ.getName().equalsIgnoreCase("Coworking Spaces")) {
			    		fieldValue = document.selectSingleNode("/root/dynamic-element[@name='"+ fieldName +"']").hasContent() ? 
								document.selectSingleNode("/root/dynamic-element[@name='"+ fieldName +"']/dynamic-content").getText() : "";
			    	} 
			    }
			} else {
				fieldValue = document.selectSingleNode("/root/dynamic-element[@name='"+ fieldName +"']").hasContent() ? 
						document.selectSingleNode("/root/dynamic-element[@name='"+ fieldName +"']/dynamic-content").getText() : "";
			}
			if(!fieldName.equalsIgnoreCase(DegreeType) && !fieldName.equalsIgnoreCase(Attendance)) {
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
	
	private List<User> getFilteredUser(List<User> usersByRole, JSONArray filter, String filterName, String pageName) throws DocumentException {
		List<User> returnedArticles = new ArrayList<User>(); 
		String token = util.getToken(_portletConfiguration);
		
		String filterUrl = "";
		if(pageName.equalsIgnoreCase("Organization")) {
			filterUrl = "get-epolicymaker-attr";
		}else if (pageName.equalsIgnoreCase("Companies")) {
			filterUrl = "get-comp-attr";
		}
		String dropdowns = util.getDropdowns(token, _portletConfiguration.get(filterUrl));
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
	
	public List<Object> getPageLimit(List<Object> articlesList, int pageNum, int pageSize) {
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
}