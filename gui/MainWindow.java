package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JPanel containerPanel, optionPanel;
	private JRadioButton inputButton, outputButton, bothButton;
	private ButtonGroup buttonGroup;
	private JButton nextButton;
	
	public MainWindow()
	{
		initializeComponent();
		makeGUI();
	}
	
	/**
	 * This method initializes all the class properties with the required values.
	 */
	private void initializeComponent()
	{
		// Initializes panels
		containerPanel = new JPanel();
		optionPanel = new JPanel();
		
		// Initialize radio buttons
		inputButton = new JRadioButton("Generate Input");
		inputButton.setToolTipText("Check to create only input files.");
		inputButton.setSelected(true);
		outputButton = new JRadioButton("Generate Output");
		outputButton.setToolTipText("Check to create only output files.");
		bothButton = new JRadioButton("Genereate Both");
		bothButton.setToolTipText("Check to create both input and output files.");
		
		// Initialize button
		nextButton = new JButton("Next");
		nextButton.addActionListener(this);
		
		// Initialize button group
		buttonGroup = new ButtonGroup();
		buttonGroup.add(inputButton);
		buttonGroup.add(outputButton);
		buttonGroup.add(bothButton);
	}
	
	/**
	 * This method will arrange the components in correct manner.
	 */
	private void makeGUI()
	{
		// --------------------
		// Set Frame Properties
		// --------------------
		this.setTitle("Test Case Generator");
		this.setLocation(200, 200);
		this.setSize(400, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// -------------
		// Design Panels
		// -------------
		containerPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		containerPanel.add(optionPanel, c);
		containerPanel.setBackground(Color.white);
		
		optionPanel.setLayout(new GridLayout(4, 1));
		optionPanel.add(inputButton);
		optionPanel.add(outputButton);
		optionPanel.add(bothButton);
		optionPanel.add(nextButton);
		optionPanel.setBackground(Color.white);
		
		// --------------------
		// Design radio buttons
		// --------------------
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
		inputButton.setFont(font);
		outputButton.setFont(font);
		bothButton.setFont(font);
		
		// -------------
		// Design button
		// -------------
		nextButton.setFont(font);
		
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
		if (e.getSource() == this.nextButton)
		{
			int mode = 0;
			
			if (inputButton.isSelected())
				mode = tcg.GenerationMode.INPUT;
			else if (outputButton.isSelected())
				mode = tcg.GenerationMode.OUTPUT;
			else if (bothButton.isSelected())
				mode = tcg.GenerationMode.BOTH;
			
			this.setVisible(false);
			new ParameterSettingWindow(mode, this);
		}
	}
}
