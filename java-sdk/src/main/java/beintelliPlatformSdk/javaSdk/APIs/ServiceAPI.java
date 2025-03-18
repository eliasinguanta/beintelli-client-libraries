package beintelliPlatformSdk.javaSdk.apis;

import beintelliPlatformSdk.javaSdk.utils.Access;
import beintelliPlatformSdk.javaSdk.utils.RosbagInfo;
import java.io.File;
import java.net.http.HttpRequest;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * Handles all Services.
 *
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 */
public class ServiceApi extends BaseApi{

  /**
   * Constructor needs reference on authorisation String.
   *
   * @param access reference of authorisation String
   * @param logger for logging
   */
  public ServiceApi(Access access, Logger logger) {
    super(access, logger);
  }

  /**
   * request the list of detected objects as excel sheet.
   *
   * @param from start date and time
   * @param to end date and time
   * @return excel sheet with data for detected objects
   */
  public CompletableFuture<File> getDetectionSheet(LocalDateTime from, LocalDateTime to) { 
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getDetectionSheet");
      logger.finer("from = " + from + ", to = " + to);

      //build query
      Map<String, String> query = new HashMap<String, String>();
      query.put("start_tmpstmp", from.toString());
      query.put("end_tmpstmp", to.toString());
      query.put("end_points", "/object_detection_data");
        
      //build request
      HttpRequest request = buildGetRequest(EndPoint.EXCELSHEET, query);
    
      //send request
      File excelSheet = sendFileRequest(request, "detections", "xlsx");
    
      return excelSheet;
    }).exceptionallyAsync((Throwable error) -> {
      logger.warning(error.getMessage());
      return null;
    });
  }

  /**
   * request weather data as excel sheet.
   *
   * @param from start date and time
   * @param to end date and time
   * @return excel sheet filled with weather data
   */
  public CompletableFuture<File> getWeatherSheet(LocalDateTime from, LocalDateTime to) {
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getWeatherSheet");
      logger.finer("from = " + from + ", to = " + to);

      //build query
      Map<String, String> query = new HashMap<String, String>();
      query.put("start_tmpstmp", from.toString());
      query.put("end_tmpstmp", to.toString());
      query.put("end_points", "/weather_data");

      //build request
      HttpRequest request = buildGetRequest(EndPoint.EXCELSHEET, query);

      //send request
      File excelSheet = sendFileRequest(request, "weather", "xlsx");

      return excelSheet;
    }).exceptionallyAsync((Throwable error) -> {
      logger.warning(error.getMessage());
      return null;
    });
  }

  /**
   * request parking data as excel sheet.
   *
   * @param from start date and time
   * @param to start date and time
   * @return excel sheet filled with parking data
   */
  public CompletableFuture<File> getParkingSheet(LocalDateTime from, LocalDateTime to) {
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getParkingSheet");
      logger.finer("from = " + from + ", to = " + to);

      //build query
      Map<String, String> query = new HashMap<String, String>();
      query.put("start_tmpstmp", from.toString());
      query.put("end_tmpstmp", to.toString());
      query.put("end_points", "/parking_data");
    
      //build request
      HttpRequest request = buildGetRequest(EndPoint.EXCELSHEET, query);
    
      //send request
      File excelSheet = sendFileRequest(request, "parking", "xlsx");

      return excelSheet;
    }).exceptionallyAsync((Throwable error) -> {
      logger.warning(error.getMessage());
      return null;
    });
  }

  /**
   * request road data as excel sheet.
   *
   * @param from start date and time
   * @param to end date and time
   * @return excel sheet filled with road data
   */
  public CompletableFuture<File> getRoadSheet(LocalDateTime from, LocalDateTime to) {
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getRoadSheet");
      logger.finer("from = " + from + ", to = " + to);

      //build query
      Map<String, String> query = new HashMap<String, String>();
      query.put("start_tmpstmp", from.toString());
      query.put("end_tmpstmp", to.toString());
      query.put("end_points", "/road_data");
    
      //build request
      HttpRequest request = buildGetRequest(EndPoint.EXCELSHEET, query);
    
      //send request
      File excelSheet = sendFileRequest(request, "road", "xlsx");

      return excelSheet;
    }).exceptionallyAsync((Throwable error) -> {
      logger.warning(error.getMessage());
      return null;
    });
  }

  /**
   * convert an available Rosbag at source to "converted" Rosbag at dest.
   *
   * @param source path to the rosbag
   * @param configSource ???
   * @param config ???
   * @return the State of the request 
   */
  public CompletableFuture<Void> convertRosbag(
      Path source, String configSource, Map<String, String> config) {
    return CompletableFuture.runAsync(() -> {
      logger.fine("entering convertRosbag");
      logger.finer(
          "source = " + source + ", configSource = " + configSource + ", config = " + config);
    });
  }

  /**
   * requests a specific "converted" Rosbag file.
   *
   * @param filename of the rosbag file
   * @return the rosbag file
   */
  public CompletableFuture<File> getConvertedRosbag(String filename) {
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getConvertedRosbag");
      logger.finer("filename = " + filename);

      //build query
      Map<String, String> query = new HashMap<String, String>();
    
      //build request
      HttpRequest request = buildGetRequest(EndPoint.CONVERTED_ROSBAG.getValue() + filename, query);
    
      //send request
      File convertedRosbag = sendFileRequest(request, "convertedRosbag", "bin");

      return convertedRosbag;
    }).exceptionallyAsync((Throwable error) -> {
      logger.warning(error.getMessage());
      return null;
    });
  }

  /**
   * request a list of available "converted" rosbag files.
   *
   * @return the list of filenames of available "converted" rosbags
   */
  public CompletableFuture<List<RosbagInfo>> getConvertedRosbags() {
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getConvertedRosbags");

      //build request
      HttpRequest request = buildGetRequest(EndPoint.DOWNLOAD_CONVERTED_ROSBAGS, null);
    
      //send request
      Map<String, List<Map<?, ?>>> body = sendRosbagInfoRequest(request);

      //parse
      logger.fine("parse response.body() of type Map<String, List<Map<?, ?>>> to List<RosbagInfo>");
      List<RosbagInfo> convertedRosbags = Parser.toRosbagInfoList(body);
      logger.finer("response.body() = " + convertedRosbags);

      return convertedRosbags;
    }).exceptionallyAsync((Throwable error) -> {
      logger.warning(error.getMessage());
      return new ArrayList<RosbagInfo>();
    });
  }

  /**
   * request a list of available rosbag files.
   *
   * @return the list of filenames of available rosbags
   */
  public CompletableFuture<List<RosbagInfo>> getRosbags() {
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getRosbags");

      //build request
      HttpRequest request = buildGetRequest(EndPoint.ROSBAGS, null);
    
      //send request
      Map<String, List<Map<?, ?>>> body = sendRosbagInfoRequest(request);

      //parse
      logger.fine("parse response.body() of type Map<String, List<Map<?, ?>>> to List<RosbagInfo>");
      List<RosbagInfo> rosbags = Parser.toRosbagInfoList(body);
      logger.finer("response.body() = " + rosbags);

      return rosbags;
    }).exceptionallyAsync((Throwable error) -> {
      logger.warning(error.getMessage());
      return new ArrayList<RosbagInfo>();
    });
  }

  /**
   * requests a specific (available) rosbag file.
   *
   * @param filename of the rosbag
   * @return the rosbag file
   */
  public CompletableFuture<File> getRosbag(String filename) {
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getRosbag");
      logger.finer("filename = " + filename);

      //build query
      Map<String, String> query = new HashMap<String, String>();
    
      //build request
      HttpRequest request = buildGetRequest(EndPoint.DOWNLOAD_ROSBAG.getValue() + filename, query);
    
      //send request
      File rosbag = sendFileRequest(request, "rosbag", "bin");
    
      return rosbag;    
    }).exceptionallyAsync((Throwable error) -> {
      logger.warning(error.getMessage());
      return null;
    });
  }

  /**
   * upload a specific rosbag file.
   *
   * @param rosbag file
   * @return request state
   */
  public CompletableFuture<Void> addRosbag(File rosbag) {
    return CompletableFuture.runAsync(() -> {
      logger.fine("entering addRosbag");
      logger.finer("rosbag-path = " + rosbag.toPath());
    });
  }  


  /**
   * upload an image to get it blurred.
   *
   * @param image to blur
   * @return blurred image
   */
  public CompletableFuture<File> getBlurredImage(File image) {
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getBlurredImage");
      logger.finer("image = " + image.toPath());
      return null;
    });
  }
}
