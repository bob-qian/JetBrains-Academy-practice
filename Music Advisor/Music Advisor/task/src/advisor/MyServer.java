package advisor;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MyServer {

    private static void HTTPPOSTRequest(HttpClient myClient) throws IOException, InterruptedException {
        HttpRequest myRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("login=admin&password=admin"))
                .build();

        HttpResponse<?> response = myClient.send(myRequest, HttpResponse.BodyHandlers.discarding());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    private static void HTTPGETRequest() throws IOException, InterruptedException {
        // Use static factory method to build a new HttpClient object
        HttpClient myClient = HttpClient.newBuilder().build();

        // Create a GET request instance
        HttpRequest myRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080"))
                .build();

        // Send the request and store it in an object implementing the HttpResponse interface
        // Uses a body handler to convert bytes to string form
        try {
            HttpResponse<String> response = myClient.send(myRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            System.out.println("Error.");
        };

        HTTPPOSTRequest(myClient);
    }


    private static void createHTTPServer() throws IOException {
        // Create an instance of the HttpServer class
        HttpServer myServer = HttpServer.create();

        // Bind it to IP and port:
        // This creates an http server that will listen for incoming TCP connections
        // from clients on 8080 port
        myServer.bind(new InetSocketAddress(8080), 0);

        // Create a handler that implements the HttpHandler interface
        HttpHandler myHandler = new HttpHandler() {
            public void handle(HttpExchange exchange) throws IOException {
                // This context handler will always return "hello, world"
                String hello = "hello, world";
                exchange.sendResponseHeaders(200, hello.length());
                exchange.getResponseBody().write(hello.getBytes());
                exchange.getResponseBody().close();

                // Get the Spotify authorization code query
                String query = exchange.getRequestURI().getQuery();
            }
        };

        // When an HTTP request is received, the appropriate
        // HttpContext (and handler) is located by finding the context
        // whose path is the longest matching prefix of the request URI's path

        // Create the context using createContext() and passing a string of the
        // URI path and myHandler
        myServer.createContext("/", myHandler);

        myServer.start();
    }


}
