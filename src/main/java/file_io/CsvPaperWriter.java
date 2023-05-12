package file_io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import model.IPaper;


public class CsvPaperWriter implements IFileWriter<IPaper>{

	public CsvPaperWriter() {
		
	}

	@Override
	public void write(IPaper entry, String filePath) {
		File file = new File(filePath);
		try {
			FileWriter writer = new FileWriter(file, true);
			CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
			printer.printRecord(entry.getAttributesForCsv());
			printer.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
}
