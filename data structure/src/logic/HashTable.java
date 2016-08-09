package logic;

public class HashTable {
	private HashNode[] table;
	
	public void init(int size){
		table = new HashNode[size];
	}
	
	public void put(Vertex vertex){
		int hashCode = hash(vertex.getName());
		
		if(table[hashCode] == null)
			table[hashCode] = new HashNode(hashCode, vertex);
		else{
			HashNode temp = table[hashCode];
			
			while(temp.getNext() != null)
				temp = temp.getNext();
			
			temp.setNext(new HashNode(hashCode, vertex));
		}
	}
	
	public Vertex get(String name){
		int hashCode = hash(name);
		HashNode temp = table[hashCode];
		
		while(temp.getNext() != null){
			if(temp.getVertex().getName().equals(name))
				return temp.getVertex();
			
			temp = temp.getNext();
		}
		
		return null;
	}
	
	public int getIndex(String name){
		return hash(name);
	}
	
	private int hash(String name){
		int hash = name.charAt(0) % Graph.MAX_VERTEX;
		return hash;
	}
}
