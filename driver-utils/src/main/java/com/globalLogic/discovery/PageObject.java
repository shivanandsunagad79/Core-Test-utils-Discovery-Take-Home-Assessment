package com.globalLogic.discovery;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.JavascriptExecutor;



/**
 * @author shivanand sunagad
 *
 */
public abstract class PageObject extends LoadableComponent<PageObject> {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	DriverInitializer driverInitializer = DriverInitializer.getdriverInitializerInstatnce();
	// Webdriver object
	public WebDriver driver;

	public void init() {
		this.driver = driverInitializer.getDriver();
		PageFactory.initElements(driver, this);
	}

	// =====================================================================================
	// Start webdriver wrapper methods
	// =====================================================================================
	/**
	 * @param byLocator
	 * @return
	 */
	public WebElement findElement(By byLocator) {
		if (driver == null) {
			throwNullPointerExeptionForNullDriver();
		}
		WebElement element;
		if (!isElementEnabledWithinWait(byLocator, 0)) {
			return null;
		}
		element = driver.findElement(byLocator);
		return element;
	}

	/**
	 * @param byLocator
	 * @param maxWaitTime
	 * @return
	 */
	public WebElement findElement(By byLocator, int maxWaitTime) {
		if (driver == null) {
			throwNullPointerExeptionForNullDriver();
		}
		WebElement element;
		if (!isElementEnabledWithinWait(byLocator, maxWaitTime)) {
			return null;
		}
		element = driver.findElement(byLocator);
		return element;

	}

	/**
	 * @param byLocator
	 * @param maxWaitTime
	 * @return
	 */
	public WebElement findElementThatIsVisibale(By byLocator, int maxWaitTime) {
		if (driver == null) {
			throwNullPointerExeptionForNullDriver();
		}
		WebElement element;
		if (!isElementVisiblWithinWait(byLocator, maxWaitTime)) {
			return null;
		}
		element = driver.findElement(byLocator);
		return element;

	}

