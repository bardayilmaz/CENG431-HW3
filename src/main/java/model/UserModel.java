package model;

import java.util.List;
import java.util.Observable;

public class UserModel extends Observable {
	
	private String id;
    private String username;
    private String password;
    private List<String> followingUsers;
    private List<String> followerUsers;
    // TODO following pubs
    
    public UserModel(String id, String username, String password, List<String> followingUsers, List<String> followerUsers) {
		super();
		this.username = username;
		this.password = password;
		this.id = id;
		this.followingUsers = followingUsers;
		this.followerUsers = followerUsers;
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
    
    
}
