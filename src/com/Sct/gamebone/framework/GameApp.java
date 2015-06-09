package com.Sct.gamebone.framework;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;

import com.Sct.gamebone.R;
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

	public void putPreference(String key, String value) {
		SharedPreferences preferences = getSharedPreferences(this
				.getResources().getString(R.string.app_name),
				Activity.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String getPreference(String key) {
		SharedPreferences preferences = getSharedPreferences(this
				.getResources().getString(R.string.app_name),
				Activity.MODE_PRIVATE);
		return preferences.getString(key, null);
	}

	public void putPreference(String key, int value) {
		SharedPreferences preferences = getSharedPreferences(this
				.getResources().getString(R.string.app_name),
				Activity.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public int getPreferenceInt(String key) {
		SharedPreferences preferences = getSharedPreferences(this
				.getResources().getString(R.string.app_name),
				Activity.MODE_PRIVATE);
		return preferences.getInt(key, 0);
	}

}
