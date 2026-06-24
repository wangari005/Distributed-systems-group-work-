import java.net.*;
import java.util.Scanner;

public class UDPMathClient {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");
        int serverPort = 9090;

        Scanner scanner = new Scanner(System.in);
        byte[] buffer = new byte[1024];

        System.out.println("UDP Math Client ready.");
        System.out.println("Format: num1 operator num2  (e.g. 10 + 5)");
        System.out.println("Type 'exit' to quit.");

        while (true) {
            System.out.print("Enter expression: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) break;

            byte[] sendData = input.getBytes();
            DatagramPacket request = new DatagramPacket(
                sendData, sendData.length, serverAddress, serverPort
            );
            socket.send(request);

            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);

            String result = new String(response.getData(), 0, response.getLength());
            System.out.println("Result: " + result);
        }

        socket.close();
        System.out.println("Client closed.");
    }
}