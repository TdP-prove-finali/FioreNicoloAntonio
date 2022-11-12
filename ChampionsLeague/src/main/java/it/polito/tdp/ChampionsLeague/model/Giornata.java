package it.polito.tdp.ChampionsLeague.model;

import java.util.ArrayList;
import java.util.List;

public class Giornata {
 private int idGiornata;
 private List<PartitaGirone> partite;
 private int cont11;
 private int cont12;
 private int cont13;
 private int cont14;
 private int cont21;
 private int cont22;
 private int cont23;
 private int cont24;
 private int cont31;
 private int cont32;
 private int cont33;
 private int cont34;
 private int cont41;
 private int cont42;
 private int cont43;
 private int cont44;
public Giornata(int idGiornata) {
	super();
	this.idGiornata = idGiornata;
	partite=new ArrayList<>();
	cont11=0;
	cont12=0;
	cont13=0;
	cont14=0;
	cont21=0;
	cont22=0;
	cont23=0;
	cont24=0;
	cont31=0;
	cont32=0;
	cont33=0;
	cont34=0;
	cont41=0;
	cont42=0;
	cont43=0;
	cont44=0;
}
public int getIdGiornata() {
	return idGiornata;
}
public void setIdGiornata(int idGiornata) {
	this.idGiornata = idGiornata;
}

public List<PartitaGirone> getPartite() {
	return partite;
}
public void aggiungiPartita(PartitaGirone partita) {
	this.partite.add(partita);
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + idGiornata;
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
	Giornata other = (Giornata) obj;
	if (idGiornata != other.idGiornata)
		return false;
	return true;
}

	@Override
public String toString() {
	return "Giornata " + idGiornata;
}
	
	public String tipoPartite() {
		return "Giornata [idGiornata=" + idGiornata + ", cont11=" + cont11 + ", cont12=" + cont12 + ", cont13=" + cont13
				+ ", cont14=" + cont14 + ", cont21=" + cont21 + ", cont22=" + cont22 + ", cont23=" + cont23
				+ ", cont24=" + cont24 + ", cont31=" + cont31 + ", cont32=" + cont32 + ", cont33=" + cont33
				+ ", cont34=" + cont34 + ", cont41=" + cont41 + ", cont42=" + cont42 + ", cont43=" + cont43
				+ ", cont44=" + cont44 + "]";
	}
	public int getCont11() {
		return cont11;
	}
	public void setCont11() {
		this.cont11++;
	}
	public void decrementaCont11() {
		this.cont11--;
	}
	public int getCont12() {
		return cont12;
	}
	public void setCont12() {
		this.cont12++;
	}
	public void decrementaCont12() {
		this.cont12--;
	}
	public int getCont13() {
		return cont13;
	}
	public void setCont13() {
		this.cont13++;
	}
	public void decrementaCont13() {
		this.cont13--;
	}
	public int getCont14() {
		return cont14;
	}
	public void setCont14() {
		this.cont14++;
	}
	public void decrementaCont14() {
		this.cont14--;
	}
	public int getCont21() {
		return cont21;
	}
	public void setCont21() {
		this.cont21++;
	}
	public void decrementaCont21() {
		this.cont21--;
	}
	public int getCont22() {
		return cont22;
	}
	public void setCont22() {
		this.cont22++;
	}
	public void decrementaCont22() {
		this.cont22--;
	}
	public int getCont23() {
		return cont23;
	}
	public void setCont23() {
		this.cont23++;
	}
	public void decrementaCont23() {
		this.cont23--;
	}
	public int getCont24() {
		return cont24;
	}
	public void setCont24() {
		this.cont24++;
	}
	public void decrementaCont24() {
		this.cont24--;
	}
	public int getCont31() {
		return cont31;
	}
	public void setCont31() {
		this.cont31++;
	}
	public void decrementaCont31() {
		this.cont31--;
	}
	public int getCont32() {
		return cont32;
	}
	public void setCont32() {
		this.cont32++;
	}
	public void decrementaCont32() {
		this.cont32--;
	}
	public int getCont33() {
		return cont33;
	}
	public void setCont33() {
		this.cont33++;
	}
	public void decrementaCont33() {
		this.cont33--;
	}
	public int getCont34() {
		return cont34;
	}
	public void setCont34() {
		this.cont34++;
	}
	public void decrementaCont34() {
		this.cont34--;
	}
	public int getCont41() {
		return cont41;
	}
	public void setCont41() {
		this.cont41++;
	}
	public void decrementaCont41() {
		this.cont41--;
	}
	public int getCont42() {
		return cont42;
	}
	public void setCont42() {
		this.cont42++;
	}
	public void decrementaCont42() {
		this.cont42--;
	}
	public int getCont43() {
		return cont43;
	}
	public void setCont43() {
		this.cont43++;
	}
	public void decrementaCont43() {
		this.cont43--;
	}
	public int getCont44() {
		return cont44;
	}
	public void setCont44() {
		this.cont44++;
	}
	public void decrementaCont44() {
		this.cont44--;
	}
    public void inizializza(){
    	cont11=0;
    	cont12=0;
    	cont13=0;
    	cont14=0;
    	cont21=0;
    	cont22=0;
    	cont23=0;
    	cont24=0;
    	cont31=0;
    	cont32=0;
    	cont33=0;
    	cont34=0;
    	cont41=0;
    	cont42=0;
    	cont43=0;
    	cont44=0;
    	partite.clear();
    }
 
}
