package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import data_access.IUserAccessor;
import model.UserModel;
import view.LoginView;
import view.MainMenuView;

import javax.swing.*;

public class LoginController implements ActionListener {
    private LoginView view;
    private UserModel model;
    private IUserAccessor userAccessor;

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

            if (isUserInDB(username, userAccessor)) {
                // Check if the username and password are valid
                performLogin(username, password);
            } else {
                view.showErrorMessage("The user is not signed up");
            }

        }
    }

    private boolean performLogin(String username, String password) {

        if (model.isValidUser(username, password)) {
            // If valid, show the main menu view
            MainMenuView mainMenuView = new MainMenuView(model);
            MainMenuController mainMenuController = new MainMenuController(mainMenuView, model);
            view.setVisible(false);
            mainMenuView.setVisible(true);
            view.clearFields();
            return true;
        } else {
            // If invalid, show an error message
            view.showErrorMessage("Invalid username or password");
            view.clearFields();
            return false;
        }
    }

    public void setUserAccessor(IUserAccessor userAccessor) {
        this.userAccessor = userAccessor;
    }

    private boolean isUserInDB(String userName, IUserAccessor userAccessor) {
        UserModel user = userAccessor.getById(userName);
        if (user != null) {
            model = new UserModel(user);
            return true;
        }
        return false;
    }

}
