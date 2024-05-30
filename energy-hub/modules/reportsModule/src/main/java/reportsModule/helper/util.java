package reportsModule.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.liferay.portal.kernel.configuration.Configuration;

public class util {
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

	public static String getDropdowns(String token, String restURL) {
		String response = "";
		try { 
			URL url = new URL(restURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", "Bearer " + token);

			OutputStream outputStream = connection.getOutputStream();
			byte[] b = "".getBytes("UTF-8");
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
			} else {
				response = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static String getOverviewReport(String token, String restURL, String data) {
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
	
	public static String moodleRestGet(String restURL, String data) {
		String response = "";
		try {
			URL url = new URL(restURL + data);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");

			if (data != "") {
				OutputStream outputStream = connection.getOutputStream();
				byte[] b = data.getBytes("UTF-8");
				outputStream.write(b);
				outputStream.flush();
				outputStream.close();
			}

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
	
	private static HttpURLConnection conn;
	
	public static void getjson (String urlString) {
		BufferedReader reader;
		String line;
		StringBuilder responseContent = new StringBuilder();
		try{
			URL url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
			
			// Request setup
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			
			// Test if the response from the server is successful
			int status = conn.getResponseCode();
			
			if (status >= 300) {
				reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			else {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			System.out.println(responseContent.toString());
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			conn.disconnect();
		} 
	}
	
	public static JSONArray GetTreeJA(JSONArray ja, JSONArray res) {
		try {
			for (int i = 0; i < ja.length(); i++) {
				JSONObject tmpJO = ja.getJSONObject(i);
				JSONObject jo = new JSONObject();
				jo.put("id", tmpJO.get("categoryID"));
				jo.put("value", tmpJO.get("categoryName"));
				res.put(jo);
				if(!tmpJO.getJSONArray("children").isEmpty()) {
					GetTreeJA(tmpJO.getJSONArray("children"), res);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static String returnJsonValue(JSONArray array, int searchValue){
        String result = "";
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj= null;
            try {
                obj = array.getJSONObject(i);
                if(obj.getInt("id") == searchValue)
                   result = obj.getString("value");
                
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
	    return result;
	}
}
