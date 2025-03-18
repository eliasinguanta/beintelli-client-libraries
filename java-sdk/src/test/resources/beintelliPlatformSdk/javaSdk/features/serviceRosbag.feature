Feature: Rosbag Services

    As a User,
    I want to upload, download and convert rosbag files

    @service
    @rosbag
    Scenario: the user requests a list of all available rosbags
        When I request all available rosbags
        Then I receive a filled list of file names

    @service
    @rosbag
    Scenario: the user requests a specific rosbag
        When I request the rosbag x
        Then I receive a filled rosbag
    
    @service
    @rosbag
    Scenario: the user uploads a specific rosbag
        When I upload the rosbag x
        Then I am successful

    @service
    @rosbag
    Scenario: the user requests a list of all available converted rosbags
        When I request all available converted rosbags
        Then I receive a empty list of file names
    
    @service
    @rosbag
    Scenario: the user request a specific converted rosbag
        When I request the converted rosbag "x"
        Then I receive an empty converted rosbag

    @service
    @rosbag
    Scenario: the user converts a specific rosbag
        When I convert rosbag x
        Then I am successful









