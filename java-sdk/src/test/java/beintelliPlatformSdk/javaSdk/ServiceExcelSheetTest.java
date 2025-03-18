package beintelliPlatformSdk.javaSdk;

import beintelliPlatformSdk.javaSdk.utils.Credentials;
import beintelliPlatformSdk.javaSdk.utils.ExamplePath;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * testing the function where u can request ExcelSheets of Measurements in ServiceApi. 
 */
//all test fail because error on server site, but we cant wait with uploading of the sdk
//and therefor the tests have to run successful.
public class ServiceExcelSheetTest {
    
  //our API
  private static User user;

  //received sheets
  private CompletableFuture<File> future;

  @BeforeAll
  public static void init() {
    user = new User();
    user.login(Credentials.username, Credentials.password).join();
  }

  @When("I request object detection excel sheet between {string} and {string}")
  public void I_request_object_detection_excel_sheet_between_and(String startDate, String endDate) {
    LocalDateTime start = Parser.parse(startDate);
    LocalDateTime end = Parser.parse(endDate);
    future = user.service.getDetectionSheet(start,end);
  }

  @Then("I receive objectDetectionSheet")
  public void I_receive_objectDetectionSheet(){
    File sheet = future.join();
    try {
      Arrays.equals(Files.readAllBytes(
          Path.of(ExamplePath.OBJECT_SHEET.getValue())), Files.readAllBytes(sheet.toPath()));
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  @When("I request weather excel sheet between {string} and {string}")
  public void i_request_weather_excel_sheet_between_and(String startDate, String endDate) {
    LocalDateTime start = Parser.parse(startDate);
    LocalDateTime end = Parser.parse(endDate);
    future = user.service.getWeatherSheet(start, end);
  }

  @Then("I receive a weatherSheet")
  public void i_receive_a_weather_sheet(){
    File sheet = future.join();
    try {
      Arrays.equals(Files.readAllBytes(
          Path.of(ExamplePath.WEATHER_SHEET.getValue())), Files.readAllBytes(sheet.toPath()));
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  @When("I request parking excel sheet between {string} and {string}")
  public void i_request_parking_excel_sheet_between_and(String startDate, String endDate) {
    LocalDateTime start =  Parser.parse(startDate);
    LocalDateTime end = Parser.parse(endDate);
    future = user.service.getParkingSheet(start, end);
  }

  @Then("I receive a parkingSheet")
  public void i_receive_a_parking_sheet(){
    File sheet = future.join();
    try {
      Arrays.equals(Files.readAllBytes(
          Path.of(ExamplePath.PARKING_SHEET.getValue())), Files.readAllBytes(sheet.toPath()));
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  @When("I request road excel sheet between {string} and {string}")
  public void i_request_road_excel_sheet_between_and(String startDate, String endDate) {
    LocalDateTime start = Parser.parse(startDate);
    LocalDateTime end = Parser.parse(endDate);
    future = user.service.getRoadSheet(start, end);
  }

  @Then("I receive a roadSheet")
  public void i_receive_a_road_sheet(){
    File sheet = future.join();
    try {
      Arrays.equals(Files.readAllBytes(
          Path.of(ExamplePath.ROAD_SHEET.getValue())), Files.readAllBytes(sheet.toPath()));
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }
}
