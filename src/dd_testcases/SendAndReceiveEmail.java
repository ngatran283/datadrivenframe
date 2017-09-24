package dd_testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.By;
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

		driver.findElement(By.xpath(object.getProperty("username"))).sendKeys(data.get("Username"));
		driver.findElement(By.xpath(object.getProperty("nextUser"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object.getProperty("password"))));
		driver.findElement(By.xpath(object.getProperty("password"))).sendKeys(data.get("Password"));
		driver.findElement(By.xpath(object.getProperty("nextPass"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(object.getProperty("inbox"))));
		driver.findElement(By.xpath(object.getProperty("compose"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object.getProperty("receiver"))));
		driver.findElement(By.xpath(object.getProperty("receiver"))).sendKeys(data.get("Receiver"));
		driver.findElement(By.cssSelector(object.getProperty("subject"))).click();
		driver.findElement(By.cssSelector(object.getProperty("subject"))).clear();
		driver.findElement(By.cssSelector(object.getProperty("subject"))).sendKeys(data.get("Subject"));
		driver.findElement(By.xpath(object.getProperty("body"))).click();
		driver.findElement(By.xpath(object.getProperty("body"))).clear();
		driver.findElement(By.xpath(object.getProperty("body"))).sendKeys(data.get("Body"));
		driver.findElement(By.xpath(object.getProperty("sendBttn"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(object.getProperty("inbox1"))));
		driver.findElement(By.linkText(object.getProperty("inbox1"))).click();
		driver.findElement(By.xpath(object.getProperty("newEmailPos"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(object.getProperty("subject-title"))));
		String actual = driver.findElement(By.cssSelector(object.getProperty("subject-title"))).getText();
		Assert.assertEquals(actual, data.get("Subject"), "send email successfully");

	}

	@AfterMethod
	public void closeBrowser() {
		driver.close();
		driver=null;
	}

	@DataProvider
	public static Object[][] getData() {
		return CommonUtil.getData("SendAndReceiveEmail");
	}

}
