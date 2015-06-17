package com.Sct.gamebone.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.Sct.gamebone.framework.GameApp;
import com.Sct.gamebone.framework.NGameData.Tileset;

public class TileCache {
	private static HashMap<String, Bitmap> bitmap = new HashMap<String, Bitmap>();
	private static List<Tileset> tilesets = new ArrayList<Tileset>();

	public static void AddTileset(Tileset ts) {
		if (bitmap.containsKey(ts.getName()))
			return;
		Context context = GameApp.getApplication();
		int resId = context.getResources().getIdentifier(ts.getName(),
				"drawable", context.getPackageName());
		Options options = new Options();
		options.inScaled = false;
		Bitmap b = BitmapFactory.decodeResource(context.getResources(), resId,
				options);

		if (ts.getTransparentColor() != "") {
			b = TransColor2Alpha(Color.parseColor(ts.getTransparentColor()), b);
		}

		bitmap.put(ts.getName(), b);
		tilesets.add(ts);
	}

	public static Bitmap TransColor2Alpha(int c, Bitmap b) {
		b = checkBitmap(b);
		int width = b.getWidth();
		int height = b.getHeight();
		Log.d("tilecache", "has alpha: " + b.hasAlpha());
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (b.getPixel(i, j) == c) {
					b.setPixel(i, j, Color.TRANSPARENT);
				}
			}
		}
		return b;
	}

	public static Bitmap get(int id) {
		Bitmap bm = null;
		for (int i = tilesets.size() - 1; i >= 0; i--) {
			Tileset ts = tilesets.get(i);
			if (id >= ts.getFirstgid() && id <= ts.getLastgid()) {
				if (bitmap.containsKey(ts.getName())) {
					Bitmap b = bitmap.get(ts.getName());
					Rect r = ts.getRect(id);
					// bm = Bitmap.createBitmap(r.width(), r.height(),
					// Config.ARGB_8888);
					// bm.setDensity(b.getDensity());
					// Canvas c = new Canvas(bm);
					// c.drawBitmap(b, r, new Rect(0, 0, r.width(), r.height()),
					// GameApp.getApplication().getTempPaint());
					bm = Bitmap.createBitmap(b, r.left, r.top, r.width(),
							r.height());
				}
				break;
			}
		}
		return bm;
	}

	public static void Draw(Canvas canvas, int id, Rect dst, Paint paint) {
		for (int i = tilesets.size() - 1; i >= 0; i--) {
			Tileset ts = tilesets.get(i);
			if (id >= ts.getFirstgid() && id <= ts.getLastgid()) {
				if (bitmap.containsKey(ts.getName())) {
					Bitmap b = bitmap.get(ts.getName());
					canvas.drawBitmap(b, ts.getRect(id), dst, paint);
				}
				break;
			}
		}
	}

	public static int getFirstGid(String name) {
		for (Tileset ts : tilesets) {
			if (ts.getName().equals(name)) {
				return ts.getFirstgid();
			}
		}
		return 0;
	}

	public static Bitmap checkBitmap(Bitmap bitmap) {
		if (bitmap == null || bitmap.isRecycled()
				|| (bitmap.hasAlpha() && bitmap.isMutable())) {
			return bitmap;
		}
		Bitmap result = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		result.setDensity(bitmap.getDensity());
		Canvas canvas = new Canvas();
		Paint paint = new Paint();
		canvas.setBitmap(result);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return result;
	}
}
