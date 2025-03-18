package beintelliPlatformSdk.javaSdk.utils;

public enum ExamplePath {
    MAP("src/test/resources/beintelliPlatformSdk/javaSdk/examples/hdmap.bin"),
    IMAGE("src/test/resources/beintelliPlatformSdk/javaSdk/examples/example.jpg"),
    OBJECT_SHEET("src/test/resources/beintelliPlatformSdk/javaSdk/examples/objectDetectionSheet.xlsx"),
    WEATHER_SHEET("src/test/resources/beintelliPlatformSdk/javaSdk/examples/weatherSheet.xlsx"),
    PARKING_SHEET("src/test/resources/beintelliPlatformSdk/javaSdk/examples/parkingSheet.xlsx"),
    ROAD_SHEET("src/test/resources/beintelliPlatformSdk/javaSdk/examples/roadSheet.xlsx"),
    ROSBAG("src/test/resources/beintelliPlatformSdk/javaSdk/examples/x.zip");

    private final String value;

    private ExamplePath(String value){
        this.value=value;
    }

    public String getValue(){
        return this.value;        
    }

}
