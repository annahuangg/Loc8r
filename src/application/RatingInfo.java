package application;

public class RatingInfo {
	// for temporary storing rating and number of ratings
	int rating;
	int ratingTime;

	public void setRatingAndTime(int rate, int time) {
		rating = rate;
		ratingTime = time;
	}

	public int getRating() {
		return rating;
	}

	public int getTime() {
		return ratingTime;
	}

}