	/**
	 * @param byLocator
	 * @param maxWaitTime
	 * @return
	 */
	public WebElement findElementThatIsPresent(final By byLocator, int maxWaitTime) {
		if (driver == null) {
			throwNullPointerExeptionForNullDriver();
		}
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(maxWaitTime, TimeUnit.SECONDS)
				.pollingEvery(200, TimeUnit.MILLISECONDS);

		try {
			return wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					List<WebElement> elems = driver.findElements(byLocator);
					if (elems.size() > 0) {
						return elems.get(0);
					} else {
						return null;
					}
				}
			});
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param byLocator
	 * @return
	 */
	public List<WebElement> findElements(By byLocator) {
		if (driver == null) {
			throwNullPointerExeptionForNullDriver();
		}
		List<WebElement> result;
		result = driver.findElements(byLocator);
		return result;
	}

	/**
	 * @param byLocator
	 * @param maxWaitTime
	 * @return
	 */
	public List<WebElement> findElements(By byLocator, int maxWaitTime) {
		if (driver == null) {
			throwNullPointerExeptionForNullDriver();
		}
		List<WebElement> result;
		result = driver.findElements(byLocator);
		return result;
	}

	/**
	 * @param InputText
	 * @param byLocator
	 */
	public void type(String InputText, By byLocator) {
		WebElement element = findElement(byLocator);
		if (element == null) {
			logErrorForNotFindingElement(byLocator);
		}
		element.sendKeys(InputText);
	}

	/**
	 * @param inputText
	 * @param element
	 */
	public void type(String inputText, WebElement element) {
		if (element == null) {
			logErrorForNotFindingElement(element);
			return;
		}
		element.sendKeys(inputText);
	}

	/**
	 * @param byLocator
	 */
	public void clear(By byLocator) {
		WebElement element = findElement(byLocator);
		if (element == null) {
			logErrorForNotFindingElement(byLocator);
			return;
		}
		element.clear();
	}

	/**
	 * @param element
	 */
	public void clear(WebElement element) {
		if (element == null) {
			logErrorForNotFindingElement(element);
			return;
		}
		element.clear();
	}

	/**
	 * @param inputText
	 * @param byLocator
	 */
	public void clearAndType(String inputText, By byLocator) {
		clear(byLocator);
		type(inputText, byLocator);
	}

	/**
	 * @param inputText
	 * @param element
	 */
	public void clearAndType(String inputText, WebElement element) {
		clear(element);
		type(inputText, element);
	}
	
	/**
	 * @param byLocator
	 * @return
	 */
	public boolean isDisplayed(By byLocator) {
		return isElementCurrentlyDisplayed(byLocator);
	}

	/**
	 * @param byLocator
	 * @param maxWaitTime
	 * @return
	 */
	public boolean isDisplayed(By byLocator, int maxWaitTime) {
		for (int waitSoFar = 1; waitSoFar < maxWaitTime; waitSoFar++) {
			if (isElementCurrentlyDisplayed(byLocator)) {
				return true;
			}
			TimeManager.waitInSeconds(TimeEntity.SEC_1.getSeconds()); // Wait 1 second and try again.
		}
		if (isElementCurrentlyDisplayed(byLocator)) {
			return true;
		}
		return false;
	}
	/**
	 * @param byLocator
	 * @return
	 */
	private boolean isElementCurrentlyDisplayed(By byLocator) {
		WebElement element = findElementThatIsPresent(byLocator, 0);
		if ((element != null) && element.isDisplayed()) {
			return true;
		}
		return false;
	}
	/**
	 * @param byLocator
	 */
	public void click(By byLocator, int maxWaitTime) {
		WebElement element = findElement((byLocator),maxWaitTime);
		if (element == null) {
			logErrorForNotFindingElement(byLocator);
			return;
		}
		element.click();
	}
	public void click(WebElement element) {
		if (element == null) {
			logErrorForNotFindingElement();
			return;
		}
		element.click();
	}
	/**
	 * @param byLocator
	 * @param value
	 */
	public void selectByValue(By byLocator,String value) {
		WebElement element = findElement(byLocator);
		if (element == null) {
			logErrorForNotFindingElement(byLocator);
			return;
		}
		Select select=new Select(element);
		select.selectByValue(value);
	}
	// =========================================================================================
	// End of Selenium core wrapper methods
	// =========================================================================================
	// ==========================================================================================
	// wait methods
	// ==========================================================================================
	/**
	 * @param byLocator
	 * @param maxWaitTime
	 * @return
	 */
	private boolean isElementEnabledWithinWait(By byLocator, int maxWaitTime) {
		if (isWaitForSuccessful(ExpectedConditions.elementToBeClickable(byLocator), maxWaitTime)) {
			return true;
		}
		return false;
	}

	/**
	 * @param condition
	 * @param maxWaitTime
	 * @return
	 */
	private boolean isWaitForSuccessful(ExpectedCondition<WebElement> condition, Integer maxWaitTime) {
		if (driver == null) {
			throwNullPointerExeptionForNullDriver();
		}
		if (maxWaitTime == 0) {
			maxWaitTime = 3;
		}
		WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);
		try {
			wait.until(condition);
			return true;
		} catch (TimeoutException e) {
			return false;
		}

	}

	/**
	 * @param byLocator
	 * @param maxWaitTime
	 * @return
	 */
	private boolean isElementVisiblWithinWait(By byLocator, int maxWaitTime) {
		if (isWaitForSuccessful(ExpectedConditions.visibilityOfElementLocated(byLocator), maxWaitTime)) {
			return true;
		}
		return false;
	}
	
	public void waitUntilLoadedAndElementClickable(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, TimeEntity.SEC_30.getSeconds());
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		}

	public void waitUntilLoadedAndElementVisible(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, TimeEntity.SEC_30.getSeconds());
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void waitUntilElementPresent(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, TimeEntity.SEC_30.getSeconds());
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public void waitUntilLoadedAndTextPresentInElement(By locator, String text) {
		WebDriverWait wait = new WebDriverWait(driver, TimeEntity.SEC_30.getSeconds());
		wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
	}

	// ================================================================================
		// Start of js helper methods
		// ================================================================================
		
		public void jsClick(By byLocator){
			WebElement element = findElement(byLocator);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		}
		
		public void jsScroll(By byLocator){
			WebElement element = findElement(byLocator);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",element);
			
		}
		
		public void jsScroll(int x, int y) {
			
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy("+x+","+y+")");

		}
		
		// ================================================================================
		// End of js helper methods
		// ================================================================================
		


	// ================================================================================
	// Start of page get/set data methods
	// ================================================================================

	public String getPageTitle() {
		return driver.getTitle();
	}

	public String getPageUrl() {
		return driver.getCurrentUrl();
	}

	// ================================================================================
	// End of page get/set data methods
	// ================================================================================

	// =========================================================================================
	// End Of wait methods
	// ==========================================================================================

	// ==========================================================================================
	// Exceptions and error messages to be logged
	// ==========================================================================================
	protected void throwNullPointerExeptionForNullDriver() {
		throw new NullPointerException(
				"The Driver object you are using is null.  Please make sure you are passing the correct driver instance into the PageObject.");
	}

	/**
	 * @param byLocator
	 */
	protected void logErrorForNotFindingElement(By byLocator) {
		logger.error("Could not find element based on locator: " + byLocator.toString());
	}

	/**
	 * @param element
	 */
	protected void logErrorForNotFindingElement(WebElement element) {
		logger.error("Could not find element based on locator: " + element.toString());
	}

	protected void logErrorForNotFindingElement() {
		logger.error("Element is null and can not be perform an action");
	}
	// ===============================================================================================
	// End of exceptions and error messages to be logged
	// ===============================================================================================

}
