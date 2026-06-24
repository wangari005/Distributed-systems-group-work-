import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class UpperCaseServer {

    private static final int PORT = 9090; // Port the server listens on

    public static void main(String[] args) {
        System.out.println("=== Uppercase Server ===");
        System.out.println("Listening on port " + PORT + "...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            // Keep accepting new client connections in a loop
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Wait for a client
                System.out.println("Client connected: "
                    + clientSocket.getInetAddress().getHostAddress());

                // Handle this client in a separate thread (supports multiple clients)
                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }

        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    /**
     * Handles communication with a single connected client.
     * Reads messages and replies with the uppercase version.
     *
     * @param socket the client's socket connection
     */
    private static void handleClient(Socket socket) {
        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String message;

            // Keep reading messages until the client disconnects
            while ((message = in.readLine()) != null) {
                System.out.println("Received : " + message);

                String upperMessage = message.toUpperCase(); // Convert to ALL CAPS
                out.println(upperMessage);                   // Send back to client

                System.out.println("Sent back: " + upperMessage);
                System.out.println("---");
            }

            System.out.println("Client disconnected.");

        } catch (Exception e) {
            System.out.println("Client handler error: " + e.getMessage());
        }
    }
}
