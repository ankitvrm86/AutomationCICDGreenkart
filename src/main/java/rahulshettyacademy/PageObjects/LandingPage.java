package rahulshettyacademy.PageObjects;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LandingPage extends AbstractComponent{
	
	WebDriver driver;
	
	public LandingPage (WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[@class='product']")
	List<WebElement> products;
	
	By quantityBy = By.xpath("div[@class='stepper-input']/a[@class='increment']");
	By addToCartButtonBy = By.xpath("div[@class='product-action']/button");
	By priceBy = By.xpath("p[@class='product-price']");
	
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
	}
	
	public int addProductToCart(String productName, int quantity)
	{
		WebElement selectedProduct = products.stream().filter(s->s.findElement(By.xpath("h4")).getText().split("-")[0].trim().contains(productName)).findFirst().orElse(null);
		for(int i=0;i<quantity;i++) {
			selectedProduct.findElement(quantityBy).click();
		}
		
		selectedProduct.findElement(addToCartButtonBy).click();
		int price = Integer.parseInt(selectedProduct.findElement(priceBy).getText());
		return price;
		
	}
	
	

}
