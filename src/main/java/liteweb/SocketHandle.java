package liteweb;

import liteweb.http.Request;
import liteweb.http.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketHandle implements Runnable{

    private static final Logger log = LogManager.getLogger(SocketHandle.class);

    private final Socket clientSocket;

    public  SocketHandle(Socket socket) {
        this.clientSocket = socket;
    }
    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            List<String> requestContent = new ArrayList<>();
            String temp = reader.readLine();
            while(temp != null && temp.length() > 0) {
                requestContent.add(temp);
                temp = reader.readLine();
            }
            Request req = new Request(requestContent);
            Response res = new Response(req);
            res.write(clientSocket.getOutputStream());
        } catch (IOException e) {
            log.error("IO Error", e);
        }
    }
}
