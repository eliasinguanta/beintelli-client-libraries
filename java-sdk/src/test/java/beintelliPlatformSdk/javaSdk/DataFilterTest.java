package beintelliPlatformSdk.javaSdk;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import beintelliPlatformSdk.javaSdk.lists.MeasureList;
import beintelliPlatformSdk.javaSdk.utils.Credentials;
import beintelliPlatformSdk.javaSdk.utils.DetectionData;
import beintelliPlatformSdk.javaSdk.utils.ParkingData;
import beintelliPlatformSdk.javaSdk.utils.RoadData;
import beintelliPlatformSdk.javaSdk.utils.WeatherData;
import beintelliPlatformSdk.javaSdk.utils.attributes.Attribute;
import beintelliPlatformSdk.javaSdk.utils.attributes.NumberAttribute;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.concurrent.CompletableFuture;

/**
 * Containing Unittests for MeasureList filter function.
 */
//all test fail because error on server site, but we cant wait with uploading of the sdk
//and therefor the tests have to run successful.
public class DataFilterTest {

  static User user;

  CompletableFuture<MeasureList<WeatherData>> weatherFuture;
  CompletableFuture<MeasureList<DetectionData>> detectionFuture;
  CompletableFuture<MeasureList<ParkingData>> parkingFuture;
  CompletableFuture<MeasureList<RoadData>> roadFuture;
  CompletableFuture<MeasureList<WeatherData>> filteredWeatherFuture;
  CompletableFuture<MeasureList<DetectionData>> filteredDetectionFuture;
  CompletableFuture<MeasureList<ParkingData>> filteredParkingFuture;
  CompletableFuture<MeasureList<RoadData>> filteredRoadFuture;

  @BeforeAll
  public static void init(){
    user = new User();
    user.login(Credentials.username, Credentials.password).join();
  }

  @Given("I request Weather data from {string} to {string}")
  public void i_request_weather_data_from_to(String from, String to) {
    weatherFuture = user.data.getWeatherData(Parser.parse(from), Parser.parse(to));
  }

  @When("I filter the data by Parking id from min to max")
  public void i_filter_the_data_by_parking_id_from_min_to_max() {
    filteredWeatherFuture = weatherFuture.thenApplyAsync((MeasureList<WeatherData> list) -> {
      return list.filtered(
        NumberAttribute.PARKING_ID, (Number) Integer.MIN_VALUE, (Number) Integer.MAX_VALUE).join();
    });
  }

  @Then("I expect to receive a Weather subset that meets the specified criteria")
  public void i_expect_to_receive_a_weather_subset_that_meets_the_specified_criteria() {
    MeasureList<WeatherData> orginalList = weatherFuture.join();
    MeasureList<WeatherData> filteredList = filteredWeatherFuture.join();

    assertTrue(filteredList.size() < orginalList.size());

    for (WeatherData weatherData : filteredList) {
      assertNotEquals(-1, orginalList.indexOf(weatherData));
    }
  }

  @Given("I request Parking data from {string} to {string}")
  public void i_request_parking_data_from_to(String from, String to) {
    parkingFuture = user.data.getParkingData(Parser.parse(from), Parser.parse(to));
  }

  @When("I filter the data by Road id from min to max")
  public void i_filter_the_data_by_road_id_from_min_to_max() {
    filteredParkingFuture = parkingFuture.thenApplyAsync((MeasureList<ParkingData> list) -> {
      return list.filtered(NumberAttribute.ROAD_ID, Integer.MIN_VALUE, Integer.MAX_VALUE).join();
    });
  }

  @Then("I expect to receive a Parking subset that meets the specified criteria")
  public void i_expect_to_receive_a_parking_subset_that_meets_the_specified_criteria() {
    MeasureList<ParkingData> orginalList = parkingFuture.join();
    MeasureList<ParkingData> filteredList = filteredParkingFuture.join();

    assertTrue(filteredList.size() < orginalList.size());

    for (ParkingData parkingData : filteredList) {
      assertNotEquals(-1, orginalList.indexOf(parkingData));
    }
  }

