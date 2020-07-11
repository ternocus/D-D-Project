package application;

import controller.StartController;
import javafx.application.Application;
import javafx.stage.Stage;
import persistence.LogFile;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("MithGenesis");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StartPane.fxml"));
			VBox root = loader.load();
			StartController controller = loader.getController();
			controller.inizialize(primaryStage);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			LogFile.writeLog(e.toString());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
