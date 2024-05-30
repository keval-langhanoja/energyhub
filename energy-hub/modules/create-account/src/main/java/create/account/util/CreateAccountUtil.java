package create.account.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

public class CreateAccountUtil {
	private final static  Configuration _portletConfiguration = ConfigurationFactoryUtil.getConfiguration(PortalClassLoaderUtil.getClassLoader(), "portlet");
	
	public static String getLoginToken(Configuration portletconfiguration) {
	    String response = "", token = "";
	    try {
	    	String restURL = portletconfiguration.get("iam-token-url");
	        URL url = new URL(restURL);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
        	connection.setRequestProperty("Accept", "application/json");
    	 	connection.setRequestProperty("Content-Type", "application/json");
    	 	connection.setRequestProperty("dataType", "json");
            
            OutputStream outputStream = connection.getOutputStream();
            JSONObject jsonData = new JSONObject();
            jsonData.put("username", portletconfiguration.get("iam-user"));
            jsonData.put("password", portletconfiguration.get("iam-password"));
    		byte[] b = jsonData.toString().getBytes("UTF-8");
    		outputStream.write(b);
    		outputStream.flush();
    		outputStream.close();
            int responseCode=connection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line=br.readLine()) != null) {
                	response+=line;
                }
                JSONObject jsonToken = new JSONObject(response);
                token = jsonToken.getString("token");
            }
            else {
            	response="";
            }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	   
		return token;
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
            int responseCode=connection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line=br.readLine()) != null) {
                	response+=line;
                }
                JSONObject jsonToken = new JSONObject(response);
                token = jsonToken.getString("token");
            }
            else {
            	response="";
            }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	   
		return token;
	}
	
	public static String callRestPost(String restURL, String data) {
	    String response = "";
	    try {
			String token = getToken(_portletConfiguration);
			URL url = new URL(restURL + data);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", "Bearer "+token);
			 
			if(data != "" ) {
				OutputStream outputStream = connection.getOutputStream();
	    		byte[] b = data.getBytes("UTF-8");
	    		outputStream.write(b);
	    		outputStream.flush();
	    		outputStream.close();
			}
			
			int responseCode=connection.getResponseCode();
			System.out.println("responseCode "+responseCode);
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
			response = String.valueOf(connection.getResponseCode());
		} catch (Exception e) {
	    	e.printStackTrace();
	    }
		return response;
	}
	
	public static String sendPost(String restURL, String data) {
		String response = "";
		try {
			String token = getToken(_portletConfiguration);
	        URL url = new URL(restURL);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
    	 	connection.setRequestProperty("Content-Type", "application/json");
    	 	connection.setRequestProperty("Authorization", "Bearer "+token);
            
            OutputStream outputStream = connection.getOutputStream();
    		byte[] b = data.getBytes("UTF-8");
    		outputStream.write(b);
    		outputStream.flush();
    		outputStream.close();
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
	
	public static String sendPostPhp(String restURL, String to, String encryptedString) {
		String response = "";
		try {
	        String command = "curl -X GET "+ restURL +"?to="+to+"&url="+encryptedString;
	        System.out.println(command);
	        Process process = Runtime.getRuntime().exec(command);
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(process.getInputStream()));
	            String inputLine;
	            while ((inputLine = in.readLine()) != null) {
	                System.out.println(inputLine);
	            }
	            in.close();
	            process.destroy();
	    } catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static String callRestGet(String restURL, String data) {
		String response = "";
		try {
			String token = getToken(_portletConfiguration);
			URL url = new URL(restURL+ data);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", "Bearer "+token);
			 
			if(data != "" ) {
				OutputStream outputStream = connection.getOutputStream();
	    		byte[] b = data.getBytes("UTF-8");
	    		outputStream.write(b);
	    		outputStream.flush();
	    		outputStream.close();
			}
			
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
	
	public static String getUserbyRole(String token, String restURL, String data) {
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
	public static String getDropdowns(String restURL) {
		String response = "";
		try {
			String token = getToken(_portletConfiguration);
	        URL url = new URL(restURL);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
    	 	connection.setRequestProperty("Content-Type", "application/json");
    	 	connection.setRequestProperty("Authorization", "Bearer "+token);
             
    	 	OutputStream outputStream = connection.getOutputStream();
      		byte[] b = "".getBytes("UTF-8");
      		outputStream.write(b);
      		outputStream.flush();
      		outputStream.close();
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
