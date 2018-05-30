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
import controller.Point;

public class ModelMap {

	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	 static final String DB_URL = "jdbc:mysql://localhost/ioTireBouchon";

	 //  Database credentials
	 static final String USER = "root";
	 static final String PASS = "";
	  
	 public ResultSetMetaData rsmd = null;
	 
	 public static Connection conn = null;
	 public static Statement stmt = null;
	
	 public ModelMap() throws ClassNotFoundException, SQLException
	 {
	
	 }
	 
	 public static void init() throws ClassNotFoundException, SQLException
	 {
		 Class.forName("com.mysql.jdbc.Driver");
		 conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
		 stmt = (Statement) conn.createStatement();
		 
	 }
	 
	 public static ArrayList<Map> getAllMap() throws SQLException
	 {
		 try {
			init();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 ArrayList<Map> tabMapRes = new ArrayList<Map>();
		   
		 String sql;
		 sql = "SELECT * FROM Map";
		 ResultSet rs = (ResultSet) stmt.executeQuery(sql);
		   
		 while(rs.next())
		 {
			 int id  = rs.getInt("idMap");
			 String nomMap = rs.getString("nomMap");
			 Date dateGenere = rs.getDate("dateMapGenere");
			 int vol_idVol = rs.getInt("vol_idVol");
			 double neLat = rs.getDouble("noLat");
			 double neLng = rs.getDouble("noLng");
			 double ouLat = rs.getDouble("seLat");
			 double ouLng = rs.getDouble("seLng");
			 
			 tabMapRes.add(new Map(id,nomMap,dateGenere,vol_idVol,neLat,neLng,ouLat,ouLng));
			        
		 }   		
		  
		 return tabMapRes;
	 }
	 
	 
	 public void supprimerMap(int idMap, int idVol) throws SQLException
		{
		 	try {
				init();
				System.out.println("init bdd ");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	try{
		 		String queryVol="DELETE FROM vol WHERE idVol = (?);";
		 		
		 		PreparedStatement prestVol;
		 		prestVol = (PreparedStatement) conn.prepareStatement(queryVol, Statement.RETURN_GENERATED_KEYS);
		 		prestVol.setInt(1,idVol);
		 		prestVol.executeUpdate();
		 		
		 		/*
		 		
				String query="DELETE FROM map WHERE idMap = (?);";
				 
				PreparedStatement prest;
				prest = (PreparedStatement) conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				prest.setInt(1,idMap);
				prest.executeUpdate();
				*/
				
				stmt.close();
				conn.close();
			}catch(SQLException se){
				      //Handle errors for JDBC
				se.printStackTrace();
			}catch(Exception e){
				      //Handle errors for Class.forName
				e.printStackTrace();
			}finally{
				      //finally block used to close resources
				try{
					if(stmt!=null)
						stmt.close();
				}catch(SQLException se2){
				}// nothing we can do
				try{
					if(conn!=null)
						conn.close();
				}catch(SQLException se){
					se.printStackTrace();
				}//end finally try
			}
			   
			System.out.println("Suppression Map : " + idMap);
			
		}
	   
	   
	 public int ajouterMap(String nomMap, Date dateGenere, int idVol,double noLat,double noLng, double seLat, double seLng) throws SQLException
	 {
		 String query="INSERT INTO Map (nomMap, dateMapGenere, vol_idVol, noLat, noLng, seLat, seLng) VALUES  (?, ?, ?, ?, ?, ?, ?);";
		 
		 int last_id = 0;
		 
		 PreparedStatement prest;
		 prest = (PreparedStatement) conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		 prest.setString(1, nomMap);
		 prest.setDate(2, dateGenere);
		 prest.setInt(3, idVol);
		 prest.setDouble(4, noLat);
		 prest.setDouble(5, noLng);
		 prest.setDouble(6, seLat);
		 prest.setDouble(7, seLng);
		 
		 prest.executeUpdate();
		   
		 ResultSet rs = (ResultSet) prest.getGeneratedKeys();
		   
		 if(rs.next())
		 {
			 last_id = rs.getInt(1);
		 }
			
		 return last_id;
	 }
	   
}
