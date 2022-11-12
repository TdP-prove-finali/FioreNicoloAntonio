package it.polito.tdp.ChampionsLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Timer;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.ChampionsLeague.db.ChampionsLeagueDao;

public class Model {
	private Map<String,Squadra> idMap;
	private ChampionsLeagueDao dao;
	private List<Squadra> squadre;
	private Map<String,Federazione> idFederazioni;
	private Map<String,Stadio> stadi;
	private List<PartitaGirone> partiteGironi;
	private Map<Integer,Giornata> giornate;
	private List<Squadra> primaFasciaCasa;
	private List<Squadra> secondaFasciaCasa;
	private List<Squadra> terzaFasciaCasa;
	private List<Squadra> quartaFasciaCasa;
	
	int tentativi;
	
	private boolean guadagniInizialiCalcolati;
	
	
	List<PartitaGirone> partiteDaAggiungere;
	boolean flag11;
	boolean flag22;
	boolean flag33;
	boolean flag44;

	
	double start;
	double end;
	double interTempo;
	//Timer timer;
	public Model() {
		dao=new ChampionsLeagueDao();
		idMap=new HashMap<>();
		giornate=new HashMap<>();
		squadre=new ArrayList<>();
		idFederazioni=new HashMap<>();
		stadi=new HashMap<>();
		start=0;
		end=0;
		interTempo=0;
		//timer=new Timer();
		guadagniInizialiCalcolati=false;
	}
	
	
	public void riempiListe() {
		dao.getAllSquadre(idMap);
		if(squadre.isEmpty())
		squadre.addAll(idMap.values());
		this.primaFasciaCasa=dao.getSquadrePrimaFascia(idMap);
		this.secondaFasciaCasa=dao.getSquadreSecondaFascia(idMap);
		this.terzaFasciaCasa=dao.getSquadreTerzaFascia(idMap);
		this.quartaFasciaCasa=dao.getSquadreQuartaFascia(idMap);
		//stadi=dao.getAllStadi();
		dao.getAllFederazioni(idFederazioni);
		
		}
	
	public List<Squadra> getSquadre() {
		dao.getAllSquadre(idMap);
		if(squadre.isEmpty())
		squadre.addAll(idMap.values());
		Collections.sort(squadre,new ComparatoreSquadreOrdineAlfabetico());
		return squadre;
	}
	public List<Stadio> getStadi(){
		
		List<Stadio> listaStadi=dao.getStadiASquadrePartecipanti(stadi);
		Collections.sort(listaStadi);
		return listaStadi;
	}
   
	List<PartitaGirone> parziale;
	public boolean controlloPartita(Squadra casa, Squadra ospite) {
		boolean flagCasa=false;
		boolean flagOspite=false;
		boolean flag=false;
		int fasciaCasa=casa.getFascia();
		int fasciaOspite=ospite.getFascia();
		if(casa.getCountPartite()<8 && ospite.getCountPartite()<8) { // controllo che non siano già state assegnate tutte le partite
			if(casa.getCountCasa()<4 && ospite.getCountTrasferta()<4) {
		switch(fasciaCasa) {
		case 1:
			if(ospite.getCount1Trasferta()>=1)
				flagOspite=false;
			else 
				flagOspite=true;
			break;
		case 2:
			if(ospite.getCount2Trasferta()>=1)
				flagOspite=false;
			else 
				flagOspite=true;
			break;
		case 3:
			if(ospite.getCount3Trasferta()>=1)
				flagOspite=false;
			else
				flagOspite=true;
			break;
		case 4:
			if(ospite.getCount4Trasferta()>=1)
				flagOspite=false;
			else
				flagOspite=true;
			break;
		}
		
		
		switch(fasciaOspite) {
		case 1:
			if(casa.getCount1Casa()>=1)
				flagCasa=false;
			else
				flagCasa=true;
			break;
		case 2:
			if(casa.getCount2Casa()>=1)
				flagCasa=false;
			else
				flagCasa=true;
			break;
		case 3:
			if(casa.getCount3Casa()>=1)
				flagCasa=false;
			else 
				flagCasa=true;
			break;
		case 4:
			if(casa.getCount4Casa()>=1)
				flagCasa=false;
			else 
				flagCasa=true;
			break;
		}
		}
			else return false;
		}
		else return false;
		if(flagCasa==true && flagOspite==true) {
			flag=true;
		}
		return flag;
	}
	
