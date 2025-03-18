Feature: Vehicle Data

    As a user
    I want to request vehicle data 

    @data
    @vehicle
    Scenario: the user requests vehicle drive data
        When I request vehicle data between "2023-01-09" and "2023-01-10" without specifc id
        Then I receive some vehicle data
    
    @data
    @vehicle
    Scenario: the user requests specific vehicle drive data
        When I request vehicle data between "2023-01-09" and "2024-01-10" with id "tiguan"
        Then I receive some and only vehicle data with id "tiguan"

    @data
    @vehicle
    Scenario: the user requests specific drive route
        When I request the drive route with id ""
        Then I get no data



    
    