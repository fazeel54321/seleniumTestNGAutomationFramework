package libraries;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Results extends Driver{
	private static final String REPORT_PATH = System.getProperty("user.dir") + "/Results/Reports/";
    private static final String REPORT_FILE = REPORT_PATH +batchName+".html";
    private static final String SCREENSHOT_PATH = System.getProperty("user.dir") + "/Results/Screenshots/";
    private static final String LOG_PATH = System.getProperty("user.dir")+"/Results/Logs/";
    private static StringBuilder reportContent = new StringBuilder();

    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;
	
	//To Create log file
	public static void createLog() {
		File logFile = new File(LOG_PATH+logFileName.get()+".log");
		if(!logFile.exists()) {
			try {
				logFile.createNewFile();
				log.executionStart();
			} catch (IOException e) {
				System.out.println(e.getCause().getMessage());
			}
			
		}
	}
	
	//To update log file
	public static void updateLog(String logMessage) {
		try {
		File logFile = new File(LOG_PATH+logFileName.get()+".log");
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(logFile.getAbsoluteFile(), true), StandardCharsets.UTF_8);
		PrintWriter pw = new PrintWriter(osw);
		
		pw.println(logMessage);
		
		pw.close();
		
		}catch(IOException e) {
			System.out.println(e.getCause().getMessage());
		}
	}
	
	public static void initializeReport() {
        File reportDir = new File(REPORT_PATH);
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }

        reportContent.append("<html><head><title>Test Execution Report</title>");
        reportContent.append("<style>");
        reportContent.append("body {font-family: Arial, sans-serif; background-color: #f4f4f4;}");
        reportContent.append(".container {width: 90%; margin: 20px auto; background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);}");
        reportContent.append("h2 {text-align: center;}");
        reportContent.append("table {width: 100%; border-collapse: collapse; margin-bottom: 20px;}");
        reportContent.append("th, td {padding: 12px; text-align: center; border: 1px solid #ddd;}");
        reportContent.append("th {background-color: #007bff; color: white;}");
        reportContent.append(".pass {color: green; font-weight: bold;}");
        reportContent.append(".fail {color: red; font-weight: bold;}");
        reportContent.append("tr:nth-child(even) {background-color: #f9f9f9;}");
        reportContent.append(".summary-table th {background-color: #343a40; color: white;}");
        reportContent.append("</style>");
        reportContent.append("</head><body>");
        reportContent.append("<div class='container'>");

        // Summary Table
        reportContent.append("<h2>Test Execution Report</h2>");
        reportContent.append("<table class='summary-table'><tr><th>Total Tests</th><th>Passed</th><th>Failed</th></tr>");
        reportContent.append("<tr><td id='totalTests'>0</td><td id='passedTests' class='pass'>0</td><td id='failedTests' class='fail'>0</td></tr></table>");

        // Detailed Test Results Table with Test Data
        reportContent.append("<table><tr><th>Test Case</th><th>Status</th><th>Timestamp</th><th>Test Data</th><th>Screenshot</th><th>Logs</th></tr>");
    }

    //Log Test Case Result with Test Data
    public static void logTestResult(String testCaseName, boolean isPassed, String testData, String errorMessage) {
        totalTests++;
        if (isPassed) {
            passedTests++;
        } else {
            failedTests++;
        }

        String status = isPassed ? "<span class='pass'>Pass</span>" : "<span class='fail'>Fail</span>";
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logFile = LOG_PATH+logFileName.get()+".log"; 
        String screenshotPath = "";

        if ((!isPassed && driver.get() != null) || !executionContinue.get()) {
            screenshotPath = takeScreenshot(testCaseName);
        }

        reportContent.append("<tr>");
        reportContent.append("<td>").append(testCaseName).append("</td>");
        reportContent.append("<td>").append(status).append("</td>");
        reportContent.append("<td>").append(timestamp).append("</td>");
        reportContent.append("<td>").append(testData).append("</td>"); // ✅ Added Test Data Column
        if (!screenshotPath.isEmpty()) {
            reportContent.append("<td><a href='").append(SCREENSHOT_PATH+screenshotPath).append("' target='_blank'>View Screenshot</a></td>");
        } else {
            reportContent.append("<td>N/A</td>");
        }
        reportContent.append("<td><a href='").append(logFile).append("' target='_blank'>View Logs</a></td>");
        reportContent.append("</tr>");
   	    Browser.quit();
    }

    //Capture Screenshot for Failed Test Cases
    public  static String takeScreenshot(String testCaseName) {
        try {
            String screenshotFileName="";
            File screenshotFile=null;
            for(int i=0; i<100; i++) {
            String screenshotFileName1 = testCaseName+logFileName.get()+"--"+i+ ".png";
            screenshotFileName = screenshotFileName1.replace(":", "-");
            	screenshotFile = new File(SCREENSHOT_PATH+screenshotFileName);
            	if(!screenshotFile.exists()) {
                  break;
                }
            }
 
            byte[] screenshotBytes = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.BYTES);
            FileOutputStream fos = new FileOutputStream(screenshotFile);
            fos.write(screenshotBytes);
            fos.close();
            return screenshotFileName;
        } catch (Exception e) {
            log.error(Results.getStackMsg(e));
            return "";
        }
    }

    //Finalize and Save Report (Update Summary Values)
    public static void generateFinalReport() {
        reportContent.append("</table>");
        
        // Update summary table values
        String updatedSummary = "<script>document.getElementById('totalTests').innerText = '" + totalTests + "';" +
                "document.getElementById('passedTests').innerText = '" + passedTests + "';" +
                "document.getElementById('failedTests').innerText = '" + failedTests + "';</script>";

        reportContent.append(updatedSummary);
        reportContent.append("</div>");
        reportContent.append("</body></html>");

        try (FileWriter writer = new FileWriter(REPORT_FILE)) {
            writer.write(reportContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Custom Report Generated: " + REPORT_FILE);
    }
    
    public static String getStackMsg(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String sStackTrace = sw.toString();
		return sStackTrace;
	}

}
