package week5;

import acm.program.ConsoleProgram;
import acm.util.ErrorException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;



public class FlightPlanner extends ConsoleProgram {
	
	public void run() {
		String file = "flights.txt";
		readDataFromFile(file);
		
		
		println("Welcome to Flight Planner!");
		println("Here's a list of all the cities in our database:");
		listCities(cities);
		
		println("Let's plan a round-trip route!");
		String firstCity = readLine("Enter the starting city: ");
		route = new ArrayList<String>();
		route.add(firstCity);
		String thisCity = firstCity;
		
		while (true) {
			String nextStop = getNextStop(thisCity);
			route.add(nextStop);
			if (nextStop.equals(firstCity)) break;
			thisCity = nextStop;
		}
		
		
		showRoute(route);
	}
	
	public void readDataFromFile(String file) {
		routes = new HashMap<String, ArrayList<String>>();
		cities = new ArrayList<String>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while (true) {
				String line = br.readLine();
				if (line == null) break;
				if (line.length() != 0) {
					readDataFromLine(line);
				}
			}
			br.close();
		} catch (IOException e) {
			throw new ErrorException(e);
		}
	}
	
	public void listCities(ArrayList<String> cityList) {
		for(int i = 0; i < cityList.size(); i++) {
			String city = cityList.get(i);
			println(" " + city);
		}
	}
	
	

	
	public String getNextStop(String city) {
		ArrayList<String> stops = getStops(city);
		String nextStop = null;
		
		while (true) {
			String prompt = "Where do you want to go from " + city + "? ";
			
			println("From " + city + " you can fly directly to:");
			listCities(stops);
			nextStop = readLine("Where do you want to go from " + city + "? ");
			
			if(stops.contains(nextStop)) break;
			println("You can't get to that city by a direct flight.");
		}
		return nextStop;
	}
	
	private ArrayList<String> getStops(String arrival) {
		return routes.get(arrival);
	}

	public void readDataFromLine(String line) {
		String arrival = line.substring(0, line.indexOf("-")).trim();
		distinguish(arrival);
		
		String destination = line.substring(line.indexOf(">") + 1).trim();
		distinguish(destination);
		
		getStops(arrival).add(destination);
		
		/*int arrow = line.indexOf("->");
		if (arrow == -1) {
			throw new ErrorException("Error at: " + line);
		}*/
		
		
	}
	
	public void distinguish(String name) {
		if (!cities.contains(name)) {
			cities.add(name);
			routes.put(name, new ArrayList<String>());
			
		}
	}
	



	
	public void showRoute(ArrayList<String> route) {
		println("The route you've chosen is: ");
		for (int i = 0; i < route.size(); i++) {
			if (i > 0) print(" -> ");
			print(route.get(i));
		}
		println();
	}
	
	private ArrayList<String> cities;
	private HashMap<String, ArrayList<String>> routes;
	private ArrayList<String> route;
	
}
