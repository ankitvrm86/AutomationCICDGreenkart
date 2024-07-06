package rahulshettyacademy;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import rahulshettyacademy.PageObjects.CartPage;
import rahulshettyacademy.PageObjects.CheckoutPage;
import rahulshettyacademy.PageObjects.ConfirmationPage;
import rahulshettyacademy.TestComponents.BaseTest;

public class SubmitOrderTest extends BaseTest{

	@Test (dataProvider = "getData" , groups = {"Purchase"})
	public void submitOrder(HashMap<String, String> input) throws IOException{
				
//		String productName = "Cauliflower";
		int quantity = 3;
		String countryName = "India";
		String promoCode = "rahulshettyacademy";
		
		//landing page
//		LandingPage landingPage = launchApplication();
		
		//Select desired product and its quantity
		int price = landingPage.addProductToCart(input.get("productName"), quantity);
		int totalPrice= price* (quantity+1);
		System.out.println(Integer.toString(totalPrice));
		
		//verify cart info
		Assert.assertTrue(landingPage.items.getText().equals("1"));
		Assert.assertTrue(landingPage.price.getText().equals(Integer.toString(totalPrice)));
		
		//go to cart page
		CartPage cartPage=landingPage.goToCartPage();
		
		//verify cart info on Cart page
		boolean productMatch= cartPage.verifyProductDisplay(input.get("productName"));
		boolean quantityMatch= cartPage.verifyQuantityDisplay(input.get("productName"), quantity+1);
		boolean priceMatch= cartPage.verifyPriceDisplay(totalPrice);
		Assert.assertTrue(productMatch);
		Assert.assertTrue(quantityMatch);
		Assert.assertTrue(priceMatch);
		
		//apply promocode
		cartPage.applyPromoCode(promoCode);
		
		// verify if promocode applied
		String promoSuccessApplyMessage = cartPage.getPromoApplyMessage();
		Assert.assertTrue(promoSuccessApplyMessage.equalsIgnoreCase("Code applied ..!"));
		
		//Submit Order
		CheckoutPage checkoutPage=cartPage.submitOrder();
		
		//Checkout Order
		checkoutPage.selectCountry(countryName);
		ConfirmationPage confirmationPage=checkoutPage.confirmOrder();
		
		//verify order placed
		System.out.println(confirmationPage.confirmationMessageDisplay());
		
	}
	
	@Test (dependsOnMethods = {"submitOrder"})
	public void verfiyCartAfterOrder() {
		
		Assert.assertTrue(landingPage.items.getText().equals("0"));
		Assert.assertTrue(landingPage.price.getText().equals("0"));
		
	}
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
//		HashMap<String, String> map1 = new HashMap<String, String>();
//		map1.put("productName", "Cauliflower");
//		
//		HashMap<String, String> map2 = new HashMap<String, String>();
//		map2.put("productName", "Beans");
//		return new Object[][] {{map1}, {map2}};
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\rahulshettyacademy\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
	

}
