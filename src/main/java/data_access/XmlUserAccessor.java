package data_access;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jbibtex.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import model.UserModel;
import util.StringUtils;

public class XmlUserAccessor implements IUserAccessor{

	private String path;
	private File xmlFile;
	
	public XmlUserAccessor(String path) {
		xmlFile = new File(path);
		if(!xmlFile.exists()) {
			throw new RuntimeException("Invalid file");
		}
	}

	@Override
	public Collection<UserModel> getAll() {
		List<UserModel> result = new ArrayList<>();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
        Document doc = null;
		try {
			doc = dBuilder.parse(this.xmlFile);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		if(doc == null) {
			return result;
		}
         doc.getDocumentElement().normalize();
         NodeList nList = doc.getElementsByTagName("researcher");
         
         for(int currentPosition = 0; currentPosition < nList.getLength(); currentPosition++) {
        	 Node node = nList.item(currentPosition);
        	 if(node.getNodeType() == Node.ELEMENT_NODE) {
        		 Element element = (Element) node;
        		 UserModel user = fromXmlToUserModel(element);
        		 result.add(user);
        	 }
    	 }
		return result;
	}

	@Override
	public UserModel getById(String id) {
		UserModel result = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
        Document doc = null;
		try {
			doc = dBuilder.parse(this.xmlFile);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		if(doc == null) {
			return result;
		}
         doc.getDocumentElement().normalize();
         NodeList nList = doc.getElementsByTagName("researcher");
         for(int currentPosition = 0; currentPosition < nList.getLength(); currentPosition++) {
        	 Node node = nList.item(currentPosition);
        	 if(node.getNodeType() == Node.ELEMENT_NODE) {
        		 Element element = (Element) node;
        		 if(!element.getElementsByTagName("researcher_name").item(0).getTextContent().equals(id)) {
        			 continue;
        		 }
        		result = fromXmlToUserModel(element);
        	 }
    	 }
		return result;
	}

	@Override
	public UserModel update(UserModel data) {
		UserModel result = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
        Document doc = null;
		try {
			doc = dBuilder.parse(this.xmlFile);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		if(doc == null) {
			return result;
		}
         doc.getDocumentElement().normalize();
         NodeList nList = doc.getElementsByTagName("researcher");
         for(int currentPosition = 0; currentPosition < nList.getLength(); currentPosition++) {
        	 Node node = nList.item(currentPosition);
        	 if(node.getNodeType() == Node.ELEMENT_NODE) {
        		 Element element = (Element) node;
        		 if(!element.getElementsByTagName("researcher_name").item(0).getTextContent().equals(data.getUsername())) {
        			 continue;
        		 }
        		 if(! (existsAllByIds(data.getFollowerUsers()) && existsAllByIds(data.getFollowingUsers()))) {
        			 throw new RuntimeException("Invalid user list");
        		 }
        		 if(!StringUtils.areElementsUnique(data.getFollowerUsers()) || !StringUtils.areElementsUnique(data.getFollowingUsers())) {
        			 throw new RuntimeException("There are duplicates!");
        		 }
        		 element.getElementsByTagName("following_researcher_names").item(0).setTextContent(StringUtils.listToCommaSeperatedString(data.getFollowingUsers()));
                 element.getElementsByTagName("follower_researcher_names").item(0).setTextContent(StringUtils.listToCommaSeperatedString(data.getFollowerUsers()));
        		 TransformerFactory transformerFactory = TransformerFactory.newInstance();
                 Transformer transformer = null;
				try {
					transformer = transformerFactory.newTransformer();
				} catch (TransformerConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                 DOMSource source = new DOMSource(doc);
                 StreamResult streamResult = new StreamResult(xmlFile);
                 try {
					transformer.transform(source, streamResult);
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		 result = fromXmlToUserModel(element);
        		 break;
        	 }
    	 }
		return result;
	}

	@Override
	public UserModel add(UserModel data) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
        Document doc = null;
		try {
			doc = dBuilder.parse(this.xmlFile);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		if(doc == null) {
			return null;
		}
		if(existsById(data.getUsername())) {
			throw new RuntimeException("User already exists");
		}
		Element researcher = doc.createElement("researcher");
		researcher.setAttribute("id", UUID.randomUUID().toString());
		
		Element researcherName = doc.createElement("researcher_name");
		researcherName.setTextContent(data.getUsername());
		
		if((StringUtils.isStringEmpty(data.getPassword()))) {
			throw new RuntimeException("Password can not be empty!");
		}
		
	    Element passwordElem = doc.createElement("password");
	    passwordElem.setTextContent(data.getPassword());
	   
	    if(! (existsAllByIds(data.getFollowerUsers()) && existsAllByIds(data.getFollowingUsers()))) {
	    	throw new RuntimeException("Invalid user list");
		}
	    if(!StringUtils.areElementsUnique(data.getFollowerUsers()) || !StringUtils.areElementsUnique(data.getFollowingUsers())) {
			throw new RuntimeException("There are duplicates!"); 
	    }
	    
	    Element followings = doc.createElement("following_researcher_names");
	    followings.setTextContent(StringUtils.listToCommaSeperatedString(data.getFollowingUsers()));
	    
	    Element followers = doc.createElement("follower_researcher_names");
	    followers.setTextContent(StringUtils.listToCommaSeperatedString(data.getFollowerUsers()));
	    
	    researcher.appendChild(researcherName);
	    researcher.appendChild(passwordElem);
	    researcher.appendChild(followings);
	    researcher.appendChild(followers);
	    
	    Element root = doc.getDocumentElement();
	    root.appendChild(researcher);

	    // Write the updated document back to the file
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    DOMSource source = new DOMSource(doc);
	    StreamResult result = new StreamResult(xmlFile);
	    try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public UserModel delete(String id) {
		UserModel userModel = null;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document doc = null;
	    try {
			doc = docBuilder.parse(this.xmlFile);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    NodeList researchers = doc.getElementsByTagName("researcher");
	    for (int i = 0; i < researchers.getLength(); i++) {
	        Node researcher = researchers.item(i);
	        if (researcher.getNodeType() == Node.ELEMENT_NODE) {
	            Element element = (Element) researcher;
	            String name = element.getElementsByTagName("researcher_name").item(0).getTextContent();
	            if (name.equals(id)) {
	                researcher.getParentNode().removeChild(researcher);
	            }
	            userModel = fromXmlToUserModel(element);
	        }
	    }
	    
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
	    DOMSource source = new DOMSource(doc);
	    StreamResult result = new StreamResult(xmlFile);
	    try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		return userModel;
	}

	@Override
	public boolean existsById(String id) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
        Document doc = null;
		try {
			doc = dBuilder.parse(this.xmlFile);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		if(doc == null) {
			return false;
		}
         doc.getDocumentElement().normalize();
         NodeList nList = doc.getElementsByTagName("researcher");
         for(int currentPosition = 0; currentPosition < nList.getLength(); currentPosition++) {
        	 Node node = nList.item(currentPosition);
        	 if(node.getNodeType() == Node.ELEMENT_NODE) {
        		 Element element = (Element) node;
        		 if(element.getElementsByTagName("researcher_name").item(0).getTextContent().equals(id)) {
        			 return true;
        		 }
        	 }
    	 }
		return false;
	}

	@Override
	public boolean existsAllByIds(Collection<String> ids) {
		if(ids != null) {
			for(String id : ids) {
				if(!existsById(id)) return false;
			}
			return true;
		}
		return false;
		
	}
	
	private UserModel fromXmlToUserModel(Element element) {
		 UserModel userModel = new UserModel();
		 List<String> followingUsers = new ArrayList<>();
		 List<String> followerUsers = new ArrayList<>();
		 for(String username : element.getElementsByTagName("following_researcher_names").item(0).getTextContent().split(",")) {
			 followingUsers.add(username);
		 }
		 for(String username : element.getElementsByTagName("follower_researcher_names").item(0).getTextContent().split(",")) {
			 followerUsers.add(username);
		 }
		 userModel = new UserModel(
				 element.getElementsByTagName("researcher_name").item(0).getTextContent(),
				 element.getElementsByTagName("password").item(0).getTextContent(), 
				 followingUsers, followerUsers);
		 return userModel;
	}
}
