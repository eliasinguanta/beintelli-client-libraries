package beintelliPlatformSdk.javaSdk.utils;

import beintelliPlatformSdk.javaSdk.apis.DataApi;
import java.time.LocalDateTime;

/**
 * Represents an road data point.
 *
 * @see DataApi#getRoadData(LocalDateTime, LocalDateTime)
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 * 
 * @param id of data point
 * @param roadSurfaceTemperatureC ?
 * @param freezingTemperatureNac ?
 * @param waterFilmHeight ?
 * @param iceLayerThickness ?
 * @param snowHeight ?
 * @param icePercentage ?
 * @param salineConcentration ?
 * @param friction ?
 * @param roadConditionType ?
 * @param measurementStatusBits ?
 * @param roadCondition ?
 * @param topic ?
 * @param tmpstmpForAdding ?
 */
public record RoadData(
    Integer id,
    Double roadSurfaceTemperatureC,
    Double freezingTemperatureNac,
    Double waterFilmHeight,
    Double iceLayerThickness,
    Double snowHeight,
    Double icePercentage,
    Double salineConcentration,
    Double friction,
    Double roadConditionType,
    Double measurementStatusBits,
    String roadCondition,
    String topic,
    LocalDateTime tmpstmpForAdding
)implements Measurement{}
