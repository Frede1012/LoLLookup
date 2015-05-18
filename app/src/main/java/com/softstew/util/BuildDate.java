package com.softstew.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.content.Context;
import android.content.pm.ApplicationInfo;

public class BuildDate {

	public static String getBuildDate(Context context) {
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(), 0);
			ZipFile zf = new ZipFile(ai.sourceDir);
			ZipEntry ze = zf.getEntry("classes.dex");
			long time = ze.getTime();
			String buildDate = SimpleDateFormat.getInstance().format(
					new Date(time));
			return buildDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Unknown";
	}

}
