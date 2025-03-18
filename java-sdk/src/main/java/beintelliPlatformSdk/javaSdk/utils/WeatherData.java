package beintelliPlatformSdk.javaSdk.utils;

import beintelliPlatformSdk.javaSdk.apis.DataApi;
import java.time.LocalDateTime;

/**
 * Represents an weather data point.
 *
 * @see DataApi#getWeatherData(LocalDateTime, LocalDateTime)
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 * 
 * @param id of data point
 * @param tempc ?
 * @param humidity ?
 * @param absoluteHumidity ?
 * @param airPressure ?
 * @param absolutePrecipitation ?
 * @param absolutePrecipitationmm ?
 * @param differentialPrecipitation ?
 * @param differentialPrecipitationmm ?
 * @param precipitationIntensity ?
 * @param precipitationType ?
 * @param precipitation ?
 * @param topic ?
 * @param tmpstmpForAdding ?
 */
public record WeatherData(
    Integer id,
    Double tempc,
    Double humidity,
    Double absoluteHumidity,
    Double airPressure,
    Double absolutePrecipitation,
    Double absolutePrecipitationmm,
    Double differentialPrecipitation,
    Double differentialPrecipitationmm,
    Double precipitationIntensity,
    Double precipitationType,
    String precipitation,
    String topic,
    LocalDateTime tmpstmpForAdding
)implements Measurement{}
