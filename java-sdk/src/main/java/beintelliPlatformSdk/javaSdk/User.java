package beintelliPlatformSdk.javaSdk;

import beintelliPlatformSdk.javaSdk.apis.DataApi;
import beintelliPlatformSdk.javaSdk.apis.LoginApi;
import beintelliPlatformSdk.javaSdk.apis.ServiceApi;
import beintelliPlatformSdk.javaSdk.utils.Access;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/** 
 * User interface of the entire SDK.
 * It is initialized without parameters, but no request to the server before 
 * login will be successful.
 *
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 */
public class User {

  //apis
  /**
   * Handling all server requests for infrastructure and vehicle data.
   */
  public DataApi data;

  /**
   * Handling all server requests for services.
   */
  public ServiceApi service;
  private LoginApi login;

  //access
  private Access access;

  /**
   * For Logging on level fine, finer, finest, warning and error level.
   */
  public Logger logger;

  /**
   * Creates an User.
   */
  public User() {
    this.logger = Logger.getLogger(User.class.getName() + " " + UUID.randomUUID().toString());
        
    logger.fine("Create User");
    this.access = new Access();
    this.login = new LoginApi(this.access, this.logger);
    this.data = new DataApi(this.access, this.logger);
    this.service = new ServiceApi(this.access, this.logger);
  }
    
  /**
   * request access for entire SDK.
   *
   * @param username for the beintelli platform
   * @param password for the beintelli platform
   * @return future that throws exception if login fails
   */
  public CompletableFuture<Void> login(String username, String password) {
    return login.login(username, password);
  }
}
