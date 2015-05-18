package com.softstew.lollookup.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.softstew.lollookup.R;
import com.softstew.lollookup.objects.ListItem;
import com.softstew.lollookup.objects.ListSection;
import com.softstew.lollookup.objects.Stat;

public class StatAdapter extends ArrayAdapter<ListItem> {

	Context context;
	ArrayList<ListItem> stats;

	public StatAdapter(Context context, int resource,
			ArrayList<ListItem> stats) {
		super(context, resource, stats);
		this.context = context;
		this.stats = stats;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ListItem item = stats.get(position);

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
			convertView = mInflater.inflate(R.layout.stat, null);
			TextView title = (TextView) convertView
					.findViewById(R.id.stat_title);
			TextView statText = (TextView) convertView
					.findViewById(R.id.stat_stat);
			Stat stat = (Stat) item;
			if (stat.getTitle() != null) {
				title.setText(stat.getTitle());
			}
			if (stat.getStat() != null) {
				statText.setText(stat.getStat());
			}

			// REMOVES CLICK ANIMATIONS
			convertView.setOnClickListener(null);
			convertView.setOnLongClickListener(null);
			convertView.setLongClickable(false);

		}
		return convertView;
	}

}
