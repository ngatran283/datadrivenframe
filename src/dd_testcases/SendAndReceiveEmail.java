package dd_testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import dd_core.TestCore;
import dd_utils.CommonUtil;
import pageObject.EmailHomepage;
import pageObject.EmailViewPage;
import pageObject.SignInPage;

public class SendAndReceiveEmail extends TestCore {
	EmailHomepage homepage;

	@BeforeTest
	public void isSkip() {
		if (!CommonUtil.isExcutable("SendAndReceiveEmail")) {
			throw new SkipException("Skipping the test as the Runmode is No");
		}
	}

	@BeforeMethod()
	public void precondition() throws IOException {
		init();

	}

	@Test(dataProvider = "getData")
	public void sendEmail(Hashtable<String, String> data) {
		ap_log.debug("test sending Email");
		// login
		SignInPage signInPage = TestCore.gotoLoginPage();
		signInPage.fillInUsername(data.get("Username"));
		signInPage.clickNextUser();
		signInPage.fillInPassword(data.get("Password"));
		homepage = signInPage.clickNextPass();
		// send email
		homepage.createEmail();
		homepage.inputReceiver(data.get("Receiver"));
		homepage.inputSubject(data.get("Subject"));
		//homepage.inputBody(data.get("Body"));
		homepage.clickSendEmail();
		// Check receiving email
		EmailViewPage emailViewPage = homepage.openNewestEmail();
		String actual = emailViewPage.getEmailTitle();
		Assert.assertEquals(actual, data.get("Subject"), "send email successfully");
	}

	@Test(dataProvider = "getData")
	public void sendEmailWithAttachment(Hashtable<String, String> data) throws FindFailed {
		ap_log.debug("test sending email with attachment");
		// login
		SignInPage signInPage = TestCore.gotoLoginPage();
		signInPage.fillInUsername(data.get("Username"));
		signInPage.clickNextUser();
		signInPage.fillInPassword(data.get("Password"));
		homepage = signInPage.clickNextPass();
		// send email
		homepage.createEmail();
		homepage.inputReceiver(data.get("Receiver"));
		homepage.inputSubject(data.get("Subject"));
		//homepage.inputBody(data.get("Body"));
		try {
			homepage.attachFile(data.get("Attach"));
		} catch (InterruptedException e) {
			e.printStackTrace();
			ap_log.debug("Attach File not found");
		}
		homepage.clickSendEmail();
		// Check receiving email
		EmailViewPage emailViewPage = homepage.openNewestEmail();
		String actual = emailViewPage.getEmailTitle();
		Assert.assertEquals(actual, data.get("Subject"), "send email successfully");
	}

	@Test(dataProvider = "getData")
	public void saveDraftEmailAutomatically(Hashtable<String, String> data) throws InterruptedException {
		ap_log.debug("save draft email automatically");
		// login
		SignInPage signInPage = TestCore.gotoLoginPage();
		signInPage.fillInUsername(data.get("Username"));
		signInPage.clickNextUser();
		signInPage.fillInPassword(data.get("Password"));
		homepage = signInPage.clickNextPass();
		// send email
		homepage.createEmail();
		homepage.inputReceiver(data.get("Receiver"));
		homepage.inputSubject(data.get("Subject"));
		//homepage.inputBody(data.get("Body"));
		homepage.closeEmail();
		// Check auto save
		EmailViewPage emailViewPage = homepage.openNewestDraft();
		String actual = emailViewPage.getEmailTitle();
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
