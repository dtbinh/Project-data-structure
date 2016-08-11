package logic;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.Conector;
import db.Querys;

public class Management {
	private Graph graph;
	
	public void initialize () {
		graph = new Graph();
		
		try{
			ResultSet vertexResults = executeQuery(Querys.GET_ALL_VERTEX);
			
			while(vertexResults.next()){
				graph.insertVertex(
						vertexResults.getInt("id"), 
						vertexResults.getString("name"), 
						vertexResults.getInt("x"), 
						vertexResults.getInt("y"));
			}
		}catch(Exception e){ e.printStackTrace(); }
		
		try{
			ResultSet arcResults = executeQuery(Querys.GET_ALL_ARC);
			
			while(arcResults.next()){
				
				
				/*graph.insertArc(
						arcResults.getInt("id"), 
						arcResults.getInt("distance"), 
						arcResults.getInt("origin"), 
						arcResults.getInt("end"));*/
			}
		}catch(Exception e){ e.printStackTrace(); }
		
		graph.fillHash();
	}
	
	public void insertVertex(String name, int positionX, int positionY) {
		try{
			String[] params = new String[]{
				":name="+name,
				":x="+positionX,
				":y="+positionY
			};
			executeQuery(Querys.CREATE_VERTEX, params);
			
			int id = executeQuery(Querys.LAST_VERTEX).getInt("id");
			graph.insertVertex(id, name, positionX, positionY);
		}catch(Exception e){ e.printStackTrace(); }
	}
	
	public int insertArc(String origin, String destination, int distance) {
		int result = 0;
		
		try{
			String[] params = new String[]{
				":distance="+distance,
				":origin="+graph.getVertexId(origin),
				":end="+graph.getVertexId(destination)
			};
			executeQuery(Querys.CREATE_ARC, params);
			
			result = graph.insertArc(origin, destination, distance);
		}catch(Exception e){ e.printStackTrace(); }
		
		return result;
	}
	
	public boolean deleteVertex(String name) {	
		boolean result = false;
		
		try{
			String[] params = new String[]{
				":id="+graph.getVertexId(name)
			};
			executeQuery(Querys.DELETE_ALL_VERTEX_ARC_FROM_ORIGIN, params);
			executeQuery(Querys.DELETE_ALL_VERTEX_ARC_FROM_END, params);
			executeQuery(Querys.DELETE_VERTEX, params);
			
			result = graph.deleteVertex(name);
		}catch(Exception e){ e.printStackTrace(); }
		
		return result;		
	}
	
	public int deleteArc(String origin, String destination) {
		int result = 0;
		
		result = graph.deleteArc(origin, destination);
		
		return result;
	}
	
	public Vertex[] getVertexs(){
		return graph.getVertexs();
	}
	
	private ResultSet executeQuery(Querys query, String... params) throws SQLException, Exception{
		String sql = query.getQuery();
		
		if(params != null){
			for(String param : params){
				String[] split = param.split("="); // :field = value
				String field = split[0].startsWith(":")? split[0] : ":".concat(split[0]);
				sql = sql.replace(field, split[1]);
			}
		}
		
		return Conector.getConector().ejecutarSQL(sql);
	}
}
