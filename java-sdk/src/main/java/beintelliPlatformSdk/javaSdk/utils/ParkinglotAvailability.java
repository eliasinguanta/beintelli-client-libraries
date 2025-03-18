package beintelliPlatformSdk.javaSdk.utils;

import beintelliPlatformSdk.javaSdk.apis.DataApi;

/**
 * Represents an parkinglot availability data point.
 *
 * @see DataApi#getParkinglotAvailability(String)
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 * 
 * @param value1 ?
 * @param value2 ?
 * @param gapId ?
 * @param value3 ?
 * @param value4 ?
 * @param value5 ?
 * @param value6 ?
 * @param value7 ?
 * @param value8 ?
 * @param value9 ?
 * @param value10 ?
 * @param value11 ?
 * @param value12 ?
 */
public record ParkinglotAvailability(
    Integer value1,
    String value2,
    String gapId,
    String value3,
    String value4,
    String value5,
    String value6,
    String value7,
    String value8,
    String value9,
    String value10,
    String value11,
    String value12
) {
}
