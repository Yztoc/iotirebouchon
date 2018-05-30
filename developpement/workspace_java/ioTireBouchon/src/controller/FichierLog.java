
package controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import model.ModelPoint;

public class FichierLog {

	public String url; 
	
	
	public FichierLog(String url)
	{
		this.url = url;
	}
	
	public void addGpsPointsFromLog(int idMap)
	{
		try{
			InputStream flux=new FileInputStream(url); 
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			String ligne;
			
			ModelPoint mp = new ModelPoint();
			
			while ((ligne=buff.readLine())!=null){
				String[] parts = ligne.split("(?=,)");
				String typeDeDonne = parts[0]; 
				if(typeDeDonne.equals("GPS2"))
				{
					String latitude = parts[7].replace(", ", "");
					String longitude = parts[8].replace(", ", "");
					System.out.println(typeDeDonne + " Longitude : " + longitude + " Latitude : " + latitude);
					mp.ajouterPoint(Double.valueOf(longitude),Double.valueOf(latitude), 10, idMap);
				}
			}
			buff.close(); 
			}		
			catch (Exception e){
			System.out.println(e.toString());
			}
		
	}
	
}
