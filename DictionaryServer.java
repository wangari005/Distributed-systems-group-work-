import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class DictionaryServer {
    private static final int PORT = 5050;
    private static final int MAX_THREADS = 10; // Bounded thread pool — prevents resource exhaustion
    private static final Map<String, String> dictionary = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    static {
        dictionary.put("algorithm", "A step-by-step procedure for solving a problem or accomplishing a task.");
        dictionary.put("binary", "A number system using only two digits: 0 and 1.");
        dictionary.put("compiler", "A program that translates source code into machine code.");
        dictionary.put("database", "An organized collection of structured data stored electronically.");
        dictionary.put("encapsulation", "An OOP principle that bundles data and methods within a single unit.");
        dictionary.put("firewall", "A network security system that monitors and controls incoming and outgoing traffic.");
        dictionary.put("garbage collection", "Automatic memory management that reclaims memory from unused objects.");
        dictionary.put("hashing", "The process of converting input data into a fixed-size string of characters.");
        dictionary.put("interface", "A contract in Java specifying methods a class must implement.");
        dictionary.put("jvm", "Java Virtual Machine — the runtime environment that executes Java bytecode.");
        dictionary.put("kernel", "The core component of an operating system managing hardware and system processes.");
        dictionary.put("latency", "The delay before data transfer begins following an instruction.");
        dictionary.put("mutex", "A mutual exclusion object used to prevent simultaneous access to a shared resource.");
        dictionary.put("namespace", "A container that holds a set of identifiers to avoid naming conflicts.");
        dictionary.put("overloading", "Defining multiple methods with the same name but different parameters.");
        dictionary.put("polymorphism", "The ability of different objects to respond to the same method call in different ways.");
        dictionary.put("queue", "A data structure that follows First-In-First-Out (FIFO) ordering.");
        dictionary.put("recursion", "A method of solving a problem where the solution depends on solutions to smaller instances.");
        dictionary.put("socket", "An endpoint for communication between two machines over a network.");
        dictionary.put("thread", "The smallest unit of a process that can be scheduled for execution.");
        dictionary.put("unicode", "A universal character encoding standard covering most of the world's writing systems.");
        dictionary.put("variable", "A named storage location in memory that holds a value.");
        dictionary.put("webhook", "A way for an app to send real-time data to another application.");
        dictionary.put("xml", "Extensible Markup Language — a format for encoding structured data as text.");
        dictionary.put("yaml", "A human-readable data serialization format commonly used for configuration files.");
    }

    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Dictionary Server running on port " + PORT);
            System.out.println("Dictionary loaded with " + dictionary.size() + " entries.\n");

            while (true) {
                final Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                threadPool.submit(() -> handleClient(clientSocket)); // Bounded pool, not raw threads
            }
        } finally {
            threadPool.shutdown();
        }
    }

    private static void handleClient(Socket socket) {
        try (socket; // Auto-closes client socket when done — fixes resource leak
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String word;
            while ((word = in.readLine()) != null) {
                word = word.trim();
                if (word.equalsIgnoreCase("exit")) {
                    out.println("Goodbye!");
                    break;
                }
                System.out.println("Lookup request: \"" + word + "\"");
                String definition = dictionary.getOrDefault(
                    word,
                    "Word not found. Try: algorithm, binary, socket, thread, recursion..."
                );
                out.println(definition);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + e.getMessage());
        }
    }
}