package view.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;

import com.jfoenix.controls.JFXButton;

import helper.Config;
import helper.Helpers;

public class InitController implements Initializable {
	
	@FXML private Hyperlink btnLogin;
	@FXML private JFXButton btnRegister;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	public void clickButton(ActionEvent event) throws IOException {
		
		if(event.getSource() == btnLogin) {
			Helpers.changeScene(getClass(),event,Config.PATH_LOGIN);
		}
		
		if(event.getSource() == btnRegister) {
			Helpers.changeScene(getClass(),event, Config.PATH_REGISTER);
		}
		
	}
	
}
