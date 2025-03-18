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
import beintelliPlatformSdk.javaSdk.utils.RosbagInfo;
import beintelliPlatformSdk.javaSdk.utils.RouteData;
import beintelliPlatformSdk.javaSdk.utils.RsuData;
import beintelliPlatformSdk.javaSdk.utils.WeatherData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionException;
import java.util.logging.Logger;

/**
 * Since we almost always work with get requests of a similar form, we have a lot of reused code
 * that is abstracted away in this class, keeping the functions of the Data API and Service API
 * minimal and clear.
 *
 * @hidden
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 */
abstract class BaseApi {
  
  private final String baseUrl = "https://api.dev.be-intelli.com/rest";
  
  //reference to the access token
  //needed for authentication in every HTTP request
  private final Access access;

  /**
   * logger object, saves logger messages on level fine, finer, finest, warning and error.
   */
  protected final Logger logger;

  /**
   * Constructor needs the access reference to get the authorisation.
   * Constructor needs the logger .... for logging.
   *
   * @param access reference on access String
   * @param logger for logging
   */
  protected BaseApi(Access access, Logger logger) {
    this.access = access;
    this.logger = logger;
  }

  /**
  * Sends an http request that expects an http response with body of type
  * {@code LinkedHashMap<String, ArrayList<ArrayList<Object>>>}.
  *
  * @param request to send
  * @return response body
  * @throws CompletionException wrapping the actual exception like an IOException
  */
  protected Map<String, List<List<?>>> sendDataRequest(HttpRequest request){
    try {

      HttpClient client = HttpClient.newHttpClient();
      logger.fine("send request");

      //send http request
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
      logger.finer("statusCode = " + response.statusCode());
      logger.finest("response = " + response);

      //parse
      logger.fine("parse response.body() of type String to Map<String, List<List<?>>>");
      ObjectMapper objectMapper = new ObjectMapper();
      TypeReference<Map<String, List<List<?>>>> bodyType = new TypeReference<>(){};           
      Map<String, List<List<?>>> bodyObject = objectMapper.readValue(response.body(), bodyType);
      logger.finest("parsed response = " + bodyObject.toString());
      return bodyObject;

    } catch (IOException | InterruptedException e) {
      logger.warning(e.getMessage());
      throw new CompletionException(e);
    }
  }

  /**
   * Creates an http request with the method type GET and the url which consists of the base url,
   * the passed endpoint and the passed query parameters. Only the standard headers are added.
   *
   * @param endpoint of the url for the request
   * @param queryParams of the request
   * @return The http request
   */
  protected HttpRequest buildGetRequest(String endpoint, Map<String, String> queryParams) {
    logger.fine("build request");

    //build query
    String query = "";
    if (queryParams != null && !queryParams.isEmpty()) {
      for (String key : queryParams.keySet()) {
        query += "&" + key + "=" + queryParams.get(key);
      }
      query = "?" + query.substring(1);
    }
    logger.finer("query = " + query);

    //build uri
    Builder builder = HttpRequest.newBuilder();
    URI uri = URI.create(this.baseUrl + endpoint + query);
    logger.finer("uri = " + uri.toString());
    builder = builder.uri(uri);

    //add standart headers        
    Map<String, String> headers = new HashMap<String, String>();
    headers.put("Content-Type", "application/x-www-form-urlencoded");
    headers.put("Authorization", "Bearer " + this.access.getAccessToken());
    logger.finer("headers = " + headers);
    for (Map.Entry<String, String> entry : headers.entrySet()) {
      builder = builder.header(entry.getKey(), entry.getValue());
    }

    //build request
    logger.finer("method = GET");
    builder = builder.GET();
    HttpRequest request = builder.build();
    logger.finest("complete request = " + request);
    return request;
  }
    
