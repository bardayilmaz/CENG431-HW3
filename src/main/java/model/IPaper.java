package model;

import java.util.List;

public interface IPaper {

	String getTitle();
	String getAuthors();
	String getAttributes();
	List<String> getAttributesForCsv();
	String getType();
}