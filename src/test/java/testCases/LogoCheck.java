package testCases;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BasePage;
import pages.SearchCategory;
import util.ExtentTestManager;

public class LogoCheck extends BasePage {
	final static Logger log = Logger.getLogger(LogoCheck.class);
	SearchCategory sc;
	
	@BeforeClass
	public void initialize() {
		sc = new SearchCategory(driver);
	}

	@Test
	public void testCase_CategorySelection() {
		sc.launch(readingPropertiesFile.getProperty("url"));
		sc.CategoryIcon();
		ExtentTestManager.getTest().log(Status.INFO, "Category Icon Check");
		this.log.info("Category Icon Check");
	}

}
