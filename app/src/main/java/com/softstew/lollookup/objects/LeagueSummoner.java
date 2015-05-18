package com.softstew.lollookup.objects;

import java.io.Serializable;

public class LeagueSummoner implements Serializable {
	private static final long serialVersionUID = 1L;
	Long id;
	int iconId;

	public LeagueSummoner(Long id, int iconId) {
		this.id = id;
		this.iconId = iconId;
	}

	public Long getID() {
		return id;
	}

	public int getIconID() {
		return iconId;
	}

}
