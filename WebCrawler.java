import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {

    public static void main(String[] args) {
        // URL to crawl (you can change this to any website)
        String targetURL = "https://www.example.com";

        System.out.println("=== Web Crawler ===");
        System.out.println("Crawling: " + targetURL);
        System.out.println("-------------------");

        List<String> links = extractLinks(targetURL);

        if (links.isEmpty()) {
            System.out.println("No hyperlinks found.");
        } else {
            System.out.println("Found " + links.size() + " hyperlink(s):\n");
            for (int i = 0; i < links.size(); i++) {
                System.out.println((i + 1) + ". " + links.get(i));
            }
        }
    }

    /**
     * Connects to the given URL, reads its HTML, and extracts all href links.
     *
     * @param urlString the URL to crawl
     * @return a list of hyperlinks found in the HTML
     */
    public static List<String> extractLinks(String urlString) {
        List<String> links = new ArrayList<>();

        try {
            // Step 1: Open a connection to the URL
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            // Set a User-Agent so the server doesn't reject the request
            connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (compatible; JavaCrawler/1.0)");

            // Step 2: Read the HTML content line by line
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
            );

            StringBuilder htmlContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                htmlContent.append(line).append("\n");
            }
            reader.close();

            // Step 3: Use regex to find all href="..." values
            // This pattern matches href="..." and href='...'
            Pattern pattern = Pattern.compile(
                "href=[\"']([^\"']+)[\"']",
                Pattern.CASE_INSENSITIVE
            );

            Matcher matcher = pattern.matcher(htmlContent.toString());

            while (matcher.find()) {
                String link = matcher.group(1); // Extract the URL inside href
                links.add(link);
            }

        } catch (Exception e) {
            System.out.println("Error crawling URL: " + e.getMessage());
        }

        return links;
    }
}
