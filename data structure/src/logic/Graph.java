package logic;

import java.util.ArrayList;

public class Graph {
	static final int MAX_VERTEX = 20;
	private HashTable hash;
	private Vertex vertex[];
	private Arc arc[][];
	boolean[] vertexVisited;
	double[] distances;
	
	public Graph() {
		vertex = new Vertex[MAX_VERTEX];
		arc = new Arc[MAX_VERTEX][MAX_VERTEX];
		
		hash = new HashTable();
		hash.init(MAX_VERTEX);
	}
	
	private int searchIndex(String nameVertex) {
		for (int i = 0; i < MAX_VERTEX; i++) {
			if (vertex[i] != null) {
				if (vertex[i].getName().equals(nameVertex)) {
					return i;
				}
			}
		}
		
		return -1;
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public Vertex searchVertex(int index) {
		return vertex[index];
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public ArrayList<Arc> searchArcs(int index) {
		ArrayList<Arc> arcs = new ArrayList();
		
		for (int i = 0; i < MAX_VERTEX; i++) {
			if (arc[index][i] != null) {
				arcs.add(arc[index][i]);
			}	
		}
		
		return arcs;
	}
	
	public void insertVertex(int id, String name, int positionX, int positionY) {
		for (int i = 0; i < MAX_VERTEX; i++) {
			if (vertex[i] == null) {
				vertex[i] = new Vertex(id, name, positionX, positionY);
				hash.put(i, name);
				break;
			}
		}
	}
	
	/**
	 * Insert arc
	 * @param _origin
	 * @param _destination
	 * @param distance
	 * @return 4 if origin and destination not found. 3 if destination not found. 2 if origin not found. 1 if insert is success.   
	 */
	public int insertArc(int id, String _origin, String _destination, int distance) {
		int origin = searchIndex(_origin);
		int destination = searchIndex(_destination);
		
		if (origin != -1 && destination != -1) {
			arc[origin][destination] = new Arc(id, distance);
			return 1;
			
		} else if (origin == -1 && destination != -1) {
			return 2;
			
		} else if (destination == -1 && origin != -1) {
			return 3;
			
		} else {
			return 4;
		}
	}
	
	/**
	 * 
	 * @param name
	 * @return true if vertex is found, false if vertex not found.
	 */
	public boolean deleteVertex(String name) {
		int indexVertex = searchIndex(name);
		
		if (indexVertex != -1) {
			vertex[indexVertex] = null;
			
			for (int i = 0; i < MAX_VERTEX; i++) {
				if (arc[indexVertex][i] != null)
					arc[indexVertex][i] = null;
			}		
			return true;
			
		} else {
			return false;
		}
	}
	
	/**
	 * Delete arc
	 * @param _origin
	 * @param _destination
	 * @return 4 if origin and destination not found. 3 if destination not found. 2 if origin not found. 1 if insert is success.   
	 */
	public int deleteArc(String _origin, String _destination) {
		int origin = searchIndex(_origin);
		int destination = searchIndex(_destination);
		
		if (origin != -1 && destination != -1) {
			arc[origin][destination] = null;
			return 1;
			
		} else if (origin == -1 && destination != -1) {
			return 2;
			
		} else if (destination == -1 && origin != -1) {
			return 3;
			
		} else {
			return 4;
		}		
	}
	
	public void shortestPath(String origin) {
		int[] lastVisited = new int[MAX_VERTEX];
		int indexOrigin = searchIndex(origin);
		double valueCompare;
		distances = new double[MAX_VERTEX];
		vertexVisited = new boolean[MAX_VERTEX];		
		
		for (int i = 0; i < MAX_VERTEX; i++) {
			vertexVisited[i] = false;
			
			if (arc[indexOrigin][i] == null)
				distances[i] = 9999;
			else
				distances[i] = arc[indexOrigin][i].getDistance();
			
			lastVisited[i] = indexOrigin;
		}
		
		distances[indexOrigin] = 0;
		vertexVisited[indexOrigin] = true;
		
		for (int i = 0; i < MAX_VERTEX; i++) {
			int indexMinDistance = calcMinimun();		
			vertexVisited[indexMinDistance] = true;
			
			for (int j = 0; j < MAX_VERTEX; j++) {
				if (!vertexVisited[j]) {
					if (arc[indexMinDistance][j] == null)
						valueCompare = 9999;
					else 
						valueCompare = distances[indexMinDistance] + arc[indexMinDistance][j].getDistance();
					
					if (valueCompare < distances[j]) {
						distances[j] = distances[indexMinDistance] + arc[indexMinDistance][j].getDistance();
						lastVisited[j] = indexMinDistance;
					}
				}
			}
		}
		
		for (int i = 0; i < MAX_VERTEX; i++) {
			System.out.println("Costo mínimo a " + i + ": " + distances[i]);
		}
	}
	
	public void shortestPath(String origin, String destination) {
		int[] lastVisited = new int[MAX_VERTEX];
		int indexOrigin = searchIndex(origin);
		int indexDestination = searchIndex(destination);
		double valueCompare;
		distances = new double[MAX_VERTEX];
		vertexVisited = new boolean[MAX_VERTEX];		
		
		for (int i = 0; i < MAX_VERTEX; i++) {
			vertexVisited[i] = false;
			
			if (arc[indexOrigin][i] == null)
				distances[i] = 9999;
			else
				distances[i] = arc[indexOrigin][i].getDistance();
			
			lastVisited[i] = indexOrigin;
		}
		
		distances[indexOrigin] = 0;
		vertexVisited[indexOrigin] = true;
		
		for (int i = 0; i < MAX_VERTEX; i++) {
			int indexMinDistance = calcMinimun();		
			vertexVisited[indexMinDistance] = true;
			
			if (!vertexVisited[indexDestination]) {
				if (arc[indexMinDistance][indexDestination] == null)
					valueCompare = 9999;
				else 
					valueCompare = distances[indexMinDistance] + arc[indexMinDistance][indexDestination].getDistance();
				
				if (valueCompare < distances[indexDestination]) {
					distances[indexDestination] = distances[indexMinDistance] + arc[indexMinDistance][indexDestination].getDistance();
					lastVisited[indexDestination] = indexMinDistance;
				}
			}
		}
		
		System.out.println("Costo mínimo a " + destination + ": " + distances[indexDestination]);
	}
	
	private int calcMinimun() {
		double max = 9999;
		int indexMinDistance = 1;
		
		for (int i = 0; i < MAX_VERTEX; i++) {
			if (!vertexVisited[i] && (distances[i] <= max)) {
				max = distances[i];
				indexMinDistance = i;
			}
		}
		return indexMinDistance ;
	}
	
	/**
	 * Linear path for test
	 */
	public void print() {
		for (int i = 0; i < MAX_VERTEX; i++) {
			for (int j = 0; j < MAX_VERTEX; j++) {
				if (arc[i][j] != null) {
					System.out.println("Origen: " + vertex[i].getName());
					System.out.println("Distancia: " + arc[i][j].getDistance());
					System.out.println("Destino: " + vertex[j].getName() + "\n");
				}
			}
		}
	}
	
	public void fillHash() {
		hash.init(MAX_VERTEX);
		
		for (int i=0;i<vertex.length;i++) {
			if (vertex[i] != null)
				hash.put(i, vertex[i].getName());
		}
	}
	
	public int getVertexId(String name){
		Vertex v = getVertex(name);
		return v != null? v.getId() : 0;
	}
	
	public Vertex getVertex(String name){
		return vertex[hash.getIndex(name)];
	}
	
	public boolean hasPath(String originVertex, String endVertex){
		Arc arc = this.arc[hash.getIndex(originVertex)][hash.getIndex(endVertex)];
		return arc != null;
	}
	
	public Vertex[] getVertexs(){
		return vertex;
	}

	public Arc[][] getArcs(){
		return arc;
	}
}