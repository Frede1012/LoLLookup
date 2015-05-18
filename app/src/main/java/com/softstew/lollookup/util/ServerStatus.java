package com.softstew.lollookup.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.preference.PreferenceManager;

import com.softstew.lollookup.objects.Server;
import com.softstew.util.Logger;

public class ServerStatus {

	public static ArrayList<ServerState> getServerStates(Context context) {
		ArrayList<ServerState> states = new ArrayList<ServerState>();
		int connectTimeout = Integer.parseInt(PreferenceManager
				.getDefaultSharedPreferences(context).getString(
						"connection_connecttimeout", "20"));
		int readTimeout = Integer.parseInt(PreferenceManager
				.getDefaultSharedPreferences(context).getString(
						"connection_readtimeout", "20"));
		for (Server e : Server.values()) {
			try {
				states.add(new ServerState(e.getName(), StateChecker.isOnline(
						e, connectTimeout, readTimeout)));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return states;
	}

	public static class StateChecker {
		@SuppressLint("DefaultLocale")
		public static String isOnline(Server server, int connectTimeout,
				int readTimeout) {
			// if (server == Server.PBE) {
			// return "UNKNOWN";
			// }
			try {
				String req = "http://ll.leagueoflegends.com/pages/launcher/"
						+ server.GetLauncherServerCode().toLowerCase();
				Logger.logDebug(req);

				URL url = new URL(req);

				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();

				// DEFAULT TIMEOUTS = 20 sec
				connection.setConnectTimeout(connectTimeout * 1000);
				connection.setReadTimeout(readTimeout * 1000);
				// connection.setDoOutput(true);
				connection.setRequestMethod("GET");
				connection.setInstanceFollowRedirects(false);

				InputStream is = connection.getInputStream();
				BufferedReader rd = new BufferedReader(
						new InputStreamReader(is));
				String line;
				StringBuffer response = new StringBuffer();

				while ((line = rd.readLine()) != null) {
					response.append(line);
					response.append('\r');
				}
				int serverStatus = Integer.parseInt(response.substring(46, 47));
				if (serverStatus == 1) {
					return "ONLINE";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "UNKNOWN";
			}
			return "OFFLINE";
		}
	}

	public static class ServerState {
		String name;
		String state;

		public ServerState(String name, String state) {
			this.name = name;
			this.state = state;
		}

		public String getName() {
			return name;
		}

		public String getState() {
			return state;
		}
	}

}
