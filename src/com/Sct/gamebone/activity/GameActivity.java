package com.Sct.gamebone.activity;

import com.Sct.gamebone.framwork.GameView;

import android.os.Bundle;

public class GameActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new GameView(this));
	}

}
