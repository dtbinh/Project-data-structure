package ui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class VertexNode extends StackPane {

	private int idVertex;
	private List<ArcNode> arcs;

	public VertexNode(){
		arcs = new ArrayList<>();
	}
	
	public int getIdVertex() {
		return idVertex;
	}

	public void setIdVertex(int idVertex) {
		this.idVertex = idVertex;
	}

	public List<ArcNode> getArcs() {
		return arcs;
	}

	public void setArcs(List<ArcNode> arcs) {
		this.arcs = arcs;
	}
	
	public Circle getCircleNode(){
		return (Circle)this.getChildren().get(0);
	}
	
	public Text getTextNode(){
		return (Text)this.getChildren().get(1);
	}
	
	public boolean containsArc(ArcNode arcNode){
		return arcs.contains(arcNode);
	}
	
	public String toString(){
		return "[" + getTextNode().getText() + "]";
	}
}
