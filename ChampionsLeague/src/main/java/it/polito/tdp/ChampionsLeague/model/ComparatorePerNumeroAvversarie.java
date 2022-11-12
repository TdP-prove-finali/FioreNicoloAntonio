package it.polito.tdp.ChampionsLeague.model;

import java.util.Comparator;

public class ComparatorePerNumeroAvversarie implements Comparator<Squadra> {

	

	@Override
	public int compare(Squadra o1, Squadra o2) {
		return o1.getPossibiliAvversarie().size()-o2.getPossibiliAvversarie().size();
	}

}
