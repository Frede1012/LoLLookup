package com.softstew.lollookup.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import jriot.main.JRiotException;
import jriot.objects.League;
import jriot.objects.LeagueEntry;
import jriot.objects.PlayerStatsSummary;
import jriot.objects.PlayerStatsSummaryList;
import jriot.objects.RankedStats;
import jriot.objects.RecentGames;
import android.content.Context;

public class Summoner implements Serializable {

	private static final long serialVersionUID = 1L;

	// ERROR HANDLING
	boolean errorBool = false;
	JRiotException error;

	IconStorage iconStorage;
	Context context;
	jriot.objects.Summoner summoner;
	PlayerStatsSummaryList playerStats;
	RankedStats rankedStats;
	League league;
	ArrayList<LeagueSummoner> leagueSummoners;
	Server server;
	RecentGames recentGames;

	public Summoner(JRiotException error) {
		errorBool = true;
		this.error = error;
	}

	public Summoner(IconStorage iconStorage, jriot.objects.Summoner summoner,
			PlayerStatsSummaryList playerStats,
			RankedStats rankedStats, League league,
			ArrayList<LeagueSummoner> leagueSummoners, Server server,
			RecentGames recentGames) {
		this.iconStorage = iconStorage;
		this.summoner = summoner;
		this.playerStats = playerStats;
		this.rankedStats = rankedStats;
		this.league = league;
		this.leagueSummoners = leagueSummoners;
		this.server = server;
		this.recentGames = recentGames;
	}

	public IconStorage getIconStorage() {
		return iconStorage;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public jriot.objects.Summoner getSummonerObject() {
		return summoner;
	}

	public PlayerStatsSummaryList getPlayerStatsList() {
		return playerStats;
	}

	public PlayerStatsSummary getPlayerStats(String queue) {
		for (int i = 0; i < getPlayerStatsList().getPlayerStatSummaries()
				.size(); i++) {
			if (getPlayerStatsList().getPlayerStatSummaries().get(i)
					.getPlayerStatSummaryType().equals(queue)) {
				return getPlayerStatsList().getPlayerStatSummaries().get(i);
			}
		}
		return null;
	}

	public PlayerStatsSummary getSoloQueueStats() {
		if (getPlayerStats("RankedSolo5x5") != null) {
			return getPlayerStats("RankedSolo5x5");
		}
		return null;
	}

	public RankedStats getRankedStats() {
		return rankedStats;
	}

	public League getLeague() {
		return league;
	}

	public ArrayList<LeagueSummoner> getLeagueSummoners() {
		return leagueSummoners;
	}

	public LeagueEntry getLeagueEntry() {
		if (getLeague() != null) {
			List<LeagueEntry> leagueItems = getLeague().getEntries();
			for (LeagueEntry leagueItem : leagueItems) {
				if (Long.parseLong(leagueItem.getPlayerOrTeamId()) == summoner
						.getId()) {
					return leagueItem;
				}

			}
		}
		return null;
	}

	public Server getServer() {
		return server;
	}

	public RecentGames getRecentGames() {
		return recentGames;
	}

	public boolean isError() {
		return errorBool;
	}

	public JRiotException getError() {
		return error;
	}

}
