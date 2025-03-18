Feature: Infrastructure Data

    As a User
    I want to request some infrastructure data

    @data
    @infrastructure
    Scenario Outline: the user requests existing structure data for a specific time frame
        When I request <dataType> data between "<startDate>" and "<endDate>"
        Then I receive a filled list of <dataType> data

        Examples:
            |dataType|startDate |endDate   |
            |weather |2022-06-17|2022-06-18|
            |parking |2023-08-07|2023-08-08|
            |road    |2022-06-17|2022-06-18|

    @data
    @infrastructure
    Scenario: the user request object data for a specific time frame
        When I request object detection data between "2020-01-01" and "2024-01-01"
        Then I get an empty list

    @data
    @infrastructure
    Scenario: the user request a specifc HD map
        When I request the HD map "BeIntelli_HD_Map_reduced_v_1.osm"
        Then I receive a HD map

    @data
    @infrastructure
    Scenario: the user request rsu data
        When I request the rsu data
        Then I receive rsu data 

    @data
    @infrastructure
    Scenario: the user request list of HD maps
        When I request the list of HD maps
        Then I receive a list of HD maps

    @data
    @infrastructure
    Scenario: the user request parkinglot data
        When I request <dataType> data for id "<gapId>"
        Then I receive a filled list of <dataType> data

        Examples:
            |dataType               |gapId                               |
            |parkinglot availability|da5b992e-8893-4be9-a27a-7cd9cc97ced0|
            |parkinglot information |da5b992e-8893-4be9-a27a-7cd9cc97ced0|

