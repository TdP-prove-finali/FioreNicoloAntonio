package it.polito.tdp.ChampionsLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

public class Simulatore {
    private PriorityQueue<Evento> coda;
    private final int rankingStorico =1137000;
    private final int marketPool=255000000;
    private final int playOff=3000000;
    private final int vittoriaPlayOff=4500000;
    private final int ottavi=9600000;
    private final int quarti=10600000;
    private final int semifinale=12500000;
    private final int finale=15500000;
    private final int vittoriaChampions=4500000;
    private final int vittoria=2800000;
    private final int pareggio=930000;
    private final double percMarketPoolGirone=0.44;
    private final double percMarketPoolOttavi=0.2;
    private final double percMarketPoolQuarti=0.16;
    private final double percMarketPoolSemi=0.12;
    private final double percMarketPoolFinale=0.08;
    private List<String> risultatiPartite;
    private List<String> risultatiPlayOff;
    private List<String> risultatiOttavi;
    private List<String> risultatiQuarti;
    private List<String> risultatiSemifinali;
    private String risultatoFinale;	
    private Map<String,Stadio> idStadi;
    
	public Simulatore(PriorityQueue<Evento> coda,Map<String,Stadio> idStadi) {
		super();
		this.coda = coda;
		risultatiPartite=new ArrayList<>();
		risultatiPlayOff=new ArrayList<>();
		risultatiOttavi=new ArrayList<>();
		risultatiQuarti=new ArrayList<>();
		risultatiSemifinali=new ArrayList<>();
		risultatoFinale="";
		this.idStadi=idStadi;
	}
	public void calcolaGuadagniIniziali(List<Squadra> squadre,Set<Federazione> federazioni) {
		Map<String,Federazione> idMap=new HashMap<>();
		Collections.sort(squadre,new ComparatorePerRanking());
		Collections.reverse(squadre);
		int cont=1;
		for(Squadra s :squadre) {
			s.setRankingStorico(cont*rankingStorico);
			cont++;
		}
		double totalePuntiFederazione=0;
		for(Federazione f: federazioni) {
			totalePuntiFederazione+=f.getPunteggio();
			idMap.put(f.getCampionato(), f);
		}
		for(Federazione ff: federazioni) {
			ff.setIndicatore(ff.getPunteggio()/totalePuntiFederazione);
		}
		for(Squadra ss: squadre ) {
			Federazione fed=idMap.get(ss.getCampionato());
			ss.setMarketPool((int)(marketPool*percMarketPoolGirone*fed.getIndicatore()/fed.getNumSquadrePerTurno()));
		}
		
	}
	public void calcolaGuadagniOttavi(List<Squadra> squadreAgliOttavi) {
		for(Squadra s:squadreAgliOttavi) {
			s.setOttavi(ottavi);
		}
	}
	public void calcolaGuadagniPlayOff(List<Squadra> squadrePlayOff) {
		for(Squadra s:squadrePlayOff) {
			s.setPlayOff(playOff);
		}
	}
	public void calcolaMarketPool(List<Squadra> squadre,Map<String,Federazione> idFederazioni,String turno) {
		double perc=0;
    	if(turno.equals("Ottavi"))
    		perc=percMarketPoolOttavi;
    	if(turno.equals("Quarti"))
    		perc=percMarketPoolQuarti;
    	if(turno.equals("Semifinale"))
    		perc=percMarketPoolSemi;
    	if(turno.equals("Finale"))
    		perc=percMarketPoolFinale;
    	
    	Set<Federazione> fedPresenti=new HashSet<Federazione>();
    	for(Federazione fed: idFederazioni.values()) {
    		fed.setNumSquadrePerTurno(0);
    	}
		for(Squadra s: squadre) {
			Federazione f=idFederazioni.get(s.getCampionato());
			f.aggiornaNumSquadrePerTurno(1);
			if(!fedPresenti.contains(f))
			fedPresenti.add(f);		
			}
		double totalePuntiFederazione=0;
		for(Federazione f: fedPresenti) {
			totalePuntiFederazione+=f.getPunteggio();
			//idMap.put(f.getCampionato(), f);
		}
		for(Federazione ff: fedPresenti) {
			ff.setIndicatore(ff.getPunteggio()/totalePuntiFederazione);
		}
		for(Squadra ss: squadre ) {
			Federazione fed=idFederazioni.get(ss.getCampionato());
			ss.setMarketPool((int)(marketPool*perc*fed.getIndicatore()/fed.getNumSquadrePerTurno()));
		}
		
	}
	public Squadra finale(PriorityQueue<Evento> coda,PartitaEliminazioneDiretta p) {
		while(!coda.isEmpty()) {
    		Evento e= coda.poll();
    		processEventFinale(e);
    	}
		Squadra s1=p.getCasa();
		Squadra s2=p.getTrasferta();
		String res="Finale :";
		String res1="";
		p.setGolCasa(p.getGolSupplementariCasa());
		p.setGolTrasferta(p.getGolSupplementariTrasferta());
		p.setGolSupplementariCasa(-p.getGolSupplementariCasa());
	    p.setGolSupplementariTrasferta(-p.getGolSupplementariTrasferta());
		if(p.getGolCasa()>p.getGolTrasferta()) {
		s1.setVittoriaChampions(vittoriaChampions);
		p.setSquadraVincente(s1.getNome());
		return s1;
		}
		if(p.getGolCasa()<p.getGolTrasferta()) {
			s2.setVittoriaChampions(vittoriaChampions);
			p.setSquadraVincente(s2.getNome());
			return s2;
		}
		if(p.getGolCasa()==p.getGolTrasferta()) {
			supplementari(s1, s2, p);
			if(p.getGolSupplementariCasa()>p.getGolSupplementariTrasferta()) {
				s1.setVittoriaChampions(vittoriaChampions);
				p.setSquadraVincente(s1.getNome());
				return s1;
				}
				if(p.getGolSupplementariCasa()<p.getGolSupplementariTrasferta()) {
					s2.setVittoriaChampions(vittoriaChampions);
					p.setSquadraVincente(s2.getNome());
					return s2;
				}
				if(p.getGolSupplementariCasa()==p.getGolSupplementariTrasferta()) {
					rigori(s1, s2, p);
					if(p.getRigoriCasa()>p.getRigoriTrasferta()) {
			        s1.setVittoriaChampions(vittoriaChampions);
			        p.setSquadraVincente(s1.getNome());
			        return s1;
					}
					if(p.getRigoriCasa()<p.getRigoriTrasferta()) {
				       
				        s2.setVittoriaChampions(vittoriaChampions);
				        p.setSquadraVincente(s2.getNome());
				        return s2;
						}
				}
				
			}
		risultatoFinale=res;
		risultatiPartite.add(res);
		return null;
	}
    public void calcolaRisultati() {
    	while(!coda.isEmpty()) {
    		Evento e= coda.poll();
    		processEvent(e);
    	}
    }
    public List<Squadra> calcolaRisultatiEliminazioneDiretta(PriorityQueue<Evento> coda,List<PartitaEliminazioneDiretta> partitePlayOff,String turno) {
    	List<Squadra> vincitrici=new ArrayList<>();
    	int guadagno=0;
    	if(turno.equals("PlayOff"))
    		guadagno=vittoriaPlayOff;
    	if(turno.equals("Ottavi"))
    		guadagno=quarti;
    	if(turno.equals("Quarti"))
    		guadagno=semifinale;
    	if(turno.equals("Semifinale"))
    		guadagno=finale;
    	while(!coda.isEmpty()) {
    		Evento e= coda.poll();
    		processEventEliminazioneDiretta(e);
    	}
    	String res=turno+" :";
    	String res1="";
    	
    	for(PartitaEliminazioneDiretta p: partitePlayOff ) {
			for(PartitaEliminazioneDiretta pp: partitePlayOff ) {
				if(p.getCasa().equals(pp.getTrasferta()) && p.getTrasferta().equals(pp.getCasa())) {
					Squadra s1=p.getCasa();
					Squadra s2=p.getTrasferta();
					if((p.getGolCasa()+pp.getGolTrasferta())>(p.getGolTrasferta()+pp.getGolCasa())) {
						if(!vincitrici.contains(s1)) {
							vincitrici.add(s1);
						s1.setGuadagni(guadagno, turno);
						res1="\nAndata :"+s1.getNome()+" "+p.getGolCasa()+" - "+p.getGolTrasferta()+" "+s2.getNome()+
								"\nRitorno : "+s2.getNome()+" "+pp.getGolCasa()+" - "+pp.getGolTrasferta()+" "+s1.getNome()+
								"\nTotale : "+s1.getNome()+" "+(p.getGolCasa()+pp.getGolTrasferta())+" - "+(p.getGolTrasferta()+pp.getGolCasa())+" "+s2.getNome();
						res+=res1;
						pp.setRisultatoComplessivo((p.getGolTrasferta()+pp.getGolCasa())+" - "+(p.getGolCasa()+pp.getGolTrasferta()));
						pp.setSquadraVincente(s1.getNome());
						}
					}
					if((p.getGolCasa()+pp.getGolTrasferta())<(p.getGolTrasferta()+pp.getGolCasa())) {
						if(!vincitrici.contains(s2)) {
							vincitrici.add(s2);
						s2.setGuadagni(guadagno, turno);
						res1="\nAndata :"+s1.getNome()+" "+p.getGolCasa()+" - "+p.getGolTrasferta()+" "+s2.getNome()+
								"\nRitorno : "+s2.getNome()+" "+pp.getGolCasa()+" - "+pp.getGolTrasferta()+" "+s1.getNome()+
								"\nTotale : "+s1.getNome()+" "+(p.getGolCasa()+pp.getGolTrasferta())+" - "+(p.getGolTrasferta()+pp.getGolCasa())+" "+s2.getNome();
						res+=res1;
						pp.setRisultatoComplessivo((p.getGolTrasferta()+pp.getGolCasa())+" - "+(p.getGolCasa()+pp.getGolTrasferta()));
						pp.setSquadraVincente(s2.getNome());
						}
					}
					if((p.getGolCasa()+pp.getGolTrasferta())==(p.getGolTrasferta()+pp.getGolCasa())) {
						if(!vincitrici.contains(s1) && !vincitrici.contains(s2)) {
						supplementari(s2,s1,pp);
						if(pp.getGolSupplementariCasa()>pp.getGolSupplementariTrasferta()) {
								vincitrici.add(s2);
								s1.setGuadagni(guadagno, turno);
								res1="\nAndata :"+s1.getNome()+" "+p.getGolCasa()+" - "+p.getGolTrasferta()+" "+s2.getNome()+
										"\nRitorno : "+s2.getNome()+" "+pp.getGolCasa()+" - "+pp.getGolTrasferta()+" "+s1.getNome()+
										"\nTotale : "+s1.getNome()+" "+(p.getGolCasa()+pp.getGolTrasferta())+" - "+(p.getGolTrasferta()+pp.getGolCasa())+" "+s2.getNome()+
										"\nSupplementari : "+s1.getNome()+" "+(p.getGolCasa()+pp.getGolTrasferta()+pp.getGolSupplementariCasa())+" - "+(p.getGolTrasferta()+pp.getGolCasa()+pp.getGolSupplementariTrasferta())+" "+s2.getNome();
								res+=res1;
								pp.setRisultatoComplessivo((p.getGolTrasferta()+pp.getGolCasa()+pp.getGolSupplementariCasa())+" - "+(p.getGolCasa()+pp.getGolTrasferta()+pp.getGolSupplementariTrasferta()));
								pp.setSquadraVincente(s2.getNome());
								
						}
						if(pp.getGolSupplementariCasa()<pp.getGolSupplementariTrasferta()) {
								vincitrici.add(s1);
								s2.setGuadagni(guadagno, turno);
								res1="\nAndata :"+s1.getNome()+" "+p.getGolCasa()+" - "+p.getGolTrasferta()+" "+s2.getNome()+
										"\nRitorno : "+s2.getNome()+" "+pp.getGolCasa()+" - "+pp.getGolTrasferta()+" "+s1.getNome()+
										"\nTotale : "+s1.getNome()+" "+(p.getGolCasa()+pp.getGolTrasferta())+" - "+(p.getGolTrasferta()+pp.getGolCasa())+" "+s2.getNome()+
										"\nSupplementari : "+s1.getNome()+" "+(p.getGolCasa()+pp.getGolTrasferta()+p.getGolSupplementariCasa())+" - "+(p.getGolTrasferta()+pp.getGolCasa()+p.getGolSupplementariTrasferta())+" "+s2.getNome();
								res+=res1;
								//pp.setRisultatoComplessivo((p.getGolCasa()+pp.getGolTrasferta()+pp.getGolSupplementariTrasferta())+" - "+(p.getGolTrasferta()+pp.getGolCasa()+pp.getGolSupplementariCasa())+"DTS");
								pp.setRisultatoComplessivo((p.getGolTrasferta()+pp.getGolCasa()+pp.getGolSupplementariCasa())+" - "+(p.getGolCasa()+pp.getGolTrasferta()+pp.getGolSupplementariTrasferta()));
								pp.setSquadraVincente(s1.getNome());
						}
						
						if(pp.getGolSupplementariCasa()==pp.getGolSupplementariTrasferta()) {
							rigori(s2,s1,pp);
							if(pp.getRigoriCasa()>pp.getRigoriTrasferta()) {
								if(!vincitrici.contains(s2)) {
									vincitrici.add(s2);
									s1.setGuadagni(guadagno, turno);
									res1="\nAndata :"+s1.getNome()+" "+p.getGolCasa()+" - "+p.getGolTrasferta()+" "+s2.getNome()+
											"\nRitorno : "+s2.getNome()+" "+pp.getGolCasa()+" - "+pp.getGolTrasferta()+" "+s1.getNome()+
											"\nTotale : "+s1.getNome()+" "+(p.getGolCasa()+pp.getGolTrasferta())+" - "+(p.getGolTrasferta()+pp.getGolCasa())+" "+s2.getNome()+
											"\nSupplementari : "+s1.getNome()+" "+(p.getGolCasa()+pp.getGolTrasferta()+p.getGolSupplementariCasa())+" - "+(p.getGolTrasferta()+pp.getGolCasa()+p.getGolSupplementariTrasferta())+" "+s2.getNome()+
											"\nLa squadra "+s1.getNome()+" vince ai rigori "+p.getRigoriCasa()+" - "+p.getRigoriTrasferta();
									res+=res1;
									pp.setRisultatoComplessivo((p.getGolTrasferta()+pp.getGolCasa()+pp.getGolSupplementariCasa()+pp.getRigoriCasa())+" - "+(p.getGolCasa()+pp.getGolTrasferta()+pp.getGolSupplementariTrasferta()+pp.getRigoriTrasferta()));
									pp.setSquadraVincente(s2.getNome());
								}
							}
							if(pp.getRigoriCasa()<pp.getRigoriTrasferta()) {
								if(!vincitrici.contains(s1)) {
									vincitrici.add(s1);
									s2.setGuadagni(guadagno, turno);
									res1="\nAndata :"+s1.getNome()+" "+p.getGolCasa()+" - "+p.getGolTrasferta()+" "+s2.getNome()+
											"\nRitorno : "+s2.getNome()+" "+pp.getGolCasa()+" - "+pp.getGolTrasferta()+" "+s1.getNome()+
											"\nTotale : "+s1.getNome()+" "+(p.getGolCasa()+pp.getGolTrasferta())+" - "+(p.getGolTrasferta()+pp.getGolCasa())+" "+s2.getNome()+
											"\nSupplementari : "+s1.getNome()+" "+(p.getGolCasa()+pp.getGolTrasferta()+p.getGolSupplementariCasa())+" - "+(p.getGolTrasferta()+pp.getGolCasa()+p.getGolSupplementariTrasferta())+" "+s2.getNome()+
											"\nLa squadra "+s2.getNome()+" vince ai rigori "+p.getRigoriTrasferta()+" - "+p.getRigoriCasa();
									res+=res1;
									//pp.setRisultatoComplessivo((p.getGolCasa()+pp.getGolTrasferta()+pp.getGolSupplementariTrasferta()+pp.getRigoriTrasferta())+" - "+(p.getGolTrasferta()+pp.getGolCasa()+pp.getGolSupplementariCasa()+pp.getRigoriCasa())+"DCR");
									pp.setRisultatoComplessivo((p.getGolTrasferta()+pp.getGolCasa()+pp.getGolSupplementariCasa()+pp.getRigoriCasa())+" - "+(p.getGolCasa()+pp.getGolTrasferta()+pp.getGolSupplementariTrasferta()+pp.getRigoriTrasferta()));
									pp.setSquadraVincente(s1.getNome());
								}
							}
						}
					  }	
					}
				}
			}
		}

    	if(turno.equals("PlayOff"))
    		risultatiPlayOff.add(res);
    	if(turno.equals("Ottavi"))
    		risultatiOttavi.add(res);
    	if(turno.equals("Quarti"))
    		risultatiQuarti.add(res);
    	if(turno.equals("Semifinale"))
    		risultatiSemifinali.add(res);
    	return vincitrici;
    }
	
