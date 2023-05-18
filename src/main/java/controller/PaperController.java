package controller;

import java.awt.Color;
import java.util.List;
import java.util.Set;

import data_access.IPaperAccessor;
import data_access.IReadingListAccessor;
import model.APaper;
import model.ReadingListModel;
import model.UserModel;
import view.PaperView;

public class PaperController {

	private PaperView view;
	private UserModel currentUser;
    private List<APaper> papers;
    private List<ReadingListModel> readingLists;
    private IPaperAccessor paperAccessor;
    private IReadingListAccessor readingListAccessor;
    

	public PaperController(IPaperAccessor paperAccessor, IReadingListAccessor readingListAccessor, UserModel currentUser) {
		this.paperAccessor = paperAccessor;
		this.readingListAccessor = readingListAccessor;
		this.currentUser = currentUser;
		
	}
	
	private void initData() {
		this.papers = (List<APaper>) paperAccessor.getAll();
		this.readingLists = (List<ReadingListModel>) readingListAccessor.getAllByCreatorName(currentUser.getUsername());
		for(APaper paper : papers) {
			paper.addObserver(this.view);
		}
		for(ReadingListModel model : readingLists) {
			model.addObserver(this.view);
		}
	}

	public PaperView getView() {
		return view;
	}


	public void setView(PaperView view) {
		this.view = view;
		initData();
		this.view.setPapers(papers);
		this.view.setReadingList(readingLists);
	}


	public List<APaper> getPapers() {
		return papers;
	}


	public void setPapers(List<APaper> papers) {
		this.papers = papers;
	}


	public List<ReadingListModel> getReadingLists() {
		return readingLists;
	}


	public void setReadingLists(List<ReadingListModel> readingLists) {
		this.readingLists = readingLists;
	}

	public void handlePaperSelection(String selectedValue) {
		this.view.setSelectedPaperTitle(selectedValue);
		
	}

	public void handleAddToReadingList(String selectedPaper, String selectedReadingList) {
		ReadingListModel readingListModel = readingListAccessor.getById(selectedReadingList);
		if(paperAccessor.existsById(selectedPaper)) {
			Set<String> paperTitles = readingListModel.getPaperTitles();
			paperTitles.add(selectedPaper);
			readingListModel.setPaperTitles(paperTitles);
			readingListModel.setNumberOfPapers(readingListModel.getNumberOfPapers()+1);
			readingListAccessor.update(readingListModel);
			this.view.update(readingListModel, readingListModel);
			this.handleReadlingListSelection(readingListModel);
		}
		
	}

	public void handleReadlingListSelection(ReadingListModel selectedValue) {
		String result = "";
		for(String str : selectedValue.getPaperTitles()) {
			result = result.concat(str).concat("\n");
		}
		view.setReadingListContent(result);
		
	}

	public void handleRemoveFromReadingList(String selectedPaper, String selectedReadingList) {
		ReadingListModel readingListModel = readingListAccessor.getById(selectedReadingList);
		if(paperAccessor.existsById(selectedPaper)) {
			Set<String> paperTitles = readingListModel.getPaperTitles();
			System.out.println(selectedPaper);
			paperTitles.remove(selectedPaper);
			readingListModel.setPaperTitles(paperTitles);
			readingListModel.setNumberOfPapers(readingListModel.getNumberOfPapers()-1);
			readingListAccessor.update(readingListModel);
			System.out.println(readingListModel.getPaperTitles());
			this.view.update(readingListModel, readingListModel);
			this.handleReadlingListSelection(readingListModel);
		}
		
	}

	
	public void handleDownloadPaper(String selectedPaper) {
		APaper paper = paperAccessor.getById(selectedPaper);
		if(paper != null) {
			paper.setDownloadCount(paper.getDownloadCount()+1);
			paperAccessor.update(paper);
			this.view.getDownloadMessageLabel().setForeground(Color.BLACK);
			this.view.getDownloadMessageLabel().setText("Download Succesfull");
		}
		else {
			this.view.getDownloadMessageLabel().setForeground(Color.RED);
			this.view.getDownloadMessageLabel().setText("Download unsuccesfull");
		}
		
	}
	
	
	

}
