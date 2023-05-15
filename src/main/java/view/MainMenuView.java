package view;

import model.UserModel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;


public class MainMenuView extends JFrame implements Observer {
    private JLabel usernameLabel;

    public MainMenuView(UserModel model) {
        setTitle("MainMenu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        model.addObserver(this);

        // Set up the panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.weighty=1.0;
        constraints.anchor = GridBagConstraints.NORTHEAST;
        constraints.insets = new Insets(5, 5, 5, 5);

        // Create the username label
        usernameLabel = new JLabel(model.getUsername());
        panel.add(usernameLabel, constraints);

        // Set the panel as the content pane
        setContentPane(panel);
    }

    @Override
    public void update(Observable o, Object arg) {
        // Update the username label with the new value
        if (arg instanceof String) {
            String username = (String) arg;
            usernameLabel.setText("Username: " + username);
        }
    }


    // TODO LIST
}
