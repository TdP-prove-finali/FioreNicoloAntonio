package it.polito.it.ChampionsLeague;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ChampionsLeague.model.Giornata;
import it.polito.tdp.ChampionsLeague.model.Model;
import it.polito.tdp.ChampionsLeague.model.PartitaEliminazioneDiretta;
import it.polito.tdp.ChampionsLeague.model.PartitaGirone;
import it.polito.tdp.ChampionsLeague.model.Squadra;
import it.polito.tdp.ChampionsLeague.model.Stadio;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FXMLController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Squadra> boxSquadraED;

    @FXML
    private ComboBox<Squadra> boxSquadraGirone;

    @FXML
    private Button btnClassifica;

    @FXML
    private Button btnCreaGirone;

    @FXML
    private Button btnEliminazioneDiretta;

    @FXML
    private Button btnGuadagni;

    @FXML
    private Button btnInfoStadio;

    @FXML
    private Button btnSimulaGirone;

    @FXML
    private TableColumn<PartitaEliminazioneDiretta, String> clAndataRitorno;

    @FXML
    private TableColumn<Squadra, String> clBiglietti;

    @FXML
    private TableColumn<PartitaEliminazioneDiretta,String> clCasaED;

    @FXML
    private TableColumn<PartitaGirone,String> clCasaGirone;

    @FXML
    private TableColumn<Squadra, Integer> clDR;

    @FXML
    private TableColumn<Squadra, String> clEliminatoria;

    @FXML
    private TableColumn<Squadra, String> clFaseGironi;

    @FXML
    private TableColumn<Squadra,Integer> clGF;

    @FXML
    private TableColumn<Squadra,Integer> clGS;

    @FXML
    private TableColumn<PartitaGirone, Integer> clGiornata;

    @FXML
    private TableColumn<Squadra, String> clMarketPool;

    @FXML
    private TableColumn<Stadio, String> clNomeStadio;

    @FXML
    private TableColumn<PartitaGirone, String> clOspite;

    @FXML
    private TableColumn<PartitaEliminazioneDiretta,String> clOspiteED;

    @FXML
    private TableColumn<Stadio, Double> clPercentuale;
    
    @FXML
    private TableColumn<Squadra, Integer> clPosizioneClassifica;
    
    @FXML
    private TableColumn<Stadio, Integer> clPartiteGiocate;
    
    @FXML
    private TableColumn<Squadra, Integer> clPunti;

    @FXML
    private TableColumn<Squadra, String> clRankingStorico;

    @FXML
    private TableColumn<PartitaEliminazioneDiretta,String> clRisultatoED;

    @FXML
    private TableColumn<PartitaGirone, String> clRisultatoGirone;

    @FXML
    private TableColumn<Squadra, String> clSqClass;

    @FXML
    private TableColumn<Squadra, String> clSquadra;

    @FXML
    private TableColumn<PartitaEliminazioneDiretta, String> clSuperaTurno;

    @FXML
    private TableColumn<Stadio, Integer> clTotBiglietti;

    @FXML
    private TableColumn<Stadio, String> clTotIncasso;

    @FXML
    private TableColumn<Squadra, String> clTotale;
    
    @FXML
    private TableColumn<PartitaEliminazioneDiretta, String> clTurno;

    @FXML
    private TableColumn<PartitaEliminazioneDiretta, String> clTotaleED;

    @FXML
    private ComboBox<Giornata> cmbGiornate;

    @FXML
    private ComboBox<Squadra> cmbSquadraGuadagni;

    @FXML
    private ComboBox<Stadio> cmbStadio;

    @FXML
    private ComboBox<String> cmbTurno;

    @FXML
    private ImageView imgVincitore;

    @FXML
    private ImageView logo;
    
    @FXML
    private Label labelVincitore;

    @FXML
    private TableView<Squadra> tblClassifica;

    @FXML
    private TableView<PartitaEliminazioneDiretta> tblFaseEliminazioneDiretta;

    @FXML
    private TableView<PartitaGirone> tblGirone;

    @FXML
    private TableView<Squadra> tblGuadagni;

    @FXML
    private TableView<Stadio> tblInfoStadio;

	private Model model;

    @FXML
    void handleCreaGirone(ActionEvent event) {
    	tblGirone.getItems().clear();
    	tblClassifica.getItems().clear();
    	tblFaseEliminazioneDiretta.getItems().clear();
    	tblGuadagni.getItems().clear();
    	tblInfoStadio.getItems().clear();
    	//imgVincitore.getImage().cancel();
    	labelVincitore.setVisible(false);
    	imgVincitore.setVisible(false);
    	model.resettaDatiStadio();
    	model.resettaDatiSquadra();
    	model.initiGiornate();
    	model.setGuadagniInizialiCalcolati(false);
    	
        for(Giornata g: model.getGiornate().values()) {
        	cmbGiornate.getItems().add(g);
        	/*txtResult.appendText("Giornata "+g.getIdGiornata()+"\n\n");
        	for(PartitaGirone pg: g.getPartite()) {
        		txtResult.appendText(pg.toString()+"\n");
        	}*/
        	
        }
        cmbGiornate.setValue(model.getGiornate().get(1));
        tblGirone.setItems(FXCollections.observableArrayList(model.getGiornate().get(1).getPartite()));
        btnEliminazioneDiretta.setDisable(true);
        boxSquadraED.setDisable(true);
        cmbTurno.setDisable(true);
        boxSquadraED.setDisable(true);
        btnGuadagni.setDisable(true);
        btnInfoStadio.setDisable(true);
        cmbSquadraGuadagni.setDisable(true);
        cmbStadio.setDisable(true);
        btnSimulaGirone.setDisable(false);
        cmbGiornate.setDisable(false);
        boxSquadraGirone.setDisable(false);
        
    }

    @FXML
    void handleEliminazioneDiretta(ActionEvent event) {
    	tblFaseEliminazioneDiretta.getItems().clear();
    	tblGuadagni.getItems().clear();
    	tblInfoStadio.getItems().clear();
    	model.resettaDatiSquadraED();
    	model.resettaDatiStadioED();
    	model.initSimulazioneEliminazioneDiretta();
    	cmbTurno.setValue("PlayOff");
    	
        tblFaseEliminazioneDiretta.setItems(FXCollections.observableArrayList(model.getPartitePlayOff()));
        
       /* File f2 = new File("Immagini/"+squadraVincitrice.getNome()+".png");
		imgVincitore.setImage(new Image(f2.toURI().toString()));*/
        cmbTurno.setDisable(false);
        boxSquadraED.setDisable(false);
        btnGuadagni.setDisable(false);
        btnInfoStadio.setDisable(false);
      List<Squadra> squadreED=model.getSquadreAgliOttavi();
      squadreED.addAll(model.getSquadreAiPlayOff());
      boxSquadraED.getItems().addAll(squadreED);
       File f2 = new File("Immagini/"+model.getVincitrice().getNome()+".png");
      //File f2 = new File("Immagini/Atlético de Madrid.png");
		imgVincitore.setImage(new Image(f2.toURI().toString()));
		labelVincitore.setVisible(true);
    	imgVincitore.setVisible(true);

    }

    @FXML
    void handleGuadagni(ActionEvent event) {
    	tblGuadagni.getItems().clear();
    	cmbSquadraGuadagni.setValue(null);
    	model.calcolaGuadagniIniziali();
    	for(Squadra s: model.getSquadre()) {
    		s.guadagniTotali();
    	}
    	 tblGuadagni.setItems(FXCollections.observableArrayList(model.getSquadre()));
    cmbSquadraGuadagni.setDisable(false);
    }

    @FXML
    void handleGuadagniSquadra(ActionEvent event) {
    if(cmbSquadraGuadagni.getValue()!=null) {
    	Squadra s=cmbSquadraGuadagni.getValue();
    	List<Squadra> squadra=new ArrayList<>();
    	squadra.add(s);
    	tblGuadagni.getItems().clear();
    	tblGuadagni.setItems(FXCollections.observableArrayList(squadra));
    }
    }

    @FXML
    void handleInfoStadio(ActionEvent event) {
    	tblInfoStadio.getItems().clear();
    	cmbStadio.setValue(null);
    	for(Stadio s : model.getStadi()) {
    		s.getPercCapienza();
    	}
    tblInfoStadio.setItems(FXCollections.observableArrayList(model.getStadi()));
    cmbStadio.setDisable(false);
    }

    @FXML
    void handleMostraPartite(ActionEvent event) {
    	if(cmbGiornate.getValue()!=null) {
    tblGirone.setItems(FXCollections.observableArrayList(cmbGiornate.getValue().getPartite()));
    boxSquadraGirone.setValue(null);
    	}
    }

    @FXML
    void handlePartiteSquadraED(ActionEvent event) {
    	if(boxSquadraED.getValue()!=null) {
        	Squadra s=boxSquadraED.getValue();
        	tblFaseEliminazioneDiretta.setItems(FXCollections.observableArrayList(s.getRisultatiEliminazioneDiretta()));
        	cmbTurno.setValue(null);
        }
    }

    @FXML
    void handlePartiteSquadraGirone(ActionEvent event) {
    if(boxSquadraGirone.getValue()!=null) {
    	Squadra s=boxSquadraGirone.getValue();
    	tblGirone.setItems(FXCollections.observableArrayList(s.getPartite()));
    	cmbGiornate.setValue(null);
    }
    }

    @FXML
    void handleScegliTurno(ActionEvent event) {
    	if(cmbTurno.getValue()!=null) {
    	if(cmbTurno.getValue().equals("PlayOff")) {
       	 tblFaseEliminazioneDiretta.setItems(FXCollections.observableArrayList(model.getPartitePlayOff()));
       }
        if(cmbTurno.getValue().equals("Ottavi")) {
       	 tblFaseEliminazioneDiretta.setItems(FXCollections.observableArrayList(model.getPartiteOttavi()));
       }
        if(cmbTurno.getValue().equals("Quarti")) {
       	 tblFaseEliminazioneDiretta.setItems(FXCollections.observableArrayList(model.getPartiteQuarti()));
       }
        if(cmbTurno.getValue().equals("Semifinali")) {
       	 tblFaseEliminazioneDiretta.setItems(FXCollections.observableArrayList(model.getPartiteSemifinale()));
       }
        if(cmbTurno.getValue().equals("Finale")) {
        	tblFaseEliminazioneDiretta.setItems(FXCollections.observableArrayList(model.getPartitaFinale()));
        }
        else {
        	
        }
        boxSquadraED.setValue(null);
    	}
    }

    @FXML
    void handleSimulaGirone(ActionEvent event) {
    	tblGirone.getItems().clear();
    	tblFaseEliminazioneDiretta.getItems().clear();
    	labelVincitore.setVisible(false);
    	imgVincitore.setVisible(false);
    	tblGuadagni.getItems().clear();
    	tblGuadagni.getItems().clear();
    	model.resettaDatiStadio();
    	model.resettaDatiSquadra2();
    	 boxSquadraED.setDisable(true);
         cmbTurno.setDisable(true);
         boxSquadraED.setDisable(true);
         btnGuadagni.setDisable(true);
         btnInfoStadio.setDisable(true);
         cmbSquadraGuadagni.setDisable(true);
         cmbStadio.setDisable(true);
         btnSimulaGirone.setDisable(false);
         cmbGiornate.setDisable(false);
         boxSquadraGirone.setDisable(false);
        model.initSimulazioneFaseGirone();
        model.setGuadagniInizialiCalcolati(false);
        cmbGiornate.setValue(model.getGiornate().get(1));
        tblGirone.setItems(FXCollections.observableArrayList(model.getGiornate().get(1).getPartite()));
        List<Squadra> squadre=model.getSquadre();
        Collections.sort(squadre);
        Collections.reverse(squadre);
        tblClassifica.setItems(FXCollections.observableArrayList(squadre));
        int pos=1;
        for(Squadra s : squadre) {
       	 s.setPosizione(pos);
       	 pos++;
        }
        btnEliminazioneDiretta.setDisable(false);
        //boxSquadraED.getItems().clear();
        
        
    }

    @FXML
    void handleStatStadio(ActionEvent event) {
    if(cmbStadio.getValue()!=null) {
    	List<Stadio> stadi = new ArrayList<>();
    	stadi.add(cmbStadio.getValue());
    	tblInfoStadio.setItems(FXCollections.observableArrayList(stadi));
    }
    }

    

    @FXML
    void initialize() {
        assert boxSquadraED != null : "fx:id=\"boxSquadraED\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxSquadraGirone != null : "fx:id=\"boxSquadraGirone\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClassifica != null : "fx:id=\"btnClassifica\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGirone != null : "fx:id=\"btnCreaGirone\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnEliminazioneDiretta != null : "fx:id=\"btnEliminazioneDiretta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGuadagni != null : "fx:id=\"btnGuadagni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnInfoStadio != null : "fx:id=\"btnInfoStadio\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimulaGirone != null : "fx:id=\"btnSimulaGirone\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clAndataRitorno != null : "fx:id=\"clAndataRitorno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clBiglietti != null : "fx:id=\"clBiglietti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clCasaED != null : "fx:id=\"clCasaED\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clCasaGirone != null : "fx:id=\"clCasaGirone\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clDR != null : "fx:id=\"clDR\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clEliminatoria != null : "fx:id=\"clEliminatoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clFaseGironi != null : "fx:id=\"clFaseGironi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clGF != null : "fx:id=\"clGF\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clGS != null : "fx:id=\"clGS\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clGiornata != null : "fx:id=\"clGiornata\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clMarketPool != null : "fx:id=\"clMarketPool\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clNomeStadio != null : "fx:id=\"clNomeStadio\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clOspite != null : "fx:id=\"clOspite\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clOspiteED != null : "fx:id=\"clOspiteED\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clPosizioneClassifica != null : "fx:id=\"clPosizioneClassifica\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clPercentuale != null : "fx:id=\"clPercentuale\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clPunti != null : "fx:id=\"clPunti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clRankingStorico != null : "fx:id=\"clRankingStorico\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clRisultatoED != null : "fx:id=\"clRisultatoED\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clRisultatoGirone != null : "fx:id=\"clRisultatoGirone\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clSqClass != null : "fx:id=\"clSqClass\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clSquadra != null : "fx:id=\"clSquadra\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clSuperaTurno != null : "fx:id=\"clSuperaTurno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clTotBiglietti != null : "fx:id=\"clTotBiglietti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clTotIncasso != null : "fx:id=\"clTotIncasso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clTotale != null : "fx:id=\"clTotale\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clTotaleED != null : "fx:id=\"clTotaleED\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGiornate != null : "fx:id=\"cmbGiornate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbSquadraGuadagni != null : "fx:id=\"cmbSquadraGuadagni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbStadio != null : "fx:id=\"cmbStadio\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbTurno != null : "fx:id=\"cmbTurno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert imgVincitore != null : "fx:id=\"imgVincitore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert logo != null : "fx:id=\"logo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tblClassifica != null : "fx:id=\"tblClassifica\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tblFaseEliminazioneDiretta != null : "fx:id=\"tblFaseEliminazioneDiretta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tblGirone != null : "fx:id=\"tblGirone\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tblGuadagni != null : "fx:id=\"tblGuadagni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tblInfoStadio != null : "fx:id=\"tblInfoStadio\" was not injected: check your FXML file 'Scene.fxml'.";
        
        btnSimulaGirone.setDisable(true);
        cmbGiornate.setDisable(true);
        boxSquadraGirone.setDisable(true);
        btnEliminazioneDiretta.setDisable(true);
        boxSquadraED.setDisable(true);
        cmbTurno.setDisable(true);
        boxSquadraED.setDisable(true);
        btnGuadagni.setDisable(true);
        btnInfoStadio.setDisable(true);
        cmbSquadraGuadagni.setDisable(true);
        cmbStadio.setDisable(true);
        
        clCasaGirone.setCellValueFactory(new PropertyValueFactory<PartitaGirone,String>("nomeSquadraCasa"));
        clOspite.setCellValueFactory(new PropertyValueFactory<PartitaGirone,String>("nomeSquadraOspite"));
        clRisultatoGirone.setCellValueFactory(new PropertyValueFactory<PartitaGirone,String>("risultato"));
        clGiornata.setCellValueFactory(new PropertyValueFactory<PartitaGirone,Integer>("idGiornata"));
        
        clPosizioneClassifica.setCellValueFactory(new PropertyValueFactory<Squadra,Integer>("posizione"));
        clSqClass.setCellValueFactory(new PropertyValueFactory<Squadra,String>("nome"));
        clPunti.setCellValueFactory(new PropertyValueFactory<Squadra,Integer>("punti"));
        clGF.setCellValueFactory(new PropertyValueFactory<Squadra,Integer>("golFatti"));
        clGS.setCellValueFactory(new PropertyValueFactory<Squadra,Integer>("golSubiti"));
        clDR.setCellValueFactory(new PropertyValueFactory<Squadra,Integer>("diffReti"));
        
        clAndataRitorno.setCellValueFactory(new PropertyValueFactory<PartitaEliminazioneDiretta,String>("andataORitorno"));
        clTurno.setCellValueFactory(new PropertyValueFactory<PartitaEliminazioneDiretta,String>("turno"));
        clCasaED.setCellValueFactory(new PropertyValueFactory<PartitaEliminazioneDiretta,String>("nomeSquadraCasa"));
        clOspiteED.setCellValueFactory(new PropertyValueFactory<PartitaEliminazioneDiretta,String>("nomeSquadraOspite"));
        clRisultatoED.setCellValueFactory(new PropertyValueFactory<PartitaEliminazioneDiretta,String>("risultato"));
        clTotaleED.setCellValueFactory(new PropertyValueFactory<PartitaEliminazioneDiretta,String>("risultatoComplessivo"));
        clSuperaTurno.setCellValueFactory(new PropertyValueFactory<PartitaEliminazioneDiretta,String>("squadraVincente"));

        clSquadra.setCellValueFactory(new PropertyValueFactory<Squadra,String>("nome"));
        clBiglietti.setCellValueFactory(new PropertyValueFactory<Squadra,String>("biglietti"));
        clEliminatoria.setCellValueFactory(new PropertyValueFactory<Squadra,String>("faseEliminatoria"));
        clFaseGironi.setCellValueFactory(new PropertyValueFactory<Squadra,String>("faseGironi"));
        clMarketPool.setCellValueFactory(new PropertyValueFactory<Squadra,String>("marketPool"));
        clRankingStorico.setCellValueFactory(new PropertyValueFactory<Squadra,String>("rankingStorico"));
        clTotale.setCellValueFactory(new PropertyValueFactory<Squadra,String>("guadagniTotali"));
        
        clNomeStadio.setCellValueFactory(new PropertyValueFactory<Stadio,String>("nome_Stadio"));
        clPartiteGiocate.setCellValueFactory(new PropertyValueFactory<Stadio,Integer>("countPartite"));
        clTotBiglietti.setCellValueFactory(new PropertyValueFactory<Stadio,Integer>("sommaCapienza"));
        clTotIncasso.setCellValueFactory(new PropertyValueFactory<Stadio,String>("sommaGuadagni"));
        clPercentuale.setCellValueFactory(new PropertyValueFactory<Stadio,Double>("percCapienza"));

      
        
    }
    public void setModel(Model model) {
    	this.model = model;
    	cmbGiornate.getItems().clear();
    	boxSquadraGirone.getItems().clear();
    	boxSquadraGirone.getItems().addAll(model.getSquadre());
    	cmbSquadraGuadagni.getItems().clear();
    	cmbSquadraGuadagni.getItems().addAll(model.getSquadre());
    	cmbStadio.getItems().clear();
    	cmbStadio.getItems().addAll(model.getStadi());
    	cmbTurno.getItems().clear();
    	cmbTurno.getItems().add("PlayOff");
    	cmbTurno.getItems().add("Ottavi");
    	cmbTurno.getItems().add("Quarti");
    	cmbTurno.getItems().add("Semifinali");
    	cmbTurno.getItems().add("Finale");


    	
		
    	File f1 = new File("Immagini/logoChampionsLeague.png");
		logo.setImage(new Image(f1.toURI().toString()));
		
    	
    	
    }


}










	