	public List<String> getRisultatiPlayOff() {
		return risultatiPlayOff;
	}
	public List<String> getRisultatiOttavi() {
		return risultatiOttavi;
	}
	public List<String> getRisultatiQuarti() {
		return risultatiQuarti;
	}
	public List<String> getRisultatiSemifinali() {
		return risultatiSemifinali;
	}
	public String getRisultatoFinale() {
		return risultatoFinale;
	}
	private void rigori(Squadra s1, Squadra s2,PartitaEliminazioneDiretta p) {
		int rigoriSegnatiS1=(int)(Math.random()*6);
		int rigoriSegnatiS2=(int)(Math.random()*6);
		if(rigoriSegnatiS1==rigoriSegnatiS2){
			do {
				rigoriSegnatiS1+=(int)(Math.random()*2);
				rigoriSegnatiS2+=(int)(Math.random()*2);
			}
			while(rigoriSegnatiS1==rigoriSegnatiS2);
		}
		p.setRigoriCasa(rigoriSegnatiS1);
		p.setRigoriTrasferta(rigoriSegnatiS2);
	}
	private void processEvent(Evento e) {
		Squadra casa=e.getPg().getCasa();
		Squadra ospite=e.getPg().getTrasferta();
		this.calcolaRisultato(casa, ospite,e.getPg());
		this.calcolaInfoStadio(casa,ospite,"Girone");
		 if(e.getPg().getGolCasa()>e.getPg().getGolTrasferta()) {
			 casa.aggiornaPunti(3);
			 casa.setGironi(vittoria);
		 }
		 else if(e.getPg().getGolCasa()<e.getPg().getGolTrasferta()) {
			 ospite.aggiornaPunti(3);
			 ospite.setGironi(vittoria);
		 }
		 else if(e.getPg().getGolCasa()==e.getPg().getGolTrasferta()) {
			 ospite.aggiornaPunti(1);
			 casa.aggiornaPunti(1);
			 ospite.setGironi(pareggio);
			 casa.setGironi(pareggio);
		 }
	}
	private void processEventEliminazioneDiretta(Evento e) {
		Squadra casa=e.getPg().getCasa();
		Squadra ospite=e.getPg().getTrasferta();
		this.calcolaRisultato(casa, ospite,e.getPg());
		PartitaEliminazioneDiretta pe=(PartitaEliminazioneDiretta) e.getPg();
		this.calcolaInfoStadio(casa,ospite,pe.getTurno());
		
		
	}
	private void calcolaInfoStadio(Squadra casa, Squadra ospite, String turno) {
		Stadio s=idStadi.get(casa.getNomeStadio());
		
		double random=Math.random();
		if(turno.equals("Girone") || turno.equals("PlayOff") ) {
			if(ospite.getPosizioneRanking()>0 && ospite.getPosizioneRanking()<=10) {
				if(random<0.9) {
					int min_val = (int) (0.95*s.getCapienza());
			        int max_val = s.getCapienza();
			        Random ran = new Random();
			        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
			        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.25)*capienza);
			        casa.setBigliettiG((int)(s.getPrezzoBiglietto()*1.25)*capienza);
			        s.setSommaCapienzaG(capienza);
				}
				else if(random<0.95) {
					int min_val = (int) (0.90*s.getCapienza());
			        int max_val =(int) (0.94*s.getCapienza());
			        Random ran = new Random();
			        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
			        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.25)*capienza);
			        s.setSommaCapienzaG(capienza);
			        casa.setBigliettiG((int)(s.getPrezzoBiglietto()*1.25)*capienza);
				}
				else {
					int min_val = (int) (0.80*s.getCapienza());
			        int max_val =(int) (0.89*s.getCapienza());
			        Random ran = new Random();
			        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
			        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.25)*capienza);
			        casa.setBigliettiG((int)(s.getPrezzoBiglietto()*1.25)*capienza);
			        s.setSommaCapienzaG(capienza);
				}
			}
			else if((ospite.getPosizioneRanking()>=11 && ospite.getPosizioneRanking()<=25) || ospite.getNome().equals("Milan")) {
			if(random<0.8) {
				int min_val = (int) (0.95*s.getCapienza());
		        int max_val = s.getCapienza();
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.15)*capienza);
		        casa.setBigliettiG((int)(s.getPrezzoBiglietto()*1.15)*capienza);
		        s.setSommaCapienzaG(capienza);
			}
			else if(random<0.9) {
				int min_val = (int) (0.90*s.getCapienza());
		        int max_val =(int) (0.94*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.15)*capienza);
		        s.setSommaCapienzaG(capienza);
		        casa.setBigliettiG((int)(s.getPrezzoBiglietto()*1.15)*capienza);
			}
			else if(random<0.95) {
				int min_val = (int) (0.80*s.getCapienza());
		        int max_val =(int) (0.89*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.15)*capienza);
		        s.setSommaCapienzaG(capienza);
		        casa.setBigliettiG((int)(s.getPrezzoBiglietto()*1.15)*capienza);
			}
			else {
				int min_val = (int) (0.75*s.getCapienza());
		        int max_val =(int) (0.79*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.15)*capienza);
		        casa.setBigliettiG((int)(s.getPrezzoBiglietto()*1.15)*capienza);
		        s.setSommaCapienzaG(capienza);
			}			
		}
		else if(ospite.getPosizioneRanking()>=26 && ospite.getPosizioneRanking()<=50 ) {
			if(random<0.4) {
				int min_val = (int) (0.95*s.getCapienza());
		        int max_val = s.getCapienza();
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.85)*capienza);
		        casa.setBigliettiG((int)(s.getPrezzoBiglietto()*0.85)*capienza);
		        s.setSommaCapienzaG(capienza);
			}
			else if(random<0.7) {
				int min_val = (int) (0.90*s.getCapienza());
		        int max_val =(int) (0.94*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.85)*capienza);
		        s.setSommaCapienzaG(capienza);
		        casa.setBigliettiG((int)(s.getPrezzoBiglietto()*0.85)*capienza);
			}
			else if(random<0.85) {
				int min_val = (int) (0.80*s.getCapienza());
		        int max_val =(int) (0.89*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.85)*capienza);
		        s.setSommaCapienzaG(capienza);
		        casa.setBigliettiG((int)(s.getPrezzoBiglietto()*0.85)*capienza);
			}else if(random<0.95) {
				int min_val = (int) (0.75*s.getCapienza());
		        int max_val =(int) (0.79*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.85)*capienza);
		        s.setSommaCapienzaG(capienza);
		        casa.setBigliettiG((int)(s.getPrezzoBiglietto()*0.85)*capienza);
			}
			else {
				int min_val = (int) (0.65*s.getCapienza());
		        int max_val =(int) (0.74*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.85)*capienza);
		        casa.setBigliettiG((int)(s.getPrezzoBiglietto()*0.85)*capienza);
		        s.setSommaCapienzaG(capienza);
			}	
		}
		else {
			if(random<0.3) {
				int min_val = (int) (0.95*s.getCapienza());
		        int max_val = s.getCapienza();
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.75)*capienza);
		        casa.setBigliettiG((int)(s.getPrezzoBiglietto()*0.75)*capienza);
		        s.setSommaCapienzaG(capienza);
			}
			else if(random<0.7) {
				int min_val = (int) (0.90*s.getCapienza());
		        int max_val =(int) (0.94*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.75)*capienza);
		        s.setSommaCapienzaG(capienza);
		        casa.setBigliettiG((int)(s.getPrezzoBiglietto()*0.75)*capienza);
			}
			else if(random<0.85) {
				int min_val = (int) (0.80*s.getCapienza());
		        int max_val =(int) (0.89*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.75)*capienza);
		        s.setSommaCapienzaG(capienza);
		        casa.setBigliettiG((int)(s.getPrezzoBiglietto()*0.75)*capienza);
			}else if(random<0.95) {
				int min_val = (int) (0.75*s.getCapienza());
		        int max_val =(int) (0.79*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.75)*capienza);
		        s.setSommaCapienzaG(capienza);
		        casa.setBigliettiG((int)(s.getPrezzoBiglietto()*0.75)*capienza);
			}
			else {
				int min_val = (int) (0.65*s.getCapienza());
		        int max_val =(int) (0.74*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.75)*capienza);
		        casa.setBigliettiG((int)(s.getPrezzoBiglietto()*0.75)*capienza);
		        s.setSommaCapienzaG(capienza);
			}
			
		}
		}
		else if(turno.equals("Ottavi")) {
			if(ospite.getPosizioneRanking()>0 && ospite.getPosizioneRanking()<=10) {
				if(random<0.9) {
					int min_val = (int) (0.975*s.getCapienza());
			        int max_val = s.getCapienza();
			        Random ran = new Random();
			        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
			        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.35)*capienza);
			        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*1.35)*capienza);
			        s.setSommaCapienzaED(capienza);
				}
				else if(random<0.975) {
					int min_val = (int) (0.95*s.getCapienza());
			        int max_val =(int) (0.974*s.getCapienza());
			        Random ran = new Random();
			        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
			        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.35)*capienza);
			        s.setSommaCapienzaED(capienza);
			        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*1.35)*capienza);
				}
				else {
					int min_val = (int) (0.89*s.getCapienza());
			        int max_val =(int) (0.94*s.getCapienza());
			        Random ran = new Random();
			        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
			        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.35)*capienza);
			        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*1.35)*capienza);
			        s.setSommaCapienzaED(capienza);
				}
			}
			else if((ospite.getPosizioneRanking()>=11 && ospite.getPosizioneRanking()<=25) || ospite.getNome().equals("Milan")) {
			if(random<0.8) {
				int min_val = (int) (0.95*s.getCapienza());
		        int max_val = s.getCapienza();
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.25)*capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*1.25)*capienza);
		        s.setSommaCapienzaED(capienza);
			}
			else if(random<0.95) {
				int min_val = (int) (0.90*s.getCapienza());
		        int max_val =(int) (0.94*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.25)*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*1.25)*capienza);
			}
			else {
				int min_val = (int) (0.80*s.getCapienza());
		        int max_val =(int) (0.89*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.25)*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*1.25)*capienza);
			}			
		}
		else if(ospite.getPosizioneRanking()>=26 && ospite.getPosizioneRanking()<=50 ) {
			if(random<0.6) {
				int min_val = (int) (0.95*s.getCapienza());
		        int max_val = s.getCapienza();
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.95)*capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*0.95)*capienza);
		        s.setSommaCapienzaED(capienza);
			}
			else if(random<0.8) {
				int min_val = (int) (0.90*s.getCapienza());
		        int max_val =(int) (0.94*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.95)*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*0.95)*capienza);
			}
			else if(random<0.9) {
				int min_val = (int) (0.80*s.getCapienza());
		        int max_val =(int) (0.89*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.95)*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*0.95)*capienza);
			}else{
				int min_val = (int) (0.75*s.getCapienza());
		        int max_val =(int) (0.79*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.95)*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*0.95)*capienza);
			}	
		}
		else {
			if(random<0.45) {
				int min_val = (int) (0.95*s.getCapienza());
		        int max_val = s.getCapienza();
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.85)*capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*0.85)*capienza);
		        s.setSommaCapienzaED(capienza);
			}
			else if(random<0.85) {
				int min_val = (int) (0.90*s.getCapienza());
		        int max_val =(int) (0.94*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.85)*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*0.85)*capienza);
			}
			else if(random<0.95) {
				int min_val = (int) (0.80*s.getCapienza());
		        int max_val =(int) (0.89*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.85)*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*0.85)*capienza);
			}else {
				int min_val = (int) (0.75*s.getCapienza());
		        int max_val =(int) (0.79*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.85)*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*0.85)*capienza);
			}
			
		}	
		}
		else if(turno.equals("Quarti")) {
			if(ospite.getPosizioneRanking()>0 && ospite.getPosizioneRanking()<=10) {
				if(random<0.9) {
					int min_val = (int) (0.975*s.getCapienza());
			        int max_val = s.getCapienza();
			        Random ran = new Random();
			        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
			        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.45)*capienza);
			        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*1.45)*capienza);
			        s.setSommaCapienzaED(capienza);
				}
				else if(random<0.975) {
					int min_val = (int) (0.95*s.getCapienza());
			        int max_val =(int) (0.974*s.getCapienza());
			        Random ran = new Random();
			        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
			        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.45)*capienza);
			        s.setSommaCapienzaED(capienza);
			        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*1.45)*capienza);
				}
				else {
					int min_val = (int) (0.89*s.getCapienza());
			        int max_val =(int) (0.94*s.getCapienza());
			        Random ran = new Random();
			        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
			        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.45)*capienza);
			        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*1.45)*capienza);
			        s.setSommaCapienzaED(capienza);
				}
			}
			else if((ospite.getPosizioneRanking()>=11 && ospite.getPosizioneRanking()<=25) || ospite.getNome().equals("Milan")) {
			if(random<0.8) {
				int min_val = (int) (0.95*s.getCapienza());
		        int max_val = s.getCapienza();
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.35)*capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*1.35)*capienza);
		        s.setSommaCapienzaED(capienza);
			}
			else if(random<0.95) {
				int min_val = (int) (0.90*s.getCapienza());
		        int max_val =(int) (0.94*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.35)*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*1.35)*capienza);
			}
			else {
				int min_val = (int) (0.80*s.getCapienza());
		        int max_val =(int) (0.89*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.35)*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*1.35)*capienza);
			}			
		}
		else if(ospite.getPosizioneRanking()>=26 && ospite.getPosizioneRanking()<=50 ) {
			if(random<0.6) {
				int min_val = (int) (0.95*s.getCapienza());
		        int max_val = s.getCapienza();
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto())*capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto())*capienza);
		        s.setSommaCapienzaED(capienza);
			}
			else if(random<0.8) {
				int min_val = (int) (0.90*s.getCapienza());
		        int max_val =(int) (0.94*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto())*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto())*capienza);
			}
			else if(random<0.9) {
				int min_val = (int) (0.80*s.getCapienza());
		        int max_val =(int) (0.89*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto())*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto())*capienza);
			}else{
				int min_val = (int) (0.75*s.getCapienza());
		        int max_val =(int) (0.79*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto())*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto())*capienza);
			}	
		}
		else {
			if(random<0.45) {
				int min_val = (int) (0.95*s.getCapienza());
		        int max_val = s.getCapienza();
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.95)*capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*0.95)*capienza);
		        s.setSommaCapienzaED(capienza);
			}
			else if(random<0.85) {
				int min_val = (int) (0.90*s.getCapienza());
		        int max_val =(int) (0.94*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.95)*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*0.95)*capienza);
			}
			else if(random<0.95) {
				int min_val = (int) (0.80*s.getCapienza());
		        int max_val =(int) (0.89*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.95)*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*0.95)*capienza);
			}else {
				int min_val = (int) (0.75*s.getCapienza());
		        int max_val =(int) (0.79*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.95)*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*0.95)*capienza);
			}
			
		}
				}
		else if(turno.equals("Semifinale")) {
			if(ospite.getPosizioneRanking()>0 && ospite.getPosizioneRanking()<=10) {
				if(random<0.9) {
					int min_val = (int) (0.99*s.getCapienza());
			        int max_val = s.getCapienza();
			        Random ran = new Random();
			        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
			        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.55)*capienza);
			        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*1.55)*capienza);
			        s.setSommaCapienzaED(capienza);
				}
				else{
					int min_val = (int) (0.95*s.getCapienza());
			        int max_val =(int) (0.98*s.getCapienza());
			        Random ran = new Random();
			        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
			        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.55)*capienza);
			        s.setSommaCapienzaED(capienza);
			        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*1.55)*capienza);
				}
			}
			else if((ospite.getPosizioneRanking()>=11 && ospite.getPosizioneRanking()<=25) || ospite.getNome().equals("Milan")) {
			if(random<0.9) {
				int min_val = (int) (0.975*s.getCapienza());
		        int max_val = s.getCapienza();
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.35)*capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*1.35)*capienza);
		        s.setSommaCapienzaED(capienza);
			}
			else  {
				int min_val = (int) (0.94*s.getCapienza());
		        int max_val =(int) (0.975*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*1.35)*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*1.35)*capienza);
			}			
		}
		else if(ospite.getPosizioneRanking()>=26 && ospite.getPosizioneRanking()<=50 ) {
			if(random<0.8) {
				int min_val = (int) (0.95*s.getCapienza());
		        int max_val = s.getCapienza();
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto())*capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto())*capienza);
		        s.setSommaCapienzaED(capienza);
			}
			else{
				int min_val = (int) (0.90*s.getCapienza());
		        int max_val =(int) (0.94*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto())*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto())*capienza);
			}	
		}
		else {
			if(random<0.75) {
				int min_val = (int) (0.95*s.getCapienza());
		        int max_val = s.getCapienza();
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.95)*capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*0.95)*capienza);
		        s.setSommaCapienzaED(capienza);
			}
			else if(random<0.95) {
				int min_val = (int) (0.90*s.getCapienza());
		        int max_val =(int) (0.94*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.95)*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*0.95)*capienza);
			}
			else{
				int min_val = (int) (0.80*s.getCapienza());
		        int max_val =(int) (0.89*s.getCapienza());
		        Random ran = new Random();
		        int capienza = ran.nextInt(max_val-min_val+1) + min_val;
		        s.setSommaGuadagni((int)(s.getPrezzoBiglietto()*0.95)*capienza);
		        s.setSommaCapienzaED(capienza);
		        casa.setBigliettiED((int)(s.getPrezzoBiglietto()*0.95)*capienza);
			}
			
		}
		}
		
	}
	private void processEventFinale(Evento e) {
		Squadra casa=e.getPg().getCasa();
		Squadra ospite=e.getPg().getTrasferta();
		this.supplementari(casa, ospite,((PartitaEliminazioneDiretta)e.getPg()));
		
	}
	
	
	private void calcolaRisultato(Squadra casa,Squadra ospite,Partita p) {
		double perc=Math.random();
		double valoreOffensivoCasa=(casa.getATT()+2+(casa.getMID()+2)*0.5)/1.5;
		double valoreDifensivoCasa=(casa.getDEF()+2+(casa.getMID()+2)*0.5)/1.5;
		double valoreOffensivoOspite=(ospite.getATT()+ospite.getMID()*0.5)/1.5;
		double valoreDifensivoOspite=(ospite.getDEF()+ospite.getMID()*0.5)/1.5;
		double diffCasa=valoreOffensivoCasa-valoreDifensivoOspite;
		double diffOspite=valoreOffensivoOspite-valoreDifensivoCasa;
		if(diffCasa<=21 && diffCasa>=19) {
			if(perc<=0.0125) {
				casa.setGolFatti(0);
				p.setGolCasa(0);
			}
			else if(perc<=0.03) {
				casa.setGolFatti(1);
				ospite.setGolSubiti(1);
				p.setGolCasa(1);
			}
			else if(perc<=0.05) {
				casa.setGolFatti(2);
				ospite.setGolSubiti(2);
				p.setGolCasa(2);
						}
			else if(perc<=0.35) {
				casa.setGolFatti(3);
				ospite.setGolSubiti(3);
				p.setGolCasa(3);
			}
			else if(perc<=0.7) {
				casa.setGolFatti(4);
				ospite.setGolSubiti(4);
				p.setGolCasa(4);
			}
			else if(perc<=0.85) {
				casa.setGolFatti(5);
				ospite.setGolSubiti(5);
				p.setGolCasa(5);
			}
			else if(perc<=0.95) {
				casa.setGolFatti(6);
				ospite.setGolSubiti(6);
				p.setGolCasa(6);
			}
			else{
				casa.setGolFatti(7);
				ospite.setGolSubiti(7);
				p.setGolCasa(7);
			}
		}
		else if( diffCasa>=16) {
			if(perc<=0.015) {
				casa.setGolFatti(0);
				p.setGolCasa(0);
			}
			else if(perc<=0.035) {
				casa.setGolFatti(1);
				ospite.setGolSubiti(1);
				p.setGolCasa(1);
			}
			else if(perc<=0.075) {
				casa.setGolFatti(2);
				ospite.setGolSubiti(2);
				p.setGolCasa(2);
						}
			else if(perc<=0.4) {
				casa.setGolFatti(3);
				ospite.setGolSubiti(3);
				p.setGolCasa(3);
			}
			else if( perc<=0.75) {
				casa.setGolFatti(4);
				ospite.setGolSubiti(4);
				p.setGolCasa(4);
			}
			else if(perc<=0.9) {
				casa.setGolFatti(5);
				ospite.setGolSubiti(5);
				p.setGolCasa(5);
			}
			else if(perc<=0.975) {
				casa.setGolFatti(6);
				ospite.setGolSubiti(6);
				p.setGolCasa(6);
			}
			else {
				casa.setGolFatti(7);
				ospite.setGolSubiti(7);
				p.setGolCasa(7);
			}
		}
		else if(diffCasa>=13) {
			if(perc<=0.025) {
				casa.setGolFatti(0);
				p.setGolCasa(0);
			}
			else if(perc<=0.055) {
				casa.setGolFatti(1);
				ospite.setGolSubiti(1);
				p.setGolCasa(1);
			}
			else if(perc<=0.12) {
				casa.setGolFatti(2);
				ospite.setGolSubiti(2);
				p.setGolCasa(2);
						}
			else if(perc<=0.45) {
				casa.setGolFatti(3);
				ospite.setGolSubiti(3);
				p.setGolCasa(3);
			}
			else if(perc<=0.805) {
				casa.setGolFatti(4);
				ospite.setGolSubiti(4);
				p.setGolCasa(4);
			}
			else if(perc<=0.925) {
				casa.setGolFatti(5);
				ospite.setGolSubiti(5);
				p.setGolCasa(5);
			}
			else if(perc<=0.98) {
				casa.setGolFatti(6);
				ospite.setGolSubiti(6);
				p.setGolCasa(6);
			}
			else {
				casa.setGolFatti(7);
				ospite.setGolSubiti(7);
				p.setGolCasa(7);
			}
		}
		else if(diffCasa>=11) {
			if(perc<=0.04) {
				casa.setGolFatti(0);
				p.setGolCasa(0);
			}
			else if(perc<=0.085) {
				casa.setGolFatti(1);
				ospite.setGolSubiti(1);
				p.setGolCasa(1);
			}
			else if(perc<=0.185) {
				casa.setGolFatti(2);
				ospite.setGolSubiti(2);
				p.setGolCasa(2);
						}
			else if(perc<=0.545) {
				casa.setGolFatti(3);
				ospite.setGolSubiti(3);
				p.setGolCasa(3);
			}
			else if(perc<=0.87) {
				casa.setGolFatti(4);
				ospite.setGolSubiti(4);
				p.setGolCasa(4);
			}
			else if(perc<=0.95) {
				casa.setGolFatti(5);
				ospite.setGolSubiti(5);
				p.setGolCasa(5);
			}
			else if(perc<=0.985) {
				casa.setGolFatti(6);
				ospite.setGolSubiti(6);
				p.setGolCasa(6);
			}
			else {
				casa.setGolFatti(7);
				ospite.setGolSubiti(7);
				p.setGolCasa(7);
			}
		}
		else if(diffCasa>=9) {
			if(perc<=0.05) {
				casa.setGolFatti(0);
				p.setGolCasa(0);
			}
			else if(perc<=0.11) {
				casa.setGolFatti(1);
				ospite.setGolSubiti(1);
				p.setGolCasa(1);
			}
			else if(perc<=0.25) {
				casa.setGolFatti(2);
				ospite.setGolSubiti(2);
				p.setGolCasa(2);
						}
			else if(perc<=0.6) {
				casa.setGolFatti(3);
				ospite.setGolSubiti(3);
				p.setGolCasa(3);
			}
			else if(perc<=0.91) {
				casa.setGolFatti(4);
				ospite.setGolSubiti(4);
				p.setGolCasa(4);
			}
			else if(perc<=0.96) {
				casa.setGolFatti(5);
				ospite.setGolSubiti(5);
				p.setGolCasa(5);
			}
			else if(perc<=0.99) {
				casa.setGolFatti(6);
				ospite.setGolSubiti(6);
				p.setGolCasa(6);
			}
			else {
				casa.setGolFatti(7);
				ospite.setGolSubiti(7);
				p.setGolCasa(7);
			}
		}
		else if(diffCasa>=7){
			if(perc<=0.075) {
				casa.setGolFatti(0);
				p.setGolCasa(0);
			}
			else if(perc<=0.175) {
				casa.setGolFatti(1);
				ospite.setGolSubiti(1);
				p.setGolCasa(1);
			}
			else if(perc<=0.375) {
				casa.setGolFatti(2);
				ospite.setGolSubiti(2);
				p.setGolCasa(2);
						}
			else if(perc<=0.7) {
				casa.setGolFatti(3);
				ospite.setGolSubiti(3);
				p.setGolCasa(3);
			}
			else if(perc<=0.95) {
				casa.setGolFatti(4);
				ospite.setGolSubiti(4);
				p.setGolCasa(4);
			}
			else if(perc<=0.985) {
				casa.setGolFatti(5);
				ospite.setGolSubiti(5);
				p.setGolCasa(5);
			}
			else {
				casa.setGolFatti(6);
				ospite.setGolSubiti(6);
				p.setGolCasa(6);
			}
		}
		else if(diffCasa>=5){
			if(perc<=0.09) {
				casa.setGolFatti(0);
				p.setGolCasa(0);
			}
			else if(perc<=0.215) {
				casa.setGolFatti(1);
				ospite.setGolSubiti(1);
				p.setGolCasa(1);
			}
			else if(perc<=0.465) {
				casa.setGolFatti(2);
				ospite.setGolSubiti(2);
				p.setGolCasa(2);
						}
			else if(perc<=0.775) {
				casa.setGolFatti(3);
				ospite.setGolSubiti(3);
				p.setGolCasa(3);
			}
			else if(perc<=0.975) {
				casa.setGolFatti(4);
				ospite.setGolSubiti(4);
				p.setGolCasa(4);
			}
			else {
				casa.setGolFatti(5);
				ospite.setGolSubiti(5);
				p.setGolCasa(5);
			}
		}
		else if(diffCasa>=3){
			if(perc<=0.115) {
				casa.setGolFatti(0);
				p.setGolCasa(0);
			}
			else if(perc<=0.265) {
				casa.setGolFatti(1);
				ospite.setGolSubiti(1);
				p.setGolCasa(1);
			}
			else if(perc<=0.545) {
				casa.setGolFatti(2);
				ospite.setGolSubiti(2);
				p.setGolCasa(2);
						}
			else if(perc<=0.835) {
				casa.setGolFatti(3);
				ospite.setGolSubiti(3);
				p.setGolCasa(3);
			}
			else if(perc<=0.985) {
				casa.setGolFatti(4);
				ospite.setGolSubiti(4);
				p.setGolCasa(4);
			}
			else {
				casa.setGolFatti(5);
				ospite.setGolSubiti(5);
				p.setGolCasa(5);
			}
		}
		else if(diffCasa>=1){
			if(perc<=0.13) {
				casa.setGolFatti(0);
				p.setGolCasa(0);
			}
			else if(perc<=0.305) {
				casa.setGolFatti(1);
				ospite.setGolSubiti(1);
				p.setGolCasa(1);
			}
			else if(perc<=0.605) {
				casa.setGolFatti(2);
				ospite.setGolSubiti(2);
				p.setGolCasa(2);
						}
			else if(perc<=0.875) {
				casa.setGolFatti(3);
				ospite.setGolSubiti(3);
				p.setGolCasa(3);
			}
			else {
				casa.setGolFatti(4);
				ospite.setGolSubiti(4);
				p.setGolCasa(4);
			}
		}
		else if(diffCasa>=-1){
			if(perc<=0.155) {
				casa.setGolFatti(0);
				p.setGolCasa(0);
			}
			else if(perc<=0.36) {
				casa.setGolFatti(1);
				ospite.setGolSubiti(1);
				p.setGolCasa(1);
			}
			else if(perc<=0.68) {
				casa.setGolFatti(2);
				ospite.setGolSubiti(2);
				p.setGolCasa(2);
						}
			else if(perc<=0.9) {
				casa.setGolFatti(3);
				ospite.setGolSubiti(3);
				p.setGolCasa(3);
			}
			else {
				casa.setGolFatti(4);
				ospite.setGolSubiti(4);
				p.setGolCasa(4);
			}
		}
		else if(diffCasa>=-3){
			if(perc<=0.175) {
				casa.setGolFatti(0);
				p.setGolCasa(0);
			}
			else if(perc<=0.405) {
				casa.setGolFatti(1);
				ospite.setGolSubiti(1);
				p.setGolCasa(1);
			}
			else if(perc<=0.755) {
				casa.setGolFatti(2);
				ospite.setGolSubiti(2);
				p.setGolCasa(2);
						}
			else if(perc<=0.935) {
				casa.setGolFatti(3);
				ospite.setGolSubiti(3);
				p.setGolCasa(3);
			}
			else {
				casa.setGolFatti(4);
				ospite.setGolSubiti(4);
				p.setGolCasa(4);
			}
		}
		else if(diffCasa>=-5){
			if(perc<=0.2) {
				casa.setGolFatti(0);
				p.setGolCasa(0);
			}
			else if(perc<=0.475) {
				casa.setGolFatti(1);
				ospite.setGolSubiti(1);
				p.setGolCasa(1);
			}
			else if(perc<=0.8) {
				casa.setGolFatti(2);
				ospite.setGolSubiti(2);
				p.setGolCasa(2);
						}
			else if(perc<=0.95) {
				casa.setGolFatti(3);
				ospite.setGolSubiti(3);
				p.setGolCasa(3);
			}
			else {
				casa.setGolFatti(4);
				ospite.setGolSubiti(4);
				p.setGolCasa(4);
			}
		}
		else if(diffCasa>=-8){
			if(perc<=0.25) {
				casa.setGolFatti(0);
				p.setGolCasa(0);
			}
			else if(perc<=0.55) {
				casa.setGolFatti(1);
				ospite.setGolSubiti(1);
				p.setGolCasa(1);
			}
			else if(perc<=0.85) {
				casa.setGolFatti(2);
				ospite.setGolSubiti(2);
				p.setGolCasa(2);
						}
			else if(perc<=0.975) {
				casa.setGolFatti(3);
				ospite.setGolSubiti(3);
				p.setGolCasa(3);
			}
			else {
				casa.setGolFatti(4);
				ospite.setGolSubiti(4);
				p.setGolCasa(4);
			}
		}
		else if(diffCasa>=-11){
			if(perc<=0.3) {
				casa.setGolFatti(0);
				p.setGolCasa(0);
			}
			else if(perc<=0.65) {
				casa.setGolFatti(1);
				ospite.setGolSubiti(1);
				p.setGolCasa(1);
			}
			else if(perc<=0.92) {
				casa.setGolFatti(2);
				ospite.setGolSubiti(2);
				p.setGolCasa(2);
			}
			else {
				casa.setGolFatti(3);
				ospite.setGolSubiti(3);
				p.setGolCasa(3);
			}
		}
		else if(diffCasa>=-14){
			if(perc<=0.375) {
				casa.setGolFatti(0);
				p.setGolCasa(0);
			}
			else if(perc<=0.75) {
				casa.setGolFatti(1);
				ospite.setGolSubiti(1);
				p.setGolCasa(1);
			}
			else if(perc<=0.96) {
				casa.setGolFatti(2);
				ospite.setGolSubiti(2);
				p.setGolCasa(2);
			}
			else {
				casa.setGolFatti(3);
				ospite.setGolSubiti(3);
				p.setGolCasa(3);
			}
		}
		else if(diffCasa>=-14){
			if(perc<=0.45) {
				casa.setGolFatti(0);
				p.setGolCasa(0);
			}
			else if(perc<=0.83) {
				casa.setGolFatti(1);
				ospite.setGolSubiti(1);
				p.setGolCasa(1);
			}
			else if(perc<=0.98) {
				casa.setGolFatti(2);
				ospite.setGolSubiti(2);
				p.setGolCasa(2);
			}
			else {
				casa.setGolFatti(3);
				ospite.setGolSubiti(3);
				p.setGolCasa(3);
			}
		}
		
		perc=Math.random();
		
		if(diffOspite<=21 && diffOspite>=19) {
			if(perc<=0.0125) {
				ospite.setGolFatti(0);
				p.setGolTrasferta(0);
			}
			else if(perc<=0.03) {
				ospite.setGolFatti(1);
				casa.setGolSubiti(1);
				p.setGolTrasferta(1);
			}
			else if(perc<=0.05) {
				ospite.setGolFatti(2);
				casa.setGolSubiti(2);
				p.setGolTrasferta(2);
						}
			else if(perc<=0.35) {
				ospite.setGolFatti(3);
				casa.setGolSubiti(3);
				p.setGolTrasferta(3);
			}
			else if(perc<=0.7) {
				ospite.setGolFatti(4);
				casa.setGolSubiti(4);
				p.setGolTrasferta(4);
			}
			else if(perc<=0.85) {
				ospite.setGolFatti(5);
				casa.setGolSubiti(5);
				p.setGolTrasferta(5);
			}
			else if(perc<=0.95) {
				ospite.setGolFatti(6);
				casa.setGolSubiti(6);
				p.setGolTrasferta(6);
			}
			else{
				ospite.setGolFatti(7);
				casa.setGolSubiti(7);
				p.setGolTrasferta(7);
			}
		}
		else if( diffOspite>=16) {
			if(perc<=0.015) {
				ospite.setGolFatti(0);
				p.setGolTrasferta(0);
			}
			else if(perc<=0.035) {
				ospite.setGolFatti(1);
				p.setGolTrasferta(1);
				casa.setGolSubiti(1);
			}
			else if(perc<=0.075) {
				ospite.setGolFatti(2);
				p.setGolTrasferta(2);
				casa.setGolSubiti(2);
						}
			else if(perc<=0.4) {
				ospite.setGolFatti(3);
				p.setGolTrasferta(3);
				casa.setGolSubiti(3);
			}
			else if( perc<=0.75) {
				ospite.setGolFatti(4);
				p.setGolTrasferta(4);
				casa.setGolSubiti(4);
			}
			else if(perc<=0.9) {
				ospite.setGolFatti(5);
				p.setGolTrasferta(5);
				casa.setGolSubiti(5);
			}
			else if(perc<=0.975) {
				ospite.setGolFatti(6);
				p.setGolTrasferta(6);
				casa.setGolSubiti(6);
			}
			else {
				ospite.setGolFatti(7);
				p.setGolTrasferta(7);
				casa.setGolSubiti(7);
			}
		}
		else if(diffOspite>=13) {
			if(perc<=0.025) {
				ospite.setGolFatti(0);
				p.setGolTrasferta(0);
			}
			else if(perc<=0.055) {
				ospite.setGolFatti(1);
				p.setGolTrasferta(1);
				casa.setGolSubiti(1);
			}
			else if(perc<=0.12) {
				ospite.setGolFatti(2);
				p.setGolTrasferta(2);
				casa.setGolSubiti(2);
						}
			else if(perc<=0.45) {
				ospite.setGolFatti(3);
				p.setGolTrasferta(3);
				casa.setGolSubiti(3);
			}
			else if(perc<=0.805) {
				ospite.setGolFatti(4);
				p.setGolTrasferta(4);
				casa.setGolSubiti(4);
			}
			else if(perc<=0.925) {
				ospite.setGolFatti(5);
				p.setGolTrasferta(5);
				casa.setGolSubiti(5);
			}
			else if(perc<=0.98) {
				ospite.setGolFatti(6);
				p.setGolTrasferta(6);
				casa.setGolSubiti(6);
			}
			else {
				ospite.setGolFatti(7);
				p.setGolTrasferta(7);
				casa.setGolSubiti(7);
			}
		}
		else if(diffOspite>=11) {
			if(perc<=0.04) {
				ospite.setGolFatti(0);
				p.setGolTrasferta(0);
			}
			else if(perc<=0.085) {
				ospite.setGolFatti(1);
				p.setGolTrasferta(1);
				casa.setGolSubiti(1);
			}
			else if(perc<=0.185) {
				ospite.setGolFatti(2);
				p.setGolTrasferta(2);
				casa.setGolSubiti(2);
						}
			else if(perc<=0.545) {
				ospite.setGolFatti(3);
				p.setGolTrasferta(3);
				casa.setGolSubiti(3);
			}
			else if(perc<=0.87) {
				ospite.setGolFatti(4);
				p.setGolTrasferta(4);
				casa.setGolSubiti(4);
			}
			else if(perc<=0.95) {
				ospite.setGolFatti(5);
				p.setGolTrasferta(5);
				casa.setGolSubiti(5);
			}
			else if(perc<=0.985) {
				ospite.setGolFatti(6);
				p.setGolTrasferta(6);
				casa.setGolSubiti(6);
			}
			else {
				ospite.setGolFatti(7);
				p.setGolTrasferta(7);
				casa.setGolSubiti(7);
			}
		}
		else if(diffOspite>=9) {
			if(perc<=0.05) {
				ospite.setGolFatti(0);
				p.setGolTrasferta(0);
			}
			else if(perc<=0.11) {
				ospite.setGolFatti(1);
				p.setGolTrasferta(1);
				casa.setGolSubiti(1);
			}
			else if(perc<=0.25) {
				ospite.setGolFatti(2);
				p.setGolTrasferta(2);
				casa.setGolSubiti(2);
						}
			else if(perc<=0.6) {
				ospite.setGolFatti(3);
				p.setGolTrasferta(3);
				casa.setGolSubiti(3);
			}
			else if(perc<=0.91) {
				ospite.setGolFatti(4);
				p.setGolTrasferta(4);
				casa.setGolSubiti(4);
			}
			else if(perc<=0.96) {
				ospite.setGolFatti(5);
				p.setGolTrasferta(5);
				casa.setGolSubiti(5);
			}
			else if(perc<=0.99) {
				ospite.setGolFatti(6);
				p.setGolTrasferta(6);
				casa.setGolSubiti(6);
			}
			else {
				ospite.setGolFatti(7);
				p.setGolTrasferta(7);
				casa.setGolSubiti(7);
			}
		}
		else if(diffOspite>=7){
			if(perc<=0.075) {
				ospite.setGolFatti(0);
				p.setGolTrasferta(0);
			}
			else if(perc<=0.175) {
				ospite.setGolFatti(1);
				p.setGolTrasferta(1);
				casa.setGolSubiti(1);
			}
			else if(perc<=0.375) {
				ospite.setGolFatti(2);
				p.setGolTrasferta(2);
				casa.setGolSubiti(2);
						}
			else if(perc<=0.7) {
				ospite.setGolFatti(3);
				p.setGolTrasferta(3);
				casa.setGolSubiti(3);
			}
			else if(perc<=0.95) {
				ospite.setGolFatti(4);
				p.setGolTrasferta(4);
				casa.setGolSubiti(4);
			}
			else if(perc<=0.985) {
				ospite.setGolFatti(5);
				p.setGolTrasferta(5);
				casa.setGolSubiti(5);
			}
			else {
				ospite.setGolFatti(6);
				p.setGolTrasferta(6);
				casa.setGolSubiti(6);
			}
		}
		else if(diffOspite>=5){
			if(perc<=0.09) {
				ospite.setGolFatti(0);
				p.setGolTrasferta(0);
			}
			else if(perc<=0.215) {
				ospite.setGolFatti(1);
				p.setGolTrasferta(1);
				casa.setGolSubiti(1);
			}
			else if(perc<=0.465) {
				ospite.setGolFatti(2);
				p.setGolTrasferta(2);
				casa.setGolSubiti(2);
						}
			else if(perc<=0.775) {
				ospite.setGolFatti(3);
				p.setGolTrasferta(3);
				casa.setGolSubiti(3);
			}
			else if(perc<=0.975) {
				ospite.setGolFatti(4);
				p.setGolTrasferta(4);
				casa.setGolSubiti(4);
			}
			else {
				ospite.setGolFatti(5);
				p.setGolTrasferta(5);
				casa.setGolSubiti(5);
			}
		}
		else if(diffOspite>=3){
			if(perc<=0.115) {
				ospite.setGolFatti(0);
				p.setGolTrasferta(0);
			}
			else if(perc<=0.265) {
				ospite.setGolFatti(1);
				p.setGolTrasferta(1);
				casa.setGolSubiti(1);
			}
			else if(perc<=0.545) {
				ospite.setGolFatti(2);
				p.setGolTrasferta(2);
				casa.setGolSubiti(2);
						}
			else if(perc<=0.835) {
				ospite.setGolFatti(3);
				p.setGolTrasferta(3);
				casa.setGolSubiti(3);
			}
			else if(perc<=0.985) {
				ospite.setGolFatti(4);
				p.setGolTrasferta(4);
				casa.setGolSubiti(4);
			}
			else {
				ospite.setGolFatti(5);
				p.setGolTrasferta(5);
				casa.setGolSubiti(5);
			}
		}
		else if(diffOspite>=1){
			if(perc<=0.13) {
				ospite.setGolFatti(0);
				p.setGolTrasferta(0);
			}
			else if(perc<=0.305) {
				ospite.setGolFatti(1);
				p.setGolTrasferta(1);
				casa.setGolSubiti(1);
			}
			else if(perc<=0.605) {
				ospite.setGolFatti(2);
				p.setGolTrasferta(2);
				casa.setGolSubiti(2);
						}
			else if(perc<=0.875) {
				ospite.setGolFatti(3);
				p.setGolTrasferta(3);
				casa.setGolSubiti(3);
			}
			else {
				ospite.setGolFatti(4);
				p.setGolTrasferta(4);
				casa.setGolSubiti(4);
			}
		}
		else if(diffOspite>=-1){
			if(perc<=0.155) {
				ospite.setGolFatti(0);
				p.setGolTrasferta(0);
			}
			else if(perc<=0.36) {
				ospite.setGolFatti(1);
				p.setGolTrasferta(1);
				casa.setGolSubiti(1);
			}
			else if(perc<=0.68) {
				ospite.setGolFatti(2);
				p.setGolTrasferta(2);
				casa.setGolSubiti(2);
						}
			else if(perc<=0.9) {
				ospite.setGolFatti(3);
				p.setGolTrasferta(3);
				casa.setGolSubiti(3);
			}
			else {
				ospite.setGolFatti(4);
				p.setGolTrasferta(4);
				casa.setGolSubiti(4);
			}
		}
		else if(diffOspite>=-3){
			if(perc<=0.175) {
				ospite.setGolFatti(0);
				p.setGolTrasferta(0);
			}
			else if(perc<=0.405) {
				ospite.setGolFatti(1);
				p.setGolTrasferta(1);
				casa.setGolSubiti(1);
			}
			else if(perc<=0.755) {
				ospite.setGolFatti(2);
				p.setGolTrasferta(2);
				casa.setGolSubiti(2);
						}
			else if(perc<=0.935) {
				ospite.setGolFatti(3);
				p.setGolTrasferta(3);
				casa.setGolSubiti(3);
			}
			else {
				ospite.setGolFatti(4);
				p.setGolTrasferta(4);
				casa.setGolSubiti(4);
			}
		}
		else if(diffOspite>=-5){
			if(perc<=0.2) {
				ospite.setGolFatti(0);
				p.setGolTrasferta(0);
			}
			else if(perc<=0.475) {
				ospite.setGolFatti(1);
				p.setGolTrasferta(1);
				casa.setGolSubiti(1);
			}
			else if(perc<=0.8) {
				ospite.setGolFatti(2);
				p.setGolTrasferta(2);
				casa.setGolSubiti(2);
						}
			else if(perc<=0.95) {
				ospite.setGolFatti(3);
				p.setGolTrasferta(3);
				casa.setGolSubiti(3);
			}
			else {
				ospite.setGolFatti(4);
				p.setGolTrasferta(4);
				casa.setGolSubiti(4);
			}
		}
		else if(diffOspite>=-8){
			if(perc<=0.25) {
				ospite.setGolFatti(0);
				p.setGolTrasferta(0);
			}
			else if(perc<=0.55) {
				ospite.setGolFatti(1);
				p.setGolTrasferta(1);
				casa.setGolSubiti(1);
			}
			else if(perc<=0.85) {
				ospite.setGolFatti(2);
				p.setGolTrasferta(2);
				casa.setGolSubiti(2);
						}
			else if(perc<=0.975) {
				ospite.setGolFatti(3);
				p.setGolTrasferta(3);
				casa.setGolSubiti(3);
			}
			else {
				ospite.setGolFatti(4);
				p.setGolTrasferta(4);
				casa.setGolSubiti(4);
			}
		}
		else if(diffOspite>=-11){
			if(perc<=0.3) {
				ospite.setGolFatti(0);
				p.setGolTrasferta(0);
			}
			else if(perc<=0.65) {
				ospite.setGolFatti(1);
				p.setGolTrasferta(1);
				casa.setGolSubiti(1);
			}
			else if(perc<=0.92) {
				ospite.setGolFatti(2);
				p.setGolTrasferta(2);
				casa.setGolSubiti(2);
			}
			else {
				ospite.setGolFatti(3);
				p.setGolTrasferta(3);
				casa.setGolSubiti(3);
			}
		}
		else if(diffOspite>=-14){
			if(perc<=0.375) {
				ospite.setGolFatti(0);
				p.setGolTrasferta(0);
			}
			else if(perc<=0.75) {
				ospite.setGolFatti(1);
				p.setGolTrasferta(1);
				casa.setGolSubiti(1);
			}
			else if(perc<=0.96) {
				ospite.setGolFatti(2);
				p.setGolTrasferta(2);
				casa.setGolSubiti(2);
			}
			else {
				ospite.setGolFatti(3);
				p.setGolTrasferta(3);
				casa.setGolSubiti(3);
			}
		}
		else if(diffOspite>=-14){
			if(perc<=0.45) {
				ospite.setGolFatti(0);
				p.setGolTrasferta(0);
			}
			else if(perc<=0.83) {
				ospite.setGolFatti(1);
				p.setGolTrasferta(1);
				casa.setGolSubiti(1);
			}
			else if(perc<=0.98) {
				ospite.setGolFatti(2);
				p.setGolTrasferta(2);
				casa.setGolSubiti(2);
			}
			else {
				ospite.setGolFatti(3);
				p.setGolTrasferta(3);
				casa.setGolSubiti(3);
			}
		}
		
		
	}
	
	private void supplementari(Squadra casa,Squadra ospite,PartitaEliminazioneDiretta p) {
		double perc=Math.random();
		double valoreOffensivoCasa=(casa.getATT()+casa.getMID()*0.5)/1.5;
		double valoreDifensivoCasa=(casa.getDEF()+casa.getMID()*0.5)/1.5;
		double valoreOffensivoOspite=(ospite.getATT()+ospite.getMID()*0.5)/1.5;
		double valoreDifensivoOspite=(ospite.getDEF()+ospite.getMID()*0.5)/1.5;
		double diffCasa=valoreOffensivoCasa-valoreDifensivoOspite;
		double diffOspite=valoreOffensivoOspite-valoreDifensivoCasa;
		if(diffCasa<=21 && diffCasa>=19) {
			if(perc<=0.1) {
				casa.setGolFatti(0);
				p.setGolSupplementariCasa(0);
			}
			else if(perc<=0.4) {
				casa.setGolFatti(1);
				p.setGolSupplementariCasa(1);
			}
			else if(perc<=0.75) {
				casa.setGolFatti(2);	
				p.setGolSupplementariCasa(2);
						}
			else{
				casa.setGolFatti(3);
				p.setGolSupplementariCasa(3);
			}
		}
		else if( diffCasa>=16) {
			if(perc<=0.125) {
				casa.setGolFatti(0);
				p.setGolSupplementariCasa(0);
			}
			else if(perc<=0.45) {
				casa.setGolFatti(1);
				p.setGolSupplementariCasa(1);
			}
			else if(perc<=0.775) {
				casa.setGolFatti(2);
				p.setGolSupplementariCasa(2);
						}
			else {
				casa.setGolFatti(3);
				p.setGolSupplementariCasa(3);
			}
		}
		else if(diffCasa>=13) {
			if(perc<=0.15) {
				casa.setGolFatti(0);
				p.setGolSupplementariCasa(0);
			}
			else if(perc<=0.5) {
				casa.setGolFatti(1);
				p.setGolSupplementariCasa(1);
			}
			else if(perc<=0.8) {
				casa.setGolFatti(2);
				p.setGolSupplementariCasa(2);
						}
			else {
				casa.setGolFatti(3);
				p.setGolSupplementariCasa(3);
			}
		}
		else if(diffCasa>=11) {
			if(perc<=0.175) {
				casa.setGolFatti(0);
				p.setGolSupplementariCasa(0);
			}
			else if(perc<=0.55) {
				casa.setGolFatti(1);
				p.setGolSupplementariCasa(1);
			}
			else if(perc<=0.825) {
				casa.setGolFatti(2);
				p.setGolSupplementariCasa(2);
						}
			else {
				casa.setGolFatti(3);
				p.setGolSupplementariCasa(3);
			}
		}
		else if(diffCasa>=9) {
			if(perc<=0.2) {
				casa.setGolFatti(0);
				p.setGolSupplementariCasa(0);
			}
			else if(perc<=0.6) {
				casa.setGolFatti(1);
				p.setGolSupplementariCasa(1);
			}
			else if(perc<=0.85) {
				casa.setGolFatti(2);
				p.setGolSupplementariCasa(2);
						}
			else {
				casa.setGolFatti(3);
				p.setGolSupplementariCasa(3);
			}
		}
		else if(diffCasa>=7){
			if(perc<=0.225) {
				casa.setGolFatti(0);
				p.setGolSupplementariCasa(0);
			}
			else if(perc<=0.65) {
				casa.setGolFatti(1);
				p.setGolSupplementariCasa(1);
			}
			else if(perc<=0.875) {
				casa.setGolFatti(2);
				p.setGolSupplementariCasa(2);
						}
			else {
				casa.setGolFatti(3);
				p.setGolSupplementariCasa(3);
			}
		}
		else if(diffCasa>=5){
			if(perc<=0.25) {
				casa.setGolFatti(0);
				p.setGolSupplementariCasa(0);
			}
			else if(perc<=0.7) {
				casa.setGolFatti(1);
				p.setGolSupplementariCasa(1);
			}
			else if(perc<=0.9) {
				casa.setGolFatti(2);
				p.setGolSupplementariCasa(2);
						}
			else {
				casa.setGolFatti(3);
				p.setGolSupplementariCasa(3);
			}
		}
		else if(diffCasa>=3){
			if(perc<=0.275) {
				casa.setGolFatti(0);
				p.setGolSupplementariCasa(0);
			}
			else if(perc<=0.75) {
				casa.setGolFatti(1);
				p.setGolSupplementariCasa(1);
			}
			else if(perc<=0.925) {
				casa.setGolFatti(2);
				p.setGolSupplementariCasa(2);
						}
			else {
				casa.setGolFatti(3);
				p.setGolSupplementariCasa(3);
			}
		}
		else if(diffCasa>=1){
			if(perc<=0.3) {
				casa.setGolFatti(0);
				p.setGolSupplementariCasa(0);
			}
			else if(perc<=0.8) {
				casa.setGolFatti(1);
				p.setGolSupplementariCasa(1);
			}
			else if(perc<=0.95) {
				casa.setGolFatti(2);
				p.setGolSupplementariCasa(2);
			}
			
			else {
				casa.setGolFatti(3);
				p.setGolSupplementariCasa(3);
			}
		}
		else if(diffCasa>=-1){
			if(perc<=0.325) {
				casa.setGolFatti(0);
				p.setGolSupplementariCasa(0);
			}
			else if(perc<=0.85) {
				casa.setGolFatti(1);
				p.setGolSupplementariCasa(1);
			}
			else if(perc<=0.975) {
				casa.setGolFatti(2);
				p.setGolSupplementariCasa(2);
			}
			else {
				casa.setGolFatti(3);
				p.setGolSupplementariCasa(3);
			}
		}
		else if(diffCasa>=-3){
			if(perc<=0.4) {
				casa.setGolFatti(0);
				p.setGolSupplementariCasa(0);
			}
			else if(perc<=0.9) {
				casa.setGolFatti(1);
				p.setGolSupplementariCasa(1);
			}
			else {
				casa.setGolFatti(2);
				p.setGolSupplementariCasa(2);
			}
		}
		else if(diffCasa>=-5){
			if(perc<=0.5) {
				casa.setGolFatti(0);
				p.setGolSupplementariCasa(0);
			}
			else if(perc<=0.95) {
				casa.setGolFatti(1);
				p.setGolSupplementariCasa(1);
			}
			else {
				casa.setGolFatti(2);
				p.setGolSupplementariCasa(2);
			}
		}
		else if(diffCasa>=-8){
			if(perc<=0.55) {
				casa.setGolFatti(0);
				p.setGolSupplementariCasa(0);
			}
			else if(perc<=0.975) {
				casa.setGolFatti(1);
				p.setGolSupplementariCasa(1);
			}
			else {
				casa.setGolFatti(2);
				p.setGolSupplementariCasa(2);
			}
		}
		else if(diffCasa>=-11){
			if(perc<=0.6) {
				casa.setGolFatti(0);
				p.setGolSupplementariCasa(0);
			}
			else if(perc<=0.98) {
				casa.setGolFatti(1);
				p.setGolSupplementariCasa(1);
			}
			else {
				casa.setGolFatti(2);
				p.setGolSupplementariCasa(2);
			}
		}
		else if(diffCasa>=-14){
			if(perc<=0.65) {
				casa.setGolFatti(0);
				p.setGolSupplementariCasa(0);
			}
			else if(perc<=0.99) {
				casa.setGolFatti(1);
				p.setGolSupplementariCasa(1);
			}
			else {
				casa.setGolFatti(2);
				p.setGolSupplementariCasa(2);
			}
		}
		
		perc=Math.random();
		
		if(diffOspite<=21 && diffOspite>=19) {
			if(perc<=0.1) {
				ospite.setGolFatti(0);
				p.setGolSupplementariTrasferta(0);
			}
			else if(perc<=0.4) {
				ospite.setGolFatti(1);
				p.setGolSupplementariTrasferta(1);
			}
			else if(perc<=0.75) {
				ospite.setGolFatti(2);	
				p.setGolSupplementariTrasferta(2);
						}
			else{
				ospite.setGolFatti(3);
				p.setGolSupplementariTrasferta(3);
			}
		}
		else if( diffOspite>=16) {
			if(perc<=0.125) {
				ospite.setGolFatti(0);
				p.setGolSupplementariTrasferta(0);
			}
			else if(perc<=0.45) {
				ospite.setGolFatti(1);
				p.setGolSupplementariTrasferta(1);
			}
			else if(perc<=0.775) {
				ospite.setGolFatti(2);
				p.setGolSupplementariTrasferta(2);
						}
			else {
				ospite.setGolFatti(3);
				p.setGolSupplementariTrasferta(3);
			}
		}
		else if(diffOspite>=13) {
			if(perc<=0.15) {
				ospite.setGolFatti(0);
				p.setGolSupplementariTrasferta(0);
			}
			else if(perc<=0.5) {
				ospite.setGolFatti(1);
				p.setGolSupplementariTrasferta(1);
			}
			else if(perc<=0.8) {
				ospite.setGolFatti(2);
				p.setGolSupplementariTrasferta(2);
						}
			else {
				ospite.setGolFatti(3);
				p.setGolSupplementariTrasferta(3);
			}
		}
		else if(diffOspite>=11) {
			if(perc<=0.175) {
				ospite.setGolFatti(0);
				p.setGolSupplementariTrasferta(0);
			}
			else if(perc<=0.55) {
				ospite.setGolFatti(1);
				p.setGolSupplementariTrasferta(1);
			}
			else if(perc<=0.825) {
				ospite.setGolFatti(2);
				p.setGolSupplementariTrasferta(2);
						}
			else {
				ospite.setGolFatti(3);
				p.setGolSupplementariTrasferta(3);
			}
		}
		else if(diffOspite>=9) {
			if(perc<=0.2) {
				ospite.setGolFatti(0);
				p.setGolSupplementariTrasferta(0);
			}
			else if(perc<=0.6) {
				ospite.setGolFatti(1);
				p.setGolSupplementariTrasferta(1);
			}
			else if(perc<=0.85) {
				ospite.setGolFatti(2);
				p.setGolSupplementariTrasferta(2);
						}
			else {
				ospite.setGolFatti(3);
				p.setGolSupplementariTrasferta(3);
			}
		}
		else if(diffOspite>=7){
			if(perc<=0.225) {
				ospite.setGolFatti(0);
				p.setGolSupplementariTrasferta(0);
			}
			else if(perc<=0.65) {
				ospite.setGolFatti(1);
				p.setGolSupplementariTrasferta(1);
			}
			else if(perc<=0.875) {
				ospite.setGolFatti(2);
				p.setGolSupplementariTrasferta(2);
						}
			else {
				ospite.setGolFatti(3);
				p.setGolSupplementariTrasferta(3);
			}
		}
		else if(diffOspite>=5){
			if(perc<=0.25) {
				ospite.setGolFatti(0);
				p.setGolSupplementariTrasferta(0);
			}
			else if(perc<=0.7) {
				ospite.setGolFatti(1);
				p.setGolSupplementariTrasferta(1);
			}
			else if(perc<=0.925) {
				ospite.setGolFatti(2);
				p.setGolSupplementariTrasferta(2);
						}
			else {
				ospite.setGolFatti(3);
				p.setGolSupplementariTrasferta(3);
			}
		}
		else if(diffOspite>=3){
			if(perc<=0.275) {
				ospite.setGolFatti(0);
				p.setGolSupplementariTrasferta(0);
			}
			else if(perc<=0.75) {
				ospite.setGolFatti(1);
				p.setGolSupplementariTrasferta(1);
			}
			else if(perc<=0.925) {
				ospite.setGolFatti(2);
				p.setGolSupplementariTrasferta(2);
						}
			else {
				ospite.setGolFatti(3);
				p.setGolSupplementariTrasferta(3);
			}
		}
		else if(diffOspite>=1){
			if(perc<=0.3) {
				ospite.setGolFatti(0);
				p.setGolSupplementariTrasferta(0);
			}
			else if(perc<=0.8) {
				ospite.setGolFatti(1);
				p.setGolSupplementariTrasferta(1);
			}
			else if(perc<=0.95) {
				ospite.setGolFatti(2);
				p.setGolSupplementariTrasferta(2);
			}
			else {
				ospite.setGolFatti(3);
				p.setGolSupplementariTrasferta(3);
			}
		}
		else if(diffOspite>=-1){
			if(perc<=0.325) {
				ospite.setGolFatti(0);
				p.setGolSupplementariTrasferta(0);
			}
			else if(perc<=0.85) {
				ospite.setGolFatti(1);
				p.setGolSupplementariTrasferta(1);
			}
			else if(perc<=0.975) {
				ospite.setGolFatti(2);
				p.setGolSupplementariTrasferta(2);
						}
			else {
				ospite.setGolFatti(3);
				p.setGolSupplementariTrasferta(3);
			}
		}
		else if(diffOspite>=-3){
			if(perc<=0.4) {
				ospite.setGolFatti(0);
				p.setGolSupplementariTrasferta(0);
			}
			else if(perc<=0.9) {
				ospite.setGolFatti(1);
				p.setGolSupplementariTrasferta(1);
			}
			else {
				ospite.setGolFatti(2);
				p.setGolSupplementariTrasferta(2);
			}
		}
		else if(diffOspite>=-5){
			if(perc<=0.5) {
				ospite.setGolFatti(0);
				p.setGolSupplementariTrasferta(0);
			}
			else if(perc<=0.95) {
				ospite.setGolFatti(1);
				p.setGolSupplementariTrasferta(1);
			}
			else {
				ospite.setGolFatti(2);
				p.setGolSupplementariTrasferta(2);
			}
		}
		else if(diffOspite>=-8){
			if(perc<=0.55) {
				ospite.setGolFatti(0);
				p.setGolSupplementariTrasferta(0);
			}
			else if(perc<=0.975) {
				ospite.setGolFatti(1);
				p.setGolSupplementariTrasferta(1);
			}
			else {
				ospite.setGolFatti(2);
				p.setGolSupplementariTrasferta(2);
			}
		}
		else if(diffOspite>=-11){
			if(perc<=0.6) {
				ospite.setGolFatti(0);
				p.setGolSupplementariTrasferta(0);
			}
			else if(perc<=0.98) {
				ospite.setGolFatti(1);
				p.setGolSupplementariTrasferta(1);
			}
			else {
				ospite.setGolFatti(2);
				p.setGolSupplementariTrasferta(2);
			}
		}
		else if(diffOspite>=-14){
			if(perc<=0.65) {
				ospite.setGolFatti(0);
				p.setGolSupplementariTrasferta(0);
			}
			else if(perc<=0.99) {
				ospite.setGolFatti(1);
				p.setGolSupplementariTrasferta(1);
			}
			else {
				ospite.setGolFatti(2);
				p.setGolSupplementariTrasferta(2);
			}
		}
	}
	
	public List<String> getRisultati() {
		return risultatiPartite;
	}
	
	
}
