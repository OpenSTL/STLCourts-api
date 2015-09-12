package svc.logging;

public class LogSystem {
	
	public static void LogEvent(String message)
	{
		System.out.println(message);
	}
	
	public static void LogDBException(Exception e)
	{
		System.out.println("Database Exception: " + e.getMessage());
	}

}
