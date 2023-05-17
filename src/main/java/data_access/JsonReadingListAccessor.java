package data_access;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;

import model.ReadingListModel;
import util.StringUtils;

public class JsonReadingListAccessor implements IReadingListAccessor {

	private String path;
	
	public JsonReadingListAccessor(String path) {
		this.path = path;
	}

	@Override
	public Collection<ReadingListModel> getAll() {
		List<ReadingListModel> result = new ArrayList<>();
		String jsonStr = null;
		try {
			jsonStr = new String(Files.readAllBytes(Paths.get(this.path)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
        JSONObject jsonObj = new JSONObject(jsonStr);
        JSONArray readingLists = jsonObj.getJSONArray("readingLists");
        
        for (int i = 0; i < readingLists.length(); i++) {
        	ReadingListModel readingListModel = fromJsonToReadingListModel(readingLists.getJSONObject(i));
        	result.add(readingListModel);
        }
		return result;
	}
	
	@Override
	public List<ReadingListModel> getAllByCreatorName(String name) {
		List<ReadingListModel> result = new ArrayList<>();
		String jsonStr = null;
		try {
			jsonStr = new String(Files.readAllBytes(Paths.get(this.path)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
        JSONObject jsonObj = new JSONObject(jsonStr);
        JSONArray readingLists = jsonObj.getJSONArray("readingLists");
        
        for (int i = 0; i < readingLists.length(); i++) {
        	ReadingListModel readingListModel = fromJsonToReadingListModel(readingLists.getJSONObject(i));
        	if(readingListModel.getCreatorResearcherName().equals(name)) {
            	result.add(readingListModel);

        	}
        }
		return result;
	}

	@Override
	public ReadingListModel getById(String id) {
		String jsonStr = null;
		try {
			jsonStr = new String(Files.readAllBytes(Paths.get(this.path)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
        JSONObject jsonObj = new JSONObject(jsonStr);
        JSONArray readingLists = jsonObj.getJSONArray("readingLists");
        
        for (int i = 0; i < readingLists.length(); i++) {
            JSONObject currentReadingList = readingLists.getJSONObject(i);
            if (currentReadingList.getString("id").equals(id)) {
            	return fromJsonToReadingListModel(currentReadingList);
            }
        }
		return null;
	}
	
	@Override
	public ReadingListModel getByName(String name) {
		String jsonStr = null;
		try {
			jsonStr = new String(Files.readAllBytes(Paths.get(this.path)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
        JSONObject jsonObj = new JSONObject(jsonStr);
        JSONArray readingLists = jsonObj.getJSONArray("readingLists");
        
        for (int i = 0; i < readingLists.length(); i++) {
            JSONObject currentReadingList = readingLists.getJSONObject(i);
            if (currentReadingList.getString("name").equals(name)) {
            	return fromJsonToReadingListModel(currentReadingList);
            }
        }
		return null;
	}

	@Override
	public ReadingListModel update(ReadingListModel data) {
		String jsonStr = null;
		try {
			jsonStr = new String(Files.readAllBytes(Paths.get(this.path)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
        JSONObject jsonObj = new JSONObject(jsonStr);
        JSONArray readingLists = jsonObj.getJSONArray("readingLists");
        
        for (int i = 0; i < readingLists.length(); i++) {
            JSONObject currentReadingList = readingLists.getJSONObject(i);
            if (currentReadingList.getString("id").equals(data.getId())) {
            	
                currentReadingList.put("creatorResearcherName", data.getCreatorResearcherName());
                currentReadingList.put("name", data.getName());
                currentReadingList.put("numberOfPapers", data.getNumberOfPapers());
                currentReadingList.put("paperTitles", new JSONArray(data.getPaperTitles()));
                System.err.println(data.getPaperTitles());
                break;
            }
        }
        try {
			Files.write(Paths.get(this.path), jsonObj.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public ReadingListModel add(ReadingListModel data) {
		if(existsById(data.getId())) {
			throw new RuntimeException("ID already exists!");
		}
		String jsonStr = null;
		try {
			jsonStr = new String(Files.readAllBytes(Paths.get(this.path)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
        JSONObject jsonObj = new JSONObject(jsonStr);
        JSONArray readingLists = jsonObj.getJSONArray("readingLists");
        
        JSONObject readingListJson = new JSONObject();
        readingListJson.put("id", data.getId());
        readingListJson.put("creatorResearcherName", data.getCreatorResearcherName());
        readingListJson.put("name", data.getName());
        readingListJson.put("numberOfPapers", data.getNumberOfPapers());
        readingListJson.put("paperTitles", new JSONArray(data.getPaperTitles()));
        readingLists.put(readingListJson);
        try {
			Files.write(Paths.get(this.path), jsonObj.toString().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public ReadingListModel delete(String id) {
		ReadingListModel readingListModel = null;
		String jsonStr = null;
		try {
			jsonStr = new String(Files.readAllBytes(Paths.get(this.path)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
        JSONObject jsonObj = new JSONObject(jsonStr);
        JSONArray readingLists = jsonObj.getJSONArray("readingLists");
        
        for (int i = 0; i < readingLists.length(); i++) {
            JSONObject currentReadingList = readingLists.getJSONObject(i);
            if (currentReadingList.getString("id").equals(id)) {
            	readingListModel = fromJsonToReadingListModel(currentReadingList);
            	readingLists.remove(i);
                break;
            }
        }
		return readingListModel;
	}

	@Override
	public boolean existsById(String id) {
		String jsonStr = null;
		try {
			jsonStr = new String(Files.readAllBytes(Paths.get(this.path)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
        JSONObject jsonObj = new JSONObject(jsonStr);
        JSONArray readingLists = jsonObj.getJSONArray("readingLists");
        
        for (int i = 0; i < readingLists.length(); i++) {
            JSONObject currentReadingList = readingLists.getJSONObject(i);
            if (currentReadingList.getString("id").equals(id)) {
            	return true;
            }
        }
		return false;
	}

	@Override
	public boolean existsAllByIds(Collection<String> ids) {
		for(String id: ids) {
			if(!existsById(id)) {
				return false;
			}
		}
		return true;
	}
	
	private ReadingListModel fromJsonToReadingListModel(JSONObject jsonObject) {
		ReadingListModel readingListModel = new ReadingListModel();
		readingListModel.setId(jsonObject.getString("id"));
		 readingListModel.setName(jsonObject.getString("name"));
		 readingListModel.setCreatorResearcherName(jsonObject.getString("creatorResearcherName"));
         int numberOfPapers = jsonObject.getInt("numberOfPapers");
         Set<String> paperTitles = new HashSet<>();
         JSONArray paperTitlesArray = jsonObject.getJSONArray("paperTitles");
         for (int j = 0; j < paperTitlesArray.length(); j++) {
             paperTitles.add(paperTitlesArray.getString(j));
         }
         readingListModel.setPaperTitles(paperTitles);
         return readingListModel;
	}

	@Override
	public boolean existByName(String name) {
		String jsonStr = null;
		try {
			jsonStr = new String(Files.readAllBytes(Paths.get(this.path)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
        JSONObject jsonObj = new JSONObject(jsonStr);
        JSONArray readingLists = jsonObj.getJSONArray("readingLists");
        
        for (int i = 0; i < readingLists.length(); i++) {
            JSONObject currentReadingList = readingLists.getJSONObject(i);
            if (currentReadingList.getString("name").equals(name)) {
            	return true;
            }
        }
		return false;
	}


}
