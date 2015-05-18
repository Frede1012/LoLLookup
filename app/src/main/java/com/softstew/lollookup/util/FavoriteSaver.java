package com.softstew.lollookup.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.softstew.lollookup.objects.Favorite;

import android.content.Context;

public class FavoriteSaver {

	public static void saveFavorites(Context context,
			ArrayList<Favorite> favorites) {
		FileOutputStream fos;
		try {
			fos = context.openFileOutput("Favorites", Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(favorites);
			os.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Favorite> loadFavorites(Context context) {
		FileInputStream fis;
		try {
			fis = context.openFileInput("Favorites");
			ObjectInputStream is = new ObjectInputStream(fis);
			ArrayList<Favorite> favorites = (ArrayList<Favorite>) is
					.readObject();
			is.close();
			return favorites;
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		return null;
	}

}
