
package libraries;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
	
	//To add log messages
	public void info(String logMessge) {
		Results.updateLog(currentDateTime()+" INFO: "+logMessge);
		Results.updateLog("\r\n");
	}
	
	//To add error message
	public void error(String logMessge) {
		Results.updateLog(currentDateTime()+" ERROR: "+logMessge);
		Results.updateLog("\r\n");
	}
	
	//To add warning message
	public void warn(String logMessge) {
		Results.updateLog(currentDateTime()+" WARN: "+logMessge);
		Results.updateLog("\r\n");
	}
	
	//To get current date and time
	public String currentDateTime() {
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		return dtf.format(ldt);
	}
	
	//To log execution start message
	public void executionStart() {
		info("XXXXXXXXXXXXXXXXXXXXXXX  Execution Started  XXXXXXXXXXXXXXXXXXXXXX");
	}
	
	//To log test case start message
	public void startTestCase(String testCaseName) {
		info("XXXXXXXXXXXXXXXXXXXX  Executing: "+testCaseName+" XXXXXXXXXXXXXXXXXXXXX");
	}
	
	//To log test case end message
	public void endTestCase(String testCaseName) {
		info("XXXXXXXXXXXXXXXXXX    Executed: "+testCaseName+"  XXXXXXXXXXXXXXXXXXXXX");
	}
	

}
