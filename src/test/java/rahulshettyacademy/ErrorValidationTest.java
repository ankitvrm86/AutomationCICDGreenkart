package rahulshettyacademy;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import rahulshettyacademy.PageObjects.CartPage;
import rahulshettyacademy.PageObjects.CheckoutPage;
import rahulshettyacademy.TestComponents.BaseTest;

public class ErrorValidationTest extends BaseTest{

	String productName = "Cauliflower";
	int quantity = 3;
	String promoCode = "rahulshettyacademy";
	
	@Test (groups = {"ErrorHandling"}, retryAnalyzer= rahulshettyacademy.TestComponents.Retry.class)
	public void chkAgreeErrorValidation() throws IOException{
				
		int price = landingPage.addProductToCart(productName, quantity);
		int totalPrice= price* (quantity+1);
		
		Assert.assertTrue(landingPage.items.getText().equals("1"));
		Assert.assertTrue(landingPage.price.getText().equals(Integer.toString(totalPrice)));
		
		CartPage cartPage=landingPage.goToCartPage();
		
		boolean productMatch= cartPage.verifyProductDisplay(productName);
		boolean quantityMatch= cartPage.verifyQuantityDisplay(productName, quantity+1);
		boolean priceMatch= cartPage.verifyPriceDisplay(totalPrice);
		Assert.assertTrue(productMatch);
		Assert.assertTrue(quantityMatch);
		Assert.assertTrue(priceMatch);
		
		cartPage.applyPromoCode(promoCode);
		
		String promoSuccessApplyMessage = cartPage.getPromoApplyMessage();
		Assert.assertTrue(promoSuccessApplyMessage.equalsIgnoreCase("Code applied ..!"));
		
		CheckoutPage checkoutPage=cartPage.submitOrder();
		
//		checkoutPage.selectCountry(countryName);
		checkoutPage.confirmOrder();
		String errorMessage = checkoutPage.getErrorMessage();
		Assert.assertEquals(errorMessage, "Please accept Terms & Conditions - Required");
		
	}
	
	@Test
	public void productErrorValidation()
	{
		
		int price = landingPage.addProductToCart(productName, quantity);
		int totalPrice= price* (quantity+1);
		
		Assert.assertTrue(landingPage.items.getText().equals("1"));
		Assert.assertTrue(landingPage.price.getText().equals(Integer.toString(totalPrice)));
		
		CartPage cartPage=landingPage.goToCartPage();
	
		boolean productMatch= cartPage.verifyProductDisplay("Cauliflowerr");
		boolean quantityMatch= cartPage.verifyQuantityDisplay(productName, quantity+1);
		boolean priceMatch= cartPage.verifyPriceDisplay(totalPrice+1);
		Assert.assertFalse(productMatch);
		Assert.assertTrue(quantityMatch);
		Assert.assertFalse(priceMatch);
	}
	

}
