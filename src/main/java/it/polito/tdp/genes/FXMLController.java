/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.genes;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.GenePeso;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="cmbGeni"
    private ComboBox<Genes> cmbGeni; // Value injected by FXMLLoader

    @FXML // fx:id="btnGeniAdiacenti"
    private Button btnGeniAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtIng"
    private TextField txtIng; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	String msg = model.createGraph();
    	txtResult.appendText(msg);
    	
    	cmbGeni.getItems().addAll(model.getVertices());
    	
    }

    @FXML
    void doGeniAdiacenti(ActionEvent event) {

    	Genes genes = cmbGeni.getValue();
    	List<GenePeso> rslt = model.getGenesAdiacenti(genes);
    	
    	if (rslt.size() == 0) {
			txtResult.appendText("Nessun gene adiacente trovato a " + genes.toString() +"\n\n");
			return ;
		}
    	
    	txtResult.appendText("Elenco geni adiacenti a " + genes.toString() + "\n");
    	for (GenePeso genePeso : rslt) {
			txtResult.appendText(genePeso.toString() + "\n");
		}
    	
    	txtResult.appendText("\n");
    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	Integer numIng;
    	
    	try {
    		numIng = Integer.parseInt(txtIng.getText());
		} catch (NumberFormatException e) {
			txtResult.appendText("Numero di ingegneri obbligatorio e deve essere un numero\n");
			return ;
		}
    	
    	Genes genesStart = cmbGeni.getValue();
    	
    	Map<Genes, Integer> geniStudiati = model.simulaIng(genesStart, numIng);
    	
    	if (geniStudiati == null) {
			txtResult.appendText("ERRORE il gene selezionato è isolato \n");
		} else {
			txtResult.appendText("Risultato simulazione\n");
			for (Genes genes : geniStudiati.keySet()) {
				txtResult.appendText(genes+" "+ geniStudiati.get(genes)+ "\n");
			}
		}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGeni != null : "fx:id=\"cmbGeni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGeniAdiacenti != null : "fx:id=\"btnGeniAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtIng != null : "fx:id=\"txtIng\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
    
}
