
public class Vertex {
	private int id;
	private String name;
	private int positionX;
	private int positionY;
	
	public Vertex(int _id, String _name, int _positionX, int _positionY) {
		this.id = _id;
		this.name = _name;
		this.positionX = _positionX;
		this.positionY = _positionY;
	}

	public int getId() {
		return id;
	}

	public void setId(int _id) {
		id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String _name) {
		name = _name;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int _positionX) {
		positionX = _positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int _positionY) {
		positionY = _positionY;
	}
}
