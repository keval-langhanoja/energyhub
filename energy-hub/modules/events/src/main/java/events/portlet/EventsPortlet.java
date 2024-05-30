package events.portlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
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

import events.constants.EventsPortletKeys;
import events.helper.UserTypes;
import events.helper.db_helper;
import events.helper.helper;
import events.helper.util;

/**
 * @author vyo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Events",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.name=" + EventsPortletKeys.EVENTS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class EventsPortlet extends MVCPortlet {
	private final  Configuration _portletConfiguration = ConfigurationFactoryUtil.getConfiguration(PortalClassLoaderUtil.getClassLoader(), "portlet");

	private String EventCategoryName = "Events", EventStructureName = "event";
	
	private long EventFolderId;
	
	private String EventName = "Text63667232", EventDescription = "RichText78070238", Attendance = "SelectFromList53435675",
			MeetingLink = "Text27240515", EventImage = "Image34239737", EventType = "SelectFromList76720910", 
			OtherLinks ="Text98087530", StartDate = "Text89858799", EndDate = "Text74493498", Speakers = "Text55833185";
	
	private String[] EventNames = {"EventName","EventDescription", "Attendance", "MeetingLink",
				"EventImage", "EventType", "OtherLinks","StartDate","EndDate", "Speakers"};
	
	private String Webinar = "Option88436365", Conference = "Option68474827", CallforApplications = "Option06529877",
			Workshop = "Option29456229", OtherType = "Option46598994";
	
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY); 
			HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
			
			String ipadd =  PortalUtil.getHttpServletRequest(renderRequest).getRemoteAddr();
			if(!ipadd.equalsIgnoreCase("51.178.45.207")) { //Offline
				EventFolderId = 40842;
			}else if(!ipadd.equalsIgnoreCase("51.178.45.174")) { //Online
				EventFolderId = 42695;
			}else { //Local
				EventFolderId = 88031;
			}
			
			db_helper.createEventAttendanceTable();
			
			String eventId = httpReq.getParameter("articleId");
			int pageNo = Integer.valueOf(helper.ifEmpty(httpReq.getParameter("pageNo"), "0"));
			int pageSize = Integer.valueOf(helper.ifEmpty(httpReq.getParameter("pageSize"), "9"));
			JSONObject filter = new JSONObject(helper.ifEmpty(httpReq.getParameter("filter"), "{}"));
			
			com.liferay.dynamic.data.mapping.model.DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
					.filter(st -> st.getName("en_US").equalsIgnoreCase(EventStructureName)).findFirst().orElse(null);	
			
			AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
						.filter(categ -> categ.getName().equalsIgnoreCase(EventCategoryName)).findFirst().orElse(null);
			
			String token = util.getToken(_portletConfiguration);
			User user = themeDisplay.getUser();
			String userString = util.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), user.getEmailAddress());
			
			if(new JSONObject(userString).has("roleId")) {
				int userRole = new JSONObject(userString).getInt("userApplicationRoleId");
				if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.InvestorUser.name()))
					|| userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.AcademicUser.name()))
					|| userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.PolicyMakerUser.name()))){
					renderRequest.setAttribute("addEvent", true);
				} else renderRequest.setAttribute("addEvent", false);
			}else if(themeDisplay.getUser().getRoles().contains(RoleLocalServiceUtil.getRole(37615))) {
				renderRequest.setAttribute("addEvent", true);  
			} else renderRequest.setAttribute("addEvent", false);
			
			List<JournalArticle> eventsList = new ArrayList<JournalArticle>();
			List<JournalArticle> similarTopics = new ArrayList<JournalArticle>();
			JSONArray jaRes = new JSONArray();
			AssetEntryQuery assetEntryQuery = new AssetEntryQuery(); 
			assetEntryQuery.setAllCategoryIds(new long[] { assetCategory.getCategoryId() });
			assetEntryQuery.setOrderByCol1("createDate");
			
			if(httpReq.getParameterMap().containsKey("p_r_p_categoryId") || httpReq.getParameterMap().containsKey("allEvents")) {
				if(httpReq.getParameterMap().containsKey("event")) {
					assetEntryQuery.setStart(0);
					assetEntryQuery.setEnd(6);
				}
				List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
				for (AssetEntry ae : assetEntryList) {
					JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
					JournalArticle journalArticle = JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey());
					if(journalArticle.getDDMStructureKey().equals(struct.getStructureKey()))
						eventsList.add(journalArticle);
				}
				
				//FILTER
				if(filter.has("type") && filter.getJSONArray("type").length()>0 ) {
					eventsList = getFilteredArticles(themeDisplay, eventsList, filter.getJSONArray("type"), EventType);
				}
				if(filter.has("attendance") && filter.getJSONArray("attendance").length()>0 ) {
					eventsList = getFilteredArticles(themeDisplay, eventsList, filter.getJSONArray("attendance"), Attendance);
				}
				if(filter.has("status") && filter.getJSONArray("status").length()>0 ) {
					eventsList = getFilteredArticlesbyStatus(eventsList, filter.getJSONArray("status"), themeDisplay);
				}
				
				//PAGINATION
				if(httpReq.getParameterMap().containsKey("p_r_p_categoryId")) {
					//FILTER get live or upcoming only
					JSONArray status = new JSONArray();
					status.put("isLive");
					status.put("isUpcoming");
					eventsList = getFilteredArticlesbyStatus(eventsList, status, themeDisplay);
					//PAGINATION
					eventsList = getPageLimit(eventsList, 0, 5);
				}else  eventsList = getPageLimit(eventsList, pageNo, pageSize);
				
			}else if(!helper.isEmpty(eventId)){
				JournalArticle event = JournalArticleLocalServiceUtil.fetchLatestArticle(Long.valueOf(eventId));
				if(event != null)
					eventsList.add(event);
				
				boolean isAttending = db_helper.GetUserAttendance(themeDisplay.getUserId(), event.getResourcePrimKey());
				renderRequest.setAttribute("isAttending", isAttending);
				
				String content = event.getContentByLocale(themeDisplay.getLanguageId());;
			    Document document = SAXReaderUtil.read(content);
				
			    String type = document.selectSingleNode("/root/dynamic-element[@name='"+ EventType +"']").hasContent()?
		        		document.selectSingleNode("/root/dynamic-element[@name='"+ EventType +"']/dynamic-content").getText() : "";
	        		
				assetEntryQuery.setStart(0);
				assetEntryQuery.setEnd(5);
				List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
				for (AssetEntry ae : assetEntryList) {
					JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
					JournalArticle journalArticle = JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey());
					similarTopics.add(journalArticle);
				}
				
				//FILTER
				if(filter.has("type") && filter.getJSONArray("type").length()>0 ) {
					similarTopics = getFilteredArticles(themeDisplay, similarTopics, new JSONArray().put(type), EventType);
				}
 
				JSONArray similarTopicsJA = getEventData(similarTopics, themeDisplay, renderRequest);
				renderRequest.setAttribute("similarTopics", helper.toList(similarTopicsJA)); 
			}  
			
			jaRes = getEventData(eventsList, themeDisplay, renderRequest);
			renderRequest.setAttribute("eventsList", helper.toList(jaRes));
			
			renderRequest.setAttribute("is_signed_in", themeDisplay.isSignedIn());
			if (httpReq.getParameterMap().containsKey("allEvents")) {
				getFilterData(themeDisplay, renderRequest, struct);
				int totalPages=  jaRes.isEmpty() ? 0 : (int) Math.floor(jaRes.length()/pageSize);
				
				renderRequest.setAttribute("totalPages", totalPages);
				renderRequest.setAttribute("pageNo", pageNo > totalPages ? 0 : pageNo);
				renderRequest.setAttribute("pageSize", pageSize);
				
				List<User> users =  UserLocalServiceUtil.getUsers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
				List<User> approvedUsers = new ArrayList<User>();
				for(User u : users) {
					u.setComments(u.getPortraitURL(themeDisplay));
					approvedUsers.add(u);
				}
				
				renderRequest.setAttribute("allUsers", approvedUsers);

				String eventsJson = getEventCalendarData(jaRes);
				renderRequest.setAttribute("eventsJson", eventsJson);
				
				include("/CardsView.jsp", renderRequest, renderResponse);
				super.render(renderRequest, renderResponse); 
			}else if (httpReq.getParameterMap().containsKey("event")) {
				include("/event.jsp", renderRequest, renderResponse);
				super.render(renderRequest, renderResponse); 
			}else {
				getFilterData(themeDisplay, renderRequest, struct);
				include("/view.jsp", renderRequest, renderResponse);
				super.render(renderRequest, renderResponse); 
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getEventCalendarData(JSONArray jaRes) {
		JSONArray res = new JSONArray();
		for(int i=0; i< jaRes.length();i++) {
			JSONObject jo = jaRes.getJSONObject(i);
			JSONObject joRes = new JSONObject();
			joRes.put("title", jo.get("eventName"));
			joRes.put("start", jo.get("startDate"));
			joRes.put("end", jo.get("endDate"));
			joRes.put("allDay", false);
			joRes.put("color", jo.get("color"));
			joRes.put("eventTypeName", jo.get("eventTypeName"));
			joRes.put("detailURL", jo.get("detailURL"));
			res.put(joRes);
		}
		
		return res.toString();
	} 

	private JSONArray getEventData(List<JournalArticle> eventsList, ThemeDisplay themeDisplay, RenderRequest renderRequest) {
		JSONArray jaRes = new JSONArray();
		String token = util.getToken(_portletConfiguration);
		
		try {
			for (JournalArticle event : eventsList) { 
			    String eventName = helper.getDFromContentXmlString (event, EventName, themeDisplay);
			    String description = helper.getDFromContentXmlString (event, EventDescription, themeDisplay);
			    String attendance = helper.getDFromContentXmlString (event, Attendance, themeDisplay);
			    String type = helper.getDFromContentXmlString (event, EventType, themeDisplay);
			    String meetingLink = helper.getDFromContentXmlString (event, MeetingLink, themeDisplay);
			    String otherLinks = helper.getDFromContentXmlString (event, OtherLinks, themeDisplay);
			    String speakers = helper.getDFromContentXmlString (event, Speakers, themeDisplay);
			    String startDate = helper.getData(event, StartDate, themeDisplay);
			    String endDate = helper.getData(event, EndDate, themeDisplay);
			    
			    User host = UserLocalServiceUtil.getUser(event.getUserId());
			    
			    String content = event.getContentByLocale(themeDisplay.getLanguageId());
			    Document document = SAXReaderUtil.read(content);
			    JSONObject coverImageJO = new JSONObject();
			    if(document.selectSingleNode("/root/dynamic-element[@name='"+ EventImage +"']") != null) {
			    	coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='"+ EventImage +"']").hasContent()?
			    			document.selectSingleNode("/root/dynamic-element[@name='"+ EventImage +"']/dynamic-content").getText() : "{}");
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
		        
	    		JSONObject res = new JSONObject();
				res.put("id", event.getId());
				res.put("resourcePrimKey", event.getResourcePrimKey());
				res.put("HostName", host.getFullName());
				String userString = util.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), host.getEmailAddress());
				JSONObject userJO = new JSONObject(userString);
				if(userJO.has("userApplicationRoleId")) {
					res.put("HostRole", getUserRoleName(userJO.getInt("userApplicationRoleId"))); 
					res.put("HostLink", "detail?userDetail&categName=" + UserTypes.getUserTypeName(String.valueOf(userJO.get("userApplicationRoleId"))) + 
							"&groupId=" + host.getGroupId() + "&articleId=" + host.getUserId());
				}
				res.put("userImageURL", host.getPortraitURL(themeDisplay));
				
				res.put("eventName", eventName);
				res.put("description", description);
				res.put("imageURL", imageUrl);
				
		        Date startDateDate = new SimpleDateFormat("dd/MM/yyyy HH:mm aa").parse(startDate);
				Date endDateDate = new SimpleDateFormat("dd/MM/yyyy HH:mm aa").parse(endDate);

				res.put("startDateDisplay", new SimpleDateFormat("dd MMM yyyy HH:mm aa").format(startDateDate));
				res.put("startDate", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(startDateDate));
				res.put("endDate", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(endDateDate));
				res.put("startTime", new SimpleDateFormat("HH:mm aa").format(startDateDate));
				res.put("endTime", new SimpleDateFormat("HH:mm aa").format(endDateDate));
				
				res.put("attendance", attendance);
				res.put("type", type);
				res.put("meetingLink", meetingLink);
				res.put("otherLinks", otherLinks);
				
				JSONArray speakersRes = new JSONArray();
				if(!helper.isEmpty(speakers)) {
					String[] speakersList = speakers.split(",");
					for(String spk : speakersList) {
						userString = util.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), spk);
						userJO = new JSONObject(userString);
						JSONObject userJORes = new JSONObject();
						String fname = "", lname = "";
						if(userJO.has("firstName")) fname = userJO.getString("firstName");
						if(userJO.has("lastName")) fname = userJO.getString("lastName");
						
						userJORes.put("FullName", fname + " " + lname);
						User speaker = UserLocalServiceUtil.fetchUserByEmailAddress(themeDisplay.getCompanyId(), spk);
						if(speaker !=null && userJO.has("userApplicationRoleId"))
							userJORes.put("profileURL", "detail?userDetail&categName=" 
								+ UserTypes.getUserTypeName(String.valueOf(userJO.get("userApplicationRoleId"))) +"&groupId="
								+ themeDisplay.getLayout().getGroupId()+"&articleId=" + speaker.getUserId());
						speakersRes.put(userJORes);
					}
				}
				res.put("speakers", helper.toList(speakersRes));
				
				
				JSONArray attendees = db_helper.getAttendanceList(String.valueOf(event.getResourcePrimKey()));
				JSONArray userJA = new JSONArray();
			    String AttendeesNames = "";
			    for(int i =0; i<attendees.length(); i++ ) {
			    	JSONObject jo = attendees.getJSONObject(i);
			    	User attendee = UserLocalServiceUtil.getUser(jo.getLong("userId"));
		    		if(attendee != null) {
		    			JSONObject uJO = new JSONObject();
		    			uJO.put("FullName", attendee.getFullName());
		    			uJO.put("PortraitUrl", attendee.getPortraitURL(themeDisplay));
		    			if(i < 3)
		    				AttendeesNames += attendee.getFirstName() + ", ";
		    			userJA.put(uJO);
		    		}
			    }
				res.put("Attendees", helper.toList(userJA));
				res.put("AttendeesNames", !helper.isEmpty(AttendeesNames) ? AttendeesNames.substring(0, AttendeesNames.length() - 2) : "");
				res.put("AttendeesTotal", userJA.length());
				
				res= getEventStatus(event, res, startDateDate, endDateDate);
				
				res.put("detailURL", "/event-detail?event&articleId="+ event.getResourcePrimKey());
				
				//Event Color
				if(type.equalsIgnoreCase(Webinar)) {
					res.put("color", "#093A5D");
					res.put("eventTypeName", "Webinar");
				}
				if(type.equalsIgnoreCase(Conference)) {
					res.put("color", "#88c08c");
					res.put("eventTypeName", "Conference");
				}
				if(type.equalsIgnoreCase(CallforApplications)) {
					res.put("color", "#feca30");
					res.put("eventTypeName", "CallforApplications");
				}
				if(type.equalsIgnoreCase(Workshop)) {
					res.put("color", "#23b6d052");
					res.put("eventTypeName", "Workshop");
				}
				if(type.equalsIgnoreCase(OtherType)) {
					res.put("color", "#d3744b");
					res.put("eventTypeName", "Other");
				}
				
				jaRes.put(res);  
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jaRes;
	}

	private JSONObject getEventStatus(JournalArticle event, JSONObject res, Date startDateDate, Date endDateDate) {
		boolean isLive = false;
		boolean isUpcoming = false;
		boolean ended = false;
		
		Instant instant = Instant.now();
		LocalDateTime now = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
		LocalDateTime start = LocalDateTime.ofInstant(startDateDate.toInstant(), ZoneId.systemDefault());
		LocalDateTime end = LocalDateTime.ofInstant(endDateDate.toInstant(), ZoneId.systemDefault());
		
		if(start.isBefore(now) && end.isBefore(now)) {
			ended = true;
			isLive = false;
			isUpcoming = false;
		}
		if(start.isAfter(now) && end.isAfter(now)) {
			ended = false;
			isLive = false;
			isUpcoming = true;
		}
		if(start.isBefore(now) && end.isAfter(now)) {
			isLive = true;
			isUpcoming = false;
			ended = false;
		}
		
		res.put("isLive", isLive);
		res.put("isUpcoming", isUpcoming);
		res.put("ended", ended);
		
		return res;
	}

	public void getFilterData(ThemeDisplay themeDisplay, RenderRequest renderRequest, 
			com.liferay.dynamic.data.mapping.model.DDMStructure struct) throws DocumentException, PortalException {
		  
		JSONObject jo = new JSONObject(struct.getDefinition());
		JSONArray ja = new JSONArray(jo.get("fields").toString());

		for (int i = 0; i < ja.length(); i++) {
			JSONObject joa = ja.getJSONObject(i);
			if (joa.get("type").equals("select")) {
				if (joa.get("name").equals(EventType)) {
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
					if(joa.get("name").equals(EventType))
						renderRequest.setAttribute("EventType", helper.toList(jaRes));
				} 	
				if (joa.get("name").equals(Attendance)) {
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
					if(joa.get("name").equals(Attendance))
						renderRequest.setAttribute("Attendance", helper.toList(jaRes));
				} 	
			}
		}
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
	
	private List<JournalArticle> getFilteredArticlesbyStatus(List<JournalArticle> fetchedArticles, JSONArray filter,
			ThemeDisplay themeDisplay) throws DocumentException, ParseException {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>(); 
		
		for (JournalArticle article : fetchedArticles) {
			String startDate = helper.getData(article, StartDate, themeDisplay);
		    String endDate = helper.getData(article, EndDate, themeDisplay);
			Date startDateDate = new SimpleDateFormat("dd/MM/yyyy HH:mm aa").parse(startDate);
			Date endDateDate = new SimpleDateFormat("dd/MM/yyyy HH:mm aa").parse(endDate);
			
			JSONObject status = new JSONObject();
			status = getEventStatus(article, new JSONObject(), startDateDate, endDateDate);
				
			for(int i=0; i< filter.length();i++) {
				String val = filter.getString(i);
				if( status.getBoolean(val) ) {
					returnedArticles.add(article);
					break;
				}
			}
		}
		return returnedArticles;
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

	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) 
				throws IOException, PortletException {
			PrintWriter out = resourceResponse.getWriter();
			String key = ParamUtil.getString(resourceRequest, "status");
			resourceResponse.setContentType("text/html");
			String retVal = "", eventId = "";
			try {
				eventId = ParamUtil.getString(resourceRequest, "eventId");
				ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
				User currentUser = themeDisplay.getUser();
				if(key.equalsIgnoreCase("yes")) {
					db_helper.addAttendance(Long.valueOf(eventId), currentUser.getUserId(), currentUser.getFullName(), true, false, false);
				}else if(key.equalsIgnoreCase("maybe")) {
					db_helper.addAttendance(Long.valueOf(eventId), currentUser.getUserId(), currentUser.getFullName(), false, true, false);
				}else if(key.equalsIgnoreCase("no")) {
					db_helper.addAttendance(Long.valueOf(eventId), currentUser.getUserId(), currentUser.getFullName(), false, false, true);
				} else {
					JSONObject content = new JSONObject();
					for(String dataName : EventNames) {
						String value = ParamUtil.getString(resourceRequest, dataName);
						String name = "EventImageName";
						String path = "EventImagePath";
						if(dataName.equalsIgnoreCase("EventImage")) {
							try {
								UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest( resourceRequest );
								String filename = "";
								String pathStr = uploadRequest.getSession().getServletContext().getRealPath("/"); 
								String dirPath = pathStr+"uploadedfiles";
								String filePath = "";
								String EventImage = ParamUtil.getString(resourceRequest, name); 
								
								
								String coverImagebase64String = ParamUtil.getString(resourceRequest, dataName); 
								String extension = FileUtil.getExtension(EventImage); 
								extension = extension.equalsIgnoreCase("svg") ? "svg+xml" : extension;
								byte[] bytes = Base64.getDecoder().decode(coverImagebase64String.split("data:image/"+extension+";base64,")[1]);
								 
								filename = UUID.randomUUID().toString()+"."+extension;
								filePath = dirPath+"/"+filename;
								Path fpath = Paths.get(filePath);
						        Files.write(fpath, bytes);
								content.put(name, EventImage); 
								content.put(path, filePath); 
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else if(dataName.equalsIgnoreCase("StartDate") || dataName.equalsIgnoreCase("EndDate")) {
							Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(value.replaceAll("T", " "));
							String formated = new SimpleDateFormat("dd/MM/yyyy HH:mm aa").format(date);
							content.put(dataName, formated);
						}else if(dataName.equalsIgnoreCase("Speakers")) {
							JSONArray speakersJa =  new JSONArray(value);
						    String speakers = ""; 
						    for(int i=0; i< speakersJa.length(); i++) {
						    	JSONObject jo = speakersJa.getJSONObject(i);
						    	speakers += (i>0?",":"")+jo.getString("id");
						    }
						    content.put(dataName, speakers);
						} else content.put(dataName, value);
					}
					addJournalArticle(themeDisplay, content, resourceRequest);
				}
				out.println(String.valueOf(retVal));
				out.flush();
				super.serveResource(resourceRequest, resourceResponse);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	public boolean addJournalArticle(ThemeDisplay themeDisplay, JSONObject contentEn, ResourceRequest request) throws Exception {
		boolean res = false;
		long userId = themeDisplay.getUserId();
		long groupId = themeDisplay.getLayout().getGroupId();
		ServiceContext serviceContext = new ServiceContext();
		
		com.liferay.dynamic.data.mapping.model.DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
				.filter(st -> st.getName("en_US").equalsIgnoreCase(EventStructureName)).findFirst().orElse(null);	
		
		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
					.filter(categ -> categ.getName().equalsIgnoreCase(EventCategoryName)).findFirst().orElse(null);

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setAssetCategoryIds(new long[] { assetCategory.getCategoryId() });
		Map<Locale, String> titleMap = new HashMap<Locale, String>();
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
		
		JournalArticle article = null;
		try {
			System.out.println();
			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
			String xmlContent = getEventFormXml(contentEn, themeDisplay, request);
			titleMap.put(Locale.US, contentEn.getString("EventName"));
			descriptionMap.put(Locale.US, contentEn.getString("EventName"));				
			 
			article = JournalArticleLocalServiceUtil.addArticle(userId, groupId, EventFolderId, titleMap, descriptionMap,
					xmlContent, struct.getStructureKey(), "", serviceContext);
			
			if (article != null) res = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			res = false;
		}
		return res;
	}

	private String getEventFormXml(JSONObject contentEn, ThemeDisplay themeDisplay, ResourceRequest request) {
			String xmlContent = "<?xml version=\"1.0\"?>\r\n" + "\r\n"
					+ "<root available-locales=\"en_US\" default-locale=\"en_US\" version=\"1.0\">\r\n";
			
			if (contentEn.has("EventName")) {
				xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\"" + EventName+ "\" type=\"text\">\r\n"
						+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("EventName")
						+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
			}
			if (contentEn.has("EventDescription")) {
				xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+EventDescription+"\" type=\"text\">\r\n"
						+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("EventDescription")
						+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
			}
			if (contentEn.has("EventType")) {
				xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+EventType+"\" type=\"text\">\r\n"
						+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("EventType")
						+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
			}
			if (contentEn.has("Attendance")) {
				xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+Attendance+"\" type=\"text\">\r\n"
						+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("Attendance")
						+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
			}
			if (contentEn.has("MeetingLink")) {
				xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+MeetingLink+"\" type=\"text\">\r\n"
						+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("MeetingLink")
						+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
			}
			if (contentEn.has("OtherLinks")) {
				xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+OtherLinks+"\" type=\"text\">\r\n"
						+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("OtherLinks")
						+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
			}
			if (contentEn.has("StartDate")) {
				xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+StartDate+"\" type=\"text\">\r\n"
						+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("StartDate")
						+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
			}
			if (contentEn.has("EndDate")) {
				xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+EndDate+"\" type=\"text\">\r\n"
						+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("EndDate")
						+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
			}
			if (contentEn.has("Speakers")) {
				xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\""+Speakers+"\" type=\"text\">\r\n"
						+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("Speakers")
						+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
			}
			if (contentEn.has("EventImagePath") && !helper.isEmpty(contentEn.getString("EventImagePath"))) {
				File f = new File(contentEn.getString("EventImagePath"));
				JSONObject EventImageJO = helper.fileUploadByDL(f, themeDisplay, request, EventCategoryName,
						contentEn.getString("EventImageName"));
				xmlContent += "	<dynamic-element index-type=\"text\" instance-id=\"r3TT432x\"  name=\"" + EventImage + "\" type=\"image\">\r\n"
						+ "		<dynamic-content language-id=\"en_US\"><![CDATA[" + EventImageJO.toString()
						+ "]]></dynamic-content>\r\n" + "	</dynamic-element>\r\n";
			} 
			xmlContent += "</root>";
			return xmlContent;
		}
	
	private String getUserRoleName(int userRole) {
		String res = "";
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.InvestorUser.name())))
				res = "InvestorUserRole";
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.IndustryUser.name())))
				res = "IndustryUserRole";
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.EnergyCompanyUser.name())))
				res = "EnergyCompanyUserRole";
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.EnergyHubUser.name())))
				res = "EnergyHubUserRole";
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.PolicyMakerUser.name())))
				res = "PolicyMakerUserRole";
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.EntrepreneurUser.name())))
				res = "EntrepreneurUserRole";
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.ResearcherUser.name())))
				res = "ResearcherUserRole";
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.AcademicUser.name())))
				res = "AcademicUserRole";
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.Administrator.name())))
				res = "AdministratorRole";
		return res;
	}
}