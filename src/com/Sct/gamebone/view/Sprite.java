package com.Sct.gamebone.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.Sct.gamebone.library.BitmapCache;

public class Sprite extends BaseNode {
	private Bitmap mBitmap = null;
	private Rect mSourceBound = null;
	private int mSourceWidth = 0;
	private int mSourceHeight = 0;
	private Paint mPaint = new Paint();

	public Sprite() {
	}

	public Sprite(Bitmap b) {
		mBitmap = b;
		width = mBitmap.getWidth();
		height = mBitmap.getHeight();
		mSourceWidth = mBitmap.getWidth();
		mSourceHeight = mBitmap.getHeight();
		mSourceBound = new Rect(0, 0, mSourceWidth, mSourceHeight);
	}

	public Sprite(String s) {
		this(BitmapCache.get(s));
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (mBitmap != null) {
			canvas.drawBitmap(mBitmap, mSourceBound, getDestBound(), mPaint);
		}
	}

	public void setBitmap(Bitmap b) {
		mBitmap = b;
	}

	public void setBitmap(String s) {
		setBitmap(BitmapCache.get(s));
	}

	public void setBitmap(Sprite s) {
		setBitmap(s.mBitmap);
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}

	public Rect getDestBound() {
		return new Rect(getRealX(), getRealY(), getRealX() + width, getRealY()
				+ height);
	}

	public Rect getSourceBound() {
		return mSourceBound;
	}

	public int getCenterX() {
		return getDestBound().centerX();
	}

	public int getCenterY() {
		return getDestBound().centerY();
	}

	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
		mPaint.setAlpha(alpha);
	}
}
