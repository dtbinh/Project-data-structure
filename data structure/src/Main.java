import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	private static BufferedReader br;
	private static Graph graph;
	
	public static void main (String [] args) {
		br = new BufferedReader(new InputStreamReader(System.in));
		graph = new Graph();
		
		graph.insertVertex(0, "0", 0, 0);
		graph.insertVertex(0, "1", 0, 0);
		graph.insertVertex(0, "2", 0, 0);
		graph.insertVertex(0, "3", 0, 0);
		graph.insertVertex(0, "4", 0, 0);
		
		graph.insertArc("0", "1", 55);
		graph.insertArc("0", "2", 110);
		graph.insertArc("0", "3", 79);
		
		graph.insertArc("1", "3", 70);
		
		graph.insertArc("2", "0", 123);
		graph.insertArc("2", "3", 40);
		graph.insertArc("2", "4", 180);
		
		graph.insertArc("3", "2", 89);
		graph.insertArc("3", "4", 105);
		
		printMenu();		
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
			
			result = graph.insertArc(origin, destination, distance);
			
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
