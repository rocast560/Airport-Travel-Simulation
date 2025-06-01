//
// Name: Castro, Roberto
// Project 5
// Due: Friday May 9, 2025
// Course: cs-2400-03-sp25
//
// Description: Displays a menu for the flights path and gets 
// 
//

import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Flights {

	public static void main(String[] args) {

		GraphInterface<String> routes = new DirectedGraph<>();
		DictionaryInterface<String, String> airports = new HashedDictionary<>();
		
		// Adding airports to the HashedDictionary
		try {	
			Scanner apCodes = new Scanner(new File("US_Airport_Codes.csv"));	// Opening airport csv file.
			
			while (apCodes.hasNextLine()) {
				String nextLine = apCodes.nextLine();
				if (!nextLine.isEmpty()) {
					String[] token = nextLine.split(",");
					airports.add(token[0], token[1]);
					routes.addVertex(token[0]);
				}	
			}
			apCodes.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		// Adding routes for the airport to the PartialDirectedGraph
		try {
			Scanner apRoutes = new Scanner(new File("US_Airports_Routes.csv"));	// Opening airport csv file.

			while (apRoutes.hasNextLine()) {
				String[] token = apRoutes.nextLine().split(",");
				routes.addEdge(token[0], token[1], Double.parseDouble(token[2]));
			}
			apRoutes.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Cheap Flights v0.25s by R. Castro\n");
		
		Scanner s = new Scanner(System.in);
		String CMD = "";
		while (!CMD.equals("e")) {	// Loops and checks for E, Q, D, and H character inputs if those aren't found then reprompts for correct command.

			System.out.print("Command? ");
			String nextCommand = s.nextLine().toLowerCase();

			if (nextCommand.equals("e")) {
				break;
			} else if (nextCommand.equals("q")) {	// Gives the full name of an airport given the abreviation.
				System.out.print("Airport code? ");
				
				String airportCode = s.nextLine();
				String airportValue = airports.getValue(airportCode);
				if (airportValue == null) {
					System.out.println("Airport code unknown");	
				} else {
					System.out.println(airports.getValue(airportCode));	
				}

				
			} else if (nextCommand.equals("d")) {	// Checks for the distance between two airports.
				System.out.print("Airport codes from to? ");
				//s.nextLine();
				String[] verticies = s.nextLine().split(" ");
				if (verticies.length < 2) {
					System.out.println("Only one airport was specified");
					continue;
				}
				String originVertex = verticies[0];
				String endVertex = verticies[1];
				
				String airportCodeOrigin = airports.getValue(originVertex);
				String airportCodeEnd = airports.getValue(endVertex);
				if (airportCodeOrigin == null || airportCodeEnd == null) {
					System.out.println("One or more Airport codes unknown");
					continue;
				}

				StackInterface<String> route = new ArrayStack<>();
				double distance = routes.getCheapestPath(originVertex, endVertex, route);
				if (distance != -1) {
					System.out.printf("The shortest distance between %s and %s is %d:\n", originVertex, endVertex, (int) distance);
					
					while (!route.isEmpty()) {
						String airportCode = route.pop();
						System.out.printf("%s [%s]\n",airports.getValue(airportCode), airportCode);
					}
				} else {
					System.out.println("Airports not connected");
				}
				continue;
			} else if (nextCommand.equals("h")) {
				System.out.println("Q Query the airport information by entering the airport code.\r\n" +
				"D Find the minimum distance between two airports.\r\n" +
				"H Display this message.\r\n" +
				"E Exit");
				continue;
			} else {
				System.out.println("Invalid command");
				continue;
			}

		}
		s.close();

	}

}
