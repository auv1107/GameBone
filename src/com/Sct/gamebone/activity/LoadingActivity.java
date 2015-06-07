package com.Sct.gamebone.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.Sct.gamebone.GameEntry;
import com.Sct.gamebone.R;

public class LoadingActivity extends BaseActivity implements AnimatorListener {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);

		// Show Animations here
		ImageView bg = (ImageView) findViewById(R.id.ld_bg);
		bg.setAlpha(0f);
		bg.animate().alpha(1.0f).setDuration(2000)
				.setInterpolator(new DecelerateInterpolator())
				.setListener(this).start();
		GameEntry.getInstance().prepare();
		GameEntry.getInstance().app();
		// ValueAnimator va = ValueAnimator.ofFloat();
	}

	@Override
	public void onAnimationCancel(Animator arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationEnd(Animator arg0) {
		// TODO Auto-generated method stub
		ImageView bg = (ImageView) findViewById(R.id.ld_bg);
		bg.animate().setListener(null);
		bg.animate().alpha(-1.0f).setDuration(2000)
				.setInterpolator(new AccelerateInterpolator())
				.setStartDelay(2000).setListener(new AnimatorListener() {

					@Override
					public void onAnimationStart(Animator arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationRepeat(Animator arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animator arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(LoadingActivity.this,
								MenuActivity.class);
						startActivity(intent);
						finish();
					}

					@Override
					public void onAnimationCancel(Animator arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	@Override
	public void onAnimationRepeat(Animator arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animator arg0) {
		// TODO Auto-generated method stub

	}

}
