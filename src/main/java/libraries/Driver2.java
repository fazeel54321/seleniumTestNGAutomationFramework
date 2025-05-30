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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import libraries.Log;
import libraries.Results;

import com.beust.jcommander.Parameter;

import io.github.bonigarcia.wdm.WebDriverManager;



public class Driver2 {
	 
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
//	@Parameters("batchName")
//    @Test
    public static void main(String args[]) throws IOException, InvalidFormatException {
    	String batch="Sauce";
	     System.out.println("Starting Test Execution...");
	     testDataFile = batch;
	     System.out.println(batch);
	     String timestamp = new SimpleDateFormat("--yy-MM-dd-HH-mm-ss").format(new Date());
	     batchName = batch+timestamp;	     
	     // Initialize HTML report
	     Results.initializeReport();
	     System.out.println("REsults initiated");
   XSSFWorkbook book;
   File file = new File("./Testdata/"+testDataFile+".xlsx");
		System.out.println("file available");
		book = new XSSFWorkbook(file);
	
	
   XSSFSheet sheet = book.getSheet("Sheet1");
   
   List<String> records = new ArrayList<String>();
   int threads = sheet.getLastRowNum();
   
   for(int row=1; row<=sheet.getLastRowNum(); row++) {
   	String testCase = sheet.getRow(row).getCell(0).toString();
   	String method = sheet.getRow(row).getCell(1).toString();
   	Map<String, String> dataRecordWise = new HashMap<>();
   	for(int column=2; column<sheet.getRow(0).getLastCellNum(); column++) {    		
   		String header = sheet.getRow(0).getCell(column).toString();
       	
   		if(header.toLowerCase().contains("key")) {
   			String key = sheet.getRow(row).getCell(column).toString();
   			if(!key.equals("")) {
   				String header2 = sheet.getRow(0).getCell(column+1).toString();
   				if(header2.toLowerCase().contains("value")) {
   			String value = sheet.getRow(row).getCell(column+1).toString();
   			     dataRecordWise.put(key, value);
   				}
   			}
   		}    		
   	}
   	records.add(testCase+"@"+method+" : "+dataRecordWise.toString()); 
   }
   
	ExecutorService executor = Executors.newFixedThreadPool(2);
		System.out.println("under 2");
		
		for (int i=1; i < records.size(); i++) {
			int task = i;
		    executor.submit(() -> {
		    try {
		    	
		        	String testCase = records.get(task);
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
		   	     

		   	     boolean isTestPassed = false;
		   	     String errorMessage = "";
		   	     
		   	     
		   	     try {
		   	    	 System.out.println("Starting test case: " + testCaseName);
		   	    	 logFileName.set(testCaseName+"--"+timestamp+"--"+Thread.currentThread().getId());
			   	     System.out.println(logFileName.get());
			   	     Results.createLog();
			   	     System.out.println("Log created");
		   	    	 log.startTestCase(testCaseName);
		   	    	 executionContinue.set(true);
		   	    	 Driver.testCase.set(testCaseName);
		   	    	 log.info(method+" : "+localTestData.toString());
		   	         Class<?> testClass = Class.forName("testCases." + method);
		   	         Object testInstance = testClass.getDeclaredConstructor().newInstance();
		   	         testClass.getMethod(method, Map.class).invoke(testInstance, localTestData);
		   	         if(executionContinue.get()) {
		   	         isTestPassed = true;
		   	         log.endTestCase(testCaseName);
		   	         }
		   	     
		   	     } catch (Exception e1) {
		   	    	 e1.printStackTrace();
		   	    	 log.error(e1.getCause().getMessage());
		   	     }
		   	     //Log test execution in the HTML report
		   	     System.out.println("Boolean: "+isTestPassed);
		   	  Results.logTestResult(testCaseName, isTestPassed, localTestData.toString(), errorMessage);
		       } catch (Exception e) {
		            e.printStackTrace();
		       }
		    });
		}
	executor.shutdown();
	try {
        if (!executor.awaitTermination(5, TimeUnit.MINUTES)) {
            System.out.println("Timeout! Not all tasks finished.");
        }
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

	}
	
	
}
