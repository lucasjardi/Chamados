package view.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import model.Users;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import facade.Facade;
import helper.Config;
import helper.Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class RegisterController implements Initializable{
	
	private Facade facade;
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
		facade = Facade.getInstancia();
		
		if(!emptyFields()){
			if( isPassEquals() ){
				
				if(facade.saveUser( new Users(txtName.getText(), txtNick.getText(), txtPassword.getText(),facade.getPermissionById(2))) ) {
					
					Helpers.successDialog();
					Helpers.changeScene(getClass(),this.ev, Config.PATH_LOGIN);
					
				} else {
					errorLabel.setText("Register error! Try again.");
					clearFields();
				}
				
			} else{
				errorLabel.setText("Invalid Passwords combination");
			}
		} else {
			errorLabel.setText("Empty Fields. Please complete the fields.");
		}
	}
	
	private void clearFields() {
		txtNick.setText(null);
		txtName.setText(null);
		txtPassword.setText(null);
		txtRepeatPassword.setText(null);
	}
	
	private boolean emptyFields(){
		return txtName.getText().equals("") || txtNick.getText().equals("") || txtPassword.getText().equals("") || txtRepeatPassword.getText().equals("");
	}
	
	private boolean isPassEquals(){
		return txtPassword.getText().equals(txtRepeatPassword.getText());
	}	
	

}
