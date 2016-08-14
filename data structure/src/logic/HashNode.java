package logic;

public class HashNode {

	private HashNode next;
	private int index;
	private String name;
	
	public HashNode(){ }
	
	public HashNode(int index, String name){
		this.index = index;
		this.setName(name);
	}
	
	public HashNode getNext() {
		return next;
	}
	
	public void setNext(HashNode next) {
		this.next = next;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}
}
