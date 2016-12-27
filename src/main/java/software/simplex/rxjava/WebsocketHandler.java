package software.simplex.rxjava;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@WebSocket
public class WebsocketHandler {
    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();
    private static final Gson gson = new Gson();

    @OnWebSocketConnect
    public void connected(Session session) {
        sessions.add(session);
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        sessions.remove(session);
    }

    public static void broadcast(Crime crime) throws IOException {

        Iterator<Session> iterator = sessions.iterator();
        while (iterator.hasNext()){
            iterator.next().getRemote().sendString(gson.toJson(crime));
        }
    }

}
