package PostLogin.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;

public class db_helper {	
	public static void createChatMessagesTable() throws SQLException {
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement(); 
		
		try {
	    	ResultSet rs = con.getMetaData().getTables(null, null, "ChatMessages" , null);
	    	if (!rs.next()) {
	    		 String sql = "CREATE TABLE ChatMessages " +
	                     " (messageId INTEGER not NULL AUTO_INCREMENT, " +
	                     " message LONGTEXT, " + 
	                     " sentDate DATETIME, " + 
	                     " chatId INTEGER not NULL, " + 
	                     " senderId INTEGER not NULL, " + 
	                     "  PRIMARY KEY  (messageId))";
	    		stmt.executeUpdate(sql);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			con.close();
		}
	}
	
	public static void createMessageInfoTable() throws SQLException {
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement(); 
		
		try {
	    	ResultSet rs = con.getMetaData().getTables(null, null, "MessageInfo" , null);
	    	if (!rs.next()) {
	    		 String sql = "CREATE TABLE MessageInfo " +
	                     " (id INTEGER not NULL AUTO_INCREMENT, " +
	                     " messageId INTEGER not NULL, " + 
	                     " isRead boolean DEFAULT false, " + 
	                     " chatId INTEGER not NULL, " + 
	                     " userId INTEGER not NULL, " + 
	                     " PRIMARY KEY (id))";
	    		stmt.executeUpdate(sql);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			con.close();
		}
	}
	
	public static void createUserChatsTable() throws SQLException {
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement(); 
		
		try {
	    	ResultSet rs = con.getMetaData().getTables(null, null, "UserChats" , null);
	    	if (!rs.next()) {
	    		 String sql = "CREATE TABLE UserChats ( " + 
	    		 		"  chatId INTEGER not NULL AUTO_INCREMENT, " + 
	    		 		"  userId INTEGER not NULL, " + 
	    		 		"  isDeleted boolean DEFAULT false, " + 
	    		 		"  leftGroup boolean DEFAULT false, " + 
	    		 		"  groupName VARCHAR(100), " + 
	    		 		"  PRIMARY KEY (chatId, userId))" ;
	    		stmt.executeUpdate(sql);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			con.close();
		}
	}
	
	public static void createUserBlocksReportsTable() throws SQLException {
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement(); 
		
		try {
	    	ResultSet rs = con.getMetaData().getTables(null, null, "UserBlocksReports" , null);
	    	if (!rs.next()) {
	    		 String sql = "CREATE TABLE UserBlocksReports ( " + 
	    		 		"  id INTEGER not NULL AUTO_INCREMENT, " + 
	    		 		"  main_userId INTEGER not NULL, " + 
	    		 		"  main_userEmail VARCHAR(1000), " + 
	    		 		"  blockedUser INTEGER, " + 
	    		 		"  reportedUser INTEGER, " + 
	    		 		"  chatId INTEGER, " + 
	    		 		"  reportedDescription LONGTEXT, " + 
	    		 		"  reportedReason VARCHAR(1000), " + 
	    		 		"  PRIMARY KEY (id))" ;
	    		stmt.executeUpdate(sql);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			con.close();
		}
	}
	
	public static List<Long> getUserChats(Long userId) throws SQLException {
		Connection con = null;
		List<Long> res = new ArrayList<Long>();
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("select * from UserChats where "
	    			+ " userId=? and  isDeleted=? and leftGroup=?");
	    	preparedStatement.setLong(1, userId); 
	    	preparedStatement.setBoolean(2, false);
	    	preparedStatement.setBoolean(3, false);
	    	
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while(rs.next()) { 
	    		res.add(rs.getLong("chatId"));
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
	
	public static int getUnreadMessagesCounter(Long chatId, Long userId) throws SQLException {
		Connection con = null;
		int res = 0;
		try {
			con = DataAccess.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement("select count(*) as count from MessageInfo"
					+ " where chatId= ? and userId = ? and isRead = false");
			preparedStatement.setLong(1, chatId);
			preparedStatement.setLong(2, userId);
			
			ResultSet rs = preparedStatement.executeQuery();
	    	while(rs.next()) { 
	    		res = rs.getInt("count");
	    	} 
			preparedStatement.close();
			
			preparedStatement.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if( con!=null ) {
				con.close();
			}
		}
		return res;
	}
	
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
	
	public static void createProjectPermTable() throws SQLException {
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement(); 
		
		try {
	    	ResultSet rs = con.getMetaData().getTables(null, null, "ProjectPermission" , null);
	    	if (!rs.next()) {
	    		 String sql = "CREATE TABLE ProjectPermission " +
	                     "(articleId INTEGER not NULL, " +
	                     " email VARCHAR(255), " + 
	                     " PRIMARY KEY (articleId, email))"; 
	    		stmt.executeUpdate(sql);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			con.close();
		}
	}
}
