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
import javafx.scene.control.ListCell;
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
import javafx.util.Callback;
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
	private ListView<ArcNode> lstArcs;
	
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
	private VertexNode originVertex;
	private VertexNode endVertex;
	private VertexNode selectedVertex;
	private ArcNode selectedArc;
	
	@FXML
	private void initialize(){
		gestor = new Management();
		
		lblValue.setLabelFor(txtVertexVal);
		lblDistance.setLabelFor(txtArcDistance);
		
		lstArcs.setCellFactory(new Callback<ListView<ArcNode>, ListCell<ArcNode>>(){
			public ListCell<ArcNode> call(ListView<ArcNode> param) {
				ListCell<ArcNode> cells = new ListCell<ArcNode>(){
					protected void updateItem(ArcNode item, boolean empty){
						super.updateItem(item, empty);
						if(item != null)
							setText(item.toString());
						else
							setText(null);
					}
				};
				
				return cells;
			}
		});
		
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
	
	private VertexNode makeVertex(Vertex v){
		Circle vertex = new Circle(RADIUS);
		Text label = new Text(v.getName());
		
		VertexNode stack = new VertexNode();
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
	
	private ArcNode makeArc(Arc arc, VertexNode origin, VertexNode end){
		Line line = new Line();
		Text label = new Text(String.valueOf(arc.getDistance()));
		
		ArcNode stack = new ArcNode();
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
	
	private void configureVertex(VertexNode stack){
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
						selectedVertex = stack;
						showVertexInfo(stack);
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
	
	private void configureArc(ArcNode stack, VertexNode origin, VertexNode end){
		origin.getArcs().add(stack);
		
		if(!origin.equals(end))
			end.getArcs().add(stack);
		
		stack.setOriginNode(origin);
		stack.setEndNode(end);
		
		Circle originCircle = (Circle)origin.getChildren().get(0);
		Circle endCircle = (Circle)end.getChildren().get(0);
		
		Line line = (Line)stack.getChildren().get(0);
		Text text = (Text)stack.getChildren().get(1);
		
		stack.toBack();
		stack.setMouseTransparent(true);
		
		originCircle.setFill(Color.GREEN);
		endCircle.setFill(Color.GREEN);
		
		line.setCursor(Cursor.HAND);
		line.setStroke(Color.DARKGOLDENROD);
		line.setStrokeWidth(2);
		line.setStrokeLineCap(StrokeLineCap.ROUND);
		line.getStrokeDashArray().setAll(10.0, 5.0);
		line.setOnMousePressed(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e){
				if(!originSelect && !endSelect){
					if(e.getClickCount() >= 2){
						selectedArc = stack;
						showArcInfo(stack);
					}
				}
			}
		});
		
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
	
	private double calcHipotenusa(VertexNode origin, VertexNode end){
		double cateto1 = end.translateXProperty().doubleValue() - origin.translateXProperty().doubleValue();
		double cateto2 = end.translateYProperty().doubleValue() - origin.translateYProperty().doubleValue();
		double hipotenusa = Math.sqrt(Math.pow(cateto1, 2) + Math.pow(cateto2, 2));
		return hipotenusa;
	}
	
	private double calcAngle(VertexNode origin, VertexNode end){
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
	
	private void showVertexInfo(VertexNode node){
		pnlVertex.setVisible(true);
		pnlArc.setVisible(false);
		pnlInfo.setVisible(true);
		
		txtVertexDescVal.setText(node.getTextNode().getText());
		
		lstArcs.getItems().clear();
		node.getArcs().forEach(a -> lstArcs.getItems().add(a));
	}
	
	private void showArcInfo(ArcNode node){
		selectedArc = node;
		
		pnlVertex.setVisible(false);
		pnlArc.setVisible(true);
		pnlInfo.setVisible(true);
		
		txtArcDistanceDesc.setText(node.getTextNode().getText());
		
		shortcutOrigin.setText(node.getOriginNode().getTextNode().getText());
		shortcutEnd.setText(node.getEndNode().getTextNode().getText());
	}
	
	@FXML
	private void hideInfoPanel(){
		pnlInfo.setVisible(false);
	}
	
	@FXML
	private void applyVertexChanges(KeyEvent e){
		if(selectedVertex != null){
			String newValue = txtVertexDescVal.getText();
			selectedVertex.getTextNode().setText(newValue);
			
			// update in db
		}
	}
	
	@FXML
	private void deleteVertex(){
		if(selectedVertex != null){
			Text text = (Text)selectedVertex.getChildren().get(1);
			gestor.deleteVertex(selectedVertex.getIdVertex(), text.getText());
			pGroup.getChildren().remove(selectedVertex);
			selectedVertex = null;
			lstArcs.getItems().clear();
			hideInfoPanel();
		}
	}
	
	@FXML
	private void clearArcs(){
		lstArcs.getItems().forEach(a -> {
			a.getOriginNode().getArcs().remove(a);
			a.getEndNode().getArcs().remove(a);
			pGroup.getChildren().remove(a);
			gestor.deleteArc(
					a.getIdArc(), 
					a.getOriginNode().getTextNode().getText(), 
					a.getEndNode().getTextNode().getText()
				);
		});
		
		lstArcs.getItems().clear();
	}
	
	@FXML
	private void gotoArc(MouseEvent e){
		if(e.getClickCount() >= 2){
			ArcNode arc = lstArcs.getSelectionModel().getSelectedItem();
			
			if(arc != null)
				showArcInfo(arc);
		}
	}
	
	@FXML
	private void applyDistance(KeyEvent e){
		if(selectedArc != null){
			String newValue = txtArcDistanceDesc.getText();
			selectedArc.getTextNode().setText(newValue);
			
			// update in db
		}
	}
	
	@FXML
	private void gotoOrigin(){
		showVertexInfo(selectedArc.getOriginNode());
	}
	
	@FXML
	private void gotoEnd(){
		showVertexInfo(selectedArc.getEndNode());
	}
	
	@FXML
	private void deleteArc(){
		if(selectedArc != null){
			gestor.deleteArc(
					selectedArc.getIdArc(),
					selectedArc.getOriginNode().getTextNode().getText(), 
					selectedArc.getEndNode().getTextNode().getText()
				);
			pGroup.getChildren().remove(selectedArc);
			
			selectedArc.getOriginNode().getArcs().remove(selectedArc);
			selectedArc.getEndNode().getArcs().remove(selectedArc);
			
			selectedArc = null;
			hideInfoPanel();
		}
	}
	
}
