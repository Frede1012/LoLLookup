package com.softstew.lollookup.util;

import java.text.DecimalFormat;

import jriot.objects.ChampionStats;
import jriot.objects.MiniSeries;
import jriot.objects.RankedStats;

public class Calculator {

	public static String getPlayerKDA(RankedStats stats,
			DecimalFormat decimalFormat) {
		ChampionStats combinedStats = stats.getCombinedChampStats();
		String kda = decimalFormat.format((float) combinedStats.getStats()
				.getTotalChampionKills()
				/ (float) combinedStats.getStats().getTotalSessionsPlayed())
				+ "/"
				+ decimalFormat.format((float) combinedStats.getStats()
						.getTotalDeathsPerSession()
						/ (float) combinedStats.getStats()
								.getTotalSessionsPlayed())
				+ "/"
				+ decimalFormat.format((float) combinedStats.getStats()
						.getTotalAssists()
						/ (float) combinedStats.getStats()
								.getTotalSessionsPlayed());
		return kda;
	}

	public static String getChampKDA(ChampionStats champ,
			DecimalFormat decimalFormat) {
		float matches = champ.getStats().getTotalSessionsPlayed();
		return decimalFormat.format(((float) champ.getStats()
				.getTotalChampionKills() / (float) matches))
				+ "/"
				+ decimalFormat.format(((float) champ.getStats()
						.getTotalDeathsPerSession() / (float) matches))
				+ "/"
				+ decimalFormat.format(((float) champ.getStats()
						.getTotalAssists() / (float) matches));
	}

	public static String getAverageMinions(float minions, float sessionsPlayed,
			DecimalFormat decimalFormat) {
		return decimalFormat.format((float) minions / (float) sessionsPlayed);
	}

	public static String getWL(int wins, int losses, DecimalFormat decimalFormat) {
		float wl = (float) wins / ((float) wins + (float) losses);
		wl = wl * 100;
		return decimalFormat.format(wl) + "%";
	}

	public static int getApproxElo(String tier, String rank, int lp,
			MiniSeries miniSeries) {
		int tierValue = 0;
		if (tier.equals("UNRANKED")) {
			tierValue = 0;
		} else if (tier.equals("BRONZE")) {
			tierValue = 1;
		} else if (tier.equals("SILVER")) {
			tierValue = 2;
		} else if (tier.equals("GOLD")) {
			tierValue = 3;
		} else if (tier.equals("PLATINUM")) {
			tierValue = 4;
		} else if (tier.equals("DIAMOND")) {
			tierValue = 5;
		} else if (tier.equals("CHALLENGER")) {
			tierValue = 6;
		}
		double totalTierValue = tierValue * 350;
		int rankValue = 0;
		if (rank.equals("V")) {
			rankValue = 0;
		} else if (rank.equals("IV")) {
			rankValue = 1;
		} else if (rank.equals("III")) {
			rankValue = 2;
		} else if (rank.equals("II")) {
			rankValue = 3;
		} else if (rank.equals("I")) {
			rankValue = 4;
		}
		double totalRankValue = rankValue * 70;
		double elo = 450;
		elo += totalTierValue;
		if (tier.equals("CHALLENGER") == false) {
			elo += totalRankValue;
		}
		elo += 0.5 * lp;
		if (miniSeries != null)
			elo += 20.0 * miniSeries.getWins() / miniSeries.getTarget();
		return (int) elo;
	}

	public static String convertGold(int gold, DecimalFormat decimalFormat) {
		float goldFloat = gold;
		float converted = goldFloat / 1000;
		return decimalFormat.format(converted) + "k";
	}
}
