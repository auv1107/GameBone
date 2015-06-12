package com.Sct.gamebone.activity;

import com.Sct.gamebone.framework.GameView;

import android.os.Bundle;

public class GameActivity extends BaseActivity implements GameView.Callback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GameView gv = new GameView(this);
		gv.setCallback(this);
		setContentView(gv);
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		finish();
	}

}
