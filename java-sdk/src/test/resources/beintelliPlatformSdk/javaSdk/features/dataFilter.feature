Feature: Measurement Filter
    
    As a user,
    I want to filter measurement data based on specific attributes within a given time range
    
    @data
    @filter
    Scenario Outline: Filtering Data
        Given I request <dataType> data from "<from1>" to "<to1>"
        When I filter the data by <attribute> from <from2> to <to2>
        Then I expect to receive a <dataType> subset that meets the specified criteria

        Examples:
            |dataType |from1     |to1       |attribute   |from2|to2|
            |Weather  |2022-06-17|2022-06-18|Parking id  |min  |max|
            |Parking  |2023-08-07|2023-08-08|Road id     |min  |max|
            |Road     |2022-06-17|2022-06-18|Detection id|min  |max|
            |Detection|2020-01-01|2024-01-01|Weather id  |min  |max|

    Scenario: Filtering multiple times
        Given I request Weather data from "2022-06-17" to "2022-06-18"
        When I filter by ROAD_SURFACE_TEMPERATURE_C from 31 to 32
        Then I get less WeatherData
        When I filter by TEMPC from 19 to 20
        Then I get less WeatherData
        When I filter by HUMIDITY of 52
        Then I get less WeatherData



