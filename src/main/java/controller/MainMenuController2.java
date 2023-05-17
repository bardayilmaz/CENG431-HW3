package controller;

import data_access.CsvPaperAccessor;
import data_access.IPaperAccessor;
import data_access.IUserAccessor;
import data_access.JsonReadingListAccessor;
import data_access.XmlUserAccessor;
import model.UserModel;
import view.MainMenuView2;
import view.PaperView;
import view.UserView;

public class MainMenuController2 {

	MainMenuView2 view;
	UserModel currentUser;
	
	public MainMenuController2(MainMenuView2 view, UserModel currentUser) {
		this.view = view;
		view.setVisible(true);
		this.currentUser = currentUser;
	}
	
	public MainMenuController2(UserModel currentUser) {
		this.currentUser = currentUser;
	}
	
	public void handleButton1Click() {
		PaperController controller = new PaperController(new CsvPaperAccessor("src/main/resources/papers.csv"),
				new JsonReadingListAccessor("src/main/resources/reading_lists.json"),
				currentUser);
		PaperView paperView = new PaperView(controller);
		controller.setView(paperView);
		view.setVisible(false);
		paperView.setVisible(true);
		
	}
	
	public void handleButton2Click() {
		UserController controller = new UserController(new XmlUserAccessor("src/main/resources/researchers.xml"), this.currentUser);
		controller.setVisible(true);
		this.view.setVisible(false);
		
	}

	public void handleButton3Click() {
		// TODO Auto-generated method stub
		
	}

	public void handleButton4Click() {
		// TODO Auto-generated method stub
		
	}

	public MainMenuView2 getView() {
		return view;
	}

	public void setView(MainMenuView2 view) {
		this.view = view;
		//this.view.setVisible(true);
	}

	public void handleButton5Click() {
		CreateReadingListController controller = new CreateReadingListController(currentUser, new JsonReadingListAccessor("src/main/resources/reading_lists.json"));
		this.view.setVisible(false);
		controller.setVisible(true);
	}

	
}
