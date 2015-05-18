package com.softstew.lollookup.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;

import jriot.objects.ChampionStats;
import jriot.objects.RecentGames;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.softstew.lollookup.R;
import com.softstew.lollookup.adapter.ChampRankedStatsAdapter;
import com.softstew.lollookup.adapter.GameAdapter;
import com.softstew.lollookup.adapter.LeagueAdapter;
import com.softstew.lollookup.adapter.MatchAdapter;
import com.softstew.lollookup.adapter.StatAdapter;
import com.softstew.lollookup.objects.Favorite;
import com.softstew.lollookup.objects.GamePackage;
import com.softstew.lollookup.objects.IconStorage;
import com.softstew.lollookup.objects.IconType;
import com.softstew.lollookup.objects.ListItem;
import com.softstew.lollookup.objects.ListSection;
import com.softstew.lollookup.objects.Stats;
import com.softstew.lollookup.objects.Summoner;
import com.softstew.lollookup.objects.SummonerInfoPackage;
import com.softstew.lollookup.objects.Tab;
import com.softstew.lollookup.util.Calculator;
import com.softstew.lollookup.util.FavoriteSaver;
import com.softstew.lollookup.util.IconCacher;
import com.softstew.lollookup.util.QueueDB;
import com.softstew.lollookup.util.Sorter;
import com.softstew.util.Config;

public class SummonerInfoActivity extends Activity {

