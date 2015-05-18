package com.softstew.lollookup.objects;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.softstew.lollookup.adapter.ChampRankedStatsAdapter;
import com.softstew.lollookup.adapter.LeagueAdapter;
import com.softstew.lollookup.adapter.MatchAdapter;

public class SummonerInfoPackage {

	Drawable icon;
	Boolean rankedBool;
	String name;
	String level;
	String ranked;
	String kda;
	Server server;
	ArrayList<ListItem> stats;
	ArrayList<Tab> tabs;
	GamePackage game;
	LeagueAdapter leagueAdapter;
	ChampRankedStatsAdapter champAdapter;
	MatchAdapter historyAdapter;

	public SummonerInfoPackage(Drawable icon, Boolean rankedBool, String name,
			String level, String ranked, String kda, Server server,
			ArrayList<Tab> tabs, GamePackage game, ArrayList<ListItem> stats,
			LeagueAdapter leagueAdapter, ChampRankedStatsAdapter champAdapter,
			MatchAdapter historyAdapter) {
		this.icon = icon;
		this.rankedBool = rankedBool;
		this.name = name;
		this.level = level;
		this.ranked = ranked;
		this.kda = kda;
		this.server = server;
		this.tabs = tabs;
		this.game = game;
		this.stats = stats;
		this.leagueAdapter = leagueAdapter;
		this.champAdapter = champAdapter;
		this.historyAdapter = historyAdapter;
	}

	public Drawable getIcon() {
		return icon;
	}

	public Boolean isRanked() {
		return rankedBool;
	}

	public String getName() {
		return name;
	}

	public String getLevel() {
		return level;
	}

	public String getRanked() {
		return ranked;
	}

	public String getKDA() {
		return kda;
	}

	public Server getServer() {
		return server;
	}

	public ArrayList<ListItem> getStats() {
		return stats;
	}

	public ArrayList<Tab> getTabs() {
		return tabs;
	}

	public Boolean showLeague() {
		for (Tab tab : getTabs()) {
			if (tab.equals(Tab.League)) {
				return true;
			}
		}
		return false;
	}

	public Boolean showChampions() {
		for (Tab tab : getTabs()) {
			if (tab.equals(Tab.Champions)) {
				return true;
			}
		}
		return false;
	}

	public Boolean showGame() {
		for (Tab tab : getTabs()) {
			if (tab.equals(Tab.Game)) {
				return true;
			}
		}
		return false;
	}

	public LeagueAdapter getLeagueAdapter() {
		return leagueAdapter;
	}

	public ChampRankedStatsAdapter getChampRankedStatsAdapter() {
		return champAdapter;
	}

	public MatchAdapter getHistoryAdapter() {
		return historyAdapter;
	}

	public GamePackage getGame() {
		return game;
	}

}
