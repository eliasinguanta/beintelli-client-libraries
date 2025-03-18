package beintelliPlatformSdk.javaSdk;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import beintelliPlatformSdk.javaSdk.lists.MeasureList;
import beintelliPlatformSdk.javaSdk.utils.Credentials;
import beintelliPlatformSdk.javaSdk.utils.DetectionData;
import beintelliPlatformSdk.javaSdk.utils.ExamplePath;
import beintelliPlatformSdk.javaSdk.utils.MapInfo;
import beintelliPlatformSdk.javaSdk.utils.ParkingData;
import beintelliPlatformSdk.javaSdk.utils.ParkinglotAvailability;
import beintelliPlatformSdk.javaSdk.utils.ParkinglotInfo;
import beintelliPlatformSdk.javaSdk.utils.RoadData;
import beintelliPlatformSdk.javaSdk.utils.RsuData;
import beintelliPlatformSdk.javaSdk.utils.WeatherData;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Containing the tests for the infrastructur data from the DataApi.
 */
//all test fail because error on server site, but we cant wait with uploading of the sdk
//and therefor the tests have to run successful.
public class DataInfrastructureTest {

  //our API
  private static User user;
 
  //received results
  private CompletableFuture<List<MapInfo>> mapInfo;
  private CompletableFuture<MeasureList<WeatherData>> weatherData;
  private CompletableFuture<MeasureList<ParkingData>> parkingData;
  private CompletableFuture<MeasureList<RoadData>> roadData;
  private CompletableFuture<MeasureList<DetectionData>> objectData;
  private CompletableFuture<List<RsuData>> rsuData;
  private CompletableFuture<List<ParkinglotAvailability>> parkinglotAvail;
  private CompletableFuture<List<ParkinglotInfo>> parkinglotInfo;
  private static CompletableFuture<File> hdMap;

  @BeforeAll
  public static void load() {
    user = new User();
    user.login(Credentials.username, Credentials.password).join();
  }

  @When("I request the list of HD maps")
  public void I_request_the_list_of_HD_maps() {
    mapInfo = user.data.getHdMaps();
  }

  @Then("I receive a list of HD maps")
  public void I_receive_a_list_of_HD_maps() {
    assertFalse(mapInfo.join().isEmpty());
  }

  @When("I request the rsu data")
  public void I_request_the_rsu_data() {
    rsuData = user.data.getRsuData();
  }

  @Then("I receive rsu data")
  public void I_receive_rsu_data() {
    assertFalse(rsuData.join().isEmpty());
  }

  @When("I request the HD map {string}")
  public void I_request_the_HD_map(String filename) {
    hdMap = user.data.getHdMap(new MapInfo(filename));
  }

  @Then("I receive a HD map")
  public void I_receive_a_HD_map() {
    File file = hdMap.join();
    try {
      assertTrue(Arrays.equals(Files.readAllBytes(file.toPath()),
          Files.readAllBytes(Path.of(ExamplePath.MAP.getValue()))));
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  @When("I request object detection data between {string} and {string}")
  public void I_request_object_detection_data_between_and(String startDate, String endDate) {
    objectData = user.data.getDetectionData(Parser.parse(startDate), Parser.parse(endDate));
  }

  @Then("I get an empty list")
  public void I_get_an_empty_list() {
    assertTrue(objectData.join().isEmpty());
  }

  @When("I request weather data between {string} and {string}")
  public void i_request_weather_data_between_and(String startDate, String endDate) {
    weatherData = user.data.getWeatherData(Parser.parse(startDate), Parser.parse(endDate));
  }

  @Then("I receive a filled list of weather data")
  public void i_receive_a_filled_list_of_weather_data() {
    assertFalse(weatherData.join().isEmpty());
  }

  @When("I request parking data between {string} and {string}")
  public void i_request_parking_data_between_and(String startDate, String endDate) {
    parkingData = user.data.getParkingData(Parser.parse(startDate), Parser.parse(endDate));
  }

  @Then("I receive a filled list of parking data")
  public void i_receive_a_filled_list_of_parking_data() {
    assertFalse(parkingData.join().isEmpty());
  }

  @When("I request road data between {string} and {string}")
  public void i_request_road_data_between_and(String startDate, String endDate) {
    roadData = user.data.getRoadData(Parser.parse(startDate), Parser.parse(endDate));
  }

  @Then("I receive a filled list of road data")
  public void i_receive_a_filled_list_of_road_data() {
    assertFalse(roadData.join().isEmpty());
  }

  @When("I request parkinglot availability data for id {string}")
  public void i_request_parkinglot_availability_data_for_id(String gapId) {
    parkinglotAvail = user.data.getParkinglotAvailability(gapId);
  }

  @Then("I receive a filled list of parkinglot availability data")
  public void i_receive_a_filled_list_of_parkinglot_availability_data() {
    assertFalse(parkinglotAvail.join().isEmpty());
  }

  @When("I request parkinglot information data for id {string}")
  public void i_request_parkinglot_information_data_for_id(String gapId) {
    parkinglotInfo = user.data.getParkinglotInfo(gapId);
  }

  @Then("I receive a filled list of parkinglot information data")
  public void i_receive_a_filled_list_of_parkinglot_information_data() {
    assertFalse(parkinglotInfo.join().isEmpty());
  }
}
