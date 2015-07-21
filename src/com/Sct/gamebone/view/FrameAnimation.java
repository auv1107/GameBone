package com.Sct.gamebone.view;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.Sct.gamebone.library.BitmapCache;

public class FrameAnimation extends BaseNode {
	public static final int DEFAULT_DURATION = 2000;
	private List<Sprite> mSpriteList = new ArrayList<Sprite>();
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
		addFrame(new Sprite(b));
		return this;
	}

	public FrameAnimation addFrame(Sprite s) {
		mSpriteList.add(s);
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
		if (mSpriteList.get(mCurrentFrameIndex) != null) {
			Sprite s = mSpriteList.get(mCurrentFrameIndex);
			s.x = getRealX();
			s.y = getRealY();
			s.onDraw(canvas);
		}
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		super.update(delta);
		if (mIsRunning) {
			if (mSpriteList == null || mSpriteList.size() == 0)
				return;
			long t = System.currentTimeMillis();
			if ((t - mLastUpdateTime) >= mDuration / mSpriteList.size()) {
				mLastUpdateTime = t;
				mCurrentFrameIndex++;
				if (mCurrentFrameIndex == mSpriteList.size()) {
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
