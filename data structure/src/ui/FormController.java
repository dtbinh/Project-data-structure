package ui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.Arc;
import logic.Management;
import logic.Vertex;

public class FormController {
	
	public static final double RADIUS = 25d;

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

	// ------------------------
	@FXML
	private StackPane pnlInfo;
	
	@FXML
	private AnchorPane pnlVertex;
	
	@FXML
	private AnchorPane pnlArc;
	
	@FXML
	private TextField txtVertexDescVal;
	
	@FXML
	private ListView<ListArc> lstArcs;
	
	@FXML
	private TextField txtArcDistanceDesc;
	
	@FXML
	private Label shortcutOrigin;
	
	@FXML
	private Label shortcutEnd;
	
	// ------------------------
	private Management gestor;
	private boolean originSelect;
	private boolean endSelect;
	private StackPane originVertex;
	private StackPane endVertex;
	private StackPane selectedVertex;
	private Pane selectedArc;
	
	@FXML
	private void initialize(){
		gestor = new Management();
		
		lblValue.setLabelFor(txtVertexVal);
		lblDistance.setLabelFor(txtArcDistance);
		
		Platform.runLater(new Runnable(){
			public void run(){
				gestor.initialize();
				load();
			}
		});
	}
	
	private void load(){
		Vertex[] vertexs = gestor.getVertexs();
		
		for(Vertex v : vertexs){
			if(v != null){
				StackPane stack = makeVertex(v);
				pGroup.getChildren().add(stack);
			}
		}
		
		/*Arc[][] arcs = new Arc[0][0];
		
		for(Arc[] relations : arcs){
			for(Arc arc : relations){
				if(arc != null){
					//Pane stack = addArc(arc);
					//pGroup.getChildren().add(stack);
				}
			}
		}*/
	}
	
	@FXML
	private void addVertex(Event e){
		if(!(e instanceof MouseEvent) && (e instanceof KeyEvent && ((KeyEvent)e).getCode() != KeyCode.ENTER))
			e.consume();
		else{
			String vertexVal = txtVertexVal.getText();
			
			if(vertexVal.length() > 0 && !vertexVal.contains(" ")){
				double limit = RADIUS * 2;
				double x = limit + Math.random() * (App.getAppWidth().doubleValue() - limit);
				double y = limit + Math.random() * (App.getAppHeight().doubleValue() - limit);
				
				gestor.insertVertex(vertexVal, (int)x, (int)y);
				
				Vertex v = new Vertex(0, vertexVal, x, y);				
				StackPane stack = makeVertex(v);
				pGroup.getChildren().add(stack);
		    	
		    	txtVertexVal.setText("");
			}
		}
	}
	
	private StackPane makeVertex(Vertex v){
		Circle vertex = new Circle(RADIUS);
		Text label = new Text(v.getName());
		
		StackPane stack = new StackPane();
		stack.getChildren().add(vertex);
		stack.getChildren().add(label);
		
		stack.setTranslateX(v.getPositionX());
		stack.setTranslateY(v.getPositionY());
		
    	configureVertex(stack);
    	
    	return stack;
	}
	
	@FXML
	private void addArc(){
		String distance = txtArcDistance.getText();
		
		if(originVertex != null && endVertex != null && (distance != null && distance.length() > 0 && !distance.contains(" "))){
			Arc arc = new Arc(Double.parseDouble(distance));
			Pane stack = makeArc(arc, originVertex, endVertex);
			pGroup.getChildren().add(stack);
			
			Text origin = (Text)originVertex.getChildren().get(1);
			Text end = (Text)endVertex.getChildren().get(1);
			gestor.insertArc(origin.getText(), end.getText(), Integer.parseInt(distance));
			
			txtArcDistance.setText("");
			
			lblOrigin.setText("Origin vertex: [ ]");
			lblEnd.setText("End vertex: [ ]");
			
			lblOrigin.setTextFill(Color.BLUE);
			lblEnd.setTextFill(Color.BLUE);
			
			originSelect = false;
			endSelect = false;
			
			originVertex = null;
			endVertex = null;
		}
	}
	
	private Pane makeArc(Arc arc, StackPane origin, StackPane end){
		Line line = new Line();
		Text label = new Text(String.valueOf(arc.getDistance()));
		
		Pane stack = new Pane();
		stack.getChildren().add(line);
		stack.getChildren().add(label);
		
		configureArc(stack, origin, end);
		
		return stack;
	}
	
	@FXML
	private void pickOrigin(){
		/*if(!originSelect){
			lblOrigin.setTextFill(Color.BLUE);
			lblOrigin.setText("Origin vertex: [ ]");
			originSelect = false;
		}else{*/
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
	}
	
