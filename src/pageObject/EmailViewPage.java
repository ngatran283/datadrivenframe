package pageObject;

import dd_core.TestCore;

public class EmailViewPage extends TestCore{

	public String getEmailTitle() {
		ap_log.debug("get Email Title");
		return getTextElement(findElement("subject-title"));
	
	}

}
