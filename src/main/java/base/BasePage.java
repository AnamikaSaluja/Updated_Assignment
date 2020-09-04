package base;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import keywords.SeleniumKeywords;
import util.ExtentManager;
import util.ExtentTestManager;
import util.PropertyFileReading;
import util.Utilities;

public class BasePage {

	public WebDriver driver;
	public static Actions action = null;
	String bType = null;
	public Utilities util = new Utilities();;
	public static PropertyFileReading readingPropertiesFile = new PropertyFileReading();
	public static Properties testcaseProperties;
	private static String dir = System.getProperty("user.dir");

	final static Logger log = Logger.getLogger(BasePage.class);
	public static String reportFolder;

	public BasePage() {

	}

	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@BeforeSuite
	public void beforeSuite() throws IOException {
		log.info("Into Before Suite - Hook");
		ExtentManager.reportFolder = dir + "/reports/" + (Utilities.getCurrentDateTime().replaceAll("/", "-").replaceAll(":", "-"));
		ExtentManager.getReporter();
		testcaseProperties = Utilities.readProperties(dir + "/src/test/resources/testCases.properties");
	}

	@BeforeTest(alwaysRun = true)
	@Parameters("browserName")
	public void openBrowser(String browserName) throws Exception {

		try {
			log.info("Into before Test Hook ");
			if (browserName.equalsIgnoreCase("Mozilla")) {
				System.setProperty("webdriver.gecko.driver", dir + PropertyFileReading.getProperty("geckoDriverExe"));
				driver = new FirefoxDriver();
			} else if (browserName.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver", dir + PropertyFileReading.getProperty("chomeDriverExe"));
				driver = new ChromeDriver();
			} else if (browserName.equalsIgnoreCase("IE")) {
				driver = new InternetExplorerDriver();
			}
			driver.manage().timeouts().implicitlyWait(PropertyFileReading.getImplicitlyWait(), TimeUnit.SECONDS);
			driver.manage().window().maximize();
			log.info(browserName + " Browser launched successfully..");
		} catch (Exception e) {
			log.info("Error while launching Browser...");
			log.error(e.getStackTrace());
		}
	}

	@BeforeMethod
	public void init(Method results) {
		if (testcaseProperties.getProperty(results.getName()).equalsIgnoreCase("no")) {
			throw new SkipException("Testcase marked as 'No' in property File");
		}
		
		log.info("Starting the testcase " + results.getName());
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			try {
				String path = new SeleniumKeywords().getScreenshot(result.getName() + "_FailedScreenShot", driver);
				ExtentTestManager.getTest().addScreenCaptureFromPath(path);
			} catch (IOException e) {
				log.info("Failed to upload Screenshot in extent report");
			}
		} 
	}

	@AfterTest
	public void quitDriver() {
		log.info("Closing brower...");
		driver.quit();
	}

	@AfterSuite
	public void tearDown() {
		try {
			ExtentManager.printResults();
		} catch (Exception e) {
			log.error("Error while printing report at desired location.");
			throw e;
		}
	}

}
