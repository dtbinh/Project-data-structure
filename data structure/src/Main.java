import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	private static BufferedReader br;
	private static Graph graph;
	
	public static void main (String [] args) {
		br = new BufferedReader(new InputStreamReader(System.in));
		graph = new Graph();
		printMenu();		
	}
	
	public static void printMenu() {
		int option = 0;
		
		do {
			try {
				System.out.println("1. Insert vertex \n");
				System.out.println("2. Insert arc \n");
				System.out.println("3. Print graph \n");
				System.out.println("4. Exit \n\n");
				System.out.println("Option: ");

				option = Integer.parseInt(br.readLine()); 
				System.out.println("\n\n------------------\n");
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
			case 4: System.exit(0); 
			default: System.out.println("\n Invalid option \n\n"); break;
		}
	}
	
	public static void insertVertex() {
		int id;
		String name;

		try {
			System.out.println("Type a id: ");
			id = Integer.parseInt(br.readLine());
			
			System.out.println("Type a name: ");
			name = br.readLine(); 
			
			graph.insertVertex(id, name);
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		} 
	}
	
	public static void insertArc() {
		int distance;
		String origin;
		String destination;

		try {
			System.out.println("Type a distance: ");
			distance = Integer.parseInt(br.readLine());
			
			System.out.println("Type a origin: ");
			origin = br.readLine(); 
			
			System.out.println("Type a destination: ");
			destination = br.readLine(); 
			
			if (graph.insertArc(origin, destination, distance)) {
				System.out.println("Success insert!");
			} else {
				System.out.println("Fail insert");
			};
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		} 
	}
}
