package beintelliPlatformSdk.javaSdk.utils;

import beintelliPlatformSdk.javaSdk.apis.DataApi;
import java.time.LocalDateTime;

/**
 * Represents an object detection data point.
 *
 * @see DataApi#getDetectionData(LocalDateTime, LocalDateTime)
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 * 
 * @param id identifier of data point
 * @param timestamp ?
 * @param kafkaTopic ?
 * @param type ?
 * @param lon ?
 * @param lat ?
 * @param trackid ?
 * @param heading ?
 * @param speed ?
 * @param tmpstmpForAdding ?
 * 
 */
public record DetectionData(
    Object id,
    Object timestamp,
    Object kafkaTopic,
    Object type, 
    Object lon, 
    Object lat,
    Object trackid,
    Object heading,
    Object speed,
    LocalDateTime tmpstmpForAdding
) implements Measurement{}
