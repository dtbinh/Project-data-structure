package ui;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class ArcNode extends Pane {
	
	private int idArc;
	private VertexNode originNode;
	private VertexNode endNode;
	
	public int getIdArc() {
		return idArc;
	}
	
	public void setIdArc(int idArc) {
		this.idArc = idArc;
	}

	public VertexNode getOriginNode() {
		return originNode;
	}
	
	public void setOriginNode(VertexNode originNode) {
		this.originNode = originNode;
	}
	
	public VertexNode getEndNode() {
		return endNode;
	}
	
	public void setEndNode(VertexNode endNode) {
		this.endNode = endNode;
	}
	
	public Line getLineNode(){
		return (Line)this.getChildren().get(0);
	}
	
	public Text getTextNode(){
		return (Text)this.getChildren().get(1);
	}
	
	public String toString(){
		String text = "[" + 
				getOriginNode().getTextNode().getText() + "] -> " + 
				getTextNode().getText() + " -> [" + 
				getEndNode().getTextNode().getText() + 
			"]";
		
		return text;
	}
}
