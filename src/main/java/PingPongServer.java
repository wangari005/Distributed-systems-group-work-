import java.io.*;
import java.net.*;

public class PingPongServer {
    public static void main(String[] args) {
        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Ping-Pong Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected!");

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String message = in.readLine();

                if (message != null && message.equalsIgnoreCase("ping")) {
                    out.println("pong");
                    System.out.println("Received ping → sent pong");
                } else {
                    System.out.println("Invalid message received. Ignored.");
                }

                clientSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}