package application;

import java.util.Comparator;
import java.util.HashMap;

// a comparator that compares Strings
// https://www.programcreek.com/2013/03/java-sort-map-by-value/
class ValueComparator implements Comparator<String> {

	HashMap<String, String> map = new HashMap<String, String>();

	public ValueComparator(HashMap<String, String> map) {
		this.map.putAll(map);
	}

	@Override
	public int compare(String s1, String s2) {
		// sorting ascendant, from smaller to larger
		if (map.get(s1).compareTo(map.get(s2)) <= 0) { 
			return -1;
		} else {
			return 1;
		}
	}
}
