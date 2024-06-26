package events.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

import javax.management.Query;
import javax.portlet.ResourceRequest;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import events.entity.CalendarEvent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PortalUtil;
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
	
	public static String getData(JournalArticle event, String title, ThemeDisplay themeDisplay) throws DocumentException {
		String data = "";
		if(event != null) {
			String content = event.getContentByLocale(themeDisplay.getLanguageId());
		    Document document = SAXReaderUtil.read(content);
			data = document.selectSingleNode("/root/dynamic-element[@name='"+ title +"']").hasContent() ? 
					document.selectSingleNode("/root/dynamic-element[@name='"+ title +"']/dynamic-content").getText() : "";
		} 
		return data;
	}
	
	public static boolean isEmpty(String str) {
		return str==null||str.trim().isEmpty();
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
