package directory.custom.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

public class helper {
	
	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
	    Map<String, Object> map = new HashMap<String, Object>();

	    Iterator<String> keysItr = object.keys();
	    while(keysItr.hasNext()) {
	        String key = keysItr.next();
	        Object value = object.get(key);
	        
	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }
	        
	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        map.put(key, value);
	    }
	    return map;
	}
	
	
	public static List<Object> toList(JSONArray array) throws JSONException {
	    List<Object> list = new ArrayList<Object>();
	    for(int i = 0; i < array.length(); i++) {
	        Object value = array.get(i);
	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        list.add(value);
	    }
	    return list;
	}
	
	public static JSONArray getDFromContentXml(JournalArticle article, Locale currentLocale, String nodeName) throws DocumentException
	{    
		JSONArray retVal = new JSONArray();
		Document document = SAXReaderUtil.read(article.getContentByLocale(LocaleUtil.toLanguageId(currentLocale)));
        List<Node> nodes = document.selectNodes("/root/dynamic-element[@name='"+nodeName+"']/dynamic-content");
        for(Node descriptionNode : nodes) {
        	if(descriptionNode != null)
        		retVal.put(descriptionNode.getText());
        } 
		return retVal;
		
	}
	
	public static String getDFromContentXmlString(JournalArticle pub, String title, ThemeDisplay themeDisplay) throws DocumentException
	{    
		String retVal = ""; 
		String content = pub.getContentByLocale(themeDisplay.getLanguageId());
	    Document document = SAXReaderUtil.read(content);
        List<Node> nodes = document.selectNodes("/root/dynamic-element[@name='"+title+"']/dynamic-content");
        for(Node descriptionNode : nodes) {
        	if(descriptionNode != null)
        		retVal = descriptionNode.getText();
        } 
		return retVal;
	}
	
	public static JSONArray getFieldGroupFromContentXml(JournalArticle article, Locale currentLocale, String nodeName) throws DocumentException
	{    
		JSONArray retVal = new JSONArray();
		Document document = SAXReaderUtil.read(article.getContentByLocale(LocaleUtil.toLanguageId(currentLocale)));
        List<Node> nodes = document.selectNodes("/root/dynamic-element[@name='"+nodeName+"']");
        for(Node descriptionNode : nodes) {
        	if(descriptionNode != null) {
        		JSONObject fieldData = new JSONObject();
        		String degreeType = descriptionNode.selectSingleNode("./dynamic-element[@name='SelectFromList75723891']").hasContent() ? 
        				descriptionNode.selectSingleNode("./dynamic-element[@name='SelectFromList75723891']/dynamic-content").getText() : "";
        		String attendance = descriptionNode.selectSingleNode("./dynamic-element[@name='SelectFromList52092032']").hasContent() ? 
        				descriptionNode.selectSingleNode("./dynamic-element[@name='SelectFromList52092032']/dynamic-content").getText() : "";
        		String energyPrograms = descriptionNode.selectSingleNode("./dynamic-element[@name='RichText80306402']").hasContent() ? 
        				descriptionNode.selectSingleNode("./dynamic-element[@name='RichText80306402']/dynamic-content").getText() : "";
        		String description = descriptionNode.selectSingleNode("./dynamic-element[@name='RichText48466122']").hasContent() ? 
        				descriptionNode.selectSingleNode("./dynamic-element[@name='RichText48466122']/dynamic-content").getText() : "";
        		String location = descriptionNode.selectSingleNode("./dynamic-element[@name='Text84722742']").hasContent() ? 
        				descriptionNode.selectSingleNode("./dynamic-element[@name='Text84722742']/dynamic-content").getText() : "";
				String links = descriptionNode.selectSingleNode("./dynamic-element[@name='Text45772616']").hasContent() ? 
						descriptionNode.selectSingleNode("./dynamic-element[@name='Text45772616']/dynamic-content").getText() : "";
        				
        		fieldData.put("degreeType", degreeType);		
        		fieldData.put("attendance", attendance);		
        		fieldData.put("energyPrograms", energyPrograms);		
        		fieldData.put("description", description);		
        		fieldData.put("location", location);	 	
        		fieldData.put("links", links);	 	
        		retVal.put(fieldData);
        	}
        } 
		return retVal;
	}
	
	public static boolean isEmpty(String str) {
		return str==null||str.trim().isEmpty();
	}

	public static String ifEmpty(String str, String defaultValue) {
		return isEmpty(str) ? defaultValue : str;
	}

}
