package logic;

public class Arc {
	private int id;
	private double distance;
	
	public Arc(int id, double d) {
		this.id = id;
		this.distance = d;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double _distance) {
		distance = _distance;
	}
}
