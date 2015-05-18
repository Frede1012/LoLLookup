package com.softstew.lollookup.objects;

import java.text.DecimalFormat;
import java.util.ArrayList;

import jriot.objects.ChampionStats;
import jriot.objects.PlayerStatsSummary;

import com.softstew.lollookup.util.Calculator;

public class Stats {

	public static ArrayList<ListItem> getAll(Summoner sum, boolean ranked) {
		ArrayList<ListItem> statList = new ArrayList<ListItem>();
		statList.addAll(getNormal5v5Stats(sum.getPlayerStats("Unranked")));
		if (ranked) {
			statList.addAll(getRankedStats(sum));
		}
		statList.addAll(getNormal3v3Stats(sum.getPlayerStats("Unranked3x3")));
		statList.addAll(getARAMStats(sum.getPlayerStats("AramUnranked5x5")));
		statList.addAll(getDominionStats(sum.getPlayerStats("OdinUnranked")));
		return statList;
	}

	public static ArrayList<ListItem> getNormal5v5Stats(
			PlayerStatsSummary statsSummary) {
		ArrayList<ListItem> statsList = new ArrayList<ListItem>();
		statsList.add(new ListSection("Normal 5v5"));
		Stat[] stats = {
				new Stat("Wins: ", statsSummary.getWins()),
				new Stat("Kills: ", statsSummary.getAggregatedStats()
						.getTotalChampionKills()),
				new Stat("Assists: ", statsSummary.getAggregatedStats()
						.getTotalAssists()),
				new Stat("Minion Kills: ", statsSummary.getAggregatedStats()
						.getTotalMinionKills()),
				new Stat("Neutral Minion Kills: ", statsSummary
						.getAggregatedStats().getTotalNeutralMinionsKilled()),
				new Stat("Towers Destroyed: ", statsSummary
						.getAggregatedStats().getTotalTurretsKilled()) };
		for (int i = 0; i < stats.length; i++) {
			statsList.add(stats[i]);
		}
		return statsList;
	}

	public static ArrayList<ListItem> getRankedStats(Summoner sum) {
		ArrayList<ListItem> statsList = new ArrayList<ListItem>();
		statsList.add(new ListSection("Ranked"));
		PlayerStatsSummary soloQueueStats = sum.getSoloQueueStats();
		ChampionStats combinedChampStats = sum.getRankedStats()
				.getCombinedChampStats();
		int wins = soloQueueStats.getWins();
		int losses = soloQueueStats.getLosses();

		String elo = ""
				+ Calculator.getApproxElo(sum.getLeague().getTier(), sum
						.getLeagueEntry().getDivision(), sum.getLeagueEntry()
						.getLeaguePoints(), sum.getLeagueEntry()
						.getMiniSeries());

		Stat[] stats = {
				new Stat("Approximate ELO: ", elo),
				new Stat("Wins: ", wins),
				new Stat("Losses: ", losses),
				new Stat("Win %: ", Calculator.getWL(wins, losses,
						new DecimalFormat("#.##"))),
				new Stat("Penta Kills: ", combinedChampStats.getStats()
						.getTotalPentaKills()),
				new Stat("Quadra Kills: ", combinedChampStats.getStats()
						.getTotalQuadraKills()),
				new Stat("Triple Kills: ", combinedChampStats.getStats()
						.getTotalTripleKills()),
				new Stat("Double Kills: ", combinedChampStats.getStats()
						.getTotalDoubleKills()),
				new Stat("Kills: ", combinedChampStats.getStats()
						.getTotalChampionKills()),
				new Stat("Assists: ", combinedChampStats.getStats()
						.getTotalAssists()),
				new Stat("Gold Earned: ", combinedChampStats.getStats()
						.getTotalGoldEarned()),
				new Stat("Turrets Destroyed: ", combinedChampStats.getStats()
						.getTotalTurretsKilled()) };
		for (int i = 0; i < stats.length; i++) {
			statsList.add(stats[i]);
		}
		return statsList;
	}

	public static ArrayList<ListItem> getNormal3v3Stats(
			PlayerStatsSummary statsSummary) {
		ArrayList<ListItem> statsList = new ArrayList<ListItem>();
		statsList.add(new ListSection("Normal 3v3"));
		Stat[] stats = {
				new Stat("Wins: ", statsSummary.getWins()),
				new Stat("Kills: ", statsSummary.getAggregatedStats()
						.getTotalChampionKills()),
				new Stat("Assists: ", statsSummary.getAggregatedStats()
						.getTotalAssists()),
				new Stat("Minion Kills: ", statsSummary.getAggregatedStats()
						.getTotalMinionKills()),
				new Stat("Neutral Minion Kills: ", statsSummary
						.getAggregatedStats().getTotalNeutralMinionsKilled()),
				new Stat("Towers Destroyed: ", statsSummary
						.getAggregatedStats().getTotalTurretsKilled()) };
		for (int i = 0; i < stats.length; i++) {
			statsList.add(stats[i]);
		}
		return statsList;
	}

	public static ArrayList<ListItem> getARAMStats(PlayerStatsSummary statsSummary) {
		ArrayList<ListItem> statsList = new ArrayList<ListItem>();
		statsList.add(new ListSection("ARAM"));
		Stat[] stats = {
				new Stat("Wins: ", statsSummary.getWins()),
				new Stat("Kills: ", statsSummary.getAggregatedStats()
						.getTotalChampionKills()),
				new Stat("Assists: ", statsSummary.getAggregatedStats()
						.getTotalAssists()),
				new Stat("Towers Destroyed: ", statsSummary
						.getAggregatedStats().getTotalTurretsKilled()) };
		for (int i = 0; i < stats.length; i++) {
			statsList.add(stats[i]);
		}
		return statsList;
	}

	public static ArrayList<ListItem> getDominionStats(
			PlayerStatsSummary statsSummary) {
		ArrayList<ListItem> statsList = new ArrayList<ListItem>();
		statsList.add(new ListSection("Dominion"));
		Stat[] stats = {
				new Stat("Wins: ", statsSummary.getWins()),
				new Stat("Kills: ", statsSummary.getAggregatedStats()
						.getTotalChampionKills()),
				new Stat("Assists: ", statsSummary.getAggregatedStats()
						.getTotalAssists()),
				new Stat("Highest Score: ", statsSummary.getAggregatedStats()
						.getMaxTotalPlayerScore()),
				new Stat("Nodes Captured: ", statsSummary.getAggregatedStats()
						.getTotalNodeCapture()),
				new Stat("Nodes Neutralized: ", statsSummary
						.getAggregatedStats().getTotalNodeNeutralize()) };
		for (int i = 0; i < stats.length; i++) {
			statsList.add(stats[i]);
		}
		return statsList;
	}
}
