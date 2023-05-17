package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import org.apache.commons.lang3.RandomStringUtils;

import data_access.IReadingListAccessor;
import model.ReadingListModel;
import model.UserModel;
import view.CreateReadingListView;
import view.MainMenuView2;

public class CreateReadingListController implements ActionListener {

	private UserModel currentUser;
	private IReadingListAccessor readingListAccessor;
	private CreateReadingListView view;
	
	public CreateReadingListController(UserModel currentUser, IReadingListAccessor readingListAccessor) {
		this.view = new CreateReadingListView(this);
		this.currentUser = currentUser;
		this.readingListAccessor = readingListAccessor;
		this.view.addSubmitReadingListListener(this);
		this.view.addBackListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.view.getSubmitButton()) {
			String value = this.view.getListNameTextField().getText();
			ReadingListModel readingListModel = new ReadingListModel();
			readingListModel.setCreatorResearcherName(this.currentUser.getUsername());
			readingListModel.setId(RandomStringUtils.randomAlphanumeric(8));
			readingListModel.setNumberOfPapers(0);
			readingListModel.setPaperTitles(new HashSet<>());
			readingListModel.setName(value);
			if(!readingListAccessor.existByName(value)) {
				readingListAccessor.add(readingListModel);
				this.view.getListNameTextField().setText("reading list added succesfully!");
			} else {
				this.view.getListNameTextField().setText(value + " Already Exists!");
			}
		} else if (e.getSource() == this.view.getBackButton()) {
			setVisible(false);
			 MainMenuController2  mainMenuController = new MainMenuController2(currentUser);
			 MainMenuView2 mainMenuView = new MainMenuView2(mainMenuController);
	         mainMenuController.setView(mainMenuView);
	         mainMenuView.setVisible(true);
		}
		
	}
	
	public void setVisible(boolean b) {
		this.view.setVisible(b);
	}
}
