package PostLogin;

import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import PostLogin.util.db_helper; 

@Component(
	immediate = true,
	property = {
		 "key=login.events.post"
	},
	service = LifecycleAction.class
)
public class Postlogin implements LifecycleAction {
	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent) throws ActionException {
		
		try {
			db_helper.createRepliesTable();
			db_helper.createForumDetails("CommunityReplies_Details", "replyId");
			db_helper.createForumDetails("Forum_Details", "forum_Id");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
    	Timer timer = new Timer();
		timer.schedule(new TimerTask() {
		  @Override
		  public void run() {
			  HttpServletRequest request = lifecycleEvent.getRequest(); 
			  try {
				  User user =  UserLocalServiceUtil.getUser(Long.valueOf(request.getRemoteUser()));
				  JSONArray chats = buildUserChats(user);
				  int unreadtotal = 0;
				  for(int i =0; i< chats.length(); i++) {
					  JSONObject chat = chats.getJSONObject(i);
					  int unredChatCounter = chat.getInt("unreadcounter");
					  if(unredChatCounter > 0){
						  unreadtotal = unreadtotal + unredChatCounter;
					  }
				  } 
				  user.setComments(String.valueOf(unreadtotal));
				  UserLocalServiceUtil.updateUser(user);
			} catch (PortalException | SQLException e) {
				e.printStackTrace();
			}
		  }
		}, 0, 5000);
	}
	
	private JSONArray buildUserChats(User currentUser) throws SQLException, PortalException {
		JSONArray chats = new JSONArray();
		db_helper.createChatMessagesTable();
		db_helper.createUserBlocksReportsTable();
		db_helper.createUserChatsTable();
		db_helper.createMessageInfoTable();
		db_helper.createProjectPermTable();
		List<Long> userChatIds = db_helper.getUserChats(currentUser.getUserId());
		for(Long chatId : userChatIds) {
			JSONObject chatJO = new JSONObject();
			  
			//GET UNREAD MESSAGES
			int unreadCounter = db_helper.getUnreadMessagesCounter(chatId, currentUser.getUserId());
			chatJO.put("unreadcounter", unreadCounter);
			chats.put(chatJO);
		}
		return chats;
	}
}