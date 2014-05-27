/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Simulators 
 * 20/04/2014
 * 
 */
package asgn2Simulators;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements Runnable {

	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public GUISimulator(String arg0) throws HeadlessException {
		super(arg0);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JLabel MaxCarSpacesLbl = new JLabel("Maximum Car Spaces");
		JLabel MaxSmallCarSpacesLbl = new JLabel("Maximum Small Car Spaces");
		JLabel MaxMotorCycleSpacesLbl = new JLabel("Maximum MotorCycle Spaces");
		JLabel MaxQueueLbl = new JLabel("Maximum Queue Size");
		
		final JTextField MaxCarSpaces = new JTextField("", 5);
		JTextField MaxSmallCarSpaces = new JTextField("", 5);
		JTextField MaxMotorCycleSpaces = new JTextField("", 5);
		JTextField MaxQueueSpaces = new JTextField("", 5);
		
		JButton startBtn = new JButton("Start");
		
        JTabbedPane pane = new JTabbedPane();
        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("Tab 1"));
        JPanel panel2 = new JPanel(new GridLayout(14,2));
        panel2.add(MaxCarSpacesLbl);
        panel2.add(MaxCarSpaces);
        panel2.add(MaxSmallCarSpacesLbl);
        panel2.add(MaxSmallCarSpaces);
        panel2.add(MaxMotorCycleSpacesLbl);
        panel2.add(MaxMotorCycleSpaces);
        panel2.add(MaxQueueLbl);
        panel2.add(MaxQueueSpaces);
        panel2.add(startBtn);
        
        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               redPanel.setBackground(Color.RED);
            }
         });        

        pane.add("Chart View", panel1);
        pane.add("Text View", panel2);
        getContentPane().add(pane);

        // Display the window.
        setPreferredSize(new Dimension(600, 600));
        setLocation(new Point(100, 100));
        pack();
        setVisible(true);
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 /** 
		 * Create the GUI and show it. 
		 */ 
		 
		 JFrame.setDefaultLookAndFeelDecorated(true); 
		 new GUISimulator("Car Park Simulator");
		 
	}

}
