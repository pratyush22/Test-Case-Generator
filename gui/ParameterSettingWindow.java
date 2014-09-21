package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ParameterSettingWindow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JFileChooser saveDirectoryChooser, inputProgramChooser, outputProgramChooser;
	private JSpinner spinner;
	private SpinnerNumberModel spinnerModel;
	private JPanel containerPanel;
	private JButton generateButton, cancelButton, saveBrowse, inputBrowse, outputBrowse;
	private JLabel saveDirectoryLabel, testCaseCountLabel, inputLabel, outputLabel;
	private JTextField saveDirectoryTextField, inputTextField, outputTextField;
	
	// Control parameters
	private int mode;
	private MainWindow mainWindow;
	
	public ParameterSettingWindow(int mode, MainWindow mainWindow)
	{
		this.mode = mode;
		this.mainWindow = mainWindow;
		initializeComponent();
		makeGUI();
	}
	
	/**
	 * This method initializes all the class properties.
	 */
	private void initializeComponent()
	{
		// Initialize file choosers
		MyFileFilter myFileFilter = new MyFileFilter();
		saveDirectoryChooser = new JFileChooser();
		saveDirectoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		inputProgramChooser = new JFileChooser();
		inputProgramChooser.setFileFilter(myFileFilter);
		
		
		if (this.mode == tcg.GenerationMode.OUTPUT)
			inputProgramChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		outputProgramChooser = new JFileChooser();
		outputProgramChooser.setFileFilter(myFileFilter);
		
		// Initialize spinner and spinner model
		spinnerModel = new SpinnerNumberModel(10, 0, Integer.MAX_VALUE, 1);
		spinner = new JSpinner(spinnerModel);

		// Initialize panels
		containerPanel = new JPanel();
		
		// Initialize labels
		saveDirectoryLabel = new JLabel("Saving Directory:");
		testCaseCountLabel = new JLabel("Number Of Test Cases:");
		
		if (this.mode == tcg.GenerationMode.OUTPUT)
			inputLabel = new JLabel("Select Input Directory:");
		else
			inputLabel = new JLabel("Select Input Program:");
		
		outputLabel = new JLabel("Select Test Program:");
		
		// Initialize Buttons
		generateButton = new JButton("Generate");
		generateButton.addActionListener(this);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		
		saveBrowse = new JButton("Browse");
		saveBrowse.addActionListener(this);
		
		inputBrowse = new JButton("Browse");
		inputBrowse.addActionListener(this);
		
		outputBrowse = new JButton("Browse");
		outputBrowse.addActionListener(this);
		
		// Initialize text fields
		saveDirectoryTextField = new JTextField(25);
		saveDirectoryTextField.setEditable(false);
		
		inputTextField = new JTextField(25);
		inputTextField.setEditable(false);
		
		outputTextField = new JTextField(25);
		outputTextField.setEditable(false);
	}
	
	/**
	 * This method place the components in the frame.
	 */
	private void makeGUI()
	{
		// --------------------
		// Set Frame properties
		// --------------------
		this.setTitle("Set Parameters");
		this.setSize(500, 400);
		this.setLocation(200, 200);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// -------------
		// Design labels
		// -------------
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
		saveDirectoryLabel.setFont(font);
		testCaseCountLabel.setFont(font);
		inputLabel.setFont(font);
		outputLabel.setFont(font);
		
		// --------------
		// Design buttons
		// --------------
		font = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
		saveBrowse.setFont(font);
		inputBrowse.setFont(font);
		outputBrowse.setFont(font);
		generateButton.setFont(font);
		cancelButton.setFont(font);
		
		// ----------------------
		// Design container Panel
		// ----------------------
		SpringLayout layout = new SpringLayout();
		
		// Save directory label
		layout.putConstraint(SpringLayout.WEST, saveDirectoryLabel, 5, SpringLayout.WEST, containerPanel);
		layout.putConstraint(SpringLayout.NORTH, saveDirectoryLabel, 5, SpringLayout.NORTH, containerPanel);
		
		// save directory text field
		layout.putConstraint(SpringLayout.WEST, saveDirectoryTextField, 5, SpringLayout.WEST, containerPanel);
		layout.putConstraint(SpringLayout.NORTH, saveDirectoryTextField, 5, SpringLayout.SOUTH, saveDirectoryLabel);
		
		// Save browse
		layout.putConstraint(SpringLayout.WEST, saveBrowse, 5, SpringLayout.EAST, saveDirectoryTextField);
		layout.putConstraint(SpringLayout.NORTH, saveBrowse, 5, SpringLayout.SOUTH, saveDirectoryLabel);
		
		// test case count label
		layout.putConstraint(SpringLayout.WEST, testCaseCountLabel, 5, SpringLayout.WEST, containerPanel);
		layout.putConstraint(SpringLayout.NORTH, testCaseCountLabel, 5, SpringLayout.SOUTH, saveBrowse);
		
		// Spinner
		layout.putConstraint(SpringLayout.WEST, spinner, 5, SpringLayout.WEST, containerPanel);
		layout.putConstraint(SpringLayout.NORTH, spinner, 5, SpringLayout.SOUTH, testCaseCountLabel);
		
		// Input Label
		layout.putConstraint(SpringLayout.WEST, inputLabel, 5, SpringLayout.WEST, containerPanel);
		layout.putConstraint(SpringLayout.NORTH, inputLabel, 20, SpringLayout.SOUTH, spinner);
		
		// Input text field
		layout.putConstraint(SpringLayout.WEST, inputTextField, 5, SpringLayout.WEST, containerPanel);
		layout.putConstraint(SpringLayout.NORTH, inputTextField, 5, SpringLayout.SOUTH, inputLabel);
		
		// Input browse button
		layout.putConstraint(SpringLayout.WEST, inputBrowse, 5, SpringLayout.EAST, inputTextField);
		layout.putConstraint(SpringLayout.NORTH, inputBrowse, 5, SpringLayout.SOUTH, inputLabel);
		
		// Output Label
		layout.putConstraint(SpringLayout.WEST, outputLabel, 5, SpringLayout.WEST, containerPanel);
		layout.putConstraint(SpringLayout.NORTH, outputLabel, 20, SpringLayout.SOUTH, inputBrowse);
		
		// Output text field
		layout.putConstraint(SpringLayout.WEST, outputTextField, 5, SpringLayout.WEST, containerPanel);
		layout.putConstraint(SpringLayout.NORTH, outputTextField, 5, SpringLayout.SOUTH, outputLabel);
		
		// Output Browse
		layout.putConstraint(SpringLayout.WEST, outputBrowse, 5, SpringLayout.EAST, outputTextField);
		layout.putConstraint(SpringLayout.NORTH, outputBrowse, 5, SpringLayout.SOUTH, outputLabel);
		
		//Generate button
		layout.putConstraint(SpringLayout.WEST, generateButton, 5, SpringLayout.WEST, containerPanel);
		layout.putConstraint(SpringLayout.NORTH, generateButton, 20, SpringLayout.SOUTH, outputBrowse);
		
		// Cancel button
		layout.putConstraint(SpringLayout.WEST, cancelButton, 5, SpringLayout.EAST, generateButton);
		layout.putConstraint(SpringLayout.NORTH, cancelButton, 20, SpringLayout.SOUTH, outputBrowse);
		
		containerPanel.setLayout(layout);
		containerPanel.setBackground(Color.white);
		containerPanel.add(saveDirectoryLabel);
		containerPanel.add(saveDirectoryTextField);
		containerPanel.add(saveBrowse);
		containerPanel.add(testCaseCountLabel);
		containerPanel.add(spinner);
		containerPanel.add(inputLabel);
		containerPanel.add(inputTextField);
		containerPanel.add(inputBrowse);
		containerPanel.add(outputLabel);
		containerPanel.add(outputTextField);
		containerPanel.add(outputBrowse);
		containerPanel.add(generateButton);
		containerPanel.add(cancelButton);
		
		// if mode is input
		if (mode == tcg.GenerationMode.INPUT)
		{
			outputLabel.setVisible(false);
			outputTextField.setVisible(false);
			outputBrowse.setVisible(false);
		}
		
		// Add components to frame
		this.add(containerPanel);
		
		// Make frame visible
		this.setVisible(true);
	}
	
	/**
	 * Action Listener
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == saveBrowse)
		{
			// Get status of file choosing option
			int status = saveDirectoryChooser.showOpenDialog(this);
			
			// If status is approved
			if (status == JFileChooser.APPROVE_OPTION)
			{
				saveDirectoryTextField.setText(saveDirectoryChooser.getSelectedFile().getAbsolutePath());
			}
		}
		else if (e.getSource() == inputBrowse)
		{
			int status = inputProgramChooser.showOpenDialog(this);
			
			if (status == JFileChooser.APPROVE_OPTION)
			{
				inputTextField.setText(inputProgramChooser.getSelectedFile().getAbsolutePath());
			}
		}
		else if (e.getSource() == outputBrowse)
		{
			int status = outputProgramChooser.showOpenDialog(this);
			
			if (status == JFileChooser.APPROVE_OPTION)
			{
				outputTextField.setText(outputProgramChooser.getSelectedFile().getAbsolutePath());
			}
		}
		else if (e.getSource() == generateButton)
		{
			if (verify())
			{
				new ProgressWindow(this);
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Fill out the details to generate test cases.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (e.getSource() == cancelButton)
		{
			this.dispose();
			mainWindow.setVisible(true);
		}
	}
	
	/**
	 * Method to validate inputs filled by the user
	 * @return boolean	validation status
	 */
	private boolean verify()
	{
		boolean isValid = false;
		
		boolean isSaveDirectoryEmpty = saveDirectoryTextField.getText().isEmpty();
		boolean isInputProgramEmpty = inputTextField.getText().isEmpty();
		boolean isOutputProgramEmpty = outputTextField.getText().isEmpty();
		
		if (!isSaveDirectoryEmpty && !isInputProgramEmpty)
		{
			isValid = true;
		}
		
		if (mode != tcg.GenerationMode.INPUT && isOutputProgramEmpty)
		{
			isValid = false;
		}
		
		return isValid;
	}
	
	/**
	 * Method which return the path of the saving directory.
	 * @return String	Path where the output of the application will be saved
	 */
	public String getSaveDirectoryPath()
	{
		return this.saveDirectoryTextField.getText();
	}
	
	/**
	 * Method which return the path of the input program/directory.
	 * @return String	Path from where the input will be taken for the application.
	 */
	public String getInputPath()
	{
		return this.inputTextField.getText();
	}
	
	/**
	 * Method which return the path of the output/solution program.
	 * @return String	Path of the output program which will be run against the inputs
	 * generated from the input program.
	 */
	public String getOutputPath()
	{
		return this.outputTextField.getText();
	}
	
	/**
	 * Method which returns the mode of generation.
	 * @return Mode of operation.
	 */
	public int getMode()
	{
		return this.mode;
	}
	
	/**
	 * @return int	Number of test cases
	 */
	public int getNumberOfTestCases()
	{
		return (int)spinner.getValue();
	}
}
