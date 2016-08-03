package db;
import java.sql.*;

public class AccessDB{
	private Connection conn;
	private Statement st;
	
	public AccessDB(String driver, String conexion)throws SQLException, Exception{
		Class.forName(driver);
		conn = DriverManager.getConnection(conexion);
		st = conn.createStatement();
	}
	
	public ResultSet ejecutarSQL(final String sentencia)throws SQLException, Exception{
		ResultSet rs;
		
		if(sentencia.contains("INSERT") || sentencia.contains("UPDATE") || sentencia.contains("DELETE") || sentencia.contains("ALTER") || sentencia.contains("CREATE")){
			st.execute(sentencia);
			rs = null;
		}else{
			rs = st.executeQuery(sentencia);
		}
		
		return rs;
	}
	
	public void iniciarTransaccion() throws SQLException{
		conn.setAutoCommit(false);	
	}
	
	public void terminarTransaccion() throws SQLException{
		conn.setAutoCommit(true);
	}
	
	public void aceptarTransaccion() throws SQLException{
		conn.commit();
	}
	
	public void deshacerTransaccion() throws SQLException{
		conn.rollback();	
	}
	
	public void closeConnection() throws SQLException{
		st.close();
		conn.close();
	}				
}