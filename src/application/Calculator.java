package application;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

public class Calculator {
	// Calculate distance (KM) between two latitude-longitude points (Haversine formula)
	// https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula


	public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
	public final static double KM_TO_MILE = 0.621371;

	public double distanceBetweenTwoPointsInMile(double lat1, double long1, double lat2, double long2) {

		double latDistance = Math.toRadians(lat1 - lat2);
		double lngDistance = Math.toRadians(long1 - long2);

		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		// return (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));

		return AVERAGE_RADIUS_OF_EARTH_KM * c;
	}

	/**
	 * Returns the distance between two sets of latitudes and longitudes in meters.
	 * <p/>
	 * Based from the following JavaScript SO answer:
	 * http://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula,
	 * which is based on https://en.wikipedia.org/wiki/Haversine_formula (error
	 * rate: ~0.55%).
	 */

	public double getDistanceBetween(double lat1, double lon1, double lat2, double lon2) {
		double dLat = toRadian(lat2 - lat1);
		double dLon = toRadian(lon2 - lon1);

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(toRadian(lat1)) * Math.cos(toRadian(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = AVERAGE_RADIUS_OF_EARTH_KM * c;

		return d;
	}

	public double distanceLatLong2(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 6371.0d; // KM: use mile here if you want mile result

		double dLat = toRadian(lat2 - lat1);
		double dLng = toRadian(lng2 - lng1);

		double a = Math.pow(Math.sin(dLat / 2), 2)
				+ Math.cos(toRadian(lat1)) * Math.cos(toRadian(lat2)) * Math.pow(Math.sin(dLng / 2), 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return earthRadius * c * KM_TO_MILE; // returns result in miles
	}

	public double toRadian(double degrees) {
		return (degrees * Math.PI) / 180.0d;
	}

	public TreeMap<String, String> sortMapByValue(HashMap<String, String> map) {
		Comparator<String> comparator = new ValueComparator(map);
		//TreeMap is a map sorted by its keys. 
		//The comparator is used to sort the TreeMap by keys. 
		TreeMap<String, String> result = new TreeMap<String, String>(comparator);
		result.putAll(map);
		return result;
	}

}
