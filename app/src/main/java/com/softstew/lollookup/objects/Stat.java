package com.softstew.lollookup.objects;

public class Stat implements ListItem {

	String title;
	String stat;

	public Stat(String title, String stat) {
		this.title = title;
		this.stat = stat;
	}

	public Stat(String title, int stat) {
		this.title = title;
		this.stat = "" + stat;
	}

	public String getTitle() {
		return title;
	}

	public String getStat() {
		return stat;
	}
	
	public boolean isSection(){
		return false;
	}

}
