package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.UserController;
import model.UserModel;

public class UserView extends JFrame implements Observer{

	  private JList<String> allUsersList;
	    private JList<String> followingUsersList;
	    private JList<String> followerUsersList;
	    private JButton followButton;
	    private JButton unfollowButton;
	    private UserController userController;

	    public UserView(UserController userController) {
	    	this.userController = userController;
	        initializeComponents();
	        createLayout();
	        setPreferredSize(new Dimension(800, 600));
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        pack();
	        setLocationRelativeTo(null);
	        setVisible(true);
	    }

	    private void initializeComponents() {
	        allUsersList = new JList<>();
	        followingUsersList = new JList<>();
	        followerUsersList = new JList<>();
	        followButton = new JButton("Follow");
	        unfollowButton = new JButton("Unfollow");
	    }

	    private void createLayout() {
	        JPanel allUsers = new JPanel(new BorderLayout());
	        allUsers.add(new JScrollPane(allUsersList), BorderLayout.CENTER);
	        allUsers.add(followButton, BorderLayout.SOUTH);
	        
	        JPanel headerPanel = new JPanel();
	        headerPanel.setBackground(Color.lightGray);
	        headerPanel.setPreferredSize(new Dimension(getWidth(), 30));
	        JLabel headerLabel = new JLabel("All Users");
	        headerPanel.add(headerLabel);

	        JPanel leftPanel = new JPanel(new BorderLayout());
	        leftPanel.add(headerPanel, BorderLayout.NORTH);
	        leftPanel.add(allUsers, BorderLayout.CENTER);
	        
	        JPanel followingUsersPanel = new JPanel(new BorderLayout());
	        followingUsersPanel.add(new JScrollPane(followingUsersList), BorderLayout.CENTER);
	        followingUsersPanel.add(unfollowButton, BorderLayout.SOUTH);
	        
	        JPanel followingUsersHeaderPanel = new JPanel();
	        followingUsersHeaderPanel.setBackground(Color.lightGray);
	        followingUsersHeaderPanel.setPreferredSize(new Dimension(getWidth(), 30));
	        JLabel followingUsersHeaderLabel= new JLabel("Following Users");
	        followingUsersHeaderPanel.add(followingUsersHeaderLabel);
	        
	        JPanel centerPanel = new JPanel(new BorderLayout());
	        centerPanel.add(followingUsersHeaderPanel, BorderLayout.NORTH);
	        centerPanel.add(followingUsersPanel, BorderLayout.CENTER);

	        JPanel followerUsersPanel = new JPanel(new BorderLayout());
	        followerUsersPanel.add(new JScrollPane(followerUsersList), BorderLayout.CENTER);

	        JPanel followerUsersHeaderPanel = new JPanel();
	        followerUsersHeaderPanel.setBackground(Color.lightGray);
	        followerUsersHeaderPanel.setPreferredSize(new Dimension(getWidth(), 30));
	        JLabel followerUsersHeaderLabel= new JLabel("Follower Users");
	        followerUsersHeaderPanel.add(followerUsersHeaderLabel);
	        
	        JPanel rightPanel = new JPanel(new BorderLayout());
	        rightPanel.add(followerUsersHeaderPanel, BorderLayout.NORTH);
	        rightPanel.add(followerUsersPanel, BorderLayout.CENTER);
	        
	        JPanel mainPanel = new JPanel(new GridLayout(1, 3));
	        mainPanel.add(leftPanel);
	        mainPanel.add(centerPanel);
	        mainPanel.add(rightPanel);

	        add(mainPanel);
	    }

	    public void setAllUsers(List<UserModel> users) {
	        DefaultListModel<String> model = new DefaultListModel<>();
	        for (UserModel user : users) {
	            model.addElement(user.getUsername());
	        }
	        allUsersList.setModel(model);
	    }

	    public void setFollowingUsers(List<String> users) {
	        DefaultListModel<String> model = new DefaultListModel<>();
	        for (String user : users) {
	            model.addElement(user);
	        }
	        followingUsersList.setModel(model);
	    }

	    public void setFollowerUsers(List<String> users) {
	        DefaultListModel<String> model = new DefaultListModel<>();
	        for (String user : users) {
	            model.addElement(user);
	        }
	        followerUsersList.setModel(model);
	    }

	    public JButton getFollowButton() {
	        return followButton;
	    }

	    public JButton getUnfollowButton() {
	        return unfollowButton;
	    }

	    public void followListener(ActionListener listener) {
	    	followButton.addActionListener(listener);
	    }
	    
	    public void unfollowListener(ActionListener listener) {
	    	unfollowButton.addActionListener(listener);
	    }
	    
	    @Override
	    public void update(Observable o, Object arg) {
	        // Update the view when the observable UserModel changes
	        if (o instanceof UserModel) {
	            UserModel userModel = (UserModel) o;
	            setFollowingUsers(userModel.getFollowingUsers());
	            setFollowerUsers(userModel.getFollowerUsers());
	        }
	    }

		public JList<String> getAllUsersList() {
			return allUsersList;
		}

		public void setAllUsersList(JList<String> allUsersList) {
			this.allUsersList = allUsersList;
		}

		public JList<String> getFollowingUsersList() {
			return followingUsersList;
		}

		public void setFollowingUsersList(JList<String> followingUsersList) {
			this.followingUsersList = followingUsersList;
		}

		public JList<String> getFollowerUsersList() {
			return followerUsersList;
		}

		public void setFollowerUsersList(JList<String> followerUsersList) {
			this.followerUsersList = followerUsersList;
		}

	    
	    
}
