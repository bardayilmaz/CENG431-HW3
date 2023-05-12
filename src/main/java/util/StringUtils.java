package util;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class StringUtils {

	public StringUtils() {

	}
	
	public static String listToCommaSeperatedString(List<String> list) {
		String result = "";
		for(String element : list) {
			result = result.concat(element).concat(",");
		}
		return result.substring(0, result.length()-1);
	}
	
	public static boolean areElementsUnique(List<String> list) {
		Set<String> stringSet = new HashSet<>(list);
	    return stringSet.size() == list.size();
	}

}
