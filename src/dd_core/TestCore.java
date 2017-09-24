package dd_core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dd_utils.ExcelReader;

public class TestCore {

	private static final long WAIT_TIME_OUT = 20;
	public static WebDriver driver = null;
	public static Logger ap_log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\dd_properties\\testdata.xlsx");
	public static Properties object = new Properties();
	public static Properties config = new Properties();
	public static WebElement element = null;
	public static WebDriverWait wait;

	public static void init() throws IOException {
		if (driver == null) {
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\dd_properties\\OR.properties");
			object.load(fis);
			fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\dd_properties\\Config.properties");
			config.load(fis);

			if (config.getProperty("browser").equals("firefox")) {
				System.setProperty("webdriver.gecko.driver", "D:\\software\\selenium-java-3.0.1\\geckodriver.exe");
				driver = new FirefoxDriver();
			} else if (config.getProperty("browser").equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", "D:\\software\\selenium-java-3.0.1\\chromedriver.exe");
				driver = new ChromeDriver();
			} else if (config.getProperty("browser").equals("iexplorer")) {
				System.setProperty("webdriver.ie.driver", "D:\\software\\selenium-java-3.0.1\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
		}

		driver.get(config.getProperty("testsiteurl"));
		driver.manage().timeouts().implicitlyWait(WAIT_TIME_OUT, TimeUnit.SECONDS);

	}

	public static void type(By by, String value) {
		element = driver.findElement(by);
		element.click();
		element.clear();
		element.sendKeys(value);
	}

	public static void click(By by) {
		element = driver.findElement(by);
		element.click();
	}

	public static void waitForVisibleOf(By by) {
		wait = new WebDriverWait(driver, WAIT_TIME_OUT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public String getTextElement(By by) {
		element = driver.findElement(by);
		return element.getText();
	}

	public WebElement findElement(String key) {
		if (object.containsKey(key + ".id")) {
			element = driver.findElement(By.id(object.getProperty(key + ".id")));
		} else if (object.containsKey(key + ".xpath")) {
			element = driver.findElement(By.xpath(object.getProperty(key + ".xpath")));
		} else if (object.containsKey(key + ".cssSelector")) {
			element = driver.findElement(By.cssSelector(object.getProperty(key + ".cssSelector")));
		} else if (object.containsKey(key + ".className")) {
			element = driver.findElement(By.className(object.getProperty(key + ".className")));
		} else if (object.containsKey(key + ".linkText")) {
			element = driver.findElement(By.linkText(object.getProperty(key + ".linkText")));
		} else if (object.containsKey(key + ".partialLinkText")) {
			element = driver.findElement(By.partialLinkText(object.getProperty(key + ".partialLinkText")));
		} else if (object.containsKey(key + ".tagName")) {
			element = driver.findElement(By.tagName(object.getProperty(key + ".tagName")));
		} else if (object.containsKey(key + ".name")) {
			element = driver.findElement(By.name(object.getProperty(key + ".name")));
		} else {
			ap_log.debug("Could not identify this type of locator");
		}
		return element;
	}
}
