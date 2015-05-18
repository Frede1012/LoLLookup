package com.softstew.lollookup.adapter;

import java.util.ArrayList;
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
import com.softstew.lollookup.game.SerializedSummoner;
import com.softstew.lollookup.objects.IconStorage;
import com.softstew.lollookup.objects.IconType;
import com.softstew.lollookup.objects.ListItem;
import com.softstew.lollookup.objects.ListSection;

public class GameAdapter extends ArrayAdapter<ListItem> {

	Context context;
	ArrayList<ListItem> items;
	IconStorage iconStorage;

	public GameAdapter(Context context, int textViewResourceId,
			ArrayList<ListItem> objects, IconStorage iconStorage) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.items = objects;
		this.iconStorage = iconStorage;
	}

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItem item = items.get(position);

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
			convertView = mInflater.inflate(R.layout.gameteammember, null);
			SerializedSummoner summoner = (SerializedSummoner) item;

			TextView name = (TextView) convertView
					.findViewById(R.id.gameteammember_name);
			name.setText(summoner.name);

			ImageView icon = (ImageView) convertView
					.findViewById(R.id.gameteammember_icon);
			icon.setImageDrawable(iconStorage.getIcon(IconType.Champion,
					Integer.parseInt(summoner.championId)).getDrawable(48, 48));

			// REMOVES CLICK ANIMATIONS
			convertView.setOnClickListener(null);
			convertView.setOnLongClickListener(null);
			convertView.setLongClickable(false);
		}
		return convertView;
	}

}
