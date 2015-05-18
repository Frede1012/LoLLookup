package com.softstew.lollookup.objects;

public enum Queue {
	NORMAL("NORMAL", "Normal 5v5"), NORMAL_3x3("NORMAL_3x3", "Normal 3v3"), ARAM_UNRANKED_5x5(
			"ARAM_UNRANKED_5x5", "Howling Abyss"), FIRSTBLOOD_2x2(
			"FIRSTBLOOD_2x2", "First Blood 2v2"), FIRSTBLOOD_1x1(
			"FIRSTBLOOD_1x1", "First Blood 1v1"), RANKED_TEAM_5x5(
			"RANKED_TEAM_5x5", "Ranked Team 5v5"), RANKED_TEAM_3x3(
			"RANKED_TEAM_3x3", "Ranked Team 3v3"), RANKED_SOLO_5x5(
			"RANKED_SOLO_5x5", "Ranked Solo"), CAP_5x5("CAP_5x5",
			"Team Builder"), ODIN_UNRANKED("ODIN_UNRANKED", "Dominion"), BOT(
			"BOT", "Co-op vs AI"), NONE("NONE", "Custom");

	String raw;
	String name;

	Queue(String raw, String name) {
		this.raw = raw;
		this.name = name;
	}

	public String getRaw() {
		return raw;
	}

	public String getName() {
		return name;
	}
}
