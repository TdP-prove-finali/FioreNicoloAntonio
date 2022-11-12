package it.polito.tdp.ChampionsLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.ChampionsLeague.model.Federazione;
import it.polito.tdp.ChampionsLeague.model.Squadra;
import it.polito.tdp.ChampionsLeague.model.Stadio;





public class ChampionsLeagueDao {
	
	public void getAllSquadre(Map<String,Squadra> idMap){
		String sql = "SELECT s.Nome_Squadra,s.Campionato,s.Nome_Stadio,r.Posizione,s.OVR,s.ATT,s.MID,s.DEF,s.FASCIA "
				+ "FROM squadre s,ranking r "
				+ "WHERE s.Nome_Squadra=r.Nome_Squadra ";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
                if(!idMap.containsKey(res.getString("Nome_Squadra"))) {
				Squadra squadra = new Squadra(res.getString("s.Nome_Squadra"),res.getString("s.Campionato"),res.getString("s.Nome_Stadio"),res.getInt("r.Posizione"),res.getInt("s.OVR"),res.getInt("s.ATT"),res.getInt("s.MID"),res.getInt("s.DEF"),res.getInt("s.FASCIA"));
				idMap.put(squadra.getNome(), squadra);
				
                }
			}
			res.close();
			st.close();
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	
		
		public List<Stadio> getStadiASquadrePartecipanti(Map<String,Stadio> stadi){
			String sql = "SELECT st.Nome_Stadio,st.Capienza,st.`Città`,st.Anno_Costruzione,st.Prezzo_biglietto "
					+ "FROM stadi st,squadre sq "
					+ "WHERE st.Nome_Stadio=sq.Nome_Stadio ";
			List<Stadio> result = new ArrayList<Stadio>();
			Connection conn = DBConnect.getConnection();

			try {
				PreparedStatement st = conn.prepareStatement(sql);
				ResultSet res = st.executeQuery();
				while (res.next()) {
                    Stadio stadio;// = new Stadio(res.getString("st.Nome_Stadio"),res.getInt("st.Capienza"),res.getString("st.Città"),res.getInt("st.Anno_Costruzione"),res.getInt("st.Prezzo_biglietto"));
                    if(!stadi.containsKey(res.getString("st.Nome_Stadio"))) { 
                    	 stadio = new Stadio(res.getString("st.Nome_Stadio"),res.getInt("st.Capienza"),res.getString("st.Città"),res.getInt("st.Anno_Costruzione"),res.getInt("st.Prezzo_biglietto"));
                    stadi.put(stadio.getNome_Stadio(), stadio);
                    }
                    else {
                    	stadio=stadi.get(res.getString("st.Nome_Stadio"));
                    }
					
					result.add(stadio);
				}
				res.close();
				st.close();
				conn.close();
				return result;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		public List<Squadra> getSquadrePrimaFascia(Map<String, Squadra> idMap){
			String sql = "SELECT * "
					+ "FROM squadre "
					+ "WHERE FASCIA=1 ";
			List<Squadra> result = new ArrayList<Squadra>();
			Connection conn = DBConnect.getConnection();

			try {
				PreparedStatement st = conn.prepareStatement(sql);
				ResultSet res = st.executeQuery();
				while (res.next()) {

					//Squadra squadra = new Squadra(res.getString("Nome_Squadra"),res.getString("Campionato"),res.getString("Nome_Stadio"),res.getInt("OVR"),res.getInt("ATT"),res.getInt("MID"),res.getInt("DEF"),res.getInt("FASCIA"));
					Squadra squadra =idMap.get(res.getString("Nome_Squadra"));
					result.add(squadra);
				}
				res.close();
				st.close();
				conn.close();
				return result;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		public List<Squadra> getSquadreSecondaFascia(Map<String, Squadra> idMap){
			String sql = "SELECT * "
					+ "FROM squadre "
					+ "WHERE FASCIA=2 ";
			List<Squadra> result = new ArrayList<Squadra>();
			Connection conn = DBConnect.getConnection();

			try {
				PreparedStatement st = conn.prepareStatement(sql);
				ResultSet res = st.executeQuery();
				while (res.next()) {

					Squadra squadra =idMap.get(res.getString("Nome_Squadra"));
					result.add(squadra);
				}
				res.close();
				st.close();
				conn.close();
				return result;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		public List<Squadra> getSquadreTerzaFascia(Map<String, Squadra> idMap){
			String sql = "SELECT * "
					+ "FROM squadre "
					+ "WHERE FASCIA=3 ";
			List<Squadra> result = new ArrayList<Squadra>();
			Connection conn = DBConnect.getConnection();

			try {
				PreparedStatement st = conn.prepareStatement(sql);
				ResultSet res = st.executeQuery();
				while (res.next()) {

					Squadra squadra =idMap.get(res.getString("Nome_Squadra"));
					result.add(squadra);
				}
				res.close();
				st.close();
				conn.close();
				return result;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		public List<Squadra> getSquadreQuartaFascia(Map<String, Squadra> idMap){
			String sql = "SELECT * "
					+ "FROM squadre "
					+ "WHERE FASCIA=4 ";
			List<Squadra> result = new ArrayList<Squadra>();
			Connection conn = DBConnect.getConnection();

			try {
				PreparedStatement st = conn.prepareStatement(sql);
				ResultSet res = st.executeQuery();
				while (res.next()) {
					Squadra squadra =idMap.get(res.getString("Nome_Squadra"));
					result.add(squadra);
				}
				res.close();
				st.close();
				conn.close();
				return result;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public void getAllFederazioni(Map<String,Federazione> idFederazioni){
			String sql = "SELECT DISTINCT r.Campionato,r.Federazione "
					+ "FROM ranking r ";
			
			Connection conn = DBConnect.getConnection();

			try {
				PreparedStatement st = conn.prepareStatement(sql);
				ResultSet res = st.executeQuery();
				while (res.next()) {

					Federazione federazione = new Federazione(res.getString("r.Campionato"),res.getInt("r.Federazione"));
					idFederazioni.put(federazione.getCampionato(), federazione);
				}
				res.close();
				st.close();
				conn.close();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
		}
		
		
}
	
