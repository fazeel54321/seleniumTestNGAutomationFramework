package libraries;

public class Assertion extends Driver {
	
	public static void assertEquals(String actual, String expected, String message) throws AssertionError{
		if(actual.equals(expected)) {
			log.info(message+" : ["+expected+"] is equal to ["+actual+"]");
		}else {
			throw new AssertionError(message+" : ["+expected+"] is not equal to ["+actual+"]");
		}
	}
	
	public static void assertEquals(String actual, String expected) throws AssertionError{
		if(actual.equals(expected)) {
			log.info("["+expected+"] is equal to ["+actual+"]");
		}else {
			throw new AssertionError("["+expected+"] is not equal to ["+actual+"]");
		}
	}
	
	public static void assertContains(String actual, String expected, String message) throws AssertionError{
		if(actual.contains(expected)){
			log.info(message+" : ["+actual+"] is contains ["+expected+"]");
		}else {
			throw new AssertionError(message+" : ["+actual+"] is not contains ["+expected+"]");
		}
	}
	public static void assertContains(String actual, String expected) throws AssertionError{
		if(actual.contains(expected)){
			log.info("["+actual+"] is contains ["+expected+"]");
		}else {
			throw new AssertionError("["+actual+"] is not contains ["+expected+"]");
		}
	}
	
	public static void assertTrue(boolean actual, String message) {
		if(actual) {
			log.info(message+" : ["+actual+"] is equals to [True]");
		} else {
			throw new AssertionError(message+" : ["+actual+"] is not equals to [True]");
		}
	}
	public static void assertTrue(boolean actual) {
		if(actual) {
			log.info("["+actual+"] is equals to [True]");
		} else {
			throw new AssertionError("["+actual+"] is not equals to [True]");
		}
	}

}
