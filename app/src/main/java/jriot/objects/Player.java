package jriot.objects;

import java.io.Serializable;

public class Player implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int championId;
	private long summonerId;
	private int teamId;

	public int getChampionId() {
		return championId;
	}

	public void setChampionId(int championId) {
		this.championId = championId;
	}

	public long getSummonerId() {
		return summonerId;
	}

	public void setSummonerId(long summonerId) {
		this.summonerId = summonerId;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	@Override
	public String toString() {
		return "Player [championId=" + championId + ", summonerId="
				+ summonerId + ", teamId=" + teamId + "]";
	}
}