	public void incrementaPartita(Squadra casa,Squadra ospite) {
		int fasciaCasa=casa.getFascia();
		int fasciaOspite=ospite.getFascia();
		casa.setCountCasa();
		ospite.setCountTrasferta();
		casa.incrementaCountPartite();
		ospite.incrementaCountPartite();
		switch(fasciaOspite) {
		case 1:
			casa.setCount1Casa();
			break;
		case 2:
			casa.setCount2Casa();
			break;
		case 3:
			casa.setCount3Casa();
			break;
		case 4:
			casa.setCount4Casa();
			break;
		}
		switch(fasciaCasa) {
		case 1:
			ospite.setCount1Trasferta();
			break;
		case 2:
			ospite.setCount2Trasferta();
			break;
		case 3:
			ospite.setCount3Trasferta();
			break;
		case 4:
			ospite.setCount4Trasferta();
			break;
		}
	}
	public void decrementaPartita(Squadra casa,Squadra ospite) {
		int fasciaCasa=casa.getFascia();
		int fasciaOspite=ospite.getFascia();
		casa.decrementaCountCasa();
		ospite.decrementaCountTrasferta(); 
		casa.decrementaCountPartite();
		ospite.decrementaCountPartite();
		switch(fasciaOspite) {
		case 1:
			casa.decrementaCount1Casa();
			break;
		case 2:
			casa.decrementaCount2Casa();
			break;
		case 3:
			casa.decrementaCount3Casa();
			break;
		case 4:
			casa.decrementaCount4Casa();
			break;
		}
		switch(fasciaCasa) {
		case 1:
			ospite.decrementaCount1Trasferta();
			break;
		case 2:
			ospite.decrementaCount2Trasferta();
			break;
		case 3:
			ospite.decrementaCount3Trasferta();
			break;
		case 4:
			ospite.decrementaCount4Trasferta();
			break;
		}
	}

	public List<PartitaGirone> getPartiteGironi() {
		return partiteGironi;
	}
	
	public void initiGiornate() {
		tentativi++;
		guadagniInizialiCalcolati=false;
		this.riempiListe();
		parziale=new ArrayList<>();
		partiteDaAggiungere=partiteGironi;
		if(giornate.size()!=8) {
		for(int i=1;i<=8;i++) {
			Giornata g=new Giornata(i);
			giornate.put(i, g);
		}
		}
		else {
			for(Giornata g: giornate.values()) {
				g.inizializza();
			}
			for(Squadra s : squadre) {
				s.inizializza();
			}
		}
		flag11=false;
		flag22=false;
		List<Squadra> squadreParziale=new ArrayList<>();
		squadreParziale.addAll(squadre);
		//parziale=new ArrayList<>();
		start=System.nanoTime();
		this.ricorsione(giornate.get(1),squadreParziale);
		end=System.nanoTime();
	}
	public double durata() {
		return this.end-this.start;
	}
	
