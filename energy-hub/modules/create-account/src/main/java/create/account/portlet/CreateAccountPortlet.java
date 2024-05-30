package create.account.portlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.mail.MessagingException;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.PwdEncryptorException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import create.account.constants.CreateAccountPortletKeys;
import create.account.constants.UserTypes;
import create.account.util.AES;
import create.account.util.CreateAccountUtil;
import create.account.util.helper;

/**
 * @author vyo
 */
@Component(immediate = true, property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=CreateAccount",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.name=" + CreateAccountPortletKeys.CREATEACCOUNT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" 
		}, 
service = Portlet.class)

public class CreateAccountPortlet extends MVCPortlet {
	private final String EntrepreneurUser_PAGE_PATH = "/html/EntrepreneurUser.jsp";
	private final String EnergyHubUser_PAGE_PATH = "/html/EnergyHubUser.jsp";
	private final String EnergyCompanyUser_PAGE_PATH = "/html/EnergyCompanyUser.jsp";
	private final String InvestorUser_PAGE_PATH = "/html/InvestorUser.jsp";
	private final String IndustryUser_PAGE_PATH = "/html/IndustryUser.jsp";
	private final String ResearcherUser_PAGE_PATH = "/html/ResearcherUser.jsp";
	private final String PolicyMAkerUser_PAGE_PATH = "/html/PolicyMakerUser.jsp";
	private final String CarDealUser_PAGE_PATH = "/html/CarDealerUser.jsp";
	private final String AcademicUser_PAGE_PATH = "/html/EnergyAcademicUser.jsp";
	private final String EmailVerification_PAGE_PATH = "/html/EmailVerification.jsp";
	private final String AdminApprove_PAGE_PATH = "/html/AdminApprove.jsp";
	
	private long areaofExpertiseFolerId;

	private final static String secretKey = "undpencryprion";

	private final static Configuration _portletConfiguration = ConfigurationFactoryUtil
			.getConfiguration(PortalClassLoaderUtil.getClassLoader(), "portlet");

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String ipadd =  PortalUtil.getHttpServletRequest(renderRequest).getRemoteAddr();
//		if(!ipadd.equalsIgnoreCase("51.178.45.207")) { //Offline
//			areaofExpertiseFolerId = 40844;
//		}else if(!ipadd.equalsIgnoreCase("51.178.45.174")) { //Online
			areaofExpertiseFolerId = 42697;
//		}else { //Local
//			areaofExpertiseFolerId = 40840;
//		}
		
//		try {
//			sendConfirmationEmail("stephaniekhalife94@gmail.com");
//		} catch (PwdEncryptorException | NoSuchAlgorithmException | MessagingException e1) {
//			e1.printStackTrace();
//		}
		
