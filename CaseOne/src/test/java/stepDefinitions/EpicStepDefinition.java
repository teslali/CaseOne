package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.UserEpicPage;
import utils.TestContextSetup;

public class EpicStepDefinition {
	UserEpicPage userEpicPage;
	TestContextSetup testContextSetup;

	public EpicStepDefinition(TestContextSetup testContextSetup) {
		this.testContextSetup = testContextSetup;
		this.userEpicPage = testContextSetup.pageObjectManager.getUserEpicPage();

	}

	@Given("User is on landing page")
	public void user_is_on_landing_page() throws InterruptedException {
		userEpicPage.landingPage();
	}

	@Given("User clicks on man jacket")
	public void user_clicks_on_man_jacket() throws InterruptedException {
		userEpicPage.manJacket();

	}

	@Given("User applies price filter")
	public void user_applies_price_filter() throws InterruptedException {
		userEpicPage.jacketPage();

	}

	@Given("User chose which jacket to buy")
	public void user_chose_which_jacket_to_buy() throws InterruptedException {
		userEpicPage.inspectJacket();

	}

	@When("User adds jacket to cart")
	public void user_adds_jacket_to_cart() throws InterruptedException {
		userEpicPage.addCart();

	}

	@Then("User goes to cart and goes back to homepage")
	public void user_goes_to_cart_and_goes_back_to_homepage() throws IOException, InterruptedException {
		userEpicPage.cart();

	}

}
