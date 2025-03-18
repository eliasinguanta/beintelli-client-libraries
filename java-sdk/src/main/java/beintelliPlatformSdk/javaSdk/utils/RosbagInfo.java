package beintelliPlatformSdk.javaSdk.utils;

import beintelliPlatformSdk.javaSdk.apis.ServiceApi;

/**
 * Represents Rosbag meta data.
 *
 * @see ServiceApi#getRosbags()
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 *
 * @param folderName ?
 * @param sizeBytes ?
 */
public record RosbagInfo(
    String folderName,
    Long sizeBytes
) {
} 