		renderRequest.setAttribute("lang", themeDisplay.getLanguageId());
		renderRequest.setAttribute("userAreas", "{}");
		if (httpReq.getParameterMap().containsKey("userType")) {
			String userTypeID = httpReq.getParameter("userType");
			String articleId = httpReq.getParameter("userId");
			String pageName = fillDropdownsData(userTypeID, renderRequest, themeDisplay);
			
			try {
				JournalArticle article = JournalArticleLocalServiceUtil.fetchArticle(Long.valueOf(articleId));
				String userLearnMoreDescription="", userDescription="";
				if(article !=null) {
					String content = article.getContentByLocale(themeDisplay.getLanguageId());
					Document document = SAXReaderUtil.read(content);
					if(document != null && document.hasContent()) {
						userLearnMoreDescription = (document.selectSingleNode("/root/dynamic-element[@name='RichText33546263']") != null &&
							document.selectSingleNode("/root/dynamic-element[@name='RichText33546263']").hasContent()) ? 
							document.selectSingleNode("/root/dynamic-element[@name='RichText33546263']/dynamic-content").getText() : "";
						
						userDescription = (document.selectSingleNode("/root/dynamic-element[@name='RichText45479476']") != null &&
							document.selectSingleNode("/root/dynamic-element[@name='RichText45479476']").hasContent()) ? 
							document.selectSingleNode("/root/dynamic-element[@name='RichText45479476']/dynamic-content").getText() : "";
					}
					renderRequest.setAttribute("userLearnMoreDescription", userLearnMoreDescription);
					renderRequest.setAttribute("userDescription", userDescription);
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			
			renderRequest.setAttribute("UserApplicationRoleId", userTypeID);
			renderRequest.setAttribute("edit", false);
			include(pageName, renderRequest, renderResponse);
			super.render(renderRequest, renderResponse);
		} else if (httpReq.getParameterMap().containsKey("EmailVerification")) {
			String encryptedEmail = httpReq.getParameter("EmailVerification");
			renderRequest.setAttribute("emailAddress", httpReq.getParameter("emailAddress"));
			renderRequest.setAttribute("encryptedEmail", encryptedEmail);
			renderRequest.setAttribute("edit", false);
			include(EmailVerification_PAGE_PATH, renderRequest, renderResponse);
			super.render(renderRequest, renderResponse);
		} else if (httpReq.getParameterMap().containsKey("AdminApprove")) {
			String nonActiveUsers = CreateAccountUtil.callRestGet(_portletConfiguration.get("get-non-active-users"),""); 
			renderRequest.setAttribute("nonActiveUsers", helper.toList(new JSONArray(nonActiveUsers)));
			renderRequest.setAttribute("edit", false);
			include(AdminApprove_PAGE_PATH, renderRequest, renderResponse);
			super.render(renderRequest, renderResponse);
		}else if (httpReq.getParameterMap().containsKey("edit")) {
			User currentUser = themeDisplay.getUser();
			String token = CreateAccountUtil.getToken(_portletConfiguration);
			String userString = CreateAccountUtil.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), currentUser.getEmailAddress()); 
			JSONObject userJson = new JSONObject(userString);
			
			String userRoleIdIAM = userJson.get("userApplicationRoleId").toString();
			String Id = userJson.get("id").toString();
			
			String pageName = "";
			pageName = fillDropdownsData(userRoleIdIAM, renderRequest, themeDisplay);
			
			String[] jsonDataNames = null;
			AssetCategory category = null;
			if (userRoleIdIAM.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.EnergyHubUser.name()))) {
				jsonDataNames = _portletConfiguration.getArray("form-values-energyhubuser");
			} else if (userRoleIdIAM.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.EntrepreneurUser.name()))) {
				jsonDataNames = _portletConfiguration.getArray("form-values-energyhubuser-entrepreneur");
				category = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase("Innovation")).findFirst().orElse(null);
				renderRequest.setAttribute("categId", category.getCategoryId());
			} else if (userRoleIdIAM.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.ResearcherUser.name()))) {
				jsonDataNames = _portletConfiguration.getArray("form-values-energyhubuser-researcher");
			} else if (userRoleIdIAM.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.EnergyCompanyUser.name()))) {
				jsonDataNames = _portletConfiguration.getArray("form-values-energyhubuser-company");
			} else if (userRoleIdIAM.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.InvestorUser.name()))) {
				jsonDataNames = _portletConfiguration.getArray("form-values-energyhubuser-investor");
				category = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase("Innovation")).findFirst().orElse(null);
				renderRequest.setAttribute("categId", category.getCategoryId());
			} else if (userRoleIdIAM.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.IndustryUser.name()))) {
				jsonDataNames = _portletConfiguration.getArray("form-values-energyhubuser-industry");
			} else if (userRoleIdIAM.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.AcademicUser.name()))) {
				jsonDataNames = _portletConfiguration.getArray("form-values-energyhubuser-academic");
			} else if (userRoleIdIAM.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.PolicyMakerUser.name()))) {
					jsonDataNames = _portletConfiguration.getArray("form-values-energyhubuser-policymaker");
			} else if (userRoleIdIAM.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.CarDealerUser.name()))) {
				jsonDataNames = _portletConfiguration.getArray("form-values-energyhubuser-cardealer");
		}
			
			
			JSONObject userData = new JSONObject();
			JSONObject jsonKeys = new JSONObject();
			
			Iterator<String> keys = userJson.keys();
			while(keys.hasNext()) {
			    String key = keys.next();
			    jsonKeys.put(key.toLowerCase(), userJson.get(key));
			    
			} 
			
			for(String data : jsonDataNames) {
				if(data.equalsIgnoreCase("coverImage") || data.equalsIgnoreCase("files")) {
					if(data.equalsIgnoreCase("coverImage")) {
						data = "logo";
					}
					if(data.equalsIgnoreCase("files")) {
						data = "files";
					}
					if(jsonKeys.has(data.toLowerCase()) && !jsonKeys.isNull(data.toLowerCase())) { 
						userData.put(data + "Name", userJson.getJSONObject(data).getString("name"));
						userData.put(data + "Hidden", userJson.getJSONObject(data));
					}
				} else if (data.equalsIgnoreCase("Company")) {
					if(jsonKeys.has(data.toLowerCase()) && !jsonKeys.isNull(data.toLowerCase())) {
						if(jsonKeys.getString(data.toLowerCase()).contains("--")) {
							userData.put(data, jsonKeys.get(data.toLowerCase()));
						}else userData.put("Other", jsonKeys.get(data.toLowerCase()));
					}
				} else if (data.equalsIgnoreCase("DateOfBirth")) {
					if(jsonKeys.has(data.toLowerCase()) && !jsonKeys.isNull(data.toLowerCase())) {
						Date date;
						try {
							date = new SimpleDateFormat("MM/dd/yyyy").parse(jsonKeys.getString(data.toLowerCase()));
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
							String strDate = dateFormat.format(date);  
							userData.put(data, strDate);
						} catch (JSONException | ParseException e) {
							e.printStackTrace();
						}
					}
				} else {
					if(jsonKeys.has(data.toLowerCase())) {
						userData.put(data, jsonKeys.get(data.toLowerCase()));
					}
				}
			}
			try {
				if(userRoleIdIAM.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.ResearcherUser.name()))) {
					com.liferay.dynamic.data.mapping.model.DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
							.filter(st -> st.getName("en_US").equalsIgnoreCase("area-expertise")).findFirst().orElse(null);	
					String ddmStructureKey = struct.getStructureKey(); 
					List<JournalArticle> articles = JournalArticleLocalServiceUtil
							.getStructureArticles(themeDisplay.getLayout().getGroupId(), ddmStructureKey);
					articles = getSearchedArticles(themeDisplay, articles, currentUser.getEmailAddress(), Arrays.asList("Text63214587"));
					if(articles.size() > 0) {
						JSONArray areas = helper.getFieldGroupFromContentXml(articles.get(0), themeDisplay.getLocale(), "FieldsGroup65973700");
						userData.put("areas", areas);
						renderRequest.setAttribute("userAreas", areas);
					}
				}
			} catch (DocumentException | PortalException e) {
				e.printStackTrace();
			}
			userData.put("UserApplicationRoleId", Integer.valueOf(userRoleIdIAM));
			userData.put("Id", Integer.valueOf(Id));
			
			renderRequest.setAttribute("edit", true);
			renderRequest.setAttribute("userData", helper.toMap(userData));
			
			include(pageName, renderRequest, renderResponse);
			super.render(renderRequest, renderResponse);
		}
	}
 
	private String fillDropdownsData(String userTypeID, RenderRequest renderRequest, ThemeDisplay themeDisplay) {
		String dropdowns = "", pageName = "";
		if (userTypeID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.EnergyHubUser.name()))) {
			dropdowns = CreateAccountUtil.getDropdowns(_portletConfiguration.get("get-ehub-attr"));
			pageName = EnergyHubUser_PAGE_PATH;
		} else if (userTypeID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.EntrepreneurUser.name()))) {
			dropdowns = CreateAccountUtil.getDropdowns(_portletConfiguration.get("get-eentrepreneur-attr"));
			pageName = EntrepreneurUser_PAGE_PATH;
		} else if (userTypeID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.ResearcherUser.name()))) {
			dropdowns = CreateAccountUtil.getDropdowns(_portletConfiguration.get("get-eresearcher-attr"));
			pageName = ResearcherUser_PAGE_PATH;
			
			com.liferay.dynamic.data.mapping.model.DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
					.filter(st -> st.getName("en_US").equalsIgnoreCase("area-expertise")).findFirst().orElse(null);
			
			JSONObject jo =  new JSONObject(struct.getDefinition());
			JSONArray ja = new JSONArray(jo.get("fields").toString());
			List<String> filterFields = Arrays.asList("SelectFromList23403899", "SelectFromList47700469");	
			renderRequest.setAttribute("userAreas", "");
			
			for(int k = 0; k<ja.length(); k++) {
				JSONObject JOType = ja.getJSONObject(k);
				if(JOType.get("type").equals("fieldset")) {
					JSONArray rows = JOType.getJSONArray("nestedFields");
					for(int i = 0; i<rows.length(); i++) {
						JSONObject joa = rows.getJSONObject(i);
						if(joa.get("type").equals("select")) {
							if(filterFields.contains(joa.getString("name"))) {
								JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
								JSONArray jaRes = new JSONArray();
								for(int j = 0; j<joOptions.length(); j++) {
									JSONObject option = joOptions.getJSONObject(j); 
									JSONObject res = new JSONObject();
									res.put("id", option.getString("value"));
									res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
									jaRes.put(res);
								}
								if (joa.getString("name").equalsIgnoreCase("SelectFromList23403899"))
									renderRequest.setAttribute("AreaOfExpertiseList", helper.toList(jaRes));
								if (joa.getString("name").equalsIgnoreCase("SelectFromList47700469"))
									renderRequest.setAttribute("LevelOfExpertiseList", helper.toList(jaRes));
							}
						}
					}
				}
			}
		} else if (userTypeID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.EnergyCompanyUser.name()))) {
			dropdowns = CreateAccountUtil.getDropdowns(_portletConfiguration.get("get-ecompany-attr"));
			pageName = EnergyCompanyUser_PAGE_PATH;
		} else if (userTypeID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.InvestorUser.name()))) {
			dropdowns = CreateAccountUtil.getDropdowns(_portletConfiguration.get("get-einvestor-attr"));
			pageName = InvestorUser_PAGE_PATH;
		} else if (userTypeID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.IndustryUser.name()))) {
			dropdowns = CreateAccountUtil.getDropdowns(_portletConfiguration.get("get-eindustry-attr"));
			pageName = IndustryUser_PAGE_PATH;
		} else if (userTypeID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.AcademicUser.name()))) {
			dropdowns = CreateAccountUtil.getDropdowns(_portletConfiguration.get("get-eacademic-attr"));
			pageName = AcademicUser_PAGE_PATH;
		} else if (userTypeID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.PolicyMakerUser.name()))) {
			dropdowns = CreateAccountUtil.getDropdowns(_portletConfiguration.get("get-epolicymaker-attr"));
			pageName = PolicyMAkerUser_PAGE_PATH;
		} else if (userTypeID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.CarDealerUser.name()))) {
			dropdowns = CreateAccountUtil.getDropdowns(_portletConfiguration.get("get-cardealer-attr"));
			pageName = CarDealUser_PAGE_PATH;
		}

		List<Integer> years = IntStream.rangeClosed(1800, Calendar.getInstance().get(Calendar.YEAR))
			    .boxed().collect(Collectors.toList());
		Collections.reverse(years);
		renderRequest.setAttribute("YearOfEstablishment", years);
		
		String genderList = CreateAccountUtil.getDropdowns(_portletConfiguration.get("get-gender-list"));
		JSONArray ja  = new JSONArray();
		JSONArray tmp  = new JSONArray(genderList);
		for(int i =0; i< tmp.length() ; i++) {
			JSONObject jo = new JSONObject(tmp.getString(i));
			ja.put(jo);
		}
		renderRequest.setAttribute("Gender", helper.toList(ja));

		JSONArray dropdownJA = new JSONArray(dropdowns);
		for(int i =0; i< dropdownJA.length() ; i++) {
			JSONObject jo = dropdownJA.getJSONObject(i);
			JSONArray list = new JSONArray();
			if(!jo.getString("name").equalsIgnoreCase("SDGSolution"))
				list = helper.sortJSONArrayAlphabetically(new JSONArray(jo.getString("listValue")), themeDisplay.getLanguageId());
			else list = new JSONArray(jo.getString("listValue"));
			
			if(jo.getString("name").equalsIgnoreCase("carMake")) {
				for (int j = 0; j < list.length(); j++) {
					JSONObject carmake = list.getJSONObject(j);
					if(carmake.getString("Text").equalsIgnoreCase("Other")) {
						list.remove(j);
						list.put(carmake);
					}
				}
			}
			renderRequest.setAttribute(jo.getString("name"), helper.toList(list));
		}
		
		String token = CreateAccountUtil.getToken(_portletConfiguration);
		String companyUsers = CreateAccountUtil.getUserbyRole(token, _portletConfiguration.get("get-users-by-role"), 
				UserTypes.getUserTypeValue(UserTypes.EnergyCompanyUser.name()));
		String academicUsers = CreateAccountUtil.getUserbyRole(token,_portletConfiguration.get("get-users-by-role"), 
				UserTypes.getUserTypeValue(UserTypes.AcademicUser.name()));
		String organizationUsers = CreateAccountUtil.getUserbyRole(token, _portletConfiguration.get("get-users-by-role"), 
				UserTypes.getUserTypeValue(UserTypes.PolicyMakerUser.name()));

		List<JSONArray> JAList = new ArrayList<JSONArray>();
		if(!helper.isEmpty(companyUsers))
			JAList.add(new JSONArray(companyUsers));
		if(!helper.isEmpty(academicUsers))
			JAList.add(new JSONArray(academicUsers));
		if(!helper.isEmpty(organizationUsers))
			JAList.add(new JSONArray(organizationUsers));
		
		JSONArray usersJA  = new JSONArray();
		for(JSONArray jaUser : JAList) {
			for(int i =0; i< jaUser.length() ; i++) {
				JSONObject jo = jaUser.getJSONObject(i);
				JSONObject res = new JSONObject();
					res.put("Company", jo.getString("Company"));
					res.put("Id", jo.getString("Id"));
				usersJA.put(res);
			}
		}
		renderRequest.setAttribute("compnayDropDown", helper.toList(usersJA));
		return pageName;
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException {
		resourceResponse.setContentType("text/html");
		PrintWriter out = resourceResponse.getWriter();
		String key = ParamUtil.getString(resourceRequest, "key");;
		String value = ParamUtil.getString(resourceRequest, "value");;
		String retVal = "";

		if (key.equalsIgnoreCase("confirm")) { 
			@SuppressWarnings("deprecation")
			String encryptedEmail = resourceRequest.getParameter("encryptedEmail").replace(" ", "+"); 
			String decryptedString = AES.decrypt(encryptedEmail, secretKey);
			
			if (decryptedString != null && decryptedString != "") {
				String responseString = CreateAccountUtil.callRestPost(_portletConfiguration.get("confirm-email"), decryptedString);
				if(responseString.contains("200")){
					ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
					try {
						User user = UserLocalServiceUtil.getUserByEmailAddress(themeDisplay.getCompanyId(), decryptedString);
						UserLocalServiceUtil.updateStatus(user.getUserId(), 0, new ServiceContext());
					} catch (PortalException e) {
						e.printStackTrace();
					}
				}
			}
		} else if (key.equalsIgnoreCase("deny"))
			retVal = CreateAccountUtil.callRestPost(_portletConfiguration.get("admin-activate-deactivate-by-id"),
					value);
		else if (key.equalsIgnoreCase("approve"))
			retVal = CreateAccountUtil.callRestPost(_portletConfiguration.get("admin-activate-deactivate-by-id"),
					value);
		else if (key.equalsIgnoreCase("add-edit")) {
			retVal = addEditAccount(resourceRequest);
		}
		out.println(String.valueOf(retVal));
		out.flush();

		super.serveResource(resourceRequest, resourceResponse);
	}
	
	public String addEditAccount(ResourceRequest resourceRequest) throws IOException, PortletException {
		String createAccUrl = "", emailAddress = "", resVal ="";
		byte[] portaitBytes = null;
		try {
			JSONObject jsonData = new JSONObject();
			jsonData.put("ApplicationId", Long.valueOf(_portletConfiguration.get("application-id")));
			jsonData.put("UserTypeId", Long.valueOf(_portletConfiguration.get("user-type-id")));
			jsonData.put("RoleId", Long.valueOf(_portletConfiguration.get("role-id")));
			int UserApplicationRoleId = ParamUtil.getInteger(resourceRequest, "UserApplicationRoleId");
			jsonData.put("UserApplicationRoleId", UserApplicationRoleId);
			String userType_ID = String.valueOf(UserApplicationRoleId);

			boolean edit = Boolean.valueOf(ParamUtil.getString(resourceRequest, "edit"));
			String[] jsonDataNames = null;
			if (userType_ID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.EnergyHubUser.name()))) {
				createAccUrl = "/User/RegisterEnergyHubUser";
				jsonDataNames = _portletConfiguration.getArray("form-values-energyhubuser");
				if(edit) createAccUrl = "/User/EditUser";
			} else if (userType_ID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.EntrepreneurUser.name()))) {
				createAccUrl = "/User/RegisterEntrepreneurUser";
				jsonDataNames = _portletConfiguration.getArray("form-values-energyhubuser-entrepreneur");
				if(edit) createAccUrl = "/User/EditEntrepreneurUser";
			} else if (userType_ID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.ResearcherUser.name()))) {
				createAccUrl = "/User/RegisterResearcherUser";
				jsonDataNames = _portletConfiguration.getArray("form-values-energyhubuser-researcher");
				if(edit) createAccUrl = "/User/EditResearcherUser";
			} else if (userType_ID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.EnergyCompanyUser.name()))) {
				createAccUrl = "/User/RegisterEnergyCompanyUser";
				jsonDataNames = _portletConfiguration.getArray("form-values-energyhubuser-company");
				if(edit) createAccUrl = "/User/EditEnergyCompanyUser";
			} else if (userType_ID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.InvestorUser.name()))) {
				createAccUrl = "/User/RegisterInvestorUser";
				jsonDataNames = _portletConfiguration.getArray("form-values-energyhubuser-investor");
				if(edit) createAccUrl = "/User/EditInvestorUser";
			} else if (userType_ID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.IndustryUser.name()))) {
				createAccUrl = "/User/RegisterIndustryUser";
				jsonDataNames = _portletConfiguration.getArray("form-values-energyhubuser-industry");
				if(edit) createAccUrl = "/User/EditIndustryUser";
			} else if (userType_ID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.AcademicUser.name()))) {
				createAccUrl = "/User/RegisterAcademicUser";
				jsonDataNames = _portletConfiguration.getArray("form-values-energyhubuser-academic");
				if(edit) createAccUrl = "/User/EditAcademicUser";
			} else if (userType_ID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.PolicyMakerUser.name()))) {
				createAccUrl = "/User/RegisterPolicyMakerUser";
				jsonDataNames = _portletConfiguration.getArray("form-values-energyhubuser-policymaker");
				if(edit) createAccUrl = "/User/EditPolicyMakerUser";
			}else if (userType_ID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.CarDealerUser.name()))) {
				createAccUrl = "/User/RegisterCarDealerUser";
				jsonDataNames = _portletConfiguration.getArray("form-values-energyhubuser-cardealer");
				if(edit) createAccUrl = "/User/EditCarDealerUser";
			}
			if(edit) 
				jsonData.put("Id", Integer.valueOf(ParamUtil.getString(resourceRequest, "Id")));
			for (String dataName : jsonDataNames) {
				String value = ParamUtil.getString(resourceRequest, dataName);
				if (dataName.equalsIgnoreCase("NumberOfEmployees")
						|| dataName.equalsIgnoreCase("YearOfEstablishment")
						|| dataName.equalsIgnoreCase("Country") || dataName.equalsIgnoreCase("title")
						|| dataName.equalsIgnoreCase("MaturityLevelOfSolution")
						|| dataName.equalsIgnoreCase("ChallengeToSolve")
						|| dataName.equalsIgnoreCase("Kaza")
						|| dataName.equalsIgnoreCase("Profession") || dataName.equalsIgnoreCase("Role") ) {
					jsonData.put(dataName, (value.equals("") || value == null) ? 0 : Integer.valueOf(value));

				} else if (dataName.equalsIgnoreCase("offlineVerification")
						|| dataName.equalsIgnoreCase("SubscribeToNewsletters")
						|| dataName.equalsIgnoreCase("includeInThePoolOfExperts")) {
					jsonData.put(dataName, (value.equals("") || value == null) ? false : Boolean.valueOf(value));

				} else if (dataName.equalsIgnoreCase("TypeOfSupport")
						|| dataName.equalsIgnoreCase("SDGSolution")
						|| dataName.equalsIgnoreCase("ALIRegistrationNumber")
						|| dataName.equalsIgnoreCase("OrganizationType")
						|| dataName.equalsIgnoreCase("MainBusinessActivity") ) {
					
					String[] ids = ParamUtil.getParameterValues(resourceRequest, dataName +"[]");
					value ="";
					for(String id : ids) {
						value +=  id + "/";
					}
					value = helper.isEmpty(value) ? "" : value.substring(0, value.length() - 1);
					jsonData.put(dataName, (value.equals("") || value == null) ? "" : value);

				} else if (dataName.equalsIgnoreCase("DateOfBirth")) {
					value = value.replaceAll("/", "-");
					Date date = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
					String strDate = dateFormat.format(date);  
					value = (strDate.equals("") || strDate == null) ? "1992-02-02" : strDate;
					jsonData.put(dataName, value);

				} else if (dataName.equalsIgnoreCase("Email")) {
					emailAddress = value;
					jsonData.put(dataName, value);
				}  else if (dataName.equalsIgnoreCase("Other") || dataName.equalsIgnoreCase("Company")) {
					if(dataName.equalsIgnoreCase("Company") && value.equalsIgnoreCase("Other"))
						value =  ParamUtil.getString(resourceRequest, "Other");
					jsonData.put("Company", value);
				}else if(dataName.equalsIgnoreCase("coverImage") || dataName.equalsIgnoreCase("Files")) {
					String originalDataName = dataName;
					if(edit) {
						dataName = "logoHidden";
					}
					 value = ParamUtil.getString(resourceRequest, dataName);
					 if(edit && !helper.isEmpty(value)) {
						 dataName = originalDataName;
						 if(dataName.equalsIgnoreCase("coverImage"))
							 jsonData.put("logo", value);
						 else if(dataName.equalsIgnoreCase("Files"))
							 jsonData.put("files", value);
					 }else {
						 dataName = originalDataName;
						 UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest( resourceRequest );
						 String filename = "";
						 String pathStr = uploadRequest.getSession().getServletContext().getRealPath("/"); 
						 String dirPath = pathStr+"uploadedfiles";
						 String filePath = "";
						 
						 String coverImageName = ParamUtil.getString(resourceRequest, dataName +"Name");
						 String coverImagebase64String = ParamUtil.getString(resourceRequest, dataName); 
						 String coverImageFileSize = ParamUtil.getString(resourceRequest, dataName +"Size"); 
						 
						 if(!helper.isEmpty(coverImagebase64String)) {
							 String extension = FileUtil.getExtension(coverImageName); 
							 extension = extension.equalsIgnoreCase("svg") ? "svg+xml" : extension;
							 String base64 = coverImagebase64String.split("data:image/"+extension+";base64,")[1];
							 byte[] bytes = Base64.getDecoder().decode(base64);
							 
							 filename = UUID.randomUUID().toString()+"."+extension;
							 filePath = dirPath+ "/" + filename;
							 Path fpath = Paths.get(filePath);
							 Files.write(fpath, bytes);
							 JSONObject doc = new JSONObject();
							 
							 doc.put("fileSize", coverImageFileSize);
							 doc.put("extension", extension);
							 doc.put("contentType", extension.equalsIgnoreCase("pdf") ? "application/pdf" : "image/" + extension);
							 doc.put("data", coverImagebase64String);
							 
							 if(dataName.equalsIgnoreCase("coverImage")) {
								 doc.put("name", coverImageName);
								 jsonData.put("logo", doc);
								 portaitBytes = bytes;
							 }
							 else if(dataName.equalsIgnoreCase("Files")) {
								 doc.put("name", ParamUtil.getString(resourceRequest, "FilesName"));
								 jsonData.put("files", doc);
							 }
					 }
 	 				}
				} else
					jsonData.put(dataName, value);

			}
			try {
				 resVal = CreateAccountUtil.sendPost(_portletConfiguration.get("host") + createAccUrl,
						jsonData.toString()); 
				if (resVal != "" && emailAddress != "" && resVal.contains("id")) {
					ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY); 
					User user = null;
					
					if(edit) 
						user = UserLocalServiceUtil.fetchUserByEmailAddress(themeDisplay.getCompanyId(), emailAddress);
					else 
						user = UserLocalServiceUtil.createUser(new JSONObject(resVal).getLong("id"));
					
					
					if (user != null) {
						user.setEmailAddress(emailAddress);
						user.setFirstName(jsonData.getString("FirstName"));
						user.setLastName(jsonData.getString("LastName"));
						user.setPassword(jsonData.getString("Password"));
						user.setScreenName(emailAddress.replaceAll("[^a-zA-Z0-9]", ""));
						
						if(edit) {
							UserLocalServiceUtil.updatePassword(user.getUserId(), jsonData.getString("Password"), 
									jsonData.getString("Password"), false);
							UserLocalServiceUtil.updateUser(user); 
						} else {
							Date date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonData.getString("DateOfBirth"));  
							boolean gender = false;
							if(jsonData.getString("Gender").equalsIgnoreCase("F"))
								gender = true;
							
							String roleName = UserTypes.getUserTypeName(String.valueOf(UserApplicationRoleId));
							Role role = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), roleName);
							user.setReminderQueryAnswer(jsonData.getString("Password"));
							
							user = UserLocalServiceUtil.addUser(0, themeDisplay.getCompanyId(), true, jsonData.getString("Password"),
									jsonData.getString("Password"), false, emailAddress.replaceAll("[^a-zA-Z0-9]", ""), emailAddress, themeDisplay.getLocale(),
									jsonData.getString("FirstName"),"", jsonData.getString("LastName"), 0, 0,gender, date.getMonth(), date.getDay(),
									date.getYear(), "", null, null, null, null, false, null);
							
							UserLocalServiceUtil.addRoleUser(role.getRoleId(), user.getUserId());
							UserLocalServiceUtil.updatePassword(user.getUserId(), jsonData.getString("Password"), 
									jsonData.getString("Password"), false);
							
							if(userType_ID.equalsIgnoreCase(UserTypes.getUserTypeValue(UserTypes.ResearcherUser.name()))) {
								String areasString = ParamUtil.getString(resourceRequest, "areas");
								JSONArray areas = new JSONArray(areasString);
								addAreaofExpertise(user, themeDisplay, areas);
							}
						}
						if(portaitBytes != null)
							UserLocalServiceUtil.updatePortrait(user.getUserId(), portaitBytes);
	
						if(!edit) {
							//5 => Inactive User
//							updateUserStatus(emailAddress, 5);
							UserLocalServiceUtil.updateStatus(user.getUserId(), 5, new ServiceContext());
							sendConfirmationEmail(emailAddress);
						}
					}
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return resVal;
	}
	
	
	public static void updateUserStatus(String email, int status) throws SQLException {
		Connection con = null;
		try {
			con = DataAccess.getConnection();
			PreparedStatement updateStatement = con.prepareStatement("UPDATE user_ SET status =? WHERE emailAddress = ? ");
    		updateStatement.setInt(1, status);
    		updateStatement.setString(2, email);
	    	updateStatement.executeUpdate();
    		updateStatement.close();
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			if( con!=null ) {
				con.close();
			}
	 	}
	}
	
	public void addAreaofExpertise(User user, ThemeDisplay themeDisplay, JSONArray areas) throws PortalException {
		Role adminRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.ADMINISTRATOR);
		List<User>admins = UserLocalServiceUtil.getRoleUsers(adminRole.getRoleId());
		
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(themeDisplay.getLayout().getGroupId());
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
		Map<Locale, String> titleMap = new HashMap<Locale, String>();
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
		titleMap.put(Locale.US, user.getEmailAddress());
	    descriptionMap.put(Locale.US, user.getEmailAddress());		
		
		com.liferay.dynamic.data.mapping.model.DDMStructure structs = DDMStructureLocalServiceUtil.getStructures().stream()
				.filter(st -> st.getName("en_US").equalsIgnoreCase("area-expertise")).findFirst().orElse(null);
		
		String xmlContent = "<?xml version=\"1.0\"?>\r\n" + "\r\n"
				+ "<root available-locales=\"en_US\" default-locale=\"en_US\" version=\"1.0\">\r\n";
		for(int i=0; i<areas.length();i++) {
			JSONObject jo = areas.getJSONObject(i);
			xmlContent += "	<dynamic-element index-type=\"\" instance-id=\"MGctpOJn\" name=\"FieldsGroup65973700\" type=\"fieldset\">\r\n" + 
					"		<dynamic-element index-type=\"keyword\" instance-id=\"RXbc80F7\" name=\"SelectFromList23403899\" type=\"select\">\r\n" + 
					"			<dynamic-content language-id=\"en_US\"><![CDATA["+ jo.get("area") +"]]></dynamic-content>\r\n" + 
					"		</dynamic-element>\r\n" + 
					"		<dynamic-element index-type=\"keyword\" instance-id=\"AFfNzdSO\" name=\"SelectFromList47700469\" type=\"select\">\r\n" + 
					"			<dynamic-content language-id=\"en_US\"><![CDATA["+ jo.get("level") +"]]></dynamic-content>\r\n" + 
					"		</dynamic-element>\r\n" + 
					"	</dynamic-element>";
		}
		xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"uu7O4lTu\" name=\"Text63214587\" type=\"text\">\r\n" + 
				"		<dynamic-content language-id=\"en_US\"><![CDATA["+ user.getEmailAddress() +"]]></dynamic-content>\r\n" + 
				"	</dynamic-element>";
		xmlContent += "</root>";
		
		JournalArticleLocalServiceUtil.addArticle(admins.get(0).getUserId(), themeDisplay.getLayout().getGroupId(), areaofExpertiseFolerId, 
				titleMap, descriptionMap, xmlContent, structs.getStructureKey(), "", serviceContext);
	}

	public void sendConfirmationEmail(String toEmail) throws MessagingException, PwdEncryptorException, NoSuchAlgorithmException {
		String url = "https://www.energyhub-lb.com/";
		String encryptedString = AES.encrypt(toEmail, secretKey);
		String url_link = url + "create-account?EmailVerification=" + encryptedString;
		CreateAccountUtil.sendPostPhp(_portletConfiguration.get("send-activation-email"),toEmail, url_link); 
		
		
//		String from = "no-reply@energyhub-lb.com";
//		String password = "P@$$w0rd";
//		String host = "smtp.office365.com";
//		int port = 587;
//		Properties props = new Properties(); 	        
//        
//		props.put("mail.transport.protocol", "smtp");
//		props.put("mail.smtp.host", host);
//		props.put("mail.smtp.auth", true);
//		props.put("mail.smtp.user", from);
//		props.put("mail.smtp.password", password);
//		props.put("mail.smtp.starttls.enable", true);
//      props.put("mail.smtp.port", port);
//      props.put("mail.smtp.ssl.enable", "true");
//        
//      System.out.println("Properties SET");
//        
//	    Session session = Session.getDefaultInstance(props);
//	    Transport trans = session.getTransport("smtp"); 
//		
//		Message  message = new MimeMessage(session);
//		message.setFrom(new InternetAddress(from));
//		message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
//		message.setSubject("EnergyHub Email Verification");
//
//		String encryptedString = AES.encrypt(toEmail, secretKey);
//		String url_link = url + "create-account?EmailVerification=" + encryptedString;
//		message.setContent("Please Verify your email address through this <a href='"+url_link+"'>link</a>", "text/html");
//
//		System.out.println("Transport connecting ...");
//		
//		trans.connect(host, port, from, password);
//		
//		System.out.println("Connected");
//		
//		trans.sendMessage(message, message.getAllRecipients());
//		trans.close();
//		
//		System.out.println("Email sent!");
	}
	
	private List<JournalArticle> getSearchedArticles(ThemeDisplay themeDisplay, List<JournalArticle> filtered, String queryText, List<String> fieldName) throws DocumentException, PortalException {
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
					}
				}
			}
		}
		
		List<JournalArticle> finalreturnedArticles = new ArrayList<JournalArticle>(); 
		for (JournalArticle article : returnedArticles) {
			finalreturnedArticles.add(JournalArticleLocalServiceUtil.getLatestArticle(article.getResourcePrimKey()));
		}
		return finalreturnedArticles;
	}
}