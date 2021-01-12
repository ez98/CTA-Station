//
/*Eric Zacarias
 * CS 201
 * Final Project
 * 11/20/2020
 * Program: CTAClient.java
 * Role: Main program that handles station and route data and the manipulation of that data.
*/
//
package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CTAClient { 
	
	public static int [] parseStationLines(String[] log) { //Purpose: To extract line positions from .CSV file.
		int[] lines = new int[7];
		int j = 0;
		for(int i = 5; i < log.length; i++ ) {
			int k = Integer.parseInt(log[i]);
			lines[j] = k;
			j++;
		}
		return lines; 
	}
	public static String isValid(Scanner input, String s) {//Purpose: To validate user string input. 
		while(s.isEmpty()) {
			System.out.print("Please Enter Valid Input: ");
			s = input.nextLine();
		}
		return s;
	}
	public static String chooseLine(Scanner input, Station s) {//Purpose: Called in modifyRoute(), to manage user line input
		String lineColor = "";
		do {
			System.out.println("Available Line(s): " + s.getLineColors());
			System.out.print("Choose A Line: ");
			lineColor = input.nextLine();	
		}while(s.hasPosition(lineColor) == -1);

		return lineColor;
	}
	public static double isValid(Scanner input, double l,String type) {//Purpose: To validate user location input
		if (type.equals("lat")) {
			while((l < 41.00 || l > 43.00)) {
				System.out.print("That isn't within the range of the CTA, try again.");
				System.out.print("Range: 41.00 < latitude < 43.00");
				System.out.print("\nPlease Enter Valid Input: ");
				l = Double.parseDouble(input.nextLine());
			}
			return l;
		}
		while(l < -88.00 || l > -87.00) {
			System.out.print("That isn't within the range of the CTA, try again.");
			System.out.print("Range: -88.00 < longtiude < -87.00");
			System.out.print("\nPlease Enter Valid Input: ");
			l = Double.parseDouble(input.nextLine());
			
		}
		return l;
	
	}
	public static String[] parseToStringArray(Station station) {//Purpose: Called in saveStations(), to store station data in array and save into a CSV file
		
		String name = station.getName();
		String lat = String.valueOf(station.getLat());
		String lng = String.valueOf(station.getLng());
		String deScrip = station.getDescription();
		String wheelChar = (String.valueOf(station.hasWheelchair()) == "true") ? "TRUE" : "FALSE";
		String red = String.valueOf(station.hasPosition("red"));
		String green = String.valueOf(station.hasPosition("green"));
		String blue = String.valueOf(station.hasPosition("blue"));
		String brown = String.valueOf(station.hasPosition("brown"));
		String purple = String.valueOf(station.hasPosition("purple"));
		String pink = String.valueOf(station.hasPosition("pink"));
		String orange = String.valueOf(station.hasPosition("orange"));
		String[] values = {name,lat,lng,deScrip,wheelChar,red,green,blue,brown,purple,pink,orange};
		return values;

	}
	public static ArrayList<Station> readStations(){ //Purpose: To read in the stations from the CSV file
		//CTAStation[0] = Name, CTAStation[1] = Latitude, CTAStation[2] = Longitude, CTAStation[3] = Description, CTAStation[4] = Wheelchair
		//CTAStation[5] = RedLinePosition, CTAStation[6] = GreenLinePosition, CTAStation[7] = BlueLinePosition, CTAStation[8] = BrownLinePosition  
		//CTAStation[9] = PurpleLinePosition, CTAStation[10] = PinkLinePosition, CTAStation[11] = OrangeLinePosition
		ArrayList<Station> stations = new ArrayList<Station>();
		try {
			File file = new File("src/project/CTAStops.csv");
			Scanner input = new Scanner(file);
			input.nextLine();
			while(input.hasNextLine()) {
				int[] positions = new int[7];
				String[] CTAStation = input.nextLine().split(",");
				double lat = Double.parseDouble(CTAStation[1]);
				double lng = Double.parseDouble(CTAStation[2]);
				boolean wheelChair = (CTAStation[4].equals("TRUE")) ? true : false;
				positions = parseStationLines(CTAStation);
				Station s = new Station(CTAStation[0], lat, lng, CTAStation[3],wheelChair, positions);
				stations.add(s);
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
		return stations;
		
	}
	public static void saveStations(ArrayList<Station> stations) { //Purpose: To save station to a CSV file
		try {
			FileWriter file = new FileWriter("src/project/CTAStops.csv");
			String header  = "Name,Latitude,Longitude,Description,Wheelchair,Red,Green,Blue,Brown,Purple,Pink,Orange";
			int columnSize = header.split(",").length;
			file.write(header + "\n");
			for(int i = 0; i < stations.size(); i++) {
				for (int j = 0; j < columnSize; j++) {
					String[] log = parseToStringArray(stations.get(i));
					file.write(log[j] + ",");
					}
				file.write("\n");
			}
			file.flush();
			file.close();
			} catch (Exception e) {
			System.out.println("An Error Occured While Saving Stations.");
			return;
			}
		System.out.println("Stations Saved!");
		}
	public static ArrayList<Station> addStation(Scanner input, ArrayList<Station> station){ //Purpose: To add a new station.
		int[] positions = new int[7];
		boolean done = false;
		System.out.print("\n========[ New Station ]========");
		do {
			try {
				Station s;
				String [] color = {"Red","Green","Blue","Brown","Purple","Pink","Orange"};
				System.out.print("\nName: ");
				String name = isValid(input, input.nextLine()); //isValid check if user is entering valid and appropriate information
				System.out.print("\nLatitude: ");
				double lat = isValid(input, Double.parseDouble(input.nextLine()),"lat");
				System.out.print("\nLongitude: ");
				double lng = isValid(input, Double.parseDouble(input.nextLine()),"long");
				System.out.print("\nDescription: ");
				String description = isValid(input, input.nextLine());
				String wheelchair = "";
				boolean wheelChair = false;
				do {
					wheelchair = "";
					System.out.print("\nWheelchair? (Y/N):");
					wheelchair += isValid(input, input.nextLine()).toLowerCase();
					if(wheelchair.equals("y")) {
						wheelChair = true;
					}
					else if(wheelchair.equals("n")) {
						wheelChair = false;
					}
				}while(!(wheelchair.equals("y")) && !(wheelchair.equals("n")));
				for (int i = 0; i < positions.length; i++) {
					do {
						System.out.print("\n" + color[i] + " Line Position (-1 to 32): ");
						positions[i] = Integer.parseInt(input.nextLine());
					}while(positions[i] > 32 || positions[i] < -1);	
				}
				s = new Station(name,lat,lng,description,wheelChair,positions);
				station.add(s);
				System.out.print("\n##############\n");
				System.out.print("Station added!\n");
				System.out.print("##############\n");
				done = true;
				
			}catch(Exception e) {
				System.out.println("Invalid Input. Try Again.");
			}
		}while(!done);
		return station;
	}
	public static Station searchStation(Scanner input, ArrayList<Station> s ) { //Purpose: To search for a station in ArrayList<Station>
		ArrayList<Station> stationsFound = new ArrayList<Station>();
		ArrayList<String> names = new ArrayList<String>(s.size());
		for(int i = 0; i < s.size(); i++) {
			names.add(s.get(i).getName().toLowerCase());
		}
		
		System.out.print("Search by Station Name: ");
		String name = input.nextLine().toLowerCase();
		int firstIndex = names.indexOf(name);
		if(firstIndex == -1) {
			System.out.println("Station Does Not Exist.");
			return null;
		}
		int lastIndex = names.lastIndexOf(name);
		if(firstIndex < lastIndex) {//appears more than once
			for (int j = firstIndex; j <= lastIndex; j++) { //check between those indexes
				if(names.get(j).equals(name)) {
					stationsFound.add(s.get(j)); //add correct station to stationsFound[]
				}
			}
			System.out.println("\n\n########################");
			System.out.println("Multiple Stations Found!");
			System.out.println("########################\n");
			int choice = 0; 
			do {
				int num = 0;
				System.out.println("*************************************");
				System.out.println("          Choose a Station");
				System.out.println("*************************************\n");
				for(Station s0 : stationsFound) {
					System.out.println("****************["+ (++num) +"]****************");
					System.out.println(s0.toString());
					System.out.println("***********************************");
				}
				System.out.print("Choice: ");
				try {
					choice = Integer.parseInt(input.nextLine());
					return stationsFound.get(choice - 1);
				}catch(Exception e) {
					System.out.println("\nInvalid Input.");
				}
				
				
			}while(stationsFound.size() < choice || choice <= 0);
			
		}else if(firstIndex == lastIndex) { //if only one index, then firstIndex == lastIndex. Return that station at that index.
			return s.get(firstIndex);
		}
		return null;
	}
	public static void displayStations(Scanner input, int count, ArrayList<Station> stationsFound) { //Purpose: Called in searchByLine(). To control the search results. User presses enter to view more results.
		System.out.println("\n\n===========================");
		System.out.println("    " + count + " Stations Found");
		System.out.println("===========================");
		boolean done = false; 
		int result = 0;
		if(count >= 10) {
			for(int i = 0; i < count / 2; i++) {
				System.out.println("**************[" + (++result) + "]**************");
				System.out.println(stationsFound.get(i).toString());
			}
			do {
				System.out.println("\n=================================");
				System.out.println("Press Enter To View More Stations.");
				System.out.println("=================================");
				String choice = input.nextLine();
				switch(choice) {
					case "":
						System.out.println("\n\n\n");
						for(int i = (count / 2); i < count; i++) {
							System.out.println("**************" + (++result) + "**************");
							System.out.println(stationsFound.get(i).toString());
						}
						done = true;
						break;
					default:
						System.out.println("Invalid Input. Press Enter.");
				}	
			}while(!done);
		}
		else {
			for(int i = 0; i < count; i++) {
				System.out.println("**************[" + (++result) + "]**************");
				System.out.println(stationsFound.get(i).toString());
				System.out.println("***************************");
			}
		}
	}
	public static void searchByLine(Scanner input, ArrayList<Station> stations) { //Purpose: To search ArrayList<Station> by line color.
		System.out.print("Enter Line Color: ");
		String choice = input.nextLine().toLowerCase();
		ArrayList<Station> stationsFound = new ArrayList<Station>();
		int count = 0;
		switch(choice) {
			case "red":
				for (Station s : stations) {
					if(s.hasPosition(choice) != -1) {
						stationsFound.add(s);
						count++;
					}
				}
				break;
			case "green":
				for (Station s : stations) {
					if(s.hasPosition(choice) != -1) {
						stationsFound.add(s);
						count++;
					}
				}
				break;
			case "blue":
				for (Station s : stations) {
					if(s.hasPosition(choice) != -1) {
						stationsFound.add(s);
						count++;
					}
				}
				break;
			case "brown":
				for (Station s : stations) {
					if(s.hasPosition(choice) != -1) {
						stationsFound.add(s);
						count++;
					}
				}
				break;
			case "purple":
				for (Station s : stations) {
					if(s.hasPosition(choice) != -1) {
						stationsFound.add(s);
						count++;
					}
				}
				break;
			case "pink":
				for (Station s : stations) {
					if(s.hasPosition(choice) != -1) {
						stationsFound.add(s);
						count++;
					}
				}
				break;
			default:
				System.out.println("Invalid Input. Try Again.");
		}
		displayStations(input, count, stationsFound);
	}
	public static String searchByOption(Scanner input) { //Purpose: Called in stationMenu(), for user to choose their searching option.
		boolean done = false;
		do {
			System.out.println("\nSearch By:");
			System.out.println("1.) Name");
			System.out.println("2.) Line");
			System.out.println("3.) Return To Menu");
			System.out.print("Choice: ");
			String choice = input.nextLine().toLowerCase();
			switch(choice) {
				case "1":
					return "name";
				case "2":
					return "line";
				case "3":
					return "none";
				default:
					System.out.println("Invalid Option. Try Again.");
			}
			
		}while(!done);
		
		return "none";
	}
	public static void modifyStation(Scanner input, ArrayList<Station> stations) { //Purpose: To allow the user to modify a station.
		Station s = searchStation(input, stations);
		if(s == null) {
			return;
		}
		int index = stations.indexOf(s);
		boolean done = false;
		do {
			System.out.println("\nWhat Would You Like To Modify?");
			System.out.println("1.) Name");
			System.out.println("2.) Location");
			System.out.println("3.) Description");
			System.out.println("4.) Wheelchair Status");
			System.out.println("5.) Return To Menu");
			System.out.print("Choice: ");
			String choice = input.nextLine();
			switch (choice) {
			case "1":
				System.out.print("Enter New Name: ");
				String name = input.nextLine();
				isValid(input, name);
				stations.get(index).setName(name);
				System.out.print("Done.");
				break;
			case "2":
				try {
					System.out.print("\nEnter New Latitude: ");
					double lat = Double.parseDouble(input.nextLine());
					isValid(input, lat,"lat");
					stations.get(index).setLat(lat);;
					System.out.print("\nEnter New Longitude: ");
					double lng = Double.parseDouble(input.nextLine());
					isValid(input, lng,"long");
					stations.get(index).setLng(lng);
					System.out.print("Done.");
					
				}catch(Exception e) {
					System.out.println("Invalid Input. Try Again");
				}
				break;
			case "3":
				System.out.print("\nEnter New Description: ");
				String description = input.nextLine();
				isValid(input, description);
				stations.get(index).setDescription(description);
				System.out.print("Done.");
				break;
			case "4":
				String wheelchair = "";
				boolean wheelChair = false;
				do {
					wheelchair = "";
					System.out.print("\nEnter Wheelchair Status (Y or N): ");
					wheelchair += isValid(input, input.nextLine()).toLowerCase();
					if(wheelchair.equals("y")) {
						wheelChair = true;
					}
					else if(wheelchair.equals("n")) {
						wheelChair = false;
					}
				}while(!(wheelchair.equals("y")) && !(wheelchair.equals("n"))); 
				stations.get(index).setWheelchair(wheelChair);
				System.out.print("Done.");
				break;
			case "5":
				done = true;
				break;
			default:
				System.out.println("Not a valid option, try again.");
				break;
				}
		}while(!done);
	}
	public static void deleteStation(Scanner input, ArrayList<Station> station) { //Purpose: To allow the user to delete a specific station
		Station s = searchStation(input, station);
		boolean done = false;
		if(s == null) {
			return;
		}
		int index = station.indexOf(s);
		do {
			System.out.print("Are You Sure You Want To Delete " + "'" + station.get(index).getName() + "'" + "?(Y/N): ");
			String choice = input.nextLine();
			switch(choice.toLowerCase()) {
				case "y":
					System.out.println("\n'" + station.get(index).getName() + "' deleted.");
					station.remove(index);
					done = true;
					break;
				case "n":
					done = true;
					break;
				default:
					System.out.println("Invalid Choice. Try Again.");
					break;
			}
		}while(!done);
		
	}
	public static void nearestStation(Scanner input, ArrayList<Station> s) { //Purpose: Given user's location, find their nearest station. Called in stationMenu()
		try {
			System.out.print("Enter your latitude: ");
			double lat = Double.parseDouble(input.nextLine());
			isValid(input, lat,"lat");
			System.out.print("\nEnter your longitude: ");
			double lng = Double.parseDouble(input.nextLine());
			isValid(input, lng, "long");
			GeoLocation userLocation = new GeoLocation(lat,lng);
			userLocation.setNearestStation(lat, s);
			Station nearest = userLocation.getNearestStation();
			System.out.println("\n=======[ Nearest Station ]=======\n" + nearest.toString());
			System.out.println("=================================\n");
		}catch(Exception e) {
			System.out.println("Invalid Input Try Again.");
			nearestStation(input, s);
		}
		
	}
	public static ArrayList<Station> getTransferStations(ArrayList<Station> s) { //Purpose: To gather all stations in which the user has the option to transfer from current line to a specific line. Called in findPath()
		ArrayList<Station> list = new ArrayList<Station>();
		for (Station s1 : s) {
			String lat = String.valueOf(s1.getLat());
			switch(lat) {
				case "42.019063":
					list.add(s1);
					break;
				case "41.964273":
					list.add(s1);
					break;
				case "41.939751":
					list.add(s1);
					break;
				case "41.925051":
					list.add(s1);
					break;
				case "41.888675":
					list.add(s1);
					break;
				case "41.885268":
					list.add(s1);
					break;
				case "41.867368":
					list.add(s1);
					break;
				case "41.885767":
					list.add(s1);
					break;
				case "41.88574": //State/Lake
					s1.setPosition("red", 0); //to travel from West green line to north red line without going all the way to Roosevelt.
					list.add(s1);			  //this doesn't permanently change the stations lines. It just makes it easier to transfer here.
					break;
				case "41.884809":
					list.add(s1);
					break;
				case "41.883164":
					list.add(s1);
					break;
				case "41.882541":
					list.add(s1);
					break;
				case "41.876862":
					list.add(s1);
					break;
				case "41.878153":
					list.add(s1);
					break;
				case "41.884431":
					list.add(s1);
					break;
				case "41.885678":
					list.add(s1);
					break;

			}
		}
		return list;
	}
	public static int findTransferStation(LineRoute lr, ArrayList<Station> transferStations, Station origin) {//Purpose to calculate closest transfer station to user that allows the user to transfer to their desired line and get to their destination. Called in findPath()
		double minDist = 0.0;
		int closestTransfer = 0;
		String fromLine = lr.getOriginLC();
		String toLine = lr.getDestinationLC();
		for(Station s : transferStations) {
			if(s.hasPosition(fromLine) != -1 && s.hasPosition(toLine) != -1) {
				minDist = s.calcDistanceFrom(origin);
				closestTransfer = transferStations.indexOf(s);
				break;
			}
		}
		for(int i = 0; i < transferStations.size(); i++) {
			if(minDist > transferStations.get(i).calcDistanceFrom(origin) && transferStations.get(i).hasPosition(toLine) !=-1 && transferStations.get(i).hasPosition(fromLine) != -1){
				minDist = transferStations.get(i).calcDistanceFrom(origin);
				closestTransfer = i;
			}
		}
		return closestTransfer;
	}
	public static String findPath(ArrayList<Station> s,LineRoute lr) { //Purpose: To generate an optimal path for user to travel from station to station. Called in addRoute()
		Station origin = lr.getStations().get(0); //LineRoute has ArrayList<Station> that stores origin and destination. Origin is first.
		ArrayList<Station> transferStations = getTransferStations(s);
		String fromLine = lr.getOriginLC();
		String toLine = lr.getDestinationLC();
		try {
			if (fromLine.equals(toLine)) { //if user is traveling on the same line then its a direct path
				String stop = lr.getStations().get(1).getName(); //get destination station name since its their only stop.
				return stop; //return the String stop which is then set in LineRoute.java
			}
			else {
				int i = findTransferStation(lr, transferStations, origin);
				String stop = transferStations.get(i).getName();
				return stop;
				}
			}catch(Exception e) {
				System.out.println("An Error Occured While Generating Path.");
		}
		return null;
	}
	public static ArrayList<LineRoute> readRoutes(ArrayList<Station> s){ //Purpose: To read the CSV file containing user routes. Called in main()
		//CTAStation[0] = Name, CTAStation[1] = Latitude, CTAStation[2] = Longitude, CTAStation[3] = Description, CTAStation[4] = Wheelchair
		//CTAStation[5] = RedLinePosition, CTAStation[6] = GreenLinePosition, CTAStation[7] = BlueLinePosition, CTAStation[8] = BrownLinePosition  
		//CTAStation[9] = PurpleLinePosition, CTAStation[10] = PinkLinePosition, CTAStation[11] = OrangeLinePosition
		ArrayList<LineRoute> routes = new ArrayList<LineRoute>();
		try {
			File file = new File("src/project/LineRoutes.csv");
			Scanner input = new Scanner(file);
			input.nextLine();
			while(input.hasNextLine()) {
				ArrayList<Station> stations = new ArrayList<Station>();
				String[] route = input.nextLine().split(",");
				String fromLC = route[1].toLowerCase();
				String toLC = route[3].toLowerCase();
				String stops = route[4];
				double latOne = Double.parseDouble(route[5]); //origin
				double latTwo = Double.parseDouble(route[6]); //destination
				GeoLocation location = new GeoLocation();
				location.setNearestStation(latOne, s); //this will retrieve the station we're looking for. It sets it as a Station object in GeoLocation.java
				Station s1 = location.getNearestStation();
				stations.add(s1);
				location.setNearestStation(latTwo, s);
				Station s2 = location.getNearestStation();
				stations.add(s2);
				LineRoute lr = new LineRoute(stations,fromLC,toLC);
				lr.setStop(stops);
				routes.add(lr);
			}
			input.close();
		} catch (Exception e) {
			System.out.println("Error Reading LineRoutes.csv");
		}
	
		return routes;
		
	}
	public static void saveRoutes(ArrayList<LineRoute> routes) { //Purpose: To save user's line routes. Called in main()
		try {
			FileWriter file = new FileWriter("src/project/LineRoutes.csv");
			String header  = "Origin,Line,Destination,Line,Stops,Origin Latitude, Destination Latitude";
			int columnSize = header.split(",").length;
			file.write(header + "\n");
			for(int i = 0; i < routes.size(); i++) {
				String[] log = parseToStringArray(routes.get(i));
				for (int j = 0; j < columnSize; j++) {	
					file.write(log[j] + ",");
					}
				file.write("\n");
			}
			file.close();
			}catch (Exception e) {
				System.out.println("Error Saving Your Routes To LineRoutes.csv.");
				return;
			}

		System.out.println("Routes Saved!");
		}
	public static String[] parseToStringArray(LineRoute lr) { //Purpose: To parse user line route data into an array to allow easy write in to CSV file. Called in saveRoutes()
		String[] log = new String[7];
		log[0] = lr.getOrigin();
		log[1] = lr.getOriginLC().toUpperCase();
		log[2] = lr.getDestination();
		log[3] = lr.getDestinationLC().toUpperCase();
		log[4] = lr.getStop();
		log[5] = String.valueOf(lr.getStations().get(0).getLat()); //latitude for origin
		log[6] = String.valueOf(lr.getStations().get(1).getLat()); //latitude for destination
		return log;
	}

	public static LineRoute addRoute(Scanner input, ArrayList<Station> s){ //Purpose: To generate a route between station to station. Called in routeMenu()
		ArrayList<Station> fromTo = new ArrayList<Station>(); 
		LineRoute lr;
		Station from = null;
		Station to = null;
		String fromLC = "";
		String toLC = "";
		System.out.println("\n-----------[ Origin ]-----------");
		do {
			from = searchStation(input, s);
		}while(from == null);
			try {		
				System.out.println("Available Line(s): " + from.getLineColors());
				System.out.print("Which Line?: ");
				fromLC += input.nextLine();
				while(from.hasPosition(fromLC.toLowerCase()) <= -1) {
					fromLC = "";
					System.out.print("Invalid Line. Try Again: ");
					fromLC += input.nextLine();
					}
			}catch(Exception e) {
					System.out.println("Try again.");
					}	
		System.out.println("\n#####################");
		System.out.println("Station Origin Added!");
		System.out.println("#####################");
		System.out.println("\n-----------[ Destination ]-----------");
		do {
			to = searchStation(input, s);
			try {
				System.out.println("Available Line(s): " + to.getLineColors());
				System.out.print("Which Line?: ");
				to.getLineColors();
				toLC += input.nextLine();
				while(to.hasPosition(toLC.toLowerCase()) <= -1) {
					toLC = "";
					System.out.print("Invalid Line. Try Again: ");
					toLC += input.nextLine();
					}
			}catch(Exception e) {
				System.out.println("Try again.");
				}
			}while(to == null);
		System.out.println("\n##########################");
		System.out.println("Station Destination Added!");
		System.out.println("##########################");
		fromTo.add(from);
		fromTo.add(to);
		lr = new LineRoute(fromTo,fromLC,toLC);
		lr.setStop(findPath(s, lr));
		System.out.println("\n################");
		System.out.println("Route Generated!");
		System.out.println("################");
		return lr;
		
		
	}
	public static int takeRoute(Scanner input, ArrayList<LineRoute> route) { //Purpose: Allows users to choose their route to take. Returns index and then deleted in routeMenu()
		int choice = 0; 
		do {
			int num = 0;
			System.out.println("\nChoose A Route");
			for(LineRoute lr : route) {
				System.out.println("******************["+(++num)+"]*********************");
				System.out.println(lr.toString());
				System.out.println("*************************************");
			}
			System.out.print("Choice: ");
			try {
				choice = Integer.parseInt(input.nextLine());
				if(choice == 0) {
					System.out.println("Invalid Input. Try Again.");
				}
			}catch(Exception e) {
				System.out.println("Invalid Input. Try Again.");
				}
			}while(choice > route.size()  || choice <= 0);
		return (choice - 1);
	}
	public static void modifyRoute(Scanner input, ArrayList<LineRoute> currentRoute, ArrayList<Station> station) {//Purpose: Allows user to modify their route and generate a new route after modification.
		boolean done = false;
		int routeIndex = takeRoute(input, currentRoute);
		Station newOrigin = null;
		Station newDestination = null;
		LineRoute newRoute = null;
	
		do {
			System.out.println("\nWhat Would You Like To Modify?");
			System.out.println("1.) Origin");
			System.out.println("2.) Destination");
			System.out.println("3.) Return To Menu");
			System.out.print("Choice: ");	
			String choice = input.nextLine();
			String fromLine = "";
			String toLine = "";
			ArrayList<Station> newStations = new ArrayList<Station>();
			newStations.add(newOrigin);
			newStations.add(newDestination);
			switch (choice) {
			case "1":	
				System.out.println("**** New Origin ****");
				do {
					newOrigin = searchStation(input, station); //search for new origin
				}while(newOrigin == null);	//search while searchStation doesn't return a station
				newStations.remove(0);
				newStations.add(0,newOrigin); //Add the station that user search for into newStation Array List
				newStations.remove(1);
				newStations.add(1,currentRoute.get(routeIndex).getStations().get(1)); //then add current destination from ArrayList<Route>. Destination is at index 1.
				fromLine += chooseLine(input, newOrigin); //chooseLine() is called for user to choose their line for travel.
				toLine += currentRoute.get(routeIndex).getDestinationLC(); //get current
				newRoute = new LineRoute(newStations,fromLine,toLine);
				newRoute.setStop(findPath(station, newRoute));
				currentRoute.remove(routeIndex);
				currentRoute.add(routeIndex,newRoute);
				newRoute = null;
				System.out.println("Done.");
				break;
			case "2":
				System.out.println("**** New Destination****");
				do {
					newDestination = searchStation(input,station);
				}while(newDestination == null);
				newStations.remove(1);
				newStations.add(1,newDestination); //Add the station that user searches for into newStation Array List
				newStations.remove(0);
				newStations.add(0,currentRoute.get(routeIndex).getStations().get(0)); //then add current origin from ArrayList<Route>. Destination is at index 1.
				toLine = chooseLine(input, newDestination); //chooseLine() is called for user to choose their line for travel.
				fromLine = currentRoute.get(routeIndex).getOriginLC(); //get current origin line color
				newRoute = new LineRoute(newStations,fromLine,toLine);
				newRoute.setStop(findPath(station, newRoute));
				currentRoute.remove(routeIndex);
				currentRoute.add(routeIndex,newRoute);
				newRoute = null;
				System.out.println("Done.");
				break;
			case "3":
				done = true;
				break;
			default:
				System.out.println("Not a valid option, try again.");
				break;
				}
		}while(!done);
	}
	public static void viewRoutes(ArrayList<LineRoute> routes) { //Purpose: To print out user routes to console. Called in deleteRoute() and routeMenu()
		int num = 0;
		for(LineRoute route : routes) {
			System.out.println("**************[" + (++num) + "]**************");
			System.out.println(route.toString());
			System.out.println("*******************************");
		}
	}
	public static void deleteRoute(Scanner input, ArrayList<LineRoute> route) { //Purpose: To delete a specific route.
		int choice = 0;
		do {
			System.out.print("\n");
			viewRoutes(route);
			System.out.print("Choose a Route:");
			try {
				choice = Integer.parseInt(input.nextLine());
				route.remove(choice - 1);
				}catch(Exception e) {
				System.out.println("Invalid Input.");
				}
			}while(choice <= 0 || choice > route.size());
	}
	public static String initMenu(Scanner input) { //This is the initial menu called in main() in which the user choose their desired menu choices.
		boolean done = false;
		System.out.print("-------------------");
		do {
			System.out.println("\n   Choose a Menu");
			System.out.println("-------------------");
			System.out.println("1.) CTA Stations");
			System.out.println("2.) CTA Routes");
			System.out.println("3.) Exit");
			System.out.print("Choice: ");
			String choice = input.nextLine();
			switch (choice) {
			case "1":
				return "1";
			case "2":
				return "2";
			case "3":
				return "3";			
			default:
				System.out.println("Not a valid option, try again.");
				break;
		}
		}while(!done);
		return null;

	}
	public static ArrayList<LineRoute> routeMenu(Scanner input, ArrayList<LineRoute> routes, ArrayList<Station> stations){ //Purpose: Continuous menu where user manipulates route data. Called in main()
		boolean done = false;
		do {
			System.out.println("\n1.) Create a Route");
			System.out.println("2.) Modify a Route");
			System.out.println("3.) View Routes");
			System.out.println("4.) Delete a Route");
			System.out.println("5.) Take a Route");
			System.out.println("6.) Exit");
			System.out.print("Choice: ");
			String choice = input.nextLine();
			switch (choice) {
				case "1":
					LineRoute lr = addRoute(input, stations);
					routes.add(lr);
					break;
				case "2":
					if(routes.size() == 0) {
						System.out.println("No Routes Available.");
						break;
					}
					modifyRoute(input,routes,stations);
					break;
				case "3":
					if(routes.size() == 0) {
						System.out.println("No Routes Available.");
						break;
					}
					viewRoutes(routes);
					break;
				case "4":
					if(routes.size() == 0) {
						System.out.println("No Routes Available.");
						break;
					}
					deleteRoute(input,routes);
					System.out.println("Done.");
					break;
				case "5":	
					if(routes.size() == 0) {
						System.out.println("No Routes Available.");
						break;
					}
					int i = takeRoute(input,routes);
					routes.get(i).takeRoute();
					routes.remove(i);	
					break;
				case "6":
					done = true;
					break;					
				default:
					System.out.println("Not a valid option, try again.");
					break;
			}
		} while (!done);

		return routes;
	}
	public static ArrayList<Station> stationMenu(Scanner input, ArrayList<Station> stations) { ////Purpose: Continuous menu where user manipulates station data. Called in main()
		
		boolean done = false;
			do {
				System.out.println("\n1.) Add Station");
				System.out.println("2.) Search Station");
				System.out.println("3.) Find Nearest Station");
				System.out.println("4.) Modify Station");
				System.out.println("5.) Delete Station");
				System.out.println("6.) Exit");
				System.out.print("Choice: ");	
				String choice2 = input.nextLine();
				switch (choice2) {
					case "1":
						stations = addStation(input, stations);
						break;
					case "2":
						String searchBy = searchByOption(input);
						if(searchBy.equals("name")) {
							Station s = searchStation(input, stations);
							while(s == null) {
								s = searchStation(input, stations);
							}
							System.out.print("\n*******************************\n");
							System.out.println(s.toString());
							System.out.println("*******************************");
							}
						else if(searchBy.equals("line")) {
							searchByLine(input, stations);
						}
						break;
					case "3":
						nearestStation(input,stations);
						break;
					case "4":
						modifyStation(input,stations);
						break;
					case"5":
						deleteStation(input,stations);
						break;
					case "6":
						done = true;
						break;					
					default:
						System.out.println("Not a valid option, try again.");
						break;
						}
				} while(!done);
			return stations;
			}
	public static void welcomeToCTA() {
		try {
			System.out.println("Welcome To The CTA Manager Tool.\n");
			Thread.sleep(1200);
			System.out.print("Manage CTA Stations.");
			Thread.sleep(1100);
			System.out.print(" Routes.");
			Thread.sleep(1000);
			System.out.print(" And More!\n");
		}catch(Exception e) {
			
		}
	}

	public static void main(String[] args) throws IOException {
		ArrayList<Station> stations = readStations();
		ArrayList<LineRoute> routes = readRoutes(stations);
		Scanner input = new Scanner(System.in);
		
		boolean managingMenu = true;
		welcomeToCTA();
		while(managingMenu) {
			switch(initMenu(input)) {
				case "1":
					stations = stationMenu(input, stations);
					break;
				case "2":
					routes = routeMenu(input, routes, stations);
					break;
				case "3":
					managingMenu = false;
					break;
					}
			}
		saveStations(stations);
		saveRoutes(routes);
		System.out.println("Exiting.");
		input.close();
		}
	}