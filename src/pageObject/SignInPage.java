package pageObject;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import dd_core.TestCore;

public class SignInPage extends TestCore {

	public void fillInUsername(String username) {
		type(findElement("username"), username);
		ap_log.debug("fill in Username");
	}

	public void clickNextUser() {
		click(findElement("nextUser"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findElement("nextPass")));
	}

	public void fillInPassword(String password) {
		type(findElement("password"), password);
		ap_log.debug("fill in password");
	}

	public EmailHomepage clickNextPass() {
		click(findElement("nextPass"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findElement("inbox")));
		ap_log.debug("login successfully");
		return PageFactory.initElements(driver, EmailHomepage.class);
	}

}
