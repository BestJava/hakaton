package ru.quantum.client;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class WebSocketClient {

    protected WebSocketContainer container;
    protected Session userSession = null;

    public WebSocketClient() {
        container = ContainerProvider.getWebSocketContainer();
    }

    public void connect(String sServer) {

        try {
            userSession = container.connectToServer(this, new URI(sServer));

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
    public void onClose(Session session, CloseReason closeReason) {
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        System.out.println(msg);
    }

    public void Disconnect() throws IOException {
        userSession.close();
    }
}
