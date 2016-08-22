package logic;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
	private static BufferedReader br;
	private static Graph graph;
	
	public static void main (String [] args) {
		br = new BufferedReader(new InputStreamReader(System.in));
		graph = new Graph();
		

		graph.insertVertex(0, "A", 0, 0);
		graph.insertVertex(0, "B", 0, 0);
		graph.insertVertex(0, "C", 0, 0);
		graph.insertVertex(0, "D", 0, 0);
		graph.insertVertex(0, "E", 0, 0);
		graph.insertVertex(0, "F", 0, 0);
		graph.insertVertex(0, "G", 0, 0);
		
		graph.insertArc(0, "A", "E", 5);
		graph.insertArc(0, "A", "C", 20);
		graph.insertArc(0, "A", "B", 10);

		graph.insertArc(0, "E", "C", 30);
		graph.insertArc(0, "B", "C", 15);

		graph.insertArc(0, "C", "F", 40);
		graph.insertArc(0, "C", "D", 20);
		graph.insertArc(0, "C", "G", 5);
		
		graph.insertArc(0, "F", "G", 25);
		
		graph.insertArc(0, "G", "D", 5);
		
		List<String> result = graph.shortestPath("A", "D");
		
		for(String v: result)
			System.out.println(v);
	}
	
	public static void printMenu() {
		int option = 0;
		
		do {
			try {
				System.out.println("1. Insert vertex");
				System.out.println("2. Insert arc");
				System.out.println("3. Print graph");
				System.out.println("4. Delete vertex \n");
				System.out.println("5. Delete arc \n");
				System.out.println("6. Exit \n");
				System.out.println("Option: ");

				option = Integer.parseInt(br.readLine()); 
				System.out.println("\n------------------\n");
				executeOption(option);
			}
			catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				e.printStackTrace();
			}
		} while (option != 4);
	}
	
	public static void executeOption(int option) {		
		switch (option) {
			case 1: insertVertex(); break;
			case 2: insertArc(); break;
			case 3: graph.print(); break;
			case 4: deleteVertex(); break;
			case 5: deleteArc(); break;
			case 6: System.exit(0); 
			default: System.out.println("\n Invalid option \n"); break;
		}
	}
	
	public static void insertVertex() {
		String name;

		try {
			System.out.println("Type a name: ");
			name = br.readLine(); 
			
			graph.insertVertex(0, name, 0, 0);
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		} 
	}
	
	public static void insertArc() {
		int distance;
		String origin;
		String destination;
		int result;

		try {			
			System.out.println("Type a origin: ");
			origin = br.readLine(); 
			
			System.out.println("Type a destination: ");
			destination = br.readLine(); 
			
			System.out.println("Type a distance: ");
			distance = Integer.parseInt(br.readLine());
			
			result = graph.insertArc(0, origin, destination, distance);
			
			switch (result) {
				case 1: System.out.println("Success insert!"); break; 
				case 2: System.out.println("Fail insert: origin not found."); break; 
				case 3: System.out.println("Fail insert: destination not found."); break; 
				case 4: System.out.println("Fail insert: origin and destination not found."); break; 
				default: System.out.println("Fail insert."); break; 
			}
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		} 
	}
	
	public static void deleteVertex() {
		String name;

		try {
			System.out.println("Type a name: ");
			name = br.readLine(); 
			
			if (graph.deleteVertex(name)) {
				System.out.println("Success delete!");
			} else {
				System.out.println("Fail delete");
			}
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		} 
	}
	
	public static void deleteArc() {
		String originName;
		String destinationName;
		int result;

		try {
			System.out.println("Type a origin name: ");
			originName = br.readLine(); 
			
			System.out.println("Type a destination name: ");
			destinationName = br.readLine(); 
			
			result = graph.deleteArc(originName, destinationName);
			
			switch (result) {
				case 1: System.out.println("Success delete!"); break; 
				case 2: System.out.println("Fail delete: origin not found."); break; 
				case 3: System.out.println("Fail delete: destination not found."); break; 
				case 4: System.out.println("Fail delete: origin and destination not found."); break; 
				default: System.out.println("Fail delete."); break; 
			}			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}  
	}
}
