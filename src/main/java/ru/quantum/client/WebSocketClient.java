package ru.quantum.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.quantum.events.EventServerProcessor;
import ru.quantum.schemas.ClientGoto;
import ru.quantum.schemas.ServerConnect;
import ru.quantum.schemas.ServerGoto;
import ru.quantum.schemas.ServerPoints;
import ru.quantum.schemas.ServerRoutes;
import ru.quantum.schemas.ServerTraffic;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class WebSocketClient {

    protected WebSocketContainer container;
    protected Session userSession = null;
    private static final int MAX_BUFFER_SIZE = 1024 * 1024 * 50;

    private EventServerProcessor event = new EventServerProcessor();
    protected ObjectMapper objectMapper = new ObjectMapper();

    public WebSocketClient() {
        container = ContainerProvider.getWebSocketContainer();
    }

    public void connect(String sServer) {

        try {
            container.setDefaultMaxTextMessageBufferSize(MAX_BUFFER_SIZE);
            container.setDefaultMaxBinaryMessageBufferSize(MAX_BUFFER_SIZE);
            userSession = container.connectToServer(this, new URI(sServer));
            userSession.setMaxBinaryMessageBufferSize(MAX_BUFFER_SIZE);
            userSession.setMaxTextMessageBufferSize(MAX_BUFFER_SIZE);
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
            sendMessage("{ \"reconnect\": \"" + event.getToken() + "\"}");//todo: put token
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
    public void onMessage(Session session, String msg) throws IOException {
        if (msg.substring(3, 8).equals("token")) {
            ServerConnect connect = objectMapper.readValue(msg, ServerConnect.class);
            event.eventConnect(connect);
        } else if (msg.substring(3, 9).equals("routes")) {
            String[] jsons = msg.split("\n");
            ServerRoutes routes = objectMapper.readValue(jsons[0], ServerRoutes.class);
            ServerPoints points = objectMapper.readValue(jsons[1], ServerPoints.class);
            ServerTraffic traffic = objectMapper.readValue(jsons[2], ServerTraffic.class);
            event.eventRoutes(routes);
            event.eventPoints(points);
            event.eventTraffic(traffic);
            ClientGoto clGoto = new ClientGoto();
            clGoto.setCar("sb0");
            clGoto.setGoto(3);
            sendMessage(objectMapper.writeValueAsString(clGoto));
        } else if (msg.substring(3, 9).equals("points")) {
            System.out.println("Point");
        } else if (msg.substring(3, 10).equals("traffic")) {
            ServerTraffic traffic = objectMapper.readValue(msg, ServerTraffic.class);
            event.eventTraffic(traffic);
        } else if (msg.substring(3, 8).equals("point")) {
            String[] jsons = msg.split("\n");
            ServerGoto srvGoto = objectMapper.readValue(msg, ServerGoto.class);
            // ServerTraffic traffic = objectMapper.readValue(jsons[1], ServerTraffic.class);
            // event.eventTraffic(traffic);
            int newPoint = event.eventGoto(srvGoto);
            ClientGoto clGoto = new ClientGoto();
            clGoto.setCar(srvGoto.getCar());
            clGoto.setGoto(newPoint);
            sendMessage(objectMapper.writeValueAsString(clGoto));
        }
        System.out.println(msg);
    }

    public void Disconnect() throws IOException {
        userSession.close();
    }
}
