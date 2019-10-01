package com.globalLogic.discovery;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shivanand sunagad
 *
 */
public class DriverInitializer {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private static DriverInitializer driverInitializerInsatance = null;

	private DriverInitializer() {
	}

	/** The webdriver object */
	protected WebDriver driver;

	public WebDriver getDriver() {
		return driver;

	}

	/**
	 * @return
	 */
	public static DriverInitializer getdriverInitializerInstatnce() {

		if (driverInitializerInsatance == null) {
			driverInitializerInsatance = new DriverInitializer();
		}
		return driverInitializerInsatance;
	}

	/**
	 * @param browserType
	 * @param location
	 * @return
	 */
	public WebDriver getAppropriateDriver(String browserType, String location) {
		TimeManager.waitInSeconds(5);
		if (browserType.equalsIgnoreCase("firefox")
				&& (System.getProperty("os.name").toLowerCase().indexOf("windows") > -1)) {
			System.setProperty("webdriver.gecko.driver", location + "/geckodriver.exe");
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("acceptInsecureCerts", true);
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"application/octet-stream;application/csv;text/csv;application/vnd.ms-excel;application/zip;application/x-zip;application/x-zip-compressed;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;");
			profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("browser.download.folderList", 1);
			caps.setCapability(FirefoxDriver.PROFILE, profile);
			driver = new FirefoxDriver(caps);
		}

		else if (browserType.equalsIgnoreCase("chrome")
				&& (System.getProperty("os.name").toLowerCase().indexOf("windows") > -1)) {
			TimeManager.waitInSeconds(5);
			System.setProperty("webdriver.chrome.driver", location + "/chromedriver.exe");
			driver = new ChromeDriver();

		} 
		if (!browserType.equalsIgnoreCase("firefox")) {
			driver.manage().window().maximize();
		}
		driver.manage().deleteAllCookies();
		logger.debug("is the browser session id null " + ((RemoteWebDriver) driver).getSessionId().toString());
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
		return driver;
	}

	// launch browser stack driver
	/**
	 * @param browserType
	 * @return
	 */
	public WebDriver getAppropriateBrowserstackDriver(String browserType) {
		TimeManager.waitInSeconds(5);
		if (browserType.equalsIgnoreCase("bschrome")
				&& (System.getProperty("os.name").toLowerCase().indexOf("mac") > -1)) {
			TimeManager.waitInSeconds(5);
			// set chrome browser options
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-plugins");
			options.setCapability("os", "windows");
			options.setCapability("os_version", "8");
			options.setCapability("resolution", "1024x768");
			options.setCapability("browser", "chrome");
			options.setCapability("browser_version", 60);
			// Launch remote webdriver in browserstack
			String username = System.getenv("BROWSERSTACK_USERNAME");

			if (username == null) {
				username = "********";
			}

			String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
			if (accessKey == null) {
				accessKey = "***********";
			}

			try {
				driver = new RemoteWebDriver(
						new URL("http://" + username + ":" + accessKey + "@hub.browserstack.com/wd/hub"), options);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		}
		return driver;

	}

	// log as URL is triggered successfully
	/**
	 * @param URL
	 */
	public void triggerUrl(String URL) {
		if (driver != null) {
			try {
				driver.get(URL);
				System.out.println("Trigger URL:" + URL + " " + "is loaded successfully");
				logger.debug("Trigger URL:" + URL + " " + "is loaded successfully");
			} catch (Exception e) {
				System.out.println("Trigger URL:" + URL + " " + "load failed");
				logger.debug("Trigger URL:" + URL + " " + "load failed");
				e.printStackTrace();
			}
		}

	}

	public void closeAllBrowsers() {
		if (driver != null) {
			try {
				driver.quit();
				logger.debug("Close Browser : Closing the browser");
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

	/**
	 * @param cookie
	 */
	public void deleteCookie(Cookie cookie) {
		if (driver != null) {
			driver.manage().deleteCookie(cookie);
		} else {

		}
	}

	public void deleteAllCookies() {

		if (driver != null) {
			driver.manage().deleteAllCookies();
		} else {

		}
	}

}
