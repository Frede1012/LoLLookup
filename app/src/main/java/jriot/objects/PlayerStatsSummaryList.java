package jriot.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerStatsSummaryList implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<PlayerStatsSummary> playerStatSummaries;
	private long summonerId;
	public ArrayList<PlayerStatsSummary> getPlayerStatSummaries() {
		return playerStatSummaries;
	}
	public void setOkayerStatSummaries(
			ArrayList<PlayerStatsSummary> playerStatSummaries) {
		this.playerStatSummaries = playerStatSummaries;
	}
	public long getSummonerId() {
		return summonerId;
	}
	public void setSummonerId(long summonerId) {
		this.summonerId = summonerId;
	}
	@Override
	public String toString() {
		return "PlayerStatsSummaryList [playerStatSummaries="
				+ playerStatSummaries + ", summonerId=" + summonerId + "]";
	}

}
