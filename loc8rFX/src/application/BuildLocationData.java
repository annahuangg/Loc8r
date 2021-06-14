package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BuildLocationData {
	ArrayList<location> locationData;
	ArrayList<String> businessType;
	ArrayList<location> locationDataForDisp;
	HashMap<String, RatingInfo> RatingMap;
	String RatingFileName = "Rating.txt";

	Calculator cal;
	int NUMBER_OF_ADDRESS_DISPLAYED = 8;

	public BuildLocationData() {
		locationData = new ArrayList<location>();
		businessType = new ArrayList<String>();
		cal = new Calculator();
		locationDataForDisp = new ArrayList<location>();
		RatingMap = new HashMap<String, RatingInfo>();

	}

	public void BuildLocationDatabaset(String fileName) throws FileNotFoundException {
		String[] locDetails;
		readRatingFromFile(); // load rating file first
		
		Scanner scan = new Scanner(new BuildLocationData().getClass().getResourceAsStream(fileName));

		String line = ""; // for read line from location file
		// loop through location file
		while (line != null) {
			try {
				line = scan.nextLine();
			} catch (Exception e) {
				break;
			}

			locDetails = line.split("\t");
			if (locDetails.length > 0) {
				location loc = new location();
				for (int i = 0; i < locDetails.length; i++) {
					loc.setName(locDetails[0]);
					loc.setAddress(locDetails[1]);
					if (RatingMap.containsKey(locDetails[1])) {// get/set rating
						loc.setRating(RatingMap.get(locDetails[1]).getRating());
						loc.setNumberRating(RatingMap.get(locDetails[1]).getTime());
					}
					loc.setType(locDetails[2]);
					loc.setLongitude(Double.parseDouble(locDetails[3]));
					loc.setLatitude(Double.parseDouble(locDetails[4]));
				}
				locationData.add(loc);
			}
		}
		scan.close();

	}

	public void DisplayLocationData() {
		location loc;
		for (int i = 0; i < locationData.size(); i++) {
			loc = locationData.get(i);
		}
	}

	public void createLocationTypeList() {
		location loc;
		boolean found;
		for (int i = 0; i < locationData.size(); i++) {
			loc = locationData.get(i);
			found = false;
			for (int j = 0; j < businessType.size(); j++)
				if (loc.getType().compareTo(businessType.get(j)) == 0) {
					found = true;
					break;
				}
			if (found == false) {
				businessType.add(loc.getType());
			}
		}
	}

	public ArrayList<String> getLocationTypeList() {
		return businessType;
	}

	public ArrayList<location> searchByType(String busType, boolean busNearby) {
		location loc;
		ArrayList<location> result = new ArrayList<location>();

		for (int i = 0; i < locationData.size(); i++) {
			loc = locationData.get(i);
			if (loc.getType().compareTo(busType) == 0)
				result.add(loc);
		}

		return getDistances(result, busNearby);
	}

	public ArrayList<location> searchByAddr(String busAddr, boolean busNearby) {
		location loc;
		ArrayList<location> result = new ArrayList<location>();

		for (int i = 0; i < locationData.size(); i++) {
			loc = locationData.get(i);
			if (loc.getAddress().toLowerCase().indexOf(busAddr.toLowerCase()) >= 0)
				result.add(loc);
		}
		return getDistances(result, busNearby);
	}

	public ArrayList<location> searchByName(String busName, boolean busNearby) {
		location loc;
		ArrayList<location> result = new ArrayList<location>();

		for (int i = 0; i < locationData.size(); i++) {
			loc = locationData.get(i);
			if (loc.getName().toLowerCase().indexOf(busName.toLowerCase()) >= 0)
				result.add(loc);
		}
		return getDistances(result, busNearby);
	}

	public ArrayList<location> searchByAddr_Type(String busAddr, String busType, boolean busNearby) {
		location loc;
		ArrayList<location> result = new ArrayList<location>();

		for (int i = 0; i < locationData.size(); i++) {
			loc = locationData.get(i);
			if (loc.getType().compareTo(busType) == 0
					&& loc.getAddress().toLowerCase().indexOf(busAddr.toLowerCase()) >= 0)
				result.add(loc);
		}
		return getDistances(result, busNearby);
	}

	public ArrayList<location> searchByName_Type(String busName, String busType, boolean busNearby) {
		location loc;
		ArrayList<location> result = new ArrayList<location>();

		for (int i = 0; i < locationData.size(); i++) {
			loc = locationData.get(i);
			if (loc.getName().toLowerCase().indexOf(busName.toLowerCase()) >= 0
					&& loc.getType().compareTo(busType) == 0)
				result.add(loc);
		}
		return getDistances(result, busNearby);
	}

	public ArrayList<location> searchByName_Addr(String busName, String busAddr, boolean busNearby) {
		location loc;
		ArrayList<location> result = new ArrayList<location>();

		for (int i = 0; i < locationData.size(); i++) {
			loc = locationData.get(i);
			if (loc.getName().toLowerCase().indexOf(busName.toLowerCase()) >= 0
					&& loc.getAddress().toLowerCase().indexOf(busAddr.toLowerCase()) >= 0)
				result.add(loc);
		}
		return getDistances(result, busNearby);
	}

	public ArrayList<location> searchByName_Addr_Type(String busName, String busAddr, String busType,
			boolean busNearby) {
		location loc;
		ArrayList<location> result = new ArrayList<location>();

		for (int i = 0; i < locationData.size(); i++) {
			loc = locationData.get(i);
			if (loc.getType().compareTo(busType) == 0
					&& loc.getAddress().toLowerCase().indexOf(busAddr.toLowerCase()) >= 0
					&& loc.getName().toLowerCase().indexOf(busName.toLowerCase()) >= 0)
				result.add(loc);
		}
		return getDistances(result, busNearby);
	}

	private ArrayList<location> getDistances(ArrayList<location> locs, boolean busNearby) {
		location loc, loc0;
		ArrayList<location> myLocs;

		HashMap<String, String> addressToDistanceMap = new HashMap<String, String>();

		if (locs.size() == 0)
			return new ArrayList<location>();
		loc0 = locs.get(0);
		addressToDistanceMap.put(loc0.getAddress(), String.format("%,.1f", loc0.getDistance()));
		if (busNearby == true) {
			NUMBER_OF_ADDRESS_DISPLAYED = 8;
			myLocs = locationData;
		} else {
			NUMBER_OF_ADDRESS_DISPLAYED = Integer.MAX_VALUE;
			myLocs = locs;
		}

		for (int i = 1; i < myLocs.size(); i++) {
			loc = myLocs.get(i);
			// if (loc0.getAddress().compareTo(loc.getAddress()) == 0)
			// continue;
			loc.setDistance(cal.distanceLatLong2(loc0.getLatitude(), loc0.getLongitude(), loc.getLatitude(),
					loc.getLongitude()));

			addressToDistanceMap.put(loc.getAddress(), String.format("%,.2f", loc.getDistance()));
		}

		return UpdateLocationDataForDisp(addressToDistanceMap);
	}

	private ArrayList<location> UpdateLocationDataForDisp(HashMap<String, String> map) {
		locationDataForDisp.clear();
		TreeMap<String, String> sortedByDistance = cal.sortMapByValue(map);
		location loc;
		int counter = 0;
		for (Map.Entry<String, String> entry : sortedByDistance.entrySet()) {
			counter++;
			for (int i = 0; i < locationData.size(); i++) {
				loc = locationData.get(i);
				if (loc.getAddress().compareTo(entry.getKey()) == 0)
					locationDataForDisp.add(loc);
				if (counter > NUMBER_OF_ADDRESS_DISPLAYED)
					break;
			}
		}
		return locationDataForDisp;
	}

	// When Submit clicked
	public void addToRatingMap(String addr, int rate, int time) {
		location loc;
		RatingInfo rinfo = new RatingInfo();
		rinfo.setRatingAndTime(rate, time);
		RatingMap.put(addr, rinfo);
		
		for (int i = 0; i < locationData.size(); i++) {
			loc = locationData.get(i);
			if (loc.getAddress().compareTo(addr) == 0) {
				loc.setNumberRating(time);
				loc.setRating(rate);
				break;
			}
		}		
	}
	
	public void readRatingFromFile() {
		RatingInfo rInfo;
		File rf = new File(RatingFileName);

		if (!rf.exists()) // no rating exist
			return;

		String line = null;
		String[] items;
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(RatingFileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				items = line.split("::");
				rInfo = new RatingInfo();
				rInfo.setRatingAndTime(Integer.parseInt(items[1]), Integer.parseInt(items[2]));		
				RatingMap.put(items[0], rInfo);
			}

			// Always close files.
			bufferedReader.close();
			fileReader.close();

		} catch (Exception ex) {
			System.out.println("Read rating file '" + RatingFileName + "' failed");
			System.exit(0);
		} 
	}

	public void saveRatingToFile() {
		try {
			FileWriter wf = new FileWriter(RatingFileName, false);
			for (Entry<String, RatingInfo> entry : RatingMap.entrySet()) {
				wf.write(entry.getKey() + "::" + entry.getValue().getRating()  + "::" + entry.getValue().getTime() + "\n");
			}
			wf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int deleteRatingHistory() {
		File file = new File(RatingFileName);
		try {
        file.delete();
		}
		catch (Exception e) {
			return -1;
		}
		return 0;
	}
	
	public void resetRating() {
		location loc;
		for (int i = 0; i < locationData.size(); i++) {
			loc = locationData.get(i);
			loc.setRating(0);
			loc.setNumberRating(0);
		}
	}
	
}
