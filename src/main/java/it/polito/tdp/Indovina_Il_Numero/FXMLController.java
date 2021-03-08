package it.polito.tdp.Indovina_Il_Numero;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class FXMLController {
	
	private int segreto;
	private int tentativiFatti;
	private boolean inGioco;
	private int NMAX ;
	private int TMAX ;
	private boolean okeasy;
	private boolean okmedium;
	private boolean okhard;
	private int rangeBasso = 1;
	private int rangeAlto = 1000;
	ArrayList<Integer> listaTentativi = new ArrayList<Integer>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane BorderPane;

    @FXML
    private Button buttonNuovaPartita;

    @FXML
    private TextField txtTentativi;

    @FXML
    private TextField txtNumeroUtente;

    @FXML
    private Button buttonProva;

    @FXML
    private ProgressBar progressB;

    @FXML
    private TextArea txtRisultato;

    @FXML
    private Button easy;

    @FXML
    private Button medium;

    @FXML
    private Button hard;

    @FXML
    private Button assist;
    
    @FXML
    private TextField testoAss;

    @FXML
    void difDifficile(ActionEvent event) {
    	
    	NMAX = 500;
    	TMAX = 7;
    	okeasy = false;
    	okmedium = false;
    	okhard = true;
    	txtRisultato.setText("Avvia la partita e successivamente inizia a giocare !");
    }

    @FXML
    void difFacile(ActionEvent event) {
    	
    	NMAX = 50;
    	TMAX = 4;
    	okeasy = true;
    	okmedium = false;
    	okhard = false;
    	txtRisultato.setText("Avvia la partita e successivamente inizia a giocare !");
    }

    @FXML
    void difMedia(ActionEvent event) {
    	
    	NMAX = 100;
    	TMAX = 5;
    	okeasy = false;
    	okmedium = true;
    	okhard = false;
    	txtRisultato.setText("Avvia la partita e successivamente inizia a giocare !");	
    }

    @FXML
    void modAssistita(ActionEvent event) {
    	testoAss.setText("Range: "+rangeBasso+"-"+rangeAlto+".");
    }

    @FXML
    void newGame(ActionEvent event) {

    	if( okeasy || okmedium || okhard ) {
    		inGioco = true;
    		this.segreto = (int)(Math.random() * NMAX +1);
    		this.tentativiFatti=0;
    		this.txtTentativi.setText(Integer.toString(TMAX));
    		buttonProva.setDisable(false);
    		txtRisultato.setText("");
    		progressB.setProgress(0);
    	}else {
    		inGioco = false;
    		buttonProva.setDisable(true);
    		txtRisultato.setText("Devi prima scegliere una modalità di gioco!.");
    		testoAss.setText("");
    	}
    	listaTentativi.clear();
    	if(okeasy) {
    		rangeBasso = 1;
        	rangeAlto = 50;
    	}
    	if(okmedium) {
    		rangeBasso = 1;
        	rangeAlto = 100;
    	}
    	if(okhard) {
    		rangeBasso = 1;
        	rangeAlto = 500;
    	}
    /*	progressB = new ProgressBar();
    	progressB.startFullDrag();
    	progressB.setMaxWidth(TMAX);
    	progressB.autosize();
    	progressB.setDisable(false);
    	progressB.isVisible();*/
    	
    }

    @FXML
    void tryNumber(ActionEvent event) {    	
    	
    	testoAss.setText("");
    	int contMin=0;
    	int contMax=0;
    	int verifica=0;

    	
    	//JProgressBar progressB = new JProgressBar(0,TMAX);
    	//valuto il tentativo appena inserito
    	String ts = txtNumeroUtente.getText();
    	int tentativo;
    	//vedo se il numero inserito è veramente un numero
    	try {
    		tentativo = Integer.parseInt(ts);
    	}catch(NumberFormatException e) {
    		txtRisultato.setText("Devi inserire un numero !");
    		return;
    	}
    	//provo a vedere se ho già inserito il tentativo precedentemente
    	for(Integer i : listaTentativi) {
    		if(i==tentativo) {
    			txtRisultato.setText("Numero già provato, inserisci un altro numero");
    			return;
    		}
    	}
    	//conto quanti tentativi già fatti sono presenti rispettivamente sotto e sopra il numero da indovinare
    	for(Integer ii:listaTentativi) {
    		if(ii<segreto) {
    			contMin++;
    		}
    	}
    	for(Integer ii:listaTentativi) {
    		if(ii>segreto) {
    			contMax++;
    		}
    	}
    	
    	listaTentativi.add(tentativo);
    	
    	txtNumeroUtente.setText("");
    	tentativiFatti++;
    	this.txtTentativi.setText(Integer.toString(TMAX-tentativiFatti));

    	if(tentativo == segreto) {
    		txtRisultato.setText("Complimenti hai indovinato usando : "+ tentativiFatti+" tentativi");
    		inGioco = false;
    		
    		return;
    	}
    	//guardo se il mio tentativo è il migliore nel range più basso
    	if(tentativo<segreto ) {
    		for(Integer i :listaTentativi) {
    			if(tentativo>i && i<segreto) {
    				verifica++;
    	
    			}
    		}
    		if(verifica==contMin) {
    			rangeBasso = tentativo;
    		}
    	}
    	//guardo se il mio tentativo è il migliore nel range più alto
    	if(tentativo>segreto ) {
    		for(Integer i :listaTentativi) {
    			if(tentativo<i && i>segreto) {
    				verifica++;
    			}
    		}
    		if(verifica==contMax) {
    			rangeAlto = tentativo;
    		}
    	}
    	if(tentativiFatti==TMAX) {
    		txtRisultato.setText("Hai Perso, tentativi finiti, il numero da indovinare era : "+ segreto +". \nSe vuoi puoi fare un'altra partita");
    		inGioco=false;
    		buttonProva.setDisable(true);
    	//	progressB.setVisible(0);
    		progressB.setProgress(tentativiFatti/TMAX);
    		return;
    	}
    	if(tentativo < segreto) {
    		txtRisultato.setText("Il tuo numero è troppo basso, hai ancora "+ (TMAX-tentativiFatti)+" tentativi");
    		//progressB.setVisible(tentativiFatti*20);
    		progressB.setProgress((double)(tentativiFatti)/(double)(TMAX));
    	}
    	if(tentativo > segreto) {
    		txtRisultato.setText("Il tuo numero è troppo alto, hai ancora "+ (TMAX-tentativiFatti)+" tentativi");
    	//	progressB.setVisible(tentativiFatti*20);
    		progressB.setProgress((double)(tentativiFatti)/(double)(TMAX));    	}
    }

    @FXML
    void initialize() {
        assert BorderPane != null : "fx:id=\"BorderPane\" was not injected: check your FXML file 'Scene.fxml'.";
        assert buttonNuovaPartita != null : "fx:id=\"buttonNuovaPartita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTentativi != null : "fx:id=\"txtTentativi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumeroUtente != null : "fx:id=\"txtNumeroUtente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert buttonProva != null : "fx:id=\"buttonProva\" was not injected: check your FXML file 'Scene.fxml'.";
        assert progressB != null : "fx:id=\"progressB\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert easy != null : "fx:id=\"easy\" was not injected: check your FXML file 'Scene.fxml'.";
        assert medium != null : "fx:id=\"medium\" was not injected: check your FXML file 'Scene.fxml'.";
        assert hard != null : "fx:id=\"hard\" was not injected: check your FXML file 'Scene.fxml'.";
        assert assist != null : "fx:id=\"assist\" was not injected: check your FXML file 'Scene.fxml'.";
        assert testoAss != null : "fx:id=\"testoAss\" was not injected: check your FXML file 'Scene.fxml'.";

    }
}


