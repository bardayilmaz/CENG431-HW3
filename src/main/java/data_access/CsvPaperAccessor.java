package data_access;

import java.util.Collection;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import model.APaper;
import util.StringUtils;

public class CsvPaperAccessor implements IPaperAccessor {

	String filePath;
	ICsvToPaperConverter converter;
	
	public CsvPaperAccessor(String filePath) {
		this.filePath = filePath;
		converter = new CsvToPaperConverter();
	}

	@Override
	public Collection<APaper> getAll() {
		List<APaper> papers = new ArrayList<>();
		
		try (CSVParser parser = new CSVParser(new FileReader(filePath), CSVFormat.DEFAULT)) {
            for (CSVRecord record : parser) {
                papers.add(converter.convert(record));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return papers;
	}

	@Override
	public APaper getById(String id) {
		  try (CSVParser parser = new CSVParser(new FileReader(filePath), CSVFormat.DEFAULT)) {
	            for (CSVRecord record : parser) {
	                String recordTitle = record.get(2);
	                if (recordTitle.equalsIgnoreCase(id)) {
	                    return converter.convert(record);
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		return null;
	}

	@Override
	public APaper update(APaper data) {
		 try (FileReader fileReader = new FileReader(filePath);
	             CSVParser parser = new CSVParser(fileReader, CSVFormat.DEFAULT);) {

	            List<APaper> papers = new ArrayList<>();

	            for (CSVRecord record : parser) {
	                String recordTitle = record.get(2);
	                if (recordTitle.equalsIgnoreCase(data.getTitle())) {
	                	System.out.println("here");
	                    papers.add(data);
	                } else {
	                	System.out.println("not here");
	                    papers.add(converter.convert(record));
	                }
	            }

	            
	            FileWriter fileWriter = new FileWriter(filePath);
	            CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);
	            for (APaper paper : papers) {
	            	System.out.println(paper.getAttributesForCsv());
	                printer.printRecord(paper.getAttributesForCsv());
	                printer.flush();
	            }
	            return data;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return null; // Paper with the given title not found
	}

	@Override
	public APaper add(APaper data) {
		 try (FileWriter fileWriter = new FileWriter(filePath, true);
	             CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {

	            printer.printRecord(data.getAttributesForCsv());

	        } catch (IOException e) {
	            e.printStackTrace();
	            // Handle the exception according to your requirements
	        }
		 return data;
	}

	@Override
	public APaper delete(String id) {
		 try (FileReader fileReader = new FileReader(filePath);
	             CSVParser parser = new CSVParser(fileReader, CSVFormat.DEFAULT);) {
			 	
			 	APaper result = null;
	            List<APaper> papers = new ArrayList<>();

	            for (CSVRecord record : parser) {
	                String recordTitle = record.get(2);
	                System.out.println("check");
	                if (!recordTitle.equalsIgnoreCase(id)) {
	                	System.out.println("added");
	                    papers.add(converter.convert(record));
	                } else {
	                	result = converter.convert(record);
	                }
	            }

	            FileWriter fileWriter = new FileWriter(filePath);
				CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);
				
	            for (APaper paper : papers) {
	            	System.out.println("wrote");
	                printer.printRecord(paper.getAttributesForCsv());
	            }
	            return result;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		 return null;
	}

	@Override
	public boolean existsById(String id) {
		 try (FileReader fileReader = new FileReader(filePath);
	             CSVParser parser = new CSVParser(fileReader, CSVFormat.DEFAULT);) {
	            for (CSVRecord record : parser) {
	                String recordTitle = record.get(2);
	                if (recordTitle.equalsIgnoreCase(id)) {
	                    return true;
	                }
	            }
	            return false;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		return false;
	}

	@Override
	public boolean existsAllByIds(Collection<String> ids) {
		for(String id : ids) {
			if(!existsById(id))
				return false;
		}
		return false;
	}

}
