import java.io.*;
import java.net.*;
import java.util.Scanner;

public class PingPongClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(host, port)) {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter message to server: ");
            String message = scanner.nextLine();

            out.println(message);

            String response = in.readLine();

            if (response != null) {
                System.out.println("Server response: " + response);
            } else {
                System.out.println("No response from server (message may have been ignored).");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}