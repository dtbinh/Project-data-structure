package ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GraphicArc implements GraphicObject {

	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private double distance;
	private boolean visible;
	
	public GraphicArc(GraphicVertex origin, GraphicVertex end, int distance){
		this(origin.getCenterX(), origin.getCenterY(), end.getCenterX(), end.getCenterY(), distance);
	}
	
	public GraphicArc(double x1, double y1, double x2, double y2, double distance){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		visible = true;
	}
	
	public double getX1() {
		return x1;
	}

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public double getY1() {
		return y1;
	}

	public void setY1(double y1) {
		this.y1 = y1;
	}

	public double getX2() {
		return x2;
	}

	public void setX2(double x2) {
		this.x2 = x2;
	}

	public double getY2() {
		return y2;
	}

	public void setY2(double y2) {
		this.y2 = y2;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void drawSelf(GraphicsContext gc){
		if(isVisible()){
			Paint col = gc.getFill();
			gc.setFill(Color.BLUE);
			
			gc.strokeLine(x1 + (GraphicVertex.SIZE/2), y1 + (GraphicVertex.SIZE/2), x2 + (GraphicVertex.SIZE/2), y2 + (GraphicVertex.SIZE/2));
			gc.fillText(String.valueOf(distance), x1 + (x2/2), y1 + (y2/2));
			
			gc.setFill(col);
		}
	}
}