  /**
   * This function wrapped buildGetRequest where the endpoint is a string. We have a fixed amount
   * of endpoints so it makes sense to use an enum instead of a string. Since the actual function
   * is still needed in a few cases, both are visible.
   *
   * @param endPoint of the url for the request
   * @param queryParams of the request
   * @return the http request
   */
  protected HttpRequest buildGetRequest(EndPoint endPoint, Map<String, String> queryParams) {
    return buildGetRequest(endPoint.getValue(), queryParams);
  }
    
  /**
   * Sends an http request that expects an http response with body of type byte[].
   *
   * @param request to send
   * @param filename of created file that get filled with requested file
   * @param filenameSuffix of created file
   * @return response body
   * @throws CompletionException the actual exception
   */
  protected File sendFileRequest(HttpRequest request, String filename, String filenameSuffix) {
    try {
      //send Request
      HttpClient client = HttpClient.newHttpClient();
      logger.finer("create temporary file = " + filename + "." + filenameSuffix);
      File result = File.createTempFile(filename, filenameSuffix);
      logger.fine("send request");
      HttpResponse<Path> response = client.send(request, BodyHandlers.ofFile(result.toPath()));
      logger.finer("statusCode = " + response.statusCode());
      logger.finest("response = " + response);

      return result;

    } catch (IOException | InterruptedException e) {
      logger.warning(e.getMessage());
      throw new CompletionException(e);
    }
  }

  /**
   * Sends an http request that expects an http response with body of type 
   * {@code Map<String, List<Map<?,?>>>}.
   *
   * @param request to send. Must request data of the correct format to avoid a parse exception
   * @return response body parsed as java object 
   * @throws CompletionException
   * 
   */
  protected Map<String, List<Map<?, ?>>> sendRosbagInfoRequest(HttpRequest request) {
    try {
      //send Request
      HttpClient client = HttpClient.newHttpClient();
      logger.fine("send request");
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
      logger.finer("statusCode = " + response.statusCode());
      logger.finest("response = " + response);

      //parse
      ObjectMapper objectMapper = new ObjectMapper();
      TypeReference<Map<String, List<Map<?, ?>>>> bodyType = new TypeReference<>(){};
      logger.fine("parse response.body() of type String to Map<String, List<Map<?,?>>>");
      Map<String, List<Map<?, ?>>> bodyObject = objectMapper.readValue(response.body(), bodyType);
      logger.finest("parsed response.body() = " + bodyObject.toString());

      return bodyObject;
    } catch (IOException | InterruptedException e) {
      logger.warning(e.getMessage());
      throw new CompletionException(e);
    }
  }
}

/**
  * Collection of various parse functions. Conceptually light, but large space consumption, which
  * made the functions of the Data API confusing. That is reason why we abstract the logic away in
  * this parser class.
  */
class Parser {
  /**
   * Unpack http response body to a list of weather data
   * parse from [[?, ..., ?], ..., [?, ..., ?]]   --->   [WeatherData, ..., WeatherData] 
   *
   * @param body of the http response parsed to java object
   * @return list of weather data
   * @throws ClassCastException if input does not have the expected form of a map with a of lists
    with 14 elements of correct types
   */
  public static MeasureList<WeatherData> toWeatherList(
      Map<String, List<List<?>>> body, DataApi api, Logger logger) throws ClassCastException {

    MeasureList<WeatherData> result = MeasureList.createWeatherList(api, logger);

    //fill result list
    List<List<?>> message = body.get("message");

    //check for NullPointerExeption
    if (message == null) {
      return result;
    }

    for (List<?> list : body.get("message")) {
      result.add(
        new WeatherData(
          (Integer) list.get(0),  (Double) list.get(1),   (Double) list.get(2),
          (Double) list.get(3),   (Double) list.get(4),   (Double) list.get(5),
          (Double) list.get(6),   (Double) list.get(7),   (Double) list.get(8),
          (Double) list.get(9),   (Double) list.get(10),  (String) list.get(11),
          (String) list.get(12),
          OffsetDateTime.parse((String) list.get(13)).toLocalDateTime()
        )
      );
    }
    return result;
  }