	public boolean ricorsione(Giornata giornata, List<Squadra> squadreParziale) {
		interTempo=System.nanoTime();
		double intervallo=(interTempo-start)/1000000000;
		if(intervallo>0.2) {
			this.initiGiornate();
			return false;
		}
		else {
		  if(numPartite(giornata)==18) {
			  if(giornata.getCont11()>=1
					  && giornata.getCont22()>=1 
					 // && giornata.getCont33()>=1
					 /* && giornata.getCont44()>=1*/)  {
 			  if(giornata.getIdGiornata()<6) {
				  partiteGironi=new ArrayList<>(parziale);
			  squadreParziale=new ArrayList<>(squadre);
		//	  System.out.println(giornata.getPartite().toString());
			  boolean continua1=ricorsione(giornate.get(giornata.getIdGiornata()+1),squadreParziale);
				if(!continua1)
					return false;
			  }
 			  if(giornata.getIdGiornata()==6) {
 				 partiteGironi=new ArrayList<>(parziale);
 				  squadreParziale=new ArrayList<>(squadre);
 				  this.calcolaPossibiliAvversarie(giornata, squadreParziale);
 			//	  System.out.println(giornata.getPartite().toString());
 				  boolean continua1=ricorsioneUltimeGiornate(giornate.get(giornata.getIdGiornata()+1),squadreParziale);
 					if(!continua1)
 						return false;
 			  }
 			
			  boolean flag=false;
			  for(Giornata g: this.giornate.values()) {
				  if(g.getPartite().size()!=18) {
					  flag=true;
				  }
			  }
			  if(flag==false) {
				  return false;
			  }
		  }
			  return true;
		  }
		  //Collections.shuffle(partiteGironi);
		     else {
		    	  if(giornata.getCont11()<1) {
			  //Collections.shuffle(primaFasciaOspite);
			     Collections.shuffle(primaFasciaCasa);
			    	 for(Squadra casa: primaFasciaCasa) {
			    		 for(Squadra ospite: primaFasciaCasa) {
					 if(!casa.equals(ospite)) {
							PartitaGirone p=new PartitaGirone(casa,ospite, giornata);
							if(!parziale.contains(p)) {
								if(controlloPartita(casa,ospite)) {
									if(controlloGiornata(p,giornata)) {
										  giornata.aggiungiPartita(p);
										  parziale.add(p);
										  this.incrementaPartita(casa, ospite);
										  //p.setPartitaAggiunta(true);
										  squadreParziale.remove(casa);
										  squadreParziale.remove(ospite);
										  casa.aggiungiPartita(p);
										  ospite.aggiungiPartita(p);
										  boolean continua=ricorsione(giornata,squadreParziale);
											if(!continua)
												return false;
									  giornata.getPartite().remove(p);
									  decrementa(p,giornata);
									  parziale.remove(p);
									  this.decrementaPartita(casa, ospite);
									  squadreParziale.add(casa);
									  squadreParziale.add(ospite);
									  casa.rimuoviPartita(p);
									  ospite.rimuoviPartita(p);
									  	}
									}
								}
						   	}
			    		 }
			    	 }
			     }
		 
		  else  if(giornata.getCont22()<1) {
			  //Collections.shuffle(secondaFasciaOspite);
			     Collections.shuffle(secondaFasciaCasa);
			    	 for(Squadra casa: secondaFasciaCasa) {
			    		 for(Squadra ospite: secondaFasciaCasa) {
					 if(!casa.equals(ospite)) {
							PartitaGirone p=new PartitaGirone(casa,ospite, giornata);
							if(!parziale.contains(p)) {
								if(controlloPartita(casa,ospite)) {
									if(controlloGiornata(p,giornata)) {
										  giornata.aggiungiPartita(p);
										  parziale.add(p);
										  this.incrementaPartita(casa, ospite);
										 // p.setPartitaAggiunta(true);
										  squadreParziale.remove(casa);
										  squadreParziale.remove(ospite);
										  casa.aggiungiPartita(p);
										  ospite.aggiungiPartita(p);
										  boolean continua=ricorsione(giornata,squadreParziale);
											if(!continua)
												return false;
									  giornata.getPartite().remove(p);
									 // p.setPartitaAggiunta(false);
									  decrementa(p,giornata);
									  parziale.remove(p);
										this.decrementaPartita(casa, ospite);
										squadreParziale.add(casa);
										squadreParziale.add(ospite);
										casa.rimuoviPartita(p);
										ospite.rimuoviPartita(p);
									  	}
									}
								}
						   	}
			    		 }
			    	 }
			     }
		  else {
			  List<Squadra> sp=new ArrayList<>(squadreParziale);
			  Collections.shuffle(sp);
			    	 for(Squadra casa: sp) {
			    		 for(Squadra ospite: sp) {
					 if(!casa.equals(ospite)) {
							PartitaGirone p=new PartitaGirone(casa,ospite, giornata);
							if(!parziale.contains(p)) {
								if(controlloPartita(casa,ospite)) {
									if(controlloGiornata(p,giornata)) {
										  giornata.aggiungiPartita(p);
										  parziale.add(p);
										  this.incrementaPartita(casa, ospite);
										//  p.setPartitaAggiunta(true);
										  squadreParziale.remove(casa);
										  squadreParziale.remove(ospite);
										  casa.aggiungiPartita(p);
										  ospite.aggiungiPartita(p);
										  boolean continua=ricorsione(giornata,squadreParziale);
											if(!continua)
												return false;
									  giornata.getPartite().remove(p);
									//  p.setPartitaAggiunta(false);
									  decrementa(p,giornata);
									  parziale.remove(p);
										this.decrementaPartita(casa, ospite);
										squadreParziale.add(casa);
										squadreParziale.add(ospite);
										casa.rimuoviPartita(p);
										ospite.rimuoviPartita(p);
									  	}
									}
								}
						   	}
			    		 }
			    	 }
			     }
		  
		
		  return true;
		  }
		}
		}
		
	
	private void calcolaPossibiliAvversarie(Giornata giornata, List<Squadra> squadreParziale) {
		//Squadra squadra=squadraScelta(giornata,squadreParziale);
		Set<Squadra> possibiliAvversarie=new HashSet<>();
		
		int fascia;
		for(Squadra s : squadreParziale) {
			possibiliAvversarie.clear();
			fascia=s.getFascia();
			if(s.getCount1Casa()==0) { // questa squadra deve giocare contro fascia uno in casa
				if(fascia==1) { // partita 1 vs 1
					for(Squadra f1: primaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount1Trasferta()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								if(giornata.getCont11()<1 || flag11==false)
								possibiliAvversarie.add(f1);
								//s.aggiungiAvversaria(f1);
								}
							}
						}
					}
				}
				if(fascia==2) { // partita 1 vs 1
					for(Squadra f1: primaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount2Trasferta()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
				if(fascia==3) { // partita 1 vs 1
					for(Squadra f1: primaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount3Trasferta()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
							 }
						   }	
						}
					}
				}
				if(fascia==4) { // partita 1 vs 1
					for(Squadra f1: primaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount4Trasferta()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
							//	s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
			}
			if(s.getCount2Casa()==0) {
				if(fascia==1) { // partita 1 vs 1
					for(Squadra f1: secondaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount1Trasferta()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
				if(fascia==2) { // partita 1 vs 1
					for(Squadra f1: secondaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount2Trasferta()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								if(giornata.getCont22()<1 || flag22==false)
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
				if(fascia==3) { // partita 1 vs 1
					for(Squadra f1: secondaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount3Trasferta()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
							 }
						   }	
						}
					}
				}
				if(fascia==4) { // partita 1 vs 1
					for(Squadra f1: secondaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount4Trasferta()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
			}
            if(s.getCount3Casa()==0) {
            	if(fascia==1) { // partita 1 vs 1
					for(Squadra f1: terzaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount1Trasferta()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
				if(fascia==2) { // partita 1 vs 1
					for(Squadra f1: terzaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount2Trasferta()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
							//	s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
				if(fascia==3) { // partita 1 vs 1
					for(Squadra f1: terzaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount3Trasferta()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
							//	s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
							 }
						   }	
						}
					}
				}
				if(fascia==4) { // partita 1 vs 1
					for(Squadra f1: terzaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount4Trasferta()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
			}
            if(s.getCount4Casa()==0) {
            	if(fascia==1) { // partita 1 vs 1
					for(Squadra f1: quartaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount1Trasferta()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
				if(fascia==2) { // partita 1 vs 1
					for(Squadra f1: quartaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount2Trasferta()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
				if(fascia==3) { // partita 1 vs 1
					for(Squadra f1: quartaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount3Trasferta()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
							 }
						   }	
						}
					}
				}
				if(fascia==4) { // partita 1 vs 1
					for(Squadra f1: quartaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount4Trasferta()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
			}
			if(s.getCount1Trasferta()==0) {
				if(fascia==1) { // partita 1 vs 1
					for(Squadra f1: primaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount1Casa()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								if(giornata.getCont11()<1 || flag11==false)
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
				if(fascia==2) { // partita 1 vs 1
					for(Squadra f1: primaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount2Casa()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
				if(fascia==3) { // partita 1 vs 1
					for(Squadra f1: primaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount3Casa()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
							//	s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
							 }
						   }	
						}
					}
				}
				if(fascia==4) { // partita 1 vs 1
					for(Squadra f1: primaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount4Casa()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
							//	s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}			
						}
			if(s.getCount2Trasferta()==0) {
				if(fascia==1) { // partita 1 vs 1
					for(Squadra f1: secondaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount1Casa()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
				if(fascia==2) { // partita 1 vs 1
					for(Squadra f1: secondaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount2Casa()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								if(giornata.getCont22()<1 || flag22==false)
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
				if(fascia==3) { // partita 1 vs 1
					for(Squadra f1: secondaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount3Casa()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
							//	s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
							 }
						   }	
						}
					}
				}
				if(fascia==4) { // partita 1 vs 1
					for(Squadra f1: secondaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount4Casa()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
			}
			if(s.getCount3Trasferta()==0) {
				if(fascia==1) { // partita 1 vs 1
					for(Squadra f1: terzaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount1Casa()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
							//	s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
				if(fascia==2) { // partita 1 vs 1
					for(Squadra f1: terzaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount2Casa()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
				if(fascia==3) { // partita 1 vs 1
					for(Squadra f1: terzaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount3Casa()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
							//	s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
							 }
						   }	
						}
					}
				}
				if(fascia==4) { // partita 1 vs 1
					for(Squadra f1: terzaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount4Casa()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
							//	s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
			}
			if(s.getCount4Trasferta()==0) {
				if(fascia==1) { // partita 1 vs 1
					for(Squadra f1: quartaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount1Casa()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
							//	s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
				if(fascia==2) { // partita 1 vs 1
					for(Squadra f1: quartaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount2Casa()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
				if(fascia==3) { // partita 1 vs 1
					for(Squadra f1: quartaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount3Casa()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
							 }
						   }	
						}
					}
				}
				if(fascia==4) { // partita 1 vs 1
					for(Squadra f1: quartaFasciaCasa) {
						if(squadreParziale.contains(f1)) {
						if(f1.getCount4Casa()==0) {
							if(!parziale.contains(new PartitaGirone(s,f1, giornata))) {
								//s.aggiungiAvversaria(f1);
								possibiliAvversarie.add(f1);
								}
							}
						}
					}
				}
			}
			s.aggiungiAvversarie(possibiliAvversarie);
		}
		
	}

	private boolean ricorsioneUltimeGiornate(Giornata giornata, List<Squadra> squadreParziale) {
		interTempo=System.nanoTime();
		double intervallo=(interTempo-start)/1000000000;
		if(intervallo>0.2) {
			this.initiGiornate();
			return false;
		}
		else {
		if(numPartite(giornata)==18) {
			  if(giornata.getCont11()>=1)  {
			  if(giornata.getIdGiornata()<8) {
				 partiteGironi=new ArrayList<>(parziale);
				  squadreParziale=new ArrayList<>(squadre);
				  this.calcolaPossibiliAvversarie(giornata, squadreParziale);
			//	  System.out.println(giornata.getPartite().toString());
				  boolean continua1=ricorsioneUltimeGiornate(giornate.get(giornata.getIdGiornata()+1),squadreParziale);
					if(!continua1)
						return false;
			  }
			  if(giornata.getIdGiornata()==8)
				  partiteGironi=new ArrayList<>(parziale); 
			
			  boolean flag=false;
			  for(Giornata g: this.giornate.values()) {
				  if(g.getPartite().size()!=18) {
					  flag=true;
				  }
			  }
			  if(flag==false) {
				  return false;
			  }
		  }
			  return true;
		  }
		this.calcolaPossibiliAvversarie(giornata, squadreParziale);
		Collections.sort(squadreParziale, new ComparatorePerNumeroAvversarie());
		List<Squadra> possibiliAvversarie;
		List<Squadra> sp=new ArrayList<>(squadreParziale);
		for(Squadra s : sp) {
			if(s.possibiliAvversarie.size()==0)
				return true;
			possibiliAvversarie=new ArrayList<>(s.getPossibiliAvversarie());
			for(Squadra s2: possibiliAvversarie) {
				if(!s.equals(s2)) {
				PartitaGirone p=new PartitaGirone(s,s2, giornata);
				if(!parziale.contains(p)) {
					if(controlloPartita(s,s2)) {
						if(controlloGiornata(p,giornata)) {
							giornata.aggiungiPartita(p);
							  parziale.add(p);
							  this.incrementaPartita(s, s2);
							//  p.setPartitaAggiunta(true);
							  squadreParziale.remove(s);
							  squadreParziale.remove(s2);
							  s.aggiungiPartita(p);
							  s2.aggiungiPartita(p);
							  boolean continua=ricorsioneUltimeGiornate(giornata,squadreParziale);
								if(!continua)
									return false;
						  giornata.getPartite().remove(p);
						//  p.setPartitaAggiunta(false);
						  decrementa(p,giornata);
						  parziale.remove(p);
							this.decrementaPartita(s, s2);
							squadreParziale.add(s);
							squadreParziale.add(s2);
							s.rimuoviPartita(p);
							s2.rimuoviPartita(p);
							this.calcolaPossibiliAvversarie(giornata, squadreParziale);
						}
					}
					else {
						PartitaGirone pp=new PartitaGirone(s2,s, giornata);
						if(!parziale.contains(pp)) {
							if(controlloPartita(s2,s)) {
								if(controlloGiornata(pp,giornata)) {
									giornata.aggiungiPartita(pp);
									  parziale.add(pp);
									  this.incrementaPartita(s2, s);
									//  p.setPartitaAggiunta(true);
									  squadreParziale.remove(s);
									  squadreParziale.remove(s2);
									  s.aggiungiPartita(pp);
									  s2.aggiungiPartita(pp);
									  boolean continua=ricorsioneUltimeGiornate(giornata,squadreParziale);
										if(!continua)
											return false;
								  giornata.getPartite().remove(pp);
								//  p.setPartitaAggiunta(false);
								  decrementa(p,giornata);
								  parziale.remove(pp);
									this.decrementaPartita(s2, s2);
									squadreParziale.add(s);
									squadreParziale.add(s2);
									s2.rimuoviPartita(pp);
									s.rimuoviPartita(pp);
									this.calcolaPossibiliAvversarie(giornata, squadreParziale);
								}
							}
					}
					}	
				}
			}
		}
		
	}
		return true;
		}
}

	private void decrementa(PartitaGirone pg, Giornata giornata) {
		switch(pg.getLivelloPartita()) {
		case "1-1":
			if(giornata.getCont11()==2)
				flag11=false;
			giornata.decrementaCont11();
			break;
		case "1-2":
			giornata.decrementaCont12();
			break;
		case "1-3":
			giornata.decrementaCont13();
			break;
		case "1-4":
			giornata.decrementaCont14();
			break;
		case "2-1":
			giornata.decrementaCont21();
			break;
		case "2-2":
			if(giornata.getCont22()==2)
				flag22=false;
			giornata.decrementaCont22();
			break;
		case "2-3":
			giornata.decrementaCont23();
			break;
		case "2-4":
			giornata.decrementaCont24();
			break;
		case "3-1":
			giornata.decrementaCont31();
			break;
		case "3-2":
			giornata.decrementaCont32();
			break;
		case "3-3":
			if(giornata.getCont33()==2)
				flag33=false;
			giornata.decrementaCont33();
			break;
		case "3-4":
			giornata.decrementaCont34();
			break;
		case "4-1":
			giornata.decrementaCont41();
			break;
		case "4-2":
			giornata.decrementaCont42();
			break;
		case "4-3":
			giornata.decrementaCont43();
			break;
		case "4-4":
			if(giornata.getCont44()==2)
				flag44=false;
			giornata.decrementaCont44();
			break;
		}
		
	}
	private boolean controlloGiornata(PartitaGirone pg, Giornata giornata) {
		for(PartitaGirone p: giornata.getPartite()) {
			if(pg.getCasa().equals(p.getCasa()) || pg.getCasa().equals(p.getTrasferta()) || pg.getTrasferta().equals(p.getTrasferta()) || pg.getTrasferta().equals(p.getCasa())) {
				return false;
			}
		}
		switch(pg.getLivelloPartita()) {
		case "1-1":
			if(giornata.getCont11()==0) {
				giornata.setCont11();
				return true;
			}
			break;
		case "2-2":
				if(giornata.getCont22()==0) {
					giornata.setCont22();
					return true;
				}
			break;
		case "3-3":
			if(giornata.getCont33()<2) {
				giornata.setCont33();
				return true;
			}
			break;
		case "4-4":
			if(giornata.getCont44()<2) {
				giornata.setCont44();
				return true;
			}
			break;
		}
			
		if(  giornata.getCont11()<=1  && giornata.getCont22()<=1 /* && giornata.getCont33()<=1 && giornata.getCont44()<=1*/) {
		switch(pg.getLivelloPartita()) {
		case "1-1":
			if(giornata.getCont11()>1) {
				return false;
			}else {
				if(flag11)
					return false;
				giornata.setCont11();
				flag11=true;
				return true;
			}
		case "2-2":
				if(giornata.getCont22()>1) {
					return false;
				}else {
					if(flag22)
						return false;
					giornata.setCont22();
					flag22=true;
					return true;
				}
		case "3-3":
			if(giornata.getCont33()>1) {
				return false;
			}else {
				if(flag33)
					return false;
				giornata.setCont33();
				flag33=true;
				return true;
			}
		case "4-4":
			if(giornata.getCont44()>1) {
				return false;
			}else {
				if(flag44)
					return false;
				giornata.setCont44();
				flag44=true;
				return true;
			}
		}
		}
			switch(pg.getLivelloPartita()) {
			case "1-2":
				if(giornata.getCont12()>2)
					return false;
				giornata.setCont12();
				return true;
			case "1-3":
				if(giornata.getCont13()>2)
					return false;
				giornata.setCont13();
				return true;
			case "1-4":
				if(giornata.getCont14()>2)
					return false;
				giornata.setCont14();
				return true;
			case "2-1":
				if(giornata.getCont21()>2)
					return false;
				giornata.setCont21();
				return true;
			case "2-3":
				if(giornata.getCont23()>2)
					return false;
				giornata.setCont23();
				return true;
			case "2-4":
				if(giornata.getCont24()>2)
					return false;
				giornata.setCont24();
				return true;
			case "3-1":
				if(giornata.getCont31()>2)
					return false;
				giornata.setCont31();
				return true;
			case "3-2":
				if(giornata.getCont32()>2)
					return false;
				giornata.setCont32();
				return true;
			
			case "3-4":
				if(giornata.getCont34()>2)
					return false;
				giornata.setCont34();
				return true;
			case "4-1":
				if(giornata.getCont41()>2)
					return false;
				giornata.setCont41();
				return true;
			case "4-2":
				if(giornata.getCont42()>2)
					return false;
				giornata.setCont42();
				return true;
			case "4-3":
				if(giornata.getCont43()>2)
					return false;
				giornata.setCont43();
				return true;
			}
		
		
		
		return false;
	}
    
	

	private int numPartite(Giornata giornata) {
		return giornata.getPartite().size();
	}
	public Map<Integer, Giornata> getGiornate() {
		return giornate;
	}
	PriorityQueue<Evento> coda;
	Simulatore sim;
	List<Squadra> squadreAgliOttavi;
	List<Squadra> squadrePlayOff;
	List<Squadra> vincitriciPlayOff;
	List<Squadra> squadreAiQuarti;
	List<Squadra> squadreInSemifinale;
	List<Squadra> squadreInFinale;
	
	
	public void initSimulazioneFaseGirone() {
		// carico la coda degli eventi per poi passarla al simulatore
		for(Squadra s : squadre) {
			s.inizializzaValori();
		}
		coda=new PriorityQueue<Evento>();
		int cont=0;
		for(Giornata g : giornate.values()) {
			cont=0;
			for(PartitaGirone pg: g.getPartite()) {
				cont++;
				pg.setGiornata(g);
				Evento e= new Evento(cont, pg);
				coda.add(e);
			}
		}
		sim=new Simulatore(coda,stadi);
		sim.calcolaRisultati();
		
		
		
	}
	public List<String> initSimulazioneEliminazioneDiretta() {
		//squadreAgliOttavi.clear();
		squadreAgliOttavi=new ArrayList<>();
		squadrePlayOff=new ArrayList<>();
		vincitriciPlayOff=new ArrayList<>();
		squadreAiQuarti=new ArrayList<>();
		squadreInSemifinale=new ArrayList<>();
		squadreInFinale=new ArrayList<>();
		Collections.sort(squadre);
		Collections.reverse(squadre);
		for(int i=0;i<8;i++) {
			squadreAgliOttavi.add(squadre.get(i));
		}
		for(int i=8;i<24;i++) {
			squadrePlayOff.add(squadre.get(i));
		}
		sim.calcolaGuadagniOttavi(squadreAgliOttavi);
		sim.calcolaGuadagniPlayOff(squadrePlayOff);
		generaPartitePlayOff(squadrePlayOff);
		return sim.getRisultati();
	}
	List<PartitaEliminazioneDiretta> partitePlayOff;
	List<PartitaEliminazioneDiretta> partiteOttavi;
	List<PartitaEliminazioneDiretta> partiteQuarti;
	List<PartitaEliminazioneDiretta> partiteSemifinale;
	List<PartitaEliminazioneDiretta> partitaFinale;
	Squadra vincitrice;
	public List<Squadra> getSquadreAiPlayOff(){
		return squadrePlayOff;
	}
	public void generaPartitePlayOff(List<Squadra> squadrePlayOff) {
		partitePlayOff=new ArrayList<>();
		for(int i=0;i<8;i++) {
			Squadra casa=squadrePlayOff.get(i);
			Squadra ospite=squadrePlayOff.get(squadrePlayOff.size()-(i+1));
			PartitaEliminazioneDiretta pA=new PartitaEliminazioneDiretta(casa,ospite,"PlayOff");
			PartitaEliminazioneDiretta pR=new PartitaEliminazioneDiretta(ospite,casa,"PlayOff");
			casa.aggiungiString(pA);
			casa.aggiungiString(pR);
			ospite.aggiungiString(pA);
			ospite.aggiungiString(pR);
			partitePlayOff.add(pA);
			partitePlayOff.add(pR);
			coda.add(new Evento(i, pA));
			coda.add(new Evento(squadrePlayOff.size()-(i+1), pR));
				pA.setAndataORitorno("A");
				pR.setAndataORitorno("R");
		}
		//sim.calcolaRisultatiEliminazioneDiretta(coda,squadrePlayOff,partitePlayOff);
		vincitriciPlayOff=sim.calcolaRisultatiEliminazioneDiretta(coda,partitePlayOff,"PlayOff");
		generaPartiteOttavi(squadreAgliOttavi,vincitriciPlayOff);
		List<Squadra> tutteSquadreAgliOttavi=new ArrayList<>();
		tutteSquadreAgliOttavi.addAll(vincitriciPlayOff);
		tutteSquadreAgliOttavi.addAll(squadreAgliOttavi);
		sim.calcolaMarketPool(tutteSquadreAgliOttavi,idFederazioni,"Ottavi");
		
		
	}
	private void generaPartiteOttavi(List<Squadra> squadreAgliOttavi2, List<Squadra> vincitriciPlayOff2) {
		int cont=0;
		partiteOttavi=new ArrayList<>();
		Collections.shuffle(squadreAgliOttavi2);
		Collections.shuffle(vincitriciPlayOff2);
		while(partiteOttavi.size()!=16) {
			Squadra casa=squadreAgliOttavi2.get(cont);
			Squadra ospite=vincitriciPlayOff2.get(cont);
			
			PartitaEliminazioneDiretta pA=new PartitaEliminazioneDiretta(casa, ospite, "Ottavi");
			PartitaEliminazioneDiretta pR=new PartitaEliminazioneDiretta(ospite, casa, "Ottavi");
			casa.aggiungiString(pA);
			casa.aggiungiString(pR);
			ospite.aggiungiString(pA);
			ospite.aggiungiString(pR);
			partiteOttavi.add(pA);
			partiteOttavi.add(pR);
			pA.setAndataORitorno("A");
			pR.setAndataORitorno("R");
			coda.add(new Evento(cont, pA));
			coda.add(new Evento(16-cont,pR));
			cont++;
		}
		squadreAiQuarti=sim.calcolaRisultatiEliminazioneDiretta(coda, partiteOttavi, "Ottavi");
		sim.calcolaMarketPool(squadreAiQuarti,idFederazioni,"Quarti");
		generaPartiteQuarti(squadreAiQuarti);
	}
   
	private void generaPartiteQuarti(List<Squadra> squadreAiQuarti2) {
		int cont=0;
		partiteQuarti=new ArrayList<>();
		Collections.shuffle(squadreAiQuarti2);
		while(partiteQuarti.size()!=8) {
			Squadra casa=squadreAiQuarti2.get(cont);
			Squadra ospite=squadreAiQuarti2.get(7-cont);
			
			PartitaEliminazioneDiretta pA=new PartitaEliminazioneDiretta(casa, ospite, "Quarti");
			PartitaEliminazioneDiretta pR=new PartitaEliminazioneDiretta(ospite, casa, "Quarti");
			casa.aggiungiString(pA);
			casa.aggiungiString(pR);
			ospite.aggiungiString(pA);
			ospite.aggiungiString(pR);
			partiteQuarti.add(pA);
			partiteQuarti.add(pR);
			pA.setAndataORitorno("A");
			pR.setAndataORitorno("R");
			coda.add(new Evento(cont, pA));
			coda.add(new Evento(7-cont,pR));
			cont++;
		}
		squadreInSemifinale=sim.calcolaRisultatiEliminazioneDiretta(coda, partiteQuarti, "Quarti");
		sim.calcolaMarketPool(squadreInSemifinale,idFederazioni,"Semifinale");
		generaPartiteSemifinali(squadreInSemifinale);
	}

	private void generaPartiteSemifinali(List<Squadra> squadreInSemifinale2) {
		int cont=0;
		partiteSemifinale=new ArrayList<>();
		Collections.shuffle(squadreInSemifinale2);
		while(partiteSemifinale.size()!=4) {
			Squadra casa=squadreInSemifinale2.get(cont);
			Squadra ospite=squadreInSemifinale2.get(3-cont);
			
			PartitaEliminazioneDiretta pA=new PartitaEliminazioneDiretta(casa, ospite, "Semifinale");
			PartitaEliminazioneDiretta pR=new PartitaEliminazioneDiretta(ospite, casa, "Semifinale");
			casa.aggiungiString(pA);
			casa.aggiungiString(pR);
			ospite.aggiungiString(pA);
			ospite.aggiungiString(pR);
			partiteSemifinale.add(pA);
			partiteSemifinale.add(pR);
			pA.setAndataORitorno("A");
			pR.setAndataORitorno("R");
			coda.add(new Evento(cont, pA));
			coda.add(new Evento(3-cont,pR));
			cont++;
		}
		squadreInFinale=sim.calcolaRisultatiEliminazioneDiretta(coda, partiteSemifinale, "Semifinale");
		sim.calcolaMarketPool(squadreInFinale,idFederazioni,"Finale");
		PartitaEliminazioneDiretta finale=new PartitaEliminazioneDiretta(squadreInFinale.get(0), squadreInFinale.get(1), "Finale");
        partitaFinale=new ArrayList<>();
        partitaFinale.add(finale);
        squadreInFinale.get(0).aggiungiString(finale);
        squadreInFinale.get(1).aggiungiString(finale);
		coda.add(new Evento(0,finale));
		vincitrice=sim.finale(coda, finale);
		
		
	}

	public List<PartitaEliminazioneDiretta> getPartitePlayOff() {
		return partitePlayOff;
	}

	public void calcolaGuadagniIniziali() {
		if(!guadagniInizialiCalcolati) {
			guadagniInizialiCalcolati=true;
		Set<Federazione> fedPresenti=new HashSet<Federazione>();
		for(Squadra s: squadre) {
			Federazione f=idFederazioni.get(s.getCampionato());
			f.aggiornaNumSquadrePerTurno(1);
			if(!fedPresenti.contains(f))
			fedPresenti.add(f);		
			}
		sim.calcolaGuadagniIniziali(squadre, fedPresenti);
	}
	}
	
	public List<String> getRisultati(){
		return sim.getRisultati();
	}
	public List<String> getRisultatiOttavi(){
		return sim.getRisultatiOttavi();
	}
	public List<String> getRisultatiQuarti(){
		return sim.getRisultatiQuarti();
	}
	public List<String> getRisultatiPlayOff(){
		return sim.getRisultatiPlayOff();
	}
	public List<String> getRisultatSemifinali(){
		return sim.getRisultatiSemifinali();
	}
	public String getRisultatoFinale(){
		return sim.getRisultatoFinale();
	}


	public List<PartitaEliminazioneDiretta> getPartiteOttavi() {
		return partiteOttavi;
	}


	public List<PartitaEliminazioneDiretta> getPartiteQuarti() {
		return partiteQuarti;
	}


	public List<PartitaEliminazioneDiretta> getPartiteSemifinale() {
		return partiteSemifinale;
	}


	public List<Squadra> getSquadreAgliOttavi() {
		return squadreAgliOttavi;
	}


	public List<PartitaEliminazioneDiretta> getPartitaFinale() {
		return partitaFinale;
	}


	public Squadra getVincitrice() {
		return vincitrice;
	}
	
	public void resettaDatiStadio() {
		for(Stadio s : stadi.values()) {
			s.reset();
		}
	}
	public void resettaDatiStadioED() {
		for(Stadio s : stadi.values()) {
			s.resetED();
		}
	}
	public void resettaDatiSquadra() {
		for(Squadra s : squadre) {
			s.inizializza();
		}
	}
	
	public void resettaDatiSquadra2() {
		for(Squadra s : squadre) {
			s.reset();
		}
	}
	public void resettaDatiSquadraED() {
		for(Squadra s : squadre) {
			s.resetED();
		}
	}


	public void setGuadagniInizialiCalcolati(boolean guadagniInizialiCalcolati) {
		this.guadagniInizialiCalcolati = guadagniInizialiCalcolati;
	}
    
	
	

}
