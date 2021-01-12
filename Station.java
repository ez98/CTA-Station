//
/*Eric Zacarias
 * CS 201
 * Final Project
 * 11/20/2020
 * Program: Station.java
 * Role: This class is used to maintain station data. This class is responsible for the creation of stations from the csv file and user input.
*/
//
package project;

public class Station extends GeoLocation{
	private String name;
	private String description;
	private boolean wheelchair;
	private int red;
	private int green;
	private int blue;
	private int brown;
	private int purple;
	private int pink;
	private int orange;
	private int[] positions;
	
	public Station() { //constructor
		super();
		this.name = "Undefined";
		this.description = "Undefined";
		this.wheelchair = false;
		this.red = -1;
		this.green = -1;
		this.blue = -1;
		this.brown = -1;
		this.purple = -1;
		this.pink = -1;
		this.orange = -1;
		this.positions = null;
		
	}
	public Station(String name, double lat, double lng, String description, boolean wheelchair, int[] positions) { //constructor
		super(lat,lng);
		this.name = name;
		this.description = description;
		this.wheelchair = wheelchair;
		this.red = positions[0];
		this.green = positions[1];
		this.blue = positions[2];
		this.brown = positions[3];
		this.purple = positions[4];
		this.pink = positions[5];
		this.orange = positions[6];
		this.positions = positions;
		
	}
	
	public String getName() { //getter method
		return name;
	}
	public void setName(String name) {//handle conditions in main
		this.name = name;
	}
	public String getDescription() {//getter method
		return description;
	}
	public void setDescription(String description) {//setter method
		this.description = description;
	}
	public boolean hasWheelchair() { //has method
		return wheelchair;
	}
	public void setWheelchair(boolean wheelchair) {//setter method
		this.wheelchair = wheelchair;
	}
	public int getRed() {//getter method
		return red;
	}
	public void setRed(int position) {//setter method
		this.red = position;
	}
	public int getGreen() {//getter method
		return green;
	}
	public void setGreen(int position) {//setter method
		this.green = position;
	}
	public int getBlue() {//getter method
		return blue;
	}
	public void setBlue(int position) {//setter method
		this.blue = position;
	}
	public int getBrown() {//getter method
		return brown;
	}
	public void setBrown(int position) {//setter method
		this.brown = position;
	}
	public int getPurple() {//getter method
		return purple;
	}
	public void setPurple(int position) {//setter method
		this.purple = position;
	}
	public int getPink() {//getter method
		return pink;
	}
	public void setPink(int position) {//setter method
		this.pink = position;
	}
	public int getOrange() {//getter method
		return orange;
	}
	public void setOrange(int position) {//setter method
		this.orange = position;
	}
	public int[] getPositions() {//getter method
		return positions;
	}
	public void setPositions(int[] p ) {//setter method
		this.positions = p;
	}
	public int hasPosition(String color) { //has method
		switch(color) {
			case "red":
				return red;
			case "green":
				return green;
			case "blue":
				return blue;
			case "brown":
				return brown;
			case "purple":
				return purple;
			case "pink":
				return pink;
			case "orange":
				return orange;
			default:
				return -1;
		}
	}
	public void setPosition(String color, int p) {//setter method
		switch(color.toLowerCase()) {
			case "red":
				this.red = p;
				break;
			case "green":
				this.green = p;
				break;
			case "blue":
				this.blue = p;
				break;
			case "brown":
				this.brown = p;
				break;
			case "purple":
				this.purple = p;
				break;
			case "pink":
				this.pink = p;
				break;
			case "orange":
				this.orange = p;
				break;
			default:
				
		}
	}
	public String getLineColors(){ //getter method, added this so it's easier to gather all available lines
		String colors = "";
		for(int i = 0; i < positions.length; i++) {
			if(positions[i] != -1) {
				switch(i) {
				case 0:
					colors = colors.concat("Red,");
					break;
				case 1:
					colors = colors.concat(" Green,");
					break;
				case 2:
					colors = colors.concat(" Blue,");
					break;
				case 3:
					colors = colors.concat(" Brown,");
					break;
				case 4:
					colors = colors.concat(" Purple,");
					break;
				case 5:
					colors = colors.concat(" Pink,");
					break;
				case 6:
					colors = colors.concat(" Orange");
					break;
					}
				}
			}
		return colors;
	}
	public String toString() { //toString
		String wChair = (wheelchair) ? "Yes" : "No";
		return "Name: " + name + "\nLine(s):" + getLineColors() +"\nDescription: " + description +"\nLocation: " + super.toString() + "\nWheelchair: " + wChair;
	}
	public boolean equals(Station s) { //equals
		if(super.equals(s)) {
			return true;
		}
		if((name.equals(s.getName()) && (red == s.red && blue == s.blue && green == s.green && brown == s.brown && orange == s.orange && pink == s.pink && purple == s.purple))) {
			return true;
		}
		return false;
	}
	public void takeRoute() { //override method
		System.out.println("Unavailable. First Create A Route.");
	}
	
	
}
