package logic;

public class Vertex {
	private int id;
	private String name;
	private double positionX;
	private double positionY;
	
	public Vertex(int _id, String _name, double x, double y) {
		this.id = _id;
		this.name = _name;
		this.positionX = x;
		this.positionY = y;
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

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double _positionX) {
		positionX = _positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double _positionY) {
		positionY = _positionY;
	}
	
}
