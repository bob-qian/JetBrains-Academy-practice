package advisor;

import java.util.Base64;

public class PrivateConfig {

    private static String spotifyClientId = "6fd95ac46e9841c5acd0b107f5ebfbeb";
    private static String spotifyClientSecret = "62c84881e2ef4f49822e09acb1ccabd6";

    protected static String getClientBase64() {
        // Convert spotify client id and client secret to base 64
        String plainCredentials  = spotifyClientId + ":" + spotifyClientSecret;
        String base64Credentials  = Base64.getUrlEncoder().encodeToString(plainCredentials.getBytes());
        return base64Credentials;
    }
}
