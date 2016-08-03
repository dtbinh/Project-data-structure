package ui;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 
public class App extends Application {
 
	private GraphicsContext graphicsContext;
	private List<GraphicObject> objects;
	private boolean stopLoop;
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Data structures 2");
        
        Group root = new Group();
        Canvas canvas = new Canvas(640, 480);
        graphicsContext = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        initUIObjs();
        
        new Thread("paint"){
        	public void run(){
        		refresh();
        	}
        }.start();
    }
    
    private void initUIObjs(){
        graphicsContext.setFill(Color.RED);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(2);
        graphicsContext.setFont(Font.font("Arial", 18));
        
    	objects = new ArrayList<>();
    	
    	objects.add(new GraphicVertex("A", 20, 20));
    	objects.add(new GraphicVertex("B", 100, 70));
    	objects.add(new GraphicVertex("C", 300, 120));
    	objects.add(new GraphicVertex("D", 250, 100));
    	
    	objects.add(new GraphicArc((GraphicVertex) objects.get(0), (GraphicVertex) objects.get(1), 50));
    	objects.add(new GraphicArc((GraphicVertex) objects.get(1), (GraphicVertex) objects.get(2), 75));
    	objects.add(new GraphicArc((GraphicVertex) objects.get(2), (GraphicVertex) objects.get(3), 15));
    }

    public void refresh(){
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
            
            updateUI(graphicsContext);
            
            try{
                long  gameTime = (lastLoopTime - System.currentTimeMillis() + OPTIMAL_TIME) / 1000000;
                Thread.sleep(gameTime);
            }catch(Exception e){ }
        }
    }
    
    private void updateUI(GraphicsContext gc) {
        gc.clearRect(0, 0, 640, 480);
        
        for(GraphicObject obj : objects)
        	obj.drawSelf(graphicsContext);
    }
    
    public void stopLoop(){
    	this.stopLoop = true;
    }
}