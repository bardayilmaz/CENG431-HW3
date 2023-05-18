package controller;

import data_access.*;
import model.ReadingListModel;
import model.UserModel;
import view.MainMenuView2;
import view.ReadingListsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ReadingListsController implements ActionListener {

    private ReadingListsView view;
    private UserModel currentUser;
    private IUserAccessor userAccessor;
    private List<UserModel> allUsers;
    private IPaperAccessor paperAccessor;
    private IReadingListAccessor readingListAccessor;

    public ReadingListsController(IUserAccessor userAccessor, IPaperAccessor paperAccessor, IReadingListAccessor readingListAccessor,
                                   UserModel user) {

        view = new ReadingListsView(this);
        currentUser = user;
        this.view.backButtonListener(this);

        this.userAccessor = userAccessor;
        this.paperAccessor = paperAccessor;
        this.readingListAccessor = readingListAccessor;
        initData();
    }

    public ReadingListsView getView() {
        return view;
    }

    public void setView(ReadingListsView readingListsView) {
        this.view = readingListsView;
    }

    public void setVisible(boolean value) {
        view.setVisible(value);
    }

    private void initData(){
        this.allUsers = (List<UserModel>) userAccessor.getAll();
        this.view.setAllUsers(allUsers);
    }

    public List<ReadingListModel> getReadingLists(String username){
        return readingListAccessor.getAllByCreatorName(username);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.view.getBackButton()) {
            System.out.println("dasdasd");
            handleGoBack();
        }
    }

    public void handleGoBack() {
        view.setVisible(false);
        MainMenuController2 mainMenuController = new MainMenuController2(currentUser);
        MainMenuView2 mainMenuView = new MainMenuView2(mainMenuController);
        mainMenuController.setView(mainMenuView);
        mainMenuView.setVisible(true);
    }

    public void handleReadingListSelection(ReadingListModel selectedValue) {
        if(selectedValue!=null){
            String result = "";
            for(String str : selectedValue.getPaperTitles()) {
                result = result.concat(str).concat("\n");
            }
            this.view.setReadingListContent(result);
        }
        else{
            this.view.setReadingListContent("");
        }
    }
}