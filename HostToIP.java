import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class HostToIP {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter hostname: ");
        String hostname = scanner.nextLine().trim();

        try {
            InetAddress address = InetAddress.getByName(hostname);
            System.out.println("Host: " + address.getHostName());
            System.out.println("IP Address: " + address.getHostAddress());
        } catch (UnknownHostException e) {
            System.out.println("Error: Could not resolve host '" + hostname + "'");
        } finally {
            scanner.close();
        }
    }
}