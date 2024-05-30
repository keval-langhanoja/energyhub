package reportsModule.portlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

//import com.github.wnameless.json.flattener.JsonFlattener;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
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
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import reportsModule.constants.ReportsModulePortletKeys;
import reportsModule.helper.UserTypes;
import reportsModule.helper.db_helper;
import reportsModule.helper.helper;
import reportsModule.helper.util;

/**
 * @author vyo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=ReportsModule",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.name=" + ReportsModulePortletKeys.REPORTSMODULE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)

public class ReportsModulePortlet extends MVCPortlet {

	private String EventCategoryName = "Events";
	public static String ThreadCategoryName ="Discussion Forums", SuccessStoryCategoryName ="Success Stories",
			OnGoingCategoryName = "Ongoing Projects", InnovationProgramsCategoryName = "Innovation Programs",
			InnovationChallengesCategoryName = "Innovation Challenges", OpportunitiesFroIndustriesCategoryName = "Opportunities for Industries", 
			NewsCategoryName = "News",  EventsCategoryName = "Events",  PublicationCategoryName = "Publications";
	
	private String ReportName = "Text65588520";
	public static String ThreadTitle = "Text94657287", ThreadType = "SelectFromList86123921";
	public static String SuccessStoryTitle = "Text31592112", SuccessStoryType = "SelectFromList20825923";
	
	public static String InnovationProgramTitle = "Text03803647", InnovationProgramStage = "SelectFromList56281506",
			InnovationProgramActivityType = "SelectFromList81632169", InnovationProgramStartDate = "Date64465134", 
			InnovationProgramEndDate = "Date88001610";
	
	public static String InnovationChallengeTitle = "Text68843417", InnovationChallengeChallengeType = "SelectFromList17093910",
			InnovationChallengeStartDate = "Date48434022", InnovationChallengeEndDate = "Date57085928";
	
	public static String OppIndustTitle = "Text11601604", OppIndustType = "SelectFromList51477468", OppIndustStartDate = "Date03889697", 
			OppIndustEndDate = "Date01559990";
	
	public static String OnGoiongTitle = "Text73021449", OnGoingStage = "SelectFromList36742361", OnGoingCateg = "SelectFromList03925321";
	
	public static String NewsTitle = "Text54400606", NewsType = "SelectFromList84062816";
	
	public static String EventName = "Text63667232", Attendance = "SelectFromList53435675", EventType = "SelectFromList76720910";
	
	public static String PublicationTitle = "Text54116795", PublicationType = "SelectFromList73350436";
	
	private final  Configuration _portletConfiguration = ConfigurationFactoryUtil.getConfiguration(PortalClassLoaderUtil.getClassLoader(), "portlet");

	private List<String> oppFilter = Arrays.asList(OppIndustType);
	private List<String> onGoigProjectFilter = Arrays.asList(OnGoingCateg, OnGoingStage);
	private List<String> InnovProgFilter = Arrays.asList(InnovationProgramActivityType, InnovationProgramStage);
	private List<String> InnovChallengeFilter = Arrays.asList(InnovationChallengeChallengeType);
	private List<String> threadFilter = Arrays.asList(ThreadType);
	private List<String> stroryFilter = Arrays.asList(SuccessStoryType);
	private List<String> newsFilter = Arrays.asList(NewsType);
	private List<String> eventFilter = Arrays.asList(Attendance, EventType);
	private List<String> publicationFilter = Arrays.asList(PublicationType);
	
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY); 
			 
			HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
			JSONObject filter = new JSONObject(helper.ifEmpty(httpReq.getParameter("filter"), "{}"));
			String sort = helper.ifEmpty(httpReq.getParameter("sort"), "desc");
			String sortField = helper.ifEmpty(httpReq.getParameter("sortField"), "");
			
			String reportKey = httpReq.getParameter("key");
			String id = httpReq.getParameter("id");

			JournalArticle report = JournalArticleLocalServiceUtil.getArticle(Long.valueOf(id));
			String content = report.getContentByLocale(themeDisplay.getLanguageId());
		    Document document = SAXReaderUtil.read(content);
		    String reportName = document.selectSingleNode("/root/dynamic-element[@name='"+ReportName+"']").hasContent()?
        			document.selectSingleNode("/root/dynamic-element[@name='"+ReportName+"']/dynamic-content").getText() : "";
		    renderRequest.setAttribute("reportName", reportName);
		    
		    //COMMUNITY REPORTS
			if(reportKey.equalsIgnoreCase("topthreadsReport")) {
				getTopThreadsReport(renderRequest, themeDisplay, filter, sortField, sort);
				include("/topCountsReportCommunity.jsp", renderRequest, renderResponse);
			}
			if(reportKey.equalsIgnoreCase("userthreadsReport") || reportKey.equalsIgnoreCase("userActivityonThreadsReport")) {
				AssetCategory assetCateg = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase(ThreadCategoryName)).findFirst().orElse(null);
				
				getReport(renderRequest, themeDisplay, filter, assetCateg, reportKey, sort, sortField);
				
				if(reportKey.equalsIgnoreCase("userthreadsReport"))
					include("/userthreadsReport.jsp", renderRequest, renderResponse);
				if(reportKey.equalsIgnoreCase("userActivityonThreadsReport"))
					include("/userthreadsReport.jsp", renderRequest, renderResponse);
			}
			
			//ADMIN OVERVIEW
			if(reportKey.equalsIgnoreCase("overviewReport")) {
				getOverviewReport(renderRequest, themeDisplay);
				include("/overviewReport.jsp", renderRequest, renderResponse);
			}
			
			//FOR BUSINESS REPORTS
			if(reportKey.equalsIgnoreCase("successStoriesReport") || reportKey.equalsIgnoreCase("UserRelatedSuccessStoriesReport")) {
				AssetCategory assetCateg = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase(SuccessStoryCategoryName)).findFirst().orElse(null);
				
				getReport(renderRequest, themeDisplay, filter, assetCateg, reportKey, sort, sortField);
				if(reportKey.equalsIgnoreCase("successStoriesReport"))
//					include("/successStoryReport.jsp", renderRequest, renderResponse);
//				if(reportKey.equalsIgnoreCase("UserRelatedSuccessStoriesReport"))
					include("/userRelatedSuccessStoriesReport.jsp", renderRequest, renderResponse);
			}
			
			//INNOVATION REPORTS
				//ONGOING
			if(reportKey.equalsIgnoreCase("OngoingProjectReport") || reportKey.equalsIgnoreCase("UserRelatedOngoingProjectsReport")) {
				AssetCategory assetCateg = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase(OnGoingCategoryName)).findFirst().orElse(null);
				
				getReport(renderRequest, themeDisplay, filter, assetCateg, reportKey, sort, sortField);
				if(reportKey.equalsIgnoreCase("OngoingProjectReport"))
//					include("/onGoingProjectReport.jsp", renderRequest, renderResponse);
//				if(reportKey.equalsIgnoreCase("UserRelatedOngoingProjectsReport"))
					include("/userRelatedonGoingProjectReportReport.jsp", renderRequest, renderResponse);
			}
				//INNOVATION PROGRAMS
			if(reportKey.equalsIgnoreCase("InnovationProgramsReport") || reportKey.equalsIgnoreCase("UserRelatedInnovationProgramsReport")) {
				AssetCategory assetCateg = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase(InnovationProgramsCategoryName)).findFirst().orElse(null);
				
				getReport(renderRequest, themeDisplay, filter, assetCateg, reportKey, sort, sortField);
				if(reportKey.equalsIgnoreCase("InnovationProgramsReport"))
//					include("/innovationProgramsReport.jsp", renderRequest, renderResponse);
//				if(reportKey.equalsIgnoreCase("UserRelatedInnovationProgramsReport"))
					include("/userRelatedInnovationProgramsReport.jsp", renderRequest, renderResponse);
			}
				//INNOVATION CHALLENGES
			if(reportKey.equalsIgnoreCase("InnovationChallengesReport") || reportKey.equalsIgnoreCase("UserRelatedInnovationChallengesReport")) {
				AssetCategory assetCateg = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase(InnovationChallengesCategoryName)).findFirst().orElse(null);
				
				getReport(renderRequest, themeDisplay, filter, assetCateg, reportKey, sort, sortField);
				if(reportKey.equalsIgnoreCase("InnovationChallengesReport"))
//					include("/innovationChallengesReport.jsp", renderRequest, renderResponse);
//				if(reportKey.equalsIgnoreCase("UserRelatedInnovationChallengesReport"))
					include("/userRelatedInnovationChallengesReport.jsp", renderRequest, renderResponse);
			}
			
			//Opportunities For Industries REPORT
			if(reportKey.equalsIgnoreCase("OpportunitiesForIndustriesReport") || reportKey.equalsIgnoreCase("UserRelatedOpportunitiesForIndustriesReport")) {
				AssetCategory assetCateg = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase(OpportunitiesFroIndustriesCategoryName)).findFirst().orElse(null);
				
				getReport(renderRequest, themeDisplay, filter, assetCateg, reportKey, sort, sortField);
				if(reportKey.equalsIgnoreCase("OpportunitiesForIndustriesReport"))
//					include("/opportunitiesForIndustriesReport.jsp", renderRequest, renderResponse);
//				if(reportKey.equalsIgnoreCase("UserRelatedOpportunitiesForIndustriesReport"))
					include("/userRelatedOpportunitiesForIndustriesReport.jsp", renderRequest, renderResponse);
			}
			
			//NEWS REPORT
			if(reportKey.equalsIgnoreCase("NewsReport")) {
				AssetCategory assetCateg = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase(NewsCategoryName)).findFirst().orElse(null);
				
				getReport(renderRequest, themeDisplay, filter, assetCateg, reportKey, sort, sortField);
				if(reportKey.equalsIgnoreCase("NewsReport"))
					include("/newsReport.jsp", renderRequest, renderResponse);
			}
			
			//EVENTS REPORT
			if(reportKey.equalsIgnoreCase("EventsReport")) {
				AssetCategory assetCateg = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase(EventCategoryName)).findFirst().orElse(null);
				
				getReport(renderRequest, themeDisplay, filter, assetCateg, reportKey, sort, sortField);
				if(reportKey.equalsIgnoreCase("EventsReport"))
					include("/eventsReport.jsp", renderRequest, renderResponse);
			}
			
			//RESSOURCES REPORT
			if(reportKey.equalsIgnoreCase("PublicationsReport") || reportKey.equalsIgnoreCase("PublicationsTypesReport")) {
				AssetCategory assetCateg = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase(PublicationCategoryName)).findFirst().orElse(null);
				
				getReport(renderRequest, themeDisplay, filter, assetCateg, reportKey, sort, sortField);
				if(reportKey.equalsIgnoreCase("PublicationsReport"))
//					include("/publicationsReport.jsp", renderRequest, renderResponse);
//				if(reportKey.equalsIgnoreCase("PublicationsTypesReport"))
					include("/publicationsTypesReport.jsp", renderRequest, renderResponse);
			}
			
//			EDUCATION REPORTS
			if(reportKey.equalsIgnoreCase("CoursesReport") || reportKey.equalsIgnoreCase("CoursesAttendeesReport")) {
				getEducationReport(renderRequest, themeDisplay, filter, reportKey, sort, sortField);
				if(reportKey.equalsIgnoreCase("CoursesReport")) 
					include("/coursesReport.jsp", renderRequest, renderResponse);
				if(reportKey.equalsIgnoreCase("CoursesAttendeesReport")) 
					include("/coursesAttendessReport.jsp", renderRequest, renderResponse);
			}
			super.render(renderRequest, renderResponse); 
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getEducationReport(RenderRequest renderRequest, ThemeDisplay themeDisplay, JSONObject filter,
			String reportKey, String sort, String sortField) throws IOException, JSONException, DocumentException {
		
		String csvString = "", csvDataALL ="", url = "";
		
		JSONArray countryJA = new JSONArray();
		JSONArray professionJA = new JSONArray();
		JSONArray roles =new JSONArray();
		JSONArray categJA = new JSONArray();
		JSONArray genderJA = new JSONArray();
		
		if(reportKey.equalsIgnoreCase("CoursesReport")) {
			String categories = _portletConfiguration.get("get-all-categories");
			String categs = util.moodleRestGet(categories, "");
			if(!helper.isEmpty(categs)) {
				JSONArray tmpCatgeJA = new JSONArray(categs);
				categJA = util.GetTreeJA(tmpCatgeJA, categJA);
			}
		}
		renderRequest.setAttribute("MoodleCategs", helper.toList(categJA));
		
		//Get all courses anyways, with filtering for both reports 
		String filterCateg = "";
		if(filter.has("MoodleCategs") && filter.getJSONArray("MoodleCategs").length()>0 ) {
			for(int i=0; i< filter.getJSONArray("MoodleCategs").length();i++) {
				String val = filter.getJSONArray("MoodleCategs").getString(i);
				 filterCateg += val;
			}
		} 
		
		if(!helper.isEmpty(filterCateg)) 
			url = _portletConfiguration.get("get-courses-by-categ") + filterCateg;
		else 
			url = _portletConfiguration.get("get-courses");
		
		
		String coursesString = util.moodleRestGet(url, "");
		JSONArray courses = new JSONArray(); 
		if(!helper.isEmpty(coursesString)) {
			courses = new JSONArray(coursesString); 
			courses.remove(courses.length()-1);
		} 
		
		for(int i = 0; i < courses.length(); i++) {
			JSONObject course = courses.getJSONObject(i);
			csvDataALL += course.getString("courseName") ;
			if(reportKey.equalsIgnoreCase("CoursesReport"))
				csvDataALL += "," +course.getString("categoryName");
				csvDataALL += "___newLine___";
		}
		
		if(reportKey.equalsIgnoreCase("CoursesAttendeesReport")) {
			countryJA = getIAMFilter(renderRequest, themeDisplay, "country");
			professionJA = getIAMFilter(renderRequest, themeDisplay, "profession");
			professionJA = getIAMFilter(renderRequest, themeDisplay, "profession");
			
			String genderList = util.getDropdowns(util.getToken(_portletConfiguration), _portletConfiguration.get("get-gender-list"));
			JSONArray tmp  = new JSONArray(genderList);
			for(int x =0; x< tmp.length() ; x++) {
				JSONObject jo = new JSONObject(tmp.getString(x));
				genderJA.put(jo);
			}
			renderRequest.setAttribute("gender", helper.toList(genderJA));
			
			roles = UserTypes.valuesList();
			renderRequest.setAttribute("roles", helper.toList(roles));
			
			for(int i = 0; i < courses.length(); i++) {
				JSONObject course = courses.getJSONObject(i);
				JSONArray attendeesInfoJA = new JSONArray(); 
				int courseID = course.getInt("courseID");
				
				String courseAttendeesString = util.moodleRestGet(_portletConfiguration.get("get-courses-attendees") + courseID, "");
				JSONArray courseAttendeesJA = new JSONArray(courseAttendeesString); 
				
				for (int j = 0; j < courseAttendeesJA.length(); j++) {
					JSONObject attJO = courseAttendeesJA.getJSONObject(j);
					String token = util.getToken(_portletConfiguration);
//					String userString = util.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), attJO.getString("email"));
					String userString = util.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), "EnergyHubUser@undp.com");
					
					if(!helper.isEmpty(userString)) {
						attJO = new JSONObject(userString);
						if(attJO.has("firstName")) {
							JSONObject attendee = new JSONObject();
							String fullName = attJO.getString("firstName") + " " + attJO.getString("lastName");
							String roleName = util.returnJsonValue(roles, attJO.getInt("userApplicationRoleId"));
							String country =  util.returnJsonValue(countryJA, attJO.getInt("country"));
							String profession =  util.returnJsonValue(professionJA, attJO.getInt("profession"));
							String genderID =  attJO.getString("gender");
							String gender = "";
							if(genderID.equalsIgnoreCase("M")) gender = "Male";
							else if(genderID.equalsIgnoreCase("F")) gender = "Female";
							
							attendee.put("attFullName", fullName);
							attendee.put("attRole", roleName);
							attendee.put("attRoleID", attJO.getInt("userApplicationRoleId"));
							attendee.put("attCountry", country);
							attendee.put("attCountryID", attJO.getInt("country"));
							attendee.put("attProfession", profession);
							attendee.put("attProfessionID", attJO.getInt("profession"));
							attendee.put("attGender", gender);
							attendee.put("attGenderID", genderID);
							attendeesInfoJA.put(attendee);
							
							csvDataALL += fullName +"," +roleName+ "," +country+"," +profession+"," +gender;
							csvDataALL += "___newLine___";
						}
					}
				}
				course.put("attendees", attendeesInfoJA);
				course.put("attendeesTotal", attendeesInfoJA.length()+1);
			}
		}
		
		if(filter.has("country") && filter.getJSONArray("country").length()>0 ) {
			courses = getFilteredCourses(themeDisplay,courses, filter.getJSONArray("country"), "country");
		}
		if(filter.has("profession") && filter.getJSONArray("profession").length()>0 ) {
			courses = getFilteredCourses(themeDisplay,courses, filter.getJSONArray("profession"), "profession");
		}
		if(filter.has("roles") && filter.getJSONArray("roles").length()>0 ) {
			courses = getFilteredCourses(themeDisplay,courses, filter.getJSONArray("roles"), "roles");
		}
		if(filter.has("gender") && filter.getJSONArray("gender").length()>0 ) {
			courses = getFilteredCourses(themeDisplay,courses, filter.getJSONArray("gender"), "gender");
		}
		

		renderRequest.setAttribute("entries", helper.toList(courses));
		
		if(reportKey.equalsIgnoreCase("CoursesReport"))
			csvString += "Course,Category";
		if(reportKey.equalsIgnoreCase("CoursesAttendeesReport"))
			csvString += "Course,Attendee,Role,Country,Profession,Gender";
		  
		csvString += "___newLine___";
		csvString += csvDataALL;
		
		renderRequest.setAttribute("csvString", csvString.toString()); 
	}

	private void getTopThreadsReport(RenderRequest renderRequest, ThemeDisplay themeDisplay, JSONObject filter, String sortField, String sort) throws SQLException, PortalException, DocumentException {
		DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
				.filter(st -> st.getName("en_US").equalsIgnoreCase("thread")).findFirst().orElse(null);	
		getFilterData(themeDisplay, renderRequest, struct);
		
		JSONArray topUpvoted = db_helper.getTop("Upvoted", themeDisplay, renderRequest, filter);
		JSONArray topDownVoted = db_helper.getTop("DownVoted", themeDisplay, renderRequest, filter);
		JSONArray topViewed = db_helper.getTop("Viewed", themeDisplay, renderRequest, filter);
		JSONArray topCommented = db_helper.getTop("Commented", themeDisplay, renderRequest, filter);
		
		if(!helper.isEmpty(sortField)) {
			topUpvoted = sortReport(topUpvoted, sortField, sort);
			topDownVoted = sortReport(topDownVoted, sortField, sort);
			topViewed = sortReport(topViewed, sortField, sort);
			topCommented = sortReport(topCommented, sortField, sort);
		}		
		
		String csvString = "topType,threadTitle,Category,Role,Count";
		csvString += "___newLine___";
		csvString += getCSVDataFromJSONArray(topUpvoted, "topUpvoted");
		csvString += getCSVDataFromJSONArray(topDownVoted, "topDownVoted");
		csvString += getCSVDataFromJSONArray(topViewed, "topViewed");
		csvString += getCSVDataFromJSONArray(topCommented, "topCommented"); 
		
		renderRequest.setAttribute("topUpvoted", helper.toList(topUpvoted));
		renderRequest.setAttribute("topDownVoted", helper.toList(topDownVoted));
		renderRequest.setAttribute("topViewed", helper.toList(topViewed));
		renderRequest.setAttribute("topCommented", helper.toList(topCommented));
		renderRequest.setAttribute("csvString", csvString);
	}
	
	private String getCSVDataFromJSONArray(JSONArray ja, String topType) {
		String csvData ="";
		for(int i =0; i< ja.length(); i++) {
			JSONObject jo = ja.getJSONObject(i);
			csvData += topType+ ","+jo.getString("csVData");
		}
		return csvData;
	}

	private void getOverviewReport(RenderRequest renderRequest, ThemeDisplay themeDisplay) {
		String token = util.getToken(_portletConfiguration);
		String roleCount = util.getOverviewReport(token, _portletConfiguration.get("get-count-users-active-role"), "");
		renderRequest.setAttribute("roleCount", helper.isEmpty(roleCount) ? "0" : roleCount);
		
		String genderCount = util.getOverviewReport(token, _portletConfiguration.get("get-count-active-gender"), "");
		renderRequest.setAttribute("genderCount", helper.isEmpty(genderCount) ? "0" : genderCount);
		
		String countryCount = util.getOverviewReport(token, _portletConfiguration.get("get-count-country-users"), "");
		renderRequest.setAttribute("countryCount", helper.isEmpty(countryCount) ? "0" : countryCount);
		
		String agesCount = util.getOverviewReport(token, _portletConfiguration.get("get-count-ages-users"), "");
		renderRequest.setAttribute("agesCount", helper.isEmpty(agesCount) ? "0" : agesCount);
		
		LocalDate currentDate = LocalDate.now();
		LocalDate thisMonth = currentDate.minusMonths(1);
		int counter = 0;
		List<User> users = UserLocalServiceUtil.getUsers(0, UserLocalServiceUtil.getUsersCount());
		for(User user : users) {
			Date lastLogin = user.getLastLoginDate();
			if(lastLogin !=null &&  user.isActive()) {
				LocalDate lastLoginDate = lastLogin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				if(lastLoginDate.isAfter(thisMonth)){
					counter++;
				}
			}
		}
		renderRequest.setAttribute("activeUsersCount", counter);
	}
	
	private void getReport(RenderRequest renderRequest, ThemeDisplay themeDisplay, JSONObject filter, 
			AssetCategory assetCateg, String reportName, String sort, String sortField) throws PortalException, DocumentException, SQLException, ParseException {

		String csvString = "", csvDataALL ="";
		List<User> allUsers = UserLocalServiceUtil.getUsers(0, UserLocalServiceUtil.getUsersCount());
		JSONArray res = new JSONArray();
		
		List<JournalArticle> articles = new ArrayList<JournalArticle>();
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery(); 
		assetEntryQuery.setAllCategoryIds(new long[] { assetCateg.getCategoryId() });

		DDMStructure  struct = null;
		List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
		for (AssetEntry ae : assetEntryList) {
			JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
			JournalArticle article = JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey());
			articles.add(article);
		} 
		
		List<JournalArticle> filtered = articles;
		struct = articles.get(0).getDDMStructure();
		getFilterData(themeDisplay, renderRequest, struct);
		
	   String today = DateTimeFormatter.ofPattern("yyyy-dd-MM").format(LocalDateTime.now());
		
		if(filter.has("startDate") && filter.getJSONArray("startDate").length()>0 &&
				!filter.getJSONArray("startDate").getString(0).equalsIgnoreCase("1970-01-01") &&
				filter.has("endDate") && filter.getJSONArray("endDate").length()>0 &&
				!filter.getJSONArray("endDate").getString(0).equalsIgnoreCase(today) ) {
			filtered = getFilteredArticlesByCreatedDate(articles, filter.getJSONArray("startDate"), filter.getJSONArray("endDate"));
		} 
		if(filter.has("ThreadType") && filter.getJSONArray("ThreadType").length()>0 ) {
			filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("ThreadType"),ThreadType);
		}
		
		if(filter.has("SuccessStoryType") && filter.getJSONArray("SuccessStoryType").length()>0 ) {
			filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("SuccessStoryType"),SuccessStoryType);
		}
		
		if(filter.has("InnovationChallengeChallengeType") && filter.getJSONArray("InnovationChallengeChallengeType").length()>0 ) {
			filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("InnovationChallengeChallengeType"),InnovationChallengeChallengeType);
		}
		
		if(filter.has("OppIndustType") && filter.getJSONArray("OppIndustType").length()>0 ) {
			filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("OppIndustType"),OppIndustType);
		}
		
		if(filter.has("NewsType") && filter.getJSONArray("NewsType").length()>0 ) {
			filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("NewsType"),NewsType);
		}
		
		if(filter.has("Attendance") && filter.getJSONArray("Attendance").length()>0 ) {
			filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("Attendance"),Attendance);
		}
		
		if(filter.has("EventType") && filter.getJSONArray("EventType").length()>0 ) {
			filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("EventType"),EventType);
		}
		
		if(filter.has("PublicationType") && filter.getJSONArray("PublicationType").length()>0 ) {
			filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("PublicationType"),PublicationType);
		}
		
		if(filter.has("InnovationProgramActivityType") && filter.getJSONArray("InnovationProgramActivityType").length()>0 ) {
			filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("InnovationProgramActivityType"),InnovationProgramActivityType);
		}
		
		if(filter.has("InnovationProgramStage") && filter.getJSONArray("InnovationProgramStage").length()>0 ) {
			filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("InnovationProgramStage"),InnovationProgramStage);
		}
		
		if(filter.has("OnGoingCateg") && filter.getJSONArray("OnGoingCateg").length()>0 ) {
			filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("OnGoingCateg"),OnGoingCateg);
		}
		
		if(filter.has("OnGoingStage") && filter.getJSONArray("OnGoingStage").length()>0 ) {
			filtered = getFilteredArticles(themeDisplay,filtered, filter.getJSONArray("OnGoingStage"),OnGoingStage);
		}
		
		if(reportName.equalsIgnoreCase("userthreadsReport") || reportName.equalsIgnoreCase("userActivityonThreadsReport") 
				|| reportName.equalsIgnoreCase("UserRelatedSuccessStoriesReport") || reportName.equalsIgnoreCase("UserRelatedOngoingProjectsReport")
				|| reportName.equalsIgnoreCase("UserRelatedInnovationProgramsReport") || reportName.equalsIgnoreCase("UserRelatedInnovationChallengesReport")
				|| reportName.equalsIgnoreCase("UserRelatedOpportunitiesForIndustriesReport")
				|| reportName.equalsIgnoreCase("successStoriesReport") || reportName.equalsIgnoreCase("OngoingProjectReport")
				|| reportName.equalsIgnoreCase("InnovationChallengesReport") || reportName.equalsIgnoreCase("InnovationProgramsReport") 
				|| reportName.equalsIgnoreCase("OpportunitiesForIndustriesReport")) {
			
//		if(reportName.equalsIgnoreCase("userthreadsReport") || reportName.equalsIgnoreCase("userActivityonThreadsReport") 
//				|| reportName.equalsIgnoreCase("UserRelatedSuccessStoriesReport") || reportName.equalsIgnoreCase("UserRelatedOngoingProjectsReport")
//				|| reportName.equalsIgnoreCase("UserRelatedInnovationProgramsReport") || reportName.equalsIgnoreCase("UserRelatedInnovationChallengesReport")
//				|| reportName.equalsIgnoreCase("UserRelatedOpportunitiesForIndustriesReport")) {
			for(User user : allUsers) {
				String csVData ="";
				JSONObject userJO = new JSONObject();
				JSONArray entries = new JSONArray();
				userJO.put("UserName", user.getFullName().equalsIgnoreCase("")? "Admin" : user.getFullName());
				
				for(JournalArticle article : filtered) {
					if(user.getUserId() == article.getUserId()) {
						String content = article.getContentByLocale(themeDisplay.getLanguageId());;
					    Document document = SAXReaderUtil.read(content);
					    JSONObject dataJO = new JSONObject();
					    
					    if(reportName.equalsIgnoreCase("UserRelatedSuccessStoriesReport")
					    		|| reportName.equalsIgnoreCase("successStoriesReport")) {
					    	String title = helper.getStringFromDoc(document, SuccessStoryTitle);
					    	if(!helper.isEmpty(title)) {
					    		dataJO = getSuccessStoryJOData(document, themeDisplay, renderRequest, article, assetCateg);
					    		csVData +=  user.getFullName().equalsIgnoreCase("")? "Admin" : user.getFullName() + ",";
					    		csVData += dataJO.getString("csVData");
					    	}
					    }
					    if(reportName.equalsIgnoreCase("UserRelatedOpportunitiesForIndustriesReport")
					    		|| reportName.equalsIgnoreCase("OpportunitiesForIndustriesReport")) {
					    	String title = helper.getStringFromDoc(document, OppIndustTitle);
					    	if(!helper.isEmpty(title)) {
						    	dataJO = getUserRelatedOpportunitiesForIndustriesReportJOData(document, themeDisplay, renderRequest, article, assetCateg);
						    	csVData +=  user.getFullName().equalsIgnoreCase("")? "Admin" : user.getFullName() + ",";
						    	csVData += dataJO.getString("csVData");
					    	}
					    }
					    if(reportName.equalsIgnoreCase("UserRelatedOngoingProjectsReport")
					    		|| reportName.equalsIgnoreCase("OngoingProjectReport")) {
					    	String title = helper.getStringFromDoc(document, OnGoiongTitle);
					    	if(!helper.isEmpty(title)) {
						    	dataJO = getOnGoingProjectJOData(document, themeDisplay, renderRequest, article, assetCateg);
						    	csVData +=  user.getFullName().equalsIgnoreCase("")? "Admin" : user.getFullName() + ",";
						    	csVData += dataJO.getString("csVData");
					    	}
					    }
					    if(reportName.equalsIgnoreCase("UserRelatedInnovationProgramsReport")
					    		|| reportName.equalsIgnoreCase("InnovationProgramsReport")) {
					    	String title = helper.getStringFromDoc(document, InnovationProgramTitle);
					    	if(!helper.isEmpty(title)) {
						    	dataJO = getInnovationProgramsJOData(document, themeDisplay, renderRequest, article, assetCateg);
						    	csVData +=  user.getFullName().equalsIgnoreCase("")? "Admin" : user.getFullName() + ",";
						    	csVData += dataJO.getString("csVData");
					    	}
					    }
					    if(reportName.equalsIgnoreCase("UserRelatedInnovationChallengesReport")
					    		|| reportName.equalsIgnoreCase("InnovationChallengesReport")) {
					    	String title = helper.getStringFromDoc(document, InnovationChallengeTitle);
					    	if(!helper.isEmpty(title)) {
						    	dataJO = getInnovationChallengesJOData(document, themeDisplay, renderRequest, article, assetCateg);
						    	csVData +=  user.getFullName().equalsIgnoreCase("")? "Admin" : user.getFullName() + ",";
						    	csVData += dataJO.getString("csVData");
					    	}
					    }
					    if(reportName.equalsIgnoreCase("userthreadsReport")) {
					    	String title = helper.getStringFromDoc(document, ThreadTitle);
					    	if(!helper.isEmpty(title)) {
						    	dataJO = getThreadsJOData(document, themeDisplay, renderRequest, article, assetCateg);
						    	csVData +=  user.getFullName().equalsIgnoreCase("")? "Admin" : user.getFullName() + ",";
						    	csVData += dataJO.getString("csVData");
					    	}
					    }
					    if(reportName.equalsIgnoreCase("userActivityonThreadsReport")) {
					    	String title = helper.getStringFromDoc(document, ThreadTitle);
					    	if(!helper.isEmpty(title)) {
						    	Boolean hasActivty = db_helper.getUserActivityOnThread(user.getUserId(), article.getArticleId());
						    	if(hasActivty) {
						    		dataJO = getThreadsJOData(document, themeDisplay, renderRequest, article, assetCateg);
						    		if(!dataJO.isEmpty()) {
						    			csVData +=  user.getFullName().equalsIgnoreCase("")? "Admin" : user.getFullName() + ",";
						    			csVData += dataJO.getString("csVData");
						    		}
						    	}
					    	}
					    }
					    
					    if(!dataJO.isEmpty())
					    	entries.put(dataJO.getJSONObject("data"));
					}
				}
				
				if(entries.length() > 0) {
					if(!helper.isEmpty(sortField))
						entries = sortReport(entries, sortField, sort);

					userJO.put("entries", entries);
					userJO.put("entryTotal", entries.length()+1);
					csvDataALL += csVData;
					res.put(userJO);
				}
			}
		}

		if(reportName.equalsIgnoreCase("EventsReport") || reportName.equalsIgnoreCase("NewsReport")){
//				|| reportName.equalsIgnoreCase("PublicationsReport")
//				|| reportName.equalsIgnoreCase("successStoriesReport") || reportName.equalsIgnoreCase("OngoingProjectReport")
//				|| reportName.equalsIgnoreCase("InnovationChallengesReport") || reportName.equalsIgnoreCase("InnovationProgramsReport") 
//				|| reportName.equalsIgnoreCase("OpportunitiesForIndustriesReport")
				
			String csVData ="";
			JSONObject resJO = new JSONObject();
			JSONArray entries = new JSONArray();
			
			for(JournalArticle article : filtered) {
				String content = article.getContentByLocale(themeDisplay.getLanguageId());;
			    Document document = SAXReaderUtil.read(content);
			    JSONObject dataJO = new JSONObject();
//			    if(reportName.equalsIgnoreCase("successStoriesReport"))
//			    	dataJO =  getSuccessStoryJOData(document, themeDisplay, renderRequest, article, assetCateg);
//			    if(reportName.equalsIgnoreCase("OngoingProjectReport"))
//			    	dataJO =  getOnGoingProjectJOData(document, themeDisplay, renderRequest, article, assetCateg);
//			    if(reportName.equalsIgnoreCase("InnovationProgramsReport"))
//			    	dataJO =  getInnovationProgramsJOData(document, themeDisplay, renderRequest, article, assetCateg);
//			    if(reportName.equalsIgnoreCase("InnovationChallengesReport"))
//			    	dataJO =  getInnovationChallengesJOData(document, themeDisplay, renderRequest, article, assetCateg);
//			    if(reportName.equalsIgnoreCase("OpportunitiesForIndustriesReport"))
//			    	dataJO =  getUserRelatedOpportunitiesForIndustriesReportJOData(document, themeDisplay, renderRequest, article, assetCateg);
			    if(reportName.equalsIgnoreCase("NewsReport")) {
			    	String title = helper.getStringFromDoc(document, NewsTitle);
			    	if(!helper.isEmpty(title))
			    		dataJO =  getNewsReportJOData(document, themeDisplay, renderRequest, article, assetCateg);
			    }
			    if(reportName.equalsIgnoreCase("EventsReport")) {
			    	String title = helper.getStringFromDoc(document, EventName);
			    	if(!helper.isEmpty(title))
			    		dataJO =  getEventsReportJOData(document, themeDisplay, renderRequest, article, assetCateg);
			    }
			    if(reportName.equalsIgnoreCase("PublicationsReport")) {
			    	String title = helper.getStringFromDoc(document, PublicationTitle);
			    	if(!helper.isEmpty(title))
			    		dataJO =  getPublicationsReportJOData(document, themeDisplay, renderRequest, article, assetCateg, reportName);
			    }
			    if(dataJO.has("csVData"))
			    	csVData += dataJO.getString("csVData");
			    
			    if(!dataJO.isEmpty())
			    	entries.put(dataJO.getJSONObject("data"));
			}
			
			if(entries.length() > 0) {
				if(!helper.isEmpty(sortField))
					entries = sortReport(entries, sortField, sort);
				
				resJO.put("entries", entries);
				resJO.put("entryTotal", entries.length()+1);
				csvDataALL += csVData;
				res.put(resJO);
			}
		}
		
		if(reportName.equalsIgnoreCase("PublicationsTypesReport") || reportName.equalsIgnoreCase("PublicationsReport")) {
			com.liferay.dynamic.data.mapping.model.DDMStructure structure = DDMStructureLocalServiceUtil.getStructures().stream()
					.filter(st -> st.getName("en_US").equalsIgnoreCase("publications")).findFirst().orElse(null);
			JSONArray pubTypesList = getPublicationTypesListData(themeDisplay, renderRequest, structure);
			
			for(int i=0; i< pubTypesList.length();i++) {
				String csVData ="";
				JSONObject typeJO = new JSONObject();
				JSONArray entries = new JSONArray();
				
				JSONObject pubTypeJO = pubTypesList.getJSONObject(i); 
				String typeName = pubTypeJO.getString("value");

				typeJO.put("pubType", typeName);
				
				for(JournalArticle article : filtered) {
					String content = article.getContentByLocale(themeDisplay.getLanguageId());;
				    Document document = SAXReaderUtil.read(content);
					String articlePubType =  helper.getStringFromDoc(document, PublicationType);
					
					JSONObject typeData = new JSONObject();
					
					if(articlePubType.equalsIgnoreCase(pubTypeJO.getString("id"))) {
						String title = helper.getStringFromDoc(document, PublicationTitle);
				    	if(!helper.isEmpty(title)) {
							typeData = getPublicationsReportJOData(document, themeDisplay, renderRequest, article, assetCateg, reportName);
					    	csVData += typeData.getString("csVData");
				    	}
					}
					
					if(!typeData.isEmpty())
				    	entries.put(typeData.getJSONObject("data"));
				}
				if(entries.length() > 0) {
					if(!helper.isEmpty(sortField))
						entries = sortReport(entries, sortField, sort);
					
					typeJO.put("entries", entries);
					typeJO.put("entryTotal", entries.length()+1);
					csvDataALL += csVData;
					res.put(typeJO);
				}
			}
		}
		
		//DRAW CSV
		if(reportName.equalsIgnoreCase("userthreadsReport") ||
				reportName.equalsIgnoreCase("userActivityonThreadsReport"))
			csvString += "User,Threads,Category,Views,Likes,Dislikes,Comments";
		
		if(reportName.equalsIgnoreCase("UserRelatedSuccessStoriesReport"))
			csvString += "User,Title,Type";
		
		if(reportName.equalsIgnoreCase("successStoriesReport"))
			csvString += "Title,Type,Creator";
		
		if(reportName.equalsIgnoreCase("OngoingProjectReport"))
			csvString += "Title,Stage,Category,Creator,Comments Total";
		
		if(reportName.equalsIgnoreCase("UserRelatedOngoingProjectsReport"))
			csvString += "User,Title,Stage,Category,Creator,Comments Total";
		
		if(reportName.equalsIgnoreCase("InnovationProgramsReport"))
			csvString += "Title,Type,Stage,Start Date,End Date,Creator";
		
		if(reportName.equalsIgnoreCase("UserRelatedInnovationProgramsReport"))
			csvString += "User,Title,Type,Stage,Start Date,End Date,Creator";
		
		if(reportName.equalsIgnoreCase("InnovationChallengesReport"))
			csvString += "Title,Type,Start Date,End Date,Creator,Role";
		
		if(reportName.equalsIgnoreCase("UserRelatedInnovationChallengesReport"))
			csvString += "User,Title,Type,Start Date,End Date,Creator,Role";
		
		if(reportName.equalsIgnoreCase("OpportunitiesForIndustriesReport"))
			csvString += "Title,Type,Start Date,End Date,Creator";
		
		if(reportName.equalsIgnoreCase("UserRelatedOpportunitiesForIndustriesReport"))
			csvString += "User,Title,Type,Start Date,End Date,Creator";
		
		if(reportName.equalsIgnoreCase("EventsReport"))
			csvString += "Title,Type,attendanceName,Host,roleName,eventAttendeesTotal,eventAttendeesTotal";
		
		if(reportName.equalsIgnoreCase("NewsReport"))
			csvString += "Title,Type,Creator,roleName,newsCommentsTotal,creationDate";
		
		if(reportName.equalsIgnoreCase("PublicationsTypesReport"))
			csvString += "Type,Title,CommentsTotal,MostCommentedRole";
		
		if(reportName.equalsIgnoreCase("PublicationsReport"))
			csvString += "Title,Type,CommentsTotal,MostCommentedRole";
		
		csvString += "___newLine___";
		
		csvString += csvDataALL;
		
		renderRequest.setAttribute("entries", helper.toList(res)); 
		renderRequest.setAttribute("csvString", csvString.toString()); 
	}

	private JSONObject getPublicationsReportJOData(Document document, ThemeDisplay themeDisplay,
			RenderRequest renderRequest, JournalArticle article, AssetCategory assetCateg, String reportName) throws DocumentException, PortalException, SQLException {
		JSONObject res = new JSONObject();
		
		String title = helper.getStringFromDoc(document, PublicationTitle);
		String type = helper.getStringFromDoc(document, PublicationType);
		String typeName = getValueName(themeDisplay, renderRequest, article.getDDMStructure(), PublicationType, type);
		
		User creator = UserLocalServiceUtil.getUser(article.getUserId());
		String roleName = UserTypes.getUserTypeName(String.valueOf(creator.getRoleIds()[0]));
		roleName =  roleName == null ? "Admin" : roleName;
		
		String publicationCommentsTotal = db_helper.getCommentsTotal(article.getResourcePrimKey());
		String roleMostAttendees = db_helper.getMostRoleName("Comments", article.getResourcePrimKey(), "article_id");
				
		JSONObject userThreadsJO = new JSONObject();
		userThreadsJO.put("publicationTitle", title);
		userThreadsJO.put("publicationType", typeName);
		userThreadsJO.put("publicationCommentsTotal", publicationCommentsTotal);
		userThreadsJO.put("roleMostAttendees", roleMostAttendees);
		userThreadsJO.put("url", "/resources-detail?publication&articleId="+ article.getResourcePrimKey());
		
		String csVData = "";
		
		if(reportName.equalsIgnoreCase("PublicationsTypesReport"))
			csVData = typeName +"," +title+ "," +publicationCommentsTotal +"," +roleMostAttendees;
		if(reportName.equalsIgnoreCase("PublicationsReport"))
			csVData = title +"," +typeName + "," +publicationCommentsTotal +"," +roleMostAttendees;

		csVData += "___newLine___";
		
		res.put("data", userThreadsJO);
		res.put("csVData", csVData);
		
		return res;
	}

	private JSONObject getEventsReportJOData(Document document, ThemeDisplay themeDisplay, RenderRequest renderRequest,
			JournalArticle article, AssetCategory assetCateg) throws DocumentException, PortalException, JSONException, SQLException {
		JSONObject res = new JSONObject();
		
		String title = helper.getStringFromDoc(document, EventName);
		String type = helper.getStringFromDoc(document, EventType);
		String typeName = getValueName(themeDisplay, renderRequest, article.getDDMStructure(), EventType, type);
		
		String attendance = helper.getStringFromDoc(document, Attendance);
		String attendanceName = getValueName(themeDisplay, renderRequest, article.getDDMStructure(), Attendance, attendance);
		
		User creator = UserLocalServiceUtil.getUser(article.getUserId());
		String roleName = UserTypes.getUserTypeName(String.valueOf(creator.getRoleIds()[0]));
		roleName =  roleName == null ? "Admin" : roleName;
		
		String eventAttendeesTotal = db_helper.getAttendeesTotal(article.getResourcePrimKey());
		String roleMostAttendees = db_helper.getMostRoleName("EventAttendance", article.getResourcePrimKey(), "eventId");
				
		JSONObject userThreadsJO = new JSONObject();
		userThreadsJO.put("eventTitle", title);
		userThreadsJO.put("eventType", typeName);
		userThreadsJO.put("eventAttendance", attendanceName);
		userThreadsJO.put("eventHost", creator.getFullName());
		userThreadsJO.put("eventHostRole", roleName);
		userThreadsJO.put("eventAttendeesTotal", eventAttendeesTotal);
		userThreadsJO.put("roleMostAttendees", roleMostAttendees);
		userThreadsJO.put("url", "/event-detail?event&articleId="+ article.getResourcePrimKey());
		
		String csVData = title +"," +typeName + "," +attendanceName +"," +creator.getFullName()+","+roleName+","+eventAttendeesTotal+","+roleMostAttendees+",";
		csVData += "___newLine___";
		
		res.put("data", userThreadsJO);
		res.put("csVData", csVData);
		
		return res;
	}

	private JSONObject getNewsReportJOData(Document document, ThemeDisplay themeDisplay, RenderRequest renderRequest,
			JournalArticle article, AssetCategory assetCateg) throws DocumentException, PortalException, JSONException, SQLException {
		JSONObject res = new JSONObject();
		
		String title = helper.getStringFromDoc(document, NewsTitle);
		String type = helper.getStringFromDoc(document, NewsType);
		String typeName = getValueName(themeDisplay, renderRequest, article.getDDMStructure(), NewsType, type);
		
		User creator = UserLocalServiceUtil.getUser(article.getUserId());
		String roleName = UserTypes.getUserTypeName(String.valueOf(creator.getRoleIds()[0]));
		roleName =  roleName == null ? "Admin" : roleName;
				
		String newsCommentsTotal = db_helper.getCommentsTotal(article.getResourcePrimKey());
		String roleMostAttendees = db_helper.getMostRoleName("Comments", article.getResourcePrimKey(), "article_id");
		
		JSONObject userThreadsJO = new JSONObject();
		userThreadsJO.put("newsTtile", title);
		userThreadsJO.put("newsType", typeName);
		userThreadsJO.put("newsCreator", article.getUserName());
		userThreadsJO.put("newsCreatorRole", roleName);
		userThreadsJO.put("newsCommentsTotal", newsCommentsTotal);
		userThreadsJO.put("newsCreationDate", new SimpleDateFormat("MMM dd, yyyy hh:mm").format(article.getCreateDate()));
		userThreadsJO.put("roleMostAttendees", roleMostAttendees);
		userThreadsJO.put("url", "/news-detail?news&articleId=" + article.getResourcePrimKey());
		
		String csVData = title +"," +typeName + "," +article.getUserName() +"," +roleName+","+newsCommentsTotal+","+article.getCreateDate()+",";
		csVData += "___newLine___";
		
		res.put("data", userThreadsJO);
		res.put("csVData", csVData);
		
		return res;
	}

	private JSONObject getUserRelatedOpportunitiesForIndustriesReportJOData(Document document,
			ThemeDisplay themeDisplay, RenderRequest renderRequest, JournalArticle article, AssetCategory assetCateg) throws DocumentException, PortalException, ParseException {
		JSONObject res = new JSONObject();
		
		String title = helper.getStringFromDoc(document, OppIndustTitle);
		String type = helper.getStringFromDoc(document, OppIndustType);
		String typeName = getValueName(themeDisplay, renderRequest, article.getDDMStructure(), OppIndustType, type);
		
		String startDate = helper.getStringFromDoc(document, OppIndustStartDate); 
		String endDate = helper.getStringFromDoc(document, OppIndustEndDate); 
		
		Date startDate_d = new SimpleDateFormat("yyyy-mm-dd").parse(startDate);
		Date endDate_d = new SimpleDateFormat("yyyy-mm-dd").parse(endDate);
				
		JSONObject userThreadsJO = new JSONObject();
		userThreadsJO.put("oppIndustTitle", title);
		userThreadsJO.put("oppIndustType", typeName);
		userThreadsJO.put("oppIndustStartDate", new SimpleDateFormat("MMM dd, yyyy").format(startDate_d));
		userThreadsJO.put("oppIndustEndDate", new SimpleDateFormat("MMM dd, yyyy").format(endDate_d));
		userThreadsJO.put("oppIndustCreator", article.getUserName());
		userThreadsJO.put("url", "/program-detail?categName=Opportunities for Industries&groupId="
				+ themeDisplay.getLayout().getGroupId() +"&articleId="+ article.getResourcePrimKey());
		
		String csVData = title +"," +typeName + "," +startDate +"," +endDate+","+article.getUserName()+",";
		csVData += "___newLine___";
		
		res.put("data", userThreadsJO);
		res.put("csVData", csVData);
		
		return res;
	}

	private JSONObject getInnovationChallengesJOData(Document document, ThemeDisplay themeDisplay,
			RenderRequest renderRequest, JournalArticle article, AssetCategory assetCateg) throws DocumentException, PortalException, ParseException {
		JSONObject res = new JSONObject();
		
		String title = helper.getStringFromDoc(document, InnovationChallengeTitle);
		String type = helper.getStringFromDoc(document, InnovationChallengeChallengeType);
		String typeName = getValueName(themeDisplay, renderRequest, article.getDDMStructure(), InnovationChallengeChallengeType, type);
		
		String startDate = helper.getStringFromDoc(document, InnovationChallengeStartDate); 
		String endDate = helper.getStringFromDoc(document, InnovationChallengeEndDate); 
		Date startDate_d = new SimpleDateFormat("yyyy-mm-dd").parse(startDate);
		Date endDate_d = new SimpleDateFormat("yyyy-mm-dd").parse(endDate);
				
		JSONObject userThreadsJO = new JSONObject();
		userThreadsJO .put("innovationChallengeTitle", title);
		userThreadsJO.put("innovationChallengeType", typeName);
		userThreadsJO.put("innovationChallengeStartDate", new SimpleDateFormat("MMM dd, yyyy").format(startDate_d));
		userThreadsJO.put("innovationChallengeEndDate", new SimpleDateFormat("MMM dd, yyyy").format(endDate_d));
		userThreadsJO.put("innovationChallengeCreator", article.getUserName());
		userThreadsJO.put("url", "/program-detail?categName=Innovation Challenges&groupId="
				+ themeDisplay.getLayout().getGroupId() +"&articleId="+ article.getResourcePrimKey());
		
		User creator = UserLocalServiceUtil.getUser(article.getUserId());
		String roleName = UserTypes.getUserTypeName(String.valueOf(creator.getRoleIds()[0]));
		roleName =  roleName == null ? "Admin" : roleName;
		userThreadsJO.put("innovationChallengeCreatorRole", roleName);
		
		String csVData = title +"," +typeName + "," +startDate +"," +endDate+","+article.getUserName()+","+roleName+",";
		csVData += "___newLine___";
		
		res.put("data", userThreadsJO);
		res.put("csVData", csVData);
		
		return res;
	}

	private JSONObject getInnovationProgramsJOData(Document document, ThemeDisplay themeDisplay,
			RenderRequest renderRequest, JournalArticle article, AssetCategory assetCateg) throws PortalException, DocumentException, ParseException {
		JSONObject res = new JSONObject();
		
		String title = helper.getStringFromDoc(document, InnovationProgramTitle);
		String stage = helper.getStringFromDoc(document, InnovationProgramStage);
		String stageName = getValueName(themeDisplay, renderRequest, article.getDDMStructure(), InnovationProgramStage, stage);
		String type = helper.getStringFromDoc(document, InnovationProgramActivityType);
		String typeName = getValueName(themeDisplay, renderRequest, article.getDDMStructure(), InnovationProgramActivityType, type);
		
		String startDate = helper.getStringFromDoc(document, InnovationProgramStartDate); 
		String endDate = helper.getStringFromDoc(document, InnovationProgramEndDate);
		
		Date startDate_d = new SimpleDateFormat("yyyy-mm-dd").parse(startDate);
		Date endDate_d = new SimpleDateFormat("yyyy-mm-dd").parse(endDate);
				
		JSONObject userThreadsJO = new JSONObject();
		userThreadsJO.put("innovationProgramTitle", title);
		userThreadsJO.put("innovationProgramType", typeName);
		userThreadsJO.put("innovationProgramStage", stageName);
		userThreadsJO.put("innovationProgramStartDate", new SimpleDateFormat("MMM dd, yyyy hh:mm").format(startDate_d));
		userThreadsJO.put("innovationProgramEndDate", new SimpleDateFormat("MMM dd, yyyy hh:mm").format(endDate_d));
		userThreadsJO.put("innovationProgramCreator", article.getUserName());
		userThreadsJO.put("url", "/program-detail?categName=Innovation Programs&groupId="
				+ themeDisplay.getLayout().getGroupId() +"&articleId="+ article.getResourcePrimKey());
		
		String csVData = title +"," +typeName + ","+stageName + "," +startDate +"," +endDate+"," +article.getUserName()+",";
		csVData += "___newLine___";
		
		res.put("data", userThreadsJO);
		res.put("csVData", csVData);
		
		return res;
	}

	private JSONObject getThreadsJOData(Document document, ThemeDisplay themeDisplay,
			RenderRequest renderRequest, JournalArticle thread, AssetCategory assetCateg) throws SQLException, PortalException, DocumentException {
		JSONObject res = new JSONObject();
		
		String title = helper.getStringFromDoc(document, ThreadTitle);
		String type = helper.getStringFromDoc(document, ThreadType);
		
		String typeName = getValueName(themeDisplay, renderRequest, thread.getDDMStructure(), ThreadType, type);
				
		JSONObject forumDetailsCount = db_helper.getForumDetailsCount(thread.getArticleId(), "Forum_Details", "forum_Id");
		
		JSONObject userThreadsJO = new JSONObject();
		userThreadsJO .put("threadTitle", title);
		userThreadsJO.put("threadType", typeName);
		userThreadsJO.put("viewcount", forumDetailsCount.get("viewcount"));
		userThreadsJO.put("likecount", forumDetailsCount.get("likecount"));
		userThreadsJO.put("dislikecount", forumDetailsCount.get("dislikecount"));
		userThreadsJO.put("commentscount", forumDetailsCount.get("commentscount"));
		userThreadsJO.put("threadUrl", "/policy-forum?viewForum&p_r_p_categoryId=" +  assetCateg.getCategoryId() 
			+ "&folderId="+ thread.getFolderId() + "&ddmStructureKey="+ thread.getDDMStructureKey() +"&ddmTemplateKey=&forumId="+ thread.getArticleId()
			+"&threadType=" +type);

		String csVData = title +"," +typeName + "," + forumDetailsCount.get("viewcount") + "," + forumDetailsCount.get("likecount") + ","
				+ forumDetailsCount.get("dislikecount") + "," + forumDetailsCount.get("commentscount") + "," ;
		csVData += "___newLine___";
		
		res.put("data", userThreadsJO);
		res.put("csVData", csVData);
		
		return res;
	}
	
	private JSONObject getSuccessStoryJOData(Document document, ThemeDisplay themeDisplay,
			RenderRequest renderRequest, JournalArticle story, AssetCategory assetCateg) throws SQLException, PortalException, DocumentException {
		JSONObject res = new JSONObject();
		
		String title = helper.getStringFromDoc(document, SuccessStoryTitle);
		String type = helper.getStringFromDoc(document, SuccessStoryType);
		String typeName = getValueName(themeDisplay, renderRequest, story.getDDMStructure(), SuccessStoryType, type);
				
		JSONObject userThreadsJO = new JSONObject();
		userThreadsJO .put("successStoryTitle", title);
		userThreadsJO.put("successStoryType", typeName);
		userThreadsJO.put("creator", story.getUserName());
		userThreadsJO.put("successStoryURL", "/story-detail?groupId="+ themeDisplay.getLayout().getGroupId() 
				+"&articleId=" + story.getResourcePrimKey() + "&categName=" +SuccessStoryCategoryName);
		
		String csVData = title +"," +typeName + "," +story.getUserName() +",";
		csVData += "___newLine___";
		
		res.put("data", userThreadsJO);
		res.put("csVData", csVData);
		
		return res;
	}
	
	private JSONObject getOnGoingProjectJOData(Document document, ThemeDisplay themeDisplay,
			RenderRequest renderRequest, JournalArticle article, AssetCategory assetCateg) throws SQLException, PortalException, DocumentException {
		JSONObject res = new JSONObject();
		
		String title = helper.getStringFromDoc(document, OnGoiongTitle);
		String stage = helper.getStringFromDoc(document, OnGoingStage);
		String categ = helper.getStringFromDoc(document, OnGoingCateg);
				
		String stageName = getValueName(themeDisplay, renderRequest, article.getDDMStructure(), OnGoingStage, stage);
		String catgeName = getValueName(themeDisplay, renderRequest, article.getDDMStructure(), OnGoingCateg, categ);
		String commentsTotal = db_helper.getCommentsTotal(article.getResourcePrimKey());
				
		JSONObject userThreadsJO = new JSONObject();
		userThreadsJO .put("onGoiongTitle", title);
		userThreadsJO.put("onGoingStage", stageName);
		userThreadsJO.put("onGoingCateg", catgeName);
		userThreadsJO.put("creator", article.getUserName());
		userThreadsJO.put("commentsTotal", commentsTotal);
		userThreadsJO.put("url", "/program-detail-comments?categName=Ongoing Projects&groupId="
		+ themeDisplay.getLayout().getGroupId() +"&articleId="+ article.getResourcePrimKey());
		
		String csVData = title +"," +stageName + "," + catgeName+","+article.getUserName() +","+commentsTotal+",";
		csVData += "___newLine___";
		
		res.put("data", userThreadsJO);
		res.put("csVData", csVData);
		
		return res;
	}

	public static String getValueName(ThemeDisplay themeDisplay, RenderRequest renderRequest, DDMStructure struct, String fieldName, String value) throws DocumentException, PortalException {
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
	
	public JSONArray getPublicationTypesListData(ThemeDisplay themeDisplay, RenderRequest renderRequest, 
			com.liferay.dynamic.data.mapping.model.DDMStructure struct) throws DocumentException, PortalException {
		  
		JSONObject jo = new JSONObject(struct.getDefinition());
		JSONArray ja = new JSONArray(jo.get("fields").toString());

		for (int i = 0; i < ja.length(); i++) {
			JSONObject joa = ja.getJSONObject(i);
			if (joa.get("type").equals("select")) {
				if (joa.get("name").equals(PublicationType)) {
					JSONArray joOptions = new JSONArray(joa.get("options").toString());
					JSONArray jaRes = new JSONArray();
					for (int j = 0; j < joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j);
						JSONObject res = new JSONObject(); 
						String val = new JSONObject(option.get("label").toString()).get("en_US").toString();
						if(!val.equalsIgnoreCase("none")) {
							res.put("id", option.getString("value"));
							res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
							jaRes.put(res);
						}
					}
					 return jaRes;
				}	
			}
		}
		return new JSONArray();
	}

	private List<JournalArticle> getFilteredArticlesByCreatedDate(List<JournalArticle> filtered, JSONArray start, JSONArray end) {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>(); 
		LocalDate startDate = LocalDate.parse(start.getString(0));
		LocalDate endDate = LocalDate.parse(end.getString(0));
		for(JournalArticle article : filtered) {
			LocalDate cdate = article.getCreateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			boolean addArticle = false;
			if( cdate.isBefore(endDate) && cdate.isAfter(startDate) ) addArticle = true;
			if( addArticle ) {
				returnedArticles.add(article);
				break;
			}
		}
		return returnedArticles;
	}
	
	public static JSONArray getFilteredCourses(ThemeDisplay themeDisplay,JSONArray fetchedArticles,
			JSONArray filter, String fieldName) throws DocumentException {
		JSONArray returnedArticles = new JSONArray(); 
		
		for (int j = 0; j < fetchedArticles.length(); j++) {
			JSONObject course = fetchedArticles.getJSONObject(j);
			JSONArray attendees = course.getJSONArray("attendees");
			for(int k = 0; k < attendees.length(); k++) {
				JSONObject att = attendees.getJSONObject(k);
				for(int i=0; i< filter.length();i++) {
					String val = filter.getString(i);
					String fieldValue ="";
					if(fieldName.equalsIgnoreCase("country") && att.has("attCountryID")) 
						fieldValue = String.valueOf(att.getInt("attCountryID"));
					
					if(fieldName.equalsIgnoreCase("profession") && att.has("attProfessionID")) 
						fieldValue = String.valueOf(att.getInt("attProfessionID"));
					
					if(fieldName.equalsIgnoreCase("roles") && att.has("attRoleID")) 
						fieldValue = String.valueOf(att.getInt("attRoleID"));
					
					if(fieldName.equalsIgnoreCase("gender") && att.has("attGenderID")) 
						fieldValue =att.getString("attGenderID");
					
					if( fieldValue.equalsIgnoreCase(val) ) {
						returnedArticles.put(course);
						break;
					}
				}
			}
		}
		return returnedArticles;
	}
	
	public static List<JournalArticle> getFilteredArticles(ThemeDisplay themeDisplay, List<JournalArticle> fetchedArticles,
			JSONArray filter, String fieldName) throws DocumentException {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>(); 
		
		for (JournalArticle article : fetchedArticles) {
			String content = article.getContentByLocale(themeDisplay.getLanguageId());
			Document document = SAXReaderUtil.read(content);
			
			String fieldValue = helper.getStringFromDoc(document, fieldName);
			
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
	
	public void getFilterData(ThemeDisplay themeDisplay, RenderRequest renderRequest, DDMStructure struct) throws DocumentException, PortalException {
		String structName = struct.getName("en_US");
		JSONObject jo = new JSONObject(struct.getDefinition());
		JSONArray ja = new JSONArray(jo.get("fields").toString());
		
		List<String> filterFields = new ArrayList<String>();
		if(structName.equalsIgnoreCase("opportunities-industries")) {
			filterFields = oppFilter;
		}else if(structName.equalsIgnoreCase("basic-programs")) {
			filterFields = onGoigProjectFilter;
		}else if(structName.equalsIgnoreCase("innovation-programs")) {
			filterFields = InnovProgFilter;
		}else if(structName.equalsIgnoreCase("innovation-challenges")) {
			filterFields = InnovChallengeFilter;
		}else if(structName.equalsIgnoreCase("thread")) {
			filterFields = threadFilter;
		} else if(structName.equalsIgnoreCase("success-story")) {
			filterFields = stroryFilter;
		} else if(structName.equalsIgnoreCase("news")) {
			filterFields = newsFilter;
		} else if(structName.equalsIgnoreCase("event")) {
			filterFields = eventFilter;
		} else if(structName.equalsIgnoreCase("publications")) {
			filterFields = publicationFilter;
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
					if(structName.equalsIgnoreCase("opportunities-industries")) {
						if (joa.getString("name").equalsIgnoreCase(OppIndustType))
							renderRequest.setAttribute("OppIndustType", helper.toList(jaRes));
					}else if(structName.equalsIgnoreCase("basic-programs")) {
						if (joa.getString("name").equalsIgnoreCase(OnGoingCateg))
							renderRequest.setAttribute("OnGoingCateg", helper.toList(jaRes));
						if (joa.getString("name").equalsIgnoreCase(OnGoingStage))
							renderRequest.setAttribute("OnGoingStage", helper.toList(jaRes));
					}else if(structName.equalsIgnoreCase("innovation-programs")) {
						if (joa.getString("name").equalsIgnoreCase(InnovationProgramActivityType))
							renderRequest.setAttribute("InnovationProgramActivityType", helper.toList(jaRes));
						if (joa.getString("name").equalsIgnoreCase(InnovationProgramStage))
							renderRequest.setAttribute("InnovationProgramStage", helper.toList(jaRes));
					}else if(structName.equalsIgnoreCase("innovation-challenges")) {
						if (joa.getString("name").equalsIgnoreCase(InnovationChallengeChallengeType))
							renderRequest.setAttribute("InnovationChallengeChallengeType", helper.toList(jaRes));
					}else if(structName.equalsIgnoreCase("success-story")) {
						if (joa.getString("name").equalsIgnoreCase(SuccessStoryType))
							renderRequest.setAttribute("SuccessStoryType", helper.toList(jaRes));
					}else if(structName.equalsIgnoreCase("thread")) {
						if (joa.getString("name").equalsIgnoreCase(ThreadType))
							renderRequest.setAttribute("ThreadType", helper.toList(jaRes));
					}else if(structName.equalsIgnoreCase("news")) {
						if (joa.getString("name").equalsIgnoreCase(NewsType))
							renderRequest.setAttribute("NewsType", helper.toList(jaRes));
					}else if(structName.equalsIgnoreCase("event")) {
						if (joa.getString("name").equalsIgnoreCase(EventType))
							renderRequest.setAttribute("EventType", helper.toList(jaRes));
						if (joa.getString("name").equalsIgnoreCase(Attendance))
							renderRequest.setAttribute("Attendance", helper.toList(jaRes));
					}else if(structName.equalsIgnoreCase("publications")) {
						if (joa.getString("name").equalsIgnoreCase(PublicationType))
							renderRequest.setAttribute("PublicationType", helper.toList(jaRes));
					}
				} 	
			}
		}
	}
	
	public JSONArray sortReport(JSONArray jaRes, String sortField, String sort) {
		JSONArray sortedJsonArray = jaRes;
		List<JSONObject> jsonValues = new ArrayList<JSONObject>();
	    for (int i = 0; i < jaRes.length(); i++) {
	        jsonValues.add(jaRes.getJSONObject(i));
	    }
		 Collections.sort(jsonValues, new Comparator<JSONObject>() {
	         private final String KEY_NAME = sortField;
	         @Override
	         public int compare(JSONObject a, JSONObject b) {
	            try {
	               if(sort.equalsIgnoreCase("asc"))
	            	   return (Integer.valueOf(a.getString(KEY_NAME)).compareTo(Integer.valueOf(b.getString(KEY_NAME))));
	               if(sort.equalsIgnoreCase("desc"))
	            	   return  -(Integer.valueOf(a.getString(KEY_NAME)).compareTo(Integer.valueOf(b.getString(KEY_NAME))));
	            } catch(JSONException e) {
	               e.printStackTrace();
	            }
	            return 0;
	         } 
	      });
		sortedJsonArray = new JSONArray(jsonValues);
		return sortedJsonArray;
	}
	
	private JSONArray getIAMFilter(RenderRequest renderRequest, ThemeDisplay themeDisplay, String name) {
		JSONArray rs = new JSONArray();
		List<String> jsonDataNames = Arrays.asList(name);  
		
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
					rs = jaRes;
					renderRequest.setAttribute(data, helper.toList(jaRes));
				}
			}
		}
		return rs;
	}
}