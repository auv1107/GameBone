package com.Sct.gamebone.fragment;

import android.graphics.Canvas;

public abstract class BaseGameEngine {
	protected Boolean mIsRunning = false;
	protected long lastUpdateTime = 0;
	protected static int DELTA = 30;
	protected Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (mIsRunning) {
				long time = System.currentTimeMillis();
				if (time - lastUpdateTime >= DELTA) {
					time = System.currentTimeMillis();
					update((int) (time - lastUpdateTime));
					lastUpdateTime = time;
				}
				Thread.yield();
			}
		}
	};

	public void initGame() {
		mIsRunning = true;
		new Thread(mRunnable).start();
	}

	public void onDraw(Canvas canvas) {
	}

	public void onTouch(float x, float y) {
	}

	public void update(float delta) {
	}

	public void exit() {
		mIsRunning = false;
	}
}
