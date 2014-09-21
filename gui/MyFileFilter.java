package gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class MyFileFilter extends FileFilter
{
	@Override
	public boolean accept(File file)
	{
		boolean is_acceptable = false;
		String fileName = file.getName();
		
		if (fileName.endsWith(".c") || fileName.endsWith(".cpp") || fileName.endsWith(".java"))
		{
			is_acceptable = true;
		}
		else if (file.isDirectory())
		{
			is_acceptable = true;
		}
		return is_acceptable;
	}

	@Override
	public String getDescription()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
