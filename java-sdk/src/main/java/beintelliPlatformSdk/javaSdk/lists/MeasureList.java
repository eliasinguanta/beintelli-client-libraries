package beintelliPlatformSdk.javaSdk.lists;

import beintelliPlatformSdk.javaSdk.apis.DataApi;
import beintelliPlatformSdk.javaSdk.utils.Access;
import beintelliPlatformSdk.javaSdk.utils.DetectionData;
import beintelliPlatformSdk.javaSdk.utils.Measurement;
import beintelliPlatformSdk.javaSdk.utils.MeasurementType;
import beintelliPlatformSdk.javaSdk.utils.ParkingData;
import beintelliPlatformSdk.javaSdk.utils.RoadData;
import beintelliPlatformSdk.javaSdk.utils.WeatherData;
import beintelliPlatformSdk.javaSdk.utils.attributes.Attribute;
import beintelliPlatformSdk.javaSdk.utils.attributes.NumberAttribute;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * List type that also has a filter function. Can only be used for detection, weather, road and
 * parking data. All elements of the measurement list must be of the same type. This is how it is
 * handled internally.
 *
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 * @param <T> can be WeatherData, DetectionData, ParkingData, RoadData
 */
public abstract class MeasureList<T extends Measurement> extends ArrayList<T> {

  /**
   * Needs to request data for filter function.
   */
  protected final DataApi api;

  /**
   * Needed for logging.
   */
  protected final Logger logger;

  /**
   * Constructor needs Data api and logger. Both can be given directly.
   *
   * @param api needed to request data for filtering
   * @param logger for logging
   */
  protected MeasureList(DataApi api, Logger logger) {
    this.api = api;
    this.logger = logger;
  }

  /**
   * Constructor needs Data api and logger. New Data Api can be created with access object.
   *
   * @param access reference for authorisation String
   * @param logger for logging
   */
  protected MeasureList(Access access, Logger logger) {
    this.api = new DataApi(access, logger);
    this.logger = logger;
  }

  /**
   * Simple factory method. We need to be able to create an empty list in the filter functions. To
   * do this, we define an empty function that is overwritten by the child classes. The filter
   * function of the abstract class will call the factory functions of the child class when it is
   * itself called by the child class.
   *
   * @return empty MeasureList
   */
  protected MeasureList<? extends Measurement> getEmptyList() {
    return null;
  }

  /**
   * Empty function that must be implemented by the child classes. Returns the latest timestamp of
   * the list items.
   *
   * @return latest timestamp
   */
  protected LocalDateTime getMaxTime() {
    return null;
  }

  /**
   * Empty function that must be implemented by the child classes. Returns the earliest timestamp of
   * the list items.
   *
   * @return earliest timestamp
   */
  protected LocalDateTime getMinTime() {
    return null;
  }

  /**
   * Filters measurements that were measured at the same time as measurements whose attributes have
   * the specific value. The idea is that, for example, you can filter for the condition of the
   * roads at minus temperatures.
   *
   * @param attribute of the data type Weather-, Detection-, Road-, ParkingData
   * @param value of the attribute that we filter for
   * @return the filtered list
   */
  public CompletableFuture<MeasureList<T>> filtered(Attribute attribute, Object value) {
    return null;
  }

  /**
   * Filters measurements that were measured at the same time as measurements whose attributes have
   * a value in a specified interval. The idea is that, for example, you can filter for the
   * condition of the roads at minus temperatures.
   *
   * @param attribute of the data type Weather-, Detection-, Road-, ParkingData
   * @param from lower end of the interval
   * @param to upper end of the interval
   * @return the filtered list
   */
  public CompletableFuture<MeasureList<T>> filtered(
      NumberAttribute attribute, Number from, Number to) {
    return null;
  }

