package ui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class FormController {
	
	public static final double SIZE = 25d;

	@FXML
	private Group pGroup;
	
	@FXML
	private TextField txtVertexVal;

	@FXML
	private TextField txtArcDistance;

	@FXML
	private Label lblValue;
	
	@FXML
	private Label lblOrigin;
	
	@FXML
	private Label lblEnd;

	@FXML
	private Label lblDistance;
	
	private boolean originSelect;
	private boolean endSelect;
	private StackPane originVertex;
	private StackPane endVertex;
	
	@FXML
	private void initialize(){
		lblValue.setLabelFor(txtVertexVal);
		lblDistance.setLabelFor(txtArcDistance);
	}
	
	@FXML
	private void addVertex(Event e){
		if(!(e instanceof MouseEvent) && (e instanceof KeyEvent && ((KeyEvent)e).getCode() != KeyCode.ENTER))
			e.consume();
		else{
			String vertexVal = txtVertexVal.getText();
			
			if(vertexVal.length() > 0 && !vertexVal.contains(" ")){
				Circle vertex = new Circle(SIZE);
				Text label = new Text(vertexVal);
				
				StackPane stack = new StackPane();
				stack.getChildren().add(vertex);
				stack.getChildren().add(label);
				
				pGroup.getChildren().add(stack);
		    	txtVertexVal.setText("");
		    	
		    	configureVertex(stack);
			}
		}
	}
	
	@FXML
	private void addArc(){
		String distance = txtArcDistance.getText();
		
		if(originVertex != null && endVertex != null && (distance != null && distance.length() > 0 && !distance.contains(" "))){
			Line line = new Line();
			//Text label = new Text(distance);
			
			Pane stack = new Pane();
			stack.getChildren().add(line);
			//stack.getChildren().add(label);
			
			pGroup.getChildren().add(stack);
			txtArcDistance.setText("");
			
			lblOrigin.setText("Origin vertex: [ ]");
			lblEnd.setText("End vertex: [ ]");
			
			lblOrigin.setTextFill(Color.BLUE);
			lblEnd.setTextFill(Color.BLUE);
			
			configureArc(stack);
			
			originSelect = false;
			endSelect = false;
			
			originVertex = null;
			endVertex = null;
		}
	}
	
	@FXML
	private void pickOrigin(){
		originSelect = true;
		endSelect = false;
		lblOrigin.setText("Origin vertex: [?]");
		lblOrigin.setTextFill(Color.ORANGE);
		
		if(originVertex != null){
			((Circle)originVertex.getChildren().get(0)).setFill(Color.GREEN);
			originVertex = null;
		}
		
		if(lblEnd.getText().contains("?")){
			lblEnd.setTextFill(Color.BLUE);
			lblEnd.setText("Origin vertex: [ ]");
		}
		
		if(lblOrigin.getText().contains("?")){
			lblOrigin.setTextFill(Color.BLUE);
			lblOrigin.setText("Origin vertex: [ ]");
			originSelect = false;
		}
	}
	
	@FXML
	private void pickEnd(){
		originSelect = false;
		endSelect = true;
		lblEnd.setText("End vertex: [?]");
		lblEnd.setTextFill(Color.ORANGE);
		
		if(endVertex != null){
			((Circle)endVertex.getChildren().get(0)).setFill(Color.GREEN);
			endVertex = null;
		}
		
		if(lblOrigin.getText().contains("?")){
			lblOrigin.setTextFill(Color.BLUE);
			lblOrigin.setText("End vertex: [ ]");
		}
		
		if(lblEnd.getText().contains("?")){
			lblEnd.setTextFill(Color.BLUE);
			lblEnd.setText("End vertex: [ ]");
			endSelect = false;
		}
	}
	
	private void configureVertex(StackPane stack){
		Circle circle = (Circle)stack.getChildren().get(0);
		Text text = (Text)stack.getChildren().get(1);
		
		stack.toFront();
		circle.setCursor(Cursor.HAND);
		circle.setFill(Color.GREEN);
		circle.setStroke(Color.BLACK);
		text.setFont(Font.font("Arial", 16));
		text.setMouseTransparent(true);
		
		stack.setOnMouseDragged(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e){
				stack.setTranslateX(stack.getTranslateX() + e.getX());
				stack.setTranslateY(stack.getTranslateY() + e.getY());
			}
		});
		
		stack.setOnMouseReleased(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e){
				if(!originSelect && !endSelect)
					circle.setFill(Color.GREEN);
			}
		});
		
		circle.setOnMousePressed(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e){
				if(!originSelect && !endSelect)
					circle.setFill(Color.DARKGRAY);
			}
		});
		
		circle.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e){
				if(originSelect){
					if(originVertex == null){
						circle.setFill(Color.LIGHTBLUE);
						originVertex = stack;
						lblOrigin.setText("Origin vertex: [" + text.getText() + "]");
						lblOrigin.setTextFill(Color.BLUE);
						originSelect = false;
					}else{
						Circle originCircle = ((Circle)originVertex.getChildren().get(0));
						
						if(originCircle.equals(circle)){
							originVertex = null;
							lblOrigin.setText("Origin vertex: [?]");
							originCircle.setFill(Color.GREEN);
						}else{
							circle.setFill(Color.LIGHTBLUE);
							originVertex = stack;
							lblOrigin.setText("Origin vertex: [" + text.getText() + "]");
							originCircle.setFill(Color.GREEN);
							lblOrigin.setTextFill(Color.BLUE);
							originSelect = false;
						}
					}
				}
				
				if(endSelect){
					if(endVertex == null){
						circle.setFill(Color.DARKBLUE);
						endVertex = stack;
						lblEnd.setText("End vertex: [" + text.getText() + "]");
						lblEnd.setTextFill(Color.BLUE);
						endSelect = false;
					}else{
						Circle endCircle = ((Circle)endVertex.getChildren().get(0));
						
						if(endCircle.equals(circle)){
							endVertex = null;
							lblEnd.setText("End vertex: [?]");
							endCircle.setFill(Color.GREEN);
						}else{
							circle.setFill(Color.DARKBLUE);
							endVertex = stack;
							lblEnd.setText("End vertex: [" + text.getText() + "]");
							endCircle.setFill(Color.GREEN);
							lblEnd.setTextFill(Color.BLUE);
							endSelect = false;
						}
					}
				}
			}
		});
	}
	
	private void configureArc(Pane stack){
		Circle originCircle = (Circle)originVertex.getChildren().get(0);
		Circle endCircle = (Circle)endVertex.getChildren().get(0);
		
		Line line = (Line)stack.getChildren().get(0);
		//Text text = (Text)stack.getChildren().get(1);
		
		stack.toBack();
		stack.setMouseTransparent(true);
		
		originCircle.setFill(Color.GREEN);
		endCircle.setFill(Color.GREEN);
		
		line.setStroke(Color.DARKGOLDENROD);
		line.setStrokeWidth(2);
		line.setStrokeLineCap(StrokeLineCap.ROUND);
		line.getStrokeDashArray().setAll(10.0, 5.0);
		
		line.startXProperty().bind(originVertex.translateXProperty().add(originCircle.layoutXProperty()));
		line.startYProperty().bind(originVertex.translateYProperty().add(originCircle.layoutYProperty()));
		line.endXProperty().bind(endVertex.translateXProperty().add(endCircle.layoutXProperty()));
		line.endYProperty().bind(endVertex.translateYProperty().add(endCircle.layoutYProperty()));
		
		//text.setFont(Font.font("Arial", 16));
		
	}
}
