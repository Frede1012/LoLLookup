package com.softstew.util;

import com.softstew.lollookup.util.ErrorReport;

import android.content.Context;
import android.util.Log;

public class Logger {

	public static void logError(String msg, Context context) {
		if (Config.ERRORLOGGING) {
			ErrorReport.reportError(context, msg);
			Log.e("Error", msg);
		}
	}

	public static void logDebug(String msg) {
		if (Config.DEBUG) {
			Log.d("DEBUG", msg);
		}
	}
}
