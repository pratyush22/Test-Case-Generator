package tcg;

import java.io.*;
import javax.swing.JProgressBar;
import javax.swing.JLabel;

public class Generator implements Runnable
{
	Thread thread;
	Process process;
	ProcessBuilder processBuilder;
	File saveDirectory, inputProgram, outputProgram;
	File inputDirectory;
	
	int numberOfTestCases;
	int mode;
	boolean shouldContinue;
	
	JProgressBar progressBar;
	JLabel label;
	
	/**
	 * One argument constructor
	 * @param mode
	 */
	public Generator(int mode)
	{
		thread = new Thread(this);
		this.mode = mode;
		
		processBuilder = new ProcessBuilder();
		processBuilder.directory(saveDirectory);
	}
	
	/**
	 * Four argument constructor
	 * @param mode
	 * @param savingDirectory
	 * @param inputProgram
	 * @param outputProgram
	 */
	public Generator(int mode, File savingDirectory, File inputProgram, File outputProgram)
	{
		thread = new Thread(this);
		this.mode = mode;
		this.saveDirectory = savingDirectory;
		this.inputProgram = inputProgram;
		this.outputProgram = outputProgram;
		
		// If mode is output then set input program as the input Directory
		if (this.mode == GenerationMode.OUTPUT)
		{
			this.inputDirectory = inputProgram;
		}
		else
		{
			this.inputDirectory = new File(this.saveDirectory.getAbsolutePath() + "/Input");
		}
		
		processBuilder = new ProcessBuilder();
		processBuilder.directory(saveDirectory);
	}
	
	/**
	 * Method which sets the saving directory.
	 * @param saveDirectory
	 */
	public void setSaveDirectory(File saveDirectory)
	{
		this.saveDirectory = saveDirectory;
	}
	
	/**
	 * Method which sets the input program/directory
	 * @param inputProgram
	 */
	public void setInputProgram(File inputProgram)
	{
		if (this.mode == GenerationMode.OUTPUT)
		{
			this.inputDirectory = inputProgram;
		}
		else
		{
			this.inputDirectory = new File(this.saveDirectory.getAbsolutePath() + "/Input");
		}
	}
	
	/**
	 * Method which sets the output program
	 * @param outputProgram
	 */
	public void setOutputProgram(File outputProgram)
	{
		this.outputProgram = outputProgram;
	}
	
	/**
	 * Method to set number of test cases.
	 * @param testCases
	 */
	public void setNumberOfTestCases(int testCases)
	{
		this.numberOfTestCases = testCases;
	}
	
	/**
	 * Method to set progress bar
	 * @param progressBar
	 */
	public void setProgressBarAndLabel(JProgressBar progressBar, JLabel label)
	{
		this.progressBar = progressBar;
		this.label = label;
	}
	/**
	 * This will run on separate thread
	 */
	public void run()
	{
		boolean outputProgramCompiled = false;
		boolean inputProgramCompiled = false;
		
		// If mode is not output then compile input program
		if (mode != GenerationMode.OUTPUT)
		{
			inputProgramCompiled = compile(inputProgram);
		}
		else
		{
			inputProgramCompiled = true;
		}
		
		// if mode is not input then compile output program
		if (mode != GenerationMode.INPUT)
		{
			outputProgramCompiled = compile(outputProgram);
		}
		else
		{
			outputProgramCompiled = true;
		}
		
		// If error in compilation then return
		if (!outputProgramCompiled || !inputProgramCompiled)
		{
			return;
		}
		
		// If mode is not output then generate inputs
		if (mode != GenerationMode.OUTPUT)
		{
			label.setText("Starting generation of input files...");
			generate(inputProgram);
		}
		
		// if mode is not input then generate outputs
		if (mode != GenerationMode.INPUT)
		{
			label.setText("Starting generation of output files...");
			generate(outputProgram, inputDirectory);
		}
		
		progressBar.setIndeterminate(false);
		
		if (shouldContinue)
		{
			label.setText("Done!");
			progressBar.setValue(progressBar.getMaximum());
		}
		else
		{
			label.setText("Stopped");
			progressBar.setValue(progressBar.getMaximum() / 2);
		}
	}
	
	/**
	 * Method that will start the thread.
	 */
	public void start()
	{
		shouldContinue = true;
		thread.start();
	}
	
	/**
	 * Method to stop the thread.
	 */
	public void stop()
	{
		shouldContinue = false;
		
		if (process != null)
			process.destroy();
	}
	
