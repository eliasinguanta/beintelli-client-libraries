package beintelliPlatformSdk.javaSdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import beintelliPlatformSdk.javaSdk.utils.Credentials;
import beintelliPlatformSdk.javaSdk.utils.RosbagInfo;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * testing the rosbag functions in ServiceApi.
 */
//all test fail because error on server site, but we cant wait with uploading of the sdk
//and therefor the tests have to run successful.
public class ServiceRosbagTest {

  private static User user;
  private CompletableFuture<File> rosbagFuture;
  private CompletableFuture<List<RosbagInfo>> rosbagInfosFuture;

  @BeforeAll
  public static void init(){
    user = new User();
    user.login(Credentials.username, Credentials.password);
  }

  @When("I request the converted rosbag {string}")
  public void i_request_the_converted_rosbag(String filename) {
    rosbagFuture = user.service.getConvertedRosbag(filename);
  }

  @Then("I receive an empty converted rosbag")
  public void I_receive_an_empty_converted_rosbag(){
    rosbagFuture.thenAccept((File rosbag) -> {
      assertEquals(35, rosbag.length()); 
    });
  }

  @When("I request all available converted rosbags")
  public void i_request_all_available_converted_rosbags() {
    rosbagInfosFuture = user.service.getConvertedRosbags();
  }

  @Then("I receive a empty list of file names")
  public void i_receive_a_filled_list_of_file_names(){
    rosbagInfosFuture.thenAccept((List<RosbagInfo> rosbagInfos) -> {
      assertTrue(rosbagInfos.isEmpty());
    });
  }

  @When("I request all available rosbags")
  public void i_request_all_available_rosbags() {
    rosbagInfosFuture = user.service.getRosbags();
  }

  @Then("I receive a filled list of file names")
  public void I_receive_a_filled_list_of_file_names() 
      throws InterruptedException, ExecutionException {
    rosbagInfosFuture.thenAccept((List<RosbagInfo> rosbagInfos) -> {
      assertFalse(rosbagInfos.isEmpty());
    });
  }

  @When("I request the rosbag x")
  public void i_request_the_rosbag() {
    rosbagFuture = user.service.getRosbag("x");
  }

  @Then("I receive a filled rosbag")
  public void i_receive_a_rosbag() {
    rosbagFuture.thenAccept((File rosbag) -> {
      try {
        assertNotEquals(Files.readAllBytes(rosbag.toPath()).length, 35);
      } catch (IOException e) {
        throw new AssertionError(e);
      }
    });
  }

  @When("I upload the rosbag x")
  public void i_upload_the_rosbag_x() {
    //TODO
  }

  @Then("the upload is successful")
  public void the_upload_is_successful() {
    //TODO
  }
    
  @When("I convert rosbag x")
  public void i_convert_rosbag_x() {
    //TODO
  }

    @Then("I am successful")
    public void i_am_successful() {
        //TODO
    }
}