	@FXML
	private void pickEnd(){
		/*if(!endSelect){
			lblEnd.setTextFill(Color.BLUE);
			lblEnd.setText("End vertex: [ ]");
			endSelect = false;
		}else{*/
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
				if(!originSelect && !endSelect){
					if(e.getClickCount() >= 2){
						pnlVertex.setVisible(true);
						pnlArc.setVisible(false);
						pnlInfo.setVisible(true);
						
						txtVertexDescVal.setText(text.getText());
						// llenar lista de arcos
					}
				}
				
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
	
	private void configureArc(Pane stack, StackPane origin, StackPane end){
		Circle originCircle = (Circle)origin.getChildren().get(0);
		Circle endCircle = (Circle)end.getChildren().get(0);
		
		Line line = (Line)stack.getChildren().get(0);
		Text text = (Text)stack.getChildren().get(1);
		
		stack.toBack();
		stack.setMouseTransparent(true);
		
		originCircle.setFill(Color.GREEN);
		endCircle.setFill(Color.GREEN);
		
		line.setStroke(Color.DARKGOLDENROD);
		line.setStrokeWidth(2);
		line.setStrokeLineCap(StrokeLineCap.ROUND);
		line.getStrokeDashArray().setAll(10.0, 5.0);
		
		line.startXProperty().bind(origin.translateXProperty().add(originCircle.layoutXProperty()));
		line.startYProperty().bind(origin.translateYProperty().add(originCircle.layoutYProperty()));
		line.endXProperty().bind(end.translateXProperty().add(endCircle.layoutXProperty()));
		line.endYProperty().bind(end.translateYProperty().add(endCircle.layoutYProperty()));
		
		text.setFont(Font.font("Arial", 16));
		
		double hipotenusa = calcHipotenusa(end, origin);
		double angle = calcAngle(end, origin);
		relocateText(text, end, hipotenusa, angle);
		
		ChangeListener<Number> originChange = new ChangeListener<Number>(){
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double hipotenusa = calcHipotenusa(end, origin);
				double angle = calcAngle(end, origin);
				relocateText(text, end, hipotenusa, angle);
			}
		};
		
		line.startXProperty().addListener(originChange);
		line.startYProperty().addListener(originChange);
		
		ChangeListener<Number> endChange = new ChangeListener<Number>(){
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double hipotenusa = calcHipotenusa(origin, end);
				double angle = calcAngle(origin, end);
				relocateText(text, origin, hipotenusa, angle);
			}
		};
		
		line.endXProperty().addListener(endChange);
		line.endYProperty().addListener(endChange);
	}
	
	private double calcHipotenusa(StackPane origin, StackPane end){
		double cateto1 = end.translateXProperty().doubleValue() - origin.translateXProperty().doubleValue();
		double cateto2 = end.translateYProperty().doubleValue() - origin.translateYProperty().doubleValue();
		double hipotenusa = Math.sqrt(Math.pow(cateto1, 2) + Math.pow(cateto2, 2));
		return hipotenusa;
	}
	
	private double calcAngle(StackPane origin, StackPane end){
		double angle = Math.atan2(
				origin.translateYProperty().doubleValue() - end.translateYProperty().doubleValue(),
				origin.translateXProperty().doubleValue() - end.translateXProperty().doubleValue()
			);
		return angle;
	}
	
	private void relocateText(Text text, StackPane rootNode, double hipotenusa, double angle){
		text.setX(rootNode.translateXProperty().doubleValue() + (-(hipotenusa / 2) * Math.cos(angle)));
		text.setY(rootNode.translateYProperty().doubleValue() + (-(hipotenusa / 2) * Math.sin(angle)));
	}

	// ---------------------
	
	@FXML
	private void hideInfoPanel(){
		pnlInfo.setVisible(false);
	}
	
	@FXML
	private void applyVertexChanges(KeyEvent e){
		
	}
	
	@FXML
	private void deleteVertex(){
		if(selectedVertex != null){
			Text text = (Text)selectedVertex.getChildren().get(1);
			gestor.deleteVertex(text.getText());
			pGroup.getChildren().remove(selectedVertex);
		}
	}
	
	@FXML
	private void clearArcs(){
		
	}
	
	@FXML
	private void gotoArc(){
		
	}
	
	@FXML
	private void applyDistance(KeyEvent e){
		
	}
	
	@FXML
	private void gotoOrigin(){
		
	}
	
	@FXML
	private void gotoEnd(){
		
	}
	
	@FXML
	private void deleteArc(){
		if(selectedArc != null){
			/*Text text = (Text)selectedArc.getChildren().get(1);
			gestor.deleteArc(text.getText());
			pGroup.getChildren().remove(selectedVertex);*/
		}
	}
	
	private static class ListArc{
		private StackPane origin;
		private StackPane end;
		
		public ListArc(StackPane origin, StackPane end){
			this.origin = origin;
			this.end = end;
		}
		
		public StackPane getOrigin(){
			return origin;
		}
		
		public void setOrigin(StackPane origin){
			this.origin = origin;
		}
		
		public StackPane getEnd(){
			return end;
		}
		
		public void setEnd(StackPane end){
			this.end = end;
		}
		
		public String toString(){
			Text textOrigin = (Text)origin.getChildren().get(1);
			Text textEnd = (Text)end.getChildren().get(1);
			
			return textOrigin.getText() + " -> " + textEnd.getText();
		}
	}
}
