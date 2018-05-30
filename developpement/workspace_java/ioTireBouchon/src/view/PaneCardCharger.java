package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import controller.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.ModelMap;

public class PaneCardCharger {

	Map map;
	Button btnSupprimer;
	Button btnCharger;
	
	public PaneCardCharger(Map map)
	{
		this.map = map;
	}
	
	public Pane getPane() throws IOException, ClassNotFoundException, SQLException
	{
		FXMLLoader loader = new FXMLLoader();
		String fxmlDocPath = "C:\\Users\\yztoc\\eclipse-workspace\\ioTireBouchon\\src\\PaneCardCharger.fxml";
		FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
		AnchorPane root =  loader.load(fxmlStream);
		btnSupprimer = (Button) loader.getNamespace().get("btnSupprimer");
		btnCharger = (Button) loader.getNamespace().get("btnCharger");
		
		Label labelNomMapGet = (Label)loader.getNamespace().get("labelMap");
		Label labelDateGet = (Label)loader.getNamespace().get("labelDate");

		labelNomMapGet.setText(map.getNomMap());
		labelDateGet.setText(String.valueOf(map.dateMapGenere));
		
		
		return root;
	}
	
	Button getButtonSupprimer()
	{
		return btnSupprimer;
	}
	
	Button getButtonCharger()
	{
		return btnCharger;
	}
	
	
	
}
