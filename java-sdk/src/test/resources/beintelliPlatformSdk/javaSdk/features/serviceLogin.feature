Feature: Login

    As a user,
    I have to sign in to use the entire SDK

    @service
    @login
    Scenario Outline: the user logs in
        When I sign in with correct credentials
        Then I receive access

    @service
    @login
    Scenario Outline: the user logs in
        When I sign in as "mike_müller" with "i like APPLES"
        Then I receive no access
