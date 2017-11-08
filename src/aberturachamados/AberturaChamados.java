package aberturachamados;

import config.Config;
import fachada.Fachada;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.EntityManagerUtil;

public class AberturaChamados extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource(Config.PATH_LOGIN));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource(Config.PATH_CSS).toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() {
		if(Fachada.hibernateStarted) {
			EntityManagerUtil.closeEntityManager();
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}