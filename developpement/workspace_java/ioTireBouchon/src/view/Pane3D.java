package view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import controller.Point;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.ModelPoint;
import view.Test3d.Xform;
import view.Test3d.Xform.RotateOrder;

public class Pane3D extends Pane
{
	
	final Pane panelPrincipal = new Pane();

    final Group root = new Group();
    final Group axisGroup = new Group();
    final Xform world = new Xform();
    
    final Text sphereCoord = new Text();
    final Text cameraCoord = new Text();
    
    final Label latitude;
    final Label longitude;
    
    final Pane panelSphereCoord = new Pane();
    final Pane panelCameraCoord = new Pane();
    
    
    final MenuBar menuBar = new MenuBar(); 
    final BorderPane menuPane = new BorderPane(); 
  
    
    
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();
    final double cameraDistance = 2500;
    final Xform moleculeGroup = new Xform();
    private Timeline timeline;
    boolean timelinePlaying = false;
    double ONE_FRAME = 1.0 / 60.0;
    double DELTA_MULTIPLIER = 200.0;
    double CONTROL_MULTIPLIER = 10.1;
    double SHIFT_MULTIPLIER = 0.1;
    double ALT_MULTIPLIER = 0.5;
    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;

    
    public ArrayList<Point> tabPoint;
    
    public static double minLongitude;
    public static double minLatitude;
    public static double maxLongitude;
    public static double maxLatitude;	
	
    public static double distanceCD; // distance largeur rectangle  
    public static double distanceCB; // distance hauteur rectangle 

    public static double largeurMap;
    public static double hauteurMap;
	
	
	static int size = 600;
	
	
	public Pane3D(ArrayList<Point> tabPoint, Label latitude, Label longitude, double noLat, double noLng, double seLat, double seLng) 
	{
		this.tabPoint = tabPoint;
		
		maxLatitude = noLat;
		maxLongitude = noLng;
		minLatitude = seLat;
		minLongitude = seLng;
		
		this.latitude = latitude;
		this.longitude = longitude;
	
	    distanceCD = coordSpheriqueVersMetre(minLongitude,minLatitude,maxLongitude,minLatitude); // distance largeur rectangle  
 
	    distanceCB =  coordSpheriqueVersMetre(minLongitude,minLatitude,minLongitude,maxLatitude); // distance hauteur rectangle 
	   
	    largeurMap = distanceCD*10;
	    hauteurMap = distanceCB*10;
	    
	    System.out.println("hauteur map : " + hauteurMap);
	    System.out.println("largeur map : " + largeurMap);

	}
	
	public static double coordSpheriqueVersMetre(double lngReference,double latReference,double lng,double lat)
	{
		double R = 6378.137; // Rayon de la terre
		double dLat = Math.abs(latReference * Math.PI / 180 - lat * Math.PI / 180);
		double dLon = Math.abs(lngReference * Math.PI / 180 - lng * Math.PI / 180);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(lat * Math.PI / 180) * Math.cos(lngReference * Math.PI / 180) *
				Math.sin(dLon/2) * Math.sin(dLon/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = R * c * 1000;
	    	    	
		return d;

	}
	    
	public static double[] triangulation(double lonPara,double latPara)
    {
    	/******
	   
	  	maxLong,maxLat  A-------B minLong,maxLat
		    			|       |
		    			|       |
		    			|       |
		maxLong,minLat  D-------C minLong,minLat
		    		
    	******/

		/** DISTANCE EN METRE **/ 
    	
    	double d1B = coordSpheriqueVersMetre(minLongitude,maxLatitude,lonPara,latPara);
    	double d2C = coordSpheriqueVersMetre(minLongitude,minLatitude,lonPara,latPara);
    	double d3D = coordSpheriqueVersMetre(maxLongitude,minLatitude,lonPara,latPara);
    
    	double x = (Math.pow(distanceCB,2.0) - Math.pow(d1B, 2.0) + Math.pow(d2C, 2.0))/(2*distanceCB);
    	double y = (Math.pow(distanceCD,2.0) - Math.pow(d3D, 2.0) + Math.pow(d2C, 2.0))/(2*distanceCD);
    	
    	//System.out.println("X : " + x + " metres ");
    	//System.out.println("Y : " + y + " metres ");
    	
    	/** DISTACE EN PIXEL  **/
    	
    	double ptsX = 10*x;
    	double ptsY = 10*y;
    	
    	
    	//double ptsX = 3780/30*x;
    	//double ptsY = 3780/30*y;
    	
    	double[] tab = {ptsX,ptsY};
    	
    	//System.out.println("ptsX : " + ptsX + " pixels ");
    	//System.out.println("ptsY : " + ptsY + " pixels ");
    	
    	return tab;
    }
    
    private void buildScene() {
    	
        root.getChildren().add(world);
    }

    private void buildCamera() {
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(0);

        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-cameraDistance);
        camera.setTranslateX(-largeurMap/2);
        cameraXform.ry.setAngle(-90);
        cameraXform.rx.setAngle(-45);
        cameraXform.rz.setAngle(0);
    }

