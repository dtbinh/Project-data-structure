package ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GraphicVertex extends Circle implements GraphicObject {

	public static final double SIZE = 50d;
	private String value;
	
	public GraphicVertex(String value, double x, double y){
		super(x, y, SIZE, Color.BLACK);
		this.value = value;
		
		setStroke(Color.AQUAMARINE);
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public void drawSelf(GraphicsContext gc){
		if(isVisible()){
			gc.fillOval(getCenterX(), getCenterY(), getRadius(), getRadius());
			gc.strokeOval(getCenterX(), getCenterY(), getRadius(), getRadius());
			
			gc.fillText(getValue(), getCenterX(), getCenterY());
		}
	}
}


/*package ui;

import javafx.scene.canvas.GraphicsContext;

public class GraphicVertex implements GraphicObject {

	public static final int SIZE = 50;
	private String value;
	private int x;
	private int y;
	private boolean visible;
	
	public GraphicVertex(String value, int x, int y){
		this.value = value;
		this.x = x;
		this.y = y;
		visible = true;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void drawSelf(GraphicsContext gc){
		if(isVisible()){
			gc.fillOval(x, y, SIZE, SIZE);
			gc.strokeText(value, x + (SIZE/2) - 4.8d, y + (SIZE/2) + 3);
		}
	}
}*/
