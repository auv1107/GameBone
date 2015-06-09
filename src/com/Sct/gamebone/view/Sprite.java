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
	public void onDraw(Canvas canvas) {
		if (mBitmap != null)
			canvas.drawBitmap(mBitmap, getRealX(), getRealY(), GameApp
					.getApplication().getTempPaint());
	}

	public void setBitmap(Bitmap b) {
		mBitmap = b;
	}

	public void setBitmap(String s) {
		setBitmap(BitmapCache.get(s));
	}

	public void setSprite(Sprite s) {
		setBitmap(s.mBitmap);
	}
}
