package beintelliPlatformSdk.javaSdk.utils;

import beintelliPlatformSdk.javaSdk.apis.DataApi;

/**
 * Represents HDMap meta data.
 * Is wrapped in a DTO for expandability reasons.
 *
 * @see DataApi#getHdMaps()
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 * 
 * @param filename of the rosbag file
 */
public record MapInfo(String filename) {}
