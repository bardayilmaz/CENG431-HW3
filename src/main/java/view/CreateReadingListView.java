package view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import controller.CreateReadingListController;

public class CreateReadingListView extends JFrame {

	private JTextField listNameTextField;
    private JButton submitButton;
    private JButton backButton;
    private CreateReadingListController controller;
	
	public CreateReadingListView(CreateReadingListController controller) { // Set up the frame
        this.controller = controller;
		setTitle("Reading List Input");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new FlowLayout());

        // Create the text input field
        listNameTextField = new JTextField(20);
        add(listNameTextField);

        // Create the submit button
        submitButton = new JButton("Submit");
        backButton = new JButton("Back");
        add(submitButton);
        add(backButton);
	}
	
	public void addSubmitReadingListListener(ActionListener listener) {
		submitButton.addActionListener(listener);
	}
	
	public void addBackListener(ActionListener listener) {
		backButton.addActionListener(listener);
	}

	public JButton getSubmitButton() {
		return submitButton;
	}

	public void setSubmitButton(JButton submitButton) {
		this.submitButton = submitButton;
	}

	public JTextField getListNameTextField() {
		return listNameTextField;
	}

	public void setListNameTextField(JTextField listNameTextField) {
		this.listNameTextField = listNameTextField;
	}

	public JButton getBackButton() {
		return backButton;
	}

	public void setBackButton(JButton backButton) {
		this.backButton = backButton;
	}

}
