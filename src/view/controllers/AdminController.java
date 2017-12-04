package view.controllers;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import facade.Facade;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Calls;
import model.Locals;
import helper.Helpers;

public class AdminController implements Initializable{
	
	private long interval = 30000;
	private Facade facade;
	private Timer timer = null;
	
	@FXML
    private Label labelTitle;
	
	 @FXML
    private MenuItem menuClose;

    @FXML
    private MenuItem menuLogoutClose;
    
    @FXML
    private MenuItem menuAllCalls;
    
    @FXML
    private MenuItem menuCallsHold;
	
    @FXML
    private MenuItem menuStopCalls;

    @FXML
    private MenuItem menuStartCalls;
    
    @FXML
    private MenuItem menuInterval;
    
    @FXML
    private MenuItem menuInsertLocal;
    
	@FXML
    private TableView<Calls> tableChamados;

    @FXML
    private TableColumn<Calls, Integer> columnId;

    @FXML
    private TableColumn<Calls, String> columnTipo;

    @FXML
    private TableColumn<Calls, String> columnDescricao;

    @FXML
    private TableColumn<Calls, String> columnData;
    
    @FXML
    private TableColumn<Calls, String> columnLocal;

    @FXML
    private TableColumn<Calls, String> columnUser;
	
    private boolean controle=false;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// verificacao de session
		facade = Facade.getInstancia();
		
		if(facade.getUser("login") == null) {
			Platform.exit();
		}
		
		
		columnId.setCellValueFactory(new PropertyValueFactory<Calls, Integer>("id"));
		columnTipo.setCellValueFactory(new PropertyValueFactory<Calls, String>("tipoChamado"));
		columnDescricao.setCellValueFactory(new PropertyValueFactory<Calls, String>("descricaoChamado"));
		columnData.setCellValueFactory(new PropertyValueFactory<Calls, String>("dataChamado"));
		columnLocal.setCellValueFactory(new PropertyValueFactory<Calls, String>("localChamado"));
		columnUser.setCellValueFactory(new PropertyValueFactory<Calls, String>("usuario"));
		
		menuCallsHold.setDisable(true);
		labelTitle.setText("Calls on Hold");
		menuStartCalls.setDisable(true);
		loadTable(false);
		waitChamados();
		
		//pegar double click da row
		tableChamados.setRowFactory( tv -> {
		    TableRow<Calls> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	Calls rowData = row.getItem();
		            loadDialog(rowData);
		        }
		    });
		    return row ;
		});
	}
	
	public void menuAction(ActionEvent event) {
		if (event.getSource() == menuClose) {
			Platform.exit();
		}
		
		if(event.getSource() == menuLogoutClose) {
			facade = Facade.getInstancia();
			facade.deleteCredentials();
			
			Platform.exit();
		}
		
		if (event.getSource() == menuAllCalls) {
			timer.cancel();
			timer = null;
			loadTable(true);
			labelTitle.setText("All Calls");
			menuAllCalls.setDisable(true);
			menuCallsHold.setDisable(false);
		}
		
		if (event.getSource() == menuCallsHold) {
			loadTable(false);
			waitChamados();
			labelTitle.setText("Calls on Hold");
			menuAllCalls.setDisable(false);
			menuCallsHold.setDisable(true);
		}
		
		if (event.getSource() == menuStopCalls) {
			timer.cancel();
			timer = null;
			
			menuStopCalls.setDisable(true);
			menuStartCalls.setDisable(false);
		}
		
		if (event.getSource() == menuStartCalls) {
			menuStopCalls.setDisable(false);
			menuStartCalls.setDisable(true);
			loadTable(false);
			waitChamados();
		}
		
		if (event.getSource() == menuInsertLocal) {
			String localName = Helpers.inputDialog("Create a local", "Type the name of the Local. (Example: Lab 1", "");
			facade = Facade.getInstancia();
			
			Locals local = new Locals();
			local.setNomeLocal(localName);
			
			facade.saveLocal(local);
			
			Helpers.simpleDialog("Success", "Registered with success.", "");
		}
		
		if (event.getSource() == menuInterval) {
			timer.cancel();
			timer = null;
			
			boolean error = false;
			
			String time = Helpers.inputDialog("Interval Time",
					"Current Interval : "+ (this.interval/60000.0) + " minutes\nType, in minutes, the interval of receiving calls:",
					"Ps: The min value is 0.5 minutes and the max is 60 minutes.");
			
			if (!time.isEmpty() && isDouble(time)) {
				double t = Double.parseDouble(time);
				if(t >= 0.5 && t<= 60.0) {
					long intervalInMiliseconds = (long) (t * 60000);
					
					this.interval = intervalInMiliseconds;
				} else {
					error = true;
				}
			} else {
				error = true;
			}
			
			if(error) Helpers.simpleDialog("Invalid", "You typed an invalid number. sorry.", "");
			
			loadTable(false);
			waitChamados();	
		}
	}
	
	public void loadTable(boolean all) {
		facade = Facade.getInstancia();
		List<Calls> list = null;
		// se false ele pega apenas chamados em espera, se true ele pega todos os chamados
		if(all) {
			list = facade.listAllChamados();
			this.controle = true;
		}else {
			list = facade.listChamadosWaiting();
			this.controle = false;
		}
	
		ObservableList<Calls> obsList = FXCollections.observableArrayList();
		
		for (Calls calls : list) {
			obsList.add(calls);
		}
		tableChamados.setItems(obsList);
	}
	
	private void loadDialog(Calls c) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Chamado Dialog");
		alert.setContentText(c.toString());

		ButtonType buttonTypeOne = new ButtonType("Change Status", ButtonData.YES);
		ButtonType buttonTypeDelete = new ButtonType("Delete", ButtonData.NO);
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeDelete, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne){
		    // ... chama facade e da update no Chamado alterando-o para recebido true
			facade = Facade.getInstancia();
			
			if(c.isRecebido()) c.setRecebido(false);
			else c.setRecebido(true);
			
			facade.changeStatus(c);
		} else if (result.get() == buttonTypeDelete) {
			facade = Facade.getInstancia();
			
			facade.deleteCall(c);
			
			loadTable(this.controle);
		}
	}
	
	private void waitChamados() {
        if (timer == null) {
            timer = new Timer();
            TimerTask tarefa = new TimerTask() {
                public void run() {
                    try {
                        
                    	List<Calls> list = facade.listChamadosWaiting();
                    	
                    	if(list != null) {
                    		loadTable(false);
                    	}
                    } catch (Exception e) {
                    	Helpers.throwExceptionDialog(e, "Waiting Calls Thread Exception.");
                    }
                }

            };
            timer.scheduleAtFixedRate(tarefa, interval, interval);
        }
    }
	
	private boolean isDouble(String str)  
	{  
	  try  
	  {  
	    Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {
	    return false;  
	  }  
	  return true;  
	}
	
	
	
}
