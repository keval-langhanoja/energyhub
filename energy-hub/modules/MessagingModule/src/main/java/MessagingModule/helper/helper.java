package MessagingModule.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 
import javax.portlet.ResourceRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PortalUtil;

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
 
}
