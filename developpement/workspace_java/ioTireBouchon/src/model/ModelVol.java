package model;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import controller.Map;
import controller.Vol;

public class ModelVol {
	 
	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	 static final String DB_URL = "jdbc:mysql://localhost/ioTireBouchon";

	 //  Database credentials
	 static final String USER = "root";
	 static final String PASS = "";
	  
	 public ResultSetMetaData rsmd = null;
	 
	 public static Connection conn = null;
	 public static Statement stmt = null;
	
	 public ModelVol() throws ClassNotFoundException, SQLException
	 {
		 Class.forName("com.mysql.jdbc.Driver");
		 conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
		 stmt = (Statement) conn.createStatement();
	 }
	 
	 public static ArrayList<Vol> getAllVol() throws SQLException
	 {
		   
		 ArrayList<Vol> tabVolRes = new ArrayList<Vol>();
		   
		 String sql;
		 sql = "SELECT * FROM Vol";
		 ResultSet rs = (ResultSet) stmt.executeQuery(sql);
		   
		 while(rs.next())
		 {
			 int id  = rs.getInt("idVol");
			 String nomVol = rs.getString("nomVol");
			 String nomPilote = rs.getString("nomPilote");
			 Date dateVol = rs.getDate("dateVol");
			 double dureeVol = rs.getDouble("dureDuVol");
			 int batterieLife = rs.getInt("batterieLife");
			 int puissanceMoteur = rs.getInt("puissanceMoteur");
			 
			 tabVolRes.add(new Vol(id,nomVol,nomPilote,dateVol,dureeVol,batterieLife,puissanceMoteur));
			        
		 }   		
		  
		 return tabVolRes;
	 }
	   
	   
	 public int ajouterVol(String nomVol,String nomPilote, Date dateVol, double dureeVol, int batterieLife,int puissanceMoteur) throws SQLException
	 {
		 String query="INSERT INTO Vol (nomVol,nomPilote,dateVol,dureDuVol,batterieLife,puissanceMoteur) VALUES  (?, ?, ?, ?, ?, ?);";
		 
		 int last_id = 0;
		 
		 PreparedStatement prest;
		 prest = (PreparedStatement) conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		 prest.setString(1, nomVol);
		 prest.setString(2, nomPilote);
		 prest.setDate(3, dateVol);
		 prest.setDouble(4,dureeVol);
		 prest.setInt(5, batterieLife);
		 prest.setInt(6, puissanceMoteur);
		 prest.executeUpdate();
		   
		 ResultSet rs = (ResultSet) prest.getGeneratedKeys();
		   
		 if(rs.next())
		 {
			 last_id = rs.getInt(1);
		 }
			
		 return last_id;
	 }
	 
}
