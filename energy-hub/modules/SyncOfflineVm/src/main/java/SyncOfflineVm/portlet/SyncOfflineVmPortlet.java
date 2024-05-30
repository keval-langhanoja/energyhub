package SyncOfflineVm.portlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.WebKeys;

import SyncOfflineVm.constants.SyncOfflineVmPortletKeys;
import SyncOfflineVm.constants.UserTypes;
import SyncOfflineVm.db_helper.db_helper;
import SyncOfflineVm.helper.helper;

/**
 * @author vyo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=SyncOfflineVm",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SyncOfflineVmPortletKeys.SYNCOFFLINEVM,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class SyncOfflineVmPortlet extends MVCPortlet {

	private String hostOnline = "vps-c0a27b61.vps.ovh.net";
	private String hostOffline = "vps-4826feb2.vps.ovh.net";
	private String user = "root";
	private String passwordOnline = "uB3TfsVJ.n_mUQr";
	private String passwordOffline = "$dcR9z?amNez";
    private String onlineFolderPath = "/liferay/offlineBackup/";
    private String offlineFolderPath = "/liferay/onlineRestore/";
    
	private final static Configuration _portletConfiguration = ConfigurationFactoryUtil
			.getConfiguration(PortalClassLoaderUtil.getClassLoader(), "portlet");
	
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException, PortletException {
		resourceResponse.setContentType("text/html");
		PrintWriter out = resourceResponse.getWriter();
		String key = ParamUtil.getString(resourceRequest, "key");
		String retVal = "";
		try {
			if (key.equalsIgnoreCase("BackupVms")) {
//				backupDB("iam", "postgres", "postgres", "iam\"");
//				backupDB("adminbkp_moodle", "adminbkp_root", "O?eUSV^iR#MR", "moodle.sql\"");
				backupUsersOnlineOffline();
			}
			if (key.equalsIgnoreCase("RestoreVms")) {
				ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
//				restoreDB("adminbkp_moodle", "adminbkp_root", "O?eUSV^iR#MR", "iam\"");
//				restoreDB("adminbkp_iam", "adminbkp_postgres", "ZgdHvg?gsxz5", "moodle.sql\"");
				restoreUsersOnlineOffline(themeDisplay, resourceRequest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println(String.valueOf(retVal));
		out.flush();
		super.serveResource(resourceRequest, resourceResponse);
	}
	
	private void backupDB(String dbName, String dbUser, String dbPass, String bckupName) {
		try {
			//Clean folder before adding new Backups
			removeFilesFromVm(hostOnline, passwordOnline, onlineFolderPath);

	        String savePath = onlineFolderPath + bckupName;
	        String executeCmd = "";
	        if(dbName.equalsIgnoreCase("adminbkp_moodle"))
	        	executeCmd = "mysqldump -u " + dbUser + " -p " + dbPass + " --no-tablespaces " + dbName + " > " + savePath;
	        else if(dbName.equalsIgnoreCase("iam"))
	        	executeCmd = "pg_dump -U " + dbUser + " -d  " + dbName + " > " + savePath;
	        
	        Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
	        int processComplete = runtimeProcess.waitFor();

	        if (processComplete == 0) {
	            System.out.println("Backup Complete");
//	            if(dbName.equalsIgnoreCase("adminbkp_moodle")){
//	            	restoreDB("adminbkp_moodle", "adminbkp_root", "O?eUSV^iR#MR", bckupName);
//	            }else if(dbName.equalsIgnoreCase("iam")) {
//	            	restoreDB("adminbkp_iam", "adminbkp_postgres", "ZgdHvg?gsxz5", bckupName);
//	            }
	        } else System.out.println("Backup Failure");
	    } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	private void restoreDB(String dbName, String dbUser, String dbPass, String bckupName) {
        try {
        	//Copy Files from online vm to offline vm in order to restore them
        	writeFilesToOfflineVm(bckupName);
            String restorePath = offlineFolderPath + bckupName;
            String executeCmd = "mysql -u " + dbUser + " -p " + dbPass + " " + dbName + " < " + restorePath;

            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
            	JOptionPane.showMessageDialog(null, "Successfully restored from SQL : " + bckupName);
            	removeFilesFromVm(hostOffline, passwordOffline, offlineFolderPath);
            } else JOptionPane.showMessageDialog(null, "Error at restoring");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
	private void writeFilesToOfflineVm(String fileName) {
		fileName = fileName.replace("\"", "").trim();
		try {
            JSch jsch = new JSch();
            Session sessionOnline = jsch.getSession(user, hostOnline);
            sessionOnline.setPassword(passwordOnline); 
            sessionOnline.setConfig("StrictHostKeyChecking", "no");
            sessionOnline.connect();
            
            Channel channelOnline = sessionOnline.openChannel("sftp");
            channelOnline.connect();
            ChannelSftp channelOnlineSFTP = (ChannelSftp) channelOnline;
            
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            channelOnlineSFTP.get(onlineFolderPath + fileName, byteArrayOutputStream);
            byte[] fileData = byteArrayOutputStream.toByteArray();
            
            channelOnlineSFTP.exit();
            sessionOnline.disconnect();
            
            Session sessionOffline = jsch.getSession(user, hostOffline);
            sessionOffline.setPassword(passwordOffline);
            sessionOffline.setConfig("StrictHostKeyChecking", "no");
            sessionOffline.connect();

            Channel channelOffline = sessionOffline.openChannel("sftp");
            channelOffline.connect();
            ChannelSftp channelOfflineSFTP = (ChannelSftp) channelOffline;
            
            channelOfflineSFTP.put(new ByteArrayInputStream(fileData), offlineFolderPath + fileName);  
            
            channelOfflineSFTP.exit();
            sessionOffline.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void removeFilesFromVm(String host, String pass, String path) {
		try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(pass);
            session.connect();
            
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            
            @SuppressWarnings("unchecked")
			Vector<ChannelSftp.LsEntry> fileList = sftpChannel.ls(path);
            // Iterate through the list of files and delete each one
            for (ChannelSftp.LsEntry file : fileList) {
                String fileName = file.getFilename();
                if (!fileName.equals(".") && !fileName.equals("..")) {
                    sftpChannel.rm(path + "/" + fileName);
                }
            }
            
            sftpChannel.exit();
            session.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void backupUsersOnlineOffline() throws SQLException, PortalException, JSONException {
		Connection con = null;
		PreparedStatement st = null;
		JSONArray usersOnlineData = new JSONArray();
		try {
			con = DataAccess.getConnection();
			st = con.prepareStatement("SELECT userId,emailAddress,firstName,lastName,reminderQueryAnswer,comments"
					+ " FROM User_ where status != '5'");
			ResultSet rs = st.executeQuery();
	    	while (rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("userId", rs.getString("userId"));
				jo.put("emailAddress", rs.getString("emailAddress"));
				jo.put("firstName", rs.getString("firstName"));
				jo.put("lastName", rs.getString("lastName"));
				jo.put("password", rs.getString("reminderQueryAnswer"));
				jo.put("comments", rs.getString("comments"));
				if(jo != null)
					usersOnlineData.put(jo); 
			 }
	    	
	    	//Write File to Offline server
	    	JSch jsch = new JSch();
	    	Session sessionOffline = jsch.getSession(user, hostOffline);
            sessionOffline.setPassword(passwordOffline);
            sessionOffline.setConfig("StrictHostKeyChecking", "no");
            sessionOffline.connect();

            Channel channelOffline = sessionOffline.openChannel("sftp");
            channelOffline.connect();
            ChannelSftp channelOfflineSFTP = (ChannelSftp) channelOffline;
            
            ByteArrayInputStream bis = new ByteArrayInputStream(usersOnlineData.toString().getBytes());
            channelOfflineSFTP.put(bis, offlineFolderPath + "userDataJson.txt");  
            
            channelOfflineSFTP.exit();
            sessionOffline.disconnect();
		} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
			 st.close();
			 con.close();
	 	}
	}
		
	private void restoreUsersOnlineOffline(ThemeDisplay themeDisplay, ResourceRequest resourceRequest) throws SQLException, PortalException, 
	JSONException, JSchException, SftpException {
		List<User> offlineUsers = UserLocalServiceUtil.getUsers(0, UserLocalServiceUtil.getUsersCount());
		List<String> offlineUsersEmailList = new ArrayList<String>();
		List<String> createUsers = new ArrayList<String>();
		List<String> updateUsers = new ArrayList<String>();
		
		for(User offEmail : offlineUsers) {
			if(!offlineUsersEmailList.contains(offEmail.getEmailAddress()))
				offlineUsersEmailList.add(offEmail.getEmailAddress());
		}
		
		JSch jsch = new JSch();
    	Session sessionOffline = jsch.getSession(user, hostOffline);
        sessionOffline.setPassword(passwordOffline);
        sessionOffline.setConfig("StrictHostKeyChecking", "no");
        sessionOffline.connect();

        Channel channelOffline = sessionOffline.openChannel("sftp");
        channelOffline.connect();
        ChannelSftp channelOfflineSFTP = (ChannelSftp) channelOffline;
        
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        channelOfflineSFTP.get(offlineFolderPath + "userDataJson.txt", byteArrayOutputStream);
        byte[] fileData = byteArrayOutputStream.toByteArray();
        String jsonString = new String(fileData);
        
        JSONArray usersOnlineData = new JSONArray(jsonString);
        channelOfflineSFTP.exit();
        sessionOffline.disconnect();
		
		for(int i = 0; i< usersOnlineData.length(); i++) {
			JSONObject oUserData = usersOnlineData.getJSONObject(i);
			String userId = oUserData.has("userId") ? oUserData.getString("userId") : "";
			String emailAddress = oUserData.has("emailAddress") ? oUserData.getString("emailAddress") : "";
			String firstName = oUserData.has("firstName") ? oUserData.getString("firstName") : "";
			String lastName = oUserData.has("lastName") ? oUserData.getString("lastName") : "";
			String password = oUserData.has("password") ? oUserData.getString("password") : "";
			String comments = oUserData.has("comments") ? oUserData.getString("comments") : "";
			
			String token = db_helper.getToken(_portletConfiguration);
			String userString = db_helper.getUserbyEmail(token, 
					_portletConfiguration.get("get-user-email"),emailAddress);
			
			Boolean gender = true;
			if(new JSONObject(userString).has("gender")) {
				String genderr =  new JSONObject(userString).getString("gender");
				if(genderr.equalsIgnoreCase("F")) gender = false;
			}
			
			Role role = null;
			String roleName = "";
			if(!helper.isEmpty(userString) && new JSONObject(userString).has("roleId")) {
				int userRole = new JSONObject(userString).getInt("userApplicationRoleId");
				roleName = UserTypes.getUserTypeName(String.valueOf(userRole));
				roleName = roleName.replace("IAM", "");
				role = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), roleName);
			}
			
			User user = null;
			if(!offlineUsersEmailList.contains(emailAddress)) {
				user = UserLocalServiceUtil.createUser(Long.valueOf(userId));
				Date date = null;
				user = UserLocalServiceUtil.addUser(0, themeDisplay.getCompanyId(), true, password,
						password, false, firstName, emailAddress, themeDisplay.getLocale(),
						firstName,"", lastName, 0, 0, gender, date.getMonth(), date.getDay(),
						date.getYear(), "", null, null, null, null, false, null);

//				if(!helper.isEmpty(comments))
//					UserLocalServiceUtil.updatePortrait(user.getUserId(), portaitBytes);
			} else {
				user = UserLocalServiceUtil.getUser(Long.valueOf(userId));
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setEmailAddress(emailAddress);
				user.setComments(comments);
				user.setPassword(password);
			}
			
			if(role != null)
				UserLocalServiceUtil.addRoleUser(role.getRoleId(), user.getUserId());
			
			UserLocalServiceUtil.updatePassword(user.getUserId(), password, password, false);
			user.setReminderQueryAnswer(password);
			UserLocalServiceUtil.updateUser(user);
		}
		removeFilesFromVm(hostOffline, passwordOffline, offlineFolderPath);
	}
}