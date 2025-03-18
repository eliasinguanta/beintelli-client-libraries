package beintelliplattformsdk.beintelliplatformjavaexample.login;


import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import beintelliplattformsdk.beintelliplatformjavaexample.utils.Credential;

public class Login {
    
    public static String login(Credential credential) throws IOException, InterruptedException{
        
            //define destination
            String url = "https://api.dev.be-intelli.com/rest/token";

            //build body params as String
            String bodyParams ="";
            bodyParams += "username=" + URLEncoder.encode(credential.username(), StandardCharsets.UTF_8);
            bodyParams += "&";
            bodyParams += "password=" + URLEncoder.encode(credential.password(), StandardCharsets.UTF_8);
            


            //build Request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(bodyParams))
                .build();

            //send Request
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            
            //unpack
            String responseBody = response.body();
            JsonParser jsonParser = JsonParserFactory.getJsonParser();
            return jsonParser.parseMap(responseBody).get("access_token").toString();
    }
}
