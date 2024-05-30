package community.db_helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import community.constants.UserTypes;
import community.helper.helper;

public class db_helper {
	private final static Configuration _portletConfiguration = ConfigurationFactoryUtil
			.getConfiguration(PortalClassLoaderUtil.getClassLoader(), "portlet");
	
	public static void createRepliesTable() throws SQLException {
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement(); 
		
		try {
	    	ResultSet rs = con.getMetaData().getTables(null, null, "Community_Replies" , null);
	    	if (!rs.next()) {
	    		 String sql = "CREATE TABLE Community_Replies " +
	                     " (forum_id INTEGER not NULL, " +
	                     " userId INTEGER not NULL, " + 
	                     " roleId INTEGER not NULL, " +
	                     " reply VARCHAR(1000), " + 
	                     " replyId INTEGER not NULL AUTO_INCREMENT, " + 
	                     " replyParentId INTEGER not NULL, " + 
	                     " fileEntryId INTEGER , " + 
	                     " createDate DATETIME, " + 
	                     " PRIMARY KEY (replyId))";
	    		stmt.executeUpdate(sql);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			con.close();
		}
	}
	
	public static void addReply(Long userId, String reply,String replyParentId, String forum_id, 
			String fileEntryId, Long roleId) throws SQLException {
		Connection con = null;
		try {
			String fileentrySql = helper.isEmpty(fileEntryId) ? "" : ", fileEntryId";
			String fileentrySql2 = helper.isEmpty(fileEntryId) ? "" : ", ?";
			con = DataAccess.getConnection();
	    	PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO Community_Replies "
	    			+ "(userId, roleId, reply, replyParentId, forum_id, createDate  "+ fileentrySql +") VALUES (?,?,?,?,?, NOW()"+ fileentrySql2 +")");
		    	preparedStatement.setLong(1, userId);
		    	preparedStatement.setLong(2, roleId != null ?roleId : 0);
		    	preparedStatement.setString(3, reply);
		    	preparedStatement.setString(4, helper.isEmpty(replyParentId) ? "0" : replyParentId);
		    	preparedStatement.setString(5, forum_id);
		    	if(!helper.isEmpty(fileEntryId))
		    		preparedStatement.setString(6, fileEntryId);
		    	preparedStatement.executeUpdate();
	    	
	    	preparedStatement.close();
	    	
	    	//Update modifiedDate in Forum_Details
	    	PreparedStatement updateStatement = con.prepareStatement("UPDATE Forum_Details set modifiedDate = now()  where forum_id = ? ");
	    	updateStatement.setString(1, forum_id);
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
	
	public static JSONArray getForumReplies(String forum_Id, String queryText) throws SQLException {
		Connection con = null;
		JSONArray replies = new JSONArray();
		JSONArray res = new JSONArray();
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("select * from Community_Replies where forum_Id=? and reply like N'%" + queryText + "%'");
	    	preparedStatement.setString(1, forum_Id); 
	    	 
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while (rs.next()) { 
	    		JSONObject details = setReplies(rs);
	    		replies.put(details);
			} 
	    	 
	    	for (int i = 0; i < replies.length(); i++) {
				if (replies.getJSONObject(i).getString("replyParentId").equals("0")) {
					JSONObject rep = replies.getJSONObject(i); 
					rep.put("children", GetTreeJA(replies, replies.getJSONObject(i).getString("replyId")));
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
	
	private static JSONObject setReplies(ResultSet rs) throws ParseException {
		JSONObject details = new JSONObject();
		try {
			details.put("reply", rs.getString("reply"));
			details.put("replyId", rs.getString("replyId"));
			String formatedDate = new SimpleDateFormat("EEE MMM d YYYY HH:mm").format(rs.getTimestamp("createDate"));
			details.put("createDate", formatedDate);
			details.put("replyParentId", rs.getString("replyParentId"));
			details.put("forum_Id", rs.getString("forum_Id"));
			User replyUser = UserLocalServiceUtil.fetchUser(rs.getLong("userId"));
			details.put("userId", rs.getString("userId"));
			details.put("replyuserName", replyUser.getFirstName() + " " + replyUser.getLastName());
			
			String token = db_helper.getToken(_portletConfiguration);
			User user = replyUser;
			String userString = db_helper.getUserbyEmail(token, _portletConfiguration.get("get-user-email"), user.getEmailAddress());
			
			if(!helper.isEmpty(userString) && new JSONObject(userString).has("roleId")) {
				int userRole = new JSONObject(userString).getInt("userApplicationRoleId");
				if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.InvestorUser.name()))){
					details.put("userRole", "InvestorUserRole");
				}
				if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.IndustryUserIAM.name()))){
					details.put("userRole", "IndustryUserRole");
				}
				if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.EnergyCompanyUserIAM.name()))){
					details.put("userRole", "EnergyCompanyUserRole");
				}
				if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.EnergyHubUserIAM.name()))){
					details.put("userRole", "EnergyHubUserRole");
				}
				if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.PolicyMakerIAM.name()))){
					details.put("userRole", "PolicyMakerRole");
				}
				if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.EntrepreneurUserIAM.name()))){
					details.put("userRole", "EntrepreneurUserRole");
				}
				if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.ResearcherUserIAM.name()))){
					details.put("userRole", "ResearcherUserRole");
				}
				if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.AcademicUserIAM.name()))){
					details.put("userRole", "AcademicUserRole");
				}
				if(userRole == Integer.valueOf(UserTypes.getUserTypeValue(UserTypes.AdministratorIAM.name()))){
					details.put("userRole", "AdministratorRole");
				}
			}
			details.put("fileEntryId", rs.getString("fileEntryId"));
		} catch (JSONException | SQLException e) {
			e.printStackTrace();
		}
		return details;
	}
	
	private static JSONArray GetTreeJA(JSONArray replies, String parentId) {
		JSONArray allrepsChild = new JSONArray();
		try {
			for (int i = 0; i < replies.length(); i++) {
				if (replies.getJSONObject(i).has("replyParentId") && replies.getJSONObject(i).getString("replyParentId").equals(parentId)) {
					JSONObject rep = replies.getJSONObject(i); 
					rep.put("children", GetTreeJA(replies, replies.getJSONObject(i).getString("replyId"))); 
					allrepsChild.put(rep);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return allrepsChild;
	}
	
	public static void deleteReply(long replyId) throws SQLException {
		Connection con = DataAccess.getConnection();
//		PreparedStatement preparedStatement = con.prepareStatement("WITH recursive VALS  AS ( " + 
//				"  SELECT * FROM Community_Replies " + 
//				"  WHERE replyId = ? " + 
//				"  UNION ALL SELECT cr.* " + 
//				"  FROM Community_Replies cr " + 
//				"  INNER JOIN VALS v ON cr.replyParentId = v.replyId " + 
//				" ) " + 
//				" DELETE FROM Community_Replies WHERE replyId IN ( " + 
//				"       SELECT replyId FROM VALS)");
//		preparedStatement.setLong(1, replyId);
//		preparedStatement.executeUpdate();
//		preparedStatement.close();
		
		PreparedStatement preparedStatement = con.prepareStatement("SET @@SESSION.max_sp_recursion_depth=255");
		preparedStatement.executeUpdate();
		preparedStatement = con.prepareStatement("call recursive_function(?)");
		preparedStatement.setLong(1, replyId);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		con.close();
	}
	
	public static void deleteForumData(long id) throws SQLException {
		Connection con = DataAccess.getConnection();
		
		PreparedStatement preparedStatement = con.prepareStatement("DELETE cr, fd FROM Community_Replies cr " + 
				" INNER JOIN Forum_Details fd " + 
				" WHERE cr.forum_id = fd.forum_id  and cr.forum_id =?");
		preparedStatement.setLong(1, id);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		con.close();
	}
	
	public static void createForumDetails(String tableName, String idName) throws SQLException {
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement(); 
		
		try {
	    	ResultSet rs = con.getMetaData().getTables(null, null, tableName , null);
	    	if (!rs.next()) {
	    		 String sql = "CREATE TABLE "+ tableName +" ( " + 
	    		 		"  "+ idName +" INTEGER not NULL, " + 
	    		 		"  userId INTEGER not NULL, " + 
	    		 		"  roleId INTEGER not NULL, " + 
	    		 		"  liked boolean DEFAULT false, " + 
	    		 		"  dislike boolean DEFAULT false, " + 
	    		 		"  view boolean DEFAULT false, " + 
	    		 		"  modifiedDate DATETIME, " + 
	    		 		"  PRIMARY KEY ("+ idName +", userId) " + 
	    		 		")" ;
	    		stmt.executeUpdate(sql);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			con.close();
		}
	}
	
	public static void addForumDetails(String id, Long user_id, Boolean like, Boolean dislike,
			Boolean view, String tableName, String idName, Long roleId) throws SQLException {
		Connection con = null;
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement selectStatement = con.prepareStatement("select count(*) from "+ tableName +" where"
	    			+ "	 "+ idName +"=? and userId=?");
	    	selectStatement.setString(1, id);
	    	selectStatement.setLong(2, user_id); 
	    	
	    	boolean createRec = true;
	    	ResultSet rs = selectStatement.executeQuery();
	    	while (rs.next()) { 
	    		createRec = rs.getInt(1)==0;
			}
	    	selectStatement.close();
	    	if( createRec ) {
	    		PreparedStatement insertStatement = con.prepareStatement("INSERT INTO " + tableName
	    				+ " ( "+ idName +", userId, roleId, liked, dislike, view) VALUES (?,?,?,?,?,?)");
	    		insertStatement.setString(1, id);
	    		insertStatement.setLong(2, user_id);
	    		insertStatement.setLong(3, roleId);
	    		insertStatement.setBoolean(4, like);
	    		insertStatement.setBoolean(5, dislike);  
	    		insertStatement.setBoolean(6, view);  
	    		insertStatement.executeUpdate();
		    	insertStatement.close();
	    	}else {
	    		if(!view) {
	    			PreparedStatement updateStatement = con.prepareStatement("UPDATE "+ tableName +" SET"
		    				+ " liked = ? , dislike = ? WHERE "+ idName +" = ? AND userId = ?");
		    		updateStatement.setBoolean(1, like);
		    		updateStatement.setBoolean(2, dislike);   
		    		updateStatement.setString(3, id);  
		    		updateStatement.setLong(4, user_id);  
		    		updateStatement.executeUpdate();
		    		updateStatement.close();
	    		} else  {
	    			PreparedStatement updateStatement = con.prepareStatement("UPDATE "+ tableName +" SET"
		    				+ " view = ?   WHERE "+ idName +" = ? AND userId = ?"); 
		    		updateStatement.setBoolean(1, view);  
		    		updateStatement.setString(2, id);  
		    		updateStatement.setLong(3, user_id);  
		    		updateStatement.executeUpdate();
		    		updateStatement.close();
	    		}
	    		
	    	} 
	    	//Update modifiedDate in Forum_Details
	    	String forum_Id ="";
			if(tableName.equalsIgnoreCase("CommunityReplies_Details"))  {
				PreparedStatement preparedStatement = con.prepareStatement("select forum_id from Community_Replies where replyId = ? ");
		    	preparedStatement.setString(1, id); 
		    	 
		    	rs = preparedStatement.executeQuery();
		    	while (rs.next()) { 
		    		forum_Id= rs.getString("forum_id");
				}
		    	preparedStatement.close();
			} else if(tableName.equalsIgnoreCase("Forum_Details")) 
				forum_Id = id;
			
	    	PreparedStatement updateStatement = con.prepareStatement("UPDATE Forum_Details set modifiedDate = now()  where forum_id = ? ");
	    	updateStatement.setString(1, forum_Id);  
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
	
	public static JSONObject getUserForumDetails(String id, long userId, String tableName, String idName) throws SQLException {
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
	    		details.put("view", rs.getBoolean("view"));
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
	
	public static JSONObject getForumDetailsCount(String id, String tableName, String idName) throws SQLException {
		Connection con = null;
		JSONObject details = new JSONObject();
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement viewCountStatement = con.prepareStatement("select count(*) as viewcount from "+ tableName +" where "+ idName +"=? and  view =  true");
	    	viewCountStatement.setString(1, id);
	    	 
	    	ResultSet rs = viewCountStatement.executeQuery();
	    	while (rs.next()) { 
	    		details.put("viewcount", rs.getString("viewcount")); 
			}
	    	viewCountStatement.close();
	    	
	    	PreparedStatement likeCountStatement = con.prepareStatement("select count(*) as likecount from "+ tableName +" where "+ idName +"=? and  liked =  true");
	    	likeCountStatement.setString(1, id);
	    	 
	    	rs = likeCountStatement.executeQuery();
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
	    	 
	    	String forum_Id ="";
			if(tableName.equalsIgnoreCase("CommunityReplies_Details"))  {
				PreparedStatement preparedStatement = con.prepareStatement("select forum_id from Community_Replies where replyId = ? ");
		    	preparedStatement.setString(1, id); 
		    	 
		    	rs = preparedStatement.executeQuery();
		    	while (rs.next()) { 
		    		forum_Id= rs.getString("forum_id");
				}
		    	preparedStatement.close();
			} else if(tableName.equalsIgnoreCase("Forum_Details")) 
				forum_Id = id;
			
	    	PreparedStatement updateStatement = con.prepareStatement("select modifiedDate from Forum_Details where forum_id = ? ");
	    	updateStatement.setString(1, forum_Id);  
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
	
	public static JSONArray getForumsIdsforUserReplies(long userId, String queryText) throws SQLException {
		Connection con = null;
		JSONArray replies = new JSONArray(); 
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("select * from Community_Replies where userId=?  and reply like N'%" + queryText + "%'");
	    	preparedStatement.setLong(1, userId);
	    	 
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while (rs.next()) { 
	    		JSONObject details = setReplies(rs);
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
	
	public static String getToken(Configuration portletconfiguration) {
		String response = "", token = "";
		try {
			String restURL = portletconfiguration.get("iam-get-token");
			URL url = new URL(restURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("dataType", "json");

			OutputStream outputStream = connection.getOutputStream();
			JSONObject jsonData = new JSONObject();
			jsonData.put("id", portletconfiguration.get("client-id"));
			jsonData.put("secret", portletconfiguration.get("client-secret"));
			byte[] b = jsonData.toString().getBytes("UTF-8");
			outputStream.write(b);
			outputStream.flush();
			outputStream.close();
			int responseCode = connection.getResponseCode();
			if (responseCode == HttpsURLConnection.HTTP_OK) {
				String line;
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = br.readLine()) != null) {
					response += line;
				}
				JSONObject jsonToken = new JSONObject(response);
				token = jsonToken.getString("token");
			} else {
				response = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return token;
	}

	public static String getUserbyEmail(String token, String restURL, String data) {
		String response = "";
		try {
			URL url = new URL(restURL + data);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", "Bearer " + token);

			int responseCode = connection.getResponseCode();
			if (responseCode == HttpsURLConnection.HTTP_OK) {
				String line;
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = br.readLine()) != null) {
					response += line;
				}
			} else {
				response = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
}
