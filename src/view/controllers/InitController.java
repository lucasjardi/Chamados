package view.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import config.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InitController implements Initializable {
	
	@FXML private Hyperlink btnLogin;
	@FXML private JFXButton btnRegister;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void clickButton(ActionEvent event) throws IOException {
		if(event.getSource() == btnLogin) {
			createStage(event, Config.PATH_LOGIN);
		}
		if(event.getSource() == btnRegister) {
			createStage(event, Config.PATH_REGISTER);
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
