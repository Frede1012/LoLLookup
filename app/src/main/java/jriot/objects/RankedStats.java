package jriot.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class RankedStats implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<ChampionStats> champions;
	private long modifyDate;
	private long summonerId;

	public ChampionStats getCombinedChampStats() {
		for (int i = 0; i < getChampions().size(); i++) {
			if (getChampions().get(i).getId() == 0) {
				return getChampions().get(i);
			}
		}
		return null;
	}

	public ArrayList<ChampionStats> getChampions() {
		return champions;
	}

	public void setChampions(ArrayList<ChampionStats> champions) {
		this.champions = champions;
	}

	public long getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(long modifyDate) {
		this.modifyDate = modifyDate;
	}

	public long getSummonerId() {
		return summonerId;
	}

	public void setSummonerId(long summonerId) {
		this.summonerId = summonerId;
	}

	@Override
	public String toString() {
		return "RankedStats [champions=" + champions + ", modifyDate="
				+ modifyDate + ", summonerId=" + summonerId + "]";
	}

}
