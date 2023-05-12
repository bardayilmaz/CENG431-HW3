package model;

import java.util.Arrays;
import java.util.List;

public class ConferencePaper extends APaper {

	private String booktitle;
	
	public ConferencePaper(String type, List<String> authors, String title, String year, String doi, String bookTitle) {
		super(type, authors, title, year, doi);
		this.booktitle = bookTitle;
	}

	@Override
	public String getAttributes() {
		return getYear() + ", " + this.booktitle + ", DOI:" + getDoi();
	}
	
	@Override
	public List<String> getAttributesForCsv() {
		return Arrays.asList(getType(), getAuthors(), getTitle(), getYear(), getYear(), getDoi(), getBooktitle(), String.valueOf(getDownloadCount()));
	}

	public String getBooktitle() {
		return booktitle;
	}

	public void setBooktitle(String booktitle) {
		this.booktitle = booktitle;
	}

	

}
