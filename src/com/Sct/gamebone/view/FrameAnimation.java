package com.Sct.gamebone.view;

import java.util.ArrayList;
import java.util.List;

import com.Sct.gamebone.framework.GameApp;
import com.Sct.gamebone.library.BitmapCache;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class FrameAnimation extends BaseNode {
	public static final int DEFAULT_DURATION = 2000;
	private List<Bitmap> mBitmapList = new ArrayList<Bitmap>();
	private long mLastUpdateTime = 0;
	private int mDuration = DEFAULT_DURATION;
	private int mCurrentFrameIndex = 0;
	private boolean mIsRunning = false;
	private int mLoopTime = 0;
	private int mLoopedTime = 0;

	public FrameAnimation() {

	}

	public FrameAnimation addFrame(String s) {
		addFrame(BitmapCache.get(s));
		return this;
	}

	public FrameAnimation addFrame(Bitmap b) {
		mBitmapList.add(b);
		return this;
	}

	public FrameAnimation addFrame(Sprite s) {
		addFrame(s.getBitmap());
		return this;
	}

	public FrameAnimation setDuration(int d) {
		mDuration = d;
		return this;
	}

	public FrameAnimation start() {
		mCurrentFrameIndex = 0;
		mLoopedTime = 0;
		mIsRunning = true;
		return this;
	}

	public FrameAnimation stop() {
		mIsRunning = false;
		return this;
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (mBitmapList.get(mCurrentFrameIndex) != null)
			canvas.drawBitmap(mBitmapList.get(mCurrentFrameIndex), getRealX(),
					getRealY(), GameApp.getApplication().getTempPaint());
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		super.update(delta);
		if (mIsRunning) {
			if (mBitmapList == null || mBitmapList.size() == 0)
				return;
			long t = System.currentTimeMillis();
			if ((t - mLastUpdateTime) >= mDuration / mBitmapList.size()) {
				mLastUpdateTime = t;
				mCurrentFrameIndex++;
				if (mCurrentFrameIndex == mBitmapList.size()) {
					mCurrentFrameIndex = 0;
					// 是否达到循环次数，若达到，则停止
					if (mLoopTime != 0) {
						mLoopedTime++;
						if (mLoopedTime >= mLoopTime) {
							mLoopedTime = 0;
							stop();
						}
					}
				}
			}
		}
	}
}
