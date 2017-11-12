package aberturachamados;

import config.Config;
import fachada.Fachada;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Usuario;

public class Bootstrap extends Application{
	
	private static Fachada facade;
	private static String view = Config.PATH_INIT;
	
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
			
			Usuario user = facade.getCredentials();
			
			if(user != null) {
				if(facade.login(user.getNick(),user.getSenha(),false,true)) {
					view = facade.getAuthPath();
				}
			}
		}
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource(view));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource(Config.PATH_CSS).toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
