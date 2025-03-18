# BeIntelli Platform Java SDK

This Java SDK was created as part of a bachelor's thesis for the BeIntelli Platform, a project in the context of autonomous mobility, and serves as a client library for the Rest API with a few additional functions.



## Authors

[@eliasinguanta](https://gitlab.com/eliasinguanta)



## License

[MIT](https://opensource.org/license/mit)




## Usage

```java

import java.time.LocalDateTime;
import java.util.List;
import beintelliPlatformSdk.javaSdk.utils.WeatherData;

public class App {
    
    public static void main(String[] args){
        User user = new User();
        user.login("USERNAME", "PASSWORD").join();
        List<WeatherData> weather = user.data.getWeatherData(
            LocalDateTime.of(2020, 1, 1, 1, 1, 1),
            LocalDateTime.of(2024, 1, 1, 1, 1, 1)
            ).join();
        System.out.println(weather);
    }
}

```