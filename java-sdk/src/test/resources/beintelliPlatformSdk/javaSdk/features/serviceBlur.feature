Feature: Anonymizing image

    As a user
    I want to upload images to blur them

    @service
    @blur
    Scenario: the user blurs an image
        When I blur the image example.jpg
        Then I receive an image