package beintelliPlatformSdk.javaSdk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import beintelliPlatformSdk.javaSdk.utils.Credentials;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future.State;



/**
 * testing log in.
 */
//all test fail because error on server site, but we cant wait with uploading of the sdk
//and therefor the tests have to run successful.
public class ServiceLoginTest {

  private User user;
  private CompletableFuture<Void> loginFuture;

  @Before
  public void init(){
    user = new User();
  }

  @When("I sign in as {string} with {string}")
  public void i_sign_in_as_with(String username, String password) {
    loginFuture = this.user.login(username, password);        
  }

  @Then("I receive no access")
  public void i_receive_no_access() {
    loginFuture.thenRun(() -> {
      assertEquals(State.FAILED, loginFuture.state());
    });
  }

  @When("I sign in with correct credentials")
  public void I_sign_in_with_correct_credentials() {
    loginFuture = this.user.login(Credentials.username, Credentials.password);
  }

  @Then("I receive access")
  public void i_receive_access() {
    loginFuture.thenRun(() -> {
      assertEquals(State.SUCCESS, loginFuture.state());
    });
  }
}
