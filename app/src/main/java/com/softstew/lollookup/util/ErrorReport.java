package com.softstew.lollookup.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.softstew.util.Logger;

import android.content.Context;
import android.preference.PreferenceManager;

public class ErrorReport {
	public static void reportError(Context context, String error) {
		boolean report = PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean("general_errorreporting", true);
		if (report) {
			Logger.logDebug("Sending Error Report");
			try {
				error = error.replace(" ", "%20");
				error = error.replace("\n", "%0A");
				URL url = new URL(
						"http://www.apps.softstew.com/lollookup/error.php?key=qvbJ6z&&msg="
								+ error);
				URLConnection connection = url.openConnection();
				connection.connect();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					Logger.logDebug(inputLine);
				}

				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
