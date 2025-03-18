package beintelliPlatformSdk.javaSdk.apis;

import beintelliPlatformSdk.javaSdk.lists.MeasureList;
import beintelliPlatformSdk.javaSdk.utils.Access;
import beintelliPlatformSdk.javaSdk.utils.DetectionData;
import beintelliPlatformSdk.javaSdk.utils.DriveData;
import beintelliPlatformSdk.javaSdk.utils.MapInfo;
import beintelliPlatformSdk.javaSdk.utils.ParkingData;
import beintelliPlatformSdk.javaSdk.utils.ParkinglotAvailability;
import beintelliPlatformSdk.javaSdk.utils.ParkinglotInfo;
import beintelliPlatformSdk.javaSdk.utils.RoadData;
import beintelliPlatformSdk.javaSdk.utils.RouteData;
import beintelliPlatformSdk.javaSdk.utils.RsuData;
import beintelliPlatformSdk.javaSdk.utils.WeatherData;
import java.io.File;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * Handles all requests regarding infrastructure and vehicles.
 *
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 */
public class DataApi extends BaseApi{

  /**
   * Constructor needs the access reference for the authorisation String.
   * Constructor needs the logger for ... logging.
   *
   * @param access reference for authorisation
   * @param logger for logging
   */
  public DataApi(Access access, Logger logger) {
    super(access, logger);
  }
    
