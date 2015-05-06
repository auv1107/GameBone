package com.Sct.gamebone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MenuActivity extends BaseActivity implements OnClickListener {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView
		setOnClickListener(this);
		/* start of USELESS codes */
		View v = new View(this);
		v.setId(0);
		onClick(v);
		/* end of USELESS codes */
	}

	protected void setOnClickListener(OnClickListener listener) {
		// Associate event with button here
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		// Rewrite here
		case 0:
			intent.setClass(this, GameActivity.class);
			break;
		case 1:
			intent.setClass(this, SetupActivity.class);
			break;
		case 2:
			intent.setClass(this, HelpActivity.class);
		case 3:
			killApp();
			break;
		}

		startActivity(intent);
	}
}
