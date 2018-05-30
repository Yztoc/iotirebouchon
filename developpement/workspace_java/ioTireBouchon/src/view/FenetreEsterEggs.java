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



public class FenetreEsterEggs extends Application
{

	public static void main(String[] args)
	{
		Application.launch();
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader();
		String fxmlDocPath = "C:\\Users\\yztoc\\eclipse-workspace\\ioTireBouchon\\src\\InterfaceEsterEggs.fxml";
		FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
		AnchorPane root =  loader.load(fxmlStream);
	
		Scene scene = new Scene(root);	
		primaryStage.setScene(scene);
		primaryStage.setTitle("IoTireBouchon: Ester Eggs");
		primaryStage.show();
		
			
	}
}
