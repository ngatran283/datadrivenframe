package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.sikuli.script.FindFailed;

import dd_core.TestCore;

public class EmailHomepage extends TestCore {

	public void createEmail() {
		click(findElement("compose"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findElement("receiver")));
		ap_log.debug("open new email");
	}

	public void inputReceiver(String to) {
		type(findElement("receiver"), to);
		ap_log.debug("input in receiver");
	}

	public void inputSubject(String subject) {
		// TODO Auto-generated method stub
		type(findElement("subject"), subject);
		ap_log.debug("input in subject");
	}

	public void inputBody(String body) {
		// TODO Auto-generated method stub
		type(findElement("body"), body);
		ap_log.debug("input in email body");

	}

	public void clickSendEmail() {
		// TODO Auto-generated method stub
		int currentNo = getCurrentEmailNo("inbox");
		ap_log.debug("get current Email Number");
		click(findElement("sendBttn"));
		ap_log.debug("send email");
		int expect = currentNo + 1;
		wait.until(ExpectedConditions.textToBe(By.partialLinkText(object.getProperty("inbox.partialLinkText")),
				"Inbox (" + expect + ")"));
		ap_log.debug("waiting for sending email successfully");

	}

	public EmailViewPage openNewestEmail() {
		// TODO Auto-generated method stub
		click(findElement("inbox"));
		click(findElement("newEmailPos"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findElement("subject-title")));
		ap_log.debug("open recent email");
		return PageFactory.initElements(driver, EmailViewPage.class);
	}

	public void attachFile(String path) throws InterruptedException, FindFailed {
		click(findElement("attach"));
		TestCore.implicitWait(driver);
		TestCore.selectFile(path);
		ap_log.debug("attach file");
	}

	public void closeEmail() throws InterruptedException {
		// TODO Auto-generated method stub

		click(findElement("closeIcon"));
		ap_log.debug("close email");

	}

	public EmailViewPage openNewestDraft() {
		// TODO Auto-generated method stub
		click(findElement("draft"));
		click(findElement("newEmailPos"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findElement("subject-title")));
		ap_log.debug("open recent draft email");
		return PageFactory.initElements(driver, EmailViewPage.class);
	}

	public int getCurrentEmailNo(String object) {
		String currentText = driver.findElement(findElement(object)).getText();
		int currentNo;
		if (currentText == "Inbox") {
			currentNo = 0;
		} else {
			currentNo = Integer.valueOf(currentText.substring(currentText.indexOf("(") + 1, currentText.indexOf(")")));
		}
		return currentNo;
	}

}