  /**
   * Unpack http response body to a list of object detection data
   * [[?, ..., ?], ..., [?, ..., ?]]   --->   [ObjectDetectionData, ..., ObjectDetectionData] 
   *
   * @param body of the http response
   * @return list of object detection data
   * @throws ClassCastException if an inner-list doesnt match the DetectionData-type
   */
  public static MeasureList<DetectionData>  toDetectionList(
      Map<String, List<List<?>>> body,  DataApi api, Logger logger) throws ClassCastException {
    MeasureList<DetectionData>  result = MeasureList.createDetectionList(api, logger);

    //fill result list
    List<List<?>> message = body.get("message");

    //check for NullPointerExeption
    if (message == null) {
      return result;
    }

    for (List<?> list : body.get("message")) {
      result.add(
        new DetectionData(
          list.get(0), list.get(1), list.get(2),
          list.get(3), list.get(4), list.get(5),
          list.get(6), list.get(7), list.get(8),
          OffsetDateTime.parse((String) list.get(9)).toLocalDateTime()
        )
      );
    }
    return result;
  }

  /**
   * Unpack http response body to a list of parking data
   * {message: [[?, ..., ?], ..., [?, ..., ?]]}   --->   [ParkingData, ..., ParkingData]
   *
   * @param body of the http response
   * @return list of parking data
   * @throws ClassCastException if inner-list doesnt match ParkingData-type
   */
  public static MeasureList<ParkingData> toParkingList(
       Map<String, List<List<?>>> body,  DataApi api, Logger logger) throws ClassCastException {
    MeasureList<ParkingData> result = MeasureList.createParkingList(api, logger);

    //fill result list
    List<List<?>> message = body.get("message");

    //check for NullPointerExeption
    if (message == null) {
      return result;
    }

    for (List<?> list : body.get("message")) {
      result.add(
        new ParkingData(
          (Integer) list.get(0),  (String) list.get(1),   (String) list.get(2),
          (String) list.get(3),   (String) list.get(4),   (String) list.get(5),
          (String) list.get(6),   (String) list.get(7),   (String) list.get(8),
          (String) list.get(9),   (String) list.get(10),  (String) list.get(11),
          OffsetDateTime.parse((String) list.get(12)).toLocalDateTime()
        )
      );
    }
    return result;
  }

  /**
   * Unpack http response body to a list of road data
   * {message: [[?, ..., ?], ..., [?, ..., ?]]}   --->   [RoadData, ..., RoadData] 
   *
   * @param body of the http response
   * @return list of road data
   * @throws ClassCastException if inner-list doesnt match RoadData-type
   */
  public static MeasureList<RoadData> toRoadList(
      Map<String, List<List<?>>> body,  DataApi api, Logger logger) throws ClassCastException {
        
    //result list 
    MeasureList<RoadData> result = MeasureList.createRoadList(api, logger);

    //fill result list
    List<List<?>> message = body.get("message");

    //check for NullPointerExeption
    if (message == null) {
      return result;
    }

    //fill result list
    for (List<?> list : body.get("message")) {
      result.add(
        new RoadData(
          (Integer) list.get(0),  (Double) list.get(1),   (Double) list.get(2),
          (Double) list.get(3),   (Double) list.get(4),   (Double) list.get(5),
          (Double) list.get(6),   (Double) list.get(7),   (Double) list.get(8),
          (Double) list.get(9),   (Double) list.get(10),  (String) list.get(11),
          (String) list.get(12),
          OffsetDateTime.parse((String) list.get(13)).toLocalDateTime()
        )
      );
    }
    return result;
  }

