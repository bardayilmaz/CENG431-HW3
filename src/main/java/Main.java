import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.csv.CSVRecord;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;

import test.SphereWindow;

import model.*;
import view.*;
import controller.*;
import data_access.CsvPaperAccessor;
import data_access.IPaperAccessor;
import data_access.IReadingListAccessor;
import data_access.IUserAccessor;
import data_access.JsonReadingListAccessor;
import data_access.XmlUserAccessor;
import file_io.BibFileParser;
import file_io.CsvPaperWriter;
import file_io.IFileParser;

import java.io.File;
import java.io.IOException;

public class Main {
	
	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		IPaperAccessor paperAccessor = new CsvPaperAccessor("src/main/resources/papers.csv");
		//writePapersToCsv();

		IUserAccessor userAccessor = new XmlUserAccessor("src/main/resources/researchers.xml");
		IReadingListAccessor readingListAccessor = new JsonReadingListAccessor("src/main/resources/reading_lists.json");
		ReadingListModel readingListModel = readingListAccessor.getById("1");
		readingListModel.setName("degistim");
		readingListModel.setCreatorResearcherName("ahahahahh");
		Set<String> set = readingListModel.getPaperTitles();
		//System.out.println(set);
		//System.out.println(set.remove("On the Effectiveness of Unit Tests in Test-Driven Development"));
		//readingListModel.setPaperTitles(set);
		readingListAccessor.update(readingListModel);

		UserModel userModel = new UserModel();
		LoginView loginView = new LoginView(userModel);

		LoginController loginController = new LoginController(loginView, userModel, userAccessor);
		loginView.setVisible(true);
		//for(APaper paper : paperAccessor.getAll()) {
			//System.out.println(paper.getType() + paper.getAttributes());
		//}
		/*
		APaper paper = new ArticlePaper("article", Arrays.asList("a"), "xD", "xx2222222", "büşent", "cexap", "öhöm", "hm", 19225);
		paperAccessor.add(paper);
		paper.setDoi("sdfsdfsdf");
		paperAccessor.update(paper);
		 */
		
		//paperAccessor.add(paper);
		/*


        
        IReadingListAccessor accessor = new JsonReadingListAccessor("src/main/resources/reading_lists.json");
        ReadingListModel readingListModel = new ReadingListModel("1", "tugkantuglularsssssssssssssssssssssssssssssssssssssssssssssssss", "zort", 1, new HashSet<>(Arrays.asList("hehe2", "hehe3")));
        accessor.update(readingListModel);
        ReadingListModel readingListModel2 = accessor.getById("1");
        System.out.println(readingListModel2.getName());
        */
        

       
	}
	
	private static List<String> getBibFiles() {
		File folder = new File("src/main/resources/papers/");
		List<String> result = new ArrayList<>();
		for(File file : folder.listFiles()) { 
			if (file.getName().split("\\.")[1].equals("bib")) {
				result.add(file.getPath());
			}
		}
		return result;
	}
	
	// Iterate over the entries and do something with them
    /*for (BibTeXEntry entry : entries) {
        // Print the entry key and type
        System.out.println("Key: " + entry.getKey().getValue());
        System.out.println("Type: " + entry.getType().getValue());
        
        // Print the fields and their values
        for (Key fieldName : entry.getFields().keySet()) {
            System.out.println(fieldName + ": " + entry.getField(fieldName).toUserString());
        }
    }*/

	private static void writePapersToCsv() {
		Random random = new Random();
		List<IPaper> papers = initPapers();
		File file = new File("src/main/resources/papers.csv");
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CsvPaperWriter writer = new CsvPaperWriter();
		for(IPaper p: papers) {
			APaper paper = (APaper)p;
			paper.setDownloadCount(random.nextInt(1500));
			writer.write(p, file.getPath());
		}
	}
	
	private static List<IPaper> initPapers() {
		IFileParser<Collection<BibTeXEntry>> parser = new BibFileParser();
		List<String> files = getBibFiles();
		List<IPaper> papers = new ArrayList<>();
		for(String fileName : files) {
			Collection<BibTeXEntry> bibTexEntries = parser.parse(fileName);
			for(BibTeXEntry entry : bibTexEntries) {
				IPaper paper = null;
				if(entry.getType().toString().equals("inproceedings")) {
					paper = new ConferencePaper("inproceedings",
							entry.getField(new Key("author")) != null ? Arrays.asList(entry.getField(new Key("author")).toUserString().split(", ")) : Arrays.asList(""),
							entry.getField(new Key("title")) != null ? entry.getField(new Key("title")).toUserString() : "",
							entry.getField(new Key("year")) != null ? entry.getField(new Key("year")).toUserString() : "",
							entry.getField(new Key("doi")) != null ? entry.getField(new Key("doi")).toUserString() : "", 
							entry.getField(new Key("bookTitle")) != null ? entry.getField(new Key("bookTitle")).toUserString() : "");
				} else if (entry.getType().toString().equals("article")) {
					paper = new ArticlePaper("article",
							entry.getField(new Key("author")) != null ? Arrays.asList(entry.getField(new Key("author")).toUserString().split(", ")) : Arrays.asList(""),
							entry.getField(new Key("title")) != null ? entry.getField(new Key("title")).toUserString() : "",
							entry.getField(new Key("year")) != null ? entry.getField(new Key("year")).toUserString() : "",
							entry.getField(new Key("doi")) != null ? entry.getField(new Key("doi")).toUserString() : "",
							entry.getField(new Key("volume")) != null ?	 entry.getField(new Key("volume")).toUserString() : "",
							entry.getField(new Key("number")) != null ? entry.getField(new Key("number")).toUserString() : "", 
							entry.getField(new Key("journal")) != null ? entry.getField(new Key("journal")).toUserString() : "");
				} else {
					continue;
				}
				papers.add(paper);
			}
		}
		return papers;
	}

}