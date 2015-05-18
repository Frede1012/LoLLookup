package jriot.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class League implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<LeagueEntry> entries;
	private String name;
	private String participantId;
	private String queue;
	private String tier;

	public ArrayList<LeagueEntry> getEntries() {
		return entries;
	}

	// CUSTOM
	public ArrayList<LeagueEntry> getEntriesByRank(String rank) {
		ArrayList<LeagueEntry> oldEntries = getEntries();
		ArrayList<LeagueEntry> entries = new ArrayList<LeagueEntry>();
		for (int i = 0; i < oldEntries.size(); i++) {
			if (oldEntries.get(i).getDivision().equals(rank)) {
				entries.add(oldEntries.get(i));
			}
		}
		return entries;
	}

	public void setEntries(ArrayList<LeagueEntry> entries) {
		this.entries = entries;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public String getTier() {
		return tier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	public String getParticipantId() {
		return participantId;
	}

	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

	@Override
	public String toString() {
		return "League [entries=" + entries + ", name=" + name
				+ ", participantId=" + participantId + ", queue=" + queue
				+ ", tier=" + tier + "]";
	}

}
