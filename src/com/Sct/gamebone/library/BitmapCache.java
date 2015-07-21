package com.Sct.gamebone.library;

import java.util.HashMap;

import com.Sct.gamebone.framework.GameApp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapCache {
	private static HashMap<String, Bitmap> list = new HashMap<String, Bitmap>();

	public static void put(String s, Bitmap b) {
		if (list.containsKey(s)) {
			list.remove(s);
		}
		list.put(s, b);
	}

	public static Bitmap get(String s) {
		return list.get(s);
	}

	public static void put(String s, int id) {
		Bitmap b = BitmapFactory.decodeResource(GameApp.getApplication()
				.getResources(), id);
		put(s, b);
	}
}
