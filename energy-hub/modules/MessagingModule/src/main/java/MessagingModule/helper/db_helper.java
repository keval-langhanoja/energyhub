package MessagingModule.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

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
	                     " PRIMARY KEY (messageId))";
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
	    		 		"  groupName NVARCHAR(100), " + 
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
	    		 		"  main_userEmail NVARCHAR(1000), " + 
	    		 		"  blockedUser INTEGER, " + 
	    		 		"  reportedUser INTEGER, " + 
	    		 		"  chatId INTEGER, " + 
	    		 		"  reportedDescription LONGTEXT, " + 
	    		 		"  reportedReason NVARCHAR(1000), " + 
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
	
	public static long addNewChat(List<Long> participants, String groupName) throws SQLException {
		Connection con = null;
		long chatId = 0;
		try {
			con = DataAccess.getConnection();
			for(Long paticipant : participants) {
				if (chatId == 0) {
					PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO UserChats "
							+ "(userId, groupName) VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);
					preparedStatement.setLong(1, paticipant);
					preparedStatement.setString(2, groupName);
					int result = preparedStatement.executeUpdate();
					if (result > 0) {
					   try { 
						   ResultSet rs = preparedStatement.getGeneratedKeys();
						   if (rs.next()) {
					    	  chatId = rs.getLong(1);
					       }
					    }catch (Exception e) {
							e.printStackTrace();
						}
					}
					preparedStatement.close();
				}else {
					PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO UserChats "
							+ "(userId, chatId, groupName ) VALUES (?,?,?)");
					preparedStatement.setLong(1, paticipant);
					preparedStatement.setLong(2, chatId);
					preparedStatement.setString(3, groupName);
					preparedStatement.executeUpdate();
					preparedStatement.close();
				}
			}
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			if( con!=null ) {
				con.close();
			}
	 	}
		return chatId;
	}
	
	public static void sendMessage(String message,Long chatId, Long userId) throws SQLException {
		Connection con = null;
		long messageId = 0;
		try {
			con = DataAccess.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO ChatMessages "
					+ "(chatId, message, sentDate, senderId ) VALUES (?,?, NOW(), ?)",Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, chatId);
			preparedStatement.setString(2, message);
			preparedStatement.setLong(3, userId); 
			
			int result = preparedStatement.executeUpdate();
			if (result > 0) {
			   try { 
				   ResultSet rs = preparedStatement.getGeneratedKeys();
				   if (rs.next()) {
					   messageId = rs.getLong(1);
					   PreparedStatement sentToStmt = con.prepareStatement("SELECT * from UserChats "
					   		+ " where chatId=? and userId not in(?)" );
					   sentToStmt.setLong(1, chatId);
					   sentToStmt.setLong(2, userId); 
					   
					   ResultSet sentToRS = sentToStmt.executeQuery();
					   List<Long> users = new ArrayList<Long>();
				    	while(sentToRS.next()) { 
				    		users.add(sentToRS.getLong("userId"));
				    	} 
						preparedStatement.close();
					   
					   addMessageInfo(messageId, chatId, userId, users);
			       }
			    }catch (Exception e) {
					e.printStackTrace();
				}
			}
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if( con!=null ) {
				con.close();
			}
		}
	}
	
	public static void addMessageInfo(Long messageId,Long chatId, Long userId, List<Long> users) throws SQLException {
		Connection con = null;
		try {
			con = DataAccess.getConnection();
			for(Long user : users) {
				PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO MessageInfo "
						+ "(chatId, messageId, userId ) VALUES (?,?,?)");
				preparedStatement.setLong(1, chatId);
				preparedStatement.setLong(2, messageId);
				preparedStatement.setLong(3, user);
				preparedStatement.executeUpdate();
				preparedStatement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if( con!=null ) {
				con.close();
			}
		}
	}
	
	public static void deleteMessage(Long chatId, Long userId) throws SQLException {
		Connection con = null;
		try {
			con = DataAccess.getConnection();
			PreparedStatement updateStatement = con.prepareStatement("UPDATE UserChats SET "
					+ " isDeleted = ?  WHERE chatId = ? and userId = ?");
    		updateStatement.setBoolean(1, true);
    		updateStatement.setLong(2, chatId);  
    		updateStatement.setLong(3, userId);  
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
	
	public static void blockUser(Long blockedUserId, Long main_userId) throws SQLException {
		Connection con = null;
		try {
			User mainUser = UserLocalServiceUtil.fetchUser(main_userId);
			
			con = DataAccess.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO UserBlocksReports "
					+ "(main_userId, main_userEmail, blockedUser) VALUES (?,?,?)");
			preparedStatement.setLong(1, main_userId);
			preparedStatement.setString(2, mainUser.getEmailAddress());
			preparedStatement.setLong(3, blockedUserId);
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
	
	public static void UnblockUser(Long main_userId, Long blockedUserId) throws SQLException {
		Connection con = null;
		try {
			con = DataAccess.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement("UPDATE UserBlocksReports SET "
					+ " blockedUser = null where blockeduser = ? and main_userId = ?");
			preparedStatement.setLong(1, blockedUserId);
			preparedStatement.setLong(2, main_userId); 
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
	
	public static JSONObject isBlocked(Long main_userId, Long otherUserId) throws SQLException {
		Connection con = null;
		JSONObject res = new JSONObject(); 
		try {
			con = DataAccess.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement("select main_userId from UserBlocksReports "
					+ " where (main_userId = ? and blockedUser = ?) " + 
					"or (main_userId = ? and blockedUser = ?)   group by main_userId");
			preparedStatement.setLong(1, main_userId);
			preparedStatement.setLong(2, otherUserId);
			preparedStatement.setLong(3, otherUserId);
			preparedStatement.setLong(4, main_userId);
			
			ResultSet rs = preparedStatement.executeQuery();
	    	while(rs.next()) { 
	    		int count = rs.getFetchSize();
	    		res.put("isBlocked", count == 0 ?  false : true);
	    		if(count == 1) {
	    			res.put("blockedBy", rs.getLong("mainID"));
	    		}else res.put("blockedBy", "both");
	    	} 
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
	
	public static void reportUser(Long reportedUserId, Long main_userId, String chatId,
			String main_userEmail, String reason, String description) throws SQLException {
		
		if(reason.equals("spam")) reason ="It's suspicious or spam";
		else if(reason.equals("abusive")) reason ="It's abusive or harmful";
		else if(reason.equals("Other")) reason ="Other reasons";
		
		Connection con = null;
		try {
			con = DataAccess.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO UserBlocksReports "
					+ "(main_userId, main_userEmail, reportedUser, reportedReason, reportedDescription, chatId) "
					+ " VALUES (?,?,?,?,?,?)");
			preparedStatement.setLong(1, main_userId);
			preparedStatement.setString(2, main_userEmail);
			preparedStatement.setLong(3, reportedUserId);
			preparedStatement.setString(4, reason);
			preparedStatement.setString(5, description);
			preparedStatement.setString(6, chatId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			
			int counterReports = 0;
			
			PreparedStatement reportCounterStmt = con.prepareStatement("select COUNT(*) as counterReports from UserBlocksReports where "
	    			+ " reportedUser=? ");
			reportCounterStmt.setLong(1, reportedUserId); 
	    	
	    	ResultSet CounterRS = reportCounterStmt.executeQuery();
	    	while(CounterRS.next()) { 
	    		counterReports = CounterRS.getInt("counterReports");
	    	} 
			
			User reportedUser = UserLocalServiceUtil.fetchUser(reportedUserId);
			User mainUser = UserLocalServiceUtil.fetchUser(main_userId);
			
			sendPostPhp("http://energyhub-lb.com:8080/sendmail/reportemail.php",mainUser.getFullName(), main_userEmail, 
					reportedUser.getFullName(), reportedUser.getEmailAddress(), reason, description, counterReports); 
			

//			String no_reply = "no-reply@undp.com";
//			String host = "localhost";
//			Properties properties = System.getProperties();
//			properties.setProperty("mail.smtp.host", host);
//			
//			MailMessage message = new MailMessage();
//			message.setFrom(new InternetAddress(no_reply));
//			message.setTo(new InternetAddress(no_reply));
//			message.setSubject("Report User"); 
//			
//			message.setBody("<p>Kindly note the following Report :</p></br>"
//                    + "<p>"+mainUser.getFullName()+" ("+ main_userEmail +") has reported " 
//						+reportedUser.getFullName() + " ("+reportedUser.getEmailAddress()+"), "
//                    + "<p>with the following reason:</p></br>"
//                    + "<p>"+ reason +"</p></br>"
//                    + (!helper.isEmpty(description) ? "<p> and Description "+ description +"</p></br>" : "")
//                    + "This user has ("+counterReports +") reports so far.</p>");
//
//			message.setHTMLFormat(true);
//			MailServiceUtil.sendEmail(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if( con!=null ) {
				con.close();
			}
		}
	}
	
	public static String sendPostPhp(String restURL, String mainUserName, String main_userEmail, 
			String reportedUserName, String reportedUserEmail, String reason, String description,
			int counterReports) {
		String response = "";
		try {
	        String command = "curl -X GET "+ restURL +"?mainUserName="+mainUserName
	        		+"&mainUserEmail="+main_userEmail
	        		+"&reportedUserName="+reportedUserName
	        		+"&reportedUserEmail="+reportedUserEmail
			        +"&reason="+reason
			        +"&description="+description
			        +"&counterReports="+counterReports;
	        Process process = Runtime.getRuntime().exec(command);
	        process.getInputStream();
	    } catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static List<Long> getUserChats(Long userId) throws SQLException {//IMPLEMENT CHNAGES FROM unreadtotal in POST LOGIN AS WELL!
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
	
	public static String getChatGroupName(Long chatId) throws SQLException {//IMPLEMENT CHNAGES FROM unreadtotal in POST LOGIN AS WELL!
		Connection con = null;
		String res = "";
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("select groupName from UserChats where "
	    			+ " chatId=?");
	    	preparedStatement.setLong(1, chatId);  
	    	
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while(rs.next()) { 
	    		res= rs.getString("groupName");
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
	
	public static List<Long> getUsersInChat(Long chatId) throws SQLException {
		Connection con = null;
		List<Long> res = new ArrayList<Long>();
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("select * from UserChats where chatId=? and leftGroup=?");
	    	preparedStatement.setLong(1, chatId);  
	    	preparedStatement.setBoolean(2, false);  
	    	
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while(rs.next()) { 
	    		res.add(rs.getLong("userId"));
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

	public static JSONArray getChatMessages(long chatId) throws SQLException {
		Connection con = null;
		JSONArray res = new JSONArray();
		try {
			con = DataAccess.getConnection();
    		PreparedStatement chatsStmt = con.prepareStatement("select * from ChatMessages where "
	    			+ " chatId=? order by sentDate asc");
    		chatsStmt.setLong(1, chatId); 
    		ResultSet chatRs = chatsStmt.executeQuery();
    		boolean firstIteration = true;
    		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");  

    		while(chatRs.next()) {
	    		JSONObject chats = new JSONObject();
	    		chats.put("message", chatRs.getString("message"));
	    		chats.put("messageid", chatRs.getInt("messageId"));
	    		chats.put("sentdate",formatter.format(chatRs.getTimestamp("sentDate").getTime()));
	    		User sender = UserLocalServiceUtil.getUser(chatRs.getLong("senderId"));
	    		chats.put("sendername", sender.getFullName());
	    		chats.put("senderfirstname", sender.getFirstName());
	    		chats.put("senderemail", sender.getEmailAddress());
	    		chats.put("senderid", chatRs.getLong("senderId"));
	    		chats.put("islastreceived", firstIteration? true : false);
	    		
	    		if(firstIteration) {
	    			chats.put("lastreceivedmessage", chatRs.getString("message"));
		    		chats.put("lastreceivedmessagesentdate",formatter.format(chatRs.getTimestamp("sentDate").getTime()));
    			}
	    		
	    		res.put(chats);
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
	
	public static int getUnreadMessagesCounter(Long chatId, Long userId) throws SQLException { //IMPLEMENT CHNAGES FROM unreadtotal in POST LOGIN AS WELL!
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
	
	public static void readMessage(Long chatId, Long userId) throws SQLException {
		Connection con = null;
		try {
			con = DataAccess.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement("UPDATE MessageInfo SET "
					+ " isRead = true  WHERE chatId = ? and userId =?");
			preparedStatement.setLong(1, chatId);
			preparedStatement.setLong(2, userId);
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

	public static void leaveGroup(Long chatId, Long userId) throws SQLException {
		Connection con = null;
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("update UserChats set leftGroup=?"
	    			+ " where chatId=? and userId =?");
	    	preparedStatement.setBoolean(1, true);  
	    	preparedStatement.setLong(2, chatId);  
	    	preparedStatement.setLong(3, userId);  
	    	
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

	public static void deleteChats1Year() throws SQLException {
		Connection con = null;
		try {
			con = DataAccess.getConnection();
	    	PreparedStatement preparedStatement = con.prepareStatement("DELETE from ChatMessages where "
	    			+ " sentDate  < DATE_SUB(NOW(),INTERVAL 1 YEAR)");
	    	
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

	public static JSONArray getReports() throws SQLException {
		Connection con = null;
		JSONArray res = new JSONArray();
		try {
			con = DataAccess.getConnection();
    		PreparedStatement chatsStmt = con.prepareStatement("select * from UserBlocksReports where "
	    			+ " reportedUser is not null");
    		 
    		ResultSet chatRs = chatsStmt.executeQuery();
    		while(chatRs.next()) {
    			User reportedUser = UserLocalServiceUtil.fetchUser(chatRs.getLong("reportedUser"));
    			User reportedByUser = UserLocalServiceUtil.fetchUser(chatRs.getInt("main_userId"));
	    		JSONObject reports = new JSONObject();
	    		reports.put("id", chatRs.getString("id"));
	    		reports.put("chatId", chatRs.getString("chatId"));
	    		
	    		reports.put("main_userId", chatRs.getInt("main_userId"));
	    		reports.put("reportedByUserName", reportedByUser.getFullName());
	    		
	    		reports.put("reportedUser", chatRs.getLong("reportedUser"));
	    		reports.put("reportedUserName", reportedUser.getFullName());
	    		
	    		reports.put("reportedReason", chatRs.getString("reportedReason"));
	    		reports.put("reportedDescription", chatRs.getString("reportedDescription"));
	    		res.put(reports);
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
	
	public static List<Long> getBlockedBy(Long blockedUserId) throws SQLException {//IMPLEMENT CHNAGES FROM unreadtotal in POST LOGIN AS WELL!
		Connection con = null;
		List<Long> res = new ArrayList<Long>();
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("select main_userId from UserBlocksReports where "
	    			+ " blockedUser=?");
	    	preparedStatement.setLong(1, blockedUserId);  
	    	
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while(rs.next()) { 
	    		res.add(rs.getLong("main_userId"));
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
	
	public static List<Long> getOpenedChats(Long currentUser) throws SQLException {
		Connection con = null;
		List<Long> res = new ArrayList<Long>();
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("select userId FROM UserChats where  userId != ? and  "
	    			+ " chatId  in ( SELECT chatId FROM UserChats where userId = ? and groupName =''  "
	    			+ " and isDeleted = false )");
	    	preparedStatement.setLong(1, currentUser);  
	    	preparedStatement.setLong(2, currentUser);  
	    	
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while(rs.next()) { 
	    		res.add(rs.getLong("userId"));
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
