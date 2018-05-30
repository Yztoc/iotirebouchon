package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import controller.FichierLog;
import controller.Map;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.ModelMap;
import model.ModelVol;

public class FenetreMenu extends Application {

	    public String url;
	    public boolean i = false;
	    public boolean o = false;
	    public boolean t = false;
		public ModelMap mMap;

	 @Override
	    public void start(Stage primaryStage) throws Exception {

		 /** MENU PRIINCIPAM **/

		 FXMLLoader loader = new FXMLLoader();
		 String fxmlDocPath = "C:\\Users\\yztoc\\eclipse-workspace\\ioTireBouchon\\src\\InterfaceMenuGeneral.fxml";
		 FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
		 AnchorPane paneMenuPrincipal =  loader.load(fxmlStream);
		 Button btnNewMap = (Button)loader.getNamespace().get("btnNewMap");
	     Button btnCharger = (Button) loader.getNamespace().get("btnCharger");
	     Button btnCredit = (Button) loader.getNamespace().get("btnCredit");

		
	     /** MENU MAP **/
		 

		FXMLLoader loaderMap = new FXMLLoader();
		String fxmlDocPathMap = "C:\\Users\\yztoc\\eclipse-workspace\\ioTireBouchon\\src\\InterfaceMenuMap.fxml";
		FileInputStream fxmlStreamMap = new FileInputStream(fxmlDocPathMap);
		AnchorPane paneMenuMap =  loaderMap.load(fxmlStreamMap);
		PaneWeb paneWeb = new PaneWeb();
		Pane paneMapGoogle = (Pane)loaderMap.getNamespace().get("paneMapGoogle");
		Button btnRetour = (Button)loaderMap.getNamespace().get("btnRetour");

		
		
		 /** MENU Charger **/

		 FXMLLoader loaderCharger = new FXMLLoader();
		 String fxmlDocPathCharger = "C:\\Users\\yztoc\\eclipse-workspace\\ioTireBouchon\\src\\InterfaceChargerMap.fxml";
		 FileInputStream fxmlStreamCharger = new FileInputStream(fxmlDocPathCharger);
		 AnchorPane paneMenuCharger =  loaderCharger.load(fxmlStreamCharger);
		 Button btnRetourCharger = (Button)loaderCharger.getNamespace().get("btnRetourCharger");
		 HBox hBoxCharger = (HBox) loaderCharger.getNamespace().get("hBoxCharger");
		 ScrollPane paneScroll = (ScrollPane) loaderCharger.getNamespace().get("scrollPane");
		 
		 /** Menu Credit **/
		 
		 FXMLLoader loaderCredit = new FXMLLoader();
		 String fxmlDocPathCredit = "C:\\Users\\yztoc\\eclipse-workspace\\ioTireBouchon\\src\\InterfaceCredit.fxml";
		 FileInputStream fxmlStreamCredit = new FileInputStream(fxmlDocPathCredit);
		 AnchorPane paneMenuCredit =  loaderCredit.load(fxmlStreamCredit);
		 Button btnRetourCredit = (Button)loaderCredit.getNamespace().get("btnRetourCredit");

		 
		 
		 paneScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		 paneScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		 mMap = new ModelMap();
		 ArrayList<Map> tabMap = mMap.getAllMap();
		 for(int i=0;i<tabMap.size();i++)
		 {
			 Map mapTampon = tabMap.get(i);
			 PaneCardCharger pCard = new PaneCardCharger(mapTampon);
			 hBoxCharger.getChildren().add(pCard.getPane());
			 paneScroll.setContent(hBoxCharger);
			
			 pCard.getButtonSupprimer().setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {

						System.out.println(mapTampon.getIdMap());
						try {
							mMap.supprimerMap(mapTampon.getIdMap(),mapTampon.getVol_idVol());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					/*	Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Suppresion :");
						alert.setHeaderText("Vous est sur le point de supprimer la map : " +  mMapTampon.getNomMap());
						alert.setContentText("Etes-vous sur ? : ");

						ButtonType btnConfirm = new ButtonType("Confirmer");
						ButtonType btnAnnuler = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);

						alert.getButtonTypes().setAll(btnConfirm,btnAnnuler);

						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == btnConfirm)
						{
							
							if(hBoxCharger.getChildren() != null)
							{
								System.out.println("ID : " + mMapTampon.getIdMap());
								
								paneScroll.setContent(null);
								
								hBoxCharger.getChildren().remove(indice);
								System.out.println("NB :" + hBoxCharger.getChildren().toString());
								
								paneScroll.setContent(hBoxCharger);	
							}
							
							
						
										
						} */
						

					}
				
				});
			 
			 
			 
			 pCard.getButtonCharger().setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {

						System.out.println("Charger : " + mapTampon.getIdMap());
						Fenetre3D m = new Fenetre3D(mapTampon.getIdMap(),mapTampon.getNeLat(), mapTampon.getNeLng(), mapTampon.getSoLat(), mapTampon.getSoLng());

						Stage fenetre3d = new Stage();
						try {
							m.start(fenetre3d);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//primaryStage.close();
					}
				
				});

				
		 }
		
		
		paneMapGoogle.getChildren().add(paneWeb);
		
		

		TextField textVol = (TextField)loaderMap.getNamespace().get("textVol");
		TextField textPilote = (TextField)loaderMap.getNamespace().get("textPilote");
		DatePicker textDate = (DatePicker)loaderMap.getNamespace().get("textDate");
		TextField textMap = (TextField)loaderMap.getNamespace().get("textMap");
		
		Button btnValider = (Button)loaderMap.getNamespace().get("btnValider");
		Button btnOuvrir = (Button) loaderMap.getNamespace().get("btnOuvrir");
		
		
		
		
		// PANE NEW //
		
		btnOuvrir.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Chioix du fichier Log");
				
		            fileChooser.setInitialDirectory(
		                new File(System.getProperty("user.home"))
		            );                 
		            fileChooser.getExtensionFilters().addAll(
		                new FileChooser.ExtensionFilter("Tous Log", "*.*"),
		                new FileChooser.ExtensionFilter("Log", "*.log")
		            );
		            
		            
		            File file =  fileChooser.showOpenDialog(primaryStage);
		            url = file.toString();
		            
			}
			
			
		});
		
		btnValider.setOnAction(new EventHandler<ActionEvent>() {
    		@Override public void handle(ActionEvent e) {
	        	
    		 double tabCoord[] = paneWeb.getCoord();
    		 
    		 try {
				ModelVol mVol = new ModelVol();
				ModelMap mMap = new ModelMap();
				
				int idVol = mVol.ajouterVol(textVol.getText(), textPilote.getText(), Date.valueOf(textDate.getValue()), 0, 0, 0);
				int idMap = mMap.ajouterMap(textMap.getText(), Date.valueOf(LocalDate.now()),idVol, tabCoord[0], tabCoord[1], tabCoord[2], tabCoord[3]);
				
				FichierLog fLog = new FichierLog(url);
				fLog.addGpsPointsFromLog(idMap);
				Fenetre3D m = new Fenetre3D(idMap,tabCoord[0], tabCoord[1], tabCoord[2], tabCoord[3]);

				Stage fenetre3d = new Stage();
				m.start(fenetre3d);
				primaryStage.close();
    		 
    		 } catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		 
	        }
	    });
		
			
		
		/** Pane Principal Gestion des transition **/
		
		
		StackPane root = new StackPane(paneMenuPrincipal);
		
		
		// TRANSITION MENU -> NEW | NEW -> MENU // 
		btnNewMap.setOnAction(event -> {
			root.getChildren().add(paneMenuMap);
			double width = root.getWidth();
			KeyFrame start = new KeyFrame(Duration.ZERO,
					new KeyValue(paneMenuMap.translateXProperty(), width),
					new KeyValue(paneMenuPrincipal.translateXProperty(), 0));
			KeyFrame end = new KeyFrame(Duration.seconds(0.4),
					new KeyValue(paneMenuMap.translateXProperty(), 0),
					new KeyValue(paneMenuPrincipal.translateXProperty(), -width));
			Timeline slide = new Timeline(start, end);
			slide.setOnFinished(e -> root.getChildren().remove(paneMenuPrincipal));
			slide.play();
	       });

			btnRetour.setOnAction(event -> {
				root.getChildren().add(paneMenuPrincipal);
				double width = root.getWidth();
				KeyFrame start = new KeyFrame(Duration.ZERO,
						new KeyValue(paneMenuPrincipal.translateXProperty(), -width),
						new KeyValue(paneMenuMap.translateXProperty(), 0));
				KeyFrame end = new KeyFrame(Duration.seconds(0.4),
						new KeyValue(paneMenuPrincipal.translateXProperty(), 0),
						new KeyValue(paneMenuMap.translateXProperty(), width));
				Timeline slide = new Timeline(start, end);
				slide.setOnFinished(e -> root.getChildren().remove(paneMenuMap));
				slide.play();
		       });
	
			
			
			// TRANSITION MENU -> CHARGER | CHARGER -> MENU 
			btnCharger.setOnAction(event -> {
				root.getChildren().add(paneMenuCharger);
				double width = root.getWidth();
				KeyFrame start = new KeyFrame(Duration.ZERO,
						new KeyValue(paneMenuCharger.translateXProperty(), width),
						new KeyValue(paneMenuPrincipal.translateXProperty(), 0));
				KeyFrame end = new KeyFrame(Duration.seconds(0.4),
						new KeyValue(paneMenuCharger.translateXProperty(), 0),
						new KeyValue(paneMenuPrincipal.translateXProperty(), -width));
				Timeline slide = new Timeline(start, end);
				slide.setOnFinished(e -> root.getChildren().remove(paneMenuPrincipal));
				slide.play();
		       });

			btnRetourCharger.setOnAction(event -> {
				root.getChildren().add(paneMenuPrincipal);
				double width = root.getWidth();
				KeyFrame start = new KeyFrame(Duration.ZERO,
						new KeyValue(paneMenuPrincipal.translateXProperty(), -width),
						new KeyValue(paneMenuCharger.translateXProperty(), 0));
				KeyFrame end = new KeyFrame(Duration.seconds(0.4),
						new KeyValue(paneMenuPrincipal.translateXProperty(), 0),
						new KeyValue(paneMenuCharger.translateXProperty(), width));
				Timeline slide = new Timeline(start, end);
				slide.setOnFinished(e -> root.getChildren().remove(paneMenuCharger));
				slide.play();
		       });
			
			
			//CREDIT 
			
			
			btnCredit.setOnAction(event -> {
				root.getChildren().add(paneMenuCredit);
				double width = root.getWidth();
				KeyFrame start = new KeyFrame(Duration.ZERO,
						new KeyValue(paneMenuCredit.translateXProperty(), width),
						new KeyValue(paneMenuPrincipal.translateXProperty(), 0));
				KeyFrame end = new KeyFrame(Duration.seconds(0.4),
						new KeyValue(paneMenuCredit.translateXProperty(), 0),
						new KeyValue(paneMenuPrincipal.translateXProperty(), -width));
				Timeline slide = new Timeline(start, end);
				slide.setOnFinished(e -> root.getChildren().remove(paneMenuPrincipal));
				slide.play();
		       });

			btnRetourCredit.setOnAction(event -> {
				root.getChildren().add(paneMenuPrincipal);
				double width = root.getWidth();
				KeyFrame start = new KeyFrame(Duration.ZERO,
						new KeyValue(paneMenuPrincipal.translateXProperty(), -width),
						new KeyValue(paneMenuCredit.translateXProperty(), 0));
				KeyFrame end = new KeyFrame(Duration.seconds(0.4),
						new KeyValue(paneMenuPrincipal.translateXProperty(), 0),
						new KeyValue(paneMenuCredit.translateXProperty(), width));
				Timeline slide = new Timeline(start, end);
				slide.setOnFinished(e -> root.getChildren().remove(paneMenuCredit));
				slide.play();
		       });
			
			
			
			
			
			
			Scene scene = new Scene(root, 1135, 553);
			
			scene.setOnKeyTyped(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					if(event.getCharacter().equals("i") && i==false)
					{					
						i = true;
					}
					else if(event.getCharacter().equals("o") && i==true && o==false)
					{
						o = true;
					}
					else if(event.getCharacter().equals("t") && i==true && o==true && t==false)
					{
						t = true;
						FenetreEsterEggs fe = new FenetreEsterEggs();

						Stage fenetreFe = new Stage();
						try {
							fe.start(fenetreFe);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("i-o-t");
					}
					if(!event.getCharacter().equals("i") && !event.getCharacter().equals("o")  && !event.getCharacter().equals("t") )
					{
						i = false;
						o = false;
						t = false;
						
					}
					
				}
		          
		    });
			
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("IoTireBouchon");
			primaryStage.show();
	  }
		
	    public static void main(String[] args) {
	        launch(args);
	    }
	
}
