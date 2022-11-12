package it.polito.tdp.ChampionsLeague.model;

public class PartitaEliminazioneDiretta extends Partita{
	private Squadra casa;
	private Squadra trasferta;
	private String turno;
	private int golSupplementariCasa;
	private int golSupplementariTrasferta;
	private int rigoriCasa;
	private int rigoriTrasferta;
	private String andataORitorno;
	private String risultatoComplessivo;
	private String squadraVincente;
	private String nomeSquadraCasa;
	private String nomeSquadraOspite;
	private String risultato;
	
	public PartitaEliminazioneDiretta(Squadra casa, Squadra trasferta,String turno) {
		super(casa, trasferta);
		this.casa = casa;
		this.trasferta = trasferta;
		this.turno=turno;
		golSupplementariCasa=0;
		golSupplementariTrasferta=0;
		rigoriCasa=0;
		rigoriTrasferta=0;
		andataORitorno="";
		nomeSquadraCasa=casa.getNome();
		nomeSquadraOspite=trasferta.getNome();
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
	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
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
		PartitaEliminazioneDiretta other = (PartitaEliminazioneDiretta) obj;
		if (casa == null) {
			if (other.casa != null)
				return false;
		} else if (!casa.equals(other.casa))
			return false;
		if (trasferta == null) {
			if (other.trasferta != null)
				return false;
		} else if (!trasferta.equals(other.trasferta))
			return false;
		return true;
	}

	public int getGolSupplementariCasa() {
		return golSupplementariCasa;
	}

	public void setGolSupplementariCasa(int golSupplementariCasa) {
		this.golSupplementariCasa += golSupplementariCasa;
	}

	public int getGolSupplementariTrasferta() {
		return golSupplementariTrasferta;
	}

	public void setGolSupplementariTrasferta(int golSupplementariTrasferta) {
		this.golSupplementariTrasferta += golSupplementariTrasferta;
	}

	public int getRigoriCasa() {
		return rigoriCasa;
	}

	public void setRigoriCasa(int rigoriCasa) {
		this.rigoriCasa = rigoriCasa;
	}

	public int getRigoriTrasferta() {
		return rigoriTrasferta;
	}

	public void setRigoriTrasferta(int rigoriTrasferta) {
		this.rigoriTrasferta = rigoriTrasferta;
	}

	public String getAndataORitorno() {
		return andataORitorno;
	}

	public void setAndataORitorno(String andataORitorno) {
		this.andataORitorno = andataORitorno;
	}

	public String getRisultatoComplessivo() {
		return risultatoComplessivo;
	}

	public void setRisultatoComplessivo(String risultatoComplessivo) {
		this.risultatoComplessivo = risultatoComplessivo;
	}

	public String getSquadraVincente() {
		return squadraVincente;
	}

	public void setSquadraVincente(String squadraVincente) {
		this.squadraVincente = squadraVincente;
	}

	public String getNomeSquadraCasa() {
		return nomeSquadraCasa;
	}

	public String getNomeSquadraOspite() {
		return nomeSquadraOspite;
	}

	public String getRisultato() {
		if(golSupplementariCasa==0 && golSupplementariTrasferta==0 && rigoriCasa==0 && rigoriTrasferta==0) {
			risultato=getGolCasa()+" - "+getGolTrasferta();
		}
		else if((golSupplementariCasa!=0 || golSupplementariTrasferta!=0) && (rigoriCasa==0 && rigoriTrasferta==0) ) {
			risultato=(getGolCasa()+golSupplementariCasa)+" - "+(getGolTrasferta()+golSupplementariTrasferta)+"DTS";
		}
		else{
			risultato=(getGolCasa()+golSupplementariCasa+rigoriCasa)+" - "+(getGolTrasferta()+golSupplementariTrasferta+rigoriTrasferta)+"DCR";
		}
		return risultato;
	}
	
    
	
	
	
	
}
