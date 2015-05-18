package com.softstew.lollookup.objects;

import com.softstew.lollookup.adapter.GameAdapter;

public class GamePackage {
	public String queueName;
	public GameAdapter gameAdapter;

	public GamePackage(String queueName, GameAdapter gameAdapter) {
		this.queueName = queueName;
		this.gameAdapter = gameAdapter;
	}
}
