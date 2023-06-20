package liteweb;

import liteweb.cache.LRUCache;
import liteweb.http.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {

    private static final Logger log = LogManager.getLogger(Server.class);
    private static final int DEFAULT_PORT = 8080;

    private LRUCache<String, Response>  lruCache = new LRUCache(3);

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException, InterruptedException {

        new Server().startListen(getValidPortParam(args));
    }


    public void startListen(int port) throws IOException, InterruptedException {

        try (ServerSocket socket = new ServerSocket(port, 100)) {
            log.info("Web server listening on port %d (press CTRL-C to quit)", port);
            while (true) {
                TimeUnit.MILLISECONDS.sleep(1);
                handle(socket);
            }
        }
    }

    private void handle(ServerSocket socket) throws IOException {
        Socket clientSocket = socket.accept();
        SocketHandle socketHandle = new SocketHandle(clientSocket, lruCache);
        executorService.execute(socketHandle);
    }

    /**
     * Parse command line arguments (string[] args) for valid port number
     *
     * @return int valid port number or default value (8080)
     */
    static int getValidPortParam(String[] args) throws NumberFormatException {
        if (args.length > 0) {
            int port = Integer.parseInt(args[0]);
            if (port > 0 && port < 65535) {
                return port;
            } else {
                throw new NumberFormatException("Invalid port! Port value is a number between 0 and 65535");
            }
        }
        return DEFAULT_PORT;
    }
}
