package com.softstew.lollookup.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;

import com.softstew.lollookup.R;
import com.softstew.lollookup.objects.Icon;
import com.softstew.lollookup.objects.IconType;
import com.softstew.util.Logger;

public class IconLoader {

	public static boolean iconExists(Context context, IconType type, int id) {
		return IconCacher.exists(type, id, context);
	}

	public static Icon getChampionIcon(Context context, int id) {
		if (iconExists(context, IconType.Champion, id) == false) {
			boolean placeholder = false;
			Drawable d = null;
			boolean loadImages = PreferenceManager.getDefaultSharedPreferences(
					context).getBoolean("general_loadimages", true);
			if (loadImages == true) {
				try {
					String address = "http://apps.softstew.com/lollookup/icons/champions/champion"
							+ id + ".png";
					Logger.logDebug("Loading Icon: " + address);
					URL url;
					try {
						url = new URL(address);
						InputStream content = (InputStream) url.getContent();
						d = Drawable.createFromStream(content, "src");
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
			if (d == null) {
				if (loadImages == true) {
					Logger.logError("Champion Icon " + id + " not found",
							context);
					ErrorReport.reportError(context, "Champion Icon " + id
							+ " not found");
				}
				d = context.getResources().getDrawable(R.drawable.unknown);
				placeholder = true;
			}
			return new Icon(placeholder, IconType.Champion, id, d);
		}
		return new Icon(false, IconType.Champion, id);
	}

	public static Icon getProfileIcon(Context context, int id) {
		if (iconExists(context, IconType.Profile, id) == false) {
			boolean placeholder = false;
			Drawable d = null;
			boolean loadImages = PreferenceManager.getDefaultSharedPreferences(
					context).getBoolean("general_loadimages", true);
			if (loadImages == true) {
				try {
					String address = "http://apps.softstew.com/lollookup/icons/profile/profileIcon"
							+ id + ".png";
					Logger.logDebug("Loading Icon: " + address);
					URL url;
					try {
						url = new URL(address);
						InputStream content = (InputStream) url.getContent();
						d = Drawable.createFromStream(content, "src");
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
			if (d == null) {
				if (loadImages == true) {
					Logger.logError("Profile Icon " + id + " not found",
							context);
				}
				d = context.getResources().getDrawable(R.drawable.unknown);
				placeholder = true;
			}
			return new Icon(placeholder, IconType.Profile, id, d);
		}
		return new Icon(false, IconType.Champion, id);
	}

}
