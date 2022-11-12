package it.polito.tdp.ChampionsLeague.model;

public class Partita {
	private Squadra casa;
	private Squadra trasferta;
	private String risultato;
	private int golCasa;
	private int golTrasferta;
	public Partita(Squadra casa, Squadra trasferta) {
		super();
		this.casa = casa;
		this.trasferta = trasferta;
		risultato=casa.getNome()+"vs"+trasferta.getNome();
		golCasa=0;
		golTrasferta=0;
	}
	public Squadra getCasa() {
		return casa;
	}
	public void setCasa(Squadra casa) {
		this.casa = casa;
	}
	public Squadra getTrasferta() {
		return trasferta;
	}
	public void setTrasferta(Squadra trasferta) {
		this.trasferta = trasferta;
	}
	public String getRisultato() {
		this.risultato = casa.getNome()+" "+golCasa+" - "+golTrasferta+" "+trasferta.getNome();
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
