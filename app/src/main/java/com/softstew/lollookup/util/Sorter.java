package com.softstew.lollookup.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import jriot.objects.Game;
import jriot.objects.LeagueEntry;

import com.softstew.lollookup.objects.ListItem;

public class Sorter {

	public static ArrayList<ListItem> sortGamesByDate(ArrayList<Game> games) {
		ArrayList<Date> dates = new ArrayList<Date>();
		for (int i = 0; i < games.size(); i++) {
			dates.add(new Date(games.get(i).getCreateDate()));
		}
		Collections.sort(dates);
		Collections.reverse(dates);
		ArrayList<ListItem> sortedGames = new ArrayList<ListItem>();
		for (int i = 0; i < dates.size(); i++) {
			for (int x = 0; x < games.size(); x++) {
				if (new Date(games.get(x).getCreateDate()).equals(dates.get(i))) {
					sortedGames.add(games.get(x));
				}
			}
		}
		return sortedGames;
	}

	public static ArrayList<ListItem> sortLeagueByLP(ArrayList<LeagueEntry> league) {
		ArrayList<Integer> lp = new ArrayList<Integer>();
		for (int i = 0; i < league.size(); i++) {
			lp.add(league.get(i).getLeaguePoints());
		}
		Collections.sort(lp);
		Collections.reverse(lp);
		ArrayList<ListItem> sortedLeague = new ArrayList<ListItem>();
		for (int i = 0; i < lp.size(); i++) {
			for (int x = 0; x < league.size(); x++) {
				if (league.get(x).getLeaguePoints() == lp.get(i)) {
					sortedLeague.add(league.get(x));
					league.remove(x);
				}
			}
		}
		return sortedLeague;
	}

}
