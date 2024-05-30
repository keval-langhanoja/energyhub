package news.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.jdbc.DataAccess; 

public class db_helper {
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
			URL url = new URL(restURL+data);
			 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setDoOutput(true);
		        connection.setRequestMethod("GET");
	    	 	connection.setRequestProperty("Content-Type", "application/json");
	    	 	connection.setRequestProperty("Authorization", "Bearer "+token);
	             
	            int responseCode=connection.getResponseCode();
	            if (responseCode == HttpsURLConnection.HTTP_OK) {
	                String line;
	                BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
	                while ((line=br.readLine()) != null) {
	                	response+=line;
	                }
	            }
	            else {
	            	response="";
	            }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	} 
	
	 
//	public static String iam_logout(String token, String redirect_url) {
//		String response = "";
//		try { 
//			URL url = new URL("https://iam.energyhub-lb.com/connect/endsession?id_token_hint=" + token
//					+ "&post_logout_redirect_uri=" + redirect_url);
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//			connection.setConnectTimeout(5000);
//			connection.setDoOutput(true); 
////			int responseCode=connection.getResponseCode();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return response;
//	}
	
	public static JSONObject getCommentDetailsCount(String id) throws SQLException {
		Connection con = null;
		JSONObject details = new JSONObject();
		try {
			con = DataAccess.getConnection();
	    	PreparedStatement commentCountStatement = con.prepareStatement("select count(*) as commentcount "
	    			+ " from Comments where article_id=? and commentParentId=0");
	    	commentCountStatement.setString(1, id);
	    	 
	    	ResultSet commentrs = commentCountStatement.executeQuery();
	    	while (commentrs.next()) { 
	    		details.put("commentcount", commentrs.getString("commentcount")); 
			}
	    	commentCountStatement.close();
	    	
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			if( con!=null ) {
				con.close();
			}
	 	}
		return details;
	}
}