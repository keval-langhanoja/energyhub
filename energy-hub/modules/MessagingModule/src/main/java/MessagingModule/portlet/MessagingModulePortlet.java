package MessagingModule.portlet;

import MessagingModule.constants.MessagingModulePortletKeys;
import MessagingModule.helper.db_helper;
import MessagingModule.helper.helper;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

/**
 * @author vyo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=MessagingModule",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.name=" + MessagingModulePortletKeys.MESSAGINGMODULE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class MessagingModulePortlet extends MVCPortlet {
	
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
			HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));

			// Create Tables
			db_helper.createChatMessagesTable();
			db_helper.createUserBlocksReportsTable();
			db_helper.createUserChatsTable();
			db_helper.createMessageInfoTable();
			//DELETE chats after 1 Year
			db_helper.deleteChats1Year();
			
			if(httpReq.getParameterMap().containsKey("admin")) {
				String chatId = httpReq.getParameter("chatId");
				String main_userId = httpReq.getParameter("main_userId");
				if(!helper.isEmpty(chatId)) {
					User currentUser = UserLocalServiceUtil.fetchUser(Long.valueOf(main_userId));
					JSONObject chat = buildChat(Long.valueOf(chatId), themeDisplay, currentUser);
					renderRequest.setAttribute("chat", chat);
					include("/adminReports.jsp", renderRequest, renderResponse);
					super.render(renderRequest, renderResponse);
				}else {
					JSONArray reports = db_helper.getReports();
					renderRequest.setAttribute("reports", helper.toList(reports));
					include("/allReportsAdmin.jsp", renderRequest, renderResponse);
					super.render(renderRequest, renderResponse);
				}
			}
			else {
				User currentUser = themeDisplay.getUser();
				Role adminRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), "Administrator");
				
				//Get non Admin Users
				JSONArray nonAdminusersJA = getUsersNonAdminNonBlocked(adminRole, currentUser);
				renderRequest.setAttribute("nonAdminusersJA", nonAdminusersJA); 
				
				//Get User Chats
				JSONArray chats = buildUserChats(currentUser, themeDisplay);
				renderRequest.setAttribute("chats", helper.toList(chats));
				renderRequest.setAttribute("chatsja", chats);
				
				//isAdmin
				renderRequest.setAttribute("isAdmin", currentUser.getRoles().contains(adminRole));
				
				include("/view.jsp", renderRequest, renderResponse);
				super.render(renderRequest, renderResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Gets all non admin users, non blocked users and Users that we don't have a chat already opened with 
	private JSONArray getUsersNonAdminNonBlocked(Role adminRole, User currentUser) throws SQLException {
		List<User> users =  UserLocalServiceUtil.getUsers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		List<User> nonAdminusers =  new ArrayList<User>();
		JSONArray nonAdminusersJA = new JSONArray(nonAdminusers); 
		List<Long> blockedBy = db_helper.getBlockedBy(currentUser.getUserId());
		List<Long> openedChats = db_helper.getOpenedChats(currentUser.getUserId());
		for(User user : users) {
			if(!user.getRoles().contains(adminRole) && !blockedBy.contains(user.getUserId()) 
					&& !openedChats.contains(user.getUserId())) {
				JSONObject jo = new JSONObject();
				jo.put("fullName", user.getFullName());
				jo.put("emailAddress", user.getEmailAddress());
				nonAdminusersJA.put(jo);
				nonAdminusers.add(user);
			}
		}
		return nonAdminusersJA;
	}

	private JSONArray buildUserChats(User currentUser, ThemeDisplay themeDisplay) throws SQLException, PortalException {
		JSONArray chats = new JSONArray();
		List<Long> userChatIds = db_helper.getUserChats(currentUser.getUserId());
		for(Long chatId : userChatIds) {
			JSONObject chatJO = buildChat(chatId, themeDisplay, currentUser);
			chats.put(chatJO);
		}
		return chats;
	}
	
	private JSONObject buildChat(Long chatId, ThemeDisplay themeDisplay, User currentUser) throws SQLException, PortalException {
		JSONObject chatJO = new JSONObject();
		List<Long> usersInChat = db_helper.getUsersInChat(chatId);
		chatJO.put("chatid", chatId);
		
		String names = "";
		for(Long userInChat : usersInChat) {
			if(userInChat != currentUser.getUserId()) {
				User user = UserLocalServiceUtil.getUser(userInChat);
				names += user.getFullName() + ", ";
			}
		}
		
		boolean isgroup = usersInChat.size() > 2 ? true : false ;
		chatJO.put("isgroup", isgroup);
		
		usersInChat.remove(currentUser.getUserId());

		List<String> strings = usersInChat.stream().map(Object::toString).collect(Collectors.toList());
		if(!helper.isEmpty(names))
			names = names.substring(0, names.length() - 2);
		chatJO.put("otherusersname", names);
		chatJO.put("otherusersid", String.join(",", strings));
		chatJO.put("currentusername", currentUser.getFullName());
		chatJO.put("currentuseremail", currentUser.getEmailAddress());
		chatJO.put("currentuserid", currentUser.getUserId());
		
		if(isgroup) {
			String groupName = db_helper.getChatGroupName(chatId);
			chatJO.put("groupname", helper.isEmpty(groupName)? names : groupName);
		}

		JSONArray messages = db_helper.getChatMessages(chatId);
		chatJO.put("messages", messages);
		
		//Check if Blocked
		if(!isgroup) {
			if(usersInChat.size() >0 ) {
				JSONObject isBlockedJO = db_helper.isBlocked(themeDisplay.getUserId(), usersInChat.get(0));
				chatJO.put("isblocked", isBlockedJO.has("isBlocked") ? isBlockedJO.get("isBlocked") : "");
				chatJO.put("blockedby", isBlockedJO.has("blockedBy") ? isBlockedJO.get("blockedBy") : "");
			}
		}
		
		//Last Received Message
		for(int i=0; i< messages.length(); i++) {
			JSONObject jo = messages.getJSONObject(i);
			if(jo.has("lastreceivedmessage")) {
				chatJO.put("lastreceivedmessage", jo.get("lastreceivedmessage"));
				chatJO.put("sentby", jo.getLong("senderid") == currentUser.getUserId()? "You" : jo.get("senderfirstname"));
				chatJO.put("lastreceivedmessagesentdate", jo.get("lastreceivedmessagesentdate"));
			}
		}
		
		//GET UNREAD MESSAGES
		int unreadCounter = db_helper.getUnreadMessagesCounter(chatId, currentUser.getUserId());
		chatJO.put("unreadcounter", unreadCounter);
		return chatJO;
	}

	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException, PortletException {
		resourceResponse.setContentType("text/html");
		PrintWriter out = resourceResponse.getWriter();
		String key = ParamUtil.getString(resourceRequest, "key");
		String retVal = "";
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
			
			if(key.equalsIgnoreCase("NewChat")) {
				String groupName = ParamUtil.getString(resourceRequest, "groupName");
				List<Long> userIds = new ArrayList<Long>();
				String newUsers = ParamUtil.getString(resourceRequest, "newUsers");
				for(String userEmail : newUsers.split(",")) {
					User user = UserLocalServiceUtil.getUserByEmailAddress(themeDisplay.getCompanyId(), userEmail);
					userIds.add(user.getUserId());
				}
				//add current user
				userIds.add(themeDisplay.getUserId());
				long chatId = db_helper.addNewChat(userIds, groupName);
				out.println(String.valueOf(chatId)); 
			}
			
			if(key.equalsIgnoreCase("getUsersNonAdminNonBlocked")) {
				JSONArray users = getUsersNonAdminNonBlockedRender(themeDisplay);
				out.println(users.toString()); 
			}
			
			if(key.equalsIgnoreCase("fetchNewMessages")) { //IMPLEMENT CHNAGES FROM unreadtotal in POST LOGIN AS WELL!
				String openedChat = ParamUtil.getString(resourceRequest, "chatId");
				//Get User Chats
				JSONArray chats = buildUserChats(themeDisplay.getUser(), themeDisplay);
				JSONObject ret = new JSONObject();
				ret.put("chats", helper.toList(chats));
				ret.put("chatsja", chats);
				ret.put("openedchat", openedChat);
				ret.put("currentuserid", themeDisplay.getUserId());
				int unreadtotal = 0;
				for(int i =0; i< chats.length(); i++) {
					JSONObject chat = chats.getJSONObject(i);
					int unredChatCounter = chat.getInt("unreadcounter");
					if(unredChatCounter > 0){
						unreadtotal = unreadtotal + unredChatCounter;
					}
				}
				ret.put("unreadtotal", unreadtotal);
				if(!helper.isEmpty(openedChat)) {
					List<Long> usersInChat = db_helper.getUsersInChat(Long.valueOf(openedChat));
					String names = "";
					for(Long userInChat : usersInChat) {
						if(userInChat != themeDisplay.getUserId()) {
							User user = UserLocalServiceUtil.getUser(userInChat);
							names += user.getFullName() + ", ";
						}
					}
					ret.put("otherusersname", names.substring(0, names.length() - 2));
				}
				
				JSONArray users = getUsersNonAdminNonBlockedRender(themeDisplay);
				ret.put("users", users);
				out.println(ret.toString()); 
			}
			
			if(key.equalsIgnoreCase("sendNewMessage")) {
				//Get User Chats
				Long chatId = Long.valueOf(ParamUtil.getString(resourceRequest, "chatId"));
				String message = ParamUtil.getString(resourceRequest, "message");
				db_helper.sendMessage(message, chatId, themeDisplay.getUserId());
				
				JSONObject ret = new JSONObject();
				ret.put("message", message);
				ret.put("sendername", themeDisplay.getUser().getFirstName());
				
				Calendar now = Calendar.getInstance();
				ret.put("time", now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE));
				out.println(ret.toString()); 
			}
			
			if(key.equalsIgnoreCase("Report")) {
				Long reportedUserId = Long.valueOf(ParamUtil.getString(resourceRequest, "reportedUserId"));
				String reason = ParamUtil.getString(resourceRequest, "reason");
				String description = ParamUtil.getString(resourceRequest, "description");
				String chatId = ParamUtil.getString(resourceRequest, "reportedUserChatId");
				db_helper.reportUser(reportedUserId, themeDisplay.getUserId(),
						themeDisplay.getUser().getEmailAddress(), reason, description, chatId);
			}
			
			if(key.equalsIgnoreCase("Block")) {
				Long blockedUserId = Long.valueOf(ParamUtil.getString(resourceRequest, "blockedUserId"));
				db_helper.blockUser(blockedUserId, themeDisplay.getUserId());
			}
			
			if(key.equalsIgnoreCase("Unblock")) {
				Long blockedUserId = Long.valueOf(ParamUtil.getString(resourceRequest, "blockedUserId"));
				db_helper.UnblockUser(themeDisplay.getUserId(), blockedUserId);
			}
			
			if(!helper.isEmpty(ParamUtil.getString(resourceRequest, "chatId"))) {
				Long chatId = Long.valueOf(ParamUtil.getString(resourceRequest, "chatId"));
				if(key.equalsIgnoreCase("Leave")) {
					db_helper.leaveGroup(chatId, themeDisplay.getUserId());
				}
				
				if(key.equalsIgnoreCase("Delete")) {
					db_helper.deleteMessage(chatId, themeDisplay.getUserId());
				}
				
				if(key.equalsIgnoreCase("MarksAsRead")) {
					db_helper.readMessage(chatId, themeDisplay.getUserId());
				}
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println(String.valueOf(retVal));
		out.flush();
		super.serveResource(resourceRequest, resourceResponse);
	}
	
	private JSONArray getUsersNonAdminNonBlockedRender(ThemeDisplay themeDisplay) 
			throws PortalException, SQLException {
		Role adminRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), "Administrator");
		JSONArray users = getUsersNonAdminNonBlocked(adminRole, themeDisplay.getUser());
		return users; 
	}
}