	Summoner curSum;
	Context context;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			this.getActionBar().setDisplayHomeAsUpEnabled(true);
			getActionBar().setTitle("Loading..");
		}

		context = this;
		Bundle extras = getIntent().getExtras();
		Summoner summoner = null;
		if (extras != null) {
			summoner = (Summoner) extras.get("summoner");
		}
		if (summoner != null) {
			summoner.setContext(context);
			curSum = summoner;
			new GetTask().execute(summoner);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.info_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.favoritesumoner:
			ArrayList<Favorite> favorites = FavoriteSaver
					.loadFavorites(context);
			ArrayList<Favorite> tempFavorites = new ArrayList<Favorite>();
			if (favorites != null && favorites.size() > 0) {
				tempFavorites.addAll(favorites);
			}
			boolean found = false;
			for (int i = 0; i < tempFavorites.size(); i++) {
				if (tempFavorites.get(i).getName()
						.equals(curSum.getSummonerObject().getName())
						&& tempFavorites.get(i).getServerCode()
								.equals(curSum.getServer().getServerCode())) {
					found = true;
				}
			}
			if (!found) {
				tempFavorites.add(new Favorite(curSum.getSummonerObject()
						.getProfileIconId(), curSum.getSummonerObject()
						.getName(), curSum.getServer().getServerCode()));
				FavoriteSaver.saveFavorites(context, tempFavorites);
				Toast.makeText(context, "Added to favorites", Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(context, "Already favorited", Toast.LENGTH_LONG)
						.show();
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@SuppressLint("NewApi")
	class GetTask extends AsyncTask<Summoner, String, SummonerInfoPackage> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		protected SummonerInfoPackage doInBackground(Summoner... summoner) {
			Summoner sum = summoner[0];

			IconStorage iconStorage = IconCacher.getCachedIcons(context,
					sum.getIconStorage());

			Drawable icon = iconStorage.getIcon(IconType.Profile,
					sum.getSummonerObject().getProfileIconId()).getDrawable(
					128, 128);

			Boolean ranked = false;
			String rankedText = "Unknown";
			String leagueSection = "";
			if (sum.getLeagueEntry() != null) {
				ranked = true;
				rankedText = sum.getLeague().getTier() + " "
						+ sum.getLeagueEntry().getDivision() + " ("
						+ sum.getLeagueEntry().getLeaguePoints() + " LP)";
				leagueSection = sum.getLeague().getName() + " - "
						+ sum.getLeague().getTier() + " "
						+ sum.getLeagueEntry().getDivision();
			}
			String kda = "Unknown";
			if (sum.getRankedStats() != null) {
				kda = Calculator.getPlayerKDA(sum.getRankedStats(),
						new DecimalFormat("0.0"));
			}

			LeagueAdapter leagueListViewAdapter = null;
			if (sum.getLeague() != null) {
				ArrayList<ListItem> league = Sorter.sortLeagueByLP(sum
						.getLeague().getEntriesByRank(
								sum.getLeagueEntry().getDivision()));
				league.add(0, new ListSection(leagueSection));
				leagueListViewAdapter = new LeagueAdapter(sum.getContext(),
						R.layout.league, league, sum.getLeagueSummoners(), sum
								.getSummonerObject().getName(), iconStorage);
			}

			ChampRankedStatsAdapter champAdapter = null;
			if (sum.getRankedStats() != null) {
				ArrayList<ChampionStats> raw = sum.getRankedStats()
						.getChampions();
				ArrayList<ListItem> champs = new ArrayList<ListItem>();
				for (int i = 0; i < raw.size(); i++) {
					if (raw.get(i).getId() != 0) {
						champs.add(raw.get(i));
					}
				}
				champs.add(0, new ListSection("Champion Stats (Ranked ONLY)"));
				champAdapter = new ChampRankedStatsAdapter(context,
						R.layout.champ, champs, iconStorage);
			}

			RecentGames matchHistory = sum.getRecentGames();
			ArrayList<ListItem> matches = Sorter.sortGamesByDate(matchHistory
					.getGames());
			matches.add(0, new ListSection("Match History"));
			MatchAdapter matchHistoryAdapter = new MatchAdapter(
					sum.getContext(), R.layout.match, matches, iconStorage);
			ArrayList<Tab> tabs = new ArrayList<Tab>();
			tabs.add(Tab.Stats);
			tabs.add(Tab.History);

			GameAdapter gameAdapter = null;
			if (sum.getGame() != null) {
				ArrayList<ListItem> items = new ArrayList<ListItem>();
				items.add(new ListSection("Team 1"));
				items.addAll(sum.getGame().teamOne);
				items.add(new ListSection("Team 2"));
				items.addAll(sum.getGame().teamTwo);
				gameAdapter = new GameAdapter(context, R.layout.gameteammember,
						items, iconStorage);
			}
			if (gameAdapter != null) {
				tabs.add(Tab.Game);
			}
			if (leagueListViewAdapter != null) {
				tabs.add(Tab.League);
			}
			if (champAdapter != null) {
				tabs.add(Tab.Champions);
			}
			return new SummonerInfoPackage(icon, ranked, sum
					.getSummonerObject().getName(), ""
					+ sum.getSummonerObject().getSummonerLevel(), rankedText,
					kda, sum.getServer(), tabs, new GamePackage(
							sum.getGame().queueName, gameAdapter),
					Stats.getAll(sum, ranked), leagueListViewAdapter,
					champAdapter, matchHistoryAdapter);
		}

		@Override
		protected void onPostExecute(SummonerInfoPackage result) {
			super.onPostExecute(result);

			setContentView(R.layout.info);

			if (Config.ADS) {
				AdView adView = (AdView) findViewById(R.id.info_adView);
				AdRequest adRequest = new AdRequest.Builder().build();
				adView.loadAd(adRequest);
				if (Config.DEBUG) {
					// AdRequest.Builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
				}
				adView.loadAd(adRequest);
			}

			TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
			tabHost.setup();
			tabHost.addTab(tabHost.newTabSpec("stats").setIndicator("Stats")
					.setContent(R.id.statsTab));
			if (result.showGame()) {
				tabHost.addTab(tabHost.newTabSpec("game").setIndicator("Game")
						.setContent(R.id.gameTab));
			}
			if (result.showLeague()) {
				tabHost.addTab(tabHost.newTabSpec("league")
						.setIndicator("League").setContent(R.id.leagueTab));
			}
			if (result.showChampions()) {
				tabHost.addTab(tabHost.newTabSpec("champions")
						.setIndicator("Champions")
						.setContent(R.id.championsTab));
			}
			tabHost.addTab(tabHost.newTabSpec("history")
					.setIndicator("History").setContent(R.id.historyTab));
			tabHost.setCurrentTab(0);

			if (android.os.Build.VERSION.SDK_INT >= 11) {
				getActionBar().setTitle(
						result.getName() + " ("
								+ result.getServer().getServerCode() + ")");
			} else {
				setTitle(result.getName() + " ("
						+ result.getServer().getServerCode() + ")");
			}

			ImageView icon = (ImageView) findViewById(R.id.info_icon);
			icon.setImageDrawable(result.getIcon());

			TextView name = (TextView) findViewById(R.id.info_summonername);
			name.setText(result.getName() + " (" + result.getLevel() + ")");

			TextView kda = (TextView) findViewById(R.id.info_kda);
			kda.setText(result.getKDA());

			TextView ranked = (TextView) findViewById(R.id.info_ranked);
			ranked.setText(result.getRanked());

			ListView statListView = (ListView) findViewById(R.id.stat_listView);
			statListView.setAdapter(new StatAdapter(context, R.layout.stat,
					result.getStats()));

			if (result.showGame()) {
				TextView gameQueue = (TextView) findViewById(R.id.game_queue);
				ListView gameList = (ListView) findViewById(R.id.game_ListView);
				gameQueue.setText(QueueDB.getQueue(result.getGame().queueName,
						context));
				gameList.setAdapter(result.getGame().gameAdapter);
			}

			if (result.showLeague()) {
				ListView leagueListView = (ListView) findViewById(R.id.leagueListView);
				leagueListView.setAdapter(result.getLeagueAdapter());
			}

			if (result.showChampions()) {
				ListView championsListView = (ListView) findViewById(R.id.championsListView);
				championsListView.setAdapter(result
						.getChampRankedStatsAdapter());
			}

			ListView historyListView = (ListView) findViewById(R.id.matchHistoryListView);
			historyListView.setAdapter(result.getHistoryAdapter());

		}
	}
}