    private void buildAxes() 
    {
    	
        
	  	for (int i = 0; i <=hauteurMap; i+=50)
		{	    
		    /***SOL***/
		    Box box3 = new Box(); 
	  		
		    box3.setWidth(1.0); 
		    box3.setHeight(1.0);   
		    box3.setDepth(largeurMap);
	     
		    box3.setTranslateX(-i); 
		    box3.setTranslateY(0); 
		    box3.setTranslateZ(-largeurMap/2);
	
		    /*
		    Box box5 = new Box(); 
	  		
	  		box5.setWidth(1.0); 
	  		box5.setHeight(1.0);   
	  		box5.setDepth(largeurMap);
	     
	  		box5.setTranslateX(0); 
	  		box5.setTranslateY(-i); 
	  		box5.setTranslateZ(-largeurMap/2);*/
		    
		    world.getChildren().addAll(box3);
		}
	  	for (int i = 0; i <=largeurMap; i+=50)
		{

		    Box box4 = new Box(); 
	  		
		    box4.setWidth(hauteurMap); 
		    box4.setHeight(1.0);   
		    box4.setDepth(1.0);
	     
		    box4.setTranslateX(-hauteurMap/2); 
		    box4.setTranslateY(0); 
		    box4.setTranslateZ(-i);
	  	
		    
		    //***ARRIERE***//

	  	/*	Box box1 = new Box(); 
	  		
		    box1.setWidth(hauteurMap); 
		    box1.setHeight(1.0);   
		    box1.setDepth(1.0);
	     
		    box1.setTranslateX(-hauteurMap/2); 
		    box1.setTranslateY(-i); 
		    box1.setTranslateZ(0);*/
		    
		    
		    world.getChildren().addAll(box4);
		
		}
	  	/*
	  	for (int i = 0; i <=size; i+=50)
		{
	  		Box box2 = new Box(); 
	  		
		    box2.setWidth(1.0); 
		    box2.setHeight(size);   
		    box2.setDepth(1.0);
	     
		    box2.setTranslateX(-i); 
		    box2.setTranslateY(-size/2); 
		    box2.setTranslateZ(0);
		    
		  
		    
		    Box box6 = new Box(); 
	  		
		    box6.setWidth(1.0); 
		    box6.setHeight(size);   
		    box6.setDepth(1.0);
	     
		    box6.setTranslateX(0); 
		    box6.setTranslateY(-size/2); 
		    box6.setTranslateZ(-i);
	  		
		    world.getChildren().addAll(box2,box6);
		}
		    
		    */
		
		    
		
		    
		  /**Boder gauche haut vert */ 
	  	
	  	 	Box arretRougeHautGauche = new Box(); 
	  		
	  	 	arretRougeHautGauche.setWidth(4.0); 
	  	 	arretRougeHautGauche.setHeight(4.0);   
	  	 	arretRougeHautGauche.setDepth(size);
	     
	  	 	arretRougeHautGauche.setTranslateX(-size); 
	  	 	arretRougeHautGauche.setTranslateY(-size); 
	  	 	arretRougeHautGauche.setTranslateZ(-size/2);
        
	  	 	final PhongMaterial redMaterial = new PhongMaterial();
	  	 	redMaterial.setSpecularColor(Color.RED);
	  	 	redMaterial.setDiffuseColor(Color.RED);
	  	
	  	 	arretRougeHautGauche.setMaterial(redMaterial);
	     
	  	 	/**Boder gauche haut vert */ 
		  	
	  	 	Box arretVertVertical= new Box(); 
	  		
	  	 	arretVertVertical.setWidth(4.0); 
	  	 	arretVertVertical.setHeight(size);   
	  	 	arretVertVertical.setDepth(4.0);
	     
	  	 	arretVertVertical.setTranslateX(-size); 
	  	 	arretVertVertical.setTranslateY(-size/2); 
	  	 	arretVertVertical.setTranslateZ(-size);
        
		  	final PhongMaterial vertMaterial = new PhongMaterial();
		  	vertMaterial.setSpecularColor(Color.GREEN);
		  	vertMaterial.setDiffuseColor(Color.GREEN);
		  	
		  	arretVertVertical.setMaterial(vertMaterial);
		     
		  	/**Boder gauche haut vert */ 
		  	
		  	 Box arretBleuHautDroit = new Box(); 
		  		
		  	arretBleuHautDroit.setWidth(size); 
		  	arretBleuHautDroit.setHeight(4.0);   
		  	arretBleuHautDroit.setDepth(4.0);
		     
		  	arretBleuHautDroit.setTranslateX(-size/2); 
		  	arretBleuHautDroit.setTranslateY(-size); 
		  	arretBleuHautDroit.setTranslateZ(-size);
       
		  	final PhongMaterial bleuMaterial = new PhongMaterial();
		  	bleuMaterial.setSpecularColor(Color.BLUE);
		  	bleuMaterial.setDiffuseColor(Color.BLUE);
		  	
		  	arretBleuHautDroit.setMaterial(bleuMaterial);
	     
		  	world.getChildren().addAll(arretRougeHautGauche,arretVertVertical,arretBleuHautDroit);

    }

