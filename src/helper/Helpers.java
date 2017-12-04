package helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class Helpers {
	
	// método para lancar excecoes em um dialog
	public static void throwExceptionDialog(Exception ex, String title) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Exception");
		alert.setHeaderText(title);

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
		
	}
	
	//metodo que pega um stage e troca sua cena
	public static void changeScene(Class<?> classe,ActionEvent event, String PATH) throws IOException{
		
		Stage st = (Stage) (((Node)event.getSource()).getScene().getWindow());
		
		AnchorPane telaChamado = (AnchorPane)FXMLLoader.load(classe.getResource(PATH));
        Scene scene = new Scene(telaChamado);
        st.setScene(scene);
        st.show();
        
	}
	
	//metodo que exibe um dialog com input
	public static String inputDialog(String title, String header, String content) {
		
    	TextInputDialog dialog = new TextInputDialog();
    	dialog.setTitle(title);
    	dialog.setHeaderText(header);
    	dialog.setContentText(content);

    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    	    return result.get();
    	}
    	
    	return "";
    	
    }
	
	//metodo que exibe um simples dialog
	public static void simpleDialog(String title, String header, String content) {
		
    	Alert dialog = new Alert(AlertType.INFORMATION);
    	
    	dialog.setTitle(title);
    	dialog.setHeaderText(header);
    	dialog.setContentText(content);
    	
    	dialog.show();
    	
    }
	
	//metodo que exibe mensagem de sucesso
	public static boolean successDialog() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Register Succeed!");
		alert.setContentText("Success. You will be taken to the Login Screen.");

		ButtonType buttonTypeOne = new ButtonType("Ok", ButtonData.YES);

		alert.getButtonTypes().setAll(buttonTypeOne);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne){
			return true;
		}
		
		return false;
	}
	
	
	//metodo helper para criptografar senha
	 public static String passToHash(String pass) {
		 
    	String hashed = null;
    	MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(Config.PASSWORD_ENCRYPTION);
            md.update(pass.getBytes(),0,pass.length());    
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } finally {
			hashed = new BigInteger(1,md.digest()).toString(16);
		}
		return hashed;
		
	  }
	
}
