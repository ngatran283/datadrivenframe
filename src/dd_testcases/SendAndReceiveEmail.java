package dd_testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import dd_core.TestCore;

import dd_utils.CommonUtil;

public class SendAndReceiveEmail extends TestCore {
	@BeforeTest
	public void isSkip() {
		if (!CommonUtil.isExcutable("SendAndReceiveEmail")) {
			throw new SkipException("Skipping the test as the Runmode is No");
		}
	}

	@BeforeMethod
	public void openBrowser() throws IOException {
		init();
	}

	@Test(dataProvider = "getData")
	public void sendEmail(Hashtable<String, String> data) {
		// login
		type(findElement("username"), data.get("Username"));
		click(findElement("nextUser"));
		wait.until(ExpectedConditions.visibilityOf(findElement("password")));
		type(findElement("password"), data.get("Password"));
		click(findElement("nextPass"));
		wait.until(ExpectedConditions.visibilityOf(findElement("inbox")));
		// send email
		click(findElement("compose"));
		waitForVisibleOf(findElement("receiver"));
		type(findElement("receiver"), data.get("Receiver"));
		type(findElement("subject"), data.get("Subject"));
		type(findElement("body"), data.get("Body"));
		click(findElement("sendBttn"));
		wait.until(ExpectedConditions.visibilityOf(findElement("inbox1")));
		click(findElement("inbox1"));
		click(findElement("newEmailPos"));
		wait.until(ExpectedConditions.visibilityOf(findElement("subject-title")));
		String actual = getTextElement(findElement("subject-title"));
		Assert.assertEquals(actual, data.get("Subject"), "send email successfully");
	}

	@AfterMethod
	public void closeBrowser() {
		driver.close();
		driver = null;
	}

	@DataProvider
	public static Object[][] getData() {
		return CommonUtil.getData("SendAndReceiveEmail");
	}

}
