package reportsModule.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.portlet.RenderRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import reportsModule.portlet.ReportsModulePortlet;


public class db_helper {
	
	public static JSONArray getAttendanceList(long eventId) throws SQLException {
		Connection con = null;
		JSONArray res = new JSONArray();
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("select * from EventAttendance where eventId=? and yes=?");
	    	preparedStatement.setLong(1, eventId); 
	    	preparedStatement.setBoolean(2, true); 
	    	 
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while (rs.next()) { 
	    		JSONObject attendee = new JSONObject();
	    		attendee.put("userId", rs.getLong("userId"));
	    		res.put(attendee);
			} 
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			if( con!=null ) {
				con.close();
			}
	 	}
		return res;
	}

	public static boolean GetUserAttendance(long userId, long eventId) throws SQLException {
		Connection con = null;
		boolean res = false;
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("select * from EventAttendance where eventId=? and userId=?");
	    	preparedStatement.setLong(1, eventId); 
	    	preparedStatement.setLong(2, userId); 
	    	 
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while (rs.next()) { 
	    		if(rs.getBoolean("yes")) res = true;
			} 
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			if( con!=null ) {
				con.close();
			}
 		}
		return res;
	}
	
	public static JSONObject getForumDetailsCount(String id, String tableName, String idName) throws SQLException {
		Connection con = null;
		JSONObject details = new JSONObject();
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement viewCountStatement = con.prepareStatement("select count(*) as viewcount from "+ tableName 
	    			+" where "+ idName +"=? and  view =  true");
	    	viewCountStatement.setString(1, id);
	    	 
	    	ResultSet rs = viewCountStatement.executeQuery();
	    	while (rs.next()) { 
	    		details.put("viewcount", rs.getString("viewcount")); 
			}
	    	viewCountStatement.close();
	    	
	    	PreparedStatement likeCountStatement = con.prepareStatement("select count(*) as likecount from "+ tableName 
	    			+" where "+ idName +"=? and  liked =  true");
	    	likeCountStatement.setString(1, id);
	    	 
	    	rs = likeCountStatement.executeQuery();
	    	while (rs.next()) { 
	    		details.put("likecount", rs.getString("likecount")); 
			}
	    	likeCountStatement.close();
	    	
	    	PreparedStatement dislikeCountStatement = con.prepareStatement("select count(*) as dislikecount from "+ tableName 
	    			+" where "+ idName +"=? and  dislike =  true");
	    	dislikeCountStatement.setString(1, id);
	    	 
	    	rs = dislikeCountStatement.executeQuery();
	    	while (rs.next()) { 
	    		details.put("dislikecount", rs.getString("dislikecount")); 
			}
	    	dislikeCountStatement.close();
	    	 
	    	PreparedStatement commentsCountStatement = con.prepareStatement("select count(*) as commentscount  "
	    			+ "  from Community_Replies where "+ idName +"=?");
	    	commentsCountStatement.setString(1, id);
	    	 
	    	rs = commentsCountStatement.executeQuery();
	    	while (rs.next()) { 
	    		details.put("commentscount", rs.getString("commentscount")); 
			}
	    	commentsCountStatement.close();
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			if( con!=null ) {
				con.close();
			}
	 	}
		return details;
	}

	public static boolean getUserActivityOnThread(long userId, String forum_id) throws SQLException {
		Connection con = null;
		boolean res = false;
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("SELECT Count(*) as count FROM Forum_Details "
	    			+ " where userId = ? and forum_id = ? and (view = true or liked = true or dislike = true);  ");
	    	preparedStatement.setLong(1, userId); 
	    	preparedStatement.setString(2, forum_id); 
	    	 
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while (rs.next()) { 
	    		if(rs.getInt("count") > 0) res = true;
			} 
	    	
	    	
	    	PreparedStatement preparedStatement2 = con.prepareStatement("SELECT Count(*) as count FROM community_replies "
	    			+ " where userId = ? and forum_id = ?");
	    	preparedStatement2.setLong(1, userId); 
	    	preparedStatement2.setString(2, forum_id); 
	    	 
	    	ResultSet rs2 = preparedStatement2.executeQuery();
	    	while (rs2.next()) { 
	    		if(rs2.getInt("count") > 0) res = true;
			} 
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			if( con!=null ) {
				con.close();
			}
 		}
		return res;
	}

	public static JSONArray getTop(String topType, ThemeDisplay themeDisplay, RenderRequest renderRequest, JSONObject filter) throws SQLException {
		Connection con = null;
		JSONArray res = new JSONArray();
		try {
			con = DataAccess.getConnection();
			String query = "";
			
			if(topType.equalsIgnoreCase("Upvoted"))
				query = " SELECT forum_id, Count(*) as count FROM Forum_Details where liked = true group by forum_id order by count desc LIMIT 10";
			
			if(topType.equalsIgnoreCase("DownVoted"))
				query = " SELECT forum_id, Count(*) as count FROM Forum_Details where dislike = true group by forum_id order by count desc LIMIT 10";
			
			if(topType.equalsIgnoreCase("Viewed"))
				query = " SELECT forum_id, Count(*) as count FROM Forum_Details where view = true group by forum_id order by count desc LIMIT 10";
			
			if(topType.equalsIgnoreCase("Commented"))
				query = " SELECT forum_id, Count(*) as count FROM community_replies group by forum_id order by count desc LIMIT 10";
			
			
	    	PreparedStatement preparedStatement = con.prepareStatement(query);
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while (rs.next()) {
	    		Long forumId = rs.getLong("forum_id");
	    		String count = rs.getString("count");
	    		String userRole = getUserRole(topType, forumId);
	    		JSONObject jo = getThreadData(forumId, themeDisplay, renderRequest, userRole, count, filter);
	    		if(!jo.isEmpty())
	    			res.put(jo); 
			} 
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			if( con!=null ) {
				con.close();
			}
 		}
		return res;
	}

	private static String getUserRole(String topType, Long forumId) throws SQLException {
		Connection con = null;
		String res = "";
		try {
			con = DataAccess.getConnection();
			String query = "";
			
			if(topType.equalsIgnoreCase("Upvoted"))
				query = " SELECT roleId, Count(*) as count FROM energyhub.Forum_Details where liked = true and forum_id = ? "
						+ " group by roleid order by count desc limit 1";
			
			if(topType.equalsIgnoreCase("DownVoted"))
				query = " SELECT roleId, Count(*) as count FROM energyhub.Forum_Details where dislike = true and forum_id = ? "
						+ " group by roleid order by count desc limit 1";
			
			if(topType.equalsIgnoreCase("Viewed"))
				query = " SELECT roleId, Count(*) as count FROM energyhub.Forum_Details where view = true and forum_id = ? "
						+ " group by roleid order by count desc limit 1";
			
			if(topType.equalsIgnoreCase("Commented"))
				query = "  SELECT roleId, Count(*) as count FROM energyhub.community_replies where forum_id = ? "
						+ " group by roleid order by count desc LIMIT 1;";
			
			
	    	PreparedStatement preparedStatement = con.prepareStatement(query);
	    	preparedStatement.setLong(1, forumId); 
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while (rs.next()) { 
	    		res = UserTypes.getUserTypeName(rs.getString("roleId"));
			} 
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			if( con!=null ) {
				con.close();
			}
 		}
		return res;
	}

	public static JSONObject getThreadData(Long threadId, ThemeDisplay themeDisplay,
			RenderRequest renderRequest, String userRole, String count, JSONObject filter) throws PortalException, DocumentException, SQLException {
		
		JSONObject userThreadsJO = new JSONObject();
		
		AssetCategory threadCategory = AssetCategoryLocalServiceUtil.getCategories().stream()
				.filter(categ -> categ.getName().equalsIgnoreCase(ReportsModulePortlet.ThreadCategoryName)).findFirst().orElse(null);
		
		JournalArticle article = null;
		
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery(); 
		assetEntryQuery.setAllCategoryIds(new long[] { threadCategory.getCategoryId() });

		List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
		for (AssetEntry ae : assetEntryList) {
			JournalArticleResource journalArticleResource = JournalArticleResourceLocalServiceUtil.getJournalArticleResource(ae.getClassPK());
			JournalArticle tmp = JournalArticleLocalServiceUtil.getLatestArticle(journalArticleResource.getResourcePrimKey());
			
			if(tmp.getArticleId().equalsIgnoreCase(String.valueOf(threadId))) {
				article = tmp;
				break;
			}
		}
		
		List<JournalArticle> tmpListArticle = new ArrayList<JournalArticle>();
		if(article != null) {
			tmpListArticle.add(article);
			if(filter.has("ThreadType") && filter.getJSONArray("ThreadType").length()>0 ) {
				tmpListArticle = ReportsModulePortlet.getFilteredArticles(themeDisplay,tmpListArticle, 
						filter.getJSONArray("ThreadType"),ReportsModulePortlet.ThreadType);
			}
		}
		
		if(tmpListArticle.size()>0) {
			String content = article.getContentByLocale(themeDisplay.getLanguageId());
			Document document = SAXReaderUtil.read(content);
			
			String title = document.selectSingleNode("/root/dynamic-element[@name='"+ ReportsModulePortlet.ThreadTitle+"']").hasContent()?
					document.selectSingleNode("/root/dynamic-element[@name='"+ReportsModulePortlet.ThreadTitle+"']/dynamic-content").getText() : "";
			String type = document.selectSingleNode("/root/dynamic-element[@name='"+ReportsModulePortlet.ThreadType+"']").hasContent()?
					document.selectSingleNode("/root/dynamic-element[@name='"+ReportsModulePortlet.ThreadType+"']/dynamic-content").getText() : "";
			String typeName = ReportsModulePortlet.getValueName(themeDisplay, renderRequest, article.getDDMStructure(), ReportsModulePortlet.ThreadType, type);
			
			userThreadsJO .put("threadTitle", title);
			userThreadsJO.put("threadType", typeName);
			userThreadsJO.put("userRole", userRole);
			userThreadsJO.put("count", count);
			
			String csVData = title +"," +typeName + "," + userRole+ "," + count;
			csVData += "___newLine___";
			
			userThreadsJO.put("csVData", csVData);
		}
		return userThreadsJO;
	}

	public static String getCommentsTotal(long id) throws SQLException {
		Connection con = null;
		String res = "";
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("SELECT count(*) as count FROM comments where article_id = ?");
	    	preparedStatement.setLong(1, id);  
	    	 
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while (rs.next()) { 
	    		res = rs.getString("count");
			} 
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			if( con!=null ) {
				con.close();
			}
 		}
		return res;
	}

	public static List<Long> getMostRole(String tableName, Long id, String idField) throws SQLException {
		Connection con = null;
		List<Long> roleIds = new ArrayList<Long>();
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("SELECT userId FROM " + tableName + " where "+idField+" = ?");
	    	preparedStatement.setLong(1, id);  
	    	 
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while (rs.next()) {
	    		Long userId = rs.getLong("userId");
	    		User creator = UserLocalServiceUtil.getUser(userId);
	    		List<Role> roles = creator.getRoles();
	    		for(Role role : roles) {
	    			if(!role.getName().equalsIgnoreCase("User")) {
	    				if(helper.isNumeric(role.getDescription("en_US"))) {
	    					roleIds.add(Long.valueOf(role.getDescription("en_US")));
	    					break;
	    				}
	    			}
	    		}
			} 
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			if( con!=null ) {
				con.close();
			}
 		}
		return roleIds;
	} 
	
	public static String getMostRoleName(String tableName, Long id, String idField) throws SQLException {
		String res = "";
		List<Long> roleIds = getMostRole(tableName, id, idField);
		if(!roleIds.isEmpty())
			res = UserTypes.getUserTypeName(String.valueOf(mostCommon(roleIds)));
		return res;
	}
	
	public static <T> T mostCommon(List<T> list) {
	    Map<T, Integer> map = new HashMap<>();

	    for (T t : list) {
	        Integer val = map.get(t);
	        map.put(t, val == null ? 1 : val + 1);
	    }

	    Entry<T, Integer> max = null;

	    for (Entry<T, Integer> e : map.entrySet()) {
	        if (max == null || e.getValue() > max.getValue())
	            max = e;
	    }

	    return max.getKey();
	}

	public static String getAttendeesTotal(long id) throws SQLException {
		Connection con = null;
		String res = "";
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("SELECT count(*) as count FROM EventAttendance where eventId = ? and yes=true");
	    	preparedStatement.setLong(1, id);  
	    	 
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while (rs.next()) { 
	    		res = rs.getString("count");
			} 
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			if( con!=null ) {
				con.close();
			}
 		}
		return res;
	}
}