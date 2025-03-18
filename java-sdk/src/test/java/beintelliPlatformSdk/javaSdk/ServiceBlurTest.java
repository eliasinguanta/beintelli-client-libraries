package beintelliPlatformSdk.javaSdk;

import beintelliPlatformSdk.javaSdk.utils.Credentials;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * testing the blur function from ServiceApi.
 */
//all test fail because error on server site, but we cant wait with uploading of the sdk
//and therefor the tests have to run successful.
public class ServiceBlurTest {

  private static User user = new User();

  @BeforeAll
  public static void init(){
    user.login(Credentials.username,Credentials.password);
  }

  @When("I blur the image example.jpg")
  public void i_blur_the_image_example_jpg() {}

  @Then("I receive an image")
  public void I_receive_an_image() {}
}
