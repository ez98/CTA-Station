//
/*Eric Zacarias
 * CS 201
 * Final Project
 * 11/20/2020
 * Program: GeoLocation.java
 * Role: This class is used to maintain station location data. It is also responsible for finding the nearest station given location and calculating distance from one location to another.
*/
//
package project;
import java.util.ArrayList;
import java.util.Collections;

public class GeoLocation{
	private double latitude;
	private double longitude;
	private Station nearestStation; //Association Relationship

	public GeoLocation() { //constructor
		this.latitude = 0.0;
		this.longitude = 0.0;
		this.nearestStation = null;
	}
	public GeoLocation(double lat, double lng) { //constructor
		this.latitude = lat;
		this.longitude = lng;
		this.nearestStation = null;
	}
	public double getLat() { //getter method
		return latitude;
	}
	public void setLat(double l) { //Setter Method for latitude
		this.latitude = l;
	}
	public double getLng() {//getter method
		return longitude;
	}
	public void setLng(double lg) {//setter method for longitude
		this.longitude = lg;
	}
	public void setNearestStation(Double userLat, ArrayList<Station> s) { //setter method
		ArrayList<Double> lats = new ArrayList<Double>(s.size() + 1); //ArrayList<> to store all station latitudes + user latitude
		lats.add(userLat);
		for(int i = 0; i < s.size(); i++) { //for loop to add all latitudes from ArrayList<Station>
			if(s.get(i).getLat() == userLat) {// this was added to get correct station while reading LineRoutes.csv. We pass in a exact latitude from saved logs to get an exact station.
				nearestStation = s.get(i);
				return;
			}
			lats.add(s.get(i).getLat());
		}
		Collections.sort(lats); //sort the ArrayList<>
		int nextIndex = lats.indexOf(userLat) + 1; //find the index of the user's inputed latitude and add 1 to get the very next index
		int prevIndex = lats.indexOf(userLat) - 1;
		double nextLat;
		double prevLat;
		int index = 0;
		if(prevIndex == -1 ) { //means that userLat is the first element in list.
			nextLat = lats.get(nextIndex);
			for(int j = 0; j < s.size(); j++) {//once again, use a for loop to find nextLat in ArrayList<Station>. Once found save that index to int 'index'
				if(s.get(j).getLat() == nextLat) {
					index = j; //'index' is responsible for storing the index of the corresponding latitude retrieved.
					this.nearestStation = s.get(index);
					return;
				}
			}
		}
		if(nextIndex < lats.size()) {
			nextLat = lats.get(nextIndex); //retrieve the latitude at 'nextIndex' and store in a double. This is the closest latitude to the user
			prevLat = lats.get(prevIndex);
			if((userLat - prevLat) < (nextLat - userLat)) {
				for(int j = 0; j < s.size(); j++) {//once again, use a for loop to find nextLat in ArrayList<Station>. Once found save that index to int 'index'
					if(s.get(j).getLat() == prevLat) {
						index = j; //'index' is responsible for storing the index of the corresponding latitude retrieved.
						break;
					}
				}
			}
			else {
				for(int j = 0; j < s.size(); j++) {//once again, use a for loop to find nextLat in ArrayList<Station>. Once found save that index to int 'index'
					if(s.get(j).getLat() == nextLat) {
						index = j; //'index' is responsible for storing the index of the corresponding latitude retrieved.
						break;
					}
				}
			}
			
		}else {
			nextLat = lats.get(nextIndex - 2); //retrieve the latitude at 'nextIndex' and store in a double. This is the closest latitude to the user
			for(int j = 0; j < s.size(); j++) {//once again, use a for loop to find nextLat in ArrayList<Station>. Once found save that index to int 'index'
				if(s.get(j).getLat() == nextLat) {
					index = j; //'index' is responsible for storing the index of the corresponding latitude retrieved.
				}
			}
		}
		this.nearestStation = s.get(index);
	}
	public Station getNearestStation() { //getter method
		return nearestStation;
	}
	public boolean equal(GeoLocation g) { //equals method
		if((g.latitude == latitude) && (g.longitude == longitude)) {
			return true;
		}
		return false;
	}
	public String toString() { //to string method
		return "(" + latitude + ", " + longitude + ")";
	}
	public double calcDistanceFrom(Station s) { //Reference: https://dzone.com/articles/distance-calculation-using-3
		double fromLat = latitude;
		double fromLong = longitude;
		double toLat = s.getLat();
		double toLong = s.getLng();
		
		if(fromLat == toLat && fromLong == toLong) {
			return 0.0;
		}
		
		double angle = fromLong - toLong;
		double distance = Math.sin(Math.toRadians(fromLat)) * Math.sin(Math.toRadians(toLat)) + Math.cos(Math.toRadians(fromLat)) * Math.cos(Math.toRadians(toLat)) * Math.cos(Math.toRadians(angle));
		distance = Math.acos(distance);
		distance = Math.toDegrees(distance);
		distance = distance * 60.0 * 1.1515;
		return distance;
	}
}
