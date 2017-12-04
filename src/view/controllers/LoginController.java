package view.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import facade.Facade;
import helper.Helpers;

public class LoginController implements Initializable{
	
	private Facade facade;
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
		
		if (! emptyFields() ) {
			
			facade = Facade.getInstancia();
			
			// verificao pra caso usuario coloque @ no nick
			String userNick = txtUser.getText();
			if( txtUser.getText().substring(0, 1).equals("@") ) { // caso tenha @ na primeira posicao ele executa o bloco
				userNick = txtUser.getText().substring(1,txtUser.getText().length()); // tira-se a primeira a posicao ( ignorado o @ )
			}
			
			
			// facade.login retorna um boolean
			// O ultimo parametro enviado eh uma variavel auxiliar -> true se a senha ja estiver criptografada, false se nao estiver.
			boolean login = facade.login(userNick,txtSenha.getText(),
										 chkbxRemember.isSelected(), false);
			
			if( login ) {
				Helpers.changeScene(getClass(),event, facade.getAuthPath());
			}else {
				txtErro.setText("Invalid Nick/Password Combination.");
				clearFields();
			}
			
		} else {
			txtErro.setText("Empty Fields.");
		}
	}
	
	
	public void loginWithTwitter(ActionEvent event) throws IOException {
		facade = Facade.getInstancia();
		boolean login = facade.loginWithTwitter();
		
		if(login) {
			Helpers.changeScene(getClass(),event, facade.getAuthPath());
		} else {
			txtErro.setText("Twitter Login failed.");
		}
	}
	
	private boolean emptyFields() {
		return txtUser.getText().isEmpty() || txtSenha.getText().isEmpty();
	}
	
	private void clearFields() {
		txtUser.setText(null);
		txtSenha.setText(null);
	}

}