  /**
   * Unpack http response body to a list of rsu data
   * {message: [[?, ?], ..., [?, ?]]}   --->   [RsuData, ..., RsuData] 
   *
   * @param body of the http response
   * @return list of rsu data
   * @throws ClassCastException if inner-list doesnt match the RsuData-type
   */
  public static List<RsuData> toRsuList(Map<String, List<List<?>>> body) throws ClassCastException {
    //result list
    List<RsuData> result = new ArrayList<RsuData>();

    //fill result list
    List<List<?>> message = body.get("message");

    //check for NullPointerExeption
    if (message == null) {
      return result;
    }

    //fill result list
    for (List<?> list : body.get("message")) {
      result.add(new RsuData((String) list.get(0),  (String) list.get(1)));
    }
    return result;
  }

  /**
   * Unpack http response body to a list of filenames
   * {message: [[?], ..., [?]]}   --->   [MapInfo, ..., MapInfo] 
   *
   * @param body of the http response
   * @return list of filenames
   * @throws ClassCastException if inner-list doesnt match the MapInfo-type
   */
  public static List<MapInfo> toMapInfoList(
      Map<String, List<List<?>>> body) throws ClassCastException {

    //our result list
    List<MapInfo> result = new ArrayList<MapInfo>();

    //fill result list
    List<List<?>> message = body.get("message");

    //check for NullPointerExeption
    if (message == null) {
      return result;
    }

    //fill result list
    for (List<?> list : body.get("message")) {
      result.add(new MapInfo((String) list.get(0)));
    }
    return result;
  }

  /**
   * Unpack http response body to a list of Drive data objects
   * {message: [[?, ..., ?], ..., [?, ..., ?]]}   --->   [DriveData, ..., DriveData]
   *
   * @param body of the http response
   * @return list of Drive data
   * @throws ClassCastException if innerlist doesnt match the DriveData-type
   */
  public static List<DriveData> toDriveList(
      Map<String, List<List<?>>> body) throws ClassCastException {

    //our result list
    List<DriveData> result = new ArrayList<DriveData>();

    //fill result list
    List<List<?>> message = body.get("message");

    //check for NullPointerExeption
    if (message == null) {
      return result;
    }

    //fill result list
    for (List<?> list : body.get("message")) {
      result.add(
        new DriveData(
          (String) list.get(0),
          (String) list.get(1),
          (String) list.get(2),
          (String) list.get(3),
          (Double) list.get(4),
          (Double) list.get(5),
          (Double) list.get(6),
          (Double) list.get(7),
          (Double) list.get(8)
        )
      );
    }
    return result;
  }

  /**
   * unpack routa data and put them in a RoutaData object.
   *
   * @param body of the http reponse
   * @return a list of RouteData objects
   */
  public static List<RouteData> toRouteList(Map<String, List<List<?>>> body)
      throws ClassCastException {
    List<RouteData> result = new ArrayList<RouteData>();

    //fill result list
    List<List<?>> message = body.get("message");

    //check for NullPointerExeption
    if (message == null) {
      return result;
    }

    for (List<?> list : body.get("message")) {
      result.add(new RouteData(list));
    }
    return result;
  }

  /**
   * Unpack http response body to a list of Parkinglot availability data objects
   * {message: [[?, ..., ?], ..., [?, ..., ?]]}   --->   [ParkinglotAvailability, ...]
   *
   * @param body of the http response
   * @return list of Parkinglot availability data
   * @throws ClassCastException if inner-list doesnt match with the ParkinglotAvailability-type
   */
  public static List<ParkinglotAvailability> toParkinglotAvailabilityList(
      Map<String, List<List<?>>> body) throws ClassCastException {

    //result list
    List<ParkinglotAvailability> result = new ArrayList<ParkinglotAvailability>();

    //fill result list
    List<List<?>> message = body.get("message");

    //check for NullPointerExeption
    if (message == null) {
      return result;
    }

    //fill result list
    for (List<?> list : message) {
      result.add(
        new ParkinglotAvailability(
          (Integer) list.get(0),
          (String) list.get(1),
          (String) list.get(2),
          (String) list.get(3),
          (String) list.get(4),
          (String) list.get(5),
          (String) list.get(6),
          (String) list.get(7),
          (String) list.get(8),
          (String) list.get(9),
          (String) list.get(10),
          (String) list.get(11),
          (String) list.get(12)
        )
      );
    }
    return result;
  }

