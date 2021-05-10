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

public class Auth {
    private static String authCode;


    // Makes a POST request to Spotify API with the authorization code, returns String access token
    protected static String getAccessToken(String serverPath, String redirectURI) throws IOException, InterruptedException{
        String authorizationHeader = "Basic " + PrivateConfig.getClientBase64();

        // Create an HttpClient object
        HttpClient myClient = HttpClient.newBuilder().build();

        // Create a POST request for Spotify API
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverPath + "/api/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", authorizationHeader)
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code" + "&" + authCode + "&redirect_uri=" + redirectURI))
                .build();

        // Send the HTTP POST request
        HttpResponse<String> response = myClient.send(request, HttpResponse.BodyHandlers.ofString());

        // If successful, return String access token
        if (response.statusCode() == 200) {
            return response.body();
        } else { // else return an empty string
            return "";
        }
    }

    // Gets Spotify authorization code and handles the UI and waiting for that process
    protected static String getAuthCode(String authLink) throws IOException {
        // Setup and start the HttpServer to listen for user authorization
        HttpServer myServer = HttpServer.create();
        initializeServer(myServer);
        myServer.start();

        // Display authorization link for user to visit
        System.out.println("use this link to request the access code:");
        System.out.println(authLink);

        // Sleep thread until user has approved
        System.out.println("waiting for code...");
        while (authCode == null) {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("error.");
            }
        }

        // Then stop the server
        myServer.stop(1);

        // And return the user's authCode
        return authCode;
    }

    // Initializes the HTTP server and its handler that accesses the Spotify authorization code
    private static void initializeServer(HttpServer myServer) throws IOException {
        // Bind it to IP and port:
        // This creates an http server that will listen for incoming TCP connections
        // from clients on 8080 port
        myServer.bind(new InetSocketAddress(8080), 0);

        // Create a handler that implements the HttpHandler interface
        HttpHandler myHandler = new HttpHandler() {
            public void handle(HttpExchange exchange) throws IOException {
                // Only proceed if authCode has not already been set
                if (authCode == null) {
                    // Get the query parameter with the authorization code
                    String query = exchange.getRequestURI().getQuery();
                    String message;
                    if (query != null && query.contains("code")) {
                        authCode = query;
                        message = "Got the code. Return back to your program.";
                    } else {
                        message = "Authorization code not found. Try again.";
                    }

                    // Send response code
                    exchange.sendResponseHeaders(200, message.length());

                    // Write the response to the localhost page
                    exchange.getResponseBody().write(message.getBytes());
                    exchange.getResponseBody().close();
                }
            }
        };

        // When an HTTP request is received, the appropriate
        // HttpContext (and handler) is located by finding the context
        // whose path is the longest matching prefix of the request URI's path

        // Create the context using createContext() and passing a string of the
        // URI path and myHandler
        myServer.createContext("/", myHandler);
    }
}
