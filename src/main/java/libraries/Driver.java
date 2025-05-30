package libraries;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;



public class Driver {
	 
	 public static String testDataFile;
	 public static String batchName;
	 public static String runId;
	 public static Log log = new Log();
	 public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	 public static ThreadLocal<String> testCase = new ThreadLocal<>();
	 public static ThreadLocal<String> logFileName = new ThreadLocal<>();
	 public static ThreadLocal<Integer> waitTime = new ThreadLocal<>();
	 public static ThreadLocal<String> application = new ThreadLocal<>();
	 public static ThreadLocal<Boolean> executionContinue = new ThreadLocal<>();
	 
	 
	 //To setup test data file and extent report
	 @Parameters("Batch Name")
	 @BeforeSuite
	 public void setup(@Optional("Default") String batch) {
	     System.out.println("Starting Test Execution... ");
	     System.out.println("Batch Name:  "+batch);
	     testDataFile = batch;
	     System.out.println(testDataFile);
	     String timestamp = new SimpleDateFormat("--yy-MM-dd-HH-mm-ss").format(new Date());
	     batchName = batch+timestamp;	     
	     // Initialize HTML report
	     Results.initializeReport();
	 }  
	 
	 //Starting point of execution
	 @Test(dataProvider = "dataProvider")
	 public void testRunner(String testCase) {
         String data[] = testCase.split("\\{");
         String testCaseName, method;
	     testCaseName = data[0].split("@")[0].trim();
	     method = data[0].split("@")[1].replace(":","").trim().replace(" ", "_");
         
	     Map<String, String> localTestData = new Hashtable<>();
	     String datainsidetest[] = data[1].replace("{", "").replace("}", "").split(",");
	 
	     for (String item : datainsidetest) {
	         String key = item.split("=")[0].trim();
	         String value = item.split("=")[1].trim();
	         localTestData.put(key, value);
	     }
	     logFileName.set(batchName+"--"+Thread.currentThread().getId());
	     Results.createLog();

	     boolean isTestPassed = false;
	     String errorMessage = "";
	     
	     
	     try {
	    	 System.out.println("Starting test case: " + testCaseName);
	    	 
	    	 log.startTestCase(testCaseName);
	    	 executionContinue.set(true);
	    	 Driver.testCase.set(testCaseName);
	    	 log.info(method+" : "+localTestData.toString());
	         Class<?> testClass = Class.forName("testCases." + method);
	         Object testInstance = testClass.getDeclaredConstructor().newInstance();
	         testClass.getMethod(method, Map.class).invoke(testInstance, localTestData);
	         if(executionContinue.get()) {
	         isTestPassed = true;
	         
	         }
	         log.endTestCase(testCaseName);
	     
	     } catch (Exception e1) {
	    	 e1.printStackTrace();
	    	 log.error(e1.getCause().getMessage());
	     }

	     //Log test execution in the HTML report
	     Results.logTestResult(testCaseName, isTestPassed, localTestData.toString(), errorMessage);
	     
	 }

	 @AfterSuite
	 public void tearDown() {
	     System.out.println("Test Execution Completed.");	     
	     //Generate final report
	     Results.generateFinalReport();
	 }	
	 
	 
	
//Data provider method to read test data from excel file
//This method reads the test data from the excel file and returns it as an array of objects
//Each object in the array represents a test case with its corresponding data
//The method is parallelized to allow multiple test cases to run simultaneously
@DataProvider(name="dataProvider", parallel=true)
public Object[][] dataDriven() throws IOException{
	
	
    XSSFWorkbook book;
    Object tests[][]=null;
    File file = new File("./Testdata/"+testDataFile+".xlsx");
	if(file.exists()) {
	try {
		
		book = new XSSFWorkbook(file);
		
	
    XSSFSheet sheet = book.getSheet("Sheet1");
    
    List<String> records = new ArrayList<String>();
    
    for(int row=1; row<=sheet.getLastRowNum(); row++) {
    	String testCase = sheet.getRow(row).getCell(0).toString();
    	String method = sheet.getRow(row).getCell(1).toString();
    	Map<String, String> dataRecordWise = new HashMap<>();
    	for(int column=2; column<sheet.getRow(0).getLastCellNum(); column++) {    		
    		String header = sheet.getRow(0).getCell(column).toString();
        	
    		if(header.toLowerCase().contains("key")) {
    			try {
    				String key = sheet.getRow(row).getCell(column).toString();
    			
    			if(!key.equals("")) {
    				String header2 = sheet.getRow(0).getCell(column+1).toString();
    				if(header2.toLowerCase().contains("value")) {
    					
    			String value = sheet.getRow(row).getCell(column+1).toString();
    			
    			     dataRecordWise.put(key, value);
    				}
    			}
}catch(NullPointerException e) {
    				
    			}
    		}    		
    	}
    	records.add(testCase+"@"+method+" : "+dataRecordWise.toString()); 
    }
    tests = new Object[records.size()][1];
    for(int i=0; i<records.size(); i++) {
	    
	    try {
		tests[i][0] = records.get(i);
	    }catch(ArrayIndexOutOfBoundsException e) {
	    	System.out.println(e.getCause().getMessage());
	    }
	}
	} catch (InvalidFormatException e1) {
		System.out.println(e1.getCause().getMessage());
	} catch (Exception e) {
		e.printStackTrace();
	}
	}else {
		System.out.println(testDataFile+" : Testdata file not available");
		throw new IOException("Testdata file not available");
	}
	
	return tests;
}

}
