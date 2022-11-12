package it.polito.tdp.ChampionsLeague.model;

public class Evento implements Comparable<Evento> {
	private int id;
	private Partita pg;
	public Evento(int id, Partita pg) {
		super();
		this.id = id;
		this.pg = pg;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Partita getPg() {
		return pg;
	}
	public void setPg(Partita pg) {
		this.pg = pg;
	}
	@Override
	public int compareTo(Evento o) {
		if(this.pg instanceof PartitaGirone && o.getPg() instanceof PartitaGirone) {
		if(((PartitaGirone)this.getPg()).getGiornata().getIdGiornata()!=((PartitaGirone)o.getPg()).getGiornata().getIdGiornata()) {
			return ((PartitaGirone)this.getPg()).getGiornata().getIdGiornata()-((PartitaGirone)o.getPg()).getGiornata().getIdGiornata();
		}
		else 
			return this.getId()-o.getId();
		}
		else if(this.pg instanceof PartitaGirone && o.getPg() instanceof PartitaEliminazioneDiretta) {
		return -1;
		}
		else if(this.pg instanceof PartitaEliminazioneDiretta && o.getPg() instanceof PartitaGirone) {
			return 1;
		}
		else{
			return -1;
		}
		
	}
	
	

}
