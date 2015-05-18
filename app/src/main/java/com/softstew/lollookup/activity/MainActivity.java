package com.softstew.lollookup.activity;

import java.util.ArrayList;
import java.util.Map;

import jriot.main.JRiot;
import jriot.main.JRiotException;
import jriot.objects.ChampionList;
import jriot.objects.ChampionStats;
import jriot.objects.League;
import jriot.objects.LeagueEntry;
import jriot.objects.PlayerStatsSummaryList;
import jriot.objects.RankedStats;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.progressbutton.ProgressButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.softstew.lollookup.R;
import com.softstew.lollookup.adapter.FavoriteListViewAdapter;
import com.softstew.lollookup.adapter.FreeChampionAdapter;
import com.softstew.lollookup.adapter.ServerListViewAdapter;
import com.softstew.lollookup.objects.Champs;
import com.softstew.lollookup.objects.Favorite;
import com.softstew.lollookup.objects.FavoritesObject;
import com.softstew.lollookup.objects.IconStorage;
import com.softstew.lollookup.objects.IconType;
import com.softstew.lollookup.objects.LeagueSummoner;
import com.softstew.lollookup.objects.LookupObject;
import com.softstew.lollookup.objects.Server;
import com.softstew.lollookup.objects.Summoner;
import com.softstew.lollookup.util.EnumHelper;
import com.softstew.lollookup.util.FavoriteSaver;
import com.softstew.lollookup.util.IconCacher;
import com.softstew.lollookup.util.ServerStatus;
import com.softstew.lollookup.util.ServerStatus.ServerState;
import com.softstew.util.BuildDate;
import com.softstew.util.Config;
import com.softstew.util.Logger;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

@SuppressLint({ "NewApi", "DefaultLocale" })
public class MainActivity extends Activity {

	TabHost tabHost;
	private String apiKey = "INSERT_API_KEY_HERE";
	Boolean resumeRan = false;
	Boolean isGetting = false;
	Boolean champsLoaded = false;
	Boolean serversLoaded = false;
	Context context;

	Boolean userServerRefresh = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		context = this;

		if (Config.ADS) {
			AdView adView = (AdView) findViewById(R.id.main_adView);
			AdRequest adRequest = new AdRequest.Builder().addTestDevice(
					AdRequest.DEVICE_ID_EMULATOR).build();
			adView.loadAd(adRequest);
			if (Config.DEBUG) {
				// adRequest.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
			}
			adView.loadAd(adRequest);
		}

