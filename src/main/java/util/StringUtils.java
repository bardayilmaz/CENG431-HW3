package util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class StringUtils {

	public StringUtils() {

	}
	
	public static String listToCommaSeperatedString(List<String> list) {
		String result = "";
		if(!list.isEmpty() || list != null) {
			for(String element : list) {
				result = result.concat(element).concat(",");
			}
			if(!list.isEmpty()) {
				return result.substring(0, result.length()-1);
			}
		}
		return " "; // TODO this mf does not work, when giving "". So I give " ", brilliant.
	}
	
	public static boolean areElementsUnique(List<String> list) {
		Set<String> stringSet = new HashSet<>(list);
	    return stringSet.size() == list.size();
	}
	
	public static boolean isStringEmpty(String string) {
		if(string == "" || string == null) {
			return true;
		}
		return false;
	}
	
	public static List<String> commaSeperatedToList(String csv) {
		return Arrays.asList(csv.split(","));
	}

}
