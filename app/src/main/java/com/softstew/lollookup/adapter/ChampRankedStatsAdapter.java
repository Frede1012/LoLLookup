package com.softstew.lollookup.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

import jriot.objects.ChampionStats;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softstew.lollookup.R;
import com.softstew.lollookup.objects.IconStorage;
import com.softstew.lollookup.objects.IconType;
import com.softstew.lollookup.objects.ListItem;
import com.softstew.lollookup.objects.ListSection;
import com.softstew.lollookup.util.Calculator;

public class ChampRankedStatsAdapter extends ArrayAdapter<ListItem> {

	Context context;
	ArrayList<ListItem> champs;
	IconStorage iconStorage;

	public ChampRankedStatsAdapter(Context context, int resource,
			ArrayList<ListItem> champs, IconStorage iconStorage) {
		super(context, resource, champs);
		this.context = context;
		this.champs = champs;
		this.iconStorage = iconStorage;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ListItem item = champs.get(position);

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
			convertView = mInflater.inflate(R.layout.champ, null);
			ChampionStats champ = (ChampionStats) item;
			ImageView icon = (ImageView) convertView
					.findViewById(R.id.champStats_icon);
			TextView wlText = (TextView) convertView
					.findViewById(R.id.champStats_wl);
			TextView wpText = (TextView) convertView
					.findViewById(R.id.champStats_winpercentage);
			TextView kdaText = (TextView) convertView
					.findViewById(R.id.champStats_kda);
			TextView minionsText = (TextView) convertView
					.findViewById(R.id.champStats_minions);

			int wins = champ.getStats().getTotalSessionsWon();
			int losses = champ.getStats().getTotalSessionsLost();
			String wl = wins + "/" + losses;
			String kda = Calculator
					.getChampKDA(champ, new DecimalFormat("0.#"));
			wlText.setText(wl);
			wpText.setText(Calculator.getWL(wins, losses, new DecimalFormat(
					"0.#")));
			kdaText.setText(kda);
			minionsText.setText(Calculator.getAverageMinions(champ.getStats()
					.getTotalMinionKills(), champ.getStats()
					.getTotalSessionsPlayed(), new DecimalFormat("0.#")));
			icon.setImageDrawable(iconStorage.getIcon(IconType.Champion,
					champ.getId()).getDrawable(92, 92));

			// REMOVES CLICK ANIMATIONS
			convertView.setOnClickListener(null);
			convertView.setOnLongClickListener(null);
			convertView.setLongClickable(false);
		}
		return convertView;
	}
}
