package testCases;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BasePage;
import pages.BookPage;
import pages.HomePage;
import pages.TextBook;
import util.ExtentTestManager;

public class Searches extends BasePage {
	public static WebDriverWait wait;
	final static Logger log = Logger.getLogger(Searches.class);
	HomePage hp;
	TextBook bookSearchPage;
	BookPage bookPage;

	@BeforeClass
	public void initialize() {
		hp = new HomePage(driver);
		bookSearchPage = new TextBook(driver);
		bookPage = new BookPage(driver);
	}

	@Test
	public void testCase_Search() {
		hp.launch(readingPropertiesFile.getProperty("url"));
		hp.clickDropDown();
		hp.searchBook();
		ExtentTestManager.getTest().log(Status.INFO, "Selected books option from drop down");
		log.info("Selected books option from drop down");
		hp.EnterBookType("Selenium");
		ExtentTestManager.getTest().log(Status.INFO, "Entered selenium in text box");
		log.info("Entered selenium in text box");
		hp.searchButtonClick();
		ExtentTestManager.getTest().log(Status.INFO, "Clicked selenium book");
		log.info("Clicked selenium book");
		bookSearchPage.foundBook();
		ExtentTestManager.getTest().log(Status.INFO, "Found book selenium");
		log.info("Found book selenium");
		bookPage.VerifyTitle();
		ExtentTestManager.getTest().log(Status.INFO, "verified title");
		log.info("verified title");
		bookPage.addToCartButon();
	}

}
