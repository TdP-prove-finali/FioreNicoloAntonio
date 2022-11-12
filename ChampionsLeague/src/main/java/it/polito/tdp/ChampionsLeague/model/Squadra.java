package it.polito.tdp.ChampionsLeague.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Squadra implements Comparable<Squadra>{
	private String nome;
	private String campionato;
	private String nomeStadio;
	private int posizioneRanking;
	private int OVR;
	private int ATT;
	private int MID;
	private int DEF;
	private int fascia;
	
	
	private int countPartite;
	private int count1Casa;
	private int count2Casa;
	private int count3Casa;
	private int count4Casa;
	private int count1Trasferta;
	private int count2Trasferta;
	private int count3Trasferta;
	private int count4Trasferta;
	private int countCasa;
	private int countTrasferta;
	
	private int posizione;
	private int punti;
	private int golFatti;
	private int golSubiti;
	private int diffReti;
	
	private float rankingStorico;
    private float marketPool;
    private int playOff;
    private int vittoriaPlayOff;
    private int ottavi;
    private int quarti;
    private int semifinale;
    private int finale;
    private int vittoriaChampions;
    private int gironi;
    private float faseGironi;
    private float faseEliminatoria;
    private float bigliettiG;
    private float bigliettiED;
    private float guadagniTotali;
    
	
	private List<PartitaGirone> partiteGirone;
	Set<Squadra> possibiliAvversarie;
	
	private List<PartitaEliminazioneDiretta> partiteEliminazioneDiretta;
	
	
	public Squadra(String nome, String campionato, String nomeStadio,int posizioneRanking, int oVR, int aTT, int mID, int dEF, int fascia) {
		super();
		this.nome = nome;
		this.campionato = campionato;
		this.nomeStadio = nomeStadio;
		this.posizioneRanking=posizioneRanking;
		OVR = oVR;
		ATT = aTT;
		MID = mID;
		DEF = dEF;
		this.fascia = fascia;
		this.count1Casa=0;
		this.count2Casa=0;
		this.count3Casa=0;
		this.count4Casa=0;
		this.count1Trasferta=0;
		this.count2Trasferta=0;
		this.count3Trasferta=0;
		this.count4Trasferta=0;
		this.countPartite=0;
		this.countCasa=0;
		this.countTrasferta=0;
		
		punti=0;
		golFatti=0;
		golSubiti=0;
		diffReti=0;
		
	    rankingStorico=0;
	    marketPool=0;
	    playOff=0;
	    vittoriaPlayOff=0;
	    ottavi=0;
	    quarti=0;
	    semifinale=0;
	    finale=0;
	    vittoriaChampions=0;
	    gironi=0;
	    
	    this.bigliettiG=0;
	    this.bigliettiED=0;
	    this.faseEliminatoria=0;
	    this.faseGironi=0;
	    guadagniTotali=0;
	    
	    partiteGirone=new ArrayList<>();
	    partiteEliminazioneDiretta=new ArrayList<>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCampionato() {
		return campionato;
	}

	public void setCampionato(String campionato) {
		this.campionato = campionato;
	}

	public String getNomeStadio() {
		return nomeStadio;
	}

	public void setNomeStadio(String nomeStadio) {
		this.nomeStadio = nomeStadio;
	}
	

	public int getPosizioneRanking() {
		return posizioneRanking;
	}

	public void setPosizioneRanking(int posizioneRanking) {
		this.posizioneRanking = posizioneRanking;
	}

	public int getOVR() {
		return OVR;
	}

	public void setOVR(int oVR) {
		OVR = oVR;
	}

	public int getATT() {
		return ATT;
	}

	public void setATT(int aTT) {
		ATT = aTT;
	}

	public int getMID() {
		return MID;
	}

	public void setMID(int mID) {
		MID = mID;
	}

	public int getDEF() {
		return DEF;
	}

	public void setDEF(int dEF) {
		DEF = dEF;
	}

	public int getFascia() {
		return fascia;
	}

	public void setFascia(int fascia) {
		this.fascia = fascia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Squadra other = (Squadra) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}

	
	public String stringaGuadagni() {
		return "Squadra [getRankingStorico()=" + getRankingStorico() + ", getMarketPool()=" + getMarketPool()
				+ ", getPlayOff()=" + getPlayOff() + ", getVittoriaPlayOff()=" + getVittoriaPlayOff() + ", getOttavi()="
				+ getOttavi() + ", getQuarti()=" + getQuarti() + ", getSemifinale()=" + getSemifinale()
				+ ", getFinale()=" + getFinale() + ", getVittoriaChampions()=" + getVittoriaChampions()
				+ ", getGironi()=" + getGironi() + ", guadagniTotali()=" + getGuadagniTotali() + "]";
	}

	public int getCountPartite() {
		return countPartite;
	}

	public void incrementaCountPartite() {
		this.countPartite++;
	}
    public void decrementaCountPartite() {
    	this.countPartite--;
    }
	public int getCount1Casa() {
		return count1Casa;
	}

	public void setCount1Casa() {
		this.count1Casa++;
	}
	public void decrementaCount1Casa() {
    	this.count1Casa--;
    }

	public int getCount2Casa() {
		return count2Casa;
	}

	public void setCount2Casa() {
		this.count2Casa++;
	}
	public void decrementaCount2Casa() {
    	this.count2Casa--;
    }
	public int getCount3Casa() {
		return count3Casa;
	}

	public void setCount3Casa() {
		this.count3Casa++;
	}
	public void decrementaCount3Casa() {
    	this.count3Casa--;
    }
	public int getCount4Casa() {
		return count4Casa;
	}

	public void setCount4Casa() {
		this.count4Casa++;
	}
	public void decrementaCount4Casa() {
    	this.count4Casa--;
    }
	public int getCount1Trasferta() {
		return count1Trasferta;
	}

	public void setCount1Trasferta() {
		this.count1Trasferta++;
	}
	public void decrementaCount1Trasferta() {
    	this.count1Trasferta--;
    }

	public int getCount2Trasferta() {
		return count2Trasferta;
	}

	public void setCount2Trasferta() {
		this.count2Trasferta++;
	}
	public void decrementaCount2Trasferta() {
    	this.count2Trasferta--;
    }
	public int getCount3Trasferta() {
		return count3Trasferta;
	}

	public void setCount3Trasferta() {
		this.count3Trasferta++;
	}
	public void decrementaCount3Trasferta() {
    	this.count3Trasferta--;
    }
	public int getCount4Trasferta() {
		return count4Trasferta;
	}

	public void setCount4Trasferta() {
		this.count4Trasferta++;
	}
	public void decrementaCount4Trasferta() {
    	this.count4Trasferta--;
    }
	public int getCountCasa() {
		return countCasa;
	}

	public void setCountCasa() {
		this.countCasa++;
	}
	public void decrementaCountCasa() {
    	this.countCasa--;
    }
	public int getCountTrasferta() {
		return countTrasferta;
	}

	public void setCountTrasferta() {
		this.countTrasferta++;
	}
	public void decrementaCountTrasferta() {
    	this.countTrasferta--;
    }
	
	public void inizializza() {
		this.count1Casa=0;
		this.count2Casa=0;
		this.count3Casa=0;
		this.count4Casa=0;
		this.count1Trasferta=0;
		this.count2Trasferta=0;
		this.count3Trasferta=0;
		this.count4Trasferta=0;
		this.countPartite=0;
		this.countCasa=0;
		this.countTrasferta=0;
		partiteGirone=new ArrayList<>();
		
		punti=0;
		golFatti=0;
		golSubiti=0;
		diffReti=0;
		
	    rankingStorico=0;
	    marketPool=0;
	    playOff=0;
	    vittoriaPlayOff=0;
	    ottavi=0;
	    quarti=0;
	    semifinale=0;
	    finale=0;
	    vittoriaChampions=0;
	    gironi=0;
	    
	    this.bigliettiG=0;
	    this.bigliettiED=0;
	    this.faseEliminatoria=0;
	    this.faseGironi=0;
	    guadagniTotali=0;
	    partiteEliminazioneDiretta=new ArrayList<>();
		
	}
	public void reset() {
		punti=0;
		golFatti=0;
		golSubiti=0;
		diffReti=0;
		
	    rankingStorico=0;
	    marketPool=0;
	    playOff=0;
	    vittoriaPlayOff=0;
	    ottavi=0;
	    quarti=0;
	    semifinale=0;
	    finale=0;
	    vittoriaChampions=0;
	    gironi=0;
	    
	    this.bigliettiG=0;
	    this.bigliettiED=0;
	    this.faseEliminatoria=0;
	    this.faseGironi=0;
	    guadagniTotali=0;
	    partiteEliminazioneDiretta=new ArrayList<>();
	}
	public void resetED() {
		
		
	    //rankingStorico=0;
	   // marketPool=0;
	    playOff=0;
	    vittoriaPlayOff=0;
	    ottavi=0;
	    quarti=0;
	    semifinale=0;
	    finale=0;
	    vittoriaChampions=0;
	    gironi=0;
	    
	    this.bigliettiED=0;
	    this.faseEliminatoria=0;
	   // this.faseGironi=0;
	    guadagniTotali=0;
	    partiteEliminazioneDiretta=new ArrayList<>();
	}

	public int getPunti() {
		return punti;
	}

	public void aggiornaPunti(int puntiFatti) {
		this.punti += puntiFatti;
	}
	
   
    public void aggiungiPartita(PartitaGirone p) {
    	partiteGirone.add(p);
    }
    
    public void rimuoviPartita(PartitaGirone p) {
    	partiteGirone.remove(p);
    }
    
    public List<PartitaGirone> getPartite(){
    	return partiteGirone;
    }
    
    public void aggiungiAvversarie(Set<Squadra> avversarie) {
    	possibiliAvversarie=new HashSet<>(avversarie);
    }
    public Set<Squadra> getPossibiliAvversarie(){
    	return possibiliAvversarie;
    }

	public int getGolFatti() {
		return golFatti;
	}

	public void setGolFatti(int golFatti) {
		this.golFatti += golFatti;
	}

	public int getGolSubiti() {
		return golSubiti;
	}

	public void setGolSubiti(int golSubiti) {
		this.golSubiti += golSubiti;
	}
	

	public int getDiffReti() {
	     diffReti= this.golFatti-this.golSubiti;
		 return diffReti;
	}

	

	@Override
	public int compareTo(Squadra o2) {
		if(this.getPunti()!=o2.getPunti())
			return this.getPunti()-o2.getPunti();
			else if(this.getDiffReti()!=o2.getDiffReti())
				return this.getDiffReti()-o2.getDiffReti();
			else return this.getGolFatti()-o2.getGolFatti();
	}

	public String getRankingStorico() {
		 return Math.round((rankingStorico/1000000)*100.0)/100.0+"M";
	}

	public void setRankingStorico(int rankingStorico) {
		this.rankingStorico = rankingStorico;
	}

	public String getMarketPool() {
		return Math.round((marketPool/1000000)*100.0)/100.0+"M";
	}

	public void setMarketPool(int marketPool) {
		this.marketPool += marketPool;
	}

	public int getPlayOff() {
		return playOff;
	}

	public void setPlayOff(int playOff) {
		this.faseGironi+=playOff;
		this.playOff = playOff;
	}

	public int getVittoriaPlayOff() {
		return vittoriaPlayOff;
	}

	public void setVittoriaPlayOff(int vittoriaPlayOff) {
		
		this.vittoriaPlayOff = vittoriaPlayOff;
	}

	public int getOttavi() {
		return ottavi;
	}

	public void setOttavi(int ottavi) {
		this.faseGironi+=ottavi;
		this.ottavi = ottavi;
	}

	public int getQuarti() {
		return quarti;
	}

	public void setQuarti(int quarti) {
		this.faseEliminatoria+=quarti;
		this.quarti = quarti;
	}

	public int getSemifinale() {
		return semifinale;
	}

	public void setSemifinale(int semifinale) {
		this.faseEliminatoria+=semifinale;
		this.semifinale = semifinale;
	}
    
	public int getFinale() {
		return finale;
	}

	public void setFinale(int finale) {
		this.faseEliminatoria+=finale;
		this.finale = finale;
	}

	public int getVittoriaChampions() {
		return vittoriaChampions;
	}

	public void setVittoriaChampions(int vittoriaChampions) {
		this.faseEliminatoria+=vittoriaChampions;
		this.vittoriaChampions = vittoriaChampions;
	}

	public int getGironi() {
		return gironi;
	}

	public void setGironi(int gironi) {
		faseGironi+=gironi;
		this.gironi += gironi;
	}
	public void setGuadagni(int guadagno,String turno) {
		if(turno.equals("PlayOff")) {
    		vittoriaPlayOff+=guadagno;
    		this.faseEliminatoria+=vittoriaPlayOff;
		}
    	if(turno.equals("Ottavi")) {
    		quarti+=guadagno;
    		this.faseEliminatoria+=quarti;
    	}
    	if(turno.equals("Quarti")) {
    		semifinale+=guadagno;
    		this.faseEliminatoria+=semifinale;
    	}
    	if(turno.equals("Semifinale")) {
    		finale+=guadagno;
    		this.faseEliminatoria+=finale;
    	}
	}
    
	public void guadagniTotali() {
		guadagniTotali= this.gironi+this.marketPool+this.ottavi+this.playOff+this.rankingStorico+this.quarti
				+this.semifinale+this.vittoriaPlayOff+this.vittoriaChampions+this.finale+this.bigliettiG+this.bigliettiED;
		
	}
	
	
	public String getGuadagniTotali() {
		return Math.round((guadagniTotali/1000000)*100.0)/100.0+"M";
	}

	public void aggiungiString(PartitaEliminazioneDiretta res) {
		partiteEliminazioneDiretta.add(res);
	}
	public List<PartitaEliminazioneDiretta> getRisultatiEliminazioneDiretta(){
		return partiteEliminazioneDiretta;
	}
    
	public void inizializzaValori() {
		this.punti=0;
		this.golFatti=0;
		this.golSubiti=0;
	}

	public String getFaseGironi() {
		return faseGironi/1000000+"M";
	}

	public String getFaseEliminatoria() {
		return faseEliminatoria/1000000+"M";
	}

	public String getBiglietti() {
		return Math.round(((bigliettiG+bigliettiED)/1000000)*100.0)/100.0+"M";
	}

	public void setBigliettiG(int biglietti) {
		this.bigliettiG += biglietti;
	}
	public void setBigliettiED(int biglietti) {
		this.bigliettiED += biglietti;
	}
	public int getPosizione() {
		return posizione;
	}

	public void setPosizione(int posizione) {
		this.posizione = posizione;
	}
	
	
	
	

}
