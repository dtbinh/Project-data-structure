package ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GraphicVertex2 extends Circle implements GraphicObject {

	public static final double SIZE = 50d;
	private String value;
	
	public GraphicVertex2(String value, double x, double y){
		super(x, y, SIZE, Color.BLACK);
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public void drawSelf(GraphicsContext gc){
		if(isVisible()){
			gc.strokeOval(getCenterX()-getRadius(), getCenterY()-getRadius(), getRadius(), getRadius());
		}
	}
}
