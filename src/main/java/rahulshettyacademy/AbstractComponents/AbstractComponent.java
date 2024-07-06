package rahulshettyacademy.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import rahulshettyacademy.PageObjects.CartPage;

public class AbstractComponent {
	
	WebDriver driver;
	
	public AbstractComponent(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[@class='cart-info']/table/tbody/tr[1]/td[3]/strong" )
	public WebElement items;
	
	@FindBy(xpath = "//div[@class='cart-info']/table/tbody/tr[2]/td[3]/strong" )
	public WebElement price;
	
	@FindBy(xpath= "//a[@class='cart-icon']/img" )
	WebElement cartImage;
	
	@FindBy(xpath ="//button[contains(text(),'PROCEED TO CHECKOUT')]" )
	WebElement cart;
	
	By cartButtonBy = By.xpath("//button[contains(text(),'PROCEED TO CHECKOUT')]");
	
	public void waitForElementToAppear(By findBy)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	public CartPage goToCartPage()
	{
		cartImage.click();
		waitForElementToAppear(cartButtonBy);
		cart.click();
		CartPage cartPage = new CartPage(driver);
		return cartPage;
	}
	
	

}
