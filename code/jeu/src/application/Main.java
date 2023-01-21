package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	
	 public void start(Stage primaryStage) {
	        try {
	            FXMLLoader loader = new FXMLLoader();
	            BorderPane root =
	                    (BorderPane)loader.load(getClass().getResource("jeu.fxml").openStream());
	            primaryStage.setScene(new Scene(root));
	            primaryStage.show();
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.exit(1);
	        }
	    }

	/*public void start(Stage primaryStage) {
		try {
            Parent root =FXMLLoader.load(getClass().getResource("accueil.fxml"));
            Scene scene= new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	*/
	public static void main(String[] args) {
		launch(args);
	}
}
