package view.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import fachada.Fachada;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import model.Chamados;
import model.Local;
import model.Usuario;

public class CallController implements Initializable{
	
	@FXML
    private JFXComboBox<String> cbTipo;

    @FXML
    private JFXTextArea txtDesc;

    @FXML
    private JFXComboBox<Local> cbLocal;
    
    private Fachada facade;
    private Usuario currentUser;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		facade = Fachada.getInstancia();
		
		if(facade.getUser("login") == null) {
			Platform.exit();
		}else {
			this.currentUser = facade.getUser("login");
		}
		
		ObservableList<String> TiposdeChamado = 
			    FXCollections.observableArrayList(
			        "Defeito",
			        "Necessidade de Suporte",
			        "Outros"
			    );
		cbTipo.setItems(TiposdeChamado);
		
		facade = Fachada.getInstancia();
		List<Local> locais = facade.listAllLocals();
		
		ObservableList<Local> locals = FXCollections.observableArrayList(locais);
		cbLocal.setItems(locals);
		
		
	}
	
	public void save() {
			
		String tipoChamado = cbTipo.getSelectionModel().getSelectedItem();
		String descricaoChamado = txtDesc.getText();
		Local localChamado = cbLocal.getSelectionModel().getSelectedItem();
		
		Chamados novo = new Chamados();
		novo.setTipoChamado(tipoChamado);
		novo.setDescricaoChamado(descricaoChamado);
		novo.setLocal(localChamado);
		novo.setUsuario(this.currentUser);
		
		
		facade = Fachada.getInstancia();
		facade.saveChamado(novo);
		
		Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
        dialogoInfo.setTitle("Chamados");
        dialogoInfo.setContentText("Chamado inserido com sucesso!");
        dialogoInfo.showAndWait();
        
		clear();
	}
	
	private void clear() {
		this.cbTipo.getSelectionModel().clearSelection();
		this.cbLocal.getSelectionModel().clearSelection();
		this.txtDesc.setText(null);
	}
}
