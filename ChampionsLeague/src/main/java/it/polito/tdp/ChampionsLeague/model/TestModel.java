package it.polito.tdp.ChampionsLeague.model;

import java.util.Collections;
import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		Model model=new Model();
		//model.riempiListe();
		
		double start=System.nanoTime();
		model.initiGiornate();
		double end=System.nanoTime();
		//System.out.println("\n"+model.getUrna1().toString()+"\n"+model.getUrna2().toString());
      //  model.creaCalendario();
		
       /* System.out.println("\n"+model.getPartiteGironi().toString());
        
        for(PartitaGirone pg:model.getPartiteGironi()) {
        	System.out.println(pg.getCasa().getNome()+"-"+pg.getTrasferta().getNome());
        }
        for(Squadra s: model.getSquadre()) {
        	System.out.println(s.getNome()+" "+s.getCountPartite()+"\n");
        }*/
		double tempo=(end-start)/1000000000;
		System.out.println("Ci sono voluti secondi"+tempo);
       /* for(Giornata g: model.getGiornate().values()) {
        	System.out.println("Giornata : "+g.toString()+"\n");
        	for(PartitaGirone pg: g.getPartite())
        	System.out.println(pg.toString());
        }*/
        System.out.println("La creazione dei gironi ha impiegato: "+model.durata()/1000000000+" secondi");
        for(Giornata g: model.getGiornate().values()) {
        	System.out.println(g.toString()+"\n"+g.getCont11()+g.getCont12()+g.getCont13()+g.getCont14()
        										+g.getCont21()+g.getCont22()+g.getCont23()+g.getCont24()
        										+g.getCont31()+g.getCont32()+g.getCont33()+g.getCont34()
        										+g.getCont41()+g.getCont42()+g.getCont43()+g.getCont44());
        }
//        model.initSimulazioneFaseGirone();
        /*for(Squadra s :model.getSquadre()) {
        	System.out.println(s.getNome()+" "+s.getCountPartite());
        }*/
        
       /* model.initSimulazioneFaseGirone();
        model.calcolaGuadagniIniziali();
        for(Giornata g: model.getGiornate().values()) {
        	for(PartitaGirone pg: g.getPartite())
        	System.out.println(pg.getRisultato()+"\n");
        }
        //model.calcolaGuadagniIniziali();
        List<Squadra> squadre=model.getSquadre();
        Collections.sort(squadre);
		Collections.reverse(squadre);
		for(Squadra s: squadre) {
		System.out.println(s.getNome()+" "+s.getPunti()+" e ha guadagnato: "+s.guadagniTotali()+" di cui"+s.getMarketPool()+"\n");
		}
		
		for(PartitaEliminazioneDiretta pe : model.getPartitePlayOff()) {
			System.out.println(pe.getCasa().getNome()+" "+pe.getGolCasa()+" - "+pe.getGolTrasferta()+" "+pe.getTrasferta().getNome());
		}
		for(String res: model.getRisultati()) {
			System.out.println(res);
		}*/
	}

}
