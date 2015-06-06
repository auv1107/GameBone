package com.Sct.gamebone.framwork;

import com.Sct.gamebone.activity.BaseActivity;

import android.app.Application;

public class GameApp extends Application {
	public BaseActivity mCurrentActivity = null;
	public static GameApp instance = null;

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
}
