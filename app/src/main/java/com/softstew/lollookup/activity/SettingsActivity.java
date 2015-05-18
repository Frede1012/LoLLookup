package com.softstew.lollookup.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

import com.softstew.lollookup.R;
import com.softstew.lollookup.util.IconCacher;

public class SettingsActivity extends PreferenceActivity {

	Context context;

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			this.getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		context = this;

		OnPreferenceChangeListener restartListener = new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				finish();
				Intent intent = new Intent(context, SettingsActivity.class);
				context.startActivity(intent);
				return true;
			}
		};
		getPreferenceScreen().findPreference("general_server")
				.setOnPreferenceChangeListener(restartListener);
		getPreferenceScreen().findPreference("general_season")
				.setOnPreferenceChangeListener(restartListener);
		getPreferenceScreen().findPreference("connection_connecttimeout")
				.setOnPreferenceChangeListener(restartListener);
		getPreferenceScreen().findPreference("connection_readtimeout")
				.setOnPreferenceChangeListener(restartListener);
		getPreferenceScreen().findPreference("cache_delete")
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference preference) {
						IconCacher.deleteCache(context);
						getPreferenceScreen().findPreference("cache_icons")
								.setTitle(IconCacher.countIcons(context));
						return false;
					}
				});
		refresh();
	}

	@SuppressWarnings("deprecation")
	void refresh() {
		String server = PreferenceManager.getDefaultSharedPreferences(context)
				.getString("general_server", "NA");
		int season = Integer.parseInt(PreferenceManager
				.getDefaultSharedPreferences(context).getString(
						"general_season", "4"));
		int connectTimeout = Integer.parseInt(PreferenceManager
				.getDefaultSharedPreferences(context).getString(
						"connection_connecttimeout", "20"));
		int readTimeout = Integer.parseInt(PreferenceManager
				.getDefaultSharedPreferences(context).getString(
						"connection_readtimeout", "20"));
		getPreferenceScreen().findPreference("general_server").setTitle(
				"Preferred Server: " + server);
		getPreferenceScreen().findPreference("general_season").setTitle(
				"Season: " + season);
		getPreferenceScreen().findPreference("connection_connecttimeout")
				.setTitle("Connection Timeout: " + connectTimeout + " sec");
		getPreferenceScreen().findPreference("connection_readtimeout")
				.setTitle("Read Timeout: " + readTimeout + " sec");
		getPreferenceScreen().findPreference("cache_icons").setTitle(
				IconCacher.countIcons(context));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
