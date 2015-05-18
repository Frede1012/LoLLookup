package com.softstew.lollookup.objects;

import java.io.Serializable;

public class Favorite implements Serializable {

	private static final long serialVersionUID = 1L;
	int iconID;
	String name;
	String servercode;

	public Favorite(int iconID, String name, String servercode) {
		this.iconID = iconID;
		this.name = name;
		this.servercode = servercode;
	}

	public int getIconID() {
		return iconID;
	}

	public String getName() {
		return name;
	}

	public String getServerCode() {
		return servercode;
	}

	public String getFullString() {
		return getName() + " (" + getServerCode() + ")";
	}

}
