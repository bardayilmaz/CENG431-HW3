package view;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.UserModel;

import javax.swing.JPanel;

public class LoginView extends JFrame implements Observer {

	 private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel errorMessageLabel;

    public LoginView(UserModel model) {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        model.addObserver(this);
        
        // Create UI components
        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        errorMessageLabel = new JLabel("");
        errorMessageLabel.setForeground(Color.RED);

        // Create layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 5, 5);
        panel.add(usernameLabel, constraints);
        constraints.gridy = 1;
        panel.add(passwordLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(usernameField, constraints);
        constraints.gridy = 1;
        panel.add(passwordField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(loginButton, constraints);
        constraints.gridy = 3;
        panel.add(errorMessageLabel, constraints);

        // Set the panel as the content pane
        setContentPane(panel);
    }

    public void showErrorMessage(String message) {
        errorMessageLabel.setText(message);
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }
    
    public void update(Observable o, Object arg) {
        // Check if the observable is the UserModel
        if (o instanceof UserModel) {
            // Update the view based on the new data
            usernameField.setText(((UserModel) o).getUsername());
            passwordField.setText(((UserModel) o).getPassword());
        }
    }

	public JLabel getUsernameLabel() {
		return usernameLabel;
	}

	public JTextField getUsernameField() {
		return usernameField;
	}

	public JLabel getPasswordLabel() {
		return passwordLabel;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public JButton getLoginButton() {
		return loginButton;
	}

	public JLabel getErrorMessageLabel() {
		return errorMessageLabel;
	}
    
    
}
