package comments.management.portlet;

import comments.management.constants.CommentsManagementPortletKeys;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
import comments.management.helper.*;

/**
 * @author vyo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=CommentsManagement",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + CommentsManagementPortletKeys.COMMENTSMANAGEMENT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CommentsManagementPortlet extends MVCPortlet {
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		try {
			HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest)); 
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			String articleId = httpReq.getParameter("articleId");
	    	
			db_helper.createCommentsTable();
			db_helper.createCommentDetails();
			
			String sort = helper.ifEmpty(httpReq.getParameter("sort"), "desc");
			String sortField = helper.ifEmpty(httpReq.getParameter("sortField"), "createDate");
			boolean showComments = false;
			
			String pageName = httpReq.getRequestURI().replace("/", "");
			
			renderRequest.setAttribute("currentUserProfile", themeDisplay.getUser().getPortraitURL(themeDisplay)); 
			if(pageName.equalsIgnoreCase("resources-detail")) { //Publication
				showComments = true;
			}else if(pageName.equalsIgnoreCase("news-detail")) { //News
				showComments = true;
			} else if(pageName.equalsIgnoreCase("program-detail-comments")){ //OnGoing Projects
				JournalArticle article = JournalArticleLocalServiceUtil.fetchLatestArticle(Long.valueOf(articleId));
				String content = article.getContentByLocale(themeDisplay.getLanguageId());
				Document document = SAXReaderUtil.read(content);
				String showCommentsData = document.selectSingleNode("/root/dynamic-element[@name='SingleSelection04730283']").hasContent() ? 
						document.selectSingleNode("/root/dynamic-element[@name='SingleSelection04730283']/dynamic-content").getText() : "";
				
				if(showCommentsData.equalsIgnoreCase("Option53543878")) {// YES
					showComments = true;
				}else if (showCommentsData.equalsIgnoreCase("Option06092560")) { //NO
					showComments = false;
				}
			}

			renderRequest.setAttribute("showComments", showComments); 
			if(showComments) {
				getComments(articleId, themeDisplay.getUserId(), renderRequest, sort, sortField);
			}
			
			renderRequest.setAttribute("isSignedIn", themeDisplay.isSignedIn()); 
			renderRequest.setAttribute("instanceId", themeDisplay.getPortletDisplay().getId()); 
			renderRequest.setAttribute("articleId", articleId); 
			super.render(renderRequest, renderResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException, PortletException {
		resourceResponse.setContentType("text/html");
		PrintWriter out = resourceResponse.getWriter();
		String key = ParamUtil.getString(resourceRequest, "key");
		String retVal = "";
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
			
			if(key.equalsIgnoreCase("like") || key.equalsIgnoreCase("dislike")) {
				boolean  like = false, dislike = false;
				if(key.equalsIgnoreCase("like")) like = true;
				if(key.equalsIgnoreCase("dislike")) dislike = true;
				String id =  ParamUtil.getString(resourceRequest, "articleId");
				
				db_helper.addCommentDetails(id, themeDisplay.getUserId(), like, dislike);
			}else if(key.equalsIgnoreCase("Postcomment")) {
				String articleId =  ParamUtil.getString(resourceRequest, "articleId");
				String commentText =  ParamUtil.getString(resourceRequest, "commentText");
				String commentParentId =  ParamUtil.getString(resourceRequest, "commentParentId");
				db_helper.addComment(themeDisplay.getUserId(), commentText, commentParentId, articleId);
				
			} else if(key.equalsIgnoreCase("deleteForum")) {
				String articleId = ParamUtil.getString(resourceRequest, "id");
				deleteForum(articleId, themeDisplay.getLayout().getGroupId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println(String.valueOf(retVal));
		out.flush();
		super.serveResource(resourceRequest, resourceResponse);
	}
	
	private void deleteForum(String articleId, long groupId) throws PortalException {
		try {
			List<JournalArticle> versiones=JournalArticleLocalServiceUtil.getArticles(groupId, articleId);
			for (int j = 0; j < versiones.size(); j++) {
			   JournalArticle version=versiones.get(j);
			   JournalArticleLocalServiceUtil.deleteArticle(groupId, version.getArticleId(), version.getVersion(), null,null);
			}
			db_helper.deleteForumData(Long.valueOf(articleId));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void getComments(String articleId, Long userId, RenderRequest renderRequest, String sort, String sortField) throws PortalException {
		List<Object> comments = new ArrayList<Object>();
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		try { 
			JSONArray repliesJA = db_helper.getComments(articleId, themeDisplay);
			JSONArray resJA = new JSONArray(); 
			for(int i = 0; repliesJA.length() > i ; i++) {
				if (repliesJA.getJSONObject(i).getString("commentParentId").equals("0")) {
					JSONObject res = repliesJA.getJSONObject(i);
					res = setRepliesData(res, renderRequest, themeDisplay);
					resJA.put(res);
				}
			} 
			
			JSONArray sortedJsonArray = resJA;
			if(sortField.equalsIgnoreCase("top")) {//viewCount
				List<JSONObject> jsonValues = new ArrayList<JSONObject>();
			    for (int i = 0; i < resJA.length(); i++) {
			        jsonValues.add(resJA.getJSONObject(i));
			    }
				 Collections.sort(jsonValues, new Comparator<JSONObject>() {
			         private final String KEY_NAME = "likecount";
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
			    for (int i = 0; i < resJA.length(); i++) {
			        jsonValues.add(resJA.getJSONObject(i));
			    }
			    Collections.sort(jsonValues, new Comparator<JSONObject>() {
			    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			         @Override
			         public int compare(JSONObject o1, JSONObject o2) {
			            try {
			            	return formatter.parse(o1.has("modifiedDate") ? o1.getString("modifiedDate") : o1.getString("createDate"))
			            			.compareTo(formatter.parse(o2.has("modifiedDate") ? o2.getString("modifiedDate") : o2.getString("createDate")));
			            } catch(JSONException | ParseException e) {
			               e.printStackTrace();
			            }
			            return 0;
			         } 
			      }.reversed());
				 sortedJsonArray = new JSONArray(jsonValues);
			} else if (sortField.equalsIgnoreCase("createDate")) {
					List<JSONObject> jsonValues = new ArrayList<JSONObject>();
				    for (int i = 0; i < resJA.length(); i++) {
				        jsonValues.add(resJA.getJSONObject(i));
				    }
				    Collections.sort(jsonValues, new Comparator<JSONObject>() {
				    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				         @Override
				         public int compare(JSONObject o1, JSONObject o2) {
				            try {
				            	return formatter.parse(o1.getString("createDate")).compareTo(formatter.parse(o2.getString("createDate")));
				            } catch(JSONException | ParseException e) {
				               e.printStackTrace();
				            }
				            return 0;
				         } 
				      }.reversed());
					 sortedJsonArray = new JSONArray(jsonValues);
				 }
			
			comments = helper.toList(sortedJsonArray);
			renderRequest.setAttribute("comments", comments);
			renderRequest.setAttribute("commentsTotal", comments.size());
		} catch (JSONException | SQLException e) {
			e.printStackTrace();
		} 
	}
	
	private static JSONObject setRepliesData (JSONObject res, RenderRequest renderRequest, ThemeDisplay themeDisplay) throws PortalException, JSONException, SQLException {
		JSONObject forumDetailsCount = db_helper.getCommentDetailsCount(res.getString("commentId"), "CommentDetails", "commentId");
		res.put("likecount", forumDetailsCount.has("likecount") ? forumDetailsCount.get("likecount") : 0);
		res.put("dislikecount", forumDetailsCount.has("dislikecount") ? forumDetailsCount.get("dislikecount") : 0);
		res.put("modifiedDate", forumDetailsCount.has("modifiedDate")? forumDetailsCount.get("modifiedDate") : null);

		JSONObject userForumDetails = db_helper.getUserCommentDetails(res.getString("commentId"), themeDisplay.getUserId(), "CommentDetails", "commentId");
		if(!userForumDetails.isEmpty()) {
			res.put("liked", userForumDetails.has("liked") ? userForumDetails.getBoolean("liked") : false);
			res.put("dislike", userForumDetails.has("dislike") ? userForumDetails.getBoolean("dislike") : false);
		} 
		if(res.has("children") && !res.getJSONArray("children").isEmpty()) {
			JSONArray tmp = res.getJSONArray("children");
			res.remove("children");
			res.put("children", getCommentRepliesChildrenDetails(tmp, res.getString("commentId"), renderRequest, themeDisplay, new JSONArray())); 
		}
		return res;
	}
	
	private static JSONArray getCommentRepliesChildrenDetails(JSONArray replies, String parentId, RenderRequest renderRequest, ThemeDisplay themeDisplay, JSONArray children2) throws PortalException, SQLException { 
		JSONArray children = new JSONArray();
		try {
			for (int i = 0; i < replies.length(); i++) {
				JSONObject  rep = replies.getJSONObject(i); 
				if (rep.has("commentParentId") && rep.getString("commentParentId").equals(parentId)) {
					rep = setRepliesData(rep, renderRequest, themeDisplay);
					getCommentRepliesChildrenDetails(replies, rep.getString("commentId"), renderRequest, themeDisplay, children); 
					children.put(rep);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return children;
	}

}