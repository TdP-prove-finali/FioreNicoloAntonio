package it.polito.tdp.ChampionsLeague.model;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PartitaGirone extends Partita {
	private Giornata giornata;
	private Squadra casa;
	private Squadra trasferta;
	private String livelloPartita;
	private boolean partitaAggiunta;
	private String risultato;
	private int golCasa;
	private int golTrasferta;
	private String nomeSquadraCasa;
	private String nomeSquadraOspite;
	private Integer idGiornata;
	private ImageView imgCasa;
	private ImageView imgOspite;	
	public PartitaGirone(Squadra casa, Squadra trasferta,Giornata giornata) {
		super(casa, trasferta);
		this.casa=casa;
		this.trasferta=trasferta;
		this.giornata=giornata;
		livelloPartita="";
		partitaAggiunta=false;
		risultato=casa.getNome()+" vs "+trasferta.getNome();
		golCasa=0;
		golTrasferta=0;
		nomeSquadraCasa=casa.getNome();
		nomeSquadraOspite=trasferta.getNome();
		idGiornata=0;
	//	imgCasa=new ImageView();
		//File f1 = new File("Immagini/logoChampionsLeague.png");
		//imgCasa.setImage(new Image(f1.toURI().toString()));
	}
	
	public Integer getIdGiornata() {
		idGiornata=giornata.getIdGiornata();
		return idGiornata;
	}

	public String getNomeSquadraCasa() {
		return nomeSquadraCasa;
	}

	public String getNomeSquadraOspite() {
		return nomeSquadraOspite;
	}
	

	public ImageView getImgCasa() {
		return imgCasa;
	}

	public Giornata getGiornata() {
		return giornata;
	}
	public void setGiornata(Giornata giornata) {
		idGiornata=giornata.getIdGiornata();
		this.giornata = giornata;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((casa == null) ? 0 : casa.hashCode());
		result = prime * result + ((trasferta == null) ? 0 : trasferta.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartitaGirone other = (PartitaGirone) obj;
		if (casa == null) {
				return false;
		}
		if (trasferta == null) {
				return false;
		}
		if (casa.equals(other.casa) && trasferta.equals(other.trasferta))
			return true;
		if(casa.equals(other.trasferta) && trasferta.equals(other.casa))
			return true;
		
		return false;
	}
	@Override
	public String toString() {
		return risultato;
	}
	public String getLivelloPartita() {
	    
		if(this.casa.getFascia()==1 && this.trasferta.getFascia()==1) {
			livelloPartita="1-1";
		}
		if(this.casa.getFascia()==1 && this.trasferta.getFascia()==2) {
			livelloPartita="1-2";
		}
		if(this.casa.getFascia()==1 && this.trasferta.getFascia()==3) {
			livelloPartita="1-3";
		}
		if(this.casa.getFascia()==1 && this.trasferta.getFascia()==4) {
			livelloPartita="1-4";
		}
		if(this.casa.getFascia()==2 && this.trasferta.getFascia()==1) {
			livelloPartita="2-1";
		}
		if(this.casa.getFascia()==2 && this.trasferta.getFascia()==2) {
			livelloPartita="2-2";
		}
		if(this.casa.getFascia()==2 && this.trasferta.getFascia()==3) {
			livelloPartita="2-3";
		}
		if(this.casa.getFascia()==2 && this.trasferta.getFascia()==4) {
			livelloPartita="2-4";
		}
		if(this.casa.getFascia()==3 && this.trasferta.getFascia()==1) {
			livelloPartita="3-1";
		}
		if(this.casa.getFascia()==3 && this.trasferta.getFascia()==2) {
			livelloPartita="3-2";
		}
		if(this.casa.getFascia()==3 && this.trasferta.getFascia()==3) {
			livelloPartita="3-3";
		}
		if(this.casa.getFascia()==3 && this.trasferta.getFascia()==4) {
			livelloPartita="3-4";
		}
		if(this.casa.getFascia()==4 && this.trasferta.getFascia()==1) {
			livelloPartita="4-1";
		}
		if(this.casa.getFascia()==4 && this.trasferta.getFascia()==2) {
			livelloPartita="4-2";
		}
		if(this.casa.getFascia()==4 && this.trasferta.getFascia()==3) {
			livelloPartita="4-3";
		}
		if(this.casa.getFascia()==4 && this.trasferta.getFascia()==4) {
			livelloPartita="4-4";
		}
		return livelloPartita;
	}
	public boolean isPartitaAggiunta() {
		return partitaAggiunta;
	}
	public void setPartitaAggiunta(boolean partitaAggiunta) {
		this.partitaAggiunta = partitaAggiunta;
	}
	public String getRisultato() {
		this.risultato =golCasa+" - "+golTrasferta;
		return risultato;
	}
	
	public void setGolCasa(int golCasa) {
		this.golCasa = golCasa;
	}
	public void setGolTrasferta(int golTrasferta) {
		this.golTrasferta = golTrasferta;
	}
	public int getGolCasa() {
		return golCasa;
	}
	public int getGolTrasferta() {
		return golTrasferta;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
