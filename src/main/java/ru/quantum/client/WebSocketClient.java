package ru.quantum.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class WebSocketClient {

    protected WebSocketContainer container;
    protected Session userSession = null;

    protected ObjectMapper objectMapper = new ObjectMapper();

    public WebSocketClient() {
        container = ContainerProvider.getWebSocketContainer();
    }

    public void connect(String sServer) {

        try {
            container.setDefaultMaxTextMessageBufferSize(1024 * 1024);
            container.setDefaultMaxBinaryMessageBufferSize(1024 * 1024);
            userSession = container.connectToServer(this, new URI(sServer));
            userSession.setMaxBinaryMessageBufferSize(1024 * 1024);
            userSession.setMaxTextMessageBufferSize(1024 * 1024);
        } catch (DeploymentException | URISyntaxException | IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(String sMsg) throws IOException {
        userSession.getBasicRemote().sendText(sMsg);
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected");
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) throws Exception {
        if (!CloseReason.CloseCodes.NORMAL_CLOSURE.equals(closeReason.getCloseCode())) {
            connect("ws://localhost:8080/race");
            sendMessage("{ \"reconnect\": \"token_goes_here\"}");//todo: put token
        }
    }

 /*   @OnError
    public void onError(Session session, Throwable t) {
        if (t instanceof SessionException) {
            userSession.
        }
    }
*/
    @OnMessage
    public void onMessage(Session session, String msg) {
        if (msg.substring(3, 8).equals("token")) {
            System.out.println("Token");
        } else if(msg.substring(3, 9).equals("routes")) {
            System.out.println("Route");
            String[] jsons = msg.split("\n");

        } else if(msg.substring(3, 9).equals("points")) {
            System.out.println("Point");
        } else if(msg.substring(3, 10).equals("traffic")) {
            System.out.println("Traffic");
        } else if(msg.substring(3, 8).equals("point")) {
            System.out.println("Point");
            String[] jsons = msg.split("\n");
        }
        System.out.println(msg);
    }

    public void Disconnect() throws IOException {
        userSession.close();
    }
}
