package rahulshettyacademy.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent{
	
	WebDriver driver;
	
	public CheckoutPage (WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy (xpath="//div[@class='products'] //select")
	WebElement countryDropdown;
	
	@FindBy (xpath = "//input[@class = 'chkAgree']")
	WebElement checkAgree;
	
	@FindBy(xpath = "//button[contains(text(),'Proceed')]")
	WebElement confirmOrder;
	
	@FindBy(xpath = "//span[@class='errorAlert']/b")
	WebElement errorAlert;
	
	public void selectCountry(String countryName)
	{
		Select sel = new Select(countryDropdown);
		sel.selectByValue(countryName);
		checkAgree.click();
	}
	
	public String getErrorMessage()
	{
		return errorAlert.getText();
	}
	
	public ConfirmationPage confirmOrder()
	{
		confirmOrder.click();
		ConfirmationPage confirmationPage =  new ConfirmationPage(driver);
		return confirmationPage;
		
	}
}
