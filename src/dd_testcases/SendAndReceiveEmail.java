package dd_testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
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

	@Test(dataProvider = "getData")
	public void sendEmail(String Username, String Password, String Receiver, String Subject, String Body) {
		driver.findElement(By.xpath(object.getProperty("username"))).sendKeys(Username);
		driver.findElement(By.xpath(object.getProperty("nextUser"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object.getProperty("password"))));
		driver.findElement(By.xpath(object.getProperty("password"))).sendKeys(Password);
		driver.findElement(By.xpath(object.getProperty("nextPass"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object.getProperty("inbox"))));
		driver.findElement(By.xpath(object.getProperty("compose"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object.getProperty("receiver"))));
		driver.findElement(By.xpath(object.getProperty("receiver"))).sendKeys(Receiver);
		driver.findElement(By.cssSelector(object.getProperty("subject"))).sendKeys(Subject);
		driver.findElement(By.xpath(object.getProperty("emailbody"))).sendKeys(Body);
		driver.findElement(By.xpath(object.getProperty("sendBttn"))).click();
		driver.findElement(By.xpath(object.getProperty("inbox")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(object.getProperty("inbox1"))));
		driver.findElement(By.xpath(object.getProperty("newEmailPos"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(object.getProperty("subject-title"))));
		String actual = driver.findElement(By.cssSelector(object.getProperty("subject-title"))).getText();
		Assert.assertEquals(actual, Subject, "send email successfully");

	}

	@DataProvider
	public static Object[][] getData() {
		return CommonUtil.getData("SendAndReceiveEmail");
	}
}
