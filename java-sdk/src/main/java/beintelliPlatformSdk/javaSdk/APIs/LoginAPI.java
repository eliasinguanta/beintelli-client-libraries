package beintelliPlatformSdk.javaSdk.apis;

import beintelliPlatformSdk.javaSdk.utils.Access;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.logging.Logger;

/**
 * takes care of the login. Contains only one but quite complex function. We do not abstract any
 * function of the http request into other classes, which would make our SDK interface extremely
 * confusing. Therefore, the login mechanism is abstracted in a particularly classy way.
 *
 * @hidden
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 */
public class LoginApi {

  private final String loginUrl = "https://api.dev.be-intelli.com/cms/api/auth/local";
  private final Access access;
  private final Logger logger;
  
  /**
   * Constructor needs reference to authorisation String and the logger object.
   *
   * @param access is the reference to authorisation String
   * @param logger for logging
   */
  public LoginApi(Access access, Logger logger) {
    this.access = access;
    this.logger = logger;
  }

  /**
   * Requests an access token from the server. If you log in successfully, access is set in all
   * necessary components such as DataApi or ServiceApi. If the login fails, an error wrapped in
   * a CompletionException will be thrown in the future.
   *
   * @param username to identify user
   * @param password to identify user
   * @return future without return object but with the capability to throw a CompletionException
   */
  public CompletableFuture<Void> login(String username, String password) {
    return CompletableFuture.runAsync(() -> {
      logger.fine("entering login");
      logger.finer("username = " + username + ", password = " + password);

      logger.fine("build request");
      //build body params as String
      String bodyParams = "";
      bodyParams += "identifier=" + username;
      bodyParams += "&";
      bodyParams += "password=" + password;
      logger.finer("body-params = " + bodyParams);

      //build Request
      HttpClient client = HttpClient.newHttpClient();
      Builder builder = HttpRequest.newBuilder();

      //add uri
      URI uri = URI.create(loginUrl);
      logger.finer("uri = " + uri);
      builder = builder.uri(uri);

      //add headers
      Map<String, String> headers = new HashMap<String, String>();
      headers.put("Content-Type", "application/x-www-form-urlencoded");
      logger.finer("headers = " + headers);
      for (Map.Entry<String, String> entry : headers.entrySet()) {
        builder = builder.header(entry.getKey(), entry.getValue());
      }
    
      //add method
      logger.finer("method = POST");
      builder = builder.POST(HttpRequest.BodyPublishers.ofString(bodyParams));

      HttpRequest request = builder.build();
      logger.finest("complete request = " + request);

      //send Request
      HttpResponse<String> response;
      try {
        logger.fine("send request");
        response = client.send(request, BodyHandlers.ofString());
        logger.finer("response = " + response);

        //parse and unpack
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = response.body();

        logger.fine("parse response.body() of type String to JsonNode");
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        logger.finest("parsed response.body() = " + jsonNode);

        logger.fine("get access token");
        String jwt = jsonNode.get("jwt").asText();
        logger.finer("access token = " + jwt);

        //set new access
        logger.fine("set access token");
        this.access.setAccessToken(jwt);
      } catch (IOException | InterruptedException e) {
        logger.warning(e.getMessage());
        throw new CompletionException(e);
      }
    });
  }
}
