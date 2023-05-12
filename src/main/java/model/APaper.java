package model;

import java.util.List;

public abstract class APaper implements IPaper {

	private List<String> authors;
	private String title;
	private String year;
	private String doi;
	private String type;
	private int downloadCount;
	
	public APaper(String type, List<String> authors, String title, String year, String doi) {
		this.type = type;
		this.authors = authors;
		this.title = title;
		this.year = year;
		this.doi = doi;
		this.downloadCount = 0;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getAuthors() {
		String authors = "";
		for(String author : this.authors) {
			authors = authors.concat(" " + author);
		}
		return authors.trim();
	}

	@Override
	public abstract String getAttributes();
	
	@Override
	public String getType() {
		return this.type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}

	public void setType(String type) {
		this.type = type;
	}	
	
	
	
}
