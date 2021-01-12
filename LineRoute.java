//
/*Eric Zacarias
 * CS 201
 * Final Project
 * 11/20/2020
 * Program: LineRoute.java
 * Role: This class is used to maintain line route data for when a user generates a path from station to station
*/
//
package project;

import java.util.ArrayList;

public class LineRoute extends Station{
	private ArrayList<Station> stations;
	private String origin; //station name of user's starting station
	private String destination; //station name of user's ending station
	public String originLC; //the current line color the user starts on
	public String destinationLC; //the line color the user ends on
	private String stop;

	public LineRoute(){ //constructor
		this.stations = null;
		this.origin = "";
		this.destination = "";
		this.stop = "";
	}
	public LineRoute(ArrayList<Station> stations,String fromLC, String toLC) { //constructor
		this.stations = stations;
		this.origin = stations.get(0).getName();
		this.destination = stations.get(stations.size() - 1).getName();
		this.originLC = fromLC;
		this.destinationLC = toLC;
		this.stop = "";
		
	}
	public ArrayList<Station> getStations() { //getter method
		return stations;
	}
	public void setStations(ArrayList<Station> stations) { //setter method
		this.stations = stations;
	}
	public String getOrigin() { //getter method
		return origin;
	}
	public void setOrigin(String origin) { //setter method
		this.origin = origin.toLowerCase();
	}
	public String getDestination() { //getter method
		return destination;
	}
	public void setDestination(String destination) { //setter method
		this.destination = destination.toLowerCase();
	}
	public void setOriginLC(String color) { //setter method
		this.originLC = color;
	}
	public String getOriginLC() { //getter method
		return originLC;
	}
	public void setDestinationLC(String color) { //setter method
		this.destinationLC = color;
	}
	public String getDestinationLC() { //getter method
		return destinationLC;
	}
	public String getStop() { //getter method
		return stop;
	}
	public void setStop(String station) { //setter method
		this.stop = station;
		}
	public String toString() {
		if(!(stop.isEmpty())) { //if a stop is available, we print it. Otherwise, return "none" for stops.
			return "Origin: " + getOrigin() + " (" + originLC.toUpperCase() +")" +   "\nDestination: " + getDestination()+ " (" + destinationLC.toUpperCase() +")" + "\nStop: " + getStop();
		}
		return "Origin: " + getOrigin() + "\nDestination: " + getDestination() + " (" + destinationLC.toUpperCase() +")" + "\nStop: None";
		
	}
	public boolean equals(LineRoute l){
		if(l.getOrigin().equals(origin) && l.getDestination().equals(destination)  && l.getOriginLC().equals(originLC) && l.getDestinationLC().equals(destinationLC)) { //make sure to handle same names(different stations) in main class
			return true;
		}
		return false;
	}
	@Override
	public void takeRoute() {
		if(originLC.equals(destinationLC)) {
			System.out.println("\n===============================================");
			System.out.println("Instructions from " + origin + " to " + destination);
			System.out.println("===============================================");
			System.out.println("\n[1] Get on the " + originLC + " line at " + origin + ".\n[2] Get off at " + destination + ".");
			}
		else if(originLC.equals("red") && destinationLC.equals("green") && stop.equals("State/Lake")){
			System.out.println("\n===============================================");
			System.out.println("Instructions from " + origin + " to " + destination + ".");
			System.out.println("===============================================");
			System.out.println("\n[1] Get on the " + originLC + " line at " + origin + ".\n[2] Transfer onto to the "
									+ destinationLC + " line at " + stop + " via the pedway after getting off at Lake. \n[3] Finally, your last stop is " + destination + ".");
			}
		else if(originLC.equals("green") && destinationLC.equals("red") && stop.equals("State/Lake")){
			System.out.println("\n===============================================");
			System.out.println("Instructions from " + origin + " to " + destination + ".");
			System.out.println("===============================================");
			System.out.println("\n[1] Get on the " + originLC + " line at " + origin + ".\n[2] Transfer onto to the "
									+ destinationLC + " line at Lake via the pedway after getting off at " + stop + ". \n[3] Finally, your last stop is " + destination + ".");
			}
		else if(originLC.equals("red") && stop.equals("State/Lake")) {
			System.out.println("\n===============================================");
			System.out.println("Instructions from " + origin + " to " + destination + ".");
			System.out.println("===============================================");
			System.out.println("\n[1] Get on the " + originLC + " line at " + origin + ".\n[2] Transfer onto to the "
									+ destinationLC + " line at Lake by walking via the pedway to " + stop + ".\n[3] Finally, your last stop is " + destination + ".");
		}
		else if(originLC.equals("pink") && destinationLC.equals("red") && stop.equals("State/Lake") && origin.equals("Harold Washington Library")){
			System.out.println("\n===============================================");
			System.out.println("Instructions from " + origin + " to " + destination + ".");
			System.out.println("===============================================");
			System.out.println("\n[1] Get on the " + originLC + " line at " + origin + ".\n[2] Transfer onto to the "
									+ destinationLC + " line at Lake via the pedway after getting off at " + stop + ".\n[3] Finally, your last stop is " + destination + ".");
		}
		else if(originLC.equals("brown") && destinationLC.equals("red") && stop.equals("State/Lake")){
			System.out.println("\n===============================================");
			System.out.println("Instructions from " + origin + " to " + destination + ".");
			System.out.println("===============================================");
			System.out.println("\n[1] Get on the " + originLC + " line at " + origin + ".\n[2] Transfer onto to the "
									+ destinationLC + " line at Lake via the pedway after getting off at " + stop + ".\n[3] Finally, your last stop is " + destination + ".");
		}
		else {
			System.out.println("\n===============================================");
			System.out.println("Instructions for: " + origin + " to " + destination +".");
			System.out.println("===============================================");
			System.out.println("\n[1] Get on the " + originLC + " line at " + origin + ".\n[2] Transfer onto to the "
									+ destinationLC + " line at " + stop + ".\n[3] Finally, your last stop is " + destination + ".");
			}
		}
}
