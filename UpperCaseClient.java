import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class UpperCaseClient {

    private static final String SERVER_HOST = "localhost"; 
    private static final int SERVER_PORT = 9090;           

    public static void main(String[] args) {
        System.out.println("=== Uppercase Client ===");
        System.out.println("Connecting to server at "
            + SERVER_HOST + ":" + SERVER_PORT + "...");

        try (
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connected! Type a message and press Enter.");
            System.out.println("Type 'quit' to exit.\n");

            while (true) {
                System.out.print("You: ");
                String userInput = scanner.nextLine(); // Reads the user's input

                if (userInput.equalsIgnoreCase("quit")) {
                    System.out.println("Disconnecting...");
                    break;
                }

                out.println(userInput);         // Sends message to server
                String response = in.readLine(); // Reads the server's response
                System.out.println("Server: " + response + "\n");
            }

        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
            System.out.println("Make sure the server is running first!");
        }
    }
}
