package com.Sct.gamebone;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,
		OnTouchListener {
	protected SurfaceHolder mHolder = null;
	protected GameThread mGameThread = null;
	protected BaseGameEngine mGameEngine = null;

	public GameView(Context context) {
		super(context);
		mHolder = getHolder();
		mHolder.addCallback(this);
		mGameEngine = new GameEngine(context);
		// mGameEngine = new MapEditor(context);
		setOnTouchListener(this);
		mGameEngine.initGame();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		mGameThread = new GameThread(this, mHolder);
		mGameThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		mGameThread.stop();
		mGameEngine.exit();
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (canvas != null)
			mGameEngine.onDraw(canvas);
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent e) {
		mGameEngine.onTouch(e.getX(), e.getY());
		return false;
	}

}
