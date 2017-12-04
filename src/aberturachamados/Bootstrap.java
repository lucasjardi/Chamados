package aberturachamados;

import javax.swing.JOptionPane;

import util.EntityManagerUtil;
import facade.Facade;
import helper.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Users;

public class Bootstrap extends Application{
	
	private static Facade facade;
	private static String view = Config.PATH_INIT; // Caminho default de Tela
	
	public static void main(String[] args) {
		loadFacade(); // carrega facade na memoria
		checkFile(); // verifica se tem arquivo de usuario salvo
		launch(args); // start aplicacao
	}
	
	private static void loadFacade() {
		try {
			facade = Facade.getInstancia();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Facade Load error.");
		}
	}
	
	private static void checkFile() {
		if(facade.existsCredentials()) {
			
			Users user = facade.getCredentials();
			
			if(user != null) {
				if(facade.login(user.getNick(),user.getSenha(),false,true)) {
					// Caso exista arquivo de usuario e o login e senha corretos, 
					// ele muda caminho da view para o caminho autenticado
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
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);			
			primaryStage.show();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Application error.");
//			e.printStackTrace();
		}
	}
	
	//sobrescrita do metodo stop para ele fechar o Entity manager e a aplicacao toda.
	@Override
	public void stop(){
		EntityManagerUtil.closeEntityManager();
		System.exit(0);
	}
}
