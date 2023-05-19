package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.MainMenuController;

public class MainMenuView extends JFrame {

	 private MainMenuController controller;
	    private JButton button1;
	    private JButton button2;
	    private JButton button3;
	    private JButton button4;
	    private JButton button5;
	    
	public MainMenuView(MainMenuController controller) {
		 this.controller = controller;

	        setTitle("Main Menu");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(400, 300);
	        setLocationRelativeTo(null);

	        initializeComponents();
	}
	
	 private void initializeComponents() {
	        button1 = new JButton("View Papers to Download & Your Reading Lists");
	        button2 = new JButton("View Researchers");
	        button3 = new JButton("View All Reading Lists");
	        button5 = new JButton("Create Reading List");
	        button4 = new JButton("Exit");

	        button1.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                controller.handlePaperListButton();
	            }
	        });

	        button2.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                controller.handleResearcherListButton();
	            }
	        });

	        button3.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                controller.handleAllReadingListsButton();
	            }
	        });

	        button4.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                controller.handleExitButton();
	            }
	        });
	        
	        button5.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                controller.handleCreateReadingListButton();
	            }
	        });

	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(4, 1));
	        panel.add(button1);
	        panel.add(button2);
	        panel.add(button3);
	        panel.add(button5);
	        panel.add(button4);

	        getContentPane().add(panel);
	    }

}
