package dd_testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.By;

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
		type(By.xpath(object.getProperty("username")), data.get("Username"));
		click(By.xpath(object.getProperty("nextUser")));
		waitForVisibleOf(By.xpath(object.getProperty("password")));
		type(By.xpath(object.getProperty("password")), data.get("Password"));
		click(By.xpath(object.getProperty("nextPass")));
		waitForVisibleOf(By.linkText(object.getProperty("inbox")));
		// send email
		click(By.xpath(object.getProperty("compose")));
		waitForVisibleOf(By.xpath(object.getProperty("receiver")));
		type(By.xpath(object.getProperty("receiver")), data.get("Receiver"));
		type(By.cssSelector(object.getProperty("subject")), data.get("Subject"));
		type(By.xpath(object.getProperty("body")), data.get("Body"));
		click(By.xpath(object.getProperty("sendBttn")));
		waitForVisibleOf(By.linkText(object.getProperty("inbox1")));
		click(By.linkText(object.getProperty("inbox1")));
		click(By.xpath(object.getProperty("newEmailPos")));
		waitForVisibleOf(By.cssSelector(object.getProperty("subject-title")));
		String actual = getTextElement(By.cssSelector(object.getProperty("subject-title")));
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
