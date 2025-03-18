package beintelliPlatformSdk.javaSdk.utils.attributes;

import beintelliPlatformSdk.javaSdk.lists.MeasureList;
import beintelliPlatformSdk.javaSdk.utils.MeasurementType;

/**
 * Contains attributes of type Number (Integer, Double, Long, Float) of the Measurment data type
 * group (DetectionData, WeatherData, RoadData, ParkingData).
 * Used for the filter function in a range in MeasurementList.
 * The associated data type is explicitly specified to make the filtering process
 * easier.
 *
 * @see MeasureList#filtered(NumberAttribute, Number, Number) 
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 */
public enum NumberAttribute {
    
    //WeatherData-attributes of type Number

    /**
     * Attribute of data type WeatherData.
     */
    WEATHER_ID(Attribute.WEATHER_ID),

    /**
     * Attribute of data type WeatherData.
     */
    TEMPC(Attribute.TEMPC),

    /**
     * Attribute of data type WeatherData.
     */
    HUMIDITY(Attribute.HUMIDITY),
    
    /**
     * Attribute of data type WeatherData.
     */
    ABSOLUTE_HUMIDITY(Attribute.ABSOLUTE_HUMIDITY),
    
    /**
     * Attribute of data type WeatherData.
     */
    AIR_PRESSURE(Attribute.AIR_PRESSURE),
    
    /**
     * Attribute of data type WeatherData.
     */
    ABSOLUTE_PRECIPITATION(Attribute.ABSOLUTE_PRECIPITATION),
    
    /**
     * Attribute of data type WeatherData.
     */
    ABSOLUTE_PRECIPITATION_MM(Attribute.ABSOLUTE_PRECIPITATION_MM),
    
    /**
     * Attribute of data type WeatherData.
     */
    DIFFERENTIAL_PRECIPITATION(Attribute.DIFFERENTIAL_PRECIPITATION),
    
    /**
     * Attribute of data type WeatherData.
     */
    DIFFERENTIAL_PRECIPITATION_MM(Attribute.DIFFERENTIAL_PRECIPITATION_MM),
    
    /**
     * Attribute of data type WeatherData.
     */
    PRECIPITATION_INTENSITY(Attribute.PRECIPITATION_INTENSITY),
    
    /**
     * Attribute of data type WeatherData.
     */
    PRECIPITATION_TYPE(Attribute.PRECIPITATION_TYPE),
    
    //parkingData-attributes of type Number
    /**
     * Attribute of data type ParkingData.
     */
    PARKING_ID(Attribute.PARKING_ID),

    //RoadData-attributes of type Number
    /**
     * Attribute of data type RoadData.
     */
    ROAD_ID(Attribute.ROAD_ID),

    /**
     * Attribute of data type RoadData.
     */
    ROAD_SURFACE_TEMPERATURE_C(Attribute.ROAD_SURFACE_TEMPERATURE_C),
    
    /**
     * Attribute of data type RoadData.
     */
    FREEZING_TEMPERATURE_NAC(Attribute.FREEZING_TEMPERATURE_NAC),
    
    /**
     * Attribute of data type RoadData.
     */
    WATER_FILM_HEIGHT(Attribute.WATER_FILM_HEIGHT),
    
    /**
     * Attribute of data type RoadData.
     */
    ICE_LAYER_THICKNESS(Attribute.ICE_LAYER_THICKNESS),
    
    /**
     * Attribute of data type RoadData.
     */
    SNOW_HEIGHT(Attribute.SNOW_HEIGHT),
    
    /**
     * Attribute of data type RoadData.
     */
    ICE_PERCENTAGE(Attribute.ICE_PERCENTAGE),
    
    /**
     * Attribute of data type RoadData.
     */
    SALINE_CONCENTRATION(Attribute.SALINE_CONCENTRATION),
    
    /**
     * Attribute of data type RoadData.
     */
    FRICTION(Attribute.FRICTION),
    
    /**
     * Attribute of data type RoadData.
     */
    ROAD_CONDITION_TYPE(Attribute.ROAD_CONDITION_TYPE),
    
    /**
     * Attribute of data type RoadData.
     */
    MEASUREMENT_STATUS_BITS(Attribute.MEASUREMENT_STATUS_BITS);

  private MeasurementType type;

  /**
   * constructor.
   *
   * @param attr of Measurement type
   */
  private NumberAttribute(Attribute attr) {
    this.type = attr.getMeasurementType();
  }

  /**
   * return the Measurement data type that has the Attribute object as attribute.
   *
   * @return data type 
   */
  public MeasurementType getMeasurementType() {
    return this.type;
  }


}