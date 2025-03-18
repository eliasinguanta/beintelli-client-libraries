package beintelliPlatformSdk.javaSdk.utils;

import beintelliPlatformSdk.javaSdk.apis.DataApi;

/**
 * Represents an rsu data point.
 *
 * @see DataApi#getRsuData()
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 * 
 * @param value1 ?
 * @param value2 ?
 */
public record RsuData(
    String value1,
    String value2
){}

