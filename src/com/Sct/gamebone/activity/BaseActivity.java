package com.Sct.gamebone.activity;

import android.app.Activity;
import android.app.Service;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Process;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.Sct.gamebone.framework.GameApp;

public class BaseActivity extends Activity {
	protected static boolean mIsMute = false;
	protected static boolean mIsNotVibrate = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setFullscreen();
		GameApp.instance.setCurrentActivity(this);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
