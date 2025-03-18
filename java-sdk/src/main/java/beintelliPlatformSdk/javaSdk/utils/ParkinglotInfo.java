package beintelliPlatformSdk.javaSdk.utils;

import beintelliPlatformSdk.javaSdk.apis.DataApi;

/**
 * Represents an parkinglot information data point.
 *
 * @see DataApi#getParkinglotInfo(String)
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 * 
 * @param gapId ?
 * @param value1 ?
 * @param value2 ?
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
 * @param value13 ?
 * @param value14 ?
 * @param value15 ? 
 * @param value16 ?
 * @param value17 ?
 */
public record ParkinglotInfo(
    String gapId,
    String value1,
    String value2,
    
    Integer value3,

    String value4,
    String value5,
    String value6,
    String value7,

    Double value8,
    Double value9,
    Double value10,
    Double value11,
    Double value12,
    
    Double value13,
    Double value14,
    Double value15,
    Double value16,
    Double value17
) {
    
}
