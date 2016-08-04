package ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
 
public class App extends Application {
 	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Data structures 2");
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/ui/Form.fxml"));
        
        try {
			BorderPane rootLayout = loader.load();
			Scene stage = new Scene(rootLayout);
			primaryStage.setScene(stage);
			primaryStage.show();
			
			FormController ctrl = loader.getController();
			//ctrl.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}