    private void buildChart() {
   
      final PhongMaterial bleuFoncer = new PhongMaterial();
      bleuFoncer.setDiffuseColor(Color.web("#294eff"));

      final PhongMaterial bleuClair = new PhongMaterial();
      bleuClair.setDiffuseColor(Color.web("#28ffff"));
      
      final PhongMaterial vertClair = new PhongMaterial();
      vertClair.setDiffuseColor(Color.web("#844cb7"));
      
      final PhongMaterial jaune = new PhongMaterial();
      jaune.setDiffuseColor(Color.web("#ffff6c"));
      
      final PhongMaterial orange = new PhongMaterial();
      orange.setDiffuseColor(Color.ORANGE);
      
      final PhongMaterial rouge = new PhongMaterial();
      rouge.setDiffuseColor(Color.web("#ff5044"));
    
      
      
      
    
      
      for(int i=0;i<tabPoint.size();i++)
      {
    	 
    	  Sphere sphere = new Sphere();
          
    	  sphere.setRadius(5.0);   
    	  
    	  double[] tabXY = triangulation(tabPoint.get(i).longitude,tabPoint.get(i).latitude);

    	  
          sphere.setTranslateX(-tabXY[0]); 
          
          sphere.setTranslateY(-10*tabPoint.get(i).hauteur);
          
          sphere.setTranslateZ(-tabXY[1]);
          if(sphere.getTranslateY() > -40)
          {
        	  sphere.setMaterial(bleuFoncer);   
          }
          else if(sphere.getTranslateY() <=- 40 && sphere.getTranslateY() > -100)
          {
        	  sphere.setMaterial(bleuClair);   
          }
          else if(sphere.getTranslateY() <= -100 && sphere.getTranslateY()  > -250)
          {
        	  sphere.setMaterial(vertClair);   
          }
          else if(sphere.getTranslateY() <= -250 && sphere.getTranslateY()  > -350)
          {
        	  sphere.setMaterial(jaune);   
          }
          else if(sphere.getTranslateY() <= -350 && sphere.getTranslateY()  > -450)
          {
        	  sphere.setMaterial(orange);   
          }
          else
          {
        	  sphere.setMaterial(rouge);  
          }
          
          sphere.setOnMouseClicked(new EventHandler<MouseEvent>() {
              @Override public void handle(MouseEvent me) {
            	  sphereCoord.setText("Coordonées : Sphère\n" + "X : " + sphere.getTranslateX() + "\nY : " + sphere.getTranslateY()+"\nZ : " + sphere.getTranslateZ());
            	  latitude.setText("Latitude : " + 2);
            	  latitude.setText("Longitude : " + 2);
              }
          });
          world.getChildren().addAll(sphere);
      }
      
      Sphere sphere = new Sphere();
      
	  sphere.setRadius(5.0);   
	 
      sphere.setTranslateX(0); 
      sphere.setTranslateY(0);
      sphere.setTranslateZ(0);
    
      world.getChildren().addAll(sphere);

    }


