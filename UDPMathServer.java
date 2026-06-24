import java.net.*;

public class UDPMathServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(9090);
        System.out.println("UDP Math Server started on port 9090...");

        byte[] buffer = new byte[1024];

        while (true) {
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            socket.receive(request);

            String message = new String(request.getData(), 0, request.getLength()).trim();
            System.out.println("Received: " + message);

            InetAddress clientAddress = request.getAddress();
            int clientPort = request.getPort();

            String[] parts = message.split(" ");
            String result;

            try {
                double a = Double.parseDouble(parts[0]);
                String op = parts[1];
                double b = Double.parseDouble(parts[2]);
                double ans;

                switch (op) {
                    case "+": ans = a + b; break;
                    case "-": ans = a - b; break;
                    case "*": ans = a * b; break;
                    case "/":
                        if (b == 0) throw new ArithmeticException("Division by zero");
                        ans = a / b; break;
                    default: throw new IllegalArgumentException("Unknown operator: " + op);
                }
                result = String.valueOf(ans);
            } catch (Exception e) {
                result = "ERROR: " + e.getMessage();
            }

            byte[] responseBytes = result.getBytes();
            DatagramPacket response = new DatagramPacket(
                responseBytes, responseBytes.length, clientAddress, clientPort
            );
            socket.send(response);
            System.out.println("Sent result: " + result);
        }
    }
}