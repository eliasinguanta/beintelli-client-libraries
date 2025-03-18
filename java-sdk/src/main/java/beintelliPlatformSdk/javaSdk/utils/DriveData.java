package beintelliPlatformSdk.javaSdk.utils;

import beintelliPlatformSdk.javaSdk.apis.DataApi;

/**
 * Represents an vehicle drive data point.
 *
 * @see DataApi#getDriveData(java.time.LocalDateTime, java.time.LocalDateTime, String)
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 * 
 * @param id identifier of data point
 * @param value1 ?
 * @param value2 ?
 * @param value3 ?
 * @param value4 ?
 * @param value5 ?
 * @param value6 ?
 * @param value7 ?
 * @param value8 ?
 */
public record DriveData(
    String id,
    String value1,
    String value2,
    String value3,
    Double value4,
    Double value5,
    Double value6,
    Double value7,
    Double value8
) {
}
