package com.softstew.lollookup.objects;

public enum Server {
	NA("NA", "North America"), EUW("EUW", "Europe West"), EUNE("EUNE",
			"Europe Nordic & East"), BR("BR", "Brazil"), LAN("LAN", "LA1",
			"Latin America North"), LAS("LAS", "LA2", "Latin America South"), OCE(
			"OCE", "Oceania"), KR("KR", "Korea"), RU("RU", "Russia"), PBE(
			"PBE", "Public Beta Enviroment");

	String serverCode;
	String launcherServerCode;
	String name;

	Server(String serverCode, String name) {
		this.serverCode = serverCode;
		this.launcherServerCode = serverCode;
		this.name = name;
	}

	Server(String serverCode, String launcherServerCode, String name) {
		this.serverCode = serverCode;
		this.launcherServerCode = launcherServerCode;
		this.name = name;
	}

	public String getServerCode() {
		return serverCode;
	}

	public String GetLauncherServerCode() {
		return launcherServerCode;
	}

	public String getName() {
		return name;
	}
}
