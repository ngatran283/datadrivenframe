package dd_testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import dd_core.TestCore;
import dd_utils.CommonUtil;

public class LoginTest extends TestCore {

	@BeforeTest
	public void isSkip() {
		if (!CommonUtil.isExcutable("LoginTest")) {
			throw new SkipException("Skipping the test as the Runmode is No");
		}
	}
	
	@BeforeMethod
	public void openBrowser() throws IOException {
		init();
	}

	@Test(dataProvider = "getData")
	public void doLogin(Hashtable<String, String> table) {
		driver.findElement(By.xpath(object.getProperty("username"))).sendKeys(table.get("Username"));
		driver.findElement(By.xpath(object.getProperty("nextUser"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object.getProperty("password"))));
		driver.findElement(By.xpath(object.getProperty("password"))).sendKeys(table.get("Password"));
		driver.findElement(By.xpath(object.getProperty("nextPass"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object.getProperty("inbox"))));

	}
	
	@AfterMethod
	public void closeBrowser() {
		driver.close();
		driver=null;
	}

	@DataProvider
	public static Object[][] getData() {
		return CommonUtil.getData("LoginTest");
	}
}
