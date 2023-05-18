package view;

import controller.ReadingListsController;
import model.APaper;
import model.ReadingListModel;
import model.UserModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ReadingListsView extends JFrame implements Observer {
    private ReadingListsController controller;
    private JButton backButton;
    private  JList<UserModel> userList;
    private DefaultListModel<UserModel> userListModel;

    private JList<ReadingListModel> readingList;
    private DefaultListModel<ReadingListModel> readingListModel;

    private JList<APaper> paperList;
    private DefaultListModel<APaper> paperListModel;

    private UserModel selectedUser;
    private ReadingListModel selectedReadingList;
    private JTextArea readingListContent;

    public ReadingListsView(ReadingListsController readingListsController){
        this.controller = readingListsController;
        setTitle("Paper Viewer");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        readingListContent = new JTextArea();
        readingListContent.setEditable(false);
        JScrollPane contentScrollPane = new JScrollPane(readingListContent);
        contentScrollPane.setBorder(BorderFactory.createTitledBorder("Reading List Content"));
        contentScrollPane.setPreferredSize(new Dimension(900, contentScrollPane.getPreferredSize().height));
        add(contentScrollPane, BorderLayout.EAST);

        readingListModel = new DefaultListModel<>();
        readingList = new JList<>(readingListModel);
        JScrollPane readingListScrollPane = new JScrollPane(readingList);
        readingListScrollPane.setBorder(BorderFactory.createTitledBorder("Current Reading List"));
        add(readingListScrollPane, BorderLayout.CENTER);

        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        JScrollPane userListScrollPane = new JScrollPane(userList);
        userListScrollPane.setBorder(BorderFactory.createTitledBorder("Current User List"));
        add(userListScrollPane, BorderLayout.WEST);


        JPanel buttonPanel = new JPanel(new FlowLayout());
        backButton = new JButton("Main Menu");
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        userList.addListSelectionListener(e->{
            if(!e.getValueIsAdjusting()){
                this.selectedUser=userList.getSelectedValue();
                List<ReadingListModel> readingListModelList = controller.getReadingLists(selectedUser.getUsername());
                setAllReadingLists(readingListModelList);
            }
            else{
                selectedReadingList=null;
            }
        });

        readingList.addListSelectionListener(e->{
            if(!e.getValueIsAdjusting()){
                this.selectedReadingList = readingList.getSelectedValue();
                controller.handleReadingListSelection(selectedReadingList);
            }
        });


    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public JButton getBackButton() {
        return backButton;
    }
    public void backButtonListener(ActionListener listener) {backButton.addActionListener(listener);}

    public void setAllUsers(List<UserModel> users) {
        for (UserModel user : users) {
            this.userListModel.addElement(user);
        }
    }

    public void setAllReadingLists(List<ReadingListModel> readingLists) {
        this.readingListModel.clear();
        for (ReadingListModel readingList : readingLists) {
            this.readingListModel.addElement(readingList);
        }
    }

    public void setReadingListContent(String content) {
        readingListContent.setText(content);
    }
}
