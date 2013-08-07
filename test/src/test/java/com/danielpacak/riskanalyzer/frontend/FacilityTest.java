package com.danielpacak.riskanalyzer.frontend;

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
public class FacilityTest {

	WebDriver webDriver;

	@Before
	public void beforeEachTest() throws Exception {
		webDriver = new FirefoxDriver();
	}

	@After
	public void afterEachTest() throws Exception {
		webDriver.quit();
	}

	@Test
	public void testCreateFacility() throws Exception {
		// webDriver.navigate().to("http://risk-analyzer-frontend.appspot.com/");
		webDriver.get("http://localhost:8080/frontend.deployment.dev");

		WebElement newFacilityButton = findButtonById(webDriver, "newFacilityButton");
		newFacilityButton.click();

		WebElement nameInput = findInputById(webDriver, "facilityName");
		WebElement addressInput = findInputById(webDriver, "facilityAddress");
		WebElement typeInput = findInputById(webDriver, "facilityTypeIndependent");
		typeInput.click();

		setText(nameInput, "Antibes");
		setText(addressInput, "Antibes");

		WebElement saveButton = findButtonById(webDriver, "facilityDialogSaveButton");
		saveButton.click();

	}

	// this is a trick for extjs framework that is using id for messing up
	public static WebElement findButtonById(WebDriver webDriver, String id) {
		return findAndWait(webDriver, By.xpath(String.format("//button[contains(@id,'%s')]", id)));
	}

	public static void setText(WebElement webElement, String text) {
		webElement.clear();
		webElement.sendKeys(text);
	}

	public static WebElement findInputById(WebDriver webDriver, String id) {
		return findAndWait(webDriver, By.xpath(String.format("//input[contains(@id,'%s')]", id)));
	}

	public static WebElement findAndWait(WebDriver driver, final By by) {
		int timeoutInSeconds = 5;
		WebElement myDynamicElement = (new WebDriverWait(driver, timeoutInSeconds))
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						return d.findElement(by);
					}
				});
		return myDynamicElement;
	}

}
