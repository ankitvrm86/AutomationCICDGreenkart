package rahulshettyacademy.stepDefinitions;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import rahulshettyacademy.PageObjects.CartPage;
import rahulshettyacademy.PageObjects.CheckoutPage;
import rahulshettyacademy.PageObjects.ConfirmationPage;
import rahulshettyacademy.PageObjects.LandingPage;
import rahulshettyacademy.TestComponents.BaseTest;

public class StepDefinitionImpl extends BaseTest{
	public LandingPage landingPage;
	public int totalPrice;
	public CartPage cartPage;
	public CheckoutPage checkoutPage;
	public ConfirmationPage confirmationPage;
	
	@Given("User is on Home Page")
	public void user_is_on_Home_Page() throws IOException {
		landingPage = launchApplication();
	}
	
	@When ("^I add product (.+) and its quantity (.+) to cart$")
	public void i_add_product_and_its_quantity_to_cart(String productName, int quantity)
	{
		int price = landingPage.addProductToCart(productName, quantity);
		totalPrice= price* (quantity+1);
		System.out.println(Integer.toString(totalPrice));
	}
	
	@When ("verify	cart information on HomePage")
	public void verify_cart_information_on_HomePage() {
		Assert.assertTrue(landingPage.items.getText().equals("1"));
		Assert.assertTrue(landingPage.price.getText().equals(Integer.toString(totalPrice)));
	}
	
	@When ("^click on Cart logo and verify product (.+) and its quantity (.+) on Cart Page$")
	public void click_on_Cart_logo_verify_product_and_its_quantity_on_CartPage(String productName, int quantity)
	{
		cartPage=landingPage.goToCartPage();
		boolean productMatch= cartPage.verifyProductDisplay(productName);
		boolean quantityMatch= cartPage.verifyQuantityDisplay(productName, quantity+1);
		boolean priceMatch= cartPage.verifyPriceDisplay(totalPrice);
		Assert.assertTrue(productMatch);
		Assert.assertTrue(quantityMatch);
		Assert.assertTrue(priceMatch);
	}
	
	@When("^Apply promo code (.+) and verify (.+) message is displayed$")
	public void apply_promocode_and_verify_message_display(String promoCode, String promoMessage) {
		cartPage.applyPromoCode(promoCode);
		String promoSuccessApplyMessage = cartPage.getPromoApplyMessage();
		Assert.assertTrue(promoSuccessApplyMessage.equalsIgnoreCase(promoMessage));
	}
	
	@When("^Checkout with country name (.+) and finally submit the order by clicking on confirm order button$")
	public void checkout_with_countryName_and_submit_order(String countryName)
	{
		checkoutPage=cartPage.submitOrder();
		checkoutPage.selectCountry(countryName);
		confirmationPage=checkoutPage.confirmOrder();
		
	}
	
	@Then("confirmation message is displayed on the Confirmation Page")
	public void confirmation_message_is_displayed_on_the_ConfirmationPage()
	{
		System.out.println(confirmationPage.confirmationMessageDisplay());
		
	}
}
