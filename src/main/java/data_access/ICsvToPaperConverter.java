package data_access;

import org.apache.commons.csv.CSVRecord;

import model.APaper;

public interface ICsvToPaperConverter {
	
	APaper convert(CSVRecord record);
}