    private void handleMouse(SubScene scene, final Node root) {
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseDeltaX = (mousePosX - mouseOldX);
                mouseDeltaY = (mousePosY - mouseOldY);

                double modifier = 1.0;
                double modifierFactor = 0.1;

                if (me.isControlDown()) {
                    modifier = 0.1;
                }
                if (me.isShiftDown()) {
                    modifier = 10.0;
                }
                if (me.isPrimaryButtonDown()) {
                    cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX * modifierFactor * modifier * 2.0);  // +
                    cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY * modifierFactor * modifier * 2.0);  // -
                } else if (me.isSecondaryButtonDown()) {
                    double z = camera.getTranslateZ();
                    double newZ = z + mouseDeltaX * modifierFactor * modifier;
                    camera.setTranslateZ(newZ);
                } else if (me.isMiddleButtonDown()) {
                    cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX * modifierFactor * modifier * 3);  // -
                    cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY * modifierFactor * modifier * 3);  // -
                }
                
                
                cameraCoord.setText("Coordonées : Camera\n" + "X : " + cameraXform2.t.getX()  + "\nY : "+ cameraXform2.t.getY()   +"\nZ : " + cameraXform2.t.getZ() );
            }
        });
    }

    private void handleKeyboard(SubScene scene, final Node root) {
        final boolean moveCamera = true;
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Duration currentTime;
                switch (event.getCode()) {
                    case Z:
                        if (event.isShiftDown()) {
                            cameraXform.ry.setAngle(0.0);
                            cameraXform.rx.setAngle(0.0);
                            camera.setTranslateZ(-600);
                        }
                        cameraXform2.t.setX(0.0);
                        cameraXform2.t.setY(0.0);
                        break;
                    case X:
                        if (event.isControlDown()) {
                            if (axisGroup.isVisible()) {
                                axisGroup.setVisible(false);
                            } else {
                                axisGroup.setVisible(true);
                            }
                        }
                        break;
                }
            
            }
        });
        
        
        
        scene.setOnScroll(new EventHandler() {
        

			@Override
			public void handle(Event event) {
				
                
                ScrollEvent ev = (ScrollEvent) event;
         
                if(ev.getDeltaY() > 0)
                {
                	cameraXform2.t.setZ(cameraXform2.t.getZ() + 10.0 * CONTROL_MULTIPLIER);
                }
                else
                {
                	cameraXform2.t.setZ(cameraXform2.t.getZ() - 10.0 * CONTROL_MULTIPLIER);
                }
                cameraCoord.setText("Coordonées : Camera\n" + "X : " + cameraXform2.t.getX() + "\nY : "+ cameraXform2.t.getY()   +"\nZ : " + cameraXform2.t.getZ() );

                event.consume();
				
               
			}
		
        });
          
         
    }


    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
   /* public static void main(String[] args) {
        System.setProperty("prism.dirtyopts", "false");
        launch(args);
    }
*/
    public static class Xform extends Group {

      public enum RotateOrder {
          XYZ, XZY, YXZ, YZX, ZXY, ZYX
      }

      public Translate t  = new Translate(); 
      public Translate p  = new Translate(); 
      public Translate ip = new Translate(); 
      public Rotate rx = new Rotate();
      { rx.setAxis(Rotate.X_AXIS); }
      public Rotate ry = new Rotate();
      { ry.setAxis(Rotate.Y_AXIS); }
      public Rotate rz = new Rotate();
      { rz.setAxis(Rotate.Z_AXIS); }
      public Scale s = new Scale();

      public Xform() { 
          super(); 
          getTransforms().addAll(t, rz, ry, rx, s); 
      }

      public Xform(RotateOrder rotateOrder) { 
          super(); 
          // choose the order of rotations based on the rotateOrder
          switch (rotateOrder) {
          case XYZ:
              getTransforms().addAll(t, p, rz, ry, rx, s, ip); 
              break;
          case XZY:
              getTransforms().addAll(t, p, ry, rz, rx, s, ip); 
              break;
          case YXZ:
              getTransforms().addAll(t, p, rz, rx, ry, s, ip); 
              break;
          case YZX:
              getTransforms().addAll(t, p, rx, rz, ry, s, ip);  // For Camera
              break;
          case ZXY:
              getTransforms().addAll(t, p, ry, rx, rz, s, ip); 
              break;
          case ZYX:
              getTransforms().addAll(t, p, rx, ry, rz, s, ip); 
              break;
          }
      }

      public void setTranslate(double x, double y, double z) {
          t.setX(x);
          t.setY(y);
          t.setZ(z);
      }

      public void setTranslate(double x, double y) {
          t.setX(x);
          t.setY(y);
      }

      // Cannot override these methods as they are final:
      // public void setTranslateX(double x) { t.setX(x); }
      // public void setTranslateY(double y) { t.setY(y); }
      // public void setTranslateZ(double z) { t.setZ(z); }
      // Use these methods instead:
      public void setTx(double x) { t.setX(x); }
      public void setTy(double y) { t.setY(y); }
      public void setTz(double z) { t.setZ(z); }

      public void setRotate(double x, double y, double z) {
          rx.setAngle(x);
          ry.setAngle(y);
          rz.setAngle(z);
      }

      public void setRotateX(double x) { rx.setAngle(x); }
      public void setRotateY(double y) { ry.setAngle(y); }
      public void setRotateZ(double z) { rz.setAngle(z); }
      public void setRx(double x) { rx.setAngle(x); }
      public void setRy(double y) { ry.setAngle(y); }
      public void setRz(double z) { rz.setAngle(z); }

      public void setScale(double scaleFactor) {
          s.setX(scaleFactor);
          s.setY(scaleFactor);
          s.setZ(scaleFactor);
      }

      public void setScale(double x, double y, double z) {
          s.setX(x);
          s.setY(y);
          s.setZ(z);
      }

      // Cannot override these methods as they are final:
      // public void setScaleX(double x) { s.setX(x); }
      // public void setScaleY(double y) { s.setY(y); }
      // public void setScaleZ(double z) { s.setZ(z); }
      // Use these methods instead:
      public void setSx(double x) { s.setX(x); }
      public void setSy(double y) { s.setY(y); }
      public void setSz(double z) { s.setZ(z); }

      public void setPivot(double x, double y, double z) {
          p.setX(x);
          p.setY(y);
          p.setZ(z);
          ip.setX(-x);
          ip.setY(-y);
          ip.setZ(-z);
      }

      public void reset() {
          t.setX(0.0);
          t.setY(0.0);
          t.setZ(0.0);
          rx.setAngle(0.0);
          ry.setAngle(0.0);
          rz.setAngle(0.0);
          s.setX(1.0);
          s.setY(1.0);
          s.setZ(1.0);
          p.setX(0.0);
          p.setY(0.0);
          p.setZ(0.0);
          ip.setX(0.0);
          ip.setY(0.0);
          ip.setZ(0.0);
      }

      public void resetTSP() {
          t.setX(0.0);
          t.setY(0.0);
          t.setZ(0.0);
          s.setX(1.0);
          s.setY(1.0);
          s.setZ(1.0);
          p.setX(0.0);
          p.setY(0.0);
          p.setZ(0.0);
          ip.setX(0.0);
          ip.setY(0.0);
          ip.setZ(0.0);
      }
  }
	

	
	public SubScene getPane()
	{
		 	buildScene(); // Création de la scene 
	        buildCamera(); // Création de la caméra 
	        buildAxes(); // Création du quadrillage
	        buildChart(); // Création des points 

	        
	        sphereCoord.setText("Coordonées : Sphère\n" + "X : 0" + "\nY : 0" +"\nZ : 0");
	        cameraCoord.setText("Coordonées : Camera\n" + "X : 0" + "\nY : 0" +"\nZ : 0");
	        
	        panelSphereCoord.getChildren().add(sphereCoord);
	        panelCameraCoord.getChildren().add(cameraCoord);
	        
	        /** LAYOUT PANEAU GAUCHE **/
	        VBox vboxGauche = new VBox();
	        vboxGauche.setPadding(new Insets(10));
	        vboxGauche.setSpacing(8);
	        vboxGauche.getChildren().addAll(panelSphereCoord,panelCameraCoord);
	        
	        
	  
	        /** DEFINITION PANNEAU GAUCHE TAILLE **/
	        StackPane panelGauche = new StackPane();
	        panelGauche.setPrefWidth(250);
	        panelGauche.getChildren().add(vboxGauche);
	        
	        /** SOUS SCENE POUR ATTRIBUER LA CAMERA JUSTE A CETTE PARTIE **/
	       
	       // panelPrincipal.getChildren().add(root);
	        SubScene subScene = new SubScene(root, 964, 700, true, SceneAntialiasing.BALANCED);
	        subScene.setCamera(camera);
	        subScene.setFill(Color.TRANSPARENT);
	        handleKeyboard(subScene, world);
	        handleMouse(subScene, world);
		
	       return subScene;
	}
	
}