package community.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.xml.Document;


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
	
	public static boolean isEmpty(String str) {
		return str==null||str.trim().isEmpty();
	}

	public static String ifEmpty(String str, String defaultValue) {
		return isEmpty(str) ? defaultValue : str;
	}
	
	public static String getData(Document document, String title) {
		String data = "";
		if(document != null) {
			data = document.selectSingleNode("/root/dynamic-element[@name='"+ title +"']").hasContent() ? 
					document.selectSingleNode("/root/dynamic-element[@name='"+ title +"']/dynamic-content").getText() : "";
		} 
		return data;
	}
 
	@SuppressWarnings("deprecation")
	public static JSONObject fileUploadByDL(File file ,ThemeDisplay themeDisplay,ResourceRequest request, String folderName, String fileTitleOrFileId)
	{
		String FileURL = "";  
	    long userId = themeDisplay.getUserId();
		long groupId = themeDisplay.getScopeGroupId();
		long repositoryId = themeDisplay.getScopeGroupId();
	 	DLFileEntry dlFileEntry = null;
	 	JSONObject coverImageData = new JSONObject();
	 	
	 	try {
	        DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(themeDisplay.getScopeGroupId(), 0, folderName);
	        long fileEntryTypeId = dlFolder.getDefaultFileEntryTypeId();
	        ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), request);
	        serviceContext.setWorkflowAction(0);
	    	
	    	@SuppressWarnings("unchecked")
			Map<String, Serializable> workflowContext =
	    			(Map<String, Serializable>)serviceContext.removeAttribute(
	    				"workflowContext");
	    	if (workflowContext == null) {
	    		workflowContext = Collections.emptyMap();
	    	} 
	    	
	    	if( file == null ) {
	    		long fileId = Long.valueOf(fileTitleOrFileId);
	    		dlFileEntry = DLFileEntryLocalServiceUtil.fetchDLFileEntry(fileId);
	    	} else {
	    		String fileTitle = fileTitleOrFileId;
		    	InputStream is = new FileInputStream(file);
		    	dlFileEntry = DLFileEntryLocalServiceUtil.fetchFileEntry(groupId, dlFolder.getFolderId(), file.getName());
		    	
		    	if( dlFileEntry == null ) {
		    		String mimeType = MimeTypesUtil.getContentType(file);
		    		String desc = fileTitle, 
	    					ext = FileUtil.getExtension(fileTitle),
		    				fileName = fileTitle.substring(0, fileTitle.length()-ext.length()-1);
					fileTitle = fileName+"-"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"))+"."+ext;
					
		    		dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(userId, groupId, repositoryId, dlFolder.getFolderId(),
		    				fileTitle, mimeType, fileTitle, desc, "", fileEntryTypeId, null, file, is, file.length(), serviceContext);
		    		
			    	// Change mode of Draft to Approved 
		    		DLFileEntryLocalServiceUtil.updateStatus(userId, dlFileEntry.getFileVersion().getFileVersionId() ,0, serviceContext, workflowContext);
		    	}
	    	}
	    	
	    	if (dlFileEntry != null) {
	    		 FileURL =
	    	         PortalUtil.getPortalURL(request) + "/documents/" + dlFileEntry.getGroupId() + "/" +
	    	        		 dlFileEntry.getFolderId() + "/" + dlFileEntry.getTitle() + "/" + dlFileEntry.getUuid() + "?t=" +
	    	             System.currentTimeMillis();
	    		  
	    		 coverImageData.put("classNameId", dlFileEntry.getClassNameId());
	    		 coverImageData.put("description", dlFileEntry.getDescription());
	    		 coverImageData.put("fileEntryId", dlFileEntry.getFileEntryId());
	    		 coverImageData.put("groupId", dlFileEntry.getGroupId()); 
	    		 coverImageData.put("title", dlFileEntry.getTitle());
	    		 coverImageData.put("type", dlFileEntry.getMimeType());
	    		 coverImageData.put("url", FileURL);
	    		 coverImageData.put("uuid", dlFileEntry.getUuid());
	    	 }
	    	 
	    } catch (Exception e) { 
	    	e.printStackTrace();
	    }
   		return coverImageData;
	}
}
