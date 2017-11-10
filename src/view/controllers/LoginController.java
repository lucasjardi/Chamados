package view.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import fachada.Fachada;

public class LoginController implements Initializable{
	
	private Fachada facade;
	@FXML private AnchorPane rootPane;
	
	@FXML private JFXTextField txtUser;
	@FXML private JFXPasswordField txtSenha;
	@FXML private JFXButton btnLogar;
	@FXML private JFXCheckBox chkbxRemember;
	
	@FXML private Hyperlink linkTwitter;
	
	@FXML
    private Label txtErro;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnLogar.setDefaultButton(true);
		txtErro.setAlignment(Pos.CENTER);
	}
	
	public void logar(ActionEvent event) throws IOException {
		facade = Fachada.getInstancia();
		
		boolean login = facade.login(txtUser.getText(),txtSenha.getText());
		
		if(login) {            
            createStage(event,facade.getAuthPath());
		}else {
			txtErro.setText("Nick or Password Incorrect.");
			clearFields();
		}
	}

	private void clearFields() {
		txtUser.setText(null);
		txtSenha.setText(null);
	}
	
	
	public void loginWithTwitter(ActionEvent event) throws IOException {
		facade = Fachada.getInstancia();
		boolean login = facade.loginWithTwitter();
		
		if(login) {
			createStage(event,facade.getAuthPath());
		}
	}
	
	
	
	private void createStage(ActionEvent event, String PATH) throws IOException{
		Stage st = (Stage) (((Node)event.getSource()).getScene().getWindow());
		
		AnchorPane telaChamado = (AnchorPane)FXMLLoader.load(getClass().getResource(PATH));
        Scene scene = new Scene(telaChamado);
        st.setScene(scene);
        st.show();
	}
}