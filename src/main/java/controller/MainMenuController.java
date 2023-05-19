package controller;

import data_access.CsvPaperAccessor;
import data_access.JsonReadingListAccessor;
import data_access.XmlUserAccessor;
import model.UserModel;
import view.MainMenuView;
import view.PaperView;

public class MainMenuController {

    private MainMenuView view;
    private UserModel currentUser;
    private PaperController paperController;
    private UserController userController;
    private ReadingListsController readingListsController;
    private CreateReadingListController createReadingListController;


    public MainMenuController(MainMenuView view, UserModel currentUser) {
        this.view = view;
        this.view.setVisible(true);
        this.currentUser = currentUser;
        initControllers();
    }

    public MainMenuController(UserModel currentUser) {
        this.currentUser = currentUser;
        initControllers();
    }

    private void initControllers() {
        paperController = new PaperController(new CsvPaperAccessor("src/main/resources/papers.csv"),
                new JsonReadingListAccessor("src/main/resources/reading_lists.json"), currentUser);
        userController = new UserController(new XmlUserAccessor("src/main/resources/researchers.xml"), this.currentUser);

        readingListsController = new ReadingListsController(
                new XmlUserAccessor("src/main/resources/researchers.xml"),
                new CsvPaperAccessor("src/main/resources/papers.csv"),
                new JsonReadingListAccessor("src/main/resources/reading_lists.json"),
                this.currentUser);

        createReadingListController = new CreateReadingListController(currentUser, new JsonReadingListAccessor("src/main/resources/reading_lists.json"));

    }

    public void handlePaperListButton() {
        PaperView paperView = new PaperView(paperController);
        paperController.setView(paperView);
        this.view.setVisible(false);
        paperView.setVisible(true);
    }

    public void handleResearcherListButton() {
        userController.setVisible(true);
        this.view.setVisible(false);
    }

    public void handleAllReadingListsButton() {
        readingListsController.setVisible(true);
        this.view.setVisible(false);
    }

    public void handleExitButton() {
        System.exit(0);
    }

    public void handleCreateReadingListButton() {
        this.view.setVisible(false);
        createReadingListController.setVisible(true);
    }

    public MainMenuView getView() {
        return view;
    }

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void setViewVisible(boolean value) {
        this.view.setVisible(value);
    }

}
