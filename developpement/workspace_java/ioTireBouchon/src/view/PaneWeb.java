package view;

import java.net.URL;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

 
public class PaneWeb  extends Pane {

    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();

    public PaneWeb() {

    	final URL urlGoogleMaps = getClass().getResource("map.html");
    	webEngine.load(urlGoogleMaps.toExternalForm());
                          
    	getChildren().add(webView);

        }
    
    	public double[] getCoord()
    	{
    		
    		double nordOuestLat = (double) webEngine.executeScript("getNordOuestLat()");
			double nordOuestLng = (double) webEngine.executeScript("getNordOuestLng()");
			double sudEstLat = (double) webEngine.executeScript("getSudEstLat()");
			double sudEstLng = (double) webEngine.executeScript("getSudEstLng()");
			
			System.out.println("NO lat : " + nordOuestLat + "\n"+"No lng : " + nordOuestLng + "\nSE lat : "+ sudEstLat + "\nSE lng : " + sudEstLng);

    		double res[] = {nordOuestLat,nordOuestLng,sudEstLat,sudEstLng};
    		return res;
    	}

}