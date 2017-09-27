package dd_testcases;

import java.io.IOException;
import java.util.Hashtable;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import dd_core.TestCore;
import dd_utils.CommonUtil;
import pageObject.SignInPage;

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
	public void doLogin(Hashtable<String, String> data) {
		SignInPage signInPage = TestCore.gotoLoginPage();
		signInPage.fillInUsername(data.get("Username"));
		signInPage.clickNextUser();
		signInPage.fillInPassword(data.get("Password"));
		signInPage.clickNextPass();
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
