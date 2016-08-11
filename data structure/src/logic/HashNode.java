package logic;

public class HashNode {

	private HashNode next;
	private Vertex vertex;
	private int index;
	
	public HashNode(){ }
	
	public HashNode(int index, Vertex vertex){
		this.index = index;
		this.vertex = vertex;
	}
	
	public HashNode getNext() {
		return next;
	}
	
	public void setNext(HashNode next) {
		this.next = next;
	}
	
	public Vertex getVertex() {
		return vertex;
	}
	
	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
}
