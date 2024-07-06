package rahulshettyacademy.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent{
	
	WebDriver driver;
	
	public CartPage (WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	WebElement productDisplay;
	By tableBy = By.xpath("//table");
	
	@FindBy (xpath = "//table/tbody/tr/td[2]/p")
	List<WebElement> cartItemNames;
	
	By quantityBy = By.xpath("parent::td/following-sibling::td[1]");
	By priceBy = By.xpath("parent::td/following-sibling::td[3]");
	
	@FindBy(xpath ="//input[@class='promoCode']")
	WebElement promoCodeTextBox;
	
	@FindBy(xpath="//button[@class='promoBtn']" )
	WebElement promoCodeButton;
	
	By promoInfoBy = By.xpath("//span[@class='promoInfo']");
	
	@FindBy(xpath="//span[@class='promoInfo']")		
	WebElement promoInfoMessage;
	
	@FindBy(xpath="//button[contains(text(),'Place Order')]")
	WebElement submitOrderButton;
	
	public boolean verifyProductDisplay(String productName)
	{
		waitForElementToAppear(tableBy);
		boolean productMatch = cartItemNames.stream().anyMatch(s->s.getText().contains(productName));
		return productMatch;
		
	}
	
	public boolean verifyQuantityDisplay(String productName, int quantity)
	{
		String actualQuantity = Integer.toString(quantity);
		productDisplay = cartItemNames.stream().filter(s->s.getText().contains(productName)).findFirst().orElse(null);
		boolean quantityMatch = productDisplay.findElement(quantityBy).getText().contains(actualQuantity);
		return quantityMatch;
		
	}
	
	public boolean verifyPriceDisplay(int price)
	{
		String totalPrice = Integer.toString(price);
		boolean priceMatch = productDisplay.findElement(priceBy).getText().contains(totalPrice);
		return priceMatch;
		
		
	}
	
	public void applyPromoCode(String promoCode)
	{
		promoCodeTextBox.sendKeys(promoCode);
		promoCodeButton.click();
	}
	
	public String getPromoApplyMessage()
	{
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='promoInfo']")));
		//Assert.assertTrue(driver.findElement(By.xpath("//span[@class='promoInfo']")).getText().equalsIgnoreCase("Code applied ..!"));
		waitForElementToAppear(promoInfoBy);
		String promoMessage = promoInfoMessage.getText();
		return promoMessage;
		
				
	}
	
	public CheckoutPage submitOrder() {
		submitOrderButton.click();
		CheckoutPage checkoutPage =  new CheckoutPage(driver);
		return checkoutPage;
	}

}
