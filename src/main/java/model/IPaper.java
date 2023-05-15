package model;

import java.util.List;

public interface IPaper {

	String getTitle();
	String getAuthors();
	List<String> getAuthorList();
	String getAttributes();
	List<String> getAttributesForCsv();
	String getType();
}