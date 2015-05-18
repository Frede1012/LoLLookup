package com.softstew.lollookup.objects;

public class LookupObject {

	String apiKey;
	Server server;
	String summonerName;
	int season;

	public LookupObject(String apiKey, Server server, String summonerName,
			int season) {
		this.apiKey = apiKey;
		this.server = server;
		this.summonerName = summonerName;
		this.season = season;
	}

	public String getAPIKey() {
		return apiKey;
	}

	public Server getServer() {
		return server;
	}

	public String getSummonerName() {
		return summonerName;
	}

	public int getSeason() {
		return season;
	}

}
