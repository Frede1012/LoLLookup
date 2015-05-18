package com.softstew.lollookup.util;

import android.content.Context;

import com.softstew.lollookup.objects.Queue;
import com.softstew.util.Logger;

public class QueueDB {

	public static String getQueue(String subType, Context context) {
		for (Queue q : Queue.values()) {
			if (subType.equals(q.getRaw())) {
				return q.getName();
			}
		}
		Logger.logError("Queue '" + subType + "' not found", context);
		return subType;
	}
}
