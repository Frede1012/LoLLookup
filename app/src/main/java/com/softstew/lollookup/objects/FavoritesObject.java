package com.softstew.lollookup.objects;

import java.util.ArrayList;

import com.softstew.lollookup.objects.Favorite;

public class FavoritesObject {

	IconStorage iconStorage;
	ArrayList<Favorite> favorites;

	public FavoritesObject(IconStorage iconStorage,
			ArrayList<Favorite> favorites) {
		this.iconStorage = iconStorage;
		this.favorites = favorites;
	}

	public IconStorage getIconStorage() {
		return iconStorage;
	}

	public ArrayList<Favorite> getFavorites() {
		return favorites;
	}

}
