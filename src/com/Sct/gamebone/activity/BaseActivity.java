package com.Sct.gamebone.activity;

import java.io.IOException;
import java.util.HashMap;

import android.app.Activity;
import android.app.Service;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Process;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.Sct.gamebone.R;
import com.Sct.gamebone.framework.GameApp;

public class BaseActivity extends Activity {
	protected static boolean mIsMute = false;
	protected static boolean mIsNotVibrate = true;
	protected static HashMap<String, MediaPlayer> mMusicMap = new HashMap<String, MediaPlayer>();;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setFullscreen();
		GameApp.instance.setCurrentActivity(this);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	public void AddMusic(String key, int res) {
		MediaPlayer mp = MediaPlayer.create(this, res);
		try {
			mp.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mMusicMap.put(key, mp);
	}

	public void PlayMusic(String key, boolean loop) {
		if (mIsMute)
			return;

		MediaPlayer mp = mMusicMap.get(key);
		mp.setLooping(loop);
		mp.start();
	}

	public void setFullscreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	public int getScreenWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	public int getScreenHeight() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	public void vibrate(long duration) {
		if (mIsNotVibrate)
			return;
		Vibrator vibrate = (Vibrator) getApplication().getSystemService(
				Service.VIBRATOR_SERVICE);
		vibrate.vibrate(duration);
	}

	public void killApp() {
		Process.killProcess(Process.myPid());
	}
}
