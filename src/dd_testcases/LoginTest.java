package dd_testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
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

	@Test(dataProvider = "getData")
	public void doLogin(String Username, String Password) {
		driver.findElement(By.xpath(object.getProperty("username"))).sendKeys(Username);
		driver.findElement(By.xpath(object.getProperty("nextUser"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object.getProperty("password"))));
		driver.findElement(By.xpath(object.getProperty("password"))).sendKeys(Password);
		driver.findElement(By.xpath(object.getProperty("nextPass"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object.getProperty("inbox"))));

	}

	@DataProvider
	public static Object[][] getData() {
		return CommonUtil.getData("LoginTest");
	}
}