	/**
	 * Method to compile the given program
	 * @param program
	 * @return boolean	whether program is successfully compiled or not
	 */
	private boolean compile(File program)
	{
		boolean isCompiled = false;
		
		// Get file type
		FileType.Type fileType = getFileType(program);
		
		// Assign compile commands
		if (fileType == FileType.Type.C)
		{
			processBuilder.command(CompileCommands.C, program.getAbsolutePath(), CompileCommands.CMath);
		}
		else if (fileType == FileType.Type.CPP)
		{
			processBuilder.command(CompileCommands.CPP, program.getAbsolutePath());
		}
		else if (fileType == FileType.Type.Java)
		{
			processBuilder.command(CompileCommands.JAVA, program.getAbsolutePath());
		}
		else
		{
			return isCompiled;
		}
		
		// Set error redirection
		processBuilder.redirectError(new File(saveDirectory.getAbsolutePath() + "/compile_execute.log"));
		
		// Start process and check status of compilation
		try
		{
			label.setText("Compiling " + program.getName());
			
			process = processBuilder.start();
			process.waitFor();
			
			if (process.exitValue() == 0)
			{
				label.setText("Compilation Successful");
				isCompiled = true;
			}
			else
			{
				label.setText("Compilation Failure");
			}
		}
		catch (IOException e)
		{
			Logger.writeLog(e.getMessage(), saveDirectory);
		}
		catch (InterruptedException e)
		{
			Logger.writeLog(e.getMessage(), saveDirectory);
		}
		catch (Exception e)
		{
			Logger.writeLog(e.getMessage(), saveDirectory);
		}
		
		return isCompiled;
	}
	
	/**
	 * Returns the file type of the program.
	 * @param program
	 * @return
	 */
	private FileType.Type getFileType(File program)
	{
		String programName = program.getName();
		
		if (programName.endsWith(".c"))
		{
			return FileType.Type.C;
		}
		else if (programName.endsWith(".cpp"))
		{
			return FileType.Type.CPP;
		}
		else if (programName.endsWith(".java"))
		{
			return FileType.Type.Java;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Method to generate input files from input program
	 * @param program
	 */
	private void generate(File program)
	{
		FileType.Type fileType = getFileType(program);
		
		if (fileType == FileType.Type.C)
		{
			processBuilder.command(RunCommands.C);
		}
		else if (fileType == FileType.Type.CPP)
		{
			processBuilder.command(RunCommands.CPP);
		}
		else if (fileType == FileType.Type.Java)
		{
			processBuilder.command(RunCommands.JAVA, program.getName().replace(".java", ""));
		}
		else
		{
			return;
		}
		
		// If input directory does not exist then create it
		if (!inputDirectory.exists())
		{
			inputDirectory.mkdir();
		}
		
		// Iterate for number of test cases times
		for (int i = 1; i <= numberOfTestCases && shouldContinue; i++)
		{
			File file = new File(inputDirectory.getAbsolutePath() + "/" + i);
			
			processBuilder.redirectOutput(file);
			processBuilder.redirectError(new File(saveDirectory.getAbsolutePath() + "/compile_execute.log"));
			
			try
			{
				label.setText("Generating " + i + " input file");
				
				process = processBuilder.start();
				process.waitFor();
				
				if (process.exitValue() != 0)
				{
					label.setText("Error in generating " + i + " input file");
					break;
				}
				else
				{
					label.setText(i + " input file generated");
				}
			}
			catch (IOException e)
			{
				Logger.writeLog(e.getMessage(), saveDirectory);
			}
			catch (InterruptedException e)
			{
				Logger.writeLog(e.getMessage(), saveDirectory);
			}
			catch (Exception e)
			{
				Logger.writeLog(e.getMessage(), saveDirectory);
			}
		}
	}
	
	/**
	 * Method to generate output files using inputs from the input directory
	 * @param program
	 * @param directory
	 */
	private void generate(File program, File directory)
	{
		FileType.Type fileType = getFileType(program);
		
		if (fileType == FileType.Type.C)
		{
			processBuilder.command(RunCommands.C);
		}
		else if (fileType == FileType.Type.CPP)
		{
			processBuilder.command(RunCommands.CPP);
		}
		else if (fileType == FileType.Type.Java)
		{
			processBuilder.command(RunCommands.JAVA, program.getName().replace(".java", ""));
		}
		else
		{
			return;
		}
		
		// Create output folder if not exist already
		File outputDir = new File(saveDirectory.getAbsolutePath() + "/Output");
		if (!outputDir.exists())
		{
			outputDir.mkdir();
		}
		
		// Get list of files in input directory
		String[] inputFiles = directory.list();
		
		// Total output files generated will be equal to the minimum of input files in the 
		// Input directory and number of test cases chosen by the user.
		int testCaseCount = (inputFiles.length < numberOfTestCases)?(inputFiles.length):numberOfTestCases;
		
		// Generate as much output files as there are in input directory
		for (int i = 1; i <= testCaseCount && shouldContinue; i++)
		{
			processBuilder.redirectInput(new File(directory.getAbsolutePath() + "/" + inputFiles[i - 1]));
			processBuilder.redirectOutput(new File(outputDir.getAbsolutePath() + "/" + inputFiles[i - 1]));
			processBuilder.redirectError(new File(saveDirectory.getAbsolutePath() + "/compile_execute.log"));
			
			try
			{
				label.setText("Generating " + i + " output file");
				
				process = processBuilder.start();
				process.waitFor();
				
				if (process.exitValue() != 0)
				{
					label.setText("Error in generating " + i + " output file");
				}
				else
				{
					label.setText(i + " output file generated");
				}
			}
			catch (IOException e)
			{
				Logger.writeLog(e.getMessage(), saveDirectory);
			}
			catch (InterruptedException e)
			{
				Logger.writeLog(e.getMessage(), saveDirectory);
			}
			catch (Exception e)
			{
				Logger.writeLog(e.getMessage(), saveDirectory);
			}
		}
	}
}
