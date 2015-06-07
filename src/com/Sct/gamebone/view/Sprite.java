package com.Sct.gamebone.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.Sct.gamebone.framework.GameApp;
import com.Sct.gamebone.library.BitmapCache;

public class Sprite extends BaseNode {
	private Bitmap mBitmap = null;

	public Sprite() {

	}

	public Sprite(Bitmap b) {
		mBitmap = b;
		width = mBitmap.getWidth();
		height = mBitmap.getHeight();
	}

	public Sprite(String s) {
		this(BitmapCache.get(s));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mBitmap != null)
			canvas.drawBitmap(mBitmap, x, y, GameApp.getApplication()
					.getTempPaint());
	}
}
