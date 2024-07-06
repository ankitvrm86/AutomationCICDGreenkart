
@tag
Feature: Purchase the order form Ecommerce Website
 As a customer, I want to puchase vegetables from the website through online

  @Regression
  Scenario Outline: Positive flow of submitting the order
    Given User is on Home Page
    When I add product <productName> and its quantity <quantity> to cart
    And verify	cart information on HomePage
    And click on Cart logo and verify product <productName> and its quantity <quantity> on Cart Page
    And Apply promo code <promocode> and verify <promoMessage> message is displayed 
    And Checkout with country name <countryName> and finally submit the order by clicking on confirm order button
    Then confirmation message is displayed on the Confirmation Page

    Examples: 
      | productName  | quantity  | promocode  				| countryName  | promoMessage      | 
      | Cucumber		 | 3				 | rahulshettyacademy | India        | Code applied ..!	 |
      | Beans				 | 2				 | rahulshettyacademy | India        | Code applied ..!	 |
     
