package jriot.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class RecentGames implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Game> games;
	private long summonerId;
	
	
	public ArrayList<Game> getGames() {
		return games;
	}
	public void setGames(ArrayList<Game> games) {
		this.games = games;
	}
	public long getSummonerId() {
		return summonerId;
	}
	public void setSummonerId(long summonerId) {
		this.summonerId = summonerId;
	}
	@Override
	public String toString() {
		return "RecentGames [games=" + games + ", summonerId=" + summonerId
				+ "]";
	}
	
	
}
