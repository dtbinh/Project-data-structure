package logic;

public class HashNode {

	private HashNode next;
	private int id;
	private int index;
	
	public HashNode(){ }
	
	public HashNode(int id, int index){
		this.id = id;
		this.index = index;
	}
	
	public HashNode getNext() {
		return next;
	}
	
	public void setNext(HashNode next) {
		this.next = next;
	}
	
	public int getId() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
}
