package aberturachamados;

import config.Config;
import fachada.Fachada;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Init extends Application{
	
	private static Fachada facade;
	private static String path = Config.PATH_LOGIN;
	
	public static void main(String[] args) {
		loadFacade();
		checkFile();
		launch(args);
	}
	
	private static void loadFacade() {
		try {
			facade = Fachada.getInstancia();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void checkFile() {
		if(facade.existsCredentials()) {
			facade.login(facade.getCredentials().getNick(),facade.getCredentials().getSenha());
		} else {
//			path = Config.PATH_INIT;
			
			System.out.println("no file. first view.");
		}
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource(path));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource(Config.PATH_CSS).toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
