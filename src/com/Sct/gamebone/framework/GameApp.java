package com.Sct.gamebone.framework;

import android.app.Application;
import android.graphics.Paint;

import com.Sct.gamebone.activity.BaseActivity;

public class GameApp extends Application {
	private BaseActivity mCurrentActivity = null;
	public static GameApp instance = null;
	private BaseGameEngine mStartupScene = null;
	private Paint mTempPaint = new Paint();

	public static GameApp getApplication() {
		return instance;
	}

	public GameApp() {
		instance = this;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	public void setCurrentActivity(BaseActivity activity) {
		mCurrentActivity = activity;
	}

	public int getScreenWidth() {
		return mCurrentActivity.getScreenWidth();
	}

	public int getScreenHeight() {
		return mCurrentActivity.getScreenHeight();
	}

	public void setStartupScene(BaseGameEngine s) {
		mStartupScene = s;
	}

	public BaseGameEngine getStartupScene() {
		return mStartupScene;
	}

	public Paint getTempPaint() {
		return mTempPaint;
	}
}
