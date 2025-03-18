package beintelliPlatformSdk.javaSdk.utils;

import beintelliPlatformSdk.javaSdk.apis.DataApi;
import java.util.List;

/**
 * Represents an route data point.
 *
 * @see DataApi#getRouteData(String)
 * @author Elias Inguanta
 * @version 0.1.0
 * @since 0.1.0
 * 
 * @param route is a list of values. How many is currently unknown
 */
public record RouteData(List<?> route) {
    
}
