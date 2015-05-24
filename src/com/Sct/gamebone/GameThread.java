package com.Sct.gamebone;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread implements Runnable {

	protected Thread mCurrentThread = null;
	protected final static int TIME_IN_FRAME = 30;

	protected SurfaceHolder mHolder = null;
	protected GameView mGameView = null;

	@Override
	public void run() {
		while (mCurrentThread != null) {
			Canvas canvas = mHolder.lockCanvas();
			try {
				mGameView.onDraw(canvas);
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					mHolder.unlockCanvasAndPost(canvas);
				} catch (Exception e) {

				}
			}
		}
	}

	public GameThread(GameView v, SurfaceHolder holder) {
		mGameView = v;
		mHolder = holder;
	}

	public void start() {
		mCurrentThread = new Thread(this);
		mCurrentThread.start();
	}

	public void stop() {
		mCurrentThread = null;
	}

}