  /**
   * Filter for the timestamps of measurements whose attributes have
   * the specific value.
   *
   * @param attribute of the data type Weather-, Detection-, Road-, ParkingData
   * @param value of the attribute that we filter for
   * @return list of times
   */
  protected CompletableFuture<List<LocalDateTime>> getfilteredTimes(
      Attribute attribute, Object value) {
    logger.fine("filter times of matching Measurements");

    LocalDateTime max = getMaxTime();
    LocalDateTime min = getMinTime();

    CompletableFuture<MeasureList<WeatherData>> weatherFuture;
    CompletableFuture<MeasureList<ParkingData>> parkingFuture;
    CompletableFuture<MeasureList<RoadData>> roadFuture;
    CompletableFuture<MeasureList<DetectionData>> detectionFuture;

    if (min == null || max == null) {
      return CompletableFuture.supplyAsync(() -> {
        return new ArrayList<LocalDateTime>();
      });
    }


    switch (attribute.getMeasurementType()) {
      case MeasurementType.WEATHER:
        logger.finer("calling getWeatherData(" + min + ", " + max + ")");
        weatherFuture = api.getWeatherData(min, max);
        return weatherFuture.thenApplyAsync((MeasureList<WeatherData> list) -> {
          MeasureList<WeatherData> filterData = new WeatherList(api, logger);
          logger.finer("filter for " + attribute + " = " + value);
          switch (attribute) {
            case Attribute.WEATHER_ID:
              for (WeatherData data : list) {
                if (data.id() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.TEMPC:
              for (WeatherData data : list) {
                if (data.tempc() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.HUMIDITY:
              for (WeatherData data : list) {
                if (data.humidity() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.ABSOLUTE_HUMIDITY:
              for (WeatherData data : list) {
                if (data.absoluteHumidity() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.AIR_PRESSURE:
              for (WeatherData data : list) {
                if (data.airPressure() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.ABSOLUTE_PRECIPITATION:
              for (WeatherData data : list) {
                if (data.absolutePrecipitation() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.ABSOLUTE_PRECIPITATION_MM:
              for (WeatherData data : list) {
                if (data.absolutePrecipitationmm() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.DIFFERENTIAL_PRECIPITATION:
              for (WeatherData data : list) {
                if (data.differentialPrecipitation() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.DIFFERENTIAL_PRECIPITATION_MM:
              for (WeatherData data : list) {
                if (data.differentialPrecipitationmm() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.PRECIPITATION_INTENSITY:
              for (WeatherData data : list) {
                if (data.precipitationIntensity() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.PRECIPITATION_TYPE:
              for (WeatherData data : list) {
                if (data.precipitationType() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.PRECIPITATION:
              for (WeatherData data : list) {
                if (data.precipitation() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.WEATHER_TOPIC:
              for (WeatherData data : list) {
                if (data.topic() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            default:
              //no default
              break;
          }
          List<LocalDateTime> result = new ArrayList<>();
          for (WeatherData data : filterData) {
            result.add(data.tmpstmpForAdding());
          }
          logger.finest("filtered times = " + result);
          return result;
        });
            
      case MeasurementType.PARKING:
        logger.finer("calling getParkingData(" + min + ", " + max + ")");
        parkingFuture = api.getParkingData(min, max);
        return parkingFuture.thenApplyAsync((MeasureList<ParkingData> list) -> {
          MeasureList<ParkingData> filterData = new ParkingList(api, logger);

          logger.finer("filter for " + attribute + " = " + value);
          switch (attribute) {
            case Attribute.PARKING_ID:
              for (ParkingData data : list) {
                if (data.id() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.CUSTOM_STATE:
              for (ParkingData data : list) {
                if (data.customState() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.GAP_IDS:
              for  (ParkingData data : list) {
                if  (data.gapIds() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.NAME:
              for  (ParkingData data : list) {
                if  (data.name() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.RAW_DATA_ARRIVAL:
              for (ParkingData data : list) {
                if  (data.rawDataArrival() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.RAW_DATA_CAR:
              for  (ParkingData data  : list) {
                if  (data.rawDataCar() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.RAW_DATA_DEPATURE:
              for (ParkingData data : list) {
                if (data.rawDataDepature() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.OCCUPIED:
              for (ParkingData data : list) {
                if (data.occupied() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.RAW_REF_DATA:
              for (ParkingData data : list) {
                if (data.rawRefData() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.REF1:
              for (ParkingData data : list) {
                if (data.ref1() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.REF2:
              for (ParkingData data : list) {
                if (data.ref2() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.VALID:
              for (ParkingData data : list) {
                if (data.valid() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            default:
              break;
          }
          List<LocalDateTime> result = new ArrayList<>();
          for (ParkingData data : filterData) {
            result.add(data.tmpstmpForAdding());
          }
          logger.finest("filtered times = " + result);
          return result;
        });
      case MeasurementType.ROAD:

        logger.finer("calling getRoadData(" + min + ", " + max + ")");
        roadFuture = api.getRoadData(min, max);
        return roadFuture.thenApplyAsync((MeasureList<RoadData> list) -> {
          MeasureList<RoadData> filterData = new RoadList(api, logger);

          logger.finer("filter for " + attribute + " = " + value);
          switch (attribute) {
            case Attribute.ROAD_ID:
              for (RoadData data : list) {
                if (data.id() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.ROAD_SURFACE_TEMPERATURE_C:
              for (RoadData data : list) {
                if (data.roadSurfaceTemperatureC() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.FREEZING_TEMPERATURE_NAC:
              for (RoadData data : list) {
                if (data.freezingTemperatureNac() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.WATER_FILM_HEIGHT:
              for (RoadData data : list) {
                if (data.waterFilmHeight() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.ICE_LAYER_THICKNESS:
              for (RoadData data : list) {
                if (data.iceLayerThickness() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.SNOW_HEIGHT:
              for (RoadData data : list) {
                if (data.snowHeight() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.ICE_PERCENTAGE:
              for (RoadData data : list) {
                if (data.icePercentage() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.SALINE_CONCENTRATION:
              for (RoadData data : list) {
                if (data.salineConcentration() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.FRICTION:
              for (RoadData data : list) {
                if (data.friction() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.ROAD_CONDITION_TYPE:
              for (RoadData data : list) {
                if (data.roadConditionType() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.MEASUREMENT_STATUS_BITS:
              for (RoadData data : list) {
                if (data.measurementStatusBits() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.ROAD_CONDITION:
              for (RoadData data : list) {
                if (data.roadCondition() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.ROAD_TOPIC:
              for (RoadData data : list) {
                if (data.topic() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            default:
              break;
          }

          List<LocalDateTime> result = new ArrayList<>();
          for (RoadData data : filterData) {
            result.add(data.tmpstmpForAdding());
          }
          logger.finest("filtered times = " + result);
          return result;
        });
      case MeasurementType.DETECTION:
        logger.finer("calling getDetectionData(" + min + ", " + max + ")");
        detectionFuture = api.getDetectionData(min, max);
        return detectionFuture.thenApplyAsync((MeasureList<DetectionData> list) -> {
          MeasureList<DetectionData> filterData = new DetectionList(api, logger);
          logger.finer("filter for " + attribute + " = " + value);
          switch (attribute) {
            case Attribute.DETECTION_ID:
              for (DetectionData data : list) {
                if (data.id() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.TIMESTAMP:
              for (DetectionData data : list) {
                if (data.timestamp() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.KAFKA_TOPIC:
              for (DetectionData data : list) {
                if (data.kafkaTopic() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.TYPE:
              for (DetectionData data : list) {
                if (data.type() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.LON:
              for (DetectionData data : list) {
                if (data.lon() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.LAT:
              for (DetectionData data : list) {
                if (data.lat() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.TRACK_ID:
              for (DetectionData data : list) {
                if (data.trackid() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.HEADING:
              for (DetectionData data : list) {
                if (data.heading() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case Attribute.SPEED:
              for (DetectionData data : list) {
                if (data.speed() == value) {
                  logger.finest(data + " meets criteria ");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;

            default:
              break;
          }
          
          List<LocalDateTime> result = new ArrayList<>();
          for (DetectionData data : filterData) {
            result.add(data.tmpstmpForAdding());
          }
          logger.finest("filtered times = " + result);
          return result;
        });

      case MeasurementType.ANY:
        logger.finer("calling getWeatherData(" + min + ", " + max + ")");
        weatherFuture = api.getWeatherData(min, max);
        logger.finer("calling getParkingData(" + min + ", " + max + ")");
        parkingFuture = api.getParkingData(min, max);
        logger.finer("calling getRoadData(" + min + ", " + max + ")");
        roadFuture = api.getRoadData(min, max);
        logger.finer("calling getDetectionData(" + min + ", " + max + ")");
        detectionFuture = api.getDetectionData(min, max);

        CompletableFuture<List<LocalDateTime>> timeFuture = 
            CompletableFuture.allOf(weatherFuture, parkingFuture, roadFuture, detectionFuture)
                .thenApplyAsync((Void) -> {
                  LocalDateTime result = null;
                  MeasureList<WeatherData> weatherList = weatherFuture.resultNow();
                  MeasureList<ParkingData> parkingList = parkingFuture.resultNow();
                  MeasureList<RoadData> roadList = roadFuture.resultNow();
                  MeasureList<DetectionData> detectionList = detectionFuture.resultNow();

                  logger.finer("filter for " + attribute + " = " + value);
                  for (WeatherData data : weatherList) {
                    if (data.tmpstmpForAdding() == value) {
                      logger.finest(data + " meets criteria ");
                      result = data.tmpstmpForAdding();
                    } else {
                      logger.finest(data + " does not meet criteria");
                    }
                  }
                  for (ParkingData data : parkingList) {
                    if (data.tmpstmpForAdding() == value) {
                      logger.finest(data + " meets criteria ");
                      result = data.tmpstmpForAdding();
                    } else {
                      logger.finest(data + " does not meet criteria");
                    }
                  }
                  for (RoadData data : roadList) {
                    if (data.tmpstmpForAdding() == value) {
                      logger.finest(data + " meets criteria ");
                      result = data.tmpstmpForAdding();
                    } else {
                      logger.finest(data + " does not meet criteria");
                    }
                  }
                  for (DetectionData data : detectionList) {
                    if (data.tmpstmpForAdding() == value) {
                      logger.finest(data + " meets criteria ");
                      result = data.tmpstmpForAdding();
                    } else {
                      logger.finest(data + " does not meet criteria");
                    }
                  }
                  List<LocalDateTime> resultList = new ArrayList<>();
                  if (result != null) {
                    resultList.add(result);
                  }
                  logger.finest("filtered times = " + resultList);
                  return resultList;
                });
        return timeFuture;
      default:
    }

    return CompletableFuture.supplyAsync(() -> {
      return new ArrayList<>();
    });
  }

  /**
   * Filter the timestamps where measurements exist that have correct (given) value for the right
   * (given) attribute.
   *
   * @param attribute of type Number
   * @param from interval-start
   * @param to interval-end
   * @return list of times 
   */
  protected CompletableFuture<List<LocalDateTime>> getfilteredTimes(
      NumberAttribute attribute, Number from, Number to) {
    logger.fine("filter times of matching Measurements");
    LocalDateTime max = getMaxTime();
    LocalDateTime min = getMinTime();
    
    CompletableFuture<MeasureList<WeatherData>> weatherFuture;
    CompletableFuture<MeasureList<ParkingData>> parkingFuture;
    CompletableFuture<MeasureList<RoadData>> roadFuture;
            
    if (min == null || max == null) {
      return CompletableFuture.supplyAsync(() -> {
        return new ArrayList<LocalDateTime>();
      });
    }

    switch (attribute.getMeasurementType()) {
      case MeasurementType.WEATHER:
        logger.finer("calling getWeatherData(" + min + ", " + max + ")");
        weatherFuture = api.getWeatherData(max, min);
        return weatherFuture.thenApplyAsync((MeasureList<WeatherData> list) -> {
          MeasureList<WeatherData> filterData = new WeatherList(api, logger);
          logger.finer("filter for " + attribute + " from " + from + " to " + to);
          switch (attribute) {
            case NumberAttribute.WEATHER_ID:
              for (WeatherData data : list) {
                if (data.id() > from.intValue() && data.id() < to.intValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.TEMPC:
              for (WeatherData data : list) {
                if (data.tempc() > from.doubleValue() && data.id() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.HUMIDITY:
              for (WeatherData data : list) {
                if (data.humidity() > from.doubleValue() && data.humidity() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.ABSOLUTE_HUMIDITY:
              for (WeatherData data : list) {
                if (data.absoluteHumidity() > from.doubleValue()
                     && data.absoluteHumidity() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.AIR_PRESSURE:
              for (WeatherData data : list) {
                if (data.airPressure() > from.doubleValue() 
                    && data.airPressure() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.ABSOLUTE_PRECIPITATION:
              for (WeatherData data : list) {
                if (data.absolutePrecipitation() > from.doubleValue() 
                    && data.absolutePrecipitation() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.ABSOLUTE_PRECIPITATION_MM:
              for (WeatherData data : list) {
                if (data.absolutePrecipitationmm() > from.doubleValue() 
                    && data.absolutePrecipitationmm() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.DIFFERENTIAL_PRECIPITATION:
              for (WeatherData data : list) {
                if (data.differentialPrecipitation() > from.doubleValue() 
                    && data.differentialPrecipitation() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.DIFFERENTIAL_PRECIPITATION_MM:
              for (WeatherData data : list) {
                if (data.differentialPrecipitationmm() > from.doubleValue() 
                    && data.differentialPrecipitationmm() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.PRECIPITATION_INTENSITY:
              for (WeatherData data : list) {
                if (data.precipitationIntensity() > from.doubleValue() 
                    && data.precipitationIntensity() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.PRECIPITATION_TYPE:
              for (WeatherData data : list) {
                if (data.precipitationType() > from.doubleValue()
                    && data.precipitationType() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;   
            default:
              //no default
              break;
          }
          List<LocalDateTime> result = new ArrayList<>();
          for (WeatherData data : filterData) {
            result.add(data.tmpstmpForAdding());
          }
          logger.finest("filtered times = " + result);
          return result;
        });

      case MeasurementType.PARKING:
        logger.finer("calling getParkingData(" + min + ", " + max + ")");
        parkingFuture = api.getParkingData(max, min);
        return parkingFuture.thenApplyAsync((MeasureList<ParkingData> list) -> {
          MeasureList<ParkingData> filterData = new ParkingList(api, logger);
            
          logger.finer("filter for " + attribute + " from " + from + " to " + to);
          switch (attribute) {
            case NumberAttribute.PARKING_ID:
              for (ParkingData data : list) {
                if (data.id() > from.intValue() && data.id() < to.intValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            default:
              break;
          }
          List<LocalDateTime> result = new ArrayList<>();
          for (ParkingData data : filterData) {
            result.add(data.tmpstmpForAdding());
          }
          logger.finest("filtered times = " + result);
          return result;
        });
      case MeasurementType.ROAD:
        logger.finer("calling getRoadData(" + min + ", " + max + ")");
        roadFuture = api.getRoadData(max, min);
        return roadFuture.thenApplyAsync((MeasureList<RoadData> list) -> {
          MeasureList<RoadData> filterData = new RoadList(api, logger);

          logger.finer("filter for " + attribute + " from " + from + " to " + to);
          switch (attribute) {
            case NumberAttribute.ROAD_ID:
              for (RoadData data : list) {
                if (data.id() > from.intValue() && data.id() < to.intValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.ROAD_SURFACE_TEMPERATURE_C:
              for (RoadData data : list) {
                if (data.roadSurfaceTemperatureC() > from.doubleValue() 
                    && data.roadSurfaceTemperatureC() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.FREEZING_TEMPERATURE_NAC:
              for (RoadData data : list) {
                if (data.freezingTemperatureNac() > from.doubleValue() 
                    && data.freezingTemperatureNac() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.WATER_FILM_HEIGHT:
              for (RoadData data : list) {
                if (data.waterFilmHeight() > from.doubleValue() 
                    && data.waterFilmHeight() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.ICE_LAYER_THICKNESS:
              for (RoadData data : list) {
                if (data.iceLayerThickness() > from.doubleValue() 
                    && data.iceLayerThickness() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.SNOW_HEIGHT:
              for (RoadData data : list) {
                if (data.snowHeight() > from.doubleValue() 
                    && data.snowHeight() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.ICE_PERCENTAGE:
              for (RoadData data : list) {
                if (data.icePercentage() > from.doubleValue() 
                    && data.icePercentage() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.SALINE_CONCENTRATION:
              for (RoadData data : list) {
                if (data.salineConcentration() > from.doubleValue() 
                    && data.salineConcentration() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.FRICTION:
              for (RoadData data : list) {
                if (data.friction() > from.doubleValue() && data.friction() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.ROAD_CONDITION_TYPE:
              for (RoadData data : list) {
                if (data.roadConditionType() > from.doubleValue() 
                    && data.roadConditionType() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            case NumberAttribute.MEASUREMENT_STATUS_BITS:
              for (RoadData data : list) {
                if (data.measurementStatusBits() > from.doubleValue() 
                      && data.measurementStatusBits() < to.doubleValue()) {
                  logger.finest(data + " meets criteria");
                  filterData.add(data);
                } else {
                  logger.finest(data + " does not meet criteria");
                }
              }
              break;
            default:
              break;
          }
              
          List<LocalDateTime> result = new ArrayList<>();
          for (RoadData data : filterData) {
            result.add(data.tmpstmpForAdding());
          }
          logger.finest("filtered times = " + result);
          return result;
        });
      default:

    }

    return CompletableFuture.supplyAsync(() -> {
      return new ArrayList<>();
    });
  }

  /**
   * Factory-method for MeasureList of DetectionData. Handled internally by subtype.
   *
   * @param api for filter function
   * @param logger for logging.
   * @return MeasureList of DetectionData
   */
  public static MeasureList<DetectionData> createDetectionList(DataApi api, Logger logger) {
    return new DetectionList(api, logger);
  }

  /**
   * Factory-method for MeasureList of WeatherData. Handled internally by subtype.
   *
   * @param api for filter function
   * @param logger for logging.
   * @return MeasureList of WeatherData
   */
  public static MeasureList<WeatherData> createWeatherList(DataApi api, Logger logger) {
    return new WeatherList(api, logger);
  }

  /**
   * Factory-method for MeasureList of ParkingData. Handled internally by subtype.
   *
   * @param api for filter function
   * @param logger for logging.
   * @return MeasureList of ParkingData
   */
  public static MeasureList<ParkingData> createParkingList(DataApi api, Logger logger) {
    return new ParkingList(api, logger);
  }

  /**
   * Factory-method for MeasureList of RoadData. Handled internally by subtype.
   *
   * @param api for filter function
   * @param logger for logging.
   * @return MeasureList of RoadData
   */
  public static MeasureList<RoadData> createRoadList(DataApi api, Logger logger) {
    return new RoadList(api, logger);
  }
}