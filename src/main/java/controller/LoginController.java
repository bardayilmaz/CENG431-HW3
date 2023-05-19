package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import data_access.IUserAccessor;
import model.UserModel;
import view.LoginView;
import view.MainMenuView;

public class LoginController implements ActionListener {
    private LoginView view;
    private UserModel model;
    private IUserAccessor userAccessor;

    public LoginController(LoginView view, IUserAccessor userAccessor) {
        this.view = view;
        this.userAccessor = userAccessor;
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
                view.showErrorMessage("User does not exists");
            }

        }
    }

    private boolean performLogin(String username, String password) {
    	model = userAccessor.getById(username);
        if (model.isValidUser(username, password)) {
            // If valid, show the main menu view
            MainMenuController mainMenuController = new MainMenuController(model);
            mainMenuController.setView(new MainMenuView(mainMenuController));
            this.view.setVisible(false);
            this.view.clearFields();
            mainMenuController.setViewVisible(true);
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
        return userAccessor.existsById(userName);
    }

}
