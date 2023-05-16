package controller;

import data_access.IDataAccessor;
import data_access.IUserAccessor;
import model.UserModel;
import view.MainMenuView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.ListView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController implements ActionListener, ListSelectionListener  {
	private MainMenuView view;
	private UserModel model;
	private IUserAccessor userAccessor;
	private IDataAccessor dataAccessor;
	private JList<String> followingView;
	private JList<String> followerView;

	public MainMenuController(MainMenuView view, UserModel model) {
		this.view = view;
		this.model = model;
		this.followingView = view.getFollowingView();
		this.followerView = view.getFollowerView();

		this.followingView.addListSelectionListener(this);
		this.followerView.addListSelectionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getSource());
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			// Handle the selected item

			// Do something with the selected item
			System.out.println("Selected Follower: " + followerView.getSelectedValue());
			System.out.println("Selected Follower: " + followingView.getSelectedValue());
		}
	}
}
