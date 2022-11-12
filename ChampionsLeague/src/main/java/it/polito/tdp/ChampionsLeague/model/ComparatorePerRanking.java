package it.polito.tdp.ChampionsLeague.model;

import java.util.Comparator;

public class ComparatorePerRanking implements Comparator<Squadra> {


	@Override
	public int compare(Squadra o1, Squadra o2) {
		// TODO Auto-generated method stub
		return o1.getPosizioneRanking()-o2.getPosizioneRanking();
	}

}
