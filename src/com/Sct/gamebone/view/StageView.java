package com.Sct.gamebone.view;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.Sct.gamebone.R;

public class StageView extends View{
	public static final int PASSED = 0;
	public static final int OPENING = 1;
	public static final int LOCKED = 2;
	public static final int PASSED_BACKGROUND_DRAWABLE = R.drawable.blue_btn;
	public static final int LOCKED_BACKGROUND_DRAWABLE = R.drawable.gray_btn;

	private int state = PASSED;
	private int star = 3;
	private int level = 0;
	private int[] words_ids = new int[]{R.drawable.stage1, R.drawable.stage2, R.drawable.stage3};
	
	public static StageView getView(Context context, int level, int state, int star) {
		StageView v = new StageView(context);
		v.setState(state);
		v.setStar(star);
		v.setLevel(level);
		return v;
	}
	

	public StageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void setState(int state) {
		this.state = state;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		Log.d("StageView", "onDraw");
		drawBackground(canvas);
		drawStage(canvas);
		drawState(canvas);
	}
	
	public void drawBackground(Canvas canvas) {
		Bitmap b = null;
		Bitmap candy = BitmapFactory.decodeResource(getResources(), R.drawable.menu_candy);
		switch (state) {
		case PASSED:
		case OPENING:
			b = BitmapFactory.decodeResource(getResources(), PASSED_BACKGROUND_DRAWABLE);
			break;
		case LOCKED:
			b = BitmapFactory.decodeResource(getResources(), LOCKED_BACKGROUND_DRAWABLE);
			break;
		}
		canvas.drawBitmap(b, 0, 0, new Paint());
		canvas.drawBitmap(candy, 30,20, new Paint());
	}
	public void drawState(Canvas canvas) {
		Bitmap b = null;
		switch (state) {
		case PASSED:
			b = BitmapFactory.decodeResource(getResources(), R.drawable.star);
			int startX = 700;
			for (int i = 0; i < star; i++) {
				canvas.drawBitmap(b, startX+i*80, 60, new Paint());
			}
			break;
		case OPENING:
			b = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_btn);
			canvas.drawBitmap(b, 750, 60, new Paint());
			break;
		case LOCKED:
			b = BitmapFactory.decodeResource(getResources(), R.drawable.lock);
			canvas.drawBitmap(b, 800, 60, new Paint());
			break;
		}
	}
	public void drawStage(Canvas canvas) {
		int id = words_ids[level];
		Bitmap b = BitmapFactory.decodeResource(getResources(), id);
		canvas.drawBitmap(b, 300, 60, new Paint());
	}

}
