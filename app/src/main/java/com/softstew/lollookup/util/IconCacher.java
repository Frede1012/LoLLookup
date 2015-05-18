package com.softstew.lollookup.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.softstew.lollookup.R;
import com.softstew.lollookup.objects.Icon;
import com.softstew.lollookup.objects.IconStorage;
import com.softstew.lollookup.objects.IconType;
import com.softstew.util.Logger;

public class IconCacher {

	public static void cache(Context context, IconStorage iconStorage) {
		createCache(context);
		for (int i = 0; i < iconStorage.getStorage(IconType.Profile).size(); i++) {
			if (iconStorage.getStorage(IconType.Profile).get(i) != null) {
				if ((iconStorage.getStorage(IconType.Profile).get(i)
						.isPlaceholder() == false)) {
					cacheIcon(IconType.Profile,
							iconStorage.getStorage(IconType.Profile).get(i)
									.getID(),
							iconStorage.getStorage(IconType.Profile).get(i)
									.getDrawable(128, 128), context);
				}
			}
		}

		for (int i = 0; i < iconStorage.getStorage(IconType.Champion).size(); i++) {
			if (iconStorage.getStorage(IconType.Champion).get(i) != null) {
				if ((iconStorage.getStorage(IconType.Champion).get(i)
						.isPlaceholder() == false)) {
					cacheIcon(IconType.Champion,
							iconStorage.getStorage(IconType.Champion).get(i)
									.getID(),
							iconStorage.getStorage(IconType.Champion).get(i)
									.getDrawable(128, 128), context);
				}
			}
		}

	}

	public static IconStorage getCachedIcons(Context context,
			IconStorage iconStorage) {
		ArrayList<Icon> profileStorage = new ArrayList<Icon>();
		ArrayList<Icon> profileIcons = iconStorage.getStorage(IconType.Profile);
		for (int i = 0; i < profileIcons.size(); i++) {
			profileStorage.add(loadCachedIcon(IconType.Profile, profileIcons
					.get(i).getID(), context));
		}
		ArrayList<Icon> championStorage = new ArrayList<Icon>();
		ArrayList<Icon> championIcons = iconStorage
				.getStorage(IconType.Champion);
		for (int i = 0; i < championIcons.size(); i++) {
			championStorage.add(loadCachedIcon(IconType.Champion, championIcons
					.get(i).getID(), context));
		}
		return new IconStorage(profileStorage, championStorage);
	}

	static void createCache(Context context) {
		File file = new File(context.getFilesDir() + "/IconCache");
		file.mkdir();
	}

	public static void deleteCache(Context context) {
		File file = new File(context.getFilesDir() + "/IconCache");
		delete(file);
	}

	static void delete(File fileOrDirectory) {
		if (fileOrDirectory.isDirectory())
			for (File child : fileOrDirectory.listFiles()) {
				delete(child);
			}
		fileOrDirectory.delete();
	}

	public static String countIcons(Context context) {
		File file = new File(context.getFilesDir() + "/IconCache");
		int fileSize = 0;
		int count = 0;
		if (file.exists()) {
			if (file.isDirectory()) {
				for (File child : file.listFiles()) {
					fileSize += child.length();
					count++;
				}
			}
		}
		return "Total Icons: " + count + " (" + size(fileSize) + ")";
	}

	static String size(long size) {
		String hrSize = "";

		double b = size;
		double k = size / 1024.0;
		double m = ((size / 1024.0) / 1024.0);

		DecimalFormat dec = new DecimalFormat("0.00");

		if (m > 1) {
			hrSize = dec.format(m).concat(" MB");
		} else if (k > 1) {
			hrSize = dec.format(k).concat(" KB");
		} else {
			hrSize = dec.format(b).concat(" Bytes");
		}

		return hrSize;

	}

	public static void cacheIcon(IconType type, int id, Drawable drawable,
			Context context) {
		if (drawable != null) {
			FileOutputStream fos;
			String name = "";
			if (type == IconType.Champion) {
				name = "champion" + id;
			} else {
				name = "profileicon" + id;
			}
			try {
				File filePath = new File(context.getFilesDir() + "/IconCache/"
						+ name + ".png");
				if (filePath.exists() == false) {
					Logger.logDebug("Caching Icon: " + name);
					fos = new FileOutputStream(filePath);
					Bitmap b = ((BitmapDrawable) drawable).getBitmap();
					b.compress(Bitmap.CompressFormat.PNG, 0, fos);
					fos.flush();
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static Icon loadCachedIcon(IconType type, int id, Context context) {
		String name = "";
		if (type == IconType.Champion) {
			name = "champion" + id;
		} else {
			name = "profileicon" + id;
		}
		try {
			File filePath = new File(context.getFilesDir() + "/IconCache/"
					+ name + ".png");
			if (filePath.exists()) {
				FileInputStream fi = new FileInputStream(filePath);
				Bitmap b = BitmapFactory.decodeStream(fi);
				fi.close();
				return new Icon(false, type, id, new BitmapDrawable(b));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Icon(true, type, id, context.getResources().getDrawable(
				R.drawable.unknown));
	}

	public static boolean exists(IconType type, int id, Context context) {
		String name = "";
		if (type == IconType.Champion) {
			name = "champion" + id;
		} else {
			name = "profileicon" + id;
		}
		Logger.logDebug("Looking for: " + name + ".png");
		return new File(context.getFilesDir() + "/IconCache/" + name + ".png")
				.exists();
	}
}
