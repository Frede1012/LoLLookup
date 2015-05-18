package com.softstew.lollookup.objects;

import java.util.ArrayList;

import jriot.main.JRiotException;
import jriot.objects.Champion;

public class Champs {

	boolean errorBool = false;
	JRiotException error;

	IconStorage iconStorage;
	ArrayList<Champion> champs;

	public Champs(JRiotException error) {
		errorBool = true;
		this.error = error;
	}

	public Champs(IconStorage iconStorage, ArrayList<Champion> champs) {
		this.iconStorage = iconStorage;
		this.champs = champs;
	}

	public boolean isError() {
		return errorBool;
	}

	public JRiotException getError() {
		return error;
	}

	public IconStorage getIconStorage() {
		return iconStorage;
	}

	public ArrayList<Champion> getChampions() {
		return champs;
	}

}
