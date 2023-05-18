package model;

import java.util.List;
import java.util.Observable;

public class UserModel extends Observable {
	
    private String username;
    private String password;
    private List<String> followingUsers;
    private List<String> followerUsers;
    // TODO following pubs
    
    public UserModel(String username, String password, List<String> followingUsers, List<String> followerUsers) {
		super();
		this.username = username;
		this.password = password;
		this.followingUsers = followingUsers;
		this.followerUsers = followerUsers;
	}
    
    public UserModel(UserModel userModel) {
    	super();
    	this.username = userModel.getUsername();
    	this.password = userModel.getPassword();
		this.followingUsers = userModel.getFollowingUsers();
		this.followerUsers = userModel.getFollowerUsers();
    }
    
    public UserModel() {
    	super();
    }
    
    public void setUsername(String username) {
        this.username = username;
        setChanged();
        notifyObservers();
    }

	public void setPassword(String password) {
        this.password = password;
        setChanged();
        notifyObservers();
    }

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	

	public List<String> getFollowingUsers() {
		return followingUsers;
	}

	public void setFollowingUsers(List<String> followingUsers) {
		this.followingUsers = followingUsers;
	}

	public List<String> getFollowerUsers() {
		return followerUsers;
	}

	public void setFollowerUsers(List<String> followerUsers) {
		this.followerUsers = followerUsers;
	}

	public boolean isValidUser(String username, String password) {
		return this.password.equals(password) && this.username.equals(username);
	}


	@Override
	public String toString() {
		return username;
	}
    
    
}
