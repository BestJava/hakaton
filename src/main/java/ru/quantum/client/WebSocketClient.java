package ru.quantum.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.quantum.domain.Car;
import ru.quantum.events.EventServerProcessor;
import ru.quantum.schemas.*;

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
import java.util.Map;

@ClientEndpoint
public class WebSocketClient {

    protected WebSocketContainer container;
    protected Session userSession = null;
    private static final int MAX_BUFFER_SIZE = 1024 * 1024 * 50;
    private boolean isStarted = false;

    private EventServerProcessor event = new EventServerProcessor();
    protected ObjectMapper objectMapper = new ObjectMapper();

    public WebSocketClient() {
        container = ContainerProvider.getWebSocketContainer();
    }

    public void connect(String sServer) {

        try {
            container.setDefaultMaxTextMessageBufferSize(MAX_BUFFER_SIZE);
            container.setDefaultMaxBinaryMessageBufferSize(MAX_BUFFER_SIZE);
            container.setAsyncSendTimeout(Long.MAX_VALUE);
            container.setDefaultMaxSessionIdleTimeout(Long.MAX_VALUE);
            userSession = container.connectToServer(this, new URI(sServer));
            userSession.setMaxBinaryMessageBufferSize(MAX_BUFFER_SIZE);
            userSession.setMaxTextMessageBufferSize(MAX_BUFFER_SIZE);
            userSession.setMaxIdleTimeout(1024L * 1024L);
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
  //      System.out.println(msg);
        if (msg.equals("{\"end\": true}")) {
            sendMessage("{ \"reconnect\": \"" + event.getToken() + "\"}");
        } else if (findCommand(msg).equals("teamsum")) {
   //         System.out.println("teamsum msg = " + msg);
            ServerTeamsum teamsum = objectMapper.readValue(msg, ServerTeamsum.class);
            event.eventTeamSum(teamsum);
            System.out.println("team sum = " + teamsum.getTeamsum());
        } else if (findCommand(msg).equals("token")) {
            ServerConnect connect = objectMapper.readValue(msg, ServerConnect.class);
            event.eventConnect(connect);
        } else if (findCommand(msg).equals("routes")) {
            ServerRoutes routes = objectMapper.readValue(msg, ServerRoutes.class);
            event.eventRoutes(routes);
        } else if (findCommand(msg).equals("points")) {
            ServerPoints points = objectMapper.readValue(msg, ServerPoints.class);
            event.eventPoints(points);
        } else if (findCommand(msg).equals("traffic")) {
            ServerTraffic traffic = objectMapper.readValue(msg, ServerTraffic.class);
            event.eventTraffic(traffic);
            if (!isStarted) {
                isStarted = true;
                for (Map.Entry<String, Car> carEntry : event.getCars().entrySet()) {
                    ClientGoto clGoto = new ClientGoto();
                    clGoto.setCar(carEntry.getKey());

                    ServerGoto serverGoto = new ServerGoto();
                    serverGoto.setCar(carEntry.getKey());
                    serverGoto.setPoint(0);
                    serverGoto.setCarsum(0d);

                    int newPoint = event.eventGoto(serverGoto);

                    clGoto.setGoto(newPoint);
                    sendMessage(objectMapper.writeValueAsString(clGoto));
                }
            }
        } else if (findCommand(msg).equals("car")) {
            ServerTraffic serverTraffic = objectMapper.readValue(msg, ServerTraffic.class);

            event.eventTraffic(serverTraffic);
        } else if (findCommand(msg).equals("point")) {
            ServerGoto srvGoto = objectMapper.readValue(msg, ServerGoto.class);
            // ServerTraffic traffic = objectMapper.readValue(jsons[1], ServerTraffic.class);
            // event.eventTraffic(traffic);
            int newPoint = event.eventGoto(srvGoto);
            ClientGoto clGoto = new ClientGoto();
            clGoto.setCar(srvGoto.getCar());
            clGoto.setGoto(newPoint);

            sendMessage(objectMapper.writeValueAsString(clGoto));
        } else if (findCommand(msg).equals("pointsupdate")) {
            ServerPointsupdate srvPointsUpd = objectMapper.readValue(msg, ServerPointsupdate.class);
            event.eventPointsUpdate(srvPointsUpd);
        }
    }

    private String findCommand(String jsonMsg) {
        int beginIdx = jsonMsg.indexOf("\"");
        if (beginIdx == -1) {
            throw new RuntimeException("Quote not found in json " + jsonMsg);
        }
        int endIdx = jsonMsg.indexOf("\"", beginIdx + 1);
        if (endIdx == -1) {
            throw new RuntimeException("Second quote not found in json " + jsonMsg);
        }

        String cmd = jsonMsg.substring(beginIdx + 1, endIdx);
        return cmd.trim();
    }

    public void Disconnect() throws IOException {
        userSession.close();
    }
}
