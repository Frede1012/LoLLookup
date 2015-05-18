package com.softstew.lollookup.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softstew.lollookup.R;
import com.softstew.lollookup.objects.Favorite;
import com.softstew.lollookup.objects.IconStorage;
import com.softstew.lollookup.objects.IconType;

public class FavoriteListViewAdapter extends ArrayAdapter<Favorite> {

	Context context;
	ArrayList<Favorite> list;
	IconStorage iconStorage;

	public FavoriteListViewAdapter(Context context, int resource,
			ArrayList<Favorite> objects, IconStorage iconStorage) {
		super(context, resource, objects);
		this.context = context;
		list = objects;
		this.iconStorage = iconStorage;
	}

	private class ViewHolder {
		ImageView icon;
		TextView text;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		Favorite curFav = list.get(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.favorite, null);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView
					.findViewById(R.id.favorite_icon);
			holder.text = (TextView) convertView
					.findViewById(R.id.server_name);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		holder.icon.setImageDrawable(iconStorage.getIcon(IconType.Profile,
				curFav.getIconID()).getDrawable(92, 92));
		holder.text.setText(curFav.getFullString());

		return convertView;
	}
}
