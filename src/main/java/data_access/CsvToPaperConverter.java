package data_access;

import org.apache.commons.csv.CSVRecord;

import model.APaper;
import model.ArticlePaper;
import model.ConferencePaper;
import util.StringUtils;

public class CsvToPaperConverter implements ICsvToPaperConverter {

	public CsvToPaperConverter() {
	}

	@Override
	public APaper convert(CSVRecord record) {
		if(record.get(0).equals("article")) {
			return new ArticlePaper(record.get(0), StringUtils.commaSeperatedToList(record.get(1)), record.get(2),
					record.get(3), record.get(4), record.get(5), record.get(6), record.get(7), Integer.parseInt(record.get(8)));
		}
		else if (record.get(0).equals("inproceedings")) {
			return new ConferencePaper(record.get(0), StringUtils.commaSeperatedToList(record.get(1)), record.get(2),
					record.get(3), record.get(4), record.get(5), Integer.parseInt(record.get(6)));
		}
		return null;
	}

}
