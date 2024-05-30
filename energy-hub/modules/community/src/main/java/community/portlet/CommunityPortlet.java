package community.portlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
import org.ocpsoft.prettytime.PrettyTime;
import org.osgi.service.component.annotations.Component;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
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

import community.constants.CommunityPortletKeys;
import community.constants.UserTypes;
import community.db_helper.db_helper;
import community.helper.helper;

/**
 * @author vyo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Community",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.name=" + CommunityPortletKeys.COMMUNITY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CommunityPortlet extends MVCPortlet {
	private long DiscussionForumFolderId;
	
	private final static Configuration _portletConfiguration = ConfigurationFactoryUtil
			.getConfiguration(PortalClassLoaderUtil.getClassLoader(), "portlet");
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		try {
			db_helper.createRepliesTable();
			db_helper.createForumDetails("CommunityReplies_Details", "replyId");
			db_helper.createForumDetails("Forum_Details", "forum_Id");
			
			HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest)); 
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
				
			String ipadd =  PortalUtil.getHttpServletRequest(renderRequest).getRemoteAddr();
			if(!ipadd.equalsIgnoreCase("51.178.45.207")) { //Offline
				DiscussionForumFolderId = 40824;
			}else if(!ipadd.equalsIgnoreCase("51.178.45.174")) { //Online
				DiscussionForumFolderId = 42697;
			}else { //Local
				DiscussionForumFolderId = 67716;
			}
			long groupId = themeDisplay.getLayout().getGroupId();
			long categId = 0;
			String ddmStructureKey = httpReq.getParameter("ddmStructureKey");
			String folderId = helper.ifEmpty(httpReq.getParameter("folderId"), String.valueOf(DiscussionForumFolderId));
			String categoryId = httpReq.getParameter("ddmStructureKey");;
			String ddmTemplateKey = httpReq.getParameter("ddmTemplateKey");
			String threadType = "";
			String pageName = themeDisplay.getLayout().getName("en_US");
			
			AssetCategory category = AssetCategoryLocalServiceUtil.getCategories().stream()
					.filter(categ -> categ.getName().equalsIgnoreCase("Discussion Forums")).findFirst().orElse(null);
			
			categId = category.getCategoryId();
			
			if (httpReq.getParameter("threadType") != null) {
				threadType = httpReq.getParameter("threadType").contains("type_") ? httpReq.getParameter("threadType") : "type_"+httpReq.getParameter("threadType");
			}			
			com.liferay.dynamic.data.mapping.model.DDMStructure struct = DDMStructureLocalServiceUtil.getStructures().stream()
					.filter(st -> st.getName("en_US").equalsIgnoreCase("thread")).findFirst().orElse(null);
					
			ddmStructureKey = String.valueOf(struct.getStructureKey());
	       
			renderRequest.setAttribute("ddmStructureKey", ddmStructureKey);  
			renderRequest.setAttribute("categoryId", categoryId);  
			renderRequest.setAttribute("threadType", threadType);  
			renderRequest.setAttribute("isSignedIn", themeDisplay.isSignedIn()); 
			renderRequest.setAttribute("sign_in_url", "/home/-/login/openid_connect_request?p_p_state=maximized&"
					+ "_com_liferay_login_web_portlet_LoginPortlet_saveLastPath=false"
					+ "&_com_liferay_login_web_portlet_LoginPortlet_redirect=/"
					+ "&_com_liferay_login_web_portlet_LoginPortlet_OPEN_ID_CONNECT_PROVIDER_NAME=intalio"); 

			String sort = helper.ifEmpty(httpReq.getParameter("sort"), "desc");
			String sortField = helper.ifEmpty(httpReq.getParameter("sortField"), "createDate");
			
			String queryText = helper.ifEmpty(httpReq.getParameter("queryText"), "");
			String filter = helper.ifEmpty(httpReq.getParameter("filter"), "");
			
			List<AssetTag>tags = getTagsOrdered(themeDisplay, categId); 
			renderRequest.setAttribute("allTags", tags);
			JSONArray jaTags = new JSONArray(tags); 
			renderRequest.setAttribute("jaTags", jaTags);
					
			getDropdownsData(themeDisplay, renderRequest, ddmStructureKey);
			renderRequest.setAttribute("instanceId", themeDisplay.getPortletDisplay().getId());
			
			if(httpReq.getParameterMap().containsKey("askQuestion")) {
				renderRequest.setAttribute("ddmStructureKey", ddmStructureKey);
				renderRequest.setAttribute("ddmTemplateKey", ddmTemplateKey);
				renderRequest.setAttribute("folderId", folderId);
				include("/askQuestion.jsp", renderRequest, renderResponse);
			}else if(httpReq.getParameterMap().containsKey("viewForum")) {
				String forumId = httpReq.getParameter("forumId");
				JSONArray jaRes = getForumDataList(groupId, categId, renderRequest, sortField, sort, forumId, queryText, filter, false, threadType); 
				renderRequest.setAttribute("forums", helper.toList(jaRes));
				renderRequest.setAttribute("forumsTotal", jaRes.length());
				getForumReplies(forumId, themeDisplay.getUserId(), renderRequest, queryText);
				renderRequest.setAttribute("viewForum", false);
				include("/view.jsp", renderRequest, renderResponse);
			}else if(httpReq.getParameterMap().containsKey("youranswers")) {
				JSONArray jaRes = getForumDataList(groupId, categId, renderRequest, sortField, sort, "", queryText, filter, false, threadType); 
				getUserAnswers(jaRes, themeDisplay, renderRequest, queryText);
				include("/yourAnswers.jsp", renderRequest, renderResponse);
			}else if(httpReq.getParameterMap().containsKey("yourquestions")) {
				JSONArray jaRes = getForumDataList(groupId, categId, renderRequest, sortField, sort, "", queryText, filter, false, threadType); 
				JSONArray userList = new JSONArray();
				for(int i = 0; i<jaRes.length(); i++) {
					JSONObject jo = jaRes.getJSONObject(i);
					if(jo.getLong("userId") == themeDisplay.getUserId()) {
						userList.put(jo);
					}
				}
				renderRequest.setAttribute("forums", helper.toList(userList));
				renderRequest.setAttribute("forumsTotal", userList.length());
				include("/view.jsp", renderRequest, renderResponse);
			} else if(httpReq.getParameterMap().containsKey("yourdrafts")) {
				JSONArray jaRes = getForumDataList(groupId, categId, renderRequest, sortField, sort, "", queryText, filter, true, threadType);
				renderRequest.setAttribute("forums", helper.toList(jaRes)); 
				renderRequest.setAttribute("forumsTotal", jaRes.length());
				renderRequest.setAttribute("isDraft", true);
				include("/view.jsp", renderRequest, renderResponse);
			}else if(httpReq.getParameterMap().containsKey("view")) { 
				JSONArray jaRes = getForumDataList(groupId, categId, renderRequest, sortField, sort, "", queryText, filter, false, threadType); 
				renderRequest.setAttribute("forums", helper.toList(jaRes)); 
				renderRequest.setAttribute("forumsTotal", jaRes.length());
				renderRequest.setAttribute("viewForum", true);
				include("/view.jsp", renderRequest, renderResponse);
			}else if(pageName.equalsIgnoreCase("Home")){
				List<JournalArticle> forums = new ArrayList<JournalArticle>();
				AssetEntryQuery assetEntryQuery = new AssetEntryQuery();
				assetEntryQuery.setAnyCategoryIds(new long[] { categId });
				assetEntryQuery.setOrderByCol1("createDate"); 
				assetEntryQuery.setOrderByType1("desc"); 
				
				List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
				for (AssetEntry ae : assetEntryList) {
				    JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
				    forums.add(JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey()));
				}
				
				forums = getLatestVersionArticle(forums);
				forums = getHomeForums(themeDisplay, forums);
				
				JSONArray jaRes = new JSONArray();
				for(JournalArticle forum : forums) {
					JSONObject forumRes = fillData(forum, renderRequest, themeDisplay, queryText);
					forumRes.put("forumURL", "/policy-forum?viewForum&p_r_p_categoryId="+ categId
							+"&folderId"+ forum.getFolderId() +"=&ddmStructureKey="+ ddmStructureKey +"&ddmTemplateKey="
							+ "&threadType="+ forumRes.getString("type") +"&forumId=" + forum.getArticleId());
					folderId = String.valueOf(forum.getFolderId());
					jaRes.put(forumRes);
				}
				renderRequest.setAttribute("seeAllURL", "/policy-forum?p_r_p_categoryId="+ categId +"&folderId="
						+ folderId +"&ddmStructureKey="+ ddmStructureKey +"&ddmTemplateKey="); 
				renderRequest.setAttribute("forums", helper.toList(jaRes)); 
				include("/homePage.jsp", renderRequest, renderResponse);
			} else { //mainView
				JSONArray threadTypeList = getThreadTypeList(themeDisplay, renderRequest, ddmStructureKey);
				JSONArray res = new JSONArray();
				for(int i=0; i<threadTypeList.length(); i++) {
					JSONObject resjo = new JSONObject();
					
					List<JournalArticle> forums = new ArrayList<JournalArticle>();
					AssetEntryQuery assetEntryQuery = new AssetEntryQuery();
					assetEntryQuery.setAnyCategoryIds(new long[] { categId });
					assetEntryQuery.setOrderByCol1("createDate"); 
					assetEntryQuery.setOrderByType1("desc"); 
					
					List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
					for (AssetEntry ae : assetEntryList) {
					    JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
					    forums.add(JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey()));
					}
					
					forums = getLatestVersionArticle(forums);
					List<JournalArticle> filtered = getFilteredArticles(themeDisplay, forums, "type_"+threadTypeList.getJSONObject(i).getString("id"));
					
					resjo.put("value", threadTypeList.getJSONObject(i).getString("value"));
					resjo.put("id", threadTypeList.getJSONObject(i).getString("id"));
					resjo.put("count", filtered.size());
					JSONArray jaRes = new JSONArray();
					int k = 0;
					for(JournalArticle forum : getFeautredForums(themeDisplay, filtered)) {
						if(k<2) {
							JSONObject forumRes = fillData(forum, renderRequest, themeDisplay, queryText);
							String  coverImageName = "";
							if(forumRes.getString("type").equalsIgnoreCase("Option22752688")) {//Policy Forum
								coverImageName = "PolicyForumCoverImage.svg";
							}
							if(forumRes.getString("type").equalsIgnoreCase("Option12202416")) { // Technologies
								coverImageName = "TechnologyForumCoverImage.svg";
							}
							String imageUrl = "";
							DLFileEntry image = DLFileEntryLocalServiceUtil.getFileEntryByFileName(themeDisplay.getLayout().getGroupId(), 0, coverImageName);

							if (image != null) {
								imageUrl = PortalUtil.getPortalURL(renderRequest) + "/documents/" + image.getGroupId() + "/"
									+ image.getFolderId() + "/" + image.getTitle() + "/" + image.getUuid() + "?t="
									+ System.currentTimeMillis();
							}
							forumRes.put("coverIamgeURL", imageUrl);
							jaRes.put(forumRes);
						}
						k++;
					}
					
					resjo.put("forumList", jaRes);
					res.put(resjo);
					 
				}
				renderRequest.setAttribute("ThreadTypes", helper.toList(res));
				include("/mainView.jsp", renderRequest, renderResponse);
			}
			
			super.render(renderRequest, renderResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	private List<AssetTag> getTagsOrdered(ThemeDisplay themeDisplay, long categId) throws PortalException { 
		List<JournalArticle> forums = new ArrayList<JournalArticle>();
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();
		assetEntryQuery.setAnyCategoryIds(new long[] { categId });
		assetEntryQuery.setOrderByCol1("createDate"); 
		assetEntryQuery.setOrderByType1("desc");
		
		List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
		for (AssetEntry ae : assetEntryList) {
		    JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
		    forums.add(JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey()));
		}
		
//		List<JournalArticle> forums = JournalArticleServiceUtil.getArticles(themeDisplay.getLayout().getGroupId(), folderId, themeDisplay.getLocale(), 0, 1000, comparator);
		List<AssetTag>  tags = AssetTagLocalServiceUtil.getTags();
		try {
			// set all counters to 0
			for(AssetTag tag : tags) {
				tag.setAssetCount(0);
			}
			
			forums = getLatestVersionArticle(forums);
			for(JournalArticle forum : forums) {
				AssetEntry entry = AssetEntryLocalServiceUtil.fetchEntry(JournalArticle.class.getName(),  forum.getResourcePrimKey());
				List<AssetTag> forumTags = AssetTagLocalServiceUtil.getAssetEntryAssetTags(entry.getEntryId());
				for(AssetTag tag : forumTags) {
					tags.stream().filter(t -> tag.getTagId() ==  t.getTagId()).findFirst().orElse(null)
					.setAssetCount(tags.stream().filter(t -> tag.getTagId() ==  t.getTagId()).findFirst().orElse(null).getAssetCount() +1);
				}
			}
			//sort tags by highest count
			tags = tags.stream().sorted(Comparator.comparing(AssetTag::getAssetCount).reversed()).collect(Collectors.toList());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tags;
	} 
	
	private JSONArray getForumDataList(long groupId, long categId, RenderRequest renderRequest,
			String sortField, String sort, String forumId, String queryText, String filter, Boolean draft,String threadType)
					throws DocumentException, PortalException, JSONException, SQLException, ParseException {
		JSONArray jaRes = new JSONArray();
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<JournalArticle> forums = new ArrayList<JournalArticle>();
		
		forums = getforums(categId, renderRequest, forumId, draft, groupId, queryText, filter, threadType);
		String ddmStructureKey = "", ddmTemplateKey = "";
		long folderId= 0;
		if(forums != null) {
			for(JournalArticle forum : forums) {
				JSONObject res = fillData(forum, renderRequest, themeDisplay, queryText);
				jaRes.put(res);
				
				ddmStructureKey = forum.getDDMStructureKey();
				ddmTemplateKey = forum.getDDMTemplateKey();
				folderId = forum.getFolderId();
			}
			
			renderRequest.setAttribute("ddmTemplateKey", ddmTemplateKey);
			renderRequest.setAttribute("ddmStructureKey", ddmStructureKey);
			renderRequest.setAttribute("folderId", folderId);
		}
		JSONArray sortedJsonArray = jaRes;
		if(sortField.equalsIgnoreCase("top")) {//viewCount
			List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		    for (int i = 0; i < jaRes.length(); i++) {
		        jsonValues.add(jaRes.getJSONObject(i));
		    }
			 Collections.sort(jsonValues, new Comparator<JSONObject>() {
		         private final String KEY_NAME = "viewcount";
		         @Override
		         public int compare(JSONObject a, JSONObject b) {
		            int int1 = 0;
		            int int2 = 0;
		            try {
		               int1 = Integer.valueOf(a.getString(KEY_NAME));
		               int2 = Integer.valueOf(b.getString(KEY_NAME));
		               return int1 < int2 ? 1 : (int1 > int2 ? -1 : 0);
		            } catch(JSONException e) {
		               e.printStackTrace();
		            }
		            return 0;
		         } 
		      });
			 sortedJsonArray = new JSONArray(jsonValues);
			
		} else if (sortField.equalsIgnoreCase("lastActivity")) {
			List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		    for (int i = 0; i < jaRes.length(); i++) {
		        jsonValues.add(jaRes.getJSONObject(i));
		    }
		    Collections.sort(jsonValues, new Comparator<JSONObject>() {
		    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		         @Override
		         public int compare(JSONObject o1, JSONObject o2) {
		            try {
		            	return formatter.parse(o1.getString("modifiedDate")).compareTo(formatter.parse(o2.getString("modifiedDate")));
		            } catch(JSONException | ParseException e) {
		               e.printStackTrace();
		            }
		            return 0;
		         } 
		      }.reversed());
			 sortedJsonArray = new JSONArray(jsonValues);}
		return sortedJsonArray;
	}

	public List<JournalArticle> getforums(long categId, RenderRequest renderRequest, String forumId, boolean draft,
			long groupId, String queryText, String filter, String threadType) { 
		List<JournalArticle> forums = new ArrayList<JournalArticle>();
		try { 
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
			
			if(helper.isEmpty(forumId)) {
				AssetEntryQuery assetEntryQuery = new AssetEntryQuery();
				assetEntryQuery.setAnyCategoryIds(new long[] { categId });
				assetEntryQuery.setOrderByCol1("createDate"); 
				assetEntryQuery.setOrderByType1("desc");
				
				List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
				for (AssetEntry ae : assetEntryList) {
				    JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
				    forums.add(JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey()));
				}
				
				forums = getFilteredArticles(themeDisplay, forums, threadType);
				if(draft) {
					List<JournalArticle> drafts = new ArrayList<JournalArticle>();
					for(JournalArticle article : forums) {
						if(article.getStatus() == WorkflowConstants.STATUS_DRAFT && article.getUserId() == themeDisplay.getUserId())
							drafts.add(article);
					}
					forums = drafts;
				} 
			}else if(!helper.isEmpty(forumId)) {
				forums.add(JournalArticleServiceUtil.fetchArticle(themeDisplay.getLayout().getGroupId(), forumId));
			}
			
			if(!helper.isEmpty(queryText) && helper.isEmpty(forumId)) {
				forums = getSearchedArticles(themeDisplay, getFilteredArticles(themeDisplay, forums, threadType), queryText);
			}
			
			if(!helper.isEmpty(filter) && helper.isEmpty(forumId)) {
				forums = getFilteredArticles(themeDisplay, getFilteredArticles(themeDisplay, forums, threadType), filter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forums;
	}
	
	private JSONObject fillData(JournalArticle forum, RenderRequest renderRequest, ThemeDisplay themeDisplay, String queryText) 
			throws DocumentException, PortalException, JSONException, SQLException, ParseException { 
		String content = forum.getContentByLocale(themeDisplay.getLanguageId());
		Document document = SAXReaderUtil.read(content);
		
		JSONObject res = new JSONObject();
		res.put("id", forum.getArticleId());
		res.put("title", helper.getData(document, "Text94657287"));
		res.put("description", helper.getData(document, "RichText10826413"));
		res.put("type", helper.getData(document, "SelectFromList86123921"));
		res.put("userName", forum.getUserName());
		
		String token = db_helper.getToken(_portletConfiguration);
		User user = UserLocalServiceUtil.getUser(forum.getUserId());
		String userString = db_helper.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), user.getEmailAddress());
		
		if(!helper.isEmpty(userString) && new JSONObject(userString).has("roleId")) {
			int userRole = new JSONObject(userString).getInt("userApplicationRoleId");
			res.put("userRole", getUserRoleName(userRole));
		}
		res.put("userId", forum.getUserId());
		
		PrettyTime p = new PrettyTime();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d YYYY HH:mm");
		java.util.Date date = Date.from(LocalDateTime.ofInstant(forum.getCreateDate().toInstant(),
                ZoneId.systemDefault()).plusHours(3).atZone(ZoneId.systemDefault()).toInstant());
		String formatedDate = sdf.format(date);
		res.put("createDate", formatedDate);
		res.put("createDateDisplay", p.format(sdf.parse(formatedDate)));
		
		JSONObject coverImageJO = new JSONObject(document.selectSingleNode("/root/dynamic-element[@name='Upload07987255']") != null && 
				document.selectSingleNode("/root/dynamic-element[@name='Upload07987255']").hasContent() ? 
				document.selectSingleNode("/root/dynamic-element[@name='Upload07987255']/dynamic-content").getText() : "{}");
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
		res.put("imageUrl", imageUrl); 
		
		User forum_user = UserLocalServiceUtil.getUser(forum.getUserId());
		res.put("userPortrait", forum_user.getPortraitURL(themeDisplay));
		
		AssetEntry  entry = AssetEntryLocalServiceUtil.fetchEntry(JournalArticle.class.getName(),  forum.getResourcePrimKey());
		List<AssetTag> currentTags = AssetTagLocalServiceUtil.getAssetEntryAssetTags(entry.getEntryId());
		res.put("currentTags", currentTags);
		
		
		JSONObject forumDetailsCount = db_helper.getForumDetailsCount(forum.getArticleId(), "Forum_Details", "forum_Id");
		res.put("viewcount", forumDetailsCount.get("viewcount"));
		res.put("likecount", forumDetailsCount.get("likecount"));
		res.put("dislikecount", forumDetailsCount.get("dislikecount"));
		res.put("modifiedDate", forumDetailsCount.has("modifiedDate") ? forumDetailsCount.get("modifiedDate") : "");
		
		JSONObject userForumDetails = db_helper.getUserForumDetails(forum.getArticleId(), themeDisplay.getUserId(), "Forum_Details", "forum_Id");
		if(!userForumDetails.isEmpty()) {
			res.put("view", userForumDetails.has("view") ? userForumDetails.getBoolean("view") : false);
			res.put("liked", userForumDetails.has("liked") ? userForumDetails.getBoolean("liked") : false);
			res.put("dislike", userForumDetails.has("dislike") ? userForumDetails.getBoolean("dislike") : false);
		}
		
		return res.put("totalReplies", getForumRepliesTotal(forum.getArticleId(),queryText));
	}

	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException, PortletException {
		resourceResponse.setContentType("text/html");
		PrintWriter out = resourceResponse.getWriter();
		String key = ParamUtil.getString(resourceRequest, "key");
		String retVal = "";
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
			String token = db_helper.getToken(_portletConfiguration);
			User user = themeDisplay.getUser();
			String userString = db_helper.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), user.getEmailAddress());
			
			Long userRoleId = null;
			if(!helper.isEmpty(userString) && new JSONObject(userString).has("roleId")) {
				int iamRoleId = new JSONObject(userString).getInt("userApplicationRoleId");
				String roleIdStrnig = UserTypes.getUserTypeValue(getUserRoleName(iamRoleId).split("Role")[0]);
				userRoleId= Long.valueOf(roleIdStrnig);
			}
			
			if(key.equalsIgnoreCase("like") || key.equalsIgnoreCase("dislike") || key.equalsIgnoreCase("view")) {
				boolean view= false, like = false, dislike = false;
				if(key.equalsIgnoreCase("like")) like = true;
				if(key.equalsIgnoreCase("dislike")) dislike = true;
				if(key.equalsIgnoreCase("view")) view = true;
				String id =  ParamUtil.getString(resourceRequest, "id");
				db_helper.createForumDetails("Forum_Details", "forum_Id");
				db_helper.addForumDetails(id, themeDisplay.getUserId(), like, dislike, view, "Forum_Details", "forum_Id", userRoleId);
			}else if (key.equalsIgnoreCase("reply_like") || key.equalsIgnoreCase("reply_dislike")){
				boolean  like = false, dislike = false;
				if(key.equalsIgnoreCase("reply_like")) like = true;
				if(key.equalsIgnoreCase("reply_dislike")) dislike = true;
				String id =  ParamUtil.getString(resourceRequest, "id");
				db_helper.addForumDetails(id, themeDisplay.getUserId(), like, dislike, false, "CommunityReplies_Details", "replyId", userRoleId);
			}else if(key.equalsIgnoreCase("postReply")) {
				String forumId =  ParamUtil.getString(resourceRequest, "id");
				String replyParentId =  ParamUtil.getString(resourceRequest, "replyParentId");
				String reply =  ParamUtil.getString(resourceRequest, "replyText");
					 try {
						 UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest( resourceRequest );
						 String filename = "";
						 String pathStr = uploadRequest.getSession().getServletContext().getRealPath("/"); 
					 	 String dirPath = pathStr+"uploadedfiles";
						 String filePath = "";
						 JSONObject replyDocJo = new JSONObject();
						 String coverImageName = ParamUtil.getString(resourceRequest, "replyDocName"); 
						 String coverImagebase64String = ParamUtil.getString(resourceRequest, "replyDoc"); 
						 
						 if(!helper.isEmpty(coverImagebase64String)) {
							 String extension = FileUtil.getExtension(coverImageName); 
							 extension = extension.equalsIgnoreCase("svg") ? "svg+xml" : extension;
							 byte[] bytes = Base64.getDecoder().decode(coverImagebase64String.split("data:image/"+extension+";base64,")[1]);
							 
							 filename = UUID.randomUUID().toString()+"."+extension;
							 filePath = dirPath+"/"+filename;
							 Path path = Paths.get(filePath);
							 Files.write(path, bytes);
							 File objFile = new File(filePath);
							 replyDocJo = helper.fileUploadByDL(objFile, themeDisplay, resourceRequest, "Forums", coverImageName);
						 }
					 	 
					 	 db_helper.addReply(themeDisplay.getUserId(), reply, replyParentId, forumId, replyDocJo.isEmpty() ? "" : String.valueOf(replyDocJo.get("fileEntryId")), userRoleId);
					 } catch (Exception e) {
					 	e.printStackTrace();
					 }
			} else if(key.equalsIgnoreCase("deleteForum")) {
				String forumId = ParamUtil.getString(resourceRequest, "id");
				deleteForum(forumId, themeDisplay.getLayout().getGroupId());
			}else if(key.equalsIgnoreCase("deleteReply")) {
				long id =  Long.valueOf(ParamUtil.getString(resourceRequest, "id"));
				db_helper.deleteReply(id); 
			}else if (key.equalsIgnoreCase("publishDraft")) {
				String id =  ParamUtil.getString(resourceRequest, "id");
				publishDrafts(id, themeDisplay);
			}else{
				getCreateJournalData(resourceRequest, themeDisplay);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println(String.valueOf(retVal));
		out.flush();
		super.serveResource(resourceRequest, resourceResponse);
	}
	
	private void publishDrafts(String id, ThemeDisplay themeDisplay) {
		try {
			ServiceContext serviceContext = new ServiceContext();
		    serviceContext.setWorkflowAction(WorkflowConstants.STATUS_APPROVED);
			JournalArticle forum = JournalArticleLocalServiceUtil.fetchArticle(themeDisplay.getLayout().getGroupId(), id);
			JournalArticleLocalServiceUtil.updateStatus(themeDisplay.getUserId(), forum, WorkflowConstants.STATUS_APPROVED, 
					null, serviceContext, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getUserAnswers(JSONArray forumsDataListJA, ThemeDisplay themeDisplay, RenderRequest renderRequest, String queryText) throws SQLException, ParseException {
		try {
			JSONArray forumsIdsforUserReplies = db_helper.getForumsIdsforUserReplies(themeDisplay.getUserId(), queryText);
//			List<String> ids= new ArrayList<String>();
			for(int i = 0; i<forumsIdsforUserReplies.length(); i++) { 
				JSONObject forumsIdsforUserRepliesJO = forumsIdsforUserReplies.getJSONObject(i);
				String forum_id = forumsIdsforUserRepliesJO.getString("forum_Id");
				String replyId = forumsIdsforUserRepliesJO.getString("replyId");
//				if(!ids.contains(forum_id)) {
					JournalArticle forum = JournalArticleServiceUtil.fetchArticle(themeDisplay.getLayout().getGroupId(), forum_id);
					if(forum != null) {
						JSONObject fillDatares = fillData(forum, renderRequest, themeDisplay, queryText);
						forumsIdsforUserRepliesJO.put("title", fillDatares.getString("title"));
						forumsIdsforUserRepliesJO.put("forumId", fillDatares.getString("id"));
						forumsIdsforUserRepliesJO.put("currentTags", fillDatares.get("currentTags"));
						User reply_user = UserLocalServiceUtil.getUser(themeDisplay.getUserId()); 
						forumsIdsforUserRepliesJO.put("userPortrait", reply_user.getPortraitURL(themeDisplay));
					}
					JSONObject forumDetailsCount = db_helper.getForumDetailsCount(replyId, "CommunityReplies_Details", "replyId");
					forumsIdsforUserRepliesJO.put("viewcount", forumDetailsCount.get("viewcount"));
					forumsIdsforUserRepliesJO.put("likecount", forumDetailsCount.get("likecount"));
					forumsIdsforUserRepliesJO.put("dislikecount", forumDetailsCount.get("dislikecount"));
					
					JSONObject userForumDetails = db_helper.getUserForumDetails(replyId, themeDisplay.getUserId(), "CommunityReplies_Details", "replyId");
					if(!userForumDetails.isEmpty()) {
						forumsIdsforUserRepliesJO.put("view", userForumDetails.has("view") ? userForumDetails.getBoolean("view") : false);
						forumsIdsforUserRepliesJO.put("liked", userForumDetails.has("liked") ? userForumDetails.getBoolean("liked") : false);
						forumsIdsforUserRepliesJO.put("dislike", userForumDetails.has("dislike") ? userForumDetails.getBoolean("dislike") : false);
					}
//					ids.add(forum_id);
//				}
			}
			renderRequest.setAttribute("replies", helper.toList(forumsIdsforUserReplies));
			renderRequest.setAttribute("repliesTotal", forumsIdsforUserReplies.length());
		} catch (PortalException | JSONException | DocumentException e) {
			e.printStackTrace();
		}
	} 
	
	private void deleteForum(String forumId, long groupId) throws PortalException {
		try {
			List<JournalArticle> versiones=JournalArticleLocalServiceUtil.getArticles(groupId, forumId);
			for (int j = 0; j < versiones.size(); j++) {
			   JournalArticle version=versiones.get(j);
			   JournalArticleLocalServiceUtil.deleteArticle(groupId, version.getArticleId(), version.getVersion(), null,null);
			}
			db_helper.deleteForumData(Long.valueOf(forumId));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void getCreateJournalData(ResourceRequest resourceRequest, ThemeDisplay themeDisplay) throws Exception {
		JSONObject contentEn = new JSONObject();
		long folderId =  ParamUtil.getLong(resourceRequest, "folderId");
		String ddmStructureKey =  ParamUtil.getString(resourceRequest, "ddmStructureKey");
		String ddmTemplateKey =  ParamUtil.getString(resourceRequest, "ddmTemplateKey");
		String workflowAction = ParamUtil.getString(resourceRequest, "workflowAction");
		String[] jsonDataNames = {"ThreadTitle", "QuestionDetails", "ProjectCategory", "tagProgramValues","coverImage", "ThreadType"}; 
		
		for(String dataName : jsonDataNames) {
			String value = ParamUtil.getString(resourceRequest, dataName);
			if(dataName.equals("coverImage")) {
				try {
					UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest( resourceRequest );
					String filename = "";
					String pathStr = uploadRequest.getSession().getServletContext().getRealPath("/"); 
					String dirPath = pathStr+"uploadedfiles";
					String filePath = "";
					String coverImageName = ParamUtil.getString(resourceRequest, "coverImageName"); 
					
					String coverImagebase64String = ParamUtil.getString(resourceRequest, dataName); 
					if(!helper.isEmpty(coverImagebase64String)) {
						String extension = FileUtil.getExtension(coverImageName); 
						extension = extension.equalsIgnoreCase("svg") ? "svg+xml" : extension;
						byte[] bytes = Base64.getDecoder().decode(coverImagebase64String.split("data:image/"+extension+";base64,")[1]);
						 
						filename = UUID.randomUUID().toString()+"."+extension;
						filePath = dirPath+"/"+filename;
						Path path = Paths.get(filePath);
				        Files.write(path, bytes);
						contentEn.put("coverImageName", coverImageName); 
						contentEn.put("coverImagePath", filePath); 
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else
				contentEn.put(dataName, value);
		}
		if(!contentEn.isEmpty())
			addJournalArticle(themeDisplay, contentEn, resourceRequest, folderId, ddmStructureKey, ddmTemplateKey, workflowAction);
	}
	
	public boolean addJournalArticle(ThemeDisplay themeDisplay, JSONObject contentEn, ResourceRequest request,  long folderId,
									String ddmStructureKey, String ddmTemplateKey, String workflowAction) throws Exception {
		boolean res = false;
		long userId = themeDisplay.getUserId();
		long groupId = themeDisplay.getLayout().getGroupId(); 
	    ServiceContext serviceContext = new ServiceContext();
	
	    AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
				.filter(categ -> categ.getName().equalsIgnoreCase("Discussion Forums")).findFirst().orElse(null);
	    
	    serviceContext.setAddGroupPermissions(true);
	    serviceContext.setAddGuestPermissions(true);
	    serviceContext.setScopeGroupId(groupId);
	    serviceContext.setAssetCategoryIds(new long[] {  assetCategory.getCategoryId() }); 
	    if(workflowAction.equalsIgnoreCase("draft"))
	    	serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);
	    else if(workflowAction.equalsIgnoreCase("publish")) 
	    	serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
	    Map<Locale, String> titleMap = new HashMap<Locale, String>();
	    Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
	
	    titleMap.put(Locale.US, contentEn.getString("ThreadTitle"));
	    descriptionMap.put(Locale.US,  contentEn.getString("ThreadTitle"));
	    
	    
	    JournalArticle article = null; 
	    try {  
		    String xmlContent = "<?xml version=\"1.0\"?>\r\n" + 
		    		"\r\n" + 
		    		"<root available-locales=\"en_US\" default-locale=\"en_US\" version=\"1.0\">\r\n" ; 

		    if(contentEn.has("ThreadTitle")) { 
		 	    xmlContent += "	<dynamic-element index-type=\"keyword\" instance-id=\"ZtqM6drp\" name=\"Text94657287\" type=\"text\">\r\n" + 
			    		"		<dynamic-content language-id=\"en_US\"><![CDATA[" + contentEn.getString("ThreadTitle")  + "]]></dynamic-content>\r\n" + 
			    		"	</dynamic-element>\r\n";
		    }

		    if(contentEn.has("QuestionDetails")) {
		 	    xmlContent +=	"	<dynamic-element index-type=\"text\" instance-id=\"HXfAY6hy\" name=\"RichText10826413\" type=\"rich_text\">\r\n" + 
			    		"		<dynamic-content language-id=\"en_US\"><![CDATA[<p>"+ contentEn.getString("QuestionDetails") +"</p>]]></dynamic-content>\r\n" + 
			    		"	</dynamic-element>\r\n" ;
		    }
		    
		    if(contentEn.has("ProjectCategory")) {
		    	xmlContent +="	<dynamic-element index-type=\"keyword\" instance-id=\"tBkABnKP\" name=\"SelectFromList42258677\" type=\"select\">\r\n" +
				    		"		<dynamic-content language-id=\"en_US\"><![CDATA["+ contentEn.getString("ProjectCategory") +"]]></dynamic-content>\r\n" + 
				    		"	</dynamic-element>\r\n" ;
		    }
		    
		    if(contentEn.has("ThreadType")) {
		    	xmlContent +="	<dynamic-element index-type=\"keyword\" instance-id=\"tBkABnKP\" name=\"SelectFromList86123921\" type=\"select\">\r\n" +
		    			"		<dynamic-content language-id=\"en_US\"><![CDATA["+ contentEn.getString("ThreadType") +"]]></dynamic-content>\r\n" + 
		    			"	</dynamic-element>\r\n" ;
		    }
		    
		    if(contentEn.has("coverImagePath") && !helper.isEmpty(contentEn.getString("coverImagePath"))) {
		    	File f = new File(contentEn.getString("coverImagePath"));
		 	    JSONObject coverImage = helper.fileUploadByDL(f, themeDisplay, request, "Forums", contentEn.getString("coverImageName"));
	 	    	xmlContent += 	"	<dynamic-element index-type=\"text\" instance-id=\"r3TT432x\"  name=\"Upload07987255\" type=\"image\">\r\n" + 
	 	    			"		<dynamic-content language-id=\"en_US\"><![CDATA["+ coverImage.toString()+"]]></dynamic-content>\r\n" + 
	 	    			"	</dynamic-element>\r\n" ;
		    }
		    xmlContent += 	"  <dynamic-element index-type=\"keyword\" instance-id=\"S49rF2hj\" name=\"SingleSelection95451105\" type=\"radio\">\r\n" + 
		    		"		<dynamic-content language-id=\"en_US\"><![CDATA[Option91571492]]></dynamic-content>\r\n" + 
		    		"	</dynamic-element>" ;
		    xmlContent += "</root>";
		  
			article = JournalArticleLocalServiceUtil.addArticle(userId, groupId, folderId, titleMap, descriptionMap,
					xmlContent, ddmStructureKey, ddmTemplateKey, serviceContext);
		    
		    if(article != null) {
		    	AssetEntry entry = AssetEntryLocalServiceUtil.fetchEntry(JournalArticle.class.getName(),  article.getResourcePrimKey());

			    //Remove Current tags
	    		List<AssetTag> currentTags = AssetTagLocalServiceUtil.getAssetEntryAssetTags(entry.getEntryId());
			    AssetTagLocalServiceUtil.deleteAssetEntryAssetTags(entry.getEntryId(), currentTags);
			    
			    JSONArray tags =  new JSONArray(contentEn.getString("tagProgramValues"));
			    for(int i =0; i<tags.length(); i++) {
			    	JSONObject jotag = tags.getJSONObject(i);
			    	AssetTag tag = AssetTagLocalServiceUtil.fetchAssetTag(Long.valueOf(jotag.getString("id")));
				    if(tag != null)
				    	AssetTagLocalServiceUtil.addAssetEntryAssetTag(entry.getEntryId(), tag);
				    else {
				    	AssetTag newTag = AssetTagLocalServiceUtil.addTag(themeDisplay.getUserId(), groupId, jotag.getString("text"), serviceContext);
				    	AssetTagLocalServiceUtil.addAssetEntryAssetTag(entry.getEntryId(), newTag);
				    }
			    }
		    	res = true;
		    }	 
		} catch (Exception ex) {
			ex.printStackTrace();
			res = false;
	    }
	    return res;
	}
	
	public void getDropdownsData(ThemeDisplay themeDisplay, RenderRequest renderRequest, String ddmStructureKey) throws DocumentException, PortalException {
		DDMStructure struct = DDMStructureManagerUtil.getStructure(themeDisplay.getLayout().getGroupId(),  
				Long.valueOf(PortalUtil.getClassNameId(JournalArticle.class)), ddmStructureKey);
		JSONObject jo =  new JSONObject(struct.getDefinition());
		JSONArray ja = new JSONArray(jo.get("fields").toString());
		 
		for(int i = 0; i<ja.length(); i++) {
			JSONObject joa = ja.getJSONObject(i); 
			if(joa.get("type").equals("select")) {
				if(joa.get("name").equals("SelectFromList42258677")) {
					JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
					JSONArray jaRes = new JSONArray();
					for(int j = 0; j<joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j); 
						JSONObject res = new JSONObject();
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					}
					renderRequest.setAttribute("ProjectCategoryList", helper.toList(jaRes));
				}
				if(joa.get("name").equals("SelectFromList86123921")) {
					JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
					JSONArray jaRes = new JSONArray();
					for(int j = 0; j<joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j); 
						JSONObject res = new JSONObject();
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					}
					renderRequest.setAttribute("ThreadTypeList", helper.toList(jaRes));
				}
			}
		}
		
	}
	
	public JSONArray getThreadTypeList(ThemeDisplay themeDisplay, RenderRequest renderRequest, String ddmStructureKey) throws DocumentException, PortalException {
		DDMStructure struct = DDMStructureManagerUtil.getStructure(themeDisplay.getLayout().getGroupId(),  
				Long.valueOf(PortalUtil.getClassNameId(JournalArticle.class)), ddmStructureKey);
		JSONObject jo =  new JSONObject(struct.getDefinition());
		JSONArray ja = new JSONArray(jo.get("fields").toString());
		JSONArray jaRes = new JSONArray();
		 
		for(int i = 0; i<ja.length(); i++) {
			JSONObject joa = ja.getJSONObject(i); 
			if(joa.get("type").equals("select")) {
				if(joa.get("name").equals("SelectFromList86123921")) {
					JSONArray joOptions = new JSONArray(joa.get("options").toString()); 
					for(int j = 0; j<joOptions.length(); j++) {
						JSONObject option = joOptions.getJSONObject(j); 
						JSONObject res = new JSONObject();
						res.put("id", option.getString("value"));
						res.put("value", new JSONObject(option.get("label").toString()).get("en_US"));
						jaRes.put(res);
					} 
				}
			}
		}
		return jaRes;
	}
	
	public List<JournalArticle> getLatestVersionArticle(List<JournalArticle> totalArticles) {
		List<JournalArticle> journalList = new ArrayList<JournalArticle>();
		JournalArticle latestArticle ;
		for (JournalArticle journalArticle : totalArticles) {
			try {
				 latestArticle = JournalArticleLocalServiceUtil.getLatestArticle(journalArticle.getResourcePrimKey());
				if (journalList.contains(latestArticle)) {
					continue;
				} else if(latestArticle.getStatus() == 0){ //get published forums only
					journalList.add(latestArticle);
				}
			} catch (PortalException | SystemException e) {
				e.printStackTrace();
			}
		}
		return journalList;

	}
	
	private void getForumReplies(String forumId, Long userId, RenderRequest renderRequest, String queryText) throws PortalException {
		List<Object> replies = new ArrayList<Object>();
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		try { 
			JSONArray repliesJA = db_helper.getForumReplies(forumId, queryText);
			JSONArray resJA = new JSONArray(); 
			for(int i = 0; repliesJA.length() > i ; i++) {
				if (repliesJA.getJSONObject(i).getString("replyParentId").equals("0")) {
					JSONObject res = repliesJA.getJSONObject(i);
					res = setRepliesData(res, renderRequest, themeDisplay);
					resJA.put(res);
				}
			} 
			replies = helper.toList(resJA);
			renderRequest.setAttribute("replies", replies);
		} catch (JSONException | SQLException e) {
			e.printStackTrace();
		} 
	}
	
	private static JSONObject setRepliesData (JSONObject res, RenderRequest renderRequest, ThemeDisplay themeDisplay) throws PortalException, JSONException, SQLException {
		JSONObject forumDetailsCount = db_helper.getForumDetailsCount(res.getString("replyId"), "CommunityReplies_Details", "replyId");
		res.put("viewcount", forumDetailsCount.get("viewcount"));
		res.put("likecount", forumDetailsCount.get("likecount"));
		res.put("dislikecount", forumDetailsCount.get("dislikecount"));
		String imageUrl = "";
		if (res.has("fileEntryId")) {
			DLFileEntry image = DLFileEntryLocalServiceUtil
					.getFileEntry(res.getLong("fileEntryId"));

			if (image != null) {
				imageUrl = PortalUtil.getPortalURL(renderRequest) + "/documents/" + image.getGroupId() + "/"
						+ image.getFolderId() + "/" + image.getTitle() + "/" + image.getUuid() + "?t="
						+ System.currentTimeMillis();
			}
		}
		
		res.put("imageUrl", imageUrl);
		JSONObject userForumDetails = db_helper.getUserForumDetails(res.getString("replyId"), themeDisplay.getUserId(), "CommunityReplies_Details", "replyId");
		if(!userForumDetails.isEmpty()) {
			res.put("view", userForumDetails.has("view") ? userForumDetails.getBoolean("view") : false);
			res.put("liked", userForumDetails.has("liked") ? userForumDetails.getBoolean("liked") : false);
			res.put("dislike", userForumDetails.has("dislike") ? userForumDetails.getBoolean("dislike") : false);
		} 
		if(res.has("children") && !res.getJSONArray("children").isEmpty()) {
			JSONArray tmp = res.getJSONArray("children");
			res.remove("children");
			res.put("children", getForumRepliesChildrenDetails(tmp, res.getString("replyId"), renderRequest, themeDisplay, new JSONArray())); 
		}
		return res;
	}
	
	private static JSONArray getForumRepliesChildrenDetails(JSONArray replies, String parentId, RenderRequest renderRequest, ThemeDisplay themeDisplay, JSONArray children2) throws PortalException, SQLException { 
		JSONArray children = new JSONArray();
		try {
			for (int i = 0; i < replies.length(); i++) {
				JSONObject  rep = replies.getJSONObject(i); 
				if (rep.has("replyParentId") && rep.getString("replyParentId").equals(parentId)) {
					rep = setRepliesData(rep, renderRequest, themeDisplay);
					getForumRepliesChildrenDetails(replies, rep.getString("replyId"), renderRequest, themeDisplay, children); 
					children.put(rep);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return children;
	}
	
	private int getForumRepliesTotal(String articleId, String queryText) {
		int res = 0;
		try { 
			JSONArray repliesJA = db_helper.getForumReplies(articleId, queryText);   
			res = repliesJA.length();
		} catch (JSONException | SQLException e) {
			e.printStackTrace();
		} 
		return res;
	}

	private List<JournalArticle> getSearchedArticles(ThemeDisplay themeDisplay, List<JournalArticle> filtered, String queryText) throws DocumentException {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>(); 
		try {
			queryText = URLDecoder.decode(queryText.toLowerCase(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			queryText = queryText.toLowerCase();
		}
		
		for (JournalArticle article : filtered) {
			String content = article.getContentByLocale(themeDisplay.getLanguageId());
			Document document = SAXReaderUtil.read(content);
			String title = document.selectSingleNode("/root/dynamic-element[@name='Text94657287']").hasContent() ? 
					document.selectSingleNode("/root/dynamic-element[@name='Text94657287']/dynamic-content").getText() : "";
			String description = document.selectSingleNode("/root/dynamic-element[@name='RichText10826413']").hasContent() ? 
					document.selectSingleNode("/root/dynamic-element[@name='RichText10826413']/dynamic-content").getText() : "";
							 
	        if(title.toLowerCase().contains(queryText) || 
        		description.toLowerCase().contains(queryText)) {
	        	returnedArticles.add(article);
	        }
		}
		return returnedArticles;
	}
	
	private List<JournalArticle> getFilteredArticles(ThemeDisplay themeDisplay, List<JournalArticle> fetchedArticles, String filter) throws DocumentException, NumberFormatException, PortalException {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>(); 
		if(filter.contains("tag_")) {
			filter = filter.split("tag_")[1];
			for (JournalArticle article : fetchedArticles) {
				AssetEntry entry = AssetEntryLocalServiceUtil.fetchEntry(JournalArticle.class.getName(),  article.getResourcePrimKey());
				List<AssetTag> currentTags = AssetTagLocalServiceUtil.getAssetEntryAssetTags(entry.getEntryId());
				AssetTag tag = AssetTagLocalServiceUtil.getTag(Long.valueOf(filter));
				if(currentTags.contains(tag) && !returnedArticles.contains(article)) {
					returnedArticles.add(article);
				}
			}
		}else if(filter.contains("categ_")) {
			for (JournalArticle article : fetchedArticles) {
				String content = article.getContentByLocale(themeDisplay.getLanguageId());
				Document document = SAXReaderUtil.read(content);
				String fieldValue = document.selectSingleNode("/root/dynamic-element[@name='SelectFromList42258677']").hasContent() ? 
						document.selectSingleNode("/root/dynamic-element[@name='SelectFromList42258677']/dynamic-content").getText() : "";
				for(int i=0; i< filter.length();i++) {
					String val = filter.split("categ_")[1];
					if( fieldValue.equalsIgnoreCase(val) ) {
						returnedArticles.add(article);
						break;
					}
				}
			}
		}else if(filter.contains("type_") && !filter.equalsIgnoreCase("type_")) {
			for (JournalArticle article : fetchedArticles) {
				String content = article.getContentByLocale(themeDisplay.getLanguageId());
				Document document = SAXReaderUtil.read(content);
				String fieldValue = document.selectSingleNode("/root/dynamic-element[@name='SelectFromList86123921']") != null && document.selectSingleNode("/root/dynamic-element[@name='SelectFromList86123921']").hasContent() ? 
						document.selectSingleNode("/root/dynamic-element[@name='SelectFromList86123921']/dynamic-content").getText() : "";
				for(int i=0; i< filter.length();i++) {
					String val = filter.split("type_")[1];
					if( fieldValue.equalsIgnoreCase(val) ) {
						returnedArticles.add(article);
						break;
					}
				}
			}
		} else returnedArticles = fetchedArticles;
		
		return returnedArticles;
	}
	
	private List<JournalArticle> getFeautredForums(ThemeDisplay themeDisplay, List<JournalArticle> fetchedArticles) throws DocumentException, NumberFormatException, PortalException {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>(); 
		for (JournalArticle article : fetchedArticles) {
			String content = article.getContentByLocale(themeDisplay.getLanguageId());
			Document document = SAXReaderUtil.read(content);
			String fieldValue = document.selectSingleNode("/root/dynamic-element[@name='SingleSelection95451105']") != null && 
					document.selectSingleNode("/root/dynamic-element[@name='SingleSelection95451105']").hasContent() ? 
					document.selectSingleNode("/root/dynamic-element[@name='SingleSelection95451105']/dynamic-content").getText() : "";
			if( fieldValue.equalsIgnoreCase("Option74775694") ) {
				returnedArticles.add(article);
			}
		}
		
		return returnedArticles;
	}
	
	private List<JournalArticle> getHomeForums(ThemeDisplay themeDisplay, List<JournalArticle> fetchedArticles) throws DocumentException, NumberFormatException, PortalException {
		List<JournalArticle> returnedArticles = new ArrayList<JournalArticle>(); 
		for (JournalArticle article : fetchedArticles) {
			String content = article.getContentByLocale(themeDisplay.getLanguageId());
			Document document = SAXReaderUtil.read(content);
			if(document !=null) {
				String fieldValue = document.selectSingleNode("/root/dynamic-element[@name='SingleSelection31538376']") != null &&
						document.selectSingleNode("/root/dynamic-element[@name='SingleSelection31538376']").hasContent() ? 
						document.selectSingleNode("/root/dynamic-element[@name='SingleSelection31538376']/dynamic-content").getText() : "";
				if( fieldValue.equalsIgnoreCase("Option74775694") ) {
					returnedArticles.add(article);
				}
			}
		}
		return returnedArticles;
	}
	
	private String getUserRoleName(int userRole) {
		String res = "";
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.InvestorUser.name())))
				res = "InvestorUserRole";
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.IndustryUserIAM.name())))
				res = "IndustryUserRole";
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.EnergyCompanyUserIAM.name())))
				res = "EnergyCompanyUserRole";
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.EnergyHubUserIAM.name())))
				res = "EnergyHubUserRole";
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.PolicyMakerIAM.name())))
				res = "PolicyMakerUserRole";
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.EntrepreneurUserIAM.name())))
				res = "EntrepreneurUserRole";
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.ResearcherUserIAM.name())))
				res = "ResearcherUserRole";
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.AcademicUserIAM.name())))
				res = "AcademicUserRole";
			if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.AdministratorIAM.name())))
				res = "AdministratorRole";
		return res;
	}
}