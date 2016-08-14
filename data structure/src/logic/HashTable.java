package logic;

public class HashTable {
	private HashNode[] table;
	
	public void init(int size){
		table = new HashNode[size];
	}
	
	public void put(int index, String name){
		int hashCode = hash(name);
		
		if(table[hashCode] == null)
			table[hashCode] = new HashNode(index, name);
		else{
			HashNode temp = table[hashCode];
			
			while(temp.getNext() != null)
				temp = temp.getNext();
			
			temp.setNext(new HashNode(index, name));
		}
	}
	
	public int getIndex(String name){
		int hashCode = hash(name);
		HashNode temp = table[hashCode];
		
		while(temp.getNext() != null){
			if(temp.getName().equals(name))
				return temp.getIndex();
			
			temp = temp.getNext();
		}
		
		return (temp != null? temp.getIndex() : -1);
	}
	
	private int hash(String name){
		int hash = name.charAt(0) % Graph.MAX_VERTEX;
		return hash;
	}
}
