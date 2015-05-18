package jriot.objects;

import java.io.Serializable;

import jriot.main.JRiotException;

public class Summoner implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private int profileIconId;
	private long revisionDate;
	private long summonerLevel;

	// ERROR HANDLING
	boolean errorBool = false;
	JRiotException error;

	public Summoner(JRiotException error) {
		errorBool = true;
		this.error = error;
	}

	public boolean isError() {
		return errorBool;
	}

	public JRiotException getError() {
		return error;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProfileIconId() {
		return profileIconId;
	}

	public void setProfileIconId(int profileIconId) {
		this.profileIconId = profileIconId;
	}

	public long getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(long revisionDate) {
		this.revisionDate = revisionDate;
	}

	public long getSummonerLevel() {
		return summonerLevel;
	}

	public void setSummonerLevel(long summonerLevel) {
		this.summonerLevel = summonerLevel;
	}

	@Override
	public String toString() {
		return "Summoner [id=" + id + ", name=" + name + ", profileIconId="
				+ profileIconId + ", revisionDate=" + revisionDate
				+ ", summonerLevel=" + summonerLevel + "]";
	}

}
