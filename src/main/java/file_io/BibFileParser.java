package file_io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXParser;
import org.jbibtex.Key;
import org.jbibtex.ObjectResolutionException;
import org.jbibtex.ParseException;
import org.jbibtex.TokenMgrException;
import org.jbibtex.BibTeXEntry;

public class BibFileParser implements IFileParser<Collection<BibTeXEntry>>{

	
	public BibFileParser() {
	}

	@Override
	public Collection<BibTeXEntry> parse(String filePath)  {
		File file = new File(filePath);
		Reader reader = null;
		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Parse the BibTeX file into a BibTeXDatabase object
		BibTeXParser parser = null;
		try {
			parser = new BibTeXParser();
		} catch (TokenMgrException | ParseException e) {
			e.printStackTrace();
		}
		BibTeXDatabase database = null;
		try {
			database = parser.parse(reader);
		} catch (ObjectResolutionException | TokenMgrException | ParseException e) {
			e.printStackTrace();
		}

		// Get all BibTeX entries in the database
        Collection<BibTeXEntry> entries = database.getEntries().values();

        /*for (BibTeXEntry entry : entries) {
        // Print the entry key and type
        System.out.println("Key: " + entry.getKey().getValue());
        System.out.println("Type: " + entry.getType().getValue());
        
        // Print the fields and their values
        for (Key fieldName : entry.getFields().keySet()) {
            System.out.println(fieldName + ": " + entry.getField(fieldName).toUserString());
        }
    }*/
        
		return entries;
	}
}
