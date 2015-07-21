package com.Sct.gamebone.framework;

import com.Sct.gamebone.framework.BaseGameEngine.SceneManageCallback;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,
		OnTouchListener, SceneManageCallback {
	protected SurfaceHolder mHolder = null;
	protected GameThread mGameThread = null;
	protected BaseGameEngine mGameEngine = null;

	protected Callback mCallback = null;

	public interface Callback {
		public void onExit();
	}

	public void setCallback(Callback c) {
		mCallback = c;
	}

	public GameView(Context context) {
		super(context);
		mHolder = getHolder();
		mHolder.addCallback(this);
		mGameEngine = GameApp.getApplication().getStartupScene();
		setOnTouchListener(this);
		if (mGameEngine != null)
			mGameEngine.initGame();
		mGameEngine.setSceneManageCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		mGameThread = new GameThread(this, mHolder);
		mGameThread.start();
		mGameEngine.enter();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		mGameThread.stop();
		if (mGameEngine != null)
			mGameEngine.exit();
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (canvas != null && mGameEngine != null)
			mGameEngine.onDraw(canvas);
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent e) {
		if (mGameEngine != null)
			mGameEngine.onTouch(e);
		return false;
	}

	@Override
	public void onEnter() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		if (mCallback != null)
			mCallback.onExit();
	}

}
