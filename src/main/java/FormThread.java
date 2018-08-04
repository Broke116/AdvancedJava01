import java.io.*;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.concurrent.atomic.AtomicInteger;

public class FormThread implements Runnable {

    private static final AtomicInteger counter = new AtomicInteger(0);
    private final Socket clientSocket;
    private static long startTime;
    private static long endTime;

    public FormThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        incrementCounter();
        System.err.println("New client connected. Total clients: " + counter.get());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String result = null;
        String s;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            while ((s = in.readLine()) != null) {
                if (s.contains("GET") && s.contains("/form")) {
                    result = s;
                }
                if (s.isEmpty()) {
                    break;
                }
            }

            FormGenerator generator = null;
            String form = "";
            if (result != null) {
                String path = result.substring(5, result.length() - 9);
                String[] elements = path.split("/");

                generator = new FormGenerator();
                form = generator.generateForm(elements[1]);
            }

            try {
                out.write("HTTP/1.0 200 OK\r\n");
                out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
                out.write("Server: OurServer/0.8.4\r\n");
                out.write("Content-Type: text/html\r\n");
                out.write(String.format("Content-Length: \r\n", form.length()));
                out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
                out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
                out.write("\r\n");
                if (form.length() > 0)
                    out.write(form);

                System.err.println("Connexion avec le client terminée");
                out.close();
                in.close();
                clientSocket.close();
                endTime = System.currentTimeMillis();
                System.out.println("Thread ID: " + Thread.currentThread().getId() + " Total time spent : " + (endTime - startTime));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            return;
        }
    }

    static void incrementCounter(){
        startTime = System.currentTimeMillis();
        System.out.println("Thread ID: " + Thread.currentThread().getId() + " : " + counter.getAndIncrement());
    }
}
