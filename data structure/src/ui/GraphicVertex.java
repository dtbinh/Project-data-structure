package ui;

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
}