		tabHost = (TabHost) findViewById(R.id.main_tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("lookup").setIndicator("Lookup")
				.setContent(R.id.main_lookupTab));
		tabHost.addTab(tabHost.newTabSpec("favorites")
				.setIndicator("Favorites").setContent(R.id.main_favorites));
		tabHost.addTab(tabHost.newTabSpec("f2pChamps")
				.setIndicator("F2P Champions")
				.setContent(R.id.main_freeChampsTab));
		tabHost.addTab(tabHost.newTabSpec("servers")
				.setIndicator("Server Status").setContent(R.id.main_serverTab));
		tabHost.setCurrentTab(0);
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				if (tabId.equals("f2pChamps")) {
					if (!champsLoaded) {
						new LoadF2PChampsTask().execute(context);
					}
				} else if (tabId.equals("servers")) {
					if (!serversLoaded) {
						new LoadServerStatusTask().execute(context);
					}
				}
			}

		});

		Button findBtn = (Button) findViewById(R.id.findBtn);
		findBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText summonerNameText = (EditText) findViewById(R.id.summonerEditText);
				Spinner serverSpinner = (Spinner) findViewById(R.id.serverSpinner);
				if (summonerNameText.getText().length() > 0
						&& isGetting == false) {
					String server = serverSpinner.getSelectedItem().toString();
					Logger.logDebug("Server: " + server);
					String summonerName = summonerNameText.getText().toString();
					Logger.logDebug("Summoner: " + summonerName);
					int season = Integer.parseInt(PreferenceManager
							.getDefaultSharedPreferences(context).getString(
									"general_season", "4"));
					Logger.logDebug("Season: " + season);
					new LookupTask().execute(new LookupObject(apiKey,
							EnumHelper.getServerEnumByString(server),
							summonerName, season));
				}
			}

		});

		ProgressButton checkBtn = (ProgressButton) findViewById(R.id.checkBtn);
		checkBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText summonerNameText = (EditText) findViewById(R.id.summonerEditText);
				new CheckTask().execute(new String[] { apiKey,
						summonerNameText.getText().toString() });
			}

		});

		PullToRefreshListView serverListView = (PullToRefreshListView) findViewById(R.id.main_serverListView);
		serverListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new LoadServerStatusTask().execute(context);
			}
		});
		Spinner serverSpinner = (Spinner) findViewById(R.id.serverSpinner);
		String[] servers = getResources().getStringArray(R.array.servers);
		String server = PreferenceManager.getDefaultSharedPreferences(context)
				.getString("general_server", "NA");
		for (int i = 0; i < servers.length; i++) {
			if (servers[i].equals(server)) {
				serverSpinner.setSelection(i);
			}
		}
		new LoadFavoritesTask().execute(context);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (resumeRan == false) {
			resumeRan = true;
			return;
		}
		new LoadFavoritesTask().execute(context);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.about:
			final AlertDialog alertDialog = new AlertDialog.Builder(context)
					.create();

			alertDialog.setTitle("About");

			PackageInfo pInfo = null;
			try {
				pInfo = context.getPackageManager().getPackageInfo(
						context.getPackageName(), 0);
			} catch (NameNotFoundException e) {
			}

			String appname = "LoL Lookup";
			String version = "Version: " + pInfo.versionName;
			String buildDate = "Compiled: " + BuildDate.getBuildDate(context);
			String credits = "\nProgramming: Frederik Bolding"
					+ "\n"
					+ "Graphics: Andreas Thomsen"
					+ "\n\n"
					+ "Uses following libraries: \nJRiot by 't-sauer' (Modified)\nGson by Google\nPullToRefresh-ListView by 'erikwt'\nPrettyTime by OCPSoft\nProgressButton by 'f2prateek'"
					+ "\n\n"
					+ "This product is not endorsed, certified or otherwise approved in any way by Riot Games, Inc. or any of its affiliates.";
			String fullmsg = appname + "\n" + version + "\n" + buildDate + "\n"
					+ credits;
			alertDialog.setMessage(fullmsg);
			alertDialog.setIcon(R.drawable.icon);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					alertDialog.dismiss();
				}
			});
			alertDialog.show();
			return true;

		case R.id.settings:
			Intent intent = new Intent(context, SettingsActivity.class);
			startActivity(intent);
			return true;

		case R.id.feedback:
			Intent mailIntent = new Intent(Intent.ACTION_SEND);
			mailIntent.setType("plain/text");
			mailIntent.putExtra(Intent.EXTRA_EMAIL,
					new String[] { "feedback@softstew.com" });
			mailIntent.putExtra(Intent.EXTRA_SUBJECT, "LoL Lookup Feedback");
			mailIntent.putExtra(Intent.EXTRA_TEXT, "LoL Lookup Feedback");
			startActivity(Intent.createChooser(mailIntent, ""));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	class CheckTask extends AsyncTask<String, Integer, ArrayList<Server>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			isGetting = true;
			ProgressButton btn = (ProgressButton) findViewById(R.id.checkBtn);
			btn.startAnimating();
		}

		@Override
		protected ArrayList<Server> doInBackground(String... params) {
			publishProgress(new Integer[] { 0 });
			ArrayList<Server> servers = new ArrayList<Server>();
			String apiKey = params[0];
			String summoner = params[1];
			JRiot riotAPI = new JRiot(apiKey, "na", context);
			float count = 0;
			float total = Server.values().length;
			for (Server e : Server.values()) {
				riotAPI.setRegion(e.getServerCode().toLowerCase());
				jriot.objects.Summoner foundSum;
				try {
					foundSum = riotAPI.getSummoner(summoner);
				} catch (JRiotException ex) {
					foundSum = new jriot.objects.Summoner(ex);
				}
				if (foundSum.isError() == false) {
					servers.add(e);
				} else if (foundSum.getError().getErrorCode() == 1337) {
					break;
				}
				count++;
				float progress = (count / total) * 100;
				publishProgress(new Integer[] { (int) progress });
			}
			return servers;
		}

		@Override
		protected void onPostExecute(ArrayList<Server> result) {
			super.onPostExecute(result);
			isGetting = false;
			String servers = "Summoner found on: ";
			if (result.size() != 0) {
				for (int i = 0; i < result.size(); i++) {
					if (i != result.size() - 1) {
						servers += result.get(i).getServerCode() + ", ";
					} else {
						servers += result.get(i).getServerCode();
					}
				}
			} else {
				servers += "No servers (Error?)";
			}
			Toast.makeText(context, servers, Toast.LENGTH_LONG).show();
			ProgressButton btn = (ProgressButton) findViewById(R.id.checkBtn);
			btn.setProgress(0);
			btn.stopAnimating();
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			ProgressButton btn = (ProgressButton) findViewById(R.id.checkBtn);
			btn.setProgress(progress[0]);
		}

	}

	class LookupTask extends AsyncTask<LookupObject, String, Summoner> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			isGetting = true;
			ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
			progressBar.setVisibility(View.VISIBLE);
			TextView progressText = (TextView) findViewById(R.id.progressText);
			progressText.setVisibility(View.VISIBLE);
			TextView errorText = (TextView) findViewById(R.id.errorText);
			errorText.setVisibility(View.GONE);
		}

		protected Summoner doInBackground(LookupObject... lookup) {
			publishProgress("Setting up API");
			String apiKey = lookup[0].getAPIKey();
			String server = lookup[0].getServer().getServerCode();
			String summonerName = lookup[0].getSummonerName();
			JRiot riotAPI = new JRiot(apiKey, server.toLowerCase(), context);
			publishProgress("Getting Summoner");
			jriot.objects.Summoner summonerObject;
			try {
				summonerObject = riotAPI.getSummoner(summonerName);
			} catch (JRiotException e) {
				return new Summoner(e);
			}
			if (summonerObject != null) {
				publishProgress("Getting Normal Stats");
				PlayerStatsSummaryList playerStats;
				try {
					playerStats = riotAPI.getPlayerStatsSummaryList(
							summonerObject.getId(), lookup[0].getSeason());
				} catch (JRiotException e) {
					return new Summoner(e);
				}
				SerializedGame game = null;
				if (!server.equals("KR")) {
					publishProgress("Getting Game");
					try {
						game = new GameAPI(context).get(server, summonerName);
					} catch (Exception e) {
						e.printStackTrace();
						Logger.logError(e.getMessage(), context);
					}
				}
				publishProgress("Getting Ranked Stats");
				RankedStats rankedStats = null;
				try {
					rankedStats = riotAPI.getRankedStats(
							summonerObject.getId(), lookup[0].getSeason());
				} catch (JRiotException e) {
					if (e.getErrorCode() != 404) {
						return new Summoner(e);
					}
				}
				publishProgress("Getting League");
				League soloQueue = null;
				ArrayList<League> leagues = null;
				try {
					leagues = (ArrayList<League>) riotAPI
							.getLeagues(summonerObject.getId());
				} catch (JRiotException e) {
					if (e.getErrorCode() != 404) {
						return new Summoner(e);
					}
				}
				if (leagues != null) {
					for (League entry : leagues) {
						if (entry.getQueue().equals("RANKED_SOLO_5x5")) {
							soloQueue = entry;
						}
					}
				}
				publishProgress("Getting Match History");
				jriot.objects.RecentGames recentGames;
				try {
					recentGames = riotAPI
							.getRecentGames(summonerObject.getId());
				} catch (JRiotException e) {
					return new Summoner(e);
				}
				publishProgress("Getting Icons");
				IconStorage iconStorage = new IconStorage();
				iconStorage.add(IconType.Profile,
						summonerObject.getProfileIconId(), context);
				ArrayList<LeagueSummoner> leagueSummoners = null;
				// WOOOO! THIS IS FINALLY WORKING
				try {
					if (soloQueue != null) {
						leagueSummoners = new ArrayList<LeagueSummoner>();
						ArrayList<Integer> leagueIconIds = new ArrayList<Integer>();
						int count = 0;
						int db = 0;
						ArrayList<LeagueEntry> league = soloQueue.getEntries();
						ArrayList<ArrayList<Long>> ids = new ArrayList<ArrayList<Long>>();
						ids.add(new ArrayList<Long>());
						for (LeagueEntry entry : league) {
							ids.get(db).add(
									Long.parseLong(entry.getPlayerOrTeamId()));
							count++;
							if (count == 9) {
								ids.add(new ArrayList<Long>());
								db++;
								count = 0;
							}
						}
						for (ArrayList<Long> idList : ids) {
							Map<String, jriot.objects.Summoner> summonerList = riotAPI
									.getSummonersById(idList);
							for (jriot.objects.Summoner sum : summonerList
									.values()) {
								int iconID = sum.getProfileIconId();
								leagueSummoners.add(new LeagueSummoner(sum
										.getId(), iconID));
								leagueIconIds.add(iconID);
							}
						}
						iconStorage.add(IconType.Profile, leagueIconIds,
								context);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (game != null) {
					ArrayList<Integer> champIds = new ArrayList<Integer>();
					for (SerializedSummoner sum : game.teamOne) {
						champIds.add(Integer.parseInt(sum.championId));
					}
					for (SerializedSummoner sum : game.teamTwo) {
						champIds.add(Integer.parseInt(sum.championId));
					}
					iconStorage.add(IconType.Champion, champIds, context);
				}
				if (rankedStats != null) {
					ArrayList<ChampionStats> raw = rankedStats.getChampions();
					ArrayList<Integer> champIds = new ArrayList<Integer>();
					for (int i = 0; i < raw.size(); i++) {
						if (raw.get(i).getId() != 0) {
							champIds.add(raw.get(i).getId());
						}
					}
					iconStorage.add(IconType.Champion, champIds, context);
				}
				ArrayList<Integer> champIDs = new ArrayList<Integer>();
				for (int i = 0; i < recentGames.getGames().size(); i++) {
					champIDs.add(recentGames.getGames().get(i).getChampionId());
				}
				iconStorage.add(IconType.Champion, champIDs, context);
				publishProgress("Caching Icons");
				IconCacher.cache(context, iconStorage);
				publishProgress("Done!");
				return new Summoner(iconStorage.getSerializeableStorage(),
						summonerObject, playerStats, game, rankedStats,
						soloQueue, leagueSummoners,
						EnumHelper.getServerEnumByString(server), recentGames);
			}
			return new Summoner(new JRiotException(0));
		}

		@Override
		protected void onProgressUpdate(String... progress) {
			TextView progressText = (TextView) findViewById(R.id.progressText);
			progressText.setText(progress[0]);
		}

		@Override
		protected void onPostExecute(Summoner result) {
			super.onPostExecute(result);
			ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
			progressBar.setVisibility(View.GONE);
			TextView progressText = (TextView) findViewById(R.id.progressText);
			progressText.setVisibility(View.GONE);
			progressText.setText("Loading");
			if (!this.isCancelled()) {
				if (result != null && result.isError() == false) {
					Intent intent = new Intent(context,
							SummonerInfoActivity.class);
					intent.putExtra("summoner", result);
					startActivity(intent);
				} else {
					TextView errorText = (TextView) findViewById(R.id.errorText);
					errorText.setVisibility(View.VISIBLE);
					if (result != null && result.getError() != null) {
						errorText.setText(result.getError().getErrorMessage());
					} else {
						errorText.setText("Unknown Error");
					}

				}
			}
			isGetting = false;
		}
	}

	private class LoadFavoritesTask extends
			AsyncTask<Context, Integer, FavoritesObject> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected FavoritesObject doInBackground(Context... context) {
			ArrayList<Favorite> favorites = FavoriteSaver
					.loadFavorites(context[0]);
			if (favorites != null) {
				ArrayList<Integer> ids = new ArrayList<Integer>();
				for (Favorite favorite : favorites) {
					ids.add(favorite.getIconID());
				}
				IconStorage iconStorage = new IconStorage();
				iconStorage.add(IconType.Profile, ids, context[0]);
				IconCacher.cache(context[0], iconStorage);
				iconStorage = IconCacher
						.getCachedIcons(context[0], iconStorage);
				return new FavoritesObject(iconStorage, favorites);
			}
			return null;
		}

		@Override
		protected void onPostExecute(final FavoritesObject result) {
			super.onPostExecute(result);
			TextView tabTitle = (TextView) tabHost.getTabWidget().getChildAt(1)
					.findViewById(android.R.id.title);
			ProgressBar progressBar = (ProgressBar) findViewById(R.id.main_favoritesProgressBar);
			progressBar.setVisibility(View.GONE);
			if (result != null) {
				tabTitle.setText("Favorites (" + result.getFavorites().size()
						+ ")");
				TextView favoritesText = (TextView) findViewById(R.id.main_favoriteText);
				favoritesText.setVisibility(View.GONE);
				LinearLayout favoriteLayout = (LinearLayout) findViewById(R.id.main_favoritesProgressBarLayout);
				favoriteLayout.setGravity(Gravity.CENTER_HORIZONTAL);
				final ListView listView = (ListView) findViewById(R.id.main_favoritesListView);
				FavoriteListViewAdapter adapter = new FavoriteListViewAdapter(
						context, R.layout.favorite, result.getFavorites(),
						result.getIconStorage());
				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapter, View view,
							int position, long arg) {
						Favorite favorite = (Favorite) listView
								.getItemAtPosition(position);
						EditText summonerNameText = (EditText) findViewById(R.id.summonerEditText);
						Spinner serverSpinner = (Spinner) findViewById(R.id.serverSpinner);
						String[] servers = getResources().getStringArray(
								R.array.servers);
						for (int i = 0; i < servers.length; i++) {
							if (servers[i].equals(favorite.getServerCode())) {
								serverSpinner.setSelection(i);
							}
						}
						summonerNameText.setText(favorite.getName());
						tabHost.setCurrentTab(0);
					}
				});
				listView.setOnItemLongClickListener(new OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, final int arg2, long arg3) {
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								context);
						alertDialogBuilder.setTitle("Delete?");
						alertDialogBuilder
								.setMessage("Delete this favorite?")
								.setCancelable(false)
								.setPositiveButton("Yes",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												ArrayList<Favorite> favorites = result
														.getFavorites();
												favorites.remove(arg2);
												FavoriteSaver.saveFavorites(
														context, favorites);
												new LoadFavoritesTask()
														.execute(context);
											}
										})
								.setNegativeButton("No",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												dialog.cancel();
											}
										});

						AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();
						return false;
					}
				});
			} else {
				tabTitle.setText("Favorites (0)");
				TextView favoritesText = (TextView) findViewById(R.id.main_favoriteText);
				favoritesText.setVisibility(View.VISIBLE);
			}
		}
	}

	private class LoadF2PChampsTask extends AsyncTask<Context, String, Champs> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			champsLoaded = true;
		}

		@Override
		protected Champs doInBackground(Context... context) {
			JRiot riotAPI = new JRiot(apiKey, "na", context[0]);
			ChampionList champs;
			try {
				champs = riotAPI.getFreeChampions();
			} catch (JRiotException e) {
				return new Champs(e);
			}
			if (champs != null) {
				ArrayList<Integer> ids = new ArrayList<Integer>();
				for (int i = 0; i < champs.getChampionList().size(); i++) {
					ids.add((int) champs.getChampionList().get(i).getId());
				}
				IconStorage iconStorage = new IconStorage(IconType.Champion,
						ids, context[0]);
				IconCacher.cache(context[0], iconStorage);
				iconStorage = IconCacher
						.getCachedIcons(context[0], iconStorage);
				return new Champs(iconStorage, champs.getChampionList());
			}
			return new Champs(new JRiotException(0));
		}

		@Override
		protected void onPostExecute(Champs result) {
			ProgressBar progressBar = (ProgressBar) findViewById(R.id.main_champProgressBar);
			progressBar.setVisibility(View.GONE);
			ListView champListView = (ListView) findViewById(R.id.main_champListView);
			if (result.isError() == false) {
				LinearLayout layout = (LinearLayout) findViewById(R.id.main_champLayout);
				layout.setGravity(Gravity.CENTER_HORIZONTAL);
				FreeChampionAdapter adapter = new FreeChampionAdapter(context,
						R.layout.freechamp, result.getChampions(),
						result.getIconStorage());
				champListView.setAdapter(adapter);
			} else {
				TextView errorText = (TextView) findViewById(R.id.main_champText);
				errorText.setVisibility(View.VISIBLE);
				errorText.setText(result.getError().getErrorMessage());
			}
		}

	}

	private class LoadServerStatusTask extends
			AsyncTask<Context, String, ArrayList<ServerState>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			serversLoaded = true;
		}

		@Override
		protected ArrayList<ServerState> doInBackground(Context... context) {
			return ServerStatus.getServerStates(context[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<ServerState> result) {
			ProgressBar progressBar = (ProgressBar) findViewById(R.id.main_serverProgressBar);
			progressBar.setVisibility(View.GONE);
			PullToRefreshListView listView = (PullToRefreshListView) findViewById(R.id.main_serverListView);
			if (userServerRefresh) {
				listView.onRefreshComplete();
			} else {
				userServerRefresh = true;
			}
			if (result != null) {
				LinearLayout layout = (LinearLayout) findViewById(R.id.main_serverLayout);
				layout.setGravity(Gravity.CENTER_HORIZONTAL);
				ServerListViewAdapter adapter = new ServerListViewAdapter(
						context, R.layout.server, result);
				listView.setAdapter(adapter);
			}
		}

	}
}
