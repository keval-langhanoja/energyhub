package MessagingModule.endpoint;

import java.io.IOException;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

import org.osgi.service.component.annotations.Component;

/**
 * @author vyo
 */
@Component(
	immediate = true,
	property = {
		"org.osgi.http.websocket.endpoint.path=" +
			EchoWebSocketEndpoint.ECHO_WEBSOCKET_PATH
	},
	service = Endpoint.class
)
public class EchoWebSocketEndpoint extends Endpoint {

	public static final String ECHO_WEBSOCKET_PATH = "/o/echo";

	@Override
	public void onOpen(final Session session, EndpointConfig endpointConfig) {
		session.addMessageHandler(
			new MessageHandler.Whole<String>() {
				@Override
				public void onMessage(String text) {
					try {
						RemoteEndpoint.Basic remoteEndpoint = session.getBasicRemote();
						remoteEndpoint.sendText(text);
					}
					catch (IOException ioe) {
						throw new RuntimeException(ioe);
					}
				}

			});
	}

}