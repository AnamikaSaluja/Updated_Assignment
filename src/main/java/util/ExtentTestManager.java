package util;

import java.util.HashMap;
import java.util.Map;
import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
	
	static Map extentTestMap = new HashMap();
		
	public static synchronized ExtentTest getTest() {		
		return (ExtentTest) extentTestMap.get((int)(long)(Thread.currentThread().getId()));
	}
	
	
	public static synchronized ExtentTest startTest(String name) {
		ExtentTest test=ExtentManager.extentReports.createTest(name);
		extentTestMap.put((int)(long)(Thread.currentThread().getId()), test);
		return test;
	}
	

}
