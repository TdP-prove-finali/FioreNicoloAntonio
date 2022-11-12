package it.polito.tdp.ChampionsLeague.model;

public class Federazione {
	private String campionato;
	private double punteggio;
	private double indicatore;
	private int numSquadrePerTurno;
	public Federazione(String campionato, double punteggio) {
		super();
		this.campionato = campionato;
		this.punteggio = punteggio;
		indicatore=0;
		numSquadrePerTurno=0;
	}
	public String getCampionato() {
		return campionato;
	}
	public void setCampionato(String campionato) {
		this.campionato = campionato;
	}
	public double getPunteggio() {
		return punteggio;
	}
	public void setPunteggio(double punteggio) {
		this.punteggio = punteggio;
	}
	
	public double getIndicatore() {
		return indicatore;
	}
	public void setIndicatore(double indicatore) {
		this.indicatore = indicatore;
	}
	public int getNumSquadrePerTurno() {
		return numSquadrePerTurno;
	}
	public void setNumSquadrePerTurno(int numSquadrePerTurno) {
		this.numSquadrePerTurno = numSquadrePerTurno;
	}
	public void aggiornaNumSquadrePerTurno(int numSquadrePerTurno) {
		this.numSquadrePerTurno += numSquadrePerTurno;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((campionato == null) ? 0 : campionato.hashCode());
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
		Federazione other = (Federazione) obj;
		if (campionato == null) {
			if (other.campionato != null)
				return false;
		} else if (!campionato.equals(other.campionato))
			return false;
		return true;
	}
	
	

}
