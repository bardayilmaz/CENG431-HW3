package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import controller.PaperController;
import model.APaper;
import model.ReadingListModel;

public class PaperView extends JFrame implements Observer {

	private PaperController paperController;
	private JList<APaper> paperList;
	private String selectedPaperTitle;
    private DefaultListModel<APaper> paperListModel;
    private JList<ReadingListModel> readingList;
    private DefaultListModel<ReadingListModel> readingListModel;
    private JTextArea readingListContent;
    private JButton addButton;
    private JButton removeButton;
    private JButton downloadButtton;
    private JLabel downloadMessageLabel;
	
	public PaperView(PaperController paperController) {
		this.paperController = paperController;
		this.paperController = paperController;
        setTitle("Paper Viewer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        paperListModel = new DefaultListModel<>();
        paperList = new JList<>(paperListModel);
        JScrollPane paperScrollPane = new JScrollPane(paperList);
        paperScrollPane.setBorder(BorderFactory.createTitledBorder("List of Papers"));
        //add(paperScrollPane, BorderLayout.WEST);
        JPanel contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(900, paperScrollPane.getPreferredSize().height));
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(paperScrollPane, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.WEST);
        

        readingListModel = new DefaultListModel<>();
        readingList = new JList<>(readingListModel);
        JScrollPane readingListScrollPane = new JScrollPane(readingList);
        readingListScrollPane.setBorder(BorderFactory.createTitledBorder("Current Reading List"));
        add(readingListScrollPane, BorderLayout.EAST);

        readingListContent = new JTextArea();
        readingListContent.setEditable(false);
        JScrollPane contentScrollPane = new JScrollPane(readingListContent);
        contentScrollPane.setBorder(BorderFactory.createTitledBorder("Reading List Content"));
        add(contentScrollPane, BorderLayout.CENTER);
        

        addButton = new JButton("Add to Reading List");
        removeButton = new JButton("Remove from Reading List");
        downloadButtton = new JButton("Download selected paper");
        downloadMessageLabel = new JLabel();

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(downloadButtton);
        buttonPanel.add(downloadMessageLabel);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        paperList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                paperController.handlePaperSelection(paperList.getSelectedValue().getTitle());
            }
        });
        
        readingList.addListSelectionListener(e -> {
        	if (!e.getValueIsAdjusting()) {
        		paperController.handleReadlingListSelection(readingList.getSelectedValue());
        	}
        });

        addButton.addActionListener(e -> {
            String selectedPaper = paperList.getSelectedValue().getTitle();
            String selectedReadingList = readingList.getSelectedValue().getId();
            System.out.println(selectedReadingList);
            if (selectedPaper != null && selectedReadingList != null) {
            	paperController.handleAddToReadingList(selectedPaper, selectedReadingList);
            }
        });

        removeButton.addActionListener(e -> {
        	String selectedPaper = paperList.getSelectedValue().getTitle();
            String selectedReadingList = readingList.getSelectedValue().getId();
            if (selectedPaper != null && selectedReadingList != null) {
            	paperController.handleRemoveFromReadingList(selectedPaper, selectedReadingList);
            }
        });
        
        downloadButtton.addActionListener(e -> {
        	String selectedPaper = paperList.getSelectedValue().getTitle();
        	if(selectedPaper != null) {
        		paperController.handleDownloadPaper(selectedPaper);
        	}
        });

        setVisible(true);
    }

    public void setPapers(List<APaper> papers) {
        paperListModel.clear();
        for (APaper paper : papers) {
            paperListModel.addElement(paper);
        }
    }

    public void setReadingList(List<ReadingListModel> readingList) {
        readingListModel.clear();
        for (ReadingListModel paper : readingList) {
            readingListModel.addElement(paper);
        }
    }

    public void setReadingListContent(String content) {
        readingListContent.setText(content);
    }

	public String getSelectedPaperTitle() {
		return selectedPaperTitle;
	}

	public void setSelectedPaperTitle(String selectedPaperTitle) {
		this.selectedPaperTitle = selectedPaperTitle;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof ReadingListModel) {
			ReadingListModel readingListModel = (ReadingListModel) o;
			for(int i = 0; i < readingList.getModel().getSize(); i++) {
				ReadingListModel curr = readingList.getModel().getElementAt(i);
				if(curr.getId().equals(readingListModel.getId())) {
					curr = readingListModel;
					this.readingListModel.setElementAt(curr, i);
					System.out.println("here");
				}
			}
		} else if (o instanceof APaper) {
			APaper paper = (APaper) o;
			for(int i = 0; i < paperList.getModel().getSize(); i++) {
				APaper curr = paperList.getModel().getElementAt(i);
				if(curr.equals(paper.getTitle())) {
					curr = paper;
					this.paperListModel.setElementAt(curr, i);
				}
			}
		}
		
	}

	public JLabel getDownloadMessageLabel() {
		return downloadMessageLabel;
	}

	public void setDownloadMessageLabel(JLabel downloadMessageLabel) {
		this.downloadMessageLabel = downloadMessageLabel;
	}

}
