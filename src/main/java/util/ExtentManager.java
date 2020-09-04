package util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	public static ExtentReports extentReports;
	private static ExtentHtmlReporter htmlReporter;
	public static String reportFolder;
	public static Properties prop;
	private static String dir = System.getProperty("user.dir");
	private static String extentPropertyPath = dir + "\\src\\test\\resources\\extentReport.properties";
	private static final Logger LOGGER = Logger.getLogger(ExtentManager.class);

	public synchronized static void getReporter() {
		try {
			if (extentReports == null) {			
				prop= Utilities.readProperties(extentPropertyPath);
				String reportPath =reportFolder+prop.getProperty("htmlReportName");
				extentReports = generateReport(reportPath);
			}
		} catch (Exception e) {
			LOGGER.info("Not able to generate Extent HTML Reports");
			System.exit(0);
		}
	}

	private static ExtentReports generateReport(String reportPath) throws IOException {
		ExtentReports extent;
		new File(reportFolder).mkdir();
		htmlReporter = new ExtentHtmlReporter(reportPath);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle((String) prop.getProperty("htmlReportTitle"));
		htmlReporter.config().setReportName((String) prop.getProperty("htmlReportTitle"));
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setTimeStampFormat((String) prop.getProperty("TimeStampFormat"));
		LOGGER.info("HTML report created : " + prop.getProperty("htmlReportTitle"));
		return extent;
	}
	
	public static synchronized void printResults() {
		extentReports.flush();
		LOGGER.info("HTML report saved at "+reportFolder+"/"+ExtentManager.prop.get("htmlReportName"));
	}

	

}
