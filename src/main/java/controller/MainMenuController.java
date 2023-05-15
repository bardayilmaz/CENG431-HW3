package controller;

import data_access.IUserAccessor;
import model.UserModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController implements ActionListener {
	JFrame view;
	private UserModel model;
	private IUserAccessor userAccessor;
	public MainMenuController(JFrame JFrame, UserModel model) {
		this.view = JFrame;
		this.model = model;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
