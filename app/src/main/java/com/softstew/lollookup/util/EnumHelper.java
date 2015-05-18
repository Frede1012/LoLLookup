package com.softstew.lollookup.util;

import com.softstew.lollookup.objects.Server;

public class EnumHelper {
	public static Server getServerEnumByString(String name) {
		for (Server e : Server.values()) {
			if (name.equals(e.name())) {
				return e;
			}
		}
		return null;
	}
}
