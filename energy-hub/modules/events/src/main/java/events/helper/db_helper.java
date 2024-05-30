package events.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONObject;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;


public class db_helper {
	public static void createEventAttendanceTable() throws SQLException {
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement(); 
		
		try {
	    	ResultSet rs = con.getMetaData().getTables(null, null, "EventAttendance" , null);
	    	if (!rs.next()) {
	    		 String sql = "CREATE TABLE EventAttendance " +
	                    "  (eventId INTEGER not NULL, " +
	                    "  userId INTEGER not NULL, " + 
	                    "  fullName VARCHAR(100), " +
	                    "  yes boolean DEFAULT false, " + 
    		 			"  maybe boolean DEFAULT false, " + 
    		 			"  no boolean DEFAULT false, " + 
                    	"  PRIMARY KEY (eventId, userId))";
	    		stmt.executeUpdate(sql);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			con.close();
		}
	}
	
	public static void addAttendance(Long eventId, Long userId, String fullName, boolean yes, boolean maybe, boolean no) throws SQLException {
		Connection con = null;
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("select * from EventAttendance where eventId=? and userId=?");
	    	preparedStatement.setLong(1, eventId);
	    	preparedStatement.setLong(2, userId);
	    	 
	    	ResultSet rs = preparedStatement.executeQuery();
	    	if (rs.next()) { 
		    	PreparedStatement updateStmt = con.prepareStatement("UPDATE EventAttendance SET "
		    			+ " yes=?, maybe=?, no=? WHERE eventId=? and userId=? ");
		    	updateStmt.setBoolean(1, yes);
		    	updateStmt.setBoolean(2, maybe);
		    	updateStmt.setBoolean(3, no);
		    	updateStmt.setLong(4, eventId);
		    	updateStmt.setLong(5, userId);
		    	updateStmt.executeUpdate();
		    	updateStmt.close();
			} else {
		    	PreparedStatement inserStmt = con.prepareStatement("INSERT INTO EventAttendance "
		    			+ "(eventId, userId, fullName, yes, maybe, no ) VALUES (?,?,?,?,?,?)");
		    	inserStmt.setLong(1, eventId);
		    	inserStmt.setLong(2, userId);
		    	inserStmt.setString(3, fullName);
		    	inserStmt.setBoolean(4, yes);
		    	inserStmt.setBoolean(5, maybe);
		    	inserStmt.setBoolean(6, no);
		    	inserStmt.executeUpdate();
		    	inserStmt.close();
			}
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			if( con!=null ) {
				con.close();
			}
	 	}
	}
	
	public static JSONArray getAttendanceList(String eventId) throws SQLException {
		Connection con = null;
		JSONArray res = new JSONArray();
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("select * from EventAttendance where eventId=? and yes=?");
	    	preparedStatement.setString(1, eventId); 
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
}