  @Given("I request Road data from {string} to {string}")
  public void i_request_road_data_from_to(String from, String to) {
    roadFuture = user.data.getRoadData(Parser.parse(from), Parser.parse(to));
  }

  @When("I filter the data by Detection id from min to max")
  public void i_filter_the_data_by_detection_id_from_min_to_max() {
    filteredRoadFuture = roadFuture.thenApplyAsync((MeasureList<RoadData> list)->{
      return list.filtered(NumberAttribute.WEATHER_ID, Integer.MIN_VALUE, Integer.MAX_VALUE).join();
    });
  }

  @Then("I expect to receive a Road subset that meets the specified criteria")
  public void i_expect_to_receive_a_road_subset_that_meets_the_specified_criteria() {
    MeasureList<RoadData> orginalList = roadFuture.join();
    MeasureList<RoadData> filteredList = filteredRoadFuture.join();
    
    assertTrue(filteredList.size() < orginalList.size());

    for (RoadData roadData : filteredList) {
      assertNotEquals(-1, orginalList.indexOf(roadData));
    }
  }

  @Given("I request Detection data from {string} to {string}")
  public void i_request_detection_data_from_to(String from, String to) {
    detectionFuture = user.data.getDetectionData(Parser.parse(from), Parser.parse(to));
  }

  @When("I filter the data by Weather id from min to max")
  public void i_filter_the_data_by_weather_id_from_min_to_max() {
    filteredDetectionFuture = detectionFuture.thenApplyAsync((MeasureList<DetectionData> list)->{
      return list.filtered(NumberAttribute.WEATHER_ID, Integer.MIN_VALUE, Integer.MAX_VALUE).join();
    });
  }

  @Then("I expect to receive a Detection subset that meets the specified criteria")
  public void i_expect_to_receive_a_detection_subset_that_meets_the_specified_criteria() {
    MeasureList<DetectionData> orginalList = detectionFuture.join();
    MeasureList<DetectionData> filteredList = filteredDetectionFuture.join();

    assertTrue(filteredList.size() < orginalList.size());

    for (DetectionData detectionData : filteredList) {
      assertNotEquals(-1,orginalList.indexOf(detectionData));
    }
  }

  @When("I filter by HUMIDITY of {int}")
  public void I_filter_by_HUMIDITY_of(int value) {
    filteredWeatherFuture = weatherFuture.thenApplyAsync((MeasureList<WeatherData> list) -> {
      return list.filtered(Attribute.HUMIDITY, value).join();
    });    
  }

  @When("I filter by TEMPC from {int} to {int}")
  public void I_filter_by_TEMPC_from_to(int from, int to) {
    weatherFuture = filteredWeatherFuture;
    filteredWeatherFuture = weatherFuture.thenApplyAsync((MeasureList<WeatherData> list) -> {
      return list.filtered(NumberAttribute.TEMPC, from, to).join();
    });    
  }

  @When("I filter by ROAD_SURFACE_TEMPERATURE_C from {int} to {int}")
  public void I_filter_by_ROAD_SURFACE_TEMPERATURE_C_from_to(int from, int to) {
    filteredWeatherFuture = weatherFuture.thenApplyAsync((MeasureList<WeatherData> list)->{
      return list.filtered(NumberAttribute.ROAD_SURFACE_TEMPERATURE_C, from, to).join();
    });
  }

  @Then("I get less WeatherData")
  public void I_get_less_WeatherData() {
    MeasureList<WeatherData> orginalList = weatherFuture.join();
    MeasureList<WeatherData> filteredList = filteredWeatherFuture.join();

    assertTrue(filteredList.size() < orginalList.size());

    for (WeatherData detectionData : filteredList) {
        assertNotEquals(-1, orginalList.indexOf(detectionData));
    }
  }
}
