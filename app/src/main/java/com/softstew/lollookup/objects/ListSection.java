package com.softstew.lollookup.objects;

public class ListSection implements ListItem {

	String title;

	public ListSection(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public boolean isSection() {
		return true;
	}

}
