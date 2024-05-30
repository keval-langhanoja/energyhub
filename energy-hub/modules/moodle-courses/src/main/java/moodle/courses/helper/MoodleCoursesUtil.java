package moodle.courses.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MoodleCoursesUtil {
	public static String callRestGet(String restURL, String data) {
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
}
