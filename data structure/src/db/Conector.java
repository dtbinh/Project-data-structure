package db;

public class Conector {

	private static AccessDB conectorBD = null;
	
	public static AccessDB getConector() throws java.sql.SQLException, Exception{
		if (conectorBD == null){
			conectorBD = new AccessDB("org.sqlite.JDBC", "jdbc:sqlite:dataStructure.db");
			setCloseConnection();
		}
		
		return conectorBD;
	}
	
	private static void setCloseConnection(){
		Runtime.getRuntime().addShutdownHook(new Thread("closeConn"){
			public void run(){
				try{
					conectorBD.closeConnection();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}
}
