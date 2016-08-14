package db;

public enum Querys {
	LAST_VERTEX("SELECT MAX(id) AS id FROM vertex;"),
	LAST_ARC("SELECT MAX(id) AS id FROM arc;"),
	
	GET_ALL_VERTEX("SELECT id, name, x, y FROM vertex;"),
	GET_ALL_ARC("SELECT a.id, a.distance, o.name AS origin, e.name AS end FROM arc AS a INNER JOIN vertex AS o ON(a.origin = o.id) INNER JOIN vertex AS e ON(a.end = e.id);"),
	
	CREATE_VERTEX("INSERT INTO vertex (name, x, y) VALUES(:name, :x, :y);"),
	CREATE_ARC("INSERT INTO arc (distance, origin, end) VALUES(:distance, :origin, :end);"),
	
	DELETE_VERTEX("DELETE FROM vertex WHERE id = :id;"),
	DELETE_ALL_VERTEX_ARC_FROM_ORIGIN("DELETE FROM arc WHERE origin = :id;"),
	DELETE_ALL_VERTEX_ARC_FROM_END("DELETE FROM arc WHERE end = :id;"),
	DELETE_ARC("DELETE FROM arc WHERE id = :id;"),
	
	UPDATE_VERTEX_POS("UPDATE vertex SET x = :x, y = :y WHERE id = :id;"),
	UPDATE_VERTEX_NAME("UPDATE vertex SET name = :name WHERE id = :id;"),
	
	UPDATE_ARC_DISTANCE("UPDATE arc SET distance = :distance WHERE id = :id;");
	
	// ---------------------------
	
	private String query;
	
	private Querys(String query){
		this.query = query;
	}
	
	public String getQuery(){
		return this.query;
	}
}
