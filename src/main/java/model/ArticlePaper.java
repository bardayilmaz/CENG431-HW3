package model;

import java.util.Arrays;
import java.util.List;

public class ArticlePaper extends APaper {

	private String volume;
	private String number;
	private String journal;
	
	public ArticlePaper(String type, List<String> authors, String title, String year, String doi, String volume, String number, String journal) {
		super(type, authors, title, year, doi);
		this.number = number;
		this.volume = volume;
		this.journal = journal;
	}
	
	public ArticlePaper(String type, List<String> authors, String title, String year, String doi, String volume, String number, String journal, int downloadCount) {
		super(type, authors, title, year, doi, downloadCount);
		this.number = number;
		this.volume = volume;
		this.journal = journal;
	}

	@Override
	public String getAttributes() {
		return getYear() + ", " + ", Title:  " + getTitle() + "Volume " + this.volume + ", Number " + this.number + ", DOI:" + getDoi() + "Journal: " + this.getJournal();
	}

	@Override
	public List<String> getAttributesForCsv() {
		return Arrays.asList(getType(), getAuthors(), getTitle(), getYear(), getVolume(), getNumber(), getDoi(), getJournal(), String.valueOf(getDownloadCount()));
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}
	
	

}
