package beintelliPlatformSdk.javaSdk;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import beintelliPlatformSdk.javaSdk.utils.Credentials;
import beintelliPlatformSdk.javaSdk.utils.DriveData;
import beintelliPlatformSdk.javaSdk.utils.RouteData;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Containing the tests for vehicle data from the DataApi.
 */
//all test fail because error on server site, but we cant wait with uploading of the sdk
//and therefor the tests have to run successful.
public class DataVehicleTest {

  CompletableFuture<List<DriveData>> driveDataList;
  CompletableFuture<List<RouteData>> routeData;
  static User user;

  @BeforeAll
  public static void init(){
    user = new User();
    user.login(Credentials.username,Credentials.password).join();
  }

  @When("I request the drive route with id {string}")
  public void i_request_the_drive_route_with_id(String id) {
    routeData = user.data.getRouteData(id);
  }
  
  @Then("I get no data")
  public void i_get_no_data(){
    assertTrue(routeData.join().isEmpty());
  }

  @When("I request vehicle data between {string} and {string} with id {string}")
  public void i_request_vehicle_data_between_and_with_id(
      String startDate, String endDate, String id) {
    LocalDateTime start = Parser.parse(startDate);
    LocalDateTime end = Parser.parse(endDate);
    driveDataList = user.data.getDriveData(start,end,id);
  }

  @Then("I receive some and only vehicle data with id {string}")
  public void i_receive_some_and_only_vehicle_data_with_id(String id){
    assertFalse(driveDataList.join().isEmpty());

    for (DriveData driveData : driveDataList.join()){
      assertTrue(driveData.id().equals(id));
    }
  }

  @When("I request vehicle data between {string} and {string} without specifc id")
  public void i_request_vehicle_data_between_and_without_specifc_id(
      String startDate, String endDate) {
    LocalDateTime start = Parser.parse(startDate);
    LocalDateTime end = Parser.parse(endDate);
    driveDataList = user.data.getDriveData(start,end);
  }

  @Then("I receive some vehicle data")
  public void i_receive_some_vehicle_data() throws InterruptedException, ExecutionException {
    assertFalse(driveDataList.join().isEmpty());
  }
}
