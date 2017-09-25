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
		type(findElement("username"), table.get("Username"));
		click(findElement("nextUser"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object.getProperty("password.xpath"))));
		type(findElement("password"), table.get("Password"));
		click(findElement("nextPass"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(object.getProperty("inbox.linkText"))));

	}

	@AfterMethod
	public void closeBrowser() {
		driver.close();
		driver = null;
	}

	@DataProvider
	public static Object[][] getData() {
		return CommonUtil.getData("LoginTest");
	}
}
