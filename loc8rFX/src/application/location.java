package application;

public class location {
	// basic location info read from location file
	String name;
	String address;
	String type;
	double longitude;
	double latitude;
	int numRating; // number of times the business been rated
	int rating;

	// distance between to long-lat points
	double distance;
	// extended info

	public location() {
		name = null;
		address = null;
		type = null;
		longitude = 0;
		latitude = 0;
		distance = 0;
		rating = 0;
		numRating=0;
	}

	public String getName() {
		return name;
	}

	public void setName(String locName) {
		name = locName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String locAddress) {
		address = locAddress;
	}

	public String getType() {
		return type;
	}

	public void setType(String locType) {
		type = locType;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double locLongitude) {
		longitude = locLongitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double locLatitude) {
		latitude = locLatitude;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int locRating) {
		rating = locRating;
	}

	public int getNumberRating() {
		return numRating;
	}

	public void setNumberRating(int num) {
		numRating = num;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double locDistance) {
		distance = locDistance;
	}
}
