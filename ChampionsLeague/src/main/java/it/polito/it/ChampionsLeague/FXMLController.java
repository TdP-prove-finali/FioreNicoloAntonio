package it.polito.it.ChampionsLeague;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.ChampionsLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class FXMLController implements Initializable {
	private Model model;
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setModel(Model model) {
    	this.model = model;
    }
}
