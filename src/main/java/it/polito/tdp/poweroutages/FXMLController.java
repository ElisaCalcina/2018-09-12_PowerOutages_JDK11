package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Archi;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<Nerc> cmbBoxNerc;

    @FXML
    private Button btnVisualizzaVicini;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	this.model.creaGrafo();
    	txtResult.appendText("Grafo creato!\n");
    	txtResult.appendText("#vertici:" +this.model.nVertici()+"\n");
    	txtResult.appendText("#archi:" +this.model.nArchi()+"\n");
    	
    	
    	this.cmbBoxNerc.getItems().addAll(this.model.grafo().vertexSet());

    }

    @FXML
    void doSimula(ActionEvent event) {

    }

    @FXML
    void doVisualizzaVicini(ActionEvent event) {
    	Nerc n= this.cmbBoxNerc.getValue();
    	
    	if(n==null) {
    		txtResult.appendText("Devi selezionare un nerc");
    		return;
    	}
    	List<Archi> lista= this.model.elencoArchi(n);
    	
    	txtResult.appendText("Vicini a "+ n+ ":\n");
    	for(Archi a: lista) {
    		txtResult.appendText(a.toString()+"\n");
    	}

    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert cmbBoxNerc != null : "fx:id=\"cmbBoxNerc\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnVisualizzaVicini != null : "fx:id=\"btnVisualizzaVicini\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'PowerOutages.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
