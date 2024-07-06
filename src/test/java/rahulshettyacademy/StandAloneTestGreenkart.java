package rahulshettyacademy;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
public class StandAloneTestGreenkart {

	public static void main(String[] args) {
		WebDriverManager.chromedriver().clearDriverCache().setup();
		WebDriver driver =  new ChromeDriver();
//		driver.manage().window().setSize(new Dimension(1920,1080));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			
		//landing page
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
		String productName = "Cauliflower";
		int quantity = 3;
		String countryName = "India";
		
		//Select desired product and its quantity
		List<WebElement> products = driver.findElements(By.xpath("//div[@class='product']"));
		WebElement selectedProduct = products.stream().filter(s->s.findElement(By.xpath("h4")).getText().split("-")[0].trim().contains(productName)).findFirst().orElse(null);
			//select quantity
		for(int i=0;i<quantity;i++) {
		selectedProduct.findElement(By.xpath("div[@class='stepper-input']/a[@class='increment']")).click();
		}
		
		//Add desired product to Cart
		selectedProduct.findElement(By.xpath("div[@class='product-action']/button")).click();
		System.out.println(selectedProduct.findElement(By.xpath("p[@class='product-price']")).getText());
		
		//verify cart info
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='cart-info']/table/tbody/tr[1]/td[3]/strong")).getText().equals("1"));
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='cart-info']/table/tbody/tr[2]/td[3]/strong")).getText().equals("240"));
		
		//go to cart page
		driver.findElement(By.xpath("//a[@class='cart-icon']/img")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'PROCEED TO CHECKOUT')]")));
		driver.findElement(By.xpath("//button[contains(text(),'PROCEED TO CHECKOUT')]")).click();
		
		//verify cart info on Cart page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
		List<WebElement> cartItemNames = driver.findElements(By.xpath("//table/tbody/tr/td[2]/p"));
		boolean productMatch = cartItemNames.stream().anyMatch(s->s.getText().contains(productName));
		WebElement productDisplay = cartItemNames.stream().filter(s->s.getText().contains(productName)).findFirst().orElse(null);
		boolean quantityMatch = productDisplay.findElement(By.xpath("parent::td/following-sibling::td[1]")).getText().contains("4");
		boolean priceMatch = productDisplay.findElement(By.xpath("parent::td/following-sibling::td[3]")).getText().contains("240");
		
		Assert.assertTrue(productMatch);
		Assert.assertTrue(quantityMatch);
		Assert.assertTrue(priceMatch);
		
		//apply promocode
		driver.findElement(By.xpath("//input[@class='promoCode']")).sendKeys("rahulshettyacademy");
		driver.findElement(By.xpath("//button[@class='promoBtn']")).click();
		
		// verify if promocode applied
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='promoInfo']")));
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='promoInfo']")).getText().equalsIgnoreCase("Code applied ..!"));
		
		//Submit Order
		driver.findElement(By.xpath("//button[contains(text(),'Place Order')]")).click();
		
		//Checkout Order
		WebElement countryDropdown = driver.findElement(By.xpath("//div[@class='products'] //select"));
		Select sel = new Select(countryDropdown);
		sel.selectByValue(countryName);
		driver.findElement(By.xpath("//input[@class = 'chkAgree']")).click();
		driver.findElement(By.xpath("//button[contains(text(),'Proceed')]")).click();
		
		//verify order placed
		System.out.println(driver.findElement(By.xpath("//span[contains(text(),'placed successfully')]")).getText());
	}
	

}
