Feature: Waikiki Jacket

@Scenario
Scenario: User choses jacket to buy and adds it to cart
    
    
    Given User is on landing page
    And User clicks on man jacket
    And User applies price filter
    And User chose which jacket to buy
   	When User adds jacket to cart
   	Then User goes to cart and goes back to homepage
    


   	 
    
