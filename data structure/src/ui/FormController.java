package ui;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.canvas.*;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class FormController extends Thread {

	@FXML
	private Canvas pCanvas;
	
	@FXML
	private TextField txtVertexVal;
	
	@FXML
	private TextField txtArcOrigin;
	
	@FXML
	private TextField txtArcEnd;
	
	@FXML
	private TextField txtArcDistance;

	private List<GraphicObject> objects;
	private boolean stopLoop;
	
	@FXML
	private void initialize(){
		objects = new ArrayList<>();
		stopLoop = false;
		initObjects();
		start();
	}
	
	@FXML
	private void addVertex(){
		
	}
	
	@FXML
	private void addArc(){
		
	}
	
	private void initObjects(){
		GraphicsContext context = pCanvas.getGraphicsContext2D();
		context.setFill(Color.RED);
		context.setStroke(Color.BLACK);
		context.setLineWidth(2);
		context.setFont(Font.font("Arial", 18));
		
    	objects.add(new GraphicVertex("A", 20, 20));
    	objects.add(new GraphicVertex("B", 100, 70));
    	objects.add(new GraphicVertex("C", 300, 120));
    	objects.add(new GraphicVertex("D", 250, 100));
    	
    	objects.add(new GraphicArc((GraphicVertex) objects.get(0), (GraphicVertex) objects.get(1), 50));
    	objects.add(new GraphicArc((GraphicVertex) objects.get(1), (GraphicVertex) objects.get(2), 75));
    	objects.add(new GraphicArc((GraphicVertex) objects.get(2), (GraphicVertex) objects.get(3), 15));
    }
	
	@Override
	public void run(){
		long lastLoopTime = System.currentTimeMillis();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        long lastFpsTime = 0;
        
        while(!stopLoop){
            long now = System.currentTimeMillis();
            long updateLength = now - lastLoopTime;
            
            lastLoopTime = now;
            lastFpsTime += updateLength;
            
            if(lastFpsTime >= 1000000000){
                lastFpsTime = 0;
            }
            
            updateUI(pCanvas.getGraphicsContext2D());
            
            try{
                long  gameTime = (lastLoopTime - System.currentTimeMillis() + OPTIMAL_TIME) / 1000000;
                Thread.sleep(gameTime);
            }catch(Exception e){ }
        }
	}
	
	private void updateUI(GraphicsContext gc) {
        gc.clearRect(0, 0, 640, 480);
        
        for(GraphicObject obj : objects)
        	obj.drawSelf(gc);
    }
    
    public void stopLoop(){
    	this.stopLoop = true;
    }
}
