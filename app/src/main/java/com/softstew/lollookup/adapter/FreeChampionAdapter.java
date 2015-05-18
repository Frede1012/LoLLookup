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
import jriot.objects.Champion;
import com.softstew.lollookup.R;
import com.softstew.lollookup.objects.IconStorage;
import com.softstew.lollookup.objects.IconType;

public class FreeChampionAdapter extends ArrayAdapter<Champion> {

	Context context;
	IconStorage iconStorage;
	ArrayList<Champion> champs;

	public FreeChampionAdapter(Context context, int resource,
			ArrayList<Champion> champs, IconStorage iconStorage) {
		super(context, resource, champs);
		this.context = context;
		this.iconStorage = iconStorage;
		this.champs = champs;
	}

	private class ViewHolder {
		TextView nameText;
		ImageView iconImage;
	}

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		Champion champ = champs.get(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.freechamp, null);
			holder = new ViewHolder();
			holder.nameText = (TextView) convertView
					.findViewById(R.id.champ_name);
			holder.iconImage = (ImageView) convertView
					.findViewById(R.id.champ_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.nameText.setText(champ.getName());
		holder.iconImage.setImageDrawable(iconStorage.getIcon(
				IconType.Champion, (int) champ.getId()).getDrawable(92, 92));

		// REMOVES CLICK ANIMATIONS
		convertView.setOnClickListener(null);
		convertView.setOnLongClickListener(null);
		convertView.setLongClickable(false);

		return convertView;
	}

}
