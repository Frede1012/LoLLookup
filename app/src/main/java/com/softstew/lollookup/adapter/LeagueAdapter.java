package com.softstew.lollookup.adapter;

import java.util.ArrayList;

import jriot.objects.LeagueEntry;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softstew.lollookup.R;
import com.softstew.lollookup.objects.Icon;
import com.softstew.lollookup.objects.IconStorage;
import com.softstew.lollookup.objects.IconType;
import com.softstew.lollookup.objects.LeagueSummoner;
import com.softstew.lollookup.objects.ListItem;
import com.softstew.lollookup.objects.ListSection;

public class LeagueAdapter extends ArrayAdapter<ListItem> {

	Context context;
	String summonerName;
	ArrayList<ListItem> league;
	ArrayList<LeagueSummoner> summoners;
	IconStorage iconStorage;

	public LeagueAdapter(Context context, int resourceId,
			ArrayList<ListItem> league, ArrayList<LeagueSummoner> summoners,
			String summonerName, IconStorage iconStorage) {
		super(context, resourceId, league);
		this.league = league;
		this.summoners = summoners;
		this.summonerName = summonerName;
		this.context = context;
		this.iconStorage = iconStorage;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ListItem item = league.get(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (item.isSection()) {
			ListSection section = (ListSection) item;
			convertView = mInflater.inflate(R.layout.section, null);

			convertView.setOnClickListener(null);
			convertView.setOnLongClickListener(null);
			convertView.setLongClickable(false);

			final TextView sectionView = (TextView) convertView
					.findViewById(R.id.list_item_section_text);
			sectionView.setText(section.getTitle());
		} else {
			convertView = mInflater.inflate(R.layout.league, null);
			LeagueEntry entry = (LeagueEntry) item;
			TextView nameText = (TextView) convertView
					.findViewById(R.id.league_name);
			TextView leaguePointsText = (TextView) convertView
					.findViewById(R.id.league_lp);
			TextView promotionText = (TextView) convertView
					.findViewById(R.id.league_promo);
			TextView promoText = (TextView) convertView
					.findViewById(R.id.league_promotext);
			ImageView icon = (ImageView) convertView
					.findViewById(R.id.league_icon);

			if (entry.getPlayerOrTeamName() != null) {
				nameText.setText(entry.getPlayerOrTeamName());
			}
			leaguePointsText.setText("" + entry.getLeaguePoints());
			String promotion = "";
			if (entry.getMiniSeries() != null) {
				String promoString = entry.getMiniSeries().getProgress();
				char[] letterArray = promoString.toCharArray();
				for (int i = 0; i < letterArray.length; i++) {
					if (i == letterArray.length - 1) {
						promotion += letterArray[i];
					} else {
						promotion += letterArray[i] + "/";
					}
				}
			} else {
				promoText.setText("");
			}
			promotionText.setText(promotion);

			if (entry.getPlayerOrTeamName().equals(summonerName)) {
				nameText.setTextColor(Color.GREEN);
				leaguePointsText.setTextColor(Color.GREEN);
				promotionText.setTextColor(Color.GREEN);
			} else {
				nameText.setTextColor(Color.WHITE);
				leaguePointsText.setTextColor(Color.WHITE);
				promotionText.setTextColor(Color.WHITE);
			}

			int iconID = 0;
			for (int i = 0; i < summoners.size(); i++) {
				if (summoners.get(i).getID() == Integer.parseInt(entry
						.getPlayerOrTeamId())) {
					iconID = summoners.get(i).getIconID();
					break;
				}
			}
			Icon sumIcon = iconStorage.getIcon(IconType.Profile, iconID);
			if (sumIcon != null) {
				icon.setImageDrawable(sumIcon.getDrawable(92, 92));
			}

			// REMOVES CLICK ANIMATIONS
			convertView.setOnClickListener(null);
			convertView.setOnLongClickListener(null);
			convertView.setLongClickable(false);

		}
		return convertView;
	}
}
