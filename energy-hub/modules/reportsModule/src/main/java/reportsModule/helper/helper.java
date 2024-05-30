package reportsModule.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

public class helper {

	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();

		Iterator<String> keysItr = object.keys();
		while (keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = object.get(key);

			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			map.put(key, value);
		}
		return map;
	}

	public static List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}

	public static JSONArray getDFromContentXml(JournalArticle article, Locale currentLocale, String nodeName)
			throws DocumentException {
		JSONArray retVal = new JSONArray();
		Document document = SAXReaderUtil.read(article.getContentByLocale(LocaleUtil.toLanguageId(currentLocale)));
		List<Node> nodes = document.selectNodes("/root/dynamic-element[@name='" + nodeName + "']/dynamic-content");
		for (Node descriptionNode : nodes) {
			if (descriptionNode != null)
				retVal.put(descriptionNode.getText());
		}
		return retVal;
	}

	public static String getStringFromDoc(Document document, String val) throws DocumentException {

		String retVal = document.selectSingleNode("/root/dynamic-element[@name='" + val + "']") != null
				&& document.selectSingleNode("/root/dynamic-element[@name='" + val + "']").hasContent()
						? document.selectSingleNode("/root/dynamic-element[@name='" + val + "']/dynamic-content")
								.getText()
						: "";
		return retVal;
	}

	public static boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	public static String ifEmpty(String str, String defaultValue) {
		return isEmpty(str) ? defaultValue : str;
	}

	private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		return pattern.matcher(strNum).matches();
	}
}
