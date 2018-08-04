import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class HttpServer {
    public static int port = 8032;
    private final static boolean serverRunning = true;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            final ExecutorService pool = Executors.newCachedThreadPool();
            serverSocket = new ServerSocket(port);
            System.err.println("Server available at port : " + port);

            while (serverRunning) {
                Socket socket = serverSocket.accept();
                pool.execute(new FormThread(socket));
                if(pool instanceof ThreadPoolExecutor) {
                    System.out.println("Pool size is now " + ((ThreadPoolExecutor) pool).getActiveCount());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
