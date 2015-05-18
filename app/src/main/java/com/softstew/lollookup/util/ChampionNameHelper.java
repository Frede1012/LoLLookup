package com.softstew.lollookup.util;

import com.google.gson.Gson;

import jriot.main.ApiCaller;
import jriot.main.JRiotException;

public class ChampionNameHelper {

	public static String getName(ApiCaller caller, Gson gson, String key,
			Long id) {
		try {
			String call = "https://na.api.pvp.net/api/lol/static-data/na/v1.2/champion/"
					+ id + "?api_key=" + key;
			String response = caller.request(call);
			return gson.fromJson(response, ChampionStatic.class).name;
		} catch (JRiotException e) {
			e.printStackTrace();
			return null;
		}
	}
}

class ChampionStatic {
	public String name;
}