package view.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import model.Usuario;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import config.Config;
import fachada.Fachada;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterController implements Initializable{
	
	private Fachada facade;
	private ActionEvent ev;
	
	@FXML private JFXTextField txtName;
	@FXML private JFXTextField txtNick;
	@FXML private JFXPasswordField txtPassword;
	@FXML private JFXPasswordField txtRepeatPassword;
	
	@FXML private Label errorLabel;
	@FXML private JFXButton btnRegister;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void register(ActionEvent event) throws IOException{
		this.ev = event;
		facade = Fachada.getInstancia();
		
		if(!emptyFields()){
			if( isPassEquals() ){
				facade.saveUser(new Usuario(txtName.getText(), txtNick.getText(), txtPassword.getText(),facade.getPermissionById(2))); //passar saveUser para boolean depois
				loadDialog();
				
			} else{
				errorLabel.setText("Invalid Passwords combination");
			}
		} else {
			errorLabel.setText("Empty Fields. Please complete the fields.");
		}
	}
	
	private boolean emptyFields(){
		return txtName.getText().equals("") || txtNick.getText().equals("") || txtPassword.getText().equals("") || txtRepeatPassword.getText().equals("");
	}
	
	private boolean isPassEquals(){
		return txtPassword.getText().equals(txtRepeatPassword.getText());
	}
	
	private void loadDialog() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Register Succeed!");
		alert.setContentText("Success. You will be taken to the Login Screen.");

		ButtonType buttonTypeOne = new ButtonType("Ok", ButtonData.YES);

		alert.getButtonTypes().setAll(buttonTypeOne);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne){
			goToLogin(ev);
		}
	}
	
	private void goToLogin(ActionEvent event) throws IOException{
		Stage st = (Stage) (((Node)event.getSource()).getScene().getWindow());
		
		AnchorPane telaChamado = (AnchorPane)FXMLLoader.load(getClass().getResource(Config.PATH_LOGIN));
        Scene scene = new Scene(telaChamado);
        st.setScene(scene);
        st.show();
	}
	

}
