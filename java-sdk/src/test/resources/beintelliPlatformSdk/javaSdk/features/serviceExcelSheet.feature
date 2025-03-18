Feature: create Excel sheet

    As a user,
    I want to get some infrastructure data as Excel sheet 

    @service
    @excelSheet
    Scenario Outline: the user requests existing structure data for a specific time frame as excel sheet
        When I request <dataType> excel sheet between "<startDate>" and "<endDate>"
        Then I receive a <excelSheet>

        Examples:
            |dataType        |startDate |endDate   |excelSheet  |
            |weather         |2022-06-17|2022-06-18|weatherSheet|
            |parking         |2023-08-07|2023-08-08|parkingSheet|
            |road            |2022-06-17|2022-06-18|roadSheet   |

    @service
    @excelSheet
    Scenario: the user requests object data for a specific time frame as excel sheet
        When I request object detection excel sheet between "2020-01-01" and "2024-01-01"
        Then I receive objectDetectionSheet