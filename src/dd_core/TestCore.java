package dd_core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import dd_utils.ExcelReader;
import pageObject.SignInPage;

public class TestCore {

	private static final long WAIT_TIME_OUT = 20;
	public static WebDriver driver = null;
	public static Logger ap_log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\dd_properties\\testdata.xlsx");
	public static Properties object = new Properties();
	public static Properties config = new Properties();

	public static Wait<WebDriver> wait;

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
		wait = new FluentWait<WebDriver>(driver).withTimeout(WAIT_TIME_OUT, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(StaleElementReferenceException.class);
	}

	public static void type(By by, String value) {
		WebElement element = driver.findElement(by);
		element.click();
		element.clear();
		element.sendKeys(value);
	}

	public static void click(By by) {
		driver.findElement(by).click();
	}

	public String getTextElement(By by) {
		return driver.findElement(by).getText();
	}

	public By findElement(String key) {
		By by = null;
		if (object.containsKey(key + ".id")) {
			by = By.id(object.getProperty(key + ".id"));
		} else if (object.containsKey(key + ".xpath")) {
			by = By.xpath(object.getProperty(key + ".xpath"));
		} else if (object.containsKey(key + ".cssSelector")) {
			by = By.cssSelector(object.getProperty(key + ".cssSelector"));
		} else if (object.containsKey(key + ".className")) {
			by = By.className(object.getProperty(key + ".className"));
		} else if (object.containsKey(key + ".linkText")) {
			by = By.linkText(object.getProperty(key + ".linkText"));
		} else if (object.containsKey(key + ".partialLinkText")) {
			by = By.partialLinkText(object.getProperty(key + ".partialLinkText"));
		} else if (object.containsKey(key + ".tagName")) {
			by = By.tagName(object.getProperty(key + ".tagName"));
		} else if (object.containsKey(key + ".name")) {
			by = By.name(object.getProperty(key + ".name"));
		} else {
			ap_log.debug("Could not identify this type of locator");
		}
		return by;
	}

	public static SignInPage gotoLoginPage() {
		driver.get(config.getProperty("testsiteurl"));
		driver.manage().timeouts().implicitlyWait(WAIT_TIME_OUT, TimeUnit.SECONDS);
		return PageFactory.initElements(driver, SignInPage.class);
	}

	public static void selectFile(String path) throws FindFailed {
		// TODO Auto-generated method stub
		Screen screen = new Screen();
		Pattern img_loc = new Pattern(System.getProperty("user.dir") + "\\src\\images\\loc.png");
		Pattern img_open = new Pattern(System.getProperty("user.dir") + "\\src\\images\\open.png");
		screen.wait(img_loc, 10);
		screen.type(img_loc, path);
		screen.click(img_open);

	}

	public static void implicitWait(WebDriver driver) {
		// TODO Auto-generated method stub
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME_OUT);
	}
}
