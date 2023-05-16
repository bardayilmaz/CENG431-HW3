package view;

import model.UserModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

public class MainMenuView extends JFrame implements Observer {
    private JLabel usernameLabel;
    private UserModel userModel;
    private JList<String> followingView;
    private JList<String> followerView;

    public MainMenuView(UserModel model) {
        setTitle("MainMenu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        userModel = model;
        model.addObserver(this);

        // Set up the panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Create the username label
        usernameLabel = new JLabel(model.getUsername());
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHEAST; // Align to top-right corner
        constraints.insets = new Insets(10, 10, 0, 10); // Add some padding
        panel.add(usernameLabel, constraints);
        constraints.weighty = 1;


        // Create the JList and JScrollPane
        followingView = createListView(userModel.getFollowingUsers());
        JLabel followingsTitle = new JLabel("Followings");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weighty = 0;
        panel.add(followingsTitle,constraints);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        JScrollPane scrollPane = new JScrollPane(followingView);
        panel.add(scrollPane, constraints);


        // Create the JList and JScrollPane
        followerView = createListView(userModel.getFollowerUsers());
        JLabel follewersTitle = new JLabel("Followers");
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.weighty = 0;
        panel.add(follewersTitle,constraints);
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        JScrollPane scrollPane2 = new JScrollPane(followerView);
        panel.add(scrollPane2, constraints);
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

    private JList<String> createListView(List<String> listString) {
        // Create a DefaultListModel and add the items to it
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String user : listString ) {
            listModel.addElement(user);
        }
        // Create the JList and set its model
        return new JList<String>(listModel);
    }


    public JList<String> getFollowingView() {
        return followingView;
    }
    public JList<String> getFollowerView() {
        return followerView;
    }

    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

}
