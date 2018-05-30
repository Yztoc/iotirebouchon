package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Test extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override public void start(Stage stage) {
        Scene scene = new Scene(new Group(), 400, 400);
        Label l = new Label("Background radius");
        l.setTranslateX(50);
        l.setTranslateY(25);
        StackPane sp = new StackPane();
        sp.setStyle(
                "-fx-background-color:  linear-gradient(from 0% 0% to 100% 100%, rgb(143,59,174),rgb(70,161,231)), white;"
                + "-fx-background-insets: 0, 28;"
                + "-fx-background-radius:  50;"
                + "-fx-ring-width: 22.0;");
        sp.setPrefSize(100, 100);
        sp.setTranslateX(50);
        sp.setTranslateY(50);

    
        ((Group)scene.getRoot()).getChildren().addAll(l, sp);
        stage.setScene(scene);
        stage.show();
    }
}