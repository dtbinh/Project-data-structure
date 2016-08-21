package logic;

import java.util.ArrayList;

public class Graph {
	static final int MAX_VERTEX = 9;
	private HashTable hash;
	private Vertex vertex[];
	private Arc arc[][];
	private String[] vertexVisited;
	Vertex[] vertexNotVisited;
	double[] distances;
	
	public Graph() {
		vertex = new Vertex[MAX_VERTEX];
		arc = new Arc[MAX_VERTEX][MAX_VERTEX];
		
		hash = new HashTable();
		hash.init(MAX_VERTEX);
	}
	
	public static int getMaxVertex() {
		return MAX_VERTEX;
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
	
	public double[] shortestPath(String origin) {
		int indexOrigin = searchIndex(origin);
		vertexNotVisited = getVertexs();
		int indexMinDistance;
		distances = new double[MAX_VERTEX];
		vertexVisited = new String[MAX_VERTEX];		
		
		for (int i = 0; i < MAX_VERTEX; i++) {
			if (arc[indexOrigin][i] == null)
				distances[i] = 9999;
			else
				distances[i] = arc[indexOrigin][i].getDistance();
		}
		
		distances[indexOrigin] = 0;
		vertexVisited[0] = searchVertex(indexOrigin).getName();
		vertexNotVisited[0] = null;

		for (int i = 0; i < MAX_VERTEX; i++) {
			indexMinDistance = searchPathMinimun();
			
			if (searchVertex(indexMinDistance) != null)
				vertexVisited[i+1] = searchVertex(indexMinDistance).getName();
				
			vertexNotVisited[indexMinDistance] = null;
			
			for (int j = 0; j < MAX_VERTEX; j++) {
				distances[j] = calcMinimun(distances[j], distances[indexMinDistance] + 
						(arc[indexMinDistance][j] == null ? 9999 : arc[indexMinDistance][j].getDistance()));
			}
		}
		
		for (int i = 0; i < getMaxVertex(); i++) {
				System.out.println("la distancia a " + i + " es " + distances[i]);
		} 
		
		return distances;
	}
	/*
	public double[] shortestPath(String origin) {
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
				valueCompare = arc[indexMinDistance][j] == null ? 9999 : arc[indexMinDistance][j].getDistance();
				
				if (!vertexVisited[j] && valueCompare > 0 && distances[indexMinDistance] != 9999 && 
							distances[indexMinDistance] + valueCompare < distances[j]) {
					distances[j] = distances[indexMinDistance] + valueCompare; 
					
					valueCompare = distances[indexMinDistance] + (arc[indexMinDistance][j] == null ? 9999 : arc[indexMinDistance][j].getDistance());
					
					if (valueCompare < distances[j]) {
						distances[j] = valueCompare;
						lastVisited[j] = indexMinDistance;
					} 
				}
			}
		}
		
		return distances;
	}
	*//*
	private int calcMinimun() { 
		double min = 0;
		int indexMaxDistance = 1;
		
		for (int i = 0; i < MAX_VERTEX; i++) {
			if (!vertexVisited[i] && distances[i] <= min) {
				min = distances[i];
				indexMaxDistance = i;
			}
		}
		return indexMaxDistance ;
	} */
	
	/*
	public double shortestPath(String origin, String destination) {
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
					distances[indexDestination] = valueCompare;
					lastVisited[indexDestination] = indexMinDistance;
				}
			}
		}
		
		return distances[indexDestination];
	}
	*/
	private int searchPathMinimun() {
		double max = 9999;
		int indexMinDistance = 1;
		
		for (int i = 0; i < MAX_VERTEX; i++) {
			if (vertexNotVisited[i] != null && distances[i] <= max) {
				max = distances[i];
				indexMinDistance = i;
			}
		}
		return indexMinDistance ;
	}
	
	private double calcMinimun(double distance1, double distance2) {
		return distance1 <= distance2 ? distance1 : distance2;
	}
	
	/*
	public double[] largestPath(String origin) {
		int[] lastVisited = new int[MAX_VERTEX];
		int indexOrigin = searchIndex(origin);
		double valueCompare;
		distances = new double[MAX_VERTEX];
		vertexVisited = new boolean[MAX_VERTEX];		
		
		for (int i = 0; i < MAX_VERTEX; i++) {
			vertexVisited[i] = false;
			
			if (arc[indexOrigin][i] == null)
				distances[i] = -1;
			else
				distances[i] = arc[indexOrigin][i].getDistance();
			
			lastVisited[i] = indexOrigin;
		}
		
		distances[indexOrigin] = 0;
		vertexVisited[indexOrigin] = true;
		
		for (int i = 0; i < MAX_VERTEX; i++) {
			int indexMaxDistance = calcMaximun();		
			vertexVisited[indexMaxDistance] = true;
			
			for (int j = 0; j < MAX_VERTEX; j++) {
				if (!vertexVisited[j]) {
					if (arc[indexMaxDistance][j] == null)
						valueCompare = 0;
					else 
						valueCompare = distances[indexMaxDistance] + arc[indexMaxDistance][j].getDistance();
					
					if (valueCompare > distances[j]) {
						distances[j] = valueCompare;
						lastVisited[j] = indexMaxDistance;
					}
				}
			}
		}
		
		return distances;
	}
	
	public double largestPath(String origin, String destination) {
		int[] lastVisited = new int[MAX_VERTEX];
		int indexOrigin = searchIndex(origin);
		int indexDestination = searchIndex(destination);
		double valueCompare;
		distances = new double[MAX_VERTEX];
		vertexVisited = new boolean[MAX_VERTEX];		
		
		for (int i = 0; i < MAX_VERTEX; i++) {
			vertexVisited[i] = false;
			
			if (arc[indexOrigin][i] == null)
				distances[i] = -1;
			else
				distances[i] = arc[indexOrigin][i].getDistance();
			
			lastVisited[i] = indexOrigin;
		}
		
		distances[indexOrigin] = 0;
		vertexVisited[indexOrigin] = true;
		
		for (int i = 0; i < MAX_VERTEX; i++) {
			int indexMaxDistance = calcMaximun();		
			vertexVisited[indexMaxDistance] = true;
			
			if (!vertexVisited[indexDestination]) {
				if (arc[indexMaxDistance][indexDestination] == null)
					valueCompare = 0;
				else 
					valueCompare = distances[indexMaxDistance] + arc[indexMaxDistance][indexDestination].getDistance();
				
				if (valueCompare > distances[indexDestination]) {
					distances[indexDestination] = valueCompare;
					lastVisited[indexDestination] = indexMaxDistance;
				}
			}
		}
		
		return distances[indexDestination];
	}
	
	private int calcMaximun() { 
		double min = 0;
		int indexMaxDistance = 1;
		
		for (int i = 0; i < MAX_VERTEX; i++) {
			if (!vertexVisited[i] && (distances[i] >= min)) {
				min = distances[i];
				indexMaxDistance = i;
			}
		}
		return indexMaxDistance ;
	}
	*/
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
		return v != null? v.getId() : -1;
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