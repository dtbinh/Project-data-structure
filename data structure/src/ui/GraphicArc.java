package ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GraphicArc implements GraphicObject {

	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int distance;
	private boolean visible;
	
	public GraphicArc(GraphicVertex origin, GraphicVertex end, int distance){
		this(origin.getX(), origin.getY(), end.getX(), end.getY(), distance);
	}
	
	public GraphicArc(int x1, int y1, int x2, int y2, int distance){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		visible = true;
	}
	
	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
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
