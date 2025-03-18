package beintelliPlatformSdk.javaSdk.utils;

/**
 * Wrapper Class for the access-token. Act as an Pointer.
 * The access token allows the apis to access the beintelli resources without
 * saving the credentials.
 * Each API class has this reference to the access token.
 *
 * @hidden 
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 */
public class Access {

  private String accessToken; 

  /**
   * Creates Wrapper object.
   */
  public Access() {
    this.accessToken = "";
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = new String(accessToken);
  }

  public String getAccessToken() {
    return new String(this.accessToken);
  }
}
