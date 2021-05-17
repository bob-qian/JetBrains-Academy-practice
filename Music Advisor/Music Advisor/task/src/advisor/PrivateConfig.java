package advisor;

import java.util.Base64;

public class PrivateConfig {

    protected static String getClientBase64() {
        // Convert spotify client id and client secret to base 64
        String plainCredentials  = spotifyClientId + ":" + spotifyClientSecret;
        String base64Credentials  = Base64.getUrlEncoder().encodeToString(plainCredentials.getBytes());
        return base64Credentials;
    }
}
