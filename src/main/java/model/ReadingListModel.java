package model;

import java.util.Objects;
import java.util.Observable;
import java.util.Set;

public class ReadingListModel extends Observable {

	private String id;
	private String creatorResearcherName;
	private String name;
	private int numberOfPapers;
	private Set<String> paperTitles;
	
	public ReadingListModel() {
		this.id = null;
		this.creatorResearcherName = null;
		this.name = null;
		this.numberOfPapers = 0;
		this.paperTitles = null;
	}

	public ReadingListModel(String id, String creatorResearcherName, String name, int numberOfPapers,
			Set<String> paperTitles) {
		super();
		this.id = id;
		this.creatorResearcherName = creatorResearcherName;
		this.name = name;
		this.numberOfPapers = numberOfPapers;
		this.paperTitles = paperTitles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatorResearcherName() {
		return creatorResearcherName;
	}

	public void setCreatorResearcherName(String creatorResearcherName) {
		this.creatorResearcherName = creatorResearcherName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfPapers() {
		return numberOfPapers;
	}

	public void setNumberOfPapers(int numberOfPapers) {
		this.numberOfPapers = numberOfPapers;
	}

	public Set<String> getPaperTitles() {
		return paperTitles;
	}

	public void setPaperTitles(Set<String> paperTitles) {
		this.paperTitles = paperTitles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(creatorResearcherName, id, name, numberOfPapers, paperTitles);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReadingListModel other = (ReadingListModel) obj;
		return Objects.equals(creatorResearcherName, other.creatorResearcherName) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && numberOfPapers == other.numberOfPapers
				&& Objects.equals(paperTitles, other.paperTitles);
	}

	@Override
	public String toString() {
		return "ReadingListModel [id=" + id + ", name=" + name + "]";
	}
	
	
}
