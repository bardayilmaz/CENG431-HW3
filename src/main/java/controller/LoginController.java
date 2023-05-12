package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.UserModel;
import view.LoginView;

public class LoginController implements ActionListener {
    private LoginView view;
    private UserModel model;

    public LoginController(LoginView view, UserModel model) {
        this.view = view;
        this.model = model;

        // Add action listener for the login button
        view.addLoginListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getLoginButton()) {
            String username = view.getUsername();
            String password = view.getPassword();

            // Check if the username and password are valid
            if (model.isValidUser(username, password)) {
                // If valid, show the main menu view
                //MainMenuView mainMenuView = new MainMenuView();
                //MainMenuController mainMenuController = new MainMenuController(mainMenuView);
                view.setVisible(false);
                //mainMenuView.setVisible(true);
                view.clearFields();
            } else {
                // If invalid, show an error message
                view.showErrorMessage("Invalid username or password");
                view.clearFields();
            }
        }
    }
}
