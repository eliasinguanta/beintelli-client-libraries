package beintelliPlatformSdk.javaSdk.utils.attributes;

import beintelliPlatformSdk.javaSdk.lists.MeasureList;
import beintelliPlatformSdk.javaSdk.utils.MeasurementType;

/**
 * Contains each measurement attribute that the user can use to filter the MeasurementList.
 * The attribute can be of any type.
 * The measurement attribute contains the measurement type that is only needed intern in the filter.
 *
 * @see MeasureList#filtered(Attribute, Object)
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 */
public enum Attribute {
    //weather attributes

    /**
     * Attribute of data type WeatherData.
     */
    WEATHER_ID(MeasurementType.WEATHER),

    /**
     * Attribute of data type WeatherData.
     */
    TEMPC(MeasurementType.WEATHER),

    /**
     * Attribute of data type WeatherData.
     */
    HUMIDITY(MeasurementType.WEATHER),

    /**
     * Attribute of data type WeatherData.
     */
    ABSOLUTE_HUMIDITY(MeasurementType.WEATHER),

    /**
     * Attribute of data type WeatherData.
     */
    AIR_PRESSURE(MeasurementType.WEATHER),

    /**
     * Attribute of data type WeatherData.
     */
    ABSOLUTE_PRECIPITATION(MeasurementType.WEATHER),

    /**
     * Attribute of data type WeatherData.
     */
    ABSOLUTE_PRECIPITATION_MM(MeasurementType.WEATHER),

    /**
     * Attribute of data type WeatherData.
     */
    DIFFERENTIAL_PRECIPITATION(MeasurementType.WEATHER),

    /**
     * Attribute of data type WeatherData.
     */
    DIFFERENTIAL_PRECIPITATION_MM(MeasurementType.WEATHER),

    /**
     * Attribute of data type WeatherData.
     */
    PRECIPITATION_INTENSITY(MeasurementType.WEATHER),

    /**
     * Attribute of data type WeatherData.
     */
    PRECIPITATION_TYPE(MeasurementType.WEATHER),

    /**
     * Attribute of data type WeatherData.
     */
    PRECIPITATION(MeasurementType.WEATHER),

    /**
     * Attribute of data type WeatherData.
     */
    WEATHER_TOPIC(MeasurementType.WEATHER),

    //parking attributes
    /**
     * Attribute of data type ParkingData.
     */
    PARKING_ID(MeasurementType.PARKING),

    /**
     * Attribute of data type ParkingData.
     */
    CUSTOM_STATE(MeasurementType.PARKING),

    /**
     * Attribute of data type ParkingData.
     */
    GAP_IDS(MeasurementType.PARKING),

    /**
     * Attribute of data type ParkingData.
     */
    NAME(MeasurementType.PARKING),

    /**
     * Attribute of data type ParkingData.
     */
    RAW_DATA_ARRIVAL(MeasurementType.PARKING),

    /**
     * Attribute of data type ParkingData.
     */
    RAW_DATA_CAR(MeasurementType.PARKING),

    /**
     * Attribute of data type ParkingData.
     */
    RAW_DATA_DEPATURE(MeasurementType.PARKING),

    /**
     * Attribute of data type ParkingData.
     */
    OCCUPIED(MeasurementType.PARKING),

    /**
     * Attribute of data type ParkingData.
     */
    RAW_REF_DATA(MeasurementType.PARKING),

    /**
     * Attribute of data type ParkingData.
     */
    REF1(MeasurementType.PARKING),

    /**
     * Attribute of data type ParkingData.
     */
    REF2(MeasurementType.PARKING),

    /**
     * Attribute of data type ParkingData.
     */
    VALID(MeasurementType.PARKING),

    //road attributes
    /**
     * Attribute of data type RoadData.
     */
    ROAD_ID(MeasurementType.ROAD),

    /**
     * Attribute of data type RoadData.
     */
    ROAD_SURFACE_TEMPERATURE_C(MeasurementType.ROAD),
    
    /**
     * Attribute of data type RoadData.
     */
    FREEZING_TEMPERATURE_NAC(MeasurementType.ROAD),
    
    /**
     * Attribute of data type RoadData.
     */
    WATER_FILM_HEIGHT(MeasurementType.ROAD),
    
    /**
     * Attribute of data type RoadData.
     */
    ICE_LAYER_THICKNESS(MeasurementType.ROAD),
    
    /**
     * Attribute of data type RoadData.
     */
    SNOW_HEIGHT(MeasurementType.ROAD),
    
    /**
     * Attribute of data type RoadData.
     */
    ICE_PERCENTAGE(MeasurementType.ROAD),
    
    /**
     * Attribute of data type RoadData.
     */
    SALINE_CONCENTRATION(MeasurementType.ROAD),
    
    /**
     * Attribute of data type RoadData.
     */
    FRICTION(MeasurementType.ROAD),
    
    /**
     * Attribute of data type RoadData.
     */
    ROAD_CONDITION_TYPE(MeasurementType.ROAD),
    
    /**
     * Attribute of data type RoadData.
     */
    MEASUREMENT_STATUS_BITS(MeasurementType.ROAD),
    
    /**
     * Attribute of data type RoadData.
     */
    ROAD_CONDITION(MeasurementType.ROAD),
    
    /**
     * Attribute of data type RoadData.
     */
    ROAD_TOPIC(MeasurementType.ROAD),

    //detection attributes
    /**
     * Attribute of data type DetectionData.
     */
    DETECTION_ID(MeasurementType.DETECTION),

    /**
     * Attribute of data type DetectionData.
     */
    TIMESTAMP(MeasurementType.DETECTION),
    
    /**
     * Attribute of data type DetectionData.
     */
    KAFKA_TOPIC(MeasurementType.DETECTION),
    
    /**
     * Attribute of data type DetectionData.
     */
    TYPE(MeasurementType.DETECTION),
    
    /**
     * Attribute of data type DetectionData.
     */
    LON(MeasurementType.DETECTION),
    
    /**
     * Attribute of data type DetectionData.
     */
    LAT(MeasurementType.DETECTION),
    
    /**
     * Attribute of data type DetectionData.
     */
    TRACK_ID(MeasurementType.DETECTION),
    
    /**
     * Attribute of data type DetectionData.
     */
    HEADING(MeasurementType.DETECTION),
    
    /**
     * Attribute of data type DetectionData.
     */
    SPEED(MeasurementType.DETECTION),

    //attributes that every Measurement Type have
    /**
     * Attribute of data type Measurement so DetectionData, WeatherData, ParkingData, RoadData. 
     */
    TMPSTMP_FOR_ADDING(MeasurementType.ANY);

  /**
   * The type is necessary to determine what data is requested from the server.
   * We could also calculate the type, but specifying it explicitly is much easier
   */
  private final MeasurementType type;

  /**
   * Creates an Attribute object. Has the MeasurmentType intern saved for easier handeling.
   *
   * @param type of Attribute. Is this attribute from data type DetectionData? 
   */
  private Attribute(MeasurementType type) {
    this.type = type;
  }

  /**
   * Getter.
   *
   * @return data Type that has the attribute ... as attribute.
   */
  public MeasurementType getMeasurementType() {
    return type;
  }
}
