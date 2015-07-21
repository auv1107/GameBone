package com.Sct.gamebone.framework;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;

import com.Sct.gamebone.R;
import com.Sct.gamebone.R.string;
import com.Sct.gamebone.activity.BaseActivity;

public class GameApp extends Application {
	private BaseActivity mCurrentActivity = null;
	public static GameApp instance = null;
	private String mStartupSceneClassName = null;
	private Paint mTempPaint = new Paint();

	private Map<String, String> mString = new HashMap<String, String>();

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

	public BaseActivity getCurrentActivity() {
		return mCurrentActivity;
	}

	public int getScreenWidth() {
		return mCurrentActivity.getScreenWidth();
	}

	public int getScreenHeight() {
		return mCurrentActivity.getScreenHeight();
	}

	public void setStartupScene(String s) {
		mStartupSceneClassName = s;
	}

	public BaseGameEngine getStartupScene() {
		try {
			return (BaseGameEngine) Class.forName(mStartupSceneClassName)
					.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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

	public void putPreferenceInt(String key, int value) {
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

	public String getString(String s) {
		return mString.get(s);
	}

	public void prepareString() {
		Class<string> c = R.string.class;

		for (Field f : c.getFields()) {
			try {
				int id = f.getInt(f);
				String s = getResources().getString(id);
				mString.put(f.getName(), s);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
