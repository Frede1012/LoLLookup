package com.softstew.lollookup.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import jriot.objects.Game;
import org.ocpsoft.prettytime.PrettyTime;
import android.annotation.SuppressLint;
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
import com.softstew.lollookup.util.QueueDB;

public class MatchAdapter extends ArrayAdapter<ListItem> {

	PrettyTime prettyTime;
	Context context;
	ArrayList<ListItem> games;
	IconStorage iconStorage;

	public MatchAdapter(Context context, int resourceId,
			ArrayList<ListItem> games, IconStorage iconStorage) {
		super(context, resourceId, games);
		this.games = games;
		this.context = context;
		this.iconStorage = iconStorage;
		prettyTime = new PrettyTime(Locale.ENGLISH);
	}

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {

		ListItem item = games.get(position);

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
			Game entry = (Game) item;
			convertView = mInflater.inflate(R.layout.match, null);
			TextView gameModeText = (TextView) convertView
					.findViewById(R.id.match_gamemode);
			TextView resultText = (TextView) convertView
					.findViewById(R.id.match_result);
			TextView dateText = (TextView) convertView
					.findViewById(R.id.match_date);
			TextView scoreText = (TextView) convertView
					.findViewById(R.id.match_score);
			TextView goldText = (TextView) convertView
					.findViewById(R.id.match_gold);
			TextView minionsText = (TextView) convertView
					.findViewById(R.id.match_minions);
			ImageView championIcon = (ImageView) convertView
					.findViewById(R.id.match_championIcon);

			String wl = "";
			if (entry.getStats().getWin()) {
				resultText.setTextColor(context.getResources().getColor(
						R.color.win));
				wl = "Win";
			} else {
				resultText.setTextColor(context.getResources().getColor(
						R.color.loss));
				wl = "Loss";
			}

			String date = prettyTime.format(new Date(entry.getCreateDate()));

			String score = entry.getStats().getChampionsKilled() + "/"
					+ entry.getStats().getNumDeaths() + "/"
					+ entry.getStats().getAssists();

			gameModeText.setText(QueueDB.getQueue(entry.getSubType(), context));
			resultText.setText(wl);
			dateText.setText(date);
			scoreText.setText(score);
			goldText.setText(Calculator.convertGold(entry.getStats()
					.getGoldEarned(), new DecimalFormat("#.#")));
			minionsText.setText("" + entry.getStats().getMinionsKilled());
			championIcon.setImageDrawable(iconStorage.getIcon(
					IconType.Champion, entry.getChampionId()).getDrawable(92,
					92));

			// REMOVES CLICK ANIMATIONS
			convertView.setOnClickListener(null);
			convertView.setOnLongClickListener(null);
			convertView.setLongClickable(false);

		}
		return convertView;
	}
}
