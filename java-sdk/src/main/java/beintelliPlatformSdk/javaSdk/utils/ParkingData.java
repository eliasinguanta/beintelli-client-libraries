package beintelliPlatformSdk.javaSdk.utils;

import beintelliPlatformSdk.javaSdk.apis.DataApi;
import java.time.LocalDateTime;

/**
 * Represents an parking data point.
 *
 * @see DataApi#getParkingData(LocalDateTime, LocalDateTime)
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 * 
 * @param id of data point
 * @param customState ?
 * @param gapIds ?
 * @param name ?
 * @param rawDataArrival ?
 * @param rawDataCar ?
 * @param rawDataDepature ?
 * @param occupied ?
 * @param rawRefData ?
 * @param ref1 ?
 * @param ref2 ?
 * @param valid ?
 * @param tmpstmpForAdding ?
 */

public record ParkingData(
    Integer id,
    String customState,
    String gapIds,
    String name,
    String rawDataArrival,
    String rawDataCar,
    String rawDataDepature,
    String occupied,
    String rawRefData,
    String ref1,
    String ref2,
    String valid,
    LocalDateTime tmpstmpForAdding
) implements Measurement{}
