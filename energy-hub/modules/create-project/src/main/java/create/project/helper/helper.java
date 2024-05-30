package create.project.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
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
	
	private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false; 
	    }
	    return pattern.matcher(strNum).matches();
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
	
//	public static void sendEmailInvitation(String to, ResourceRequest request) throws MessagingException, PwdEncryptorException, NoSuchAlgorithmException { 
//		try {
//			  String from = "no-reply@undp.com"; 
//			  String host = "localhost"; 
//			  Properties properties = System.getProperties(); 
//			  properties.setProperty("mail.smtp.host", host); //fix localhost and smtp
//		      Session session = Session.getDefaultInstance(properties);
// 
//		      MimeMessage message = new MimeMessage(session);
//		      message.setFrom(new InternetAddress(from));
//		      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//		      message.setSubject("UNDP Invitation");
//		      
//		      int port = request.getServerPort();
//		         
//		      message.setText("You are invited to View a project on UNDP, please craete and account first : " +
//		    		  request.getScheme() + "://" + request.getServerName() + (port == 80 || port == 443 ? "" : ":" + port));
//		      
//		      Transport.send(message);
//		} catch (AddressException e) {
//		    	e.printStackTrace();
//		}
//		
//	}
	
	public JSONArray getDataListFromCustomTable(String dataId) throws IOException, PortletException, SQLException {
		 java.sql.Connection con = null;
		 java.sql.Statement st = null;
		 Thread thread = Thread.currentThread();    
		 thread.setContextClassLoader(PortalClassLoaderUtil.getClassLoader());
		 JSONArray data_Recovered = new JSONArray();
		 try {  
			 con = DataAccess.getConnection(); 
			 st = con.createStatement(); 
			 java.sql.ResultSet rs = st.executeQuery("SELECT * FROM journalarticle as j1 where id_= "+ dataId
					 + " and statusDate in (SELECT MAX(j2.statusDate) FROM journalarticle as j2 "
					 + " where j1.resourcePrimKey = j2.resourcePrimKey GROUP BY resourcePrimKey)"); 
			 while (rs.next()) { 
				 if(rs.getString("content") != null) {
					 JSONObject jo = new JSONObject();
					 jo.put("id", rs.getString("id_"));
					 jo.put("content", rs.getString("content"));
					 data_Recovered.put(jo); 
				 }
			 }
		 	} catch (Exception e) {
		 		e.printStackTrace();
		 	} finally {
				 st.close();
				 con.close();
		 	}
		return data_Recovered;
	 }

	public static void createProjectPermTable() throws SQLException {
		Connection con = DataAccess.getConnection();
		Statement stmt = con.createStatement(); 
		
		try {
	    	ResultSet rs = con.getMetaData().getTables(null, null, "ProjectPermission" , null);
	    	if (!rs.next()) {
	    		 String sql = "CREATE TABLE ProjectPermission " +
	                     "(articleId INTEGER not NULL, " +
	                     " email VARCHAR(255), " + 
	                     " PRIMARY KEY (articleId, email))"; 
	    		stmt.executeUpdate(sql);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			con.close();
		}
	}
	
	
	public static void addProjectPermission(String userEmail, long articleId) throws SQLException {
		Connection con = null;
		try {
			con = DataAccess.getConnection();
			
	    	PreparedStatement preparedStatement = con.prepareStatement("select count(*) from ProjectPermission where articleId=? and email=?");
	    	preparedStatement.setLong(1, articleId);
	    	preparedStatement.setString(2, userEmail); 
	    	
	    	boolean createRec = true;
	    	ResultSet rs = preparedStatement.executeQuery();
	    	while (rs.next()) { 
	    		createRec = rs.getInt(1)==0;
			}
	    	
	    	if( createRec ) {
		    	preparedStatement = con.prepareStatement("INSERT INTO ProjectPermission (articleId, email) VALUES (?,?)");
		    	preparedStatement.setLong(1, articleId);
		    	preparedStatement.setString(2, userEmail);  
		    	preparedStatement.executeUpdate();
	    	}
	    	
	    	preparedStatement.close();
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			if( con!=null ) {
				con.close();
			}
	 	}
	}
	
	public static void deleteProjectPermission(long articleId) throws SQLException {
		Connection con = DataAccess.getConnection();
		
		PreparedStatement preparedStatement = con.prepareStatement("DELETE from ProjectPermission where articleId = ?");
		preparedStatement.setLong(1, articleId);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		con.close();
	}

	public static boolean isEmpty(String str) {
		return str==null||str.trim().isEmpty();
	}
	public static String ifEmpty(String str, String defaultValue) {
		return isEmpty(str) ? defaultValue : str;
	}
}
