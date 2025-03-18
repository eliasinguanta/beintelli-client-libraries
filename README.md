# BeIntelli Client Library
> [!WARNING]  
> The REST API may have changed. The client library may not work with the current API.

## Description
This java and python client library was created as part of my bachelor's thesis for the BeIntelli platform, a project in the field of autonomous mobility. It serves as an abstraction layer for the REST API with a few additional features. A small example shows how to use the client library.

## Getting Started
You can install the java client library with [Maven Central](https://central.sonatype.com/artifact/io.gitlab.eliasinguanta/beintelli-platform-java-sdk) and start with the following example:

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

You can install the python client library with [PyPI](https://pypi.org/project/beintelli-platform-python-sdk/) and start with the following example:
```python
import asyncio
from asyncio import Future
from datetime import datetime

from src.beintelli_platform_python_sdk_eliasinguanta.measurement_list import MeasurementList
from src.beintelli_platform_python_sdk_eliasinguanta.user import User
from src.beintelli_platform_python_sdk_eliasinguanta.utils.types import WeatherData

async def print_weather():
    """show data api"""

    user: User = User()

    #log in
    await user.login("my_username","my_password")

    #params
    start_date: datetime = datetime.fromisocalendar(2020, 1, 1)
    end_date: datetime = datetime.fromisocalendar(2024, 1, 1)

    #request weather data
    future_weather: Future[MeasurementList[WeatherData]] = await user.data.get_weather_data(start_date, end_date)
    weather: MeasurementList[WeatherData] = await future_weather
    print(weather)

asyncio.run(print_weather())

```
You can start by downloading this repository and do:
- Install Maven
- Go to the project folder `java-example`
- Run the command `mvn spring-boot:run`
- The example-application can be accessed in the browser at http://localhost:8080


## Development
The python client library uses the [requests](https://pypi.org/project/requests/) library for handling HTTP, while the java client library relies on [Jackson](https://github.com/FasterXML/jackson) for JSON processing. For development, I used the behavior-driven framework [Cucumber](https://cucumber.io/). The example backend logic is written in java, and the UI is built with [Spring Boot](https://spring.io/projects/spring-boot).

## Authors

[@eliasinguanta](https://gitlab.com/eliasinguanta)



## License

[MIT](https://opensource.org/license/mit)

