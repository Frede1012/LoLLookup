package com.softstew.lollookup.objects;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Icon implements Serializable {

	private static final long serialVersionUID = 1L;

	boolean placeholder;
	IconType type;
	int id;
	Drawable drawable;
	
	public Icon(boolean placeholder, IconType type, int id, Drawable drawable) {
		this.placeholder = placeholder;
		this.type = type;
		this.id = id;
		this.drawable = drawable;
	}

	public Icon(boolean placeholder, IconType type, int id) {
		this.placeholder = placeholder;
		this.type = type;
		this.id = id;
	}

	public boolean isPlaceholder() {
		return placeholder;
	}

	public IconType getType() {
		return type;
	}

	public int getID() {
		return id;
	}

	public Drawable getDrawable(int width, int height) {
		return resize(drawable, width, height);
	}

	@SuppressWarnings("deprecation")
	static Drawable resize(Drawable image, int width, int height) {
		if (height == 128 && width == 128) {
			return image;
		}
		Bitmap b = ((BitmapDrawable) image).getBitmap();
		Bitmap bitmapResized = Bitmap.createScaledBitmap(b, width, height,
				false);
		return new BitmapDrawable(bitmapResized);
	}
}
