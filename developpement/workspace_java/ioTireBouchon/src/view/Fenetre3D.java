package view;

import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.FichierLog;
import controller.Point;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.ModelPoint;



public class Fenetre3D extends Application
{

	public ArrayList<Point> tabPoint = new ArrayList<Point>();
	
	public static int idMap;
	public static double minLongitude;
	public static double minLatitude;
	public static double maxLongitude;
	public static double maxLatitude;
		
	public Fenetre3D(int idMap,double noLat, double noLng, double seLat, double seLng)
	{
		this.idMap = idMap;
		maxLatitude = noLat;
		maxLongitude = noLng;
		minLatitude = seLat;
		minLongitude = seLng;

		ModelPoint mp = null;
	      try {
	    	  mp = new ModelPoint();
	      } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
	    	  e.printStackTrace();
	      } catch (SQLException e) {
				// TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
	      try {  
	    	  tabPoint = ModelPoint.getAllPointMap(idMap);
	      } catch (SQLException e) {
	    	  // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
	}
	
	
	public static void main(String[] args)
	{
		Application.launch();
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader();
		String fxmlDocPath = "C:\\Users\\yztoc\\eclipse-workspace\\ioTireBouchon\\src\\IoTireBouchonInterfaceFX.fxml";
		FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
		AnchorPane root =  loader.load(fxmlStream);
	

		Pane panelCenter = (Pane)loader.getNamespace().get("paneCenter");
		Button btnMenu = (Button)loader.getNamespace().get("btnMenu");
		ProgressIndicator pIndic = (ProgressIndicator) loader.getNamespace().get("pIndicBattery");
		pIndic.setStyle("-fx-progress-color: rgb(166,166,166);");
		
		Label labelNbPoint = (Label)loader.getNamespace().get("labelNbPoint");
		Label latitude = (Label)loader.getNamespace().get("latitude");
		Label longitude = (Label)loader.getNamespace().get("longitude");

		labelNbPoint.setText("     " + tabPoint.size());
		
		
		btnMenu.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				FenetreMenu m = new FenetreMenu();
				Stage stageMenu = new Stage();
				try {
					m.start(stageMenu);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				primaryStage.close();
		            
			}
			
		});
	
		
		panelCenter.getChildren().add(new Pane3D(tabPoint,latitude,longitude,maxLatitude,maxLongitude,minLatitude,minLongitude).getPane());
		
		
		Scene scene = new Scene(root);	
		primaryStage.setScene(scene);
		primaryStage.setTitle("IoTireBouchon: 0.0.1");
		primaryStage.show();
		
			
	}
}
