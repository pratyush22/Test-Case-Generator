package gui;

import java.awt.Color;

import javax.swing.*;
import java.io.File;
import java.awt.event.*;

public class ProgressWindow extends JFrame implements ActionListener, WindowListener
{
	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;
	private JButton stopButton;
	private JLabel infoLabel, messageLabel;
	private JPanel containerPanel;
	private ParameterSettingWindow parameterSettingWindow;
	
	private int mode;
	File savingDirectory;
	File inputProgram;
	File outputProgram;
	
	tcg.Generator generator;
	
	public ProgressWindow(ParameterSettingWindow parameterSettingWindow)
	{
		this.parameterSettingWindow = parameterSettingWindow;
		this.mode = parameterSettingWindow.getMode();
		
		savingDirectory = new File(this.parameterSettingWindow.getSaveDirectoryPath());
		inputProgram = new File(this.parameterSettingWindow.getInputPath());
		outputProgram = new File(this.parameterSettingWindow.getOutputPath());
		
		this.generator = new tcg.Generator(this.mode, this.savingDirectory, this.inputProgram, this.outputProgram);
		this.generator.setNumberOfTestCases(this.parameterSettingWindow.getNumberOfTestCases());
		
		initializeComponent();
		makeGUI();
		
		generator.setProgressBarAndLabel(progressBar, infoLabel);
		generator.start();
	}
	
	/**
	 * Method to initialize the GUI properties of this class
	 */
	private void initializeComponent()
	{
		// Initialize container Panel
		containerPanel = new JPanel();
		
		// Initialize progress bar
		progressBar = new JProgressBar(0, 100);
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(false);
		
		// Initialize stop button
		stopButton = new JButton("Stop");
		stopButton.addActionListener(this);
		
		// Initialize label
		infoLabel = new JLabel("Please wait...");
		messageLabel = new JLabel("Please wait while test cases are being generated");
	}
	
	/**
	 * Method to place GUI components in the the frame at their
	 * proper place.
	 */
	private void makeGUI()
	{
		// Set frame properties
		this.setTitle("Generation");
		this.setSize(400, 150);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// ----------------------
		// Design container panel
		// ----------------------
		SpringLayout layout = new SpringLayout();
		containerPanel.setLayout(layout);
		containerPanel.setBackground(Color.white);
		
		// messageLabel
		layout.putConstraint(SpringLayout.WEST, messageLabel, 5, SpringLayout.WEST, containerPanel);
		layout.putConstraint(SpringLayout.NORTH, messageLabel, 5, SpringLayout.NORTH, containerPanel);
		
		// progressBar
		layout.putConstraint(SpringLayout.WEST, progressBar, 5, SpringLayout.WEST, containerPanel);
		layout.putConstraint(SpringLayout.NORTH, progressBar, 5, SpringLayout.SOUTH, messageLabel);
		
		// stopButton
		layout.putConstraint(SpringLayout.WEST, stopButton, 5, SpringLayout.EAST, progressBar);
		layout.putConstraint(SpringLayout.NORTH, stopButton, 5, SpringLayout.SOUTH, messageLabel);
		
		// infoLabel
		layout.putConstraint(SpringLayout.WEST, infoLabel, 5, SpringLayout.WEST, containerPanel);
		layout.putConstraint(SpringLayout.NORTH, infoLabel, 5, SpringLayout.SOUTH, stopButton);
		
		containerPanel.add(messageLabel);
		containerPanel.add(progressBar);
		containerPanel.add(stopButton);
		containerPanel.add(infoLabel);
		
		// Add components to frame
		this.getContentPane().add(containerPanel);
		
		// Show frame
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == stopButton)
		{
			stopGenerator();
		}
	}
	
	private void stopGenerator()
	{
		generator.stop();
		progressBar.setIndeterminate(false);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		stopGenerator();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
