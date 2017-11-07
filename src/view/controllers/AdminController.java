package view.controllers;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import fachada.Fachada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Chamados;
import javafx.fxml.Initializable;

public class AdminController implements Initializable{
	
	private Fachada facade;
	private Timer timer = null;
	 @FXML
    private TableView<Chamados> tableChamados;

    @FXML
    private TableColumn<Chamados, Integer> columnId;

    @FXML
    private TableColumn<Chamados, String> columnTipo;

    @FXML
    private TableColumn<Chamados, String> columnDescricao;

    @FXML
    private TableColumn<Chamados, String> columnData;
    
    @FXML
    private TableColumn<Chamados, String> columnLocal;

    @FXML
    private TableColumn<Chamados, String> columnUser;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		columnId.setCellValueFactory(new PropertyValueFactory<Chamados, Integer>("id"));
		columnTipo.setCellValueFactory(new PropertyValueFactory<Chamados, String>("tipoChamado"));
		columnDescricao.setCellValueFactory(new PropertyValueFactory<Chamados, String>("descricaoChamado"));
		columnData.setCellValueFactory(new PropertyValueFactory<Chamados, String>("dataChamado"));
		columnLocal.setCellValueFactory(new PropertyValueFactory<Chamados, String>("localChamado"));
		columnUser.setCellValueFactory(new PropertyValueFactory<Chamados, String>("usuario"));
		
		loadTable();
		waitChamados();
		
		//pegar double click da row
		tableChamados.setRowFactory( tv -> {
		    TableRow<Chamados> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	Chamados rowData = row.getItem();
		            loadDialog(rowData);
		        }
		    });
		    return row ;
		});
	}
	
	public void loadTable() {
		facade = Fachada.getInstancia();
		List<Chamados> list = facade.listChamadosWaiting();
	
		ObservableList<Chamados> obsList = FXCollections.observableArrayList();
		
		for (Chamados chamados : list) {
			obsList.add(chamados);
		}
		tableChamados.setItems(obsList);
	}
	
	private void loadDialog(Chamados c) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Chamado Dialog");
		alert.setContentText(c.toString());

		ButtonType buttonTypeOne = new ButtonType("Change Status", ButtonData.YES);
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne){
		    // ... chama facade e da update no Chamado alterando-o para recebido true
			facade = Fachada.getInstancia();
			c.setRecebido(true);
			
			facade.changeStatus(c);
		}
	}
	
	private void waitChamados() {
        if (timer == null) {
            timer = new Timer();
            TimerTask tarefa = new TimerTask() {
                public void run() {
                    try {
                        
                    	List<Chamados> list = facade.listChamadosWaiting();
                    	
                    	if(list != null) {
                    		loadTable();
                    	}
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            };
            timer.scheduleAtFixedRate(tarefa, 5000, 5000);
        }
    }
	
	
	
	
}
