package com.Sct.gamebone.framework;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.Sct.gamebone.view.BaseLayer;

public abstract class BaseGameEngine {
	protected List<BaseLayer> mChildrenList = new ArrayList<BaseLayer>();

	private Boolean mIsRunning = false;
	private long lastUpdateTime = 0;
	public static int DELTA = 30;
	private Runnable mRunnable = new Runnable() {

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
		lastUpdateTime = System.currentTimeMillis();
		new Thread(mRunnable).start();
	}

	public void onDraw(Canvas canvas) {
		for (BaseLayer l : mChildrenList) {
			canvas.save();
			canvas.translate(l.x, l.y);
			canvas.clipRect(0, 0, l.width, l.height);
			l.onDraw(canvas);
			canvas.restore();
		}
	}

	public void onTouch(MotionEvent e) {
		for (BaseLayer l : mChildrenList) {
			l.onTouch(e);
		}
	}

	public void update(float delta) {
		for (BaseLayer l : mChildrenList) {
			l.update(delta);
		}
	}

	public void exit() {
		mIsRunning = false;
	}

	public void addChild(BaseLayer l) {
		if (mChildrenList.contains(l)) {
			mChildrenList.remove(l);
		}
		mChildrenList.add(l);
	}

	public void removeChild(BaseLayer l) {
		if (mChildrenList.contains(l)) {
			mChildrenList.remove(l);
		}
	}

	public void removeAllChildren() {
		mChildrenList.clear();
	}
}
