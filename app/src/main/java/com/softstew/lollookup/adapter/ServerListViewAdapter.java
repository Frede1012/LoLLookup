package com.softstew.lollookup.adapter;

import java.util.ArrayList;

import com.softstew.lollookup.R;
import com.softstew.lollookup.util.ServerStatus.ServerState;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ServerListViewAdapter extends ArrayAdapter<ServerState> {

	Context context;
	ArrayList<ServerState> servers;

	public ServerListViewAdapter(Context context, int resource,
			ArrayList<ServerState> objects) {
		super(context, resource, objects);
		this.context = context;
		servers = objects;
	}

	private class ViewHolder {
		TextView name;
		TextView state;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		ServerState serverState = servers.get(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.server, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.server_name);
			holder.state = (TextView) convertView
					.findViewById(R.id.server_state);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setText(serverState.getName());
		holder.state.setText(serverState.getState());

		int color;
		if (serverState.getState().equals("ONLINE")) {
			color = Color.GREEN;
		} else if (serverState.getState().equals("OFFLINE")) {
			color = Color.RED;
		} else {
			color = Color.YELLOW;
		}

		holder.state.setTextColor(color);

		// REMOVES CLICK ANIMATIONS
		convertView.setOnClickListener(null);
		convertView.setOnLongClickListener(null);
		convertView.setLongClickable(false);

		return convertView;
	}

}
