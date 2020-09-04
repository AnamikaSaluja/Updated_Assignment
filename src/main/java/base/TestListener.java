package base;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import util.ExtentTestManager;


public class TestListener extends BasePage implements ITestListener {

	private static Logger logger = Logger.getLogger(TestListener.class);
	static ThreadLocal<Integer> flag = new ThreadLocal<Integer>();


	@Override
	public void onTestStart(ITestResult result) {
		logger.info("Starting new testcase :" + result.getMethod().getMethodName());
		ExtentTestManager.startTest(result.getMethod().getMethodName());
		if (result.getMethod().getDescription() != null) {
			String summary = result.getMethod().getDescription().replaceAll("\n", "<BR>");
			ExtentTestManager.getTest().log(Status.INFO, "Testcase Summary : <BR>" + summary);
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		logger.info("Test successfully passed : " + result.getMethod().getMethodName() + result.getTestClass());
		ExtentTestManager.getTest().log(Status.PASS,
				MarkupHelper.createLabel(result.getName() + " PASSED.", ExtentColor.GREEN));
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ExtentTestManager.getTest().log(Status.FAIL,
				MarkupHelper.createLabel(result.getName() + " FAILED ", ExtentColor.RED));
		ExtentTestManager.getTest().fail(result.getThrowable());
		logger.error("Test failed : " + result.getName());
		StringWriter sw = new StringWriter();
		result.getThrowable().printStackTrace(new PrintWriter(sw));
		logger.error(sw.toString());
		sw = null;
	}

	/*
	 * Function Description - This function will Listen to Test Skipping call and
	 * will be executed after any TestCase is skipped.
	 */

	@Override
	public void onTestSkipped(ITestResult result) {
			if (result.getMethod().getDescription() != null) {
				String summary = result.getMethod().getDescription().replaceAll("\n", "<BR>");
				ExtentTestManager.getTest().log(Status.INFO, "Testcase Summary : <BR>" + summary);
			}
			logger.info("Test skipped : " + result.getName() + result.getTestClass());
			ExtentTestManager.getTest().log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " SKIPPED ", ExtentColor.ORANGE));

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {

	}

	@Override
	public void onFinish(ITestContext context) {
		Iterator<ITestResult> skippedTestCases = context.getSkippedTests().getAllResults().iterator();
		while (skippedTestCases.hasNext()) {
			ITestResult skippedTestCase = skippedTestCases.next();
			ITestNGMethod method = skippedTestCase.getMethod();
			if (context.getSkippedTests().getResults(method).size() > 0) {
				skippedTestCases.remove();
			}
		}
	}

}