  /**
   * Unpack http response body to a list of Parkinglot info objects
   * {message: [[?, ..., ?], ..., [?, ..., ?]]}   --->   [ParkinglotInfo, ..., ParkinglotInfo] 
   *
   * @param body of the http response
   * @return list of Parkinglot info data
   * @throws ClassCastException if inner-list doesnt match with the ParkinglotInfo-type
   */
  public static List<ParkinglotInfo> toParkinglotInfoList(
      Map<String, List<List<?>>> body) throws ClassCastException {

    //result list 
    List<ParkinglotInfo> result = new ArrayList<ParkinglotInfo>();

    //fill result list
    List<List<?>> message = body.get("message");
        
    //check for NullPointerExeption
    if (message == null) {
      return result;
    }

    //parse
    for (List<?> list : message) {
      result.add(
        new ParkinglotInfo(
          (String) list.get(0),
          (String) list.get(1),
          (String) list.get(2),

          (Integer) list.get(3),

          (String) list.get(4),
          (String) list.get(5),
          (String) list.get(6),
          (String) list.get(7),

          (Double) list.get(8),
          (Double) list.get(9),
          (Double) list.get(10),
          (Double) list.get(11),
          (Double) list.get(12),

          (Double) list.get(13),
          (Double) list.get(14),
          (Double) list.get(15),
          (Double) list.get(16),
          (Double) list.get(16)
        )
      );
    }
    return result;
  }

  /**
   * Unpack http response body to a list of RosbagInfo  [{foldername,size}, ..., {foldername, size}]
   * {rosbags: [{foldername,size}, ..., {foldername, size}]}   --->   [RosbagInfo, ..., RosbagInfo] 
   *
   * @param body of the http response
   * @return list of RosbagInfo objects
   * @throws ClassCastException if input doesnt match expected format
   */
  public static List<RosbagInfo> toRosbagInfoList(Map<String, List<Map<?, ?>>> body)
      throws ClassCastException {

    //our result list
    List<RosbagInfo> filenames = new ArrayList<RosbagInfo>();

    //fill result list
    try {
      String folderName;
      Object sizeBytes;
      List<Map<?, ?>> message =  body.get("rosbags");

      if (message == null) {
        return new ArrayList<RosbagInfo>();
      }

      for (Map<?, ?> map : message) {
        folderName = (String) map.get("folder_name");
        sizeBytes = (Object) map.get("size_bytes");

        //parse to Long wether the object is of type Long or Integer
        if ((sizeBytes.getClass()) != (Long.class)) {
          sizeBytes = (Long) ((Integer) sizeBytes).longValue();
        }

        filenames.add(
          new RosbagInfo(
            folderName,
            (Long) sizeBytes
          )
        );
      }

    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<RosbagInfo>();   
    }
    return filenames;
  }
}

/**
 * Containg all endpoints. We reach a resource by sending a request to the 
 * url = baseURL + Endpoint.value
 */
enum EndPoint {

    HDMAPS("/list_of_hdmaps"),
    RSU("/rsu_data"),
    HDMAP("/hdmap"),
    DETECTION("/object_detection_data"),
    WEATHER("/weather_data"),
    PARKING("/parking_data"),
    ROAD("/road_data"),
    DRIVE("/vehicle_drives_data"),
    ROUTE("/vehicle_drive_route_data"),
    PARKINGLOT_AVAILABILITY("/parkinglot_availability"),
    PARKINGLOT_INFO("/parkinglot_information"),
    EXCELSHEET("/create_sheet"),
    CONVERTED_ROSBAG("/download_converted_rosbag/"),
    DOWNLOAD_CONVERTED_ROSBAGS("/rosbags_converted"),
    ROSBAGS("/rosbags"),
    DOWNLOAD_ROSBAG("/download_rosbag/");

  private final String value;

  private EndPoint(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}