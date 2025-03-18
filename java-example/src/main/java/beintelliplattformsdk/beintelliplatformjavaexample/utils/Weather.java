package beintelliplattformsdk.beintelliplatformjavaexample.utils;

public record Weather(
    Integer id,
    Double tempc,
    Double humidity,
    Double absoluteHumidity,
    Double airPressure,
    Double absolutePrecipitation,
    Double absolutePrecipitationmm,
    Double differentialPrecipitation,
    Double differentialPrecipitationmm,
    Double precipitationIntensity,
    Double precipitationType,
    String precipitation,
    String topic,
    String tmpstmpForAdding) {}