package tcg;

import java.io.*;
import java.util.Date;

public class Logger
{
	public static void writeLog(String errorMessage, File saveDirectory)
	{
		File file = new File(saveDirectory.getAbsolutePath() + "/error.log");
		
		try
		{
			PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			printWriter.println(new Date() + "\t" + errorMessage);
			printWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
