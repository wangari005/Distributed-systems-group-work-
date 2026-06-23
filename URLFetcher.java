import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class URLFetcher {
    public static void main(String[] args) {
        String targetURL = "http://www.buyya.com";

        try {
            URL url = new URL(targetURL);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            System.out.println("Fetching content from: " + targetURL);
            System.out.println("=".repeat(50));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("Error fetching URL: " + e.getMessage());
        }
    }
}