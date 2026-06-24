import java.io.*;
import java.net.*;
import java.util.Scanner;

public class DictionaryClient {
    private static final String HOST = "localhost";
    private static final int PORT = 5050;

    public static void main(String[] args) {
        System.out.println("=== Online Dictionary Client ===");
        System.out.println("Connecting to server at " + HOST + ":" + PORT + "...\n");

        try (
            Socket socket = new Socket(HOST, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connected! Type a word to look it up, or type 'exit' to quit.\n");

            while (true) {
                System.out.print("Enter word: ");
                String word = scanner.nextLine().trim();

                if (word.isEmpty()) continue;

                out.println(word);  // Send word to server

                String response = in.readLine();  // Read definition back
                System.out.println("Definition: " + response + "\n");

                if (word.equalsIgnoreCase("exit")) break;
            }

        } catch (ConnectException e) {
            System.out.println("Could not connect. Is DictionaryServer running on port " + PORT + "?");
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}