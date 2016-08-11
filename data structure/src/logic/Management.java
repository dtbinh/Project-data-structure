package logic;
public class Management {
	private Graph graph;
	
	public void initialize () {
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
	}
	
	public void insertVertex(String name, int positionX, int positionY) {

		graph.insertVertex(0, name, positionX, positionY);

	}
	
	public int insertArc(String origin, String destination, int distance) {
		int result = 0;
			
		result = graph.insertArc(origin, destination, distance);
		
		return result;
	}
	
	public boolean deleteVertex(String name) {	
		boolean result = false;
		
		result = graph.deleteVertex(name);
		
		return result;		
	}
	
	public int deleteArc(String origin, String destination) {
		int result = 0;
		
		result = graph.deleteArc(origin, destination);
		
		return result;
	}
	
	public Vertex[] getVertexs(){
		return graph.getVertexs();
	}
}
