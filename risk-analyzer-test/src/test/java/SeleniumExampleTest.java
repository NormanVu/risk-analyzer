import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

@Ignore("Run it manually or during the right maven lifecycle phase ..")
public class SeleniumExampleTest {

	WebDriver webDriver;

	@Before
	public void beforeEachTest() throws Exception {
		webDriver = new FirefoxDriver();
	}

	@After
	public void afterEachTest() throws Exception {
		// webDriver.quit();
	}

	// <>button id="button-1022-btnEl" class="x-btn-center" autocomplete="off"
	// role="button" hidefocus="true" type="button" style="height: 16px;">
	// <span id="button-1022-btnInnerEl" class="x-btn-inner" style="">New</span>

	@Test
	public void testSomethingSimple() throws Exception {
		int timeout = 10;
		webDriver.navigate().to("http://risk-analyzer-frontend.appspot.com/");

		WebElement newFacilityButton = findAndWait(webDriver,
				By.xpath("//span[contains(@id,'button') and text()='New']"), timeout);
		newFacilityButton.click();
		
		
		WebElement description = findAndWait(webDriver, By.xpath("//textarea[@name='description']"), timeout);
		description.clear(); // remove text
		description.sendKeys("Hello World!");
	}

	public static WebElement findAndWait(WebDriver driver, final By by,
			long timeOutSeconds) {
		WebElement myDynamicElement = (new WebDriverWait(driver, timeOutSeconds))
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						return d.findElement(by);
					}
				});
		return myDynamicElement;
	}

}
