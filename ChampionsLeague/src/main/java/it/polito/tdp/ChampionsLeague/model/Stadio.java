package it.polito.tdp.ChampionsLeague.model;

public class Stadio implements Comparable<Stadio> {
	
	private String nome_Stadio;
	private int capienza;
	private String citta;
	private int anno_Costruzione;
	private int prezzoBiglietto;
	private float sommaGuadagni;
	private int sommaCapienzaG;
	private int sommaCapienzaED;
	private int countPartiteG;
	private int countPartiteED;
	private double percCapienza;
	
	public Stadio(String nome_Stadio, int capienza, String citta, int anno_Costruzione,int prezzoBiglietto) {
		super();
		this.nome_Stadio = nome_Stadio;
		this.capienza = capienza;
		this.citta = citta;
		this.anno_Costruzione = anno_Costruzione;
		this.prezzoBiglietto=prezzoBiglietto;
		sommaGuadagni=0;
		sommaCapienzaG=0;
		sommaCapienzaED=0;
		countPartiteG=0;
		countPartiteED=0;
		percCapienza=0;
	}
	public void reset() {
		sommaGuadagni=0;
		sommaCapienzaG=0;
		sommaCapienzaED=0;
		countPartiteG=0;
		countPartiteED=0;
		percCapienza=0;
	}
	public void resetED() {
		//sommaGuadagni=0;
		//sommaCapienzaG=0;
		sommaCapienzaED=0;
		//countPartiteG=0;
		countPartiteED=0;
		percCapienza=0;
	}

	public String getNome_Stadio() {
		return nome_Stadio;
	}

	public void setNome_Stadio(String nome_Stadio) {
		this.nome_Stadio = nome_Stadio;
	}

	public int getCapienza() {
		return capienza;
	}

	public void setCapienza(int capienza) {
		this.capienza = capienza;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public int getAnno_Costruzione() {
		return anno_Costruzione;
	}

	public void setAnno_Costruzione(int anno_Costruzione) {
		this.anno_Costruzione = anno_Costruzione;
	}
	
	

	public int getPrezzoBiglietto() {
		return prezzoBiglietto;
	}

	public void setPrezzoBiglietto(int prezzoBiglietto) {
		this.prezzoBiglietto = prezzoBiglietto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome_Stadio == null) ? 0 : nome_Stadio.hashCode());
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
		Stadio other = (Stadio) obj;
		if (nome_Stadio == null) {
			if (other.nome_Stadio != null)
				return false;
		} else if (!nome_Stadio.equals(other.nome_Stadio))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome_Stadio;
	}

	@Override
	public int compareTo(Stadio o) {
		return this.getNome_Stadio().compareTo(o.getNome_Stadio());
	}

	public String getSommaGuadagni() {
		return Math.round((sommaGuadagni/1000000)*100.0)/100.0+"M";
	}

	public void setSommaGuadagni(int sommaGuadagni) {
		this.sommaGuadagni += sommaGuadagni;
	}

	public int getSommaCapienza() {
		return sommaCapienzaG+sommaCapienzaED;
	}

	public void setSommaCapienzaG(int sommaCapienza) {
		this.sommaCapienzaG += sommaCapienza;
		countPartiteG++;
	}
	public void setSommaCapienzaED(int sommaCapienza) {
		this.sommaCapienzaED += sommaCapienza;
		countPartiteED++;
	}
	public int getCountPartite() {
		return countPartiteED+countPartiteG;
	}

	public double getPercCapienza() {
		double s=sommaCapienzaG+sommaCapienzaED;
		double stot=(capienza*(countPartiteED+countPartiteG));
		percCapienza=(s/stot)*100;
		return Math.round((percCapienza)*100.0)/100.0;
	}

	
	
	
	

}
