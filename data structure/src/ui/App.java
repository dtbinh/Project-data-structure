package ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
 
public class App extends Application {
 	
	private static Stage primaryStage;
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	App.primaryStage = primaryStage;
        primaryStage.setTitle("Data structures 2");
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/ui/Form.fxml"));
        
        try {
			BorderPane rootLayout = loader.load();
			Scene stage = new Scene(rootLayout);
			primaryStage.setScene(stage);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static ReadOnlyDoubleProperty getAppWidth(){
    	return primaryStage.widthProperty();
    }
    
    public static ReadOnlyDoubleProperty getAppHeight(){
    	return primaryStage.heightProperty();
    }
}