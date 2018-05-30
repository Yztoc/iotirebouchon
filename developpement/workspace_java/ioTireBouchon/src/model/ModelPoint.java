package model;

import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import controller.Point;

public class ModelPoint {

	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	 static final String DB_URL = "jdbc:mysql://localhost/ioTireBouchon";

	 //  Database credentials
	 static final String USER = "root";
	 static final String PASS = "";
	  
	 public ResultSetMetaData rsmd = null;
	 
	 public static Connection conn = null;
	 public static Statement stmt = null;
	
	 public ModelPoint() throws ClassNotFoundException, SQLException
	 {
		 Class.forName("com.mysql.jdbc.Driver");
		 conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
		 stmt = (Statement) conn.createStatement();
	 }
	   
	   
	 public static ArrayList<Point> getAllPointMap(int idMap) throws SQLException
	 {
		   
		 ArrayList<Point> tabPointRes = new ArrayList<Point>();
		   
		 String sql;
		 sql = "SELECT * FROM Point WHERE map_idMap = " + idMap;
		 ResultSet rs = (ResultSet) stmt.executeQuery(sql);
		   
		 while(rs.next())
		 {
			 int id  = rs.getInt("idPoint");
			 double longitude = rs.getDouble("longitude");
			 double latitude = rs.getDouble("latitude");
			 double hauteur = rs.getDouble("hauteur");
			 tabPointRes.add(new Point(id,longitude,latitude,hauteur,idMap));
			        
		 }   		
		  
		 return tabPointRes;
	 }
	   
	   
	 public int ajouterPoint(double longitude, double latitude, double hauteur,int idMap) throws SQLException
	 {
		 String query="INSERT INTO Point (longitude,latitude,hauteur,map_idMap) VALUES  (?, ?, ?, ?);";
		 
		 int last_id = 0;
		 
		 PreparedStatement prest;
		 prest = (PreparedStatement) conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		 prest.setDouble(1,longitude);
		 prest.setDouble(2,latitude);
		 prest.setDouble(3,hauteur);
		 prest.setInt(4, idMap);
		 prest.executeUpdate();
		   
		 ResultSet rs = (ResultSet) prest.getGeneratedKeys();
		   
		 if(rs.next())
		 {
			 last_id = rs.getInt(1);
		 }
			
		 return last_id;
	 }
	   
	   
}
