package comments.management.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

public class db_helper {
	private final static Configuration _portletConfiguration = ConfigurationFactoryUtil
			.getConfiguration(PortalClassLoaderUtil.getClassLoader(), "portlet");
	
	public static void createCommentsTable() throws SQLException {
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement(); 
		
		try {
	    	ResultSet rs = con.getMetaData().getTables(null, null, "Comments" , null);
	    	if (!rs.next()) {
	    		 String sql = "CREATE TABLE Comments " +
	                     " (article_id INTEGER not NULL, " +
	                     " userId INTEGER not NULL, " + 
	                     " comment LONGTEXT, " + 
	                     " commentId INTEGER not NULL AUTO_INCREMENT, " + 
	                     " commentParentId INTEGER not NULL, " + 
	                     " createDate DATETIME, " + 
	                     " PRIMARY KEY (commentId))";
	    		stmt.executeUpdate(sql);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			con.close();
		}
	}
	
	public static void createCommentDetails() throws SQLException {
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement(); 
		
		try {
	    	ResultSet rs = con.getMetaData().getTables(null, null, "CommentDetails" , null);
	    	if (!rs.next()) {
	    		 String sql = "CREATE TABLE CommentDetails ( " + 
	    		 		"  commentId INTEGER not NULL, " + 
	    		 		"  userId INTEGER not NULL, " + 
	    		 		"  liked boolean DEFAULT false, " + 
	    		 		"  dislike boolean DEFAULT false, " + 
	    		 		"  modifiedDate DATETIME, " + 
	    		 		"  PRIMARY KEY (commentId, userId))" ;
	    		stmt.executeUpdate(sql);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			con.close();
		}
	}
	
	public static void addComment(Long userId, String comment,String commentParentId, String article_id) throws SQLException {
		Connection con = null;
		try {
			con = DataAccess.getConnection();
	    	PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO Comments "
	    			+ "(userId, comment, commentParentId, article_id, createDate ) VALUES (?,?,?,?, NOW())");
	    	preparedStatement.setLong(1, userId);
	    	preparedStatement.setString(2, comment);
	    	preparedStatement.setString(3, helper.isEmpty(commentParentId) ? "0" : commentParentId);
	    	preparedStatement.setString(4, article_id);
	    	preparedStatement.executeUpdate();
	    	preparedStatement.close();
	    	 
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			if( con!=null ) {
				con.close();
			}
	 	}
	}
	
	public static JSONArray getComments(String article_id, ThemeDisplay themeDisplay) throws SQLException {
		Connection con = null;
		JSONArray replies = new JSONArray();
		JSONArray res = new JSONArray();
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("select * from Comments where article_id=?");
	    	preparedStatement.setString(1, article_id); 
	    	 
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while (rs.next()) { 
	    		JSONObject details = setReplies(rs, themeDisplay);
	    		replies.put(details);
			} 
	    	 
	    	for (int i = 0; i < replies.length(); i++) {
				if (replies.getJSONObject(i).getString("commentParentId").equals("0")) {
					JSONObject rep = replies.getJSONObject(i); 
					rep.put("children", GetTreeJA(replies, replies.getJSONObject(i).getString("commentId")));
					res.put(rep);
				}
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
	
	private static JSONObject setReplies(ResultSet rs, ThemeDisplay themeDisplay) throws ParseException, PortalException {
		JSONObject details = new JSONObject();
		try {
			details.put("comment", rs.getString("comment"));
			details.put("commentId", rs.getString("commentId"));
			PrettyTime p = new PrettyTime();
			details.put("createDateDisplay", p.format(rs.getTimestamp("createDate")));
			String formatedDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(rs.getTimestamp("createDate"));
			details.put("createDate", formatedDate);
			details.put("commentParentId", rs.getString("commentParentId"));
			details.put("article_id", rs.getString("article_id"));
			User commentUser = UserLocalServiceUtil.fetchUser(rs.getLong("userId"));
			details.put("userId", rs.getString("userId"));
			details.put("commentuserName", commentUser.getFirstName() + " " + commentUser.getLastName());
			
			String token = util.getToken(_portletConfiguration);
			User user = themeDisplay.getUser();
			String userString = util.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), user.getEmailAddress());
			String roleName = "";
			if(new JSONObject(userString).has("userApplicationRoleId")) {
				int userRole = new JSONObject(userString).getInt("userApplicationRoleId");
				roleName = UserTypes.getUserTypeName(String.valueOf(userRole));
				details.put("commentUserProfileUrl", "detail?userDetail&categName=" 
						+ UserTypes.getUserTypeName(String.valueOf(userRole)) +"&groupId="
						+ themeDisplay.getLayout().getGroupId()+"&articleId=" + commentUser.getUserId());
			}
			details.put("commentuserRole", roleName);
			details.put("userPortrait", commentUser.getPortraitURL(themeDisplay));

		} catch (JSONException | SQLException e) {
			e.printStackTrace();
		}
		return details;
	}
	
	private static JSONArray GetTreeJA(JSONArray replies, String parentId) {
		JSONArray allrepsChild = new JSONArray();
		try {
			for (int i = 0; i < replies.length(); i++) {
				if (replies.getJSONObject(i).has("commentParentId") && replies.getJSONObject(i).getString("commentParentId").equals(parentId)) {
					JSONObject rep = replies.getJSONObject(i); 
					rep.put("children", GetTreeJA(replies, replies.getJSONObject(i).getString("commentId"))); 
					allrepsChild.put(rep);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return allrepsChild;
	}
	
	public static void deleteComment(long commentId) throws SQLException {
		Connection con = DataAccess.getConnection();
//		PreparedStatement preparedStatement = con.prepareStatement("WITH recursive VALS  AS ( " + 
//				"  SELECT * FROM energyhub.Comments " + 
//				"  WHERE commentId = ? " + 
//				"  UNION ALL SELECT cr.* " + 
//				"  FROM energyhub.Comments cr " + 
//				"  INNER JOIN VALS v ON cr.commentParentId = v.commentId " + 
//				" ) " + 
//				" DELETE FROM energyhub.Comments WHERE commentId IN ( " + 
//				"       SELECT commentId FROM VALS)");
//		preparedStatement.setLong(1, commentId);
//		preparedStatement.executeUpdate();
//		preparedStatement.close();
		
		PreparedStatement preparedStatement = con.prepareStatement("SET @@SESSION.max_sp_recursion_depth=255");
		preparedStatement.executeUpdate();
		preparedStatement = con.prepareStatement("call recursive_function(?)");
		preparedStatement.setLong(1, commentId);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		con.close();
	}
	
	public static void deleteForumData(long id) throws SQLException {
		Connection con = DataAccess.getConnection();
		
		PreparedStatement preparedStatement = con.prepareStatement("DELETE cr, fd FROM energyhub.Comments cr " + 
				" INNER JOIN energyhub.CommentDetails fd " + 
				" WHERE cr.article_id = fd.article_id  and cr.article_id =?");
		preparedStatement.setLong(1, id);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		con.close();
	}
	
	public static void addCommentDetails(String id, Long user_id, Boolean like, Boolean dislike) throws SQLException {
		Connection con = null;
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement selectStatement = con.prepareStatement("select count(*) from CommentDetails where"
	    			+ "	 commentId =? and userId=?");
	    	selectStatement.setString(1, id);
	    	selectStatement.setLong(2, user_id); 
	    	
	    	boolean createRec = true;
	    	ResultSet rs = selectStatement.executeQuery();
	    	while (rs.next()) { 
	    		createRec = rs.getInt(1)==0;
			}
	    	selectStatement.close();
	    	if( createRec ) {
	    		PreparedStatement insertStatement = con.prepareStatement("INSERT INTO CommentDetails " 
	    				+ " ( commentId, userId, liked, dislike, modifiedDate) VALUES (?,?,?,?, NOW())");
	    		insertStatement.setString(1, id);
	    		insertStatement.setLong(2, user_id);
	    		insertStatement.setBoolean(3, like);
	    		insertStatement.setBoolean(4, dislike);  
	    		insertStatement.executeUpdate();
		    	insertStatement.close();
	    	}else {
    			PreparedStatement updateStatement = con.prepareStatement("UPDATE CommentDetails SET modifiedDate =NOW(), " 
	    				+ " liked = ? , dislike = ? WHERE commentId = ? AND userId = ? ");
	    		updateStatement.setBoolean(1, like);
	    		updateStatement.setBoolean(2, dislike);   
	    		updateStatement.setString(3, id);  
	    		updateStatement.setLong(4, user_id);  
	    		updateStatement.executeUpdate();
	    		updateStatement.close();
	    	} 
	    	
	    	//Update modifiedDate in CommentDetails
	    	PreparedStatement updateStatement = con.prepareStatement("UPDATE CommentDetails set modifiedDate = now()  where commentId = ? ");
	    	updateStatement.setString(1, id);  
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
	
	public static JSONObject getUserCommentDetails(String id, long userId, String tableName, String idName) throws SQLException {
		Connection con = null;
		JSONObject details = new JSONObject();
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("select * from " + tableName
	    			+ "	where "+ idName +"=? and userId=?");
	    	preparedStatement.setString(1, id);
	    	preparedStatement.setLong(2, userId); 
	    	 
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while (rs.next()) { 
	    		details.put("liked", rs.getBoolean("liked"));
	    		details.put("dislike", rs.getBoolean("dislike"));
			}
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			if( con!=null ) {
				con.close();
			}
	 	}
		return details;
	}
	
	public static JSONObject getCommentDetailsCount(String id, String tableName, String idName) throws SQLException {
		Connection con = null;
		JSONObject details = new JSONObject();
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement likeCountStatement = con.prepareStatement("select count(*) as likecount from "+ tableName +" where "+ idName +"=? and  liked =  true");
	    	likeCountStatement.setString(1, id);
	    	 
	    	ResultSet rs = likeCountStatement.executeQuery();
	    	while (rs.next()) { 
	    		details.put("likecount", rs.getString("likecount")); 
			}
	    	likeCountStatement.close();
	    	
	    	PreparedStatement dislikeCountStatement = con.prepareStatement("select count(*) as dislikecount from "+ tableName +" where "+ idName +"=? and  dislike =  true");
	    	dislikeCountStatement.setString(1, id);
	    	 
	    	rs = dislikeCountStatement.executeQuery();
	    	while (rs.next()) { 
	    		details.put("dislikecount", rs.getString("dislikecount")); 
			}
	    	dislikeCountStatement.close();
			
	    	PreparedStatement updateStatement = con.prepareStatement("select modifiedDate from CommentDetails where commentId = ? ");
	    	updateStatement.setString(1, id);  
	    	rs = updateStatement.executeQuery();
	    	while (rs.next()) { 
	    		details.put("modifiedDate", rs.getString("modifiedDate"));
			}
	    	updateStatement.close();
	    	
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			if( con!=null ) {
				con.close();
			}
	 	}
		return details;
	}
	
	public static JSONArray getForumsIdsforUserReplies(long userId, String queryText, ThemeDisplay themeDisplay) throws SQLException {
		Connection con = null;
		JSONArray replies = new JSONArray(); 
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("select * from Comments where userId=?  and comment like N'%" + queryText + "%'");
	    	preparedStatement.setLong(1, userId);
	    	 
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while (rs.next()) { 
	    		JSONObject details = setReplies(rs, themeDisplay);
	    		replies.put(details);
			}  
	    	
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			if( con!=null ) {
				con.close();
			}
	 	}
		return replies;
	}
}
