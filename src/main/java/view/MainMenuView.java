package view;

import model.UserModel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

public class MainMenuView extends JFrame implements Observer {
    private JLabel usernameLabel;
    private JList<String> listView;

    public MainMenuView(UserModel model) {
        setTitle("MainMenu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
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


        // Create the JList and JScrollPane
        listView = createListView();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.WEST;
        JScrollPane scrollPane = new JScrollPane(listView);
        panel.add(scrollPane, constraints);

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

    private JList<String> createListView() {
        // Create a list of items
        ArrayList<UserModel> items = new ArrayList<>();

        // Create a DefaultListModel and add the items to it
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (UserModel item : items) {
            listModel.addElement(item.getUsername());
        }

        // Create the JList and set its model
        return new JList<String>(listModel);
    }

    public JList<String> getListView() {
        return listView;
    }

    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

}
