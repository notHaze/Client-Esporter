package com.esporter.both.data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import com.esporter.both.types.Types;
import com.esporter.both.types.TypesGame;
import com.esporter.both.types.TypesRanking;
import com.esporter.both.types.TypesStable;
import com.esporter.both.types.TypesTeam;
import com.esporter.both.types.TypesTournament;

public class Data implements Serializable, Types {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6632461413087395187L;
	private volatile HashMap<Integer, TypesStable> stables = new HashMap<>();
	private volatile HashMap<Integer, TypesTournament> calendar = new HashMap<>();
	private volatile HashMap<Integer, TypesRanking> ranking = new HashMap<>();
	private volatile HashMap<Integer, TypesTeam> teams = new HashMap<>();
	
	public HashMap<Integer, TypesTournament> getCalendar() {
		return calendar;
	}
	
	public HashMap<Integer, TypesRanking> getRanking() {
		return ranking;
	}
	
	public HashMap<Integer, TypesStable> getStables() {
		return stables;
	}
	
	public HashMap<Integer, TypesTeam> getTeams() {
		return teams;
	}
	
	
	
	public void setCalendar(HashMap<Integer, TypesTournament> calendar) {
		this.calendar = calendar;
	}
	
	public void setRanking(HashMap<Integer, TypesRanking> ranking) {
		this.ranking = ranking;
	}
	
	public void setStables(HashMap<Integer, TypesStable> stables) {
		this.stables = stables;
	}
	
	public ArrayList<TypesStable> listStables() {
		Collection<TypesStable> values;
		synchronized (this) {
			values = this.stables.values();
		}
		ArrayList<TypesStable> listOfValues = new ArrayList<>(values);
		Collections.sort(listOfValues);
		return listOfValues;
	}
	
	public ArrayList<TypesTournament> listSortedTournaments() {
		Collection<TypesTournament> values;
		synchronized (this) {
			values = this.calendar.values();
		}
		ArrayList<TypesTournament>  listOfTournamentInfo = new ArrayList<>(values);
		Collections.sort(listOfTournamentInfo);
		return listOfTournamentInfo;
	}
	
	public ArrayList<TypesTournament> listFilteredTournament(Timestamp date, TypesGame game) {
		ArrayList<TypesTournament>  listOfTournamentInfo = listSortedTournaments();
		ArrayList<TypesTournament> sortedFiltered = new ArrayList<>();
		if (date!=null && game!=null) {
			for (TypesTournament tournament : listOfTournamentInfo) {
				if (tournament.getRegisterDate().compareTo(date)==0 && tournament.getGame().compareTo(game)==0) {
					sortedFiltered.add(tournament);
				}
			}
		} else if (date!=null && game == null) {
			for (TypesTournament tournament : listOfTournamentInfo) {
				if (tournament.getRegisterDate().compareTo(date)==0) {
					sortedFiltered.add(tournament);
				}
			}
		} else if (game!=null && date == null) {
			for (TypesTournament tournament : listOfTournamentInfo) {
				if (tournament.getGame().compareTo(game)==0) {
					sortedFiltered.add(tournament);
				}
			}
		} else {
			return listOfTournamentInfo;
		}
		
		return sortedFiltered;
	}
	
}