  /**
   * requests all drive data of specific vehicle in an specific time frame.
   *
   * @param from start date and time
   * @param to end date and time
   * @param id of the vehicle
   * @return a list of drive data
   */
  public CompletableFuture<List<DriveData>> getDriveData(
      LocalDateTime from, LocalDateTime to, String id) {

    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getDriveData");
      logger.finer("from = " + from + ", to = " +  to);

      //query parameter
      Map<String, String> query = new HashMap<String, String>();
      query.put("start_date", from.toString());
      query.put("end_date", to.toString());
      if (id != null) {
        query.put("vehicle_id", id);
      }

      //build request
      HttpRequest request = buildGetRequest(EndPoint.DRIVE, query);    

      //send request
      Map<String, List<List<?>>> body = sendDataRequest(request);

      //parse to list of object detection data-objects
      logger.fine("parse response.body() of type Map<String, List<List<?>>> to List<DriveData>");
      List<DriveData> driveDatas = Parser.toDriveList(body);
      logger.finest("parsed response.body() = " + driveDatas.toString());

      return driveDatas;
    }).exceptionallyAsync((Throwable error) -> {
      logger.warning(error.getMessage());
      return new ArrayList<DriveData>();
    });
  }

  /**
   * requests all drive data between in an specific time frame.
   *
   * @param from start date and time
   * @param to end date and time
   * @return a list of drive data 
   */
  public CompletableFuture<List<DriveData>> getDriveData(LocalDateTime from, LocalDateTime to) {
    return getDriveData(from, to, null);      
  }

  /**
   * requests a specific route.
   *
   * @param id of a route
   * @return a Route
   */
  public CompletableFuture<List<RouteData>> getRouteData(String id) {
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getRouteData");
      logger.finer("id = " + id);

      //query parameter
      Map<String, String> query = new HashMap<>();
      query.put("drive_id", id);

      //build request
      HttpRequest request = buildGetRequest(EndPoint.ROUTE, query);

      //send request
      Map<String, List<List<?>>> body = sendDataRequest(request);
            
      logger.fine("parse response.body() of type Map<String, List<List<?>>> to List<RouteData>");
      List<RouteData> route = Parser.toRouteList(body);
      logger.finest("parsed response.body() = " + route);

      return route;
    }).exceptionallyAsync((Throwable error) -> {
      logger.warning(error.getMessage());
      return new ArrayList<RouteData>();
    });
  }

  /**
   * request a list of available HD maps.
   *
   * @return filenames
   */
  public CompletableFuture<List<MapInfo>> getHdMaps() {
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getHDMaps");

      //build request
      HttpRequest request = buildGetRequest(EndPoint.HDMAPS, null);

      //send request
      Map<String, List<List<?>>> body = sendDataRequest(request);

      //parse to list of filenames
      logger.fine("parse response.body() of type Map<String, List<List<?>>> to List<MapInfo>");
      List<MapInfo> hdMaps = Parser.toMapInfoList(body);
      logger.finest("parsed response.body() = " + hdMaps);

      return hdMaps;
    }).exceptionallyAsync((Throwable error) -> {
      logger.warning(error.getMessage());
      return new ArrayList<MapInfo>();
    });
  }

  /**
   * requests all rsu data.
   *
   * @return list of rsu data
   */
  public CompletableFuture<List<RsuData>> getRsuData() {
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getRsuData");

      //build request
      HttpRequest request = buildGetRequest(EndPoint.RSU, null);

      //send request
      Map<String, List<List<?>>> body = sendDataRequest(request);
        
      //parse to list of object detection data-objects
      logger.fine("parse response.body() of type  Map<String, List<List<?>>> to List<RsuData>");
      List<RsuData> rsuDataList = Parser.toRsuList(body);
      logger.finest("parsed response.body() = " + rsuDataList);

      return rsuDataList;
    }).exceptionallyAsync((Throwable error) -> {
      logger.warning(error.getMessage());
      return new ArrayList<RsuData>();
    });
  }

  /**
   * request a specific HD map.
   *
   * @param filename of the HD map
   * @return HD map
   */
  public CompletableFuture<File> getHdMap(MapInfo filename) {
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getHDMap");
      logger.finer("filename = " + filename.filename());

      //query parameter
      Map<String, String> query = new HashMap<>();
      query.put("filename", filename.filename());

      //build request
      HttpRequest request = buildGetRequest(EndPoint.HDMAP, query);

      //send request
      File file = sendFileRequest(request, "hdmap", "bin");
      return file;
    }).exceptionallyAsync((Throwable error) -> {
      logger.warning(error.getMessage());
      return null;
    });
  }

  /**
   * requests detected objects in a specific time window.
   *
   * @param from start date and time
   * @param to end date and time
   * @return list of detected objects
   */
  public CompletableFuture<MeasureList<DetectionData>> getDetectionData(
      LocalDateTime from, LocalDateTime to) {
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getDetectionData");
      logger.finer("from = " + from + ", to = " + to);

      //query parameter
      Map<String, String> query = new HashMap<>();
      query.put("start_tmpstmp", from.toString());
      query.put("end_tmpstmp", to.toString());

      //build request
      HttpRequest request = buildGetRequest(EndPoint.DETECTION, query);

      //send request
      Map<String, List<List<?>>> body = sendDataRequest(request);
  
      //parse to list of object detection data-objects
      logger.fine(
          "parse response.body() of type Map<String, List<List<?>>> to MeasureList<DetectionData>");
      MeasureList<DetectionData> detectionDataList = Parser.toDetectionList(body, this, logger);
      logger.finest("parsed response.body() = " + detectionDataList.toString());

      return detectionDataList;
    }).exceptionallyAsync((Throwable error) -> {
      logger.warning(error.getMessage());
      return MeasureList.createDetectionList(this, logger);
    });
  }

  /**
   * requests all weather data in a specific time window.
   *
   * @param from start date and time 
   * @param to end date and time
   * @return list of weather data
   */
  public CompletableFuture<MeasureList<WeatherData>> getWeatherData(
      LocalDateTime from, LocalDateTime to) {
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getWeatherData");
      logger.finer("from = " + from + ", to = " + to);

      //query parameter
      Map<String, String> query = new HashMap<>();
      query.put("start_tmpstmp", from.toString());
      query.put("end_tmpstmp", to.toString());

      //build request
      HttpRequest request = buildGetRequest(EndPoint.WEATHER, query);

      //send request
      Map<String, List<List<?>>> body = sendDataRequest(request);

      //parse to list of weatherData-objects
      logger.fine(
          "parse response.body() of type Map<String, List<List<?>>> to MeasureList<WeatherData>");
      MeasureList<WeatherData> weatherDataList = Parser.toWeatherList(body, this, logger);
      logger.finest("parsed response.body() = " + weatherDataList);
      
      return weatherDataList;
    }).exceptionallyAsync((Throwable error) -> {
      logger.warning(error.getMessage());
      return MeasureList.createWeatherList(this, logger);
    });
  }

  /**
   * requests all park data in a specific time window.
   *
   * @param from start date and time
   * @param to end date and time
   * @return list of parking data
   */
  public CompletableFuture<MeasureList<ParkingData>> getParkingData(
      LocalDateTime from, LocalDateTime to) {
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getParkingData");
      logger.finer("from = " + from + ", to = " + to);

      //query parameter
      Map<String, String> query = new HashMap<>();
      query.put("start_tmpstmp", from.toString());
      query.put("end_tmpstmp", to.toString());

      //build request
      HttpRequest request = buildGetRequest(EndPoint.PARKING, query);

      //send request
      Map<String, List<List<?>>> body = sendDataRequest(request);

      //parse to list of parkingData-objects
      logger.fine(
          "parse response.body() of type Map<String, List<List<?>>> to MeasureList<ParkingData>");
      MeasureList<ParkingData> parkingDataList = Parser.toParkingList(body, this, logger);
      logger.finest("parsed response.body() = " + parkingDataList);

      return parkingDataList;
    }).exceptionallyAsync((Throwable error) -> {

      logger.warning(error.getMessage());
      return MeasureList.createParkingList(this, logger);
    });
  }

  /**
   * requests all road data in a specific time window.
   *
   * @param from start date and time
   * @param to end date and time
   * @return list of road data
   */
  public CompletableFuture<MeasureList<RoadData>> getRoadData(
      LocalDateTime from, LocalDateTime to) {
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getRoadData");
      logger.finer("from = " + from + ", to = " + to);

      //query parameter
      Map<String, String> query = new HashMap<>();
      query.put("start_tmpstmp", from.toString());
      query.put("end_tmpstmp", to.toString());

      //build request
      HttpRequest request = buildGetRequest(EndPoint.ROAD, query);

      //send request
      Map<String, List<List<?>>> body = sendDataRequest(request);

      //parse to list of roadData-objects
      logger.fine(
          "parse response.body() of type Map<String, List<List<?>>> to MeasureList<RoadData>");
      MeasureList<RoadData> roadDataList = Parser.toRoadList(body, this, logger);
      logger.finest("parsed response.body() = " + roadDataList);
            
      return roadDataList;
    }).exceptionallyAsync((Throwable error) -> {
      logger.warning(error.getMessage());
      return MeasureList.createRoadList(this, logger);
    });
  }

  /**
   * requests ParkinglotAvailability-data for a specific parkinglot.
   *
   * @param gapId of specific parkinglot
   * @return list of parkinglot availability
   */
  public CompletableFuture<List<ParkinglotAvailability>> getParkinglotAvailability(String gapId) {
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getParkinglotAvailability");
      logger.finer("gapId = " + gapId);

      //query parameter
      Map<String, String> query = new HashMap<>();
      query.put("gap_id", gapId);

      //build request
      HttpRequest request = buildGetRequest(EndPoint.PARKINGLOT_AVAILABILITY, query);

      //send request
      Map<String, List<List<?>>> body = sendDataRequest(request);

      //parse to list of roadData-objects
      logger.fine(
          "parse response.body() of type Map<String, List<List<?>>> to List<ParkinglotAvailability>"
      );
      List<ParkinglotAvailability> parkinglotAvailabilityList = 
          Parser.toParkinglotAvailabilityList(body);

      logger.finest("parsed response.body() = " + parkinglotAvailabilityList);

      return parkinglotAvailabilityList;
    }).exceptionallyAsync((Throwable error) -> {
      logger.warning(error.getMessage());
      return new ArrayList<ParkinglotAvailability>();
    });
  }

  /**
   * requests ParkinglotInfo-data for a specific parkinglot.
   *
   * @param gapId of specific parkinglot
   * @return list of parkinglot availability
   */
  public CompletableFuture<List<ParkinglotInfo>> getParkinglotInfo(String gapId) {
    return CompletableFuture.supplyAsync(() -> {
      logger.fine("entering getParkinglotInfo");
      logger.finer("gapId = " + gapId);

      //query parameter
      Map<String, String> query = new HashMap<>();
      query.put("gap_id", gapId);

      //build request
      HttpRequest request = buildGetRequest(EndPoint.PARKINGLOT_INFO, query);

      //send request
      Map<String, List<List<?>>> body = sendDataRequest(request);
        
      //parse to list of roadData-objects
      logger.fine(
          "parse response.body() of type Map<String, List<List<?>>> to List<ParkinglotInfo>");
      List<ParkinglotInfo> parkinglotInfo = Parser.toParkinglotInfoList(body);
      logger.finest("parsed response.body() = " + parkinglotInfo);

      return parkinglotInfo;
    }).exceptionallyAsync((Throwable error) -> {
      logger.warning(error.getMessage());
      return new ArrayList<ParkinglotInfo>();
    });
  }
}

