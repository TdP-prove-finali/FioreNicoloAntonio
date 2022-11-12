package it.polito.tdp.ChampionsLeague.model;

import java.util.Comparator;

public class ComparatoreSquadreOrdineAlfabetico implements Comparator<Squadra> {

	@Override
	public int compare(Squadra o1, Squadra o2) {
		return o1.getNome().compareTo(o2.getNome());
	}

}
