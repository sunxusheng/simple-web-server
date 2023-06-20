package liteweb;

import liteweb.cache.LRUCache;
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
import java.util.Optional;

public class SocketHandle implements Runnable {

    private static final Logger log = LogManager.getLogger(SocketHandle.class);

    private final Socket clientSocket;

    private final LRUCache<String, Response> lruCache;

    public  SocketHandle(Socket socket, LRUCache<String, Response> lruCache) {
        this.clientSocket = socket;
        this.lruCache = lruCache;
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

            String uri = req.getUri();
            Response res = getResponse(req, uri);
            res.write(clientSocket.getOutputStream());
        } catch (IOException e) {
            log.error("IO Error", e);
        }
    }

    private Response getResponse(Request req, String uri) {
        Response res;
        Optional<Response> responseOptional = lruCache.get(uri);
        if (!responseOptional.isPresent()) {
            res = new Response(req);
            if (res.isResponseLessThan1M()) {
                lruCache.put(uri, res);
            }

        } else {
            res = responseOptional.get();
        }
        return res;
    }
}
