package view.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import facade.Facade;
import helper.Helpers;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import model.Calls;
import model.Locals;
import model.Users;

public class CallController implements Initializable{
	
	@FXML
    private JFXComboBox<String> cbTipo;

    @FXML
    private JFXTextArea txtDesc;

    @FXML
    private JFXComboBox<Locals> cbLocal;
    
    @FXML
    private MenuItem menuClose;

    @FXML
    private MenuItem menuLogoutClose;
    
    private Facade facade;
    private Users currentUser;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// verificacao de session
		facade = Facade.getInstancia();
		
		if(facade.getUser("login") == null) {
			Platform.exit();
		}else {
			this.currentUser = facade.getUser("login");
		}
		
		// popula a combobox de tipos
		ObservableList<String> TiposdeChamado = 
			    FXCollections.observableArrayList(
			        "Hardware problems",
			        "Software problems",
			        "Technical Support",
			        "Network/Wifi",
			        "Printer",
			        "Other"
			    );
		cbTipo.setItems(TiposdeChamado);
		
		//popula a combobox de locais com os dados do banco
		facade = Facade.getInstancia();
		List<Locals> locais = facade.listAllLocals();
		
		ObservableList<Locals> locals = FXCollections.observableArrayList(locais);
		cbLocal.setItems(locals);
		
		
	}
	
	public void menuClose(ActionEvent event) {
		if (event.getSource() == menuClose) {
			Platform.exit();
		}
		
		if(event.getSource() == menuLogoutClose) {
			facade = Facade.getInstancia();
			facade.deleteCredentials();
			
			Platform.exit();
		}
	}
	
	public void save() {
			
		if (! emptyFields() ) {
			
			String tipoChamado = cbTipo.getSelectionModel().getSelectedItem();
			String descricaoChamado = txtDesc.getText();
			Locals localChamado = cbLocal.getSelectionModel().getSelectedItem();
			
			Calls call = new Calls();
			call.setTipoChamado(tipoChamado);
			call.setDescricaoChamado(descricaoChamado);
			call.setLocal(localChamado);
			call.setUsuario(this.currentUser);
			
			
			facade = Facade.getInstancia();
			facade.saveChamado(call);
	        
	        Helpers.simpleDialog("Registered", "Call requested.", "");
	        
			clear();
		}
	}
	
	private boolean emptyFields() {
		return txtDesc.getText().isEmpty() || 
			   cbTipo.getSelectionModel().isEmpty() || 
			   cbLocal.getSelectionModel().isEmpty();
	}
	
	private void clear() {
		this.cbTipo.getSelectionModel().clearSelection();
		this.cbLocal.getSelectionModel().clearSelection();
		this.txtDesc.setText(null);
	}
}
