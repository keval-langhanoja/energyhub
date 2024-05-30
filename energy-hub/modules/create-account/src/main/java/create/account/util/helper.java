package create.account.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
 
import javax.portlet.ResourceRequest;

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
	
	
	@SuppressWarnings("deprecation")
	public static String fileUploadByDL(File file ,ThemeDisplay themeDisplay,ResourceRequest request, String folderName, String objFileName)
	{
		String FileURL = "";  
	    long userId = themeDisplay.getUserId();
		long groupId = themeDisplay.getScopeGroupId();
		long repositoryId = themeDisplay.getScopeGroupId();
	 	String mimeType = MimeTypesUtil.getContentType(file);   
   try
	    {
	        DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(themeDisplay.getScopeGroupId(), 0, folderName);
	        long fileEntryTypeId = dlFolder.getDefaultFileEntryTypeId();
	        ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), request);
	    	InputStream is = new FileInputStream( file );
	    	DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(userId, groupId, repositoryId, dlFolder.getFolderId(),
	    			file.getName(), mimeType, objFileName, "", "", fileEntryTypeId, null, file, is, file.getTotalSpace(), serviceContext);
	    	
	    	//Change mode of Draft to Approved 
	    	 DLFileEntryLocalServiceUtil.updateFileEntry(userId, dlFileEntry.getFileEntryId(), dlFileEntry.getName(), mimeType, 
	    			 objFileName,  "", "", null, fileEntryTypeId, null, file,
	    			 is, dlFileEntry.getSize(), serviceContext);
	    	  
	    	 if (dlFileEntry != null) {
	    		 FileURL =
	    	         PortalUtil.getPortalURL(request) + "/documents/" + dlFileEntry.getGroupId() + "/" +
	    	        		 dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle() + "/" + dlFileEntry.getUuid() + "?t=" +
	    	             System.currentTimeMillis();
	    	 }
	    	 
	    } catch (Exception e) { 
	    	e.printStackTrace();
	    }
   		return FileURL;
	}

	public static boolean isEmpty(String str) {
		return str==null||str.trim().isEmpty();
	}
	public static String ifEmpty(String str, String defaultValue) {
		return isEmpty(str) ? defaultValue : str;
	}
 
	public static JSONArray getFieldGroupFromContentXml(JournalArticle article, Locale currentLocale, String nodeName) throws DocumentException
	{    
		JSONArray retVal = new JSONArray();
		Document document = SAXReaderUtil.read(article.getContentByLocale(LocaleUtil.toLanguageId(currentLocale)));
        List<Node> nodes = document.selectNodes("/root/dynamic-element[@name='"+nodeName+"']");
        for(Node descriptionNode : nodes) {
        	if(descriptionNode.getText() != null) {
        		JSONObject fieldData = new JSONObject();
        		String area = descriptionNode.selectSingleNode("./dynamic-element[@name='SelectFromList23403899']").hasContent() ? 
        				descriptionNode.selectSingleNode("./dynamic-element[@name='SelectFromList23403899']/dynamic-content").getText() : "";
        		String level = descriptionNode.selectSingleNode("./dynamic-element[@name='SelectFromList47700469']").hasContent() ? 
        				descriptionNode.selectSingleNode("./dynamic-element[@name='SelectFromList47700469']/dynamic-content").getText() : "";
        				
        		fieldData.put("area", area);		
        		fieldData.put("level", level);		  	
        		retVal.put(fieldData);
        	}
        } 
		return retVal;
	}
	
	public static JSONArray sortJSONArrayAlphabetically(JSONArray jArray, String lang) throws JSONException {
		JSONArray sortedJsonArray = jArray;
		List<JSONObject> jsonValues = new ArrayList<JSONObject>();
	    for (int i = 0; i < jArray.length(); i++) {
	        jsonValues.add(jArray.getJSONObject(i));
	    }
	    String sortField = "";
	    if(lang.equalsIgnoreCase("ar_SA")) sortField = "TextAr";
	    if(lang.equalsIgnoreCase("en_US")) sortField = "Text";
	    
	    final String sortFieldName = sortField;
	    
	    Collections.sort(jsonValues, new Comparator<JSONObject>() {
	         private final String KEY_NAME = sortFieldName;
	         @Override
	         public int compare(JSONObject a, JSONObject b) {
	        	 String valA = new String();
	             String valB = new String();
	            try {
	            	 valA = (String) a.get(KEY_NAME);
	                 valB = (String) b.get(KEY_NAME);
	                 
	            } catch(JSONException e) {
	               e.printStackTrace();
	            }
	            return valA.compareTo(valB);
	         } 
	    });
		sortedJsonArray = new JSONArray(jsonValues);
		return sortedJsonArray;
	}
}
