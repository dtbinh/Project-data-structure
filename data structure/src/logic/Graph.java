package logic;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<Arc> searchArcs(String vertexVal) {
		return searchArcs(searchIndex(vertexVal));
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public List<Arc> searchArcs(int index) {
		List<Arc> arcs = new ArrayList<>();
		
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
				System.out.println("La distancia del vertice en el indice " + i + " es " + distances[i]);
		} 
		
		return distances;
	}

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

	public List<String> getArcs(){
		List<String> arcs = new ArrayList<>();
		
		for(int i=0;i<MAX_VERTEX;i++){
			for(int j=0;j<MAX_VERTEX;j++){
				if(arc[i][j] != null){
					String data = vertex[i].getName() + "," + vertex[j].getName() + "," + arc[i][j].getDistance() + "," + arc[i][j].getId();
					arcs.add(data);
				}
			}
		}
		
		return arcs;
	}

	public List<String> searchPath(String origen, String destino, boolean searchMinimunPath){
		List<String> minimumPath = new ArrayList<>();
		List<String> path = new ArrayList<String>();
		
		searchPath(searchIndex(origen), searchIndex(destino), path, minimumPath, 0, searchMinimunPath);
		
		return minimumPath;
	}
	
	private void searchPath(int indexOrigen, int indexDestino, List<String> path, List<String> minimumPath, int nextArc, boolean searchMinimunPath) {
		if(indexOrigen >= 0 && indexOrigen < MAX_VERTEX && nextArc < MAX_VERTEX){
			Vertex vertex = this.vertex[indexOrigen];
			Arc arc = this.arc[indexOrigen][nextArc];
			String[] temp;
			double sum1 = 0;
			double sum2 = 0;
			
			if(vertex != null){
				if(indexOrigen == indexDestino){
					if (minimumPath.size() == 0) {
						sum1 = 0; sum2 = 0;
						
						for(String v : path) {
							minimumPath.add(v);
							temp = v.split(",");
							sum1 =+ Double.parseDouble(temp[1]);
						}
						
						minimumPath.add(vertex.getName() + ",0.0"); 
					} else  {
						for(String v : path) {
							temp = v.split(",");
							sum2 =+ Double.parseDouble(temp[1]);
						}
						
						if ((searchMinimunPath && sum1 > sum2) || (!searchMinimunPath && sum1 < sum2)) {
							minimumPath.clear();
							
							for(String v : path)
								minimumPath.add(v);
							
							minimumPath.add(vertex.getName() + ",0.0"); 
						}
					}

					searchPath(--indexOrigen, indexDestino, path, minimumPath, ++nextArc, searchMinimunPath);	
					return ;
					
				} else if(arc != null){
					if(!path.contains(vertex.getName() + "," + arc.getDistance())){
						path.add(vertex.getName() + "," + arc.getDistance());
						searchPath(nextArc, indexDestino, path, minimumPath, 0, searchMinimunPath);
						
					} else {					
						for (int i = nextArc + 1; i < MAX_VERTEX; i++) {
							if (this.arc[indexOrigen][i] != null) {
								if (!path.contains(vertex.getName() + "," + this.arc[indexOrigen][i].getDistance()))
									path.remove(vertex.getName() + "," + this.arc[indexOrigen][nextArc].getDistance());
							}
						}
								
						searchPath(indexOrigen, indexDestino, path, minimumPath, ++nextArc, searchMinimunPath);
					}
					return ;
				}
				
				searchPath(indexOrigen, indexDestino, path, minimumPath, ++nextArc, searchMinimunPath);
				return ;
			}
		}
		
		if(indexOrigen < MAX_VERTEX)
			searchPath(++indexOrigen, indexDestino, path, minimumPath, 0, searchMinimunPath);
	}
	
    // Linear path for test
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
}