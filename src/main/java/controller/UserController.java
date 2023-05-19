package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import data_access.IUserAccessor;
import model.UserModel;
import view.MainMenuView;
import view.UserView;

public class UserController implements ActionListener {

	private UserView view;
	private IUserAccessor userAccessor;
	private List<UserModel> allUsers;
	private UserModel currentUser;
	
	public UserController(IUserAccessor userAccessor, UserModel currentUser) {
		this.view = new UserView(this);
		this.currentUser = currentUser;
		this.userAccessor = userAccessor;
		initData();
		this.view.followListener(this);
		this.view.unfollowListener(this);
		this.view.backButtonListener(this);
	}

	private void initData() {
		this.allUsers = (List<UserModel>) userAccessor.getAll();
		this.view.setAllUsers(allUsers);
		this.view.setFollowerUsers(currentUser.getFollowerUsers());
		this.view.setFollowingUsers(currentUser.getFollowingUsers());
		this.currentUser.addObserver(this.view);
	}

	public void setVisible(boolean b) {
		this.view.setVisible(b);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.view.getFollowButton()) {
			String selectedUser = this.view.getAllUsersList().getSelectedValue();
			if(selectedUser != null && !currentUser.getFollowingUsers().contains(selectedUser)) { 
				List<String> followings = currentUser.getFollowingUsers();
				followings.add(selectedUser);
				currentUser.setFollowingUsers(followings);
				userAccessor.update(currentUser);
				UserModel selectedUserModel = userAccessor.getById(selectedUser);
				List<String> followers = selectedUserModel.getFollowerUsers();
				followers.add(this.currentUser.getUsername());
				selectedUserModel.setFollowerUsers(followers);
				userAccessor.update(selectedUserModel);
				this.view.update(currentUser, null);
			}
		} else if (e.getSource() == this.view.getUnfollowButton()) {
			
			String selectedUser = this.view.getFollowingUsersList().getSelectedValue();

			if(selectedUser != null && currentUser.getFollowingUsers().contains(selectedUser)) {
				List<String> followings = currentUser.getFollowingUsers();
				followings.remove(selectedUser);
				currentUser.setFollowingUsers(followings);
				userAccessor.update(currentUser);
				UserModel selectedUserModel = userAccessor.getById(selectedUser);
				List<String> followers = selectedUserModel.getFollowerUsers();
				followers.remove(this.currentUser.getUsername());
				selectedUserModel.setFollowerUsers(followers);
				userAccessor.update(selectedUserModel);
				this.view.update(currentUser, null);
			}
		}
		else if (e.getSource() == this.view.getBackButton()) {
			setVisible(false);
			MainMenuController mainMenuController = new MainMenuController(currentUser);
			MainMenuView mainMenuView = new MainMenuView(mainMenuController);
			mainMenuController.setView(mainMenuView);
			mainMenuView.setVisible(true);
		}
	}
}
